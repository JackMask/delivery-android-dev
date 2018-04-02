package com.guxingdongli.yizhangguan.view.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.util.AbToastUtil;
import com.alibaba.fastjson.JSON;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.YiZhangGuanApplication;
import com.guxingdongli.yizhangguan.model.FeeNameBase;
import com.guxingdongli.yizhangguan.model.OriginBase;
import com.guxingdongli.yizhangguan.model.ReasonAnalysisBase;
import com.guxingdongli.yizhangguan.model.ServiceLevelBase;
import com.guxingdongli.yizhangguan.model.SourcesOfFundsBase;
import com.guxingdongli.yizhangguan.model.UnitBase;
import com.guxingdongli.yizhangguan.model.UploadFileBase;
import com.guxingdongli.yizhangguan.model.passvalue.ScanCodeInput;
import com.guxingdongli.yizhangguan.util.AppUtile;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.guxingdongli.yizhangguan.view.home.dialog.ScanCodeDialog;
import com.guxingdongli.yizhangguan.view.home.fragment.HospitalFragment;
import com.guxingdongli.yizhangguan.view.home.fragment.MerchantsFragment;
import com.guxingdongli.yizhangguan.view.home.fragment.MessageFragment;
import com.guxingdongli.yizhangguan.view.home.fragment.MyFragment;
import com.guxingdongli.yizhangguan.view.home.fragment.RemindFragment;
import com.guxingdongli.yizhangguan.view.home.home_hospital.AddRepairActivity;
import com.guxingdongli.yizhangguan.view.login.LoginActivity;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by jackmask on 2018/3/1.
 */

public class HomeActivity extends YiZhangGuanActivity  {

    @Bind(R.id.fragment_container)
    RelativeLayout fragmentContainer;
    @Bind(R.id.show_merchants)
    ImageView showMerchants;
    @Bind(R.id.hide_merchants)
    ImageView hideMerchants;
    @Bind(R.id.merchants_btn)
    RelativeLayout merchantsBtn;
    @Bind(R.id.show_remind)
    ImageView showRemind;
    @Bind(R.id.hide_remind)
    ImageView hideRemind;
    @Bind(R.id.remind_btn)
    RelativeLayout remindBtn;
    @Bind(R.id.show_message)
    ImageView showMessage;
    @Bind(R.id.new_msg_number)
    TextView newMsgNumber;
    @Bind(R.id.hide_message)
    RelativeLayout hideMessage;
    @Bind(R.id.message_btn)
    RelativeLayout messageBtn;
    @Bind(R.id.show_my)
    ImageView showMy;
    @Bind(R.id.hide_my)
    ImageView hideMy;
    @Bind(R.id.my_btn)
    RelativeLayout myBtn;
    @Bind(R.id.main_bottom)
    LinearLayout mainBottom;

    private static boolean isPermissionRequested = false;
    private List<View> showImgs,hideImgs;

