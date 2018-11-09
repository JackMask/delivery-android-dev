package com.yum.two_yum.view.merchants.merchantsOrders;

import android.content.Intent;
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
import com.yum.two_yum.base.input.CancelOrderBase;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.controller.adapter.DagDetailsAdapter;
import com.yum.two_yum.controller.adapter.callback.DagDetailsCallBack;
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

import static com.yum.two_yum.view.merchants.merchantsfragment.childfragment.OrdersNewFragment.isAvilible;

/**
 * @author 余先德
 * @data 2018/4/13
 */

public class DagDetailsActivity extends BaseActivity {

    @Bind(R.id.content_lv)
    PullToRefreshListView contentLv;
    @Bind(R.id.title_tv)
    TextView titleTv;
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
    @Bind(R.id.no_data_layout1)
    View noDataLayout1;
    @Bind(R.id.sold_btn)
    TextView soldBtn;
    @Bind(R.id.wrong_btn)
    TextView wrongBtn;
    @Bind(R.id.customer_btn)
    TextView customerBtn;
    @Bind(R.id.address_btn)
    TextView addressBtn;
    @Bind(R.id.cannot_btn)
    TextView cannotBtn;
    @Bind(R.id.cancel_cancel_btn)
    TextView cancelCancelBtn;
    @Bind(R.id.cancel_layout)
    LinearLayout cancelLayout;

    private DagDetailsAdapter adapter;
    private List<OrdersNewBase.DataBean.MerchantTodayOrderRespResultsBean> dataList = new ArrayList<>();
    private int page = 1;
    private int size = 8;
    private int allPage = 1;

