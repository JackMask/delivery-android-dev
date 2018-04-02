package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.callback.MyOrderNewAdapterCallBack;
import com.guxingdongli.yizhangguan.model.MyStockOrderListBase;
import com.guxingdongli.yizhangguan.model.StorageHospitalBase;
import com.guxingdongli.yizhangguan.view.home.home_hospital.fragment.callback.StorageHospitalOnClickCallBack;
import com.guxingdongli.yizhangguan.view.home.home_merchants.QRExamineActivity;
import com.guxingdongli.yizhangguan.view.home.home_merchants.StockingInfoActivity;
import com.yuxiaolong.yuxiandelibrary.NoSlideListView;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/30
 */

public class MyStockOrderAdapter extends BaseCommAdapter<MyStockOrderListBase.DataBeanX.DataBean> {

    private StorageHospitalOnClickCallBack clickCallBack;
    private MyOrderNewAdapterCallBack callBack;


    //由于是我的备货单和我的订单里的listview引用的的adapter ,跳转的页面只有少许不同，
    //所以就引用了同一个adapter,就用这个做为标记来跳转哪，
    //true 是订货单，false 是备货单
    private boolean type = true;

    public MyStockOrderAdapter(List<MyStockOrderListBase.DataBeanX.DataBean> datas) {
        super(datas);
    }


    public MyOrderNewAdapterCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(MyOrderNewAdapterCallBack callBack) {
        this.callBack = callBack;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public void setClickCallBack(StorageHospitalOnClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    @Override
    protected void setUI(ViewHolder holder, final int position, final Context context) {
        final MyStockOrderListBase.DataBeanX.DataBean data = getItem(position);
        final TextView status_tv = holder.getItemView(R.id.status_tv);
        TextView all_cost_tv = holder.getItemView(R.id.all_cost_tv);
        TextView hospital_name = holder.getItemView(R.id.hospital_name);
        ImageView examine_btn = holder.getItemView(R.id.examine_btn);
        ImageView preparation_examine_btn = holder.getItemView(R.id.preparation_examine_btn);
        final NoSlideListView data_list = holder.getItemView(R.id.data_list);
        LinearLayout more_btn = holder.getItemView(R.id.more_btn);
        final LinearLayout expand_layout = holder.getItemView(R.id.expand_layout);
        ImageView more_iv = holder.getItemView(R.id.more_iv);
        TextView cancel_btn = holder.getItemView(R.id.cancel_btn);
        LinearLayout btn_layout = holder.getItemView(R.id.btn_layout);
        TextView enter_btn = holder.getItemView(R.id.enter_btn);
        LinearLayout item_layout = holder.getItemView(R.id.item_layout);
        LinearLayout other_two = holder.getItemView(R.id.other_two);
        LinearLayout other_one = holder.getItemView(R.id.other_one);
        LinearLayout preparation_layout = holder.getItemView(R.id.preparation_layout);
        TextView preparation_department_name = holder.getItemView(R.id.preparation_department_name);
        TextView preparation_order_name = holder.getItemView(R.id.preparation_order_name);
        TextView preparation_time = holder.getItemView(R.id.preparation_time);

        other_one.setVisibility(View.GONE);
        other_two.setVisibility(View.GONE);
        preparation_layout.setVisibility(View.VISIBLE);


        preparation_order_name.setText(data.getBusinessNumber());
        hospital_name.setText(data.getHospitalName());
        preparation_department_name.setText(data.getDepartmentName());
        status_tv.setText(data.getOrderStage());
        double allPicer = 0;
        for (MyStockOrderListBase.DataBeanX.DataBean.DetailsListBean item : data.getDetailsList()) {
            allPicer = allPicer + (item.getQuantity() * item.getPrice());
        }
        all_cost_tv.setText("￥ " + allPicer);
        preparation_time.setText(data.getOrderTime());
        if (data.getOrderStage().equals("待接单") || data.getOrderStage().equals("处理中")) {
            btn_layout.setVisibility(View.VISIBLE);
        } else {
            btn_layout.setVisibility(View.GONE);
        }
        item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StockingInfoActivity.class);
                intent.putExtra("orderID", data.getGid());
                intent.putExtra("con", false);
                context.startActivity(intent);
            }
        });
        preparation_examine_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, QRExamineActivity.class);
                intent.putExtra("content",data.getHospitalGuid()+"|"+data.getBusinessNumber());
                context.startActivity(intent);
            }
        });
        examine_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, QRExamineActivity.class);
                context.startActivity(intent);
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callBack != null) {
                    callBack.adapterCallBack(data.getGid(), position, type);
                }

            }
        });
        enter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //备货单详情
                Intent intent = new Intent(context, StockingInfoActivity.class);
                intent.putExtra("orderID", data.getGid());
                intent.putExtra("con", true);
                context.startActivity(intent);
            }
        });
        if (data.isType()) {
            expand_layout.setVisibility(View.VISIBLE);
            more_iv.setImageResource(R.mipmap.keepimg);
            List<StorageHospitalBase.DataBeanX.DataBean.DetailsListBean> list = new ArrayList<>();
            if (data.getDetailsList()!=null&&data.getDetailsList().size()>0)
            for (MyStockOrderListBase.DataBeanX.DataBean.DetailsListBean item:data.getDetailsList()){
                StorageHospitalBase.DataBeanX.DataBean.DetailsListBean listItem = new StorageHospitalBase.DataBeanX.DataBean.DetailsListBean();
                listItem.setAcceptanceQuantity((int) Math.ceil(item.getAcceptanceQuantity()));
                listItem.setAcceptanceResult(item.getAcceptanceResult());
                listItem.setBatchNumber(item.getBatchNumber());
                listItem.setCityId(item.getCityId());
                listItem.setCountyId(item.getCountyId());
                listItem.setName(item.getName());
                listItem.setSpecification(item.getSpecification());
                listItem.setQuantity((int) Math.ceil(item.getQuantity()));
                listItem.setUnit(item.getUnit());
                listItem.setPrice(item.getPrice());
                listItem.setValid(item.getValid());
            }

            StorageHospitalChildNewAdapter adapter = new StorageHospitalChildNewAdapter(list);
            adapter.setClick(false);
            data_list.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            expand_layout.setVisibility(View.GONE);
            more_iv.setImageResource(R.mipmap.spreadoutimg);
        }
        more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean type = false;
                if (data.isType()) {
                    getItem(position).setType(false);
                    type = false;
                    expand_layout.setVisibility(View.GONE);
                } else {
                    getItem(position).setType(true);
                    type = true;
                    expand_layout.setVisibility(View.VISIBLE);

                }
                if (clickCallBack != null) {
                    clickCallBack.clickNo(position, type);
                }
            }
        });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_my_order;
    }
}