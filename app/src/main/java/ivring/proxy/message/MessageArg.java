package ivring.proxy.message;

/**
 * $desc
 * Created by IVRING on 2017/7/12.
 */

public class MessageArg {
    public static class ARG1 {
//        Toast的标志
        public static final int TOAST_MESSAGE = 1;
//        回调的标志
        public static final int CALL_BACKMETHOD = 2;
//        加载框的标志
        public static final int PROGRESSDIALOG_MESSAGE = 3;
//        登录的标志
        public static final int LOGIN = 4;
    }

    public static class ARG2 {
    }

    public static class WHAT {
//        主线程标志
        public static final int UI_MESSAGE = 0;
    }
}
