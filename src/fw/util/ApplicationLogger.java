package fw.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ApplicationLogger {
    public static void log(Thread thread, String layer, Object object) {
        System.out.printf("\n============================================================================================================================\n" +
                "[%s-current thread(%s)-status(%s)] %s : %s is running!" +
                "\n============================================================================================================================\n",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")),
                thread.getName(),
                thread.getState(),
                layer,
                object
        );
    }
}
