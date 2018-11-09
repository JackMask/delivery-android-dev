package com.yum.two_yum.view.merchants.merchantsfragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BankBase;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.BaseReturn;
import com.yum.two_yum.base.CertBase;
import com.yum.two_yum.base.GetMerchantMenuListBase;
import com.yum.two_yum.base.MenuListBase;
import com.yum.two_yum.base.MerchantBase;
import com.yum.two_yum.base.ReleaseMenuInfoBase;
import com.yum.two_yum.base.SelectCountryBase;
import com.yum.two_yum.base.input.BankInput;
import com.yum.two_yum.base.input.CertInput;
import com.yum.two_yum.base.input.MerchantInput;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.pulltorefresh.PullToRefreshBase;
import com.yum.two_yum.utile.pulltorefresh.PullToRefreshScrollView;
import com.yum.two_yum.utile.view.SwitchView;
import com.yum.two_yum.view.client.business.BusinessDetailsActivity;
import com.yum.two_yum.view.dialog.Prompt2Dialog;
import com.yum.two_yum.view.dialog.PromptDialog;
import com.yum.two_yum.view.guide.ReleaseActivity;
import com.yum.two_yum.view.guide.bank.Bank5Activity;
import com.yum.two_yum.view.guide.document.Document1Activity;
import com.yum.two_yum.view.guide.operations.Operations7Activity;
import com.yum.two_yum.view.merchants.MerchantsActivity;
import com.yum.two_yum.view.merchants.menu.BankAccountActivity;
import com.yum.two_yum.view.merchants.menu.NowMenuActivity;
import com.yum.two_yum.view.merchants.menu.OperationsSettingsActivity;
import com.yum.two_yum.view.merchants.menu.YourIdActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @author 余先德
 * @data 2018/4/7
 */

public class MenuFragment extends Fragment {

    @Bind(R.id.your_id_btn)
    LinearLayout yourIdBtn;
    @Bind(R.id.bank_account_btn)
    LinearLayout bankAccountBtn;
    @Bind(R.id.operation_btn)
    LinearLayout operationBtn;
    @Bind(R.id.now_mun_btn)
    LinearLayout nowMunBtn;
    @Bind(R.id.v_switch_1)
    SwitchView vSwitch1;
    @Bind(R.id.list_btn)
    LinearLayout listBtn;
    @Bind(R.id.menu_layout)
    LinearLayout menuLayout;
    @Bind(R.id.add_menu_btn)
    ImageView addMenuBtn;
    @Bind(R.id.your_id_tv)
    TextView yourIdTv;
    @Bind(R.id.bank_account_tv)
    TextView bankAccountTv;
    @Bind(R.id.operation_tv)
    TextView operationTv;
    @Bind(R.id.now_mun_tv)
    TextView nowMunTv;
    @Bind(R.id.list_tv)
    TextView listTv;
    @Bind(R.id.close_btn)
    TextView closeBtn;
    @Bind(R.id.preview_btn)
    LinearLayout previewBtn;
    @Bind(R.id.scroll_view)
    PullToRefreshScrollView scrollView;
    @Bind(R.id.stat_btn)
    ImageView disableBtn;
    @Bind(R.id.menu_layout_all)
    RelativeLayout menuLayoutAll;
    @Bind(R.id.no_menu)
    View noMenu;

