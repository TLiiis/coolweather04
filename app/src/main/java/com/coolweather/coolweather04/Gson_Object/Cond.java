package com.coolweather.coolweather04.Gson_Object;

public class Cond {
    /*
    "cond":{"code":"100","txt":"晴"}
    */

    private  String code;
    private String txt;
    private String txt_d;

    public Cond(String code, String txt) {
        this.code = code;
        this.txt = txt;
    }

    public String getTxt_d() {
        return txt_d;
    }

    public void setTxt_d(String txt_d) {
        this.txt_d = txt_d;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
