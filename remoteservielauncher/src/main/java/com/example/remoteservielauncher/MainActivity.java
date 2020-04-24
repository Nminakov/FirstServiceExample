package com.example.remoteservielauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    ServiceConnection serviceConnection;
    Messenger messenger;
    private final static  int MSG_PLAY = 1;
    private final static  int MSG_PAUSE = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                messenger = new Messenger(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                messenger = null;
            }
        };
    }

    public void onClick(View view) {

        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.firstserviceexample", "com.example.firstserviceexample.MyRemoteService"));

        switch (view.getId()){
            case R.id.btn_start:
                startService(intent);
                break;
            case R.id.btn_stop:
                stopService(intent);
                break;
            case R.id.btn_bind:
                bindService(intent,serviceConnection,BIND_AUTO_CREATE);
                break;
            case R.id.iv_pause:
                if (messenger != null){
                    Message msg = new Message();
                    msg.what = MSG_PAUSE;
                    try {
                        messenger.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.iv_play:
                if (messenger != null){
                    Message msg = new Message();
                    msg.what = MSG_PLAY;
                    try {
                        messenger.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
