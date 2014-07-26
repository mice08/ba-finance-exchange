package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.RORNMatchFireService;
import com.dianping.ba.finance.exchange.api.RORNMatchService;
import com.dianping.ba.finance.exchange.api.ReceiveNotifyService;
import com.dianping.ba.finance.exchange.api.beans.RORNMatchingResultBean;
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
import com.dianping.ba.finance.exchange.api.enums.ReceiveNotifyStatus;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;
import com.dianping.finance.common.util.LionConfigUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by noahshen on 14-7-26.
 */
public class RORNMatchFireServiceObject implements RORNMatchFireService {

    private static final String REMATCH_TIMES = "ba-finance-exchange-biz.rematch.times";

    private ReceiveNotifyService receiveNotifyService;

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
}
