package com.yum.two_yum.view.login;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.util.AbDialogUtil;
import com.alibaba.fastjson.JSON;
import com.yum.two_yum.ExampleUtil;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BasDeviceBase;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.BaseReturn;
import com.yum.two_yum.base.LoginBase;
import com.yum.two_yum.base.SearchNearbyDataBase;
import com.yum.two_yum.base.UploadFlieBase;
import com.yum.two_yum.base.input.LoginInputBase;
import com.yum.two_yum.base.laguageResponse;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.AutoEndEditText2;
import com.yum.two_yum.utile.ContainsEmojiEditText;
import com.yum.two_yum.utile.LanguageReceiver;
import com.yum.two_yum.view.ReportActivity;
import com.yum.two_yum.view.client.ClientActivity;
import com.yum.two_yum.view.client.business.SubmitOrdersActivity;
import com.yum.two_yum.view.client.clientorder.AddToAddressActivity;
import com.yum.two_yum.view.client.clientorder.SearchNearbyActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.jpush.android.service.JPushMessageReceiver;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;

/**
 * @author 余先德
 * @data 2018/4/3
 */

public class LoginActivity extends BaseActivity {

    @Bind(R.id.del_btn)
    ImageView delBtn;
    @Bind(R.id.email_et)
    ContainsEmojiEditText emailEt;
    @Bind(R.id.password_et)
    ContainsEmojiEditText passwordEt;
    @Bind(R.id.login_btn)
    TextView loginBtn;
    @Bind(R.id.forgot_password)
    TextView forgotPassword;
    @Bind(R.id.del_btn1)
    ImageView delBtn1;
    @Bind(R.id.del_btn2)
    ImageView delBtn2;

    boolean contype = false,password = false;

    public static LoginActivity instance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        instance=this;
        ButterKnife.bind(this);
        delBtn1.setVisibility(View.GONE);
        delBtn2.setVisibility(View.GONE);
        setView();
    }



    @Override
    protected void setView() {
        super.setView();
        emailEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String reg = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
                Pattern pattern = Pattern.compile(reg);
                if(!TextUtils.isEmpty(s.toString())){
                    delBtn1.setVisibility(View.VISIBLE);
                    Matcher matcher = pattern.matcher(s.toString());
                    boolean b = matcher.matches();
                    if (b){
                        contype = true;
                        if (password) {
                            loginBtn.setBackgroundResource(R.drawable.button_red_bright);
                        }
                    }else{
                        contype = false;
                        loginBtn.setBackgroundResource(R.drawable.button_red);
                    }
                }else{
                    delBtn1.setVisibility(View.GONE);
                    contype = false;
                    loginBtn.setBackgroundResource(R.drawable.button_red);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passwordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String content = s.toString();
                if (!TextUtils.isEmpty(content)&&content.length()>0){
                    delBtn2.setVisibility(View.VISIBLE);
                    password =true;
                    if (contype){
                        loginBtn.setBackgroundResource(R.drawable.button_red_bright);
                    }
                }else{
                    delBtn2.setVisibility(View.GONE);
                    password = false;
                    loginBtn.setBackgroundResource(R.drawable.button_red);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        emailEt.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {

                    // 获得焦点
                if (!TextUtils.isEmpty(emailEt.getText().toString())){
                    delBtn1.setVisibility(View.VISIBLE);
                }else{
                    delBtn1.setVisibility(View.GONE);
                }
                } else {

                    // 失去焦点
                    delBtn1.setVisibility(View.GONE);
                }

            }


        });
        passwordEt.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {

                    // 获得焦点
                    if (!TextUtils.isEmpty(passwordEt.getText().toString())){
                        delBtn2.setVisibility(View.VISIBLE);
                    }else{
                        delBtn2.setVisibility(View.GONE);
                    }
                } else {

                    // 失去焦点
                    delBtn2.setVisibility(View.GONE);
                }

            }


        });

    }
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
            }
        }
    };
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
            }
            ExampleUtil.showToast(logs, getApplicationContext());
        }
    };
    private static final int MSG_SET_ALIAS = 1001;

    @OnClick({R.id.del_btn, R.id.login_btn, R.id.forgot_password,R.id.create_account_btn,R.id.del_btn1,R.id.del_btn2})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.del_btn2:
                passwordEt.setText("");
                break;
            case R.id.del_btn1:
                emailEt.setText("");
                break;
            case R.id.del_btn:
                YumApplication.getInstance().getMyInformation().setClientOk("0");
                //if (!YumApplication.getInstance().isBackToHome()) {
                    intent = new Intent();
                    intent.setClassName(getPackageName(), "com.yum.two_yum.view.client.ClientActivity");

                    if (getIntent().getBooleanExtra("type", true)) {
                        if (getPackageManager().resolveActivity(intent, 0) == null) {

                            // 说明系统中不存在这个activity
                            Intent intent1 = new Intent(LoginActivity.this, ClientActivity.class);
                            startActivity(intent1);
                        } else {
                            setResult(-3);
                        }
                    } else {
                        Intent intent1 = new Intent(LoginActivity.this, ClientActivity.class);
                        startActivity(intent1);
                    }
                    finish();
                    overridePendingTransition(R.anim.activity_open, R.anim.out);
