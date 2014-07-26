package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.RORNMatchFireService;
import com.dianping.ba.finance.exchange.api.RORNMatchService;
import com.dianping.ba.finance.exchange.api.ReceiveNotifyService;
import com.dianping.ba.finance.exchange.api.ReceiveOrderService;
import com.dianping.ba.finance.exchange.api.beans.RORNMatchingResultBean;
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
import com.dianping.ba.finance.exchange.api.enums.ReceiveNotifyStatus;
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderStatus;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;
import com.dianping.finance.common.util.LionConfigUtils;
import com.google.common.collect.Lists;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by noahshen on 14-7-26.
 */
public class RORNMatchFireServiceObject implements RORNMatchFireService {

    private static final String REMATCH_TIMES = "ba-finance-exchange-biz.rematch.times";

    private ReceiveNotifyService receiveNotifyService;

    private ReceiveOrderService receiveOrderService;

    private RORNMatchService rornMatchService;

    private ExecutorService executorService;

    @Log(severity = 1, logBefore = true, logAfter = false)
    @ReturnDefault
    @Override
    public void executeMatchingForNewReceiveOrder(final ReceiveOrderData newROData) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                doExecuteMatchingForNewReceiveOrder(newROData);
            }
        });
    }

    @Override
    public void executeMatchingForNewReceiveNotify(final ReceiveNotifyData newRNData) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                doExecuteMatchingForNewReceiveNotify(newRNData);
            }
        });
    }

    @Override
    public void executeMatchingForReceiveOrderConfirmed(final ReceiveOrderData confirmedROData) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                doExecuteMatchingForReceiveOrderConfirmed(confirmedROData);
            }
        });
    }

    void doExecuteMatchingForReceiveOrderConfirmed(ReceiveOrderData confirmedROData) {
        String applicationId = confirmedROData.getApplicationId();
        if (StringUtils.isEmpty(applicationId)) {
            return;
        }
        // 先获取，
        List<ReceiveNotifyData> receiveNotifyDataList = receiveNotifyService.findUnmatchedLeftReceiveNotify(ReceiveNotifyStatus.MATCHED, applicationId);
        // 再清空MatchID修改状态
        List<Integer> rnIdList = buildRNIDList(receiveNotifyDataList);
        receiveNotifyService.clearReceiveNotifyMatchInfo(ReceiveNotifyStatus.INIT, rnIdList);

        List<ReceiveOrderData> receiveOrderDataList = receiveOrderService.findUnmatchAndUnconfirmedReceiveOrder(ReceiveOrderStatus.UNCONFIRMED);

        rornMatchService.matching(receiveOrderDataList, receiveNotifyDataList);
    }

    private List<Integer> buildRNIDList(List<ReceiveNotifyData> receiveNotifyDataList) {
        List<Integer> rnIdList = Lists.newLinkedList();
        for (ReceiveNotifyData rnData : receiveNotifyDataList) {
            rnIdList.add(rnData.getReceiveNotifyId());
        }
        return rnIdList;
    }

    void doExecuteMatchingForNewReceiveNotify(ReceiveNotifyData newRNData) {
        List<ReceiveNotifyData> doMatchRNDataList = Arrays.asList(newRNData);
        List<ReceiveOrderData> receiveOrderDataList = receiveOrderService.findUnmatchAndUnconfirmedReceiveOrder(ReceiveOrderStatus.UNCONFIRMED);

        // 直接调用匹配即可，无需重试
        rornMatchService.matching(receiveOrderDataList, doMatchRNDataList);
    }

    void doExecuteMatchingForNewReceiveOrder(ReceiveOrderData newROData) {
        int reMatchTimes = Integer.parseInt(LionConfigUtils.getProperty(REMATCH_TIMES, "3"));

        List<ReceiveOrderData> doMatchRODataList = Arrays.asList(newROData);
        do {
            List<ReceiveNotifyData> receiveNotifyDataList = receiveNotifyService.getUnMatchedReceiveNotify(ReceiveNotifyStatus.INIT);
            RORNMatchingResultBean resultBean = rornMatchService.matching(doMatchRODataList, receiveNotifyDataList);
            if (resultBean.getMatchUpdateFailedROList().isEmpty()) {
                break;
            } else {
                doMatchRODataList = resultBean.getMatchUpdateFailedROList();
            }
        } while (reMatchTimes-- > 0);
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void setReceiveNotifyService(ReceiveNotifyService receiveNotifyService) {
        this.receiveNotifyService = receiveNotifyService;
    }

    public void setRornMatchService(RORNMatchService rornMatchService) {
        this.rornMatchService = rornMatchService;
    }

    public void setReceiveOrderService(ReceiveOrderService receiveOrderService) {
        this.receiveOrderService = receiveOrderService;
    }
}
