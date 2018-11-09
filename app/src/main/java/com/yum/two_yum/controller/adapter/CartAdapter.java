package com.yum.two_yum.controller.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yum.two_yum.R;
import com.yum.two_yum.base.BusinessDetailsBase;
import com.yum.two_yum.base.FilterBase;
import com.yum.two_yum.base.input.DishInput;
import com.yum.two_yum.controller.adapter.callback.BusinessDetailsCallBack;
import com.yum.two_yum.controller.adapter.callback.FilterFootCallBack;
import com.yum.two_yum.controller.adapter.universalAdapter.BaseCommAdapter;
import com.yum.two_yum.controller.adapter.universalAdapter.ViewHolder;
import com.yum.two_yum.utile.AppUtile;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/4/10
 */

public class CartAdapter extends BaseCommAdapter<DishInput> {

    private BuseinessDetailsMainAdapter.ItemClic callBack;

    public void setCallBack(BuseinessDetailsMainAdapter.ItemClic callBack) {
        this.callBack = callBack;
    }

    public CartAdapter(List<DishInput> datas) {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, final int position, final Context context) {
       final DishInput data = mDatas.get(position);
        final ImageView lessBtn = holder.getItemView(R.id.less_btn);
        final TextView numTv = holder.getItemView(R.id.num_tv);
        final ImageView plusBtn = holder.getItemView(R.id.plus_btn);
        TextView dish_name = holder.getItemView(R.id.dish_name);
        TextView dish_price = holder.getItemView(R.id.dish_price);
        dish_price.setText("$ "+ AppUtile.getPrice(AppUtile.mul(data.getData().getPrice(),data.getDishNum())+""));
        dish_name.setText(data.getData().getName());
        numTv.setText(data.getDishNum()+"");
        if (data.getDishNum()>0){
            lessBtn.setVisibility(View.VISIBLE);
            numTv.setVisibility(View.VISIBLE);
        }
        lessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (callBack != null) {
//                    callBack.itemLessClike(1, v,data.getDishNum(),plusBtn);
//                }
                int num = Integer.valueOf(numTv.getText().toString());
                num--;
                numTv.setText(num + "");
                if (num == 0) {
                    numTv.setVisibility(View.GONE);
                    lessBtn.setVisibility(View.GONE);
                }
                if (callBack != null) {
                    //    callBack.itemPlusClike(0, v, data.getDishNum(), lessBtn);
                    callBack.itemLessClike(data.getData(),plusBtn, num, data.getData().getNumber(), numTv,lessBtn);
                }
            }
        });
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.valueOf(numTv.getText().toString());
                if (data.getData().getNumber()>num){

                num++;
                numTv.setText(num + "");

                numTv.setVisibility(View.VISIBLE);
                lessBtn.setVisibility(View.VISIBLE);
                if (callBack != null) {
                //    callBack.itemPlusClike(0, v, data.getDishNum(), lessBtn);
                    callBack.itemPlusClike(data.getData(),plusBtn, num, data.getData().getNumber(), numTv,lessBtn);
                }

                }

            }
        });

    }


    @Override
    protected int getLayoutId() {
        return R.layout.item_cart;
    }
}
