package com.guxingdongli.yizhangguan.view.home.home_hospital.fragment;

/**
 * Created by jackmask on 2018/3/2.
 */

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.StorageHospitalFragmentAdapter;
import com.guxingdongli.yizhangguan.model.StorageHospitalBase;
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


public class StorageHospitalFragment extends Fragment {

    @Bind(R.id.content_list)
    PullToRefreshListView contentList;
    private YuXianDeActivity activity;
    private View view;
    private StorageHospitalFragmentAdapter adapter;
    private List<StorageHospitalBase.DataBeanX.DataBean> dataList = new ArrayList<>();
    private int page = 1;
    private float allPage = 1;
    private boolean sortOrder = false;
    private String keyword = "";

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            HttpUtile httpUtile;
            switch (what){
                case 0:
                    if (adapter!=null)
                    adapter.notifyDataSetChanged();
                    if (contentList!=null)
                    contentList.onRefreshComplete();
                    break;
            }

        }
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

    private void setView(){
        //测试数据
        adapter = new StorageHospitalFragmentAdapter(dataList);
        contentList.setMode(PullToRefreshBase.Mode.BOTH);
        contentList.setAdapter(adapter);

        //添加展示的回调
        adapter.setClickCallBack(new StorageHospitalOnClickCallBack() {
            @Override
            public void clickNo(int position, boolean type) {
                for (int i = 0 ; i < dataList.size();i++){
                    dataList.get(i).setType(false);
                }
                dataList.get(position).setType(type);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void clickTime(String hospitalGuid, String number) {

            }
        });

        contentList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //刷新
                page = 1;
                webData(keyword,sortOrder);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //加载
                page ++;
                webData(keyword,sortOrder);

            }
        });
        webData(keyword,sortOrder);
    }

    public void setSortOrder(String keyword,boolean sortOrder){
        this.keyword  = keyword;
        this.sortOrder = sortOrder;
        webData(keyword,sortOrder);
    }

    public void setPage(){
        page = 1;
    }

    private void webData(String keyword,boolean sortOrder){
        String type = getArguments().getString("DATA");
        RequestBody formBody;
        if (type.equals("find")){
            formBody = new  FormBody.Builder()
                    .add("pageIndex", page+"")
                    .add("pageSize", "10")
                    .add("sortField", "Id")
                    .add("sortOrder", sortOrder?"asc":"desc")
                    .add("filter_group", "{\"Rules\":[{\"Field\":\"businessNumber\",\"Value\":\""+keyword+"\",\"Operate\":\"contains\"},{\"Field\":\"OrderStage\",\"Value\":\"1\",\"Operate\":\"equal\"}],\"Operate\":\"and\"}")
                    .build();
        }else{
            formBody = new  FormBody.Builder()
                    .add("pageIndex", page+"")
                    .add("pageSize", "10")
                    .add("sortField", "Id")
                    .add("sortOrder", sortOrder?"asc":"desc")
                    .add("filter_group", "{\"Rules\":[{\"Field\":\"businessNumber\",\"Value\":\""+keyword+"\",\"Operate\":\"contains\"},{\"Field\":\"OrderStage\",\"Value\":\"3\",\"Operate\":\"equal\"}],\"Operate\":\"and\"}")
                    .build();
        }

        HttpUtile httpUtile = new HttpUtile(getActivity(), Constant.DOMAIN_NAME + Constant.SEARCHPURCHASEORDERLIST, formBody, new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {
                StorageHospitalBase data = JSON.parseObject(returnStr,StorageHospitalBase.class);
                if (page == 1){
                    dataList.clear();
                    allPage = data.getData().getTotal();
                }
                if (Math.ceil(allPage/10) >= page) {
                    for (StorageHospitalBase.DataBeanX.DataBean dataItem : data.getData().getData()) {
                        dataList.add(dataItem);
                    }
                }
                mHandler.sendEmptyMessage(0);

            }

            @Override
            public void getReturnStrFailure(String returnStr) {


                Looper.prepare();
                dataList.clear();
                mHandler.sendEmptyMessage(0);
                Toast.makeText(getContext(),returnStr,Toast.LENGTH_LONG).show();
                Looper.loop();
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