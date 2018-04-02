package com.guxingdongli.yizhangguan.view.home.home_merchants;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.MakeUpInfoAdapter;
import com.guxingdongli.yizhangguan.controller.adapter.callback.MakeUpInfoCallBack;
import com.guxingdongli.yizhangguan.model.PreparationOrderDetailsBase;
import com.guxingdongli.yizhangguan.model.StoraeHospitalDetailsBase;
import com.guxingdongli.yizhangguan.model.passvalue.MyStockOrderInput;
import com.guxingdongli.yizhangguan.util.AppUtile;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.GetTimeStrCallBack;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.guxingdongli.yizhangguan.view.home.dialog.PromptDialog;
import com.guxingdongli.yizhangguan.view.login.LoginActivity;
import com.yuxiaolong.yuxiandelibrary.NoSlideListView;
import com.yuxiaolong.yuxiandelibrary.pickerview.TimePickerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 补录备货单器材信息
 * Created by jackmask on 2018/3/8.
 */

public class StockingInfoActivity extends YiZhangGuanActivity {

    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.equipment_list)
    NoSlideListView equipmentList;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.department_tv)
    TextView departmentTv;
    @Bind(R.id.time_tv)
    TextView timeTv;
    @Bind(R.id.num_tv)
    TextView numTv;
    @Bind(R.id.linearLayout6)
    LinearLayout linearLayout6;

    private MakeUpInfoAdapter adapter;
    private List<PreparationOrderDetailsBase.DataBean.DetailsListBean> dataList = new ArrayList<>();
    private List<MyStockOrderInput> inputs = new ArrayList<>();
    private TimePickerView pvTime;
    private boolean type = true;
    private String orderId;


    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) //如果消息是刚才发送的标识
            {
                PreparationOrderDetailsBase data = (PreparationOrderDetailsBase) msg.obj;
                name.setText(data.getData().getHospitalName());
                departmentTv.setText(data.getData().getDepartmentName());
                timeTv.setText(data.getData().getOrderTime());
                numTv.setText(data.getData().getBusinessNumber());
                for (PreparationOrderDetailsBase.DataBean.DetailsListBean item:data.getData().getDetailsList()){
                    dataList.add(item);
                    MyStockOrderInput a = new MyStockOrderInput();
                    a.setBatchNumber(item.getBatchNumber());
                    a.setOrderGuid(orderId);
                    a.setGuid(item.getGid());
                    a.setProductNum(item.getProductNum());
                    a.setSerialNum(item.getSerialNum());
                    a.setValid(item.getValid());
                    inputs.add(a);
                }
                adapter.notifyDataSetChanged();

            }
        }

        ;
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocking_info);
        ButterKnife.bind(this);
        setView();
    }

    private void setView() {
        titleText.setText("补录备货单器材信息");
        type = getIntent().getBooleanExtra("con", false);
        orderId = getIntent().getStringExtra("orderID");
        if (type){
            linearLayout6.setVisibility(View.VISIBLE);
        }
        adapter = new MakeUpInfoAdapter(dataList);
        adapter.setType(type);
        adapter.setCallBack(new MakeUpInfoCallBack() {
            @Override
            public void selectItem(final int position,final TextView view) {
                pvTime = AppUtile.initTimePicker(StockingInfoActivity.this, pvTime, "请选择有效期", new GetTimeStrCallBack() {
                    @Override
                    public void getStr(Date date, View v) {
                        view.setText(AppUtile.getTime(date));
                        setValid(position,AppUtile.getTime(date));
                    }
                });
                pvTime.show();
            }
        });
        RequestBody formBody = new FormBody.Builder()
                .add("orderID", getIntent().getStringExtra("orderID"))
                .build();

        HttpUtile httpUtile = new HttpUtile(this, Constant.DOMAIN_NAME + Constant.GETSPAREORDER, formBody, new HttpUtileCallBack() {

            @Override
            public void getReturnStr(String returnStr) {
                System.out.println("returnStr = " + returnStr);
                PreparationOrderDetailsBase dataBase = JSON.parseObject(returnStr, PreparationOrderDetailsBase.class);
                Message message = new Message();
                message.what = 0;
                message.obj = dataBase;
                handler.sendMessage(message);

            }

            @Override
            public void getReturnStrFailure(String returnStr) {
                Looper.prepare();
                handler.sendEmptyMessage(1);
                Toast.makeText(StockingInfoActivity.this, returnStr, Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void getErrorStr(String errorStr) {

            }
        },false);
        equipmentList.setAdapter(adapter);
    }
    public void setBatchNumber(int position,String con){
        inputs.get(position).setBatchNumber(con);
    }
    public void setProductNum(int position,String con){
        inputs.get(position).setProductNum(con);
    }
    public void setSerialNum(int position,String con){
        inputs.get(position).setSerialNum(con);
    }
    public void setValid(int position,String con){
        inputs.get(position).setValid(con);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == 1001) {
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
                intent.putExtra("content", "MakeUpInfo");
                startActivityForResult(intent, 0);
                break;
            case R.id.enter_btn:
                String str = JSON.toJSONString(inputs);
                HttpUtile httpUtile = new HttpUtile(this, Constant.DOMAIN_NAME + Constant.ADDSPAREORDER, str, new HttpUtileCallBack() {
                    @Override
                    public void getReturnStr(String returnStr) {
                        Looper.prepare();
                        Toast.makeText(StockingInfoActivity.this,"成功",Toast.LENGTH_LONG).show();
                        finish();
                        Looper.loop();
                    }

                    @Override
                    public void getReturnStrFailure(String returnStr) {
                        Looper.prepare();
                        Toast.makeText(StockingInfoActivity.this,returnStr,Toast.LENGTH_LONG).show();
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
