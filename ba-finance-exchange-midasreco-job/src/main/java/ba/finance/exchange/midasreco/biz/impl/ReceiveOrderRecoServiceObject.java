package ba.finance.exchange.midasreco.biz.impl;

import ba.finance.exchange.midasreco.api.ReceiveOrderRecoService;
import ba.finance.exchange.midasreco.api.datas.ReceiveOrderRecoData;
import ba.finance.exchange.midasreco.biz.dao.ReceiveOrderRecoDao;

import java.util.List;

/**
 * Created by will on 14-8-18.
 */
public class ReceiveOrderRecoServiceObject implements ReceiveOrderRecoService {

    private ReceiveOrderRecoDao receiveOrderRecoDao;

    @Override
    public void insertReceiveOrderRecoDatas(List<ReceiveOrderRecoData> list) {
        receiveOrderRecoDao.insertReceiveOrderRecoDatas(list);
    }
}
