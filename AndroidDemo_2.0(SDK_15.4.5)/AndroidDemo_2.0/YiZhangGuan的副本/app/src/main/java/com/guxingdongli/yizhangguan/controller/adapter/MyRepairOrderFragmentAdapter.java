package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.YiZhangGuanApplication;
import com.guxingdongli.yizhangguan.model.MyRepairOrderBase;
import com.guxingdongli.yizhangguan.model.TestHospitalBean;
import com.guxingdongli.yizhangguan.view.home.home_hospital.HospitalFillInRepairOrderActivity;
import com.guxingdongli.yizhangguan.view.home.home_hospital.StoraeHospitalDetailsActivity;
import com.guxingdongli.yizhangguan.view.home.home_hospital.fragment.callback.StorageHospitalOnClickCallBack;
import com.guxingdongli.yizhangguan.view.home.home_merchants.FillInRepairOrderActivity;
import com.yuxiaolong.yuxiandelibrary.NoSlideListView;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;
import com.yuxiaolong.yuxiandelibrary.view.ProhibitSlideGridView;

import java.util.List;

/**
 * Created by jackmask on 2018/3/2.
 */

public class MyRepairOrderFragmentAdapter extends BaseCommAdapter<MyRepairOrderBase.DataBeanX.DataBean> {

    private StorageHospitalOnClickCallBack clickCallBack;

    public MyRepairOrderFragmentAdapter(List<MyRepairOrderBase.DataBeanX.DataBean> datas) {
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
        TextView order_num = holder.getItemView(R.id.order_num);
        final TextView status_tv = holder.getItemView(R.id.status_tv);
        TextView time_tv = holder.getItemView(R.id.time_tv);
        LinearLayout more_btn = holder.getItemView(R.id.more_btn);
        TextView equipment_name_tv = holder.getItemView(R.id.equipment_name_tv);
        ImageView more_iv = holder.getItemView(R.id.more_iv);
        TextView department_tv = holder.getItemView(R.id.department_tv);
        TextView malfunction_content = holder.getItemView(R.id.malfunction_content);
        ProhibitSlideGridView pic_grid = holder.getItemView(R.id.pic_grid);
        final LinearLayout malfunction_layout = holder.getItemView(R.id.malfunction_layout);
        TextView registration_btn = holder.getItemView(R.id.registration_btn);

        LinearLayout merchants_layout = holder.getItemView(R.id.merchants_layout);
        if (YiZhangGuanApplication.getInstance().isEngineer()){
            merchants_layout.setVisibility(View.VISIBLE);
            TextView timing_btn = holder.getItemView(R.id.timing_btn);



            if (data.isCheckIn()) {
                registration_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //供应商端的维修单
                            if (data.isCheckIn()) {
                                Intent intent = new Intent(context, HospitalFillInRepairOrderActivity.class);
                                intent.putExtra("guid", data.getGid());
                                context.startActivity(intent);
                            }
                       // }

                    }
                });

            }else{
                registration_btn.setTextColor(Color.parseColor("#bbbbbb"));
            }
            if (data.isScanTiming()) {
                timing_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (clickCallBack!=null){
                           clickCallBack.clickTime(data.getHospitalGuid(),data.getBusinessNumber());
                        }
                    }
                });
            }else{
                timing_btn.setTextColor(Color.parseColor("#bbbbbb"));
            }
        }


        order_num.setText(data.getBusinessNumber());
        status_tv.setText(data.getMaintenanceStage());
        equipment_name_tv.setText(data.getName());
        malfunction_content.setText(data.getFaultDescription());
        department_tv.setText(data.getDepartmentName());
        if (data.isType()){
            malfunction_layout.setVisibility(View.VISIBLE);
            more_iv.setImageResource(R.mipmap.keepimg);
            if (data.getImgList()!=null&&data.getImgList().size()>0){
                pic_grid.setNumColumns(3);
                pic_grid.setVisibility(View.VISIBLE);
            ShopCommentPicAdapter adapter = new ShopCommentPicAdapter(context,data.getImgList());
            pic_grid.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            }else{
                System.out.println("2222");
                pic_grid.setVisibility(View.GONE);
            }

        }else{
            malfunction_layout.setVisibility(View.GONE);
            more_iv.setImageResource(R.mipmap.spreadoutimg);
        }
        more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean type = false;
                if (data.isType()){
                    getItem(position).setType(false);
                    type = false;
                    malfunction_layout.setVisibility(View.GONE);
                }else{
                    getItem(position).setType(true);
                    type = true;
                    malfunction_layout.setVisibility(View.VISIBLE);

                }
                if (clickCallBack!=null){
                    clickCallBack.clickNo(position,type);
                }
            }
        });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_my_repair_order;
    }


}