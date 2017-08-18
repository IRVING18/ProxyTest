package ivring.proxy.helper;

import ivring.proxy.control.BaseControl;
import ivring.proxy.handler.ActivityHandler;
import ivring.ui.activity.BaseActivity;

/**
 * 这个类其实并没有做什么操作，只是提供了一个自定义Handler对象。
 * Created by IVRING on 2017/7/13.
 */

public class ActivityHelper<T extends BaseControl,R extends BaseActivity> extends BaseWZActivityHelper<T,R> {
    public ActivityHelper(R mReferenceObj) {
//        通过R的对象来创建Handler对象。其实R类型的对象就是Activity或者Fragment对象
        super(mReferenceObj, new ActivityHandler(mReferenceObj));
    }

    @Override
    public void initHelper() {
        super.initHelper();
    }
}
