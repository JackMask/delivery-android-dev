package com.yum.two_yum.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;

import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.view.client.ClientActivity;
import com.yum.two_yum.view.login.LoginActivity;
import com.yum.two_yum.view.merchants.MerchantsActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author 余先德
 * @data 2018/4/3
 */

public class WelcomeActivity extends BaseActivity {

    @Bind(R.id.view_116)
    View view106;
    @Bind(R.id.popup)
    View popup;
    @Bind(R.id.dishes_title)
    View dishesTitle;
    @Bind(R.id.dishes_item)
    View dishesItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                YumApplication.getInstance().setView106dp(view106.getHeight());
                YumApplication.getInstance().setView127dp(popup.getWidth());
                YumApplication.getInstance().setViewh106dp(popup.getHeight());
                YumApplication.getInstance().setDishesTitle(dishesTitle.getHeight());
                YumApplication.getInstance().setDishesItem(dishesItem.getHeight());
               /*  if (!TextUtils.isEmpty(YiZhangGuanApplication.getInstance().getMyInfo().getUid())) {
                    //已经登陆，不是第一次启动
                    i = new Intent(WelcomeActivity.this,
                            HomeActivity.class);
                    YiZhangGuanApplication.getInstance().setEngineer(YiZhangGuanApplication.getInstance().getMyInfo().isEngineer());
                    YiZhangGuanApplication.getInstance().setAppType((YiZhangGuanApplication.getInstance().getMyInfo().getUserType().equals("1"))? true:false);
                } else {*/
                //未登陆，不是第一次启动
                if (YumApplication.getInstance().isInputType()) {
                    intent = new Intent(WelcomeActivity.this,
                            ClientActivity.class);
                }else{
                    intent = new Intent(WelcomeActivity.this,
                            MerchantsActivity.class);
                }
                // }

                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();

            }
        }, 3000);
        //requestPermission();
    }

//    private void requestPermission() {
//        if (Build.VERSION.SDK_INT >= 23 && !isPermissionRequested) {
//
//            isPermissionRequested = true;
//
//            ArrayList<String> permissions = new ArrayList<>();
//            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                    || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                    || checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
//                    || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
//                    || checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
//                    ) {
//                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
//                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
//                permissions.add(Manifest.permission.READ_PHONE_STATE);
//                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//                permissions.add(Manifest.permission.CALL_PHONE);
//            }
//
//            if (permissions.size() == 0) {
//                return;
//            } else {
//                requestPermissions(permissions.toArray(new String[permissions.size()]), 0);
//            }
//        }
//    }
}
