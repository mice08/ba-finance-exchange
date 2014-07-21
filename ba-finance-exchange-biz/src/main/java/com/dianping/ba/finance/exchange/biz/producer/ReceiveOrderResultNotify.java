package com.dianping.ba.finance.exchange.biz.producer;

import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderResultBean;
import com.dianping.ba.finance.exchange.api.dtos.ReceiveResultNotifyDTO;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.swallow.SwallowEventBean;
import com.dianping.finance.common.swallow.SwallowProducer;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: bingqiu.yuan
 * Date: 14-6-11
 * Time: 下午3:07
 * To change this template use File | Settings | File Templates.
 */
public class ReceiveOrderResultNotify {

    private SwallowProducer receiveOrderProducer;

    public static final String AD_RECEIVE_RESULT_EVENT_KEY = "FS_AD_RECEIVE_RESULT";

    public static final String TS_RECEIVE_RESULT_EVENT_KEY = "FS_TS_RECEIVE_RESULT";

    @Log(severity = 1, logBefore = true, logAfter = true)
    public void receiveResultNotify(ReceiveOrderResultBean receiveOrderResultBean) {
        ReceiveResultNotifyDTO receiveResultNotifyDTO = buildReceiveResultNotifyDTO(receiveOrderResultBean);
        if(receiveOrderResultBean.getBusinessType() == BusinessType.ADVERTISEMENT){
            SwallowEventBean eventBean = new SwallowEventBean(AD_RECEIVE_RESULT_EVENT_KEY, receiveResultNotifyDTO);
            receiveOrderProducer.fireSwallowEvent(eventBean);
        } else if(receiveOrderResultBean.getBusinessType() == BusinessType.GROUP_PURCHASE){
            SwallowEventBean eventBean = new SwallowEventBean(TS_RECEIVE_RESULT_EVENT_KEY, receiveResultNotifyDTO);
            receiveOrderProducer.fireSwallowEvent(eventBean);
        }
    }

    private ReceiveResultNotifyDTO buildReceiveResultNotifyDTO(ReceiveOrderResultBean receiveOrderResultBean) {
        ReceiveResultNotifyDTO receiveResultNotifyDTO = new ReceiveResultNotifyDTO();
        receiveResultNotifyDTO.setBizId(String.valueOf(receiveOrderResultBean.getRoId()));
        receiveResultNotifyDTO.setCustomerId(receiveOrderResultBean.getCustomerId());
        receiveResultNotifyDTO.setShopId(receiveOrderResultBean.getShopId());
        BigDecimal receiveAmount = receiveOrderResultBean.getReceiveAmount();
        if (receiveAmount != null) {
            int type = receiveAmount.compareTo(BigDecimal.ZERO) > 0 ? 1 : 2;
            receiveResultNotifyDTO.setType(type);
            receiveResultNotifyDTO.setReceiveAmount(receiveAmount.abs());
        }
        receiveResultNotifyDTO.setPayChannel(receiveOrderResultBean.getPayChannel().value());
        receiveResultNotifyDTO.setReceiveType(receiveOrderResultBean.getReceiveType().value());
        receiveResultNotifyDTO.setBizContent(receiveOrderResultBean.getBizContent());
        receiveResultNotifyDTO.setPayTime(receiveOrderResultBean.getPayTime());
        receiveResultNotifyDTO.setReceiveTime(receiveOrderResultBean.getReceiveTime());
        receiveResultNotifyDTO.setBankReceiveTime(receiveOrderResultBean.getBankReceiveTime());
        receiveResultNotifyDTO.setBankId(receiveOrderResultBean.getBankId());
        receiveResultNotifyDTO.setTradeNo(receiveOrderResultBean.getTradeNo());
        receiveResultNotifyDTO.setMemo(receiveOrderResultBean.getMemo());
		receiveResultNotifyDTO.setOriBizId(String.valueOf(receiveOrderResultBean.getReverseRoId()));
        return receiveResultNotifyDTO;
    }

    public void setReceiveOrderProducer(SwallowProducer receiveOrderProducer) {
        this.receiveOrderProducer = receiveOrderProducer;
    }
}
