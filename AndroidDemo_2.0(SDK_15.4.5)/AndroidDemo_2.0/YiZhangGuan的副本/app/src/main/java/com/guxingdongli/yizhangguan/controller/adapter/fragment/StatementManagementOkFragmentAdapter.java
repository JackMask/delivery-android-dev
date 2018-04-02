package com.guxingdongli.yizhangguan.controller.adapter.fragment;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.callback.StatementManagementOkCallBack;
import com.guxingdongli.yizhangguan.model.StatementManagementBase;
import com.guxingdongli.yizhangguan.model.StatementManagementOkBase;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.List;

/**
 * Created by jackmask on 2018/3/9.
 */

public class StatementManagementOkFragmentAdapter extends BaseCommAdapter<StatementManagementOkBase.DataBeanX.DataBean> {

    private StatementManagementOkCallBack callBack;


    public void setCallBack(StatementManagementOkCallBack callBack) {
        this.callBack = callBack;
    }

    public StatementManagementOkFragmentAdapter(List<StatementManagementOkBase.DataBeanX.DataBean> datas) {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, int position, final Context context) {
        final StatementManagementOkBase.DataBeanX.DataBean data = mDatas.get(position);
        TextView hospital = holder.getItemView(R.id.hospital);
        TextView type = holder.getItemView(R.id.type);
        TextView request_time = holder.getItemView(R.id.request_time);
        TextView actual_amount = holder.getItemView(R.id.actual_amount);
        TextView form_amount = holder.getItemView(R.id.form_amount);
        TextView returns_amount = holder.getItemView(R.id.returns_amount);
        TextView should_amount = holder.getItemView(R.id.should_amount);
        TextView confirm_btn = holder.getItemView(R.id.confirm_btn);
        hospital.setText(data.getHospitalName());
        type.setText(data.getConfirmStatus());
        request_time.setText(data.getCreateTime());
        actual_amount.setText(data.getActualAmount()+"");
        form_amount.setText(data.getPretendAmount()+"");
        returns_amount.setText(data.getReturnAmount()+"");
        should_amount.setText(data.getReconciliationAmount()+"");
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callBack!=null){
                    callBack.Click(data.getGid());
                }
            }
        });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.itme_statement_management_ok_fragment;
    }
}
