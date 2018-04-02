package com.guxingdongli.yizhangguan.view.home.home_merchants.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.DasisDataAdapter;
import com.guxingdongli.yizhangguan.controller.adapter.callback.StatementManagementOkCallBack;
import com.guxingdongli.yizhangguan.controller.adapter.fragment.StatementManagementFragmentAdapter;
import com.guxingdongli.yizhangguan.controller.adapter.fragment.StatementManagementOkFragmentAdapter;
import com.guxingdongli.yizhangguan.model.StatementManagementBase;
import com.guxingdongli.yizhangguan.model.StatementManagementOkBase;
import com.guxingdongli.yizhangguan.model.TestHospitalBean;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.view.home.home_hospital.fragment.callback.StorageHospitalOnClickCallBack;
import com.guxingdongli.yizhangguan.view.login.LoginActivity;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;
import com.yuxiaolong.yuxiandelibrary.pulltorefresh.PullToRefreshBase;
import com.yuxiaolong.yuxiandelibrary.pulltorefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 对账单
 * Created by jackmask on 2018/3/9.
 */

public class StatementManagementFragment  extends Fragment{

    @Bind(R.id.content_list)
    PullToRefreshListView contentList;
    private YuXianDeActivity activity;
    private View view;
    private StatementManagementFragmentAdapter adapter;
    private StatementManagementOkFragmentAdapter okAdapter;
    private List<StatementManagementBase.DataBeanX.DataBean> notList = new ArrayList<>();
    private List<StatementManagementOkBase.DataBeanX.DataBean> okList = new ArrayList<>();

    private boolean type = true;
    private int page = 1;
    private float total = 1;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) //如果消息是刚才发送的标识
            {
                String formBody = (String)msg.obj;
                StatementManagementBase data = JSON.parseObject(formBody,StatementManagementBase.class);
                if (page == 1){
                    notList.clear();
                    total = data.getData().getTotal();
                }
                if (Math.ceil(total/10)>=page){
                    for (StatementManagementBase.DataBeanX.DataBean item:data.getData().getData()){
                        notList.add(item);
                    }
                }
                if (adapter!=null){
                    adapter.notifyDataSetChanged();
                }
                contentList.onRefreshComplete();

            }else if (msg.what == 1){
                String formBody = (String)msg.obj;
                StatementManagementOkBase data = JSON.parseObject(formBody,StatementManagementOkBase.class);
                if (page == 1){
                    okList.clear();
                    total = data.getData().getTotal();
                }
                if (Math.ceil(total/10)>=page){
                    for (StatementManagementOkBase.DataBeanX.DataBean item:data.getData().getData()){
                        okList.add(item);
                    }
                }
                if (okAdapter!=null){
                    okAdapter.notifyDataSetChanged();
                }
                contentList.onRefreshComplete();

            }else if (msg.what == 2){
                contentList.onRefreshComplete();
            }
        }

        ;
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (YuXianDeActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_storage_hospital, container, false);
        ButterKnife.bind(this, view);
        setView();
        return view;
    }

    private void setView() {
        String con = getArguments().getString("DATA");

        if (con.equals("all")){
            type = true;
            adapter = new StatementManagementFragmentAdapter(notList);
            contentList.setAdapter(adapter);
        }else{
            type = false;
            okAdapter = new StatementManagementOkFragmentAdapter(okList);
            contentList.setAdapter(okAdapter);
            okAdapter.setCallBack(new StatementManagementOkCallBack() {
                @Override
                public void Click(String guid) {
                    List<String> guids = new ArrayList<>();
                    guids.add(guid);
                    HttpUtile httpUtile = new HttpUtile(getActivity(), Constant.DOMAIN_NAME + Constant.BILLCONFIRM, JSON.toJSONString(guids), new HttpUtileCallBack() {
                        @Override
                        public void getReturnStr(String returnStr) {
                            page = 1;
                            getWebData();
                            Looper.prepare();
                            Toast.makeText(getActivity(),"成功",Toast.LENGTH_LONG).show();
                            Looper.loop();

                        }

                        @Override
                        public void getReturnStrFailure(String returnStr) {
                            Looper.prepare();
                            Toast.makeText(getActivity(),returnStr,Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }

                        @Override
                        public void getErrorStr(String errorStr) {
                            System.out.println("errorStr = "+errorStr);
                        }
                    },true);
                }
            });
        }
        contentList.setMode(PullToRefreshBase.Mode.BOTH);
        contentList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //刷新
                page = 1;
                getWebData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //加载
                page ++;
                getWebData();

            }
        });
        getWebData();
    }

    private void getWebData(){
        RequestBody formBody = new  FormBody.Builder()
                .add("pageIndex", page+"")
                .add("pageSize", "10")
                .build();
        if (type) {
            HttpUtile httpUtile = new HttpUtile(getActivity(), Constant.DOMAIN_NAME + Constant.SEARCHRECONCILIATION, formBody, new HttpUtileCallBack() {
                @Override
                public void getReturnStr(String returnStr) {
                    Message message = new Message();
                    message.what = 0;
                    message.obj = returnStr;
                    handler.sendMessage(message);
                }

                @Override
                public void getReturnStrFailure(String returnStr) {
                    handler.sendEmptyMessage(2);
                    Looper.prepare();
                    Toast.makeText(getActivity(),returnStr,Toast.LENGTH_LONG).show();
                    Looper.loop();
                }

                @Override
                public void getErrorStr(String errorStr) {
                    handler.sendEmptyMessage(2);
                    System.out.println("errorStr = "+errorStr);
                }
            },false);
        }else{
            HttpUtile httpUtile = new HttpUtile(getActivity(), Constant.DOMAIN_NAME + Constant.SEARCHRECONCILIATIONRECORD, formBody, new HttpUtileCallBack() {
                @Override
                public void getReturnStr(String returnStr) {
                    Message message = new Message();
                    message.what = 1;
                    message.obj = returnStr;
                    handler.sendMessage(message);
                }

                @Override
                public void getReturnStrFailure(String returnStr) {
                    handler.sendEmptyMessage(2);
                    Looper.prepare();
                    Toast.makeText(getActivity(),returnStr,Toast.LENGTH_LONG).show();
                    Looper.loop();
                }

                @Override
                public void getErrorStr(String errorStr) {
                    handler.sendEmptyMessage(2);
                    System.out.println("errorStr = "+errorStr);
                }
            },false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}