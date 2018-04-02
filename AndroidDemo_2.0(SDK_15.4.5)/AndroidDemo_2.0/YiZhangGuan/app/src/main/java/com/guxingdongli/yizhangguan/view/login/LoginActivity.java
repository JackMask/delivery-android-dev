package com.guxingdongli.yizhangguan.view.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.util.AbDialogUtil;
import com.ab.util.AbToastUtil;
import com.alibaba.fastjson.JSON;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.YiZhangGuanApplication;
import com.guxingdongli.yizhangguan.model.LoginBase;
import com.guxingdongli.yizhangguan.util.AppUtile;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.guxingdongli.yizhangguan.view.home.HomeActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 登陆
 * Created by jackmask on 2018/2/27.
 */

public class LoginActivity extends YiZhangGuanActivity {

    @Bind(R.id.radioGroup2)
    RadioGroup radioGroup2;
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.textView)
    TextView textView;

    @Bind(R.id.hospitol_name_btn)
    RelativeLayout hospitolNameBtn;
    @Bind(R.id.imageView1)
    ImageView imageView1;
    @Bind(R.id.hospitol_name)
    EditText hospitolName;

    @Bind(R.id.hospitol_pass)
    EditText hospitolPass;
    @Bind(R.id.show_pass_btn)
    ImageView showPassBtn;
    @Bind(R.id.hospital)
    LinearLayout hospital;

    @Bind(R.id.merchants_name)
    EditText merchantsName;

    @Bind(R.id.merchants_password)
    EditText merchantsPassword;
    @Bind(R.id.show_pass_btn_g)
    ImageView showPassBtnG;
    @Bind(R.id.merchants)
    LinearLayout merchants;
    @Bind(R.id.log_in)
    TextView logIn;
    @Bind(R.id.registered)
    TextView registered;
    @Bind(R.id.forget_password)
    TextView forgetPassword;
    @Bind(R.id.linearLayout)
    LinearLayout linearLayout;
    @Bind(R.id.hospitol_string)
    TextView hospitolNameString;

    int hospitolFlag = 1;
    int merchantsFlag = 1;
    @Bind(R.id.hospital_button)
    RadioButton hospitalButton;
    @Bind(R.id.merchants_button)
    RadioButton merchantsButton;
    private String hospitolString;
    boolean logInTheWay = true;//登陆方式，true是医院用户，false是供应商
    private static boolean isPermissionRequested = false;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) //如果消息是刚才发送的标识
            {
                RequestBody formBody = (RequestBody) msg.obj;


            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setAnimCon(true);
        setView();
        requestPermission();
    }

    private void setView() {
        hospitolName.setText(YiZhangGuanApplication.getInstance().getMyInfo().getHospitalLoginName());
        hospitolNameString.setText(YiZhangGuanApplication.getInstance().getMyInfo().getToken_hospital());
        merchantsName.setText(YiZhangGuanApplication.getInstance().getMyInfo().getSupplierLoginName());

        if (YiZhangGuanApplication.getInstance().isAppType()){
            hospitalButton.setChecked(true);
            hospital.setVisibility(View.VISIBLE);
            merchants.setVisibility(View.GONE);
        }else{
            hospital.setVisibility(View.GONE);
            merchants.setVisibility(View.VISIBLE);
            merchantsButton.setChecked(true);
        }

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) LoginActivity.this.findViewById(radioGroup2.getCheckedRadioButtonId());
                if (rb.getText().toString().equals("医院用户")) {
                    hospital.setVisibility(View.VISIBLE);
                    merchants.setVisibility(View.GONE);
                    logInTheWay = true;
                } else {
                    hospital.setVisibility(View.GONE);
                    merchants.setVisibility(View.VISIBLE);
                    logInTheWay = false;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {
            hospitolName.setText(YiZhangGuanApplication.getInstance().getMyInfo().getHospitalLoginName());
            hospitolNameString.setText(YiZhangGuanApplication.getInstance().getMyInfo().getToken_hospital());
            merchantsName.setText(YiZhangGuanApplication.getInstance().getMyInfo().getSupplierLoginName());
        } else if (requestCode == 11 && resultCode == 1001) {
            if (data != null)
                hospitolNameString.setText(data.getStringExtra("hospital"));
        }
    }

    @OnClick({R.id.hospitol_name, R.id.show_pass_btn, R.id.show_pass_btn_g, R.id.registered, R.id.log_in, R.id.forget_password, R.id.hospitol_name_btn})
    public void onViewClicked(View view) {
        final Intent intent;
        switch (view.getId()) {
            case R.id.forget_password://忘记密码
                intent = new Intent(this, ForgetPasswordActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.log_in://登录
                YiZhangGuanApplication.getInstance().setAppType(logInTheWay);
                String loginName;
                String loginPassword;
                RequestBody formBody;
                if (logInTheWay) {
                    hospitolString = hospitolNameString.getText().toString();
                    loginName = hospitolName.getText().toString();
                    loginPassword = hospitolPass.getText().toString();
                    if (!AppUtile.checkNull(LoginActivity.this, hospitolString, "医院名称", true))
                        break;
                    if (!AppUtile.checkNull(LoginActivity.this, loginName, "用户名/工号", true))
                        break;
                    if (!AppUtile.checkNull(LoginActivity.this, loginPassword, "密码", true))
                        break;
                    if (loginPassword.length() < 6) {
                        AbToastUtil.showToast(LoginActivity.this, "密码长度最少为6位");
                        break;
                    }

                    formBody = new FormBody.Builder()
                            .add("Id", "0")
                            .add("JPushId", "11111")
                            .add("LoginName", loginName)
                            .add("LoginPassword", loginPassword)
                            .add("Name", hospitolString)
                            .add("UserType", "1")
                            .build();
                } else {
                    loginName = merchantsName.getText().toString();
                    loginPassword = merchantsPassword.getText().toString();
                    if (!AppUtile.checkNull(LoginActivity.this, loginName, "用户名", true))
                        break;
                    if (!AppUtile.checkNull(LoginActivity.this, loginPassword, "密码", true))
                        break;
                    if (loginPassword.length() < 6) {
                        AbToastUtil.showToast(LoginActivity.this, "密码长度最少为6位");
                        break;
                    }
                    formBody = new FormBody.Builder()
                            .add("Id", "0")
                            .add("JPushId", "11111")
                            .add("LoginName", loginName)
                            .add("LoginPassword", loginPassword)
                            .add("UserType", "2")
                            .build();
                }

               /* Message message  = new Message();
                message.what = 0 ;
                message.obj = formBody;
                handler.sendMessage(message);*/

                HttpUtile httpUtile = new HttpUtile(LoginActivity.this, Constant.DOMAIN_NAME + Constant.LOGIN, formBody, new HttpUtileCallBack() {
                    @Override
                    public void getReturnStr(String returnStr) {

                        LoginBase data = JSON.parseObject(returnStr, LoginBase.class);
                        //AbToastUtil.showToast(LoginActivity.this,data.getMsg());
                        YiZhangGuanApplication.getInstance().getMyInfo().setName(data.getData().getUser().getName());
                        YiZhangGuanApplication.getInstance().getMyInfo().setLoginName(data.getData().getUser().getLoginName());
                        YiZhangGuanApplication.getInstance().getMyInfo().setAdmin(data.getData().getUser().isIsAdmin());
                        YiZhangGuanApplication.getInstance().getMyInfo().setDepartmentName(data.getData().getUser().getDepartmentName());
                        YiZhangGuanApplication.getInstance().getMyInfo().setEmail(data.getData().getUser().getEmail());
                        YiZhangGuanApplication.getInstance().getMyInfo().setHeadImg(data.getData().getUser().getHeadImg());
                        YiZhangGuanApplication.getInstance().getMyInfo().setRealName(data.getData().getUser().getRealName());
                        YiZhangGuanApplication.getInstance().getMyInfo().setMobilePhone(data.getData().getUser().getMobilePhone());
                        YiZhangGuanApplication.getInstance().getMyInfo().setTokenData(data.getData().getTokenData().getAccess_token());
                        YiZhangGuanApplication.getInstance().getMyInfo().setToken_type(data.getData().getTokenData().getToken_type());
                        YiZhangGuanApplication.getInstance().getMyInfo().setUid(data.getData().getUser().getUid());
                        YiZhangGuanApplication.getInstance().getMyInfo().setUserType(data.getData().getUser().getUserType() + "");
                        YiZhangGuanApplication.getInstance().getMyInfo().setEngineer(data.getData().getUser().isIsEngineer());
                        YiZhangGuanApplication.getInstance().getMyInfo().setAppType(logInTheWay);
                        if (logInTheWay) {
                            YiZhangGuanApplication.getInstance().getMyInfo().setToken_hospital(hospitolString);
                            YiZhangGuanApplication.getInstance().getMyInfo().setHospitalLoginName(data.getData().getUser().getLoginName());
                        }else{
                            YiZhangGuanApplication.getInstance().getMyInfo().setSupplierLoginName(data.getData().getUser().getLoginName());
                        }
                        YiZhangGuanApplication.getInstance().setEngineer(data.getData().getUser().isIsEngineer());
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        String alias = data.getData().getUser().getUid().replace("-", "");
                        JPushInterface.setAlias(LoginActivity.this, 1001, alias);
                        AbDialogUtil.removeDialog(LoginActivity.this);
                        startActivity(intent);
                        finish();

                    }

                    @Override
                    public void getReturnStrFailure(String returnStr) {
                        Looper.prepare();
                        AbDialogUtil.removeDialog(LoginActivity.this);
                        Toast.makeText(LoginActivity.this, returnStr, Toast.LENGTH_LONG).show();
                        Looper.loop();

                        // AbToastUtil.showToast(LoginActivity.this,returnStr);
                    }

                    @Override
                    public void getErrorStr(String errorStr) {
                        AbDialogUtil.removeDialog(LoginActivity.this);
                        System.out.println("errorStr = " + errorStr);
                    }
                },true);

                break;
            case R.id.registered://注册
                intent = new Intent(this, RegisteredActivity.class);
                startActivityForResult(intent, 10);
                break;
            case R.id.hospitol_name_btn://选择医院名称
                intent = new Intent(this, SelectAddressActivity.class);
                startActivityForResult(intent, 11);
                break;
            case R.id.show_pass_btn_g://供应商密码是否显示
                switch (merchantsFlag) {
                    case 0:
                        showPassBtnG.setActivated(false);
                        merchantsFlag = 1;
                        merchantsPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        break;
                    case 1:
                        showPassBtnG.setActivated(true);
                        merchantsFlag = 0;
                        merchantsPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                break;
            case R.id.show_pass_btn://医院密码是否显示
                switch (hospitolFlag) {
                    case 0:
                        showPassBtn.setActivated(false);
                        hospitolFlag = 1;
                        hospitolPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        break;
                    case 1:
                        showPassBtn.setActivated(true);
                        hospitolFlag = 0;
                        hospitolPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                break;
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
