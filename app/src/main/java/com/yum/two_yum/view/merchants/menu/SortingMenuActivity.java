package com.yum.two_yum.view.merchants.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.mobeta.android.dslv.DragSortListView;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.FilterBase;
import com.yum.two_yum.base.GetMerchantMenuListBase;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.controller.adapter.NowMenuAdapter;
import com.yum.two_yum.controller.adapter.SortingMenuAdapter;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.pulltorefresh.PullToRefreshBase;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @author 余先德
 * @data 2018/4/14
 */

public class SortingMenuActivity extends BaseActivity {
    @Bind(R.id.dslvList)
    DragSortListView dslvList;

    private SortingMenuAdapter adapter;
    private List<GetMerchantMenuListBase.DataBean.MerchantMenuRespResultsBean> dataList = new ArrayList<>();


    private DragSortListView.DropListener onDrop = new DragSortListView.DropListener() {
        @Override
        public void drop(int from, int to) {// from to �ֱ��ʾ ���϶��ؼ�ԭλ�� ��Ŀ��λ��
            if (from != to) {
                GetMerchantMenuListBase.DataBean.MerchantMenuRespResultsBean item = (GetMerchantMenuListBase.DataBean.MerchantMenuRespResultsBean) adapter.getItem(from);// �õ�listview��������
                adapter.remove(from);// ���������С�ԭλ�á������ݡ�
                adapter.insert(item, to);// ��Ŀ��λ���в��뱻�϶��Ŀؼ���
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorting_menu);
        ButterKnife.bind(this);
        setView();

    }

    @Override
    protected void setView() {
        super.setView();

        dslvList.setDropListener(onDrop);
        adapter = new SortingMenuAdapter(this,dataList);
        dslvList.setAdapter(adapter);
        dslvList.setDragEnabled(true); // �����Ƿ���϶���
        adapter.notifyDataSetChanged();
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
                        if (AppUtile.getNetWork(e.getMessage())){
                            showMsg(getString(R.string.NETERROR));
                        }
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        if (!TextUtils.isEmpty(s)) {

                            if (AppUtile.setCode(s,SortingMenuActivity.this)) {
                                GetMerchantMenuListBase data = JSON.parseObject(s, GetMerchantMenuListBase.class);
                                if (data.getData().getMerchantMenuRespResults() != null && data.getData().getMerchantMenuRespResults().size() > 0) {
                                    dataList.clear();
                                    List<String> dataLIst = new ArrayList<>();
                                    for (GetMerchantMenuListBase.DataBean.MerchantMenuRespResultsBean item:data.getData().getMerchantMenuRespResults()){
                                        dataList.add(item);
                                        dataLIst.add(item.getId()+",");
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                });
    }


    @OnClick({R.id.del_btn, R.id.save_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.del_btn:
                finish();
                overridePendingTransition(R.anim.activity_open,R.anim.in);
                break;
            case R.id.save_btn:
                showLoading();
                String dataLIst = "";
                for (GetMerchantMenuListBase.DataBean.MerchantMenuRespResultsBean item:dataList){
                    dataLIst=dataLIst+item.getId()+",";
                }
                dataLIst =  dataLIst.substring(0,dataLIst.length()-1);
                OkHttpUtils
                        .post()
                        .url(Constant.SETTING_MENU_SORT)
                        .addHeader("Accept-Language", AppUtile.getLanguage())
                        .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                        .addParams("userId", YumApplication.getInstance().getMyInformation().getUid())
                        .addParams("merchantMenuIds", dataLIst)
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
                                if (AppUtile.setCode(s,SortingMenuActivity.this)) {
                                    setResult(RESULT_OK);
                                    finish();
                                    overridePendingTransition(R.anim.activity_open, R.anim.in);
                                }
                            }
                        });

                break;
        }
    }


}
