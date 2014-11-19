package com.dianping.ba.finance.exchange.biz.producer;

import com.dianping.ba.finance.exchange.api.beans.PayOrderResultBean;
import com.dianping.ba.finance.exchange.api.dtos.PayResultNotifyDTO;
import com.dianping.ba.finance.exchange.api.enums.PayResultStatus;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.swallow.SwallowEventBean;
import com.dianping.finance.common.swallow.SwallowProducer;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: bingqiu.yuan
 * Date: 14-6-11
 * Time: 下午3:07
 * To change this template use File | Settings | File Templates.
 */
public class PayOrderResultNotify {

    private SwallowProducer payOrderProducer;

    public static final String EXCHANGE_PAY_RESULT_EVENT_KEY = "EX_FS_PAY_RESULT";

    @Log(severity = 1, logBefore = true, logAfter = true)
    public void payResultNotify(PayOrderResultBean payOrderResultBean) {
        PayResultNotifyDTO payResultNotifyDTO = buildPayResultNotifyDTO(payOrderResultBean);
        SwallowEventBean eventBean = new SwallowEventBean(EXCHANGE_PAY_RESULT_EVENT_KEY, payResultNotifyDTO);
        payOrderProducer.fireSwallowEvent(eventBean);
    }

    private PayResultNotifyDTO buildPayResultNotifyDTO(PayOrderResultBean payOrderResultBean) {
        PayResultNotifyDTO payResultNotifyDTO = new PayResultNotifyDTO();
        payResultNotifyDTO.setPaySequence(payOrderResultBean.getPaySequence());
        StringBuilder memoSb = new StringBuilder(payOrderResultBean.getStatus().toString());
        if (StringUtils.isNotBlank(payOrderResultBean.getMemo())) {
            memoSb.append("-").append(payOrderResultBean.getMemo());
        }
        payResultNotifyDTO.setMemo(memoSb.toString());
        payResultNotifyDTO.setLoginId(payOrderResultBean.getLoginId());
        payResultNotifyDTO.setPoId(payOrderResultBean.getPoId());
        payResultNotifyDTO.setPaidAmount(BigDecimal.ZERO);
        payResultNotifyDTO.setStatus(payOrderResultBean.getStatus().value());
        if (payOrderResultBean.getStatus() == PayResultStatus.PAY_SUCCESS){
            payResultNotifyDTO.setPaidAmount(payOrderResultBean.getPaidAmount());
        }
        return payResultNotifyDTO;
    }

    public void setPayOrderProducer(SwallowProducer payOrderProducer) {
        this.payOrderProducer = payOrderProducer;
    }
}
