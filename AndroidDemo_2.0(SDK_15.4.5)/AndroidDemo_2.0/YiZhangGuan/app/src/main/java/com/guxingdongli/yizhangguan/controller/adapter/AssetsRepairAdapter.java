package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.callback.AssetsRepairCallBack;
import com.guxingdongli.yizhangguan.model.AssetsRepairBase;
import com.guxingdongli.yizhangguan.model.MyRepairOrderDetailsTabBase;
import com.guxingdongli.yizhangguan.model.TestHospitalBean;
import com.guxingdongli.yizhangguan.view.home.home_hospital.MyRepairOrderDetailsActivity;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackmask on 2018/3/5.
 */

public class AssetsRepairAdapter extends BaseCommAdapter<AssetsRepairBase.DataBeanX.DataBean> {

    private AssetsRepairCallBack callBack;

    private AssetsRepairBase.DataBeanX.DataBean equipment ;
    public AssetsRepairCallBack getCallBack() {
        return callBack;
    }

    public AssetsRepairBase.DataBeanX.DataBean getEquipment() {
        return equipment;
    }

    public void setCallBack(AssetsRepairCallBack callBack) {
        this.callBack = callBack;
    }

    public AssetsRepairAdapter(List<AssetsRepairBase.DataBeanX.DataBean> datas) {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, final int position, final Context context) {
       final AssetsRepairBase.DataBeanX.DataBean data = getItem(position);
        //content_tv.setText(data.getContent());
        final ImageView select_check = holder.getItemView(R.id.select_check_se);
        LinearLayout item_layout = holder.getItemView(R.id.item_layout_se);
        LinearLayout item_layout_old = holder.getItemView(R.id.item_layout);
        TextView name_tv = holder.getItemView(R.id.name_tv_se);
        TextView department_tv = holder.getItemView(R.id.department_tv_se);
        TextView department_num_se= holder.getItemView(R.id.department_num_se);
        item_layout_old.setVisibility(View.GONE);
        item_layout.setVisibility(View.VISIBLE);
        name_tv.setText(data.getName());
        department_num_se.setText(data.getMaterialNumber());
        department_tv.setText(data.getUseDepartment());


        item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MyRepairOrderDetailsActivity.class);
                intent.putExtra("type","equipment");
                intent.putExtra("guid",data.getGid());
                context.startActivity(intent);

            }
        });
        select_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (equipment!=null&&equipment.getGid().equals(data.getGid())){
                    mDatas.get(position).setSelectTab(false);
                    equipment = null;
                }else {
                    for (int i = 0 ; i <mDatas.size();i++){
                        mDatas.get(i).setSelectTab(false);
                    }
                    mDatas.get(position).setSelectTab(true);
                    equipment = data;
                }
                notifyDataSetChanged();
            }

        });
        if (data.isSelectTab()) {
            select_check.setImageResource(R.mipmap.chked);
        } else {
            select_check.setImageResource(R.mipmap.unchk);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_assets_repair;
    }
}