package com.guxingdongli.yizhangguan.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.util.AbToastUtil;
import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.YiZhangGuanApplication;
import com.guxingdongli.yizhangguan.model.CityBase;
import com.guxingdongli.yizhangguan.model.ProvinceBean;
import com.guxingdongli.yizhangguan.util.AppUtile;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.GetPickerStrCallBack;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;
import com.yuxiaolong.yuxiandelibrary.pickerview.OptionsPickerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 注册
 * Created by jackmask on 2018/2/28.
 */

public class RegisteredActivity extends YiZhangGuanActivity {

    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.right_tv_btn)
    TextView rightTvBtn;
    @Bind(R.id.right_img_btn)
    ImageView rightImgBtn;
    @Bind(R.id.title_layout)
    LinearLayout titleLayout;
    @Bind(R.id.radioGroup2)
    RadioGroup radioGroup2;
    @Bind(R.id.imageView2)
    ImageView imageView2;
    @Bind(R.id.hospitol_name_btn)
    RelativeLayout hospitolNameBtn;
    @Bind(R.id.hospitol_name)
    EditText hospitolName;
    @Bind(R.id.hospital)
    LinearLayout hospital;
    @Bind(R.id.merchants_company_name)
    EditText merchantsCompanyName;
    @Bind(R.id.merchants_name)
    EditText merchantsName;
    @Bind(R.id.merchants)
    LinearLayout merchants;
    @Bind(R.id.password_tv)
    EditText passwordTv;
    @Bind(R.id.again_password_tv)
    EditText againPasswordTv;
    @Bind(R.id.phone_tv)
    EditText phoneTv;
    @Bind(R.id.agree_btn)
    CheckBox agreeBtn;
    @Bind(R.id.protocol_btn)
    TextView protocolBtn;
    @Bind(R.id.registered_btn)
    TextView registeredBtn;
    @Bind(R.id.modify_btn)
    TextView modifyBtn;
    @Bind(R.id.addres)
    TextView addres;
    @Bind(R.id.code_tv)
    EditText codeTv;
    @Bind(R.id.get_code_btn)
    TextView getCodeBtn;
    @Bind(R.id.hospital_tv)
    TextView hospitalTv;

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;

    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private OptionsPickerView pvOptions;
    private boolean loactionCon = true;
    boolean registeredTheWay = true;//注册方式，true是医院用户，false是供应商
    private TimeCount time;
    private boolean tiemCon = true;
    private List<CityBase> cityLists;
    private String cityStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        ButterKnife.bind(this);
        setAnimCon(true);
        setView();

        initOptionData();
        pvOptions = AppUtile.initOptionsPicker(this, pvOptions, options1Items, options2Items, options3Items, null, new GetPickerStrCallBack() {
            @Override
            public void getStr(String returnStr,long id) {
                addres.setText(returnStr);
            }
        });
    }

    private void setView() {
        System.out.println("YiZhangGuanApplication.getInstance().getProvince() = " + YiZhangGuanApplication.getInstance().getProvince());
        addres.setText(YiZhangGuanApplication.getInstance().getProvince()+" "+YiZhangGuanApplication.getInstance().getCounty() + " " + YiZhangGuanApplication.getInstance().getCity());
        cityStr = AppUtile.readAssetsCityTxt(this);
        titleText.setText("注册");
        time = new TimeCount(60000, 1000);
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) RegisteredActivity.this.findViewById(radioGroup2.getCheckedRadioButtonId());
                if (rb.getText().toString().equals("医院用户")) {
                    hospital.setVisibility(View.VISIBLE);
                    merchants.setVisibility(View.GONE);
                    registeredTheWay = true;
                } else {
                    hospital.setVisibility(View.GONE);
                    merchants.setVisibility(View.VISIBLE);
                    registeredTheWay = false;
                }
            }
        });
    }



    @OnClick({R.id.return_btn, R.id.protocol_btn, R.id.registered_btn,
            R.id.modify_btn, R.id.hospitol_name_btn, R.id.get_code_btn})
    public void onViewClicked(View view) {
        Intent intent;
        HttpUtile httpUtile;
        switch (view.getId()) {
            case R.id.hospitol_name_btn:
                intent = new Intent(this, SelectAddressActivity.class);
                intent.putExtra("hospitalName",addres.getText().toString());
                startActivityForResult(intent,11);
                break;
            case R.id.return_btn://返回
                finish();
                break;
            case R.id.protocol_btn://协议
                 intent = new Intent(this,ProtocolActivity.class);
                startActivity(intent);
                break;
            case R.id.get_code_btn://发送验证码
                if (tiemCon) {
                    RequestBody formBody = new  FormBody.Builder()
                            .add("Id", "0")
                            .add("ActionType", "注册验证")
                            .add("MobilePhone", phoneTv.getText().toString())
                            .build();
                 httpUtile = new HttpUtile(this, Constant.DOMAIN_NAME + Constant.GETVERCODE, formBody, new HttpUtileCallBack() {
                    @Override
                    public void getReturnStr(String returnStr) {
                        time.start();
                        //AbToastUtil.showToast(RegisteredActivity.this,"验证码已发送");
                    }
                     @Override
                     public void getReturnStrFailure(String returnStr) {
                     }
                    @Override
                    public void getErrorStr(String errorStr) {

                    }
                },false);
                }
                break;
            case R.id.registered_btn://注册
                String json = "{";
                if (registeredTheWay) {
                    if (AppUtile.checkNull(RegisteredActivity.this, hospitolName.getText().toString().trim(), "工号", true)) {
                        json += "\"userType\":1,\"name\":\"" + hospitalTv.getText().toString().trim() + "\",\"id\":0,\"loginName\":\""+hospitolName.getText().toString().trim()+"\",";
                    }
                } else {
                    if (AppUtile.checkNull(RegisteredActivity.this, merchantsCompanyName.getText().toString().trim(), "公司名称", true)) {
                        if (AppUtile.checkNull(RegisteredActivity.this, merchantsName.getText().toString().trim(), "用户名", true)) {
                            json += "\"userType\":2,\"name\":\"" + merchantsCompanyName.getText().toString().trim() + "\",\"id\":0,\"loginName\":\""+merchantsName.getText().toString().trim()+"\",";
                        }
                    }
                }

                if (AppUtile.checkPassword(RegisteredActivity.this, passwordTv.getText().toString().trim())) {
                    if (AppUtile.checkAgainPassword(RegisteredActivity.this, againPasswordTv.getText().toString().trim(), passwordTv.getText().toString().trim())) {
                        if (AppUtile.checkPhone(RegisteredActivity.this, phoneTv.getText().toString().trim())) {
                            json+="\"loginPassword\":\""+passwordTv.getText().toString().trim()+"\",\"loginPassword1\":\""+againPasswordTv.getText().toString().trim()+"\",\"mobilePhone\":\""+
                                    phoneTv.getText().toString().trim()+"\",\"verificationCode\":\""+codeTv.getText().toString().trim()+"\",\"isAgreeAgreement\":\""+agreeBtn.isChecked()+"\"}";
                            httpUtile = new HttpUtile(this, Constant.DOMAIN_NAME + Constant.REGISTER, json, new HttpUtileCallBack() {
                                @Override
                                public void getReturnStr(String returnStr) {
                                    Looper.prepare();
                                    if (registeredTheWay) {
                                        YiZhangGuanApplication.getInstance().getMyInfo().setToken_hospital(hospitalTv.getText().toString().trim());
                                        YiZhangGuanApplication.getInstance().getMyInfo().setHospitalLoginName(hospitolName.getText().toString().trim());
                                    }else{
                                        YiZhangGuanApplication.getInstance().getMyInfo().setSupplierLoginName(merchantsName.getText().toString().trim());
                                    }
                                    setResult(RESULT_OK);
                                    finish();
                                    Toast.makeText(RegisteredActivity.this,"注册成功",Toast.LENGTH_LONG).show();
                                    Looper.loop();


                                    //AbToastUtil.showToast(RegisteredActivity.this,"验证码已发送");
                                }
                                @Override
                                public void getReturnStrFailure(String returnStr) {
                                    Looper.prepare();
                                    if (!TextUtils.isEmpty(returnStr))
                                    Toast.makeText(RegisteredActivity.this,returnStr,Toast.LENGTH_LONG).show();
                                    Looper.loop();
                                }
                                @Override
                                public void getErrorStr(String errorStr) {

                                }
                            },false);
                        }
                    }
                }

                break;
            case R.id.modify_btn://修改
                pvOptions.show();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10&&resultCode == 101){

        }else if (requestCode == 11&&resultCode == 1001){
            if (data!=null)
                hospitalTv.setText(data.getStringExtra("hospital"));
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    /**
     * 获取测试数据
     */
    private void initOptionData() {

        /**
         * 注意：如果是添加JavaBean实体数据，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        if (!TextUtils.isEmpty(cityStr)) {
            cityLists = JSON.parseArray(cityStr, CityBase.class);
        } else {
            return;
        }

        for (int i = 0; i < cityLists.size(); i++) {
            options1Items.add(new ProvinceBean(i, cityLists.get(i).getName(), "描述部分", "其他数据"));
            ArrayList<String> options2Items_01 = new ArrayList<>();
            ArrayList<ArrayList<String>> options3Items_01 = new ArrayList<ArrayList<String>>();
            for (int c = 0; c < cityLists.get(i).getCity().size(); c++) {
                options2Items_01.add(cityLists.get(i).getCity().get(c).getName());
                ArrayList<String> options3Items_01_01 = new ArrayList<>();
                for (int d = 0; d < cityLists.get(i).getCity().get(c).getArea().size(); d++) {
                    options3Items_01_01.add(cityLists.get(i).getCity().get(c).getArea().get(d));
                }
                options3Items_01.add(options3Items_01_01);
            }
            options2Items.add(options2Items_01);
            options3Items.add(options3Items_01);
        }

    }

    /**
     * 定时器类
     */
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            //verificationBtn.setClickable(false);
            tiemCon = false;
            getCodeBtn.setText(millisUntilFinished / 1000 + "秒后重发");
            //verificationBtn.setTextColor(Color.parseColor("#999999"));
            //verificationBtn.setBackgroundResource(R.drawable.area_stroke_white_gred_btn);
        }

        @Override
        public void onFinish() {
            tiemCon = true;
            getCodeBtn.setText("重新获取");
            // verificationBtn.setClickable(true);
            // verificationBtn.setTextColor(Color.parseColor("#2177d5"));
            // verificationBtn.setBackgroundResource(R.drawable.area_stroke_white_btn);

        }
    }

}
