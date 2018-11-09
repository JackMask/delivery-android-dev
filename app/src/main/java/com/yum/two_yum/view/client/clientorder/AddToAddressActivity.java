package com.yum.two_yum.view.client.clientorder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.SearchNearbyBase;
import com.yum.two_yum.base.SearchNearbyDataBase;
import com.yum.two_yum.base.input.AddToAddressInput;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.ContainsEmojiEditText;
import com.yum.two_yum.view.login.LoginActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * @author 余先德
 * @data 2018/4/9
 */

public class AddToAddressActivity extends BaseActivity {

    private static final String TAG = "AddToAddressActivity";

    @Bind(R.id.name_ed)
    ContainsEmojiEditText nameEd;
    @Bind(R.id.phone_ed)
    ContainsEmojiEditText phoneEd;
    @Bind(R.id.address_ed)
    TextView addressEd;
    @Bind(R.id.ps_tv)
    TextView psTv;
    @Bind(R.id.ps_ed)
    ContainsEmojiEditText psEd;
    @Bind(R.id.save_btn)
    TextView saveBtn;
    @Bind(R.id.sex_rg)
    RadioGroup sexRg;

    @Bind(R.id.hospital_button)
    RadioButton hospitalButton;
    @Bind(R.id.merchants_button)
    RadioButton merchantsButton;
    @Bind(R.id.select_address_btn)
    LinearLayout selectAddressBtn;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.save_out_btn)
    TextView saveOutBtn;
    @Bind(R.id.del_btn)
    ImageView delBtn;

    private boolean sexType = false;
    private boolean saveType = false;
    private AddToAddressInput addToAddressInput;
    private String lat, lng;
    private SearchNearbyDataBase data;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_address);
        ButterKnife.bind(this);
        addToAddressInput = new AddToAddressInput();

        data = (SearchNearbyDataBase) getIntent().getSerializableExtra("data");
        setView();
    }


    protected void setView() {
        addToAddressInput.setUserId(YumApplication.getInstance().getMyInformation().getUid());
        if (data != null) {
            delBtn.setImageResource(R.mipmap.back_img);
            saveOutBtn.setVisibility(View.VISIBLE);
            titleTv.setText(getString(R.string.EDITADDRESS));
            addToAddressInput.setGender(data.getGender());
            if (data.getGender() == 1) {
                merchantsButton.setChecked(true);
                hospitalButton.setChecked(false);
            } else {
                merchantsButton.setChecked(false);
                hospitalButton.setChecked(true);

            }
            nameEd.setText(data.getName());
            phoneEd.setText(data.getPhone());
            addressEd.setText(data.getAddress());
            psEd.setText(data.getNote());
            addToAddressInput.setLat(data.getLat());
            addToAddressInput.setLng(data.getLng());
            addToAddressInput.setNote(data.getNote());
            addToAddressInput.setAddress(data.getAddress());
            addressStr = data.getAddress();
            addToAddressInput.setPhone(data.getPhone());
            addToAddressInput.setName(data.getName());
            addToAddressInput.setId(data.getId());
            lat = data.getLat();
            lng = data.getLng();
            saveType = true;
            saveBtn.setBackgroundResource(R.drawable.button_red_bright);
        } else {
            saveOutBtn.setVisibility(View.GONE);
            addToAddressInput.setGender(1);

        }
        sexRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.hospital_button://女
                        sexType = true;
                        addToAddressInput.setGender(0);
                        Log.i(TAG, "女");
                        break;
                    case R.id.merchants_button://男
                        sexType = false;
                        addToAddressInput.setGender(1);
                        Log.i(TAG, "男");
                        break;

                }
            }
        });
        nameEd.addTextChangedListener(new AddTextWatcher());
        phoneEd.addTextChangedListener(new AddTextWatcher());
    }

    private class AddTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (examinationData()) {
                saveBtn.setBackgroundResource(R.drawable.button_red_bright);
            } else {
                saveBtn.setBackgroundResource(R.drawable.button_red);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private boolean examinationData() {
        String name = nameEd.getText().toString();
        String phone = phoneEd.getText().toString();
        String address = addressEd.getText().toString();
        if (!TextUtils.isEmpty(name)) {
            if (!TextUtils.isEmpty(phone)) {
                if (!TextUtils.isEmpty(address)) {
                    saveType = true;
                    saveBtn.setBackgroundResource(R.drawable.button_red_bright);
                    return true;
                } else {
                    saveType = false;
                    saveBtn.setBackgroundResource(R.drawable.button_red);
                    return false;
                }
            } else {
                saveType = false;
                saveBtn.setBackgroundResource(R.drawable.button_red);
                return false;
            }
        } else {
            saveType = false;
            saveBtn.setBackgroundResource(R.drawable.button_red);
            return false;
        }
    }

    @OnClick({R.id.del_btn, R.id.select_address_btn, R.id.save_btn,R.id.save_out_btn})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.save_out_btn:
                showLoading();
                OkHttpUtils
                        .post()
                    .addHeader("Accept-Language", AppUtile.getLanguage())
                    .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                    .url(Constant.DEL_ADDRESS)
                        .addParams("addressId",data.getId())
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int i) {
                            dismissLoading();
                            System.out.println(e.getMessage());
                        }

                        @Override
                        public void onResponse(String s, int i) {
                            dismissLoading();
                            if (AppUtile.setCode(s,AddToAddressActivity.this)) {
                                if (data.getId().equals(YumApplication.getInstance().getAddressDB().getId())){
                                    YumApplication.getInstance().getAddressDB().setAddress("");
                                    YumApplication.getInstance().getAddressDB().setGender(-1);
                                    YumApplication.getInstance().getAddressDB().setId("");
                                    YumApplication.getInstance().getAddressDB().setLat("");
                                    YumApplication.getInstance().getAddressDB().setLng("");
                                    YumApplication.getInstance().getAddressDB().setName("");
                                    YumApplication.getInstance().getAddressDB().setNote("");
                                    YumApplication.getInstance().getAddressDB().setPhone("");
                                    YumApplication.getInstance().getAddressDB().setUserId("");
                                }
                                setResult(RESULT_OK);
                                finish();
                                overridePendingTransition(R.anim.activity_open, R.anim.in);
                            }
                        }
                    });
                break;
            case R.id.del_btn:
                finish();
                if (!getIntent().getBooleanExtra("type",false)){
                    overridePendingTransition(R.anim.activity_open, R.anim.out);
                }

                break;
            case R.id.select_address_btn:
                intent = new Intent(this, SearchAddressActivity.class);
                startActivityForResult(intent, 0);
                overridePendingTransition(R.anim.activity_open,R.anim.in);
                break;
            case R.id.save_btn:
                if (saveType) {
                    addToAddressInput.setName(nameEd.getText().toString());
                    addToAddressInput.setPhone(phoneEd.getText().toString());
                    addToAddressInput.setLat(lat);
                    addToAddressInput.setLng(lng);
                    addToAddressInput.setAddress(addressStr);
                    addToAddressInput.setNote(psEd.getText().toString());
                    showLoading();
                    if (data == null) {

                        // String content = JSON.toJSONString(addToAddressInput);
                        OkHttpUtils
                                .postString()
                                .mediaType(MediaType.parse("application/json"))
                                .addHeader("Accept-Language", AppUtile.getLanguage())
                                .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                                .url(Constant.ADD_USER_ADDRESS)
                                .content(JSON.toJSONString(addToAddressInput))
                                .build()
                                .execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int i) {
                                        if (AppUtile.getNetWork(e.getMessage())){
                                            showMsg(getString(R.string.NETERROR));
                                        }
                                        dismissLoading();
                                    }

                                    @Override
                                    public void onResponse(String s, int i) {
                                        dismissLoading();
                                        if (AppUtile.setCode(s,AddToAddressActivity.this)) {
                                            setResult(RESULT_OK);
                                            finish();
                                            overridePendingTransition(R.anim.activity_open, R.anim.in);
                                        }
                                    }
                                });

                    } else {
                        OkHttpUtils
                                .postString()
                                .mediaType(MediaType.parse("application/json"))
                                .addHeader("Accept-Language", AppUtile.getLanguage())
                                .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                                .url(Constant.EDIT_USER_ADDRESS)
                                .content(JSON.toJSONString(addToAddressInput))
                                .build()
                                .execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int i) {
                                        if (AppUtile.getNetWork(e.getMessage())){
                                            showMsg(getString(R.string.NETERROR));
                                        }
                                        dismissLoading();
                                    }

                                    @Override
                                    public void onResponse(String s, int i) {
                                        dismissLoading();
                                        if (AppUtile.setCode(s,AddToAddressActivity.this)) {
                                            if (addToAddressInput.getId().equals(YumApplication.getInstance().getAddressDB().getId())){
                                                YumApplication.getInstance().getAddressDB().setAddress(addToAddressInput.getAddress());
                                                YumApplication.getInstance().getAddressDB().setGender(addToAddressInput.getGender());
                                                YumApplication.getInstance().getAddressDB().setId(addToAddressInput.getId());
                                                YumApplication.getInstance().getAddressDB().setLat(addToAddressInput.getLat());
                                                YumApplication.getInstance().getAddressDB().setLng(addToAddressInput.getLng());
                                                YumApplication.getInstance().getAddressDB().setName(addToAddressInput.getName());
                                                YumApplication.getInstance().getAddressDB().setNote(addToAddressInput.getNote());
                                                YumApplication.getInstance().getAddressDB().setPhone(addToAddressInput.getPhone());
                                                YumApplication.getInstance().getAddressDB().setUserId(addToAddressInput.getUserId());
                                            }
                                            setResult(RESULT_OK);
                                            finish();
                                            overridePendingTransition(R.anim.activity_open, R.anim.in);
                                        }
                                    }
                                });

                    }
                }
                break;
        }
    }
    private String addressStr = "";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data != null) {
                lat = data.getStringExtra("lat");
                lng = data.getStringExtra("lng");
                String addStr = data.getStringExtra("address").replace(data.getStringExtra("name"),"");
                //addressStr = data.getStringExtra("name")+", "+(addStr.substring(0,1).equals(",")?addStr.substring(1,addStr.length()):addStr);
                try {
                    addressStr = data.getStringExtra("name") + "\n" + (addStr.substring(0, 1).equals(",") ? addStr.substring(1, addStr.length()) : addStr);
                }catch (Exception e){
                    addressStr =data.getStringExtra("name") + "\n" + addStr;
                }
                addressEd.setText(addressStr.trim());
                examinationData();
            }
        }
    }
}
