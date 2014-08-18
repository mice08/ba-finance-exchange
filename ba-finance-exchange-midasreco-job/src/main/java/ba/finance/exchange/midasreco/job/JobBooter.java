package ba.finance.exchange.midasreco.job;

import ba.finance.exchange.midasreco.api.MidasRecoService;
import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.finance.common.util.LogUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by will on 14-8-18.
 */
public class JobBooter {

    private static final AvatarLogger monitorLogger = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.midasreco.job");

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
        MidasRecoService midasRecoService = (MidasRecoService) appContext.getBean("midasRecoService");
        midasRecoService.saveReconciliationData();
    }
}
