package com.guxingdongli.yizhangguan.view.home.home_merchants;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.util.AbToastUtil;
import com.alibaba.fastjson.JSON;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.PushSelectHospitalAdapter;
import com.guxingdongli.yizhangguan.controller.adapter.callback.PushSelectHospitalCallBack;
import com.guxingdongli.yizhangguan.model.PushBase;
import com.guxingdongli.yizhangguan.model.PushSelectHospitalBase;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.guxingdongli.yizhangguan.view.login.LoginActivity;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;
import com.yuxiaolong.yuxiandelibrary.pulltorefresh.PullToRefreshBase;
import com.yuxiaolong.yuxiandelibrary.pulltorefresh.PullToRefreshListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 余先德
 * @data 2018/3/24
 */

public class PushSelectHospitalActivity extends YiZhangGuanActivity {
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.right_tv_btn)
    TextView rightTvBtn;
    @Bind(R.id.content_list)
    PullToRefreshListView contentList;

    private PushSelectHospitalAdapter adapter;
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
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_select_hospital);
        ButterKnife.bind(this);
        setView();
    }

    private void setView(){
        titleText.setText("选择医院");
        rightTvBtn.setVisibility(View.VISIBLE);
        rightTvBtn.setText("保存");
        contentList.setMode(PullToRefreshBase.Mode.DISABLED);
        adapter = new PushSelectHospitalAdapter(dataBeans);
        contentList.setAdapter(adapter);
        adapter.setCallBack(new PushSelectHospitalCallBack() {
            @Override
            public void getData(PushSelectHospitalBase.DataBean item, boolean type) {
                boolean con = false;
                for (PushSelectHospitalBase.DataBean data : selectHospital){
                    if (data.getId() == item.getId()){
                        con = true;
                        break;
                    }
                }
                if (type){
                    if (!con){
                        selectHospital.add(item);
                    }
                }else{
                    if (con){
                        for (int i = 0 ; i <  selectHospital.size();i++){
                            selectHospital.remove(i);
                        }
                    }
                }
            }
        });
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
                Toast.makeText(PushSelectHospitalActivity.this,returnStr,Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void getErrorStr(String errorStr) {
                System.out.println("errorStr = "+errorStr);
            }
        },false);
    }

    @OnClick({R.id.return_btn, R.id.right_tv_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_btn:
                finish();
                break;
            case R.id.right_tv_btn:
                if (selectHospital.size()>0){
                    Intent intent = new Intent();
                    intent.putExtra("datas",(Serializable)selectHospital);
                    setResult(RESULT_OK,intent);
                    finish();
                }else{
                    AbToastUtil.showToast(this,"您还未选择医院");
                }
                break;
        }
    }
}
