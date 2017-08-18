package ivring.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import ivring.proxy.ModelMap;
import ivring.proxy.control.BaseControl;
import ivring.proxy.helper.FragmentHelper;
import ivring.proxy.message.MessageProxy;
import ivring.ui.view.LoadingView;

/**
 * $desc
 * Created by IVRING on 2017/7/13.
 */

public class BaseFragment<T extends BaseControl> extends Fragment {
    private static final String TAG = "BaseFragment";
    protected T mControl;
    protected MessageProxy mMessageProxy;
    protected ModelMap mModelMap;
    private FragmentHelper mFragmentHelper;

    private LoadingView mLoadingView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mFragmentHelper == null) {
            initHelper();
        }

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mFragmentHelper == null) {
            initHelper();
        }
    }
    private void initHelper() {
        mFragmentHelper = new FragmentHelper(this);
        mFragmentHelper.initHelper();
        initVar();
    }

    private void initVar() {
        mModelMap = mFragmentHelper.getModel();
        mMessageProxy = mFragmentHelper.getMessageProxy();
        mControl = (T) mFragmentHelper.getControl();
    }


    public void showLoad() {
        if (mLoadingView == null) {
            mLoadingView = new LoadingView();
        }
        mLoadingView.show(getFragmentManager(), "");
    }

    public void dismissLoad() {
        if (mLoadingView != null) {
            mLoadingView.dismiss();
        }
    }

}
