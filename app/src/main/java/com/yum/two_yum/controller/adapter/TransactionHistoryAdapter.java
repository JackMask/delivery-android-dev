package com.yum.two_yum.controller.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.yum.two_yum.R;
import com.yum.two_yum.base.TransactionHistoryBase;
import com.yum.two_yum.controller.adapter.callback.ClientNearbyCallBack;
import com.yum.two_yum.controller.adapter.universalAdapter.BaseCommAdapter;
import com.yum.two_yum.controller.adapter.universalAdapter.ViewHolder;
import com.yum.two_yum.utile.AppUtile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 余先德
 * @data 2018/4/14
 */

public class TransactionHistoryAdapter extends BaseCommAdapter<TransactionHistoryBase.DataBean.TransactionRecordRespResultListBean> {


    public TransactionHistoryAdapter(List<TransactionHistoryBase.DataBean.TransactionRecordRespResultListBean> datas) {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, final int position, final Context context) {
        TextView date_tv = holder.getItemView(R.id.date_tv);
        TextView pice_tv = holder.getItemView(R.id.pice_tv);
        TextView account_tv = holder.getItemView(R.id.account_tv);
        TextView service_tv = holder.getItemView(R.id.service_tv);
        TextView adjustment_tv = holder.getItemView(R.id.adjustment_tv);
        TextView return_tv = holder.getItemView(R.id.return_tv);
        RelativeLayout refund_layout = holder.getItemView(R.id.refund_layout);
        RelativeLayout adjustment_layout = holder.getItemView(R.id.adjustment_layout);
        TransactionHistoryBase.DataBean.TransactionRecordRespResultListBean data = mDatas.get(position);
        date_tv.setText(getData(data.getRecordTime()));
        pice_tv.setText("$" + AppUtile.getPrice(data.getTotalAmount()+""));
        account_tv.setText(data.getAccount().length()>3?"***"+(data.getAccount().substring(data.getAccount().length()-4,data.getAccount().length())):"***");
        service_tv.setText("-"+AppUtile.getPrice(data.getServiceAmount()+""));
        return_tv.setText("-"+AppUtile.getPrice(data.getRefundAmount()+""));
        adjustment_tv.setText("-"+AppUtile.getPrice(data.getChangeAmount()+""));
        if (data.getChangeAmount() == 0){
            adjustment_layout.setVisibility(View.GONE);
        }else{
            adjustment_layout.setVisibility(View.VISIBLE);
        }
        if (data.getRefundAmount() == 0){
            refund_layout.setVisibility(View.GONE);
        }else{
            refund_layout.setVisibility(View.VISIBLE);
        }
    }

    private String getData(String data){
        //注意：SimpleDateFormat构造函数的样式与strDate的样式必须相符
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sDateFormat=new SimpleDateFormat("MM/dd/yyyy"); //加上时间
        //必须捕获异常
        try {
            Date date=simpleDateFormat.parse(data);
            return sDateFormat.format(date);

        } catch(ParseException px) {
            px.printStackTrace();
            return data;
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.item_transaction_history;
    }
}