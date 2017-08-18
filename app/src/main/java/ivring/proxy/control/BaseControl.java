package ivring.proxy.control;

import android.os.Message;
import android.util.Log;

import java.io.InterruptedIOException;
import java.net.UnknownHostException;

import ivring.proxy.ModelMap;
import ivring.proxy.exception.FTTHException;
import ivring.proxy.exception.FTTHIOException;
import ivring.proxy.exception.FTTHJSONException;
import ivring.proxy.exception.FTTHLocalAuthFailException;
import ivring.proxy.exception.FTTHSocketTimeOut;
import ivring.proxy.message.MessageArg;
import ivring.proxy.message.MessageProxy;

/**
 * $desc
 * Created by IVRING on 2017/7/12.
 */

public class BaseControl {
    public static final String TAG = "BaseControl";
//    创建一个集合，用于存储被注解的方法执行之后的得到的数据。
    protected ModelMap mModel;
//    创建一个Handler的代理类，其实就是Handler
    protected MessageProxy mMessageProxy;

    public BaseControl(MessageProxy mMessageProxy) {
        this.mMessageProxy = mMessageProxy;
    }
    public void setModelMap(ModelMap modelMap){
        this.mModel = modelMap;
    }
    protected void dealWithException(Exception e) {

        if (e instanceof FTTHJSONException) {
            sendToastMessage("数据异常");
        } else if (e instanceof FTTHIOException) {
            sendToastMessage("无法连接网络");
        } else if (e instanceof FTTHSocketTimeOut) {
            sendToastMessage("连接超时");
        } else if (e instanceof InterruptedIOException) {
            sendToastMessage("连接断开，请查看网络连接，或稍后重试");
        } else if (e instanceof UnknownHostException) {
            sendToastMessage("无法连接到服务器");
        } else if (e instanceof FTTHLocalAuthFailException) {
            sendToastMessage("token失效，请重新登录");
            sendToLogin();
        } else if (e instanceof FTTHException) {
            sendToastMessage(e.getMessage());
        }
    }

    /**
     * 跳转到登录界面
     */
    private void sendToLogin() {
        Message msg = mMessageProxy.obtionMessage(MessageArg.WHAT.UI_MESSAGE);
        msg.arg1 = MessageArg.ARG1.LOGIN;
        mMessageProxy.sendMessage(msg);
    }    /**
     * 直接发送toast消息
     *
     * @param toast
     */
    protected void sendToastMessage(String toast) {
        Message msg = mMessageProxy.obtionMessage(MessageArg.WHAT.UI_MESSAGE);
        msg.arg1 = MessageArg.ARG1.TOAST_MESSAGE;
        msg.obj = toast;
        mMessageProxy.sendMessage(msg);
    }

    /**
     * 这个方法就是真正实现Activity或者Fragment的后缀callBack的方法的调用。
     *
     * @param method
     */
    protected void sendMessage(String method){
//        通过Handler代理类获取消息对象
        Message message = mMessageProxy.obtionMessage(MessageArg.WHAT.UI_MESSAGE);
//        传递arg1参数，CALL_BACKMETHOD在BaseHandler里是用来处理callback方法的。通过反射的方法获取到发起指令的Activity或者Fragment中的后缀callBack的方法，并执行它
        message.arg1 = MessageArg.ARG1.CALL_BACKMETHOD;
//        传递方法名
        message.obj = method;
//        通过Handler代理类发送消息。
        mMessageProxy.sendMessage(message);
        Log.i("zheng", "sendMessage: "+message);
    }
}
