package com.coolweather.coolweather04.util;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {

    private static final String TAG = "标记--HttpUtil";
    public static void sendRequest(String address, Callback callback, Context context){
        boolean isConnected = CheckNetworkUtil.isNetWorkConnected(context);
        if (isConnected){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "run: 开始索要数据");
                    Log.d(TAG, "run: "+address);
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().
                            url(address).
                            build();
                    client.newCall(request).enqueue(callback);
                    //索要数据完毕 响应对象放到了callback参数里面
                    Log.d(TAG, "run: 索要数据完毕");
                }
            }).start();
        }else {
            Looper.prepare();
            Toast.makeText(context, "请打开网络连接获取以获取网络连接", Toast.LENGTH_SHORT).show();
        }
    }
}