//                }else{
//                    YumApplication.getInstance().setBackToHome(false);
//                    Intent intent1 = new Intent(LoginActivity.this, ClientActivity.class);
//                    startActivity(intent1);
//                    finish();
//                    overridePendingTransition(R.anim.activity_open, R.anim.out);
//                }
                break;
            case R.id.login_btn://登录
                if (password&&contype) {
                    if (passwordEt.getText().toString().length() < 8) {
                        showMsg(getResources().getString(R.string.PASSWORDS820CHARACTERS));
                        break;
                    }
                    showLoading();
                    OkHttpUtils
                            .get()
                            .url(Constant.USER_LOGIN)
                            .addHeader("Accept-Language", AppUtile.getLanguage())
                            .addParams("email", emailEt.getText().toString())
                            .addParams("password", passwordEt.getText().toString())
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int i) {
                                    if (AppUtile.getNetWork(e.getMessage())) {
                                        showMsg(getString(R.string.NETERROR));
                                    }
                                    dismissLoading();
                                }

                                @Override
                                public void onResponse(String s, int i) {
                                    dismissLoading();

                                    // showMsg(loginBase.getMessage());
                                    //AbDialogUtil.removeDialog(this);
                                    // System.out.println("content = " +s);
                                    if (AppUtile.setCode(s, LoginActivity.this)) {
                                        LoginBase loginBase = JSON.parseObject(s, LoginBase.class);
                                        System.out.println(loginBase.getData().getToken());
                                        YumApplication.getInstance().setLoginNowType(true);
                                        YumApplication.getInstance().getMyInformation().setClientOk("0");
                                        YumApplication.getInstance().getMyInformation().setAvatar(loginBase.getData().getAvatar());
                                        YumApplication.getInstance().getMyInformation().setBornTime(loginBase.getData().getBornTime());
                                        YumApplication.getInstance().getMyInformation().setCurrentType(loginBase.getData().getCurrentType());
                                        YumApplication.getInstance().getMyInformation().setEmail(loginBase.getData().getEmail());
                                        YumApplication.getInstance().getMyInformation().setGender(loginBase.getData().getGender());
                                        YumApplication.getInstance().getMyInformation().setToken(loginBase.getData().getToken());
                                        YumApplication.getInstance().getMyInformation().setType(loginBase.getData().getType());
                                        YumApplication.getInstance().getMyInformation().setUid(loginBase.getData().getId());
                                        YumApplication.getInstance().getMyInformation().setUsername(loginBase.getData().getUsername());
                                        YumApplication.getInstance().getMyInformation().setMerchantType(loginBase.getData().getMerchantType());
                                        JPushInterface.setAlias(LoginActivity.this, 1001, loginBase.getData().getId());
                                       // JPushInterface.resumePush(getApplicationContext());
                                        setLanguage();
//                                        if (getIntent().getBooleanExtra("test",false)) {
//                                            mHandler1.postDelayed(r, 1000);
//                                            YumApplication.getInstance().setLoginType(true);
//                                            if (getIntent().getStringExtra("TAG").equals(SearchNearbyActivity.TAG)){
//                                                Intent intent1 = new Intent(LoginActivity.this,AddToAddressActivity.class);
//                                                startActivityForResult(intent1,1);
//                                                overridePendingTransition(R.anim.activity_open,R.anim.in);
//                                            }else if (getIntent().getStringExtra("TAG").equals(ReportActivity.TAG)){
//                                                Intent intent = new Intent(LoginActivity.this, ReportActivity.class);
//                                                intent.putExtra("userid", getIntent().getStringExtra("userid"));
//                                                startActivity(intent);
//                                                overridePendingTransition(R.anim.activity_open, R.anim.in);
//                                            }else if (getIntent().getStringExtra("TAG").equals(SubmitOrdersActivity.TAG)){
//                                                Intent intent = new Intent(LoginActivity.this, SubmitOrdersActivity.class);
//                                                SearchNearbyDataBase data = (SearchNearbyDataBase)getIntent().getSerializableExtra("data");
//                                                if (data!=null&&AppUtile.GetDistance(Double.valueOf(data.getLat()),Double.valueOf(data.getLng()),getIntent().getDoubleExtra("lat",0),getIntent().getDoubleExtra("lng",0))/1609.344<=5) {
//                                                    intent.putExtra("addressData", getIntent().getSerializableExtra("data"));
//                                                }
//                                                intent.putExtra("time",getIntent().getStringExtra("time"));
//                                                intent.putExtra("data", getIntent().getSerializableExtra("data"));
//                                                startActivityForResult(intent, 0);
//                                            }
//
//                                        }else {
                                            if (YumApplication.getInstance().getMyInformation().isFirst()) {
                                                dismissLoading();
                                                if (!getIntent().getBooleanExtra("type", true)) {
                                                    if (AppUtile.isClsRunning("com.yum.two_yum.view.client.ClientActivity", LoginActivity.this)) {
                                                        setResult(3);
                                                    } else {
                                                        Intent intent1 = new Intent(LoginActivity.this, ClientActivity.class);
                                                        startActivity(intent1);
                                                    }
                                                } else {
                                                    setResult(3);
                                                }
                                                finish();
                                                overridePendingTransition(R.anim.activity_open, R.anim.out);
                                            } else {
                                                report(loginBase.getData().getId());
                                            }
                                        }
                                //    }
                                }

                            });
                }
                break;
            case R.id.forgot_password://忘记密码



                intent =new Intent(this,ForgetPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.create_account_btn://新建账号
                intent =new Intent(this,RegisteredActivity.class);
//                if (getIntent().getBooleanExtra("test",false)) {
//                    intent.putExtra("test",true);
//                    intent.putExtra("TAG",getIntent().getStringExtra("TAG"));
//                    if (getIntent().getStringExtra("TAG").equals(ReportActivity.TAG)){
//                        intent.putExtra("userid", getIntent().getStringExtra("userid"));
//                    }else if (getIntent().getStringExtra("TAG").equals(SubmitOrdersActivity.TAG)){
//                        SearchNearbyDataBase data = (SearchNearbyDataBase)getIntent().getSerializableExtra("data");
//                        if (data!=null&&AppUtile.GetDistance(Double.valueOf(data.getLat()),Double.valueOf(data.getLng()),getIntent().getDoubleExtra("lat",0),getIntent().getDoubleExtra("lng",0))/1609.344<=5) {
//                            intent.putExtra("addressData", getIntent().getSerializableExtra("data"));
//                        }
//                        intent.putExtra("time",getIntent().getStringExtra("time"));
//                        intent.putExtra("data", getIntent().getSerializableExtra("data"));
//                    }
//                }
                startActivityForResult(intent,0);
                break;
        }
    }

  ;

    private void report(String uid){
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;

        BasDeviceBase data = new BasDeviceBase();
        data.setDeviceModel(AppUtile.getSystemModel());
        data.setUserId(uid);
        data.setDeviceSerial(AppUtile.getIMEI(this));
        data.setDeviceType("ANDROID");
        data.setResolution(screenWidth+"*"+screenHeight);
        data.setSystemVersion(AppUtile.getSystemVersion());
        OkHttpUtils
                .postString()
                .url(Constant.BASDEVICE)
                .mediaType(MediaType.parse("application/json"))
                .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                .content(JSON.toJSONString(data))
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        if (AppUtile.getNetWork(e.getMessage())){
                            showMsg(getString(R.string.NETERROR));
                        }
                        dismissLoading();
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        dismissLoading();
                        BaseReturn data = JSON.parseObject(s, BaseReturn.class);
                        // showMsg(loginBase.getMessage());
                        //AbDialogUtil.removeDialog(this);
                        // System.out.println("content = " +s);
                        if (AppUtile.setCode(s,LoginActivity.this)) {
                            YumApplication.getInstance().getMyInformation().setFirst(true);
                            setLanguage();
                            if (getIntent().getBooleanExtra("type",true)) {
                                Intent intent = new Intent();
                                intent.setClassName(getPackageName(), "com.yum.two_yum.view.client.ClientActivity");
                                if (getPackageManager().resolveActivity(intent, 0) == null) {

                                    // 说明系统中不存在这个activity
                                    Intent intent1 = new Intent(LoginActivity.this, ClientActivity.class);
                                    startActivity(intent1);
                                }else{
                                    setResult(3);
                                }
//                                if (AppUtile.isClsRunning("com.yum.two_yum.view.client.ClientActivity", LoginActivity.this)) {
//
//                                } else {
//
//                                }
                            }else{
                                setResult(3);
                            }
                            //setResult(3);
                            finish();
                            overridePendingTransition(R.anim.activity_open, R.anim.out);
                        }
                       // showMsg(data.getMsg());
                    }


                });
    }
    private void setLanguage(){
        if (!TextUtils.isEmpty(YumApplication.getInstance().getMyInformation().getToken())&&!TextUtils.isEmpty(YumApplication.getInstance().getMyInformation().getUid())) {
            laguageResponse data = new laguageResponse();
            data.setUserId(YumApplication.getInstance().getMyInformation().getUid());
            data.setType(AppUtile.getLanguageInt()+"");
            OkHttpUtils
                    .postString()
                    .mediaType(MediaType.parse("application/json"))
                    .addHeader("Accept-Language", AppUtile.getLanguage())
                    .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                    .url(Constant.HOME_LIST)
                    .content(JSON.toJSONString(data))
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

                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_OK:
                if (!TextUtils.isEmpty(YumApplication.getInstance().getMyInformation().getClientOk())&&YumApplication.getInstance().getMyInformation().getClientOk().equals("1")) {
                    YumApplication.getInstance().getMyInformation().setClientOk("0");
                    Intent intent = new Intent(this,ClientActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent();
                    intent.setClassName(getPackageName(), "com.yum.two_yum.view.client.ClientActivity");
                    if (getPackageManager().resolveActivity(intent, 0) == null) {

                        // 说明系统中不存在这个activity
                        Intent intent1 = new Intent(LoginActivity.this, ClientActivity.class);
                        startActivity(intent1);
                    } else {
                        setResult(3);
                    }
//                if (getIntent().getBooleanExtra("type",false)) {
//                    Intent intent1 = new Intent(LoginActivity.this, ClientActivity.class);
//                    startActivity(intent1);
//                }
                    //setResult(3);
                    finish();
                    overridePendingTransition(R.anim.activity_open, R.anim.out);
                }
                break;
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //Log.e("MyApplication","onConfigurationChanged");
        super.onConfigurationChanged(newConfig);

    }
}
