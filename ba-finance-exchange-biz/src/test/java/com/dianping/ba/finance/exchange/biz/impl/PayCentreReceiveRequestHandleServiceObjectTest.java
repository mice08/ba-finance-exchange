package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.PayCentreReceiveRequestService;
import com.dianping.ba.finance.exchange.api.ReceiveOrderService;
import com.dianping.ba.finance.exchange.api.datas.PayCentreReceiveRequestData;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
import com.dianping.ba.finance.exchange.api.dtos.PayCentreReceiveRequestDTO;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderStatus;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class PayCentreReceiveRequestHandleServiceObjectTest {

	private PayCentreReceiveRequestHandleServiceObject payCentreReceiveRequestHandleServiceObjectStub;

	private PayCentreReceiveRequestService payCentreReceiveRequestServiceMock;

	private ReceiveOrderService receiveOrderServiceMock;

	private ExecutorService executorServiceMock;

	@Before
	public void setUp() throws Exception {
		payCentreReceiveRequestHandleServiceObjectStub = new PayCentreReceiveRequestHandleServiceObject();

		payCentreReceiveRequestServiceMock = mock(PayCentreReceiveRequestService.class);
		payCentreReceiveRequestHandleServiceObjectStub.setPayCentreReceiveRequestService(payCentreReceiveRequestServiceMock);

		receiveOrderServiceMock = mock(ReceiveOrderService.class);
		payCentreReceiveRequestHandleServiceObjectStub.setReceiveOrderService(receiveOrderServiceMock);

		executorServiceMock = Executors.newSingleThreadExecutor();
		payCentreReceiveRequestHandleServiceObjectStub.setExecutorService(executorServiceMock);
	}

	@Test
	public void testHandleReceiveRequest() throws Exception {
		when(payCentreReceiveRequestServiceMock.insertPayCentreReceiveRequest(any(PayCentreReceiveRequestData.class))).thenReturn(true);
		when(receiveOrderServiceMock.createReceiveOrder(any(ReceiveOrderData.class))).thenReturn(1);

		PayCentreReceiveRequestDTO payCentreReceiveRequestDTO = new PayCentreReceiveRequestDTO();
		payCentreReceiveRequestDTO.setBankId(123);
		payCentreReceiveRequestDTO.setTradeType(1);
		payCentreReceiveRequestDTO.setTradeNo("TS312");
		payCentreReceiveRequestDTO.setBusinessType(BusinessType.ADVERTISEMENT.value());
		payCentreReceiveRequestDTO.setBizContent("8787");
		payCentreReceiveRequestDTO.setReceiveAmount(BigDecimal.TEN);
		payCentreReceiveRequestDTO.setReceiveDate(new Date());

		payCentreReceiveRequestHandleServiceObjectStub.handleReceiveRequest(payCentreReceiveRequestDTO);

		verify(receiveOrderServiceMock, timeout(5000).atLeastOnce()).createReceiveOrder(any(ReceiveOrderData.class));
		verify(payCentreReceiveRequestServiceMock, timeout(5000).atLeastOnce()).insertPayCentreReceiveRequest(any(PayCentreReceiveRequestData.class));
	}

	@Test
	public void testHandleReverseRequestWhenOriginOrderNotFound() throws Exception {
		when(payCentreReceiveRequestServiceMock.insertPayCentreReceiveRequest(any(PayCentreReceiveRequestData.class))).thenReturn(true);
		when(receiveOrderServiceMock.loadReceiveOrderByTradeNo(anyString())).thenReturn(null);

		boolean result = payCentreReceiveRequestHandleServiceObjectStub.handleReceiveRequest(buildReverseRequestDTO());

		Assert.assertTrue(result);
	}

	@Test
	public void testHandleReverseRequestWhenOrderNotConfirmed() throws Exception {
		when(payCentreReceiveRequestServiceMock.insertPayCentreReceiveRequest(any(PayCentreReceiveRequestData.class))).thenReturn(true);
		when(receiveOrderServiceMock.createReceiveOrder(any(ReceiveOrderData.class))).thenReturn(1);
		when(receiveOrderServiceMock.dropReceiveOrder(anyInt(), anyString())).thenReturn(true);
		when(receiveOrderServiceMock.loadReceiveOrderByTradeNo(anyString())).thenReturn(buildUnConfirmedReceiveOrderData());

		boolean result = payCentreReceiveRequestHandleServiceObjectStub.handleReceiveRequest(buildReverseRequestDTO());

		Assert.assertTrue(result);
		verify(receiveOrderServiceMock, never()).createReceiveOrder(any(ReceiveOrderData.class));
		verify(payCentreReceiveRequestServiceMock, timeout(5000).atLeastOnce()).insertPayCentreReceiveRequest(any(PayCentreReceiveRequestData.class));
		verify(receiveOrderServiceMock, timeout(5000).atLeastOnce()).dropReceiveOrder(anyInt(), anyString());
	}

	@Test
	public void testHandleReverseRequestWhenOrderConfirmed() throws Exception {
		when(payCentreReceiveRequestServiceMock.insertPayCentreReceiveRequest(any(PayCentreReceiveRequestData.class))).thenReturn(true);
		when(receiveOrderServiceMock.createReceiveOrder(any(ReceiveOrderData.class))).thenReturn(1);
		when(receiveOrderServiceMock.dropReceiveOrder(anyInt(), anyString())).thenReturn(true);
		when(receiveOrderServiceMock.loadReceiveOrderByTradeNo(anyString())).thenReturn(buildConfirmedReceiveOrderData());
		when(receiveOrderServiceMock.updateReverseRoId(anyInt(), anyInt())).thenReturn(true);

		boolean result = payCentreReceiveRequestHandleServiceObjectStub.handleReceiveRequest(buildReverseRequestDTO());

		Assert.assertTrue(result);
		verify(receiveOrderServiceMock, timeout(5000).atLeastOnce()).createReceiveOrder(any(ReceiveOrderData.class));
		verify(payCentreReceiveRequestServiceMock, timeout(5000).atLeastOnce()).insertPayCentreReceiveRequest(any(PayCentreReceiveRequestData.class));
		verify(receiveOrderServiceMock, never()).dropReceiveOrder(anyInt(), anyString());
		verify(receiveOrderServiceMock, timeout(5000).atLeastOnce()).updateReverseRoId(anyInt(), anyInt());
	}

	private PayCentreReceiveRequestDTO buildReverseRequestDTO() {
		PayCentreReceiveRequestDTO payCentreReceiveRequestDTO = new PayCentreReceiveRequestDTO();
		payCentreReceiveRequestDTO.setBankId(123);
		payCentreReceiveRequestDTO.setTradeType(2);
		payCentreReceiveRequestDTO.setTradeNo("TS312");
		payCentreReceiveRequestDTO.setBusinessType(BusinessType.ADVERTISEMENT.value());
		payCentreReceiveRequestDTO.setBizContent("8787");
		payCentreReceiveRequestDTO.setReceiveAmount(BigDecimal.TEN);
		payCentreReceiveRequestDTO.setReceiveDate(new Date());
		return payCentreReceiveRequestDTO;
	}

	private ReceiveOrderData buildUnConfirmedReceiveOrderData() {
		ReceiveOrderData receiveOrderData = new ReceiveOrderData();
		receiveOrderData.setStatus(ReceiveOrderStatus.UNCONFIRMED.value());
		return receiveOrderData;
	}

	private ReceiveOrderData buildConfirmedReceiveOrderData() {
		ReceiveOrderData receiveOrderData = new ReceiveOrderData();
		receiveOrderData.setStatus(ReceiveOrderStatus.CONFIRMED.value());
		return receiveOrderData;
	}
}