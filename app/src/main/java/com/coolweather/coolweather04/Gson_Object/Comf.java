package com.coolweather.coolweather04.Gson_Object;

public class Comf {
    /*
    "comf":{"type":"comf","brf":"较舒适","txt":"白天天气晴好，您在这种天气条件下，会感觉早晚凉爽、舒适，午后偏热。"}
    */
    private String type;
    private String brf;
    private String txt;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrg() {
        return brf;
    }

    public void setBrg(String brg) {
        this.brf = brg;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
