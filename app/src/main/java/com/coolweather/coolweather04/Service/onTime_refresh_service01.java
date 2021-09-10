package com.coolweather.coolweather04.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.coolweather.coolweather04.data_fragment;
import com.coolweather.coolweather04.util.HttpUtil;
import com.coolweather.coolweather04.util.ParseUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

//这里的activity之所以有值是因为可以保证这个activity在使用的时候前面的代码已经给它赋了值了
//同理 这里的weather_address也是一样的

public  class onTime_refresh_service01 extends Service {

    //Activity activity02;
    public static boolean isNotVisible;
    private static final String TAG = "标记--service01";

    Handler handler02 = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 400:
                    Log.d(TAG, "handleMessage: 接收到service中的Handler发送过来的消息，准备更新ui");
                    String return_data = (String) msg.obj;
                    ParseUtil.parse_returnWeatherData(return_data, data_fragment.activity);
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: 服务被创建");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String weather_address01 = intent.getStringExtra("weather_address");
        Log.d(TAG, "onStartCommand:接收到的intent的参数： " + weather_address01);
        Log.d(TAG, "onStartCommand: "+data_fragment.activity);

//            Activity_Object activity_object = (Activity_Object) intent.getSerializableExtra("activity_object");
//            activity02 = activity_object.getActivity();
//            Log.d(TAG, "onStartCommand: 接收到的intent的参数：activity02"+activity02);

        if (isNotVisible) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpUtil.sendRequest(weather_address01, new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Log.d(TAG, "onFailure: 已在服务中向服务器器发送请求，但是请求失败");
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            String return_data = response.body().string();
                            Message message = new Message();
                            message.what = 400;
                            message.obj = return_data;
                            handler02.sendMessage(message);
                        }
                    },getApplicationContext());
                }
            }).start();
        }

        //开启定时服务
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //60秒*60次
        int anHour = 60 * 1000 * 2;
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, onTime_refresh_service01.class);
        i.putExtra("weather_address",data_fragment.weather_address);//这里的weather_address是动态变化的 有可能在过程中给赋值了
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        //manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        manager.setAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        Log.d(TAG, "onStartCommand: 定时服务开启！");
        return super.onStartCommand(intent, flags, startId);
    }
}
