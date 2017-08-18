package ivring.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ivring.proxy.control.SingControl;
import ivring.proxytest.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity<SingControl> {

    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button = (Button) findViewById(R.id.email_sign_in_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mControl.getChallengeCode();
            }
        });
    }


    public void getChallengeCodeCallBack(){
        Toast.makeText(this, "4564654", Toast.LENGTH_SHORT).show();
        Log.i("zheng", "getChallengeCodeCallBack: 32423423");
    }

}

