package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.YiZhangGuanApplication;
import com.guxingdongli.yizhangguan.model.StorageHospitalBase;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.List;

/**
 * Created by jackmask on 2018/3/2.
 */

public class StorageHospitalChildNewAdapter extends BaseCommAdapter<StorageHospitalBase.DataBeanX.DataBean.DetailsListBean> {
    private boolean clickType = true;
    public StorageHospitalChildNewAdapter(List<StorageHospitalBase.DataBeanX.DataBean.DetailsListBean> datas) {
        super(datas);
    }

    public void setClick(boolean clickType){
        this.clickType = clickType;
    }

    @Override
    protected void setUI(ViewHolder holder, int position, final Context context) {
        final StorageHospitalBase.DataBeanX.DataBean.DetailsListBean data = getItem(position);
        LinearLayout supplier_layout = holder.getItemView(R.id.supplier_layout);
        TextView name_tv = holder.getItemView(R.id.name_tv);
        TextView model_tv = holder.getItemView(R.id.model_tv);
        TextView num_tv = holder.getItemView(R.id.num_tv);
        TextView price_tv = holder.getItemView(R.id.price_tv);
        TextView batch_number = holder.getItemView(R.id.batch_number);
        TextView validity_period = holder.getItemView(R.id.validity_period);
        final LinearLayout item_layout = holder.getItemView(R.id.item_layout);
        supplier_layout.setVisibility(View.VISIBLE);
        name_tv.setText(data.getName());
        model_tv.setText(data.getSpecification());
        num_tv.setText(data.getQuantity()+data.getUnit());
        price_tv.setText(data.getPrice()+"");
        batch_number.setText(data.getBatchNumber());
        validity_period.setText(data.getValid());


    }


    @Override
    protected int getLayoutId() {
        return R.layout.item_storage_hospital_child;
    }
}