    private MerchantsActivity abActivity;
    private View view;
    private boolean yourIdType = false,bankAccountType = false,operationType = false,nowMunType = false;
    private int reviewType = -1;
    private List<SelectCountryBase.DataBean.CountryRespResultListBean> countryList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        abActivity = (MerchantsActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_menu, container, false);
        ButterKnife.bind(this, view);
        setView();
        getData();
        return view;
    }
    public void refreshInfo(final boolean type,final int now){
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
                            if (AppUtile.setCode(s,(BaseActivity)getActivity())){
                            MerchantBase merchantBase = JSON.parseObject(s, MerchantBase.class);
                            if (merchantBase.getData()!=null) {
                                if (merchantBase.getData().getState() == 1) {
                                    setSwitch(true);
                                    setDisable(true);
                                } else if (merchantBase.getData().getState() == 2) {
                                    setSwitch(false);
                                    setDisable(true);
                                } else {
                                    setDisable(false);
                                }
                                if (merchantBase.getData().getAuditState() == 1) {
                                    vSwitch1.setOpened(false);
                                    setDisable(true);
                                } else if (merchantBase.getData().getState() == 1) {
                                    vSwitch1.setOpened(true);
                                }
                                if (type) {
                                    if (merchantBase.getData().getAuditState() == 2) {
                                        if (now == 1) {
                                            Intent intent = new Intent(getContext(), PromptDialog.class);
                                            intent.putExtra("content", abActivity.getResources().getString(R.string.OPENQUESTION));
                                            abActivity.startActivityForResult(intent, 101);
                                        } else {
                                            Intent intent = new Intent(getContext(), PromptDialog.class);
                                            intent.putExtra("content", abActivity.getResources().getString(R.string.CLOSEQUESTION));
                                            abActivity.startActivityForResult(intent, 102);
                                        }
                                    } else if (merchantBase.getData().getAuditState() == 1) {
                                        Intent intent = new Intent(getContext(), Prompt2Dialog.class);
                                        intent.putExtra("title", true);
                                        intent.putExtra("content", abActivity.getResources().getString(R.string.IMAGEORTEXTERRORPLEASERESUBMIT));
                                        abActivity.startActivity(intent);

                                    }
                                }
                            }
                            }
                        }
                    }
                });
    }
    private void setView() {
        scrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        scrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                getData();
                refreshInfo(false,-1);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

            }
        });
        vSwitch1.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                refreshInfo(true,1);


            }

            @Override
            public void toggleToOff(SwitchView view) {
                refreshInfo(true,2);

            }
        });
    }

    public void stopMenu() {
        noMenu.setVisibility(View.VISIBLE);
        previewBtn.setVisibility(View.GONE);
        disableBtn.setVisibility(View.VISIBLE);
    }
    public void setSwitch(boolean con){
        if (vSwitch1!=null)
        vSwitch1.setOpened(con);

    }
    public void setDisable(boolean con){
        if (disableBtn!=null&&previewBtn!=null&&menuLayoutAll!=null) {
            if (con) {
                disableBtn.setVisibility(View.GONE);
                previewBtn.setVisibility(View.VISIBLE);
                noMenu.setVisibility(View.GONE);
            } else {
                disableBtn.setVisibility(View.VISIBLE);
                previewBtn.setVisibility(View.GONE);
                noMenu.setVisibility(View.VISIBLE);
            }
        }
    }
    public void open() {
        vSwitch1.setOpened(true);
    }

    public void refresh(){
        getData();
    }
    public void close() {
        vSwitch1.setOpened(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.your_id_btn, R.id.bank_account_btn, R.id.operation_btn, R.id.now_mun_btn, R.id.list_btn, R.id.close_btn, R.id.add_menu_btn
    ,R.id.preview_btn,R.id.stat_btn})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.stat_btn:
                intent = new Intent(getContext(), ReleaseActivity.class);
                startActivityForResult(intent,10);
                getActivity().overridePendingTransition(R.anim.activity_open, R.anim.in);
                break;
            case R.id.preview_btn:
                intent = new Intent(getActivity(), BusinessDetailsActivity.class);
                intent.putExtra("id", YumApplication.getInstance().getMyInformation().getUid());
                intent.putExtra("type", 2);
                intent.putExtra("like", false);
                intent.putExtra("menuType", true);
                startActivityForResult(intent, 1);
                break;
            case R.id.add_menu_btn:
//                intent = new Intent(getContext(), ReleaseActivity.class);
//                startActivity(intent);
                break;
            case R.id.your_id_btn:
                if (yourIdType) {
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
                    intent = new Intent(getActivity(), YourIdActivity.class);
                    intent.putExtra("type", yourIdType);
                    intent.putExtra("data", inputData);
                    startActivityForResult(intent, 0);
                }
                break;
            case R.id.bank_account_btn:
                if (bankAccountType) {
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
                    intent = new Intent(getActivity(), BankAccountActivity.class);
                    //intent = new Intent(ReleaseActivity.this, Bank5Activity.class);
                    intent.putExtra("type", bankAccountType);
                    intent.putExtra("data", bankInput);
                    startActivityForResult(intent, 0);
                }
                break;
            case R.id.operation_btn:
                if (operationType) {
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
                    intent = new Intent(getActivity(), OperationsSettingsActivity.class);
                    intent.putExtra("data", merchantInput);
                    intent.putExtra("type", operationType);
                    startActivityForResult(intent, 0);
                }
                break;
            case R.id.now_mun_btn:
                intent = new Intent(getContext(), NowMenuActivity.class);
                abActivity.startActivityForResult(intent,1);
