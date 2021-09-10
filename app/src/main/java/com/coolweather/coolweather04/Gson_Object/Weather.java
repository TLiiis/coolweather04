package com.coolweather.coolweather04.Gson_Object;

import java.util.List;

public class Weather {

    private List<WeatherInfo> HeWeather;

    public Weather(List heWeather) {
        HeWeather = heWeather;
    }

    public List getHeWeather() {
        return HeWeather;
    }

    public void setHeWeather(List heWeather) {
        HeWeather = heWeather;
    }
}
