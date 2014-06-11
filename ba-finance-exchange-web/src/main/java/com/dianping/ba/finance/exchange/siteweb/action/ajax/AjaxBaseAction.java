package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.siteweb.action.WebBaseAction;

import java.util.HashMap;
import java.util.Map;

public abstract class AjaxBaseAction extends WebBaseAction {

	private static final long serialVersionUID = -4523576723974238041L;

	protected static final int SUCCESS_CODE = 200;
	protected static final int ERROR_CODE = 500;
	protected static final String msgKey = "message";

	private static AvatarLogger logger = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.web.monitor.AjaxBaseAction");
	private int code;

	private Map<String, Object> msg = new HashMap<String, Object>();


	public String webExecute() throws Exception {
		try {
			jsonExecute();
		} catch (Exception e) {
			code = ERROR_CODE;
			msg.put(msgKey, "系统异常");
			logger.error("ajax error:", e);
		}
		return SUCCESS;
	}

	abstract protected void jsonExecute() throws Exception;

	/**
	 * @return the code
	 */
	public abstract int getCode();

	/**
	 * @return the msg
	 */
	public abstract Map<String, Object> getMsg();

}
