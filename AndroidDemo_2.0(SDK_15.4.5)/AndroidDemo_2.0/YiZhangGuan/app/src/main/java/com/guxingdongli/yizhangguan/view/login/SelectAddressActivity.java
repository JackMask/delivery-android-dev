package com.guxingdongli.yizhangguan.view.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.util.AbDialogUtil;
import com.ab.util.AbToastUtil;
import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.SelectAddressAdapter;
import com.guxingdongli.yizhangguan.controller.YiZhangGuanApplication;
import com.guxingdongli.yizhangguan.model.SelectAddressBase;
import com.guxingdongli.yizhangguan.model.SelectAddressModel;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.yuxiaolong.yuxiandelibrary.NoSlideListView;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 注册选择地址
 * Created by jackmask on 2018/3/1.
 */

public class SelectAddressActivity extends YiZhangGuanActivity {
    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.hospital_tv)
    EditText hospitalTv;
    @Bind(R.id.filter_list)
    NoSlideListView filterList;
    @Bind(R.id.title_text)
    TextView titleText;


    private String keywords = "";
    private SelectAddressAdapter addressAdapter;

    private List<SelectAddressBase> data = new ArrayList<>();
    private  HttpUtile httpUtile;


    Handler handler = new Handler()
    {
        public void handleMessage(android.os.Message msg) {
            if(msg.what==0) //如果消息是刚才发送的标识
            {
                addressAdapter.notifyDataSetChanged();
            }else if (msg.what ==1){
                AbDialogUtil.removeDialog(SelectAddressActivity.this);
            }
        };
    };

    private String province = YiZhangGuanApplication.getInstance().getProvince();
    private String city = YiZhangGuanApplication.getInstance().getCity();
    private String county = YiZhangGuanApplication.getInstance().getCounty();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectaddress);
        ButterKnife.bind(this);
        setView();
    }

    private void setView() {
        if (!TextUtils.isEmpty( getIntent().getStringExtra("hospitalName"))){
            keywords = getIntent().getStringExtra("hospitalName");
            try {
                String[] arr2 = keywords.split(" ");
                province = arr2[0];
                city = arr2[1];
                county = arr2[2];
            }catch (Exception e){

            }
            hospitalTv.setText(keywords);
        }
        titleText.setText("选择医院");

        hospitalTv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //initData();
                    keywords = hospitalTv.getText().toString().trim();
                    data.clear();
                    RequestBody formBody= new  FormBody.Builder()
                            .add("province", province)
                            .add("city", city)
                            .add("county", county)
                            .add("keyword", keywords)
                            .build();
                    HttpUtile getHttp = new HttpUtile(SelectAddressActivity.this, Constant.DOMAIN_NAME + Constant.GETHOSPITALLIST, formBody, new HttpUtileCallBack() {
                        @Override
                        public void getReturnStr(String returnStr) {
                            SelectAddressModel dataList = JSON.parseObject(returnStr,SelectAddressModel.class);
                            for (int i = 0 ; i < dataList.getData().size();i++){
                                data.add(dataList.getData().get(i));
                            }
                            AbDialogUtil.removeDialog(SelectAddressActivity.this);
                            handler.sendEmptyMessage(0);
                        }

                        @Override
                        public void getErrorStr(String errorStr) {
                            handler.sendEmptyMessage(1);
                        }
                        @Override
                        public void getReturnStrFailure(String returnStr) {
                            handler.sendEmptyMessage(1);
                        }
                    },true);
                    return true;
                }
                return false;
            }
        });
        addressAdapter = new SelectAddressAdapter(data);
        addressAdapter.setAddressActivity(SelectAddressActivity.this);
        filterList.setAdapter(addressAdapter);
        filterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView hospitalName = (TextView) view.findViewById(R.id.name);
                Intent intent = new Intent();
                intent.putExtra("hospital",hospitalName.getText().toString());
                setResult(1001,intent);
                finish();
            }
        });
        setWeb();
    }

    private void setWeb(){

        //initData();
        keywords = hospitalTv.getText().toString().trim();
        data.clear();
        RequestBody formBody= new  FormBody.Builder()
                .add("province", province)
                .add("city", city)
                .add("county", county)
                .build();
        HttpUtile getHttp = new HttpUtile(SelectAddressActivity.this, Constant.DOMAIN_NAME + Constant.GETHOSPITALLIST, formBody, new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {
                SelectAddressModel dataList = JSON.parseObject(returnStr,SelectAddressModel.class);
                for (int i = 0 ; i < dataList.getData().size();i++){
                    data.add(dataList.getData().get(i));
                }
                AbDialogUtil.removeDialog(SelectAddressActivity.this);
                handler.sendEmptyMessage(0);
            }

            @Override
            public void getErrorStr(String errorStr) {
                handler.sendEmptyMessage(1);
            }
            @Override
            public void getReturnStrFailure(String returnStr) {
                Looper.prepare();
                handler.sendEmptyMessage(1);
                Toast.makeText(SelectAddressActivity.this, returnStr, Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        },true);
    }

    public String getKeywords() {
        return keywords;
    }





    @OnClick({R.id.return_btn,R.id.right_tv_btn})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.return_btn:
                finish();
                break;

        }

    }
}
