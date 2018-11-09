package com.yum.two_yum.view.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.util.AbDialogUtil;
import com.alibaba.fastjson.JSON;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.BaseReturn;
import com.yum.two_yum.base.LoginBase;
import com.yum.two_yum.base.input.ForgetPasswordInputBase;
import com.yum.two_yum.base.input.LoginInputBase;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.AutoEndEditText2;
import com.yum.two_yum.utile.ContainsEmojiEditText;
import com.yum.two_yum.view.dialog.Prompt2Dialog;
import com.yum.two_yum.view.guide.SelectCountryActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @author 余先德
 * @data 2018/4/7
 */

public class ForgetPasswordActivity extends BaseActivity {

    @Bind(R.id.email_et)
    ContainsEmojiEditText emailEt;
    @Bind(R.id.clear_btn)
    ImageView clearBtn;
    @Bind(R.id.create_account_btn)
    TextView createAccountBtn;

    private boolean contype = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        setView();
    }

    @Override
    protected void setView() {
        super.setView();
        emailEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String reg = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
                Pattern pattern = Pattern.compile(reg);
                if(!TextUtils.isEmpty(s.toString())){
                    clearBtn.setVisibility(View.VISIBLE);
                    Matcher matcher = pattern.matcher(s.toString());
                    boolean b = matcher.matches();
                    if (b){
                        contype = true;
                        createAccountBtn.setTextColor(Color.parseColor("#484848"));

                    }else{
                        contype = false;
                        createAccountBtn.setTextColor(Color.parseColor("#DBDBDB"));
                    }
                }else{
                    clearBtn.setVisibility(View.GONE);
                    contype = false;
                    createAccountBtn.setTextColor(Color.parseColor("#DBDBDB"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.del_btn, R.id.clear_btn,R.id.create_account_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.clear_btn:
                emailEt.setText("");
                break;
            case R.id.del_btn:
                finish();
                break;

            case R.id.create_account_btn:
                if (contype){
                    showLoading();
                    ForgetPasswordInputBase inputBase = new ForgetPasswordInputBase();
                    inputBase.setEmail(emailEt.getText().toString());
                    inputBase.setTypeCode(getLanguage());
                    //appHttpUtile.postJson(Constant.FORGET_PASSWORD,JSON.toJSONString(inputBase));
                    OkHttpUtils
                            .post()
                            .url(Constant.FORGET_PASSWORD)
                            .addHeader("Accept-Language", AppUtile.getLanguage())
                            .addParams("email", emailEt.getText().toString())
                            .addParams("authType", "resetpwd")
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
                                        if (AppUtile.setCode(s,ForgetPasswordActivity.this)) {
                                            showMsg(getResources().getString(R.string.SUBMITTEDSUCCESSFULLY));
                                            finish();
//                                            Intent intent = new Intent(ForgetPasswordActivity.this, Prompt2Dialog.class);
//                                            intent.putExtra("content", baseReturn.getMsg());
//                                            intent.putExtra("title", false);
//                                            startActivityForResult(intent, 0);
                                    }
                                }
                            });
                    break;
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0&&resultCode==1001){
            finish();
        }
    }

}
