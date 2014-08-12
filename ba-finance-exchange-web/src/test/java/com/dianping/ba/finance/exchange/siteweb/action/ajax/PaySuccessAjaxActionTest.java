package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.beans.PayOrderSearchBean;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.api.enums.PayOrderStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.*;

/**
 * Description：确认支付PaySuccessAjaxActionTest
 * User: liufeng.ren
 * Date: 14-6-12
 * Time: 下午1:48
 */
public class PaySuccessAjaxActionTest {
	private PayOrderService payOrderServiceMock;
	private PaySuccessAjaxAction paySuccessAjaxActionStub;
	private ExecutorService executorServiceMock;

	@Before
	public void setUp() {
		payOrderServiceMock = mock(PayOrderService.class);
		paySuccessAjaxActionStub = new PaySuccessAjaxAction();
		executorServiceMock = Executors.newSingleThreadExecutor();

		paySuccessAjaxActionStub.setExecutorService(executorServiceMock);
		paySuccessAjaxActionStub.setPayOrderService(payOrderServiceMock);
	}

	@Test
	public void testSubmitPaySuccessByPoIds() {
		paySuccessAjaxActionStub.setPoIds("12345");
		when(payOrderServiceMock.updatePayOrderToPaySuccess(anyList(), anyInt())).thenReturn(1);

		String actual = paySuccessAjaxActionStub.submitPaySuccess();

		assertEquals("success", actual);
		verify(payOrderServiceMock, never()).findPayOrderList(any(PayOrderSearchBean.class));
		verify(payOrderServiceMock, atLeastOnce()).updatePayOrderToPaySuccess(anyList(), anyInt());
	}

	@Test
	public void testSubmitPaySuccessByConditionFindNothing() {
		when(payOrderServiceMock.findPayOrderList(any(PayOrderSearchBean.class))).thenReturn(new ArrayList<PayOrderData>());
		when(payOrderServiceMock.updatePayOrderToPaySuccess(anyList(), anyInt())).thenReturn(1);

		String actual = paySuccessAjaxActionStub.submitPaySuccess();

		assertEquals("success", actual);
		verify(payOrderServiceMock, atLeastOnce()).findPayOrderIdList(any(PayOrderSearchBean.class));
		verify(payOrderServiceMock, never()).updatePayOrderToPaySuccess(anyList(), anyInt());
	}

	@Test
	public void testSubmitPaySuccessByConditionStatusIsValid() {
		when(payOrderServiceMock.findPayOrderIdList(any(PayOrderSearchBean.class))).thenReturn(Arrays.asList(123));
		when(payOrderServiceMock.updatePayOrderToPaySuccess(anyList(), anyInt())).thenReturn(1);

		String actual = paySuccessAjaxActionStub.submitPaySuccess();

		assertEquals("success", actual);
		verify(payOrderServiceMock, atLeastOnce()).findPayOrderIdList(any(PayOrderSearchBean.class));
		verify(payOrderServiceMock, atLeastOnce()).updatePayOrderToPaySuccess(anyList(), anyInt());
	}

	@Test
	public void testSubmitPaySuccessByConditionStatusIsInvalid() {
		List<PayOrderData> dataList = new ArrayList<PayOrderData>();
		PayOrderData data = new PayOrderData();
		data.setStatus(PayOrderStatus.INIT.value());
		dataList.add(data);
		when(payOrderServiceMock.findPayOrderList(any(PayOrderSearchBean.class))).thenReturn(dataList);
		when(payOrderServiceMock.updatePayOrderToPaySuccess(anyList(), anyInt())).thenReturn(1);

		String actual = paySuccessAjaxActionStub.submitPaySuccess();

		assertEquals("success", actual);
		verify(payOrderServiceMock, atLeastOnce()).findPayOrderIdList(any(PayOrderSearchBean.class));
		verify(payOrderServiceMock, never()).updatePayOrderToPaySuccess(anyList(), anyInt());
	}
}
