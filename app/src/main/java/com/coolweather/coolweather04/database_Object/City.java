package com.coolweather.coolweather04.database_Object;

import org.litepal.crud.LitePalSupport;

public class City extends LitePalSupport {

    private int id;//数据库赋的id

    private int city_code;//城市的代号
    private String city_name;//城市的名字

    private int province_code;//城市所对应的省份编码


    public City() {
    }

    public City(int city_code, String city_name) {
        this.city_code = city_code;
        this.city_name = city_name;
    }

    public City(int city_code, String city_name, int province_code) {
        this.city_code = city_code;
        this.city_name = city_name;
        this.province_code = province_code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCity_code() {
        return city_code;
    }

    public void setCity_code(int city_code) {
        this.city_code = city_code;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public int getProvince_code() {
        return province_code;
    }

    public void setProvince_code(int province_code) {
        this.province_code = province_code;
    }
}
