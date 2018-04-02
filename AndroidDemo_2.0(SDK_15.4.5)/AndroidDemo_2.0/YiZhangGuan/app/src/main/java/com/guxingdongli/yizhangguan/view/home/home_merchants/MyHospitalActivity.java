package com.guxingdongli.yizhangguan.view.home.home_merchants;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.util.AbDialogUtil;
import com.alibaba.fastjson.JSON;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.MyHospitalAdapter;
import com.guxingdongli.yizhangguan.model.PushSelectHospitalBase;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.guxingdongli.yizhangguan.view.home.home_hospital.MyRepairOrderDetailsActivity;
import com.guxingdongli.yizhangguan.view.home.home_hospital.fragment.callback.StorageHospitalOnClickCallBack;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;
import com.yuxiaolong.yuxiandelibrary.pulltorefresh.PullToRefreshBase;
import com.yuxiaolong.yuxiandelibrary.pulltorefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jackmask on 2018/3/11.
 */

public class MyHospitalActivity extends YiZhangGuanActivity {
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.content_list)
    PullToRefreshListView contentList;

    private MyHospitalAdapter adapter;

    private List<PushSelectHospitalBase.DataBean> dataBeans = new ArrayList<>(),selectHospital = new ArrayList<>();
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) //如果消息是刚才发送的标识
            {
                String formBody = (String)msg.obj;
                PushSelectHospitalBase data = JSON.parseObject(formBody,PushSelectHospitalBase.class);

                for (PushSelectHospitalBase.DataBean item : data.getData()){
                    dataBeans.add(item);
                }
                if (adapter!=null);
                adapter.notifyDataSetChanged();
            }
            AbDialogUtil.removeDialog(MyHospitalActivity.this);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_hospital);
        ButterKnife.bind(this);
        setView();
    }

    private void setView(){
        titleText.setText("我所供应的医院");

        adapter = new MyHospitalAdapter(dataBeans);
        contentList.setMode(PullToRefreshBase.Mode.DISABLED);
        contentList.setAdapter(adapter);


        setWeb();
    }

    private void setWeb(){
        HttpUtile httpUtile = new HttpUtile(this, Constant.DOMAIN_NAME + Constant.SEARCHSUPPLIERHOSPITALRELATION, new HttpUtileCallBack() {
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
                handler.sendEmptyMessage(1);
                Toast.makeText(MyHospitalActivity.this,returnStr,Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void getErrorStr(String errorStr) {
                handler.sendEmptyMessage(1);
                System.out.println("errorStr = "+errorStr);
            }
        },true);
    }

    @OnClick(R.id.return_btn)
    public void onViewClicked() {
        finish();
    }
}
