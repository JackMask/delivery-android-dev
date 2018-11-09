package com.yum.two_yum.view.merchants.menu;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.FilterBase;
import com.yum.two_yum.base.GetMerchantMenuListBase;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.controller.adapter.NowMenuAdapter;
import com.yum.two_yum.controller.adapter.callback.NowMenuCallBack;
import com.yum.two_yum.utile.ActionSheetDialog;
import com.yum.two_yum.utile.AnimationUtil;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.pulltorefresh.PullToRefreshBase;
import com.yum.two_yum.utile.pulltorefresh.PullToRefreshListView;
import com.yum.two_yum.view.client.ClientActivity;
import com.yum.two_yum.view.my.SettingsActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * @author 余先德
 * @data 2018/4/13
 */

public class NowMenuActivity extends BaseActivity {

    @Bind(R.id.content_lv)
    PullToRefreshListView contentLv;
    @Bind(R.id.ok_btn)
    TextView okBtn;
    @Bind(R.id.add_btn)
    ImageView addBtn;
    @Bind(R.id.add_pic_btn)
    LinearLayout addPicBtn;
    @Bind(R.id.no_data_layout)
    View noDataLayout;
    @Bind(R.id.select_layout)
    LinearLayout selectLayout;

    private List<FilterBase> dataList = new ArrayList<>();
    private NowMenuAdapter adapter;
    private String typeStr;
    private boolean showPopu = false;
    private boolean editType = false;
    private PopupWindow popupWindow;
    private boolean noData = false;

