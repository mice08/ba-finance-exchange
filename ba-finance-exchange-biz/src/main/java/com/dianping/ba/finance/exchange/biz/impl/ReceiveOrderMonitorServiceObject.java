package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.ReceiveOrderMonitorService;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
import com.dianping.ba.finance.exchange.api.dtos.ReceiveOrderMonitorDTO;
import com.dianping.ba.finance.exchange.biz.dao.ReceiveOrderDao;

import java.util.ArrayList;
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
        ReceiveOrderMonitorDTO receiveOrderDTO = buildReceiveOrderMonitorDTO(receiveOrderData);
        return receiveOrderDTO;
    }

    @Override
    public List<ReceiveOrderMonitorDTO> findReceiveOrderMonitorDataByTime(Date startTime, Date endTime) {
        List<ReceiveOrderData> receiveOrderDataList = receiveOrderDao.findReceiveOrderDataByTime(startTime, endTime);
        List<ReceiveOrderMonitorDTO> receiveOrderMonitorDTOList = new ArrayList<ReceiveOrderMonitorDTO>();
        for(ReceiveOrderData receiveOrderData : receiveOrderDataList){
            ReceiveOrderMonitorDTO receiveOrderMonitorDTO = buildReceiveOrderMonitorDTO(receiveOrderData);
            receiveOrderMonitorDTOList.add(receiveOrderMonitorDTO);
        }
        return receiveOrderMonitorDTOList;
    }

    private ReceiveOrderMonitorDTO buildReceiveOrderMonitorDTO(ReceiveOrderData receiveOrderData) {
        ReceiveOrderMonitorDTO receiveOrderMonitorDTO = new ReceiveOrderMonitorDTO();
        receiveOrderMonitorDTO.setRoId(receiveOrderData.getRoId());
        receiveOrderMonitorDTO.setCustomerId(receiveOrderData.getCustomerId());
        receiveOrderMonitorDTO.setShopId(receiveOrderData.getShopId());
        receiveOrderMonitorDTO.setBusinessType(receiveOrderData.getBusinessType());
        receiveOrderMonitorDTO.setReceiveAmount(receiveOrderData.getReceiveAmount());
        receiveOrderMonitorDTO.setReceiveTime(receiveOrderData.getReceiveTime());
        receiveOrderMonitorDTO.setPayChannel(receiveOrderData.getPayChannel());
        receiveOrderMonitorDTO.setReceiveType(receiveOrderData.getReceiveType());
        receiveOrderMonitorDTO.setBizContent(receiveOrderData.getBizContent());
        receiveOrderMonitorDTO.setTradeNo(receiveOrderData.getTradeNo());
        receiveOrderMonitorDTO.setBankID(receiveOrderData.getBankID());
        receiveOrderMonitorDTO.setAddTime(receiveOrderData.getAddTime());
        receiveOrderMonitorDTO.setAddLoginId(receiveOrderData.getAddLoginId());
        receiveOrderMonitorDTO.setUpdateLoginId(receiveOrderData.getUpdateLoginId());
        receiveOrderMonitorDTO.setUpdateTime(receiveOrderData.getUpdateTime());
        receiveOrderMonitorDTO.setMemo(receiveOrderData.getMemo());
        return receiveOrderMonitorDTO;
    }

    public void setReceiveOrderDao(ReceiveOrderDao receiveOrderDao) {
        this.receiveOrderDao = receiveOrderDao;
    }
}
