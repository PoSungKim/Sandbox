package fw.util;

import fw.annotation.BizObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BizObjScanner {
    public static List<Object> findBizObj() {
        List<String> fullClassPathStrLst = findAllFullClassPathStrUnderBizPkg();
        List<Object> bizObjLst = findAllObjWithBizAnnotation(fullClassPathStrLst);
        return bizObjLst;
    }
    private static List<Object> findAllObjWithBizAnnotation(List<String> fullClassPathLst) {
        return fullClassPathLst
                .stream()
                .map(classPath -> {
                    try {
                        return Class.forName(classPath.substring(0, classPath.indexOf(".")).replace("/", "."));
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }})
                .filter(bizObj -> bizObj.getAnnotation(BizObject.class) != null)
                .collect(Collectors.toList());
    }
    private static List<String> findAllFullClassPathStrUnderBizPkg() {
        List<String> bizObjLst = new ArrayList<>();
        List<String> pathCurList = new ArrayList<>() {{
            this.add("biz");
        }};
        List<String> pathTmpLst = new ArrayList<>();

        for(int i = 0; i < pathCurList.size(); i++) {
            String path = pathCurList.get(i);
            InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(path);

            if (stream == null) throw new RuntimeException("biz 패키지 및 Biz 하단 패키지에 정의된 Class가 없습니다");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            reader.lines().forEach(line -> {
                if (line.endsWith(".class")) {
                    bizObjLst.add(path.concat("/" + line));
                } else {
                    pathTmpLst.add(path.concat("/" + line));
                }
            });

            if (i == pathCurList.size() - 1) {
                if (pathTmpLst.isEmpty()) {
                    pathCurList.clear();
                    break;
                }
                pathCurList.clear();
                pathCurList.addAll(pathTmpLst);
                pathTmpLst.clear();
                i = -1;
            }
        }

        return bizObjLst;
    }
}