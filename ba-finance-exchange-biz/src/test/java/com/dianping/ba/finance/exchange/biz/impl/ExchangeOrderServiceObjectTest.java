package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.beans.ExchangeOrderSearchBean;
import com.dianping.ba.finance.exchange.api.beans.GenericResult;
import com.dianping.ba.finance.exchange.api.datas.*;
import com.dianping.ba.finance.exchange.api.dtos.EOAndFlowIdSummaryDTO;
import com.dianping.ba.finance.exchange.api.dtos.ExchangeOrderSummaryDTO;
import com.dianping.ba.finance.exchange.api.dtos.RefundDTO;
import com.dianping.ba.finance.exchange.api.dtos.RefundResultDTO;
import com.dianping.ba.finance.exchange.api.enums.ExchangeOrderStatus;
import com.dianping.ba.finance.exchange.biz.dao.ExchangeOrderDao;
import com.dianping.ba.finance.exchange.biz.producer.ExchangeOrderStatusChangeNotify;
import com.dianping.ba.finance.exchange.biz.utils.RandomUtils;
import com.dianping.core.type.PageModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 13-12-13
 * Time: 下午12:39
 * To change this template use File | Settings | File Templates.
 */
public class ExchangeOrderServiceObjectTest {
    private ExchangeOrderDao exchangeOrderDaoMock;
    private ExchangeOrderStatusChangeNotify exchangeOrderStatusChangeNotifyMock;
    private ExchangeOrderServiceObject exchangeOrderServiceObjectStub;

    @Before
    public void runBeforeTest() {
        exchangeOrderDaoMock = mock(ExchangeOrderDao.class);
        exchangeOrderStatusChangeNotifyMock = mock(ExchangeOrderStatusChangeNotify.class);

        exchangeOrderServiceObjectStub = new ExchangeOrderServiceObject();
        exchangeOrderServiceObjectStub.setExchangeOrderDao(exchangeOrderDaoMock);
        exchangeOrderServiceObjectStub.setExchangeOrderStatusChangeNotify(exchangeOrderStatusChangeNotifyMock);

    }

    @Test
    public void testUpdateExchangeOrderSuccess() {
        ExchangeOrderData exchangeOrderData = new ExchangeOrderData();
        exchangeOrderData.setStatus(ExchangeOrderStatus.PENDING.getExchangeOrderStatus());

        List<Integer> orderIds = new ArrayList<Integer>();
        orderIds.add(1);
        orderIds.add(2);
        orderIds.add(3);

        when(exchangeOrderDaoMock.loadExchangeOrderByOrderId(anyInt())).thenReturn(exchangeOrderData);
        when(exchangeOrderDaoMock.updateExchangeOrderData(anyInt(), any(Date.class), anyInt(), anyInt(), anyInt())).thenReturn(1);

        GenericResult<Integer> result = exchangeOrderServiceObjectStub.updateExchangeOrderToSuccess(orderIds, 1);

        Assert.assertEquals(3, result.getSuccessList().size());
        Assert.assertEquals(0, result.getFailList().size());
        List<Integer> actualIds = result.getSuccessList();
        Assert.assertArrayEquals(orderIds.toArray(new Integer[3]), actualIds.toArray(new Integer[3]));

    }

    @Test
    public void testUpdateExchangeOrderFailWhenOrderIdInvalid() {
        ExchangeOrderData exchangeOrderData = new ExchangeOrderData();
        exchangeOrderData.setStatus(ExchangeOrderStatus.PENDING.getExchangeOrderStatus());

        ShopFundAccountFlowData shopFundAccountFlowData = new ShopFundAccountFlowData();
        shopFundAccountFlowData.setFundAccountId(1);

        List<Integer> orderIds = new ArrayList<Integer>();
        orderIds.add(-2);
        orderIds.add(-1);
        orderIds.add(2);
        orderIds.add(3);

        when(exchangeOrderDaoMock.loadExchangeOrderByOrderId(anyInt())).thenReturn(exchangeOrderData);
        when(exchangeOrderDaoMock.updateExchangeOrderData(anyInt(), any(Date.class), anyInt(), anyInt(), anyInt())).thenReturn(1);

        GenericResult<Integer> result = exchangeOrderServiceObjectStub.updateExchangeOrderToSuccess(orderIds, 1);

        Assert.assertEquals(2, result.getFailList().size());
        Assert.assertTrue(result.getFailList().get(0) < 0);

        Assert.assertEquals(2, result.getSuccessList().size());
        Assert.assertTrue(result.getSuccessList().get(0) > 0);

    }

