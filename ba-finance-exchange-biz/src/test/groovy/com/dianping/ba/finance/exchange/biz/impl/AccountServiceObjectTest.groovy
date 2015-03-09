package com.dianping.ba.finance.exchange.biz.impl

import com.dianping.ba.finance.exchange.biz.dao.AccountDao
import com.dianping.ba.finance.exchange.biz.dao.AccountEntryDao
import com.dianping.ba.finance.exchange.biz.dao.BankAccountDao
import spock.lang.Specification

/**
 * Created by will on 15-3-9.
 */
class AccountServiceObjectTest extends Specification {

    AccountServiceObject accountServiceObjectStub;
    AccountEntryDao accountEntryDaoMock;
    AccountDao accountDaoMock;
    BankAccountDao bankAccountDaoMock;

    void setup() {
        accountServiceObjectStub = [];
        accountEntryDaoMock = Mock();
        accountDaoMock = Mock();
        bankAccountDaoMock = Mock();
        accountServiceObjectStub.accountDao = accountDaoMock;
        accountServiceObjectStub.accountEntryDao = accountEntryDaoMock;
        accountServiceObjectStub.bankAccountDao = bankAccountDaoMock;
    }

    def "UpdateAccount"() {

    }

    def "FindAccountEntryByTime"() {

    }

    def "LoadBankAccount"() {

    }
}
