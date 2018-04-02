package com.guxingdongli.yizhangguan.view.home.home_hospital;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ab.util.AbToastUtil;
import com.dmcbig.mediapicker.PickerActivity;
import com.dmcbig.mediapicker.PickerConfig;
import com.google.zxing.integration.android.IntentIntegrator;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.YiZhangGuanApplication;
import com.guxingdongli.yizhangguan.controller.adapter.fragment.StorageHospitalAdapter;
import com.guxingdongli.yizhangguan.model.ProvinceBean;
import com.guxingdongli.yizhangguan.model.passvalue.ScanCodeInput;
import com.guxingdongli.yizhangguan.util.AppUtile;
import com.guxingdongli.yizhangguan.util.GetPickerStrCallBack;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.guxingdongli.yizhangguan.view.CustomScanActivity;
import com.guxingdongli.yizhangguan.view.home.dialog.ScanCodeDialog;
import com.guxingdongli.yizhangguan.view.home.home_hospital.fragment.MyRepairOrderFragment;
import com.guxingdongli.yizhangguan.view.home.home_hospital.fragment.StorageHospitalFragment;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TakePhotoOptions;
import com.yuxiaolong.yuxiandelibrary.ActionSheetDialog;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;
import com.yuxiaolong.yuxiandelibrary.pickerview.OptionsPickerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 维修单总汇
 * Created by jackmask on 2018/3/3.
 */

public class MyRepairOrderActivity extends YiZhangGuanActivity implements TabLayout.OnTabSelectedListener {

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
    @Bind(R.id.time_image)
    ImageView timeImage;
    @Bind(R.id.time_layout)
    LinearLayout timeLayout;

    @Bind(R.id.search_et)
    EditText searchEt;
    @Bind(R.id.search_layout)
    LinearLayout searchLayout;
    @Bind(R.id.right_img_add_btn)
    ImageView rightImgAddBtn;
    //TabLayout标签
    private String[] titles = new String[]{"全部", "待维修", "已维修"};
    private List<Fragment> fragments = new ArrayList<>();
    private StorageHospitalAdapter viewPagerAdapter;

    private String keyword = "";
    private boolean type = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_repair_order);
        ButterKnife.bind(this);
        setView();
    }

    private void setView() {
        titleText.setText("维修单汇总");
        rightImgSearchBtn.setVisibility(View.VISIBLE);
        rightImgBtn.setVisibility(View.VISIBLE);
        rightImgAddBtn.setVisibility(View.VISIBLE);
        searchEt.setHint("维修单号/设备名称");
        searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId,KeyEvent event)  {
                if ((event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER))
                {
                    keyword = searchEt.getText().toString().trim();
                    ((MyRepairOrderFragment)fragments.get(viewPager.getCurrentItem())).setPage();
                    ((MyRepairOrderFragment)fragments.get(viewPager.getCurrentItem())).setSortOrder(keyword,type);
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
        tabLayou.setOnTabSelectedListener(this);

        fragments.add(getFragment("all"));
        fragments.add(getFragment("not"));
        fragments.add(getFragment("already"));


        viewPagerAdapter = new StorageHospitalAdapter(getSupportFragmentManager(), titles, fragments);
        viewPager.setAdapter(viewPagerAdapter);

        tabLayou.setupWithViewPager(viewPager);
        tabLayou.getTabAt(1).select();
        viewPager.setCurrentItem(1);

        searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener(){

            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                // TODO Auto-generated method stub
                if(arg1 == EditorInfo.IME_ACTION_SEARCH)
                {
                    keyword = searchEt.getText().toString().trim();
                    ((MyRepairOrderFragment)fragments.get(viewPager.getCurrentItem())).setPage();
                    ((MyRepairOrderFragment)fragments.get(viewPager.getCurrentItem())).setSortOrder(keyword,type);
                    // search pressed and perform your functionality.
                }
                return false;
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1001){
            for (Fragment fragmentPage:fragments){
                ((MyRepairOrderFragment)fragmentPage).refresh();
            }
        }

    }

    public void setTabText(int num){
        tabLayou.getTabAt(0).setText("全部("+num+")");
    }



    private MyRepairOrderFragment getFragment(String type) {
        MyRepairOrderFragment fragment = new MyRepairOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("DATA", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @OnClick({R.id.return_btn, R.id.right_img_search_btn,R.id.right_img_add_btn, R.id.right_img_btn, R.id.time_layout,R.id.search_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.right_img_btn://扫一扫
                new IntentIntegrator(this)
                        .setOrientationLocked(false)
                        .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                        .initiateScan(); // 初始化扫描
                break;
            case R.id.right_img_add_btn://添加报修
                new ActionSheetDialog(MyRepairOrderActivity.this)
                        .builder()
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem("选择设备报修", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        Intent intent = new Intent(MyRepairOrderActivity.this, AssetsRepairActivity.class);
                                        startActivityForResult(intent,1);

                                    }
                                })
                        .addSheetItem("手工录入报修", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        //AbToastUtil.showToast(InteractiveMessage.this,"清空所有消息");
                                        Intent intent = new Intent(MyRepairOrderActivity.this, AddRepairActivity.class);
                                        startActivityForResult(intent,1);
                                    }
                                })
                        .show();
                break;
            case R.id.right_img_search_btn://显示搜索
                if (searchLayout.getVisibility() == View.VISIBLE) {
                    searchLayout.setVisibility(View.GONE);
                } else {
                    searchLayout.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.return_btn:
                finish();
                break;

            case R.id.time_layout:
                type = !type;
                AppUtile.setTitleFilter(type,timeImage);
                ((MyRepairOrderFragment)fragments.get(viewPager.getCurrentItem())).setPage();
                ((MyRepairOrderFragment)fragments.get(viewPager.getCurrentItem())).setSortOrder(keyword,type);
                break;
            case R.id.search_btn:
                keyword = searchEt.getText().toString().trim();
                ((MyRepairOrderFragment)fragments.get(viewPager.getCurrentItem())).setPage();
                ((MyRepairOrderFragment)fragments.get(viewPager.getCurrentItem())).setSortOrder(keyword,type);
                break;
        }
    }


    /**
     * 扫二维码返回值
     *
     * @param ScanResult 二维码返回值
     */
    @Override
    protected void getQr(String ScanResult) {
        super.getQr(ScanResult);
        try {
            String[] strarray = ScanResult.split("[|]");
            ScanCodeInput data = new ScanCodeInput();
            data.setType(ScanCodeDialog.TIMING);
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
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
