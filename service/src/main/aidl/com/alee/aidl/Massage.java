package com.alee.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class Massage implements Parcelable {

    private String msg;

    public Massage(){
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static final Creator<Massage> CREATOR = new Creator<Massage>() {
        @Override
        public Massage createFromParcel(Parcel in) {
            Massage msg=new Massage();
            msg.setMsg(in.readString());
            return msg;
        }

        @Override
        public Massage[] newArray(int size) {
            return new Massage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(msg);
    }
}
