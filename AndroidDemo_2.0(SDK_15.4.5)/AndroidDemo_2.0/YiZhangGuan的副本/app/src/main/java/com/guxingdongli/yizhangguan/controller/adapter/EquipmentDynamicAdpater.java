package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.callback.EquipmentDynamicAdapterCallBack;
import com.guxingdongli.yizhangguan.model.EquipmentDynamicBase;
import com.guxingdongli.yizhangguan.model.MyRepairOrderDetailsContentBasicInfoBase;
import com.guxingdongli.yizhangguan.view.home.home_hospital.MyRepairOrderActivity;
import com.guxingdongli.yizhangguan.view.home.home_hospital.MyRepairOrderDetailsActivity;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackmask on 2018/3/6.
 */

public class EquipmentDynamicAdpater extends BaseCommAdapter<EquipmentDynamicBase.DataBeanX.DataBean> {

    private EquipmentDynamicAdapterCallBack callBack;
    private List<String> guids = new ArrayList<>();


    public void setCallBack(EquipmentDynamicAdapterCallBack callBack) {
        this.callBack = callBack;
    }

    public EquipmentDynamicAdpater(List<EquipmentDynamicBase.DataBeanX.DataBean> datas) {
        super(datas);
    }

    public List<String> getGuids() {
        return guids;
    }

    @Override
    protected void setUI(ViewHolder holder, final int position, final Context context) {
        final EquipmentDynamicBase.DataBeanX.DataBean data = getItem(position);
        LinearLayout item_layout = holder.getItemView(R.id.item_layout);
        final ImageView hospital_button = holder.getItemView(R.id.hospital_button);
        TextView numbering_tv = holder.getItemView(R.id.numbering_tv);
        TextView name_tv = holder.getItemView(R.id.name_tv);
        TextView specification_tv = holder.getItemView(R.id.specification_tv);
        TextView type_tv = holder.getItemView(R.id.type_tv);
        numbering_tv.setText(data.getMaterialNumber());
        name_tv.setText(data.getName());
        specification_tv.setText(data.getSpecification());
        type_tv.setText(data.getUseStatus());
        if (data.isType()){
            hospital_button.setImageResource(R.mipmap.chked);
        }else{
            hospital_button.setImageResource(R.mipmap.unchk);
        }
        hospital_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDatas.get(position).isType()){
                    mDatas.get(position).setType(false);
                    for (int i = 0 ; i < guids.size();i++){
                        if (guids.get(i).equals(mDatas.get(position).getGid())){
                            guids.remove(i);
                            break;
                        }
                    }
                }else{
                    mDatas.get(position).setType(true);
                    getGuids().add(mDatas.get(position).getGid());
                }
                notifyDataSetChanged();
            }
        });

        item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MyRepairOrderDetailsActivity.class);
                intent.putExtra("type","equipment");
                intent.putExtra("guid",data.getGid());
                context.startActivity(intent);
            }
        });

    }



    @Override
    protected int getLayoutId() {
        return R.layout.item_equipment_dyamic;
    }
}