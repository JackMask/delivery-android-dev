package com.yum.two_yum.view.merchants.merchantsfragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BankBase;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.BaseReturn;
import com.yum.two_yum.base.CertBase;
import com.yum.two_yum.base.GetMerchantMenuListBase;
import com.yum.two_yum.base.MerchantBase;
import com.yum.two_yum.base.OrdersDistributionBase;
import com.yum.two_yum.base.input.BankInput;
import com.yum.two_yum.base.input.CertInput;
import com.yum.two_yum.base.input.MerchantInput;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.controller.adapter.fragment.StorageHospitalAdapter;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.view.SwitchView;
import com.yum.two_yum.view.dialog.PromptDialog;
import com.yum.two_yum.view.merchants.MerchantsActivity;
import com.yum.two_yum.view.merchants.menu.BankAccountActivity;
import com.yum.two_yum.view.merchants.menu.OperationsSettingsActivity;
import com.yum.two_yum.view.merchants.menu.YourIdActivity;
import com.yum.two_yum.view.merchants.merchantsfragment.childfragment.OrdersCancelFragment;
import com.yum.two_yum.view.merchants.merchantsfragment.childfragment.OrdersDeliverFragment;
import com.yum.two_yum.view.merchants.merchantsfragment.childfragment.OrdersDistributionFragment;
import com.yum.two_yum.view.merchants.merchantsfragment.childfragment.OrdersNewFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 商户的订单
 *
 * @author 余先德
 * @data 2018/4/7
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class OrdersFragment extends Fragment implements TabLayout.OnTabSelectedListener {


    @Bind(R.id.sound_btn)
    ImageView soundBtn;
    @Bind(R.id.v_switch_1)
    SwitchView vSwitch1;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.tab_layou)
    TabLayout tabLayou;
    @Bind(R.id.view_pager)
    ViewPager viewPager;


    private OrdersNewFragment ordersNewFragment;//新单
    private OrdersDistributionFragment distributionFragment ;//配送
    private OrdersDeliverFragment deliverFragment;//交付
    private OrdersCancelFragment cancelFragment;//取消
    private MerchantsActivity abActivity;
    private View view;
    private String[] titles ;
    private List<Fragment> fragments = new ArrayList<>();
    private StorageHospitalAdapter viewPagerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        abActivity = (MerchantsActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_orders_merchants, container, false);
        ButterKnife.bind(this, view);
        setView();
        return view;
    }

    public void refreshTwo(){
        if (distributionFragment!=null){
            distributionFragment.refresh();
        }

    }

    private void setView() {
        //设置TabLayout标签的显示方式
        tabLayou.setTabMode(TabLayout.MODE_FIXED);
        titles  = new String[]{abActivity.getResources().getString(R.string.NEW), abActivity.getResources().getString(R.string.DELIVERYNOW),
                abActivity.getResources().getString(R.string.THENDELIVERED), abActivity.getResources().getString(R.string.THENCANCELED)};
        //循环注入标签
        for (String tab : titles) {
            tabLayou.addTab(tabLayou.newTab().setText(tab));
        }
        //设置TabLayout点击事件
        tabLayou.addOnTabSelectedListener(this);
        ordersNewFragment = new OrdersNewFragment();
        distributionFragment = new OrdersDistributionFragment();
        deliverFragment = new OrdersDeliverFragment();
        cancelFragment = new OrdersCancelFragment();
        fragments.add(ordersNewFragment);
        fragments.add(distributionFragment);
        fragments.add(deliverFragment);
        fragments.add(cancelFragment);


        viewPagerAdapter = new StorageHospitalAdapter(abActivity.getSupportFragmentManager(), titles, fragments);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayou.setupWithViewPager(viewPager);
        vSwitch1.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                //vSwitch1.setOpened(true);
                Intent intent = new Intent(getContext(), PromptDialog.class);
                intent.putExtra("content",abActivity.getResources().getString(R.string.AUTOMATICORDERS));
                getActivity().startActivityForResult(intent,12);
            }

            @Override
            public void toggleToOff(SwitchView view) {

                OkHttpUtils
                        .post()
                        .url(Constant.MERCHANT_AUTOMATION_ACCEPT)
                        .addHeader("Accept-Language", AppUtile.getLanguage())
                        .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                        .addParams("noticeState","0")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int i) {
                                if (AppUtile.getNetWork(e.getMessage())){
                                    ((BaseActivity)getActivity()).showMsg(getString(R.string.NETERROR));
                                }
                            }

                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onResponse(String s, int i) {
                                if (!TextUtils.isEmpty(s)) {
                                    if (AppUtile.setCode(s,(BaseActivity)getActivity())){
                                        vSwitch1.setOpened(false);
                                    }
                                }
                            }
                        });

            }
        });
    }
    public void refreshChildView(int con){
        if (con == 0){
            if (ordersNewFragment!=null){
                ordersNewFragment.refreshData();
            }
        }else if (con == 2){
            if (deliverFragment!=null){
                deliverFragment.refreshData();
            }
        }else if (con == 3){
            if (cancelFragment!=null){
                cancelFragment.refreshData();
            }
        }
    }


    public void setData(List<OrdersDistributionBase.DataBean.DispatchRespResultsBean> pockets){
        if (distributionFragment!=null){
            distributionFragment.setData(pockets);
        }
    }
    public void setSwitch(boolean con){
        vSwitch1.setOpened(con);

    }

    public void cancelClick(String OrderId,String note){
        if (ordersNewFragment!=null){
            ordersNewFragment.cancelOreder(OrderId,note);
        }
    }

    public void openSwitch1(){
        vSwitch1.setOpened(true);
    }
    public void closeSwitch1(){
        vSwitch1.setOpened(false);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        System.out.println("tab.getPosition() = " + tab.getPosition());
        if (tab.getPosition()==2){
            if (deliverFragment!=null){
                deliverFragment.refresh();
            }
        }else if (tab.getPosition()==1){
            if (distributionFragment!=null){
                distributionFragment.refresh();
            }
        }
        viewPager.setCurrentItem(tab.getPosition());
    }


    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
    private void getInfo(final String url){


    }
}
