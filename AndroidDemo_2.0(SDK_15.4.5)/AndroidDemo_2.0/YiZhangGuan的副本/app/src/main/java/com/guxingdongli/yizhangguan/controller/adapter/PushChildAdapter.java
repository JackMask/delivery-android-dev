package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.callback.PushChildCallBack;
import com.guxingdongli.yizhangguan.model.PushBase;
import com.guxingdongli.yizhangguan.view.home.home_hospital.fragment.callback.StorageHospitalOnClickCallBack;
import com.yuxiaolong.yuxiandelibrary.NoSlideListView;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/24
 */

public class PushChildAdapter extends BaseCommAdapter<PushBase.DataBean.ListManyBean> {

    public PushChildAdapter(List<PushBase.DataBean.ListManyBean> datas) {
        super(datas);
    }


    public void setPice(String pice){
        for (int i = 0 ; i < mDatas.size();i++){
            mDatas.get(i).setPrice(pice);
        }
    }

    @Override
    protected void setUI(ViewHolder holder, final int position, final Context context) {
        final PushBase.DataBean.ListManyBean data = getItem(position);
        TextView specification = holder.getItemView(R.id.specification);
        TextView model = holder.getItemView(R.id.model);
        final EditText amount = holder.getItemView(R.id.amount);
        specification.setText(data.getSpecification());
        model.setText(data.getModel());
        System.out.println("data.getPrice() = " + data.getPrice());
        amount.setText(data.getPrice()+"");


    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_child_push;
    }
}