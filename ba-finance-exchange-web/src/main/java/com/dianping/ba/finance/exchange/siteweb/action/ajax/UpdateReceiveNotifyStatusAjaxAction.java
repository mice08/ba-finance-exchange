package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.ReceiveNotifyService;
import com.dianping.ba.finance.exchange.api.enums.ReceiveNotifyStatus;

import java.util.Map;

/**
 *
 */
public class UpdateReceiveNotifyStatusAjaxAction extends AjaxBaseAction {

	/**
	 * 记录需要监控的业务日志
	 */
	private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.web.monitor.UpdateReceiveNotifyStatusAjaxAction");

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
