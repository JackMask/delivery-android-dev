package com.yum.two_yum.view.client.clientfragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.BaseFragment;
import com.yum.two_yum.base.BaseReturn;
import com.yum.two_yum.base.NearbyBase;
import com.yum.two_yum.base.SearchNearbyDataBase;
import com.yum.two_yum.base.input.NearbyInput;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.controller.adapter.ClientNearbyAdapter;
import com.yum.two_yum.controller.adapter.callback.ClientNearbyCallBack;
import com.yum.two_yum.utile.AnimationUtil;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.PullToRefreshScrollViewCallBack;
import com.yum.two_yum.utile.pulltorefresh.PullToRefreshBase;
import com.yum.two_yum.utile.pulltorefresh.PullToRefreshScrollView;
import com.yum.two_yum.utile.view.NoSlideListView;
import com.yum.two_yum.view.client.ClientActivity;
import com.yum.two_yum.view.client.business.BusinessDetailsActivity;
import com.yum.two_yum.view.client.clientorder.FilterActivity;
import com.yum.two_yum.view.client.clientorder.SearchNearbyActivity;
import com.yum.two_yum.view.guide.ReleaseActivity;
import com.yum.two_yum.view.login.LoginActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

public class NearbyFragment extends BaseFragment {


    @Bind(R.id.nearby_lv)
    NoSlideListView nearbyLv;
    @Bind(R.id.scroll_view)
    PullToRefreshScrollView scrollView;
    @Bind(R.id.title_view)
    LinearLayout titleView;
    @Bind(R.id.search_btn)
    LinearLayout searchBtn;
    @Bind(R.id.distance_btn)
    TextView distanceBtn;
    @Bind(R.id.collection_btn)
    TextView collectionBtn;
    @Bind(R.id.filter)
    TextView filter;
    @Bind(R.id.progressBar2)
    ProgressBar progressBar2;
    @Bind(R.id.btns_layout)
    LinearLayout btnsLayout;
    @Bind(R.id.no_data_layout)
    LinearLayout noDataLayout;
    @Bind(R.id.release_btn)
    TextView releaseBtn;
    @Bind(R.id.no_location_layout)
    LinearLayout noLocationLayout;

    @Bind(R.id.no_service_title)
    TextView noServiceTitle;
    @Bind(R.id.no_service_prompt)
    LinearLayout noServicePrompt;

    private FragmentActivity abActivity;
    private View view;

    public ArrayList<NearbyBase.DataBean.HomeMerchantRespResultListBean> dataList = new ArrayList<>();
    public ArrayList<NearbyBase.DataBean.HomeMerchantRespResultListBean> nowDataList = new ArrayList<>();
    boolean mShow = true;
    private ClientNearbyAdapter adapter;

    private int ScrollY;
    private int allPage = 1;
    private int page =1;
    public int size = 10;
    private String lat,Long;
    private String screening = "";
    private String keyword = "";
    private int con = -1;
    private int likeType = -1;
    private NearbyBase.DataBean.HomeMerchantRespResultListBean itemData;
    private ImageView dataImage;
    private SearchNearbyDataBase data;
    private String selectId;
    private ImageView likeView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        abActivity = (ClientActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_nearby, container, false);
        ButterKnife.bind(this, view);
        if (!AppUtile.isLocServiceEnable(getContext())){
            noLocationLayout.setVisibility(View.VISIBLE);
            noDataLayout.setVisibility(View.GONE);
            btnsLayout.setVisibility(View.GONE);
        }
        if (YumApplication.getInstance().isNoLocation()){
            setBg();
        }else{
            setBg();
        }

