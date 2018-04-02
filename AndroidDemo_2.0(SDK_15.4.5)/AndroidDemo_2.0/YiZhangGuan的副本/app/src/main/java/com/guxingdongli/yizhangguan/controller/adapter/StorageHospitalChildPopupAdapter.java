package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.List;

/**
 * Created by jackmask on 2018/3/3.
 */

public class StorageHospitalChildPopupAdapter extends BaseCommAdapter<String> {

    public StorageHospitalChildPopupAdapter(List<String> datas) {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, int position, final Context context) {
        String data = getItem(position);
        TextView name_tv = holder.getItemView(R.id.name_tv);
        TextView content_tv = holder.getItemView(R.id.content_tv);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_popup_storage_hospital;
    }
}
