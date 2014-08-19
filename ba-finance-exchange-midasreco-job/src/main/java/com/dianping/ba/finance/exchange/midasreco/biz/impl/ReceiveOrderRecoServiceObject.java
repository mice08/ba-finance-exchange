package com.dianping.ba.finance.exchange.midasreco.biz.impl;

import com.dianping.ba.finance.exchange.midasreco.api.ReceiveOrderRecoService;
import com.dianping.ba.finance.exchange.midasreco.api.datas.ReceiveOrderRecoData;
import com.dianping.ba.finance.exchange.midasreco.biz.dao.ReceiveOrderRecoDao;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Created by will on 14-8-18.
 */
public class ReceiveOrderRecoServiceObject implements ReceiveOrderRecoService {

    private ReceiveOrderRecoDao receiveOrderRecoDao;

    @Override
    public void insertReceiveOrderRecoDatas(List<ReceiveOrderRecoData> list) {
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        receiveOrderRecoDao.insertReceiveOrderRecoDatas(list);
    }

    public void setReceiveOrderRecoDao(ReceiveOrderRecoDao receiveOrderRecoDao) {
        this.receiveOrderRecoDao = receiveOrderRecoDao;
    }
}
