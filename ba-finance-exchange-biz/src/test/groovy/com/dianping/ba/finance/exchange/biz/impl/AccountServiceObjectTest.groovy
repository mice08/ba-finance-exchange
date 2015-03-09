package com.dianping.ba.finance.exchange.biz.impl

import com.dianping.ba.finance.exchange.api.beans.AccountUpdateInfoBean
import com.dianping.ba.finance.exchange.api.datas.AccountData
import com.dianping.ba.finance.exchange.api.datas.AccountEntryData
import com.dianping.ba.finance.exchange.api.datas.BankAccountData
import com.dianping.ba.finance.exchange.api.dtos.AccountEntryRequestDTO
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
        setup:
        accountDaoMock.loadAccountByBankAccount(_ as Integer) >> {
            [direction:1, balance:1]
        }
        when:
        accountServiceObjectStub.updateAccount([sourceType:1,amount:1] as AccountEntryRequestDTO)
        then:
        1* accountDaoMock.updateAccount(_ as AccountUpdateInfoBean)
    }

    def "FindAccountEntryByTime"(Integer size, Integer actual) {
        given:
        accountEntryDaoMock.findAccountEntryByTime(_ as Integer, 1, _ as Date, _ as Date) >> {
            AccountEntryData data = [id: 1]
            [data]
        }
        accountEntryDaoMock.findAccountEntryByTime(_ as Integer, 0, _ as Date, _ as Date) >> {
            []
        }
        expect:
        actual == accountServiceObjectStub.findAccountEntryByTime(1, size, new Date(), new Date()).size()

        where:
        size | actual
        1    | 1
        0    | 0
    }

    def "LoadBankAccount"(Integer id, Boolean result) {
        given:
        bankAccountDaoMock.loadBankAccount(1) >> {
            BankAccountData data = [id: 1]
            data
        }
        accountDaoMock.loadAccountByBankAccount(0) >> {
            null
        }
        expect:
        result == (accountServiceObjectStub.loadBankAccount(id) != null)
        where:
        id | result
        1  | true
        0  | false
    }


}
