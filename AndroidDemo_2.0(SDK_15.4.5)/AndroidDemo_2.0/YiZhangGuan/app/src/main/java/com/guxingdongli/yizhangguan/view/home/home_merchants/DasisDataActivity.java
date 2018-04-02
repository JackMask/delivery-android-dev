package com.guxingdongli.yizhangguan.view.home.home_merchants;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.fragment.StorageHospitalAdapter;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.guxingdongli.yizhangguan.view.home.home_merchants.fragment.DasisDataFragment;
import com.guxingdongli.yizhangguan.view.home.home_merchants.fragment.MyOrderFragment;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 基础数据
 * Created by jackmask on 2018/3/8.
 */

public class DasisDataActivity extends YiZhangGuanActivity implements TabLayout.OnTabSelectedListener {

    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.tab_layou)
    TabLayout tabLayou;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    private String[] titles = new String[]{"我的基础数据", "医院基础数据"};
    private List<Fragment> fragments = new ArrayList<>();
    private StorageHospitalAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_hospital);
        ButterKnife.bind(this);
        setView();
    }

    private void setView(){
        titleText.setText("我的基础数据");
        //设置TabLayout标签的显示方式
        tabLayou.setTabMode(TabLayout.MODE_FIXED);
        //循环注入标签
        for (String tab : titles) {
            tabLayou.addTab(tabLayou.newTab().setText(tab));
        }
        //设置TabLayout点击事件
        tabLayou.addOnTabSelectedListener(this);

        fragments.add(getFragment("my"));
        fragments.add(getFragment("hospital"));

        viewPagerAdapter = new StorageHospitalAdapter(getSupportFragmentManager(), titles, fragments);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayou.setupWithViewPager(viewPager);
    }

    /**
     * 获取fragment
     *
     * @param type
     * @return
     */
    private DasisDataFragment getFragment(String type) {
        DasisDataFragment fragment = new DasisDataFragment();
        Bundle bundle = new Bundle();
        bundle.putString("DATA", type);
        fragment.setArguments(bundle);
        return fragment;

    }

    @OnClick(R.id.return_btn)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
