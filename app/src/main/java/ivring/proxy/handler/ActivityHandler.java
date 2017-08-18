package ivring.proxy.handler;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

/**
 * 进一步封装Handler，
 * Created by IVRING on 2017/7/12.
 */

public class ActivityHandler extends BaseHandler<AppCompatActivity>{
    public ActivityHandler(AppCompatActivity appCompatActivity) {
        super(appCompatActivity);
    }

    /**
     * 通过软引用获取Context对象
     * @return
     */
    @Override
    public Context getFTTHContext() {
//        在BaseHandler中创建的软引用中存储的就是APPCompatActivity对象
        return mReWeakReference.get();
    }

    @Override
    public void finish() {
        mReWeakReference.get().finish();
    }

    /**
     * 软引用中当前的Activity对象
     * @return
     */
    @Override
    protected AppCompatActivity checkAvailability() {
        AppCompatActivity appCompatActivity = mReWeakReference.get();
//        如果Activity对象是空或者正在finish就返回空。
        if (appCompatActivity==null||appCompatActivity.isFinishing()){
            return null;
        }
        return appCompatActivity;
    }
}
