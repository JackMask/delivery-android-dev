package com.yum.two_yum.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.yum.two_yum.R;
import com.yum.two_yum.base.OrderHistoryBase;
import com.yum.two_yum.controller.adapter.callback.ClientNearbyCallBack;
import com.yum.two_yum.controller.adapter.universalAdapter.BaseCommAdapter;
import com.yum.two_yum.controller.adapter.universalAdapter.ViewHolder;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.view.merchants.statistics.OrderHistoryDetailsActivity;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/4/14
 */

public class OrderHistoryAdapter extends BaseCommAdapter<OrderHistoryBase.DataBean.MerchantStatisticsRespResultsBean> {

    private ClientNearbyCallBack clientNearbyCallBack;

    public void setClientNearbyCallBack(ClientNearbyCallBack clientNearbyCallBack) {
        this.clientNearbyCallBack = clientNearbyCallBack;
    }

    public OrderHistoryAdapter(List<OrderHistoryBase.DataBean.MerchantStatisticsRespResultsBean> datas) {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, final int position, final Context context) {
        TextView cancelBtn = holder.getItemView(R.id.cancel_btn);
        TextView deliverBtn = holder.getItemView(R.id.deliver_btn);
        TextView all_pice_tv = holder.getItemView(R.id.all_pice_tv);
        TextView date_tv = holder.getItemView(R.id.date_tv);
        TextView order_num_tv = holder.getItemView(R.id.order_num_tv);
        OrderHistoryBase.DataBean.MerchantStatisticsRespResultsBean data = mDatas.get(position);
        date_tv.setText(data.getDate().substring(2,data.getDate().length()));
        all_pice_tv.setText(AppUtile.getPrice(data.getTotalMoney()+""));
        order_num_tv.setText(data.getOrderCount()+"");
        cancelBtn.setText(data.getCancelCount()+"");
        deliverBtn.setText(data.getCompleteCount()+"");
        cancelBtn.setOnClickListener(new CLick(context,data.getDate()));
        deliverBtn.setOnClickListener(new CLick(context,data.getDate()));

    }

    private class CLick implements View.OnClickListener{

        Context context;
        String data;

        public CLick(Context context,String data){
            this.context = context;
            this.data = data;
        }

        @Override
        public void onClick(View v) {
           Intent intent = new Intent(context, OrderHistoryDetailsActivity.class);
            intent.putExtra("data",data);
            switch (v.getId()){
                case R.id.cancel_btn:
                    intent.putExtra("title",context.getResources().getString(R.string.THENCANCELED));
                    context.startActivity(intent);
                    break;
                case R.id.deliver_btn:
                    intent.putExtra("title",context.getResources().getString(R.string.THENDELIVERED));
                    context.startActivity(intent);
                    break;
            }

        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.item_orders_history;
    }
}