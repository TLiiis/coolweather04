//package com.coolweather.coolweather04;
//
//import android.database.Cursor;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.coolweather.coolweather04.util.HttpUtil;
//import com.coolweather.coolweather04.util.ParseUtil;
//
//import org.jetbrains.annotations.NotNull;
//import org.litepal.LitePal;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.Response;
//
//public class dataAdapter extends RecyclerView.Adapter<dataAdapter.ViewHolder> {
//
//    List<String> list = new ArrayList<>();
//    public static int Province_selectedState = 0;
//    public static int City_selectedState = 1;
//    public static int County_selectedState = 2;
//
//    public static int selectedState;
//
//    int province_code = 0;
//    int city_code = 0;
//    String address = null;
//
//    Handler handler = new Handler(){
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            switch (msg.what){
//                case 100:
//                    Log.d(TAG, "handleMessage: 接收到分支线程(城市)发送过来的消息并准备开始执行");
//                    //为城市表中的城市添加相应的省份编号
////                    List<City> cityList;
////                    cityList = (List<City>) msg.obj;
////                    for (int i = 0;i<cityList.size();i++){
////                        City city = new City();
////                        city.setProvince_code(province_code);
////                        city.updateAll("city_name=?",city.getCity_name());
////                    }
//                    //更新ui的操作 此时list中的条目已经发生了变化了 直接更改就行了
//                    notifyDataSetChanged();
//                    dataAdapter.selectedState = dataAdapter.City_selectedState;
//                    Log.d(TAG, "handleMessage: 接收到分支线程（城市）发送过来的消息并执行完毕");
//                    break;
//                case 200:
//                    Log.d(TAG, "handleMessage:接收到分支线程(县级)发送过来的消息并准备开始执行");
//                    notifyDataSetChanged();
//                    dataAdapter.selectedState = dataAdapter.County_selectedState;
//                    Log.d(TAG, "handleMessage:接收到分支线程(县级)发送过来的消息并执行完毕");
//                    break;
//                default:
//                    break;
//
//            }
//        }
//    };
//
//
//    public dataAdapter(List<String> list) {
//        this.list = list;
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView data_name;
//        View view;
//        public ViewHolder(View itemView) {
//            super(itemView);
//            data_name = itemView.findViewById(R.id.data_name);
//            view = itemView;
//        }
//    }
//
//    private static final String TAG = "标记--dataAdapter";
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View data_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data,parent,false);
//        ViewHolder holder = new ViewHolder(data_view);
//
////        空指针异常
////        TextView item_title = parent.findViewById(R.id.item_title);
////        item_title.setText("中国");
//
//        holder.view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: 当前所处表级别："+selectedState);
//                int position = holder.getAdapterPosition();
//                Log.d(TAG, "onClick: "+list.get(position));
//                //找到设置标题文字的控件
//
//                //****************************************************************************************************
//                //如果当前recyclerView级别是省份级别---->那么肯定查询来源就是城市表或者向服务器发送索要城市数据的请求
//
//                if (selectedState==Province_selectedState){
//                    //先从省份表中查询这个省份对应的province_code;
//                    String province_name = list.get(position);
//                    Log.d(TAG, "onClick: "+province_name);
////                    List<Province> provinceList = LitePal.where("province_name=?",province_name).find(Province.class);
////                    Province province = (Province) LitePal.where("province_name=?",province_name).find(Province.class);
////                    if (province!=null){
////                        province_code = province.getProvince_code();
////                    }
//                    Cursor cursor =  LitePal.findBySQL("select * from province where province_name=\""+province_name+"\"");
//
//                    Province province = null;
//                    if (cursor!=null){
//                        cursor.moveToFirst();
//                        province_code = cursor.getInt(cursor.getColumnIndex("province_code"));
//                    }
//                    //拿着这个province_code去查找这个省下所有的市
//                    //首先从City表当中找，返回的是对应这个省份code的所有城市的城市列表
//                    List<City> cityList = LitePal.where("province_code=?", String.valueOf(province_code)).find(City.class);
//                    Log.d(TAG, "onClick: "+cityList.size()+"  :"+cityList);
//                    if (cityList.size()!=0){
//                        Log.d(TAG, "onClick: 开始从City表中查询内容");
//                        list.clear();
//                        for (City city:cityList){
//                            list.add(city.getCity_name());
//                        }
//                        Log.d(TAG, "onClick: 查询完毕，准备更新list");
//                        dataAdapter.selectedState = dataAdapter.City_selectedState;
//                        dataAdapter.this.notifyDataSetChanged();
//                        address = "http://guolin.tech/api/china/" + province_code;
//                    }
//                    //如果这个City列表的长度为0，那么说明还没有存进去过，需要联网查找数据
//                    else {
//                        Log.d(TAG, "onClick: 准备联网从数据库当中查找内容");
//                        address = "http://guolin.tech/api/china/" + province_code;
//                        Log.d(TAG, "onClick:   "+"http://guolin.tech/api/china/" + province_code);
//                        //开启线程了
//                        HttpUtil.sendRequest(address, new Callback() {
//                            @Override
//                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                                Log.d(TAG, "错误信息---->onResponse:省份编号： "+province_code);
//                                e.printStackTrace();
//                            }
//
//                            @Override
//                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                                //这里的主要问题是这个province_code 到这个内部类里面来还是外面那个被赋值了的那个province_code嘛？
//                                // 如果不是那还需要想其它办法来弄到这个province_id进来。经过测试之后，发现是的，赋值后的传进来了。
//                                Log.d(TAG, "onResponse:省份编号： "+province_code);
//                                String cityData = response.body().string();
//                                Log.d(TAG, "onResponse: "+cityData);
//                                ParseUtil.parseCity(cityData,province_code);
//                                //此时已经解析完毕并且将数据存入到数据库当中了
//                                Log.d(TAG, "onResponse: 城市数据解析完毕，准备拿出存进去的数据");
//                                //此时从数据库当中拿出数据 拿出city表中province_code等于xx的所有条目
//                                List<City> cityList = LitePal.where("province_code=?", String.valueOf(province_code)).find(City.class);
//                                list.clear();
//                                for (City city:cityList){
//                                    list.add(city.getCity_name());
//                                }
//                                //返回到主线程更新ui并且将省份code存进去
//                                //parent.getContext().run  这里好像不能runOnUiThread 所以需要利用Handler将消息发送出去
//                                Message city_msg = new Message();
//                                city_msg.what = 100;
//                                //city_msg.obj = list;
//                                handler.sendMessage(city_msg);
//                                Log.d(TAG, "onResponse: 将数据由分支线程向主线程发出");
//                            }
//                        });
//                    }
//                //****************************************************************************************************
//                //如果当前recyclerView级别是City级别---->那么肯定查询来源就是County县表或者向服务器发送索要县级数据的请求
//                } else if (selectedState==dataAdapter.City_selectedState){
//                    //首先拿到是查询哪个城市下的县,拿到这个城市的city_code
//                    String city_name = list.get(position);
//                    //item_title.setText(city_name);
//                    //然后先看数据库当中有没有
//                    Cursor cursor =  LitePal.findBySQL("select * from city where city_name=\""+city_name+"\"");
//                    City city = null;
//                    if (cursor!=null){
//                        cursor.moveToFirst();
//                        city_code = cursor.getInt(cursor.getColumnIndex("city_code"));
//                    }
//                    List<County> countyList = LitePal.where("city_code=?", String.valueOf(city_code)).find(County.class);
//                    Log.d(TAG, "onClick: "+countyList.size()+"  :"+countyList);
//                    //有就是从数据库里面拿
//                    if (countyList.size()!=0){
//                        list.clear();
//                        Log.d(TAG, "onClick: 开始从County表中找数据");
//                        for (County county:countyList){
//                            list.add(county.getCounty_name());
//                        }
//                        dataAdapter.this.notifyDataSetChanged();
//                        dataAdapter.selectedState = dataAdapter.County_selectedState;
//                        Log.d(TAG, "onClick: 查询完毕 更新list");
//                        address = "http://guolin.tech/api/china/" + province_code+"/"+city_code;
//                    }else {
//                        //数据库找不到就联网找
//                        //此处开启了一个线程了 分支线程
//                        //address = address+"/"+city_code;
//                        address = "http://guolin.tech/api/china/" + province_code+"/"+city_code;
//                        Log.d(TAG, "onClick: 拿取城市下的所有数据地址："+address);
//                        HttpUtil.sendRequest(address, new Callback() {
//                            @Override
//                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                                e.printStackTrace();
//                            }
//
//                            @Override
//                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                                //将数据送去解析
//                                ParseUtil.parseCounty(response.body().string(),city_code);
//                                //解析完之后从数据库当中取出这些数据
//                                List<County> countyList = LitePal.where("city_code=?", String.valueOf(city_code)).find(County.class);
//                                list.clear();
//                                for (County county:countyList){
//                                    list.add(county.getCounty_name());
//                                }
//                                Message county_msg = new Message();
//                                county_msg.what = 200;
//                                handler.sendMessage(county_msg);
//                                Log.d(TAG, "onResponse: 将数据由分支线程（县级）向主线程发出");
//                            }
//                        });
//                    }
//                }
//            }
//        });
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        String name = list.get(position);
//        holder.data_name.setText(name);
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//}