    private Map<String,String> map = new HashMap<>();
    private List<String> idKey= new ArrayList<>();
    private List<String> typeV = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_menu);
        ButterKnife.bind(this);
        setView();
    }

    @Override
    protected void setView() {
        super.setView();
        typeStr = getResources().getString(R.string.EDIT);

        contentLv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        adapter = new NowMenuAdapter(dataList);
        contentLv.setAdapter(adapter);
        //contentLv.setGoneAll();
       // contentLv.setGoneHead();
        contentLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                getInfo();
            }
        });

        adapter.setCallBack(new NowMenuCallBack() {
            @Override
            public void clickSelect(int position, boolean type) {
                //dataList.get(position).setSelectType(!type);
                dataList.get(position).getMenuRespResultsBean().setState(type?0:1);
                int con = 0;
                for (int i =0 ; i < dataList.size();i++){
                    if (dataList.get(i).getMenuRespResultsBean().getState()==1){
                        con++;
                    }
                }
                if (con>0) {
                    editType = true;
                    okBtn.setBackgroundResource(R.drawable.button_red_bright);
                }else{
                    editType = false;
                    okBtn.setBackgroundResource(R.drawable.button_red);
                }
                adapter.notifyDataSetChanged();
                map.put(dataList.get(position).getMenuRespResultsBean().getId()+"",type?"0":"1");

            }

            @Override
            public void clickDel(int position, String id) {
                showLoading();
                OkHttpUtils
                        .post()
                        .url(Constant.DELETE_MENU)
                        .addHeader("Accept-Language", AppUtile.getLanguage())
                        .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                        .addParams("userId",YumApplication.getInstance().getMyInformation().getUid())
                        .addParams("merchantMenuIds",id)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int i) {
                                dismissLoading();
                                if (AppUtile.getNetWork(e.getMessage())){
                                    showMsg(getString(R.string.NETERROR));
                                }
                            }

                            @Override
                            public void onResponse(String s, int i) {
                                dismissLoading();
                                if (AppUtile.setCode(s,NowMenuActivity.this)) {
                                    getInfo();
                                    okBtn.setBackgroundResource(R.drawable.button_red);
                                }
                            }
                        });
            }

            @Override
            public void itemClick(GetMerchantMenuListBase.DataBean.MerchantMenuRespResultsBean data) {
                Intent intent = new Intent(NowMenuActivity.this,AddEditMenuActivity.class);
                intent.putExtra("data",data);
                intent.putExtra("type",false);
                startActivityForResult(intent,0);
            }
        });
        getInfo();
    }

    private void getInfo() {
        OkHttpUtils
                .get()
                .url(Constant.GET_MERCHANT_MENU_LIST)
                .addHeader("Accept-Language", AppUtile.getLanguage())
                .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                .addParams("userId", YumApplication.getInstance().getMyInformation().getUid())
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
                        if (!TextUtils.isEmpty(s)) {

                            if (AppUtile.setCode(s,NowMenuActivity.this)) {
                                GetMerchantMenuListBase data = JSON.parseObject(s, GetMerchantMenuListBase.class);
                                if (data.getData().getMerchantMenuRespResults() != null && data.getData().getMerchantMenuRespResults().size() > 0) {
                                    addPicBtn.setVisibility(View.GONE);
                                    contentLv.setVisibility(View.VISIBLE);
                                    dataList.clear();
                                    showPopu = false;
                                    int con = 0;

                                    for (GetMerchantMenuListBase.DataBean.MerchantMenuRespResultsBean item:data.getData().getMerchantMenuRespResults()){
                                        FilterBase filterBase = new FilterBase();
                                        filterBase.setType(false);
                                        filterBase.setSelectType(false);
                                        filterBase.setMenuRespResultsBean(item);
                                        if (item.getState()==1){
                                            con ++;
                                        }
                                        dataList.add(filterBase);
                                    }
                                    if (con==0){
                                        editType = false;
                                        okBtn.setBackgroundResource(R.drawable.button_red);
                                    }else{
                                        editType = true;
                                        okBtn.setBackgroundResource(R.drawable.button_red_bright);
                                    }
                                    adapter.notifyDataSetChanged();
                                } else {
                                    noData= true;
                                        addPicBtn.setVisibility(View.VISIBLE);
                                        contentLv.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                });
    }

    @OnClick({R.id.del_btn, R.id.add_btn, R.id.cancel_btn, R.id.ok_btn, R.id.more_btn,R.id.add_pic_btn,R.id.no_data_layout,R.id.cancel_btn1
    ,R.id.call_btn})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.call_btn:
                noDataLayout.setVisibility(View.GONE);
                selectLayout.setVisibility(View.GONE);
                selectLayout.setAnimation(AnimationUtil.fromNowToBottom());
                 intent = new Intent(NowMenuActivity.this, SortingMenuActivity.class);
                startActivityForResult(intent,0);
                overridePendingTransition(R.anim.activity_open,R.anim.in);

                break;
            case R.id.cancel_btn1:
                noDataLayout.setVisibility(View.GONE);
                selectLayout.setVisibility(View.GONE);
                selectLayout.setAnimation(AnimationUtil.fromNowToBottom());
                break;
            case R.id.no_data_layout:
                noDataLayout.setVisibility(View.GONE);
                selectLayout.setVisibility(View.GONE);
                selectLayout.setAnimation(AnimationUtil.fromNowToBottom());
                break;
            case R.id.del_btn:
                setResult(1001);
                finish();
                break;
            case R.id.more_btn:
                noDataLayout.setVisibility(View.VISIBLE);
                selectLayout.setVisibility(View.VISIBLE);
                selectLayout.setAnimation(AnimationUtil.fromBottomToNow());
                break;
            case R.id.add_pic_btn:
            case R.id.add_btn:
                intent = new Intent(this, AddEditMenuActivity.class);
                intent.putExtra("type", true);
                startActivityForResult(intent,0);
                overridePendingTransition(R.anim.activity_open,R.anim.in);
                break;
            case R.id.cancel_btn:
                intent = new Intent(this, PreviewMenuActivity.class);
                startActivity(intent);
                break;
            case R.id.ok_btn:
                if (editType) {
                    showLoading();
                    ShelfData data = new ShelfData();
                    data.setMap(map);
                    data.setUserId(YumApplication.getInstance().getMyInformation().getUid());
                    String content = JSON.toJSONString(data);
                    OkHttpUtils
                        .postString()
                        .url(Constant.SETTING_MENU)
                            .addHeader("Accept-Language", AppUtile.getLanguage())
                        .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                            .mediaType(MediaType.parse("application/json"))
                            .content(content)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int i) {
                                dismissLoading();
                                if (AppUtile.getNetWork(e.getMessage())){
                                    showMsg(getString(R.string.NETERROR));
                                }
                            }

                            @Override
                            public void onResponse(String s, int i) {
                                dismissLoading();
                                if (AppUtile.setCode(s,NowMenuActivity.this)) {
                                    setResult(RESULT_OK);
                                    finish();
                                }
//                                getInfo();
//                                okBtn.setBackgroundResource(R.drawable.button_red);
                            }
                        });
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            getInfo();
        }
    }




    private class ShelfData{
        private Map<String,String> map;
        private String userId;

        public Map<String, String> getMap() {
            return map;
        }

        public void setMap(Map<String, String> map) {
            this.map = map;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }



}
