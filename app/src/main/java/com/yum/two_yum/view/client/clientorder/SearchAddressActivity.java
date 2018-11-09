package com.yum.two_yum.view.client.clientorder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.CharacterStyle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.GoogleBase;
import com.yum.two_yum.base.SearchAddressDataBase;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.controller.adapter.SearchAddressAdapter;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.ContainsEmojiEditText;
import com.yum.two_yum.utile.pulltorefresh.PullToRefreshBase;
import com.yum.two_yum.utile.pulltorefresh.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * @author 余先德
 * @data 2018/4/9
 */

public class SearchAddressActivity extends BaseActivity implements  GoogleApiClient.OnConnectionFailedListener  {

    @Bind(R.id.search_et)
    ContainsEmojiEditText searchEt;
    @Bind(R.id.address_lv)
    PullToRefreshListView addressLv;
    @Bind(R.id.empty_btn)
    ImageView emptyBtn;
    private static final int GOOGLE_API_CLIENT_ID = 0;

    private List<SearchAddressDataBase> dataList = new ArrayList<>();
    private SearchAddressAdapter addressAdapter;
    private GoogleApiClient mGoogleApiClient;
    private boolean isPermissionRequested;
    private String latStr,lngStr;
    private String addressStr;
    private String nameStr;

    private android.os.Handler handler = new android.os.Handler() {


        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            addressAdapter.notifyDataSetChanged();

        }

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_address);
        ButterKnife.bind(this);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("type"))){
            searchEt.setHint("");
        }
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    //.addConnectionCallbacks(this)
                    .enableAutoManage(this, GOOGLE_API_CLIENT_ID /* clientId */, this)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    //.addApi(LocationServices.API)
                    .build();
        }

        setView();
    }
    private void updateWithNewLocation(String lat,String lng) {


        String url = String.format(

                "https://maps.google.cn/maps/api/geocode/json?latlng=%s,%s&sensor=false&language=%s&key=%s",
                lat, lng, AppUtile.getLanguageIntSetAddress(), Constant.GOOGLEAPI);
        showLoading();
        sendLocationAdressRequest(url);
    }
    @Override
    protected void setView() {
        super.setView();
        Timer timer = new Timer();
        timer.schedule(new TimerTask()   {
            public void run() {
                InputMethodManager inputManager = (InputMethodManager)searchEt.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(searchEt, 0);
            }
        }, 300);

        addressAdapter = new SearchAddressAdapter(dataList);
        addressAdapter.setItemClick(new SearchAddressAdapter.ItemClick() {
            @Override
            public void click(String lat, String lng, String address,String name) {
                latStr = lat;
                lngStr = lng;
                //addressStr = address.replace(name, "");
                nameStr = name;
                addressStr = address;
                Intent intent = new Intent();
                //intent.putExtra("city",cityStr);
                //intent.putExtra("province",province);
                //intent.putExtra("zip",zip);
                intent.putExtra("name",nameStr);
                intent.putExtra("lat",latStr);
                intent.putExtra("lng",lngStr);
                intent.putExtra("address",nameStr+addressStr.replace("\n",""));
                intent.putExtra("address1",addressStr.replace("\n",""));
                intent.putExtra("address2",nameStr+","+addressStr.replace("\n",""));
                setResult(RESULT_OK,intent);
                finish();
                overridePendingTransition(R.anim.activity_open,R.anim.in);
               // updateWithNewLocation(lat,lng);

            }
        });
        addressLv.setMode(PullToRefreshBase.Mode.DISABLED);
        addressLv.setAdapter(addressAdapter);
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                //LatLng a = new LatLng()
                if (mGoogleApiClient.isConnected()) {
                    //     LatLngBounds data = new LatLngBounds(oldLatLng, newLatLng);
                    if (!TextUtils.isEmpty(s.toString())) {
                        emptyBtn.setVisibility(View.VISIBLE);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                dataList.clear();
                                AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                                        .setTypeFilter(AutocompleteFilter.TYPE_FILTER_NONE)
                                        .build();
                                PendingResult<AutocompletePredictionBuffer> result =
                                        Places.GeoDataApi.getAutocompletePredictions(mGoogleApiClient, (String) s.toString(),null,
//                                                new LatLngBounds(
//                                                        new LatLng(Double.valueOf(YumApplication.getInstance().getLat())-10, Double.valueOf(YumApplication.getInstance().getLong())-10),new LatLng(Double.valueOf(YumApplication.getInstance().getLat())+10, Double.valueOf(YumApplication.getInstance().getLat())+10)),
                                                typeFilter);

                                AutocompletePredictionBuffer autocompletePredictions = result
                                        .await(1, TimeUnit.SECONDS)
                                        ;
                                final Status status = autocompletePredictions.getStatus();
                                if (!status.isSuccess()) {
                                    handler.sendMessage(new Message());
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
                                    for (int i = 0; i < list.size();i++){
                                        a[i]=list.get(i);
                                    }
                                    if (a.length>0) {
                                        Places.GeoDataApi.getPlaceById(mGoogleApiClient, a)
                                                .setResultCallback(new ResultCallback<PlaceBuffer>() {
                                                    @Override
                                                    public void onResult(PlaceBuffer places) {

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
                                                                dataList.add(data);
                                                            }
                                                            addressAdapter.notifyDataSetChanged();
                                                        } else {

                                                        }
                                                        places.release();
                                                    }
                                                });
                                    }
                                    autocompletePredictions.release();
                                }
                            }
                        }).start();

