package com.dianping.ba.finance.exchange.monitor.job;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.monitor.job.utils.LogUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Job入口-启动器
 */
public class JobBooter {

    private static final AvatarLogger monitorLogger = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.monitor.job");

	public static void main(String [] args) {
        Long startTime = System.currentTimeMillis();
		try{
            monitorLogger.info("============job start===============");
			startSpring();
            monitorLogger.info("============job end===============");
		} catch (Exception e){
            monitorLogger.error(LogUtils.formatErrorLogMsg(startTime, "JobBooter", ""), e);
		} finally {
            System.exit(0);
        }
    }

	/**
	 * 启动spring容器
	 */
	private static void startSpring() throws InterruptedException {
		String[] path = new String[2];
		path[0] = "classpath*:/config/spring/local/appcontext-*.xml";
		path[1] = "classpath*:/config/spring/common/appcontext-*.xml";
        ApplicationContext appContext = new ClassPathXmlApplicationContext(path);
        CheckController controller = (CheckController) appContext.getBean("checkController");
        controller.execute();
	}


}
