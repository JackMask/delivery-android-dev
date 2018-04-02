package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.model.MaintenanceDataBase;
import com.guxingdongli.yizhangguan.model.MyRepairOrderDetailsContentBasicInfoBase;
import com.yuxiaolong.yuxiandelibrary.NoSlideListView;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.List;

/**
 * Created by jackmask on 2018/3/7.
 */

public class MaintenanceRecordsAdapter  extends BaseCommAdapter<MaintenanceDataBase> {

    public MaintenanceRecordsAdapter(List<MaintenanceDataBase> datas) {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, int position, Context context) {
        MaintenanceDataBase data = getItem(position);
        NoSlideListView data_list = holder.getItemView(R.id.data_list);
        MyRepairOrderDetailsContentOneAdapter basicInformationAdapter = new MyRepairOrderDetailsContentOneAdapter(data.getDataList());
        data_list.setAdapter(basicInformationAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_maintenance_records;
    }
}