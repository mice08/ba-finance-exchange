package com.dianping.ba.finance.exchange.siteweb.action;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.portal.header.client.util.AuthenticationUtil;
import com.dianping.combiz.web.action.AvatarAction;
import com.dianping.finance.common.util.LionConfigUtils;
import com.dianping.midas.common.LionUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * WebBaseAction页面基础类
 */
public abstract class WebBaseAction extends AvatarAction implements Preparable {

    protected static final int SUCCESS_CODE = 200;
    protected static final int ERROR_CODE = 500;
    protected static final int NOT_LOGIN = 403;
    private static final long serialVersionUID = 1L;

    /**
     * 记录需要监控的业务日志
     */
    private static final AvatarLogger monitorLogger = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.web.monitor.WebBaseAction");
    private HashMap<String, String> actionMap = new HashMap<String, String>();

    @Override
    public void prepare() throws Exception {
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            // 这个参数别乱动 后果自负
            response.setHeader("Cache-Control", "must-revalidate, no-cache,private,no-store");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "-1");
            ActionContext context = getActionContext();
            context.put("currentChannel", "");
//            context.put("domain", "http://" + WebUtil.getWebUrlFromLion());
//            context.put("loginName", getLoginName());
        } catch (Exception ex) {
            monitorLogger.error(String.format("severity=[2] WebBaseAction.basePrepare set header info failed"), ex);
        }
    }

    public ActionContext getActionContext() {
        return ActionContext.getContext();
    }

    public String execute() throws Exception {
        String result;
        ActionContext context = getActionContext();
        try {
            String nameSpace = ServletActionContext.getActionMapping().getNamespace();
            String currentChannel = actionMap.get(nameSpace);
            context.put("currentChannel", currentChannel);
            if(ServletActionContext.getRequest().getRequestURL().indexOf("payorder/orderlist") > 0) {
                context.put("authChannel", LionConfigUtils.getProperty("ba-finance-exchange-service.auth.type"));
            }
            context.put("currentChannel", currentChannel);
//            context.put("domain", "http://" + WebUtil.getWebUrlFromLion());
//            context.put("loginName", getLoginName());
            result = webExecute();
        } catch (Exception ex) {
            monitorLogger.error(String.format("severity=[2] WebBaseAction.baseExecute error"), ex);
            context.put("msg", "系统错误");
            return ERROR;
        }
        return result;
    }

    public int getLoginId(){
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            if (request == null) {
                return 0;
            }
            String assertion = AuthenticationUtil.getRemoteUser(request);
            if (assertion != null) {
                return Integer.parseInt(assertion.split("\\|")[1]);
            }
        } catch (Exception e) {
            monitorLogger.error(String.format("severity=[1] getLoginId error!"), e);
        }
        return 0;
    }

    public String getWorkNo(){
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            if (request == null) {
                return null;
            }
            String assertion = AuthenticationUtil.getRemoteUser(request);
            if (assertion != null) {
                return assertion.split("\\|")[2];
            }
        } catch (Exception e) {
            monitorLogger.error(String.format("severity=[1] getWorkNo error!"), e);
        }
        return null;
    }

    abstract protected String webExecute() throws Exception;

}
