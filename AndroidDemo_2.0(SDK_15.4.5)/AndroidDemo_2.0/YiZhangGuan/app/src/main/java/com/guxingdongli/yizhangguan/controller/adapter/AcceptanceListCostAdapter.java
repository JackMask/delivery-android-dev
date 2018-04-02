package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.model.AcceptanceListCostBase;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.List;

/**
 * Created by jackmask on 2018/3/6.
 */

public class AcceptanceListCostAdapter extends BaseCommAdapter<AcceptanceListCostBase.DataBean> {
    public AcceptanceListCostAdapter(List<AcceptanceListCostBase.DataBean> datas) {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, int position, final Context context) {
        AcceptanceListCostBase.DataBean data = getItem(position);
        TextView name = holder.getItemView(R.id.name);
        TextView plan_pice = holder.getItemView(R.id.plan_pice);
        TextView actual_pice = holder.getItemView(R.id.actual_pice);
        TextView source_tv = holder.getItemView(R.id.source_tv);
        name.setText(data.getFeeName());
        plan_pice.setText(data.getQuotePrice()+"");
        actual_pice.setText(data.getActualPrice()+"");
        source_tv.setText(data.getFundSource());

    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_acceptance_list_cost;
    }
}

