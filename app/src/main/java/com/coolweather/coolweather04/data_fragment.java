package com.coolweather.coolweather04;

import android.app.Activity;

import android.app.ProgressDialog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;

import android.os.Bundle;
import android.os.Handler;

import android.os.Looper;
import android.os.Message;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.coolweather.coolweather04.Service.onTime_refresh_service01;
import com.coolweather.coolweather04.database_Object.City;
import com.coolweather.coolweather04.database_Object.County;
import com.coolweather.coolweather04.util.CheckNetworkUtil;
import com.coolweather.coolweather04.util.HttpUtil;
import com.coolweather.coolweather04.util.ParseUtil;


import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class data_fragment extends Fragment {

    //TextView data_title;

    private static final String TAG = "标记--data_fragment";

    //Activity activity;
    TextView textView;//这个textView设置为实例变量，对象级别的变量，在后面的所有内部类当中也都可以使用---->
    // 可以用到后面的类中的原因应该就是那些内部类是依赖于data_fragment这个类的，有这个类才有它们那些类，那data_fragment变量自然是可以用到这些内部类里面的！
    ProgressDialog progressDialog = null;

    public static Activity activity;
    //Activity activity;

    public static String weather_address;
    //String weather_address;

    String i;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.data_fragment_layout, container, false);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("正在加载中，请稍等");
        progressDialog.setCancelable(false);
        return view;
    }

    @Override
    //在碎片对应的活动完全创建时调用
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        Log.d(TAG, "onActivityCreated: 拿到的activity对象：" + activity);
        textView = activity.findViewById(R.id.item_title);
        textView.setText("中国");
        //网络是否连接
