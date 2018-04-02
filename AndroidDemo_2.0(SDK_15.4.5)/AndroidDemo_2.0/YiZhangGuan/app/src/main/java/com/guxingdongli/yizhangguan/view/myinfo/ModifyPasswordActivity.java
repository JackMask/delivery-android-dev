package com.guxingdongli.yizhangguan.view.myinfo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.util.AbDialogUtil;
import com.ab.util.AbToastUtil;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.YiZhangGuanApplication;
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
 * Created by jackmask on 2018/3/5.
 */

public class ModifyPasswordActivity extends YiZhangGuanActivity {

    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.blue_save_btn)
    TextView blueSaveBtn;
    @Bind(R.id.old_password_tv)
    EditText oldPasswordTv;
    @Bind(R.id.new_password_tv)
    EditText newPasswordTv;
    @Bind(R.id.again_password_tv)
    EditText againPasswordTv;
    @Bind(R.id.show_old_password)
    ImageView showOldPassword;
    @Bind(R.id.show_new_password)
    ImageView showNewPassword;
    @Bind(R.id.show_again_password)
    ImageView showAgainPassword;

    int oldPasswordFlag = 1;
    int newPasswordFlag = 1;
    int againPasswordFlag = 1;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (!AppUtile.checkNull(ModifyPasswordActivity.this, oldPasswordTv.getText().toString().trim(), "原密码", true)) {
                        break;
                    }
                    if (!AppUtile.checkNull(ModifyPasswordActivity.this, newPasswordTv.getText().toString().trim(), "新原密码", true)) {
                        break;
                    }
                    if (!AppUtile.checkAgainPassword(ModifyPasswordActivity.this, againPasswordTv.getText().toString().trim(), newPasswordTv.getText().toString().trim())) {
                        break;
                    }
                    RequestBody formBody = new FormBody.Builder()
                            .add("OriginalPwd", oldPasswordTv.getText().toString().trim())
                            .add("NewPwd", newPasswordTv.getText().toString().trim())
                            .add("NewPwd1", againPasswordTv.getText().toString().trim())
                            .build();
                    HttpUtile httpUtile = new HttpUtile(ModifyPasswordActivity.this, Constant.DOMAIN_NAME + Constant.CHANGEPWD, formBody, new HttpUtileCallBack() {
                        @Override
                        public void getReturnStr(String returnStr) {
                            Looper.prepare();
                            AbDialogUtil.removeDialog(ModifyPasswordActivity.this);
                            AbToastUtil.showToast(ModifyPasswordActivity.this, "修改成功");
                            finish();

                            Looper.loop();
                        }
                        @Override
                        public void getReturnStrFailure(String returnStr) {
                            Looper.prepare();
                            AbDialogUtil.removeDialog(ModifyPasswordActivity.this);
                            Toast.makeText(ModifyPasswordActivity.this,returnStr,Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }
                        @Override
                        public void getErrorStr(String errorStr) {

                        }
                    },true);

                    break;
            }
            return true;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        ButterKnife.bind(this);
        setView();
    }

    private void setView() {
        titleText.setText("密码修改");
        blueSaveBtn.setVisibility(View.VISIBLE);
        blueSaveBtn.setText("保存");
    }

    @OnClick({R.id.return_btn, R.id.blue_save_btn, R.id.show_old_password, R.id.show_new_password, R.id.show_again_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_btn:
                finish();
                break;
            case R.id.blue_save_btn:
                handler.sendEmptyMessage(0);
                break;
            case R.id.show_old_password:
                switch (oldPasswordFlag) {
                    case 0:
                        showOldPassword.setActivated(false);
                        oldPasswordFlag = 1;
                        oldPasswordTv.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        break;
                    case 1:
                        showOldPassword.setActivated(true);
                        oldPasswordFlag = 0;
                        oldPasswordTv.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                break;
            case R.id.show_new_password:
                switch (newPasswordFlag) {
                    case 0:
                        showNewPassword.setActivated(false);
                        newPasswordFlag = 1;
                        newPasswordTv.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        break;
                    case 1:
                        showNewPassword.setActivated(true);
                        newPasswordFlag = 0;
                        newPasswordTv.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                break;
            case R.id.show_again_password:
                switch (againPasswordFlag) {
                    case 0:
                        showAgainPassword.setActivated(false);
                        againPasswordFlag = 1;
                        againPasswordTv.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        break;
                    case 1:
                        showAgainPassword.setActivated(true);
                        againPasswordFlag = 0;
                        againPasswordTv.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                break;
        }
    }


}
