package com.yum.two_yum.view.client.business;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.BusinessDetailsBase;
import com.yum.two_yum.controller.adapter.fragment.StorageHospitalAdapter;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.view.client.business.fragment.DishesDetailsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 余先德
 * @data 2018/4/10
 */

public class DishesDetailsActivity extends BaseActivity {

    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.del_btn)
    ImageView delBtn;
    @Bind(R.id.share_btn)
    ImageView shareBtn;
    @Bind(R.id.tab_layou)
    RelativeLayout tabLayou;
    @Bind(R.id.dish_name)
    TextView dishName;
    @Bind(R.id.price_name)
    TextView priceName;
    @Bind(R.id.content_tv)
    TextView contentTv;
    @Bind(R.id.content_ll)
    LinearLayout contentLl;

    private StorageHospitalAdapter viewPagerAdapter;
    private List<Fragment> fragments = new ArrayList<>();
    private String[] titles;
    private List<String> urlList = new ArrayList<>();
    private List<BusinessDetailsBase.DataBean.MerchantMenuListRespResultBean.MerchantMenuRespResultsBean> dataList;
    private List<String> urls = new ArrayList<>();
    private BusinessDetailsBase.DataBean.MerchantMenuListRespResultBean.MerchantMenuRespResultsBean data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishes_details);
        ButterKnife.bind(this);
        setView();

    }
    private String id;

    @Override
    protected void setView() {
        super.setView();
        id = getIntent().getStringExtra("id");
        dataList = (ArrayList<BusinessDetailsBase.DataBean.MerchantMenuListRespResultBean.MerchantMenuRespResultsBean>) getIntent().getSerializableExtra("data");
       // data = (BusinessDetailsBase.DataBean.MerchantMenuListRespResultBean.MerchantMenuRespResultsBean) getIntent().getSerializableExtra("data");
//        if (data!=null){
//            dishName.setText(data.getName());
//            priceName.setText("$ "+data.getPrice());
//            contentTv.setText(data.getDescribe());
//            urlList.add(data.getCover());
//        }
        int con = 0;
        if (dataList!=null&&dataList.size()>0){
            a:for (int i = 0 ; i < dataList.size();i++){
                if (TextUtils.isEmpty(dataList.get(i).getId())){
                    dataList.remove(i);
                    break a;
                }
            }
            titles = new String[dataList.size()];
            for (int i = 0; i < dataList.size(); i++) {
                if (dataList.get(i).getId().equals(id)){
                    con = i;
                    dishName.setText(dataList.get(i).getName());
                    priceName.setText("$ "+ AppUtile.getPrice(dataList.get(i).getPrice()+""));
                    contentTv.setText(dataList.get(i).getDescribe());
                    urlList.add(dataList.get(i).getCover());
                }
                titles[i] = dataList.get(i).getCover();
                urls.add(dataList.get(i).getCover());
            }
        }


        for (String url : urls) {
            fragments.add(getFragment(url));
        }
        viewPagerAdapter = new StorageHospitalAdapter(getSupportFragmentManager(), titles, fragments);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(con);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //切换页面的监听：开始默认是0
                dishName.setText(dataList.get(position).getName());
                priceName.setText("$ "+AppUtile.getPrice(dataList.get(position).getPrice()+""));
                contentTv.setText(dataList.get(position).getDescribe());
                urlList.add(dataList.get(position).getCover());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 获取fragment
     *
     * @param pic
     * @return
     */
    private DishesDetailsFragment getFragment(String pic) {
        DishesDetailsFragment fragment = new DishesDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("PIC", pic);
        fragment.setArguments(bundle);
        return fragment;

    }

    @OnClick({R.id.del_btn, R.id.share_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.del_btn:
                finish();
                overridePendingTransition(R.anim.activity_open,R.anim.in);
                break;
            case R.id.share_btn:
                Intent share_intent = new Intent();

                share_intent.setAction(Intent.ACTION_SEND);

                share_intent.setType("text/plain");

                share_intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.SHARENEW));

                share_intent.putExtra(Intent.EXTRA_TEXT, "2Yum Food Delivery \n http://www.2yum.app/yum/share");

                share_intent = Intent.createChooser(share_intent, getString(R.string.SHARENEW));

                startActivity(share_intent);
                break;
        }
    }
}
