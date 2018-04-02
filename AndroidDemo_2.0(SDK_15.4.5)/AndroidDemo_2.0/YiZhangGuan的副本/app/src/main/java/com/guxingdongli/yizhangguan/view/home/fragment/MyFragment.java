package com.guxingdongli.yizhangguan.view.home.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dmcbig.mediapicker.PickerActivity;
import com.dmcbig.mediapicker.PickerConfig;
import com.dmcbig.mediapicker.entity.Media;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.YiZhangGuanApplication;
import com.guxingdongli.yizhangguan.util.AppUtile;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.view.home.HomeActivity;
import com.guxingdongli.yizhangguan.view.home.home_merchants.MyHospitalActivity;
import com.guxingdongli.yizhangguan.view.login.LoginActivity;
import com.guxingdongli.yizhangguan.view.myinfo.ModifyInformationActivity;
import com.guxingdongli.yizhangguan.view.myinfo.ModifyPasswordActivity;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TakePhotoOptions;
import com.squareup.picasso.Picasso;
import com.yuxiaolong.yuxiandelibrary.ActionSheetDialog;
import com.yuxiaolong.yuxiandelibrary.CircleImageView;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by jackmask on 2018/3/1.
 */

public class MyFragment extends Fragment {

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
    private TakePhoto takePhoto;
    ArrayList<Media> select;
    private final int CAMERA_STORAGES = 2;
    private InvokeParam invokeParam;

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
        Picasso.get().load(path).into(myHeadImg);
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
                                            File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
                                            if (!file.getParentFile().exists())
                                                file.getParentFile().mkdirs();
                                            Uri imageUri = Uri.fromFile(file);
                                            abActivity.getTakePhoto().onEnableCompress(null, false);
                                            TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
                                            builder.setWithOwnGallery(true);
                                            builder.setCorrectImage(true);
                                            abActivity.getTakePhoto().setTakePhotoOptions(builder.create());
                                            CompressConfig config = new CompressConfig.Builder()
                                                    .setMaxSize(102400)
                                                    .setMaxPixel(800)
                                                    .enableReserveRaw(false)
                                                    .create();
                                            abActivity.getTakePhoto().onEnableCompress(config, false);
                                            abActivity.getTakePhoto().onPickFromCapture(imageUri);
                                        }

                                    }
                                })
                        .addSheetItem("相册", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        //AbToastUtil.showToast(InteractiveMessage.this,"清空所有消息");
                                        if (select != null) {
                                            select.clear();
                                        }
                                        Intent intent = new Intent(abActivity, PickerActivity.class);
                                        intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_IMAGE);//default image and video (Optional)
                                        long maxSize = 188743680L;//long long long
                                        intent.putExtra(PickerConfig.MAX_SELECT_SIZE, maxSize); //default 180MB (Optional)
                                        intent.putExtra(PickerConfig.MAX_SELECT_COUNT, 1);  //default 40 (Optional)
                                        intent.putExtra(PickerConfig.DEFAULT_SELECTED_LIST, select); // (Optional)
                                        startActivityForResult(intent, 321);
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
