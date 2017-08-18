package ivring.proxy.filter;

import java.lang.reflect.Method;

/**
 * $desc
 * Created by IVRING on 2017/7/13.
 */

public interface InterceptorFilter {
    int accept(Method method);
}