//        isConnected = CheckNetworkUtil.isNetWorkConnected(getContext());
//        Log.d(TAG, "onActivityCreated: 网络是否连接："+isConnected);
        //从SharedPreference里面拿数据出来
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        weather_address = preferences.getString("weather_address", null);
        Log.d(TAG, "onActivityCreated: 从sharedPreference里面拿到的weather_address：" + weather_address);
        //如果有数据 那么向服务器发起请求并且更新ui------>这里其实没有数据就是要根据当前的定位来获取数据
        if (weather_address != null) {
            progressDialog.show();
            HttpUtil.sendRequest(weather_address, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    progressDialog.dismiss();
                    Looper.prepare();
                    Toast.makeText(activity, "初始化失败，请手动获取", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String return_data = response.body().string();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //根据返回的数据更新ui
                            ParseUtil.parse_returnWeatherData(return_data, activity);
                            progressDialog.dismiss();
                        }
                    });
                }
            }, getContext());
        }
    }


    //boolean isNotVisible;
    boolean isHaveStarted = false;

    @Override
    public void onResume() {
        super.onResume();
        onTime_refresh_service01.isNotVisible = false;
    }

    @Override
    //每次不可见的时候就在半小时后自动更新天气信息
    public void onStop() {
        super.onStop();
        onTime_refresh_service01.isNotVisible = true;
        Context context = getContext();
        if (!isHaveStarted) {
            Intent intent = new Intent(context, onTime_refresh_service01.class);
            intent.putExtra("weather_address", weather_address);
//            Activity_Object activity_object = new Activity_Object();
//            activity_object.setActivity(activity);
//            intent.putExtra("activity_object",activity_object);
            if (context != null) {
                context.startService(intent);
                Log.d(TAG, "onStop: 启动服务成功，传入地址参数：" + weather_address);
                isHaveStarted = true;
            }
        }
    }

    //实例内部类
    class dataAdapter extends RecyclerView.Adapter<com.coolweather.coolweather04.data_fragment.dataAdapter.ViewHolder> {

        List<String> list = new ArrayList<>();
        //        public static int Province_selectedState = 0;
//        public static int City_selectedState = 1;
//        public static int County_selectedState = 2;
//
//        public static int selectedState;
        public int Province_selectedState = 0;
        public int City_selectedState = 1;
        public int County_selectedState = 2;
        public int selectedState;

        int province_code = 0;
        int city_code = 0;
        String address = null;

        //这个就不能叫内部类，只是new接口而已，后面的{}就是对接口方法的实现了
        Handler handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 100:
                        Log.d(TAG, "handleMessage: 接收到分支线程(城市)发送过来的消息并准备开始执行");
                        //为城市表中的城市添加相应的省份编号
//                    List<City> cityList;
//                    cityList = (List<City>) msg.obj;
//                    for (int i = 0;i<cityList.size();i++){
//                        City city = new City();
//                        city.setProvince_code(province_code);
//                        city.updateAll("city_name=?",city.getCity_name());
//                    }
                        //更新ui的操作 此时list中的条目已经发生了变化了 直接更改就行了
                        notifyDataSetChanged();
                        progressDialog.dismiss();
                        //com.coolweather.coolweather04.data_fragment.dataAdapter.selectedState = com.coolweather.coolweather04.data_fragment.dataAdapter.City_selectedState;
                        selectedState = City_selectedState;
                        String province_name = (String) msg.obj;
                        textView.setText(province_name);
                        Log.d(TAG, "handleMessage: 接收到分支线程（城市）发送过来的消息并执行完毕");
                        break;
                    case 200:
                        Log.d(TAG, "handleMessage:接收到分支线程(县级)发送过来的消息并准备开始执行");
                        progressDialog.dismiss();
                        notifyDataSetChanged();
                        //com.coolweather.coolweather04.data_fragment.dataAdapter.selectedState = com.coolweather.coolweather04.data_fragment.dataAdapter.County_selectedState;
                        selectedState = County_selectedState;
                        Log.d(TAG, "handleMessage:接收到分支线程(县级)发送过来的消息并执行完毕");
                        String city_name = (String) msg.obj;
                        textView.setText(city_name);
                        break;
                    case 300:
                        Log.d(TAG, "handleMessage: 接收到分支线程发送过来的天气数据并准备开始解析和更新ui");
                        //拿到返回来的数据
                        String return_data = (String) msg.obj;
                        ParseUtil.parse_returnWeatherData(return_data, activity);
                        progressDialog.dismiss();
                        //isHaveStarted = false;
                    default:
                        break;

                }
            }
        };


        public dataAdapter(List<String> list) {
            this.list = list;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView data_name;
            View view;

            public ViewHolder(View itemView) {
                super(itemView);
                data_name = itemView.findViewById(R.id.data_name);
                view = itemView;
            }
        }

        private static final String TAG = "标记--dataAdapter";

        @Override
        public com.coolweather.coolweather04.data_fragment.dataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View data_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data, parent, false);
            com.coolweather.coolweather04.data_fragment.dataAdapter.ViewHolder holder = new com.coolweather.coolweather04.data_fragment.dataAdapter.ViewHolder(data_view);


            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: 当前所处表级别：" + selectedState);
                    int position = holder.getAdapterPosition();
                    Log.d(TAG, "onClick: " + list.get(position));
                    //找到设置标题文字的控件

                    //****************************************************************************************************
                    //如果当前recyclerView级别是省份级别---->那么肯定查询来源就是城市表或者向服务器发送索要城市数据的请求

                    if (selectedState == Province_selectedState) {
                        //先从省份表中查询这个省份对应的province_code;
                        String province_name = list.get(position);
                        Log.d(TAG, "onClick: " + province_name);
//                    List<Province> provinceList = LitePal.where("province_name=?",province_name).find(Province.class);
//                    Province province = (Province) LitePal.where("province_name=?",province_name).find(Province.class);
//                    if (province!=null){
//                        province_code = province.getProvince_code();
//                    }
                        Cursor cursor = LitePal.findBySQL("select * from province where province_name=\"" + province_name + "\"");

                        //Province province = null;
                        if (cursor != null) {
                            cursor.moveToFirst();
                            province_code = cursor.getInt(cursor.getColumnIndex("province_code"));
                        }
                        //拿着这个province_code去查找这个省下所有的市
                        //首先从City表当中找，返回的是对应这个省份code的所有城市的城市列表
                        List<City> cityList = LitePal.where("province_code=?", String.valueOf(province_code)).find(City.class);
                        Log.d(TAG, "onClick: " + cityList.size() + "  :" + cityList);
                        if (cityList.size() != 0) {
                            Log.d(TAG, "onClick: 开始从City表中查询内容");
                            list.clear();
                            for (City city : cityList) {
                                list.add(city.getCity_name());
                            }
                            Log.d(TAG, "onClick: 查询完毕，准备更新list");
                            //com.coolweather.coolweather04.data_fragment.dataAdapter.selectedState = com.coolweather.coolweather04.data_fragment.dataAdapter.City_selectedState;
                            selectedState = City_selectedState;
                            notifyDataSetChanged();
                            address = "http://guolin.tech/api/china/" + province_code;
                            textView.setText(province_name);
                        }
                        //如果这个City列表的长度为0，那么说明还没有存进去过，需要联网查找数据
                        else {
                            Log.d(TAG, "onClick: 准备联网从数据库当中查找内容");
                            address = "http://guolin.tech/api/china/" + province_code;
                            Log.d(TAG, "onClick:   " + "http://guolin.tech/api/china/" + province_code);
                            //开启线程了
//                            if (isConnected){
                            progressDialog.show();
                            HttpUtil.sendRequest(address, new Callback() {
                                @Override
                                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                    Log.d(TAG, "错误信息---->onResponse:省份编号： " + province_code);
                                    progressDialog.dismiss();
                                    Looper.prepare();
                                    Toast.makeText(getContext(), "获取失败，请重新尝试", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }

                                @Override
                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                    //这里的主要问题是这个province_code 到这个内部类里面来还是外面那个被赋值了的那个province_code嘛？
                                    // 如果不是那还需要想其它办法来弄到这个province_id进来。经过测试之后，发现是的，赋值后的传进来了。
                                    Log.d(TAG, "onResponse:省份编号： " + province_code);
                                    String cityData = response.body().string();
                                    Log.d(TAG, "onResponse: " + cityData);
                                    ParseUtil.parseCity(cityData, province_code);
                                    //此时已经解析完毕并且将数据存入到数据库当中了
                                    Log.d(TAG, "onResponse: 城市数据解析完毕，准备拿出存进去的数据");
                                    //此时从数据库当中拿出数据 拿出city表中province_code等于xx的所有条目
                                    List<City> cityList = LitePal.where("province_code=?", String.valueOf(province_code)).find(City.class);
                                    list.clear();
                                    for (City city : cityList) {
                                        list.add(city.getCity_name());
                                    }
                                    //返回到主线程更新ui并且将省份code存进去
                                    //parent.getContext().run  这里好像不能runOnUiThread 所以需要利用Handler将消息发送出去
                                    Message city_msg = new Message();
                                    city_msg.what = 100;
                                    city_msg.obj = province_name;
                                    //city_msg.obj = list;
                                    handler.sendMessage(city_msg);
                                    Log.d(TAG, "onResponse: 将数据由分支线程向主线程发出");
                                }
                            }, getContext());
//                            }
                        }
                        //****************************************************************************************************
                        //如果当前recyclerView级别是City级别---->那么肯定查询来源就是County县表或者向服务器发送索要县级数据的请求
                    }
                    //else if (selectedState == com.coolweather.coolweather04.data_fragment.dataAdapter.City_selectedState) {
                    else if (selectedState == City_selectedState) {
                        //首先拿到是查询哪个城市下的县,拿到这个城市的city_code
                        String city_name = list.get(position);
                        //item_title.setText(city_name);
                        //然后先看数据库当中有没有
                        Cursor cursor = LitePal.findBySQL("select * from city where city_name=\"" + city_name + "\"");
                        //City city = null;
                        if (cursor != null) {
                            cursor.moveToFirst();
                            city_code = cursor.getInt(cursor.getColumnIndex("city_code"));
                        }
                        List<County> countyList = LitePal.where("city_code=?", String.valueOf(city_code)).find(County.class);
                        Log.d(TAG, "onClick: " + countyList.size() + "  :" + countyList);
                        //有就是从数据库里面拿
                        if (countyList.size() != 0) {
                            list.clear();
                            Log.d(TAG, "onClick: 开始从County表中找数据");
                            for (County county : countyList) {
                                list.add(county.getCounty_name());
                            }
                            notifyDataSetChanged();
                            //com.coolweather.coolweather04.data_fragment.dataAdapter.selectedState = com.coolweather.coolweather04.data_fragment.dataAdapter.County_selectedState;
                            selectedState = County_selectedState;
                            Log.d(TAG, "onClick: 查询完毕 更新list");
                            address = "http://guolin.tech/api/china/" + province_code + "/" + city_code;
                            textView.setText(city_name);
                        } else {
                            //数据库找不到就联网找
                            //此处开启了一个线程了 分支线程
                            //address = address+"/"+city_code;
                            address = "http://guolin.tech/api/china/" + province_code + "/" + city_code;
                            progressDialog.show();
                            Log.d(TAG, "onClick: 拿取城市下的所有数据地址：" + address);
                            HttpUtil.sendRequest(address, new Callback() {
                                @Override
                                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                    progressDialog.dismiss();
                                    Looper.prepare();
                                    Toast.makeText(getContext(), "获取失败，请重新尝试", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }

                                @Override
                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                    //将数据送去解析
                                    ParseUtil.parseCounty(response.body().string(), city_code);
                                    //解析完之后从数据库当中取出这些数据
                                    List<County> countyList = LitePal.where("city_code=?", String.valueOf(city_code)).find(County.class);
                                    list.clear();
                                    for (County county : countyList) {
                                        list.add(county.getCounty_name());
                                    }
                                    Message county_msg = new Message();
                                    county_msg.what = 200;
                                    county_msg.obj = city_name;
                                    handler.sendMessage(county_msg);
                                    Log.d(TAG, "onResponse: 将数据由分支线程（县级）向主线程发出");
                                }
                            }, getContext());
                        }
                    } else if (selectedState == County_selectedState) {
                        Log.d(TAG, "onClick: 当前所处表级别：" + selectedState);
                        /*
                        思路：点击向服务器发送请求 服务器返回数据 就是实时的天气数据了  需要将相关内容展示在activity_main.xml的第一个布局上面了
                        //同样也是需要将服务器返回的数据通过Handler传送出去的  然后在Handler那边更新ui
                        */
                        //首先拿到路径
                        String county_name = list.get(position);
                        List<County> countyList = LitePal.where("county_name=?", county_name).find(County.class);
                        String weather_id = countyList.get(0).getWeather_id();
                        weather_address = "http://guolin.tech/api/weather?cityid=" + weather_id + "&key=d3ec9ac8cd2e4c82ad1db45d8a8df71b";
                        Log.d(TAG, "onClick: 请求天气的地址    " + weather_address);
                        //**************************************************************************
                        //联网进行访问----> 注意这里开启了一个线程

                        //联网之前检查是否有网 有网才连接！！！！！到这里了
//                        Log.d(TAG, "onClick: 网络状态：" + isConnected);
//                        if (isConnected) {
                        progressDialog.show();
                        HttpUtil.sendRequest(weather_address, new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                progressDialog.dismiss();
                                Looper.prepare();
                                Toast.makeText(getContext(), "获取天气信息失败，请重新获取", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                String return_data = response.body().string();
                                //开始解析返回来的数据
                                //发送给主线程 让主线程解析 并且可以直接更新ui
                                Message return_data_message = new Message();
                                return_data_message.what = 300;
                                return_data_message.obj = return_data;
                                //parse_returnWeatherData(return_data);
                                //progressDialog.dismiss();
                                handler.sendMessage(return_data_message);
                                Log.d(TAG, "onResponse: 将返回的天气的数据向主线程发出");
                            }
                        }, getContext());
