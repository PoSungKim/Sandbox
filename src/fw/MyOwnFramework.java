package fw;

import biz.market.domain.Market;
import fw.util.BizObjScanner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MyOwnFramework {
    private static ApplicationContext applicationContext;
    public static void run(Class<?> application, String[] args) {
        preSetup(application);
        startBiz(application);
        postSetup(application);
    }
    private static void preSetup(Class<?> application) {
        System.out.println("\n====================================================================================");
        System.out.printf("[%s-current thread(%s)] FW : %s is open!", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")), Thread.currentThread().getName(), application.getSimpleName());
        System.out.println("\n====================================================================================");

        applicationContext = createApplicationContext(application);

        List<Object> bizObjectList = BizObjScanner.findBizObj();
        for(Object bizObject : bizObjectList) {
            applicationContext.addObject((Class<?>) bizObject);
        }
        System.out.println(applicationContext.getContainer());
    }
    private static void startBiz(Class<?> application) {
        System.out.println("\n====================================================================================");
        System.out.printf("[%s-current thread(%s)] FW : %s is running!", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")), Thread.currentThread().getName(), application.getSimpleName());
        System.out.println("\n====================================================================================");
        Market market = (Market) applicationContext.getObject(Market.class);
        if (market != null) {
            market.open();
            market.run();
            market.close();
        }
    }
    private static void postSetup(Class<?> application) {
        System.out.println("\n====================================================================================");
        System.out.printf("[%s-current thread(%s)] FW : %s is close!", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")), Thread.currentThread().getName(), application.getSimpleName());
        System.out.println("\n====================================================================================");
        System.out.println(Market.getInstance().getTicketStore());
    }
    private static ApplicationContext createApplicationContext(Class<?> application) {
        return new ApplicationContext(application);
    }
}