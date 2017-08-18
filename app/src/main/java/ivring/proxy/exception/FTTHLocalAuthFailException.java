package ivring.proxy.exception;

/**
 * $desc
 * Created by IVRING on 2017/7/12.
 */


public class FTTHLocalAuthFailException extends Exception implements FTTHException {

    public FTTHLocalAuthFailException(String message) {
        super(message);
    }
}
