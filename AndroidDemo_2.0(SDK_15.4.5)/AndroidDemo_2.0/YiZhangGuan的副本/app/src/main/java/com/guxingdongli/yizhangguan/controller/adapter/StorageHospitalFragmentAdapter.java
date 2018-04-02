package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.model.StorageHospitalBase;
import com.guxingdongli.yizhangguan.model.TestHospitalBean;
import com.guxingdongli.yizhangguan.view.home.home_hospital.StoraeHospitalDetailsActivity;
import com.guxingdongli.yizhangguan.view.home.home_hospital.fragment.callback.StorageHospitalOnClickCallBack;
import com.yuxiaolong.yuxiandelibrary.NoSlideListView;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.List;

/**
 * Created by jackmask on 2018/3/2.
 */

public class StorageHospitalFragmentAdapter extends BaseCommAdapter<StorageHospitalBase.DataBeanX.DataBean> {

    private StorageHospitalOnClickCallBack clickCallBack;

    public StorageHospitalFragmentAdapter(List<StorageHospitalBase.DataBeanX.DataBean> datas) {
        super(datas);
    }

    public StorageHospitalOnClickCallBack getClickCallBack() {
        return clickCallBack;
    }

    public void setClickCallBack(StorageHospitalOnClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    @Override
    protected void setUI(ViewHolder holder, final int position, final Context context) {
        final StorageHospitalBase.DataBeanX.DataBean data = getItem(position);
        TextView order_num = holder.getItemView(R.id.order_num);
        final TextView status_tv = holder.getItemView(R.id.status_tv);
        TextView time_tv = holder.getItemView(R.id.time_tv);
        TextView examine_btn = holder.getItemView(R.id.examine_btn);
        final NoSlideListView data_list = holder.getItemView(R.id.data_list);
        LinearLayout more_btn = holder.getItemView(R.id.more_btn);
        ImageView more_iv = holder.getItemView(R.id.more_iv);
        TextView supplier_tv = holder.getItemView(R.id.supplier_tv);
        LinearLayout item_layout = holder.getItemView(R.id.item_layout);
        item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StoraeHospitalDetailsActivity.class);
                intent.putExtra("type",true);
                intent.putExtra("guid",data.getGid());
                context.startActivity(intent);
            }
        });
        examine_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StoraeHospitalDetailsActivity.class);
                intent.putExtra("guid",data.getGid());
                context.startActivity(intent);
            }
        });
        order_num.setText(data.getBusinessNumber());
        time_tv.setText(data.getOrderTime());
        supplier_tv.setText(data.getSupplierName());
        status_tv.setText(data.getOrderStage());
        status_tv.setTextColor(Color.parseColor("#"+data.getOrderStageColor()));
        if (data.getOrderStage().equals("已处理")||data.getOrderStage().equals("部分验收")){
            examine_btn.setVisibility(View.VISIBLE);
        }else{
            examine_btn.setVisibility(View.GONE);
        }

        if (data.isType()){
            data_list.setVisibility(View.VISIBLE);
            more_iv.setImageResource(R.mipmap.keepimg);
            StorageHospitalChildAdapter adapter = new StorageHospitalChildAdapter(data.getDetailsList());
            data_list.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else{
            data_list.setVisibility(View.GONE);
            more_iv.setImageResource(R.mipmap.spreadoutimg);
        }
        more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean type = false;
                if (data.isType()){
                    getItem(position).setType(false);
                    type = false;
                    data_list.setVisibility(View.GONE);
                }else{
                    getItem(position).setType(true);
                    type = true;
                    data_list.setVisibility(View.VISIBLE);

                }
                if (clickCallBack!=null){
                    clickCallBack.clickNo(position,type);
                }
            }
        });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_storage_hospital;
    }


}