package com.yum.two_yum.view.my;

import android.graphics.Bitmap;
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
import com.squareup.picasso.Picasso;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.LoginBase;
import com.yum.two_yum.base.input.RegisterInputBase;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.CircleImageView;
import com.yum.two_yum.utile.ContainsEmojiEditText;
import com.yum.two_yum.utile.GetTimeStrCallBack;
import com.yum.two_yum.utile.PicassoImageLoader;
import com.yum.two_yum.utile.pickerview.TimePickerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @author 余先德
 * @data 2018/4/12
 */

public class ModifyInformationActivity extends BaseActivity {

    public static final String TAG = "ModifyInformationActivity";

    @Bind(R.id.hospital_head_img)
    CircleImageView hospitalHeadImg;
    @Bind(R.id.hospital_button)
    RadioButton hospitalButton;
    @Bind(R.id.merchants_button)
    RadioButton merchantsButton;
    @Bind(R.id.sex_rg)
    RadioGroup sexRg;
    @Bind(R.id.name_ed)
    ContainsEmojiEditText nameEd;
    @Bind(R.id.phone_ed)
    TextView phoneEd;
    @Bind(R.id.email_ed)
    ContainsEmojiEditText emailEd;
    @Bind(R.id.save_btn)
    TextView saveBtn;

    private boolean sexType = false;
    boolean  checking = true;
    private String headPath;
    private TimePickerView timePickerView;
    private String bornTime;
    private String dateStr;
    Calendar dateCalendar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_information);
        ButterKnife.bind(this);
        setView();
    }

    @Override
    protected void setView() {
        super.setView();
//        Picasso.get().load(YumApplication.getInstance().getMyInformation().getAvatar()).placeholder(R.mipmap.head_img_being).into(hospitalHeadImg);
//        if (!TextUtils.isEmpty(YumApplication.getInstance().getMyInformation().getGender())&&YumApplication.getInstance().getMyInformation().getGender().equals("0")){
//            hospitalButton.setChecked(true);
//            merchantsButton.setChecked(false);
//            sexType = true;
//        }
//        String string = YumApplication.getInstance().getMyInformation().getBornTime();
//        dateCalendar = Calendar.getInstance();
//        String year  = "",month = "",day ="";
//        if (!TextUtils.isEmpty(string)){
//            String[] sourceStrArray = string.split("-");
//            if (sourceStrArray.length>2){
//                year = sourceStrArray[0];
//                month = sourceStrArray[1];
//                String[] dayArray = sourceStrArray[2].split(" ");
//                if (dayArray.length>1){
//                    day = dayArray[0];
//                }
//                dateCalendar.set(Integer.valueOf(year),Integer.valueOf(month)-1,Integer.valueOf(day));
//                if ( AppUtile.getLanguage().equals("zh-HK")||AppUtile.getLanguage().equals("zh-CN")){
//                    phoneEd.setText(year+"年"+month+"月"+day+"日");
//                }else{
//                    phoneEd.setText(month+"/"+day+"/"+year);
//                }
//                bornTime = day+"/"+month+"/"+year;
//            }
//
//
//        }
//
//       // bornTime = YumApplication.getInstance().getMyInformation().getBornTime();
//        nameEd.setText(YumApplication.getInstance().getMyInformation().getUsername());
//        //phoneEd.setText(YumApplication.getInstance().getMyInformation().getBornTime());
//        if (!TextUtils.isEmpty(YumApplication.getInstance().getMyInformation().getEmail())){
//            emailEd.setText(YumApplication.getInstance().getMyInformation().getEmail());
//            emailEd.setCursorVisible(false);
//            emailEd.setFocusable(false);
//            emailEd.setFocusableInTouchMode(false);
//        }
        getUserInfo();

        sexRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.hospital_button://女
                        sexType = true;
                        break;
                    case R.id.merchants_button://男
                        sexType = false;
                        break;

                }
            }
        });
        nameEd.addTextChangedListener(new MyTextWatcher());

