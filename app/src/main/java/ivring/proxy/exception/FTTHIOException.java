package ivring.proxy.exception;

import java.io.IOException;

/**
 * $desc
 * Created by IVRING on 2017/7/12.
 */

public class FTTHIOException extends IOException implements FTTHException {
    public FTTHIOException() {
    }

    public FTTHIOException(String message) {
        super(message);
    }

    public FTTHIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public FTTHIOException(Throwable cause) {
        super(cause);
    }
}
