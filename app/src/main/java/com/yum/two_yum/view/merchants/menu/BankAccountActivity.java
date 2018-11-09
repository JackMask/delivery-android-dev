package com.yum.two_yum.view.merchants.menu;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.BaseReturn;
import com.yum.two_yum.base.input.BankInput;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.AsteriskPasswordTransformationMethod;
import com.yum.two_yum.utile.ContainsEmojiEditText;
import com.yum.two_yum.view.dialog.PromptDialog;
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
 * @data 2018/4/13
 */

public class BankAccountActivity extends BaseActivity {

    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.account_name_ed)
    ContainsEmojiEditText accountNameEd;
    @Bind(R.id.routing_num_ed)
    ContainsEmojiEditText routingNumEd;
    @Bind(R.id.account_ed)
    ContainsEmojiEditText accountEd;
    @Bind(R.id.check_button)
    RadioButton checkButton;
    @Bind(R.id.savings_button)
    RadioButton savingsButton;
    @Bind(R.id.account_type_btn)
    RadioGroup accountTypeBtn;
    @Bind(R.id.country_tv)
    TextView countryTv;
    @Bind(R.id.street_ed)
    ContainsEmojiEditText streetEd;
    @Bind(R.id.apt_ed)
    ContainsEmojiEditText aptEd;
    @Bind(R.id.city_ed)
    ContainsEmojiEditText cityEd;
    @Bind(R.id.province_ed)
    ContainsEmojiEditText provinceEd;
    @Bind(R.id.zip_ed)
    ContainsEmojiEditText zipEd;
    @Bind(R.id.save_btn)
    TextView saveBtn;

    private boolean clickType = false;
    private boolean changeType = false;

    private BankInput data,noPutData;
    private String accountNumberStr = "";
    private int type = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_account);
        ButterKnife.bind(this);
        data = (BankInput)getIntent().getSerializableExtra("data");
        setNoPutData();
        setView();
    }
    private void setNoPutData(){
        noPutData = new BankInput();
        noPutData.setStreet(data.getStreet());
        noPutData.setType(data.getType());
        noPutData.setAccount(data.getAccount());
        noPutData.setCode(data.getCode());
        noPutData.setName(data.getName());
        noPutData.setCountry(data.getCountry());
        noPutData.setZipCode(data.getZipCode());
        noPutData.setUserId(data.getUserId());
        noPutData.setRoomNo(data.getRoomNo());
        noPutData.setProvince(data.getProvince());
        noPutData.setCity(data.getCity());
        noPutData.setCountryId(data.getCountryId());
    }

    private void setChangeType(){
        if (!(!TextUtils.isEmpty(noPutData.getAccount())?noPutData.getAccount():"").equals(!TextUtils.isEmpty(accountEd.getText().toString())?accountEd.getText().toString():"")
                ||!(!TextUtils.isEmpty(noPutData.getCity())?noPutData.getCity():"").equals(!TextUtils.isEmpty(cityEd.getText().toString())?cityEd.getText().toString():"")
                ||!(!TextUtils.isEmpty(noPutData.getCode())?noPutData.getCode():"").equals(!TextUtils.isEmpty(routingNumEd.getText().toString())?routingNumEd.getText().toString():"")
                ||!(!TextUtils.isEmpty(noPutData.getCountryId())?noPutData.getCountryId():"").equals(!TextUtils.isEmpty(data.getCountryId())?data.getCountryId():"")
                ||!(!TextUtils.isEmpty(noPutData.getName())?noPutData.getName():"").equals(!TextUtils.isEmpty(accountNameEd.getText().toString())?accountNameEd.getText().toString():"")
                ||!(!TextUtils.isEmpty(noPutData.getProvince())?noPutData.getProvince():"").equals(!TextUtils.isEmpty(provinceEd.getText().toString())?provinceEd.getText().toString():"")
                ||!(!TextUtils.isEmpty(noPutData.getRoomNo())?noPutData.getRoomNo():"").equals(!TextUtils.isEmpty(aptEd.getText().toString())?aptEd.getText().toString():"")
                ||!(!TextUtils.isEmpty(noPutData.getStreet())?noPutData.getStreet():"").equals(!TextUtils.isEmpty(streetEd.getText().toString())?streetEd.getText().toString():"")
                ||!(!TextUtils.isEmpty(noPutData.getZipCode())?noPutData.getZipCode():"").equals(!TextUtils.isEmpty(zipEd.getText().toString())?zipEd.getText().toString():"")
                ||noPutData.getType()!=data.getType()){
                   changeType = true;
        }
    }

    @Override
    protected void setView() {
        super.setView();
        if (data!=null){
            accountNameEd.setText(data.getName());
            routingNumEd.setText(data.getCode());
            accountEd.setText(data.getAccount());
            cityEd.setText(data.getCity());
            provinceEd.setText(data.getProvince());
            zipEd.setText(data.getZipCode());
            countryTv.setText(data.getCountry());
            aptEd.setText(data.getRoomNo());
            accountNumberStr = data.getAccount();
            streetEd.setText(data.getStreet());
            if (data.getType()!=0){
                if (data.getType() == 1){
                    checkButton.setChecked(true);
                    type = 1;
                }else if (data.getType() == 2){
                    savingsButton.setChecked(true);
                    type =2;
                }
            }
            if (!TextUtils.isEmpty(data.getName())
                    &&!TextUtils.isEmpty(data.getCode())
                    &&!TextUtils.isEmpty(data.getAccount())
                    &&!TextUtils.isEmpty(data.getProvince())
                    &&!TextUtils.isEmpty(data.getZipCode())
                    &&!TextUtils.isEmpty(data.getStreet())
                    &&!TextUtils.isEmpty(data.getCountryId())
                    &&(type==1||type == 2)){
                clickType = true;
                saveBtn.setBackgroundResource(R.drawable.button_red_bright);
            }
        }
        accountTypeBtn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setChangeType();
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.check_button://支票
                        data.setType(1);
                        YumApplication.getInstance().getpBankAccount().setType(1);
                        type = 1;
                        break;
                    case R.id.savings_button://储蓄
                        data.setType(2);
                        YumApplication.getInstance().getpBankAccount().setType(2);
                        type = 2;
                        break;

                }
                Testing();
            }
        });
        accountNameEd.addTextChangedListener(new textChange());
        routingNumEd.addTextChangedListener(new textChange());
        //accountNameEd.setTransformationMethod(new AsteriskPasswordTransformationMethod());
       // routingNumEd.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        //accountEd.addTextChangedListener(new textChange());
        aptEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setChangeType();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//        accountEd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            @Override
