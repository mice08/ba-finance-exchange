package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.ReceiveNotifyService;
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData;
import com.dianping.ba.finance.exchange.biz.dao.ReceiveNotifyDao;

/**
 * Created by Administrator on 2014/7/24.
 */
public class ReceiveNotifyServiceObject implements ReceiveNotifyService {
    private ReceiveNotifyDao receiveNotifyDao;

    @Override
    public int insertReceiveNotify(ReceiveNotifyData receiveNotifyData) {
        return receiveNotifyDao.insertReceiveNotify(receiveNotifyData);
    }

    public void setReceiveNotifyDao(ReceiveNotifyDao receiveNotifyDao) {
        this.receiveNotifyDao = receiveNotifyDao;
    }
}
