package com.dianping.ba.finance.exchange.midasreco.biz.impl;

import com.dianping.ba.finance.exchange.midasreco.api.InvoiceRecoService;
import com.dianping.ba.finance.exchange.midasreco.api.MidasRecoService;
import com.dianping.ba.finance.exchange.midasreco.api.ReceiveOrderRecoService;
import com.dianping.ba.finance.exchange.midasreco.api.datas.InvoiceRecoData;
import com.dianping.ba.finance.exchange.midasreco.api.datas.ReceiveOrderRecoData;
import com.dianping.ba.finance.exchange.api.ReceiveOrderMonitorService;
import com.dianping.ba.finance.exchange.api.dtos.ReceiveOrderMonitorDTO;
import com.dianping.ba.finance.settle.api.InvoiceMonitorService;
import com.dianping.ba.finance.settle.api.dtos.InvoiceMonitorDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by will on 14-8-18.
 */
public class MidasRecoServiceObject implements MidasRecoService {

    private final static String INVOICE_PREFIX = "midas_invoice_";
    private final static String RO_PREFIX = "midas_ro_";
    private ReceiveOrderMonitorService receiveOrderMonitorService;
    private InvoiceMonitorService invoiceMonitorService;
    private InvoiceRecoService invoiceRecoService;
    private ReceiveOrderRecoService receiveOrderRecoService;

    @Override
    public boolean saveReconciliationData() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date endTime = cal.getTime();
        cal.add(Calendar.DATE, -1);
        Date startTime = cal.getTime();

        List<ReceiveOrderMonitorDTO> receiveOrderMonitorDTOs = receiveOrderMonitorService.findReceiveOrderMonitorDataByTime(startTime, endTime);
        List<InvoiceMonitorDTO> invoiceMonitorDTOs = invoiceMonitorService.findInvoiceMonitorDataByTime(startTime, endTime);

        receiveOrderRecoService.insertReceiveOrderRecoDatas(buildReceiveOrderRecoDatas(receiveOrderMonitorDTOs));
        invoiceRecoService.insertInvoiceRecoDatas(buildInvoiceRecoDatas(invoiceMonitorDTOs));
        return false;
    }

    private List<InvoiceRecoData> buildInvoiceRecoDatas(List<InvoiceMonitorDTO> invoiceMonitorDTOs){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("yyyyMMdd");
        String batchId = INVOICE_PREFIX + simpleDateFormat.format(cal.getTime());

        List<InvoiceRecoData> invoiceRecoDataList = new ArrayList<InvoiceRecoData>();
        for(InvoiceMonitorDTO invoiceMonitorDTO : invoiceMonitorDTOs){
            InvoiceRecoData invoiceRecoData = buildInvoiceRecoData(invoiceMonitorDTO,batchId);
            invoiceRecoDataList.add(invoiceRecoData);
        }
        return invoiceRecoDataList;
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

    private InvoiceRecoData buildInvoiceRecoData(InvoiceMonitorDTO invoiceMonitorDTO, String batchId){
        InvoiceRecoData invoiceRecoData = new InvoiceRecoData();
        invoiceRecoData.setId(invoiceMonitorDTO.getInvoiceId());
        invoiceRecoData.setInvoiceAmount(invoiceMonitorDTO.getInvoiceAmount());
        invoiceRecoData.setBizId(invoiceMonitorDTO.getBizId());
        invoiceRecoData.setCustomerId(invoiceMonitorDTO.getCustomerId());
        invoiceRecoData.setShopId(invoiceMonitorDTO.getShopId());
        invoiceRecoData.setBizContent(invoiceMonitorDTO.getBizContent());
        invoiceRecoData.setInvoiceTitle(invoiceMonitorDTO.getInvoiceTitle());
        invoiceRecoData.setInvoiceContent(invoiceMonitorDTO.getInvoiceContent());
        invoiceRecoData.setCompanyId(invoiceMonitorDTO.getCompanyId());
        invoiceRecoData.setTaxNumber(invoiceMonitorDTO.getTaxNumber());
        invoiceRecoData.setBankName(invoiceMonitorDTO.getBankName());
        invoiceRecoData.setBankAccountNo(invoiceMonitorDTO.getBankAccountNo());
        invoiceRecoData.setAddress(invoiceMonitorDTO.getAddress());
        invoiceRecoData.setPhone(invoiceMonitorDTO.getPhone());
        invoiceRecoData.setInvoiceTaxNo(invoiceMonitorDTO.getInvoiceTaxNo());
        invoiceRecoData.setReleaseDate(invoiceMonitorDTO.getReleaseDate());
        invoiceRecoData.setInvoiceType(invoiceMonitorDTO.getInvoiceType());
        invoiceRecoData.setAddTime(invoiceMonitorDTO.getAddTime());
        invoiceRecoData.setUpdateTime(invoiceMonitorDTO.getUpdateTime());
        invoiceRecoData.setBatchId(batchId);
        return invoiceRecoData;
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

    public void setReceiveOrderMonitorService(ReceiveOrderMonitorService receiveOrderMonitorService) {
        this.receiveOrderMonitorService = receiveOrderMonitorService;
    }

    public void setInvoiceMonitorService(InvoiceMonitorService invoiceMonitorService) {
        this.invoiceMonitorService = invoiceMonitorService;
    }

    public void setInvoiceRecoService(InvoiceRecoService invoiceRecoService) {
        this.invoiceRecoService = invoiceRecoService;
    }

    public void setReceiveOrderRecoService(ReceiveOrderRecoService receiveOrderRecoService) {
        this.receiveOrderRecoService = receiveOrderRecoService;
    }
}
