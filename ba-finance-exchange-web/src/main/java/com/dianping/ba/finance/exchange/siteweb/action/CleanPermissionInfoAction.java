package com.dianping.ba.finance.exchange.siteweb.action;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.finance.gabriel.impl.GabrielService;

/**
 * Created by will on 14-8-6.
 */
public class CleanPermissionInfoAction extends WebBaseAction {

    /**
     * 记录需要监控的业务日志
     */
    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger
            ("com.dianping.ba.finance.exchange.web.monitor.PayCentreAjaxAction");

    @Override
    protected String webExecute() throws Exception {
        try {
            GabrielService.getInstance().cleanCache();
            return SUCCESS;
        } catch (Exception e) {
            MONITOR_LOGGER.error("severity=[1] CleanPermissionInfoAction.jsonExecute error!", e);
            return ERROR;
        }
    }
}
