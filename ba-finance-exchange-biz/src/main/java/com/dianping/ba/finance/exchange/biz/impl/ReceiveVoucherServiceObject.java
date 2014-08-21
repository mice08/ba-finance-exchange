package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.ReceiveOrderService;
import com.dianping.ba.finance.exchange.api.ReceiveVoucherService;
import com.dianping.ba.finance.exchange.api.datas.ReceiveCalResultData;
import com.dianping.ba.finance.exchange.api.datas.ReceiveVoucherData;
import com.dianping.ba.finance.exchange.biz.dao.ReceiveVoucherDao;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;
import com.dianping.finance.common.util.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by noahshen on 14-8-21.
 */
public class ReceiveVoucherServiceObject implements ReceiveVoucherService {

    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.biz.monitor.ReceiveVoucherServiceObject");

    private ReceiveOrderService receiveOrderService;

    private ReceiveVoucherDao receiveVoucherDao;

    @Log(severity = 1, logBefore = true, logAfter = true)
    @ReturnDefault
    @Override
    public void generateUnconfirmedReceiveVoucher(Date date) {
        try {
            Date today = DateUtils.addDate(date, 1);
            Date endDate = DateUtils.removeTime(today);
            Date startDate = DateUtils.removeTime(date);
//            List<ReceiveCalResultData> receiveCalResultDataList = receiveOrderService.findCalculatedReceiveResult(ReceiveOrderStatus.UNCONFIRMED, startDate, endDate);
//
//            for (ReceiveCalResultData rcData : receiveCalResultDataList) {
//                ReceiveVoucherData rvData = buildReceiveVoucherData(rcData);
//                receiveVoucherDao.insertReceiveVoucherData(rvData);
//            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private ReceiveVoucherData buildReceiveVoucherData(ReceiveCalResultData rcData) {
        ReceiveVoucherData rvData = new ReceiveVoucherData();
        rvData.setAddLoginId(-1);
        rvData.setAddTime(new Date());
        rvData.setAmount(rcData.getTotalAmount());
        rvData.setBankId(rcData.getBankId());
        rvData.setCityId(0);
        rvData.setCompanyId(0);
        rvData.setCustomerId(rcData.getCustomerId());
        rvData.setShopId(rcData.getShopId());
        rvData.setUpdateLoginId(-1);
        rvData.setUpdateTime(new Date());
        rvData.setVoucherDate(rcData.getVoucherDate());
        rvData.setVoucherType(13);
        return rvData;
    }
}
