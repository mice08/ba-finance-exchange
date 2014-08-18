package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.ReceiveOrderMonitorService;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
import com.dianping.ba.finance.exchange.api.dtos.ReceiveOrderMonitorDTO;
import com.dianping.ba.finance.exchange.biz.dao.ReceiveOrderDao;

import java.util.Date;
import java.util.List;

/**
 * Created by adam.huang on 2014/8/18.
 */
public class ReceiveOrderMonitorServiceObject implements ReceiveOrderMonitorService {

    private ReceiveOrderDao receiveOrderDao;

    @Override
    public ReceiveOrderMonitorDTO loadReceiveOrderMonitorDTOById(int roId) {
        ReceiveOrderData receiveOrderData = receiveOrderDao.loadReceiveOrderDataByRoId(roId);
        ReceiveOrderMonitorDTO receiveOrderDTO = buildReceiveOrderDTO(receiveOrderData);
        return receiveOrderDTO;
    }

    @Override
    public List<ReceiveOrderMonitorDTO> findReceiveOrderMonitorDataByTime(Date startTime, Date endTime) {
        return null;
    }

    private ReceiveOrderMonitorDTO buildReceiveOrderDTO(ReceiveOrderData receiveOrderData) {
        ReceiveOrderMonitorDTO receiveOrderMonitorDTO = new ReceiveOrderMonitorDTO();
        //TODO
        receiveOrderMonitorDTO.setRoId(receiveOrderData.getRoId());
        return receiveOrderMonitorDTO;
    }

    public void setReceiveOrderDao(ReceiveOrderDao receiveOrderDao) {
        this.receiveOrderDao = receiveOrderDao;
    }
}
