package com.coolweather.coolweather04;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coolweather.coolweather04.database_Object.City;
import com.coolweather.coolweather04.database_Object.Province;
import com.coolweather.coolweather04.util.HttpUtil;
import com.coolweather.coolweather04.util.ParseUtil;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "标记--MainActivity";
    List<String> list = new ArrayList<>();
    RecyclerView recyclerView;
    com.coolweather.coolweather04.data_fragment.dataAdapter  adapter;
    DrawerLayout drawerLayout;


    //ProgressDialog progressDialog = new ProgressDialog();----->报错？？？？为什么
    ProgressDialog progressDialog;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: 程序开始执行"+this);
        SQLiteDatabase database = LitePal.getDatabase();
        Log.d(TAG, "onCreate: 拿到CoolWeather04.db数据库");
        recyclerView =findViewById(R.id.recyclerView);
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        data_fragment data_fragment = (com.coolweather.coolweather04.data_fragment) fragmentManager.findFragmentById(R.id.data_fragment);
        adapter = data_fragment.new dataAdapter(list);

        //progressDialog部分
        progressDialog = new ProgressDialog(MainActivity.this);
        Log.d(TAG, "onCreate: progress："+progressDialog);
        progressDialog.setMessage("正在初始化数据……");
        progressDialog.setCancelable(false);

        initProvinceData();

        drawerLayout = findViewById(R.id.drawerLayout);
        Button back_button = findViewById(R.id.back);
        TextView textView = findViewById(R.id.item_title);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        //ToolBar部分
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle("天气预报");

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.purple_700);
        //swipeRefreshLayout.setTooltipText("正在刷新中");
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (adapter.selectedState){
                    case 0:
                        //说明此时处于省份级别--->按一下就退出
                        drawerLayout.closeDrawers();
                        break;
                    case 1:
                        //说明此时处于城市级别 --->按一下返回省份级别
                        List<Province> provinceList = LitePal.findAll(Province.class);
                        list.clear();
                        for (Province province:provinceList){
                            list.add(province.getProvince_name());
                        }
                        adapter.notifyDataSetChanged();
                        adapter.selectedState = adapter.Province_selectedState;
                        textView.setText("中国");
                        break;
                    case 2:
                        //说明此时处于县级级别 按一下返回城市级别
                        //首先要知道是哪个城市 比如现在知道是江门---->那么要返回广东级别的-->那么要找到广东的province_code---->
                        //--->然后根据province_code从City表里面查询
                        String city_name = (String) textView.getText();
                        //通过江门的名字查出广东 然后查出广东的编号
                        List<City> cityList = LitePal.where("city_name=?",city_name).find(City.class);
                        Log.d(TAG, "onClick: "+cityList);
                        int province_code = cityList.get(0).getProvince_code();
                        //通过广东的编号 查出广东的名字
                        List<Province> provinces =  LitePal.where("province_code=?",String.valueOf(province_code)).find(Province.class);
                        String province_name = provinces.get(0).getProvince_name();
                        //通过广东的编号 查出广东下所有的城市
                        List<City> cities = LitePal.where("province_code=?",String.valueOf(province_code)).find(City.class);
                        list.clear();
                        for (City city:cities){
                            list.add(city.getCity_name());
                        }
                        adapter.notifyDataSetChanged();
                        adapter.selectedState = adapter.City_selectedState;
                        textView.setText(province_name);
                        break;
                    case 3:
                        break;
                    default:
                        break;
                }
            }
        });

    }


    //首先打开活动页面的时候需要初始化数据 主要是获取省份数据
    public void initProvinceData(){
        Log.d(TAG, "onResponse: 开始初始化省份数据");
        //首先是先从省份数据库里面找
        List<Province> provinceList = LitePal.findAll(Province.class);
        //如果这个Province表的条目数量不为0 那么说明之前已经导入了数据了

        if (provinceList.size()!=0){
            Log.d(TAG, "initProvinceData: 开始从Province表中找Province数据");
            for (Province province:provinceList){
                //为recyclerView添加要适配的内容
                list.add(province.getProvince_name());
            }
            Log.d(TAG, "initProvinceData: 从数据库当中找省份数据完毕");
            //为适配器添加适配内容
//            data_fragment.dataAdapter adapter = new data_fragment.dataAdapter(list);
//            data_fragment.dataAdapter.selectedState = data_fragment.dataAdapter.Province_selectedState;
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            data_fragment data_fragment = (com.coolweather.coolweather04.data_fragment) fragmentManager.findFragmentById(R.id.data_fragment);
//            com.coolweather.coolweather04.data_fragment.dataAdapter  adapter = data_fragment.new dataAdapter(list);
            adapter.selectedState = adapter.Province_selectedState;
            recyclerView.setAdapter(adapter);
        } else {
            //如果数据库里面没有 那么需要向服务器索取 注意这个方法是在分支线程当中执行的
            Log.d(TAG, "initProvinceData: 开启一个线程向服务器索要Province数据");
            progressDialog.show();
            HttpUtil.sendRequest("http://guolin.tech/api/china", new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    progressDialog.dismiss();
                    Looper.prepare();
                    Toast.makeText(MainActivity.this, "初始化失败，请退出app并重新打开", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                @Override
                //参数中的response就是服务器返回的响应对象
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    //需要将拿到的数据进行解析 调用静态方法
                    ParseUtil.parseProvince(response.body().string());
                    //此时数据库当中已经存入的Province表内容，这里从数据库当中查出数据来
                    List<Province> provinceList = LitePal.findAll(Province.class);
                    //List<String> list01 = new ArrayList<>();
                    //list.clear();应该是还不需要清楚，此时list当中本来就是没有东西的
                    for (Province province:provinceList){
                        //list01.add(province.getProvince_name());
                        list.add(province.getProvince_name());
                    }
                    //切换回主线程更新ui
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "run: 切换回主线程更新ui");
//                            data_fragment.dataAdapter adapter = new data_fragment.dataAdapter(list);
//                            recyclerView.setAdapter(adapter);
//                            data_fragment.dataAdapter.selectedState = data_fragment.dataAdapter.Province_selectedState;
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            data_fragment data_fragment = (com.coolweather.coolweather04.data_fragment) fragmentManager.findFragmentById(R.id.data_fragment);
                            //com.coolweather.coolweather04.data_fragment.dataAdapter adapter = data_fragment.new dataAdapter(list);
                            progressDialog.dismiss();
                            adapter.selectedState = adapter.Province_selectedState;
                            recyclerView.setAdapter(adapter);
                        }
                    });
                }
            },getApplicationContext());
        }
    }


    public void refresh(){
        //刷新的逻辑
        if (data_fragment.weather_address!=null){
            //重新发送请求获取天气信息
            HttpUtil.sendRequest(data_fragment.weather_address, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                    Looper.prepare();
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(MainActivity.this, "刷新失败，获取服务器数据失败", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String return_data = response.body().string();
                    Log.d(TAG, "onResponse: 刷新获取的数据:"+return_data);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ParseUtil.parse_returnWeatherData(return_data,MainActivity.this);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            },this);
        }else {
            Toast.makeText(this, "刷新失败，您还没有获取过任何天气无法刷新", Toast.LENGTH_SHORT).show();
        }
    }
}