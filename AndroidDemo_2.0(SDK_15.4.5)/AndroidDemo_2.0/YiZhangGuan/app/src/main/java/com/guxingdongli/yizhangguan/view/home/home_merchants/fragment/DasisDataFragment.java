package com.guxingdongli.yizhangguan.view.home.home_merchants.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.DasisDataAdapter;
import com.guxingdongli.yizhangguan.controller.adapter.DasisDataHospitalAdapter;
import com.guxingdongli.yizhangguan.model.DasisDataBase;
import com.guxingdongli.yizhangguan.model.DasisDataHospitalBase;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.view.home.home_merchants.PushActivity;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;
import com.yuxiaolong.yuxiandelibrary.pulltorefresh.PullToRefreshBase;
import com.yuxiaolong.yuxiandelibrary.pulltorefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by jackmask on 2018/3/8.
 */

public class DasisDataFragment extends Fragment {

    @Bind(R.id.content_list)
    PullToRefreshListView contentList;
    @Bind(R.id.push_layout)
    LinearLayout pushLayout;

    private YuXianDeActivity activity;
    private View view;
    private DasisDataAdapter adapter;
    private DasisDataHospitalAdapter hospitalAdapter;
    private List<DasisDataBase.DataBeanX.DataBean> dataList = new ArrayList<>();
    private List<DasisDataHospitalBase.DataBeanX.DataBean> hospitalDataList = new ArrayList<>();
    private int page = 1;
    private boolean conType = false;
    private float allNum = 1;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                String formBody = (String) msg.obj;
                DasisDataBase data = JSON.parseObject(formBody, DasisDataBase.class);
                if (page == 1) {
                    dataList.clear();
                    allNum = data.getData().getTotal();
                }
                if (Math.ceil(allNum / 10) >= page)
                    for (DasisDataBase.DataBeanX.DataBean item : data.getData().getData()) {
                        dataList.add(item);
                    }
                adapter.notifyDataSetChanged();
            } else if (msg.what == 1) {
                String formBody = (String) msg.obj;
                DasisDataHospitalBase data = JSON.parseObject(formBody, DasisDataHospitalBase.class);
                if (page == 1) {
                    hospitalDataList.clear();
                    allNum = data.getData().getTotal();
                }
                if (Math.ceil(allNum / 10) >= page)
                    for (DasisDataHospitalBase.DataBeanX.DataBean item : data.getData().getData()) {
                        hospitalDataList.add(item);
                    }
                hospitalAdapter.notifyDataSetChanged();
            }
            contentList.onRefreshComplete();
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
        String type = getArguments().getString("DATA");
        contentList.setMode(PullToRefreshBase.Mode.BOTH);
        if (type.equals("my")) {
            conType = true;
            adapter = new DasisDataAdapter(dataList);
            contentList.setAdapter(adapter);
        } else {
            conType = false;
            pushLayout.setVisibility(View.VISIBLE);
            hospitalAdapter = new DasisDataHospitalAdapter(hospitalDataList);
            contentList.setAdapter(hospitalAdapter);
        }


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
                page++;
                getWebData();

            }
        });
        getWebData();
    }

    private void getWebData() {
        RequestBody formBody = new FormBody.Builder()
                .add("pageIndex", page + "")
                .add("pageSize", "10")
                .build();
        if (conType) {
            HttpUtile httpUtile = new HttpUtile(getActivity(), Constant.DOMAIN_NAME + Constant.SEARCHBASICDATALIST, formBody, new HttpUtileCallBack() {
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
                    handler.sendEmptyMessage(3);
                    Toast.makeText(getActivity(), returnStr, Toast.LENGTH_LONG).show();
                    Looper.loop();
                }

                @Override
                public void getErrorStr(String errorStr) {
                    System.out.println("errorStr = " + errorStr);
                }
            },false);
        } else {
            HttpUtile httpUtile = new HttpUtile(getActivity(), Constant.DOMAIN_NAME + Constant.SEARCHBASICHOSPITDATA, formBody, new HttpUtileCallBack() {
                @Override
                public void getReturnStr(String returnStr) {
                    Message message = new Message();
                    message.what = 1;
                    message.obj = returnStr;
                    handler.sendMessage(message);
                }

                @Override
                public void getReturnStrFailure(String returnStr) {
                    Looper.prepare();
                    handler.sendEmptyMessage(3);
                    Toast.makeText(getActivity(), returnStr, Toast.LENGTH_LONG).show();
                    Looper.loop();
                }

                @Override
                public void getErrorStr(String errorStr) {
                    System.out.println("errorStr = " + errorStr);
                }
            },false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.push_btn)
    public void onViewClicked() {
        //推送数据
        Intent intent  = new Intent(getActivity(), PushActivity.class);
        startActivity(intent);

    }
}