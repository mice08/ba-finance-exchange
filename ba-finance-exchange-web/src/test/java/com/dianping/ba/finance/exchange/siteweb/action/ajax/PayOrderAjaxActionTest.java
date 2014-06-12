package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.beans.PayOrderSearchBean;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.core.type.PageModel;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Eric on 2014/6/11.
 */
public class PayOrderAjaxActionTest {

    private PayOrderService payOrderServiceMock;
    private PayOrderAjaxAction payOrderAjaxActionStub;

    @Before
    public void setUp(){
        payOrderServiceMock=mock(PayOrderService.class);
        payOrderAjaxActionStub=new PayOrderAjaxAction();
        payOrderAjaxActionStub.setPayOrderService(payOrderServiceMock);
    }

    @Test
    public void testJsonExecute() throws Exception {
        PageModel pageModel=new PageModel();
        PayOrderData payOrderData=new PayOrderData();
        pageModel.setRecords(Arrays.asList(payOrderData));
        when(payOrderServiceMock.paginatePayOrderList(any(PayOrderSearchBean.class), anyInt(), anyInt())).thenReturn(pageModel);
        when(payOrderServiceMock.findPayOrderTotalAmount(any(PayOrderSearchBean.class))).thenReturn(BigDecimal.ONE);
        payOrderAjaxActionStub.setBusinessType(0);
        payOrderAjaxActionStub.jsonExecute();

        payOrderAjaxActionStub.setBusinessType(1);
        payOrderAjaxActionStub.jsonExecute();
        Assert.assertEquals(payOrderAjaxActionStub.getTotalAmount().compareTo(BigDecimal.ONE),0);
    }
}
