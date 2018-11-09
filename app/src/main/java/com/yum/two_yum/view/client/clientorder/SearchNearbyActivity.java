package com.yum.two_yum.view.client.clientorder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.SearchAddressDataBase;
import com.yum.two_yum.base.SearchNearbyBase;
import com.yum.two_yum.base.SearchNearbyDataBase;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.controller.adapter.SearchAddressAdapter;
import com.yum.two_yum.controller.adapter.SearchNearbyAdapter;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.ContainsEmojiEditText;
import com.yum.two_yum.utile.view.NoSlideListView;
import com.yum.two_yum.view.client.business.ChooseDeliveryAddressActivity;
import com.yum.two_yum.view.login.LoginActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * @author 余先德
 * @data 2018/4/9
 */

public class SearchNearbyActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {

    public final static String TAG = "SearchNearbyActivity";


    @Bind(R.id.address_list)
    NoSlideListView addressList;
    @Bind(R.id.empty_btn)
    ImageView emptyBtn;
    @Bind(R.id.address_tv)
    ContainsEmojiEditText addressTv;
    @Bind(R.id.location_btn)
    LinearLayout locationBtn;
    @Bind(R.id.my_address_layout)
    LinearLayout myAddressLayout;


    private static final int GOOGLE_API_CLIENT_ID = 0;

