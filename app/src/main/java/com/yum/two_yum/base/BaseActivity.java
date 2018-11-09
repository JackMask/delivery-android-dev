package com.yum.two_yum.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.yum.two_yum.R;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.utile.AppHttpUtile;
import com.yum.two_yum.utile.AppHttpUtileCallBack;
import com.yum.two_yum.utile.LoadingDialog;
import com.yum.two_yum.utile.Utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author 余先德
 * @data 2018/4/3
 */

public class BaseActivity extends FragmentActivity  {
    private LoadingDialog loading;
    private LoadingDailog dialog;
    public static int navigationHeight = 0;
    private Handler handler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub

            Toast toast = Toast.makeText(getApplicationContext(),
                    (String) msg.obj, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }

    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        YumApplication.getInstance().addActivity_(this);

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Utils.setStatusBar(this, false, false);
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(this)
                .setShowMessage(false)

                .setCancelable(false);
         dialog=loadBuilder.create();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){
            if (resultCode == 3){
                //setLoginResult();
            }
        }
    }

    public void showLoading(){
        dialog.show();
    }
    public void dismissLoading(){
        if (dialog != null){
            dialog.dismiss();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        //注：回调 1
    }

    @Override
    protected void onPause() {
        super.onPause();
        //注：回调 2
    }


    protected void setView(){

    }

    /**
     * 获取当前手机的系统语言
     * @return
     */
    public String getLanguage(){
            String language = Locale.getDefault().getLanguage();
            String country = Locale.getDefault().getCountry();
            if (language.equals("zh")||language.equals("zh-cn")){
                if (country.equals("TW")||country.equals("HK")){
                    return "zh-hk";
                }else {
                    return "zh-cn";
                }
            }else if (language.equals("es")){
                return "esp";
            }else{
                return language;
            }

    }

    public void showMsg(String msg){
        Message message = new Message();
        message.what = 0;
        message.obj = msg;
        handler.sendMessage(message);
       // handler.sendEmptyMessage(0);

    }
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            finish();
            return true;//不执行父类点击事件
        }
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        System.out.println("切换语言");
    }
    public void showEmail(){

    }

    private interface popupClick{
        void click(int num);
    }

    private popupClick popupClick;

    public BaseActivity.popupClick getPopupClick() {
        return popupClick;
    }

    public void setPopupClick(BaseActivity.popupClick popupClick) {
        this.popupClick = popupClick;
    }

    private void showPopwindow(int num) {
        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        View popView = View.inflate(this, R.layout.pop_menu_layout, null);

        List<LinearLayout> btns = new ArrayList<>(6);
        LinearLayout btn_layout_1 = (LinearLayout) popView.findViewById(R.id.btn_layout_1);
        LinearLayout btn_layout_2 = (LinearLayout) popView.findViewById(R.id.btn_layout_2);
        LinearLayout btn_layout_3 = (LinearLayout) popView.findViewById(R.id.btn_layout_3);
        LinearLayout btn_layout_4 = (LinearLayout) popView.findViewById(R.id.btn_layout_4);
        LinearLayout btn_layout_5 = (LinearLayout) popView.findViewById(R.id.btn_layout_5);
        LinearLayout btn_layout_6 = (LinearLayout) popView.findViewById(R.id.btn_layout_6);
        btns.add(btn_layout_1);
        btns.add(btn_layout_2);
        btns.add(btn_layout_3);
        btns.add(btn_layout_4);
        btns.add(btn_layout_5);
        btns.add(btn_layout_6);
        TextView btn_1 = (TextView) popView.findViewById(R.id.btn_1);
        TextView btn_2 = (TextView) popView.findViewById(R.id.btn_2);
        TextView btn_3 = (TextView) popView.findViewById(R.id.btn_3);
        TextView btn_4 = (TextView) popView.findViewById(R.id.btn_4);
        TextView btn_5 = (TextView) popView.findViewById(R.id.btn_5);
        TextView btn_6 = (TextView) popView.findViewById(R.id.btn_6);

        for (int i = 0 ; i < num;i++){
            btns.get(i).setVisibility(View.VISIBLE);
        }

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        final PopupWindow popWindow = new PopupWindow(popView,width,height);
        popWindow.setAnimationStyle(R.style.anim_menu_bottombar);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(false);// 设置同意在外点击消失

        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_1:
                        if (popupClick!=null){
                            popupClick.click(0);
                        }

                        break;
                    case R.id.btn_2:
                        if (popupClick!=null){
                            popupClick.click(1);
                        }
                        break;
                    case R.id.btn_3:
                        if (popupClick!=null){
                            popupClick.click(2);
                        }
                        break;
                    case R.id.btn_4:
                        if (popupClick!=null){
                            popupClick.click(3);
                        }
                        break;
                    case R.id.btn_5:
                        if (popupClick!=null){
                            popupClick.click(4);
                        }
                        break;
                    case R.id.btn_6:
                        if (popupClick!=null){
                            popupClick.click(5);
                        }
                        break;
                }
                popWindow.dismiss();
            }
        };

        btn_1.setOnClickListener(listener);
        btn_2.setOnClickListener(listener);
        btn_3.setOnClickListener(listener);
        btn_4.setOnClickListener(listener);
        btn_5.setOnClickListener(listener);
        btn_6.setOnClickListener(listener);

        ColorDrawable dw = new ColorDrawable(0x30000000);
        popWindow.setBackgroundDrawable(dw);
        popWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }
}
