package com.yum.two_yum.view.guide.document;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.input.CertInput;
import com.yum.two_yum.base.input.RegisterInputBase;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.view.client.clientorder.SearchAddressActivity;
import com.yum.two_yum.view.guide.GuideBaseActivity;
import com.yum.two_yum.view.guide.SelectCountryActivity;
import com.yum.two_yum.view.guide.bank.Bank5Activity;
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

public class Document4Activity extends GuideBaseActivity {

    @Bind(R.id.country_ed)
    TextView countryEd;
    @Bind(R.id.country_btn)
    LinearLayout countryBtn;
    @Bind(R.id.street_ed)
    TextView streetEd;
    @Bind(R.id.apt_ed)
    EditText aptEd;
    @Bind(R.id.city_ed)
    EditText cityEd;
    @Bind(R.id.state_ed)
    EditText stateEd;
    @Bind(R.id.zip_ed)
    EditText zipEd;
    @Bind(R.id.save_btn)
    TextView saveBtn;

    private CertInput data;
    private boolean click = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document4);
        ButterKnife.bind(this);
        data = (CertInput)getIntent().getSerializableExtra("data");
        setViewData();
    }

    private void setViewData(){
        if (data!=null){
            countryEd.setText(data.getCountry());
            streetEd.setText(data.getStreet());
            aptEd.setText(data.getRoomNo());
            cityEd.setText(data.getCity());
            zipEd.setText(data.getZipCode());
            stateEd.setText(data.getProvince());
            if (!TextUtils.isEmpty(data.getCountry())&&!TextUtils.isEmpty(data.getStreet())&&!TextUtils.isEmpty(data.getCity())
                   && !TextUtils.isEmpty(data.getZipCode())&&!TextUtils.isEmpty(data.getProvince())){
                click = true;
                saveBtn.setBackgroundResource(R.drawable.button_red_bright);
            }
        }
       // aptEd.addTextChangedListener(new TextChanged());
        cityEd.addTextChangedListener(new TextChanged());
        zipEd.addTextChangedListener(new TextChanged());
        stateEd.addTextChangedListener(new TextChanged());

    }

    private class TextChanged implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            examinationData();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
    private boolean examinationData(){
        String country = countryEd.getText().toString();
        String street = streetEd.getText().toString();
        String city = cityEd.getText().toString();
        String zip = zipEd.getText().toString();
        String state = stateEd.getText().toString();
        if (!TextUtils.isEmpty(state)&&!TextUtils.isEmpty(zip)
                &&!TextUtils.isEmpty(city)
                &&!TextUtils.isEmpty(street)
                &&!TextUtils.isEmpty(country)){
            click = true;
            saveBtn.setBackgroundResource(R.drawable.button_red_bright);
            return true;
        }else{
            click = false;
            saveBtn.setBackgroundResource(R.drawable.button_red);
            return false;
        }

    }
    @OnClick({R.id.del_btn, R.id.save_out_btn, R.id.save_btn,R.id.country_btn,R.id.select_address_btn})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.select_address_btn:
                intent = new Intent(this, SearchAddressActivity.class);
                startActivityForResult(intent,1);
                break;
            case R.id.country_btn:
            intent = new Intent(this, SelectCountryActivity.class);
            startActivityForResult(intent,0);
            break;
            case R.id.del_btn:
                finish();
                break;
            case R.id.save_out_btn:
                showLoading();
                if (data.getCertUrl().substring(0,4).equals("http")){
                    sevaData();
                }else {
                    UploadPersonal(data.getCertUrl());
                }
                break;
            case R.id.save_btn:
                if (click) {
                    showLoading();
                    data.setStreet(streetEd.getText().toString());
                    data.setCity(cityEd.getText().toString());
                    data.setZipCode(zipEd.getText().toString());
                    data.setProvince(stateEd.getText().toString());
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
                            sevaData();
                            // registered(inputBase.getData().getFileRespResults().get(0).getUrl());
                        }
                        // inputBase.setUrl(uploadFlieBase.getData());
                        //appHttpUtile.postJson(Constant.USER_REGISTER,JSON.toJSONString(inputBase));
                    }
                });
    }
    private void sevaData(){
        data.setStreet(streetEd.getText().toString());
        data.setCity(cityEd.getText().toString());
        data.setZipCode(zipEd.getText().toString());
        data.setProvince(stateEd.getText().toString());
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
                        setResult(RESULT_OK);
                        YumApplication.getInstance().removeGuideALLActivity_();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK){
            if (requestCode == 0){
                if (intent!=null){
                    data.setCountryId(intent.getStringExtra("countryId"));
                    countryEd.setText(intent.getStringExtra("countryName"));
                }
            }else{
                if (intent!=null){
                    data.setStreet(intent.getStringExtra("address"));
                    streetEd.setText(intent.getStringExtra("address"));
                }
            }
            examinationData();
        }
    }
}
