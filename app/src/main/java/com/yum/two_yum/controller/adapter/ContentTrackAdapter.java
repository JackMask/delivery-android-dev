package com.yum.two_yum.controller.adapter;

import android.content.Context;
import android.view.View;
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

public class ContentTrackAdapter extends BaseCommAdapter<String> {


    public ContentTrackAdapter(List<String> datas) {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, final int position, final Context context) {

    }


    @Override
    protected int getLayoutId() {
        return R.layout.item_orders_client;
    }
}