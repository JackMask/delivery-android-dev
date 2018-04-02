package com.guxingdongli.yizhangguan.view.home.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.util.AbDialogUtil;
import com.alibaba.fastjson.JSON;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.YiZhangGuanApplication;
import com.guxingdongli.yizhangguan.model.UploadFileBase;
import com.guxingdongli.yizhangguan.util.AppUtile;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.util.PicassoImageLoader;
import com.guxingdongli.yizhangguan.view.home.HomeActivity;
import com.guxingdongli.yizhangguan.view.home.home_merchants.MyHospitalActivity;
import com.guxingdongli.yizhangguan.view.login.LoginActivity;
import com.guxingdongli.yizhangguan.view.login.RegisteredActivity;
import com.guxingdongli.yizhangguan.view.message.MessageDetailsActivity;
import com.guxingdongli.yizhangguan.view.myinfo.ModifyInformationActivity;
import com.guxingdongli.yizhangguan.view.myinfo.ModifyPasswordActivity;
import com.squareup.picasso.Picasso;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;
import com.yuxiaolong.yuxiandelibrary.ActionSheetDialog;
import com.yuxiaolong.yuxiandelibrary.CircleImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by jackmask on 2018/3/1.
 */

public class MyFragment extends Fragment {
    private final static String TAG = "MyFragment";

    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.supply_hospital_layout)
    LinearLayout supplyHospitalLayout;
    @Bind(R.id.my_head_img)
    CircleImageView myHeadImg;


    private HomeActivity abActivity;
    private View view;
    private TimeCount time;
    private String headPath;

    private final int CAMERA_STORAGES = 2;
    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(final Message msg) {
            switch (msg.what){
                case 0:
                    String pic = (String)msg.obj;
                    RequestBody formBody = new FormBody.Builder()
                            .add("base64String", AppUtile.GetImageStr(pic))
                            .add("uploadType", "头像上传")
                            .build();
                    HttpUtile httpUtile = new HttpUtile(getActivity(), Constant.DOMAIN_NAME + Constant.UPLOADFILE, formBody, new HttpUtileCallBack() {
                        @Override
                        public void getReturnStr(String returnStr) {
                            UploadFileBase data = JSON.parseObject(returnStr,UploadFileBase.class);
                            YiZhangGuanApplication.getInstance().getMyInfo().setHeadImg(data.getData().get(0).getFileUrl());
                            Message message = new Message();
                            message.what=1;
                            message.obj = data.getData().get(0).getFileUrl();
                            handler.sendMessage(message);
                        }

                        @Override
                        public void getReturnStrFailure(String returnStr) {

                        }

                        @Override
                        public void getErrorStr(String errorStr) {
                        }
                    },false);
                    break;
                case 1:

                    break;
            }
            return true;
        }
    });
    /**
     * 定时器类
     */
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            //verificationBtn.setClickable(false);

            //verificationBtn.setTextColor(Color.parseColor("#999999"));
            //verificationBtn.setBackgroundResource(R.drawable.area_stroke_white_gred_btn);
        }

        @Override
        public void onFinish() {
            Message message = new Message();
            message.what = 0;
            message.obj = headPath;
            handler.sendMessage(message);
            // verificationBtn.setClickable(true);
            // verificationBtn.setTextColor(Color.parseColor("#2177d5"));
            // verificationBtn.setBackgroundResource(R.drawable.area_stroke_white_btn);

        }
    }
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
                //((HomeActivity)getActivity()).setTestHeadImg(headPath);
                time.start();

               // ((HomeActivity)getActivity()).setHeadImg(headPath);
            }
        }

        @Override
        public void onCancel() {
            Log.i(TAG, "onCancel: 取消");
        }

        @Override
        public void onFinish() {
            ((HomeActivity)getActivity()).setTestHeadImg(headPath);
           // System.out.println("headPath = " +headPath);
            //AbDialogUtil.showProgressDialog(getActivity(),0,"上传中....");
            //time.start();
           /* Message message = new Message();
            message.what = 0 ;
            message.*/
            Log.i(TAG, "onFinish: 结束");
        }

        @Override
        public void onError() {
            Log.i(TAG, "onError: 出错");
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        abActivity = (HomeActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_my, container, false);

        ButterKnife.bind(this, view);
        setView();
        return view;
    }

    private void setView() {
        time = new TimeCount(10000, 1000);
        refreshUserInfo();
        returnBtn.setVisibility(View.GONE);
        titleText.setText("账号信息管理");
        if (!YiZhangGuanApplication.getInstance().isAppType()){
            supplyHospitalLayout.setVisibility(View.VISIBLE);
        }else{
            supplyHospitalLayout.setVisibility(View.GONE);
        }
    }
    public void setMyHeadImg(String path){
        Bitmap bitmap = AppUtile.getSmallBitmap(path);
        myHeadImg.setImageBitmap(bitmap);
        //Picasso.get().load(path).placeholder(R.mipmap.default_image).into(myHeadImg);
    }


    @OnClick({R.id.modify_head, R.id.modify_information, R.id.modify_password, R.id.quit_btn,R.id.supply_hospital})
    public void onViewClicked(View view) {
        final Intent intent;
        HttpUtile httpUtile;
        switch (view.getId()) {
            case R.id.supply_hospital://我供应医院
                intent = new Intent(abActivity, MyHospitalActivity.class);
                startActivity(intent);
                break;
            case R.id.modify_head://修改头像
                new ActionSheetDialog(abActivity)
                        .builder()
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem("相机", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        //调取系统相机
                                        if (AppUtile.dynamicAuthority(abActivity, Manifest.permission.CAMERA, CAMERA_STORAGES)) {
                                            GalleryConfig galleryConfig = new GalleryConfig.Builder()
                                                    .imageLoader(new PicassoImageLoader())    // ImageLoader 加载框架（必填）
                                                    .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                                                    .provider("com.guxingdongli.yizhangguan.fileprovider")   // provider (必填)
                                                    .filePath("/Gallery/Pictures")          // 图片存放路径   （选填）
                                                    .isOpenCamera(true)                  // 直接开启相机的标识位
                                                    .build();

                                            GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(getActivity());
                                        }

                                    }
                                })
                        .addSheetItem("相册", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        //AbToastUtil.showToast(InteractiveMessage.this,"清空所有消息");
                                        GalleryConfig galleryConfig = new GalleryConfig.Builder()
                                                .imageLoader(new PicassoImageLoader())    // ImageLoader 加载框架（必填）
                                                .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                                                .provider("com.guxingdongli.yizhangguan.fileprovider")   // provider (必填)
                                                .multiSelect(false)                      // 是否多选   默认：false
                                                .filePath("/Gallery/Pictures")          // 图片存放路径
                                                .build();
                                        GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(getActivity());
                                    }
                                })
                        .show();
                break;
            case R.id.modify_information://修改信息
                intent = new Intent(abActivity, ModifyInformationActivity.class);
                abActivity.startActivityForResult(intent, 0);
                break;
            case R.id.modify_password://修改密码
                intent = new Intent(abActivity, ModifyPasswordActivity.class);
                abActivity.startActivityForResult(intent, 0);
                break;
            case R.id.quit_btn://退出
                RequestBody body = new  FormBody.Builder().build();
                httpUtile = new HttpUtile(abActivity, Constant.DOMAIN_NAME + Constant.SIGNOUT, body, new HttpUtileCallBack() {
                    @Override
                    public void getReturnStr(String returnStr) {
                        Intent intent1 = new Intent(abActivity, LoginActivity.class);
                        startActivity(intent1);
                        YiZhangGuanApplication.getInstance().getMyInfo().setUid("");
                        abActivity.finish();
                    }
                    @Override
                    public void getReturnStrFailure(String returnStr) {
                        Intent intent1 = new Intent(abActivity, LoginActivity.class);
                        startActivity(intent1);
                        YiZhangGuanApplication.getInstance().getMyInfo().setUid("");
                        abActivity.finish();
                    }
                    @Override
                    public void getErrorStr(String errorStr) {
                        Intent intent1 = new Intent(abActivity, LoginActivity.class);
                        startActivity(intent1);
                        YiZhangGuanApplication.getInstance().getMyInfo().setUid("");
                        abActivity.finish();

                    }
                },false);
                break;
        }
    }
    public  void refreshUserInfo(){
        if (!TextUtils.isEmpty(YiZhangGuanApplication.getInstance().getMyInfo().getHeadImg()))
            Picasso.get().load(YiZhangGuanApplication.getInstance().getMyInfo().getHeadImg()).placeholder(R.drawable.ease_default_avatar).into(myHeadImg);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
