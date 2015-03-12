package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.auditlog.api.enums.OperationType;
import com.dianping.ba.finance.auditlog.client.OperationLogger;
import com.dianping.ba.finance.exchange.api.ReceiveNotifyService;
import com.dianping.ba.finance.exchange.api.enums.ReceiveNotifyStatus;
import com.dianping.finance.common.util.LionConfigUtils;

import java.util.Map;

/**
 *
 */
public class UpdateReceiveNotifyStatusAjaxAction extends AjaxBaseAction {

	/**
	 * 记录需要监控的业务日志
	 */
	private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.web.monitor.UpdateReceiveNotifyStatusAjaxAction");

    private static final OperationLogger OPERATION_LOGGER = new OperationLogger("Exchange", "PayOrder", LionConfigUtils.getProperty("ba-finance-exchange-web.auditlog.token"));

    private int rnId;

    private String rejectReason;

    private ReceiveNotifyService receiveNotifyService;

    @Override
	protected void jsonExecute() {
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public Map<String, Object> getMsg() {
		return msg;
	}


    public String confirmReceiveNotify() {
        if (rnId <= 0) {
            msg.put(msgKey, "请选择收款通知");
            code = ERROR_CODE;
            return SUCCESS;
        }
        try {
            OPERATION_LOGGER.log(OperationType.UPDATE, "确认收款通知", String.format("rnId: %s", rnId), String.valueOf(getLoginId()));
            int u = receiveNotifyService.updateReceiveNotifyStatus(rnId, ReceiveNotifyStatus.INIT, ReceiveNotifyStatus.CONFIRMED, "");
            if (u > 0) {
                msg.put(msgKey, "更新成功");
            } else {
                msg.put(msgKey, "更新失败");
            }
            code = SUCCESS_CODE;
        } catch (Exception e) {
            MONITOR_LOGGER.error("severity=[1] ReceiveOrderAjaxAction.jsonExecute error!", e);
            code = ERROR_CODE;
        }
        return SUCCESS;
    }

    public String rejectReceiveNotify() {
        if (rnId <= 0) {
            msg.put(msgKey, "请选择收款通知");
            code = ERROR_CODE;
            return SUCCESS;
        }
        try {
            OPERATION_LOGGER.log(OperationType.UPDATE, "驳回收款通知", String.format("rnId: %s, rejectReason: %s", rnId, rejectReason), String.valueOf(getLoginId()));
            int u = receiveNotifyService.updateReceiveNotifyStatus(rnId, ReceiveNotifyStatus.INIT, ReceiveNotifyStatus.REJECT, rejectReason);
            if (u > 0) {
                msg.put(msgKey, "更新成功");
            } else {
                msg.put(msgKey, "更新失败");
            }
            code = SUCCESS_CODE;
        } catch (Exception e) {
            MONITOR_LOGGER.error("severity=[1] ReceiveOrderAjaxAction.jsonExecute error!", e);
            code = ERROR_CODE;
        }
        return SUCCESS;
    }

    public void setRnId(int rnId) {
        this.rnId = rnId;
    }

    public void setReceiveNotifyService(ReceiveNotifyService receiveNotifyService) {
        this.receiveNotifyService = receiveNotifyService;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
}
