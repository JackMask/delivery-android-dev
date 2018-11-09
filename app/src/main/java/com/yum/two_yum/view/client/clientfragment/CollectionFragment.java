package com.yum.two_yum.view.client.clientfragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.BaseFragment;
import com.yum.two_yum.base.NearbyBase;
import com.yum.two_yum.base.SearchNearbyDataBase;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.controller.adapter.ClientNearbyAdapter;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.pulltorefresh.PullToRefreshBase;
import com.yum.two_yum.utile.pulltorefresh.PullToRefreshScrollView;
import com.yum.two_yum.utile.view.NoSlideListView;
import com.yum.two_yum.view.client.ClientActivity;
import com.yum.two_yum.view.client.business.BusinessDetailsActivity;
import com.yum.two_yum.view.client.business.ProvideCreditActivity;
import com.yum.two_yum.view.login.LoginActivity;
import com.yum.two_yum.view.login.RegisteredActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * @author 余先德
 * @data 2018/4/7
 */

public class CollectionFragment extends BaseFragment {


    @Bind(R.id.collection_sv)
    PullToRefreshScrollView collectionSv;
    @Bind(R.id.collection_lv)
    NoSlideListView collectionLv;
    @Bind(R.id.login_btn)
    TextView loginBtn;
    @Bind(R.id.forgot_password)
    TextView forgotPassword;
    @Bind(R.id.no_log_in)
    LinearLayout noLogIn;

    private SearchNearbyDataBase data;
    private FragmentActivity abActivity;
    private View view;
    private ClientNearbyAdapter adapter;
    //private CollectionAdapter adapter;
    public ArrayList<NearbyBase.DataBean.HomeMerchantRespResultListBean> dataList = new ArrayList<>();
    private int page = 1, pageSize = 20;
    private RequestData requestData = new RequestData();
    private int allPage = 1;
    private String selectId;
    private ImageView likeView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        abActivity = (ClientActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_collection, container, false);
        ButterKnife.bind(this, view);

