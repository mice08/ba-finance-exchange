package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.beans.PayOrderSearchBean;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.api.enums.PayOrderStatus;
import com.dianping.ba.finance.exchange.siteweb.services.PayTemplateService;
import com.dianping.core.type.PageModel;
import com.google.common.collect.Maps;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * Created by Eric on 2014/6/11.
 */
public class PayOrderAjaxActionTest {

    private PayOrderService payOrderServiceMock;
    private PayTemplateService payTemplateServiceMock;
    private PayOrderAjaxAction payOrderAjaxActionStub;

	@Before
	public void setUp() {
		payOrderServiceMock = mock(PayOrderService.class);
        payTemplateServiceMock = mock(PayTemplateService.class);
		payOrderAjaxActionStub = new PayOrderAjaxAction4Test();
		payOrderAjaxActionStub.setPayOrderService(payOrderServiceMock);
        Map<String, PayTemplateService> payTemplateServiceMap = Maps.newHashMap();
        payTemplateServiceMap.put("Minsheng", payTemplateServiceMock);
        payTemplateServiceMap.put("Merchants", payTemplateServiceMock);
        payOrderAjaxActionStub.setPayTemplateServiceMap(payTemplateServiceMap);
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
        Assert.assertEquals(payOrderAjaxActionStub.getTotalAmount(),"0.00");
    }

	@Test
	public void testExportForPayWhenFindNoOrder() throws Exception {
		payOrderAjaxActionStub.setBusinessType(0);
		when(payOrderServiceMock.findPayOrderList(any(PayOrderSearchBean.class))).thenReturn(new ArrayList<PayOrderData>());

		String actual = payOrderAjaxActionStub.payOrderExportForPay();

		Assert.assertNull(actual);
		verify(payOrderServiceMock, never()).updatePayOrderToPaying(anyList(), anyInt());
	}

	@Test
	public void testExportForPayWhenFindOrderNotAllowedExport() throws Exception {
		payOrderAjaxActionStub.setBusinessType(0);
		List orderList = new ArrayList<PayOrderData>();
		PayOrderData dataAmountIsZero = new PayOrderData();
		dataAmountIsZero.setPayAmount(BigDecimal.ZERO);
		orderList.add(dataAmountIsZero);
		PayOrderData dataStatusIsSuccess = new PayOrderData();
		dataStatusIsSuccess.setStatus(PayOrderStatus.PAY_SUCCESS.value());
		orderList.add(dataAmountIsZero);
		when(payOrderServiceMock.findPayOrderList(any(PayOrderSearchBean.class))).thenReturn(orderList);

		String actual = payOrderAjaxActionStub.payOrderExportForPay();

		Assert.assertNull(actual);
		verify(payOrderServiceMock, never()).updatePayOrderToPaying(anyList(), anyInt());
	}

	@Test
	public void testExportForPayWhenFindOrderToExport() throws Exception {
		payOrderAjaxActionStub.setBusinessType(BusinessType.EXPENSE.value());
		List orderList = new ArrayList<PayOrderData>();
		PayOrderData dataToExport = new PayOrderData();
		dataToExport.setPayAmount(BigDecimal.TEN);
		dataToExport.setStatus(PayOrderStatus.INIT.value());
        dataToExport.setBusinessType(BusinessType.EXPENSE.value());
		orderList.add(dataToExport);
		when(payOrderServiceMock.findPayOrderList(any(PayOrderSearchBean.class))).thenReturn(orderList);

		String actual = payOrderAjaxActionStub.payOrderExportForPay();

		Assert.assertNull(actual);
		verify(payOrderServiceMock, atLeastOnce()).updatePayOrderToPaying(anyList(), anyInt());
		verify(payTemplateServiceMock, atLeastOnce()).createExcelAndDownload(any(HttpServletResponse.class), anyString(), anyList(), anyInt());
	}
	@Test

	public void testExportForPayWhenFindOrderToExportNotExpense() throws Exception {
		payOrderAjaxActionStub.setBusinessType(BusinessType.GROUP_PURCHASE.value());
		List orderList = new ArrayList<PayOrderData>();
		PayOrderData dataToExport = new PayOrderData();
		dataToExport.setPayAmount(BigDecimal.TEN);
		dataToExport.setStatus(PayOrderStatus.INIT.value());
        dataToExport.setBusinessType(BusinessType.GROUP_PURCHASE.value());
		orderList.add(dataToExport);
		when(payOrderServiceMock.findPayOrderList(any(PayOrderSearchBean.class))).thenReturn(orderList);

		String actual = payOrderAjaxActionStub.payOrderExportForPay();

		Assert.assertNull(actual);
		verify(payOrderServiceMock, atLeastOnce()).updatePayOrderToPaying(anyList(), anyInt());
		verify(payTemplateServiceMock, atLeastOnce()).createExcelAndDownload(any(HttpServletResponse.class), anyString(), anyList(), anyInt());
	}

	private class PayOrderAjaxAction4Test extends PayOrderAjaxAction {
		@Override
		protected HttpServletResponse getHttpServletResponse() {
			return new MockHttpServletResponse();
		}

        @Override
        public int getLoginId() {
            return 0;
        }
    }
}
