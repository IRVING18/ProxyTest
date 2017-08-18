package ivring;

import android.app.Application;

import ivring.proxy.ControlFactory;

/**
 * $desc
 * Created by IVRING on 2017/7/14.
 */

public class MYApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initProxyDexCache();
    }

    /**
     * 用于初始化，ProxyBuilder需要的参数。
     */
    private void initProxyDexCache() {
        ControlFactory.init(getApplicationContext());
    }
}
