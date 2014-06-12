package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.beans.POUpdateInfoBean;
import com.dianping.ba.finance.exchange.api.beans.PayOrderResultBean;
import com.dianping.ba.finance.exchange.api.beans.PayOrderSearchBean;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.api.dtos.RefundDTO;
import com.dianping.ba.finance.exchange.api.dtos.RefundResultDTO;
import com.dianping.ba.finance.exchange.api.enums.PayOrderStatus;
import com.dianping.ba.finance.exchange.api.enums.RefundFailedReason;
import com.dianping.ba.finance.exchange.biz.dao.PayOrderDao;
import com.dianping.ba.finance.exchange.biz.producer.PayOrderResultNotify;
import com.dianping.core.type.PageModel;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.*;

public class PayOrderServiceObjectTest {
    private PayOrderServiceObject payOrderServiceObjectStub;
    private PayOrderDao payOrderDaoMock;
    private PayOrderResultNotify payOrderResultNotifyMock;

    @Before
    public void before() {
        payOrderDaoMock = mock(PayOrderDao.class);
        payOrderResultNotifyMock=mock(PayOrderResultNotify.class);
        payOrderServiceObjectStub = new PayOrderServiceObject();
        payOrderServiceObjectStub.setPayOrderDao(payOrderDaoMock);
        payOrderServiceObjectStub.setPayOrderResultNotify(payOrderResultNotifyMock);
    }

    @Test
    public void testCreatePayOrder() {
        PayOrderData payOrderData = new PayOrderData();
        payOrderData.setAddLoginId(-1);
        payOrderData.setBusinessType(1);
        payOrderData.setPaySequence("1");

        when(payOrderDaoMock.insertPayOrder(any(PayOrderData.class))).thenReturn(2);

        int poId = payOrderServiceObjectStub.createPayOrder(payOrderData);
        Assert.assertEquals(2, poId);
    }

    @Test
    public void testCreatePayOrderFailed() {
        PayOrderData payOrderData = new PayOrderData();
        payOrderData.setAddLoginId(-1);
        payOrderData.setBusinessType(1);
        payOrderData.setPaySequence("1");

        when(payOrderDaoMock.insertPayOrder(any(PayOrderData.class))).thenThrow(new RuntimeException("test"));
        int poId = payOrderServiceObjectStub.createPayOrder(payOrderData);
        Assert.assertEquals(-1, poId);
        verify(payOrderDaoMock, times(5)).insertPayOrder(any(PayOrderData.class));
    }

    @Test
    public void testUpdatePayOrderToPaying() {
        List<Integer> poIds = new ArrayList<Integer>();
        poIds.add(1);
        poIds.add(2);
        poIds.add(3);

        when(payOrderDaoMock.updatePayOrders(any(POUpdateInfoBean.class))).thenReturn(3);

        int actual = payOrderServiceObjectStub.updatePayOrderToPaying(poIds, -1);
        Assert.assertEquals(3, actual);

        when(payOrderDaoMock.updatePayOrders(any(POUpdateInfoBean.class))).thenThrow(new RuntimeException("test"));
        actual = payOrderServiceObjectStub.updatePayOrderToPaying(poIds, -1);
        Assert.assertEquals(-1, actual);
    }

    @Test
    public void testUpdatePayOrderToPaySuccess() {
        List<Integer> poIds = new ArrayList<Integer>();
        poIds.add(1);
        poIds.add(2);
        poIds.add(3);

        when(payOrderDaoMock.updatePayOrders(any(POUpdateInfoBean.class))).thenReturn(3);

        List<PayOrderData> payOrderDataList = new ArrayList<PayOrderData>();
        PayOrderData payOrderData=new PayOrderData();
        payOrderData.setStatus(PayOrderStatus.PAY_SUCCESS.value());
        payOrderDataList.add(payOrderData);
        when(payOrderDaoMock.findPayOrderListByPoIdList(anyListOf(Integer.class))).thenReturn(payOrderDataList);

        int actual = payOrderServiceObjectStub.updatePayOrderToPaySuccess(poIds, -1);
        Assert.assertEquals(3, actual);

        when(payOrderDaoMock.updatePayOrders(any(POUpdateInfoBean.class))).thenThrow(new RuntimeException("test"));
        actual = payOrderServiceObjectStub.updatePayOrderToPaySuccess(poIds, -1);
        Assert.assertEquals(-1, actual);
    }

    @Test
    public void testPaginatePayOrderList() {
        when(payOrderDaoMock.paginatePayOrderList(any(PayOrderSearchBean.class), anyInt(), anyInt())).thenReturn(new PageModel());
        PageModel pageModel = payOrderServiceObjectStub.paginatePayOrderList(new PayOrderSearchBean(), 1, 1);
        Assert.assertNotNull(pageModel);
    }

