package com.yum.two_yum.controller.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;
import com.yum.two_yum.R;
import com.yum.two_yum.base.SearchAddressDataBase;
import com.yum.two_yum.controller.adapter.universalAdapter.BaseCommAdapter;
import com.yum.two_yum.controller.adapter.universalAdapter.ViewHolder;
import com.yum.two_yum.view.client.clientorder.SearchAddressActivity;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/4/9
 */

public class SearchAddressAdapter extends BaseCommAdapter<SearchAddressDataBase> {

    public interface ItemClick{
        void click(String lat,String lng,String address,String name);
    }

    private ItemClick itemClick;

    public ItemClick getItemClick() {
        return itemClick;
    }

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public SearchAddressAdapter(List<SearchAddressDataBase> datas) {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, int position, Context context) {
        final SearchAddressDataBase data  = mDatas.get(position);
        TextView address_content_tv = holder.getItemView(R.id.address_content_tv);
        TextView address_allText_tv = holder.getItemView(R.id.address_allText_tv);
        LinearLayout item_layout = holder.getItemView(R.id.item_layout);
        address_content_tv.setText(data.getName().toString());
        address_allText_tv.setText(data.getAddress().toString());
        item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClick!=null){
                    itemClick.click(data.getLatLng().latitude+"",data.getLatLng().longitude+"",data.getAddress(),data.getName());
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_search_address;
    }
}
