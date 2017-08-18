package ivring.proxy.callback;

import android.os.Message;
import android.os.Process;
import android.util.Log;

import com.google.dexmaker.stock.ProxyBuilder;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ivring.proxy.annotation.WangZheng;
import ivring.proxy.message.MessageArg;
import ivring.proxy.message.MessageProxy;

/**
 * 这个类是用来获取使用WangZheng注解的，并在这个拦截器中进行对函数的调用和处理。
 *
 * 将所有的Wangzheng注解的函数都放到了子线程中去处理，因为需要耗时操作。
 * 并通过动态代理的模式来执行这个方法。ProxyBuilder.callSuper(proxy, method, args);
 * Created by IVRING on 2017/7/13.
 */

public class WZMethodAtomInterceptor implements Interceptor{
    //线程
    private static ExecutorService service = Executors.newCachedThreadPool();
    //message 的封装，其实就是Handler，因为它是Handler的静态代理类
    private MessageProxy mMessageProxy;
    //动态代理的方法集合，就是用来存储带WZ注解的函数的集合。
    private Set<String> methodHashSet;

    public WZMethodAtomInterceptor(MessageProxy mMessageProxy) {
        this.mMessageProxy = mMessageProxy;
//        创建一个线程安全的set集合。
        methodHashSet = Collections.synchronizedSet(new HashSet<String>());
    }

    @Override
    public Object intercept(final Object proxy, final Method method, final Object[] args) throws Throwable {
//        获取Method的注解
        final WangZheng wzAnnotation = method.getAnnotation(WangZheng.class);
        if (wzAnnotation != null){
            switch (wzAnnotation.methodType()){
                case atom:
//                    如果这个方法已经在集合中了就不再重复添加
                    if (methodHashSet.contains(method.getName())){
                        return null;
                    }else{
                        methodHashSet.add(method.getName());
                    }
                    break;
            }
//            如果注解上添加了进度框标志，就通过Handler代理类发送显示进度框指令
            if (wzAnnotation.withDialog()){
                mMessageProxy.showDialog();
            }
//            其实这个代码中和上边的是一个东西，在BaseHandler封装的时候就是一样的。用来演示就好，可以随意自定义他的作用。
            if (wzAnnotation.withCancelableDialog()){
                mMessageProxy.showCancelableDialog();
            }
//            开启线程请求数据
            service.execute(new Runnable() {
                @Override
                public void run() {
//                    将这个子线程优先级设置为后台
                    Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                    Object result = null;
                    try {
//                        通过代理模式执行被wangzheng注解的方法
                        result = getResult(result);
//                        处理请求结果
                        dealWithResult(result);
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
//              通过动态代理模式，获取数据。也就是执行被WangZheng注解的方法。
                private Object getResult(Object result) throws Throwable {
                    switch (wzAnnotation.methodType()){
                        case normal:
                            result = ProxyBuilder
                                    .callSuper(proxy, method, args);
                            break;
                        case atom:
                            Log.i("zheng", "getResult: "+method.getName());
                            result = ProxyBuilder
                                    .callSuper(proxy, method, args);
                            Log.i("zheng", "getResult: "+result);
                            boolean b = proxy == null;
                            boolean b1 = method == null;
                            boolean b2 = args == null;
                            Log.i("zheng", "getResult: "+b +"  "+b1+ "  "+b2);

//                            执行完之后从集合删除这个方法  不是很明白这是干嘛，但肯定时atom和normal的唯一区别的地方。
                            methodHashSet.remove(method.getName());
                            break;
                        default:
                            break;
                    }
                    return result;
                }

                /**
                 * 用于处理动态代理ProxyBuilder执行方法的返回值
                 * @param result
                 */
                private void dealWithResult(Object result){
//                    请求数据结束将进度框关闭
                    if (wzAnnotation.withDialog() || wzAnnotation.withCancelableDialog()){
//                        mMessageProxy.hideDialog();
                    }
                    if (result!=null && result.getClass() == Message.class){
//                        通过代理模式返回到result就是一个Message（消息队列）。
                        Message msg = (Message) result;
                        switch (msg.arg1){
                            case MessageArg.ARG1.LOGIN:

                                break;
                            case MessageArg.ARG1.TOAST_MESSAGE:
                                break;
                            case MessageArg.ARG1.CALL_BACKMETHOD:
                                if (msg.obj == null//如果obj是空
                                        || !(msg.obj instanceof String)//obj不是String类型
                                        || "".equals(msg.obj.toString().trim())) {//obj是""
//                                    将被WangZheng注解的函数后加上CallBack，固定这个格式是回调的方法。在BaseHandler封装时用反射的方式来调用的。
                                    msg.obj = method.getName() + "CallBack";
                                    Log.i("zheng", "dealWithResult: sdfsd");
                                }
                                break;
                            default:

                                break;
                        }
//                        使用BaseHandler的代理类发送消息。
                        mMessageProxy.sendMessage(msg);
                    }
                }
            });

            return null;
        }else {
            return ProxyBuilder.callSuper(proxy, method, args);
        }
    }
}
