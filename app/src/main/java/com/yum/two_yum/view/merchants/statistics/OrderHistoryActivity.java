package com.yum.two_yum.view.merchants.statistics;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;

import com.alibaba.fastjson.JSON;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.OrderHistoryBase;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.controller.adapter.OrderHistoryAdapter;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.pulltorefresh.PullToRefreshBase;
import com.yum.two_yum.utile.pulltorefresh.PullToRefreshScrollView;
import com.yum.two_yum.utile.view.NoSlideListView;
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

public class OrderHistoryActivity extends BaseActivity {
    @Bind(R.id.content_lv)
    NoSlideListView contentLv;
    @Bind(R.id.scroll_view)
    PullToRefreshScrollView scroll_view;

    private OrderHistoryAdapter adapter ;
    private List<OrderHistoryBase.DataBean.MerchantStatisticsRespResultsBean> dataList = new ArrayList<>();
    private int page =1;
    private int size = 15;
    private int allpage = 1;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        ButterKnife.bind(this);
        setView();
    }

    @Override
    protected void setView() {
        super.setView();

        scroll_view.setMode(PullToRefreshBase.Mode.BOTH);
        scroll_view.setGoneHead();
        scroll_view.setGoneAll();
        scroll_view.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                page = 1;
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                if (allpage > page) {
                    page ++;
                    getData();
                }else{
                    new Handler().postDelayed(new Runnable(){

                        public void run() {

                            //execute the task
                            scroll_view.onRefreshComplete();
                        }

                    }, 200);
                }
            }
        });
        adapter = new OrderHistoryAdapter(dataList);
        contentLv.setAdapter(adapter);
        getData();
    }

    private void getData(){
        OkHttpUtils
                .get()
                .url(Constant.MERCHANT_ORDER_STATISTICS_LIST)
                .addHeader("Accept-Language", AppUtile.getLanguage())
                .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                .addParams("userId", YumApplication.getInstance().getMyInformation().getUid())
                .addParams("pageIndex", page+"")
                .addParams("pageSize", size+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        scroll_view.onRefreshComplete();
                        if (AppUtile.getNetWork(e.getMessage())){
                            showMsg(getString(R.string.NETERROR));
                        }
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        scroll_view.onRefreshComplete();
                        if (!TextUtils.isEmpty(s)){

                            if (AppUtile.setCode(s,OrderHistoryActivity.this)){
                                OrderHistoryBase data = JSON.parseObject(s,OrderHistoryBase.class);
                                if (page == 1){
                                    dataList.clear();
                                    allpage = data.getData().getPageCount();
                                }
                                if (data.getData().getMerchantStatisticsRespResults()!=null&&data.getData().getMerchantStatisticsRespResults().size()>0){
                                    for (OrderHistoryBase.DataBean.MerchantStatisticsRespResultsBean item:data.getData().getMerchantStatisticsRespResults()){
                                        dataList.add(item);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

    }

    @OnClick({R.id.del_btn,R.id.to_day_btn})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.del_btn:
                finish();
                break;
            case R.id.to_day_btn:
                scroll_view.getRefreshableView().smoothScrollTo(0,0);
                break;
        }

    }
}
