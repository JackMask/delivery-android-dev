package com.guxingdongli.yizhangguan.view.home.home_merchants;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.fragment.StorageHospitalAdapter;
import com.guxingdongli.yizhangguan.util.AppUtile;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.guxingdongli.yizhangguan.view.CustomScanActivity;
import com.guxingdongli.yizhangguan.view.home.home_merchants.fragment.MyOrderFragment;
import com.guxingdongli.yizhangguan.view.home.home_merchants.fragment.MyStockOrderFragment;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的备货单
 * Created by jackmask on 2018/3/8.
 */

public class PreparationOrderActivity extends YiZhangGuanActivity implements TabLayout.OnTabSelectedListener {

    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.right_img_search_btn)
    ImageView rightImgSearchBtn;
    @Bind(R.id.right_img_btn)
    ImageView rightImgBtn;
    @Bind(R.id.tab_layou)
    TabLayout tabLayou;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.search_et)
    EditText searchEt;
    @Bind(R.id.search_layout)
    LinearLayout searchLayout;
    @Bind(R.id.time_image)
    ImageView timeImage;


    private String[] titles = new String[]{"全部（0）", "未处理", "已处理"};
    private List<Fragment> fragments = new ArrayList<>();
    private StorageHospitalAdapter viewPagerAdapter;
    private String keyword = "";
    private boolean type = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_hospital);
        ButterKnife.bind(this);
        setView();
    }

    private void setView() {
        titleText.setText("我的备货单");
        rightImgSearchBtn.setVisibility(View.VISIBLE);
        rightImgSearchBtn.setImageResource(R.mipmap.searchimg);
        searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId,KeyEvent event)  {
                if ((event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER))
                {
                    keyword = searchEt.getText().toString().trim();
                    ((MyOrderFragment)fragments.get(viewPager.getCurrentItem())).setPage();
                    ((MyOrderFragment)fragments.get(viewPager.getCurrentItem())).setSortOrder(keyword,type);
                    return true;
                }
                return false;
            }
        });

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
        fragments.add(getFragment("over"));

        viewPagerAdapter = new StorageHospitalAdapter(getSupportFragmentManager(), titles, fragments);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayou.setupWithViewPager(viewPager);

        tabLayou.getTabAt(1).select();
        viewPager.setCurrentItem(1);
    }
    public void setAllNum(int allNum){
        tabLayou.getTabAt(0).setText("全部("+allNum+")");
    }
    /**
     * 获取fragment
     *
     * @param type
     * @return
     */
    private MyStockOrderFragment getFragment(String type) {
        MyStockOrderFragment fragment = new MyStockOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("DATA", type);
        bundle.putString("type", "PreparationOrder");
        fragment.setArguments(bundle);
        return fragment;

    }

    @OnClick({R.id.return_btn, R.id.right_img_search_btn, R.id.right_img_btn
            , R.id.time_layout,R.id.search_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.time_layout:
                type = !type;
                AppUtile.setTitleFilter(type,timeImage);
                ((MyOrderFragment)fragments.get(viewPager.getCurrentItem())).setPage();
                ((MyOrderFragment)fragments.get(viewPager.getCurrentItem())).setSortOrder(keyword,type);

                break;
            case R.id.search_btn:
                keyword = searchEt.getText().toString().trim();
                ((MyOrderFragment)fragments.get(viewPager.getCurrentItem())).setPage();
                ((MyOrderFragment)fragments.get(viewPager.getCurrentItem())).setSortOrder(keyword,type);
                break;
            case R.id.return_btn: //返回
                finish();
                break;
            case R.id.right_img_search_btn:
                if (searchLayout.getVisibility() == View.VISIBLE) {
                    searchLayout.setVisibility(View.GONE);
                } else {
                    searchLayout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.right_img_btn://二维码
                new IntentIntegrator(this)
                        .setOrientationLocked(false)
                        .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                        .initiateScan(); // 初始化扫描
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

