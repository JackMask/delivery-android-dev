package com.guxingdongli.yizhangguan.controller.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ab.activity.AbActivity;
import com.guxingdongli.yizhangguan.R;
import com.squareup.picasso.Picasso;
import com.yuxiaolong.yuxiandelibrary.imgborwser.helper.UTPreImageViewHelper;

import java.util.List;

/**
 * Created by Administrator on 2017/4/30.
 */

public class ShopCommentPicAdapter extends BaseAdapter {
    private Context abActivity;
    private List<String> list;
    private LayoutInflater inflater;

    public ShopCommentPicAdapter(Context context, List<String> list){
        this.list = list;
        this.abActivity = context;
        this.inflater = ((LayoutInflater) abActivity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final imageHolder viewHolder;
        final int con = position;
        if (convertView == null) {
            viewHolder = new imageHolder();
                convertView = inflater.inflate(R.layout.item_shop_comment_pics, null);
            viewHolder.pic = (ImageView)convertView.findViewById(R.id.comment_pics);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (imageHolder)convertView.getTag();
        }
        //viewHolder.pic.setImageResource(imgs[position]);//为ImageView设置图片资源

        Picasso.get().load(list.get(position)).placeholder(R.drawable.default_image).into(viewHolder.pic);


        viewHolder.pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView mCommentPic;
                UTPreImageViewHelper helper1 = new UTPreImageViewHelper((Activity) abActivity);
                helper1.setIndicatorStyle(2);
                helper1.setSaveTextMargin(0, 0, 0, 5000);
                for (int i = 0; i < list.size(); i++) {
                    mCommentPic = viewHolder.pic;
                    helper1.addImageView(mCommentPic, list.get(i).trim());
                }
                helper1.startPreActivity(con);
            }
        });

        return convertView;
    }
    static class imageHolder{
        private ImageView pic;
    }
}
