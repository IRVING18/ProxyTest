package ivring.proxy.exception;

import org.json.JSONException;

/**
 * JSON解析异常
 * Created by IVRING on 2017/7/12.
 */

public class FTTHJSONException extends JSONException implements FTTHException {

    public FTTHJSONException(String s) {
        super(s);
    }


}
