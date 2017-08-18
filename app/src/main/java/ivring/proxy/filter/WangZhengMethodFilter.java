package ivring.proxy.filter;

import java.lang.reflect.Method;

/**
 * 这个类现在没有任何意义，以后可以用来过滤用WangZheng注解的方法
 * Created by IVRING on 2017/7/13.
 */

public class WangZhengMethodFilter implements InterceptorFilter {
    @Override
    public int accept(Method method) {
        return 0;
    }
}
