package com.guxingdongli.yizhangguan.view.home.home_merchants;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.model.AddStatementDataBase;
import com.guxingdongli.yizhangguan.model.PushSelectHospitalBase;
import com.guxingdongli.yizhangguan.model.passvalue.AddStatementValue;
import com.guxingdongli.yizhangguan.util.AppUtile;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.GetTimeStrCallBack;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.guxingdongli.yizhangguan.view.login.LoginActivity;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;
import com.yuxiaolong.yuxiandelibrary.pickerview.TimePickerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;

/**
 * 添加对账单
 * Created by jackmask on 2018/3/9.
 */

public class AddStatementActivity extends YiZhangGuanActivity {

    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.right_tv_btn)
    TextView rightTvBtn;
    @Bind(R.id.start_time_tv)
    TextView startTimeTv;
    @Bind(R.id.end_time_tv)
    TextView endTimeTv;
    @Bind(R.id.supply_hospital_tv)
    TextView supplyHospitalTv;

    private TimePickerView timePickerStart,timePickerEnd;
    private List<PushSelectHospitalBase.DataBean> dataBeans;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) //如果消息是刚才发送的标识
            {
                AddStatementDataBase formBody = (AddStatementDataBase)msg.obj;
                startTimeTv.setText(formBody.getData().substring(0,10));

            }
        }

        ;
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_statement);
        ButterKnife.bind(this);
        setView();
    }

    private void setView(){
        titleText.setText("添加对账单");
        rightTvBtn.setText("保存");
        rightTvBtn.setVisibility(View.VISIBLE);
        rightTvBtn.setTextColor(Color.parseColor("#2ea1fb"));
        timePickerStart =AppUtile.initTimePicker(this,timePickerStart,"请选择开始时间",new GetTimeStrCallBack(){
            @Override
            public void getStr(Date date, View v) {
                startTimeTv.setText(AppUtile.getTime(date).substring(0,10));
            }
        });
        timePickerEnd=AppUtile.initTimePicker(this,timePickerStart,"请选择结束时间",new GetTimeStrCallBack(){
            @Override
            public void getStr(Date date, View v) {
                endTimeTv.setText(AppUtile.getTime(date).substring(0,10));
            }
        });
        getTiem();
    }

   private void getTiem(){
        HttpUtile httpUtile = new HttpUtile(this, Constant.DOMAIN_NAME + Constant.GETLASTTIME, new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {
                AddStatementDataBase dataBase = JSON.parseObject(returnStr,AddStatementDataBase.class);
                Message message = new Message();
                message.what = 0 ;
                message.obj = dataBase;
                handler.sendMessage(message);
            }

            @Override
            public void getReturnStrFailure(String returnStr) {
                Looper.prepare();
                Toast.makeText(AddStatementActivity.this,returnStr,Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void getErrorStr(String errorStr) {

            }
        },false);
   }
   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);
       if (requestCode == 10) {
           if (resultCode == RESULT_OK && data != null) {
               dataBeans = (List<PushSelectHospitalBase.DataBean>) data.getSerializableExtra("datas");
               String names = "";
               for (PushSelectHospitalBase.DataBean item : dataBeans) {
                   names += item.getName() + ",";
               }
               if (names.length() > 1) {
                   names.substring(0, names.length() - 1);
               }
               supplyHospitalTv.setText(names);
           }
       }
   }

    @OnClick({R.id.return_btn, R.id.start_time_btn, R.id.end_time_btn, R.id.supply_hospital_btn
    ,R.id.right_tv_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_btn:
                finish();
                break;
            case R.id.start_time_btn:
                //开始时间
                timePickerStart.show();
                break;
            case R.id.end_time_btn:
                //结束时间
                timePickerEnd.show();
                break;
            case R.id.supply_hospital_btn:
                //供货医院
                Intent intent = new Intent(this, PushSelectHospitalActivity.class);
                startActivityForResult(intent, 10);
                /*Intent intent = new Intent(this,SelectAvailabilityHospitalActivity.class);
                startActivityForResult(intent,0);*/
                break;
            case R.id.right_tv_btn:
                //保存
                AddStatementValue passValue = new AddStatementValue();
                List<Integer> ids = new ArrayList<>();
                if (dataBeans!=null)
                for (PushSelectHospitalBase.DataBean item : dataBeans){
                    ids.add(item.getId());
                }
                passValue.setHospitalId(ids);

                passValue.setBeginTime((startTimeTv.getText().toString())+" 00:00:00");
                passValue.setEndTime(endTimeTv.getText().toString()+" 00:00:00");
                HttpUtile httpUtile = new HttpUtile(this, Constant.DOMAIN_NAME + Constant.ADDRECONCILIATION, JSON.toJSONString(passValue), new HttpUtileCallBack() {
                    @Override
                    public void getReturnStr(String returnStr) {
                        Looper.prepare();
                        Toast.makeText(AddStatementActivity.this,"成功",Toast.LENGTH_LONG).show();
                        finish();
                        Looper.loop();

                    }

                    @Override
                    public void getReturnStrFailure(String returnStr) {
                        Looper.prepare();
                        Toast.makeText(AddStatementActivity.this,returnStr,Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }

                    @Override
                    public void getErrorStr(String errorStr) {
                        System.out.println("errorStr = "+errorStr);
                    }
                },false);

                break;
        }
    }
}