//
//            public void onFocusChange(View v, boolean hasFocus) {
//
//                if(hasFocus) {
//                    accountEd.setText(accountNumberStr);
//                } else {
//                    if (!TextUtils.isEmpty(accountNumberStr)) {
//                        if (accountNumberStr.length() > 4) {
//                            String headStr = "";
//                            for (int i = 0; i < accountNumberStr.length() - 4; i++) {
//                                headStr += "*";
//                            }
//                            accountEd.setText(headStr + accountNumberStr.toString().substring(accountNumberStr.length() - 4, accountNumberStr.length()));
//                        }
//                    }
//                }
//
//            }
//
//        });
        accountEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//               if (!s.toString().contains("*")) {
                    accountNumberStr = s.toString();
//                }
                Testing();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        streetEd.addTextChangedListener(new textChange());
        cityEd.addTextChangedListener(new textChange());
        provinceEd.addTextChangedListener(new textChange());
        zipEd.addTextChangedListener(new textChange());
    }



    private void Testing() {
        String idEdStr = accountNameEd.getText().toString();
        String nameEdStr = routingNumEd.getText().toString();
        String phoneEdStr = accountNumberStr;
        String emailEdStr = provinceEd.getText().toString();
        String streetEdStr = streetEd.getText().toString();
        String cityEdStr = cityEd.getText().toString();
        String zipEdStr = zipEd.getText().toString();
        if (type==1||type == 2) {
            if (!TextUtils.isEmpty(idEdStr)) {
                if (!TextUtils.isEmpty(nameEdStr)) {
                    if (!TextUtils.isEmpty(phoneEdStr)) {
                        if (!TextUtils.isEmpty(emailEdStr)) {
                            if (!TextUtils.isEmpty(streetEdStr)) {
                                if (!TextUtils.isEmpty(cityEdStr)) {
                                    if (!TextUtils.isEmpty(zipEdStr)) {
                                        clickType = true;
                                        saveBtn.setBackgroundResource(R.drawable.button_red_bright);
                                    } else {
                                        clickType = false;
                                        saveBtn.setBackgroundResource(R.drawable.button_red);
                                    }
                                } else {
                                    clickType = false;
                                    saveBtn.setBackgroundResource(R.drawable.button_red);
                                }
                            } else {
                                clickType = false;
                                saveBtn.setBackgroundResource(R.drawable.button_red);
                            }
                        } else {
                            clickType = false;
                            saveBtn.setBackgroundResource(R.drawable.button_red);
                        }
                    } else {
                        clickType = false;
                        saveBtn.setBackgroundResource(R.drawable.button_red);
                    }
                } else {
                    clickType = false;
                    saveBtn.setBackgroundResource(R.drawable.button_red);
                }
            } else {
                clickType = false;
                saveBtn.setBackgroundResource(R.drawable.button_red);
            }
        }else{
            clickType = false;
            saveBtn.setBackgroundResource(R.drawable.button_red);
        }
        setChangeType();
    }

    private class textChange implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Testing();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    @OnClick({R.id.del_btn, R.id.country_btn, R.id.save_btn})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.del_btn:
                if (changeType&&!getIntent().getBooleanExtra("type",false)){
                    intent = new Intent(this, PromptDialog.class);
                    intent.putExtra("content",getString(R.string.SAVEEDITRETURN));
                    startActivityForResult(intent,1);
                }else{
                    finish();
                }
                break;
            case R.id.country_btn:
                intent = new Intent(this, SelectCountryActivity.class);
                intent.putExtra("title","country");
                intent.putExtra("type",true);
                startActivityForResult(intent,0);
                break;
            case R.id.save_btn:
                if (clickType){
                    showLoading();
                    sevaData(true);
                }
                break;

        }
    }
    private void sevaData(boolean con){
        data.setCity(cityEd.getText().toString());
        data.setProvince(provinceEd.getText().toString());
        data.setZipCode(zipEd.getText().toString());
        data.setRoomNo(aptEd.getText().toString());
        data.setName(accountNameEd.getText().toString());
        data.setCode(routingNumEd.getText().toString());
        data.setStreet(streetEd.getText().toString());
        data.setAccount(accountNumberStr);

        if (con) {
            OkHttpUtils
                    .postString()
                    .url(Constant.GET_MERCHANT_SAVE_BANK)
                    .addHeader("Accept-Language", AppUtile.getLanguage())
                    .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                    .mediaType(MediaType.parse("application/json"))
                    .content(JSON.toJSONString(data))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int i) {
                            dismissLoading();
                            if (AppUtile.getNetWork(e.getMessage())){
                                showMsg(getString(R.string.NETERROR));
                            }
                        }

                        @Override
                        public void onResponse(String s, int i) {
                            dismissLoading();
                            if (!TextUtils.isEmpty(s)) {
                                BaseReturn data = JSON.parseObject(s, BaseReturn.class);
                                if (AppUtile.setCode(s,BankAccountActivity.this)) {
                                    YumApplication.getInstance().getpBankAccount().setAccount("");
                                    YumApplication.getInstance().getpBankAccount().setCode("");
                                    YumApplication.getInstance().getpBankAccount().setName("");
                                    YumApplication.getInstance().getpBankAccount().setType(-1);
                                    YumApplication.getInstance().getpBankAccount().setCity("");
                                    YumApplication.getInstance().getpBankAccount().setCountryId("1");
                                    YumApplication.getInstance().getpBankAccount().setCountry("");
                                    YumApplication.getInstance().getpBankAccount().setProvince("");
                                    YumApplication.getInstance().getpBankAccount().setRoomNo("");
                                    YumApplication.getInstance().getpBankAccount().setStreet("");
                                    YumApplication.getInstance().getpBankAccount().setZipCode("");
                                    YumApplication.getInstance().getpBankAccount().setUserId("");
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            }
                        }
                    });
        }else{

            OkHttpUtils
                    .postString()
                    .url(Constant.GET_MERCHANT_UPDATE_BANK)
                    .addHeader("Accept-Language", AppUtile.getLanguage())
                    .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                    .mediaType(MediaType.parse("application/json"))
                    .content(JSON.toJSONString(data))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int i) {
                            dismissLoading();
                            if (AppUtile.getNetWork(e.getMessage())){
                                showMsg(getString(R.string.NETERROR));
                            }
                        }

                        @Override
                        public void onResponse(String s, int i) {
                            dismissLoading();
                            if (!TextUtils.isEmpty(s)) {
                                if (AppUtile.setCode(s,BankAccountActivity.this)) {
                                    YumApplication.getInstance().getpBankAccount().setAccount("");
                                    YumApplication.getInstance().getpBankAccount().setCode("");
                                    YumApplication.getInstance().getpBankAccount().setName("");
                                    YumApplication.getInstance().getpBankAccount().setType(-1);
                                    YumApplication.getInstance().getpBankAccount().setCity("");
                                    YumApplication.getInstance().getpBankAccount().setCountryId("1");
                                    YumApplication.getInstance().getpBankAccount().setCountry("");
                                    YumApplication.getInstance().getpBankAccount().setProvince("");
                                    YumApplication.getInstance().getpBankAccount().setRoomNo("");
                                    YumApplication.getInstance().getpBankAccount().setStreet("");
                                    YumApplication.getInstance().getpBankAccount().setZipCode("");
                                    YumApplication.getInstance().getpBankAccount().setUserId("");
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            }
                        }
                    });
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1){
            if (resultCode == 1001){
                YumApplication.getInstance().getpBankAccount().setCity(cityEd.getText().toString());
                YumApplication.getInstance().getpBankAccount().setProvince(provinceEd.getText().toString());
                YumApplication.getInstance().getpBankAccount().setZipCode(zipEd.getText().toString());
                YumApplication.getInstance().getpBankAccount().setRoomNo(aptEd.getText().toString());
                YumApplication.getInstance().getpBankAccount().setName(accountNameEd.getText().toString());
                YumApplication.getInstance().getpBankAccount().setCode(routingNumEd.getText().toString());
                YumApplication.getInstance().getpBankAccount().setStreet(streetEd.getText().toString());
                YumApplication.getInstance().getpBankAccount().setAccount(accountNumberStr);
                setResult(RESULT_OK);
                finish();
                //sevaData(!getIntent().getBooleanExtra("type",false));
            }
        }
        if (resultCode == RESULT_OK){
            if (requestCode == 0){
                if (intent!=null){
                    YumApplication.getInstance().getpBankAccount().setCountryId(intent.getStringExtra("countryId")+"");
                    YumApplication.getInstance().getpBankAccount().setCountry(intent.getStringExtra("countryName")+"");
                    data.setCountryId(intent.getStringExtra("countryId")+"");
                    countryTv.setText(intent.getStringExtra("countryName"));
                }
            }else{
                if (intent!=null){
                    data.setStreet(intent.getStringExtra("address"));
                    streetEd.setText(intent.getStringExtra("address"));
                }
            }
            Testing();
        }
    }
}
