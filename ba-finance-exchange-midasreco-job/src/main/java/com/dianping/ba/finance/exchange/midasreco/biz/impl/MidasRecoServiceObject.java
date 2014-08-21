package com.dianping.ba.finance.exchange.midasreco.biz.impl;

import com.dianping.ba.finance.exchange.api.ReceiveOrderMonitorService;
import com.dianping.ba.finance.exchange.api.dtos.ReceiveOrderMonitorSearchDTO;
import com.dianping.ba.finance.exchange.midasreco.api.MidasRecoService;
import com.dianping.ba.finance.settle.api.InvoiceMonitorService;
import com.dianping.ba.finance.settle.api.dtos.InvoiceMonitorSearchDTO;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by will on 14-8-18.
 */
public class MidasRecoServiceObject implements MidasRecoService {


    private ReceiveOrderMonitorService receiveOrderMonitorService;
    private InvoiceMonitorService invoiceMonitorService;

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

        ReceiveOrderMonitorSearchDTO receiveOrderMonitorSearchDTO = new ReceiveOrderMonitorSearchDTO();
        receiveOrderMonitorSearchDTO.setStartTime(startTime);
        receiveOrderMonitorSearchDTO.setEndTime(endTime);
        receiveOrderMonitorSearchDTO.setBusinessType(5);
        receiveOrderMonitorService.insertReceiveOrderRecoDatas(receiveOrderMonitorSearchDTO);

        InvoiceMonitorSearchDTO invoiceMonitorSearchDTO = new InvoiceMonitorSearchDTO();
        invoiceMonitorSearchDTO.setStartTime(startTime);
        invoiceMonitorSearchDTO.setEndTime(endTime);
        invoiceMonitorSearchDTO.setBusinessType(5);
        invoiceMonitorService.insertInvoiceRecoDatas(invoiceMonitorSearchDTO);
        return false;
    }

    public void setReceiveOrderMonitorService(ReceiveOrderMonitorService receiveOrderMonitorService) {
        this.receiveOrderMonitorService = receiveOrderMonitorService;
    }

    public void setInvoiceMonitorService(InvoiceMonitorService invoiceMonitorService) {
        this.invoiceMonitorService = invoiceMonitorService;
    }

}
