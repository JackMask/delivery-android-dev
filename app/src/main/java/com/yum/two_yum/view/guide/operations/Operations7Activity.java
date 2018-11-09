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
import com.yum.two_yum.view.guide.GuideBaseActivity;
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

public class Operations7Activity extends GuideBaseActivity {

    @Bind(R.id.name_ed)
    EditText nameEd;
    @Bind(R.id.main_dish_ed)
    EditText mainDishEd;
    @Bind(R.id.save_btn)
    TextView saveBtn;


    private MerchantInput data;
    private boolean click = false;
    private boolean clickType = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations7);
        ButterKnife.bind(this);
        data = (MerchantInput)getIntent().getSerializableExtra("data");
        setViewData();
    }
    private void setViewData(){
        if (data!=null){
            nameEd.setText(data.getName());
            mainDishEd.setText(data.getSeries());
            if (!TextUtils.isEmpty(data.getName())&&!TextUtils.isEmpty(data.getSeries())){
                click = true;
                saveBtn.setBackgroundResource(R.drawable.button_red_bright);
            }
        }
        nameEd.addTextChangedListener(new TextChanged());
        mainDishEd.addTextChangedListener(new TextChanged());
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
        String name = nameEd.getText().toString();
        String mainDish = mainDishEd.getText().toString();
        if (!TextUtils.isEmpty(name)
                &&!TextUtils.isEmpty(mainDish)){
            click = true;
            saveBtn.setBackgroundResource(R.drawable.button_red_bright);
            return true;
        }else{
            click = false;
            saveBtn.setBackgroundResource(R.drawable.button_red);
            return false;
        }

    }

    @OnClick({R.id.del_btn, R.id.save_out_btn, R.id.save_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.del_btn:
                finish();
                break;
            case R.id.save_out_btn:
                clickType = true;
                showLoading();
                sevaData();
                break;
            case R.id.save_btn:
                if (click) {
                    clickType = false;
                    showLoading();
                    sevaData();
//                    data.setName(nameEd.getText().toString());
//                    data.setSeries(mainDishEd.getText().toString());
//                    Intent intent = new Intent(this, Operations8Activity.class);
//                    intent.putExtra("data",data);
//                    startActivityForResult(intent,0);
                }
                break;
        }
    }
    private void sevaData(){
        data.setName(nameEd.getText().toString());
        data.setSeries(mainDishEd.getText().toString());
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
                        if (!clickType){
                            Intent intent = new Intent(Operations7Activity.this, Operations8Activity.class);
                            intent.putExtra("data",data);
                            startActivityForResult(intent,0);
                        }else {
                            setResult(RESULT_OK);
                            YumApplication.getInstance().removeGuideALLActivity_();
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
