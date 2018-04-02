package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.callback.MakeUpInfoCallBack;
import com.guxingdongli.yizhangguan.model.PreparationOrderDetailsBase;
import com.guxingdongli.yizhangguan.model.StoraeHospitalDetailsBase;
import com.guxingdongli.yizhangguan.model.TestHospitalBean;
import com.guxingdongli.yizhangguan.view.home.dialog.PromptDialog;
import com.guxingdongli.yizhangguan.view.home.home_hospital.fragment.callback.StorageHospitalOnClickCallBack;
import com.guxingdongli.yizhangguan.view.home.home_merchants.MakeUpInfoActivity;
import com.guxingdongli.yizhangguan.view.home.home_merchants.QRExamineActivity;
import com.guxingdongli.yizhangguan.view.home.home_merchants.StockingInfoActivity;
import com.yuxiaolong.yuxiandelibrary.NoSlideListView;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.List;

/**
 * Created by jackmask on 2018/3/8.
 */

public class MakeUpInfoAdapter extends BaseCommAdapter<PreparationOrderDetailsBase.DataBean.DetailsListBean> {

    private MakeUpInfoCallBack callBack;
    private boolean type = true;
    public MakeUpInfoAdapter(List<PreparationOrderDetailsBase.DataBean.DetailsListBean> datas) {
        super(datas);
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public void setCallBack(MakeUpInfoCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    protected void setUI(ViewHolder holder, final int position, final Context context) {
        PreparationOrderDetailsBase.DataBean.DetailsListBean data = mDatas.get(position);
        LinearLayout make_up_info_layout = holder.getItemView(R.id.make_up_info_layout );
        LinearLayout stocking_info_layout = holder.getItemView(R.id.stocking_info_layout);
        EditText batch_number = holder.getItemView(R.id.batch_number);
        EditText serial_number = holder.getItemView(R.id.serial_number);
        TextView title_name = holder.getItemView(R.id.title_name);
        EditText coding = holder.getItemView(R.id.coding);
        TextView num_until_tv = holder.getItemView(R.id.num_until_tv);
        TextView specification_tv = holder.getItemView(R.id.specification_tv);
        TextView num_tv = holder.getItemView(R.id.num_tv);
        batch_number.setText(data.getBatchNumber());
        serial_number.setText(data.getSerialNum());
        coding.setText(data.getProductNum());
        title_name.setText(data.getName());
        num_until_tv.setText(data.getQuantity()+" / " + data.getUnit());
        specification_tv.setText(data.getPacking());
        num_tv.setText(data.getPrice());

        make_up_info_layout.setVisibility(View.GONE);
        stocking_info_layout.setVisibility(View.VISIBLE);
        LinearLayout stocking_select_btn = holder.getItemView(R.id.stocking_select_btn);
        final TextView stocking_data_tv = holder.getItemView(R.id.stocking_data_tv);

        if (type) {
            stocking_select_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (callBack != null) {
                        callBack.selectItem(position,stocking_data_tv);
                    }
                }
            });
            batch_number.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    ((StockingInfoActivity)context).setBatchNumber(position,charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            serial_number.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    ((StockingInfoActivity)context).setSerialNum(position,charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            coding.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    ((StockingInfoActivity)context).setProductNum(position,charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }else{
            batch_number.setEnabled(false);
            serial_number.setEnabled(false);
            coding.setEnabled(false);

        }

/*
        if (type) {
            make_up_info_layout.setVisibility(View.VISIBLE);
            stocking_info_layout.setVisibility(View.GONE);
            LinearLayout select_btn = holder.getItemView(R.id.select_btn);
            final TextView data_tv = holder.getItemView(R.id.data_tv);
            select_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (callBack != null) {
                        callBack.selectItem(data_tv);
                    }
                }
            });
        }else{


        }*/
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_make_up_info;
    }


}