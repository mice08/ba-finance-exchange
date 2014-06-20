package com.dianping.ba.finance.exchange.siteweb.interceptors;

import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.combiz.util.LoginUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import org.apache.struts2.ServletActionContext;

import java.util.Enumeration;

/**
 * 用于业务的一般性访问日志记录。主要记录请求的进出参数，耗时，状态。
 * @author xiaozhe.li
 * @version 1.0
 */
public class VisitLoggerInterceptor implements Interceptor {

	private static final long serialVersionUID = 1L;

	@Override
	public void destroy() {
	}

	@Override
	public void init() {
	}

	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		Long begin = System.currentTimeMillis();
		try{
			String result = arg0.invoke();
			logStatistics(arg0, begin, System.currentTimeMillis(), "success", result);
			return result;
		}catch(Exception e){
			Long end = System.currentTimeMillis();
			StringBuffer sb = logStatistics(arg0, begin, end, "error", null);
			AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.web.monitor." + arg0.getAction().getClass().toString().split("[.]")[arg0.getAction().getClass().toString().split("[.]").length - 1]).error((end - begin) + "ms "
							+ arg0.getInvocationContext().getName() + " error " + sb, e);
			return "error";
		}
	}

	private StringBuffer logStatistics(ActionInvocation arg0, Long begin,
                                       Long end, String resultType, String resultCode) {
		StringBuffer sb = new StringBuffer();
		if(ServletActionContext.getRequest().getParameterNames() != null){
			Enumeration en = ServletActionContext.getRequest().getParameterNames();
			while(en.hasMoreElements()){
				String name = (String)en.nextElement();
				sb.append("&");
				sb.append(name);
				sb.append("=");
				sb.append(ServletActionContext.getRequest().getParameter(name));
			}
		}

		String[] actionStrArr = arg0.getAction().getClass().toString().split("[.]");
		AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.web.statistics").info("action=" +
				actionStrArr[actionStrArr.length - 1]
						+ ":"
						+ arg0.getInvocationContext().getName()
						+ sb.toString()
						+ "&resultType=" + resultType
						+ "&resultCode=" + resultCode
						+ "&startTime=" + begin
						+ "&endTIme=" + end
						+ "&period=" + (end - begin) + "ms"
			    		+ "&ip=" + LoginUtils.getUserIP(ServletActionContext.getRequest()));
		return sb;
	}
}