//        timePickerView = AppUtile.initBirthdayPicker(dateCalendar,this, timePickerView, getResources().getString(R.string.BIRTHDATE), new GetTimeStrCallBack() {
//            @Override
//            public void getStr(Date date, View v) {
//                bornTime = AppUtile.getTime(date," yyyy-MM-dd HH-mm-ss");
//                if (AppUtile.getLanguage().equals("zh-HK")||AppUtile.getLanguage().equals("zh-CN")){
//                    phoneEd.setText(AppUtile.getTime(date,"YYYY年MM月dd日"));
//
//                }else{
//                    phoneEd.setText(AppUtile.getTime(date,"dd/MM/YYYY"));
//                }
//
//                dateStr = AppUtile.getTime(date,"dd/MM/YYYY");
//                //phoneEd.setText(AppUtile.getTime(date,"YYYY"+getString(R.string.YEAR)+"MM"+getString(R.string.MONTH)+"dd"+getString(R.string.DAY)));
//               // checkingData();
//            }
//        });
    }
    public void getUserInfo(){
        OkHttpUtils
                .get()
                .url(Constant.GET_USER_INFO)
                .addHeader("Accept-Language", AppUtile.getLanguage())
                .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        //System.out.println("e = "+e.getMessage());;
                        if (AppUtile.getNetWork(e.getMessage())){
                            showMsg(getString(R.string.NETERROR));
                        }
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        if (!TextUtils.isEmpty(s)) {
                            if (AppUtile.setCode(s,ModifyInformationActivity.this)) {
                                LoginBase loginBase = JSON.parseObject(s, LoginBase.class);
                                Picasso.get().load(Constant.SHOW_IMG+loginBase.getData().getAvatar()).placeholder(R.mipmap.head_img_being).into(hospitalHeadImg);
                                if (!TextUtils.isEmpty(loginBase.getData().getGender())&&loginBase.getData().getGender().equals("0")){
                                    hospitalButton.setChecked(true);
                                    merchantsButton.setChecked(false);
                                    sexType = true;
                                }
                                String string = loginBase.getData().getBornTime();
                                dateCalendar = Calendar.getInstance();
                                String year  = "",month = "",day ="";
                                if (!TextUtils.isEmpty(string)){
                                    String[] sourceStrArray = string.split("-");
                                    if (sourceStrArray.length>2){
                                        year = sourceStrArray[0];
                                        month = sourceStrArray[1];
                                        String[] dayArray = sourceStrArray[2].split(" ");
                                        if (dayArray.length>1){
                                            day = dayArray[0];
                                        }
                                        dateCalendar.set(Integer.valueOf(year),Integer.valueOf(month)-1,Integer.valueOf(day));
                                        if ( AppUtile.getLanguage().equals("zh-HK")||AppUtile.getLanguage().equals("zh-CN")){
                                            phoneEd.setText(year+"年"+month+"月"+day+"日");
                                        }else{
                                            phoneEd.setText(month+"/"+day+"/"+year);
                                        }
                                        bornTime = day+"/"+month+"/"+year;
                                    }


                                }
                                nameEd.setText(loginBase.getData().getUsername());
                                if (!TextUtils.isEmpty(loginBase.getData().getEmail())){
                                    emailEd.setText(loginBase.getData().getEmail());
                                    emailEd.setCursorVisible(false);
                                    emailEd.setFocusable(false);
                                    emailEd.setFocusableInTouchMode(false);
                                }
                                YumApplication.getInstance().getMyInformation().setAvatar(Constant.SHOW_IMG+loginBase.getData().getAvatar());
                                YumApplication.getInstance().getMyInformation().setBornTime(loginBase.getData().getBornTime());
                                YumApplication.getInstance().getMyInformation().setCurrentType(loginBase.getData().getCurrentType());
                                YumApplication.getInstance().getMyInformation().setEmail(loginBase.getData().getEmail());
                                YumApplication.getInstance().getMyInformation().setGender(loginBase.getData().getGender());
                                YumApplication.getInstance().getMyInformation().setType(loginBase.getData().getType());
                                YumApplication.getInstance().getMyInformation().setUid(loginBase.getData().getId());
                                YumApplication.getInstance().getMyInformation().setUsername(loginBase.getData().getUsername());
                            }
                        }
                    }
                });
    }
    private class MyTextWatcher implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
          //  checkingData();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

