package com.coolweather.coolweather04.Gson_Object;

public class Update {
    //"update":{"loc":"2021-09-05 02:41","utc":"2021-09-05 02:41"}

    private String loc;
    private String utc;

    public Update(String loc, String utc) {
        this.loc = loc;
        this.utc = utc;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getUtc() {
        return utc;
    }

    public void setUtc(String utc) {
        this.utc = utc;
    }
}
