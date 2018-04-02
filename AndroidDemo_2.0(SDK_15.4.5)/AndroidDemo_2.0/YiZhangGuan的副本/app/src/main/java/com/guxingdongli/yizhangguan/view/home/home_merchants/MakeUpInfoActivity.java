package com.guxingdongli.yizhangguan.view.home.home_merchants;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.ab.util.AbDialogUtil;
import com.alibaba.fastjson.JSON;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.MakeUpInfoAdapter;
import com.guxingdongli.yizhangguan.controller.adapter.callback.MakeUpInfoCallBack;
import com.guxingdongli.yizhangguan.model.StoraeHospitalDetailsBase;
import com.guxingdongli.yizhangguan.util.AppUtile;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.GetTimeStrCallBack;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.guxingdongli.yizhangguan.view.home.dialog.PromptDialog;
import com.yuxiaolong.yuxiandelibrary.NoSlideListView;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;
import com.yuxiaolong.yuxiandelibrary.pickerview.TimePickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 补录订购器材信息
 * Created by jackmask on 2018/3/8.
 */

public class MakeUpInfoActivity extends YiZhangGuanActivity {

    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.equipment_list)
    NoSlideListView equipmentList;

    private MakeUpInfoAdapter adapter;
    private List<String> dataList = new ArrayList<>();
    private TimePickerView pvTime;
    private String gid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_up_info);
        ButterKnife.bind(this);
        setView();
    }

    private void setView() {
        for (int i = 0 ; i < 5 ; i ++){
            dataList.add("");
        }
        gid = getIntent().getStringExtra("guid");
        titleText.setText("补录订购器材信息");
        //adapter = new MakeUpInfoAdapter(dataList);
        adapter.setCallBack(new MakeUpInfoCallBack() {
            @Override
            public void selectItem(final int position,final TextView view) {
                pvTime = AppUtile.initTimePicker(MakeUpInfoActivity.this,pvTime,"请选择有效期",new GetTimeStrCallBack(){

                    @Override
                    public void getStr(Date date, View v) {
                        view.setText(AppUtile.getTime(date));
                    }
                });

                pvTime.show();
            }
        });
        equipmentList.setAdapter(adapter);
        RequestBody formBody = new  FormBody.Builder()
                .add("guid", gid)
                .build();
        HttpUtile httpUtile = new HttpUtile(this, Constant.DOMAIN_NAME + Constant.GETPURCHASEORDER, formBody, new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {
                StoraeHospitalDetailsBase dataBase = JSON.parseObject(returnStr,StoraeHospitalDetailsBase.class);

                Message message = new Message();
                message.what = 0 ;
                message.obj = dataBase;

               // mHandler.sendMessage(message);
            }

            @Override
            public void getReturnStrFailure(String returnStr) {

            }

            @Override
            public void getErrorStr(String errorStr) {

            }
        },false);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0){
            if (resultCode == 1001){
                finish();
            }
        }
    }

    @OnClick({R.id.return_btn, R.id.cancel_btn, R.id.enter_btn})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {

            case R.id.return_btn:
                finish();
                break;
            case R.id.cancel_btn:
                intent = new Intent(this, PromptDialog.class);
                intent.putExtra("content","MakeUpInfo");
                startActivityForResult(intent,0);
                break;
            case R.id.enter_btn:

                break;
        }
    }
}
