package com.yum.two_yum.controller.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.yum.two_yum.R;
import com.yum.two_yum.base.input.CreateOrdersInput;
import com.yum.two_yum.controller.adapter.callback.ClientNearbyCallBack;
import com.yum.two_yum.controller.adapter.universalAdapter.BaseCommAdapter;
import com.yum.two_yum.controller.adapter.universalAdapter.ViewHolder;
import com.yum.two_yum.utile.AppUtile;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/4/10
 */

public class SubmitOrdersAdapter extends BaseCommAdapter<CreateOrdersInput.MenuReqListBean> {



    public SubmitOrdersAdapter(List<CreateOrdersInput.MenuReqListBean> datas) {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, final int position, final Context context) {
        TextView name = holder.getItemView(R.id.name);
        TextView num_tv = holder.getItemView(R.id.num_tv);
        TextView price = holder.getItemView(R.id.price);
        CreateOrdersInput.MenuReqListBean data = mDatas.get(position);
        name.setText(data.getMerchantMenuName());
        num_tv.setText("x"+data.getNumber());
        price.setText("$"+ AppUtile.getPrice(AppUtile.mul(data.getPrice(),data.getNumber())+""));
    }


    @Override
    protected int getLayoutId() {
        return R.layout.item_submit_orders;
    }
}