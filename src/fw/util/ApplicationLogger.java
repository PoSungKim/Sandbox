package fw.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ApplicationLogger {
    public static void log(Thread thread, String layer, Object object, String process) {
        System.out.printf("\n" + "=".repeat(149) + "\n" +
                "[%s-current thread(%s)-status(%s)] %s : %s is %s!" +
                "\n" + "=".repeat(149) + "\n",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")),
                thread.getName(),
                thread.getState(),
                layer,
                object,
                process
        );
    }
}
