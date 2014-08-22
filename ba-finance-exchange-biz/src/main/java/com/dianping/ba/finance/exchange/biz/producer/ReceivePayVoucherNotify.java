package com.dianping.ba.finance.exchange.biz.producer;

import com.dianping.ba.finance.exchange.api.beans.ReceiveVoucherNotifyBean;
import com.dianping.ba.finance.exchange.api.dtos.ReceiveVoucherNotifyDTO;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;
import com.dianping.finance.common.swallow.SwallowEventBean;
import com.dianping.finance.common.swallow.SwallowProducer;

/**
 *
 */
public class ReceivePayVoucherNotify {

    public static final String RECEIVE_PAY_VOUCHER_EVENT_KEY = "RECEIVE_PAY_VOUCHER_NOTIFY";

    private SwallowProducer receivePayVoucherProducer;

    @Log(severity = 1, logBefore = true, logAfter = true)
    @ReturnDefault
    public void notifyVoucher(ReceiveVoucherNotifyBean receiveVoucherNotifyBean) {
        ReceiveVoucherNotifyDTO receiveNotifyResultDTO = buildReceiveVoucherNotifyDTO(receiveVoucherNotifyBean);
        SwallowEventBean swallowEventBean = new SwallowEventBean(RECEIVE_PAY_VOUCHER_EVENT_KEY, receiveNotifyResultDTO);
        receivePayVoucherProducer.fireSwallowEvent(swallowEventBean);
    }

    private ReceiveVoucherNotifyDTO buildReceiveVoucherNotifyDTO(ReceiveVoucherNotifyBean receiveVoucherNotifyBean) {
        ReceiveVoucherNotifyDTO notifyDTO = new ReceiveVoucherNotifyDTO();
        notifyDTO.setAmount(receiveVoucherNotifyBean.getAmount());
        notifyDTO.setBankId(receiveVoucherNotifyBean.getBankId());
        notifyDTO.setBizId(receiveVoucherNotifyBean.getBizId());
        notifyDTO.setBizInfo(receiveVoucherNotifyBean.getBizInfo());
        notifyDTO.setCityId(receiveVoucherNotifyBean.getCityId());
        notifyDTO.setCompanyId(receiveVoucherNotifyBean.getCompanyId());
        notifyDTO.setCustomerId(receiveVoucherNotifyBean.getCustomerId());
        notifyDTO.setShopId(receiveVoucherNotifyBean.getShopId());
        notifyDTO.setVoucherDate(receiveVoucherNotifyBean.getVoucherDate());
        notifyDTO.setVoucherType(receiveVoucherNotifyBean.getVoucherType());
        return notifyDTO;
    }

    public void setReceivePayVoucherProducer(SwallowProducer receivePayVoucherProducer) {
        this.receivePayVoucherProducer = receivePayVoucherProducer;
    }
}
