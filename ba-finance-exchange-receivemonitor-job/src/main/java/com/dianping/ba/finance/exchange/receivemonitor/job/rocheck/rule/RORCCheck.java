package com.dianping.ba.finance.exchange.receivemonitor.job.rocheck.rule;

import com.dianping.ba.finance.exchange.receivemonitor.api.ReceiveConfirmMonitorService;
import com.dianping.ba.finance.exchange.receivemonitor.api.datas.ReceiveConfirmMonitorData;
import com.dianping.ba.finance.exchange.receivemonitor.api.datas.ReceiveOrderMonitorData;
import com.dianping.ba.finance.exchange.receivemonitor.api.enums.ExceptionType;
import com.dianping.ba.finance.exchange.receivemonitor.api.enums.MonitorReceiveOrderStatus;
import com.dianping.ba.finance.exchange.receivemonitor.api.enums.ReceiveConfirmStatus;
import com.dianping.ba.finance.exchange.receivemonitor.job.rocheck.ROCheckBase;
import com.dianping.ba.finance.exchange.receivemonitor.job.rocheck.ROCheckResult;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * 监控收款单，流程简单说就是对已确认的收款单查找对应的收款确认，如果状态一致，OK通过。
 * 如果未找到收款确认，未超时，加入Todo，超时，告警。
 * 如果找到，状态不一致，告警。
 * by yaxiong.cheng
 */
public class RORCCheck extends ROCheckBase {

    private ReceiveConfirmMonitorService receiveConfirmMonitorService;

    @Override
    public boolean filter(ReceiveOrderMonitorData receiveOrderMonitorData) {
        return receiveOrderMonitorData.getStatus() == MonitorReceiveOrderStatus.CONFIRMED.value();
    }

    @Override
    public ROCheckResult check(ReceiveOrderMonitorData receiveOrderMonitorData) {
        ReceiveConfirmMonitorData rcData = receiveConfirmMonitorService.loadReceiveConfirmData(receiveOrderMonitorData.getRoId());
        if (rcData==null) {
            return createResult(false, checkIfTimeout(receiveOrderMonitorData.getLastUpdateDate()), ExceptionType.RO_SUCCESS_WITHOUT_RC_TIMEOUT);
        } else if (rcData.getStatus()== ReceiveConfirmStatus.CONFIRM_FAILURE.value()) {
            return createResult(false, checkIfTimeout(receiveOrderMonitorData.getLastUpdateDate()), ExceptionType.RO_SUCCESS_WITH_WRONG_RC);
        }
        return createValidResult();
    }

    public void setReceiveConfirmMonitorService(ReceiveConfirmMonitorService receiveConfirmMonitorService) {
        this.receiveConfirmMonitorService = receiveConfirmMonitorService;
    }
}
