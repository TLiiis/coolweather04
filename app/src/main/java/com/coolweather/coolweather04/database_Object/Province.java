package com.coolweather.coolweather04.database_Object;

import org.litepal.crud.LitePalSupport;

public class Province extends LitePalSupport {
    private int id;//数据库赋的id

    private int province_code;//省份的代号
    private String province_name;//省份的名字

    public Province() {
    }

    public Province(int province_code, String province_name) {
        this.province_code = province_code;
        this.province_name = province_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public int getProvince_code() {
        return province_code;
    }

    public void setProvince_code(int province_code) {
        this.province_code = province_code;
    }
}
