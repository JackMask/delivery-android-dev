package com.yum.two_yum.view.merchants;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.BaseReturn;
import com.yum.two_yum.base.MerchantBase;
import com.yum.two_yum.base.OrdersDistributionBase;
import com.yum.two_yum.base.ReleaseMenuInfoBase;
import com.yum.two_yum.base.input.CancelOrderBase;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.GetMerchantOrders;
import com.yum.two_yum.controller.TimerService;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.utile.AnimationUtil;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.LocationUtils;
import com.yum.two_yum.utile.Utils;
import com.yum.two_yum.view.client.ClientActivity;
import com.yum.two_yum.view.guide.ReleaseActivity;
import com.yum.two_yum.view.login.LoginActivity;
import com.yum.two_yum.view.merchants.merchantsfragment.MenuFragment;
import com.yum.two_yum.view.merchants.merchantsfragment.OrdersFragment;
import com.yum.two_yum.view.merchants.merchantsfragment.StatisticsFragment;
import com.yum.two_yum.view.my.fragment.MyFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * @author 余先德
 * @data 2018/4/7
 */

public class MerchantsActivity extends BaseActivity {

    @Bind(R.id.fragment_container)
    RelativeLayout fragmentContainer;
    @Bind(R.id.show_merchants)
    ImageView showMerchants;
    @Bind(R.id.hide_merchants)
    ImageView hideMerchants;
    @Bind(R.id.merchants_btn)
    LinearLayout merchantsBtn;
    @Bind(R.id.show_remind)
    ImageView showRemind;
    @Bind(R.id.hide_remind)
    ImageView hideRemind;
    @Bind(R.id.remind_btn)
    LinearLayout remindBtn;
    @Bind(R.id.show_message)
    ImageView showMessage;
    @Bind(R.id.hide_message)
    ImageView hideMessage;
    @Bind(R.id.message_btn)
    LinearLayout messageBtn;
    @Bind(R.id.show_my)
    ImageView showMy;
    @Bind(R.id.hide_my)
    ImageView hideMy;
    @Bind(R.id.my_btn)
    LinearLayout myBtn;
    @Bind(R.id.main_bottom)
    LinearLayout mainBottom;
    @Bind(R.id.orders_tv)
    TextView ordersTv;
    @Bind(R.id.favorites_tv)
    TextView favoritesTv;
    @Bind(R.id.nearby_tv)
    TextView nearbyTv;
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
    @Bind(R.id.point_tv)
    View pointTv;
    @Bind(R.id.cancel_layout)
    LinearLayout cancelLayout;


    @Bind(R.id.sold_btn)
    TextView soldBtn;
    @Bind(R.id.wrong_btn)
    TextView wrongBtn;
    @Bind(R.id.customer_btn)
    TextView customerBtn;
    @Bind(R.id.address_btn)
    TextView addressBtn;
    @Bind(R.id.cannot_btn)
    TextView cannotBtn;
    @Bind(R.id.cancel_cancel_btn)
    TextView cancelCancelBtn;
    @Bind(R.id.no_data_layout1)
    View noDataLayout1;

    @Bind(R.id.call_phone_btn)
    TextView callPhoneBtn;
    @Bind(R.id.sms_phone_btn)
    TextView smsPhoneBtn;
    @Bind(R.id.cancel_btn1)
    TextView cancelBtn1;
    @Bind(R.id.phone_tv)
    TextView phoneTv;
    @Bind(R.id.phone_layout)
    LinearLayout phoneLayout;
    @Bind(R.id.no_data_layout2)
    View noDataLayout2;


    @Bind(R.id.cancel_btn2)
    TextView cancelBtn2;
    @Bind(R.id.ok_btn)
    TextView okBtn;
    @Bind(R.id.ok_layout)
    LinearLayout okLayout;
    @Bind(R.id.no_data_layout3)
    View noDataLayout3;


