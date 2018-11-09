package com.yum.two_yum.view.my;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.utile.ActionSheetDialog;
import com.yum.two_yum.utile.AnimationUtil;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.view.WelcomeActivity;
import com.yum.two_yum.view.client.ClientActivity;
import com.yum.two_yum.view.login.LoginActivity;
import com.yum.two_yum.view.login.RegisteredActivity;
import com.yum.two_yum.view.merchants.MerchantsActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Call;

/**
 * @author 余先德
 * @data 2018/4/12
 */

public class SettingsActivity extends BaseActivity {


    @Bind(R.id.version_name)
    TextView versionName;
    @Bind(R.id.no_data_layout)
    View noDataLayout;
    @Bind(R.id.select_layout)
    LinearLayout selectLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        versionName.setText(getVerName(this));
    }

    @OnClick({R.id.del_btn, R.id.log_out_btn, R.id.service_btn,R.id.no_data_layout,R.id.cancel_btn,R.id.call_btn})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.call_btn:
                noDataLayout.setVisibility(View.GONE);
                selectLayout.setVisibility(View.GONE);
                selectLayout.setAnimation(AnimationUtil.fromNowToBottom());
                OkHttpUtils
                        .get()
                        .url(Constant.USER_LOGOUT)
                        .addHeader("Accept-Language", AppUtile.getLanguage())
                        .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int i) {
                                System.out.println(e.getMessage());
                                if (AppUtile.getNetWork(e.getMessage())){
                                    showMsg(getString(R.string.NETERROR));
                                }
                            }

                            @Override
                            public void onResponse(String s, int i) {
                                if (AppUtile.setCode(s,SettingsActivity.this)) {
                                   // mHandler1.postDelayed(r, 1000);

                                    YumApplication.getInstance().removeALLActivity_();
                                    YumApplication.getInstance().getMyInformation().setClientOk("1");
                                    //YumApplication.getInstance().getMyInformation().getCookiePrefs().edit().clear().commit();
                                    YumApplication.getInstance().getMyInformation().setUsername("");
                                    YumApplication.getInstance().getMyInformation().setUid("");
                                    YumApplication.getInstance().getMyInformation().setToken("");
                                    YumApplication.getInstance().getMyInformation().setGender("");
                                    YumApplication.getInstance().getMyInformation().setEmail("");
                                    YumApplication.getInstance().getMyInformation().setUid("");
                                    YumApplication.getInstance().getMyInformation().setAvatar("");
                                    YumApplication.getInstance().getAddressDB().setAddress("");
                                    YumApplication.getInstance().getAddressDB().setGender(-1);
                                    YumApplication.getInstance().getAddressDB().setId("");
                                    YumApplication.getInstance().getAddressDB().setLat("");
                                    YumApplication.getInstance().getAddressDB().setLng("");
                                    YumApplication.getInstance().getAddressDB().setName("");
                                    YumApplication.getInstance().getAddressDB().setNote("");
                                    YumApplication.getInstance().getAddressDB().setPhone("");
                                    YumApplication.getInstance().getAddressDB().setUserId("");
                                    YumApplication.getInstance().getpYourId().clear();
                                    YumApplication.getInstance().getpBankAccount().clear();
                                    YumApplication.getInstance().getpOperationsSettings().clear();
                                    if (!YumApplication.getInstance().isInputType()) {
                                        YumApplication.getInstance().setBackToHome(true);
                                    }
//                                    JPushInterface.stopPush(SettingsActivity.this);
//
//                                    JPushInterface.setAlias(SettingsActivity.this, "", new TagAliasCallback() {
//                                        @Override
//                                        public void gotResult(int i, String s, Set<String> set) {
//                                            //LogHelp.i("JPush","Logout");
//                                        }
//                                    });
                                    JPushInterface.deleteAlias(SettingsActivity.this,1001);
                                    //JPushInterface.setAlias(SettingsActivity.this, 0, null);
                                   // JPushInterface.stopPush(getApplicationContext());
//                                    Intent intent2 = new Intent(SettingsActivity.this, LoginActivity.class);
//                                    startActivity(intent2);
//                                    finish();
                                   // overridePendingTransition(R.anim.activity_open, R.anim.in);
                                    //Intent intent1 = new Intent(SettingsActivity.this, ClientActivity.class);
                                    Intent intent2 = new Intent(SettingsActivity.this, LoginActivity.class);
                                    intent2.putExtra("type",false);
                                    // startActivity(intent1);
                                    startActivity(intent2);
//                                    setResult(999);
//                                    finish();
//                                    overridePendingTransition(R.anim.activity_open, R.anim.in);
                                }

                            }
                        });
                break;
            case R.id.cancel_btn:
                noDataLayout.setVisibility(View.GONE);
                selectLayout.setVisibility(View.GONE);
                selectLayout.setAnimation(AnimationUtil.fromNowToBottom());
                break;
            case R.id.no_data_layout:
                noDataLayout.setVisibility(View.GONE);
                selectLayout.setVisibility(View.GONE);
                selectLayout.setAnimation(AnimationUtil.fromNowToBottom());
                break;
            case R.id.service_btn:
                intent = new Intent(this, PrivacyServiceTermsActivity.class);
                intent.putExtra("type",false);
                startActivity(intent);
                break;
            case R.id.del_btn:
                finish();
                break;
            case R.id.log_out_btn:
                noDataLayout.setVisibility(View.VISIBLE);
                selectLayout.setVisibility(View.VISIBLE);
                selectLayout.setAnimation(AnimationUtil.fromBottomToNow());
                break;
        }
    }
    Handler mHandler1 = new Handler();
    Runnable r = new Runnable() {

        @Override
        public void run() {
            //do something
            //每隔1s循环执行run方法
            if (YumApplication.getInstance().isInputType()) {
                ClientActivity.instance.backHome();
                ClientActivity.instance.nearbyRefresh();
            }else{
               // MerchantsActivity.instance.finish();

            }
            YumApplication.getInstance().setInputType(true);
            finish();
        }
    };
    /**
     * 获取版本号名称
     *
     * @param context 上下文
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
        return verName;
    }
}