    private String id;
    private String phoneNum;
    private String OrderId;
    private int num;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dag_details);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
        String[] titles = {getString(R.string.BAG1), getString(R.string.BAG2), getString(R.string.BAG3), getString(R.string.BAG4),
                getString(R.string.BAG5), getString(R.string.BAG6), getString(R.string.BAG7), getString(R.string.BAG8), getString(R.string.BAG9),
                getString(R.string.BAG10), getString(R.string.BAG11)};
        if (!TextUtils.isEmpty(getIntent().getStringExtra("id"))) {
            num = Integer.valueOf(getIntent().getStringExtra("id"));
            titleTv.setText(titles[num - 1]);
        }
        setView();
    }

    @Override
    protected void setView() {
        super.setView();

        contentLv.setMode(PullToRefreshBase.Mode.BOTH);
        adapter = new DagDetailsAdapter(dataList);
        contentLv.setAdapter(adapter);
        contentLv.setGoneHead();
        contentLv.setGoneAll();
        contentLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (allPage > page) {
                    page++;
                    getData();
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
        adapter.setCallBack(new DagDetailsCallBack() {
            @Override
            public void clickCall(int position, final String num) {
                phoneTv.setText(num);
                phoneNum = num;
                noDataLayout2.setVisibility(View.VISIBLE);
                phoneLayout.setVisibility(View.VISIBLE);
                phoneLayout.setAnimation(AnimationUtil.fromBottomToNow());

            }

            @Override
            public void clickCancel(int position, final String id) {
                OrderId = id;
                noDataLayout1.setVisibility(View.VISIBLE);
                cancelLayout.setVisibility(View.VISIBLE);
                cancelLayout.setAnimation(AnimationUtil.fromBottomToNow());

            }

            @Override
            public void clickOk(int position, String id, final TextView okBtn, String type) {
                CancelOrderBase inputData = new CancelOrderBase();
                inputData.setOrderId(id);
                inputData.setOrderStatus(type);
                String content = JSON.toJSONString(inputData);
                showLoading();
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
                                dismissLoading();
                                if (AppUtile.getNetWork(e.getMessage())) {
                                    showMsg(getString(R.string.NETERROR));

                                }
                            }

                            @Override
                            public void onResponse(String s, int i) {
                                if (AppUtile.setCode(s, DagDetailsActivity.this)) {
                                    //okBtn.setText(getResources().getString(R.string.DELIVER));
                                    page = 1;
                                    getData();
                                }
                            }
                        });
            }

            @Override
            public void clickaddres(int position, String latitude, String longitude, String adddress) {

                if (isAvilible(DagDetailsActivity.this, "com.google.android.apps.maps")) {
                    StringBuffer stringBuffer = new StringBuffer("google.navigation:q=").append(adddress).append("&mode=d");
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(stringBuffer.toString()));
                    i.setPackage("com.google.android.apps.maps");
                    startActivity(i);
                } else {
                    Intent intent = new Intent(DagDetailsActivity.this, PromptDialog.class);
                    intent.putExtra("content", getResources().getString(R.string.GOOGLEMAP));
                    startActivityForResult(intent, 0);
                }
            }
        });
        getData();
    }

    private void cancelOreder( String note) {
        CancelOrderBase inputData = new CancelOrderBase();
        inputData.setCancelNote(note);
        inputData.setOrderId(OrderId);
        inputData.setOrderStatus("CANCEL");
        String content = JSON.toJSONString(inputData);
        showLoading();
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
                        dismissLoading();
                        if (AppUtile.getNetWork(e.getMessage())) {
                            showMsg(getString(R.string.NETERROR));
                        }
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        if (AppUtile.setCode(s, DagDetailsActivity.this)) {
                            page = 1;
                            getData();
                        }
                    }
                });
    }

    private void getData() {
        OkHttpUtils
                .get()
                .addHeader("Accept-Language", AppUtile.getLanguage())
                .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                .url(Constant.MERCHANT_ORDER_DISPATCH_LIST)
                .addParams("salerUserId", YumApplication.getInstance().getMyInformation().getUid())
                .addParams("bagNo", id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        contentLv.onRefreshComplete();
                        dismissLoading();
                        if (AppUtile.getNetWork(e.getMessage())) {
                            showMsg(getString(R.string.NETERROR));
                        }
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        dismissLoading();
                        contentLv.onRefreshComplete();
                        if (!TextUtils.isEmpty(s)) {

                            if (AppUtile.setCode(s, DagDetailsActivity.this)) {
                                OrdersNewBase data = JSON.parseObject(s, OrdersNewBase.class);
                                allPage = data.getData().getPageCount();
                                if (page == 1) {
                                    dataList.clear();

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



    @OnClick({R.id.cannot_btn,R.id.del_btn,R.id.no_data_layout2, R.id.call_phone_btn, R.id.sms_phone_btn, R.id.cancel_btn1, R.id.no_data_layout1, R.id.sold_btn, R.id.wrong_btn, R.id.customer_btn, R.id.address_btn, R.id.cancel_cancel_btn})
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
            case R.id.cancel_cancel_btn:
                noDataLayout1.setVisibility(View.GONE);
                cancelLayout.setVisibility(View.GONE);
                cancelLayout.setAnimation(AnimationUtil.fromNowToBottom());
                break;
            case R.id.cancel_btn1:
                noDataLayout2.setVisibility(View.GONE);
                phoneLayout.setVisibility(View.GONE);
                phoneLayout.setAnimation(AnimationUtil.fromNowToBottom());
                break;
            case R.id.no_data_layout1:
                noDataLayout1.setVisibility(View.GONE);
                cancelLayout.setVisibility(View.GONE);
                cancelLayout.setAnimation(AnimationUtil.fromNowToBottom());
                break;
            case R.id.sold_btn:
                noDataLayout1.setVisibility(View.GONE);
                cancelLayout.setVisibility(View.GONE);
                cancelLayout.setAnimation(AnimationUtil.fromNowToBottom());
                cancelOreder("售完");
                break;
            case R.id.wrong_btn:
                noDataLayout1.setVisibility(View.GONE);
                cancelLayout.setVisibility(View.GONE);
                cancelLayout.setAnimation(AnimationUtil.fromNowToBottom());
                cancelOreder("错误订单");
                break;
            case R.id.customer_btn:
                noDataLayout1.setVisibility(View.GONE);
                cancelLayout.setVisibility(View.GONE);
                cancelLayout.setAnimation(AnimationUtil.fromNowToBottom());
                cancelOreder("客户要求取消");
                break;
            case R.id.address_btn:
                noDataLayout1.setVisibility(View.GONE);
                cancelLayout.setVisibility(View.GONE);
                cancelLayout.setAnimation(AnimationUtil.fromNowToBottom());
                cancelOreder("地址不正确");
                break;
            case R.id.cannot_btn:
                noDataLayout1.setVisibility(View.GONE);
                cancelLayout.setVisibility(View.GONE);
                cancelLayout.setAnimation(AnimationUtil.fromNowToBottom());
                cancelOreder("无法联系");
                break;

        }
    }
}
