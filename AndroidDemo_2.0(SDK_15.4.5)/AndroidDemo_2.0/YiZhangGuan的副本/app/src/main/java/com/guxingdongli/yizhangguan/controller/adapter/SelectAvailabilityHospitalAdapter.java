package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.model.MyRepairOrderDetailsTabBase;
import com.guxingdongli.yizhangguan.view.home.home_merchants.SelectAvailabilityHospitalActivity;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.List;

/**
 * Created by jackmask on 2018/3/9.
 */

public class SelectAvailabilityHospitalAdapter  extends BaseCommAdapter<String> {

    public SelectAvailabilityHospitalAdapter(List<String> datas) {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, int position, final Context context) {
        final String data = getItem(position);
        LinearLayout select_layout = holder.getItemView(R.id.select_layout);
       final CheckBox select_checkbox = holder.getItemView(R.id.select_checkbox);
        TextView hospital_name = holder.getItemView(R.id.hospital_name);
        select_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (select_checkbox.isChecked()){
                    select_checkbox.setChecked(false);
                }else{
                    select_checkbox.setChecked(true);
                }
            }
        });
        select_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    ((SelectAvailabilityHospitalActivity)context).selectHospital(data);
                }else{
                    ((SelectAvailabilityHospitalActivity)context).delHospital(data);
                }
            }
        });
        hospital_name.setText("data");


    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_select_hospital;
    }
}