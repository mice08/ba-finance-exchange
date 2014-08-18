package ba.finance.exchange.midasreco.api;

import ba.finance.exchange.midasreco.api.datas.InvoiceRecoData;
import ba.finance.exchange.midasreco.biz.dao.InvoiceRecoDao;

import java.util.List;

/**
 * Created by will on 14-8-18.
 */
public interface InvoiceRecoService {

    void insertInvoiceRecoDatas(List<InvoiceRecoData> invoiceRecoDatas);
}
