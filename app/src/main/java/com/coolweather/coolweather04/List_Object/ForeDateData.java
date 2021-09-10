package com.coolweather.coolweather04.List_Object;

import android.widget.TextView;

public class ForeDateData {
//    TextView fore_day02 = activity.findViewById(R.id.fore_date02);
//    TextView fore_day02_txt = activity.findViewById(R.id.fore_date02_txt);
//    TextView fore_day02_max = activity.findViewById(R.id.fore_date02_max);
//    TextView fore_day02_min = activity.findViewById(R.id.fore_date02_min);

    private TextView fore_day;
    private TextView fore_day_txt;
    private TextView fore_day_max;
    private TextView fore_day_min;

    public ForeDateData(TextView fore_day, TextView fore_day_txt, TextView fore_day_max, TextView fore_day_min) {
        this.fore_day = fore_day;
        this.fore_day_txt = fore_day_txt;
        this.fore_day_max = fore_day_max;
        this.fore_day_min = fore_day_min;
    }

    public TextView getFore_day() {
        return fore_day;
    }

    public void setFore_day(TextView fore_day) {
        this.fore_day = fore_day;
    }

    public TextView getFore_day_txt() {
        return fore_day_txt;
    }

    public void setFore_day_txt(TextView fore_day_txt) {
        this.fore_day_txt = fore_day_txt;
    }

    public TextView getFore_day_max() {
        return fore_day_max;
    }

    public void setFore_day_max(TextView fore_day_max) {
        this.fore_day_max = fore_day_max;
    }

    public TextView getFore_day_min() {
        return fore_day_min;
    }

    public void setFore_day_min(TextView fore_day_min) {
        this.fore_day_min = fore_day_min;
    }
}
