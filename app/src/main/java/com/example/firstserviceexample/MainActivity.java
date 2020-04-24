package com.example.firstserviceexample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
ServiceConnection serviceConnection;
MyService myService;
ProgressBar progressBar;
public static  final String CHANNEL_ID = "androidx.appcompat.app.AppCompatActivity.my_channel";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                    new NotificationChannel(CHANNEL_ID, "androidx.appcompat.app.AppCompatActivity.my_channel", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
        }


        progressBar = findViewById(R.id.progressBar);
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder iBinder) {
                myService = ((MyService.MyBinder) iBinder).service;
                progressBar.setEnabled(true);
                progressBar.setProgress((int) (myService.getProgress()*100));
                progressBar.post(new Runnable() {
                    @Override
                    public void run() {
                        if (myService != null){
                            progressBar.setProgress((int) (myService.getProgress()*100));
                            progressBar.postDelayed(this, 3000);
                        } else{
                            progressBar.setEnabled(false);
                            progressBar.setProgress(0);
                        }
                    }
                });
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                myService = null;
            }
        };
    }




    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start:
                startService(new Intent(this, MyRemoteService.class));
                break;
            case R.id.btn_stop:
                stopService(new Intent(this, MyRemoteService.class));
                break;
            case R.id.btn_bind:
                bindService(new Intent(this, MyService.class), serviceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.iv_pause:
                if(myService != null){
                    myService.pause();
                }
                break;
            case R.id.iv_play:
                if (myService != null){
                    myService.play();
                }
                break;
            case R.id.btn_bigtask:
                startService(new Intent(this,BigTaskService.class));
                break;
        }
    }
}
