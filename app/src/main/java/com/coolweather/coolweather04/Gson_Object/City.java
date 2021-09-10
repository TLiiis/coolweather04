package com.coolweather.coolweather04.Gson_Object;

public class City {
    //"city":{"aqi":"41","pm25":"22","qlty":"优"}

    private String aqi;
    private String pm25;
    private String qlty;

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getQlty() {
        return qlty;
    }

    public void setQlty(String qlty) {
        this.qlty = qlty;
    }
}
