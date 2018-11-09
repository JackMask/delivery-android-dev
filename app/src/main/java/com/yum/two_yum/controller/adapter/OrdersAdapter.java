package com.yum.two_yum.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.squareup.picasso.Picasso;
import com.yum.two_yum.R;
import com.yum.two_yum.base.ClientOrdersBase;
import com.yum.two_yum.controller.adapter.callback.ClientNearbyCallBack;
import com.yum.two_yum.controller.adapter.universalAdapter.BaseCommAdapter;
import com.yum.two_yum.controller.adapter.universalAdapter.ViewHolder;
import com.yum.two_yum.utile.CircleImageView;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/4/9
 */

public class OrdersAdapter extends BaseCommAdapter<ClientOrdersBase.DataBean.OrderRespResultsBean> {

    private OrdersCallBack clientNearbyCallBack;
    public interface OrdersCallBack{
        public abstract void itemClike(int position,String id);
        void DelClick(String id);
    }

    public void setClientNearbyCallBack(OrdersCallBack clientNearbyCallBack) {
        this.clientNearbyCallBack = clientNearbyCallBack;
    }

    public OrdersAdapter(List<ClientOrdersBase.DataBean.OrderRespResultsBean> datas) {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, final int position, final Context context) {
        final ClientOrdersBase.DataBean.OrderRespResultsBean data = mDatas.get(position);
        LinearLayout item_layout = holder.getItemView(R.id.content_view);
        final SwipeMenuLayout chat_item = holder.getItemView(R.id.chat_item);
        TextView del_btn = holder.getItemView(R.id.del_btn);
        TextView name_tv = holder.getItemView(R.id.name_tv);
        TextView time_tv = holder.getItemView(R.id.time_tv);
        CircleImageView store_img = holder.getItemView(R.id.store_img);
        TextView status_tv = holder.getItemView(R.id.status_tv);
        item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clientNearbyCallBack!=null){
                    clientNearbyCallBack.itemClike(position,data.getOrderId());
                }
            }
        });
        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chat_item.quickClose();
                if (clientNearbyCallBack!=null){
                    clientNearbyCallBack.DelClick(data.getOrderId());
                }
            }
        });
        if (!TextUtils.isEmpty(data.getMerchantAvatar()))
            Picasso.get().load(data.getMerchantAvatar()).placeholder(R.mipmap.timg).into(store_img);
        name_tv.setText(data.getMerchantName());
        time_tv.setText(data.getDoneTime().length()>10?data.getDoneTime().substring(0,10):data.getDoneTime());

        int type = data.getStatus();
        if (type == 1){
            status_tv.setText(context.getResources().getString(R.string.PAYMENTSUCCESSFUL));
            status_tv.setTextColor(context.getResources().getColor(R.color.color_ff3b30));
        }else if (type == 2){
            status_tv.setText(context.getResources().getString(R.string.PREPARING));
            status_tv.setTextColor(context.getResources().getColor(R.color.color_ff3b30));
        }else if (type == 3){
            status_tv.setText(context.getResources().getString(R.string.DELIVERYNOW));
            status_tv.setTextColor(context.getResources().getColor(R.color.color_ff3b30));
        }else if(type == 4){
            status_tv.setText(context.getResources().getString(R.string.THENDELIVERED));
            status_tv.setTextColor(context.getResources().getColor(R.color.color_FF484848));
        }else if(type == 0){
            status_tv.setText(context.getResources().getString(R.string.THENCANCELED));
            status_tv.setTextColor(context.getResources().getColor(R.color.color_FF484848));
        }
//        //del_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                chat_item.quickClose();
//            }
//        });

    }


    @Override
    protected int getLayoutId() {
        return R.layout.item_orders_client;
    }
}