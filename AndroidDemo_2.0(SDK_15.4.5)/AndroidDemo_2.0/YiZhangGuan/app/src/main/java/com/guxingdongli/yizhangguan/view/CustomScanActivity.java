package com.guxingdongli.yizhangguan.view;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.YiZhangGuanApplication;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jackmask on 2018/3/3.
 */

public class CustomScanActivity extends YiZhangGuanActivity implements DecoratedBarcodeView.TorchListener {

    @Bind(R.id.dbv_custom)
    DecoratedBarcodeView mDBV;
    @Bind(R.id.btn_switch)
    Button swichLight;
    @Bind(R.id.btn_hint1)
    Button btnHint1;
    @Bind(R.id.btn_hint2)
    Button btnHint2;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.title_layout)
    LinearLayout titleLayout;
    private CaptureManager captureManager;
    private boolean isLightOn = false;

    private int customScanType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_scan);
        ButterKnife.bind(this);
        setView(savedInstanceState);
        customScanType = YiZhangGuanApplication.getInstance().getCustomScan();
    }

    private void setView(Bundle savedInstanceState) {
        titleText.setText("扫一扫");
        titleLayout.setBackgroundColor(Color.parseColor("#000000"));
        titleText.setTextColor(Color.parseColor("#333333"));
        mDBV.setTorchListener(this);

        // 如果没有闪光灯功能，就去掉相关按钮
        if (!hasFlash()) {
            swichLight.setVisibility(View.GONE);
        }

        //重要代码，初始化捕获
        captureManager = new CaptureManager(this, mDBV);
        captureManager.initializeFromIntent(getIntent(), savedInstanceState);
        captureManager.decode();
    }

    // 判断是否有闪光灯功能
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    @Override
    public void onTorchOn() {

    }

    @Override
    public void onTorchOff() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        captureManager.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mDBV.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.return_btn})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.return_btn:
                finish();
                break;

        }
    }
}
