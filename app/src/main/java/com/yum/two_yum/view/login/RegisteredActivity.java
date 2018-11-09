package com.yum.two_yum.view.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ab.util.AbDialogUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.LoginBase;
import com.yum.two_yum.base.RegisteredBase;
import com.yum.two_yum.base.SearchNearbyDataBase;
import com.yum.two_yum.base.UploadFlieBase;
import com.yum.two_yum.base.input.RegisterInputBase;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.AutoEndEditText2;
import com.yum.two_yum.utile.CircleImageView;
import com.yum.two_yum.utile.ContainsEmojiEditText;
import com.yum.two_yum.utile.PicassoImageLoader;
import com.yum.two_yum.view.ReportActivity;
import com.yum.two_yum.view.client.ClientActivity;
import com.yum.two_yum.view.client.business.SubmitOrdersActivity;
import com.yum.two_yum.view.client.clientorder.AddToAddressActivity;
import com.yum.two_yum.view.client.clientorder.SearchNearbyActivity;
import com.yum.two_yum.view.guide.ReleaseActivity;
import com.yum.two_yum.view.my.PrivacyServiceTermsActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * @author 余先德
 * @data 2018/4/7
 */

public class RegisteredActivity extends BaseActivity {

    private static final String TAG = "RegisteredActivity";

    @Bind(R.id.hospital_head_img)
    CircleImageView hospitalHeadImg;
    @Bind(R.id.email_et)
    ContainsEmojiEditText emailEt;
    @Bind(R.id.password_et)
    ContainsEmojiEditText passwordEt;
    @Bind(R.id.forgot_password)
    TextView forgotPassword;
    @Bind(R.id.registered_btn)
    TextView registeredBtn;
    @Bind(R.id.head_btn)
    RelativeLayout headBtn;
    @Bind(R.id.prompt_tv)
            TextView promptTv;
    @Bind(R.id.del_btn1)
    ImageView delBtn1;
    @Bind(R.id.del_btn2)
    ImageView delBtn2;

