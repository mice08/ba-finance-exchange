package com.dianping.ba.finance.exchange.biz.producer;

import com.dianping.ba.finance.exchange.api.beans.ReceiveNotifyResultBean;
import com.dianping.ba.finance.exchange.api.dtos.ReceiveNotifyResultDTO;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.swallow.SwallowEventBean;
import com.dianping.finance.common.swallow.SwallowProducer;

/**
 * Created with IntelliJ IDEA.
 * User: bingqiu.yuan
 * Date: 14-6-11
 * Time: 下午3:07
 * To change this template use File | Settings | File Templates.
 */
public class ReceiveNotifyResultNotify {

    private SwallowProducer receiveNotifyProducer;

    public static final String AD_RECEIVE_NOTIFY_RESULT_EVENT_KEY = "FS_AD_RECEIVE_NOTIFY_RESULT";

    @Log(severity = 1, logBefore = true, logAfter = true)
    public void resultNotify(ReceiveNotifyResultBean receiveNotifyResultBean) {
        ReceiveNotifyResultDTO receiveNotifyResultDTO = buildReceiveNotifyResultDTO(receiveNotifyResultBean);
        if (receiveNotifyResultBean.getBusinessType() == BusinessType.ADVERTISEMENT.value()) {
            SwallowEventBean swallowEventBean = new SwallowEventBean(AD_RECEIVE_NOTIFY_RESULT_EVENT_KEY, receiveNotifyResultDTO);
            receiveNotifyProducer.fireSwallowEvent(swallowEventBean);
        }
    }

    private ReceiveNotifyResultDTO buildReceiveNotifyResultDTO(ReceiveNotifyResultBean receiveNotifyResultBean) {
        ReceiveNotifyResultDTO receiveNotifyResultDTO = new ReceiveNotifyResultDTO();
        receiveNotifyResultDTO.setApplicationId(receiveNotifyResultBean.getApplicationId());
        receiveNotifyResultDTO.setReceiveNotifyId(receiveNotifyResultBean.getReceiveNotifyId());
        receiveNotifyResultDTO.setMemo(receiveNotifyResultBean.getMemo());
        receiveNotifyResultDTO.setStatus(receiveNotifyResultBean.getStatus().value());
        return receiveNotifyResultDTO;
    }

    public void setReceiveNotifyProducer(SwallowProducer receiveNotifyProducer) {
        this.receiveNotifyProducer = receiveNotifyProducer;
    }
}
