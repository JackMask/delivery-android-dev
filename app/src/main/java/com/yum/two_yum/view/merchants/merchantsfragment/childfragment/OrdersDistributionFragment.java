package com.yum.two_yum.view.merchants.merchantsfragment.childfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.OrdersDetailsBase;
import com.yum.two_yum.base.OrdersDistributionBase;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.controller.adapter.OrdersDistributionAdapter;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.pulltorefresh.PullToRefreshBase;
import com.yum.two_yum.utile.pulltorefresh.PullToRefreshScrollView;
import com.yum.two_yum.view.merchants.MerchantsActivity;
import com.yum.two_yum.view.merchants.merchantsOrders.DagDetailsActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * @author 余先德
 * @data 2018/4/12
 */

public class OrdersDistributionFragment extends Fragment {


    @Bind(R.id.point_1)
    View point1;
    @Bind(R.id.bag_btn_1)
    RelativeLayout bagBtn1;
    @Bind(R.id.point_2)
    View point2;
    @Bind(R.id.bag_btn_2)
    RelativeLayout bagBtn2;
    @Bind(R.id.point_3)
    View point3;
    @Bind(R.id.bag_btn_3)
    RelativeLayout bagBtn3;
    @Bind(R.id.point_4)
    View point4;
    @Bind(R.id.bag_btn_4)
    RelativeLayout bagBtn4;
    @Bind(R.id.point_5)
    View point5;
    @Bind(R.id.bag_btn_5)
    RelativeLayout bagBtn5;
    @Bind(R.id.point_6)
    View point6;
    @Bind(R.id.bag_btn_6)
    RelativeLayout bagBtn6;
    @Bind(R.id.point_7)
    View point7;
    @Bind(R.id.bag_btn_7)
    RelativeLayout bagBtn7;
    @Bind(R.id.point_8)
    View point8;
    @Bind(R.id.bag_btn_8)
    RelativeLayout bagBtn8;
    @Bind(R.id.point_9)
    View point9;
    @Bind(R.id.bag_btn_9)
    RelativeLayout bagBtn9;
    @Bind(R.id.point_10)
    View point10;
    @Bind(R.id.bag_btn_10)
    RelativeLayout bagBtn10;
    @Bind(R.id.point_11)
    View point11;
    @Bind(R.id.bag_btn_11)
    RelativeLayout bagBtn11;
    @Bind(R.id.num_tv11)
    TextView numTv11;
    @Bind(R.id.scroll_view)
    PullToRefreshScrollView scrollView;


    private MerchantsActivity abActivity;
    private View view;
    private OrdersDistributionAdapter adapter;
    private List<RelativeLayout> bagBtns = new ArrayList<>();
    private List<View> points = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        abActivity = (MerchantsActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_orders_child_distribution, container, false);
        ButterKnife.bind(this, view);
        setView();
        return view;
    }

    public void refresh(){
        getData();
    }
    private void setView() {
        bagBtns.clear();
        points.clear();
        scrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        //scrollView.setGoneHead();
       scrollView.setGoneAll();
        scrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                getData();
                ((MerchantsActivity)getActivity()).refreshInfo();
            }
        });
//        scrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
//                scrollView.onRefreshComplete();
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
//                scrollView.onRefreshComplete();
//            }
//        });
        numTv11.setText("#201~"+getString(R.string.MORE));
        point1.setTag(1);
        point2.setTag(2);
        point3.setTag(3);
        point4.setTag(4);
        point5.setTag(5);
        point6.setTag(6);
        point7.setTag(7);
        point8.setTag(8);
        point9.setTag(9);
        point10.setTag(10);
        point11.setTag(11);
        bagBtns.add(bagBtn1);
        bagBtns.add(bagBtn2);
        bagBtns.add(bagBtn3);
        bagBtns.add(bagBtn4);
        bagBtns.add(bagBtn5);
        bagBtns.add(bagBtn6);
        bagBtns.add(bagBtn7);
        bagBtns.add(bagBtn8);
        bagBtns.add(bagBtn9);
        bagBtns.add(bagBtn10);
        bagBtns.add(bagBtn11);
        points.add(point1);
        points.add(point2);
        points.add(point3);
        points.add(point4);
        points.add(point5);
        points.add(point6);
        points.add(point7);
        points.add(point8);
        points.add(point9);
        points.add(point10);
        points.add(point11);
        for (View item:points){
            item.setVisibility(View.GONE);
        }
        for (int i = 0 ; i < bagBtns.size();i++){
            bagBtns.get(i).setOnClickListener(new BagClick(i+1));
        }
        getData();
    }

    private class BagClick implements View.OnClickListener{
        private int num;
        public BagClick(int num){
            this.num = num;
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), DagDetailsActivity.class);
            intent.putExtra("id",num+"");
            getActivity().startActivityForResult(intent,999);
        }
    }
    public void setData(List<OrdersDistributionBase.DataBean.DispatchRespResultsBean> pockets){

        if (pockets!=null){
            boolean conType = true;
            a:for (View item:points){
                if (item==null){
                    conType = false;
                    break a;
                }
            }
            if (conType) {
                for (View item : points) {
                    item.setVisibility(View.GONE);
                }
                for (int con = 0; con < (pockets.size() > 11 ? 11 : pockets.size()); con++) {
                    for (View item : points) {
                        if (pockets.get(con).getBagNo() == ((Integer) item.getTag())) {
                            if (!pockets.get(con).isEmpty()) {
                                item.setVisibility(View.VISIBLE);
                            } else {
                                item.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            }

        }
    }
    private void getData() {
        OkHttpUtils
                .get()
                .url(Constant.MERCHANT_ORDER_DISPATCH_BAG_LIST)
                .addHeader("Accept-Language", AppUtile.getLanguage())
                .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                .addParams("salerUserId", YumApplication.getInstance().getMyInformation().getUid())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        if (AppUtile.getNetWork(e.getMessage())){
                            ((BaseActivity)getActivity()).showMsg(getString(R.string.NETERROR));
                        }
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        scrollView.onRefreshComplete();
                        if (!TextUtils.isEmpty(s)){

                            if (AppUtile.setCode(s,(BaseActivity)getActivity())){
                                OrdersDistributionBase data = JSON.parseObject(s, OrdersDistributionBase.class);
                                for (View item:points){
                                    item.setVisibility(View.GONE);
                                }
                                for (int con = 0 ; con < (data.getData().getDispatchRespResults().size()>11?11:data.getData().getDispatchRespResults().size());con++){
                                    for (View item:points) {
                                        if (data.getData().getDispatchRespResults().get(con).getBagNo() == ((Integer) item.getTag())){
                                            if (!data.getData().getDispatchRespResults().get(con).isEmpty()) {
                                                item.setVisibility(View.VISIBLE);
                                            }else{
                                                item.setVisibility(View.GONE);
                                            }
                                        }
                                    }
                                }
                                boolean type = false;
                                for (View item:points){
                                    if (item.getVisibility() == View.VISIBLE){
                                        type = true;

                                        break;
                                    }
                                }
                                if (type){
                                    ((MerchantsActivity)getActivity()).setDistribution(true);
                                }else{
                                    ((MerchantsActivity)getActivity()).setDistribution(false);
                                }
                                ((MerchantsActivity)getActivity()).showDian();
                            }
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
