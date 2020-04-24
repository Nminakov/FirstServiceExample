package com.example.dzservice;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class MyIntentService extends IntentService {





     static final String EXTRA_URL = "com.example.dzservice.extra.PARAM2";

    public MyIntentService() {
        super("MyIntentService");
    }






    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String surl = intent.getStringExtra(EXTRA_URL);
            try {
                Log.d("service", "start loading..." );
                URL url = new URL(surl);
                File file = new File(Environment.getExternalStorageDirectory() + "/x.zip");
                if (!file.exists()){
                    file.createNewFile();
                }
                URLConnection connection = url.openConnection();
                BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
                byte[] buffer = new byte[2048];
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                int length = 0;
                while ((length = bis.read(buffer)) !=-1){
                    fileOutputStream.write(buffer, 0, length);
                    Log.d("service", "progress..." + length + "bytes");
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                bis.close();
            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



}
