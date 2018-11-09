package com.yum.two_yum.view.merchants.menu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.squareup.picasso.Picasso;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BankBase;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.BaseReturn;
import com.yum.two_yum.base.CertBase;
import com.yum.two_yum.base.MerchantBase;
import com.yum.two_yum.base.input.BankInput;
import com.yum.two_yum.base.input.CertInput;
import com.yum.two_yum.base.input.MerchantInput;
import com.yum.two_yum.base.input.RegisterInputBase;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.utile.ActionSheetDialog;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.ContainsEmojiEditText;
import com.yum.two_yum.utile.PicassoImageLoader;
import com.yum.two_yum.view.client.ClientActivity;
import com.yum.two_yum.view.client.clientorder.SearchAddressActivity;
import com.yum.two_yum.view.dialog.PromptDialog;
import com.yum.two_yum.view.guide.ReleaseActivity;
import com.yum.two_yum.view.guide.SelectCountryActivity;
import com.yum.two_yum.view.guide.bank.Bank5Activity;
import com.yum.two_yum.view.guide.document.Document1Activity;
import com.yum.two_yum.view.guide.operations.Operations7Activity;
import com.yum.two_yum.view.my.SettingsActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * @author 余先德
 * @data 2018/4/12
 */

public class YourIdActivity extends BaseActivity {

