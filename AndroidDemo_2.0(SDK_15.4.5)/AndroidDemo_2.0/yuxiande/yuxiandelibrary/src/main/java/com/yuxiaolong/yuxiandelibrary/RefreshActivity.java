package com.yuxiaolong.yuxiandelibrary;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbToastUtil;
import com.alibaba.fastjson.JSON;
import com.yuxiaolong.yuxiandelibrary.pulltorefresh.PullToRefreshBase;
import com.yuxiaolong.yuxiandelibrary.pulltorefresh.PullToRefreshListView;

/**
 * Created by jackmask on 2018/2/15.
 */

public class RefreshActivity extends YuXianDeActivity {

    AbHttpUtil abHttpUtil;
    PullToRefreshListView contentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        abHttpUtil = AbHttpUtil.getInstance(this);
        abHttpUtil.setTimeout(10000);


    }

    /**
     * 刷新加载
     * @param contentList
     * @param adapter
     */
    protected void setRefreshView(PullToRefreshListView contentList, BaseAdapter adapter){
        this.contentList = contentList;
        contentList.setMode(PullToRefreshBase.Mode.BOTH);
        contentList.setAdapter(adapter);
        contentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                onItemClick(adapterView,view,i,l);
            }
        });
        contentList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                onPullDownToRefresh(refreshView);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                onPullUpToRefresh(refreshView);
            }
        });
    }

    /**
     * 获取网络数据
     * @param con
     * @param content JSON数据
     */
    protected void getData(int con, String content){

    }

    /**
     * 获取网络数据错误
     * @param i
     * @param con
     * @param throwable
     */
    protected void getDataError(int i, String con, Throwable throwable){

    }

    /**
     * 网络访问数据
     * @param url 地址
     * @param params 内容
     */
    protected void setWebPost(final String url,final AbRequestParams params){
        abHttpUtil.post(url, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                if (!TextUtils.isEmpty(s)){
                    getData(i,s);
                }else{
                    AbToastUtil.showToast(RefreshActivity.this,"未知错误");
                }
            }

            @Override
            public void onStart() {

            }

            /**
             * 完成
             */
            @Override
            public void onFinish() {

            }
            /**
             * 错误
             */
            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                getDataError(i,  s,  throwable);
            }
        });
    }

    /**
     * 加载
     * @param refreshView
     */
    protected void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView){

    }

    /**
     * 刷新
     * @param refreshView
     */
    protected  void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView){

    }

    /**
     * 单击
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    protected void onItemClick(AdapterView<?> adapterView, View view, int i, long l){

    }

}
