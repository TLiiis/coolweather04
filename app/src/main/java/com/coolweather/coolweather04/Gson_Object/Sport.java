package com.coolweather.coolweather04.Gson_Object;

public class Sport {

    // "sport":{"type":"sport","brf":"较适宜","txt":"天气较好，户外运动请注意防晒，推荐您在室内进行低强度运动。"},

    private String type;
    private String brf;
    private String txt;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrf() {
        return brf;
    }

    public void setBrf(String brf) {
        this.brf = brf;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
