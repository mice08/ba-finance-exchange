package com.dianping.ba.finance.exchange.api;

import com.dianping.ba.finance.exchange.api.dtos.AccountEntryDTO;

/**
 * Created by will on 15-3-6.
 */
public interface AccountService {

    boolean updateAccount(AccountEntryDTO accountEntryDTO);
}
