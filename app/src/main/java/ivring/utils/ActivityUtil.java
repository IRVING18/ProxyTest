package ivring.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * $desc
 * Created by IVRING on 2017/7/12.
 */

public class ActivityUtil {

    /**
     * 无参情况的简单跳转
     * @param context
     * @param cls
     */
    public static void Go(Context context,Class<?> cls){
        Intent starter = new Intent(context, cls);
        context.startActivity(starter);
    }
    public static void Go(Context context, Class<?> cls, Bundle bundle){
        Intent starter = new Intent(context, cls);
        if (null != bundle)
        starter.putExtras(bundle);

        context.startActivity(starter);

    }
}
