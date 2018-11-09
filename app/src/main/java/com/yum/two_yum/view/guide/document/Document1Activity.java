package com.yum.two_yum.view.guide.document;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.squareup.picasso.Picasso;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;
import com.yum.two_yum.R;
import com.yum.two_yum.base.input.CertInput;
import com.yum.two_yum.base.input.RegisterInputBase;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.PicassoImageLoader;
import com.yum.two_yum.view.guide.GuideBaseActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * @author 余先德
 * @data 2018/4/14
 */

public class Document1Activity extends GuideBaseActivity {
    private static final String TAG = "Document1Activity";
    @Bind(R.id.save_btn)
    TextView saveBtn;
    @Bind(R.id.cert_iv)
    ImageView certIv;
    @Bind(R.id.check_button)
    RadioButton checkButton;


    private CertInput data;
    private boolean click = false;
    private boolean terms = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document1);
        ButterKnife.bind(this);
        data = (CertInput) getIntent().getSerializableExtra("data");
        setViewData();
    }

    private void setViewData() {
        if (data != null) {
            if (!TextUtils.isEmpty(data.getCertUrl())) {
                click = true;
                saveBtn.setBackgroundResource(R.drawable.button_red_bright);
                Picasso.get()
                        .load(data.getCertUrl())
                        .into(certIv);
            }
        }
        checkButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        terms = true;
                        if (click){
                            saveBtn.setBackgroundResource(R.drawable.button_red_bright);
                        }
                    }else{
                        saveBtn.setBackgroundResource(R.drawable.button_red);

                        terms = false;
                    }
            }
        });
    }

    @OnClick({R.id.del_btn, R.id.save_btn, R.id.cert_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cert_iv:
                GalleryConfig galleryConfig = new GalleryConfig.Builder()
                        .imageLoader(new PicassoImageLoader())    // ImageLoader 加载框架（必填）
                        .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                        .provider("com.yum.two_yum.fileprovider")   // provider (必填)
                        .multiSelect(false)                      // 是否多选   默认：false
                        .isShowCamera(false)                     // 是否现实相机按钮  默认：false
                        .filePath("/Gallery/Pictures")          // 图片存放路径
                        .build();
                GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(this);
                break;
            case R.id.del_btn:
                finish();
                break;
            case R.id.save_btn:
                if (click) {
                    if (!TextUtils.isEmpty(data.getCertUrl())) {
                        showLoading();
                        UploadPersonal(data.getCertUrl());
//                        Intent intent = new Intent(this, Document2Activity.class);
//                        intent.putExtra("data",data);
//                        startActivityForResult(intent,0);
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
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
                click = true;
                if (terms) {
                    saveBtn.setBackgroundResource(R.drawable.button_red_bright);
                }else{
                    saveBtn.setBackgroundResource(R.drawable.button_red);
                }

                data.setCertUrl(s);
                Bitmap bitmap = AppUtile.getSmallBitmap(s);
                certIv.setImageBitmap(bitmap);
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

    private void UploadPersonal(String picStr) {
        OkHttpUtils
                .post()
                .url(Constant.FILEINFO_UPLOADPERSONAL)
                .addFile("files", AppUtile.getTime() + ".png", new File(picStr))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        // System.out.println("e = " + e.getMessage());
                        showMsg(e.getMessage());
                        dismissLoading();
                        //AbDialogUtil.removeDialog(RegisteredActivity.this);
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        // System.out.println("s = " + s);
                        RegisterInputBase inputBase = JSON.parseObject(s, RegisterInputBase.class);
                        if (inputBase.getCode().equals(Constant.SUCCESSCON) && inputBase.getData().getFileRespResults() != null && inputBase.getData().getFileRespResults().size() > 0) {
                            data.setCertUrl(inputBase.getData().getFileRespResults().get(0).getUrl());
                            System.out.println(inputBase.getData().getFileRespResults().get(0).getUrl());
                            sevaData();
                            // registered(inputBase.getData().getFileRespResults().get(0).getUrl());
                        }
                        // inputBase.setUrl(uploadFlieBase.getData());
                        //appHttpUtile.postJson(Constant.USER_REGISTER,JSON.toJSONString(inputBase));
                    }
                });
    }

    private void sevaData() {
        // data.setCertNo(idEd.getText().toString());
        OkHttpUtils
                .postString()
                .url(Constant.GET_MERCHANT_SAVE_CERT)
                .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                .mediaType(MediaType.parse("application/json"))
                .content(JSON.toJSONString(data))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        dismissLoading();
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        dismissLoading();
                        Intent intent = new Intent(Document1Activity.this, Document2Activity.class);
                        intent.putExtra("data", data);
                        startActivityForResult(intent, 0);
                        //setResult(RESULT_OK);
                        //YumApplication.getInstance().removeGuideALLActivity_();
                    }
                });
    }
}
