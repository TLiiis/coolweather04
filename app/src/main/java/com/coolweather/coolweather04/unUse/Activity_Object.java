package com.coolweather.coolweather04.unUse;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Activity_Object implements Serializable {

    private Activity activity;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
