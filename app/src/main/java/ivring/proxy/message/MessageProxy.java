package ivring.proxy.message;

import android.os.Message;

import ivring.proxy.handler.BaseHandler;

/**
 * 封装一个MessageProxy对象，实现的其实就是Handler的功能。
 * 标准的静态代理模式，代理BaseHandler。
 * Created by IVRING on 2017/7/12.
 */

public class MessageProxy {
    private BaseHandler mBaseHandler;

    public MessageProxy(BaseHandler mBaseHandler) {
        this.mBaseHandler = mBaseHandler;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 静态代理BaseHandler
    ///////////////////////////////////////////////////////////////////////////
    public Message obtionMessage(int What){
        return mBaseHandler.obtainMessage(What);
    }
    public void sendMessage(Message message){
        mBaseHandler.sendMessage(message);
    }
    public void sendMessageDelay(Message message, long delayMillis) {
        mBaseHandler.sendMessageDelayed(message, delayMillis);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 实现功能的拓展
    ///////////////////////////////////////////////////////////////////////////
    public void showDialog() {
        Message message = mBaseHandler.obtainMessage(MessageArg.WHAT.UI_MESSAGE);
        message.arg1 = MessageArg.ARG1.PROGRESSDIALOG_MESSAGE;
        message.arg2 = 1;
        mBaseHandler.sendMessage(message);
    }

    public void showCancelableDialog() {
        Message message = mBaseHandler.obtainMessage(MessageArg.WHAT.UI_MESSAGE);
        message.arg1 = MessageArg.ARG1.PROGRESSDIALOG_MESSAGE;
        message.arg2 = 2;
        mBaseHandler.sendMessage(message);
    }

    public void hideDialog() {
        Message msg = mBaseHandler.obtainMessage(MessageArg.WHAT.UI_MESSAGE);
        msg.arg1 = MessageArg.ARG1.PROGRESSDIALOG_MESSAGE;
        msg.arg2 = 0;
        mBaseHandler.sendMessage(msg);
    }
}
