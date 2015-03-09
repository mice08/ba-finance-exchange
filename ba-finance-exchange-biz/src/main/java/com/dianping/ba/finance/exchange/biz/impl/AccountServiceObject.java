package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.AccountService;
import com.dianping.ba.finance.exchange.api.beans.AccountUpdateInfoBean;
import com.dianping.ba.finance.exchange.api.datas.AccountData;
import com.dianping.ba.finance.exchange.api.datas.AccountEntryData;
import com.dianping.ba.finance.exchange.api.dtos.AccountEntryDTO;
import com.dianping.ba.finance.exchange.api.enums.AccountEntryDirection;
import com.dianping.ba.finance.exchange.api.enums.AccountEntrySourceType;
import com.dianping.ba.finance.exchange.biz.dao.AccountDao;
import com.dianping.ba.finance.exchange.biz.dao.AccountEntryDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by will on 15-3-9.
 */
public class AccountServiceObject implements AccountService {

    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.biz.impl.monitor.AccountServiceObject");

    @Autowired
    private AccountEntryDao accountEntryDao;
    @Autowired
    private AccountDao accountDao;

    @Override
    public boolean updateAccount(AccountEntryDTO accountEntryDTO) {
        try {
            AccountData account = accountDao.loadAccountByBankAccount(accountEntryDTO.getBankAccountId());
            AccountUpdateInfoBean accountUpdateInfo = buildAccountUpdateInfo(account, accountEntryDTO);
            accountDao.updateAccount(accountUpdateInfo);
            accountEntryDao.insertAccountEntry(buildAccountEntry(account, accountEntryDTO, accountUpdateInfo));
        } catch (Exception e) {
            MONITOR_LOGGER.error("severity=[2] AccountServiceObject.updateAccount error", e);
            return false;
        }
        return true;
    }

    private AccountEntryData buildAccountEntry(AccountData account, AccountEntryDTO accountEntryDTO, AccountUpdateInfoBean accountUpdateInfo) {
        AccountEntryData accountEntry = new AccountEntryData();
        accountEntry.setDirection(accountEntryDTO.getSourceType() == AccountEntrySourceType.PAY_ORDER.getSourceType() ? AccountEntryDirection.POSITIVE.getDirection() : AccountEntryDirection.NEGATIVE.getDirection());
        accountEntry.setSourceType(accountEntryDTO.getSourceType());
        accountEntry.setAccountBalance(accountUpdateInfo.getBalance());
        accountEntry.setAccountId(account.getId());
        accountEntry.setAmount(accountEntryDTO.getAmount());
        accountEntry.setBankAccountId(accountEntryDTO.getBankAccountId());
        accountEntry.setEntryNo(""); //todo: entry no generation
        accountEntry.setInstId(accountEntryDTO.getInstId());
        return accountEntry;
    }

    private AccountUpdateInfoBean buildAccountUpdateInfo(AccountData account, AccountEntryDTO accountEntryDTO) {
        AccountUpdateInfoBean bean = new AccountUpdateInfoBean();
        if(isCredit(accountEntryDTO.getSourceType(), account.getDirection())) {
            bean.setCredit(account.getCredit().add(accountEntryDTO.getAmount()));
            bean.setDebit(account.getDebit());
            bean.setBalance(account.getBalance().add(accountEntryDTO.getAmount()));
        } else {
            bean.setCredit(account.getCredit());
            bean.setDebit(account.getDebit().add(accountEntryDTO.getAmount()));
            bean.setBalance(account.getBalance().subtract(accountEntryDTO.getAmount()));
        }
        return bean;
    }

    private boolean isCredit(int sourceType, int direction) {
        return sourceType == AccountEntrySourceType.PAY_ORDER.getSourceType() ^ direction == AccountEntryDirection.POSITIVE.getDirection();
    }
}
