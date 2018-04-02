package com.guxingdongli.yizhangguan.controller.adapter.fragment;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.model.StatementManagementBase;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.List;

/**
 * Created by jackmask on 2018/3/9.
 */

public class StatementManagementFragmentAdapter extends BaseCommAdapter<StatementManagementBase.DataBeanX.DataBean> {
    public StatementManagementFragmentAdapter(List<StatementManagementBase.DataBeanX.DataBean> datas) {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, int position, final Context context) {
        StatementManagementBase.DataBeanX.DataBean data = mDatas.get(position);
        TextView request_time = holder.getItemView(R.id.request_time);
        TextView end_time = holder.getItemView(R.id.end_time);
        TextView start_time = holder.getItemView(R.id.start_time);
        TextView hospital = holder.getItemView(R.id.hospital);
        TextView type = holder.getItemView(R.id.type);
        TextView single_number = holder.getItemView(R.id.single_number);
        single_number.setText(data.getRequestNumber());
        type.setText(data.getHandleStatus());
        hospital.setText(data.getHospitalName());
        start_time.setText(data.getReconciliationDate1());
        end_time.setText(data.getReconciliationDate2());
        request_time.setText(data.getCreateTime());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.itme_statement_management_fragment;
    }
}
