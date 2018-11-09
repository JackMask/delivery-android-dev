package com.yum.two_yum.view.merchants.statistics;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.OrderHistoryBase;
import com.yum.two_yum.base.TransactionHistoryBase;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.controller.adapter.TransactionHistoryAdapter;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.pulltorefresh.PullToRefreshBase;
import com.yum.two_yum.utile.pulltorefresh.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @author 余先德
 * @data 2018/4/14
 */

public class TransactionHistoryActivity extends BaseActivity {

    @Bind(R.id.content_lv)
    PullToRefreshListView contentLv;
    private TransactionHistoryAdapter adapter;
    private List<TransactionHistoryBase.DataBean.TransactionRecordRespResultListBean> dataList  = new ArrayList<>();
    private int page =1;
    private int size = 8;
    private int allpage = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        ButterKnife.bind(this);
        setView();
    }

    private void getData(){
        OkHttpUtils
                .get()
                .url(Constant.PAY_TRANSFER_RECORD)
                .addHeader("Accept-Language", AppUtile.getLanguage())
                .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                .addParams("userId", YumApplication.getInstance().getMyInformation().getUid())
                .addParams("pageIndex", page+"")
                .addParams("pageSize", size+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        contentLv.onRefreshComplete();
                        if (AppUtile.getNetWork(e.getMessage())){
                            showMsg(getString(R.string.NETERROR));
                        }
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        contentLv.onRefreshComplete();
                        if (!TextUtils.isEmpty(s)){

                            if (AppUtile.setCode(s,TransactionHistoryActivity.this)){
                                TransactionHistoryBase data = JSON.parseObject(s,TransactionHistoryBase.class);
                                if (page == 1){
                                    dataList.clear();
                                    allpage = data.getData().getPageCount();
                                }
                                if (data.getData().getTransactionRecordRespResultList()!=null&&data.getData().getTransactionRecordRespResultList().size()>0){
                                    for (TransactionHistoryBase.DataBean.TransactionRecordRespResultListBean item:data.getData().getTransactionRecordRespResultList()){
                                        dataList.add(item);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

    @Override
    protected void setView() {
        super.setView();

        adapter = new TransactionHistoryAdapter(dataList);
        contentLv.setMode(PullToRefreshBase.Mode.BOTH);
        //contentLv.setGoneHead();
        //contentLv.setGoneAll();
        contentLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (allpage>page){
                    page++;
                    getData();
                }else{
                    new Handler().postDelayed(new Runnable(){

                        public void run() {

                            //execute the task
                            contentLv.onRefreshComplete();
                        }

                    }, 200);
                }
            }
        });
        contentLv.setAdapter(adapter);
        if (contentLv.getVisibility() == View.VISIBLE) {
            contentLv.setVisibility(View.VISIBLE);
        }
        getData();
    }

    @OnClick(R.id.del_btn)
    public void onViewClicked() {
        finish();
    }
}
