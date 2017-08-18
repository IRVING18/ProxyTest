package ivring.proxy;

import com.google.dexmaker.stock.ProxyBuilder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import ivring.proxy.callback.Interceptor;
import ivring.proxy.filter.InterceptorFilter;

/**
 * 动态代理类。被代理人：BaseControl类。
 * 这里使用的是ProxyBuilder框架，参考：https://github.com/linkedin/dexmaker
 * create方法是真正实现动态代理的方法。
 *
 * Created by IVRING on 2017/7/13.
 */

public class Enhancer<T> implements InvocationHandler {
//    在WZMethodAtomInterceptor中实现这个接口，然后处理的所有WangZheng注解的函数。
    private Interceptor[] callBacks;
//    这个是对WangZheng注解的方法进行过滤的类，但是目前项目中并没有实质性用到。
    private InterceptorFilter filter;
//    T泛型实际是BaseControl的子类，这个参数是用来动态代理模式ProxyBuilder框架的固定使用参数。
//    其实ProxyBuilder代理的就是BaseControl的子类
    private Class<T> superClazz;
//    这两个参数是ProxyBuilder用的Handler的对象。Type传的Handler的字节码文件，Values传的对象。
//    而我们这个项目中用的是封装Handler的静态代理类MessageProxy
    private Class<?>[] constructorArgTypes;
    private Object[] constructorArgValues;
//    这个参数是ProxyBuilder用来在运行时生成动态代理的参数的文件位置。
    private File cacheFileDir;

    /**
     * 最简单的ProxyBuilder框架生成动态代理的参数
     * @param superClazz  被代理类的字节码文件（实际用的是BaseControl的子类）
     * @param cacheFileDir  这个参数是必须参数，ProxyBuilder在动态代理的时候需要的文件位置。
     */
    public Enhancer(Class<T> superClazz, File cacheFileDir) {
        this.superClazz = superClazz;
        this.cacheFileDir = cacheFileDir;
    }

    /**
     * ProxyBuilder的第二种用法，可以自定义Handler添加进来
     * @param superClazz 被代理类
     * @param constructorArgTypes 自定义Handler（或者Handler的代理类）字节码文件
     * @param constructorArgValues 自定义Handler（或Handler的代理类）的对象
     * @param cacheFileDir 必要文件对象。
     */
    public Enhancer(Class<T> superClazz, Class<?>[] constructorArgTypes, Object[] constructorArgValues, File cacheFileDir) {
        this.superClazz = superClazz;
        this.constructorArgTypes = constructorArgTypes;
        this.constructorArgValues = constructorArgValues;
        this.cacheFileDir = cacheFileDir;
    }

    /**
     * 动态代理的方法
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] objects) throws Throwable {
//        这是用来判断过滤器是否添加的方法。其实我们项目中filter并没有实质性的使用
        if (filter == null) {
            return null;
        }
//        通过filter获取是否过滤method，但是实际我们直接返回的0，并没有做实质性的动作
        int accept = filter.accept(method);
//        过滤器没有实质性作用，这个更没有了，盼以后使用过程中又新的理解
        if (accept >= callBacks.length) {
            return null;
        }
//        如果hashcode，toString方法的话直接动态代理执行。
        if (method.getName().equals("hashCode")
                || method.getName().equals("toString")) {
            return ProxyBuilder.callSuper(proxy, method, objects);
        }
//        正常方法就通过WZMethodAtomIterceptor处理被注解的方法。
//        看api中，实际这个方法返回的是一个ProxyBuilder.callSuper()对象。具体是个啥，不太明白，以后开发中再理解吧。
        return callBacks[accept].intercept(proxy, method, objects);
    }

    /**
     * 对外暴露初始化Interceptor接口的方法，Interceptor接口的继承类是实际处理被注解函数的。
     * 也就是拦截器，拦截被注解的函数。
     * @param callBacks
     */
    public void setCallBacks(Interceptor[] callBacks) {
        this.callBacks = callBacks;
    }

    public void setFilter(InterceptorFilter filter) {
        this.filter = filter;
    }

    /**
     * 暴露用来真正可以生成代理的方法。
     * 这个方法执行返回值Ｔ实际上就是BaseControl的子类对象。
     *
     * 这里写的这些方法都是ProxyBuilder框架的固定使用。参考文档：https://github.com/linkedin/dexmaker
     * @return
     */
    public T create(){
//        传入被代理类，BaseControl的子类字节码文件
        ProxyBuilder<T> tProxyBuilder = ProxyBuilder.forClass(superClazz);
//        判断自定义Handler对象是否传进来了，传进来就用上。
        if (constructorArgTypes != null && constructorArgValues != null
                //因为ProxyBuilder框架要求参数是数组，所以参数设成了数组，这里是判断是不是传递进来的Handler对象和字节码文件个数一致的。
                && constructorArgValues.length == constructorArgTypes.length) {
//            ProxyBuilder设置自定义Handler
            tProxyBuilder.constructorArgTypes(constructorArgTypes)
                    .constructorArgValues(constructorArgValues);
        }
//        传递InvocationHandler的对象。而我们这个类本身就是实现了这个接口的
        tProxyBuilder.handler(this);
//        设置框架需要的缓存文件对象
        tProxyBuilder.dexCache(cacheFileDir);
        try {
//        创建动态代理对象
            return tProxyBuilder.build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
