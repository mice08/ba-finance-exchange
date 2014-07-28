package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.*;
import com.dianping.ba.finance.exchange.api.beans.ReceiveNotifyResultBean;
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData;
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyRecordData;
import com.dianping.ba.finance.exchange.api.dtos.ReceiveNotifyDTO;
import com.dianping.ba.finance.exchange.api.dtos.ReceiveNotifyResultDTO;
import com.dianping.ba.finance.exchange.api.enums.ReceiveNotifyCheckResult;
import com.dianping.ba.finance.exchange.api.enums.ReceiveNotifyResultStatus;
import com.dianping.ba.finance.exchange.api.enums.ReceiveNotifyStatus;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;
import com.dianping.finance.common.swallow.SwallowEventBean;
import com.dianping.finance.common.swallow.SwallowProducer;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.ExecutorService;

/**
 * Created by Administrator on 2014/7/23.
 */
public class ReceiveNotifyHandleServiceObject implements ReceiveNotifyHandleService {

    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.biz.monitor.ReceiveNotifyHandleServiceObject");

    private long timeout;

    private ReceiveNotifyRecordService receiveNotifyRecordService;

    private ReceiveNotifyService receiveNotifyService;

    private AccessTokenService accessTokenService;

    private SwallowProducer receiveNotifyResultProducer;

    private RORNMatchFireService rornMatchFireService;

    private ExecutorService executorService;

