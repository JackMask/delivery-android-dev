package com.guxingdongli.yizhangguan.view.myinfo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.util.AbDialogUtil;
import com.ab.util.AbToastUtil;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.YiZhangGuanApplication;
import com.guxingdongli.yizhangguan.util.AppUtile;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.guxingdongli.yizhangguan.view.login.LoginActivity;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by jackmask on 2018/3/5.
 */

public class ModifyInformationActivity extends YiZhangGuanActivity {

    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.blue_save_btn)
    TextView blueSaveBtn;
    @Bind(R.id.mail_tv)
    EditText mailEt;
    @Bind(R.id.account_tv)
    TextView account_tv;
    @Bind(R.id.name_tv)
    EditText nameTv;
    @Bind(R.id.phone_tv)
    TextView phoneTv;


    @Bind(R.id.phone_num_et)
    EditText phoneNumEt;
    @Bind(R.id.new_phone_num_tv)
    EditText newPhoneNumTv;
    @Bind(R.id.phone_code_et)
    EditText phoneCodeEt;
    @Bind(R.id.phone_laout)
    LinearLayout phoneLaout;
    @Bind(R.id.send_phone_code_btn)
    TextView sendPhoneCodeBtn;

    private AppUtile.TimeCount time;
    private boolean replacePhoneCon = false;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            HttpUtile httpUtile;
            switch (what){
                case 0:
                    if (!AppUtile.checkNull(ModifyInformationActivity.this,newPhoneNumTv.getText().toString(),"新手机号",true)){
                        break;
                    }
                    RequestBody formBody = new FormBody.Builder()
                            .add("Id", "0")
                            .add("ActionType", "更换手机")
                            .add("MobilePhone", newPhoneNumTv.getText().toString())
                            .build();
                    httpUtile = new HttpUtile(ModifyInformationActivity.this, Constant.DOMAIN_NAME + Constant.GETVERCODE, formBody, new HttpUtileCallBack() {
                        @Override
                        public void getReturnStr(String returnStr) {
                            Looper.prepare();
                            AbDialogUtil.removeDialog(ModifyInformationActivity.this);
                            time.start();
                            AbToastUtil.showToast(ModifyInformationActivity.this, "验证码已发送");
                            Looper.loop();
                        }
                        @Override
                        public void getReturnStrFailure(String returnStr) {
                            Looper.prepare();
                            AbDialogUtil.removeDialog(ModifyInformationActivity.this);
                            AbToastUtil.showToast(ModifyInformationActivity.this, returnStr);
                            Looper.loop();

                        }
                        @Override
                        public void getErrorStr(String errorStr) {

                        }
                    },true);
                    break;
                case 1:
                    String json = "{\"id\":1,\"uid\":\""+ YiZhangGuanApplication.getInstance().getMyInfo().getUid()+"\",\"realName\":\""+((nameTv.getText().toString().indexOf("*")!=-1)?YiZhangGuanApplication.getInstance().getMyInfo().getMobilePhone():nameTv.getText().toString())+
                            "\",\"email\":\""+mailEt.getText().toString().trim()+"\"";
                    if (replacePhoneCon){
                        if (!AppUtile.checkNull(ModifyInformationActivity.this,phoneNumEt.getText().toString(),"原手机号",true)){
                            break;
                        }
                        if (!AppUtile.checkNull(ModifyInformationActivity.this,newPhoneNumTv.getText().toString(),"新手机号",true)){
                            break;
                        }
                        if (!AppUtile.checkNull(ModifyInformationActivity.this,phoneCodeEt.getText().toString(),"验证码",true)){
                            break;
                        }
                        json+=",\"phone\":"+phoneNumEt.getText().toString()+",\"mobilePhone\":"+newPhoneNumTv.getText().toString()+",\"verificationCode\":\""+
                                phoneCodeEt.getText().toString()+"\"";
                    }
                    json+="}";
                    httpUtile  = new HttpUtile(ModifyInformationActivity.this, Constant.DOMAIN_NAME + Constant.UPDATEUSER, json, new HttpUtileCallBack() {
                        @Override
                        public void getReturnStr(String returnStr)
                        {
                            Looper.prepare();
                            AbDialogUtil.removeDialog(ModifyInformationActivity.this);
                            YiZhangGuanApplication.getInstance().getMyInfo().setEmail(mailEt.getText().toString().trim());
                            YiZhangGuanApplication.getInstance().getMyInfo().setRealName(nameTv.getText().toString());
                            if (replacePhoneCon){
                                if (!TextUtils.isEmpty(newPhoneNumTv.getText().toString())) {
                                    YiZhangGuanApplication.getInstance().getMyInfo().setRealName(newPhoneNumTv.getText().toString());
                                }
                            }
                            AbToastUtil.showToast(ModifyInformationActivity.this, "保存成功");
                            finish();
                            Looper.loop();
                        }
                        @Override
                        public void getReturnStrFailure(String returnStr) {
                            Looper.prepare();
                            AbDialogUtil.removeDialog(ModifyInformationActivity.this);
                            Toast.makeText(ModifyInformationActivity.this,returnStr,Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }
                        @Override
                        public void getErrorStr(String errorStr) {
                            System.out.println("errorStr = "+errorStr);
                        }
                    },true);

                    break;
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_information);
        ButterKnife.bind(this);
        setView();
    }

    private void setView() {
        titleText.setText("基本信息修改");
        blueSaveBtn.setVisibility(View.VISIBLE);
        blueSaveBtn.setText("保存");
        account_tv.setText(YiZhangGuanApplication.getInstance().getMyInfo().getLoginName());
        nameTv.setText(YiZhangGuanApplication.getInstance().getMyInfo().getRealName());
        mailEt.setText(YiZhangGuanApplication.getInstance().getMyInfo().getEmail());
        String phone = YiZhangGuanApplication.getInstance().getMyInfo().getMobilePhone();
        phoneTv.setText(phone);

        time = new AppUtile.TimeCount(60000, 1000, sendPhoneCodeBtn);
    }

    @OnClick({R.id.return_btn, R.id.blue_save_btn, R.id.replace_phone_btn, R.id.send_phone_code_btn})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.return_btn:
                finish();
                break;
            case R.id.blue_save_btn://修改个人信息
                mHandler.sendEmptyMessage(1);

                break;
            case R.id.replace_phone_btn:
                if (phoneLaout.getVisibility() == View.VISIBLE){
                    phoneLaout.setVisibility(View.GONE);
                    replacePhoneCon = false;
                }else{
                    phoneLaout.setVisibility(View.VISIBLE);
                    replacePhoneCon = true;
                }

                break;
            case R.id.send_phone_code_btn://发送验证码
                mHandler.sendEmptyMessage(0);

                break;
        }
    }


}
