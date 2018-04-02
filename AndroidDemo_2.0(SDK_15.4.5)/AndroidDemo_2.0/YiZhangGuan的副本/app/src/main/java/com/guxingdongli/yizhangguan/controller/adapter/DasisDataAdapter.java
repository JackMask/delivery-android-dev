package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.model.DasisDataBase;
import com.guxingdongli.yizhangguan.model.TestHospitalBean;
import com.guxingdongli.yizhangguan.view.home.dialog.PromptDialog;
import com.guxingdongli.yizhangguan.view.home.home_hospital.fragment.callback.StorageHospitalOnClickCallBack;
import com.guxingdongli.yizhangguan.view.home.home_merchants.DaisDataDetailsActivity;
import com.guxingdongli.yizhangguan.view.home.home_merchants.MakeUpInfoActivity;
import com.guxingdongli.yizhangguan.view.home.home_merchants.QRExamineActivity;
import com.yuxiaolong.yuxiandelibrary.NoSlideListView;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.List;

/**
 * Created by jackmask on 2018/3/8.
 */

public class DasisDataAdapter extends BaseCommAdapter<DasisDataBase.DataBeanX.DataBean> {

    private StorageHospitalOnClickCallBack clickCallBack;

    public DasisDataAdapter(List<DasisDataBase.DataBeanX.DataBean> datas) {
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
        final DasisDataBase.DataBeanX.DataBean data = getItem(position);
        final LinearLayout item_layout = holder.getItemView(R.id.item_layout);
        TextView specification_tv = holder.getItemView(R.id.specification_tv);
        TextView name_tv = holder.getItemView(R.id.name_tv);
        TextView price_tv = holder.getItemView(R.id.price_tv);
        TextView model_tv = holder.getItemView(R.id.model_tv);
        TextView factory_name = holder.getItemView(R.id.factory_name);
        TextView registration_number_tv = holder.getItemView(R.id.registration_number_tv);

        registration_number_tv.setText(data.getRegistrationNumber());
        model_tv.setText(data.getModel());
        price_tv.setText(data.getPrice()+"");
        factory_name.setText(data.getManufacturerName());
        specification_tv.setText(data.getSpecification());
        name_tv.setText(data.getName());
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