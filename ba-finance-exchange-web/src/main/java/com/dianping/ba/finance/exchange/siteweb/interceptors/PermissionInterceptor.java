package com.dianping.ba.finance.exchange.siteweb.interceptors;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.finance.gabriel.impl.GabrielService;
import com.dianping.lion.EnvZooKeeperConfig;
import com.dianping.lion.client.ConfigCache;
import com.dianping.lion.client.LionException;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by will on 14-8-5.
 */
public class PermissionInterceptor extends AbstractInterceptor {

    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.web.monitor.PermissionInterceptor");
    private static final String AJAX_ERROR = "ajaxerror";
    private GabrielService gabrielService;
    private boolean permissionLock;

    @Override
    public void init() {
        try {
            ConfigCache configCache = ConfigCache.getInstance(EnvZooKeeperConfig.getZKAddress());
            permissionLock = configCache.getBooleanProperty("ba-finance-exchange-web.permission.open");
        } catch (LionException e) {
            MONITOR_LOGGER.error(String.format("severity=[2] PermissionInterceptor.init error"), e);
        }
    }

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {

        if (gabrielService == null) {
            gabrielService = GabrielService.getInstance();
        }
        String path = ServletActionContext.getRequest().getRequestURI();

        try {
            if (!permissionLock || gabrielService.hasPermission(getLoginId(), path, getParams())) {
                return actionInvocation.invoke();
            }
        } catch (Exception e) {
            MONITOR_LOGGER.error(String.format("severity=[2] PermissionInterceptor.intercept error"), e);
        }
        if (path.contains("/ajax/")) {
            return AJAX_ERROR;
        }
        return Action.LOGIN;
    }

    private Map<String, String> getParams() {
        Map<String, String[]> tmp = ServletActionContext.getRequest().getParameterMap();
        Map<String, String> params = new HashMap<String, String>();
        Set<String> keys = tmp.keySet();
        for (String key : keys) {
            params.put(key, tmp.get(key)[0]);
        }
        return params;
    }

    private int getLoginId() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            if (request == null) {
                return 0;
            }
            String assertion = request.getRemoteUser();
            if (assertion != null) {
                return Integer.parseInt(assertion.split("\\|")[1]);
            }
        } catch (Exception e) {
            MONITOR_LOGGER.error(String.format("severity=[1] getLoginId error!"), e);
        }
        return 0;
    }
}
