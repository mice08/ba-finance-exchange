package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.ReceiveNotifyRecordService;
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyRecordData;
import com.dianping.ba.finance.exchange.biz.dao.ReceiveNotifyRecordDao;

/**
 * Created by Administrator on 2014/7/23.
 */
public class ReceiveNotifyRecordServiceObject implements ReceiveNotifyRecordService {
    private ReceiveNotifyRecordDao receiveNotifyRecordDao;

    @Override
    public int insertReceiveNotifyRecord(ReceiveNotifyRecordData receiveNotifyRecordData) {
        return receiveNotifyRecordDao.insertReceiveNotifyRecord(receiveNotifyRecordData);
    }

    @Override
    public void changeCustomer(int oldCustomerId, int newCustomerId) {
        receiveNotifyRecordDao.updateCustomerId(oldCustomerId, newCustomerId);
    }

    public void setReceiveNotifyRecordDao(ReceiveNotifyRecordDao receiveNotifyRecordDao) {
        this.receiveNotifyRecordDao = receiveNotifyRecordDao;
    }


}
