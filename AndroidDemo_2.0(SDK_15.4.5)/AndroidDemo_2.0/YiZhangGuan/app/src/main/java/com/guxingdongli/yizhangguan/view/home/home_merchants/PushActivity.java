package com.guxingdongli.yizhangguan.view.home.home_merchants;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.util.AbDialogUtil;
import com.ab.util.AbToastUtil;
import com.alibaba.fastjson.JSON;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.PushAdapter;
import com.guxingdongli.yizhangguan.controller.adapter.PushChildAdapter;
import com.guxingdongli.yizhangguan.model.PushBase;
import com.guxingdongli.yizhangguan.model.PushSelectHospitalBase;
import com.guxingdongli.yizhangguan.model.passvalue.PushValue;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.guxingdongli.yizhangguan.view.home.home_hospital.fragment.callback.StorageHospitalOnClickCallBack;
import com.guxingdongli.yizhangguan.view.login.LoginActivity;
import com.yuxiaolong.yuxiandelibrary.NoSlideListView;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;
import com.yuxiaolong.yuxiandelibrary.pulltorefresh.PullToRefreshBase;
import com.yuxiaolong.yuxiandelibrary.pulltorefresh.PullToRefreshScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * @author 余先德
 * @data 2018/3/23
 */

public class PushActivity extends YiZhangGuanActivity {

    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.select_hospital_tv)
    TextView selectHospitalTv;
    @Bind(R.id.data_list)
    NoSlideListView dataList;
    @Bind(R.id.scroll_view)
    PullToRefreshScrollView scrollView;
    @Bind(R.id.right_tv_btn)
    TextView rightTvBtn;

    private PushAdapter adapter;
    private List<PushBase.DataBean> datas = new ArrayList<>();
    private List<PushSelectHospitalBase.DataBean> dataBeans;
    private List<PushChildAdapter> childAdapters = new ArrayList<>();
    private PushBase data;

    private int page = 1;
    private int allPage = 1;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) //如果消息是刚才发送的标识
            {
                String formBody = (String) msg.obj;
                 data = JSON.parseObject(formBody, PushBase.class);
                if (page == 1) {
                    datas.clear();
                    childAdapters.clear();
                }
                for (PushBase.DataBean item : data.getData()) {
                    datas.add(item);
                    childAdapters.add(new PushChildAdapter(item.getListMany()));
                }
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                    adapter.setAdapters(childAdapters);
                }
                if (scrollView != null)
                    scrollView.onRefreshComplete();

            } else if (msg.what == 1) {
                if (scrollView != null)
                    scrollView.onRefreshComplete();
            }
        }

        ;
    };

    public PushChildAdapter getAdapter(int i ){
        return childAdapters.get(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);
        ButterKnife.bind(this);
        setView();
    }

    private void setView() {
        titleText.setText("选择推送器材的品种");
        rightTvBtn.setVisibility(View.VISIBLE);
        rightTvBtn.setText("保存");
        scrollView.setMode(PullToRefreshBase.Mode.DISABLED);
        adapter = new PushAdapter(datas);
        dataList.setAdapter(adapter);
        adapter.setClickCallBack(new StorageHospitalOnClickCallBack() {
            @Override
            public void clickNo(int position, boolean type) {
                for (int i = 0; i < datas.size(); i++) {
                    datas.get(i).setType(false);
                }
                datas.get(position).setType(type);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void clickTime(String hospitalGuid, String number) {

            }
        });
        scrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                page = 1;
                getWebData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                page++;
                getWebData();
            }
        });
        getWebData();
    }

    private void getWebData() {
        RequestBody formBody = new FormBody.Builder()
                .add("pageIndex", page + "")
                .add("pageSize", "10")
                .build();

        HttpUtile httpUtile = new HttpUtile(this, Constant.DOMAIN_NAME + Constant.GETPUSHBASICDATA, formBody, new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {
                Message message = new Message();
                message.what = 0;
                message.obj = returnStr;
                handler.sendMessage(message);
            }

            @Override
            public void getReturnStrFailure(String returnStr) {
                handler.sendEmptyMessage(1);
                Looper.prepare();
                Toast.makeText(PushActivity.this, returnStr, Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void getErrorStr(String errorStr) {
                handler.sendEmptyMessage(1);
            }
        },false);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (resultCode == RESULT_OK && data != null) {
                dataBeans = (List<PushSelectHospitalBase.DataBean>) data.getSerializableExtra("datas");
                String names = "";
                for (PushSelectHospitalBase.DataBean item : dataBeans) {
                    names += item.getName() + ",";
                }
                if (names.length() > 1) {
                    names.substring(0, names.length() - 1);
                }
                selectHospitalTv.setText("我所供应的医院    " + names);
            }
        }
    }

    @OnClick({R.id.return_btn, R.id.select_hospital_btn,R.id.right_tv_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.right_tv_btn:
                //System.out.println("data = "+ adapter.getItem(0).getPrice());
                List<PushValue> passValue = new ArrayList<>();
                List<String> names = new ArrayList<>();
                if (dataBeans!=null) {
                    for (PushSelectHospitalBase.DataBean item : dataBeans) {
                        names.add(item.getGid());
                    }
                    for (int i = 0; i < adapter.getCount(); i++) {
                        PushValue dataBase = new PushValue();
                        dataBase.setHospitalGuid(names);
                        dataBase.setName(adapter.getItem(i).getName());
                        dataBase.setManufacturerName(adapter.getItem(i).getManufacturerName());
                        dataBase.setPrice(adapter.getItem(i).getPrice());
                        List<PushValue.listManyBase> itemDatas = new ArrayList<>();
                        for (int c = 0; c < adapter.getItem(i).getListMany().size(); c++) {
                            PushValue.listManyBase item = new PushValue.listManyBase();
                            item.setId(adapter.getItem(i).getListMany().get(c).getId());
                            item.setModel(adapter.getItem(i).getListMany().get(c).getModel());
                            item.setPrice(Double.valueOf(!TextUtils.isEmpty(adapter.getItem(i).getListMany().get(c).getPrice())?adapter.getItem(i).getListMany().get(c).getPrice():"0"));
                            item.setSpecification(adapter.getItem(i).getListMany().get(c).getSpecification());
                            itemDatas.add(item);
                        }
                        dataBase.setListMany(itemDatas);
                        dataBase.setRegistrationNumber(adapter.getItem(i).getRegistrationNumber());
                        passValue.add(dataBase);
                    }
                    String dataJSON = JSON.toJSONString(passValue);
                    HttpUtile httpUtile = new HttpUtile(this, Constant.DOMAIN_NAME + Constant.GETPUSHADD, dataJSON, new HttpUtileCallBack() {
                        @Override
                        public void getReturnStr(String returnStr) {
                            Looper.prepare();
                            AbDialogUtil.removeDialog(PushActivity.this);
                            Toast.makeText(PushActivity.this, "推送成功", Toast.LENGTH_LONG).show();
                            Looper.loop();
                            finish();
                        }

                        @Override
                        public void getReturnStrFailure(String returnStr) {
                            Looper.prepare();
                            AbDialogUtil.removeDialog(PushActivity.this);
                            Toast.makeText(PushActivity.this, returnStr, Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }

                        @Override
                        public void getErrorStr(String errorStr) {
                            Looper.prepare();
                            AbDialogUtil.removeDialog(PushActivity.this);
                            Toast.makeText(PushActivity.this, errorStr, Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }
                    },true);
                }else{
                    AbToastUtil.showToast(this,"请选择医院");
                }



                break;
            case R.id.return_btn:
                finish();
                break;
            case R.id.select_hospital_btn:
                Intent intent = new Intent(this, PushSelectHospitalActivity.class);
                startActivityForResult(intent, 10);
                break;
        }
    }

    @OnClick(R.id.right_tv_btn)
    public void onViewClicked() {
    }
}
