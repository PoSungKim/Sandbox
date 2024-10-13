package fw;

import biz.market.domain.Market;
import fw.annotation.BizObject;

import java.lang.reflect.InvocationTargetException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {
    private final String name;
    private final String startTime;
    private final Map<String, Object> container;
    public ApplicationContext(Class<?> application) {
        this.name = application.getSimpleName();
        this.startTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.container = new HashMap<>();
    }
    public void addObject(Class<?> objectClass){
        try {
            this.container.putIfAbsent(objectClass.getSimpleName(), objectClass.getAnnotation(BizObject.class) != null ? objectClass.getMethod("getInstance") .invoke(objectClass.getMethod("getInstance")) : objectClass.getConstructor().newInstance());
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
    public Object getObject(Class<?> objectClass) {
        return container.get(objectClass.getSimpleName());
    }
    @Override
    public String toString() {
        return String.format("%s(name :  %s, startTime : %s, container : %s)", this.getClass().getSimpleName(), name, startTime, container);
    }
}