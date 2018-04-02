package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dmcbig.mediapicker.PickerActivity;
import com.dmcbig.mediapicker.PickerConfig;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.view.home.home_hospital.AddRepairActivity;
import com.squareup.picasso.Picasso;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.io.File;
import java.util.List;

/**
 * Created by jackmask on 2018/3/5.
 */

public class AddRepairAdapter extends BaseCommAdapter<String> {
    public AddRepairAdapter(List<String> datas) {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, int position, final Context context) {
        String data = getItem(position);
        final ImageView pic = holder.getItemView(R.id.pic);
        if (data.equals("btn")){
            Picasso.get()
                    .load(R.mipmap.d2_tianjiazhaopian)
                    .into(pic);
            pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((YuXianDeActivity)context).selePic();
                    /*Intent intent =new Intent(context, PickerActivity.class);
                    intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_IMAGE);//default image and video (Optional)
                    long maxSize=188743680L;//long long long
                    intent.putExtra(PickerConfig.MAX_SELECT_SIZE,maxSize); //default 180MB (Optional)
                    intent.putExtra(PickerConfig.MAX_SELECT_COUNT,9);  //default 40 (Optional)
                    intent.putExtra(PickerConfig.DEFAULT_SELECTED_LIST,((AddRepairActivity)context).getSelects()); // (Optional)
                    ((AddRepairActivity)context).startActivityForResult(intent,321);
                    System.out.println("相册");*/
                }
            });
        }else{
            Picasso.get()
                    .load(new File(data))
                    .into(pic);
            /*Glide.with(context)
                    .load(data)
                    .into(pic);*/

        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_add_repair_pic;
    }
}