    private List<SearchNearbyDataBase> dataList = new ArrayList<>();
    private List<SearchAddressDataBase> dataList1 = new ArrayList<>();
    private SearchNearbyAdapter adapter;
    private GoogleApiClient mGoogleApiClient;
    private GoogleApiClient mGoogleApiClient1;
    private Location mLastLocation;
    private String address;
    private SearchAddressAdapter addressAdapter;
    private boolean isPermissionRequested;
    private android.os.Handler handler = new android.os.Handler() {


        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            try {
                if (msg.what == 0) {
                    addressAdapter.notifyDataSetChanged();
                } else if (msg.what == 1) {
                    addressList.setAdapter(addressAdapter);
                    addressAdapter.notifyDataSetChanged();
                } else if (msg.what == 2) {
                    addressList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }catch (Exception e){
                showMsg("刷新报错");
            }

        }

    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_nearby);
        ButterKnife.bind(this);
        if (mGoogleApiClient1==null) {
            mGoogleApiClient1 = new GoogleApiClient.Builder(SearchNearbyActivity.this)
                    //.addConnectionCallbacks(this)
                    .enableAutoManage(SearchNearbyActivity.this, GOOGLE_API_CLIENT_ID /* clientId */, SearchNearbyActivity.this)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    //.addApi(LocationServices.API)
                    .build();
        }
        if (mGoogleApiClient==null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        adapter = new SearchNearbyAdapter(dataList);
        addressAdapter = new SearchAddressAdapter(dataList1);
        addressAdapter.setItemClick(new SearchAddressAdapter.ItemClick() {
            @Override
            public void click(String lat, String lng, String address, String name) {
                YumApplication.getInstance().setLat(lat);
                YumApplication.getInstance().setLong(lng);
                YumApplication.getInstance().getAddressDB().clear();
                setResult(RESULT_OK);
                finish();
            }
        });
        adapter.setItemClick(new SearchNearbyAdapter.ItemClick() {
            @Override
            public void itemClick(SearchNearbyDataBase data) {
                YumApplication.getInstance().setLat(data.getLat());
                YumApplication.getInstance().setLong(data.getLng());

                YumApplication.getInstance().getAddressDB().setAddress(data.getAddress());
                YumApplication.getInstance().getAddressDB().setGender(data.getGender());
                YumApplication.getInstance().getAddressDB().setId(data.getId());
                YumApplication.getInstance().getAddressDB().setLat(data.getLat());
                YumApplication.getInstance().getAddressDB().setLng(data.getLng());
                YumApplication.getInstance().getAddressDB().setName(data.getName());
                YumApplication.getInstance().getAddressDB().setNote(data.getNote());
                YumApplication.getInstance().getAddressDB().setPhone(data.getPhone());
                YumApplication.getInstance().getAddressDB().setUserId(data.getUserId());
                Intent intent = new Intent();
                intent.putExtra("data",data);
                setResult(RESULT_OK,intent);
                finish();
                overridePendingTransition(R.anim.activity_open,R.anim.out);
            }

            @Override
            public void editClick(SearchNearbyDataBase data) {
               Intent intent = new Intent(SearchNearbyActivity.this,AddToAddressActivity.class);
               intent.putExtra("data",data);
               intent.putExtra("type",true);
               startActivityForResult(intent,0);
            }

            @Override
            public void delClick(final SearchNearbyDataBase data) {
                Map<String,String> map = new HashMap<>();
                map.put("userId",YumApplication.getInstance().getMyInformation().getUid());
                map.put("addressId",data.getId());
                OkHttpUtils
                        .post()
                        .url(Constant.DEL_ADDRESS)
                        .addHeader("Accept-Language", AppUtile.getLanguage())
                        .addHeader("YUM-AUTH-TOKEN",YumApplication.getInstance().getMyInformation().getToken())
                        .params(map)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int i) {
                                if (AppUtile.getNetWork(e.getMessage())){
                                    showMsg(getString(R.string.NETERROR));
                                }
                            }

                            @Override
                            public void onResponse(String s, int i) {
                                if (AppUtile.setCode(s,SearchNearbyActivity.this)) {
                                    if (data.getId().equals(YumApplication.getInstance().getAddressDB().getId())) {
                                        YumApplication.getInstance().getAddressDB().setAddress("");
                                        YumApplication.getInstance().getAddressDB().setGender(-1);
                                        YumApplication.getInstance().getAddressDB().setId("");
                                        YumApplication.getInstance().getAddressDB().setLat("");
                                        YumApplication.getInstance().getAddressDB().setLng("");
                                        YumApplication.getInstance().getAddressDB().setName("");
                                        YumApplication.getInstance().getAddressDB().setNote("");
                                        YumApplication.getInstance().getAddressDB().setPhone("");
                                        YumApplication.getInstance().getAddressDB().setUserId("");
                                    }
                                    getAddress();
                                }
                            }
                        });
            }
        });
        addressList.setAdapter(adapter);
        addressTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                    if (!TextUtils.isEmpty(s)) {

                        locationBtn.setVisibility(View.GONE);
                        dataList1.clear();
                        myAddressLayout.setVisibility(View.GONE);
                        address = s.toString();

                        //getAddress();

                        if (mGoogleApiClient1.isConnected()) {
                            //     LatLngBounds data = new LatLngBounds(oldLatLng, newLatLng);
                            if (!TextUtils.isEmpty(s.toString())) {
                                emptyBtn.setVisibility(View.VISIBLE);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {

                                        dataList1.clear();
                                        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                                                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_NONE)
                                                .build();
                                        if (typeFilter!=null) {
                                            PendingResult<AutocompletePredictionBuffer> result =
                                                    Places.GeoDataApi.getAutocompletePredictions(mGoogleApiClient1, (String) s.toString(), null,
//                                                new LatLngBounds(
//                                                        new LatLng(Double.valueOf(YumApplication.getInstance().getLat())-10, Double.valueOf(YumApplication.getInstance().getLong())-10),new LatLng(Double.valueOf(YumApplication.getInstance().getLat())+10, Double.valueOf(YumApplication.getInstance().getLat())+10)),
                                                            typeFilter);
                                            if (result != null) {
                                                AutocompletePredictionBuffer autocompletePredictions = result
                                                        .await(10, TimeUnit.SECONDS);
                                                if (autocompletePredictions != null) {
                                                    final Status status = autocompletePredictions.getStatus();
                                                    if (!status.isSuccess()) {
                                                        Message message = new Message();
                                                        message.what = 0;
                                                        handler.sendMessage(message);
                                                        autocompletePredictions.release();
                                                    } else {


                                                        // Copy the results into our own data structure, because we can't hold onto the buffer.
                                                        // AutocompletePrediction objects encapsulate the API response (place ID and description).

                                                        Iterator<AutocompletePrediction> iterator = autocompletePredictions.iterator();
                                                        // ArrayList resultList = new ArrayList<>(autocompletePredictions.getCount());
                                                        ArrayList<String> list = new ArrayList<>();
                                                        while (iterator.hasNext()) {
                                                            AutocompletePrediction prediction = iterator.next();
                                                            // Get the details of this prediction and copy it into a new PlaceAutocomplete object.

                                                            list.add(prediction.getPlaceId());


                                                        }
                                                        String[] a = new String[list.size()];
                                                        for (int i = 0; i < list.size(); i++) {
                                                            a[i] = list.get(i);
                                                        }
                                                        if (a.length > 0) {
                                                            Message message = new Message();
                                                            message.what = 1;
                                                            handler.sendMessage(message);
                                                            Places.GeoDataApi.getPlaceById(mGoogleApiClient1, a)
                                                                    .setResultCallback(new ResultCallback<PlaceBuffer>() {
                                                                        @Override
                                                                        public void onResult(PlaceBuffer places) {
                                                                            if (places!=null) {
                                                                                if (places.getStatus().isSuccess() && places.getCount() > 0) {
                                                                                    for (Place item : places) {

                                                                                        SearchAddressDataBase data = new SearchAddressDataBase();
                                                                                        String address = item.getAddress().toString().replace(item.getName().toString(), "");
                                                                                        if(!TextUtils.isEmpty(address)&&address.length()>0) {
                                                                                            if (address.substring(0, 1).equals(",")) {
                                                                                                address = address.substring(1, address.length());
                                                                                            }
                                                                                        }
                                                                                        data.setAddress(address.trim());
                                                                                        data.setName(item.getName().toString());
                                                                                        data.setLatLng(item.getLatLng());
                                                                                        // data.setCity(item.getLocale().get());
                                                                                        dataList1.add(data);


                                                                                    }
                                                                                    addressAdapter.notifyDataSetChanged();
                                                                                } else {

                                                                                }
                                                                                places.release();
                                                                            }
                                                                        }
                                                                    });
                                                        }
                                                        autocompletePredictions.release();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }).start();

//
                            } else {
                                emptyBtn.setVisibility(View.GONE);
                            }
                        }
                    } else {
                            Message message = new Message();
                            message.what = 2;
                            handler.sendMessage(message);

                            locationBtn.setVisibility(View.VISIBLE);
                            myAddressLayout.setVisibility(View.VISIBLE);
                    }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        requestPermission();
        getAddress();
    }

    private void getAddress(){
        OkHttpUtils
                .get()
                .url(Constant.USER_ADDRESS)
                .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        if (AppUtile.getNetWork(e.getMessage())){
                            showMsg(getString(R.string.NETERROR));
                        }
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        if (!TextUtils.isEmpty(s)) {
                            if (AppUtile.setCode(s,SearchNearbyActivity.this)){
                                SearchNearbyBase data = JSON.parseObject(s, SearchNearbyBase.class);
                                dataList.clear();
                                if (data.getData().getUserAddressRespResults()!=null&&data.getData().getUserAddressRespResults().size()>0){
                                    for (SearchNearbyDataBase item:data.getData().getUserAddressRespResults()){
                                        dataList.add(item);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

    @OnClick({R.id.del_btn, R.id.search_btn, R.id.create_account_btn, R.id.location_btn,R.id.empty_btn})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.empty_btn:
                addressTv.setText("");
                emptyBtn.setVisibility(View.GONE);
                break;
            case R.id.del_btn://关闭
                finish();
                overridePendingTransition(R.anim.activity_open,R.anim.out);
                break;
            case R.id.search_btn://搜索

                break;
            case R.id.create_account_btn://添加地址
                if (!TextUtils.isEmpty(YumApplication.getInstance().getMyInformation().getToken())){
                    intent = new Intent(this,AddToAddressActivity.class);
                    startActivityForResult(intent,1);
                    overridePendingTransition(R.anim.activity_open,R.anim.in);
                }else{
                    intent = new Intent(this, LoginActivity.class);
                    intent.putExtra("test",true);
                    intent.putExtra("TAG",TAG);
                    startActivityForResult(intent,0);
                    overridePendingTransition(R.anim.activity_open,R.anim.in);
                }
                break;
            case R.id.location_btn://定位

                mGoogleApiClient.reconnect();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK) {
                getAddress();
            } else if (resultCode == 1001) {
                getAddress();
            }else if (resultCode == 3){
                getAddress();
                Intent intent = new Intent(this,AddToAddressActivity.class);
                startActivityForResult(intent,1);
                overridePendingTransition(R.anim.activity_open,R.anim.in);
            }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Permission to access the location is missing.", Toast.LENGTH_LONG).show();
            return;
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            String Long = mLastLocation.getLongitude() + "";
            String Lat = mLastLocation.getLatitude() + "";

            //System.out.println("Lat = " + Lat + " : " + Long);
            YumApplication.getInstance().setLat(Lat);
            YumApplication.getInstance().setLong(Long);
            setResult(RESULT_OK);
            finish();
            overridePendingTransition(R.anim.activity_open,R.anim.out);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23 && !isPermissionRequested) {

            isPermissionRequested = true;

            ArrayList<String> permissions = new ArrayList<>();
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);

            }

            if (permissions.size() == 0) {
                YumApplication.getInstance().setNoLocation(true);
                return;
            } else {
                YumApplication.getInstance().setNoLocation(false);
                requestPermissions(permissions.toArray(new String[permissions.size()]), 0);
            }
        }
    }
    /**
     * Android6.0申请权限的回调方法
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 0://刚才的识别码
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){//用户同意权限,执行我们的操作
                    System.out.println("12121212121212");
                    if (mGoogleApiClient == null) {
                        mGoogleApiClient = new GoogleApiClient.Builder(this)
                                .addConnectionCallbacks(this)
                                .addApi(LocationServices.API)
                                .build();
                    }
                    YumApplication.getInstance().setNoLocation(true);
                }else{//用户拒绝之后,当然我们也可以弹出一个窗口,直接跳转到系统设置页面
                    YumApplication.getInstance().setNoLocation(false);
                }
                break;
            default:break;
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
