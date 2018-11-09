package com.yum.two_yum.view.client;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.BaseReturn;
import com.yum.two_yum.base.SearchNearbyDataBase;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.utile.AnimationUtil;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.Utils;
import com.yum.two_yum.view.client.clientfragment.CollectionFragment;
import com.yum.two_yum.view.client.clientfragment.NearbyFragment;
import com.yum.two_yum.view.client.clientfragment.OrdersFragment;
import com.yum.two_yum.view.client.orders.OrdersDetailsActivity;
import com.yum.two_yum.view.login.LoginActivity;
import com.yum.two_yum.view.merchants.MerchantsActivity;
import com.yum.two_yum.view.my.fragment.MyFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * @author 余先德
 * @data 2018/4/7
 */

public class ClientActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks {


    @Bind(R.id.show_merchants)
    ImageView showMerchants;
    @Bind(R.id.hide_merchants)
    ImageView hideMerchants;

    @Bind(R.id.show_remind)
    ImageView showRemind;
    @Bind(R.id.hide_remind)
    ImageView hideRemind;

    @Bind(R.id.show_message)
    ImageView showMessage;
    @Bind(R.id.hide_message)
    ImageView hideMessage;

    @Bind(R.id.show_my)
    ImageView showMy;
    @Bind(R.id.hide_my)
    ImageView hideMy;

    @Bind(R.id.main_bottom)
    LinearLayout mainBottom;
    @Bind(R.id.nearby_tv)
    TextView nearbyTv;
    @Bind(R.id.favorites_tv)
    TextView favoritesTv;
    @Bind(R.id.orders_tv)
    TextView ordersTv;
    @Bind(R.id.me_tv)
    TextView meTv;
    @Bind(R.id.no_data_layout)
    View noDataLayout;
    @Bind(R.id.call_btn)
    TextView callBtn;
    @Bind(R.id.cancel_btn)
    TextView cancelBtn;
    @Bind(R.id.select_layout)
    LinearLayout selectLayout;

    private List<View> showImgs, hideImgs, textViews;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;

    private NearbyFragment nearbyFragment;
    private CollectionFragment collectionFragment;
    private OrdersFragment ordersFragment;
    private String Lat, Long;
    @Bind(R.id.popup)
    View popup;

    private MyFragment myFragment;

    private Fragment[] tabFragments; //分页的集合
    private int con = 0;//当前选中的选项卡编号
    private int currentTabIndex = 0;//当前选中标记
    private boolean isPermissionRequested;


