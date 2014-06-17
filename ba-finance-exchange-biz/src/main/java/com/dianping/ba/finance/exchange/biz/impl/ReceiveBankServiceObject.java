package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.ReceiveBankService;
import com.dianping.ba.finance.exchange.api.datas.ReceiveBankData;
import com.dianping.ba.finance.exchange.biz.dao.ReceiveBankDao;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by noahshen on 14-6-17.
 */
public class ReceiveBankServiceObject implements ReceiveBankService {

    private ReceiveBankDao receiveBankDao;

    private boolean cache;

    private List<ReceiveBankData> receiveBankDataCache;

    @Log(severity = 1, logBefore = true, logAfter = true)
    @ReturnDefault
    @Override
    public List<ReceiveBankData> findAllReceiveBank() {
        if (cache) {
            if (receiveBankDataCache != null) {
                return Lists.newArrayList(receiveBankDataCache);
            }
            receiveBankDataCache = receiveBankDao.findAllReceiveBank();
            return Lists.newArrayList(receiveBankDataCache);
        }
        return receiveBankDao.findAllReceiveBank();
    }

    @Override
    public void clearCache() {
        receiveBankDataCache = null;
    }


    public void setReceiveBankDao(ReceiveBankDao receiveBankDao) {
        this.receiveBankDao = receiveBankDao;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }
}
