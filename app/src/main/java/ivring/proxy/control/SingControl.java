package ivring.proxy.control;

import ivring.proxy.annotation.WangZheng;
import ivring.proxy.message.MessageProxy;

/**
 * $desc
 * Created by IVRING on 2017/7/12.
 */

public class SingControl extends BaseControl {
    private static final String TAG = "SingleControl";

    public SingControl(MessageProxy mMessageProxy) {
        super(mMessageProxy);
    }
    @WangZheng
    public void getChallengeCode(){
//            LoginChallengeCodeBean challengeCodeBean = new LoginChallengeCodeBean();
//            mModel.put(1, challengeCodeBean);
//            Thread.sleep(5000);
        try {
            sendMessage("getChallengeCodeCallBack");
        } catch (Exception e) {
            dealWithException(e);
        }
    }
}
