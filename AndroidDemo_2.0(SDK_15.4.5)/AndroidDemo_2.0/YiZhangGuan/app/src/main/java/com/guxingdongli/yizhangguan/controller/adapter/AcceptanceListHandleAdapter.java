package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.model.AcceptanceListHandleBase;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.List;

/**
 * Created by jackmask on 2018/3/6.
 */

public class AcceptanceListHandleAdapter extends BaseCommAdapter<AcceptanceListHandleBase.DataBean> {
    public AcceptanceListHandleAdapter(List<AcceptanceListHandleBase.DataBean> datas) {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, int position, final Context context) {
        AcceptanceListHandleBase.DataBean data = getItem(position);
        TextView user_name = holder.getItemView(R.id.user_name);
        TextView start_time_tv = holder.getItemView(R.id.start_time_tv);
        TextView end_time_tv = holder.getItemView(R.id.end_time_tv);
        TextView man_hour = holder.getItemView(R.id.man_hour);
        TextView long_shutdown = holder.getItemView(R.id.long_shutdown);
        TextView remark_tv = holder.getItemView(R.id.remark_tv);
        user_name.setText(data.getMaintenanceEngineer());
        start_time_tv.setText(data.getMaintenanceStartDate());
        end_time_tv.setText(data.getMaintenanceEndDate());
        man_hour.setText(data.getManHour()+"");
        long_shutdown.setText(data.getLongShutdown()+"");
        remark_tv.setText(data.getMaintenanceSituation());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_acceptance_list_handle;
    }
}

