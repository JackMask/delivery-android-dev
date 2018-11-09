package com.yum.two_yum.view.guide.operations;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.input.MerchantInput;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.utile.pickerview.OptionsPickerView;
import com.yum.two_yum.view.guide.GuideBaseActivity;
import com.yum.two_yum.view.guide.SelectCountryActivity;
import com.yum.two_yum.view.merchants.menu.AddEditMenuActivity;
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

public class Operations16Activity extends GuideBaseActivity {

    @Bind(R.id.currency_tv)
    TextView currencyTv;
    @Bind(R.id.save_btn)
    TextView saveBtn;

    private MerchantInput data;
    private boolean click = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations16);
        ButterKnife.bind(this);
        data = (MerchantInput) getIntent().getSerializableExtra("data");
        setViewData();
    }
    private void setViewData() {
        if (data != null) {
            currencyTv.setText(data.getCurrencyId());
            if (data.getCurrencyId()!=-1) {
                click = true;
                saveBtn.setBackgroundResource(R.drawable.button_red_bright);
            }
        }

    }
    @OnClick({R.id.del_btn, R.id.save_out_btn, R.id.save_btn, R.id.currency_btn})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.del_btn:
                finish();
                break;
            case R.id.save_out_btn:
                showLoading();
                sevaData();
                break;
            case R.id.currency_btn:
                intent = new Intent(this,SelectCountryActivity.class);
                intent.putExtra("type",true);
                startActivityForResult(intent,0);
//                intent.putExtra("type",true);
//                startActivity(intent);
                break;
            case R.id.save_btn:
                if (click) {
                    showLoading();
                    sevaData();
                }
                break;

        }
    }
    private void sevaData() {
        // data.setStartTime(openEd.getText().toString());
        //  data.setEndTime(closeEd.getText().toString());
        //data.setCurrencyId(currencyTv.getText().toString());
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
                        setResult(RESULT_OK);
                        YumApplication.getInstance().removeGuideALLActivity_();
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0){
                if (intent!=null){
                    data.setCurrencyId(Integer.valueOf(!TextUtils.isEmpty(intent.getStringExtra("countryId"))?intent.getStringExtra("countryId"):"-1"));
                    currencyTv.setText(intent.getStringExtra("countryName"));
                    click = true;
                    saveBtn.setBackgroundResource(R.drawable.button_red_bright);
                }
            }
        }

    }

}
