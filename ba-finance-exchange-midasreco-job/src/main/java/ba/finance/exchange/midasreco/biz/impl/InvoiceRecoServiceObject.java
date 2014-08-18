package ba.finance.exchange.midasreco.biz.impl;

import ba.finance.exchange.midasreco.api.InvoiceRecoService;
import ba.finance.exchange.midasreco.api.datas.InvoiceRecoData;
import ba.finance.exchange.midasreco.biz.dao.InvoiceRecoDao;

import java.util.List;

/**
 * Created by will on 14-8-18.
 */
public class InvoiceRecoServiceObject implements InvoiceRecoService {

    private InvoiceRecoDao invoiceRecoDao;

    @Override
    public void insertInvoiceRecoDatas(List<InvoiceRecoData> invoiceRecoDatas) {
        invoiceRecoDao.insertInvoiceRecoDatas(invoiceRecoDatas);
    }

    public void setInvoiceRecoDao(InvoiceRecoDao invoiceRecoDao) {
        this.invoiceRecoDao = invoiceRecoDao;
    }
}
