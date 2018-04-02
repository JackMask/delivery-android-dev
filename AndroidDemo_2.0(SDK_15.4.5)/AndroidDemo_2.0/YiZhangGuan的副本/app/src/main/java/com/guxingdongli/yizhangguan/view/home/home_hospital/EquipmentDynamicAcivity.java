package com.guxingdongli.yizhangguan.view.home.home_hospital;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.util.AbToastUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.EquipmentDynamicAdpater;
import com.guxingdongli.yizhangguan.controller.adapter.callback.EquipmentDynamicAdapterCallBack;
import com.guxingdongli.yizhangguan.model.EquipmentDynamicBase;
import com.guxingdongli.yizhangguan.model.MyRepairOrderBase;
import com.guxingdongli.yizhangguan.util.AppUtile;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.guxingdongli.yizhangguan.view.home.home_hospital.fragment.MyRepairOrderFragment;
import com.guxingdongli.yizhangguan.view.login.LoginActivity;
import com.yuxiaolong.yuxiandelibrary.ActionSheetDialog;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;
import com.yuxiaolong.yuxiandelibrary.pulltorefresh.PullToRefreshBase;
import com.yuxiaolong.yuxiandelibrary.pulltorefresh.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 设备动态
 * Created by jackmask on 2018/3/6.
 */

