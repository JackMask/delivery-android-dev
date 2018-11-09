package com.yum.two_yum.controller.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.yum.two_yum.R;
import com.yum.two_yum.base.FilterBase;
import com.yum.two_yum.controller.adapter.callback.ClientNearbyCallBack;
import com.yum.two_yum.controller.adapter.callback.FilterFootCallBack;
import com.yum.two_yum.controller.adapter.universalAdapter.BaseCommAdapter;
import com.yum.two_yum.controller.adapter.universalAdapter.ViewHolder;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/4/10
 */

public class ReportAdapter extends BaseCommAdapter<FilterBase> {

    private FilterFootCallBack callBack;

    public void setCallBack(FilterFootCallBack callBack) {
        this.callBack = callBack;
    }

    public ReportAdapter(List<FilterBase> datas) {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, final int position, final Context context) {
        LinearLayout select_item = (LinearLayout)holder.getItemView(R.id.select_item);
        TextView title_view = (TextView) holder.getItemView(R.id.title_view);
        ImageView hook_red_iv = (ImageView) holder.getItemView(R.id.hook_red_iv);
        final FilterBase person = mDatas.get(position);
        title_view.setText(person.getTitle());
        select_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack!=null){
                    callBack.selectFoot(position,person.isType());
                }
            }
        });
        if (person.isType()){
            hook_red_iv.setImageResource(R.mipmap.hook_red);
        }else {
            hook_red_iv.setImageResource(0);
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.item_report;
    }
}