    private List<View> showImgs, hideImgs, textViews;
    private OrdersFragment ordersFragment;
    private MenuFragment menuFragment;
    private StatisticsFragment statisticsFragment;


    private MyFragment myFragment;
    private boolean menuType = true;

    private Fragment[] tabFragments; //分页的集合
    private int con = 0;//当前选中的选项卡编号
    private int currentTabIndex = 0;//当前选中标记
    private boolean releaseType = false;
    public static MerchantsActivity instance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_merchants_home);
        instance = this;
        ButterKnife.bind(this);
//        if (!AppUtile.isServiceRunning(this))
//            getConnet();
        releaseType = YumApplication.getInstance().isReleaseType();
        setBtnInfo();
        setView();
        Utils.setStatusTextColor(true, this);

    }


    @Override
    protected void setView() {
        super.setView();
        YumApplication.getInstance().setInputType(false);
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
        textViews.add(ordersTv);
        textViews.add(favoritesTv);
        textViews.add(nearbyTv);
        textViews.add(meTv);
        this.ordersFragment = new OrdersFragment();
        this.menuFragment = new MenuFragment();
        this.statisticsFragment = new StatisticsFragment();
        this.myFragment = new MyFragment();
        Fragment[] arrayOfFragment = new Fragment[4];
        arrayOfFragment[0] = this.ordersFragment;
        arrayOfFragment[1] = this.menuFragment;
        arrayOfFragment[2] = this.statisticsFragment;
        arrayOfFragment[3] = this.myFragment;
        this.tabFragments = arrayOfFragment;


        if (releaseType) {
            YumApplication.getInstance().setReleaseType(false);
            con = 1;
            currentTabIndex = 1;
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, this.ordersFragment)
                    .add(R.id.fragment_container, this.menuFragment)
                    .add(R.id.fragment_container, this.statisticsFragment)
                    .add(R.id.fragment_container, this.myFragment)
                    .hide(this.ordersFragment).hide(this.statisticsFragment).hide(this.myFragment).show(this.menuFragment).commit();
            menuFragment.refreshInfo(false,-1);
            showTab(con);

        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, this.ordersFragment)
                    .add(R.id.fragment_container, this.menuFragment)
                    .add(R.id.fragment_container, this.statisticsFragment)
                    .add(R.id.fragment_container, this.myFragment)
                    .hide(this.menuFragment).hide(this.statisticsFragment).hide(this.myFragment).show(this.ordersFragment).commit();
        }
        noDataLayout.setOnClickListener(new click());
        callBtn.setOnClickListener(new click());
        cancelBtn.setOnClickListener(new click());
        cancelCancelBtn.setOnClickListener(new click());
        cannotBtn.setOnClickListener(new click());
        addressBtn.setOnClickListener(new click());
        customerBtn.setOnClickListener(new click());
        wrongBtn.setOnClickListener(new click());
        soldBtn.setOnClickListener(new click());

        callPhoneBtn.setOnClickListener(new click());
        smsPhoneBtn.setOnClickListener(new click());
        cancelBtn1.setOnClickListener(new click());
        noDataLayout2.setOnClickListener(new click());

        cancelBtn2.setOnClickListener(new click());
        okBtn.setOnClickListener(new click());
        noDataLayout3.setOnClickListener(new click());
        noDataLayout1.setOnClickListener(new click());
        refreshInfo();
    }



    String id;
    int fragmentNum = -1;
    public void showCancel(int con ,String id) {
        this.id = id;
        fragmentNum = con;
        noDataLayout1.setVisibility(View.VISIBLE);
        cancelLayout.setVisibility(View.VISIBLE);
        cancelLayout.setAnimation(AnimationUtil.fromBottomToNow());
    }
    String phoneNum;
    public void showPhone(String phoneNum){
        phoneTv.setText(phoneNum);
        this.phoneNum = phoneNum;
        noDataLayout2.setVisibility(View.VISIBLE);
        phoneLayout.setVisibility(View.VISIBLE);
        phoneLayout.setAnimation(AnimationUtil.fromBottomToNow());
    }
    String okId;
    public void showOk(int con,String okId){
        fragmentNum = con;
        this.okId = okId;
        noDataLayout3.setVisibility(View.VISIBLE);
        okLayout.setVisibility(View.VISIBLE);
        okLayout.setAnimation(AnimationUtil.fromBottomToNow());
    }

    boolean newOrder = false;
    boolean distribution = false;
    public void showDian() {
        if (newOrder||distribution) {
            pointTv.setVisibility(View.VISIBLE);
        }else{
            pointTv.setVisibility(View.GONE);
        }
    }
    public void setDistribution(boolean con){
        distribution = con;
    }
   public void setNewOrder(boolean con){
       newOrder = con;
   }

    private class click implements View.OnClickListener {

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.no_data_layout3:
                    noDataLayout3.setVisibility(View.GONE);
                    okLayout.setVisibility(View.GONE);
                    okLayout.setAnimation(AnimationUtil.fromNowToBottom());
                    break;
                case R.id.ok_btn:
                    noDataLayout3.setVisibility(View.GONE);
                    okLayout.setVisibility(View.GONE);
                    okLayout.setAnimation(AnimationUtil.fromNowToBottom());
                    CancelOrderBase inputData = new CancelOrderBase();
                    inputData.setOrderId(okId);
                    inputData.setOrderStatus("COMPLETE");
                    String content = JSON.toJSONString(inputData);
                    showLoading();
                    OkHttpUtils
                            .postString()
                            .url(Constant.MERCHANT_ORDER_SETUP)
                            .addHeader("Accept-Language", AppUtile.getLanguage())
                            .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                            .mediaType(MediaType.parse("application/json"))
                            .content(content)
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
                                public void onResponse(String s, int i) {
                                    //okBtn.setText(getResources().getString(R.string.DELIVER));
                                    if (AppUtile.setCode(s,MerchantsActivity.this)){
                                        if (ordersFragment != null) {
                                            ordersFragment.refreshChildView(fragmentNum);
                                        }
                                    }
                                }
                            });
                    break;
                case R.id.cancel_btn2:
                    noDataLayout3.setVisibility(View.GONE);
                    okLayout.setVisibility(View.GONE);
                    okLayout.setAnimation(AnimationUtil.fromNowToBottom());
                    break;
                case R.id.cancel_btn1:
                    noDataLayout2.setVisibility(View.GONE);
                    phoneLayout.setVisibility(View.GONE);
                    phoneLayout.setAnimation(AnimationUtil.fromNowToBottom());
                    break;
                case R.id.call_phone_btn:
                    noDataLayout2.setVisibility(View.GONE);
                    phoneLayout.setVisibility(View.GONE);
                    phoneLayout.setAnimation(AnimationUtil.fromNowToBottom());
                    Intent intent = new Intent(); // 意图对象：动作 + 数据
                    intent.setAction(Intent.ACTION_CALL); // 设置动作
                    Uri data = Uri.parse("tel:" + phoneNum); // 设置数据
                    intent.setData(data);
                    startActivity(intent); // 激活Activity组件
                    break;
                case R.id.sms_phone_btn:
                    noDataLayout2.setVisibility(View.GONE);
                    phoneLayout.setVisibility(View.GONE);
                    phoneLayout.setAnimation(AnimationUtil.fromNowToBottom());
                    Uri uri = Uri.parse("smsto:"+phoneNum);
                    Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                    startActivity(it);
                    break;
                case R.id.no_data_layout2:
                    noDataLayout2.setVisibility(View.GONE);
                    phoneLayout.setVisibility(View.GONE);
                    phoneLayout.setAnimation(AnimationUtil.fromNowToBottom());
                case R.id.no_data_layout1:
                    noDataLayout1.setVisibility(View.GONE);
                    cancelLayout.setVisibility(View.GONE);
                    cancelLayout.setAnimation(AnimationUtil.fromNowToBottom());
                    break;
                case R.id.sold_btn:
                    showLoading();
                    noDataLayout1.setVisibility(View.GONE);
                    cancelLayout.setVisibility(View.GONE);
                    cancelLayout.setAnimation(AnimationUtil.fromNowToBottom());
                    cancelOreder(id,"售完");
