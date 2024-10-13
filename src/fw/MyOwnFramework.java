package fw;

import biz.market.domain.Market;
import fw.util.BizObjScanner;

import java.util.List;

public class MyOwnFramework {
    private static ApplicationContext applicationContext;
    public static void run(Class<?> application, String[] args) {
        System.out.println("\n==========================================");
        System.out.printf("FW : %s is open!", application.getSimpleName());
        System.out.println("\n==========================================");

        System.out.println("\n==========================================");
        System.out.printf("FW : %s is running!", application.getSimpleName());
        System.out.println("\n==========================================");
        preSetup(application);
        startBiz();
        postSetup();

        System.out.println("\n==========================================");
        System.out.printf("FW : %s is close!", application.getSimpleName());
        System.out.println("\n==========================================");
    }
    private static void preSetup(Class<?> application) {
        applicationContext = createApplicationContext(application);
        List<Object> bizObjectList = BizObjScanner.findBizObj();
        for(Object bizObject : bizObjectList) {
            applicationContext.addObject((Class<?>) bizObject);
        }
    }
    private static void startBiz() {
        Market market = (Market) applicationContext.getObject(Market.class);
        if (market != null) {
            market.open();
            market.run();
            market.close();
        }
    }
    private static void postSetup() {

    }
    private static ApplicationContext createApplicationContext(Class<?> application) {
        return new ApplicationContext(application);
    }
}