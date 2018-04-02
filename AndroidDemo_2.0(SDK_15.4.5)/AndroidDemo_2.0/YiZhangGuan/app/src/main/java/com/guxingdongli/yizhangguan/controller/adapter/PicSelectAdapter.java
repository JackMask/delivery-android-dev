package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.view.home.home_merchants.FillInRepairOrderActivity;
import com.squareup.picasso.Picasso;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.io.File;
import java.util.List;

/**
 * Created by jackmask on 2018/3/9.
 */

public class PicSelectAdapter   extends BaseCommAdapter<String> {
    public PicSelectAdapter(List<String> datas) {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, int position, final Context context) {
        String data = getItem(position);
        final ImageView pic = holder.getItemView(R.id.pic);
        if (data.equals("btn")){
            Picasso.get().load(R.mipmap.d2_tianjiazhaopian)
                    .into(pic);
            pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((YuXianDeActivity)context).selePic();
                }
            });
        }else if (data.substring(0,4).equals("http")){
            Picasso.get()
                    .load(data)
                    .placeholder(R.mipmap.default_image)
                    .into(pic);
        }else{
            Picasso.get()
                    .load(new File(data))
                    .placeholder(R.mipmap.default_image)
                    .into(pic);

        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_edit_dynamic;
    }
}