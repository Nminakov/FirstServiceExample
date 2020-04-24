package com.example.firstserviceexample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BigTaskService extends Service {
    public static final String MARKETS_URL = "https://api.bittrex.com/api/v1.1/public/getmarketsummaries";
    public BigTaskService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bigTask();
        return START_STICKY;
    }
    void bigTask(){
        String result = "error";
        try {
            result = getData(MARKETS_URL);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            Toast.makeText(this,result, Toast.LENGTH_LONG).show();
        }
        stopSelf();
    }

    public String getData(String baseURL) throws IOException {
        URL url = new URL(baseURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line = "";
        StringBuilder builder = new StringBuilder();
        while ((line = reader.readLine())!=null){
            builder.append(line);
        }
        return builder.toString();
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
