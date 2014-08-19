package com.dianping.ba.finance.exchange.midasreco.api;

import com.dianping.ba.finance.exchange.midasreco.api.datas.InvoiceRecoData;

import java.util.List;

/**
 * Created by will on 14-8-18.
 */
public interface InvoiceRecoService {

    void insertInvoiceRecoDatas(List<InvoiceRecoData> invoiceRecoDatas);
}
