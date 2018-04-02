package com.guxingdongli.yizhangguan.view.home.home_hospital;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.YiZhangGuanApplication;
import com.guxingdongli.yizhangguan.controller.adapter.AcceptanceListComponentsAdapter;
import com.guxingdongli.yizhangguan.controller.adapter.AcceptanceListCostAdapter;
import com.guxingdongli.yizhangguan.controller.adapter.AcceptanceListHandleAdapter;
import com.guxingdongli.yizhangguan.model.AcceptanceListComponentsBase;
import com.guxingdongli.yizhangguan.model.AcceptanceListCostBase;
import com.guxingdongli.yizhangguan.model.AcceptanceListHandleBase;
import com.guxingdongli.yizhangguan.model.LoginBase;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.guxingdongli.yizhangguan.view.home.HomeActivity;
import com.guxingdongli.yizhangguan.view.login.LoginActivity;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * '
 * 验收维修单列表
 * Created by jackmask on 2018/3/6.
 */

public class AcceptanceListActivity extends YiZhangGuanActivity {

    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.data_list)
    ListView dataListView;

    private AcceptanceListCostAdapter costAdapter;
    private AcceptanceListComponentsAdapter componentsAdapter;
    private AcceptanceListHandleAdapter handleAdapter;
    private List<String> dataList = new ArrayList<>();
    private List<AcceptanceListHandleBase.DataBean> handleDataList = new ArrayList<>();
    private List<AcceptanceListComponentsBase.DataBean> componentsDataList = new ArrayList<>();
    private List<AcceptanceListCostBase.DataBean> costDataList = new ArrayList<>();
    private String type;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) //如果消息是刚才发送的标识
            {
                String returnStr = (String)msg.obj;
                if (type.equals("cost")) {//费用明细
                    AcceptanceListCostBase data = JSON.parseObject(returnStr,AcceptanceListCostBase.class);
                    for (AcceptanceListCostBase.DataBean dataBean:data.getData()){
                        costDataList.add(dataBean);
                    }
                    costAdapter.notifyDataSetChanged();

                }else if (type.equals("components")) { //零件明细
                    AcceptanceListComponentsBase data = JSON.parseObject(returnStr,AcceptanceListComponentsBase.class);
                    for (AcceptanceListComponentsBase.DataBean dataBean:data.getData()){
                        componentsDataList.add(dataBean);
                    }
                    componentsAdapter.notifyDataSetChanged();

                }else if (type.equals("handle")) {//处理情况查看
                    AcceptanceListHandleBase data = JSON.parseObject(returnStr,AcceptanceListHandleBase.class);
                    for (AcceptanceListHandleBase.DataBean dataBean:data.getData()){
                        handleDataList.add(dataBean);
                    }
                    handleAdapter.notifyDataSetChanged();
                }


            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptance_list);
        ButterKnife.bind(this);
        setView();
    }

    private void setView(){
        type = getIntent().getStringExtra("type");

        if (!TextUtils.isEmpty(type)){

        if (type.equals("cost")){//费用明细
            titleText.setText("查看费用记录");
            costAdapter = new AcceptanceListCostAdapter(costDataList);
            dataListView.setAdapter(costAdapter);
            webGetData(Constant.DOMAIN_NAME + Constant.GETGYREPAIRFEELIST);
        }else if (type.equals("components")){ //零件明细
            titleText.setText("查看更换零部件");
            componentsAdapter = new AcceptanceListComponentsAdapter(componentsDataList);
            dataListView.setAdapter(componentsAdapter);
            webGetData(Constant.DOMAIN_NAME + Constant.GETREPLACINGFITTINGLIST);

        }else if (type.equals("handle")) {//处理情况查看
            titleText.setText("查看维修处理记录");
            handleAdapter = new AcceptanceListHandleAdapter(handleDataList);
            dataListView.setAdapter(handleAdapter);
            webGetData(Constant.DOMAIN_NAME + Constant.GETREPAIRPROCESSLIST);

        }
        }
    }
    private void webGetData(String url){
        RequestBody formBody = new  FormBody.Builder()
                .add("hospitalGuid", getIntent().getStringExtra("hospitalGUID"))
                .add("applyId", getIntent().getStringExtra("applyId"))
                .build();
        final HttpUtile httpUtile = new HttpUtile(AcceptanceListActivity.this, url, formBody, new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {
                Message message = new Message();
                message.what = 0;
                message.obj = returnStr;
                handler.sendMessage(message);


            }

            @Override
            public void getReturnStrFailure(String returnStr) {
                Looper.prepare();
                Toast.makeText(AcceptanceListActivity.this, returnStr, Toast.LENGTH_LONG).show();
                Looper.loop();

                // AbToastUtil.showToast(LoginActivity.this,returnStr);
            }

            @Override
            public void getErrorStr(String errorStr) {
                System.out.println("errorStr = " + errorStr);
            }
        },false);
    }

    @OnClick(R.id.return_btn)
    public void onViewClicked() {
        finish();
    }
}
