package com.guxingdongli.yizhangguan.view.home.home_merchants;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.util.AbDialogUtil;
import com.alibaba.fastjson.JSON;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.MyOrderDetailsAdapter;
import com.guxingdongli.yizhangguan.controller.adapter.MyOrderDetailsNewAdapter;
import com.guxingdongli.yizhangguan.controller.adapter.callback.StoraeHospitalDetailsCallBack;
import com.guxingdongli.yizhangguan.model.StoraeHospitalDetailsBase;
import com.guxingdongli.yizhangguan.model.passvalue.MyOrderDetailsInputBase;
import com.guxingdongli.yizhangguan.model.passvalue.StoraeHospitalPassValue;
import com.guxingdongli.yizhangguan.util.AppUtile;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.GetTimeStrCallBack;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
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
 * @author 余先德
 * @data 2018/3/22
 */

public class MyOrderDetailsNewActivity extends YiZhangGuanActivity {


    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.hospital_name)
    TextView hospitalName;
    @Bind(R.id.order_department)
    TextView orderDepartment;
    @Bind(R.id.order_data)
    TextView orderData;
    @Bind(R.id.order_id)
    TextView orderId;
    @Bind(R.id.order_price)
    TextView orderPrice;
    @Bind(R.id.details_list)
    NoSlideListView detailsList;
    @Bind(R.id.ok_btn)
    TextView okBtn;
    @Bind(R.id.scroll_layout)
    ScrollView scroll_layout;

    private TimePickerView timePickerView;

    private MyOrderDetailsNewAdapter adapter;
    private List<StoraeHospitalDetailsBase.DataBean.DetailsListBean> dataList;
    private List<StoraeHospitalPassValue> inputList = new ArrayList<>();
    private boolean type;
    private String gid;
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what){
                case 0:
                    StoraeHospitalDetailsBase dataBase = (StoraeHospitalDetailsBase)msg.obj;
                    hospitalName.setText(dataBase.getData().getHospitalName());
                    orderDepartment.setText(dataBase.getData().getDepartmentName());
                    orderData.setText(dataBase.getData().getOrderTime());
                    orderId.setText(dataBase.getData().getBusinessNumber());
                    orderPrice.setText(dataBase.getData().getTotalAmount()+"");
                    for (StoraeHospitalDetailsBase.DataBean.DetailsListBean data : dataBase.getData().getDetailsList()){
                        if (data.getAcceptanceQuantity()==data.getQuantity())
                            continue;
                        dataList.add(data);
                        StoraeHospitalPassValue inuptData = new StoraeHospitalPassValue();
                        inuptData.setOrderGuid(gid);
                        inuptData.setGuid(data.getGid());
                        inuptData.setAcceptanceQuantity(0);
                        inputList.add(inuptData);
                    }
                    if (adapter!=null) {
                        //adapter.setInputList(inputList);
                        adapter.notifyDataSetChanged();
                    }
                    scroll_layout.scrollTo(0,0);
                    break;
            }
            AbDialogUtil.removeDialog(MyOrderDetailsNewActivity.this);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_storae_hospital_details);
        ButterKnife.bind(this);
        setView();
    }

    private void setView(){
        type = getIntent().getBooleanExtra("con",false);
        gid = getIntent().getStringExtra("guid");
        titleText.setText("订单详情");
        dataList = new ArrayList<>();

        adapter = new MyOrderDetailsNewAdapter(this,dataList,getIntent().getBooleanExtra("con",false));
        detailsList.setAdapter(adapter);
        adapter.setCallBack(new StoraeHospitalDetailsCallBack() {


            @Override
            public void getNumNew(int position, EditText num) {
                if (!TextUtils.isEmpty(num.getText().toString()))
                    inputList.get(position).setAcceptanceQuantity(Integer.valueOf(num.getText().toString()));
            }
        });
        if (type){
            titleText.setText("订单详情");
            okBtn.setText("录入订单");
        }else{
            titleText.setText("录入订单详情");
            okBtn.setVisibility(View.GONE);
            adapter.setEditextCon(true);



        }
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
                mHandler.sendMessage(message);
            }

            @Override
            public void getReturnStrFailure(String returnStr) {
                mHandler.sendEmptyMessage(1);
            }

            @Override
            public void getErrorStr(String errorStr) {
                mHandler.sendEmptyMessage(1);
            }
        },true);
    }

    public void selectTime(final TextView editText,final StoraeHospitalDetailsBase.DataBean.DetailsListBean data){
        timePickerView = AppUtile.initTimePicker(this, timePickerView, "有效期", new GetTimeStrCallBack() {
            @Override
            public void getStr(Date date, View v) {
                editText.setText(AppUtile.getTime(date,"yyyy-MM-dd"));
                data.setValid(AppUtile.getTime(date,"yyyy-MM-dd"));
            }
        });
        timePickerView.show();
    }

    private List<MyOrderDetailsInputBase> myOrderDetailsInputBases = new ArrayList<>();
    public void setNum(int position,String num){
        inputList.get(position).setAcceptanceQuantity(Integer.valueOf(num));
    }
    @OnClick({R.id.return_btn, R.id.ok_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_btn:
                finish();
                break;
            case R.id.ok_btn://确定验收
                //String con = adapter.getInputList().toString();
                myOrderDetailsInputBases.clear();
                for (StoraeHospitalDetailsBase.DataBean.DetailsListBean item:adapter.getData()){
                    MyOrderDetailsInputBase data = new MyOrderDetailsInputBase();
                    data.setOrderGuid(gid);
                    data.setGuid(item.getGid());
                    data.setBatchNumber(item.getBatchNumber());
                    data.setQuantity(item.getQuantity());
                    data.setValid(item.getValid());
                    myOrderDetailsInputBases.add(data);
                }
                String data = JSON.toJSONString(myOrderDetailsInputBases);
                HttpUtile httpUtile =new HttpUtile(this, Constant.DOMAIN_NAME + Constant.ADDPURCHASEORDERQUANTITY, data, new HttpUtileCallBack() {
                    @Override
                    public void getReturnStr(String returnStr) {
                        Looper.prepare();
                        mHandler.sendEmptyMessage(1);
                        Toast.makeText(MyOrderDetailsNewActivity.this,"录入成功",Toast.LENGTH_LONG).show();
                        finish();
                        Looper.loop();

                    }

                    @Override
                    public void getReturnStrFailure(String returnStr) {
                        Looper.prepare();
                        mHandler.sendEmptyMessage(1);
                        Toast.makeText(MyOrderDetailsNewActivity.this,returnStr,Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }

                    @Override
                    public void getErrorStr(String errorStr) {
                        mHandler.sendEmptyMessage(1);
                        System.out.println("errorStr = " + errorStr);
                    }
                },true);
                break;
        }
    }
}
