package com.yum.two_yum.view.guide;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.IntentCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BankBase;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.BaseReturn;
import com.yum.two_yum.base.CertBase;
import com.yum.two_yum.base.FilterBase;
import com.yum.two_yum.base.GetMerchantMenuListBase;
import com.yum.two_yum.base.MerchantBase;
import com.yum.two_yum.base.ReleaseMenuInfoBase;
import com.yum.two_yum.base.SelectCountryBase;
import com.yum.two_yum.base.input.BankInput;
import com.yum.two_yum.base.input.CertInput;
import com.yum.two_yum.base.input.MerchantInput;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.Utils;
import com.yum.two_yum.view.WelcomeActivity;
import com.yum.two_yum.view.client.ClientActivity;
import com.yum.two_yum.view.client.business.BusinessDetailsActivity;
import com.yum.two_yum.view.client.orders.OrdersDetailsActivity;
import com.yum.two_yum.view.guide.bank.Bank5Activity;
import com.yum.two_yum.view.guide.document.Document1Activity;
import com.yum.two_yum.view.guide.operations.Operations7Activity;
import com.yum.two_yum.view.login.RegisteredActivity;
import com.yum.two_yum.view.merchants.MerchantsActivity;
import com.yum.two_yum.view.merchants.menu.BankAccountActivity;
import com.yum.two_yum.view.merchants.menu.NowMenuActivity;
import com.yum.two_yum.view.merchants.menu.OperationsSettingsActivity;
import com.yum.two_yum.view.merchants.menu.PreviewMenuActivity;
import com.yum.two_yum.view.merchants.menu.YourIdActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @author 余先德
 * @data 2018/4/14
 */

public class ReleaseActivity extends BaseActivity {

    @Bind(R.id.your_id)
    TextView yourId;
    @Bind(R.id.ok_iv)
    ImageView okIv;
    @Bind(R.id.continue_btn)
    TextView continueBtn;
    @Bind(R.id.bank_account_tv)
    TextView bankAccountTv;
    @Bind(R.id.ok_iv1)
    ImageView okIv1;
    @Bind(R.id.continue_btn1)
    TextView continueBtn1;
    @Bind(R.id.operation_settings)
    TextView operationSettings;
    @Bind(R.id.ok_iv2)
    ImageView okIv2;
    @Bind(R.id.continue_btn2)
    TextView continueBtn2;
    @Bind(R.id.now_menu)
    TextView nowMenu;
    @Bind(R.id.ok_iv3)
    ImageView okIv3;
    @Bind(R.id.continue_btn3)
    TextView continueBtn3;
    @Bind(R.id.btn_layout)
    RelativeLayout btnLayout;
    @Bind(R.id.btn_view_1)
    View btnView1;
    @Bind(R.id.btn_view_2)
    View btnView2;
    @Bind(R.id.btn_view_3)
    View btnView3;
    @Bind(R.id.btn_view_4)
    View btnView4;

    private boolean btnType1= false,btnType2= false,btnType3= false;

