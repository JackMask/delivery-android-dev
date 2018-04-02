package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.model.AcceptanceListComponentsBase;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.List;

/**
 * Created by jackmask on 2018/3/6.
 */

public class AcceptanceListComponentsAdapter extends BaseCommAdapter<AcceptanceListComponentsBase.DataBean> {
    public AcceptanceListComponentsAdapter(List<AcceptanceListComponentsBase.DataBean> datas) {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, int position, final Context context) {
        AcceptanceListComponentsBase.DataBean data = getItem(position);
        TextView name = holder.getItemView(R.id.name);
        TextView accessories_type = holder.getItemView(R.id.accessories_type);
        TextView amount = holder.getItemView(R.id.amount);
        name.setText(data.getName());
        accessories_type.setText(data.getFittingType());
        amount.setText(data.getPrice()+"");

    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_acceptanec_list_components;
    }
}