        initLayout();
        return view;
    }

    /**
     * 初始化数据
     */
    private void initLayout() {
        //模拟数据
        adapter = new ClientNearbyAdapter(dataList);
        scrollView.setMode(PullToRefreshBase.Mode.BOTH);
        scrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                page = 1;
                //RefreshData();
                ((ClientActivity)getActivity()).setNearby();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                if (allPage>page) {
                    page ++;
                    RefreshData();
                }else{
                    scrollView.onRefreshComplete();
                }

            }
        });
        scrollView.setGoneHead();
        scrollView.setGoneAll();
        scrollView.setCallBack(new PullToRefreshScrollViewCallBack() {
            @Override
            public void getScrollY(int y) {
                if (y > YumApplication.getInstance().getView106dp() && y > ScrollY) {//上滑
                    if (mShow) {
                        titleView.setVisibility(View.GONE);
                        titleView.setAnimation(AnimationUtil.moveToViewLocation100());
                        mShow = !mShow;
                        new Handler().postDelayed(new Runnable(){
                            public void run() {
                                //execute the task
                                progressBar2.setVisibility(View.GONE);
                            }
                        }, 100);
                    }
                } else if (y+30 < ScrollY||y<=YumApplication.getInstance().getView106dp() ) {//下滑
                    if (!mShow) {
                        titleView.setVisibility(View.VISIBLE);
                        titleView.setAnimation(AnimationUtil.moveToViewBottom100());
                        mShow = !mShow;
                        new Handler().postDelayed(new Runnable(){
                            public void run() {
                                //execute the task
                                progressBar2.setVisibility(View.VISIBLE);
                            }
                        }, 100);
                    }
                }
                ScrollY = y;
            }
        });

        nearbyLv.setAdapter(adapter);
        adapter.setClientNearbyCallBack(new ClientNearbyAdapter.NearbyCallBack() {
            @Override
            public void itemClick(int position, String id,boolean isLike,int state,ImageView liekview) {
                likeView = liekview;
                    Intent intent = new Intent(getContext(), BusinessDetailsActivity.class);
                    intent.putExtra("data",data);
                    intent.putExtra("id", id);
                    selectId = id;
                    intent.putExtra("type", state);
                    intent.putExtra("like", isLike);
                    startActivityForResult(intent, 1);

            }

            @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
            @Override
            public void itemCollectionClick(final ImageView imageView,final NearbyBase.DataBean.HomeMerchantRespResultListBean like) {
                if (!TextUtils.isEmpty(YumApplication.getInstance().getMyInformation().getUid())) {
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

                                    if (AppUtile.setCode(s,(BaseActivity) getActivity())) {
                                        if (like.isLike()) {
                                            imageView.setImageResource(R.mipmap.heart_white_big);
                                            like.setLike(false);
                                            likeType = 1;
                                        } else {
                                            imageView.setImageResource(R.mipmap.heart_red_big);
                                            like.setLike(true);
                                            likeType = 0;

                                        }
                                    }
                                }
                            });
                }else{
                    con = 0;
                    itemData = like;
                    dataImage = imageView;
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    intent.putExtra("type",true);
                    startActivityForResult(intent,0);
                    getActivity().overridePendingTransition(R.anim.activity_open,R.anim.in);
                }
            }

        });

       // input.setLat();