        initLayout();
        return view;
    }

    public void setExamination() {
        if (TextUtils.isEmpty(YumApplication.getInstance().getMyInformation().getToken())) {
            noLogIn.setVisibility(View.VISIBLE);
            collectionSv.setVisibility(View.GONE);
            loginBtn.setText(getString(R.string.LOGINCREATEACCOUNT));
            forgotPassword.setTextColor(getResources().getColor(R.color.color_484848));
            String protocol = getResources().getString(R.string.BYPROCEEDINGYOUAGREEWITHTHE);
            String termsofservice = getResources().getString(R.string.TERMSOFSERVICE_);
            String privacypolicy = getResources().getString(R.string.PRIVACYPOLICY);
            SpannableString spanTermsofservice = new SpannableString(termsofservice);
            SpannableString spanPrivacypolicy = new SpannableString(privacypolicy);
            ClickableSpan clickTermsofservice = new ShuoMClickableSpan(termsofservice, getActivity(),true);
            ClickableSpan clickPrivacypolicy = new ShuoMClickableSpan(privacypolicy, getActivity(),false);
            spanTermsofservice.setSpan(clickTermsofservice, 0, termsofservice.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spanPrivacypolicy.setSpan(clickPrivacypolicy, 0, privacypolicy.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            if (getLanguage().equals("zh-hk")||getLanguage().equals("zh-cn")){
                forgotPassword.setText(protocol);
                forgotPassword.append(spanTermsofservice);
                forgotPassword.append(getResources().getString(R.string.AND));
                forgotPassword.append(spanPrivacypolicy);
                forgotPassword.setMovementMethod(LinkMovementMethod.getInstance());
            }else {
                forgotPassword.setText(protocol + " ");
                forgotPassword.append(spanTermsofservice);
                forgotPassword.append(" " + getResources().getString(R.string.AND) + " ");
                forgotPassword.append(spanPrivacypolicy);
                forgotPassword.setMovementMethod(LinkMovementMethod.getInstance());
            }
            forgotPassword.setHighlightColor(getResources().getColor(android.R.color.transparent));
        } else {
            noLogIn.setVisibility(View.GONE);
            collectionSv.setVisibility(View.VISIBLE);
            //getData();
        }
    }



    /**
     * 初始化数据
     */
    private void initLayout() {
        collectionSv.setMode(PullToRefreshBase.Mode.BOTH);

        adapter = new ClientNearbyAdapter(dataList);
        collectionSv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                page = 1;
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                if (allPage > page) {
                    page++;
                    getData();
                } else {
                    collectionSv.onRefreshComplete();
                }
            }
        });
        collectionLv.setAdapter(adapter);
        collectionSv.setGoneAll();
        adapter.setClientNearbyCallBack(new ClientNearbyAdapter.NearbyCallBack() {


            @Override
            public void itemClick(int position, String id, boolean isLike, int state,ImageView liekview) {
                likeView = liekview;
                Intent intent = new Intent(getContext(), BusinessDetailsActivity.class);
                intent.putExtra("data",data);
                intent.putExtra("id", id);
                selectId = id;
                intent.putExtra("type", state);
                intent.putExtra("like", isLike);
                startActivityForResult(intent, 1);
//                Intent intent = new Intent(getContext(), BusinessDetailsActivity.class);
//                intent.putExtra("id", id);
//                intent.putExtra("like", isLike);
//                startActivity(intent);
            }

            @Override
            public void itemCollectionClick(final ImageView imageView, final NearbyBase.DataBean.HomeMerchantRespResultListBean like) {
                Map<String, String> params = new HashMap<>();
                params.put("likeType", like.isLike() ? "CANCEL" : "LIKE");
                params.put("userId", YumApplication.getInstance().getMyInformation().getUid());
                params.put("salerUserId", like.getUserId() + "");
                OkHttpUtils
                        .post()
                        .url(Constant.ADD_LIKE)
                        .addHeader("Accept-Language", AppUtile.getLanguage())
                        .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                        .params(params)
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
                                if (!TextUtils.isEmpty(s)) {
                                    if (AppUtile.setCode(s,(BaseActivity)getActivity())) {
                                        page = 1;
                                        getData();
                                        if (like.isLike()) {
                                            ((ClientActivity) getActivity()).clickCollection(like.getUserId() , 1);
                                        } else {
                                            ((ClientActivity) getActivity()).clickCollection(like.getUserId() , 0);
                                        }
                                    }
                                }
                            }
                        });
            }
        });
        requestData.setLat("0");
        requestData.setLng("0");
        requestData.setPageSize(pageSize);
    }
    public void setAddressData(SearchNearbyDataBase data){
        this.data = data;
    }
    public void RefreshData(){
        page = 1;
        getData();
    }
    public void setLike(int type){
        if (!TextUtils.isEmpty(selectId)) {
            for (int i = 0; i < dataList.size(); i++) {
                if (dataList.get(i).getUserId() == Integer.valueOf(selectId)) {
                    if (type == 0) {
                        dataList.get(i).setLike(true);
                        likeView.setImageResource(R.mipmap.heart_red_big);
                    } else if (type == 1) {
                        dataList.get(i).setLike(false);
                        likeView.setImageResource(R.mipmap.heart_white_big);
                    }
                }
            }
        }
    }
    private void getData() {
        requestData.setPageIndex(page);
        OkHttpUtils
                .postString()
                .mediaType(MediaType.parse("application/json"))
                .url(Constant.LIKE_LIST)
                .addHeader("Accept-Language", AppUtile.getLanguage())
                .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                .content(JSON.toJSONString(requestData))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        if (AppUtile.getNetWork(e.getMessage())){
                            showMsg(getString(R.string.NETERROR));
                        }
                        if (collectionSv!=null)
                            collectionSv.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        if (collectionSv!=null)
                            collectionSv.onRefreshComplete();
                        if (!TextUtils.isEmpty(s)) {
                            if (AppUtile.setCode(s,(BaseActivity) getActivity())) {
                                NearbyBase data = JSON.parseObject(s, NearbyBase.class);
                                allPage = data.getData().getPageCount();
                                if (page == 1) {
                                    dataList.clear();
                                    collectionSv.setFocusable(true);
                                    collectionSv.setFocusableInTouchMode(true);
                                    collectionSv.requestLayout();
                                }
                                if (data.getData().getHomeMerchantRespResultList() != null && data.getData().getHomeMerchantRespResultList().size() > 0) {
                                    for (NearbyBase.DataBean.HomeMerchantRespResultListBean item : data.getData().getHomeMerchantRespResultList()) {
                                        dataList.add(item);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });


    }

    public void setLatLong(String lat, String Long) {
        requestData.setLat(lat);
        requestData.setLng(Long);
        getData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.login_btn)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.putExtra("type",true);
        startActivityForResult(intent,0);
        getActivity().overridePendingTransition(R.anim.activity_open,R.anim.in);
    }


    private class RequestData {
        private String lat;
        private String lng;
        private int pageIndex;
        private int pageSize;
        private String userId = YumApplication.getInstance().getMyInformation().getUid();

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

}
