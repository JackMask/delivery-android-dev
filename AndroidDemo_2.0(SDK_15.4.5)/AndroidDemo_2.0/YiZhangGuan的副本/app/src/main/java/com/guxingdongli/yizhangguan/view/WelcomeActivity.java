package com.guxingdongli.yizhangguan.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import com.ab.http.AbHttpUtil;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.MyService;
import com.guxingdongli.yizhangguan.controller.YiZhangGuanApplication;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.guxingdongli.yizhangguan.view.home.HomeActivity;
import com.guxingdongli.yizhangguan.view.login.LoginActivity;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jackmask on 2018/2/27.
 */

public class WelcomeActivity extends YiZhangGuanActivity {

    private static boolean isPermissionRequested = false;
    @Bind(R.id.storage_popup)
     View storagePopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        AbHttpUtil a = AbHttpUtil.getInstance(this);
        requestPermission();
        setAnimCon(true);
        Intent startIntent = new Intent(this, MyService.class);
        startService(startIntent);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                YiZhangGuanApplication.getInstance().setStorageHospitalPopupW(storagePopup.getWidth());
                YiZhangGuanApplication.getInstance().setStorageHospitalPopupH(storagePopup.getHeight());
                Intent i;
                if (!TextUtils.isEmpty(YiZhangGuanApplication.getInstance().getMyInfo().getUid())) {
                    //已经登陆，不是第一次启动
                    i = new Intent(WelcomeActivity.this,
                            HomeActivity.class);
                    YiZhangGuanApplication.getInstance().setEngineer(YiZhangGuanApplication.getInstance().getMyInfo().isEngineer());
                    YiZhangGuanApplication.getInstance().setAppType((YiZhangGuanApplication.getInstance().getMyInfo().getUserType().equals("1"))? true:false);
                } else {
                    //未登陆，不是第一次启动
                    i = new Intent(WelcomeActivity.this,
                            LoginActivity.class);
                }

                startActivity(i);
                finish();

            }
        }, 3000);
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23 && !isPermissionRequested) {

            isPermissionRequested = true;

            ArrayList<String> permissions = new ArrayList<>();
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }

            if (permissions.size() == 0) {
                return;
            } else {
                requestPermissions(permissions.toArray(new String[permissions.size()]), 0);
            }
        }
    }

}