    boolean contype = false,password = false,heatType = false;
    private String headPath;
    private int httpCode = 0;
    public static RegisteredActivity instance;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        instance = this;
        ButterKnife.bind(this);
        delBtn1.setVisibility(View.GONE);
        delBtn2.setVisibility(View.GONE);
        setView();
    }

    protected void setView() {
        emailEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String reg = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
                Pattern pattern = Pattern.compile(reg);
                if (!TextUtils.isEmpty(s.toString())) {
                    delBtn1.setVisibility(View.VISIBLE);
                    Matcher matcher = pattern.matcher(s.toString());
                    boolean b = matcher.matches();
                    if (b) {
                        contype = true;
                        if (password)
                            registeredBtn.setBackgroundResource(R.drawable.button_red_bright);

                    } else {
                        contype = false;
                        registeredBtn.setBackgroundResource(R.drawable.button_red);
                    }
                } else {
                    delBtn1.setVisibility(View.GONE);
                    contype = false;
                    registeredBtn.setBackgroundResource(R.drawable.button_red);
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
                        registeredBtn.setBackgroundResource(R.drawable.button_red_bright);
                    }
                }else{
                    delBtn2.setVisibility(View.GONE);
                    password = false;
                    registeredBtn.setBackgroundResource(R.drawable.button_red);
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
        forgotPassword.setTextColor(getResources().getColor(R.color.color_484848));
        String protocol = getResources().getString(R.string.BYPROCEEDINGYOUAGREEWITHTHE);
        String termsofservice = getResources().getString(R.string.TERMSOFSERVICE_);
        String privacypolicy = getResources().getString(R.string.PRIVACYPOLICY);
        SpannableString spanTermsofservice = new SpannableString(termsofservice);
        SpannableString spanPrivacypolicy = new SpannableString(privacypolicy);
        ClickableSpan clickTermsofservice = new ShuoMClickableSpan(termsofservice, this,true);
        ClickableSpan clickPrivacypolicy = new ShuoMClickableSpan(privacypolicy, this,false);
        spanTermsofservice.setSpan(clickTermsofservice, 0, termsofservice.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spanPrivacypolicy.setSpan(clickPrivacypolicy, 0, privacypolicy.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        if (getLanguage().equals("zh-hk")||getLanguage().equals("zh-cn")){
            forgotPassword.setText(protocol);
            forgotPassword.append(spanTermsofservice);
            forgotPassword.append(getResources().getString(R.string.AND));
            forgotPassword.append(spanPrivacypolicy);
            forgotPassword.setMovementMethod(LinkMovementMethod.getInstance());
        }else {
            forgotPassword.setText(protocol + " ");
            forgotPassword.append(spanTermsofservice);
            forgotPassword.append(" " + getResources().getString(R.string.AND) + " ");
            forgotPassword.append(spanPrivacypolicy);
            forgotPassword.setMovementMethod(LinkMovementMethod.getInstance());
        }
        forgotPassword.setHighlightColor(getResources().getColor(android.R.color.transparent));
    }

    public class ShuoMClickableSpan extends ClickableSpan {

        String string;
        Context context;
        boolean type;
        public ShuoMClickableSpan(String str,Context context,boolean type){
            super();
            this.string = str;
            this.type = type;
            this.context = context;
        }


        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.BLUE);
            ds.setUnderlineText(false);
            //ds.setHighlightColor(getResources().getColor(android.R.color.transparent));
        }


        @Override
        public void onClick(View widget) {
            Intent intent;
            if (type){//服务条款
                intent = new Intent(RegisteredActivity.this, PrivacyServiceTermsActivity.class);
                intent.putExtra("type",false);
                startActivity(intent);
            }else{//隐私政策
                intent = new Intent(RegisteredActivity.this, PrivacyServiceTermsActivity.class);
                intent.putExtra("type",true);
                startActivity(intent);
            }



        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意，可以做你要做的事情了。
                    GalleryConfig galleryConfig = new GalleryConfig.Builder()
                            .imageLoader(new PicassoImageLoader())    // ImageLoader 加载框架（必填）
                            .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                            .provider("com.yum.two_yum.fileprovider")   // provider (必填)
                            .multiSelect(false)                      // 是否多选   默认：false
                            .isShowCamera(false)                     // 是否现实相机按钮  默认：false
                            .crop(true)                             // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效

                            .crop(true, 1, 1, 500, 500)
                            .filePath("/Gallery/Pictures")          // 图片存放路径
                            .build();
                    GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(this);
                } else {
                    // 权限被用户拒绝了，可以提示用户,关闭界面等等。

                }
                return;
            }
        }
    }
    @OnClick({R.id.del_btn, R.id.registered_btn,R.id.head_btn,R.id.del_btn1,R.id.del_btn2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.del_btn2:
                passwordEt.setText("");
                break;
            case R.id.del_btn1:
                emailEt.setText("");
                break;
            case R.id.head_btn:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {
                    // 没有权限，可以在这里重新申请权限。

                    ActivityCompat.requestPermissions(
                            this,
                            PERMISSIONS_STORAGE,
                            REQUEST_EXTERNAL_STORAGE
                    );
                }else{
                    // 有权限了。
                    GalleryConfig galleryConfig = new GalleryConfig.Builder()
                            .imageLoader(new PicassoImageLoader())    // ImageLoader 加载框架（必填）
                            .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                            .provider("com.yum.two_yum.fileprovider")   // provider (必填)
                            .multiSelect(false)                      // 是否多选   默认：false
                            .isShowCamera(false)                     // 是否现实相机按钮  默认：false
                            .crop(true)                             // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
                            .crop(true, 1, 1, 500, 500)
                            .filePath("/Gallery/Pictures")          // 图片存放路径
                            .build();
                    GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(this);
                }

                break;
            case R.id.del_btn:
                finish();
                break;

            case R.id.registered_btn:
                if (contype&&password) {
                    if (!heatType) {
                        showMsg(getResources().getString(R.string.YOURPROFILETHANKYOU));
                        break;
                    }


                    if (passwordEt.getText().toString().length() < 8) {
                        showMsg(getResources().getString(R.string.PASSWORDS820CHARACTERS));
                        break;
                    }
                    httpCode = 0;
                    showLoading();
                    UploadPersonal(headPath);
                }
                break;
        }
    }

    private void UploadPersonal(String picStr){
        showLoading();
        OkHttpUtils
                .post()
                .url(Constant.FILEINFO_UPLOADPERSONAL)
                .addHeader("Accept-Language", AppUtile.getLanguage())
                .addFile("files",AppUtile.getTime()+".png",new File(picStr))
               // .file(new File(picStr))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        if (AppUtile.getNetWork(e.getMessage())){
                            showMsg(getString(R.string.NETERROR));
                        }
                     dismissLoading();
                    }

                    @Override
                    public void onResponse(String s, int i) {
                       // System.out.println("s = " + s);
                        if (AppUtile.setCode(s,RegisteredActivity.this)) {
                            RegisterInputBase inputBase = JSON.parseObject(s, RegisterInputBase.class);
                            if (AppUtile.setCode(s, RegisteredActivity.this) && inputBase.getData().getFileRespResults() != null && inputBase.getData().getFileRespResults().size() > 0) {
                                registered(inputBase.getData().getFileRespResults().get(0).getUrl());
                            } else {
                                dismissLoading();
                            }
                        }else{
                            dismissLoading();
                        }
                       // inputBase.setUrl(uploadFlieBase.getData());
                        //appHttpUtile.postJson(Constant.USER_REGISTER,JSON.toJSONString(inputBase));
                    }
                });
    }
    private void registered(String headPath){
        OkHttpUtils
                .post()
                .url(Constant.USER_REGISTER)
                .addHeader("Accept-Language", AppUtile.getLanguage())
                .addParams("avatar", headPath)
                .addParams("email", emailEt.getText().toString())
                .addParams("password", passwordEt.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        dismissLoading();
                        if (AppUtile.getNetWork(e.getMessage())){
                            showMsg(getString(R.string.NETERROR));
                        }
                        AbDialogUtil.removeDialog(RegisteredActivity.this);
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        dismissLoading();
                    if (!TextUtils.isEmpty(s)) {
                        try {
                            LoginBase loginBase = JSON.parseObject(s, LoginBase.class);
                            if (AppUtile.setCode(s, RegisteredActivity.this)) {
                                YumApplication.getInstance().getMyInformation().setAvatar(loginBase.getData().getAvatar());
                                YumApplication.getInstance().getMyInformation().setBornTime(loginBase.getData().getBornTime());
                                YumApplication.getInstance().getMyInformation().setCurrentType(loginBase.getData().getCurrentType());
                                YumApplication.getInstance().getMyInformation().setEmail(loginBase.getData().getEmail());
                                YumApplication.getInstance().getMyInformation().setGender(loginBase.getData().getGender());
                                YumApplication.getInstance().getMyInformation().setToken(loginBase.getData().getToken());
                                YumApplication.getInstance().getMyInformation().setType(loginBase.getData().getType());
                                YumApplication.getInstance().setLoginNowType(true);
                                YumApplication.getInstance().getMyInformation().setUid(loginBase.getData().getId());
                                YumApplication.getInstance().getMyInformation().setUsername(loginBase.getData().getUsername());
                                YumApplication.getInstance().getMyInformation().setMerchantType(loginBase.getData().getMerchantType());

                                JPushInterface.setAlias(RegisteredActivity.this, 1001, loginBase.getData().getId());
                                JPushInterface.resumePush(getApplicationContext());
//                            if (getIntent().getBooleanExtra("test",false)){
//                                YumApplication.getInstance().setLoginType(true);
//                                if (getIntent().getStringExtra("TAG").equals(SearchNearbyActivity.TAG)){
//                                    Intent intent1 = new Intent(RegisteredActivity.this,AddToAddressActivity.class);
//                                    startActivityForResult(intent1,1);
//                                    overridePendingTransition(R.anim.activity_open,R.anim.in);
//                                }else if (getIntent().getStringExtra("TAG").equals(ReportActivity.TAG)){
//                                    Intent intent = new Intent(RegisteredActivity.this, ReportActivity.class);
//                                    intent.putExtra("userid", getIntent().getStringExtra("userid"));
//                                    startActivity(intent);
//                                    overridePendingTransition(R.anim.activity_open, R.anim.in);
//                                }else if (getIntent().getStringExtra("TAG").equals(SubmitOrdersActivity.TAG)){
//                                    Intent intent = new Intent(RegisteredActivity.this, SubmitOrdersActivity.class);
//                                    SearchNearbyDataBase data = (SearchNearbyDataBase)getIntent().getSerializableExtra("data");
//                                    if (data!=null&&AppUtile.GetDistance(Double.valueOf(data.getLat()),Double.valueOf(data.getLng()),getIntent().getDoubleExtra("lat",0),getIntent().getDoubleExtra("lng",0))/1609.344<=5) {
//                                        intent.putExtra("addressData", getIntent().getSerializableExtra("data"));
//                                    }
//                                    intent.putExtra("time",getIntent().getStringExtra("time"));
//                                    intent.putExtra("data", getIntent().getSerializableExtra("data"));
//                                    startActivityForResult(intent, 0);
//                                }
//                            }else{
//                                Intent intent = new Intent();
//                                intent.setClassName(getPackageName(), "com.yum.two_yum.view.client.ClientActivity");
//                                if (getPackageManager().resolveActivity(intent, 0) == null) {
//
//                                    // 说明系统中不存在这个activity
//                                    Intent intent1 = new Intent(RegisteredActivity.this, ClientActivity.class);
//                                    startActivity(intent1);
//                                }else{
//                                    setResult(3);
//                                    YumApplication.getInstance().setRegistered(true);
//                                }
//                                mHandler1.postDelayed(r, 1000);
                                setResult(RESULT_OK);
                                finish();
                                //}
                            } else {
                                showMsg(loginBase.getMsg());
                            }
                        }catch (JSONException e){
                            showMsg(getString(R.string.NETERROR));
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
            LoginActivity.instance.finish();
            finish();
        }
    };
    IHandlerCallBack iHandlerCallBack = new IHandlerCallBack() {
        @Override
        public void onStart() {
            Log.i(TAG, "onStart: 开启");
        }

        @Override
        public void onSuccess(List<String> photoList) {
            Log.i(TAG, "onSuccess: 返回数据");
            for (String s : photoList) {
                headPath = s;
                Bitmap bitmap = AppUtile.getSmallBitmap(headPath);
                hospitalHeadImg.setImageBitmap(bitmap);
                heatType = true;
                if (contype&&password){
                    registeredBtn.setBackgroundResource(R.drawable.button_red_bright);
                }else{
                    registeredBtn.setBackgroundResource(R.drawable.button_red);
                }
                promptTv.setVisibility(View.GONE);
            }
        }

        @Override
        public void onCancel() {
            Log.i(TAG, "onCancel: 取消");
        }

        @Override
        public void onFinish() {

        }

        @Override
        public void onError() {
            Log.i(TAG, "onError: 出错");
        }
    };

}
