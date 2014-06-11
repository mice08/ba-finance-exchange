package com.dianping.ba.finance.exchange.siteweb.action;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: huajiao.zou
 * Date: 13-12-13
 * Time: 下午4:32
 * Time: 下午4:32
 * To change this template use File | Settings | File Templates.
 */
public class ExchangeOrderListActon extends WebBaseAction {
	/**
	 * 记录需要监控的业务日志
	 */
	private static final AvatarLogger monitorLogger = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.web.monitor.ExchangeOrderListActon");

	@Override
	protected String webExecute() throws Exception {
        return SUCCESS;
    }
}
