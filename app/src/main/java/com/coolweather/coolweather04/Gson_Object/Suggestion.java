package com.coolweather.coolweather04.Gson_Object;

public class Suggestion {
    private Comf comf;
    private Sport sport;
    private Cw cw;

    public Suggestion(Comf comf, Sport sport, Cw cw) {
        this.comf = comf;
        this.sport = sport;
        this.cw = cw;
    }

    public Comf getComf() {
        return comf;
    }

    public void setComf(Comf comf) {
        this.comf = comf;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public Cw getCw() {
        return cw;
    }

    public void setCw(Cw cw) {
        this.cw = cw;
    }
}
