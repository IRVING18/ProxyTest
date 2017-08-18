package ivring.proxy.callback;

import java.lang.reflect.Method;

/**
 * $desc
 * Created by IVRING on 2017/7/13.
 */

public interface Interceptor {
    Object intercept(final Object proxy, Method method,
                     final Object[] args) throws Throwable;
}
