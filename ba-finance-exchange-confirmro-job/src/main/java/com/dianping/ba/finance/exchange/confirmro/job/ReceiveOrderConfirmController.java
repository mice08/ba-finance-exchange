package com.dianping.ba.finance.exchange.confirmro.job;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.ReceiveOrderService;
import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderSearchBean;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderStatus;


public class ReceiveOrderConfirmController {

    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.confirmro.job.ReceiveOrderConfirmController");

    private ReceiveOrderService receiveOrderService;

    public boolean execute() {
        MONITOR_LOGGER.info(String.format("ReceiveOrderConfirmController.execute start"));
        try {
            ReceiveOrderSearchBean searchBean = new ReceiveOrderSearchBean();
            searchBean.setBusinessType(BusinessType.ADVERTISEMENT.value());
            searchBean.setStatus(ReceiveOrderStatus.UNCONFIRMED.value());
            receiveOrderService.fireAutoConfirm(searchBean);
        } catch (Exception e) {
            MONITOR_LOGGER.error(String.format("severity=[2] ReceiveOrderConfirmController.execute error"), e);
        }
        return true;
    }

    public void setReceiveOrderService(ReceiveOrderService receiveOrderService) {
        this.receiveOrderService = receiveOrderService;
    }
}
