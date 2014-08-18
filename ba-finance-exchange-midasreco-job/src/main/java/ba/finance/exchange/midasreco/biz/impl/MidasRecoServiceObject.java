package ba.finance.exchange.midasreco.biz.impl;

import ba.finance.exchange.midasreco.api.InvoiceRecoService;
import ba.finance.exchange.midasreco.api.MidasRecoService;
import ba.finance.exchange.midasreco.api.ReceiveOrderRecoService;
import ba.finance.exchange.midasreco.api.datas.InvoiceRecoData;
import ba.finance.exchange.midasreco.api.datas.ReceiveOrderRecoData;
import com.dianping.ba.finance.exchange.api.ReceiveOrderMonitorService;
import com.dianping.ba.finance.exchange.api.dtos.ReceiveOrderMonitorDTO;
import com.dianping.ba.finance.settle.api.InvoiceMonitorService;
import com.dianping.ba.finance.settle.api.dtos.InvoiceMonitorDTO;

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
        return null;
    }

    private List<ReceiveOrderRecoData> buildReceiveOrderRecoDatas(List<ReceiveOrderMonitorDTO> receiveOrderMonitorDTOs){
        return null;
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
