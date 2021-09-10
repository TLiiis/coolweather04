package com.coolweather.coolweather04.util;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;

import com.coolweather.coolweather04.database_Object.City;
import com.coolweather.coolweather04.database_Object.County;
import com.coolweather.coolweather04.List_Object.ForeDateData;
import com.coolweather.coolweather04.Gson_Object.Aqi;
import com.coolweather.coolweather04.Gson_Object.Basic;
import com.coolweather.coolweather04.Gson_Object.Comf;
import com.coolweather.coolweather04.Gson_Object.Cond;
import com.coolweather.coolweather04.Gson_Object.Cw;
import com.coolweather.coolweather04.Gson_Object.Forecast;
import com.coolweather.coolweather04.Gson_Object.Now;
import com.coolweather.coolweather04.Gson_Object.Sport;
import com.coolweather.coolweather04.Gson_Object.Suggestion;
import com.coolweather.coolweather04.Gson_Object.Weather;
import com.coolweather.coolweather04.Gson_Object.WeatherInfo;
import com.coolweather.coolweather04.database_Object.Province;
import com.coolweather.coolweather04.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParseUtil {
    private static final String TAG = "标记--ParseUtil";
    public static void parseProvince(String ProvinceData){
        try {
            Log.d(TAG, "parseProvince: 开始解析省份数据");
            JSONArray jsonArray = new JSONArray(ProvinceData);
            for (int i =0;i< jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Province province = new Province(jsonObject.getInt("id"),jsonObject.getString("name"));
                province.save();
            }
            Log.d(TAG, "parseProvince: 数据解析完毕并且已存入Province表当中");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //public static void parseCity(String CityData,int province_code){
    public static void parseCity(String CityData,int province_code){
        try {
            Log.d(TAG, "parseCity: 开始解析服务器返回的City数据");
            JSONArray jsonArray = new JSONArray(CityData);
            for (int i =0;i< jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                City city = new City(jsonObject.getInt("id"),jsonObject.getString("name"),province_code);
                city.save();
            }
            Log.d(TAG, "parseCity: 数据解析完毕并且已存入City表当中");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void parseCounty(String CountyData,int city_code){
        try {
            Log.d(TAG, "parseCounty: 开始解析服务器返回的County数据");
            JSONArray jsonArray = new JSONArray(CountyData);
            for (int i =0;i< jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                County county = new County(jsonObject.getInt("id"),jsonObject.getString("name"),
                        jsonObject.getString("weather_id"),city_code);
                county.save();
            }
            Log.d(TAG, "parseCounty: 数据解析完毕并且已存入County表当中");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void parse_returnWeatherData(String return_data , Activity activity){
            /*
            点击查看具体天气情况---->拿到数据之后更新ui，然后将左侧收回
            */

        TextView loc_text = activity.findViewById(R.id.loc);
        TextView temp_text = activity.findViewById(R.id.temp);
        TextView txt_text = activity.findViewById(R.id.txt);

        TextView fore_day01 = activity.findViewById(R.id.fore_date01);
        TextView fore_day01_txt = activity.findViewById(R.id.fore_date01_txt);
        TextView fore_day01_max = activity.findViewById(R.id.fore_date01_max);
        TextView fore_day01_min = activity.findViewById(R.id.fore_date01_min);


        TextView fore_day02 = activity.findViewById(R.id.fore_date02);
        TextView fore_day02_txt = activity.findViewById(R.id.fore_date02_txt);
        TextView fore_day02_max = activity.findViewById(R.id.fore_date02_max);
        TextView fore_day02_min = activity.findViewById(R.id.fore_date02_min);
        ForeDateData foreDateData02 = new ForeDateData(fore_day02,fore_day02_txt,fore_day02_max,fore_day02_min);

        TextView fore_day03 = activity.findViewById(R.id.fore_date03);
        TextView fore_day03_txt = activity.findViewById(R.id.fore_date03_txt);
        TextView fore_day03_max = activity.findViewById(R.id.fore_date03_max);
        TextView fore_day03_min = activity.findViewById(R.id.fore_date03_min);
        ForeDateData foreDateData03 = new ForeDateData(fore_day03,fore_day03_txt,fore_day03_max,fore_day03_min);

        TextView fore_day04 = activity.findViewById(R.id.fore_date04);
        TextView fore_day04_txt = activity.findViewById(R.id.fore_date04_txt);
        TextView fore_day04_max = activity.findViewById(R.id.fore_date04_max);
        TextView fore_day04_min = activity.findViewById(R.id.fore_date04_min);
        ForeDateData foreDateData04 = new ForeDateData(fore_day04,fore_day04_txt,fore_day04_max,fore_day04_min);

        TextView fore_day05 = activity.findViewById(R.id.fore_date05);
        TextView fore_day05_txt = activity.findViewById(R.id.fore_date05_txt);
        TextView fore_day05_max = activity.findViewById(R.id.fore_date05_max);
        TextView fore_day05_min = activity.findViewById(R.id.fore_date05_min);
        ForeDateData foreDateData05 = new ForeDateData(fore_day05,fore_day05_txt,fore_day05_max,fore_day05_min);


        TextView fore_day06 = activity.findViewById(R.id.fore_date06);
        TextView fore_day06_txt = activity.findViewById(R.id.fore_date06_txt);
        TextView fore_day06_max = activity.findViewById(R.id.fore_date06_max);
        TextView fore_day06_min = activity.findViewById(R.id.fore_date06_min);
        ForeDateData foreDateData06 = new ForeDateData(fore_day06,fore_day06_txt,fore_day06_max,fore_day06_min);

        TextView fore_day07 = activity.findViewById(R.id.fore_date07);
        TextView fore_day07_txt = activity.findViewById(R.id.fore_date07_txt);
        TextView fore_day07_max = activity.findViewById(R.id.fore_date07_max);
        TextView fore_day07_min = activity.findViewById(R.id.fore_date07_min);
        ForeDateData foreDateData07 = new ForeDateData(fore_day07,fore_day07_txt,fore_day07_max,fore_day07_min);

        List<ForeDateData> foreDateDataList = new ArrayList<>();
        foreDateDataList.add(foreDateData02);
        foreDateDataList.add(foreDateData03);
        foreDateDataList.add(foreDateData04);
        foreDateDataList.add(foreDateData05);
        foreDateDataList.add(foreDateData06);
        foreDateDataList.add(foreDateData07);



        TextView aqi_text = activity.findViewById(R.id.aqi);
        TextView Pm2_5 = activity.findViewById(R.id.PM2_5);

        TextView comf_text = activity.findViewById(R.id.comf);
        TextView sport_text = activity.findViewById(R.id.sport);
        TextView cw_text = activity.findViewById(R.id.cw);


        DrawerLayout drawerLayout = activity.findViewById(R.id.drawerLayout);

        Gson gson = new Gson();
        Weather weather = gson.fromJson(return_data, Weather.class);
        Log.d(TAG, "onClick: " + weather);

        List<WeatherInfo> weatherInfoList = weather.getHeWeather();
        Log.d(TAG, "onClick: " + weatherInfoList);
        WeatherInfo weatherInfo = weatherInfoList.get(0);

        //基本信息
        Basic basic = weatherInfo.getBasic();
        String location = basic.getLocation();
        Log.d(TAG, "onClick: location:" + location+"区");
        loc_text.setText(location);


        //当前状况
        Now now = weatherInfo.getNow();
        Cond cond = now.getCond();
        String txt = cond.getTxt();
        Log.d(TAG, "onClick: " + txt);
        txt_text.setText(txt);
        String tmp = now.getTmp();
        temp_text.setText(tmp+"°C");

        //当前空气质量状态
        Aqi aqi = weatherInfo.getAqi();
        com.coolweather.coolweather04.Gson_Object.City city = aqi.getCity();
        Log.d(TAG, "onClick: aqi:" + city.getAqi());
        aqi_text.setText(city.getAqi());
        Log.d(TAG, "onClick: PM25:" + city.getPm25());
        Pm2_5.setText(city.getPm25());

        //关于当前天气的建议：
        Suggestion suggestion = weatherInfo.getSuggestion();
        Comf comf = suggestion.getComf();
        Log.d(TAG, "onClick: comf_txt:" + comf.getTxt());
        comf_text.setText(comf.getTxt());
        Sport sport = suggestion.getSport();
        Log.d(TAG, "onClick: sport_txt:" + sport.getTxt());
        sport_text.setText(sport.getTxt());
        Cw cw = suggestion.getCw();
        Log.d(TAG, "onClick: cw_txt: " + cw.getTxt());
        cw_text.setText(cw.getTxt());

        //msg
        String msg = weatherInfo.getMsg();
        Log.d(TAG, "onClick: " + msg);

        //未来几天天气情况
        List<Forecast> forecastList = weatherInfo.getDaily_forecast();
        for (int i = 0;i<forecastList.size();i++) {
            Log.d(TAG, "onClick: " + forecastList.get(i).getDate()
                    + "   min_Tmp：" + forecastList.get(i).getTmp().getMin()
                    + "   max_tmp：" + forecastList.get(i).getTmp().getMax()
                    + "   txt_d：" + forecastList.get(i).getCond().getTxt_d());
            foreDateDataList.get(i).getFore_day().setText(forecastList.get(i).getDate());
            foreDateDataList.get(i).getFore_day_txt().setText(forecastList.get(i).getCond().getTxt_d());
            foreDateDataList.get(i).getFore_day_max().setText(forecastList.get(i).getTmp().getMax());
            foreDateDataList.get(i).getFore_day_min().setText(forecastList.get(i).getTmp().getMin());
        }

        drawerLayout.closeDrawers();
    }

}