//                    if (ordersFragment!=null){
//                        ordersFragment.cancelClick(id,"售完");
//                    }
                    break;
                case R.id.wrong_btn:
                    showLoading();
                    noDataLayout1.setVisibility(View.GONE);
                    cancelLayout.setVisibility(View.GONE);
                    cancelLayout.setAnimation(AnimationUtil.fromNowToBottom());
                        cancelOreder(id,"错误订单");
                    break;
                case R.id.customer_btn:
                    showLoading();
                    noDataLayout1.setVisibility(View.GONE);
                    cancelLayout.setVisibility(View.GONE);
                    cancelLayout.setAnimation(AnimationUtil.fromNowToBottom());
                        cancelOreder(id,"客户要求取消");
                    break;
                case R.id.address_btn:
                    showLoading();
                    noDataLayout1.setVisibility(View.GONE);
                    cancelLayout.setVisibility(View.GONE);
                    cancelLayout.setAnimation(AnimationUtil.fromNowToBottom());
                        cancelOreder(id,"地址不正确");
                    break;
                case R.id.cannot_btn:
                    showLoading();
                    noDataLayout1.setVisibility(View.GONE);
                    cancelLayout.setVisibility(View.GONE);
                    cancelLayout.setAnimation(AnimationUtil.fromNowToBottom());
                        cancelOreder(id,"无法联系");
                    break;
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
                case R.id.cancel_cancel_btn:
                    noDataLayout1.setVisibility(View.GONE);
                    cancelLayout.setVisibility(View.GONE);
                    cancelLayout.setAnimation(AnimationUtil.fromNowToBottom());
                    break;
                case R.id.cancel_btn:
                    noDataLayout.setVisibility(View.GONE);
                    selectLayout.setVisibility(View.GONE);
                    selectLayout.setAnimation(AnimationUtil.fromNowToBottom());
                    break;
            }
        }
    }
    public void cancelOreder(String OrderId,String note){
        CancelOrderBase inputData = new CancelOrderBase();
        inputData.setCancelNote(note);
        inputData.setOrderId(OrderId);
        inputData.setOrderStatus("CANCEL");
        String content = JSON.toJSONString(inputData);
        showLoading();
        OkHttpUtils
                .postString()
                .url(Constant.MERCHANT_ORDER_SETUP)
                .addHeader("Accept-Language", AppUtile.getLanguage())
                .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                .mediaType(MediaType.parse("application/json"))
                .content(content)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        dismissLoading();
                        if (AppUtile.getNetWork(e.getMessage())){
                            showMsg(getString(R.string.NETERROR));
                        }
                    }

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onResponse(String s, int i) {
                        if (AppUtile.setCode(s,MerchantsActivity.this)) {
                            if (ordersFragment != null) {
                                ordersFragment.refreshChildView(fragmentNum);
                            }
                        }
//                        if (AppUtile.setCode(s,MerchantsActivity.this)) {
//                            page = 1;
//                            webData();
//                        }
                    }
                });
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
            YumApplication.getInstance().setInputType(false);
            if (!TextUtils.isEmpty(YumApplication.getInstance().getMyInformation().getType())) {
                if (YumApplication.getInstance().getMyInformation().getType().equals("1")) {
                    YumApplication.getInstance().setConType(true);
                } else {
                    YumApplication.getInstance().setConType(false);
                }
                if (myFragment != null) {
                    myFragment.setIssue(!YumApplication.getInstance().isConType());
                    myFragment.setType();
                }
            }
        }