public class EquipmentDynamicAcivity extends YiZhangGuanActivity {

    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.time_image)
    ImageView timeImage;
    @Bind(R.id.search_et)
    EditText searchEt;

    @Bind(R.id.content_list)
    PullToRefreshListView contentList;
    @Bind(R.id.right_img_search_btn)
    ImageView rightImgSearchBtn;
    @Bind(R.id.right_img_btn)
    ImageView rightImgBtn;
    @Bind(R.id.search_layout)
    LinearLayout searchLayout;


    private int page = 1;
    private int allPage = 1;
    private boolean sortOrder = false;
    boolean type = false;
    private String keyword = "";
    private List<String> guids = new ArrayList<>();

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            HttpUtile httpUtile;
            switch (what) {
                case 0:
                    String returnStr = (String)msg.obj;
                    EquipmentDynamicBase data = JSON.parseObject(returnStr,EquipmentDynamicBase.class);
                    if (page == 1){
                        dataList.clear();
                        allPage = data.getData().getTotal();
                    }
                    if (allPage/10 != page) {
                        for (EquipmentDynamicBase.DataBeanX.DataBean dataItem : data.getData().getData()) {
                            dataItem.setType(false);
                            dataList.add(dataItem);
                        }
                    }
                    if (adapter != null)
                        adapter.notifyDataSetChanged();
                    if (contentList != null)
                        contentList.onRefreshComplete();


                    break;
                case  1:
                    if (adapter != null)
                        adapter.notifyDataSetChanged();
                    if (contentList != null)
                        contentList.onRefreshComplete();

                    break;
                case 2:
                    webPostData(keyword,sortOrder);
                    break;

            }

        }
    };


    private EquipmentDynamicAdpater adapter;
    private List<EquipmentDynamicBase.DataBeanX.DataBean> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_dyamic);
        ButterKnife.bind(this);
        setView();
    }

    private void setView() {

        titleText.setText("设备总汇");
        rightImgSearchBtn.setVisibility(View.VISIBLE);
        rightImgBtn.setVisibility(View.VISIBLE);
        searchLayout.setVisibility(View.VISIBLE);
        searchEt.setHint("请填写订单号");
        rightImgSearchBtn.setPadding(0, AppUtile.dp2px(this, 16), AppUtile.dp2px(this, 16), AppUtile.dp2px(this, 16));
        rightImgSearchBtn.setImageResource(R.mipmap.modify_status);

        adapter = new EquipmentDynamicAdpater(dataList);
        contentList.setMode(PullToRefreshBase.Mode.BOTH);
        contentList.setAdapter(adapter);
        /*adapter.setCallBack(new EquipmentDynamicAdapterCallBack() {
            @Override
            public void getNumNew(int position,String guie, boolean selectCon) {
                for (int i = 0  ; i < dataList.size() ;i ++){
                    if (dataList.get(i).getGid().equals(guie)){
                        dataList.get(i).setType(selectCon);
                        break;
                    }
                }
                int con = 0 ;
                for (int i = 0 ; i < guids.size();i++){
                    if (guids.get(i).equals(guie)){
                        con++;
                        if (!selectCon) {
                            guids.remove(i);
                            break;
                        }
                    }
                }
                if (con == 0){
                    if (selectCon) {
                        guids.add(guie);
                    }
                }

            }
        });*/
        contentList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //刷新
                page=1;
                webPostData(keyword,sortOrder);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //加载
                page++;

                webPostData(keyword,sortOrder);

            }
        });

        webPostData(keyword,sortOrder);

    }

    private void webPostData(String keyword, boolean sortOrder) {
        RequestBody formBody = new FormBody.Builder()
                .add("pageIndex", page + "")
                .add("pageSize", "10")
                .add("sortField", "Id")
                .add("sortOrder", sortOrder ? "asc" : "desc")
                .add("filter_group", "{\"Groups\":[{\"Rules\":[{\"Field\":\"Name\",\"Value\":\"" + keyword + "\",\"Operate\":\"contains\"},{\"Field\":\"MaterialNumber\",\"Value\":\"" + keyword + "\",\"Operate\":\"contains\"}],\"Operate\": \"or\"}],\"Operate\": \"and\"} ")
                .build();
        HttpUtile httpUtile = new HttpUtile(this, Constant.DOMAIN_NAME + Constant.SEARCHMATERIAL, formBody, new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {
                Message message = new Message();
                message.what = 0 ;
                message.obj = returnStr;
                mHandler.sendMessage(message);
                // mHandler.sendEmptyMessage(0);

            }

            @Override
            public void getReturnStrFailure(String returnStr) {
                dataList.clear();
                mHandler.sendEmptyMessage(1);
            }

            @Override
            public void getErrorStr(String errorStr) {

            }
        },false);
    }

    public String getToString(List<String> arrayData) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0 ; i < arrayData.size(); i++) {
            stringBuilder.append(arrayData.get(i));
            if (i < arrayData.size() - 1) {
                stringBuilder.append(",");
            }
        }
        return stringBuilder.toString();
    }




    private void changeStatus(String gidJson,String type){
        RequestBody formBody = new FormBody.Builder()
                .add("guids", gidJson)
                .add("useStatus", type)
                .build();

        HttpUtile httpUtile = new HttpUtile(this, Constant.DOMAIN_NAME + Constant.UPDQTEMATERIAL, formBody, new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {
                // mHandler.sendEmptyMessage(0);
                page = 1;
                mHandler.sendEmptyMessage(2);
            }

            @Override
            public void getReturnStrFailure(String returnStr) {
                Looper.prepare();
                Toast.makeText(EquipmentDynamicAcivity.this,returnStr,Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void getErrorStr(String errorStr) {
                System.out.println("errorStr = "+errorStr);
            }
        },false);
    }
    private void changeStatus(String gidJson){


        HttpUtile httpUtile = new HttpUtile(this, Constant.DOMAIN_NAME + Constant.UPDQTEMATERIAL+gidJson, new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {
                // mHandler.sendEmptyMessage(0);
                page = 1;
                mHandler.sendEmptyMessage(2);
            }

            @Override
            public void getReturnStrFailure(String returnStr) {
                Looper.prepare();
                Toast.makeText(EquipmentDynamicAcivity.this,returnStr,Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void getErrorStr(String errorStr) {
                System.out.println("errorStr = "+errorStr);
            }
        },false);
    }


    @OnClick({R.id.return_btn, R.id.time_layout, R.id.right_img_search_btn,R.id.search_btn,R.id.right_img_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_btn:
                keyword = searchEt.getText().toString();
                page = 1;
                webPostData(keyword,type);
                break;
            case R.id.right_img_btn:
                AbToastUtil.showToast(EquipmentDynamicAcivity.this,"程序猿哥哥正在开发中~");
                break;
            case R.id.return_btn:

                finish();
                break;
            case R.id.right_img_search_btn://修改状态
                new ActionSheetDialog(this)
                        .builder()
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem("在用设备", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        //String cong =getToString(guids) ;
                                        //changeStatus(cong,"在用设备");
                                        if (adapter!=null) {
                                            guids = adapter.getGuids();
                                            String url = "?";
                                            for (int i = 0; i < guids.size(); i++) {
                                                url += "guids=" + guids.get(i) + "&";
                                            }
                                            url += "useStatus=在用设备";
                                            changeStatus(url);
                                        }
                                    }
                                })
                        .addSheetItem("闲置设备", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        /*String cong =getToString(guids) ;
                                        changeStatus(cong,"闲置设备");*/
                                        if (adapter!=null) {
                                            guids = adapter.getGuids();
                                            String url = "?";
                                            for (int i = 0; i < guids.size(); i++) {
                                                url += "guids=" + guids.get(i) + "&";
                                            }
                                            url += "useStatus=闲置设备";
                                            changeStatus(url);
                                        }
                                    }
                                })
                        .addSheetItem("停用设备", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        /*String cong =getToString(guids) ;
                                        changeStatus(cong,"停用设备");*/
                                        if (adapter!=null) {
                                            guids = adapter.getGuids();
                                            String url = "?";
                                            for (int i = 0; i < guids.size(); i++) {
                                                url += "guids=" + guids.get(i) + "&";
                                            }
                                            url += "useStatus=停用设备";
                                            changeStatus(url);
                                        }
                                    }
                                })
                        .show();
                break;
            case R.id.time_layout:
                type = !type;
                AppUtile.setTitleFilter(type,timeImage);
                page = 1;
                webPostData(keyword,type);
                break;

        }
    }
}
