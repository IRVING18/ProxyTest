package ivring.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import java.util.List;

/**
 * $desc
 * Created by IVRING on 2017/7/12.
 */

public class TipToolUtil {
    private static Toast mToast;

    /**
     * 用于自定义Toast时长，位置的方法。
     * @param context
     * @param message
     */
    public static void onCreateToastDialog(Context context, String message) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
//        大概意思是获取Activity栈的list集合
        List<ActivityManager.RunningTaskInfo> infos = am.getRunningTasks(1);
//        栈不为空，栈中第一个Activity就是栈顶不为空，栈顶activity和context的包名是否一致。如果一致说明这是一个activity。
//        也就是说判断context是否为当前栈顶的activity的引用。（个人猜测这么理解）
        if (infos != null
                && infos.get(0) != null
                && context.getPackageName().equals(
                infos.get(0).baseActivity.getPackageName())) {

            if (mToast != null)
                mToast.cancel();
            int duration = Toast.LENGTH_SHORT;
            if (message.length() > 15) {
                duration = Toast.LENGTH_LONG;// 如果字符串比较长，那么显示的时间也长一些。
            }
            mToast = Toast.makeText(context, message, duration);
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.show();
        }
    }
}
