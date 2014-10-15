package com.dianping.ba.finance.exchange.api;


import java.util.Date;

/**
 *  处理收款凭证的Service类
 */
public interface ReceiveVoucherService {

    /**
     * 生成未确认的、无合同收款
     * @param date
     */
    void generateUnconfirmedReceiveVoucher(Date date);
}
