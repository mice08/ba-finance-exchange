package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.ReceiveBankService;
import com.dianping.ba.finance.exchange.api.ReceiveOrderService;
import com.dianping.ba.finance.exchange.api.ReceiveVoucherService;
import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderSearchBean;
import com.dianping.ba.finance.exchange.api.beans.ReceiveVoucherNotifyBean;
import com.dianping.ba.finance.exchange.api.datas.ReceiveBankData;
import com.dianping.ba.finance.exchange.api.datas.ReceiveCalResultData;
import com.dianping.ba.finance.exchange.api.datas.ReceiveVoucherData;
import com.dianping.ba.finance.exchange.api.enums.AccountingVoucherType;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderPayChannel;
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderStatus;
import com.dianping.ba.finance.exchange.biz.dao.ReceiveVoucherDao;
import com.dianping.ba.finance.exchange.biz.producer.ReceivePayVoucherNotify;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;
import com.dianping.finance.common.util.DateUtils;
import com.google.common.collect.Lists;

import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by noahshen on 14-8-21.
 */
public class ReceiveVoucherServiceObject implements ReceiveVoucherService {

    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.biz.monitor.ReceiveVoucherServiceObject");

    private ReceiveOrderService receiveOrderService;

    private ReceiveVoucherDao receiveVoucherDao;

    private ReceivePayVoucherNotify receivePayVoucherNotify;

    private ReceiveBankService receiveBankService;

    @Log(severity = 1, logBefore = true, logAfter = true)
    @ReturnDefault
    @Override
    public void generateUnconfirmedReceiveVoucher(Date date) {
        try {
            Date today = DateUtils.addDate(date, 1);
            Date endDate = DateUtils.removeTime(today);
            Date startDate = DateUtils.removeTime(date);
            ReceiveOrderSearchBean searchBean = buildReceiveOrderSearchBean(startDate, endDate);
            List<ReceiveCalResultData> receiveCalResultDataList = receiveOrderService.findCalculatedReceiveResult(searchBean);
            for (ReceiveCalResultData rcData : receiveCalResultDataList) {
                List<ReceiveVoucherData> rvDataList = buildReceiveVoucherData(rcData);
                for (ReceiveVoucherData rvData : rvDataList) {
                    int voucherId = receiveVoucherDao.insertReceiveVoucherData(rvData);
                    if (voucherId <= 0) {
                        MONITOR_LOGGER.error(String.format("severity=[1] ReceiveVoucherServiceObject.generateUnconfirmedReceiveVoucher insertReceiveVoucherData error! receiveVoucherData=%s", rvData));
                        continue;
                    }
                    rvData.setVoucherId(voucherId);
                    ReceiveVoucherNotifyBean notifyBean = buildReceiveVoucherNotifyBean(rvData);
                    receivePayVoucherNotify.notifyVoucher(notifyBean);
                }
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private ReceiveVoucherNotifyBean buildReceiveVoucherNotifyBean(ReceiveVoucherData rvData) {
        ReceiveVoucherNotifyBean notifyBean = new ReceiveVoucherNotifyBean();
        notifyBean.setAmount(rvData.getAmount());
        notifyBean.setBankId(rvData.getBankId());
        notifyBean.setBizId(String.valueOf(rvData.getVoucherId()));
//        notifyBean.setBizInfo();
        notifyBean.setCityId(rvData.getCityId());
        notifyBean.setCompanyId(rvData.getCompanyId());
        notifyBean.setCustomerId(rvData.getCustomerId());
        notifyBean.setShopId(rvData.getShopId());
        notifyBean.setVoucherDate(rvData.getVoucherDate());
        notifyBean.setVoucherType(rvData.getVoucherType());
        return notifyBean;
    }

    private ReceiveOrderSearchBean buildReceiveOrderSearchBean(Date startDate, Date endDate) {
        ReceiveOrderSearchBean searchBean = new ReceiveOrderSearchBean();
        searchBean.setAddTimeBegin(startDate);
        searchBean.setAddTimeEnd(endDate);
        searchBean.setPayChannel(ReceiveOrderPayChannel.POS_MACHINE.value());
        searchBean.setStatus(ReceiveOrderStatus.UNCONFIRMED.value());
        searchBean.setBusinessType(BusinessType.ADVERTISEMENT.value());
        return searchBean;
    }

    private List<ReceiveVoucherData> buildReceiveVoucherData(ReceiveCalResultData rcData) {
        ReceiveBankData receiveBankData = receiveBankService.loadReceiveBankByBankId(rcData.getBankId());
        if (rcData.getBusinessType() == BusinessType.ADVERTISEMENT.value()) {
            return buildReceiveVoucherDataForAD(rcData, receiveBankData);

        }
        return Collections.emptyList();
    }

    private List<ReceiveVoucherData> buildReceiveVoucherDataForAD(ReceiveCalResultData rcData, ReceiveBankData receiveBankData) {
        List<ReceiveVoucherData> rvDataList = Lists.newLinkedList();

        ReceiveVoucherData rvDataPRC = new ReceiveVoucherData();
        rvDataPRC.setAddLoginId(-1);
        rvDataPRC.setAddTime(new Date());
        rvDataPRC.setAmount(rcData.getTotalAmount());
        rvDataPRC.setBankId(rcData.getBankId());
        rvDataPRC.setCityId(0);
        if (receiveBankData != null) {
            rvDataPRC.setCompanyId(receiveBankData.getCompanyId());
        }
        rvDataPRC.setCustomerId(rcData.getCustomerId());
        rvDataPRC.setShopId(rcData.getShopId());
        rvDataPRC.setUpdateLoginId(-1);
        rvDataPRC.setUpdateTime(new Date());
        rvDataPRC.setVoucherDate(rcData.getVoucherDate());
        rvDataPRC.setVoucherType(AccountingVoucherType.AD_O_NONE_CONTRACT_RECEIVE_PRC.value());
        rvDataList.add(rvDataPRC);

        ReceiveVoucherData rvDataUS = new ReceiveVoucherData();
        rvDataUS.setAddLoginId(-1);
        rvDataUS.setAddTime(new Date());
        rvDataUS.setAmount(rcData.getTotalAmount());
        rvDataUS.setBankId(rcData.getBankId());
        rvDataUS.setCityId(0);
        if (receiveBankData != null) {
            rvDataUS.setCompanyId(receiveBankData.getCompanyId());
        }
        rvDataUS.setCustomerId(rcData.getCustomerId());
        rvDataUS.setShopId(rcData.getShopId());
        rvDataUS.setUpdateLoginId(-1);
        rvDataUS.setUpdateTime(new Date());
        rvDataUS.setVoucherDate(rcData.getVoucherDate());
        rvDataUS.setVoucherType(AccountingVoucherType.AD_O_NONE_CONTRACT_RECEIVE_US.value());
        rvDataList.add(rvDataUS);

        return rvDataList;
    }

    public void setReceiveOrderService(ReceiveOrderService receiveOrderService) {
        this.receiveOrderService = receiveOrderService;
    }

    public void setReceiveVoucherDao(ReceiveVoucherDao receiveVoucherDao) {
        this.receiveVoucherDao = receiveVoucherDao;
    }

    public void setReceivePayVoucherNotify(ReceivePayVoucherNotify receivePayVoucherNotify) {
        this.receivePayVoucherNotify = receivePayVoucherNotify;
    }

    public void setReceiveBankService(ReceiveBankService receiveBankService) {
        this.receiveBankService = receiveBankService;
    }
}
