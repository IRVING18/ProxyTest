package ivring.proxy.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ivring.proxy.message.MessageArg;
import ivring.ui.view.LoadingView;
import ivring.utils.LogUtil;
import ivring.utils.TipToolUtil;

import static java.security.AccessController.getContext;

/**
 * 封装Handler，目的是为了处理MessageArg中那些参数对应的功能。
 * 原理：封装BaseHandler之后，在之后所有需要四个功能时直接就用Handler发送这些消息就能实现，而代码中我们是通过代理类MessageProxy类实现消息发送的。
 * 1、BaseHandler属于基类，然后ActivityHandler继承它，进而到ActivityHelper中。而Helper又会在BaseActivity中作为参数存在，
 * 所以继承BaseActivity和Fragment的类都可以实现这些功能。
 * 2、本类只是用来处理如下四个标志的功能，以及拓展的进度框的操作。而真正应用发送功能的是代理类MessageProxy。
 *    //        Toast的标志
 *    public static final int TOAST_MESSAGE = 1;
 *    //        回调的标志
 *    public static final int CALL_BACKMETHOD = 2;
 *    //        加载框的标志
 *    public static final int PROGRESSDIALOG_MESSAGE = 3;
 *    //        登录的标志
 *    public static final int LOGIN = 4;
 *    Created by IVRING on 2017/7/12.
 */
//Re传进来的是Activity或者Fragment
public abstract class BaseHandler<Re> extends Handler implements Icontext {
    private static final String TAG = "BaseHandler";
    //    创建一个软引用，不知道干嘛用还目前
// TODO: 2017/7/12
    protected WeakReference<Re> mReWeakReference;
    //    创建一个加载框DialogFragment
    private LoadingView mLoadingDialog;

    public BaseHandler(Re re) {
//        将AppCompatActivity或者Fragment对象存到这个软引用中
        mReWeakReference = new WeakReference<Re>(re);
    }

    //    创建一个抽象方法获取Re对象，在AcitivityHandler和FragmentHander中对其重写了。
    protected abstract Re checkAvailability();

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
//        通过抽象方法获取Re对象
        Re re = checkAvailability();
        switch (msg.arg1) {
//            传过来Toast标志
            case MessageArg.ARG1.TOAST_MESSAGE:
                if (msg.obj instanceof String) {
//                    这个其实就是Toast，只是封装了一下，改变了Toast的位置和显示时间
                    TipToolUtil.onCreateToastDialog(getFTTHContext(), msg.obj + "");
                } else if (msg.obj instanceof Integer) {//资源文件string
                    TipToolUtil.onCreateToastDialog(getFTTHContext(), getFTTHContext().getString((Integer) msg.obj));
                } else {
                    TipToolUtil.onCreateToastDialog(getFTTHContext(), msg.obj + "");
                }
//                弹出Toast的同时隐藏进度框（如果显示的话）
                hideDialog();
                break;
//            用于处理callback方法的。
            case MessageArg.ARG1.CALL_BACKMETHOD:
                invokeMethod(re, msg);
                break;
            case MessageArg.ARG1.PROGRESSDIALOG_MESSAGE:
                switch (msg.arg2) {
                    case 0:
                        hideDialog();
                        break;
                    case 1:
                        showDialog(false, (AppCompatActivity) re);
                        break;
                    case 2:
                        showDialog(true, (AppCompatActivity) re);
                        break;
                    default:
                        hideDialog();
                }
                break;
            case MessageArg.ARG1.LOGIN:
//                finish();
//                ActivityUtil.Go(getFTTHContext(), LoginActivity.class);
                break;
        }
    }

    /**
     * 用于处理回调方法
     *
     * @param re
     * @param msg
     */
    private void invokeMethod(Re re, Message msg) {
        if (!(msg.obj instanceof String)) {
            LogUtil.e(TAG, "nvokeMethod method err!");
//            TipToolUtil.onCreateToastDialog(getFTTHContext(), "invokeMethod method err!");
            return;
        }
        Method method;

        try {
            try {
//                http://www.jianshu.com/p/f67182a482eb 反射基础可参考
//                通过反射获取msg.obj传过来的这个方法。
                Log.i("zheng", "invokeMethod: "+msg.obj);
                method = re.getClass().getMethod(msg.obj + "", new Class[]{Bundle.class});
            } catch (NoSuchMethodException e) {
                invokeNoArgMethod(re, msg);
                return;
            }

            if (method != null) {
//                设置是否允许访问，true就是不管private还是啥都能访问。
                method.setAccessible(true);
//                invoke方法就是在操作获取的这个方法。
                method.invoke(re, msg.getData());
            }

        } catch (NoSuchMethodException e) {
            LogUtil.e(TAG, "NoSuchMethodException");
        } catch (InvocationTargetException e) {
            LogUtil.e(TAG, e.toString());
        } catch (IllegalAccessException e) {
            LogUtil.e(TAG, "IllegalAccessException");
        }
    }

    /**
     * invokeMethod NoSuchMethodException
     * 处理异常
     *
     * @param re
     * @param msg
     */
    private void invokeNoArgMethod(Re re, Message msg) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = re.getClass().getMethod(msg.obj + "");
        if (method == null) {
            throw new NoSuchMethodException(msg.obj + "");
        }
        method.setAccessible(true);
        method.invoke(re);
    }

    /**
     * 用于隐藏加载框
     */
    private void hideDialog() {
        if (getContext() != null && mLoadingDialog != null && !mLoadingDialog.isHidden()) {
            mLoadingDialog.dismiss();
        }
    }

    /**
     * 显示dialog
     *
     * @param touchOutside 是否可点击外侧隐藏对话框
     */
    private void showDialog(boolean touchOutside, AppCompatActivity activity) {
        if (getFTTHContext() != null) {
            hideDialog();
            if (mLoadingDialog == null) {
                mLoadingDialog = new LoadingView();

            }
            Log.i("showload", "showLoad: basehandler");

            mLoadingDialog.show(activity.getSupportFragmentManager(), "");
        }
    }
}
