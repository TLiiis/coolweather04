package com.coolweather.coolweather04.database_Object;

import org.litepal.crud.LitePalSupport;

public class County extends LitePalSupport {

    //县级id 数据库自动赋予的
    private int County_id;

    //服务器返回数据对应的数据
    private int County_code;
    private String County_name;
    private String weather_id;

    //这个县级对应的城市编号
    private int City_code;

    public County(int county_code, String county_name, String weather_id, int city_code) {
        County_code = county_code;
        County_name = county_name;
        this.weather_id = weather_id;
        City_code = city_code;
    }

    public String getWeather_id() {
        return weather_id;
    }

    public void setWeather_id(String weather_id) {
        this.weather_id = weather_id;
    }

    public int getCounty_id() {
        return County_id;
    }

    public void setCounty_id(int county_id) {
        County_id = county_id;
    }

    public int getCounty_code() {
        return County_code;
    }

    public void setCounty_code(int county_code) {
        County_code = county_code;
    }

    public String getCounty_name() {
        return County_name;
    }

    public void setCounty_name(String county_name) {
        County_name = county_name;
    }

    public int getCity_code() {
        return City_code;
    }

    public void setCity_code(int city_code) {
        City_code = city_code;
    }
}