//                if (nowMunType) {
//                    intent = new Intent(getContext(), NowMenuActivity.class);
//                    abActivity.startActivity(intent);
//                }else{
//                    getInfo(Constant.GET_MERCHANT_MENU_LIST);
//                }
                break;
            case R.id.list_btn:
                break;
            case R.id.close_btn:
                intent = new Intent(getContext(), PromptDialog.class);
                intent.putExtra("content", abActivity.getResources().getString(R.string.DELETEMENU));
                abActivity.startActivityForResult(intent, 100);
                break;
        }
    }

    public void getData() {
       // System.out.println("MenuFragment = "+ YumApplication.getInstance().getMyInformation().getToken());
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
                            ((BaseActivity)getActivity()).showMsg(getString(R.string.NETERROR));
                        }
                    }

                    @Override
                    public void onResponse(String s, int b) {
                        if (!TextUtils.isEmpty(s)){

                            if (AppUtile.setCode(s,(BaseActivity)getActivity())){
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
                .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                .addParams("userId", YumApplication.getInstance().getMyInformation().getUid())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        if (AppUtile.getNetWork(e.getMessage())){
                            ((BaseActivity)getActivity()).showMsg(getString(R.string.NETERROR));
                        }
                        scrollView.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        scrollView.onRefreshComplete();
                        if (!TextUtils.isEmpty(s)) {
                            if (AppUtile.setCode(s,(BaseActivity)getActivity())) {
                                ReleaseMenuInfoBase data = JSON.parseObject(s, ReleaseMenuInfoBase.class);
                                boolean con = true;
                                if (data.getData().getCert() == 0 && con) {//证件
                                    con = false;
                                    yourIdType = false;
                                   // yourIdTv.setTextColor(Color.parseColor("#484848"));
                                } else if (data.getData().getCert() == 1) {
                                    con = false;
                                    yourIdType = false;
                                    //yourIdTv.setTextColor(Color.parseColor("#484848"));
                                } else if (data.getData().getCert() == 2) {
                                    yourIdType = true;
                                    //yourIdTv.setTextColor(Color.parseColor("#484848"));
                                }
                                if (data.getData().getBank() == 0 && con) {//银行
                                    con = false;
                                    bankAccountType = false;
                                   // bankAccountTv.setTextColor(Color.parseColor("#484848"));
                                } else if (data.getData().getBank() == 1) {//银行
                                    con = false;
                                    bankAccountType = false;
                                   // bankAccountTv.setTextColor(Color.parseColor("#484848"));
                                } else if (data.getData().getBank() == 2) {//银行
                                    bankAccountType = true;
                                    //bankAccountTv.setTextColor(Color.parseColor("#484848"));
                                }
                                if (data.getData().getMerchant() == 0 && con) {//运营
                                    con = false;
                                    operationType = false;
                                   // operationTv.setTextColor(Color.parseColor("#484848"));
                                } else if (data.getData().getMerchant() == 1) {//运营
                                    con = false;
                                    operationType = false;
                                    //operationTv.setTextColor(Color.parseColor("#484848"));
                                } else if (data.getData().getMerchant() == 2) {//运营\
                                    operationType = true;
                                    //operationTv.setTextColor(Color.parseColor("#484848"));

                                }
                                if (data.getData().getMerchantMenu() == 0 && con) {//菜单
                                    con = false;
                                    nowMunType = false;
                                    //nowMunTv.setTextColor(Color.parseColor("#484848"));
                                } else if (data.getData().getMerchantMenu() == 1) {//菜单
                                    con = false;
                                    nowMunType = false;
                                    //nowMunTv.setTextColor(Color.parseColor("#484848"));
                                } else if (data.getData().getMerchantMenu() == 2) {//菜单
                                    nowMunType = true;
                                    //nowMunTv.setTextColor(Color.parseColor("#484848"));
                                }
                                if (data.getData().getIssue()==1){
                                    //listTv.setTextColor(Color.parseColor("#484848"));
                                    vSwitch1.setOpened(true);
                                }


                            }
                        }
                    }
                });
    }
    private void getInfo(final String url){

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
                            ((BaseActivity)getActivity()).showMsg(getString(R.string.NETERROR));
                        }
                    }

                    @Override
                    public void onResponse(String s, int con) {
                        if (!TextUtils.isEmpty(s)) {
                            if (AppUtile.setCode(s,(BaseActivity)getActivity())) {
                                Intent intent;
                                switch (url) {
                                    case Constant.GET_MERCHANT_INFO_CERT:
                                        System.out.println("s = " + s);
                                        CertBase data = JSON.parseObject(s, CertBase.class);
                                        CertInput inputData = new CertInput();
                                        inputData.setCertNo(data.getData().getCertNo() + "");
                                        inputData.setCertType(data.getData().getCertType());
                                        inputData.setCertUrl(data.getData().getCertUrl());
                                        inputData.setEmail(data.getData().getEmail());
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
                                        intent = new Intent(getActivity(), YourIdActivity.class);
                                        intent.putExtra("type", yourIdType);
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
                                        intent = new Intent(getActivity(), BankAccountActivity.class);
                                        //intent = new Intent(ReleaseActivity.this, Bank5Activity.class);
                                        intent.putExtra("type", bankAccountType);
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
                                        intent = new Intent(getActivity(), OperationsSettingsActivity.class);
                                        intent.putExtra("data", merchantInput);
                                        intent.putExtra("type", operationType);
                                        startActivityForResult(intent, 0);
                                        break;
                                    case Constant.GET_MERCHANT_MENU_LIST:
                                        GetMerchantMenuListBase getMerchantMenuListBase = JSON.parseObject(s, GetMerchantMenuListBase.class);
                                        if (getMerchantMenuListBase.getData().getMerchantMenuRespResults() != null && getMerchantMenuListBase.getData().getMerchantMenuRespResults().size() > 0) {

                                        }

                                        break;
                                }
                            }
                        }
                    }
                });
    }
}
