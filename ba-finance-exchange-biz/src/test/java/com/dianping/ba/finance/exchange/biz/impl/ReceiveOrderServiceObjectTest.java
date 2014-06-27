package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderResultBean;
import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderSearchBean;
import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderUpdateBean;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderStatus;
import com.dianping.ba.finance.exchange.biz.dao.ReceiveOrderDao;
import com.dianping.ba.finance.exchange.biz.producer.ReceiveOrderResultNotify;
import com.dianping.core.type.PageModel;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ReceiveOrderServiceObjectTest {

	private ReceiveOrderServiceObject receiveOrderServiceObjectStub;

	private ReceiveOrderDao receiveOrderDaoMock;

	private ReceiveOrderResultNotify receiveOrderResultNotifyMock;

	@Before
	public void setUp() throws Exception {
		receiveOrderServiceObjectStub = new ReceiveOrderServiceObject();

		receiveOrderDaoMock = mock(ReceiveOrderDao.class);
		receiveOrderServiceObjectStub.setReceiveOrderDao(receiveOrderDaoMock);

		receiveOrderResultNotifyMock = mock(ReceiveOrderResultNotify.class);
		receiveOrderServiceObjectStub.setReceiveOrderResultNotify(receiveOrderResultNotifyMock);
	}

	@Test
	public void testCreateReceiveOrder() throws Exception {
		when(receiveOrderDaoMock.insertReceiveOrderData(any(ReceiveOrderData.class))).thenReturn(87);

		ReceiveOrderData roData = new ReceiveOrderData();
		roData.setCustomerId(123);
		roData.setShopId(123);
		roData.setStatus(ReceiveOrderStatus.CONFIRMED.value());
		int roId = receiveOrderServiceObjectStub.createReceiveOrder(roData);
		Assert.assertEquals(87, roId);
		Assert.assertEquals(87, roData.getRoId());

		verify(receiveOrderResultNotifyMock, times(1)).receiveResultNotify(any(ReceiveOrderResultBean.class));
	}

	@Test
	public void testPaginateReceiveOrderList() throws Exception {
		ReceiveOrderData roData = new ReceiveOrderData();
		roData.setRoId(87871);
		roData.setCustomerId(123);
		roData.setShopId(123);
		roData.setStatus(ReceiveOrderStatus.CONFIRMED.value());
		PageModel pm = new PageModel();
		pm.setRecords(Arrays.asList(roData));
		when(receiveOrderDaoMock.paginateReceiveOrderList(any(ReceiveOrderSearchBean.class), anyInt(), anyInt())).thenReturn(pm);

		ReceiveOrderSearchBean searchBean = new ReceiveOrderSearchBean();
		searchBean.setCustomerId(123);
		PageModel pmResult = receiveOrderServiceObjectStub.paginateReceiveOrderList(searchBean, 1, 20);
		Assert.assertEquals(87871, ((ReceiveOrderData) pmResult.getRecords().get(0)).getRoId());
	}

	@Test
	public void testLoadReceiveOrderTotalAmountByCondition() throws Exception {
		ReceiveOrderData roData = new ReceiveOrderData();
		roData.setRoId(87871);
		roData.setCustomerId(123);
		roData.setShopId(123);
		roData.setStatus(ReceiveOrderStatus.CONFIRMED.value());
		PageModel pm = new PageModel();
		pm.setRecords(Arrays.asList(roData));
		when(receiveOrderDaoMock.loadReceiveOrderTotalAmountByCondition(any(ReceiveOrderSearchBean.class))).thenReturn(BigDecimal.TEN);

		ReceiveOrderSearchBean searchBean = new ReceiveOrderSearchBean();
		searchBean.setCustomerId(123);
		BigDecimal totalAmount = receiveOrderServiceObjectStub.loadReceiveOrderTotalAmountByCondition(searchBean);
		Assert.assertEquals(0, BigDecimal.TEN.compareTo(totalAmount));
	}

	@Test
	public void testLoadReceiveOrderByTradeNo() throws Exception {
		when(receiveOrderDaoMock.loadReceiveOrderByTradeNo(anyString())).thenReturn(null);

		ReceiveOrderData roId = receiveOrderServiceObjectStub.loadReceiveOrderByTradeNo("aaa");

		Assert.assertNull(roId);
		verify(receiveOrderDaoMock, times(1)).loadReceiveOrderByTradeNo(anyString());
	}

	@Test
	public void testDropReceiveOrder() throws Exception {
		when(receiveOrderDaoMock.updateReceiveOrderByRoId(anyInt(), any(ReceiveOrderUpdateBean.class))).thenReturn(1);

		boolean result = receiveOrderServiceObjectStub.dropReceiveOrder(123, "aaa");

		Assert.assertTrue(result);
		verify(receiveOrderDaoMock, times(1)).updateReceiveOrderByRoId(anyInt(), any(ReceiveOrderUpdateBean.class));
	}

	@Test
	public void testUpdateReverseRoId() throws Exception {
		when(receiveOrderDaoMock.updateReceiveOrderByRoId(anyInt(), any(ReceiveOrderUpdateBean.class))).thenReturn(1);

		boolean result = receiveOrderServiceObjectStub.updateReverseRoId(123, 234);

		Assert.assertTrue(result);
		verify(receiveOrderDaoMock, times(1)).updateReceiveOrderByRoId(anyInt(), any(ReceiveOrderUpdateBean.class));
	}
}