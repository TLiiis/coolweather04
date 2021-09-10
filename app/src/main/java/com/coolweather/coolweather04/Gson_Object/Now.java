package com.coolweather.coolweather04.Gson_Object;

public class Now {
    /*
    "now":{
               "cloud":"2",
               "cond_code":"100",
               "cond_txt":"晴",
               "fl":"21",
               "hum":"21",
               "pcpn":"0.0",
               "pres":"1014",
               "tmp":"23",
               "vis":"16",
               "wind_deg":"344",
               "wind_dir":"西北风",
               "wind_sc":"1",
               "wind_spd":"5",
               "cond":{"code":"100","txt":"晴"}
              },
    */

    private String wind_deg ;//风级别
    private String wind_dir ;//风向
    private String vis;//能见度
    private Cond cond;//状态
    private String tmp;

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getWind_deg() {
        return wind_deg;
    }

    public void setWind_deg(String wind_deg) {
        this.wind_deg = wind_deg;
    }

    public String getWind_dir() {
        return wind_dir;
    }

    public void setWind_dir(String wind_dir) {
        this.wind_dir = wind_dir;
    }

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    public Cond getCond() {
        return cond;
    }

    public void setCond(Cond cond) {
        this.cond = cond;
    }
}
