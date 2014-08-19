package com.dianping.ba.finance.exchange.midasreco.biz.impl;

import com.dianping.ba.finance.exchange.midasreco.api.InvoiceRecoService;
import com.dianping.ba.finance.exchange.midasreco.api.datas.InvoiceRecoData;
import com.dianping.ba.finance.exchange.midasreco.biz.dao.InvoiceRecoDao;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Created by will on 14-8-18.
 */
public class InvoiceRecoServiceObject implements InvoiceRecoService {

    private InvoiceRecoDao invoiceRecoDao;

    @Override
    public void insertInvoiceRecoDatas(List<InvoiceRecoData> invoiceRecoDatas) {
        if(CollectionUtils.isEmpty(invoiceRecoDatas)){
            return;
        }
        invoiceRecoDao.insertInvoiceRecoDatas(invoiceRecoDatas);
    }

    public void setInvoiceRecoDao(InvoiceRecoDao invoiceRecoDao) {
        this.invoiceRecoDao = invoiceRecoDao;
    }
}
