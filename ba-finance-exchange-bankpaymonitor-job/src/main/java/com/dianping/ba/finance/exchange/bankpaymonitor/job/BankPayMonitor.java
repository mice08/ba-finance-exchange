package com.dianping.ba.finance.exchange.bankpaymonitor.job;

import com.dianping.ba.finance.exchange.api.enums.PayOrderStatus;
import com.dianping.ba.finance.exchange.bankpaymonitor.api.MonitorService;
import com.dianping.ba.finance.exchange.bankpaymonitor.api.PayOrderService;
import com.dianping.ba.finance.exchange.bankpaymonitor.api.datas.PayOrderMonitorData;
import com.dianping.ba.finance.exchange.bankpaymonitor.api.enums.BankPayException;
import com.dianping.ba.finance.paymentplatform.api.PaymentQueryService;
import com.dianping.ba.finance.paymentplatform.api.dtos.PaymentRecordDTO;
import com.dianping.ba.finance.paymentplatform.api.enums.PaymentRecordStatus;
import com.dianping.finance.common.util.LionConfigUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.ExecutorService;

/**
 * Created by will on 15-3-13.
 */
public class BankPayMonitor {

    private static final int SIZE = Integer.parseInt(LionConfigUtils.getProperty("ba-finance-expense-bankpaymonitor-job.monitor.per.size", "1000"));
    @Autowired
    private MonitorService monitorService;
    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private PaymentQueryService paymentQueryService;
    @Autowired
    private ExecutorService executorService;


    public void check() {
        boolean finish = false;
        int start = 0;
        while (!finish) {
            final List<PayOrderMonitorData> payOrders = payOrderService.findPayOrders(start, SIZE, monitorService.loadLastMonitorTime(), new Date());
            List<String> insIds = fetchInsIds(payOrders);
            final List<PaymentRecordDTO> paymentRecords = paymentQueryService.queryPaymentRecordByInsIds(insIds);
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    check(payOrders, paymentRecords);
                }
            });
            start += payOrders.size();
            finish = payOrders.size() != SIZE;
        }

    }

    private void check(List<PayOrderMonitorData> payOrders, List<PaymentRecordDTO> paymentRecords) {
        Map<String, ArrayList<PaymentRecordDTO>> paymentRecodeMap = generateRecordMap(paymentRecords);
        for (PayOrderMonitorData payOrder : payOrders) {
            checkRelation(payOrder, paymentRecodeMap.get(payOrder.getPayCode()));
        }
    }

    private Map<String, ArrayList<PaymentRecordDTO>> generateRecordMap(List<PaymentRecordDTO> paymentRecords) {
        Map<String, ArrayList<PaymentRecordDTO>> paymentRecodeMap = new HashMap<String, ArrayList<PaymentRecordDTO>>();
        for (PaymentRecordDTO dto : paymentRecords) {
            if (paymentRecodeMap.containsKey(dto.getInsId())) {
                paymentRecodeMap.get(dto.getInsId()).add(dto);
            } else {
                paymentRecodeMap.put(dto.getInsId(), new ArrayList<PaymentRecordDTO>(Arrays.asList(dto)));
            }
        }
        return paymentRecodeMap;
    }

    private void checkRelation(PayOrderMonitorData payOrder, List<PaymentRecordDTO> paymentRecords) {
        BankPayException bankPayException = null;
        if (CollectionUtils.isEmpty(paymentRecords)) {
            bankPayException = BankPayException.RECORD_NOT_FOUND;
        } else {
            int payRetryTimes = 0;
            boolean isMatch = false;
            for (PaymentRecordDTO record : paymentRecords) {
                if (!isMatch && matchStatus(payOrder, record)) {
                    isMatch = true;
                } else if (record.getStatus() == PaymentRecordStatus.PAY_FAILED.getCode()) {
                    payRetryTimes++;
                }
            }
            if (!isMatch) {
                bankPayException = BankPayException.DIFF_STATUS;
            } else if (payRetryTimes < paymentRecords.size() - 1) {
                bankPayException = BankPayException.MULTI_RECORDS;
            }
        }
        if (bankPayException != null) {
            saveException(payOrder.getPoId(), bankPayException);
        }
    }

    private void saveException(int poId, BankPayException bankPayException) {
        monitorService.insertMonitorResult(poId, bankPayException);
    }

    private boolean matchStatus(PayOrderMonitorData payOrder, PaymentRecordDTO record) {
        if (payOrder.getStatus() == PayOrderStatus.BANK_PAYING.value()) {
            return record.getStatus() == PaymentRecordStatus.INIT.getCode() || record.getStatus() == PaymentRecordStatus.PAYING.getCode();
        } else if (payOrder.getStatus() == PayOrderStatus.PAY_SUCCESS.value()) {
            return record.getStatus() == PaymentRecordStatus.PAY_SUCCESS.getCode();
        } else if (payOrder.getStatus() == PayOrderStatus.PAY_FAILED.value()) {
            return record.getStatus() == PaymentRecordStatus.PAY_FAILED.getCode();
        }
        return false;
    }

    private List<String> fetchInsIds(List<PayOrderMonitorData> payOrders) {
        List<String> insIds = new ArrayList<String>();
        if (CollectionUtils.isEmpty(payOrders)) {
            return insIds;
        }
        for (PayOrderMonitorData data : payOrders) {
            insIds.add(data.getPayCode());
        }
        return insIds;
    }

    public void setMonitorService(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    public void setPayOrderService(PayOrderService payOrderService) {
        this.payOrderService = payOrderService;
    }

    public void setPaymentQueryService(PaymentQueryService paymentQueryService) {
        this.paymentQueryService = paymentQueryService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }
}
