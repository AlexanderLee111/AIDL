package com.alee.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.alee.aidl.IMessage;


public class MsgService extends Service {

    private String mMsg;

    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    private IMessage.Stub stub = new IMessage.Stub() {

        @Override
        public void setMsg(String msg) throws RemoteException {
            mMsg = msg;
        }

        @Override
        public String getMsg() throws RemoteException {
            return mMsg + "from service";
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    };
}
