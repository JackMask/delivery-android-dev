package com.yum.two_yum.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.squareup.picasso.Picasso;
import com.yum.two_yum.R;
import com.yum.two_yum.base.FilterBase;
import com.yum.two_yum.base.GetMerchantMenuListBase;
import com.yum.two_yum.controller.adapter.callback.NowMenuCallBack;
import com.yum.two_yum.controller.adapter.universalAdapter.BaseCommAdapter;
import com.yum.two_yum.controller.adapter.universalAdapter.ViewHolder;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.view.merchants.menu.AddEditMenuActivity;
import com.yum.two_yum.view.merchants.menu.NowMenuActivity;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/4/13
 */

public class NowMenuAdapter extends BaseCommAdapter<FilterBase> {
    private NowMenuCallBack callBack;

    public void setCallBack(NowMenuCallBack callBack) {
        this.callBack = callBack;
    }

    public NowMenuAdapter(List<FilterBase> datas) {
        super(datas);
    }


    @Override
    protected void setUI(ViewHolder holder, final int position, final Context context) {
        TextView delBtn = holder.getItemView(R.id.del_btn);
        RelativeLayout selectLayout = holder.getItemView(R.id.select_layout);
        LinearLayout itemLayout = holder.getItemView(R.id.item_layout);
        ImageView selectBtn = holder.getItemView(R.id.select_btn);
        ImageView dishes_img= holder.getItemView(R.id.dishes_img);
        TextView menu_name_tv= holder.getItemView(R.id.menu_name_tv);
        TextView content_tv = holder.getItemView(R.id.content_tv);
        TextView pice_tv = holder.getItemView(R.id.pice_tv);
        TextView prompt_tv = holder.getItemView(R.id.prompt_tv);
        TextView num_tv = holder.getItemView(R.id.num_tv);
        final SwipeMenuLayout chatItem = holder.getItemView(R.id.chat_item);
        final FilterBase data = mDatas.get(position);
//        if (data.isType()){
//            delBtn.setVisibility(View.VISIBLE);
//            selectLayout.setVisibility(View.VISIBLE);
//        }else{
//            delBtn.setVisibility(View.GONE);
//            selectLayout.setVisibility(View.GONE);
//        }
       // selectLayout.setVisibility(View.VISIBLE);
        Picasso.get().load(data.getMenuRespResultsBean().getCover()).placeholder(R.mipmap.timg).into(dishes_img);
        menu_name_tv.setText(data.getMenuRespResultsBean().getName());
        content_tv.setText(data.getMenuRespResultsBean().getDescribe());
        pice_tv.setText("$ "+ AppUtile.getPrice(data.getMenuRespResultsBean().getPrice()+"")+"/");

        num_tv.setText(data.getMenuRespResultsBean().getNumber()>0?data.getMenuRespResultsBean().getNumber()+"":context.getResources().getString(R.string.SOLDOUT));
        if (data.getMenuRespResultsBean().getNumber()>0){
            num_tv.setTextColor(Color.parseColor("#2A90FF"));
        }else{
            num_tv.setTextColor(Color.parseColor("#ff3b30"));
        }
        if (data.getMenuRespResultsBean().getState()==1){
            selectBtn.setImageResource(R.mipmap.select);
        }else{
            selectBtn.setImageResource(R.mipmap.select_not);
        }
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack!=null){
                    callBack.clickSelect(position,data.getMenuRespResultsBean().getState()==1?true:false);
                }
                chatItem.quickClose();
            }
        });
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack!=null){
                    callBack.clickDel(position,data.getMenuRespResultsBean().getId()+"");
                }
                chatItem.quickClose();
            }
        });
        itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack!=null){
                    callBack.itemClick(data.getMenuRespResultsBean());
                }
//                Intent intent = new Intent(context,AddEditMenuActivity.class);
//                intent.putExtra("data",data.getMenuRespResultsBean());
//                intent.putExtra("type",false);
//                context.startActivityForResult(intent,0);
            }
        });
        if (position == mDatas.size()-1){
            prompt_tv.setVisibility(View.VISIBLE);
        }else{
            prompt_tv.setVisibility(View.GONE);
        }

    }


    @Override
    protected int getLayoutId() {
        return R.layout.item_now_menu;
    }
}