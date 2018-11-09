package com.yum.two_yum.controller.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.squareup.picasso.Picasso;
import com.yum.two_yum.R;
import com.yum.two_yum.base.OrdersNewBase;
import com.yum.two_yum.controller.adapter.callback.ClientNearbyCallBack;
import com.yum.two_yum.controller.adapter.callback.OrdersMerchantsCallBack;
import com.yum.two_yum.controller.adapter.universalAdapter.BaseCommAdapter;
import com.yum.two_yum.controller.adapter.universalAdapter.ViewHolder;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.CircleImageView;
import com.yum.two_yum.utile.view.NoSlideListView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 余先德
 * @data 2018/4/12
 */

public class OrdersDliverAdapter extends BaseCommAdapter<OrdersNewBase.DataBean.MerchantTodayOrderRespResultsBean> {

    private OrdersMerchantsCallBack callBack;
    private boolean conType = false;

    private String cancel = "COMPLETE";

    public String isCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public boolean isConType() {
        return conType;
    }

    public void setConType(boolean conType) {
        this.conType = conType;
    }

    public void setCallBack(OrdersMerchantsCallBack callBack) {
        this.callBack = callBack;
    }

    public OrdersDliverAdapter(List<OrdersNewBase.DataBean.MerchantTodayOrderRespResultsBean> datas) {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, final int position, final Context context) {
        RelativeLayout btn_layout = holder.getItemView(R.id.btn_layout);
        LinearLayout phone_btn = holder.getItemView(R.id.phone_btn);
        final TextView phone_num = holder.getItemView(R.id.phone_num);
        TextView address_btn = holder.getItemView(R.id.address_btn);
        TextView cancel_btn = holder.getItemView(R.id.cancel_btn);
        CircleImageView head_img = holder.getItemView(R.id.head_img);
        TextView sex_tv = holder.getItemView(R.id.sex_tv);
        TextView numbering_tv = holder.getItemView(R.id.numbering_tv);
        TextView time_tv = holder.getItemView(R.id.time_tv);
        TextView distance_tv = holder.getItemView(R.id.distance_tv);
        TextView order_num_tv = holder.getItemView(R.id.order_num_tv);
        TextView order_time_tv = holder.getItemView(R.id.order_time_tv);
        TextView name_tv = holder.getItemView(R.id.name_tv);
        TextView note_tv = holder.getItemView(R.id.note_tv);
        TextView delivery_tv = holder.getItemView(R.id.delivery_tv);
        TextView tax_tv = holder.getItemView(R.id.tax_tv);
        TextView all_pice_tv = holder.getItemView(R.id.all_pice_tv);
        TextView ok_btn = holder.getItemView(R.id.ok_btn);
        TextView ps_tv = holder.getItemView(R.id.ps_tv);
        //TextView cancel_btn = holder.getItemView(R.id.cancel_btn);

        final OrdersNewBase.DataBean.MerchantTodayOrderRespResultsBean data = mDatas.get(position);
        if (cancel.equals("CANCEL")){
            ps_tv.setVisibility(View.VISIBLE);
        }else{
            ps_tv.setVisibility(View.GONE);
        }
        Picasso.get().load(data.getAvatar()).placeholder(R.mipmap.head_img_being).into(head_img);
        if (data.getGender()==1){
            sex_tv.setText(context.getString(R.string.MR));
        }else{
            sex_tv.setText(context.getString(R.string.MS));
        }
        numbering_tv.setText("No."+data.getShortNumber());
        time_tv.setText(data.getDeliveryTime());
        distance_tv.setText(data.getDistance()+context.getString(R.string.MILE));
        order_num_tv.setText(context.getString(R.string.ORDERNO)+": "+data.getOrderNumber());
        order_time_tv.setText(AppUtile.stampToDate(AppUtile.testString2Date(data.getDoneTime(),"yyyy-MM-dd HH:mm:ss"),"MM/dd/yy HH:mm"));
       // order_time_tv.setText(data.getDoneTime().length()>16?data.getDoneTime().substring(0,16):data.getDoneTime());
        name_tv.setText(data.getName());
        phone_num.setText(data.getPhone());
        address_btn.setText(data.getAddress().replace("\n",", "));
        note_tv.setText(context.getString(R.string.PS)+" "+data.getNote());
        delivery_tv.setText("$"+ AppUtile.getPrice(data.getDeliveryMoney()+""));
        double allPrice = 0;
        if (data.getOrderDetailRespResults()!=null&&data.getOrderDetailRespResults().size()>0){
            for (int i = 0 ; i < data.getOrderDetailRespResults().size();i++){
                allPrice = AppUtile.sum(allPrice,AppUtile.mul(data.getOrderDetailRespResults().get(i).getPrice(),data.getOrderDetailRespResults().get(i).getNumber()));
            }
        }
        allPrice = AppUtile.sum(allPrice,data.getDeliveryMoney());
        double tax= AppUtile.div(data.getTaxRate(),100.0,5);
        double all = AppUtile.mul(allPrice,tax);
        double taxDouble1 = AppUtile.formatDouble1(all);
        tax_tv.setText("$"+AppUtile.getPrice(taxDouble1 +""));
        all_pice_tv.setText("$"+AppUtile.getPrice(AppUtile.sum(taxDouble1,allPrice)+""));
//        tax_tv.setText("$"+AppUtile.getPrice(AppUtile.formatDouble1(AppUtile.mul(allPrice,AppUtile.div(data.getTaxRate(),100.0,9)))+""));
//        all_pice_tv.setText("$"+AppUtile.getPrice(AppUtile.sum(AppUtile.formatDouble1(AppUtile.mul(allPrice,data.getTaxRate())),allPrice)+""));
        ok_btn.setText(context.getString(R.string.DELIVER));
        ps_tv.setText("*"+data.getCancelNote());
        OrdersNewChlieAdapter adapter = new OrdersNewChlieAdapter(data.getOrderDetailRespResults());
        adapter.setConType(true);
        NoSlideListView content_lv = holder.getItemView(R.id.content_lv);
        content_lv.setAdapter(adapter);
        phone_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(phone_num.getText().toString())) {
                    if (callBack!=null){
                        callBack.clickCall(position,phone_num.getText().toString());
                    }

                }
            }
        });
        address_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack!=null){
                    callBack.clickaddres(position,data.getLat()+"",data.getLng()+"",data.getAddress());
                }
            }
        });
        if (!conType) {
            if (data.getStatus()==3) {
                btn_layout.setVisibility(View.VISIBLE);
            }else{
                btn_layout.setVisibility(View.GONE);
            }
            ok_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callBack != null) {
                        callBack.clickOk(position, data.getId());
                    }
                }
            });
            cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callBack != null) {
                        callBack.clickCancel(position, data.getId());
                    }
                }
            });

        }else{
            btn_layout.setVisibility(View.GONE);
        }
        //btn_layout.setVisibility(View.GONE);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.item_orders_new;
    }
}