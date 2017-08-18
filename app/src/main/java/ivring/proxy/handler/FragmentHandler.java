package ivring.proxy.handler;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * $desc
 * Created by IVRING on 2017/7/12.
 */

public class FragmentHandler extends BaseHandler<Fragment> {

    public FragmentHandler(Fragment fragment) {
        super(fragment);
    }

    @Override
    protected Fragment checkAvailability() {
        Fragment fragment = mReWeakReference.get();
        if (fragment == null || fragment.isHidden()) {
            return null;
        }
        return fragment;
    }

    @Override
    public Context getFTTHContext() {
        return mReWeakReference.get().getContext();
    }

    @Override
    public void finish() {
//        mReWeakReference.get().getActivity().finish();
    }
}

