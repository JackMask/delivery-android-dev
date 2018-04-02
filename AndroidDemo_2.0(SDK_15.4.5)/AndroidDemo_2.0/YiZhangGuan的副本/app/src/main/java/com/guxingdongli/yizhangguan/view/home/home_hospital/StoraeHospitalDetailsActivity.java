package com.guxingdongli.yizhangguan.view.home.home_hospital;

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

import com.alibaba.fastjson.JSON;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.StoraeHospitalDetailsListAdapterNew;
import com.guxingdongli.yizhangguan.controller.adapter.callback.StoraeHospitalDetailsCallBack;
import com.guxingdongli.yizhangguan.model.StoraeHospitalDetailsBase;
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
 * 验收入库详情
 * Created by jackmask on 2018/3/3.
 */

public class StoraeHospitalDetailsActivity extends YiZhangGuanActivity {

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
    @Bind(R.id.status_tv)
    TextView statusTv;

    private TimePickerView timePickerView;

    private StoraeHospitalDetailsListAdapterNew adapter;
    private List<StoraeHospitalDetailsBase.DataBean.DetailsListBean> dataList;
    private List<StoraeHospitalPassValue> inputList = new ArrayList<>();
    private boolean type;
    private String gid;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what) {
                case 0:
                    StoraeHospitalDetailsBase dataBase = (StoraeHospitalDetailsBase) msg.obj;
                    hospitalName.setText(dataBase.getData().getSupplierName());
                    orderDepartment.setText(dataBase.getData().getDepartmentName());
                    orderData.setText(dataBase.getData().getOrderTime());
                    orderId.setText(dataBase.getData().getBusinessNumber());
                    orderPrice.setText(dataBase.getData().getTotalAmount() + "");
                    statusTv.setText(dataBase.getData().getOrderStage());
                    for (StoraeHospitalDetailsBase.DataBean.DetailsListBean data : dataBase.getData().getDetailsList()) {
                        if (!type) {
                            if (data.getNoAcceptanceQuantity() == 0)
                                continue;
                        }
                        dataList.add(data);
                        StoraeHospitalPassValue inuptData = new StoraeHospitalPassValue();
                        inuptData.setOrderGuid(gid);
                        inuptData.setGuid(data.getGid());
                        inuptData.setAcceptanceQuantity(0);
                        inputList.add(inuptData);
                    }
                    if (adapter != null) {
                        //adapter.setInputList(inputList);
                        adapter.notifyDataSetChanged();
                    }
                    scroll_layout.scrollTo(0, 0);
                    break;
            }

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

    private void setView() {
        type = getIntent().getBooleanExtra("type", false);
        gid = getIntent().getStringExtra("guid");
        titleText.setText("订单详情");
        dataList = new ArrayList<>();

        adapter = new StoraeHospitalDetailsListAdapterNew(this, dataList, getIntent().getBooleanExtra("con", false));
        detailsList.setAdapter(adapter);
        adapter.setCallBack(new StoraeHospitalDetailsCallBack() {


            @Override
            public void getNumNew(int position, EditText num) {
                if (!TextUtils.isEmpty(num.getText().toString()))
                    inputList.get(position).setAcceptanceQuantity(Integer.valueOf(num.getText().toString()));
            }
        });
        if (type) {
            okBtn.setVisibility(View.GONE);
            adapter.setEditextCon(true);
        } else {
            titleText.setText("验收入库");

        }
        RequestBody formBody = new FormBody.Builder()
                .add("guid", gid)
                .build();
        HttpUtile httpUtile = new HttpUtile(this, Constant.DOMAIN_NAME + Constant.GETPURCHASEORDER, formBody, new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {
                System.out.println("returnStr = " + returnStr);
                StoraeHospitalDetailsBase dataBase = JSON.parseObject(returnStr, StoraeHospitalDetailsBase.class);
                Message message = new Message();
                message.what = 0;
                message.obj = dataBase;
                mHandler.sendMessage(message);
            }

            @Override
            public void getReturnStrFailure(String returnStr) {

            }

            @Override
            public void getErrorStr(String errorStr) {

            }
        },false);
    }

    public void selectTime(final EditText editText) {
        timePickerView = AppUtile.initTimePicker(this, timePickerView, "有效期", new GetTimeStrCallBack() {
            @Override
            public void getStr(Date date, View v) {
                editText.setText(AppUtile.getTime(date));
            }
        });
    }


    public void setNum(int position, String num) {
        try {
            inputList.get(position).setAcceptanceQuantity(Integer.valueOf(num));
        } catch (Exception e) {

        }
    }

    @OnClick({R.id.return_btn, R.id.ok_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_btn:
                finish();
                break;
            case R.id.ok_btn://确定验收
                //String con = adapter.getInputList().toString();
                String con = inputList.toString();
                System.out.println(con);
                HttpUtile httpUtile = new HttpUtile(this, Constant.DOMAIN_NAME + Constant.HOSPITALACCEPURCHASEORDER, con, new HttpUtileCallBack() {
                    @Override
                    public void getReturnStr(String returnStr) {
                        finish();
                    }

                    @Override
                    public void getReturnStrFailure(String returnStr) {
                        Looper.prepare();
                        Toast.makeText(StoraeHospitalDetailsActivity.this, returnStr, Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }

                    @Override
                    public void getErrorStr(String errorStr) {
                        System.out.println("errorStr = " + errorStr);
                    }
                },false);
                break;
        }
    }
}
