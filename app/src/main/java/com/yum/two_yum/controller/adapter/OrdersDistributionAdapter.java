package com.yum.two_yum.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.yum.two_yum.R;
import com.yum.two_yum.controller.adapter.callback.ClientNearbyCallBack;
import com.yum.two_yum.controller.adapter.universalAdapter.BaseCommAdapter;
import com.yum.two_yum.controller.adapter.universalAdapter.ViewHolder;
import com.yum.two_yum.view.merchants.merchantsOrders.DagDetailsActivity;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/4/12
 */

public class OrdersDistributionAdapter extends BaseCommAdapter<String> {

    private Click click;

    public Click getClick() {
        return click;
    }

    public void setClick(Click click) {
        this.click = click;
    }

    public interface Click{
        void itemClick();
    }


    public OrdersDistributionAdapter(List<String> datas) {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, final int position, final Context context) {
        RelativeLayout item_layout = holder.getItemView(R.id.item_layout);
        item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click!=null){
                    click.itemClick();
                }
                Intent intent = new Intent(context, DagDetailsActivity.class);
                context.startActivity(intent);
            }
        });

    }


    @Override
    protected int getLayoutId() {
        return R.layout.item_orders_distribution;
    }
}