    @Test
    public void testFindPayOrderTotalAmount() {
        when(payOrderDaoMock.findPayOrderTotalAmountByCondition(any(PayOrderSearchBean.class))).thenReturn(BigDecimal.ONE);
        BigDecimal amount = payOrderServiceObjectStub.findPayOrderTotalAmount(new PayOrderSearchBean());
        Assert.assertEquals(amount.compareTo(BigDecimal.ONE), 0);
    }


    @Test
    public void testRefundPayOrderEmptyRefundList() throws Exception {
        payOrderServiceObjectStub.refundPayOrder(null, 1);
        verify(payOrderDaoMock, never()).findPayOrderListByPayCode(anyList());
        verify(payOrderDaoMock, never()).updatePayOrders(any(POUpdateInfoBean.class));
        verify(payOrderResultNotifyMock, never()).payResultNotify(any(PayOrderResultBean.class));
    }

    @Test
    public void testRefundPayOrderPayOrderNotFound() throws Exception {
        when(payOrderDaoMock.findPayOrderListByPayCode(anyList())).thenReturn(null);

        RefundDTO refundDTO = new RefundDTO();
        refundDTO.setRefundId("PayCode");
        refundDTO.setRefundReason("退票原因");
        RefundResultDTO resultDTO = payOrderServiceObjectStub.refundPayOrder(Arrays.asList(refundDTO), 1);

        Assert.assertTrue(resultDTO.containFailedResult());
        Assert.assertEquals(RefundFailedReason.INFO_EMPTY, resultDTO.getRefundFailedMap().get("PayCode"));

        verify(payOrderDaoMock, times(1)).findPayOrderListByPayCode(anyList());
        verify(payOrderDaoMock, never()).updatePayOrders(any(POUpdateInfoBean.class));
        verify(payOrderResultNotifyMock, never()).payResultNotify(any(PayOrderResultBean.class));
    }

    @Test
    public void testRefundPayOrderPayOrderStatusInvalid() throws Exception {
        PayOrderData poData = new PayOrderData();
        poData.setPoId(123);
        poData.setPayCode("PayCode");
        poData.setStatus(PayOrderStatus.INIT.value());
        when(payOrderDaoMock.findPayOrderListByPayCode(anyList())).thenReturn(Arrays.asList(poData));

        RefundDTO refundDTO = new RefundDTO();
        refundDTO.setRefundId("PayCode");
        refundDTO.setRefundReason("退票原因");
        RefundResultDTO resultDTO = payOrderServiceObjectStub.refundPayOrder(Arrays.asList(refundDTO), 1);

        Assert.assertTrue(resultDTO.containFailedResult());
        Assert.assertEquals(RefundFailedReason.STATUS_ERROR, resultDTO.getRefundFailedMap().get("PayCode"));

        verify(payOrderDaoMock, times(1)).findPayOrderListByPayCode(anyList());
        verify(payOrderDaoMock, never()).updatePayOrders(any(POUpdateInfoBean.class));
        verify(payOrderResultNotifyMock, never()).payResultNotify(any(PayOrderResultBean.class));
    }

    @Test
    public void testRefundPayOrderSuccess() throws Exception {
        PayOrderData poData = new PayOrderData();
        poData.setPoId(123);
        poData.setPayCode("PayCode");
        poData.setPayAmount(BigDecimal.TEN);
        poData.setStatus(PayOrderStatus.PAY_SUCCESS.value());
        when(payOrderDaoMock.findPayOrderListByPayCode(anyList())).thenReturn(Arrays.asList(poData));

        RefundDTO refundDTO = new RefundDTO();
        refundDTO.setRefundId("PayCode");
        refundDTO.setRefundReason("退票原因");
        RefundResultDTO resultDTO = payOrderServiceObjectStub.refundPayOrder(Arrays.asList(refundDTO), 1);

        Assert.assertFalse(resultDTO.containFailedResult());
        Assert.assertEquals(0, resultDTO.getRefundTotalAmount().compareTo(BigDecimal.TEN));
        Assert.assertEquals(1, resultDTO.getSuccessCount());
        verify(payOrderDaoMock, times(1)).findPayOrderListByPayCode(anyList());
        verify(payOrderDaoMock, times(1)).updatePayOrders(any(POUpdateInfoBean.class));
        verify(payOrderResultNotifyMock, times(1)).payResultNotify(any(PayOrderResultBean.class));
    }
}
