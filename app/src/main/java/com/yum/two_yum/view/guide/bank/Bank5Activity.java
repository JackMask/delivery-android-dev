package com.yum.two_yum.view.guide.bank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.input.BankInput;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.view.guide.GuideBaseActivity;
import com.yum.two_yum.view.guide.document.Document4Activity;
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

public class Bank5Activity extends GuideBaseActivity {

    @Bind(R.id.account_name_ed)
    EditText accountNameEd;
    @Bind(R.id.routing_num_ed)
    EditText routingNumEd;
    @Bind(R.id.account_ed)
    EditText accountEd;
    @Bind(R.id.check_button)
    RadioButton checkButton;
    @Bind(R.id.savings_button)
    RadioButton savingsButton;
    @Bind(R.id.account_type_btn)
    RadioGroup accountTypeBtn;
    @Bind(R.id.save_btn)
    TextView saveBtn;

    private BankInput data;
    private boolean click = false;
    private boolean clickType = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank5);
        ButterKnife.bind(this);
        data = (BankInput)getIntent().getSerializableExtra("data");
        setViewData();
    }

    private void setViewData(){
        if (data!=null){
            accountNameEd.setText(data.getName());
            routingNumEd.setText(data.getCode());
            accountEd.setText(data.getAccount());
            if (!TextUtils.isEmpty(data.getName())
                    &&!TextUtils.isEmpty(data.getCode())
            &&!TextUtils.isEmpty(data.getAccount())){
                click = true;
                saveBtn.setBackgroundResource(R.drawable.button_red_bright);
            }
        }
        accountTypeBtn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.check_button://支票账户
                        //data.setCertType(1);
                        break;
                    case R.id.savings_button://储蓄账户
                        //data.setCertType(0);
                        break;

                }
            }
        });
        accountNameEd.addTextChangedListener(new TextChanged());
        routingNumEd.addTextChangedListener(new TextChanged());
        accountEd.addTextChangedListener(new TextChanged());
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
       String accountName = accountNameEd.getText().toString();
       String routingNum = routingNumEd.getText().toString();
       String account = accountEd.getText().toString();
       if (!TextUtils.isEmpty(accountName)
               &&!TextUtils.isEmpty(routingNum)
               &&!TextUtils.isEmpty(account)){
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
//                    data.setName(accountNameEd.getText().toString());
//                    data.setCode(routingNumEd.getText().toString());
//                    data.setAccount(accountEd.getText().toString());
//                    Intent intent = new Intent(this, Bank6Activity.class);
//                    intent.putExtra("data",data);
//                    startActivityForResult(intent,0);
                }
                break;
        }
    }
    private void sevaData(){
        data.setName(accountNameEd.getText().toString());
        data.setCode(routingNumEd.getText().toString());
        data.setAccount(accountEd.getText().toString());
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
                        if (clickType){
                            setResult(RESULT_OK);
                            YumApplication.getInstance().removeGuideALLActivity_();
                        }else {
                            Intent intent = new Intent(Bank5Activity.this, Bank6Activity.class);
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
