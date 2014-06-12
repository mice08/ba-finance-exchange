package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.beans.PayOrderSearchBean;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.api.enums.PayOrderStatus;
import com.dianping.finance.common.util.LionConfigUtils;
import com.dianping.finance.common.util.ListUtils;
import com.dianping.finance.common.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * Created with IntelliJ IDEA.
 * User: huajiao.zou
 * Date: 13-12-16
 * Time: 上午11:02
 * To change this template use File | Settings | File Templates.
 */
public class PaySuccessAjaxAction extends PayOrderAjaxAction {

	/**
	 * 记录需要监控的业务日志
	 */
	private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.web.monitor.PaySuccessAjaxAction");

	private String orderIds;

	private Map<String, Object> msg = new HashMap<String, Object>();

	private int code;

	protected static final String msgKey = "message";

	private PayOrderService payOrderService;

	private ExecutorService executorService;

	private int loginId;

	public String submitPaySuccess() {
		loginId = GetLoginId();
		try {
			List<Integer> orderIdList = StringUtils.splitStringToList(orderIds, ",");
			doPaySuccess(orderIdList);
			msg.put(msgKey, "<br>本次提交条数：" + orderIdList.size());
			code = SUCCESS_CODE;
		} catch (Exception e) {
			msg.put(msgKey, "系统异常，请稍候再试");
			MONITOR_LOGGER.error("severity=[1] PaySuccessAjaxAction.submitPaySuccess", e);
			code = ERROR_CODE;
		}
		return SUCCESS;
	}

	public String submitPaySuccessBySearchCondition() {
		loginId = GetLoginId();
		try {
			PayOrderSearchBean searchBean = buildPayOrderSearchBean();
			List<PayOrderData> orderList = payOrderService.findPayOrderList(searchBean);
			List<Integer> orderIdList = getSubmitOrderIdList(orderList);
			doPaySuccess(orderIdList);
			msg.put(msgKey, "<br>本次提交条数：" + orderIdList.size());
			code = SUCCESS_CODE;
		} catch (Exception e) {
			msg.put(msgKey, "系统异常，请稍候再试");
			MONITOR_LOGGER.error("PaySuccessAjaxAction.submitPaySuccessBySearchCondition", e);
			code = ERROR_CODE;
		}
		return SUCCESS;
	}

	private List<Integer> getSubmitOrderIdList(List<PayOrderData> orderList) {
		List<Integer> orderIdList = new ArrayList<Integer>();
		for (PayOrderData order : orderList){

		if (order.getStatus() == PayOrderStatus.EXPORT_PAYING.value())
			orderIdList.add(order.getPoId());
		}
		return orderIdList;
	}

	private int doPaySuccess(List<Integer> orderIdList) {
		int successCount = 0;
		int groupSize = Integer.parseInt(LionConfigUtils.getProperty("ba-finance-exchange-web.paySuccess.batchCount","1000"));
		List<List<Integer>> orderListGroupList = ListUtils.generateListGroup(orderIdList, groupSize);
		final CountDownLatch doneSignal = new CountDownLatch(orderListGroupList.size());

		for (List<Integer> orderList : orderListGroupList) {
			final List<Integer> processList = new ArrayList<Integer>(orderList);
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					try {
						String batchId = System.nanoTime() + "_" + Thread.currentThread().getId();
						MONITOR_LOGGER.info(String.format("[%d][pt_event=paySuccessStart]batchId=%s;", System.currentTimeMillis(), batchId));
						payOrderService.updatePayOrderToPaySuccess(processList, loginId);
						MONITOR_LOGGER.info(String.format("[%d][pt_event=paySuccessComplete]batchId=%s;", System.currentTimeMillis(), batchId));
					} finally {
						doneSignal.countDown();
					}
				}
			});
		}
		try {
			doneSignal.await();
		} catch (InterruptedException e) {
			MONITOR_LOGGER.log("doPaySuccess", e);
		}
		return successCount;
	}

	@Override
	public int getCode() {
		return this.code;
	}

	@Override
	public Map<String, Object> getMsg() {
		return this.msg;
	}

	public void setPayOrderService(PayOrderService payOrderService) {
		this.payOrderService = payOrderService;
	}

	private int GetLoginId() {
		return 0;
	}

	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}
}