//        if (!TextUtils.isEmpty(YumApplication.getInstance().getMyInformation().getToken())){
//            if (YumApplication.getInstance().getMyInformation().getMerchantType()==2){
//                releaseBtn.setVisibility(View.GONE);
//            }else{
//                releaseBtn.setVisibility(View.VISIBLE);
//            }
//        }
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
    public void setLike(int id,int type){
        if (dataList!=null&&dataList.size()>0){
            a:for (int i = 0; i < dataList.size(); i++){
                if (id == dataList.get(i).getUserId()){
                    if (type == 0) {
                        dataList.get(i).setLike(true);
                        //likeView.setImageResource(R.mipmap.heart_red_big);
                    } else if (type == 1) {
                        dataList.get(i).setLike(false);
                       // likeView.setImageResource(R.mipmap.heart_white_big);
                    }
                    adapter.notifyDataSetChanged();
                    break a;
                }
            }

        }
    }
    public void setBg(){
        if (YumApplication.getInstance().isNoLocation()){
            noServicePrompt.setVisibility(View.VISIBLE);
            btnsLayout.setVisibility(View.GONE);
            noLocationLayout.setVisibility(View.GONE);
        }else{
            noLocationLayout.setVisibility(View.VISIBLE);
            btnsLayout.setVisibility(View.GONE);
            noServicePrompt.setVisibility(View.GONE);
        }
    }

    public void getData(){
        if (dataList.size()==0){
            setBg();
        }else{
            noLocationLayout.setVisibility(View.GONE);
            noServicePrompt.setVisibility(View.GONE);
        }
    }

    public void setAddressData(SearchNearbyDataBase data){
        this.data = data;
    }
    public void loginResult(){
        if (con == 0){
            Map<String, String> params = new HashMap<>();
            params.put("likeType", itemData.isLike() ? "CANCEL" : "LIKE");
            params.put("userId", YumApplication.getInstance().getMyInformation().getUid());
            params.put("salerUserId", itemData.getUserId() + "");
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
                            if (itemData.isLike()) {
                                dataImage.setImageResource(R.mipmap.heart_white_big);
                                itemData.setLike(false);
                                likeType = 1;
                            } else {
                                dataImage.setImageResource(R.mipmap.heart_red_big);
                                itemData.setLike(true);
                                likeType = 0;
                            }
                        }
                    });
        }
    }
    public void RefreshData(){
        noLocationLayout.setVisibility(View.GONE);
        noDataLayout.setVisibility(View.GONE);
        btnsLayout.setVisibility(View.VISIBLE);
        NearbyInput input = new NearbyInput();
        input.setPageSize(size);
        input.setUserId(YumApplication.getInstance().getMyInformation().getUid());
        input.setPageIndex(page);
        input.setLat(lat);
        input.setLng(Long);
        input.setKeyword(keyword);
        input.setScreening(!TextUtils.isEmpty(screening)?screening:null);
        if (AppUtile.isLocServiceEnable(getContext())) {
            getData(JSON.toJSONString(input));
        }else{
            noLocationLayout.setVisibility(View.VISIBLE);
            noDataLayout.setVisibility(View.GONE);
            btnsLayout.setVisibility(View.GONE);
        }
    }

    public void setLatLong(String lat,String Long){
        this.lat = !TextUtils.isEmpty(lat)?lat:"0.0";
        this.Long = !TextUtils.isEmpty(Long)?Long:"0.0";
        collectionBtn.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        distanceBtn.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        filter.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        keyword= "";
        screening = "";
        RefreshData();
    }
    private void getData(String string){
        System.out.println("YumApplication.getInstance().getMyInformation().getToken() = " + YumApplication.getInstance().getMyInformation().getToken());
        OkHttpUtils
                .postString()
                .mediaType(MediaType.parse("application/json"))
                .addHeader("Accept-Language",AppUtile.getLanguage())
                .addHeader("YUM-AUTH-TOKEN",YumApplication.getInstance().getMyInformation().getToken())
                .url(Constant.HOME_LIST)
                .content(string)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        //showMsg(e.getLocalizedMessage());
                        if (AppUtile.getNetWork(e.getMessage())){
                            showMsg(getString(R.string.NETERROR));
                        }
                        if (scrollView!=null)
                        scrollView.onRefreshComplete();
                        if (noDataLayout!=null)
                        noDataLayout.setVisibility(View.GONE);
                        if (btnsLayout!=null)
                        btnsLayout.setVisibility(View.GONE);
                        if (noLocationLayout!=null)
                        noLocationLayout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onResponse(String s, int con) {
                        if (scrollView!=null)
                        scrollView.onRefreshComplete();
                        if (!TextUtils.isEmpty(s)){

                            if (AppUtile.setCode(s,(BaseActivity) getActivity())){
                                NearbyBase data = JSON.parseObject(s,NearbyBase.class);
                                allPage = data.getData().getPageCount();
                                if (page ==1){
                                    dataList.clear();
                                    nowDataList.clear();
                                }
                                if (data.getData().getHomeMerchantRespResultList()!=null&&data.getData().getHomeMerchantRespResultList().size()>0){
                                    setBg();
                                    btnsLayout.setVisibility(View.VISIBLE);

                                    for (NearbyBase.DataBean.HomeMerchantRespResultListBean item:data.getData().getHomeMerchantRespResultList()){
//                                        if (dataList.size()>0){
//                                            boolean type = true;
//                                            a:for (int i = 0 ; i < dataList.size();i++ ){
//                                                if (dataList.get(i).getId() == item.getId()){
//                                                    type = false;
//                                                    break a;
//                                                }
//                                            }
//                                            if (type){
//                                                dataList.add(item);
//                                            }
//                                        }else{
//                                            dataList.add(item);
//                                        }
                                        dataList.add(item);
                                    }

                                }else if (page ==1){
                                    if (!TextUtils.isEmpty(keyword)||!TextUtils.isEmpty(screening)){
                                        noServiceTitle.setText(getString(R.string.NONENEARBY));
                                        if (YumApplication.getInstance().isNoLocation()){
                                            noServicePrompt.setVisibility(View.GONE);
                                            btnsLayout.setVisibility(View.GONE);
                                            noLocationLayout.setVisibility(View.GONE);
                                        }else{
                                            noLocationLayout.setVisibility(View.VISIBLE);
                                            btnsLayout.setVisibility(View.GONE);
                                            noServicePrompt.setVisibility(View.GONE);
                                        }
                                        releaseBtn.setVisibility(View.VISIBLE);
                                    }else{
                                        noServiceTitle.setText(getString(R.string.NOSERVICENEARBY));
                                        if (YumApplication.getInstance().isNoLocation()){
                                            noServicePrompt.setVisibility(View.VISIBLE);
                                            btnsLayout.setVisibility(View.GONE);
                                            noLocationLayout.setVisibility(View.GONE);
                                        }else{
                                            noLocationLayout.setVisibility(View.VISIBLE);
                                            btnsLayout.setVisibility(View.GONE);
                                            noServicePrompt.setVisibility(View.GONE);
                                        }
                                        releaseBtn.setVisibility(View.GONE);
                                    }
                                    noDataLayout.setVisibility(View.VISIBLE);
                                    btnsLayout.setVisibility(View.GONE);
                                }
                                adapter.notifyDataSetChanged();

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
    public void setFontNormal(){
        collectionBtn.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        distanceBtn.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        filter.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        screening = "";
        keyword = "";
        page = 1;
        RefreshData();
    }
    public void setKeywordStr(String keyword){
        this.keyword = keyword;

    }
    public void setScreeningStr(String screening){
        this.screening = screening;

    }
    public void setKeyword(String keyword){
        screening = "ALL";
        if (TextUtils.isEmpty(keyword)||keyword.equals("ALL Cuisines")){
            this.keyword ="";
        }else {
            this.keyword = keyword;
        }
        distanceBtn.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        collectionBtn.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        filter.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        page = 1;
        RefreshData();
    }

    @OnClick({R.id.filter, R.id.search_btn, R.id.distance_btn, R.id.collection_btn,R.id.release_btn
    ,R.id.location_btn})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {

            case R.id.location_btn:
                intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                getActivity().startActivityForResult(intent, 1002); // 设置完成后返回到原来的界面
                break;
            case R.id.release_btn:
                keyword = "";
                screening = "";
                collectionBtn.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                distanceBtn.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                filter.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                noLocationLayout.setVisibility(View.GONE);
                noDataLayout.setVisibility(View.GONE);
                btnsLayout.setVisibility(View.VISIBLE);
                page = 1;
                RefreshData();
//                intent = new Intent(getContext(), ReleaseActivity.class);
//                startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.activity_open,R.anim.in);

                break;
            case R.id.filter://筛选
                if (!TextUtils.isEmpty(keyword)
                        ||(!TextUtils.isEmpty(screening)&&screening.equals("ALL"))) {
                    filter.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    keyword ="";
                    screening = "";
                    page = 1;
                    RefreshData();
                }else {
                    intent = new Intent(getActivity(), FilterActivity.class);
                    intent.putExtra("type", "nearby");
                    intent.putExtra("keyword", keyword);
                    startActivityForResult(intent, 102);
                    getActivity().overridePendingTransition(R.anim.activity_open, R.anim.in);
                }
                break;
            case R.id.search_btn://搜索
                intent = new Intent(getActivity(), SearchNearbyActivity.class);
                getActivity().startActivityForResult(intent,102);
                getActivity().overridePendingTransition(R.anim.activity_open,R.anim.in);
                break;
            case R.id.distance_btn://距离
                if (screening.equals("DISTANCE")){
                    distanceBtn.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    screening = "";
                }else{
                    distanceBtn.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    screening = "DISTANCE";
                }

                collectionBtn.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                filter.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                keyword = "";
                page = 1;
                RefreshData();
                break;
            case R.id.collection_btn://收藏
                if (screening.equals("LIKE")){
                    collectionBtn.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    screening = "";
                }else{
                    collectionBtn.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    screening = "LIKE";
                }
                distanceBtn.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                filter.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                keyword = "";
                page = 1;
                RefreshData();
                break;
        }

    }


}