//    private void checkingData(){
//        String name = nameEd.getText().toString();
//        String birthday = phoneEd.getText().toString();
//        if (!TextUtils.isEmpty(name)){
//            if (!TextUtils.isEmpty(birthday)){
//                checking =true;
//                saveBtn.setTextColor(getResources().getColor(R.color.color_FF484848));
////                if (contype){
////                    saveBtn.setTextColor(getResources().getColor(R.color.color_FF484848));
////                }else{
////                    saveBtn.setTextColor(getResources().getColor(R.color.color_bababa));
////                }
//            }else{
//                checking = false;
//              //  saveBtn.setTextColor(getResources().getColor(R.color.color_bababa));
//            }
//        }else {
//            checking = false;
//           // saveBtn.setTextColor(getResources().getColor(R.color.color_bababa));
//        }
//    }

    @OnClick({R.id.del_btn, R.id.save_btn, R.id.hospital_head_img,R.id.birthdate_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.birthdate_btn:
                timePickerView = AppUtile.initBirthdayPicker(dateCalendar,this, timePickerView, getResources().getString(R.string.BIRTHDATE), new GetTimeStrCallBack() {
                    @Override
                    public void getStr(Date date, View v) {
                        bornTime = AppUtile.getTime(date," yyyy-MM-dd HH-mm-ss");
                        if (AppUtile.getLanguage().equals("zh-HK")||AppUtile.getLanguage().equals("zh-CN")){
                            phoneEd.setText(AppUtile.getTime(date,"YYYY年MM月dd日"));

                        }else{
                            phoneEd.setText(AppUtile.getTime(date,"MM/dd/YYYY"));
                        }

                        dateStr = AppUtile.getTime(date,"dd/MM/YYYY");
                        Calendar c = Calendar.getInstance();
                        c.setTime(date);

                        dateCalendar.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
                        //phoneEd.setText(AppUtile.getTime(date,"YYYY"+getString(R.string.YEAR)+"MM"+getString(R.string.MONTH)+"dd"+getString(R.string.DAY)));
                        // checkingData();
                    }
                });
                timePickerView.show();
                break;
            case R.id.del_btn:
                finish();
                break;
            case R.id.save_btn://保存
                //if (checking){
                    showLoading();
                    if (!TextUtils.isEmpty(headPath)){
                        UploadPersonal(headPath);
                    }else{
                        modifyMessage(null);
                    }
                //}

                break;
            case R.id.hospital_head_img://头像
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
                break;
        }
    }
    private void UploadPersonal(String picStr){
        showLoading();
        OkHttpUtils
                .post()
                .url(Constant.FILEINFO_UPLOADPERSONAL)
                .addFile("files",AppUtile.getTime()+".png",new File(picStr))
                // .file(new File(picStr))
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
                        // System.out.println("s = " + s);
                        if (AppUtile.setCode(s,ModifyInformationActivity.this)) {
                            RegisterInputBase inputBase = JSON.parseObject(s, RegisterInputBase.class);
                            if (inputBase.getCode().equals(Constant.SUCCESSCON) && inputBase.getData().getFileRespResults() != null && inputBase.getData().getFileRespResults().size() > 0) {
                                modifyMessage(inputBase.getData().getFileRespResults().get(0).getUrl());
                            }
                        }
                        // inputBase.setUrl(uploadFlieBase.getData());
                        //appHttpUtile.postJson(Constant.USER_REGISTER,JSON.toJSONString(inputBase));
                    }
                });
    }
    private void modifyMessage(String headImg){
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("userId",YumApplication.getInstance().getMyInformation().getUid());

        if (!TextUtils.isEmpty(headImg)){
            paramsMap.put("avatar",headImg);
        }
        if (!TextUtils.isEmpty(emailEd.getText().toString())){
            if (TextUtils.isEmpty(YumApplication.getInstance().getMyInformation().getEmail())){
                paramsMap.put("email",emailEd.getText().toString());
            }else if (!emailEd.getText().toString().equals(YumApplication.getInstance().getMyInformation().getEmail())){
                paramsMap.put("email",emailEd.getText().toString());
            }
        }else{
            if (!TextUtils.isEmpty(YumApplication.getInstance().getMyInformation().getEmail())) {
                paramsMap.put("email", YumApplication.getInstance().getMyInformation().getEmail());
            }
        }
        if (!TextUtils.isEmpty(nameEd.getText().toString())){
            if (TextUtils.isEmpty(YumApplication.getInstance().getMyInformation().getUsername())){
                paramsMap.put("username",nameEd.getText().toString());
            }else if (!nameEd.getText().toString().equals(YumApplication.getInstance().getMyInformation().getUsername())){
                paramsMap.put("username",nameEd.getText().toString());
            }
        }

        String sex ;
        if (sexType){
            sex = "0";
        }else{
            sex = "1";
        }

        if (TextUtils.isEmpty(YumApplication.getInstance().getMyInformation().getGender())||(!TextUtils.isEmpty(YumApplication.getInstance().getMyInformation().getGender())&&!YumApplication.getInstance().getMyInformation().getGender().equals(sex))){
            paramsMap.put("gender",sex);
        }
        if (!TextUtils.isEmpty(dateStr)){
            if (TextUtils.isEmpty(bornTime)){
                paramsMap.put("bornTime",dateStr);
            } else if (!bornTime.equals(dateStr)) {
                paramsMap.put("bornTime",dateStr);
            }
        }else{
            if (!TextUtils.isEmpty(bornTime)){
                paramsMap.put("bornTime",bornTime);
            }
        }

        OkHttpUtils
                .post()
                .addHeader("Accept-Language", AppUtile.getLanguage())
                .addHeader("YUM-AUTH-TOKEN",YumApplication.getInstance().getMyInformation().getToken())
                .url(Constant.USER_UPDATE)
                .params(paramsMap)
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
                        System.out.println("s = " + s);
                        if (!TextUtils.isEmpty(s)){

                            if (AppUtile.setCode(s,ModifyInformationActivity.this)){
                                LoginBase loginBase = JSON.parseObject(s,LoginBase.class);
                                YumApplication.getInstance().getMyInformation().setAvatar(loginBase.getData().getAvatar());
                                YumApplication.getInstance().getMyInformation().setBornTime(loginBase.getData().getBornTime());
                                YumApplication.getInstance().getMyInformation().setCurrentType(loginBase.getData().getCurrentType());
                                YumApplication.getInstance().getMyInformation().setEmail(loginBase.getData().getEmail());
                                YumApplication.getInstance().getMyInformation().setGender(loginBase.getData().getGender());
                                YumApplication.getInstance().getMyInformation().setType(loginBase.getData().getType());
                                YumApplication.getInstance().getMyInformation().setUid(loginBase.getData().getId());
                                YumApplication.getInstance().getMyInformation().setUsername(loginBase.getData().getUsername());
                                // showMsg(loginBase.getMsg());
                                setResult(RESULT_OK);
                                finish();
                            }
                        }
                    }
                });
    }
    IHandlerCallBack iHandlerCallBack = new IHandlerCallBack() {
        @Override
        public void onStart() {
            System.out.println("onStart: 开启");
        }

        @Override
        public void onSuccess(List<String> photoList) {
            System.out.println("onSuccess: 返回数据");
            for (String s : photoList) {
                headPath = s;
                Bitmap bitmap = AppUtile.getSmallBitmap(headPath);
                hospitalHeadImg.setImageBitmap(bitmap);
            }
        }

        @Override
        public void onCancel() {
            System.out.println("onCancel: 取消");
        }

        @Override
        public void onFinish() {

        }

        @Override
        public void onError() {
            System.out.println("onError: 出错");
        }
    };
}