    @Test
    public void testUpdateExchangeOrderSuccessWhenExchangeTypeIsSuccess() {
        ExchangeOrderData exchangeOrderData = new ExchangeOrderData();
        exchangeOrderData.setStatus(ExchangeOrderStatus.SUCCESS.getExchangeOrderStatus());

        ShopFundAccountFlowData shopFundAccountFlowData = new ShopFundAccountFlowData();
        shopFundAccountFlowData.setFundAccountId(1);

        List<Integer> orderIds = new ArrayList<Integer>();
        orderIds.add(1);
        orderIds.add(2);
        orderIds.add(3);

        when(exchangeOrderDaoMock.loadExchangeOrderByOrderId(anyInt())).thenReturn(exchangeOrderData);

        GenericResult<Integer> result = exchangeOrderServiceObjectStub.updateExchangeOrderToSuccess(orderIds, 1);

        Assert.assertEquals(0, result.getSuccessList().size());
        Assert.assertEquals(3, result.getFailList().size());
        List<Integer> actualIds = result.getFailList();
        Assert.assertArrayEquals(orderIds.toArray(new Integer[3]), actualIds.toArray(new Integer[3]));

    }

    @Test
    public void testUpdateExchangeOrderFailed() {
        ExchangeOrderData exchangeOrderData = new ExchangeOrderData();
        exchangeOrderData.setStatus(ExchangeOrderStatus.INIT.getExchangeOrderStatus());

        List<Integer> orderIds = new ArrayList<Integer>();
        orderIds.add(1);
        orderIds.add(2);
        orderIds.add(3);

        when(exchangeOrderDaoMock.loadExchangeOrderByOrderId(anyInt())).thenReturn(exchangeOrderData);
        when(exchangeOrderDaoMock.updateExchangeOrderData(anyInt(), any(Date.class), anyInt(), anyInt(), anyInt())).thenReturn(-1);

        GenericResult<Integer> result = exchangeOrderServiceObjectStub.updateExchangeOrderToSuccess(orderIds, 1);

        Assert.assertEquals(0, result.getSuccessList().size());
        Assert.assertEquals(3, result.getFailList().size());
        List<Integer> actualIds = result.getFailList();
        Assert.assertArrayEquals(orderIds.toArray(new Integer[3]), actualIds.toArray(new Integer[3]));

    }

    @Test
    public void testUpdateExchangeOrderSuccessWhenExchangeOrderNotExist() {

        ShopFundAccountFlowData shopFundAccountFlowData = new ShopFundAccountFlowData();
        shopFundAccountFlowData.setFundAccountId(1);

        List<Integer> orderIds = new ArrayList<Integer>();
        orderIds.add(1);
        orderIds.add(2);
        orderIds.add(3);

        when(exchangeOrderDaoMock.loadExchangeOrderByOrderId(anyInt())).thenReturn(null);

        GenericResult<Integer> result = exchangeOrderServiceObjectStub.updateExchangeOrderToSuccess(orderIds, 1);

        Assert.assertEquals(0, result.getSuccessList().size());
        Assert.assertEquals(3, result.getFailList().size());
        List<Integer> actualIds = result.getFailList();
        Assert.assertArrayEquals(orderIds.toArray(new Integer[3]), actualIds.toArray(new Integer[3]));

    }

    @Test
    public void testInsertExchangeOrder() {
        ExchangeOrderData exchangeOrderData = new ExchangeOrderData();
        when(exchangeOrderDaoMock.insertExchangeOrder(exchangeOrderData)).thenReturn(1);
        int actual = exchangeOrderServiceObjectStub.insertExchangeOrder(exchangeOrderData);
        Assert.assertEquals(1, actual);
    }

