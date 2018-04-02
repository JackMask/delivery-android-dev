package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.YiZhangGuanApplication;
import com.guxingdongli.yizhangguan.model.PushSelectHospitalBase;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackmask on 2018/3/11.
 */

public class MyHospitalAdapter  extends BaseCommAdapter<PushSelectHospitalBase.DataBean> {
    private boolean clickType = true;
    public MyHospitalAdapter(List<PushSelectHospitalBase.DataBean> datas) {
        super(datas);
    }

    public void setClick(boolean clickType){
        this.clickType = clickType;
    }

    @Override
    protected void setUI(ViewHolder holder, int position, final Context context) {
        PushSelectHospitalBase.DataBean data = getItem(position);
        TextView hospital_tv = holder.getItemView(R.id.hospital_tv);
        TextView hospital_add = holder.getItemView(R.id.hospital_add);

        hospital_tv.setText(data.getName());
        hospital_add.setText(data.getHospitalAddress());
    }


    @Override
    protected int getLayoutId() {
        return R.layout.item_my_hospital;
    }
}