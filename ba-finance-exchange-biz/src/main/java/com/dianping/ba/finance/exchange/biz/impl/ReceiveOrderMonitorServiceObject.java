package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.ReceiveOrderMonitorService;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderRecoData;
import com.dianping.ba.finance.exchange.api.dtos.ReceiveOrderMonitorDTO;
import com.dianping.ba.finance.exchange.biz.dao.ReceiveOrderDao;
import com.dianping.ba.finance.exchange.biz.dao.ReceiveOrderRecoDao;
import org.apache.commons.collections.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by adam.huang on 2014/8/18.
 */
public class ReceiveOrderMonitorServiceObject implements ReceiveOrderMonitorService {

    private final static String RO_PREFIX = "midas_ro_";
    private ReceiveOrderDao receiveOrderDao;
    private ReceiveOrderRecoDao receiveOrderRecoDao;

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

    @Override
    public void insertReceiveOrderRecoDatas(Date startTime, Date endTime) {
        List<ReceiveOrderMonitorDTO> receiveOrderMonitorDTOList = this.findReceiveOrderMonitorDataByTime(startTime, endTime);
        if(CollectionUtils.isEmpty(receiveOrderMonitorDTOList)){
            return;
        }
        receiveOrderRecoDao.insertReceiveOrderRecoDatas(buildReceiveOrderRecoDatas(receiveOrderMonitorDTOList));
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

    private List<ReceiveOrderRecoData> buildReceiveOrderRecoDatas(List<ReceiveOrderMonitorDTO> receiveOrderMonitorDTOs){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("yyyyMMdd");
        String batchId = RO_PREFIX + simpleDateFormat.format(cal.getTime());

        List<ReceiveOrderRecoData> receiveOrderRecoDataList = new ArrayList<ReceiveOrderRecoData>();
        for(ReceiveOrderMonitorDTO receiveOrderMonitorDTO : receiveOrderMonitorDTOs) {
            ReceiveOrderRecoData receiveOrderRecoData = buildReceiveOrderRecoData(receiveOrderMonitorDTO,batchId);
            receiveOrderRecoDataList.add(receiveOrderRecoData);
        }
        return receiveOrderRecoDataList;
    }

    private ReceiveOrderRecoData buildReceiveOrderRecoData(ReceiveOrderMonitorDTO receiveOrderMonitorDTO, String batchId){
        ReceiveOrderRecoData receiveOrderRecoData = new ReceiveOrderRecoData();
        receiveOrderRecoData.setId(receiveOrderMonitorDTO.getRoId());
        receiveOrderRecoData.setCustomerId(receiveOrderMonitorDTO.getCustomerId());
        receiveOrderRecoData.setShopId(receiveOrderMonitorDTO.getShopId());
        receiveOrderRecoData.setType(receiveOrderMonitorDTO.getBusinessType());
        receiveOrderRecoData.setReceiveAmount(receiveOrderMonitorDTO.getReceiveAmount());
        receiveOrderRecoData.setReceiveTime(receiveOrderMonitorDTO.getReceiveTime());
        receiveOrderRecoData.setPayChannel(receiveOrderMonitorDTO.getPayChannel());
        receiveOrderRecoData.setReceiveType(receiveOrderMonitorDTO.getReceiveType());
        receiveOrderRecoData.setBizContent(receiveOrderMonitorDTO.getBizContent());
        receiveOrderRecoData.setTradeNo(receiveOrderMonitorDTO.getTradeNo());
        receiveOrderRecoData.setBankID(receiveOrderMonitorDTO.getBankID());
        receiveOrderRecoData.setAddTime(receiveOrderMonitorDTO.getAddTime());
        receiveOrderRecoData.setUpdateTime(receiveOrderMonitorDTO.getUpdateTime());
        receiveOrderRecoData.setBatchId(batchId);
        return receiveOrderRecoData;
    }

    public void setReceiveOrderDao(ReceiveOrderDao receiveOrderDao) {
        this.receiveOrderDao = receiveOrderDao;
    }

    public void setReceiveOrderRecoDao(ReceiveOrderRecoDao receiveOrderRecoDao) {
        this.receiveOrderRecoDao = receiveOrderRecoDao;
    }
}
