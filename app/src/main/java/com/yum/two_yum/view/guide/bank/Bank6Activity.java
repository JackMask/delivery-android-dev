package com.yum.two_yum.view.guide.bank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.input.BankInput;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.view.client.clientorder.SearchAddressActivity;
import com.yum.two_yum.view.guide.GuideBaseActivity;
import com.yum.two_yum.view.guide.SelectCountryActivity;
import com.yum.two_yum.view.guide.operations.Operations7Activity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * @author 余先德
 * @data 2018/4/14
 */

public class Bank6Activity extends GuideBaseActivity {

    @Bind(R.id.country_tv)
    TextView countryTv;
    @Bind(R.id.country_btn)
    LinearLayout countryBtn;
    @Bind(R.id.street_ed)
    TextView streetEd;
    @Bind(R.id.apt_ed)
    EditText aptEd;
    @Bind(R.id.city_ed)
    EditText cityEd;
    @Bind(R.id.province_ed)
    EditText provinceEd;
    @Bind(R.id.zip_ed)
    EditText zipEd;
    @Bind(R.id.save_btn)
    TextView saveBtn;
    private BankInput data;
    private boolean click = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank6);
        ButterKnife.bind(this);
        data = (BankInput)getIntent().getSerializableExtra("data");
        setViewData();
    }

    private void setViewData(){
        if (data!=null){
            cityEd.setText(data.getCity());
            provinceEd.setText(data.getProvince());
            zipEd.setText(data.getZipCode());
            countryTv.setText(data.getCountry());
            aptEd.setText(data.getRoomNo());
            streetEd.setText(data.getStreet());
        }

        cityEd.addTextChangedListener(new TextChanged());
        provinceEd.addTextChangedListener(new TextChanged());
        zipEd.addTextChangedListener(new TextChanged());
    }

    private class TextChanged implements TextWatcher {

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
        String accountName = cityEd.getText().toString();
        String routingNum = provinceEd.getText().toString();
        String account = zipEd.getText().toString();
        String country = countryTv.getText().toString();
        String street = streetEd.getText().toString();
        if (!TextUtils.isEmpty(accountName)
                &&!TextUtils.isEmpty(routingNum)
                &&!TextUtils.isEmpty(account)
                &&!TextUtils.isEmpty(country)
                &&!TextUtils.isEmpty(street)){
            click = true;
            saveBtn.setBackgroundResource(R.drawable.button_red_bright);
            return true;
        }else{
            click = false;
            saveBtn.setBackgroundResource(R.drawable.button_red);
            return false;
        }

    }


    @OnClick({R.id.del_btn, R.id.save_out_btn, R.id.country_btn, R.id.save_btn,R.id.select_address_btn})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.select_address_btn:
                intent = new Intent(this, SearchAddressActivity.class);
                startActivityForResult(intent,1);
                break;

            case R.id.del_btn:
                finish();
                break;
            case R.id.save_out_btn:
                showLoading();
                sevaData();
                break;
            case R.id.country_btn:
                intent = new Intent(this, SelectCountryActivity.class);
                startActivityForResult(intent,0);
                break;
            case R.id.save_btn:
                showLoading();
                sevaData();
                break;
        }
    }
    private void sevaData(){
        data.setCity(cityEd.getText().toString());
        data.setProvince(provinceEd.getText().toString());
        data.setZipCode(zipEd.getText().toString());
        data.setRoomNo(aptEd.getText().toString());
        OkHttpUtils
                .postString()
                .url(Constant.GET_MERCHANT_SAVE_BANK)
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
                    data.setCountryId(intent.getStringExtra("countryId")+"");
                    countryTv.setText(intent.getStringExtra("countryName"));
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