    private List<SelectCountryBase.DataBean.CountryRespResultListBean> countryList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_guide);
        ButterKnife.bind(this);
        Utils.setStatusTextColor(false,this);
        getData();
       // getInfo();
    }

    private void getData(){
        OkHttpUtils
                .get()
                .url(Constant.COUNTRY_ALL)
                .addHeader("Accept-Language", AppUtile.getLanguage())
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
                    public void onResponse(String s, int b) {
                        if (!TextUtils.isEmpty(s)){

                           if (AppUtile.setCode(s,ReleaseActivity.this)){
                               SelectCountryBase data = JSON.parseObject(s,SelectCountryBase.class);
                               if (data.getData()!=null&&data.getData().getCountryRespResultList()!=null&&data.getData().getCountryRespResultList().size()>0){
                                   for (int i = 0 ; i < data.getData().getCountryRespResultList().size();i++){
                                       countryList.add(data.getData().getCountryRespResultList().get(i));
                                   }
                               }
                           }
                        }

                    }
                });
        OkHttpUtils
                .get()
                .url(Constant.MENU_INFO)
                .addHeader("Accept-Language", AppUtile.getLanguage())
                .addHeader("YUM-AUTH-TOKEN",YumApplication.getInstance().getMyInformation().getToken())
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
                        System.out.println("s = " + s);
                        if (!TextUtils.isEmpty(s)){

                            if (AppUtile.setCode(s,ReleaseActivity.this)){
                                ReleaseMenuInfoBase data = JSON.parseObject(s,ReleaseMenuInfoBase.class);
                                boolean con = true;

                                if (data.getData().getCert()==0&&con){//证件
                                    con = false;
                                    continueBtn.setVisibility(View.VISIBLE);
                                    okIv.setVisibility(View.GONE);
                                    btnType1 = false;
                                     btnView1.setVisibility(View.GONE);
                                    yourId.setTextColor(Color.parseColor("#484848"));
                                }else if (data.getData().getCert()==1){
                                    con = false;
                                    continueBtn.setVisibility(View.VISIBLE);
                                    okIv.setVisibility(View.GONE);
                                    btnView1.setVisibility(View.GONE);
                                    btnType1 = false;
                                    yourId.setTextColor(Color.parseColor("#484848"));
                                }else if (data.getData().getCert()==2){
                                    continueBtn.setVisibility(View.GONE);
                                    okIv.setVisibility(View.VISIBLE);
                                    btnType1 = true;
                                    btnView1.setVisibility(View.VISIBLE);
                                    yourId.setTextColor(Color.parseColor("#484848"));
                                }

                                if (data.getData().getBank() == 0&&con){//银行
                                    con = false;
                                    continueBtn1.setVisibility(View.VISIBLE);
                                    okIv1.setVisibility(View.GONE);
                                    btnType2 = false;
                                    btnView2.setVisibility(View.GONE);
                                    bankAccountTv.setTextColor(Color.parseColor("#484848"));
                                }else if (data.getData().getBank() == 1) {//银行
                                    con = false;
                                    continueBtn1.setVisibility(View.VISIBLE);
                                    okIv1.setVisibility(View.GONE);
                                    btnType2 = false;
                                    btnView2.setVisibility(View.GONE);
                                    bankAccountTv.setTextColor(Color.parseColor("#484848"));
                                }else if (data.getData().getBank() == 2) {//银行
                                    continueBtn1.setVisibility(View.GONE);
                                    okIv1.setVisibility(View.VISIBLE);
                                    btnType2 = true;
                                    btnView2.setVisibility(View.VISIBLE);
                                    bankAccountTv.setTextColor(Color.parseColor("#484848"));
                                }
                                System.out.println("s = " + data.getData().getMerchant());
                                if (data.getData().getMerchant()==0&&con){//运营
                                    con = false;
                                    continueBtn2.setVisibility(View.VISIBLE);
                                    okIv2.setVisibility(View.GONE);
                                    btnType3 = false;
                                    btnView3.setVisibility(View.GONE);
                                    operationSettings.setTextColor(Color.parseColor("#484848"));
                                }else if (data.getData().getMerchant()==1){//运营
                                    con = false;
                                    continueBtn2.setVisibility(View.VISIBLE);
                                    okIv2.setVisibility(View.GONE);
                                    btnType3 = false;
                                    btnView3.setVisibility(View.GONE);
                                    operationSettings.setTextColor(Color.parseColor("#484848"));
                                }else if (data.getData().getMerchant()==2){//运营\
                                    continueBtn2.setVisibility(View.GONE);
                                    okIv2.setVisibility(View.VISIBLE);
                                    btnType3 = true;
                                    btnView3.setVisibility(View.VISIBLE);
                                    operationSettings.setTextColor(Color.parseColor("#484848"));

                                }

                                if (btnLayout.getVisibility()!=View.VISIBLE) {
                                    if (data.getData().getMerchantMenu() == 0 && con) {//菜单
                                        con = false;
                                        continueBtn3.setVisibility(View.VISIBLE);
                                        okIv3.setVisibility(View.GONE);
                                        btnView4.setVisibility(View.GONE);
                                        nowMenu.setTextColor(Color.parseColor("#484848"));
                                    } else if (data.getData().getMerchantMenu() == 1) {//菜单
                                        con = false;
                                        continueBtn3.setVisibility(View.VISIBLE);
                                        okIv3.setVisibility(View.GONE);
                                        btnView4.setVisibility(View.GONE);
                                        nowMenu.setTextColor(Color.parseColor("#484848"));
                                    } else if (data.getData().getMerchantMenu() == 2) {//菜单
                                        getInfo();

                                    }
                                }




                            }
                        }
                    }
                });
    }
    Handler mHandler1 = new Handler();
    Runnable r = new Runnable() {

        @Override
        public void run() {
            //do something
            //每隔1s循环执行run方法
            ClientActivity.instance.finish();
            finish();
        }
    };
    @OnClick({R.id.del_btn, R.id.continue_btn, R.id.continue_btn2, R.id.continue_btn3,R.id.continue_btn1,
    R.id.cancel_btn,R.id.ok_btn,R.id.ok_iv,R.id.ok_iv1,R.id.ok_iv2,R.id.ok_iv3,R.id.btn_view_1,R.id.btn_view_2,R.id.btn_view_3
    ,R.id.btn_view_4})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.cancel_btn:
                intent = new Intent(this, BusinessDetailsActivity.class);
                intent.putExtra("id", YumApplication.getInstance().getMyInformation().getUid());
                intent.putExtra("type", 2);
                intent.putExtra("like", false);
                intent.putExtra("menuType", true);
                startActivityForResult(intent, 1);
                break;
            case R.id.ok_btn:
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
                                if (AppUtile.getNetWork(e.getMessage())){
                                    showMsg(getString(R.string.NETERROR));
                                }
                            }

                            @Override
                            public void onResponse(String s, int i) {
                                if (AppUtile.setCode(s,ReleaseActivity.this)) {
                                    //mHandler1.postDelayed(r, 1000);
                                    //YumApplication.getInstance().removeALLActivity_();
                                    //Intent intent = new Intent(ReleaseActivity.this, MerchantsActivity.class);
                                    if (YumApplication.getInstance().isInputType()) {
                                        YumApplication.getInstance().setInputType(false);
                                    } else {
                                        YumApplication.getInstance().setInputType(true);
                                    }
                                    YumApplication.getInstance().setReleaseType(true);
                                    YumApplication.getInstance().getMyInformation().setType("1");

                                    //YumApplication.getInstance().getMyInformation().setToken("1");
//                                    ComponentName cn = intent.getComponent();
//                                    Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);//ComponentInfo{包名+类名}
//                                    startActivity(mainIntent);
//                                    finish();
                                    setResult(999);
                                    finish();
                                    //startActivity(intent);
                                    //overridePendingTransition(R.anim.activity_open, R.anim.in);
                                }
                            }
                        });
                break;
            case R.id.del_btn:
                finish();
                overridePendingTransition(R.anim.activity_open,R.anim.out);
                break;
            case R.id.btn_view_1:
            case R.id.continue_btn:
                if (btnType1) {
                    getInfo(Constant.GET_MERCHANT_INFO_CERT);
                }else {
                    CertInput inputData = new CertInput();
                    if (TextUtils.isEmpty(YumApplication.getInstance().getpYourId().getUserId())||
                            YumApplication.getInstance().getMyInformation().getUid().equals(YumApplication.getInstance().getpYourId().getUserId())) {
                        inputData.setCertNo(TextUtils.isEmpty(YumApplication.getInstance().getpYourId().getCertNo()) ? "" : YumApplication.getInstance().getpYourId().getCertNo());
                        inputData.setCertType(YumApplication.getInstance().getpYourId().getCertType());
                        inputData.setCertUrl(YumApplication.getInstance().getpYourId().getCertUrl());
                        inputData.setEmail(YumApplication.getInstance().getpYourId().getEmail());
                        inputData.setGender(YumApplication.getInstance().getpYourId().getGender() + "");
                        inputData.setUserId(YumApplication.getInstance().getMyInformation().getUid());
                        YumApplication.getInstance().getpYourId().setUserId(YumApplication.getInstance().getMyInformation().getUid());
                        inputData.setName(YumApplication.getInstance().getpYourId().getName());
                        inputData.setPhone(YumApplication.getInstance().getpYourId().getPhone());
                        inputData.setCity(YumApplication.getInstance().getpYourId().getCity());
                        inputData.setProvince(YumApplication.getInstance().getpYourId().getProvince());
                        inputData.setOkCode(YumApplication.getInstance().getpYourId().getOkCode());
                        inputData.setRoomNo(YumApplication.getInstance().getpYourId().getRoomNo());
                        inputData.setStreet(YumApplication.getInstance().getpYourId().getStreet());
                        inputData.setZipCode(YumApplication.getInstance().getpYourId().getZipCode());
                        int countryId = Integer.valueOf(YumApplication.getInstance().getpYourId().getCountryId());
                        String countryName = "";
                        if (countryList != null && countryList.size() > 0) {
                            a:
                            for (SelectCountryBase.DataBean.CountryRespResultListBean item : countryList) {
                                if (item.getId() == countryId) {
                                    countryName = item.getName();
                                    break a;
                                }
                            }
                        }
                        inputData.setCountry(countryName);
                        inputData.setCountryId(countryId + "");
                    }else{
                        int countryId = 1;
                        String countryName = "";
                        if (countryList != null && countryList.size() > 0) {
                            a:
                            for (SelectCountryBase.DataBean.CountryRespResultListBean item : countryList) {
                                if (item.getId() == countryId) {
                                    countryName = item.getName();
                                    break a;
                                }
                            }
                        }
                        inputData.setCountry(countryName);
                        inputData.setCountryId(countryId + "");
                        YumApplication.getInstance().getpYourId().clear();
                    }
                    intent = new Intent(ReleaseActivity.this, YourIdActivity.class);
                    intent.putExtra("type", btnType1);
                    intent.putExtra("data", inputData);
                    startActivityForResult(intent, 0);
                }



                break;
            case R.id.btn_view_2:
            case R.id.continue_btn1:
                if (btnType2) {
                    getInfo(Constant.GET_MERCHANT_INFO_BANK);
                }else{
                    BankInput bankInput = new BankInput();
                    if (TextUtils.isEmpty(YumApplication.getInstance().getpBankAccount().getUserId())||
                            YumApplication.getInstance().getMyInformation().getUid().equals(YumApplication.getInstance().getpBankAccount().getUserId())) {
                        bankInput.setAccount(YumApplication.getInstance().getpBankAccount().getAccount());
                        bankInput.setCode(YumApplication.getInstance().getpBankAccount().getCode());
                        bankInput.setName(YumApplication.getInstance().getpBankAccount().getName());
                        bankInput.setType(YumApplication.getInstance().getpBankAccount().getType());
                        bankInput.setCity(YumApplication.getInstance().getpBankAccount().getCity());
                        int countryId = Integer.valueOf(YumApplication.getInstance().getpBankAccount().getCountryId());
                        String countryName = "";

                        if (countryList != null && countryList.size() > 0) {
                            a:
                            for (SelectCountryBase.DataBean.CountryRespResultListBean item : countryList) {
                                if (item.getId() == countryId) {
                                    countryName = item.getName();
                                    countryId = item.getId();
                                    break a;
                                }
                            }

                        }
                        bankInput.setCountryId(countryId + "");
                        bankInput.setCountry(countryName);
                        bankInput.setProvince(YumApplication.getInstance().getpBankAccount().getProvince());
                        bankInput.setRoomNo(YumApplication.getInstance().getpBankAccount().getRoomNo());
                        bankInput.setStreet(YumApplication.getInstance().getpBankAccount().getStreet());
                        bankInput.setZipCode(YumApplication.getInstance().getpBankAccount().getZipCode());
                        YumApplication.getInstance().getpBankAccount().setUserId(YumApplication.getInstance().getMyInformation().getUid());
                        bankInput.setUserId(YumApplication.getInstance().getMyInformation().getUid());
                    }else{
                        YumApplication.getInstance().getpBankAccount().clear();
                        int countryId = 1;
                        String countryName = "";

                        if (countryList != null && countryList.size() > 0) {
                            a:
                            for (SelectCountryBase.DataBean.CountryRespResultListBean item : countryList) {
                                if (item.getId() == countryId) {
                                    countryName = item.getName();
                                    countryId = item.getId();
                                    break a;
                                }
                            }

                        }
                        bankInput.setCountryId(countryId + "");
                        bankInput.setCountry(countryName);
                    }
                    intent = new Intent(ReleaseActivity.this, BankAccountActivity.class);
                    //intent = new Intent(ReleaseActivity.this, Bank5Activity.class);
                    intent.putExtra("type", btnType2);
                    intent.putExtra("data", bankInput);
                    startActivityForResult(intent, 0);
                }

                break;
            case R.id.btn_view_3:
            case R.id.continue_btn2:
                if (btnType3) {
                    getInfo(Constant.GET_MERCHANT_INFO_INFO);
                }else{
                    MerchantInput merchantInput = new MerchantInput();
                    if (TextUtils.isEmpty(YumApplication.getInstance().getpOperationsSettings().getUserId())||
                            YumApplication.getInstance().getMyInformation().getUid().equals(YumApplication.getInstance().getpOperationsSettings().getUserId())) {
                        merchantInput.setEndTime(YumApplication.getInstance().getpOperationsSettings().getEndTime());
                        merchantInput.setKeyword(YumApplication.getInstance().getpOperationsSettings().getKeyword());
                        merchantInput.setMap(YumApplication.getInstance().getpOperationsSettings().getMap());
                        merchantInput.setMoney(YumApplication.getInstance().getpOperationsSettings().getMoney());
                        merchantInput.setName(YumApplication.getInstance().getpOperationsSettings().getName());
                        merchantInput.setRange(YumApplication.getInstance().getpOperationsSettings().getRange());
                        merchantInput.setSeries(YumApplication.getInstance().getpOperationsSettings().getSeries());
                        merchantInput.setStartTime(YumApplication.getInstance().getpOperationsSettings().getStartTime());
                        merchantInput.setTime(YumApplication.getInstance().getpOperationsSettings().getTime().replace("-","~"));
                        YumApplication.getInstance().getpOperationsSettings().setUserId(YumApplication.getInstance().getMyInformation().getUid());
                        merchantInput.setUserId(YumApplication.getInstance().getMyInformation().getUid());
                        merchantInput.setTaxRate(YumApplication.getInstance().getpOperationsSettings().getTaxRate());
                        merchantInput.setLng(YumApplication.getInstance().getpOperationsSettings().getLng());
                        merchantInput.setLat(YumApplication.getInstance().getpOperationsSettings().getLat());
                        int currencyId = Integer.valueOf(YumApplication.getInstance().getpOperationsSettings().getCurrencyId());
                        String currencyName = "";
                        String currencyFlag = "";
                        String countrySymbol = "";
                        if (countryList != null && countryList.size() > 0) {
                            a:
                            for (SelectCountryBase.DataBean.CountryRespResultListBean item : countryList) {
                                if (item.getId() == currencyId) {
                                    currencyName = item.getName();
                                    currencyFlag = item.getFlag();
                                    countrySymbol = item.getCurrencySymbol();
                                    break a;
                                }
                            }
                        }
                        merchantInput.setCountrySymbol(countrySymbol);
                        merchantInput.setCurrencyId(currencyId);
                        merchantInput.setCurrencyName(currencyName);
                        merchantInput.setCurrencyFlag(currencyFlag);
                        int countryId = Integer.valueOf(YumApplication.getInstance().getpOperationsSettings().getCountryId());
                        String countryName = "";

                        if (countryList != null && countryList.size() > 0) {
                            a:
                            for (SelectCountryBase.DataBean.CountryRespResultListBean item : countryList) {
                                if (item.getId() == 1) {
                                    countryName = item.getName();
                                    countryId = item.getId();
                                    break a;
                                }
                            }

                        }
                        merchantInput.setCountryId(countryId + "");
                        merchantInput.setCountry(countryName);
                        merchantInput.setProvince(YumApplication.getInstance().getpOperationsSettings().getProvince());
                        merchantInput.setRoomNo(YumApplication.getInstance().getpOperationsSettings().getRoomNo());
                        merchantInput.setStreet(YumApplication.getInstance().getpOperationsSettings().getStreet());
                        merchantInput.setZipCode(YumApplication.getInstance().getpOperationsSettings().getZipCode());
                        merchantInput.setCity(YumApplication.getInstance().getpOperationsSettings().getCity());
                    }else{
                        YumApplication.getInstance().getpOperationsSettings().clear();
                        int currencyId = 1;
                        String currencyName = "";
                        String currencyFlag = "";
                        if (countryList != null && countryList.size() > 0) {
                            a:
                            for (SelectCountryBase.DataBean.CountryRespResultListBean item : countryList) {
                                if (item.getId() == currencyId) {
                                    currencyName = item.getName();
                                    currencyFlag = item.getFlag();
                                    break a;
                                }
                            }
                        }

                        merchantInput.setCurrencyId(currencyId);
                        merchantInput.setCurrencyName(currencyName);
                        merchantInput.setCurrencyFlag(currencyFlag);
                        int countryId = 1;
                        String countryName = "";

                        if (countryList != null && countryList.size() > 0) {
                            a:
                            for (SelectCountryBase.DataBean.CountryRespResultListBean item : countryList) {
                                if (item.getId() == 1) {
                                    countryName = item.getName();
                                    countryId = item.getId();
                                    break a;
                                }
                            }

                        }
                        merchantInput.setCountryId(countryId + "");
                        merchantInput.setCountry(countryName);
                    }
                    //intent = new Intent(ReleaseActivity.this, Operations7Activity.class);
                    intent = new Intent(ReleaseActivity.this, OperationsSettingsActivity.class);
                    intent.putExtra("data", merchantInput);
                    intent.putExtra("type", btnType3);
                    startActivityForResult(intent, 0);
                }
                break;
            case R.id.btn_view_4:
            case R.id.continue_btn3:
               intent = new Intent(this, NowMenuActivity.class);
                startActivityForResult(intent,2);
                //getInfo(Constant.GET_MERCHANT_MENU_LIST);
                break;
        }
    }
    private void getInfo(final String url){
        System.out.append("ReleaseActivity = "+ YumApplication.getInstance().getMyInformation().getToken());
        OkHttpUtils
                .get()
                .url(url)
                .addHeader("Accept-Language", AppUtile.getLanguage())
                .addHeader("YUM-AUTH-TOKEN",YumApplication.getInstance().getMyInformation().getToken())
                .addParams("userId",YumApplication.getInstance().getMyInformation().getUid())
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
                            if (AppUtile.setCode(s,ReleaseActivity.this)) {
                                Intent intent;
                                switch (url) {
                                    case Constant.GET_MERCHANT_INFO_CERT:
                                        System.out.println("s = " + s);
                                        CertBase data = JSON.parseObject(s, CertBase.class);
                                        CertInput inputData = new CertInput();
                                        if (data.getData()!=null) {
                                                inputData.setCertNo(TextUtils.isEmpty(data.getData().getCertNo())?"":data.getData().getCertNo());
                                            inputData.setCertType(data.getData().getCertType());
                                            inputData.setCertUrl(data.getData().getCertUrl());
                                            inputData.setEmail(data.getData().getEmail());
                                            inputData.setOkCode("1");
                                            inputData.setGender(data.getData().getGender() + "");
                                            inputData.setUserId(YumApplication.getInstance().getMyInformation().getUid());
                                            inputData.setName(data.getData().getName());
                                            inputData.setPhone(data.getData().getPhone());
                                            if (data.getData().getMerchantAddressRespResult() != null) {
                                                inputData.setCity(data.getData().getMerchantAddressRespResult().getCity());
                                                inputData.setProvince(data.getData().getMerchantAddressRespResult().getProvince());
                                                inputData.setRoomNo(data.getData().getMerchantAddressRespResult().getRoomNo());
                                                inputData.setStreet(data.getData().getMerchantAddressRespResult().getStreet());
                                                inputData.setUserId(YumApplication.getInstance().getMyInformation().getUid());
                                                inputData.setZipCode(data.getData().getMerchantAddressRespResult().getZipCode());
                                                int countryId = -1;
                                                String countryName = "";
                                                try {
                                                    countryId = Integer.valueOf(data.getData().getMerchantAddressRespResult().getCountryId());
                                                    if (countryList != null && countryList.size() > 0) {
                                                        a:
                                                        for (SelectCountryBase.DataBean.CountryRespResultListBean item : countryList) {
                                                            if (item.getId() == countryId) {
                                                                countryName = item.getName();
                                                                break a;
                                                            }
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    if (countryList != null && countryList.size() > 0) {
                                                        a:
                                                        for (SelectCountryBase.DataBean.CountryRespResultListBean item : countryList) {
                                                            if (item.getId() == 1) {
                                                                countryName = item.getName();
                                                                countryId = item.getId();
                                                                break a;
                                                            }
                                                        }

                                                    }
                                                }

                                                inputData.setCountry(countryName);
                                                inputData.setCountryId(countryId + "");
                                            }else{
                                                int countryId = -1;
                                                String countryName = "";
                                                if (countryList != null && countryList.size() > 0) {
                                                    a:
                                                    for (SelectCountryBase.DataBean.CountryRespResultListBean item : countryList) {
                                                        if (item.getId() == 1) {
                                                            countryName = item.getName();
                                                            countryId = item.getId();
                                                            break a;
                                                        }
                                                    }

                                                }
                                                inputData.setCountry(countryName);
                                                inputData.setCountryId(countryId + "");
                                            }
                                        }
                                        // intent = new Intent(ReleaseActivity.this, Document1Activity.class);
                                        intent = new Intent(ReleaseActivity.this, YourIdActivity.class);
                                        intent.putExtra("type", btnType1);
                                        intent.putExtra("data", inputData);
                                        startActivityForResult(intent, 0);

                                        break;
                                    case Constant.GET_MERCHANT_INFO_BANK:
                                        BankBase bankBase = JSON.parseObject(s, BankBase.class);
                                        BankInput bankInput = new BankInput();
                                        bankInput.setAccount(bankBase.getData().getAccount());
                                        bankInput.setCode(bankBase.getData().getCode());
                                        bankInput.setName(bankBase.getData().getName());
                                        bankInput.setType(bankBase.getData().getType());
                                        if (bankBase.getData().getMerchantAddressRespResult() != null) {
                                            bankInput.setCity(bankBase.getData().getMerchantAddressRespResult().getCity());
                                            int countryId = -1;
                                            String countryName = "";
                                            try {
                                                countryId = Integer.valueOf(bankBase.getData().getMerchantAddressRespResult().getCountryId());
                                                if (countryList != null && countryList.size() > 0) {
                                                    a:
                                                    for (SelectCountryBase.DataBean.CountryRespResultListBean item : countryList) {
                                                        if (item.getId() == countryId) {
                                                            countryName = item.getName();
                                                            break a;
                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {
                                                if (countryList != null && countryList.size() > 0) {
                                                    a:
                                                    for (SelectCountryBase.DataBean.CountryRespResultListBean item : countryList) {
                                                        if (item.getId() == 1) {
                                                            countryName = item.getName();
                                                            countryId = item.getId();
                                                            break a;
                                                        }
                                                    }

                                                }

                                            }
                                            bankInput.setCountryId(countryId + "");
                                            bankInput.setCountry(countryName);
                                            bankInput.setProvince(bankBase.getData().getMerchantAddressRespResult().getProvince());
                                            bankInput.setRoomNo(bankBase.getData().getMerchantAddressRespResult().getRoomNo());
                                            bankInput.setStreet(bankBase.getData().getMerchantAddressRespResult().getStreet());
                                            bankInput.setZipCode(bankBase.getData().getMerchantAddressRespResult().getZipCode());
                                        }else{
                                            int countryId = -1;
                                            String countryName = "";

                                            if (countryList != null && countryList.size() > 0) {
                                                a:
                                                for (SelectCountryBase.DataBean.CountryRespResultListBean item : countryList) {
                                                    if (item.getId() == 1) {
                                                        countryName = item.getName();
                                                        countryId = item.getId();
                                                        break a;
                                                    }
                                                }

                                            }
                                            bankInput.setCountryId(countryId + "");
                                            bankInput.setCountry(countryName);
                                        }

                                        bankInput.setUserId(YumApplication.getInstance().getMyInformation().getUid());
                                        intent = new Intent(ReleaseActivity.this, BankAccountActivity.class);
                                        //intent = new Intent(ReleaseActivity.this, Bank5Activity.class);
                                        intent.putExtra("type", btnType2);
                                        intent.putExtra("data", bankInput);
                                        startActivityForResult(intent, 0);

                                        break;
                                    case Constant.GET_MERCHANT_INFO_INFO:
                                        MerchantBase merchantBase = JSON.parseObject(s, MerchantBase.class);
                                        MerchantInput merchantInput = new MerchantInput();

                                        merchantInput.setEndTime(merchantBase.getData().getEndTime());
                                        merchantInput.setKeyword(merchantBase.getData().getKeyword());
                                        merchantInput.setMap(merchantBase.getData().getMap());
                                        merchantInput.setMoney(merchantBase.getData().getMoney());
                                        merchantInput.setName(merchantBase.getData().getName());
                                        merchantInput.setRange(merchantBase.getData().getRange());
                                        merchantInput.setSeries(merchantBase.getData().getSeries());
                                        merchantInput.setStartTime(merchantBase.getData().getStartTime());
                                        merchantInput.setTime(merchantBase.getData().getTime().replace("-","~"));
                                        merchantInput.setUserId(YumApplication.getInstance().getMyInformation().getUid());
                                        merchantInput.setTaxRate(merchantBase.getData().getTaxRate());
                                        merchantInput.setLng(merchantBase.getData().getLng());
                                        merchantInput.setLat(merchantBase.getData().getLat());
                                        int currencyId = -1;
                                        String currencyName = "";
                                        String currencyFlag = "";
                                        String countrySymbol = "";
                                        try {
                                            currencyId = Integer.valueOf(merchantBase.getData().getCurrencyId());
                                            if (countryList != null && countryList.size() > 0) {
                                                a:
                                                for (SelectCountryBase.DataBean.CountryRespResultListBean item : countryList) {
                                                    if (item.getId() == currencyId) {
                                                        currencyName = item.getName();
                                                        currencyFlag = item.getFlag();
                                                        countrySymbol = item.getCurrencySymbol();
                                                        break a;
                                                    }
                                                }
                                            }
                                        } catch (Exception e) {
                                            if (countryList != null && countryList.size() > 0) {
                                                a:
                                                for (SelectCountryBase.DataBean.CountryRespResultListBean item : countryList) {
                                                    if (item.getId() == 1) {
                                                        currencyName = item.getName();
                                                        currencyId = item.getId();
                                                        currencyFlag = item.getFlag();
                                                        countrySymbol = item.getCurrencySymbol();
                                                        break a;
                                                    }
                                                }

                                            }

                                        }
                                        merchantInput.setCurrencyId(currencyId);
                                        merchantInput.setCountrySymbol(countrySymbol);
                                        merchantInput.setCurrencyName(currencyName);
                                        merchantInput.setCurrencyFlag(currencyFlag);
                                        if (merchantBase.getData().getMerchantAddressRespResult() != null) {
                                            merchantInput.setCity(merchantBase.getData().getMerchantAddressRespResult().getCity());
                                            int countryId = -1;
                                            String countryName = "";
                                            try {
                                                countryId = Integer.valueOf(merchantBase.getData().getMerchantAddressRespResult().getCountryId());
                                                if (countryList != null && countryList.size() > 0) {
                                                    a:
                                                    for (SelectCountryBase.DataBean.CountryRespResultListBean item : countryList) {
                                                        if (item.getId() == countryId) {
                                                            countryName = item.getName();
                                                            break a;
                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {
                                                if (countryList != null && countryList.size() > 0) {
                                                    a:
                                                    for (SelectCountryBase.DataBean.CountryRespResultListBean item : countryList) {
                                                        if (item.getId() == 1) {
                                                            countryName = item.getName();
                                                            countryId = item.getId();
                                                            break a;
                                                        }
                                                    }

                                                }

                                            }
                                            merchantInput.setCountryId(countryId + "");
                                            merchantInput.setCountry(countryName);
                                            merchantInput.setProvince(merchantBase.getData().getMerchantAddressRespResult().getProvince());
                                            merchantInput.setRoomNo(merchantBase.getData().getMerchantAddressRespResult().getRoomNo());
                                            merchantInput.setStreet(merchantBase.getData().getMerchantAddressRespResult().getStreet());
                                            merchantInput.setZipCode(merchantBase.getData().getMerchantAddressRespResult().getZipCode());

                                        }else{
                                            int countryId = -1;
                                            String countryName = "";

                                            if (countryList != null && countryList.size() > 0) {
                                                a:
                                                for (SelectCountryBase.DataBean.CountryRespResultListBean item : countryList) {
                                                    if (item.getId() == 1) {
                                                        countryName = item.getName();
                                                        countryId = item.getId();
                                                        break a;
                                                    }
                                                }

                                            }
                                            merchantInput.setCountryId(countryId + "");
                                            merchantInput.setCountry(countryName);
                                        }
                                        //intent = new Intent(ReleaseActivity.this, Operations7Activity.class);
                                        intent = new Intent(ReleaseActivity.this, OperationsSettingsActivity.class);
                                        intent.putExtra("data", merchantInput);
                                        intent.putExtra("type", btnType3);
                                        startActivityForResult(intent, 0);
                                        break;
                                    case Constant.GET_MERCHANT_MENU_LIST:

                                        break;
                                }
                            }
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getInfo() {
        OkHttpUtils
                .get()
                .url(Constant.GET_MERCHANT_MENU_LIST)
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
                    public void onResponse(String s, int c) {
                        if (!TextUtils.isEmpty(s)) {

                            if (AppUtile.setCode(s,ReleaseActivity.this)) {
                                GetMerchantMenuListBase data = JSON.parseObject(s, GetMerchantMenuListBase.class);
                                if (data.getData().getMerchantMenuRespResults() != null && data.getData().getMerchantMenuRespResults().size() > 0) {
                                    boolean con = false;
                                    for (int i =0 ; i < data.getData().getMerchantMenuRespResults().size();i++){
                                        if (data.getData().getMerchantMenuRespResults().get(i).getState()==1){
                                            con = true;
                                            break;
                                        }
                                    }
                                    if (con){
                                        btnLayout.setVisibility(View.VISIBLE);
                                        continueBtn3.setVisibility(View.GONE);
                                        okIv3.setVisibility(View.VISIBLE);
                                        btnView4.setVisibility(View.VISIBLE);
                                        nowMenu.setTextColor(Color.parseColor("#484848"));
                                    }else{
                                        btnLayout.setVisibility(View.GONE);
                                        continueBtn3.setVisibility(View.VISIBLE);
                                        okIv3.setVisibility(View.GONE);
                                        btnView4.setVisibility(View.GONE);
                                        nowMenu.setTextColor(Color.parseColor("#484848"));
                                    }
                                }else{
                                    btnLayout.setVisibility(View.GONE);
                                    continueBtn3.setVisibility(View.VISIBLE);
                                    okIv3.setVisibility(View.GONE);
                                    btnView4.setVisibility(View.GONE);
                                    nowMenu.setTextColor(Color.parseColor("#484848"));
                                }
                            }
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == 1){
                setResult(RESULT_OK);
                finish();
            }else if (requestCode == 2){
                btnLayout.setVisibility(View.VISIBLE);
                continueBtn3.setVisibility(View.GONE);
                okIv3.setVisibility(View.VISIBLE);
                btnView4.setVisibility(View.VISIBLE);
                nowMenu.setTextColor(Color.parseColor("#484848"));
            }else {
                getData();
            }
        }
    }

}
