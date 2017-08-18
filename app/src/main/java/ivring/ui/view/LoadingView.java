package ivring.ui.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import ivring.proxytest.R;


/**
 * $desc
 * Created by IVRING on 2017/7/12.
 */

public class LoadingView extends DialogFragment {
    Animation operatingAnim;

    Dialog mDialog;

    View mouse;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (mDialog == null) {
            mDialog = new Dialog(getActivity(), R.style.loading_dialog);
            mDialog.setContentView(R.layout.view_loading_main);
            mDialog.setCanceledOnTouchOutside(false); // 设置false！
            mDialog.setCancelable(true);
            mDialog.getWindow().setGravity(Gravity.CENTER);

            operatingAnim = new RotateAnimation(360f, 0f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            operatingAnim.setRepeatCount(Animation.INFINITE);
            operatingAnim.setDuration(2000);

            View view = mDialog.getWindow().getDecorView();

            mouse = view.findViewById(R.id.mouse);

        }
        return mDialog;
    }


    @Override
    public void onResume() {
        super.onResume();
        mouse.setAnimation(operatingAnim);
    }


    @Override
    public void onPause() {
        super.onPause();
        operatingAnim.reset();
        mouse.clearAnimation();
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mDialog = null;
        System.gc();
    }
}