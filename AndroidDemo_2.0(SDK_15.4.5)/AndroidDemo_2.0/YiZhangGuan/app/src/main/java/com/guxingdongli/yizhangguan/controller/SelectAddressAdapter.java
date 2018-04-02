package com.guxingdongli.yizhangguan.controller;

import android.content.Context;
import android.text.Html;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.model.SelectAddressBase;
import com.guxingdongli.yizhangguan.util.AppUtile;
import com.guxingdongli.yizhangguan.view.login.SelectAddressActivity;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.List;

/**
 * Created by jackmask on 2018/3/1.
 */

public class SelectAddressAdapter extends BaseCommAdapter<SelectAddressBase> {

    private String keywords = "";
    private SelectAddressActivity addressActivity;

    public SelectAddressAdapter(List<SelectAddressBase> datas) {
        super(datas);
    }

    public void setAddressActivity(SelectAddressActivity addressActivity){
        this.addressActivity = addressActivity;
    }


    @Override
    protected void setUI(ViewHolder holder, int position, Context context) {
        SelectAddressBase data = getItem(position);
        TextView tv_name = holder.getItemView(R.id.name);
        String str1 = data.getName().replaceAll(keywords,"<font color='blue'>"+keywords+"</font>");
        tv_name.setText(AppUtile.getHtmlFrom(str1));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_select_address;
    }

    @Override
    public void notifyDataSetChanged() {
        keywords = addressActivity.getKeywords();
        super.notifyDataSetChanged();
    }
}
