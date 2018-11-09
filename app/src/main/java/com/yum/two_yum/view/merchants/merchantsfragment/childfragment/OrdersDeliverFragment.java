package com.yum.two_yum.view.merchants.merchantsfragment.childfragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.BaseFragment;
import com.yum.two_yum.base.OrdersDistributionBase;
import com.yum.two_yum.base.OrdersNewBase;
import com.yum.two_yum.base.input.CancelOrderBase;
import com.yum.two_yum.base.input.OrdersNewInput;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.controller.adapter.OrdersDliverAdapter;
import com.yum.two_yum.controller.adapter.callback.OrdersMerchantsCallBack;
import com.yum.two_yum.utile.ActionSheetDialog;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.pulltorefresh.PullToRefreshBase;
import com.yum.two_yum.utile.pulltorefresh.PullToRefreshListView;
import com.yum.two_yum.view.dialog.PromptDialog;
import com.yum.two_yum.view.merchants.MerchantsActivity;
import com.yum.two_yum.view.merchants.merchantsOrders.DagDetailsActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * @author 余先德
 * @data 2018/4/12
 */

public class OrdersDeliverFragment extends BaseFragment {


    @Bind(R.id.content_lv)
    PullToRefreshListView contentLv;

    private MerchantsActivity abActivity;
    private View view;
    private OrdersDliverAdapter adapter;
    private List<OrdersNewBase.DataBean.MerchantTodayOrderRespResultsBean> dataList = new ArrayList<>();
    private int page = 1;
    private int size = 20;
    private int allPage = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        abActivity = (MerchantsActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_orders_child, container, false);
        ButterKnife.bind(this, view);
        setView();
        return view;
    }
    public void refreshData(){
        page= 1;
        webData();
    }
    private void setView() {

        adapter = new OrdersDliverAdapter(dataList);
        contentLv.setMode(PullToRefreshBase.Mode.BOTH);
        contentLv.setGoneAll();
        contentLv.setGoneHead();
        contentLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page= 1;
                webData();
                ((MerchantsActivity)getActivity()).refreshInfo();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (allPage>page){
                    page++;
                    webData();
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
        adapter.setCallBack(new OrdersMerchantsCallBack() {
            @Override
            public void clickOk(final int position, String OrderId) {
                ((MerchantsActivity)getActivity()).showOk(2,OrderId);


            }

            @Override
            public void clickCall(int position,final String num) {
                ((MerchantsActivity)getActivity()).showPhone(num);
            }

            @Override
            public void clickCancel(int position, final String id) {
                ((MerchantsActivity)getActivity()).showCancel(2,id);
            }

            @Override
            public void clickaddres(int position, String latitude, String longitude,String addressStr) {
                if (isAvilible(getContext(),"com.google.android.apps.maps")) {
                    StringBuffer stringBuffer = new StringBuffer("google.navigation:q=").append(addressStr).append("&mode=d");
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(stringBuffer.toString()));
                    i.setPackage("com.google.android.apps.maps");
                    startActivity(i);
                }else {
                    Intent intent = new Intent(getContext(), PromptDialog.class);
                    intent.putExtra("content",getResources().getString(R.string.GOOGLEMAP));
                    startActivityForResult(intent,0);
                }
            }
        });
        webData();
    }
    /*  * 检查手机上是否安装了指定的软件
      * @param context
      * @param packageName：应用包名
              */
    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }
    private void cancelOreder(String OrderId,String note){
        CancelOrderBase inputData = new CancelOrderBase();
        inputData.setCancelNote(note);
        inputData.setOrderId(OrderId);
        inputData.setOrderStatus("CANCEL");
        String content = JSON.toJSONString(inputData);
        OkHttpUtils
                .postString()
                .url(Constant.MERCHANT_ORDER_SETUP)
                .addHeader("Accept-Language", AppUtile.getLanguage())
                .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                .mediaType(MediaType.parse("application/json"))
                .content(content)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        if (AppUtile.getNetWork(e.getMessage())){
                            showMsg(getString(R.string.NETERROR));
                        }
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        if (AppUtile.setCode(s,(BaseActivity)getActivity())){
                            page = 1;
                            webData();
                        }
                    }
                });
    }
    public void refresh(){
        page = 1;
        webData();
    }
    private void webData(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        OrdersNewInput data = new OrdersNewInput();
        data.setDoneTime(simpleDateFormat.format(date));
        data.setPageSize(size);
        data.setPageIndex(page);
        data.setSalerUserId(YumApplication.getInstance().getMyInformation().getUid());
        data.setStatus("DISPATCH");
        //getData(JSON.toJSONString(data));
        getData();
    }

    private void getData(){
        System.out.println(YumApplication.getInstance().getMyInformation().getToken());

        OkHttpUtils
                .get()
                .url(Constant.MERCHANT_COMPLETE_ORDER_LIST)
                .addHeader("Accept-Language", AppUtile.getLanguage())
                .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                .addParams("pageIndex", page+"")
                .addParams("pageSize", size+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        ((BaseActivity)getActivity()).dismissLoading();
                        if (AppUtile.getNetWork(e.getMessage())){
                            showMsg(getString(R.string.NETERROR));
                        }
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        ((BaseActivity)getActivity()).dismissLoading();
                        if (contentLv!=null)
                        contentLv.onRefreshComplete();
                        if (!TextUtils.isEmpty(s)){

                            if (AppUtile.setCode(s,(BaseActivity)getActivity())){
                                OrdersNewBase data = JSON.parseObject(s,OrdersNewBase.class);
                                allPage = data.getData().getPageCount();
                                if (page == 1){
                                    dataList.clear();

                                }
                                if (data.getData().getMerchantTodayOrderRespResults()!=null&&data.getData().getMerchantTodayOrderRespResults().size()>0){
                                    for (OrdersNewBase.DataBean.MerchantTodayOrderRespResultsBean item:data.getData().getMerchantTodayOrderRespResults()){
                                        dataList.add(item);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }

                    }
                });
//
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}