    @Log(logBefore = true, logAfter = true, severity = 1)
    @ReturnDefault
    @Override
    public void handleReceiveNotify(final ReceiveNotifyDTO receiveNotifyDTO) {
        final ReceiveNotifyRecordData receiveNotifyRecordData = buildReceiveNotifyRecordDate(receiveNotifyDTO);
        receiveNotifyRecordService.insertReceiveNotifyRecord(receiveNotifyRecordData);

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                doHandle(receiveNotifyRecordData,receiveNotifyDTO);
            }
        });
    }

    private void doHandle(ReceiveNotifyRecordData receiveNotifyRecordData, ReceiveNotifyDTO receiveNotifyDTO) {
        ReceiveNotifyResultBean receiveNotifyResultBean = new ReceiveNotifyResultBean();
        receiveNotifyResultBean.setApplicationId(receiveNotifyRecordData.getApplicationId());
        receiveNotifyResultBean.setStatus(ReceiveNotifyResultStatus.SUCCESS);
        receiveNotifyResultBean.setMemo("");

        if(!checkNotify(receiveNotifyDTO, receiveNotifyResultBean)){
            resultNotify(receiveNotifyResultBean);
            return;
        }

        ReceiveNotifyData receiveNotifyData = buildReceiveNotifyData(receiveNotifyDTO);
        try {
            int receiveNotifyId = receiveNotifyService.insertReceiveNotify(receiveNotifyData);
            receiveNotifyData.setReceiveNotifyId(receiveNotifyId);
            receiveNotifyResultBean.setReceiveNotifyId(receiveNotifyId);

        }catch (Exception e ){
            receiveNotifyResultBean.setStatus(ReceiveNotifyResultStatus.FAIL);
            receiveNotifyResultBean.setMemo(ReceiveNotifyCheckResult.DUPLICATE_APPLICATIONID.toString());
        }

        resultNotify(receiveNotifyResultBean);
        rornMatchFireService.executeMatchingForNewReceiveNotify(receiveNotifyData);
    }

    private boolean checkNotify(ReceiveNotifyDTO receiveNotifyDTO, ReceiveNotifyResultBean receiveNotifyResultBean) {
        if(!checkTimeout(receiveNotifyDTO)){
            receiveNotifyResultBean.setStatus(ReceiveNotifyResultStatus.FAIL);
            receiveNotifyResultBean.setMemo(ReceiveNotifyCheckResult.TIMEOUT.toString());
            return false;
        }
        if(!checkToken(receiveNotifyDTO)){
            receiveNotifyResultBean.setStatus(ReceiveNotifyResultStatus.FAIL);
            receiveNotifyResultBean.setMemo(ReceiveNotifyCheckResult.INVALID_TOKEN.toString());
            return false;
        }
        if(!checkReceiveAmount(receiveNotifyDTO)){
            receiveNotifyResultBean.setStatus(ReceiveNotifyResultStatus.FAIL);
            receiveNotifyResultBean.setMemo(ReceiveNotifyCheckResult.INVALID_RECEIVEAMOUNT.toString());
            return false;
        }
        return true;
    }

    private void resultNotify(ReceiveNotifyResultBean receiveNotifyResultBean) {
        ReceiveNotifyResultDTO receiveNotifyResultDTO = buildReceiveNotifyResultDTO(receiveNotifyResultBean);
        SwallowEventBean swallowEventBean = new SwallowEventBean("FS_AD_RECEIVE_NOTIFY_RESULT",receiveNotifyResultDTO);
        receiveNotifyResultProducer.fireSwallowEvent(swallowEventBean);
    }

    private boolean checkTimeout(ReceiveNotifyDTO receiveNotifyDTO){
        Date current = new Date();
        Date requestTime = receiveNotifyDTO.getRequestTime();

        if(requestTime == null){
            return false;
        }

        return current.getTime() - requestTime.getTime()  <= timeout;
    }

    private boolean checkToken(ReceiveNotifyDTO receiveNotifyDTO){
        String token = receiveNotifyDTO.getToken();
        if (StringUtils.isEmpty(token)) {
            MONITOR_LOGGER.error(String.format("severity=[1] NewPayRequestServiceObject.checkToken error, token is empty, PayRequestDTO=%s", receiveNotifyDTO));
            return false;
        }
        int businessType = receiveNotifyDTO.getBusinessType();
        return accessTokenService.verifyAccessToken(token, businessType);
    }

    private boolean checkReceiveAmount(ReceiveNotifyDTO receiveNotifyDTO) {
        BigDecimal receiveAmount = receiveNotifyDTO.getReceiveAmount() != null ? receiveNotifyDTO.getReceiveAmount() : BigDecimal.ZERO;
        return receiveAmount.compareTo(BigDecimal.ZERO) > 0;
    }

    private ReceiveNotifyRecordData buildReceiveNotifyRecordDate(ReceiveNotifyDTO receiveNotifyDTO) {
        ReceiveNotifyRecordData receiveNotifyRecordData = new ReceiveNotifyRecordData();
        receiveNotifyRecordData.setApplicationId(receiveNotifyDTO.getApplicationId());
        receiveNotifyRecordData.setBusinessType(receiveNotifyDTO.getBusinessType());
        receiveNotifyRecordData.setReceiveAmount(receiveNotifyDTO.getReceiveAmount());
        receiveNotifyRecordData.setCustomerId(receiveNotifyDTO.getCustomerId());
        receiveNotifyRecordData.setPayChannel(receiveNotifyDTO.getPayChannel());
        receiveNotifyRecordData.setPayTime(receiveNotifyDTO.getPayTime());
        receiveNotifyRecordData.setPayerName(receiveNotifyDTO.getPayerName());
        receiveNotifyRecordData.setBizContent(receiveNotifyDTO.getBizContent());
        receiveNotifyRecordData.setAttachment(receiveNotifyDTO.getAttachment());
        receiveNotifyRecordData.setMemo(receiveNotifyDTO.getMemo());
        receiveNotifyRecordData.setRequestTime(receiveNotifyDTO.getRequestTime());
        receiveNotifyRecordData.setToken(receiveNotifyDTO.getToken());
        receiveNotifyRecordData.setBankId(receiveNotifyDTO.getBankId());
        return receiveNotifyRecordData;
    }

    private ReceiveNotifyResultDTO buildReceiveNotifyResultDTO(ReceiveNotifyResultBean receiveNotifyResultBean) {
        ReceiveNotifyResultDTO receiveNotifyResultDTO = new ReceiveNotifyResultDTO();
        receiveNotifyResultDTO.setApplicationId(receiveNotifyResultBean.getApplicationId());
        receiveNotifyResultDTO.setReceiveNotifyId(receiveNotifyResultBean.getReceiveNotifyId());
        receiveNotifyResultDTO.setMemo(receiveNotifyResultBean.getMemo());
        receiveNotifyResultDTO.setStatus(receiveNotifyResultBean.getStatus().value());
        return receiveNotifyResultDTO;
    }

    private ReceiveNotifyData buildReceiveNotifyData(ReceiveNotifyDTO receiveNotifyDTO) {
        ReceiveNotifyData receiveNotifyData = new ReceiveNotifyData();
        receiveNotifyData.setApplicationId(receiveNotifyDTO.getApplicationId());
        receiveNotifyData.setBusinessType(receiveNotifyDTO.getBusinessType());
        receiveNotifyData.setReceiveAmount(receiveNotifyDTO.getReceiveAmount());
        receiveNotifyData.setPayChannel(receiveNotifyDTO.getPayChannel());
        receiveNotifyData.setPayTime(receiveNotifyDTO.getPayTime());
        receiveNotifyData.setPayerName(receiveNotifyDTO.getPayerName());
        receiveNotifyData.setBizContent(receiveNotifyDTO.getBizContent());
        receiveNotifyData.setCustomerId(receiveNotifyDTO.getCustomerId());
        receiveNotifyData.setBankId(receiveNotifyDTO.getBankId());
        receiveNotifyData.setAttachment(receiveNotifyDTO.getAttachment());
        receiveNotifyData.setStatus(ReceiveNotifyStatus.INIT.value());
        receiveNotifyData.setRoMatcherId(0);
        receiveNotifyData.setMemo(receiveNotifyDTO.getMemo());
        receiveNotifyData.setAddTime(new Date());
        receiveNotifyData.setAddLoginId(-1);
        receiveNotifyData.setUpdateTime(new Date());
        receiveNotifyData.setUpdateLoginId(-1);
        return receiveNotifyData;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public void setReceiveNotifyRecordService(ReceiveNotifyRecordService receiveNotifyRecordService) {
        this.receiveNotifyRecordService = receiveNotifyRecordService;
    }

    public void setReceiveNotifyService(ReceiveNotifyService receiveNotifyService) {
        this.receiveNotifyService = receiveNotifyService;
    }

    public void setAccessTokenService(AccessTokenService accessTokenService) {
        this.accessTokenService = accessTokenService;
    }

    public void setReceiveNotifyResultProducer(SwallowProducer receiveNotifyResultProducer) {
        this.receiveNotifyResultProducer = receiveNotifyResultProducer;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void setRornMatchFireService(RORNMatchFireService rornMatchFireService) {
        this.rornMatchFireService = rornMatchFireService;
    }
}
