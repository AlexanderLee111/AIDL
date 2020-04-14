package com.alee.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alee.aidl.IMessage;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etMsg;
    TextView tvMsg;
    Button btnMsgSend;
    Button btnMsgGet;

    IMessage iMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etMsg = findViewById(R.id.et_msg);
        tvMsg = findViewById(R.id.tv_msg);
        btnMsgSend = findViewById(R.id.btn_msg_send);
        btnMsgGet = findViewById(R.id.btn_msg_get);
        btnMsgSend.setOnClickListener(this);
        btnMsgGet.setOnClickListener(this);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iMessage = IMessage.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iMessage = null;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.alee.service", "com.alee.service.MsgService"));
        bindService(intent, connection, BIND_AUTO_CREATE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_msg_send:
                String name = etMsg.getText().toString().trim();
                if (iMessage == null) {
                    Toast.makeText(MainActivity.this, "null message", Toast.LENGTH_SHORT).show();
                }
                try {
                    iMessage.setMsg(name);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_msg_get:
                try {
                    tvMsg.setText(iMessage.getMsg());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑服务，回收资源
        unbindService(connection);
    }
}