    @Test
    public void testPaginateExchangeOrderList() {
        PageModel pageModel = new PageModel();
        when(exchangeOrderDaoMock.paginateExchangeOrderList(any(ExchangeOrderSearchBean.class), anyInt(), anyInt()))
                .thenReturn(pageModel);
        PageModel result = exchangeOrderServiceObjectStub.paginateExchangeOrderList(new ExchangeOrderSearchBean(), 1, 20);
        Assert.assertNotNull(result);
    }

    @Test
    public void testPaginateExchangeOrderListThrowException() {
        when(exchangeOrderDaoMock.paginateExchangeOrderList(any(ExchangeOrderSearchBean.class), anyInt(), anyInt()))
                .thenThrow(new RuntimeException());
        PageModel result = exchangeOrderServiceObjectStub.paginateExchangeOrderList(new ExchangeOrderSearchBean(), 1, 20);
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetExchangeOrderStatisticResult() {
        when(exchangeOrderDaoMock.findExchangeOrderTotalAmount(any(ExchangeOrderSearchBean.class)))
                .thenReturn(new BigDecimal(1.0));
        BigDecimal result = exchangeOrderServiceObjectStub.findExchangeOrderTotalAmount(new ExchangeOrderSearchBean());
        Assert.assertEquals(new BigDecimal(1.0), result);
    }

    @Test
    public void testGetExchangeOrderStatisticResultThrowException() {
        when(exchangeOrderDaoMock.findExchangeOrderTotalAmount(any(ExchangeOrderSearchBean.class)))
                .thenThrow(new RuntimeException());
        BigDecimal r = exchangeOrderServiceObjectStub.findExchangeOrderTotalAmount(new ExchangeOrderSearchBean());
        Assert.assertEquals(new BigDecimal(0), r);
    }

    @Test
    public void testUpdateExchangeOrderToPending() {
        List<Integer> orderIds = new ArrayList<Integer>();
        orderIds.add(1);
        orderIds.add(2);
        orderIds.add(3);

        when(exchangeOrderDaoMock.updateExchangeOrderToPending(anyListOf(Integer.class), anyInt(), anyInt(), anyInt())).thenReturn(3);

        int actual = exchangeOrderServiceObjectStub.updateExchangeOrderToPending(orderIds, 1);
        Assert.assertEquals(3, actual);
    }

    @Test
    public void testUpdateExchangeOrderToPendingFalse() {
        List<Integer> orderIds = new ArrayList<Integer>();
        orderIds.add(1);
        orderIds.add(2);
        orderIds.add(3);

        when(exchangeOrderDaoMock.updateExchangeOrderToPending(anyListOf(Integer.class), anyInt(), anyInt(), anyInt())).thenReturn(0);

        int actual = exchangeOrderServiceObjectStub.updateExchangeOrderToPending(orderIds, 1);
        Assert.assertEquals(0, actual);
    }

    @Test
    public void testFindExchangeOrderListSuccess() {
        ExchangeOrderSearchBean searchBean = new ExchangeOrderSearchBean();
        when(exchangeOrderDaoMock.findExchangeOrderList(any(ExchangeOrderSearchBean.class))).thenReturn(new ArrayList<ExchangeOrderDisplayData>());

        List<ExchangeOrderDisplayData> exList = exchangeOrderServiceObjectStub.findExchangeOrderDataList(searchBean);
        Assert.assertNotNull(exList);
    }

    @Test
    public void testFindExchangeOrderIdListSuccess() {
        ExchangeOrderSearchBean searchBean = new ExchangeOrderSearchBean();
        when(exchangeOrderDaoMock.findExchangeOrderIdList(any(ExchangeOrderSearchBean.class))).thenReturn(new ArrayList<Integer>());

        List<Integer> exList = exchangeOrderServiceObjectStub.findExchangeOrderIdList(searchBean);
        Assert.assertNotNull(exList);
    }

    @Test
    public void testRefundExchangeOrderAccountError() throws Exception {
        List<RefundDTO> refundDTOList = new ArrayList<RefundDTO>();

        RefundDTO refundDTO = new RefundDTO();
        refundDTO.setRefundId("333");
        refundDTO.setRefundReason("test333");
        refundDTOList.add(refundDTO);

        RefundDTO refundDTO1 = new RefundDTO();
        refundDTO1.setRefundId("111");
        refundDTO1.setRefundReason("test111");
        refundDTOList.add(refundDTO1);

        RefundDTO refundDTO2 = new RefundDTO();
        refundDTO2.setRefundId("222");
        refundDTO2.setRefundReason("test222");
        refundDTOList.add(refundDTO2);

        int loginId = -2000;

        List<ExchangeOrderData> exchangeOrderDataList = new ArrayList<ExchangeOrderData>();

        ExchangeOrderData exchangeOrderData = new ExchangeOrderData();
        exchangeOrderData.setBizCode("111");
        exchangeOrderData.setStatus(ExchangeOrderStatus.SUCCESS.value());
        exchangeOrderDataList.add(exchangeOrderData);

        when(exchangeOrderDaoMock.updateExchangeOrderToRefund(any(RefundDTO.class), anyInt(), anyInt(), anyInt())).thenReturn(1);
        when(exchangeOrderDaoMock.findExchangeOrderByBizCode(anyList())).thenReturn(exchangeOrderDataList);
        when(exchangeOrderDaoMock.findExchangeOrderTotalAmountByBizCode(anyList())).thenReturn(BigDecimal.ONE);
        RefundResultDTO actual = exchangeOrderServiceObjectStub.refundExchangeOrder(refundDTOList, loginId);
        Assert.assertEquals(2, actual.getRefundFailedMap().size());
    }

    @Test
    public void testRefundExchangeOrderStatusError() throws Exception {
        List<RefundDTO> refundDTOList = new ArrayList<RefundDTO>();

        RefundDTO refundDTO1 = new RefundDTO();
        refundDTO1.setRefundId("111");
        refundDTO1.setRefundReason("test111");
        refundDTOList.add(refundDTO1);

        int loginId = -2000;

        List<ExchangeOrderData> exchangeOrderDataList = new ArrayList<ExchangeOrderData>();

        ExchangeOrderData exchangeOrderData = new ExchangeOrderData();
        exchangeOrderData.setBizCode("111");
        exchangeOrderData.setStatus(ExchangeOrderStatus.INIT.value());
        exchangeOrderDataList.add(exchangeOrderData);

        when(exchangeOrderDaoMock.updateExchangeOrderToRefund(any(RefundDTO.class), anyInt(), anyInt(), anyInt())).thenReturn(1);
        when(exchangeOrderDaoMock.findExchangeOrderByBizCode(anyList())).thenReturn(exchangeOrderDataList);
        when(exchangeOrderDaoMock.findExchangeOrderTotalAmountByBizCode(anyList())).thenReturn(BigDecimal.ONE);
        RefundResultDTO actual = exchangeOrderServiceObjectStub.refundExchangeOrder(refundDTOList, loginId);
        Assert.assertEquals(1, actual.getRefundFailedMap().size());
    }


    @Test
    public void testRefundExchangeOrderAddFailedResult() throws Exception {
        List<RefundDTO> refundDTOList = new ArrayList<RefundDTO>();

        RefundDTO refundDTO = new RefundDTO();
        refundDTO.setRefundId("333");
        refundDTO.setRefundReason("test333");
        refundDTOList.add(refundDTO);

        RefundDTO refundDTO1 = new RefundDTO();
        refundDTO1.setRefundId("111");
        refundDTO1.setRefundReason("test111");
        refundDTOList.add(refundDTO1);

        RefundDTO refundDTO2 = new RefundDTO();
        refundDTO2.setRefundId("222");
        refundDTO2.setRefundReason("test222");
        refundDTOList.add(refundDTO2);

        int loginId = -2000;

        List<ExchangeOrderData> exchangeOrderDataList = new ArrayList<ExchangeOrderData>();

        ExchangeOrderData exchangeOrderData = new ExchangeOrderData();
        exchangeOrderData.setBizCode("111");
        exchangeOrderData.setStatus(ExchangeOrderStatus.SUCCESS.value());
        exchangeOrderDataList.add(exchangeOrderData);

        ExchangeOrderData exchangeOrderData1 = new ExchangeOrderData();
        exchangeOrderData1.setBizCode("222");
        exchangeOrderData1.setStatus(ExchangeOrderStatus.SUCCESS.value());
        exchangeOrderDataList.add(exchangeOrderData1);

        ExchangeOrderData exchangeOrderData2 = new ExchangeOrderData();
        exchangeOrderData2.setBizCode("333");
        exchangeOrderData2.setStatus(ExchangeOrderStatus.SUCCESS.value());
        exchangeOrderDataList.add(exchangeOrderData2);

        when(exchangeOrderDaoMock.updateExchangeOrderToRefund(any(RefundDTO.class), anyInt(), anyInt(), anyInt())).thenReturn(1);
        when(exchangeOrderDaoMock.findExchangeOrderByBizCode(anyList())).thenReturn(exchangeOrderDataList);
        when(exchangeOrderDaoMock.findExchangeOrderTotalAmountByBizCode(anyList())).thenReturn(BigDecimal.ONE);
        RefundResultDTO actual = exchangeOrderServiceObjectStub.refundExchangeOrder(refundDTOList, loginId);
        Assert.assertEquals(0, actual.getRefundFailedMap().size());
        Assert.assertEquals(0,actual.getRefundTotalAmount().compareTo(BigDecimal.ONE));
    }

    @Test
    public void testLoadExchangeOrderData() throws Exception {
        String bizCode = "P" + RandomUtils.getRandomNumbers(10);
        EOAndFlowIdSummaryData eoAndFlowIdSummaryData = new EOAndFlowIdSummaryData();
        eoAndFlowIdSummaryData.setBizCode(bizCode);
        eoAndFlowIdSummaryData.setStatus(ExchangeOrderStatus.PENDING.getExchangeOrderStatus());

        when(exchangeOrderDaoMock.loadExchangeOrderAndPositiveFlow(anyInt(), anyInt(), anyInt())).thenReturn(eoAndFlowIdSummaryData);

        EOAndFlowIdSummaryDTO result = exchangeOrderServiceObjectStub.loadExchangeOrderDataAndPositiveFlow(123);

        Assert.assertEquals(bizCode, result.getBizCode());
    }

    @Test
    public void testGetExchangeOrderSummaryInfo() throws Exception{
        String bizCode = "P" + RandomUtils.getRandomNumbers(10);
        ExchangeOrderSummaryData summaryData = new ExchangeOrderSummaryData();
        summaryData.setBizCode(bizCode);
        List<ExchangeOrderSummaryData> summaryDataList = new ArrayList<ExchangeOrderSummaryData>();
        summaryDataList.add(summaryData);
        List<Integer> flowIdList = new ArrayList<Integer>();
        flowIdList.add(1);
        when(exchangeOrderDaoMock.findExchangeOrderSummaryDataListByFlowIdList(anyList())).thenReturn(summaryDataList);

        List<ExchangeOrderSummaryDTO> actual = exchangeOrderServiceObjectStub.getExchangeOrderSummaryInfo(flowIdList);

        Assert.assertNotNull(actual);
        Assert.assertEquals(bizCode, actual.get(0).getBizCode());
    }


    @Test
    public void testGetExchangeOrderSummaryInfoWhenNoExchangeOrderFound() throws Exception{
        List<Integer> flowIdList = new ArrayList<Integer>();
        flowIdList.add(1);
        when(exchangeOrderDaoMock.findExchangeOrderSummaryDataListByFlowIdList(anyList())).thenReturn(null);

        List<ExchangeOrderSummaryDTO> actual = exchangeOrderServiceObjectStub.getExchangeOrderSummaryInfo(flowIdList);

        Assert.assertNotNull(actual);
        Assert.assertEquals(0, actual.size());
    }
}
