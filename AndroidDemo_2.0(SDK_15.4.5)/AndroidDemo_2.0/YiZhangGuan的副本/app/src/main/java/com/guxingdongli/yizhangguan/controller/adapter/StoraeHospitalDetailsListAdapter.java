package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.callback.StoraeHospitalDetailsCallBack;
import com.guxingdongli.yizhangguan.model.StoraeHospitalDetailsBase;
import com.guxingdongli.yizhangguan.model.passvalue.StoraeHospitalPassValue;
import com.guxingdongli.yizhangguan.view.home.home_hospital.StoraeHospitalDetailsActivity;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 验收详情适配器
 * Created by jackmask on 2018/3/3.
 */

public class StoraeHospitalDetailsListAdapter extends BaseCommAdapter<StoraeHospitalDetailsBase.DataBean.DetailsListBean> {
    boolean editextCon = false;
    private List<StoraeHospitalPassValue> inputList = new ArrayList<>();
    private StoraeHospitalDetailsCallBack callBack;
    public StoraeHospitalDetailsListAdapter(List<StoraeHospitalDetailsBase.DataBean.DetailsListBean> datas) {
        super(datas);
    }

    public void setEditextCon(boolean editextCon) {
        this.editextCon = editextCon;
    }

    public List<StoraeHospitalPassValue> getInputList() {
        return inputList;
    }

    public void setInputList(List<StoraeHospitalPassValue> inputList) {
        this.inputList = inputList;
    }



    @Override
    protected void setUI(ViewHolder holder, final int position, final Context context) {
        final EditText acceptance_num = holder.getItemView(R.id.acceptance_num);
        TextView name_tv = holder.getItemView(R.id.name_tv);
        TextView model_tv = holder.getItemView(R.id.model_tv);
        TextView price_tv = holder.getItemView(R.id.price_tv);
        TextView batch_number = holder.getItemView(R.id.batch_number);
        TextView effective_data = holder.getItemView(R.id.effective_data);
        TextView delivery_num = holder.getItemView(R.id.delivery_num);
        LinearLayout item_layout = holder.getItemView(R.id.item_layout);
        StoraeHospitalDetailsBase.DataBean.DetailsListBean data = mDatas.get(position);
        name_tv.setText(data.getName());
        model_tv.setText(data.getSpecification());
        price_tv.setText(data.getPrice());
        batch_number.setText(data.getBatchNumber());
        effective_data.setText(data.getValid());
        delivery_num.setText(data.getQuantity()+"");
        acceptance_num.setText(data.getNoAcceptanceQuantity()==0?"":data.getNoAcceptanceQuantity()+"");
        inputList.get(position).setAcceptanceQuantity(0);
        item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("item_layout = " + position);
            }
        });
        acceptance_num.addTextChangedListener(new MyTextWatcher(position));
        /*acceptance_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (acceptance_num.getText().toString()!=null){
                    //inputList.get(position).setAcceptanceQuantity(acceptance_num.getText().toString());
                   // System.out.println("acceptance_num.getText().toString() = " + acceptance_num.getText().toString());
                    ((StoraeHospitalDetailsActivity)context).setNum(position,acceptance_num.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/
        if (editextCon){
            acceptance_num.setFocusable(false);
            acceptance_num.setFocusableInTouchMode(false);

        }
    }

    private class MyTextWatcher implements TextWatcher{
        int position;

        private MyTextWatcher(int position){
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
                System.out.println("position = " + position);
            System.out.println("editable = " + editable.toString());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_storae_hospital_details_list;
    }
}
