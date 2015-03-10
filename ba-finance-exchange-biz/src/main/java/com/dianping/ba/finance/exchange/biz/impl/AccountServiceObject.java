package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.AccountService;
import com.dianping.ba.finance.exchange.api.beans.AccountUpdateInfoBean;
import com.dianping.ba.finance.exchange.api.datas.AccountData;
import com.dianping.ba.finance.exchange.api.datas.AccountEntryData;
import com.dianping.ba.finance.exchange.api.datas.BankAccountData;
import com.dianping.ba.finance.exchange.api.dtos.AccountEntryDTO;
import com.dianping.ba.finance.exchange.api.dtos.AccountEntryRequestDTO;
import com.dianping.ba.finance.exchange.api.dtos.BankAccountDTO;
import com.dianping.ba.finance.exchange.api.enums.AccountEntryDirection;
import com.dianping.ba.finance.exchange.api.enums.AccountEntrySourceType;
import com.dianping.ba.finance.exchange.biz.dao.AccountDao;
import com.dianping.ba.finance.exchange.biz.dao.AccountEntryDao;
import com.dianping.ba.finance.exchange.biz.dao.BankAccountDao;
import com.dianping.ba.finance.exchange.biz.utils.SerialNoHelper;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.util.ConvertUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by will on 15-3-9.
 */
public class AccountServiceObject implements AccountService {

    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.biz.impl.monitor.AccountServiceObject");

    @Autowired
    private AccountEntryDao accountEntryDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private BankAccountDao bankAccountDao;

    @Override
    @Log(logBefore = true, logAfter = true)
    public boolean updateAccount(AccountEntryRequestDTO accountEntryRequestDTO) {
        try {
            AccountData account = accountDao.loadAccountByBankAccount(accountEntryRequestDTO.getBankAccountId());
            AccountUpdateInfoBean accountUpdateInfo = buildAccountUpdateInfo(account, accountEntryRequestDTO);
            accountDao.updateAccount(accountUpdateInfo);
            accountEntryDao.insertAccountEntry(buildAccountEntry(account, accountEntryRequestDTO, accountUpdateInfo));
        } catch (Exception e) {
            MONITOR_LOGGER.error("severity=[2] AccountServiceObject.updateAccount error", e);
            return false;
        }
        return true;
    }

    @Override
    @Log(logBefore = true, logAfter = true)
    public List<AccountEntryDTO> findAccountEntryByTime(int start, int size, Date startTime, Date endTime) {
        List<AccountEntryDTO> list = new ArrayList<AccountEntryDTO>();
        try {
            List<AccountEntryData> dataList = accountEntryDao.findAccountEntryByTime(start, size, startTime, endTime);
            if (CollectionUtils.isEmpty(dataList)) {
                return list;
            }
            for (AccountEntryData data : dataList) {
                list.add(ConvertUtils.copy(data, AccountEntryDTO.class));
            }
        } catch (Exception e) {
            MONITOR_LOGGER.error("severity=[2] AccountServiceObject.findAccountEntryByTime error", e);
            return new ArrayList<AccountEntryDTO>();
        }
        return list;
    }

    @Override
    public BankAccountDTO loadBankAccount(int id) {
        try {
            BankAccountData data = bankAccountDao.loadBankAccount(id);
            if (data == null) {
                return null;
            }
            return ConvertUtils.copy(data, BankAccountDTO.class);
        } catch (Exception e) {
            MONITOR_LOGGER.error("severity=[2] AccountServiceObject.loadBankAccount error", e);
        }
        return null;
    }

    private AccountEntryData buildAccountEntry(AccountData account, AccountEntryRequestDTO accountEntryRequestDTO, AccountUpdateInfoBean accountUpdateInfo) {
        AccountEntryData accountEntry = new AccountEntryData();
        accountEntry.setDirection(accountEntryRequestDTO.getSourceType() == AccountEntrySourceType.PAY_ORDER.getSourceType() ? AccountEntryDirection.POSITIVE.getDirection() : AccountEntryDirection.NEGATIVE.getDirection());
        accountEntry.setSourceType(accountEntryRequestDTO.getSourceType());
        accountEntry.setAccountBalance(accountUpdateInfo.getBalance());
        accountEntry.setAccountId(account.getId());
        accountEntry.setAmount(accountEntryRequestDTO.getAmount());
        accountEntry.setDescription(accountEntryRequestDTO.getDescription() == null ? "" : accountEntryRequestDTO.getDescription());
        accountEntry.setBankAccountId(accountEntryRequestDTO.getBankAccountId());
        accountEntry.setEntryNo(SerialNoHelper.generateSerialNo("AE", 15));
        accountEntry.setInstId(accountEntryRequestDTO.getInstId());
        return accountEntry;
    }

    private AccountUpdateInfoBean buildAccountUpdateInfo(AccountData account, AccountEntryRequestDTO accountEntryRequestDTO) {
        AccountUpdateInfoBean bean = new AccountUpdateInfoBean();
        if(isCredit(accountEntryRequestDTO.getSourceType(), account.getDirection())) {
            bean.setCredit(account.getCredit().add(accountEntryRequestDTO.getAmount()));
            bean.setDebit(account.getDebit());
            bean.setBalance(account.getBalance().add(accountEntryRequestDTO.getAmount()));
        } else {
            bean.setCredit(account.getCredit());
            bean.setDebit(account.getDebit().add(accountEntryRequestDTO.getAmount()));
            bean.setBalance(account.getBalance().subtract(accountEntryRequestDTO.getAmount()));
        }
        return bean;
    }

    private boolean isCredit(int sourceType, int direction) {
        return sourceType == AccountEntrySourceType.PAY_ORDER.getSourceType() ^ direction == AccountEntryDirection.POSITIVE.getDirection();
    }

    public void setAccountEntryDao(AccountEntryDao accountEntryDao) {
        this.accountEntryDao = accountEntryDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void setBankAccountDao(BankAccountDao bankAccountDao) {
        this.bankAccountDao = bankAccountDao;
    }
}
