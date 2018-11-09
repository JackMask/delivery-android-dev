package com.yum.two_yum.view.merchants.menu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.squareup.picasso.Picasso;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.BaseReturn;
import com.yum.two_yum.base.GetMerchantMenuListBase;
import com.yum.two_yum.base.input.AddEditMenuInput;
import com.yum.two_yum.base.input.RegisterInputBase;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.ContainsEmojiEditText;
import com.yum.two_yum.utile.DeleteFileUtil;
import com.yum.two_yum.utile.PicassoImageLoader;
import com.yum.two_yum.view.client.clientorder.AddToAddressActivity;
import com.yum.two_yum.view.my.ModifyInformationActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * @author 余先德
 * @data 2018/4/14
 */

public class AddEditMenuActivity extends BaseActivity {

    @Bind(R.id.create_account_btn)
    TextView createAccountBtn;
    @Bind(R.id.pice_btn)
    ImageView piceBtn;
    @Bind(R.id.food_name_ed)
    ContainsEmojiEditText foodNameEd;
    @Bind(R.id.account_ed)
    ContainsEmojiEditText accountEd;
    @Bind(R.id.price_ed)
    ContainsEmojiEditText priceEd;
    @Bind(R.id.num_ed)
    ContainsEmojiEditText numEd;
    @Bind(R.id.save_btn)
    TextView saveBtn;
    @Bind(R.id.add_pic_btn)
    LinearLayout addPicBtn;
    @Bind(R.id.no_pic_layout)
    LinearLayout noPicLayout;
    @Bind(R.id.cover_check)
    CheckBox coverCheck;
    @Bind(R.id.del_btn)
    ImageView delBtn;

    private String headPath;
    private boolean clickType = false;
    private int ifCover = 0;
    private GetMerchantMenuListBase.DataBean.MerchantMenuRespResultsBean data;

