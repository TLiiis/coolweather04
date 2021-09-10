package com.coolweather.coolweather04.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckNetworkUtil {

    public static boolean isNetWorkConnected(Context context){
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (networkInfo!=null){
            return networkInfo.isAvailable();
        }
        return false;
    }

}