    public static ClientActivity instance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);
        instance = this;
        ButterKnife.bind(this);
        YumApplication.getInstance().setInputType(true);



        setView();
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        }
        Utils.setStatusTextColor(true, this);
        requestPermission();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Permission to access the location is missing.", Toast.LENGTH_LONG).show();
            return;
        }
        System.out.println("fdsfsdfsdfsdfsdfsfsdfsfsdf");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                YumApplication.getInstance().setView127dp(popup.getWidth());
                YumApplication.getInstance().setViewh106dp(popup.getHeight());
            }
        }, 1000);
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            Long = mLastLocation.getLongitude() + "";
            Lat = mLastLocation.getLatitude() + "";

           //System.out.println("Lat = " + Lat + " : " + Long);
            YumApplication.getInstance().setLat(Lat);
            YumApplication.getInstance().setLong(Long);
            if (nearbyFragment != null) {
                nearbyFragment.setLatLong(Lat, Long);
                collectionFragment.setLatLong(Lat, Long);
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
                    mGoogleApiClient.reconnect();
                    YumApplication.getInstance().setNoLocation(true);
                    if (nearbyFragment!=null){
                        nearbyFragment.getData();
                    }


                }else{//用户拒绝之后,当然我们也可以弹出一个窗口,直接跳转到系统设置页面
                    YumApplication.getInstance().setNoLocation(false);
                    if (nearbyFragment!=null){
                        nearbyFragment.getData();
                    }
                }
                break;
            default:break;
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    protected void setView() {
        super.setView();

        showImgs = new ArrayList<>();
        hideImgs = new ArrayList<>();
        textViews = new ArrayList<>();
        showImgs.add(showMerchants);
        showImgs.add(showRemind);
        showImgs.add(showMessage);
        showImgs.add(showMy);
        hideImgs.add(hideMerchants);
        hideImgs.add(hideRemind);
        hideImgs.add(hideMessage);
        hideImgs.add(hideMy);
        textViews.add(nearbyTv);
        textViews.add(favoritesTv);
        textViews.add(ordersTv);
        textViews.add(meTv);

        this.nearbyFragment = new NearbyFragment();

        this.collectionFragment = new CollectionFragment();
        this.ordersFragment = new OrdersFragment();
        this.myFragment = new MyFragment();
        Fragment[] arrayOfFragment = new Fragment[4];
        arrayOfFragment[0] = this.nearbyFragment;
        arrayOfFragment[1] = this.collectionFragment;
        arrayOfFragment[2] = this.ordersFragment;
        arrayOfFragment[3] = this.myFragment;
        this.tabFragments = arrayOfFragment;
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, this.nearbyFragment)
                .add(R.id.fragment_container, this.collectionFragment)
                .add(R.id.fragment_container, this.ordersFragment)
                .add(R.id.fragment_container, this.myFragment)
                .hide(this.collectionFragment).hide(this.ordersFragment).hide(this.myFragment).show(this.nearbyFragment). commitAllowingStateLoss();
        noDataLayout.setOnClickListener(new click());
        callBtn.setOnClickListener(new click());
        cancelBtn.setOnClickListener(new click());

    }


    private class click implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.no_data_layout:
                    noDataLayout.setVisibility(View.GONE);
                    selectLayout.setVisibility(View.GONE);
                    selectLayout.setAnimation(AnimationUtil.fromNowToBottom());
                    break;
                case R.id.call_btn:
                    noDataLayout.setVisibility(View.GONE);
                    selectLayout.setVisibility(View.GONE);
                    selectLayout.setAnimation(AnimationUtil.fromNowToBottom());
                    Intent i = new Intent(Intent.ACTION_SEND);
                    // i.setType("text/plain"); //模拟器请使用这行
                    i.setType("message/rfc822"); // 真机上使用这行
                    i.putExtra(Intent.EXTRA_EMAIL,
                            new String[]{Constant.EMAIl});
                    i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.GIVEUSFEEDBACK));
                    i.putExtra(Intent.EXTRA_TEXT, "");
                    startActivity(Intent.createChooser(i,
                            "Select email application."));
                    break;
                case R.id.cancel_btn:
                    noDataLayout.setVisibility(View.GONE);
                    selectLayout.setVisibility(View.GONE);
                    selectLayout.setAnimation(AnimationUtil.fromNowToBottom());
                    break;
            }
        }
    }

    @OnClick({R.id.merchants_btn, R.id.remind_btn, R.id.message_btn, R.id.my_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.merchants_btn:
                con = 0;
                break;
            case R.id.remind_btn:
                con = 1;
                break;
            case R.id.message_btn:
                con = 2;
                break;
            case R.id.my_btn:
                con = 3;
                break;
        }

        if (currentTabIndex != con) {

            FragmentTransaction trx = getSupportFragmentManager()
                    .beginTransaction();
            trx.hide(tabFragments[currentTabIndex]);
            if (!tabFragments[con].isAdded()) {
                trx.add(R.id.fragment_container, tabFragments[con]);
            }
            trx.show(tabFragments[con]).commit();
        }
        showTab(con);
        currentTabIndex = con;
       if (con == 3){
            YumApplication.getInstance().setInputType(true);
            if (!TextUtils.isEmpty(YumApplication.getInstance().getMyInformation().getType())){
                if (YumApplication.getInstance().getMyInformation().getType().equals("1")){
                    YumApplication.getInstance().setConType(true);
                }else{
                    YumApplication.getInstance().setConType(false);
                }
                if (myFragment!=null) {
                    myFragment.setIssue(!YumApplication.getInstance().isConType());
                    myFragment.setType();
                }
            }
        }
        if (con > 0 ) {
            if (con == 1) {
                collectionFragment.setExamination();
            } else if (con == 2) {
                ordersFragment.setExamination();
            } else if (con == 3) {
                myFragment.setExamination();

            }
        }
    }

    public void setNearby(){

        mGoogleApiClient.reconnect();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            if (data != null) {
                nearbyFragment.setKeyword(data.getStringExtra("data"));
            }
        }
        if (resultCode == RESULT_OK) {
            if (requestCode == 102) {
                if (nearbyFragment != null) {
                   // nearbyFragment.RefreshData();
                    nearbyFragment.setKeywordStr("");
                    nearbyFragment.setScreeningStr("");
                    nearbyFragment.setLatLong(YumApplication.getInstance().getLat(), YumApplication.getInstance().getLong());
                    nearbyFragment.setFontNormal();
                    if (data!=null) {
                        nearbyFragment.setAddressData((SearchNearbyDataBase) data.getSerializableExtra("data"));
                        collectionFragment.setAddressData((SearchNearbyDataBase) data.getSerializableExtra("data"));
                    }
                }
            }

        }else if (resultCode == 1003){
            if (data!=null) {
                if (nearbyFragment != null) {
                    nearbyFragment.setLike(data.getIntExtra("like", -1));
                }
                if (collectionFragment != null) {
                    collectionFragment.setLike(data.getIntExtra("like", -1));
                }
            }
        }
        if (requestCode == 999&&resultCode==999){
            Intent intent = new Intent(this, MerchantsActivity.class);
            startActivity(intent);
            finish();
        }
        if (requestCode==0&&resultCode == RESULT_OK){
            if (myFragment!=null){
                myFragment.setHeadImg();
            }
        }
        if (resultCode == 3) {
            YumApplication.getInstance().setRegistered(false);
            YumApplication.getInstance().setInputType(true);
            if (myFragment!=null){
                myFragment.setHeadImg();
                myFragment.setExamination();
                if (!TextUtils.isEmpty(YumApplication.getInstance().getMyInformation().getType())) {
                    if (YumApplication.getInstance().getMyInformation().getType().equals("1")) {
                        YumApplication.getInstance().setConType(true);
                    } else {
                        YumApplication.getInstance().setConType(false);
                    }
                    myFragment.setIssue(!YumApplication.getInstance().isConType());
                    myFragment.setType();
                }
            }
            collectionFragment.setExamination();
            collectionFragment.RefreshData();
            ordersFragment.setExamination();
            ordersFragment.Refresh();
            nearbyFragment.loginResult();
            nearbyFragment.RefreshData();
        }
        if (resultCode == 1001) {
            if (data != null) {
                con = 2;
                FragmentTransaction trx = getSupportFragmentManager()
                        .beginTransaction();
                trx.hide(tabFragments[currentTabIndex]);
                if (!tabFragments[con].isAdded()) {
                    trx.add(R.id.fragment_container, tabFragments[con]);
                }
                trx.show(tabFragments[con]).commit();
                showTab(con);
                currentTabIndex = con;
                ordersFragment.Refresh();

            }
        }
        if (resultCode == 1002){
            mGoogleApiClient.reconnect();
        }
        if ( YumApplication.getInstance().isLoginNowType()){
            if (nearbyFragment!=null){
                nearbyFragment.RefreshData();
            }
            if (collectionFragment!=null){
                collectionFragment.RefreshData();
            }
            if (ordersFragment!=null){
                ordersFragment.Refresh();
            }
            if (myFragment!=null){
                myFragment.setHeadImg();
            }
            YumApplication.getInstance().setLoginNowType(false);
        }
    }
    public void clickCollection(int id,int like){
        nearbyFragment.setLike(id,like);
    }
    public void submitOk(){
        con = 2;
        FragmentTransaction trx = getSupportFragmentManager()
                .beginTransaction();
        trx.hide(tabFragments[currentTabIndex]);
        if (!tabFragments[con].isAdded()) {
            trx.add(R.id.fragment_container, tabFragments[con]);
        }
        trx.show(tabFragments[con]).commit();
        showTab(con);
        currentTabIndex = con;
        ordersFragment.Refresh();
    }
    public void backHome(){
        con = 0;
        FragmentTransaction trx = getSupportFragmentManager()
                .beginTransaction();
        trx.hide(tabFragments[currentTabIndex]);
        if (!tabFragments[con].isAdded()) {
            trx.add(R.id.fragment_container, tabFragments[con]);
        }
        trx.show(tabFragments[con]).commit();
        showTab(con);
        currentTabIndex = con;
        ordersFragment.Refresh();
    }

    /**
     * 显示和隐藏
     *
     * @param con 当前选中的选项卡
     */
    private void showTab(int con) {
        for (int i = 0; i < showImgs.size(); i++) {
            if (i == con) {
                showImgs.get(i).setVisibility(View.VISIBLE);
                hideImgs.get(i).setVisibility(View.GONE);
                ((TextView) textViews.get(i)).setTextColor(Color.parseColor("#FF3B30"));

            } else {
                showImgs.get(i).setVisibility(View.GONE);
                hideImgs.get(i).setVisibility(View.VISIBLE);
                ((TextView) textViews.get(i)).setTextColor(Color.parseColor("#484848"));
            }
        }
    }

    protected void onStart() {
//        if (mGoogleApiClient != null) {
//            mGoogleApiClient.connect();
//        }
        super.onStart();
        if (!TextUtils.isEmpty(YumApplication.getInstance().getMyInformation().getType())){
            if (YumApplication.getInstance().getMyInformation().getType().equals("1")){
                YumApplication.getInstance().setConType(true);
            }else{
                YumApplication.getInstance().setConType(false);
            }
            if (myFragment!=null) {
                myFragment.setIssue(!YumApplication.getInstance().isConType());
                myFragment.setType();
            }
        }
    }

    protected void onStop() {
        if (mGoogleApiClient != null)
            mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        YumApplication.getInstance().setInputType(true);
        if (!TextUtils.isEmpty(YumApplication.getInstance().getMyInformation().getType())){
            if (YumApplication.getInstance().getMyInformation().getType().equals("1")){
                YumApplication.getInstance().setConType(true);
            }else{
                YumApplication.getInstance().setConType(false);
            }
            if (myFragment!=null) {
                myFragment.setIssue(!YumApplication.getInstance().isConType());
                myFragment.setType();
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("onPause");
    }

    public void nearbyRefresh(){
        if (nearbyFragment!=null)
        nearbyFragment.RefreshData();
    }

    @Override
    public void showEmail() {
        super.showEmail();
        noDataLayout.setVisibility(View.VISIBLE);
        selectLayout.setVisibility(View.VISIBLE);
        selectLayout.setAnimation(AnimationUtil.fromBottomToNow());
    }
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23 && !isPermissionRequested) {

            isPermissionRequested = true;

            ArrayList<String> permissions1 = new ArrayList<>();
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){//未开启定位权限
                //开启定位权限,200是标识码
                permissions1.add(Manifest.permission.ACCESS_FINE_LOCATION);
                permissions1.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (permissions1.size() == 0) {
                YumApplication.getInstance().setNoLocation(true);
            }else{
                YumApplication.getInstance().setNoLocation(false);
            }
            ArrayList<String> permissions = new ArrayList<>();
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                    ) {

                permissions.add(Manifest.permission.READ_PHONE_STATE);
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                permissions.add(Manifest.permission.CALL_PHONE);
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }

            if (permissions.size() == 0) {
                return;
            } else {
                requestPermissions(permissions.toArray(new String[permissions.size()]), 0);
            }
        }
    }


}