//                        }else {
//                            Looper.prepare();
//                            Toast.makeText(getContext(), "你没有开启网络权限", Toast.LENGTH_SHORT).show();
//                        }
                    }
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(com.coolweather.coolweather04.data_fragment.dataAdapter.ViewHolder holder, int position) {
            String name = list.get(position);
            holder.data_name.setText(name);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }

    //当这个活动被销毁的时候 需要将 weather_address 保存到SharePreference当中。
    //而当前这个活动的生命周期其实是和这个碎片一样的，所以也可以利用当前碎片的生命周期
    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        if (weather_address != null) {
            editor.putString("weather_address", weather_address);
            editor.apply();
            Log.d(TAG, "onDestroy: 存储成功:" + weather_address);
        }
    }

//    public static class onTime_refresh_service01 extends Service {
//
//        //Activity activity02;
//        static boolean isNotVisible;
//
//        Handler handler02 = new Handler(Looper.myLooper()) {
//            @Override
//            public void handleMessage(Message msg) {
//                switch (msg.what) {
//                    case 400:
//                        Log.d(TAG, "handleMessage: 接收到service中的Handler发送过来的消息，准备更新ui");
//                        String return_data = (String) msg.obj;
//                        ParseUtil.parse_returnWeatherData(return_data, data_fragment.activity);
//                        break;
//                    default:
//                        break;
//                }
//            }
//        };
//
//
//        @Override
//        public void onCreate() {
//            super.onCreate();
//            Log.d(TAG, "onCreate: 服务被创建");
//        }
//
//        @Override
//        public IBinder onBind(Intent intent) {
//            return null;
//        }
//
//        @RequiresApi(api = Build.VERSION_CODES.M)
//        @Override
//        public int onStartCommand(Intent intent, int flags, int startId) {
//            String weather_address01 = intent.getStringExtra("weather_address");
//            Log.d(TAG, "onStartCommand:接收到的intent的参数： " + weather_address01);
//            Log.d(TAG, "onStartCommand: "+activity);
//
////            Activity_Object activity_object = (Activity_Object) intent.getSerializableExtra("activity_object");
////            activity02 = activity_object.getActivity();
////            Log.d(TAG, "onStartCommand: 接收到的intent的参数：activity02"+activity02);
//
//            if (isNotVisible) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        HttpUtil.sendRequest(weather_address01, new Callback() {
//                            @Override
//                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                                Log.d(TAG, "onFailure: 已在服务中向服务器器发送请求，但是请求失败");
//                                e.printStackTrace();
//                            }
//
//                            @Override
//                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                                String return_data = response.body().string();
//                                Message message = new Message();
//                                message.what = 400;
//                                message.obj = return_data;
//                                handler02.sendMessage(message);
//                            }
//                        });
//                    }
//                }).start();
//            }
//
//            //开启定时服务
//            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//            //60秒*60次
//            int anHour = 60 * 1000 * 2;
//            long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
//            Intent i = new Intent(this, onTime_refresh_service01.class);
//            i.putExtra("weather_address",weather_address);
//            PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
//            //manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
//            manager.setAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
//            Log.d(TAG, "onStartCommand: 定时服务开启！");
//            return super.onStartCommand(intent, flags, startId);
//        }
//    }
}