    boolean con = false;
    private String filePath;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_menu);
        ButterKnife.bind(this);
        setView();
    }

    @Override
    protected void setView() {
        super.setView();
        data = (GetMerchantMenuListBase.DataBean.MerchantMenuRespResultsBean)getIntent().getSerializableExtra("data");
        if (data!=null){
            createAccountBtn.setText(getString(R.string.MENUEDIT));
            delBtn.setImageResource(R.mipmap.back_img);
            if (!TextUtils.isEmpty(data.getCover())){
                noPicLayout.setVisibility(View.GONE);
                piceBtn.setVisibility(View.VISIBLE);

                headPath = data.getCover();
                Picasso.get().load(data.getCover()).placeholder(R.mipmap.timg).into(piceBtn);
            }
            foodNameEd.setText(data.getName());
            accountEd.setText(data.getDescribe());
            priceEd.setText(AppUtile.getPrice(data.getPrice()+""));
            numEd.setText(data.getNumber()+"");
            if (data.getIfCover()==1){
                coverCheck.setChecked(true);
                ifCover = 1;
            }else{
                ifCover = 0;
            }
            clickType = true;
            saveBtn.setBackgroundResource(R.drawable.button_red_bright);
        }

        foodNameEd.addTextChangedListener(new textChange());
        accountEd.addTextChangedListener(new textChange());
        priceEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        priceEd.setText(s);
                        priceEd.setSelection(priceEd.getText().toString().length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    priceEd.setText(s);
                    priceEd.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        priceEd.setText(s.subSequence(0, 1));
                        priceEd.setSelection(1);
                        return;
                    }
                }
                if (getCount(s.toString(),".")==2){
                    priceEd.setText(s.subSequence(0, s.toString().length()-1));
                    priceEd.setSelection(s.toString().length()-1);
                    return;
                }
                changeType();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        numEd.addTextChangedListener(new textChange());
        coverCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    ifCover = 1;
                }else{
                    ifCover = 0;
                }
            }
        });
    }
    public static int getCount(String str,String scan){
        int count = 0;
        int index = 0;
        while(((index = str.indexOf(scan)) != -1)){
            //想个办法截取字符串中查找字符,并将查找当前匹配字符之后的字符串重新
            //赋值给字符串
            str = str.substring(index+1);
            count++;
        }
        return count;
    }
    private class textChange implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            changeType();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private void changeType(){
        String foodNameEdStr = foodNameEd.getText().toString();
        String accountEdStr = accountEd.getText().toString();
        String priceEdStr = AppUtile.getPrice(!TextUtils.isEmpty(priceEd.getText().toString())?priceEd.getText().toString():"");
        String numEdStr = numEd.getText().toString();
        if (!TextUtils.isEmpty(headPath)){
            if (!TextUtils.isEmpty(foodNameEdStr)){
                if (!TextUtils.isEmpty(accountEdStr)){
                    if (!TextUtils.isEmpty(priceEdStr)&&!priceEdStr.equals("0.00")){
                        if (!TextUtils.isEmpty(numEdStr)){
                            clickType = true;
                            saveBtn.setBackgroundResource(R.drawable.button_red_bright);
                        }else{
                            clickType = true;
                            saveBtn.setBackgroundResource(R.drawable.button_red);
                        }
                    }else{
                        clickType = true;
                        saveBtn.setBackgroundResource(R.drawable.button_red);
                    }
                }else{
                    clickType = true;
                    saveBtn.setBackgroundResource(R.drawable.button_red);
                }
            }else{
                clickType = true;
                saveBtn.setBackgroundResource(R.drawable.button_red);
            }
        }else{
            clickType = true;
            saveBtn.setBackgroundResource(R.drawable.button_red);
        }
    }

    @OnClick({R.id.del_btn, R.id.save_btn,R.id.add_pic_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_pic_btn:
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
            case R.id.del_btn:
                finish();
                if (getIntent().getBooleanExtra("type",false)) {
                    overridePendingTransition(R.anim.activity_open, R.anim.in);
                }
                break;

            case R.id.save_btn:
                if (clickType){
                    if (!TextUtils.isEmpty(headPath)){
                        showLoading();
                        if (headPath.substring(0,4).equals("http")){
                            saveMenu(headPath,false,"");
                        }else {
                            UploadPersonal(headPath,false);
                        }


                    }

                }

                break;
        }
    }
    private void UploadPersonal(final String picStr,final boolean con){
        OkHttpUtils
                .post()
                .url(Constant.FILEINFO_UPLOADPERSONAL)
                .addFile("files", AppUtile.getTime()+".png",new File(picStr))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        // System.out.println("e = " + e.getMessage());
                        if (AppUtile.getNetWork(e.getMessage())){
                            showMsg(getString(R.string.NETERROR));
                        }
                        dismissLoading();
                        //AbDialogUtil.removeDialog(RegisteredActivity.this);
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        // System.out.println("s = " + s);
                        if (AppUtile.setCode(s,AddEditMenuActivity.this)) {
                            RegisterInputBase inputBase = JSON.parseObject(s, RegisterInputBase.class);
                            if (AppUtile.setCode(s, AddEditMenuActivity.this) && inputBase.getData().getFileRespResults() != null && inputBase.getData().getFileRespResults().size() > 0) {
                                // data.setCertUrl(inputBase.getData().getFileRespResults().get(0).getUrl());
                                //System.out.println(inputBase.getData().getFileRespResults().get(0).getUrl());
                                saveMenu(inputBase.getData().getFileRespResults().get(0).getUrl(), con, picStr);

                                // registered(inputBase.getData().getFileRespResults().get(0).getUrl());
                            }
                        }
                        // inputBase.setUrl(uploadFlieBase.getData());
                        //appHttpUtile.postJson(Constant.USER_REGISTER,JSON.toJSONString(inputBase));
                    }
                });

    }


    private void saveMenu(String picAdd,final boolean con ,final String  picStr){
        AddEditMenuInput addEditMenuInput = new AddEditMenuInput();
        addEditMenuInput.setUserId(YumApplication.getInstance().getMyInformation().getUid());
        addEditMenuInput.setDescribe(accountEd.getText().toString());
        addEditMenuInput.setName(foodNameEd.getText().toString());
        addEditMenuInput.setPrice(priceEd.getText().toString());
        addEditMenuInput.setIfCover(ifCover);
        addEditMenuInput.setNumber(numEd.getText().toString());
        addEditMenuInput.setCover(picAdd);
        addEditMenuInput.setState("0");
        if (data!=null){
            addEditMenuInput.setId(data.getId()+"");
            if (data.getState()==1){
                addEditMenuInput.setState("1");
            }else{
                addEditMenuInput.setState("0");
            }
//            if (data.getIfCover()==1){
//                addEditMenuInput.setIfCover(1);
//            }else{
//                addEditMenuInput.setIfCover(0);
//            }
        }

        OkHttpUtils
                .postString()
                .url(Constant.RELEASE_MENU)
                .mediaType(MediaType.parse("application/json"))
                .addHeader("Accept-Language", AppUtile.getLanguage())
                .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                .content(JSON.toJSONString(addEditMenuInput))
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
                    if (!TextUtils.isEmpty(s)){
                        if (AppUtile.setCode(s,AddEditMenuActivity.this)){
                            if (con&&!TextUtils.isEmpty(picStr)){
                                DeleteFileUtil.deleteFile(picStr);
                            }
                            setResult(RESULT_OK);
                            finish();
                            overridePendingTransition(R.anim.activity_open,R.anim.in);
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
            for (String s : photoList) {
                headPath = s;
                noPicLayout.setVisibility(View.GONE);
                piceBtn.setVisibility(View.VISIBLE);
                Bitmap bitmap = AppUtile.getSmallBitmap(headPath);
                piceBtn.setImageBitmap(bitmap);
                changeType();
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
