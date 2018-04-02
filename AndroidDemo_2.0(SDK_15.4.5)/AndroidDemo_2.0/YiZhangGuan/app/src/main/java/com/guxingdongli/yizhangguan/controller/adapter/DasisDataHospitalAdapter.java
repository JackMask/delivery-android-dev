package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.model.DasisDataHospitalBase;
import com.guxingdongli.yizhangguan.view.home.home_hospital.fragment.callback.StorageHospitalOnClickCallBack;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/23
 */

public class DasisDataHospitalAdapter extends BaseCommAdapter<DasisDataHospitalBase.DataBeanX.DataBean> {

    private StorageHospitalOnClickCallBack clickCallBack;

    public DasisDataHospitalAdapter(List<DasisDataHospitalBase.DataBeanX.DataBean> datas) {
        super(datas);
    }

    public StorageHospitalOnClickCallBack getClickCallBack() {
        return clickCallBack;
    }

    public void setClickCallBack(StorageHospitalOnClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    @Override
    protected void setUI(ViewHolder holder, final int position, final Context context) {
        final DasisDataHospitalBase.DataBeanX.DataBean data = getItem(position);

        final LinearLayout item_layout = holder.getItemView(R.id.item_layout);
        LinearLayout linearLayout = holder.getItemView(R.id.linearLayout);
        linearLayout.setVisibility(View.GONE);
        LinearLayout price_layout = holder.getItemView(R.id.price_layout);
        price_layout.setVisibility(View.GONE);
        TextView tab_model = holder.getItemView(R.id.tab_model);
        TextView factory_tab = holder.getItemView(R.id.factory_tab);
        TextView registration_number = holder.getItemView(R.id.registration_number);
        TextView name_tv = holder.getItemView(R.id.name_tv);
        TextView model_tv = holder.getItemView(R.id.model_tv);
        TextView factory_name = holder.getItemView(R.id.factory_name);
        TextView registration_number_tv = holder.getItemView(R.id.registration_number_tv);
        tab_model.setText("68码：");
        registration_number.setText("价格：");
        factory_tab.setText("供应医院：");
        name_tv.setText(data.getName());
        model_tv.setText(data.getCode());
        factory_name.setText(data.getHospitalName());
        registration_number_tv.setText(data.getPrice()+"");

        /*item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DaisDataDetailsActivity.class);
                intent.putExtra("data",data);
                context.startActivity(intent);
            }
        });*/


    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_dasis_data;
    }


}