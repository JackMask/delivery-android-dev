package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.model.RemindBase;
import com.guxingdongli.yizhangguan.view.message.MessageDetailsActivity;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.List;

/**
 * Created by jackmask on 2018/3/3.
 */

public class RemindAdapter  extends BaseCommAdapter<RemindBase.DataBase> {
    public RemindAdapter(List<RemindBase.DataBase> datas) {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, int position, final Context context) {
        RemindBase.DataBase data = getItem(position);
        final TextView title_name = holder.getItemView(R.id.title_name);
        TextView num_tv = holder.getItemView(R.id.num_tv);
        View  line = holder.getItemView(R.id.line);
        title_name.setText(data.getName());
        num_tv.setText(data.getQuantity()+"");
        if (mDatas.size()-1==position){
            line.setVisibility(View.GONE);
        }else{
            line.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_remind;
    }
}
