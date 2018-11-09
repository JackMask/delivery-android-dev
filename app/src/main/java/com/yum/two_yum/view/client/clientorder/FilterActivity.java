package com.yum.two_yum.view.client.clientorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.FilterBase;
import com.yum.two_yum.base.FilterWebBase;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.controller.adapter.FilterFootAdapter;
import com.yum.two_yum.controller.adapter.callback.FilterFootCallBack;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.arrogantlistview.widget.IndexableListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @author 余先德
 * @data 2018/4/8
 */

public class FilterActivity extends BaseActivity {

    @Bind(R.id.listview)
    IndexableListView listview;

    private ArrayList<FilterBase> mItems;
    private FilterFootAdapter adapter;
    private String keywords;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        mItems = new ArrayList<FilterBase>();
        keywords = getIntent().getStringExtra("keywords");



        adapter = new FilterFootAdapter(this,
                android.R.layout.simple_list_item_1, mItems);
        adapter.setCallBack(new FilterFootCallBack() {
            @Override
            public void selectFoot(int position, boolean selectType) {
//                if (mItems.get(position).getTitle().equals("ALL Cuisines")){
//                    for (int i =0;i<mItems.size();i++){
//                        mItems.get(i).setType(false);
//                    }
//                    mItems.get(position).setType(true);
//                }else {
//
//                    a: for (FilterBase item:mItems){
//                        if (item.getTitle().equals("ALL Cuisines")){
//                            item.setType(false);
//                            break a;
//                        }
//                    }
//                    mItems.get(position).setType(!selectType);
//
//                }
                for (int i =0;i<mItems.size();i++){
                    mItems.get(i).setType(false);
                }
                mItems.get(position).setType(!selectType);
                adapter.notifyDataSetChanged();
            }
        });



        listview.setAdapter(adapter);
        listview.setFastScrollEnabled(true);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mItems.get(position).setType(true);
            }
        });
        getData();
    }

    private void getData(){
        OkHttpUtils
                .get()
                .url(Constant.KEYWORD_ALL)
                .addHeader("Accept-Language", AppUtile.getLanguage())
                .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        if (AppUtile.getNetWork(e.getMessage())){
                            showMsg(getString(R.string.NETERROR));
                        }
                    }

                    @Override
                    public void onResponse(String s, int c) {
                        if (!TextUtils.isEmpty(s)){

                            if (AppUtile.setCode(s,FilterActivity.this)) {
                                FilterWebBase data = JSON.parseObject(s,FilterWebBase.class);
                                if (data.getData().getMerchantKeywordRespResults() != null && data.getData().getMerchantKeywordRespResults().size() > 0) {
                                    for (FilterWebBase.DataBean.MerchantKeywordRespResultsBean item : data.getData().getMerchantKeywordRespResults()) {
                                        FilterBase filterBase = new FilterBase();
                                        filterBase.setTitle(item.getKeyword());
                                        filterBase.setComparison(item.getKeyword());
                                        filterBase.setType(false);
                                        mItems.add(filterBase);
                                    }
                                    Collections.sort(mItems);
                                    FilterBase allData = new FilterBase();
                                    allData.setTitle("ALL Cuisines");
                                    allData.setComparison("ALL Cuisines");
                                    if (TextUtils.isEmpty(keywords)) {
                                        allData.setType(true);
                                    } else {
                                        allData.setType(false);
                                    }
                                    mItems.add(0, allData);
                                }
                                if (!TextUtils.isEmpty(keywords)) {
                                    String[] keywordSelect = keywords.split(",");
                                    for (int b = 0; b < keywordSelect.length; b++) {
                                        a:
                                        for (int i = 0; i < mItems.size(); i++) {
                                            if (keywordSelect[b].equals(mItems.get(i).getTitle())) {
                                                mItems.get(i).setType(true);
                                                break a;
                                            }
                                        }
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }

                    }
                });
    }



    @OnClick({R.id.del_btn, R.id.reset_btn,R.id.ok_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ok_btn:
                Intent intent = new Intent();
                ArrayList<String> data = new ArrayList<>();
                if (mItems!=null&&mItems.size()>0) {
                    for (int i = 0; i < mItems.size(); i++) {
                        if (mItems.get(i).isType()) {
                            data.add(mItems.get(i).getTitle());
                        }
                    }
                    //System.out.println(data.toString().substring(1, data.toString().length() - 1).replace(" ", ""));
                    intent.putExtra("data", data.toString().substring(1, data.toString().length() - 1).trim());
                }
                setResult(0, intent);
                finish();
                overridePendingTransition(R.anim.activity_open,R.anim.out);
                break;
            case R.id.del_btn:

                finish();
                overridePendingTransition(R.anim.activity_open,R.anim.out);
                break;
            case R.id.reset_btn:
                if (mItems!=null&&mItems.size()>0) {
                    for (int i = 0; i < mItems.size(); i++) {
                        mItems.get(i).setType(false);
                    }
                    mItems.get(0).setType(true);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }
}
