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
import com.yum.two_yum.view.guide.SelectTimeActivity;
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

public class Operations14Activity extends GuideBaseActivity {

    @Bind(R.id.time_tv)
    TextView timeTv;
    @Bind(R.id.save_btn)
    TextView saveBtn;
    private MerchantInput data;
    private boolean click = false;
    private boolean clickType = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations14);
        ButterKnife.bind(this);
        data = (MerchantInput) getIntent().getSerializableExtra("data");
        setViewData();
    }

    private void setViewData() {
        if (data != null) {
            timeTv.setText(data.getTime());
            if (!TextUtils.isEmpty(data.getTime())) {
                click = true;
                 saveBtn.setBackgroundResource(R.drawable.button_red_bright);
            }
        }
    }

    @OnClick({R.id.del_btn, R.id.save_out_btn, R.id.time_btn, R.id.save_btn})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.del_btn:
                finish();
                break;
            case R.id.save_out_btn:
                clickType = true;
                showLoading();
                sevaData();
                break;
            case R.id.time_btn:
                intent = new Intent(this, SelectTimeActivity.class);
                startActivityForResult(intent,1);
                break;
            case R.id.save_btn:
                if (click) {
                    clickType = false;
                    showLoading();
                    sevaData();
                  //  data.setTime(timeTv.getText().toString());

                }
                break;
        }
    }
    private void sevaData() {
        // data.setStartTime(openEd.getText().toString());
        //  data.setEndTime(closeEd.getText().toString());
        data.setTime(timeTv.getText().toString());
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
                            Intent intent = new Intent(Operations14Activity.this, Operations15Activity.class);
                            intent.putExtra("data",data);
                            startActivityForResult(intent,0);
                        }
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            if (requestCode ==1){
                if (intent!=null) {
                    timeTv.setText(intent.getStringExtra("data"));
                }
            }else {
                setResult(RESULT_OK);
                finish();
            }
        }

    }
}
