package com.guxingdongli.yizhangguan.view.home.home_merchants;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.SelectAvailabilityHospitalAdapter;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;
import com.yuxiaolong.yuxiandelibrary.pulltorefresh.PullToRefreshBase;
import com.yuxiaolong.yuxiandelibrary.pulltorefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jackmask on 2018/3/9.
 */

public class SelectAvailabilityHospitalActivity extends YiZhangGuanActivity {


    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.right_tv_btn)
    TextView rightTvBtn;
    @Bind(R.id.content_list)
    PullToRefreshListView contentList;

    private List<String> hospitalListString = new ArrayList<>(),hospitalNameList = new ArrayList<>();
    private SelectAvailabilityHospitalAdapter availabilityHospitalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_hospital);
        ButterKnife.bind(this);
        setView();
    }

    private void setView(){
        titleText.setText("选择医院");
        rightTvBtn.setVisibility(View.VISIBLE);
        rightTvBtn.setText("保存");
        rightTvBtn.setTextColor(Color.parseColor("#2ea1fb"));
        for (int i = 0 ; i < 5 ; i ++){
            hospitalListString.add("金牛区人民医院");
        }
        availabilityHospitalAdapter = new SelectAvailabilityHospitalAdapter(hospitalListString);
        contentList.setMode(PullToRefreshBase.Mode.DISABLED);
        contentList.setAdapter(availabilityHospitalAdapter);


        contentList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //刷新

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //加载


            }
        });

    }
    public void selectHospital(String id){

        hospitalNameList.add(id);
    }
    public void delHospital(String id){
        for (int i = 0 ; i <hospitalNameList.size() ;i++){
            if (hospitalNameList.get(i).equals(id)) {
                hospitalNameList.remove(i);
            }
        }
    }
    @OnClick({R.id.return_btn, R.id.right_tv_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_btn:
                finish();
                break;
            case R.id.right_tv_btn:
                String selectName = hospitalNameList.toString();
                if (!TextUtils.isEmpty(selectName)) {
                    Intent intent = new Intent();
                    intent.putExtra("name", selectName.substring(1, selectName.length() - 1));
                    setResult(1001,intent);
                }
                finish();
                break;
        }
    }
}
