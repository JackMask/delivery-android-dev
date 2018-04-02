package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.model.MyRepairOrderDetailsContentBasicInfoBase;
import com.guxingdongli.yizhangguan.model.MyRepairOrderDetailsContentOtherBase;
import com.yuxiaolong.yuxiandelibrary.NoSlideListView;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.List;

/**
 * 维修单详情几个选项卡的其中一个页面
 * Created by jackmask on 2018/3/5.
 */

public class MyRepairOrderDetailsContentOtherAdapter extends BaseCommAdapter<MyRepairOrderDetailsContentOtherBase> {

    public MyRepairOrderDetailsContentOtherAdapter(List<MyRepairOrderDetailsContentOtherBase> datas) {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, int position, Context context) {
        MyRepairOrderDetailsContentOtherBase data = getItem(position);
        TextView title_tv = holder.getItemView(R.id.title_tv);
        NoSlideListView content_list = holder.getItemView(R.id.content_list);
        title_tv.setText(data.getTitle());
        MyRepairOrderDetailsContentOtherChildAdapter adapter = new MyRepairOrderDetailsContentOtherChildAdapter(data.getData());
        content_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //content_tv.setText(data.getContent());

    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_my_repair_order_details_other;
    }
}