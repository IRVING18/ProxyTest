package ivring.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import ivring.proxy.ModelMap;
import ivring.proxy.control.BaseControl;
import ivring.proxy.helper.ActivityHelper;
import ivring.proxy.message.MessageProxy;
import ivring.ui.view.LoadingView;

/**
 * $desc
 * Created by IVRING on 2017/7/13.
 */

public class BaseActivity<T extends BaseControl> extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    protected T mControl;
    protected MessageProxy mMessageProxy;
    protected ModelMap mModelMap;
    private ActivityHelper mActivityHelper;
    private LoadingView mLoadingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        初始化Helper对象。
        initHelper();
    }

    /**
     * 初始化Helper对象。
     */
    private void initHelper() {
        mActivityHelper = new ActivityHelper(this);
        mActivityHelper.initHelper();
//        初始化存储数据集合，Handler代理类，control对象。
        initVar();
    }

    /**
     * 初始化存储数据集合，Handler代理类，control对象。
     */
    private void initVar() {
//        获取集合对象，是用来存储被WangZheng注解的函数执行后得到的值的。
//          因为在WZMethodAtomInterceptor中对所有被注解的函数都放到了子线程中执行了，所以这样传值更合理。
        mModelMap = mActivityHelper.getModel();
//        这个其实就是Handler，他是BaseHandler的静态代理类。
        mMessageProxy = mActivityHelper.getMessageProxy();
//        获取Control对象。
        mControl = (T) mActivityHelper.getControl();
    }

    public void showLoad() {
        if (mLoadingView == null) {
            mLoadingView = new LoadingView();
        }
        mLoadingView.show(getSupportFragmentManager(), "");
    }

    public void dismissLoad() {
        if (mLoadingView != null) {
            mLoadingView.dismiss();
        }
    }
}
