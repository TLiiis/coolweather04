package com.coolweather.coolweather04.Gson_Object;

import java.util.List;

public class WeatherInfo {
    //这个list里面就一个大 { } ，里面分别是
    // "basic" : { }
    // "update" :{ }
    // "status": { }
    // "now":{ }
    // "aqi" : { }
    // "suggestion" : { }
    // "daily_forecast":[ ]
    // "msg": " "

    private Basic basic;
    private Now now;
    private Aqi aqi;
    private Suggestion suggestion;
    private String msg;
    private List<Forecast> daily_forecast ;


    public List<Forecast> getDaily_forecast() {
        return daily_forecast;
    }

    public void setDaily_forecast(List<Forecast> daily_forecast) {
        this.daily_forecast = daily_forecast;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Basic getBasic() {
        return basic;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    public Now getNow() {
        return now;
    }

    public void setNow(Now now) {
        this.now = now;
    }

    public Aqi getAqi() {
        return aqi;
    }

    public void setAqi(Aqi aqi) {
        this.aqi = aqi;
    }

    public Suggestion getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;
    }

    //    public Daily_forecast getDaily_forecast() {
//        return daily_forecast;
//    }

}
