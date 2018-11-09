package com.yum.two_yum.view.merchants.statistics;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.OrdersNewBase;
import com.yum.two_yum.base.input.OrdersNewInput;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.controller.adapter.OrdersDliverAdapter;
import com.yum.two_yum.controller.adapter.callback.OrdersMerchantsCallBack;
import com.yum.two_yum.utile.ActionSheetDialog;
import com.yum.two_yum.utile.AnimationUtil;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.pulltorefresh.PullToRefreshBase;
import com.yum.two_yum.utile.pulltorefresh.PullToRefreshListView;
import com.yum.two_yum.view.dialog.PromptDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * @author 余先德
 * @data 2018/4/14
 */

public class OrderHistoryDetailsActivity extends BaseActivity {


    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.content_lv)
    PullToRefreshListView contentLv;
    @Bind(R.id.no_data_layout2)
    View noDataLayout2;
    @Bind(R.id.phone_tv)
    TextView phoneTv;
    @Bind(R.id.call_phone_btn)
    TextView callPhoneBtn;
    @Bind(R.id.sms_phone_btn)
    TextView smsPhoneBtn;
    @Bind(R.id.cancel_btn1)
    TextView cancelBtn1;
    @Bind(R.id.phone_layout)
    LinearLayout phoneLayout;
    private OrdersDliverAdapter adapter;
    private List<OrdersNewBase.DataBean.MerchantTodayOrderRespResultsBean> dataList = new ArrayList<>();
    private static final String CANCEL = "CANCEL";
    private static final String COMPLETE = "COMPLETE";

    private int page = 1;
    private int size = 20;
    private int allPage = 1;
    private String type;
    private String dataStr;
    private String phoneNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_details);
        ButterKnife.bind(this);
        dataStr = getIntent().getStringExtra("data");
        setView();
    }

    @Override
    protected void setView() {
        super.setView();
        type = CANCEL;
        if (!TextUtils.isEmpty(getIntent().getStringExtra("title"))) {
            titleTv.setText(getIntent().getStringExtra("title"));
            if (getIntent().getStringExtra("title").equals(getResources().getString(R.string.THENCANCELED))) {
                type = CANCEL;
            } else {
                type = COMPLETE;
            }
        }

        adapter = new OrdersDliverAdapter(dataList);
        adapter.setConType(true);
        adapter.setCancel(type);
        contentLv.setMode(PullToRefreshBase.Mode.BOTH);
        contentLv.setAdapter(adapter);
        contentLv.setGoneAll();
        contentLv.setGoneHead();
        contentLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                webData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                if (allPage > page) {
                    page++;
                    webData();
                } else {
                    new Handler().postDelayed(new Runnable() {

                        public void run() {

                            //execute the task
                            contentLv.onRefreshComplete();
                        }

                    }, 200);
                }
            }
        });
        adapter.setCallBack(new OrdersMerchantsCallBack() {
            @Override
            public void clickOk(int position, String OrderId) {

            }

            @Override
            public void clickCall(int position, final String num) {
                phoneTv.setText(num);
                phoneNum = num;
                noDataLayout2.setVisibility(View.VISIBLE);
                phoneLayout.setVisibility(View.VISIBLE);
                phoneLayout.setAnimation(AnimationUtil.fromBottomToNow());
            }

            @Override
            public void clickCancel(int position, String id) {

            }

            @Override
            public void clickaddres(int position, String latitude, String longitude, String addressStr) {
                if (isAvilible(OrderHistoryDetailsActivity.this,"com.google.android.apps.maps")) {
                    StringBuffer stringBuffer = new StringBuffer("google.navigation:q=").append(addressStr).append("&mode=d");
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(stringBuffer.toString()));
                    i.setPackage("com.google.android.apps.maps");
                    startActivity(i);
                }else {
                    Intent intent = new Intent(OrderHistoryDetailsActivity.this, PromptDialog.class);
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
    private void webData() {
        OrdersNewInput data = new OrdersNewInput();
        data.setDoneTime(dataStr);
        data.setPageSize(size);
        data.setPageIndex(page);
        data.setSalerUserId(YumApplication.getInstance().getMyInformation().getUid());
        data.setStatus(type);
        getData(JSON.toJSONString(data));
    }

    private void getData(String jsonStr) {
        OkHttpUtils
                .postString()
                .mediaType(MediaType.parse("application/json"))
                .addHeader("Accept-Language", AppUtile.getLanguage())
                .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                .url(Constant.MENU_TODAY)
                .content(jsonStr)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        contentLv.onRefreshComplete();
                        if (AppUtile.getNetWork(e.getMessage())) {
                            showMsg(getString(R.string.NETERROR));
                        }
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        contentLv.onRefreshComplete();
                        if (!TextUtils.isEmpty(s)) {

                            if (AppUtile.setCode(s, OrderHistoryDetailsActivity.this)) {
                                OrdersNewBase data = JSON.parseObject(s, OrdersNewBase.class);
                                if (page == 1) {
                                    dataList.clear();
                                    allPage = data.getData().getPageCount();
                                }
                                if (data.getData().getMerchantTodayOrderRespResults() != null && data.getData().getMerchantTodayOrderRespResults().size() > 0) {
                                    for (OrdersNewBase.DataBean.MerchantTodayOrderRespResultsBean item : data.getData().getMerchantTodayOrderRespResults()) {
                                        dataList.add(item);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }



    @OnClick({R.id.del_btn,R.id.no_data_layout2, R.id.call_phone_btn, R.id.sms_phone_btn, R.id.cancel_btn1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.del_btn:
                finish();
                break;
            case R.id.no_data_layout2:
                noDataLayout2.setVisibility(View.GONE);
                phoneLayout.setVisibility(View.GONE);
                phoneLayout.setAnimation(AnimationUtil.fromNowToBottom());
                break;
            case R.id.call_phone_btn:
                noDataLayout2.setVisibility(View.GONE);
                phoneLayout.setVisibility(View.GONE);
                phoneLayout.setAnimation(AnimationUtil.fromNowToBottom());
                Intent intent = new Intent(); // 意图对象：动作 + 数据
                intent.setAction(Intent.ACTION_CALL); // 设置动作
                Uri data = Uri.parse("tel:" + phoneNum); // 设置数据
                intent.setData(data);
                startActivity(intent); // 激活Activity组件
                break;
            case R.id.sms_phone_btn:
                noDataLayout2.setVisibility(View.GONE);
                phoneLayout.setVisibility(View.GONE);
                phoneLayout.setAnimation(AnimationUtil.fromNowToBottom());
                Uri uri = Uri.parse("smsto:" + phoneNum);
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                startActivity(it);
                break;
            case R.id.cancel_btn1:
                noDataLayout2.setVisibility(View.GONE);
                phoneLayout.setVisibility(View.GONE);
                phoneLayout.setAnimation(AnimationUtil.fromNowToBottom());
                break;
        }
    }
}