//
                    }else{
                        emptyBtn.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        addressLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        requestPermission();
    }

    @OnClick({R.id.del_btn, R.id.empty_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.del_btn:
                finish();
                overridePendingTransition(R.anim.activity_open,R.anim.in);
                break;
            case R.id.empty_btn:
                searchEt.setText("");
                break;
        }
    }



    private void sendLocationAdressRequest(String address) {
        OkHttpUtils
                .post()
                .url(address)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        dismissLoading();
                        if (AppUtile.getNetWork(e.getMessage())){
                            showMsg(getString(R.string.NETERROR));
                        }
                    }

                    @Override
                    public void onResponse(String s, int con) {
                        dismissLoading();
                        if (!TextUtils.isEmpty(s)){
                            GoogleBase data  = JSON.parseObject(s,GoogleBase.class);
                            if (data.getStatus().equals("OK")){
                                String cityStr = "",province = "",zip ="" ;
                                if (data.getResults()!=null&&data.getResults().size()>0){
                                    if ( data.getResults().get(0).getAddress_components()!=null&& data.getResults().get(0).getAddress_components().size()>0){
                                        for (int i =0 ;i < data.getResults().get(0).getAddress_components().size();i++){
                                            if (data.getResults().get(0).getAddress_components().get(i).getTypes()!=null&&data.getResults().get(0).getAddress_components().get(i).getTypes().size()>0){
                                                if (data.getResults().get(0).getAddress_components().get(i).getTypes().get(0).equals("locality")){//城市
                                                    cityStr = data.getResults().get(0).getAddress_components().get(i).getLong_name();
                                                }
                                                if (data.getResults().get(0).getAddress_components().get(i).getTypes().get(0).equals("administrative_area_level_1")){//州/省
                                                    province = data.getResults().get(0).getAddress_components().get(i).getLong_name();
                                                }
                                                if (data.getResults().get(0).getAddress_components().get(i).getTypes().get(0).equals("postal_code")){//邮编
                                                    zip = data.getResults().get(0).getAddress_components().get(i).getLong_name();
                                                }
                                            }
                                        }
                                    }
                                }
                                Intent intent = new Intent();
                                intent.putExtra("city",cityStr);
                                intent.putExtra("province",province);
                                intent.putExtra("zip",zip);
                                intent.putExtra("name",nameStr);
                                intent.putExtra("lat",latStr);
                                intent.putExtra("lng",lngStr);
                                intent.putExtra("address",addressStr);
                                setResult(RESULT_OK,intent);
                                finish();
                                overridePendingTransition(R.anim.activity_open,R.anim.in);
                            }
                        }
                    }
                });

    }


                    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23 && !isPermissionRequested) {

            isPermissionRequested = true;

            ArrayList<String> permissions = new ArrayList<>();
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);

            }

            if (permissions.size() == 0) {
                return;
            } else {
                requestPermissions(permissions.toArray(new String[permissions.size()]), 0);
            }
        }
    }
}