    private MerchantsFragment merchantsFragment = new MerchantsFragment();//供应商
    private HospitalFragment hospitalFragment;//医院
    private RemindFragment remindFragment;//工作提醒
    private MessageFragment messageFragment;//消息
    private MyFragment myFragment;//我的
    private Fragment[] tabFragments; //分页的集合
    private int con = 0;//当前选中的选项卡编号
    private int currentTabIndex = 0;//当前选中标记


    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 0://更新未读消息
                    String messageNum = (String)msg.obj;
                    if (messageNum.equals("0")){
                        newMsgNumber.setVisibility(View.GONE);
                    }else{
                        newMsgNumber.setVisibility(View.VISIBLE);
                        newMsgNumber.setText(messageNum+"");
                    }
                    break;
                case 1:
                    if (YiZhangGuanApplication.getInstance().isAppType()){
                        hospitalFragment.refreshUserInfo();
                    }else{
                        merchantsFragment.refreshUserInfo();
                    }
                    myFragment.refreshUserInfo();
                    break;
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setView();
        requestPermission();
    }

    private void setView(){
        setAnimCon(true);
        System.out.println(!TextUtils.isEmpty("fdsfsafdsafsdf"));
        showImgs = new ArrayList<>();
        hideImgs = new ArrayList<>();
        showImgs.add(showMerchants);
        showImgs.add(showRemind);
        showImgs.add(showMessage);
        showImgs.add(showMy);
        hideImgs.add(hideMerchants);
        hideImgs.add(hideRemind);
        hideImgs.add(hideMessage);
        hideImgs.add(hideMy);
        if (YiZhangGuanApplication.getInstance().isAppType()){
            //医院布局
            this.hospitalFragment =  new HospitalFragment();
        }else{
            //供应商布局
            this.merchantsFragment = new MerchantsFragment();
        }
        this.remindFragment = new RemindFragment();
        this.messageFragment = new MessageFragment();
        this.myFragment = new MyFragment();
        Fragment[] arrayOfFragment = new Fragment[4];
        if (YiZhangGuanApplication.getInstance().isAppType()){
            arrayOfFragment[0] = this.hospitalFragment;
        }else{
            arrayOfFragment[0] = this.merchantsFragment;
        }

        arrayOfFragment[1] = this.remindFragment;
        arrayOfFragment[2] = this.messageFragment;
        arrayOfFragment[3] = this.myFragment;
        this.tabFragments = arrayOfFragment;
        showFragment();
        getDictionaryList("原因分析");
        getDictionaryList("维修级别");


    }

    private void getDictionaryList(final String dicType){
        RequestBody formBody = new  FormBody.Builder()
                .add("dicType", dicType)
                .build();
        HttpUtile httpUtile = new HttpUtile(this, Constant.DOMAIN_NAME + Constant.GETDICTIONARYLIST, formBody, new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {
                if (dicType.equals("原因分析")){
                    ReasonAnalysisBase data = JSON.parseObject(returnStr,ReasonAnalysisBase.class);
                    YiZhangGuanApplication.getInstance().setReasonAnalysisList(data.getData());
                }else if (dicType.equals("维修级别")){
                    ServiceLevelBase data = JSON.parseObject(returnStr,ServiceLevelBase.class);
                    YiZhangGuanApplication.getInstance().setServiceLeveList(data.getData());
                }else if (dicType.equals("产地")){
                    OriginBase data = JSON.parseObject(returnStr,OriginBase.class);
                    YiZhangGuanApplication.getInstance().setOrigingList(data.getData());
                }else if (dicType.equals("单位")){
                    UnitBase data = JSON.parseObject(returnStr,UnitBase.class);
                    YiZhangGuanApplication.getInstance().setUnitList(data.getData());
                }else if (dicType.equals("费用名称")){
                    FeeNameBase data = JSON.parseObject(returnStr,FeeNameBase.class);
                    YiZhangGuanApplication.getInstance().setFeeNameBaseList(data.getData());
                }else if (dicType.equals("资金来源")){
                    SourcesOfFundsBase data = JSON.parseObject(returnStr,SourcesOfFundsBase.class);
                    YiZhangGuanApplication.getInstance().setSourcesOfFundsList(data.getData());
                }
            }


            @Override
            public void getReturnStrFailure(String returnStr) {


            }

            @Override
            public void getErrorStr(String errorStr) {

            }
        },false);

    }
    private boolean conType = true;

    private void showFragment(){
        if (YiZhangGuanApplication.getInstance().isAppType()){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, this.hospitalFragment)
                    .add(R.id.fragment_container, this.remindFragment)
                    .add(R.id.fragment_container, this.messageFragment)
                    .add(R.id.fragment_container, this.myFragment)
                    .hide(this.remindFragment).hide(this.messageFragment).hide(this.myFragment).show(this.hospitalFragment).commit();
        }else{
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, this.merchantsFragment)
                    .add(R.id.fragment_container, this.remindFragment)
                    .add(R.id.fragment_container, this.messageFragment)
                    .add(R.id.fragment_container, this.myFragment)
                    .hide(this.remindFragment).hide(this.messageFragment).hide(this.myFragment).show(this.merchantsFragment).commit();
        }
    }

    public void setMessageNum(int messageNum){
        Message message=new Message();
        message.what=0;
        message.obj=messageNum+"";
        handler.sendMessage(message);

    }

    @Override
    protected void getQr(String ScanResult) {
        super.getQr(ScanResult);
        try{
        String[] strarray = ScanResult.split("[|]");
        ScanCodeInput data = new ScanCodeInput();
        data.setType(ScanCodeDialog.REPAIR);
        data.setGuid(strarray[0]);
        data.setSingle_or_numbering(strarray[1]);
        Intent intent = new Intent(this,ScanCodeDialog.class);
        intent.putExtra("data",data);
        startActivityForResult(intent,0);
        }catch (Exception e){
            AbToastUtil.showToast(this,"二维码无效");
        }
        //AbToastUtil.showToast(this,ScanResult);
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
            if (con == 2) {
                newMsgNumber.setVisibility(View.GONE);
            }
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
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0){
            if (YiZhangGuanApplication.getInstance().isAppType()){
                //医院主页刷新
                this.hospitalFragment.getWebData();
            }else{
                //供应商布局
                this.merchantsFragment.getData();
            }
        }else if (requestCode == 1){
            if (YiZhangGuanApplication.getInstance().isAppType()){
                hospitalFragment.refreshUserInfo();
            }else{
                merchantsFragment.refreshUserInfo();
            }
            myFragment.refreshUserInfo();
        }
    }
    public void setTestHeadImg(String str)
    {
        if (YiZhangGuanApplication.getInstance().isAppType()){
            this.hospitalFragment.setHeadImg(str);
        }else{
            this.merchantsFragment .setHeadImg(str);
        }
        myFragment.setMyHeadImg(str);
    }
    public void setHeadImg(String str){
        //setTestHeadImg(str);
        if (YiZhangGuanApplication.getInstance().isAppType()){
            //医院布局
            hospitalFragment.refreshUserInfo();
        }else{
            //供应商布局
            merchantsFragment.refreshUserInfo();
        }
        myFragment.refreshUserInfo();
    }





    /**
     * 显示和隐藏
     * @param con 当前选中的选项卡
     */
    private void showTab(int con){
        for (int i = 0 ; i < showImgs.size();i++){
            if (i == con){
                showImgs.get(i).setVisibility(View.VISIBLE);
                hideImgs.get(i).setVisibility(View.GONE);
            }else{
                showImgs.get(i).setVisibility(View.GONE);
                hideImgs.get(i).setVisibility(View.VISIBLE);
            }
        }
    }
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23 && !isPermissionRequested) {

            isPermissionRequested = true;

            ArrayList<String> permissions = new ArrayList<>();
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    ||checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    ||checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                    ||checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED

                    ) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
                permissions.add(Manifest.permission.READ_PHONE_STATE);
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (permissions.size() == 0) {
                return;
            } else {
                requestPermissions(permissions.toArray(new String[permissions.size()]), 0);
            }
        }
    }


}
