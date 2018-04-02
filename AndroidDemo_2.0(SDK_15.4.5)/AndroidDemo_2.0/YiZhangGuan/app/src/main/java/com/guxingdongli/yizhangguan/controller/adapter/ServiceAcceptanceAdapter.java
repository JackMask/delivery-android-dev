package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.callback.ServiceAcceptanceCallBack;
import com.guxingdongli.yizhangguan.model.MyRepairOrderBase;
import com.guxingdongli.yizhangguan.model.TestHospitalBean;
import com.guxingdongli.yizhangguan.view.home.home_hospital.AcceptanceActivity;
import com.guxingdongli.yizhangguan.view.home.home_hospital.MyRepairOrderDetailsActivity;
import com.guxingdongli.yizhangguan.view.home.home_hospital.StoraeHospitalDetailsActivity;
import com.guxingdongli.yizhangguan.view.home.home_hospital.fragment.callback.StorageHospitalOnClickCallBack;
import com.yuxiaolong.yuxiandelibrary.NoSlideListView;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.List;

/**
 * Created by jackmask on 2018/3/5.
 */

public class ServiceAcceptanceAdapter extends BaseCommAdapter<MyRepairOrderBase.DataBeanX.DataBean> {

    private StorageHospitalOnClickCallBack clickCallBack;
    private ServiceAcceptanceCallBack callBack;

    public ServiceAcceptanceCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(ServiceAcceptanceCallBack callBack) {
        this.callBack = callBack;
    }

    public ServiceAcceptanceAdapter(List<MyRepairOrderBase.DataBeanX.DataBean> datas) {
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
        final MyRepairOrderBase.DataBeanX.DataBean data = getItem(position);
        final LinearLayout data_list = holder.getItemView(R.id.detailed_layout);
        LinearLayout more_btn = holder.getItemView(R.id.more_btn);
        ImageView more_iv = holder.getItemView(R.id.more_iv);
        TextView order_num = holder.getItemView(R.id.order_num);
        LinearLayout item_layout = holder.getItemView(R.id.item_layout);
        TextView acceptance_btn = holder.getItemView(R.id.acceptance_btn);
        TextView unit_name = holder.getItemView(R.id.unit_name);
        TextView department_name = holder.getItemView(R.id.department_name);
        TextView time_tv = holder.getItemView(R.id.time_tv);
        TextView status_tv = holder.getItemView(R.id.status_tv);

        order_num.setText(data.getBusinessNumber());
        unit_name.setText(data.getName());
        department_name.setText(data.getDepartmentName());
        time_tv.setText(data.getRepairTime());
        status_tv.setText(data.getMaintenanceStage());
        if (data.getMaintenanceStage().equals("已维修待验收")){
            acceptance_btn.setVisibility(View.VISIBLE);
        }else{
            acceptance_btn.setVisibility(View.GONE);
        }

        acceptance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //验收
                if (callBack!=null){
                    callBack.startActivity(data.getGid());
                }

            }
        });
        item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //详情
                Intent intent = new Intent(context, MyRepairOrderDetailsActivity.class);
                intent.putExtra("guid",data.getGid());
                context.startActivity(intent);

            }
        });

        /*if (data.isType()) {
            data_list.setVisibility(View.VISIBLE);
            more_iv.setImageResource(R.mipmap.keepimg);
        } else {
            data_list.setVisibility(View.GONE);
            more_iv.setImageResource(R.mipmap.spreadoutimg);
        }*/
        more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* boolean type = false;
                if (data.isType()) {
                    getItem(position).setType(false);
                    type = false;
                    data_list.setVisibility(View.GONE);
                } else {
                    getItem(position).setType(true);
                    type = true;
                    data_list.setVisibility(View.VISIBLE);

                }
                if (clickCallBack != null) {
                    clickCallBack.clickNo(position, type);
                }*/
            }
        });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_service_acceptance;
    }
}

