package com.yuxiaolong.yuxiandelibrary;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.alibaba.fastjson.JSON;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Created by jackmask on 2018/2/14.
 */

public class YuXianDeActivity extends AbActivity {
    /**
     * 简单的退出和跳转动画
     */
    private boolean animCon = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }


    protected void setAnimCon(boolean animCon) {
        this.animCon = animCon;
    }

    @Override
    public void finish() {
        if (animCon)
        overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
        super.finish();
    }

    @Override
    public void startActivity(Intent intent) {
        if (animCon)
        overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
        super.startActivity(intent);
    }
    public void setAllNum(int allNum){

    }
    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (animCon)
        overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
        super.startActivityForResult(intent, requestCode);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
    protected void getQr(String ScanResult){

    }

    /**
     * 删除图片的方法
     * @param position
     */
    public void delPic(int position){

    }

    public void selePic(){

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult != null) {
            if(intentResult.getContents() == null) {
                if (data!=null) {
                    String ScanResult = data.getStringExtra("result");
                    //selectActivity(ScanResult);
                    getQr(ScanResult);
                }else {
                }
            } else {
                // ScanResult 为 获取到的字符串
                String ScanResult = intentResult.getContents();
                //selectActivity(ScanResult);
                getQr(ScanResult);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == 300) {
                if (data != null) {
                    // ScanResult 为 获取到的字符串
                    String ScanResult = data.getStringExtra("result");
                    // selectActivity(ScanResult);
                    getQr(ScanResult);
                }
            }
        }
    }
}
