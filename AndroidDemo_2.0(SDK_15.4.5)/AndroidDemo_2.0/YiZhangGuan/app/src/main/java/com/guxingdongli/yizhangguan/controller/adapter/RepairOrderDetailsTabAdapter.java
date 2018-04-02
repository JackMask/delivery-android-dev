package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.model.MyRepairOrderDetailsTabBase;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.List;

/**
 * Created by jackmask on 2018/3/4.
 */

public class RepairOrderDetailsTabAdapter  extends BaseCommAdapter<MyRepairOrderDetailsTabBase> {

    public RepairOrderDetailsTabAdapter(List<MyRepairOrderDetailsTabBase> datas) {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, int position, Context context) {
        MyRepairOrderDetailsTabBase data = getItem(position);
        TextView tab_name = holder.getItemView(R.id.tab_name);
        tab_name.setText(data.getName());
        if (data.isSelectTab()){
            tab_name.setBackgroundResource(R.color.white);
        }else{
            tab_name.setBackgroundResource(R.color.whitesmoke);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_repair_order_details_tab;
    }
}
