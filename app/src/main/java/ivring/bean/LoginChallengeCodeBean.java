package ivring.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * $desc
 * Created by IVRING on 2017/7/12.
 */

public class LoginChallengeCodeBean implements Parcelable{
    private String CmdType;
    private String SequenceId;
    private String Status;
    private String MAC;
    private String Vendor;
    private String Model;
    private String PhoneAppURL;
    private String ChallengeCode;
    //    2017/5/3新加参数，APPToken获取时使用这个
    private String ChallengeCode1;

    public String getChallengeCode1() {
        return ChallengeCode1;
    }

    public void setChallengeCode1(String challengeCode1) {
        ChallengeCode1 = challengeCode1;
    }

    public void setCmdType(String CmdType) {
        this.CmdType = CmdType;
    }

    public void setSequenceId(String SequenceId) {
        this.SequenceId = SequenceId;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    public void setVendor(String Vendor) {
        this.Vendor = Vendor;
    }

    public void setModel(String Model) {
        this.Model = Model;
    }

    public void setPhoneAppURL(String PhoneAppURL) {
        this.PhoneAppURL = PhoneAppURL;
    }

    public void setChallengeCode(String ChallengeCode) {
        this.ChallengeCode = ChallengeCode;
    }

    public String getCmdType() {
        return CmdType;
    }

    public String getSequenceId() {
        return SequenceId;
    }

    public String getStatus() {
        return Status;
    }

    public String getMAC() {
        return MAC;
    }

    public String getVendor() {
        return Vendor;
    }

    public String getModel() {
        return Model;
    }

    public String getPhoneAppURL() {
        return PhoneAppURL;
    }

    public String getChallengeCode() {
        return ChallengeCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.CmdType);
        dest.writeString(this.SequenceId);
        dest.writeString(this.Status);
        dest.writeString(this.MAC);
        dest.writeString(this.Vendor);
        dest.writeString(this.Model);
        dest.writeString(this.PhoneAppURL);
        dest.writeString(this.ChallengeCode);
    }

    public LoginChallengeCodeBean() {
    }

    protected LoginChallengeCodeBean(Parcel in) {
        this.CmdType = in.readString();
        this.SequenceId = in.readString();
        this.Status = in.readString();
        this.MAC = in.readString();
        this.Vendor = in.readString();
        this.Model = in.readString();
        this.PhoneAppURL = in.readString();
        this.ChallengeCode = in.readString();
    }

    public static final Parcelable.Creator<LoginChallengeCodeBean> CREATOR = new Parcelable.Creator<LoginChallengeCodeBean>() {
        public LoginChallengeCodeBean createFromParcel(Parcel source) {
            return new LoginChallengeCodeBean(source);
        }

        public LoginChallengeCodeBean[] newArray(int size) {
            return new LoginChallengeCodeBean[size];
        }
    };
}
