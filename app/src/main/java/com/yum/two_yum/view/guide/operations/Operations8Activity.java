package com.yum.two_yum.view.guide.operations;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.input.MerchantInput;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.view.client.clientorder.SearchAddressActivity;
import com.yum.two_yum.view.guide.GuideBaseActivity;
import com.yum.two_yum.view.guide.SelectCountryActivity;
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

public class Operations8Activity extends GuideBaseActivity {

    @Bind(R.id.county_ed)
    TextView countyEd;
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

    private MerchantInput data;
    private boolean click = false;
    private boolean clickType = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations8);
        ButterKnife.bind(this);
        data = (MerchantInput)getIntent().getSerializableExtra("data");
        setViewData();
    }
    private void setViewData(){
        if (data!=null){
            countyEd.setText(data.getCountry());
            streetEd.setText(data.getStreet());
            aptEd.setText(data.getRoomNo());
            cityEd.setText(data.getCity());
            provinceEd.setText(data.getProvince());
            zipEd.setText(data.getZipCode());

            if (!TextUtils.isEmpty(data.getCountry())
                    &&!TextUtils.isEmpty(data.getStreet())
                    &&!TextUtils.isEmpty(data.getCity())
                    &&!TextUtils.isEmpty(data.getProvince())
                    &&!TextUtils.isEmpty(data.getZipCode())){
                click = true;
                saveBtn.setBackgroundResource(R.drawable.button_red_bright);
            }
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
        String county = countyEd.getText().toString();
        String street = streetEd.getText().toString();
        String city = cityEd.getText().toString();
        String province = provinceEd.getText().toString();
        String zip = zipEd.getText().toString();
        if (!TextUtils.isEmpty(county)
                &&!TextUtils.isEmpty(street)
                &&!TextUtils.isEmpty(city)
                &&!TextUtils.isEmpty(province)
                &&!TextUtils.isEmpty(zip)){
            click = true;
            saveBtn.setBackgroundResource(R.drawable.button_red_bright);
            return true;
        }else{
            click = false;
            saveBtn.setBackgroundResource(R.drawable.button_red);
            return false;
        }

    }

    @OnClick({R.id.del_btn, R.id.save_out_btn, R.id.county_btn, R.id.save_btn,R.id.street_btn})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.street_btn:
                intent = new Intent(this, SearchAddressActivity.class);
                startActivityForResult(intent,2);
                break;
            case R.id.del_btn:
                finish();
                break;
            case R.id.save_out_btn:
                clickType = true;
                showLoading();
                sevaData();
                break;
            case R.id.county_btn:
                intent = new Intent(this, SelectCountryActivity.class);
                startActivityForResult(intent,1);
                break;
            case R.id.save_btn:
                if (click) {
                    clickType = false;
                    showLoading();
                    sevaData();
//                   data.setCity(cityEd.getText().toString());
//                   data.setProvince(provinceEd.getText().toString());
//                   data.setZipCode(zipEd.getText().toString());
//                   data.setRoomNo(aptEd.getText().toString());
//
                }
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
                .url(Constant.GET_MERCHANT_SAVE)
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
                        if (clickType) {
                            setResult(RESULT_OK);
                            YumApplication.getInstance().removeGuideALLActivity_();
                        }else{
                             Intent intent = new Intent(Operations8Activity.this, Operations9Activity.class);
                            intent.putExtra("data",data);
                            startActivityForResult(intent,0);
                        }
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK){
            if (requestCode == 1){
                if (intent!=null){
                    data.setCountryId(intent.getStringExtra("countryId")+"");
                    countyEd.setText(intent.getStringExtra("countryName"));
                }
                examinationData();
            }else if (requestCode == 2){
                if (intent!=null){
                    data.setStreet(intent.getStringExtra("address").replace("\n",""));
                    data.setLat(intent.getStringExtra("lat"));
                    data.setLng(intent.getStringExtra("lng"));
                    streetEd.setText(intent.getStringExtra("address"));
                }
                examinationData();
            }else{
                setResult(RESULT_OK);
                finish();
            }


        }
    }
}
