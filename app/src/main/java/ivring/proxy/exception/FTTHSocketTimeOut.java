package ivring.proxy.exception;

import java.net.SocketTimeoutException;

/**
 * Socket超时
 * Created by IVRING on 2017/7/12.
 */


public class FTTHSocketTimeOut extends SocketTimeoutException implements FTTHException {
    public FTTHSocketTimeOut(String msg) {
        super(msg);
    }


}
