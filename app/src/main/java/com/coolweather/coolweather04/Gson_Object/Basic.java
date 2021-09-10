package com.coolweather.coolweather04.Gson_Object;

public class Basic {
    /*

    "basic":{
                 "cid":"CN101281107",
                 "location":"蓬江",
                 "parent_city":"江门",
                 "admin_area":"广东",
                 "cnty":"中国",
                 "lat":"27.88676262",
                 "lon":"102.25874329",
                 "tz":"+8.00",
                 "city":"蓬江",
                 "id":"CN101281107",
                 "update":{"loc":"2021-09-05 02:41","utc":"2021-09-05 02:41"}
                 },
    */

    private String cid;
    private String location;
    private String parent_city;
    private String admin_area;
    private String cnty;
    private String lay;
    private String lon;
    private String tz;
    private String city;
    private String id;
    private Update update;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getParent_city() {
        return parent_city;
    }

    public void setParent_city(String parent_city) {
        this.parent_city = parent_city;
    }

    public String getAdmin_area() {
        return admin_area;
    }

    public void setAdmin_are(String admin_are) {
        this.admin_area = admin_are;
    }

    public String getCnty() {
        return cnty;
    }

    public void setCnty(String cnty) {
        this.cnty = cnty;
    }

    public String getLay() {
        return lay;
    }

    public void setLay(String lay) {
        this.lay = lay;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Update getUpdate() {
        return update;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }
}