//        if (con == 1 && menuType) {
//            Intent intent = new Intent(this, ReleaseActivity.class);
//            startActivity(intent);
//            overridePendingTransition(R.anim.activity_open, R.anim.in);
//        }
        if (TextUtils.isEmpty(YumApplication.getInstance().getMyInformation().getToken())) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, 0);
            overridePendingTransition(R.anim.activity_open, R.anim.in);
        }
        if (con==1){
            if (menuFragment!=null){
                menuFragment.refreshInfo(false,-1);
            }
        }
    }

    private boolean conType = true;
    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocationUtils.getInstance(this).removeLocationUpdatesListener();
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

    public void setBtnInfo() {
        OkHttpUtils
                .get()
                .url(Constant.MENU_INFO)
                .addHeader("Accept-Language", AppUtile.getLanguage())
                .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                .addParams("userId", YumApplication.getInstance().getMyInformation().getUid())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        //System.out.println("e = "+e.getMessage());;
                        if (AppUtile.getNetWork(e.getMessage())){
                            showMsg(getString(R.string.NETERROR));
                        }
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        if (!TextUtils.isEmpty(s)) {

                            if (AppUtile.setCode(s,MerchantsActivity.this)) {
                                ReleaseMenuInfoBase data = JSON.parseObject(s, ReleaseMenuInfoBase.class);
                                boolean con = false;
                                if (data.getData().getCert() < 2) {//证件
                                    con = true;
                                } else if (data.getData().getBank() < 2) {
                                    con = true;
                                } else if (data.getData().getMerchant() < 2) {
                                    con = true;
                                } else if (data.getData().getMerchantMenu() < 2) {
                                    con = true;
                                }
                                if (con) {
                                    menuType = true;
                                    //setSwitchingTvText();
                                } else {
                                    menuType = false;
                                    //switchingTv.setText(getString(R.string.LISTYOURMENU));
                                }

                            }
                        }
                    }
                });
    }

    public void setOpenOrders(String noticeState) {
        OkHttpUtils
                .post()
                .url(Constant.MERCHANT_AUTOMATION_ACCEPT)
                .addHeader("Accept-Language", AppUtile.getLanguage())
                .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                .addParams("noticeState", noticeState)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        if (AppUtile.getNetWork(e.getMessage())){
                            showMsg(getString(R.string.NETERROR));
                        }
                    }

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onResponse(String s, int i) {
                        if (!TextUtils.isEmpty(s)) {
                            if (AppUtile.setCode(s,MerchantsActivity.this)) {
                                ordersFragment.openSwitch1();
                            }
                        }
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10){
            if (menuFragment!=null){
                menuFragment.getData();
            }
        }else if (requestCode == 999){
            if (ordersFragment!=null){
                ordersFragment.refreshTwo();
            }
        }else if (resultCode==999){
            if (menuFragment!=null){
                menuFragment.refreshInfo(false,-1);
            }
        }
        if (requestCode == 12) {
            if (resultCode == 1001) {
                setOpenOrders("1");
                //ordersFragment.openSwitch1();
            }
        } else if (resultCode == 1001) {
            if (requestCode == 100) {
                OkHttpUtils
                        .post()
                        .url(Constant.MENU_STOP)
                        .addHeader("Accept-Language", AppUtile.getLanguage())
                        .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                        .addParams("userId", YumApplication.getInstance().getMyInformation().getUid())
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
                                    if (AppUtile.setCode(s,MerchantsActivity.this)) {
                                        if (menuFragment != null) {
                                            YumApplication.getInstance().getpBankAccount().setAccount("");
                                            YumApplication.getInstance().getpBankAccount().setCode("");
                                            YumApplication.getInstance().getpBankAccount().setName("");
                                            YumApplication.getInstance().getpBankAccount().setType(-1);
                                            YumApplication.getInstance().getpBankAccount().setCity("");
                                            YumApplication.getInstance().getpBankAccount().setCountryId("1");
                                            YumApplication.getInstance().getpBankAccount().setCountry("");
                                            YumApplication.getInstance().getpBankAccount().setProvince("");
                                            YumApplication.getInstance().getpBankAccount().setRoomNo("");
                                            YumApplication.getInstance().getpBankAccount().setStreet("");
                                            YumApplication.getInstance().getpBankAccount().setZipCode("");
                                            YumApplication.getInstance().getpBankAccount().setUserId("");
                                            YumApplication.getInstance().getpOperationsSettings().setEndTime("");
                                            YumApplication.getInstance().getpOperationsSettings().setKeyword("");
                                            YumApplication.getInstance().getpOperationsSettings().setMap("");
                                            YumApplication.getInstance().getpOperationsSettings().setMoney("6.99");
                                            YumApplication.getInstance().getpOperationsSettings().setName("");
                                            YumApplication.getInstance().getpOperationsSettings().setRange("5.0");
                                            YumApplication.getInstance().getpOperationsSettings().setSeries("");
                                            YumApplication.getInstance().getpOperationsSettings().setStartTime("");
                                            YumApplication.getInstance().getpOperationsSettings().setTime("");
                                            YumApplication.getInstance().getpOperationsSettings().setUserId("");
                                            YumApplication.getInstance().getpOperationsSettings().setTaxRate("");
                                            YumApplication.getInstance().getpOperationsSettings().setLng("0.0");
                                            YumApplication.getInstance().getpOperationsSettings().setLat("0.0");
                                            YumApplication.getInstance().getpOperationsSettings().setCurrencyId(1);
                                            YumApplication.getInstance().getpOperationsSettings().setCountrySymbol("");
                                            YumApplication.getInstance().getpOperationsSettings().setCurrencyName("");
                                            YumApplication.getInstance().getpOperationsSettings().setCurrencyFlag("");
                                            YumApplication.getInstance().getpOperationsSettings().setCountry("");
                                            YumApplication.getInstance().getpOperationsSettings().setCountryId("1");
                                            YumApplication.getInstance().getpOperationsSettings().setProvince("");
                                            YumApplication.getInstance().getpOperationsSettings().setRoomNo("");
                                            YumApplication.getInstance().getpOperationsSettings().setZipCode("");
                                            YumApplication.getInstance().getpOperationsSettings().setCity("");
                                            YumApplication.getInstance().getpOperationsSettings().setStreet("");
                                            YumApplication.getInstance().getpYourId().setCertNo("");
                                            YumApplication.getInstance().getpYourId().setCertType("");
                                            YumApplication.getInstance().getpYourId().setCertUrl("");
                                            YumApplication.getInstance().getpYourId().setEmail("");
                                            YumApplication.getInstance().getpYourId().setGender("");
                                            YumApplication.getInstance().getpYourId().setUserId("");
                                            YumApplication.getInstance().getpYourId().setName("");
                                            YumApplication.getInstance().getpYourId().setPhone("");
                                            YumApplication.getInstance().getpYourId().setCity("");
                                            YumApplication.getInstance().getpYourId().setProvince("");
                                            YumApplication.getInstance().getpYourId().setOkCode("");
                                            YumApplication.getInstance().getpYourId().setRoomNo("");
                                            YumApplication.getInstance().getpYourId().setStreet("");
                                            YumApplication.getInstance().getpYourId().setZipCode("");
                                            YumApplication.getInstance().getpYourId().setCountry("");
                                            YumApplication.getInstance().getpYourId().setCountryId("1");
                                            menuFragment.stopMenu();
                                        }
                                    }
                                }
                            }
                        });
            } else if (requestCode == 101) {
                OkHttpUtils
                        .post()
                        .url(Constant.MENU_ISSUE)
                        .addHeader("Accept-Language", AppUtile.getLanguage())
                        .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                        .addParams("userId", YumApplication.getInstance().getMyInformation().getUid())
                        .addParams("issueType", "1")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int i) {
                                if (menuFragment != null) {
                                    menuFragment.close();
                                }
                                if (AppUtile.getNetWork(e.getMessage())){
                                    showMsg(getString(R.string.NETERROR));
                                }
                            }

                            @Override
                            public void onResponse(String s, int i) {
                                if (!TextUtils.isEmpty(s)) {
                                    if (AppUtile.setCode(s,MerchantsActivity.this)) {
                                        if (menuFragment != null) {
                                            menuFragment.open();
                                        }
                                    }
                                }
                            }
                        });

            } else if (requestCode == 102) {
                OkHttpUtils
                        .post()
                        .url(Constant.MENU_ISSUE)
                        .addHeader("Accept-Language", AppUtile.getLanguage())
                        .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                        .addParams("userId", YumApplication.getInstance().getMyInformation().getUid())
                        .addParams("issueType", "2")
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
                                    if (AppUtile.setCode(s,MerchantsActivity.this)) {
                                        if (menuFragment != null) {
                                            menuFragment.close();
                                        }
                                        // vSwitch1.setOpened(false);
                                    }
                                }
                            }
                        });
            } else if (requestCode == 1){
                if (menuFragment!=null){
                    menuFragment.getData();
                }
            }else {
                Uri uri = Uri.parse("market://details?id=com.google.android.apps.maps");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }

        }

        if (resultCode == RESULT_OK) {
            if (myFragment != null) {
                myFragment.setHeadImg();
            }
            if (menuFragment != null) {
                menuFragment.refresh();
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_open, R.anim.o);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!TextUtils.isEmpty(YumApplication.getInstance().getMyInformation().getType())) {
            if (YumApplication.getInstance().getMyInformation().getType().equals("1")) {
                YumApplication.getInstance().setConType(true);
            } else {
                YumApplication.getInstance().setConType(false);
            }
            if (myFragment != null) {
                myFragment.setIssue(!YumApplication.getInstance().isConType());
                myFragment.setType();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        YumApplication.getInstance().setInputType(false);
        if (!TextUtils.isEmpty(YumApplication.getInstance().getMyInformation().getType())) {
            if (YumApplication.getInstance().getMyInformation().getType().equals("1")) {
                YumApplication.getInstance().setConType(true);
            } else {
                YumApplication.getInstance().setConType(false);
            }
        }
        if (myFragment != null) {
            myFragment.setIssue(!YumApplication.getInstance().isConType());
            myFragment.setType();
        }

        //System.out.println("onResume");
    }

    @Override
    public void showEmail() {
        super.showEmail();
        noDataLayout.setVisibility(View.VISIBLE);
        selectLayout.setVisibility(View.VISIBLE);
        selectLayout.setAnimation(AnimationUtil.fromBottomToNow());
    }


    public void refreshInfo(){
        OkHttpUtils
                .get()
                .url(Constant.MERCHANT_HOME)
                .addHeader("Accept-Language", AppUtile.getLanguage())
                .addHeader("YUM-AUTH-TOKEN",YumApplication.getInstance().getMyInformation().getToken())
                .addParams("userId",YumApplication.getInstance().getMyInformation().getUid())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {

                    }

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onResponse(String s, int con) {
                        if (!TextUtils.isEmpty(s)) {

                            if (AppUtile.setCode(s,MerchantsActivity.this)) {
                                MerchantBase merchantBase = JSON.parseObject(s, MerchantBase.class);
                                if (merchantBase.getData() != null) {
                                    if (merchantBase.getData().getNoticeState() == 1) {
                                        if (ordersFragment != null) {
                                            ordersFragment.setSwitch(true);
                                        }
                                    } else {
                                        if (ordersFragment != null) {
                                            ordersFragment.setSwitch(false);
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
    }

}
