package com.dianping.ba.finance.exchange.api;

import com.dianping.ba.finance.exchange.api.dtos.AccountEntryDTO;
import com.dianping.ba.finance.exchange.api.dtos.AccountEntryRequestDTO;
import com.dianping.ba.finance.exchange.api.dtos.BankAccountDTO;

import java.util.Date;
import java.util.List;

/**
 * Created by will on 15-3-6.
 */
public interface AccountService {

    boolean updateAccount(AccountEntryRequestDTO accountEntryRequestDTO);

    List<AccountEntryDTO> findAccountEntryByTime(int start, int size, Date startTime, Date endTime);

    BankAccountDTO loadBankAccount(int id);
}
