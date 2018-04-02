package com.guxingdongli.yizhangguan.view.login;

import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.util.AbToastUtil;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.util.AppUtile;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 忘记密码
 * Created by jackmask on 2018/3/1.
 */

public class ForgetPasswordActivity extends YiZhangGuanActivity {
    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.title_layout)
    LinearLayout titleLayout;
    @Bind(R.id.phone_tv)
    EditText phoneTv;
    @Bind(R.id.code_tv)
    EditText codeTv;
    @Bind(R.id.send_code_btn)
    TextView sendCodeBtn;
    @Bind(R.id.password_tv)
    EditText passwordTv;
    @Bind(R.id.again_password_tv)
    EditText againPasswordTv;
    @Bind(R.id.linearLayout)
    LinearLayout linearLayout;
    @Bind(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @Bind(R.id.reset_btn)
    TextView resetBtn;

    private AppUtile.TimeCount time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        getView();
    }

    private void getView(){
        time = new AppUtile.TimeCount(60000, 1000,sendCodeBtn);
    }

    @OnClick({R.id.return_btn, R.id.send_code_btn,R.id.reset_btn})
    public void onViewClicked(View view) {
        HttpUtile httpUtile;
        switch (view.getId()) {
            case R.id.reset_btn://重置
                if (!AppUtile.checkNull(this,phoneTv.getText().toString().trim(),"手机号",true))
                    break;
                if (!AppUtile.checkNull(this,codeTv.getText().toString().trim(),"验证码",true))
                    break;
                if (!AppUtile.checkPassword(this,passwordTv.getText().toString().trim()))
                    break;
                if (!AppUtile.checkAgainPassword(this,againPasswordTv.getText().toString().trim(),passwordTv.getText().toString().trim()))
                    break;
                String json = "{\"id\":1,\"mobilePhone\":"+phoneTv.getText().toString().trim()+",\"verificationCode\":"+codeTv.getText().toString().trim()+
                        ",\"loginPassword\":"+passwordTv.getText().toString().trim()+",\"loginPassword1\":"+againPasswordTv.getText().toString().trim()+"}";
                System.out.println("json = " + json);
                httpUtile = new HttpUtile(this, Constant.DOMAIN_NAME + Constant.FINDPWD, json, new HttpUtileCallBack() {
                    @Override
                    public void getReturnStr(String returnStr) {
                        finish();
                        Looper.prepare();
                        Toast.makeText(ForgetPasswordActivity.this,"完成",Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                    @Override
                    public void getReturnStrFailure(String returnStr) {
                        Looper.prepare();
                        Toast.makeText(ForgetPasswordActivity.this,returnStr,Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                    @Override
                    public void getErrorStr(String errorStr) {

                    }
                },false);

                break;

            case R.id.return_btn://返回
                finish();

                break;
            case R.id.send_code_btn://发送验证码
                if (!AppUtile.checkNull(this,phoneTv.getText().toString().trim(),"手机号",true))
                    break;
                RequestBody formBody = new  FormBody.Builder()
                        .add("Id", "0")
                        .add("ActionType", "找回密码")
                        .add("MobilePhone", phoneTv.getText().toString())
                        .build();
                httpUtile = new HttpUtile(this, Constant.DOMAIN_NAME + Constant.GETVERCODE, formBody, new HttpUtileCallBack() {
                    @Override
                    public void getReturnStr(String returnStr) {
                        time.start();
                        Looper.prepare();
                        Toast.makeText(ForgetPasswordActivity.this,"验证码已发送",Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                    @Override
                    public void getReturnStrFailure(String returnStr) {
                        Looper.prepare();
                        Toast.makeText(ForgetPasswordActivity.this,returnStr,Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                    @Override
                    public void getErrorStr(String errorStr) {

                    }
                },false);

                break;
        }
    }
}
