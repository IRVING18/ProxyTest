package ivring.proxytest.proxy.daili;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * $desc
 * Created by IVRING on 2017/7/11.
 */

public class dailiren implements InvocationHandler{
//    被代理人类的引用
    private Object obj;

    public dailiren(Object obj) {
        this.obj = obj;
    }

    /**
     * 通过invoke方法调用具体的被代理方法，也就是真实的方法
     * @param o
     * @param method
     * @param objects
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        Object invoke = method.invoke(obj);
        return invoke;
    }
}
