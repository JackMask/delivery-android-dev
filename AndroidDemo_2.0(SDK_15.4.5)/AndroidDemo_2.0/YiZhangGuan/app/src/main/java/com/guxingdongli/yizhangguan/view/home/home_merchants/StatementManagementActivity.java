package com.guxingdongli.yizhangguan.view.home.home_merchants;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.fragment.StorageHospitalAdapter;
import com.guxingdongli.yizhangguan.util.AppUtile;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.guxingdongli.yizhangguan.view.home.home_merchants.fragment.StatementManagementFragment;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 对账单管理
 * Created by jackmask on 2018/3/9.
 */

public class StatementManagementActivity extends YiZhangGuanActivity implements TabLayout.OnTabSelectedListener {

    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.right_img_btn)
    ImageView rightImgBtn;
    @Bind(R.id.tab_layou)
    TabLayout tabLayou;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.right_img_search_btn)
    ImageView rightImgSearchBtn;
    @Bind(R.id.time_image)
    ImageView timeImage;

    @Bind(R.id.search_layout)
    LinearLayout searchLayout;

    private String[] titles = new String[]{"对账单请求汇总", "已请求账单汇总"};
    private List<Fragment> fragments = new ArrayList<>();
    private StorageHospitalAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_hospital);
        ButterKnife.bind(this);
        setView();
    }

    private void setView() {
        titleText.setText("对账单管理");
        rightImgBtn.setVisibility(View.VISIBLE);
        rightImgSearchBtn.setVisibility(View.GONE);
        rightImgBtn.setImageResource(R.mipmap.plus_img);

        //设置TabLayout标签的显示方式
        tabLayou.setTabMode(TabLayout.MODE_FIXED);
        //循环注入标签
        for (String tab : titles) {
            tabLayou.addTab(tabLayou.newTab().setText(tab));
        }
        //设置TabLayout点击事件
        tabLayou.addOnTabSelectedListener(this);


        fragments.add(getFragment("all"));
        fragments.add(getFragment("no"));

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
    private StatementManagementFragment getFragment(String type) {
        StatementManagementFragment fragment = new StatementManagementFragment();
        Bundle bundle = new Bundle();
        bundle.putString("DATA", type);
        fragment.setArguments(bundle);
        return fragment;

    }


    @OnClick({R.id.return_btn, R.id.right_img_btn, R.id.right_img_search_btn, R.id.time_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_btn:
                finish();
                break;
            case R.id.right_img_btn:
                Intent intent = new Intent(this, AddStatementActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.right_img_search_btn:
                if (searchLayout.getVisibility() == View.VISIBLE) {
                    searchLayout.setVisibility(View.GONE);
                } else {
                    searchLayout.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.time_layout:
                AppUtile.setTitleFilter(true, timeImage);
                break;
        }

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
