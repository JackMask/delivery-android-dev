package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.callback.PushSelectHospitalCallBack;
import com.guxingdongli.yizhangguan.model.AcceptanceListComponentsBase;
import com.guxingdongli.yizhangguan.model.PushSelectHospitalBase;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/24
 */

public class PushSelectHospitalAdapter extends BaseCommAdapter<PushSelectHospitalBase.DataBean> {

    private PushSelectHospitalCallBack callBack;

    public void setCallBack(PushSelectHospitalCallBack callBack) {
        this.callBack = callBack;
    }

    public PushSelectHospitalAdapter(List<PushSelectHospitalBase.DataBean> datas) {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, int position, final Context context) {
        final PushSelectHospitalBase.DataBean data = getItem(position);
        TextView hospital_name = holder.getItemView(R.id.hospital_name);
        CheckBox hospital_button = holder.getItemView(R.id.hospital_button);
        hospital_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (callBack!=null){
                    callBack.getData(data,b);
                }
            }
        });
        hospital_name.setText(data.getName());

    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_push_select_hospital;
    }
}