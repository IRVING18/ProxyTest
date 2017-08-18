package ivring.proxy.exception;

/**
 * 网络错误
 * Created by IVRING on 2017/7/12.
 */


public class FTTHNetworkException extends Exception implements FTTHException{

    public FTTHNetworkException(String detailMessage) {
        super(detailMessage);
    }

    public FTTHNetworkException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public FTTHNetworkException(Throwable throwable) {
        super(throwable);
    }
}
