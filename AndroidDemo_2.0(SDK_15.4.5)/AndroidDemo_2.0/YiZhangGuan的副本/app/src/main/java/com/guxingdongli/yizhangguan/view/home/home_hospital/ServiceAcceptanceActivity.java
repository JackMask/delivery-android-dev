package com.guxingdongli.yizhangguan.view.home.home_hospital;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.util.AbToastUtil;
import com.google.zxing.integration.android.IntentIntegrator;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.fragment.StorageHospitalAdapter;
import com.guxingdongli.yizhangguan.model.passvalue.ScanCodeInput;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.guxingdongli.yizhangguan.view.CustomScanActivity;
import com.guxingdongli.yizhangguan.view.home.dialog.ScanCodeDialog;
import com.guxingdongli.yizhangguan.view.home.home_hospital.fragment.ServiceAcceptanceFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 维修验收
 * Created by jackmask on 2018/3/5.
 */

public class ServiceAcceptanceActivity extends YiZhangGuanActivity implements TabLayout.OnTabSelectedListener {

    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.right_img_btn)
    ImageView rightImgBtn;
    @Bind(R.id.tab_layou)
    TabLayout tabLayou;
    @Bind(R.id.view_pager)
    ViewPager viewPager;


    //TabLayout标签
    private String[] titles = new String[]{"未验收", "已验收"};
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
        titleText.setText("维修验收");
        rightImgBtn.setVisibility(View.VISIBLE);

        //设置TabLayout标签的显示方式
        tabLayou.setTabMode(TabLayout.MODE_FIXED);
        //循环注入标签
        for (String tab : titles) {
            tabLayou.addTab(tabLayou.newTab().setText(tab));
        }
        //设置TabLayout点击事件
        tabLayou.setOnTabSelectedListener(this);

        fragments.add(getFragment("find"));
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
    private ServiceAcceptanceFragment getFragment(String type) {
        ServiceAcceptanceFragment fragment = new ServiceAcceptanceFragment();
        Bundle bundle = new Bundle();
        bundle.putString("DATA", type);
        fragment.setArguments(bundle);
        return fragment;

    }

    @OnClick({R.id.return_btn, R.id.right_img_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_btn:
                finish();
                break;
            case R.id.right_img_btn:
                new IntentIntegrator(this)
                        .setOrientationLocked(false)
                        .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                        .initiateScan(); // 初始化扫描
                break;
        }

    }

    @Override
    protected void getQr(String ScanResult) {
        super.getQr(ScanResult);
        try {
            String[] strarray = ScanResult.split("[|]");
            ScanCodeInput data = new ScanCodeInput();
            data.setType(ScanCodeDialog.ACCEPTANCE);
            data.setGuid(strarray[0]);
            data.setSingle_or_numbering(strarray[1]);
            Intent intent = new Intent(this, ScanCodeDialog.class);
            intent.putExtra("data", data);
            startActivityForResult(intent, 0);
        }catch (Exception e){
            AbToastUtil.showToast(this,"二维码无效");
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
