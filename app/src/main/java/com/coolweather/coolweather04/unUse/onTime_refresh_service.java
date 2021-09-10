package com.coolweather.coolweather04.unUse;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.coolweather.coolweather04.util.HttpUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class onTime_refresh_service extends Service {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String weather_address = intent.getStringExtra("weather_address");
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtil.sendRequest(weather_address, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String return_data = response.body().string();
                        //好像是获取不到活动对象的 只能使用内部类的方法了
                        //data_fragment.parse_returnWeatherData(return_data,传入一个活动对象);
                    }
                },getApplicationContext());
            }
        }).start();

        //开启定时服务
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int anHour = 60*60*1000;
        long triggerAtTime = SystemClock.elapsedRealtime()+anHour;
        Intent i = new Intent(this,onTime_refresh_service.class);
        PendingIntent pi = PendingIntent.getService(this,0,i,0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
