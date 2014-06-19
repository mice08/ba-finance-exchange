package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.PayCentreReceiveRequestHandleService;
import com.dianping.ba.finance.exchange.api.PayCentreReceiveRequestService;
import com.dianping.ba.finance.exchange.api.ReceiveOrderService;
import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderResultBean;
import com.dianping.ba.finance.exchange.api.datas.PayCentreReceiveRequestData;
import com.dianping.ba.finance.exchange.api.dtos.PayCentreReceiveRequestDTO;
import com.dianping.ba.finance.exchange.biz.producer.ReceiveOrderResultNotify;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;
import com.dianping.finance.common.util.DateUtils;

import java.util.concurrent.ExecutorService;

/**
 * 处理支付中心收款请求的Service类
 */
public class PayCentreReceiveRequestHandleServiceObject implements PayCentreReceiveRequestHandleService {

    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.biz.monitor.PayCentreReceiveRequestHandleServiceObject");

    private ReceiveOrderService receiveOrderService;

    private PayCentreReceiveRequestService payCentreReceiveRequestService;

    private ReceiveOrderResultNotify receiveOrderResultNotify;

    private ExecutorService executorService;

    @Log(logBefore = true, logAfter = true, severity = 1)
    @ReturnDefault
    @Override
    public boolean handleReceiveRequest(final PayCentreReceiveRequestDTO payCentreReceiveRequestDTO) {
        final PayCentreReceiveRequestData payCentreReceiveRequestData = buildPayCentreReceiveRequestData(payCentreReceiveRequestDTO);
        payCentreReceiveRequestService.insertPayCentreReceiveRequest(payCentreReceiveRequestData);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                doHandle(payCentreReceiveRequestDTO);
            }
        });
        return true;
    }

    private PayCentreReceiveRequestData buildPayCentreReceiveRequestData(PayCentreReceiveRequestDTO payCentreReceiveRequestDTO) {
        PayCentreReceiveRequestData payCentreReceiveRequestData=new PayCentreReceiveRequestData();
        payCentreReceiveRequestData.setReceiveAmount(payCentreReceiveRequestDTO.getReceiveAmount());
        payCentreReceiveRequestData.setReceiveDate(payCentreReceiveRequestDTO.getReceiveDate());
        payCentreReceiveRequestData.setBankId(payCentreReceiveRequestDTO.getBankId());
        payCentreReceiveRequestData.setBizContent(payCentreReceiveRequestDTO.getBizContent());
        payCentreReceiveRequestData.setBusinessType(payCentreReceiveRequestDTO.getBusinessType());
        payCentreReceiveRequestData.setMemo(payCentreReceiveRequestDTO.getMemo());
        payCentreReceiveRequestData.setOriTradeNo(payCentreReceiveRequestDTO.getOriTradeNo());
        payCentreReceiveRequestData.setPayChannel(payCentreReceiveRequestDTO.getPayChannel());
        payCentreReceiveRequestData.setPayMethod(payCentreReceiveRequestDTO.getPayMethod());
        payCentreReceiveRequestData.setTradeType(payCentreReceiveRequestDTO.getTradeType());
        payCentreReceiveRequestData.setAddTime(DateUtils.getCurrentTime());
        return payCentreReceiveRequestData;
    }

    /**
     * 处理支付中心收款请求
     * @param payCentreReceiveRequestDTO
     */
    private void doHandle(PayCentreReceiveRequestDTO payCentreReceiveRequestDTO) {

        ReceiveOrderResultBean receiveOrderResultBean=new ReceiveOrderResultBean();
        receiveOrderResultNotify.receiveResultNotify(receiveOrderResultBean);
    }

    public void setReceiveOrderService(ReceiveOrderService receiveOrderService) {
        this.receiveOrderService = receiveOrderService;
    }

    public void setReceiveOrderResultNotify(ReceiveOrderResultNotify receiveOrderResultNotify) {
        this.receiveOrderResultNotify = receiveOrderResultNotify;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void setPayCentreReceiveRequestService(PayCentreReceiveRequestService payCentreReceiveRequestService) {
        this.payCentreReceiveRequestService = payCentreReceiveRequestService;
    }
}
