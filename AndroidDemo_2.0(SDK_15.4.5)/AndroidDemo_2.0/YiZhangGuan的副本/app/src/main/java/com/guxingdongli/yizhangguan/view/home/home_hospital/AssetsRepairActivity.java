package com.guxingdongli.yizhangguan.view.home.home_hospital;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.util.AbToastUtil;
import com.alibaba.fastjson.JSON;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.AssetsRepairAdapter;
import com.guxingdongli.yizhangguan.controller.adapter.callback.AssetsRepairCallBack;
import com.guxingdongli.yizhangguan.model.AssetsRepairBase;
import com.guxingdongli.yizhangguan.util.AppUtile;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;
import com.yuxiaolong.yuxiandelibrary.pulltorefresh.PullToRefreshBase;
import com.yuxiaolong.yuxiandelibrary.pulltorefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 资产报修
 * Created by jackmask on 2018/3/5.
 */

public class AssetsRepairActivity extends YiZhangGuanActivity {

    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.search_et)
    EditText searchEt;
    @Bind(R.id.search_layout)
    LinearLayout searchLayout;
    @Bind(R.id.search_btn)
    TextView search_btn;
    @Bind(R.id.content_list)
    PullToRefreshListView contentList;
    @Bind(R.id.time_image)
    ImageView timeImage;

    private AssetsRepairAdapter assetsRepairAdapter;
    private List<AssetsRepairBase.DataBeanX.DataBean> dataList = new ArrayList<>();
    private AssetsRepairBase.DataBeanX.DataBean equipment;
    private int page = 1;
    private int allPage = 1;
    private boolean sortOrder = false;
    private String keyword = "";

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            HttpUtile httpUtile;
            switch (what) {
                case 0:
                    if (assetsRepairAdapter != null)
                        assetsRepairAdapter.notifyDataSetChanged();
                    contentList.onRefreshComplete();
                    break;
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets_repair);
        ButterKnife.bind(this);
        setView();
    }

    private void setView() {
        titleText.setText("请选择报修设备");
        titleText.setTextSize(18);
        searchLayout.setVisibility(View.VISIBLE);
        searchEt.setHint("请填写编号或名称");
        assetsRepairAdapter = new AssetsRepairAdapter(dataList);
        contentList.setMode(PullToRefreshBase.Mode.BOTH);
        contentList.setAdapter(assetsRepairAdapter);


        /*assetsRepairAdapter.setCallBack(new AssetsRepairCallBack() {
            @Override
            public void selectItem(int position, boolean type) {
                for (int con = 0; con < dataList.size(); con++) {
                    dataList.get(con).setSelectTab(false);
                }
                dataList.get(position).setSelectTab(type);
                equipment = dataList.get(position);
                assetsRepairAdapter.notifyDataSetChanged();
            }
        });*/
        assetsRepairAdapter.notifyDataSetChanged();
        contentList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //刷新
                page = 1;
                webData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //加载
                page++;
                webData();

            }
        });

        webData();
    }

    private void webData() {
        RequestBody formBody = new FormBody.Builder()
                .add("pageIndex", "" + page)
                .add("pageSize", "10")
                .add("sortField", "Id")
                .add("sortOrder", sortOrder ? "asc" : "desc")
                .add("filter_group", "{\"Rules\":[{\"Field\":\"UseStatus\",\"Value\":\"8\",\"Operate\":\"notequal\"}],\"Groups\":[{\"Rules\":[{\"Field\":\"materialNumber\",\"Value\":\"" + keyword + "\",\"Operate\":\"contains\"},{\"Field\":\"name\",\"Value\":\"" + keyword + "\",\"Operate\":\"contains\"}],\"Operate\":\"or\"}],\"Operate\": \"and\"}")
                .build();
        HttpUtile httpUtile = new HttpUtile(this, Constant.DOMAIN_NAME + Constant.SEARCHMATERIAL, formBody, new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {
                AssetsRepairBase data = JSON.parseObject(returnStr, AssetsRepairBase.class);
                if (page == 1) {
                    dataList.clear();
                    allPage = data.getData().getTotal();
                }
                if (allPage / 10 != page) {
                    for (AssetsRepairBase.DataBeanX.DataBean dataItem : data.getData().getData()) {
                        dataItem.setSelectTab(false);
                        dataList.add(dataItem);
                    }
                }
                mHandler.sendEmptyMessage(0);

            }

            @Override
            public void getReturnStrFailure(String returnStr) {
                mHandler.sendEmptyMessage(0);
                Looper.prepare();
                Toast.makeText(AssetsRepairActivity.this, returnStr, Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void getErrorStr(String errorStr) {

            }
        },false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1001){
            setResult(1001);
            finish();
        }
    }

    @OnClick({R.id.return_btn, R.id.repair_btn, R.id.search_btn,R.id.time_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_btn://返回
                finish();
                break;
            case R.id.repair_btn://报修按钮
                if (assetsRepairAdapter!=null&&assetsRepairAdapter.getEquipment()!=null) {
                    Intent intent = new Intent(AssetsRepairActivity.this, AddRepairActivity.class);
                    intent.putExtra("equipment", assetsRepairAdapter.getEquipment());
                    startActivityForResult(intent, 1);
                }else{
                    AbToastUtil.showToast(this,"请选择至少一个设备");
                }

                break;
            case R.id.time_layout:
                sortOrder = !sortOrder;
                AppUtile.setTitleFilter(sortOrder, timeImage);
                page = 1;
                webData();
                break;
            case R.id.search_btn://搜索
                keyword = searchEt.getText().toString().trim();
                page = 1;
                webData();
                break;
        }
    }
}
