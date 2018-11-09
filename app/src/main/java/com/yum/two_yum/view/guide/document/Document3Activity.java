package com.yum.two_yum.view.guide.document;

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
import com.yum.two_yum.base.input.CertInput;
import com.yum.two_yum.base.input.RegisterInputBase;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.view.guide.GuideBaseActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * @author 余先德
 * @data 2018/4/14
 */

public class Document3Activity extends GuideBaseActivity {

    @Bind(R.id.woman_button)
    RadioButton womanButton;
    @Bind(R.id.man_button)
    RadioButton manButton;
    @Bind(R.id.sex_rg)
    RadioGroup sexRg;
    @Bind(R.id.name_ed)
    EditText nameEd;
    @Bind(R.id.phone_ed)
    EditText phoneEd;
    @Bind(R.id.email_ed)
    EditText emailEd;
    @Bind(R.id.save_btn)
    TextView saveBtn;

    private CertInput data;
    private boolean click = false;
    private boolean clickType = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document3);
        ButterKnife.bind(this);
        data = (CertInput)getIntent().getSerializableExtra("data");
        setViewData();
    }

    private void setViewData(){
        if (data!=null){
            nameEd.setText(data.getName());
            phoneEd.setText(data.getPhone()+"");
            emailEd.setText(data.getEmail());
            if (data.getGender().equals("0")){
                womanButton.setChecked(true);
            }
            if (!TextUtils.isEmpty(data.getName())&&!TextUtils.isEmpty(data.getEmail())&&!TextUtils.isEmpty(data.getPhone())){
                click = true;
                saveBtn.setBackgroundResource(R.drawable.button_red_bright);
            }
        }
        sexRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.woman_button:
                        data.setGender("0");
                        break;
                    case R.id.man_button:
                        data.setGender("1");
                        break;

                }
            }
        });
        nameEd.addTextChangedListener(new TextChanged(false));
        phoneEd.addTextChangedListener(new TextChanged(false));
        emailEd.addTextChangedListener(new TextChanged(true));
    }
    private class TextChanged implements TextWatcher{
        boolean type = false;
        public TextChanged(boolean con){
            type = con;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (type){
                String reg = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
                Pattern pattern = Pattern.compile(reg);
                if (!TextUtils.isEmpty(s.toString())) {
                    Matcher matcher = pattern.matcher(s.toString());
                    boolean b = matcher.matches();
                    if (b&&examinationData()) {
                        click = true;
                        saveBtn.setBackgroundResource(R.drawable.button_red_bright);

                    } else {
                        click = false;
                        saveBtn.setBackgroundResource(R.drawable.button_red);
                    }
                } else {
                    click = false;
                    saveBtn.setBackgroundResource(R.drawable.button_red);
                }
            }else{
                examinationData();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private boolean examinationData(){
        String name = nameEd.getText().toString();
        String phone = phoneEd.getText().toString();
        String email = emailEd.getText().toString();
        if (!TextUtils.isEmpty(name)){
            if (!TextUtils.isEmpty(phone)){
                if (!TextUtils.isEmpty(email)){
                    click = true;
                    saveBtn.setBackgroundResource(R.drawable.button_red_bright);
                    return true;
                }else{
                    click = false;
                    saveBtn.setBackgroundResource(R.drawable.button_red);
                    return false;
                }
            }else{
                click = false;
                saveBtn.setBackgroundResource(R.drawable.button_red);
                return false;
            }
        }else{
            click = false;
            saveBtn.setBackgroundResource(R.drawable.button_red);
            return false;
        }

    }
    @OnClick({R.id.del_btn, R.id.save_btn,R.id.save_out_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.save_out_btn:
                clickType = true;
                showLoading();
                if (data.getCertUrl().substring(0,4).equals("http")){
                    sevaData();
                }else {
                    UploadPersonal(data.getCertUrl());
                }
                break;
            case R.id.del_btn:
                finish();
                break;
            case R.id.save_btn:
                if (click) {
                    clickType = false;
                    showLoading();
                    if (data.getCertUrl().substring(0,4).equals("http")){
                        sevaData();
                    }else {
                        UploadPersonal(data.getCertUrl());
                    }
//                    data.setName(nameEd.getText().toString());
//                    data.setPhone(phoneEd.getText().toString());
//                    data.setEmail(emailEd.getText().toString());
//                    Intent intent = new Intent(this, Document4Activity.class);
//                    intent.putExtra("data",data);
//                    startActivityForResult(intent,0);
                }
                break;
        }
    }
    private void UploadPersonal(String picStr){
        OkHttpUtils
                .post()
                .url(Constant.FILEINFO_UPLOADPERSONAL)
                .addFile("files", AppUtile.getTime()+".png",new File(picStr))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        // System.out.println("e = " + e.getMessage());
                        showMsg(e.getMessage());
                        dismissLoading();
                        //AbDialogUtil.removeDialog(RegisteredActivity.this);
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        // System.out.println("s = " + s);
                        RegisterInputBase inputBase = JSON.parseObject(s,RegisterInputBase.class);
                        if (inputBase.getCode().equals(Constant.SUCCESSCON)&&inputBase.getData().getFileRespResults()!=null&&inputBase.getData().getFileRespResults().size()>0){
                            data.setCertUrl(inputBase.getData().getFileRespResults().get(0).getUrl());
                            sevaData();
                            // registered(inputBase.getData().getFileRespResults().get(0).getUrl());
                        }
                        // inputBase.setUrl(uploadFlieBase.getData());
                        //appHttpUtile.postJson(Constant.USER_REGISTER,JSON.toJSONString(inputBase));
                    }
                });
    }
    private void sevaData(){
        data.setName(nameEd.getText().toString());
        data.setPhone(phoneEd.getText().toString());
        data.setEmail(emailEd.getText().toString());
        OkHttpUtils
                .postString()
                .url(Constant.GET_MERCHANT_SAVE_CERT)
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
                        }else{
                            Intent intent = new Intent(Document3Activity.this, Document4Activity.class);
                            intent.putExtra("data",data);
                            startActivityForResult(intent,0);
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
