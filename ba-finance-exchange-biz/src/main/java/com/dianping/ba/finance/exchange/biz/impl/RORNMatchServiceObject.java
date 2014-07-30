package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.RORNMatchService;
import com.dianping.ba.finance.exchange.api.ReceiveNotifyService;
import com.dianping.ba.finance.exchange.api.beans.RORNMatchingResultBean;
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
import com.dianping.ba.finance.exchange.api.enums.ReceiveNotifyStatus;
import com.dianping.ba.finance.exchange.biz.rornmatcher.RORNMatcher;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

import java.util.Iterator;
import java.util.List;

/**
 * Created by noahshen on 14-7-25.
 */
public class RORNMatchServiceObject implements RORNMatchService {

    private ReceiveNotifyService receiveNotifyService;

    private List<RORNMatcher> matchers;


    @Log(severity = 1, logBefore = true, logAfter = false)
    @ReturnDefault
    @Override
    public RORNMatchingResultBean matching(List<ReceiveOrderData> receiveOrderDataList, List<ReceiveNotifyData> receiveNotifyDataList) {
        RORNMatchingResultBean resultBean = new RORNMatchingResultBean();
        if (CollectionUtils.isEmpty(receiveOrderDataList) || CollectionUtils.isEmpty(receiveNotifyDataList)) {
            return resultBean;
        }
        List<ReceiveOrderData> matchRODataList = Lists.newLinkedList(receiveOrderDataList);
        List<ReceiveNotifyData> matchRNDataList = Lists.newLinkedList(receiveNotifyDataList);
        for (ReceiveOrderData roData : matchRODataList) {
            matchReceiveNotify(roData, matchRNDataList, resultBean);
        }
        return resultBean;
    }

    private void matchReceiveNotify(ReceiveOrderData roData, List<ReceiveNotifyData> matchRNDataList, RORNMatchingResultBean resultBean) {
        boolean findMatched = false;
        boolean updateAtLeastOne = false;
        for (Iterator<ReceiveNotifyData> it = matchRNDataList.iterator(); it.hasNext();) {
            ReceiveNotifyData rnData = it.next();
            boolean match = doMatch(roData, rnData);
            if (match) {
                findMatched = true;
                boolean updated = receiveNotifyService.updateReceiveNotifyMatchId(ReceiveNotifyStatus.MATCHED.value(),
                        roData.getRoId(),
                        ReceiveNotifyStatus.INIT.value(),
                        rnData.getReceiveNotifyId());
                it.remove();
                // 未更新成功，可能是其他的Service实例更新了该收款通知的状态
                if (updated) {
                    updateAtLeastOne = true;
                }
            }
        }
        // 找到匹配的，但是一条收款通知都未更新成功
        if (findMatched && !updateAtLeastOne) {
            resultBean.addUpdateFailedRO(roData);
        }
    }

    private boolean doMatch(ReceiveOrderData roData, ReceiveNotifyData rnData) {
        for (RORNMatcher matcher : matchers) {
            boolean tmp = matcher.match(roData, rnData);
            if (tmp) {
                return true;
            }
        }
        return false;
    }

    public void setMatchers(List<RORNMatcher> matchers) {
        this.matchers = matchers;
    }

    public void setReceiveNotifyService(ReceiveNotifyService receiveNotifyService) {
        this.receiveNotifyService = receiveNotifyService;
    }

}
