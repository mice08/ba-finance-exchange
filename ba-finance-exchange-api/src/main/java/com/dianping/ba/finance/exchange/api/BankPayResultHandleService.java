package com.dianping.ba.finance.exchange.api;

import com.dianping.ba.finance.exchange.api.dtos.BankPayResultDTO;

/**
 * Created by will on 15-3-9.
 */
public interface BankPayResultHandleService {

    boolean handleBankPayResult(BankPayResultDTO bankPayResultDTO);
}
