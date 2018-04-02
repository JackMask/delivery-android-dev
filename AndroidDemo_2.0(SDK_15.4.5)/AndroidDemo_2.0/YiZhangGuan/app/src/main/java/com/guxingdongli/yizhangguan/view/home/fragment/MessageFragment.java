package com.guxingdongli.yizhangguan.view.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ab.http.AbRequestParams;
import com.alibaba.fastjson.JSON;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.MessageAdapter;
import com.guxingdongli.yizhangguan.model.MessageBase;
import com.guxingdongli.yizhangguan.model.RemindBase;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.view.home.HomeActivity;
import com.yuxiaolong.yuxiandelibrary.pulltorefresh.PullToRefreshBase;
import com.yuxiaolong.yuxiandelibrary.pulltorefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by jackmask on 2018/3/1.
 */

public class MessageFragment extends Fragment {

    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.content_list)
    PullToRefreshListView contentList;

    private HomeActivity abActivity;
    private View view;
    private MessageAdapter adapter;
    private List<MessageBase.MessageDataBase.dataBase> dataList = new ArrayList<>();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        abActivity = (HomeActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.bind(this, view);
        setView();
        return view;
    }

    private void setView(){
        returnBtn.setVisibility(View.INVISIBLE);
        titleText.setText("消息管理");

        adapter = new MessageAdapter(dataList);
        contentList.setMode(PullToRefreshBase.Mode.DISABLED);
        contentList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        RequestBody body = new  FormBody.Builder().build();
        HttpUtile httpUtile =new HttpUtile(getActivity(), Constant.DOMAIN_NAME + Constant.SEARCHMESSAGE, body, new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {
                MessageBase data = JSON.parseObject(returnStr,MessageBase.class);
                dataList.clear();
                int noRead = 0 ;
                for (MessageBase.MessageDataBase.dataBase dataBase : data.getData().getData()){
                    dataList.add(dataBase);
                    if (dataBase.getReadState().equals("未读取")){
                        noRead++;
                    }
                }
                abActivity.setMessageNum(noRead);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
