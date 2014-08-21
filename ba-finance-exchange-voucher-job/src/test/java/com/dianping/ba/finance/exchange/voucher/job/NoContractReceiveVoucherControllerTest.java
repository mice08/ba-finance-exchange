package com.dianping.ba.finance.exchange.voucher.job;

import com.dianping.ba.finance.exchange.api.ReceiveVoucherService;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class NoContractReceiveVoucherControllerTest {

    private NoContractReceiveVoucherController noContractReceiveVoucherControllerStub;
    private ReceiveVoucherService receiveVoucherServiceMock;

    @Before
    public void setup(){
        noContractReceiveVoucherControllerStub = new NoContractReceiveVoucherController();
        noContractReceiveVoucherControllerStub.setReceiveVoucherService(receiveVoucherServiceMock);
    }

    @Test
    public void testExecute(){
        boolean actual = noContractReceiveVoucherControllerStub.execute();
        Assert.assertTrue(actual);
//        verify(receiveVoucherServiceMock, Mockito.times(1)).generateUnconfirmedReceiveVoucher(any(Date.class));
    }

}