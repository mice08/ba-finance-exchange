package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.beans.PayOrderSearchBean;
import com.dianping.finance.common.util.LionConfigUtils;
import com.dianping.finance.common.util.ListUtils;
import com.dianping.finance.common.util.StringUtils;
import jodd.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * 确认支付
 */
public class PaySuccessAjaxAction extends PayOrderAjaxAction {

	/**
	 * 记录需要监控的业务日志
	 */
	private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.web.monitor.PaySuccessAjaxAction");

	private String poIds;

	protected static final String msgKey = "message";

	private PayOrderService payOrderService;

	private ExecutorService executorService;

	public String submitPaySuccess() {
		try {
			List<Integer> orderIdList = getSubmitOrderIdList();
			doPaySuccess(orderIdList);
			msg.put(msgKey, "<br>本次提交条数：" + orderIdList.size());
			msg.put("count", orderIdList.size());
			code = SUCCESS_CODE;
		} catch (Exception e) {
			msg.put(msgKey, "系统异常，请稍候再试");
			MONITOR_LOGGER.error("severity=[1] PaySuccessAjaxAction.submitPaySuccess", e);
			code = ERROR_CODE;
		}
		return SUCCESS;
	}

	private List<Integer> getSubmitOrderIdList() throws ParseException {
		List<Integer> orderIdList = StringUtil.isBlank(poIds)? new ArrayList<Integer>() : StringUtils.splitStringToList(poIds, ",");
		if (CollectionUtils.isEmpty(orderIdList)){
			PayOrderSearchBean searchBean = buildPayOrderSearchBean();
			orderIdList = payOrderService.findPayOrderIdList(searchBean);
		}
		return orderIdList;
	}


	private int doPaySuccess(List<Integer> orderIdList) {
		int successCount = 0;
		int groupSize = Integer.parseInt(LionConfigUtils.getProperty("ba-finance-exchange-web.paySuccess.batchCount", "1000"));
		List<List<Integer>> orderListGroupList = ListUtils.generateListGroup(orderIdList, groupSize);
		final CountDownLatch doneSignal = new CountDownLatch(orderListGroupList.size());
		final int loginId = getLoginId();

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

	public void setPayOrderService(PayOrderService payOrderService) {
		this.payOrderService = payOrderService;
	}

	public void setPoIds(String poIds) {
		this.poIds = poIds;
	}

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}
}