    @Bind(R.id.id_ed)
    ContainsEmojiEditText idEd;
    @Bind(R.id.hospital_button)
    RadioButton hospitalButton;
    @Bind(R.id.merchants_button)
    RadioButton merchantsButton;
    @Bind(R.id.woman_button)
    RadioButton womanButton;
    @Bind(R.id.man_button)
    RadioButton manButton;
    @Bind(R.id.sex_rg)
    RadioGroup sexRg;
    @Bind(R.id.name_ed)
    ContainsEmojiEditText nameEd;
    @Bind(R.id.phone_ed)
    ContainsEmojiEditText phoneEd;
    @Bind(R.id.email_ed)
    ContainsEmojiEditText emailEd;
    @Bind(R.id.country_ed)
    TextView countryEd;
    @Bind(R.id.street_ed)
    ContainsEmojiEditText streetEd;
    @Bind(R.id.apt_ed)
    ContainsEmojiEditText aptEd;
    @Bind(R.id.city_ed)
    ContainsEmojiEditText cityEd;
    @Bind(R.id.state_ed)
    ContainsEmojiEditText stateEd;
    @Bind(R.id.zip_ed)
    ContainsEmojiEditText zipEd;
    @Bind(R.id.save_btn)
    TextView saveBtn;
    @Bind(R.id.select_id_btn)
    RadioGroup selectIdBtn;
    @Bind(R.id.id_iv)
    ImageView idIv;
    @Bind(R.id.street_btn)
    LinearLayout streetBtn;
    @Bind(R.id.add_img_btn)
    RelativeLayout addImgBtn;
    @Bind(R.id.add_img)
    ImageView addImg;
    @Bind(R.id.prompt_btn)
    LinearLayout promptBtn;
    @Bind(R.id.prompt_iv)
    ImageView promptIv;


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private boolean clickType = false;
    private boolean sexType = false;
    private String selectType = "";
    private boolean promptType = false;
    private CertInput data;
    private boolean changeType = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_id);
        ButterKnife.bind(this);
        setView();
    }


    @Override
    protected void setView() {
        super.setView();
        data = (CertInput)getIntent().getSerializableExtra("data");
        if (data!=null) {
            if (!TextUtils.isEmpty(data.getCertUrl())) {
                if (data.getCertUrl().substring(0, 4).equals("http")) {
                    Picasso.get().load(data.getCertUrl()).placeholder(R.mipmap.timg).into(idIv);
                }else{
                    Bitmap bitmap = AppUtile.getSmallBitmap(data.getCertUrl());
                    idIv.setImageBitmap(bitmap);
                }

                addImg.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(data.getCertType())) {
                if (data.getCertType().equals("1")) {
                    selectType = "1";
                    hospitalButton.setChecked(true);
                } else if (data.getCertType().equals("0")) {
                    selectType = "0";
                    merchantsButton.setChecked(true);
                }
            }
            nameEd.setText(data.getName());
            if (!TextUtils.isEmpty(data.getGender())&&data.getGender().equals("0")) {
                womanButton.setChecked(true);
            }
            if ((!TextUtils.isEmpty(data.getCertNo()))){
                idEd.setText(data.getCertNo());
            }else{
                data.setCertNo("");
                idEd.setText("");
            }
            if (getIntent().getBooleanExtra("type",false)){
                promptIv.setImageResource(R.mipmap.chked);
                promptType = true;
            }else {
                if (!TextUtils.isEmpty(data.isOkCode())&&data.isOkCode().equals("1")) {
                    promptIv.setImageResource(R.mipmap.chked);
                    promptType = true;
                } else {
                    promptIv.setImageResource(R.mipmap.unchk);
                    promptType = false;
                }
            }
            phoneEd.setText(data.getPhone());
            emailEd.setText(data.getEmail());
            countryEd.setText(data.getCountry());
            streetEd.setText(data.getStreet());
            aptEd.setText(data.getRoomNo());
            cityEd.setText(data.getCity());
            stateEd.setText(data.getProvince());
            zipEd.setText(data.getZipCode());
//            if (!TextUtils.isEmpty(data.getCertUrl())
//                    ||(!TextUtils.isEmpty(data.getCertNo())&&data.getCertNo().length()==9)
//                    ||!TextUtils.isEmpty(data.getName())
//                    ||!TextUtils.isEmpty(data.getPhone())
//                    ||!TextUtils.isEmpty(data.getEmail())
//                    ||!TextUtils.isEmpty(data.getCountryId())
//                    ||!TextUtils.isEmpty(data.getStreet())
//                    ||!TextUtils.isEmpty(data.getCity())
//                    ||!TextUtils.isEmpty(data.getProvince())
//                    ||!TextUtils.isEmpty(data.getZipCode())
//                    ){
//
//
//            }
            if (!TextUtils.isEmpty(data.getCertUrl())
                    &&!TextUtils.isEmpty(data.getCertNo())
                    &&!TextUtils.isEmpty(data.getName())
                    &&!TextUtils.isEmpty(data.getPhone())
                    &&!TextUtils.isEmpty(data.getEmail())
                    &&!TextUtils.isEmpty(data.getCountryId())
                    &&!TextUtils.isEmpty(data.getStreet())
                    &&!TextUtils.isEmpty(data.getCity())
                    &&!TextUtils.isEmpty(data.getProvince())
                    &&!TextUtils.isEmpty(data.getZipCode())
                    &&((!TextUtils.isEmpty(data.isOkCode())&&data.isOkCode().equals("1"))||getIntent().getBooleanExtra("type",false))
                    &&!TextUtils.isEmpty(selectType)){

                clickType = true;
                saveBtn.setBackgroundResource(R.drawable.button_red_bright);
            }
        }


        selectIdBtn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setChangeType();
                switch (group.getCheckedRadioButtonId()) {

                    case R.id.hospital_button://SSN
                        selectType = "1";
                        data.setCertType("1");
                        YumApplication.getInstance().getpYourId().setCertType("1");
                        break;
                    case R.id.merchants_button://EIN
                        selectType = "0";
                        data.setCertType("0");
                        YumApplication.getInstance().getpYourId().setCertType("0");
                        break;

                }
                Testing();
            }
        });
        sexRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setChangeType();
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.woman_button://女
                        sexType = true;
                        data.setGender("0");
                        YumApplication.getInstance().getpYourId().setGender("0");
                        break;
                    case R.id.man_button://男
                        sexType = false;
                        data.setGender("1");
                        YumApplication.getInstance().getpYourId().setGender("1");
                        break;

                }
            }
        });
        idEd.addTextChangedListener(new textChange());
        nameEd.addTextChangedListener(new textChange());
        phoneEd.addTextChangedListener(new textChange());
        emailEd.addTextChangedListener(new textChange());
        streetEd.addTextChangedListener(new textChange());
        cityEd.addTextChangedListener(new textChange());
        stateEd.addTextChangedListener(new textChange());
        zipEd.addTextChangedListener(new textChange());
    }

    private class textChange implements TextWatcher {

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

    private void setChangeType(){
        changeType = true;
    }

    private void Testing() {
        String idEdStr = idEd.getText().toString();
        String nameEdStr = nameEd.getText().toString();
        String phoneEdStr = phoneEd.getText().toString();
        String emailEdStr = emailEd.getText().toString();
        String countryEdStr = countryEd.getText().toString();
        String streetEdStr = streetEd.getText().toString();
        String cityEdStr = cityEd.getText().toString();
        String stateEdStr = stateEd.getText().toString();
        String zipEdStr = zipEd.getText().toString();
        String urlStr =  data.getCertUrl();
        if (!TextUtils.isEmpty(selectType)) {
            if (promptType) {
                if (!TextUtils.isEmpty(urlStr)) {
                    if (!TextUtils.isEmpty(idEdStr)) {
                        if (!TextUtils.isEmpty(nameEdStr)) {
                            if (!TextUtils.isEmpty(phoneEdStr)) {
                                if (!TextUtils.isEmpty(emailEdStr)) {
                                    if (!TextUtils.isEmpty(countryEdStr)) {
                                        if (!TextUtils.isEmpty(streetEdStr)) {
                                            if (!TextUtils.isEmpty(cityEdStr)) {
                                                if (!TextUtils.isEmpty(stateEdStr)) {
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
                        } else {
                            clickType = false;
                            saveBtn.setBackgroundResource(R.drawable.button_red);
                        }
                    } else {
                        //showMsg(getString(R.string.ERRORTAXID));
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
    IHandlerCallBack iHandlerCallBack = new IHandlerCallBack() {
        @Override
        public void onStart() {
        }

        @Override
        public void onSuccess(List<String> photoList) {
            for (String s : photoList) {
//                //click = true;
//                saveBtn.setBackgroundResource(R.drawable.button_red_bright);

                data.setCertUrl(s);
                addImg.setVisibility(View.GONE);
                Bitmap bitmap = AppUtile.getSmallBitmap(s);
                idIv.setImageBitmap(bitmap);
                Testing();
            }
        }

        @Override
        public void onCancel() {
        }

        @Override
        public void onFinish() {

        }

        @Override
        public void onError() {
        }
    };
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意，可以做你要做的事情了。
                    GalleryConfig galleryConfig = new GalleryConfig.Builder()
                            .imageLoader(new PicassoImageLoader())    // ImageLoader 加载框架（必填）
                            .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                            .provider("com.yum.two_yum.fileprovider")   // provider (必填)
                            .multiSelect(false)                      // 是否多选   默认：false
                            .isShowCamera(false)                     // 是否现实相机按钮  默认：false
                            .crop(true)                             // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
                            .crop(true, 1, 1, 500, 500)
                            .filePath("/Gallery/Pictures")          // 图片存放路径
                            .build();
                    GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(this);
                } else {
                    // 权限被用户拒绝了，可以提示用户,关闭界面等等。

                }
                return;
            }
        }
    }

    @OnClick({R.id.del_btn, R.id.country_btn, R.id.save_btn,R.id.add_img_btn,R.id.prompt_btn})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.prompt_btn:
                if (promptType){
                    YumApplication.getInstance().getpYourId().setOkCode("0");
                    promptIv.setImageResource(R.mipmap.unchk);
                }else{
                    YumApplication.getInstance().getpYourId().setOkCode("1");
                    promptIv.setImageResource(R.mipmap.chked);
                }
                promptType = !promptType;
                Testing();
                break;
            case R.id.add_img_btn:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {
                    // 没有权限，可以在这里重新申请权限。

                    ActivityCompat.requestPermissions(
                            this,
                            PERMISSIONS_STORAGE,
                            REQUEST_EXTERNAL_STORAGE
                    );
                }else{
                    // 有权限了。
                    GalleryConfig galleryConfig = new GalleryConfig.Builder()
                            .imageLoader(new PicassoImageLoader())    // ImageLoader 加载框架（必填）
                            .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                            .provider("com.yum.two_yum.fileprovider")   // provider (必填)
                            .multiSelect(false)                      // 是否多选   默认：false
                            .isShowCamera(false)                     // 是否现实相机按钮  默认：false
                            .crop(true)                             // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
                            .crop(true, 1, 1, 500, 500)
                            .filePath("/Gallery/Pictures")          // 图片存放路径
                            .build();
                    GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(this);
                }
                break;


            case R.id.save_btn:
                if (promptType) {
                    if (clickType) {
                        if (TextUtils.isEmpty(idEd.getText().toString()) || idEd.getText().toString().length() < 9) {
                            showMsg(getString(R.string.ERRORTAXID));
                            break;
                        }
                        showLoading();
                        if (data.getCertUrl().substring(0, 4).equals("http")) {
                            sevaData(true);
                        } else {
                            UploadPersonal(data.getCertUrl(),true);
                        }
                    }
                }
                break;
            case R.id.del_btn:
                if (changeType&&!getIntent().getBooleanExtra("type",false)){
                        intent = new Intent(this, PromptDialog.class);
                        intent.putExtra("content", getString(R.string.SAVEEDITRETURN));
                        startActivityForResult(intent, 1);
                }else{
                  finish();
                }
                break;
            case R.id.country_btn:
                intent = new Intent(this, SelectCountryActivity.class);
                intent.putExtra("title","Country");
                intent.putExtra("type",true);
                startActivityForResult(intent,0);
                break;
        }
    }
    private void UploadPersonal(String picStr,final boolean con){
        OkHttpUtils
                .post()
                .url(Constant.FILEINFO_UPLOADPERSONAL)
                .addHeader("Accept-Language", AppUtile.getLanguage())
                .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                .addFile("files", AppUtile.getTime()+".png",new File(picStr))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        if (AppUtile.getNetWork(e.getMessage())){
                            showMsg(getString(R.string.NETERROR));
                        }
                        dismissLoading();
                        //AbDialogUtil.removeDialog(RegisteredActivity.this);
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        // System.out.println("s = " + s);

                        if (AppUtile.setCode(s,YourIdActivity.this)){
                            RegisterInputBase inputBase = JSON.parseObject(s,RegisterInputBase.class);
                            if (inputBase.getData().getFileRespResults()!=null&&inputBase.getData().getFileRespResults().size()>0) {
                                data.setCertUrl(inputBase.getData().getFileRespResults().get(0).getUrl());
                                System.out.println(inputBase.getData().getFileRespResults().get(0).getUrl());
                                sevaData(con);
                            }
                            // registered(inputBase.getData().getFileRespResults().get(0).getUrl());
                        }else{
                            dismissLoading();
                        }
                        // inputBase.setUrl(uploadFlieBase.getData());
                        //appHttpUtile.postJson(Constant.USER_REGISTER,JSON.toJSONString(inputBase));
                    }
                });
    }
    private void sevaData(boolean con){
        data.setCertNo(idEd.getText().toString());
        data.setName(nameEd.getText().toString());
        data.setPhone(phoneEd.getText().toString());
        data.setEmail(emailEd.getText().toString());
        data.setStreet(streetEd.getText().toString());
        data.setCity(cityEd.getText().toString());
        data.setZipCode(zipEd.getText().toString());
        data.setProvince(stateEd.getText().toString());
        data.setRoomNo(aptEd.getText().toString());
        if (TextUtils.isEmpty(data.getGender())){
            data.setGender("1");
        }
        if (con) {
            OkHttpUtils
                    .postString()
                    .url(Constant.GET_MERCHANT_SAVE_CERT)
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
                                if (AppUtile.setCode(s,YourIdActivity.this)) {
                                    YumApplication.getInstance().getpYourId().setCertNo("");
                                    YumApplication.getInstance().getpYourId().setCertType("");
                                    YumApplication.getInstance().getpYourId().setCertUrl("");
                                    YumApplication.getInstance().getpYourId().setEmail("");
                                    YumApplication.getInstance().getpYourId().setGender("");
                                    YumApplication.getInstance().getpYourId().setUserId("");
                                    YumApplication.getInstance().getpYourId().setName("");
                                    YumApplication.getInstance().getpYourId().setPhone("");
                                    YumApplication.getInstance().getpYourId().setCity("");
                                    YumApplication.getInstance().getpYourId().setProvince("");
                                    YumApplication.getInstance().getpYourId().setOkCode("");
                                    YumApplication.getInstance().getpYourId().setRoomNo("");
                                    YumApplication.getInstance().getpYourId().setStreet("");
                                    YumApplication.getInstance().getpYourId().setZipCode("");
                                    YumApplication.getInstance().getpYourId().setCountry("");
                                    YumApplication.getInstance().getpYourId().setCountryId("1");
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            }
                        }
                    });
        }else{
            OkHttpUtils
                    .postString()
                    .url(Constant.GET_MERCHANT_UPDATE_CERT)
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
                                if (AppUtile.setCode(s,YourIdActivity.this)) {
                                    YumApplication.getInstance().getpYourId().setCertNo("");
                                    YumApplication.getInstance().getpYourId().setCertType("");
                                    YumApplication.getInstance().getpYourId().setCertUrl("");
                                    YumApplication.getInstance().getpYourId().setEmail("");
                                    YumApplication.getInstance().getpYourId().setGender("");
                                    YumApplication.getInstance().getpYourId().setUserId("");
                                    YumApplication.getInstance().getpYourId().setName("");
                                    YumApplication.getInstance().getpYourId().setPhone("");
                                    YumApplication.getInstance().getpYourId().setCity("");
                                    YumApplication.getInstance().getpYourId().setProvince("");
                                    YumApplication.getInstance().getpYourId().setOkCode("");
                                    YumApplication.getInstance().getpYourId().setRoomNo("");
                                    YumApplication.getInstance().getpYourId().setStreet("");
                                    YumApplication.getInstance().getpYourId().setZipCode("");
                                    YumApplication.getInstance().getpYourId().setCountry("");
                                    YumApplication.getInstance().getpYourId().setCountryId("1");
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
                YumApplication.getInstance().getpYourId().setCertNo(idEd.getText().toString());
                YumApplication.getInstance().getpYourId().setName(nameEd.getText().toString());
                YumApplication.getInstance().getpYourId().setPhone(phoneEd.getText().toString());
                YumApplication.getInstance().getpYourId().setEmail(emailEd.getText().toString());
                YumApplication.getInstance().getpYourId().setStreet(streetEd.getText().toString());
                YumApplication.getInstance().getpYourId().setCity(cityEd.getText().toString());
                YumApplication.getInstance().getpYourId().setZipCode(zipEd.getText().toString());
                YumApplication.getInstance().getpYourId().setProvince(stateEd.getText().toString());
                YumApplication.getInstance().getpYourId().setRoomNo(aptEd.getText().toString());
                YumApplication.getInstance().getpYourId().setCertUrl(data.getCertUrl());
                if (TextUtils.isEmpty(data.getGender())){
                    YumApplication.getInstance().getpYourId().setGender("1");
                }
                setResult(RESULT_OK);
                finish();
//                showLoading();
//                if (!TextUtils.isEmpty(data.getCertUrl())&&data.getCertUrl().length()>4) {
//                   if (data.getCertUrl().substring(0, 4).equals("http")) {
//                       sevaData(!getIntent().getBooleanExtra("type",false));
//                   } else {
//                       UploadPersonal(data.getCertUrl(),!getIntent().getBooleanExtra("type",false));
//                    }
//                   }else{
//                       sevaData(!getIntent().getBooleanExtra("type",false));
//                   }
            }
        }
        if (resultCode == RESULT_OK){
            if (requestCode == 0){
                if (intent!=null){
                    YumApplication.getInstance().getpYourId().setCountryId(intent.getStringExtra("countryId"));
                    YumApplication.getInstance().getpYourId().setCountry(intent.getStringExtra("countryName"));
                    data.setCountryId(intent.getStringExtra("countryId"));
                    countryEd.setText(intent.getStringExtra("countryName"));
                }
            }else{
                if (intent!=null){
                    YumApplication.getInstance().getpYourId().setStreet(intent.getStringExtra("address"));
                    data.setStreet(intent.getStringExtra("address"));
                    streetEd.setText(intent.getStringExtra("address"));
                }
            }
            Testing();
        }
    }

}
