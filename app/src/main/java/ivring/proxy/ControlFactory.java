package ivring.proxy;

import android.content.Context;

import java.io.File;

import ivring.proxy.callback.Interceptor;
import ivring.proxy.callback.WZMethodAtomInterceptor;
import ivring.proxy.filter.WangZhengMethodFilter;
import ivring.proxy.message.MessageProxy;
import ivring.utils.AppInfoUtil;
import ivring.utils.FileUtil;

/**
 * 这个工厂类，实际就是用来动态代理模式生成BaseControl子类的工厂。
 * 动态代理使用的是ProxyBuilder的框架。
 *
 * Created by IVRING on 2017/7/12.
 */

public class ControlFactory {
    private static File mCacheDir;

    /**
     * 用于初始化ProxyBuilder需要的参数dexcache，就是一个文件路径。在Application中调用的。
     * @param context
     */
    public static void init(Context context) {
        int versionCode = AppInfoUtil.getVersionCode(context);
        for (int i = 0; i < versionCode; i++) {
            String dirName = i + "";
            File file = context.getDir(dirName, Context.MODE_PRIVATE);
            deleteDirection(file);
        }
        mCacheDir = context.getDir(versionCode + "", context.MODE_PRIVATE);
    }

    private static void deleteDirection(File file) {
        if (file.exists()) {
            FileUtil.RecursionDeleteFile(file);
        }
    }

    public static <T> T getControlInstance(Class<T> tClass, MessageProxy messageProxy) {
        Enhancer<T> enhancer;
        if (messageProxy != null) {
            enhancer = new Enhancer<T>(tClass,
                    new Class[]{messageProxy.getClass()},
                    new Object[]{messageProxy},
                    mCacheDir
            );
        } else {
            enhancer = new Enhancer<T>(tClass,mCacheDir );
        }
        enhancer.setCallBacks(new Interceptor[]{new WZMethodAtomInterceptor(messageProxy)});
        enhancer.setFilter(new WangZhengMethodFilter());
        return enhancer.create();
    }
}
