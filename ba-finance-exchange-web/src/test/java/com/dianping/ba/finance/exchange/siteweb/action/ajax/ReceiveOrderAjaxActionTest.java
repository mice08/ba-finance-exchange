package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.ba.finance.exchange.api.ReceiveOrderService;
import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderSearchBean;
import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderUpdateBean;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderPaginateData;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderPayChannel;
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderStatus;
import com.dianping.ba.finance.exchange.api.enums.ReceiveType;
import com.dianping.ba.finance.exchange.siteweb.services.CustomerNameService;
import com.dianping.core.type.PageModel;
import com.google.common.collect.Maps;
import com.opensymphony.xwork2.Action;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ReceiveOrderAjaxActionTest {

    private ReceiveOrderAjaxAction receiveOrderAjaxActionStub;

    private ReceiveOrderService receiveOrderServiceMock;

    private CustomerNameService customerNameServiceMock;

    @Before
    public void setUp() throws Exception {
        receiveOrderAjaxActionStub = new ReceiveOrderAjaxAction();

        receiveOrderServiceMock = mock(ReceiveOrderService.class);
        receiveOrderAjaxActionStub.setReceiveOrderService(receiveOrderServiceMock);

        customerNameServiceMock = mock(CustomerNameService.class);
        receiveOrderAjaxActionStub.setCustomerNameService(customerNameServiceMock);
    }

    @Test
    public void testUpdateReceiveOrder() {
        receiveOrderAjaxActionStub.updateReceiveOrder();
        verify(receiveOrderServiceMock, times(1)).updateReceiveOrderConfirm(any(ReceiveOrderUpdateBean.class));
    }

    @Test
    public void testConfirmNotify() {
        when(receiveOrderServiceMock.confirmReceiveOrderAndReceiveNotify(anyInt(),anyInt(),anyInt())).thenReturn(false);
        receiveOrderAjaxActionStub.confirmNotify();
        Assert.assertEquals(receiveOrderAjaxActionStub.getCode(), ReceiveOrderAjaxAction.ERROR_CODE);

        when(receiveOrderServiceMock.confirmReceiveOrderAndReceiveNotify(anyInt(),anyInt(),anyInt())).thenReturn(true);
        receiveOrderAjaxActionStub.confirmNotify();
        Assert.assertEquals(receiveOrderAjaxActionStub.getCode(), ReceiveOrderAjaxAction.SUCCESS_CODE);
    }

    @Test
    public void testGetReceiveOrderById() {
        ReceiveOrderData receiveOrderData = new ReceiveOrderData();
        receiveOrderData.setReceiveAmount(BigDecimal.ONE);
        when(receiveOrderServiceMock.loadReceiveOrderDataByRoId(anyInt())).thenReturn(receiveOrderData);
        receiveOrderAjaxActionStub.loadReceiveOrderById();
        Assert.assertNotNull(receiveOrderAjaxActionStub.getReceiveOrder());
    }

    @Test
    public void testJsonExecute() throws Exception {
        PageModel pageModel = new PageModel();
        ReceiveOrderData receiveOrderData = new ReceiveOrderData();
        pageModel.setRecords(Arrays.asList(receiveOrderData));
        when(receiveOrderServiceMock.paginateReceiveOrderList(new ReceiveOrderSearchBean(), 1, 1)).thenReturn(pageModel);
        when(receiveOrderServiceMock.loadReceiveOrderTotalAmountByCondition(new ReceiveOrderSearchBean())).thenReturn(BigDecimal.ONE);

        receiveOrderAjaxActionStub.setBusinessType(0);
        receiveOrderAjaxActionStub.jsonExecute();

        receiveOrderAjaxActionStub.setBusinessType(1);
        receiveOrderAjaxActionStub.jsonExecute();

        Assert.assertEquals(receiveOrderAjaxActionStub.getTotalAmount(), "0.00");
    }

    @Test
    public void testJsonExecuteSuccess() throws Exception {
        PageModel pageModel = new PageModel();
        ReceiveOrderPaginateData receiveOrderData = new ReceiveOrderPaginateData();
        receiveOrderData.setCustomerId(123);
        receiveOrderData.setBusinessType(BusinessType.GROUP_PURCHASE.value());
        receiveOrderData.setPayChannel(ReceiveOrderPayChannel.POS_MACHINE.value());
        receiveOrderData.setPayTime(new Date());
        receiveOrderData.setReceiveAmount(BigDecimal.TEN);
        receiveOrderData.setReceiveTime(new Date());
        receiveOrderData.setStatus(ReceiveOrderStatus.UNCONFIRMED.value());
        receiveOrderData.setMatchedCount(10);
        pageModel.setRecords(Arrays.asList(receiveOrderData));
        when(receiveOrderServiceMock.paginateReceiveOrderList(any(ReceiveOrderSearchBean.class), anyInt(), anyInt())).thenReturn(pageModel);
        when(receiveOrderServiceMock.loadReceiveOrderTotalAmountByCondition(any(ReceiveOrderSearchBean.class))).thenReturn(BigDecimal.ONE);

        Map<Integer, String> customerIdNameMap = Maps.newHashMap();
        customerIdNameMap.put(123, "客户名称");
        when(customerNameServiceMock.getROCustomerName(anyList(), anyInt())).thenReturn(customerIdNameMap);

        receiveOrderAjaxActionStub.setBusinessType(BusinessType.GROUP_PURCHASE.value());
        receiveOrderAjaxActionStub.jsonExecute();

        Assert.assertEquals(receiveOrderAjaxActionStub.getTotalAmount(), "1.00");
    }

    @Test
    public void testCreateReceiveOrderManuallyCustomerIdZero() throws Exception {
        receiveOrderAjaxActionStub.setCustomerId(0);
        String result = receiveOrderAjaxActionStub.createReceiveOrderManually();
        Assert.assertEquals(Action.SUCCESS, result);
        Assert.assertEquals(AjaxBaseAction.ERROR_CODE, receiveOrderAjaxActionStub.getCode());
    }

    @Test
    public void testCreateReceiveOrderManuallyBusinessTypeInvalid() throws Exception {
        receiveOrderAjaxActionStub.setCustomerId(123);
        receiveOrderAjaxActionStub.setBusinessType(BusinessType.DEFAULT.value());
        String result = receiveOrderAjaxActionStub.createReceiveOrderManually();
        Assert.assertEquals(Action.SUCCESS, result);
        Assert.assertEquals(AjaxBaseAction.ERROR_CODE, receiveOrderAjaxActionStub.getCode());
    }

    @Test
    public void testCreateReceiveOrderManuallyReceiveAmountInvalid() throws Exception {
        receiveOrderAjaxActionStub.setCustomerId(123);
        receiveOrderAjaxActionStub.setBusinessType(BusinessType.ADVERTISEMENT.value());
        receiveOrderAjaxActionStub.setReceiveAmount(BigDecimal.ZERO);
        String result = receiveOrderAjaxActionStub.createReceiveOrderManually();
        Assert.assertEquals(Action.SUCCESS, result);
        Assert.assertEquals(AjaxBaseAction.ERROR_CODE, receiveOrderAjaxActionStub.getCode());
    }

    @Test
    public void testCreateReceiveOrderManuallyDateInvalid() throws Exception {
        receiveOrderAjaxActionStub.setCustomerId(123);
        receiveOrderAjaxActionStub.setBusinessType(BusinessType.ADVERTISEMENT.value());
        receiveOrderAjaxActionStub.setReceiveAmount(BigDecimal.TEN);
        receiveOrderAjaxActionStub.setBankReceiveTime("123Invalid");
        String result = receiveOrderAjaxActionStub.createReceiveOrderManually();
        Assert.assertEquals(Action.SUCCESS, result);
        Assert.assertEquals(AjaxBaseAction.ERROR_CODE, receiveOrderAjaxActionStub.getCode());
    }

    @Test
    public void testCreateReceiveOrderManuallyPayChannelInvalid() throws Exception {
        receiveOrderAjaxActionStub.setCustomerId(123);
        receiveOrderAjaxActionStub.setBusinessType(BusinessType.ADVERTISEMENT.value());
        receiveOrderAjaxActionStub.setReceiveAmount(BigDecimal.TEN);
        receiveOrderAjaxActionStub.setBankReceiveTime("2014-06-17");
        receiveOrderAjaxActionStub.setPayChannel(ReceiveOrderPayChannel.DEFAULT.value());
        String result = receiveOrderAjaxActionStub.createReceiveOrderManually();
        Assert.assertEquals(Action.SUCCESS, result);
        Assert.assertEquals(AjaxBaseAction.ERROR_CODE, receiveOrderAjaxActionStub.getCode());
    }

    @Test
    public void testCreateReceiveOrderManuallyReceiveTypeInvalid() throws Exception {
        receiveOrderAjaxActionStub.setCustomerId(123);
        receiveOrderAjaxActionStub.setBusinessType(BusinessType.ADVERTISEMENT.value());
        receiveOrderAjaxActionStub.setReceiveAmount(BigDecimal.TEN);
        receiveOrderAjaxActionStub.setBankReceiveTime("2014-06-17");
        receiveOrderAjaxActionStub.setPayChannel(ReceiveOrderPayChannel.POS_MACHINE.value());
        receiveOrderAjaxActionStub.setReceiveType(ReceiveType.DEFAULT.value());
        String result = receiveOrderAjaxActionStub.createReceiveOrderManually();
        Assert.assertEquals(Action.SUCCESS, result);
        Assert.assertEquals(AjaxBaseAction.ERROR_CODE, receiveOrderAjaxActionStub.getCode());
    }

    @Test
    public void testCreateReceiveOrderManuallyBizContentInvalid() throws Exception {
        receiveOrderAjaxActionStub.setCustomerId(123);
        receiveOrderAjaxActionStub.setBusinessType(BusinessType.ADVERTISEMENT.value());
        receiveOrderAjaxActionStub.setReceiveAmount(BigDecimal.TEN);
        receiveOrderAjaxActionStub.setBankReceiveTime("2014-06-17");
        receiveOrderAjaxActionStub.setPayChannel(ReceiveOrderPayChannel.POS_MACHINE.value());
        receiveOrderAjaxActionStub.setReceiveType(ReceiveType.AD_FEE.value());
        receiveOrderAjaxActionStub.setBizContent(null);
        String result = receiveOrderAjaxActionStub.createReceiveOrderManually();
        Assert.assertEquals(Action.SUCCESS, result);
        Assert.assertEquals(AjaxBaseAction.ERROR_CODE, receiveOrderAjaxActionStub.getCode());
    }

    @Test
    public void testCreateReceiveOrderManuallySuccess() throws Exception {
        receiveOrderAjaxActionStub.setCustomerId(123);
        receiveOrderAjaxActionStub.setBusinessType(BusinessType.ADVERTISEMENT.value());
        receiveOrderAjaxActionStub.setReceiveAmount(BigDecimal.TEN);
        receiveOrderAjaxActionStub.setBankReceiveTime("2014-06-17");
        receiveOrderAjaxActionStub.setPayChannel(ReceiveOrderPayChannel.POS_MACHINE.value());
        receiveOrderAjaxActionStub.setReceiveType(ReceiveType.AD_FEE.value());
        receiveOrderAjaxActionStub.setBizContent("fadsf123123");
        String result = receiveOrderAjaxActionStub.createReceiveOrderManually();
        Assert.assertEquals(Action.SUCCESS, result);
        Assert.assertEquals(AjaxBaseAction.SUCCESS_CODE, receiveOrderAjaxActionStub.getCode());
        verify(receiveOrderServiceMock, times(1)).createReceiveOrder(any(ReceiveOrderData.class));
    }
}