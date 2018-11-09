package com.yum.two_yum.view.guide.document;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ab.util.AbDialogUtil;
import com.alibaba.fastjson.JSON;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.input.CertInput;
import com.yum.two_yum.base.input.RegisterInputBase;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.view.guide.GuideBaseActivity;
import com.yum.two_yum.view.login.RegisteredActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * @author 余先德
 * @data 2018/4/14
 */

public class Document2Activity extends GuideBaseActivity {

    @Bind(R.id.id_ed)
    EditText idEd;
    @Bind(R.id.hospital_button)
    RadioButton hospitalButton;
    @Bind(R.id.merchants_button)
    RadioButton merchantsButton;
    @Bind(R.id.select_id_btn)
    RadioGroup selectIdBtn;
    @Bind(R.id.save_btn)
    TextView saveBtn;

    private CertInput data;
    private boolean click = false;
    private boolean clickType = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document2);
        ButterKnife.bind(this);
        data = (CertInput)getIntent().getSerializableExtra("data");
        setViewData();
    }

    private void setViewData(){
        if (data!=null){
            idEd.setText(data.getCertNo()+"");
            if (!TextUtils.isEmpty(data.getCertNo())){
                click = true;
                saveBtn.setBackgroundResource(R.drawable.button_red_bright);
            }
//            if (data.getCertType() == 1){
//                hospitalButton.setChecked(true);
//            }
        }
        selectIdBtn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.hospital_button://SSN
                        //data.setCertType(1);
                        break;
                    case R.id.merchants_button://EIN
                        //data.setCertType(0);
                        break;

                }
            }
        });
        idEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length()==9){
                    click = true;
                    saveBtn.setBackgroundResource(R.drawable.button_red_bright);
                }else{
                    click = false;
                    saveBtn.setBackgroundResource(R.drawable.button_red);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.del_btn, R.id.save_btn,R.id.save_out_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.save_out_btn:
                clickType = true;
                showLoading();
                if (data.getCertUrl().substring(0,4).equals("http")){
                    sevaData();
                }else {
                    UploadPersonal(data.getCertUrl());
                }
                break;
            case R.id.del_btn:
                finish();
                break;
            case R.id.save_btn:
                if (click) {
                    clickType = false;
                    showLoading();
                    if (data.getCertUrl().substring(0,4).equals("http")){
                        sevaData();
                    }else {
                        UploadPersonal(data.getCertUrl());
                    }

                }
                break;
        }
    }
    private void UploadPersonal(String picStr){
        OkHttpUtils
                .post()
                .url(Constant.FILEINFO_UPLOADPERSONAL)
                .addFile("files", AppUtile.getTime()+".png",new File(picStr))
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
                        RegisterInputBase inputBase = JSON.parseObject(s,RegisterInputBase.class);
                        if (inputBase.getCode().equals(Constant.SUCCESSCON)&&inputBase.getData().getFileRespResults()!=null&&inputBase.getData().getFileRespResults().size()>0){
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
    private void sevaData(){
        data.setCertNo(idEd.getText().toString());
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
                        if (true){
                            setResult(RESULT_OK);
                            YumApplication.getInstance().removeGuideALLActivity_();
                        }else {
                            data.setCertNo(idEd.getText().toString());
                            Intent intent = new Intent(Document2Activity.this, Document3Activity.class);
                            intent.putExtra("data", data);
                            startActivityForResult(intent, 0);
                        }
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            setResult(RESULT_OK);
            finish();
        }
    }
}
