package com.yum.two_yum.view.client.clientfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.BaseFragment;
import com.yum.two_yum.base.ClientOrdersBase;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.controller.adapter.OrdersAdapter;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.pulltorefresh.PullToRefreshBase;
import com.yum.two_yum.utile.pulltorefresh.PullToRefreshScrollView;
import com.yum.two_yum.utile.view.NoSlideListView;
import com.yum.two_yum.view.client.ClientActivity;
import com.yum.two_yum.view.client.orders.OrdersDetailsActivity;
import com.yum.two_yum.view.login.LoginActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @author 余先德
 * @data 2018/4/7
 */

public class OrdersFragment extends BaseFragment {

    @Bind(R.id.orders_lv)
    NoSlideListView ordersLv;
    @Bind(R.id.orders_sv)
    PullToRefreshScrollView ordersSv;
    @Bind(R.id.login_btn)
    TextView loginBtn;
    @Bind(R.id.forgot_password)
    TextView forgotPassword;
    @Bind(R.id.no_log_in)
    LinearLayout noLogIn;
    private FragmentActivity abActivity;
    private View view;
    private OrdersAdapter adapter;
    private ArrayList<ClientOrdersBase.DataBean.OrderRespResultsBean> dataList = new ArrayList<>();
    private int page = 1;
    private int size = 20;
    private int allPage = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        abActivity = (ClientActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_orders_client, container, false);
        ButterKnife.bind(this, view);

        initLayout();
        return view;
    }

    public void setExamination() {
        if (TextUtils.isEmpty(YumApplication.getInstance().getMyInformation().getToken())) {
            noLogIn.setVisibility(View.VISIBLE);
            ordersSv.setVisibility(View.GONE);
            loginBtn.setText(getString(R.string.LOGINCREATEACCOUNT));
            forgotPassword.setTextColor(getResources().getColor(R.color.color_484848));
            String protocol = getResources().getString(R.string.BYPROCEEDINGYOUAGREEWITHTHE);
            String termsofservice = getResources().getString(R.string.TERMSOFSERVICE_);
            String privacypolicy = getResources().getString(R.string.PRIVACYPOLICY);
            SpannableString spanTermsofservice = new SpannableString(termsofservice);
            SpannableString spanPrivacypolicy = new SpannableString(privacypolicy);
            ClickableSpan clickTermsofservice = new ShuoMClickableSpan(termsofservice, getActivity(), true);
            ClickableSpan clickPrivacypolicy = new ShuoMClickableSpan(privacypolicy, getActivity(), false);
            spanTermsofservice.setSpan(clickTermsofservice, 0, termsofservice.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spanPrivacypolicy.setSpan(clickPrivacypolicy, 0, privacypolicy.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            if (getLanguage().equals("zh-hk") || getLanguage().equals("zh-cn")) {
                forgotPassword.setText(protocol);
                forgotPassword.append(spanTermsofservice);
                forgotPassword.append(getResources().getString(R.string.AND));
                forgotPassword.append(spanPrivacypolicy);
                forgotPassword.setMovementMethod(LinkMovementMethod.getInstance());
            } else {
                forgotPassword.setText(protocol + " ");
                forgotPassword.append(spanTermsofservice);
                forgotPassword.append(" " + getResources().getString(R.string.AND) + " ");
                forgotPassword.append(spanPrivacypolicy);
                forgotPassword.setMovementMethod(LinkMovementMethod.getInstance());
            }
            forgotPassword.setHighlightColor(getResources().getColor(android.R.color.transparent));
        } else {
            noLogIn.setVisibility(View.GONE);
            ordersSv.setVisibility(View.VISIBLE);
            //getInput();
        }
    }

    public void Refresh(){
        page = 1;
        getInput();
    }

    /**
     * 初始化数据
     */
    private void initLayout() {
        ordersSv.setMode(PullToRefreshBase.Mode.BOTH);
        adapter = new OrdersAdapter(dataList);
        adapter.setClientNearbyCallBack(new OrdersAdapter.OrdersCallBack() {
            @Override
            public void itemClike(int position, String id) {
                Intent intent = new Intent(getActivity(), OrdersDetailsActivity.class);
                intent.putExtra("id", id);
                getActivity().startActivity(intent);
            }

            @Override
            public void DelClick(String id) {
                OkHttpUtils
                        .post()
                        .url(Constant.ORDER_BUYER_DELETE)
                        .addHeader("Accept-Language", AppUtile.getLanguage())
                        .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                        .addParams("userId", YumApplication.getInstance().getMyInformation().getUid())
                        .addParams("orderIds", id)
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
                                page = 1;
                                getInput();
                            }
                        });
            }
        });

        ordersLv.setAdapter(adapter);
        ordersSv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                page = 1;
                getInput();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                if (allPage > page) {
                    page++;
                    getInput();
                } else {
                    ordersSv.onRefreshComplete();
                }
            }
        });
        getInput();
    }

    private void getInput() {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", YumApplication.getInstance().getMyInformation().getUid());
        map.put("pageIndex", page + "");
        map.put("pageSize", size + "");
        getData(map);
    }

    private void getData(HashMap<String, String> params) {

        OkHttpUtils
                .get()
                .url(Constant.ORDER_LIST_BUYER)
                .addHeader("Accept-Language", AppUtile.getLanguage())
                .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        if (ordersSv!=null)
                        ordersSv.onRefreshComplete();
                        if (AppUtile.getNetWork(e.getMessage())){
                            showMsg(getString(R.string.NETERROR));
                        }
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        if (ordersSv!=null)
                        ordersSv.onRefreshComplete();
                        if (!TextUtils.isEmpty(s)) {

                            if (AppUtile.setCode(s,(BaseActivity) getActivity())) {
                                ClientOrdersBase data = JSON.parseObject(s, ClientOrdersBase.class);
                                if (page == 1) {
                                    allPage = data.getData().getPageCount();
                                    dataList.clear();
                                    ordersSv.setFocusable(true);
                                    ordersSv.setFocusableInTouchMode(true);
                                    ordersSv.requestLayout();
                                }
                                if (data.getData().getOrderRespResults() != null && data.getData().getOrderRespResults().size() > 0) {
                                    for (ClientOrdersBase.DataBean.OrderRespResultsBean item : data.getData().getOrderRespResults()) {
                                        dataList.add(item);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }
    @OnClick(R.id.login_btn)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.putExtra("type",true);
        startActivityForResult(intent,0);
        getActivity().overridePendingTransition(R.anim.activity_open,R.anim.in);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
