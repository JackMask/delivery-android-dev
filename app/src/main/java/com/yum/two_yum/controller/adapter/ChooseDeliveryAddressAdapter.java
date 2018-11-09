package com.yum.two_yum.controller.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.yum.two_yum.R;
import com.yum.two_yum.base.FilterBase;
import com.yum.two_yum.base.SearchNearbyBase;
import com.yum.two_yum.base.SearchNearbyDataBase;
import com.yum.two_yum.controller.adapter.callback.ClientNearbyCallBack;
import com.yum.two_yum.controller.adapter.callback.FilterFootCallBack;
import com.yum.two_yum.controller.adapter.universalAdapter.BaseCommAdapter;
import com.yum.two_yum.controller.adapter.universalAdapter.ViewHolder;
import com.yum.two_yum.utile.AppUtile;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/4/11
 */

public class ChooseDeliveryAddressAdapter extends BaseCommAdapter<FilterBase> {

    public interface  ChooseDeliveryAddressCallBack{
        public abstract void selectFoot(int position,boolean selectType,boolean clickType);
        void delclic(String addressId);
        void editClick(SearchNearbyDataBase data);
    }
    private String lat,lng,distance;
    private ChooseDeliveryAddressCallBack callBack;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setCallBack(ChooseDeliveryAddressCallBack callBack) {
        this.callBack = callBack;
    }

    public ChooseDeliveryAddressAdapter(List<FilterBase> datas) {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, final int position, final Context context) {
        TextView delBtn = holder.getItemView(R.id.del_btn);
        final SwipeMenuLayout chat_item = holder.getItemView(R.id.chat_item);
        final FilterBase data = mDatas.get(position);
        ImageView edit_btn = holder.getItemView(R.id.edit_btn);
        TextView address_tv = holder.getItemView(R.id.address_tv);
        TextView name_tv = holder.getItemView(R.id.name_tv);
        TextView phone_tv = holder.getItemView(R.id.phone_tv);
        address_tv.setText(data.getAddressRespResultsBeans().getAddress().replace("\n",", "));
        name_tv.setText(data.getAddressRespResultsBeans().getName());
        phone_tv.setText(data.getAddressRespResultsBeans().getPhone()+"");
        ImageView hook_red_iv = holder.getItemView(R.id.hook_red_iv);
        RelativeLayout item_layout = holder.getItemView(R.id.item_layout);
        if (!TextUtils.isEmpty(lat)&&!TextUtils.isEmpty(lng)&&!TextUtils.isEmpty(distance))
        if ((Double.valueOf(distance)*1600)>= AppUtile.GetDistance(Double.valueOf(data.getAddressRespResultsBeans().getLat()),Double.valueOf(data.getAddressRespResultsBeans().getLng())
                ,Double.valueOf(lat),Double.valueOf(lng))){
            address_tv.setTextColor(Color.parseColor("#484848"));
            name_tv.setTextColor(Color.parseColor("#bfbfbf"));
            phone_tv.setTextColor(Color.parseColor("#bfbfbf"));
        }else{
            address_tv.setTextColor(Color.parseColor("#DBDBDB"));
            name_tv.setTextColor(Color.parseColor("#DBDBDB"));
            phone_tv.setTextColor(Color.parseColor("#DBDBDB"));
        }
        if (data.isType()){
            hook_red_iv.setImageResource(R.mipmap.hook_red);
        }else{
            hook_red_iv.setImageResource(0);
        }
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack!=null){
                    callBack.editClick(data.getAddressRespResultsBeans());
                }
                chat_item.quickClose();
            }
        });
        item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack!=null){
                    boolean conType = true;
                    if (!TextUtils.isEmpty(lat)&&!TextUtils.isEmpty(lng)&&!TextUtils.isEmpty(distance)){
                        if ((Double.valueOf(distance)*1600)>= AppUtile.GetDistance(Double.valueOf(data.getAddressRespResultsBeans().getLat()),Double.valueOf(data.getAddressRespResultsBeans().getLng())
                                ,Double.valueOf(lat),Double.valueOf(lng))){
                            conType = true;
                        }else{
                            conType = false;
                        }

                    }
                    if (data.isType()){
                        callBack.selectFoot(position,false,conType);
                    }else{
                        callBack.selectFoot(position,true,conType);
                    }

                }
                chat_item.quickClose();
            }
        });
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack!=null){
                    callBack.delclic(data.getAddressRespResultsBeans().getId());
                }
                chat_item.quickClose();
            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.item_choose_delivery_address;
    }
}