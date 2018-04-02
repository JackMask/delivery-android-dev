package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.callback.MakeUpInfoCallBack;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.List;

/**
 * Created by jackmask on 2018/3/8.
 */

public class StockingInfoAdapter extends BaseCommAdapter<String> {

    private MakeUpInfoCallBack callBack;
    public StockingInfoAdapter(List<String> datas) {
        super(datas);
    }

    public MakeUpInfoCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(MakeUpInfoCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    protected void setUI(ViewHolder holder, final int position, final Context context) {
        LinearLayout select_btn = holder.getItemView(R.id.select_btn);
        final TextView data_tv = holder.getItemView(R.id.data_tv);
        select_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callBack!=null){
                    callBack.selectItem(position,data_tv);
                }
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_make_up_info;
    }


}