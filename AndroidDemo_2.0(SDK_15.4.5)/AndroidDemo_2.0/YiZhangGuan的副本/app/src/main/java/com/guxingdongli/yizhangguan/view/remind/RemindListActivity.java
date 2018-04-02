package com.guxingdongli.yizhangguan.view.remind;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.RemindAdapter;
import com.guxingdongli.yizhangguan.model.RemindBase;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by jackmask on 2018/3/3.
 */

public class RemindListActivity extends YiZhangGuanActivity {

    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.list_layout)
    ListView listLayout;

    private RemindAdapter adapter;
    private List<RemindBase.DataBase> dataList = new ArrayList<>();
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            if(what == 0){
                //在主线程中需要执行的操作，一般是UI操作
                if (adapter!=null)
                    adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_list);
        ButterKnife.bind(this);
        setView();
    }

    private void setView(){

        titleText.setText("工作提醒");
        adapter = new RemindAdapter(dataList);
        listLayout.setAdapter(adapter);
        RequestBody body = new  FormBody.Builder().build();
        HttpUtile httpUtile =new HttpUtile(RemindListActivity.this, Constant.DOMAIN_NAME + Constant.GETWORKTIP, body, new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {
                RemindBase data = JSON.parseObject(returnStr,RemindBase.class);
                dataList.clear();
                for (RemindBase.DataBase dataBase : data.getData()){
                    dataList.add(dataBase);
                }
                mHandler.sendEmptyMessage(0);
            }
            @Override
            public void getReturnStrFailure(String returnStr) {
            }
            @Override
            public void getErrorStr(String errorStr) {

            }
        },false);
    }

    @OnClick(R.id.return_btn)
    public void onViewClicked() {
        finish();
    }
}
