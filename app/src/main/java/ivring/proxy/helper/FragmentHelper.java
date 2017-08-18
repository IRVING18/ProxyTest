package ivring.proxy.helper;

import ivring.proxy.control.BaseControl;
import ivring.proxy.handler.FragmentHandler;
import ivring.ui.fragment.BaseFragment;

/**
 * $desc
 * Created by IVRING on 2017/7/13.
 */

public class FragmentHelper<T extends BaseControl,R extends BaseFragment> extends BaseWZFragmentHelper<T,R> {
    public FragmentHelper(R mReferenceObj) {
//        通过R的对象来创建Handler对象。其实R类型的对象就是Activity或者Fragment对象
        super(mReferenceObj, new FragmentHandler(mReferenceObj));
    }

    @Override
    public void initHelper() {
        super.initHelper();
    }
}
