package com.yum.two_yum.controller.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.yum.two_yum.R;
import com.yum.two_yum.controller.adapter.callback.ClientNearbyCallBack;
import com.yum.two_yum.controller.adapter.universalAdapter.BaseCommAdapter;
import com.yum.two_yum.controller.adapter.universalAdapter.ViewHolder;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/4/11
 */

public class NumberDianAdapter extends BaseCommAdapter<Integer> {



    public NumberDianAdapter(List<Integer> datas) {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, final int position, final Context context) {
        int num = mDatas.get(position);
        ImageView one = holder.getItemView(R.id.track_one);
        ImageView two = holder.getItemView(R.id.track_two);
        ImageView line = holder.getItemView(R.id.line);
        if (mDatas.size()-1==position){
            one.setImageResource(R.mipmap.track2);
            line.setVisibility(View.GONE);
            two.setVisibility(View.GONE);
        }else{
            one.setImageResource(R.mipmap.track1);
            two.setVisibility(View.GONE);
            line.setVisibility(View.VISIBLE);

        }


    }


    @Override
    protected int getLayoutId() {
        return R.layout.item_number_dian;
    }
}