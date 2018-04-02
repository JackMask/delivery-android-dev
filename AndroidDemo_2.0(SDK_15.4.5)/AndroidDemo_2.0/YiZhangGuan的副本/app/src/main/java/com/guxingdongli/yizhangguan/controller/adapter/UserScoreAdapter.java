package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.List;

/**
 * Created by jackmask on 2018/3/6.
 */

public class UserScoreAdapter extends BaseCommAdapter<String> {


    public UserScoreAdapter(List<String> datas) {
        super(datas);
    }


    @Override
    protected void setUI(ViewHolder holder, final int position, final Context context) {
        final String data = getItem(position);

        final RatingBar rsv_rating = holder.getItemView(R.id.rc_rate);
        final TextView score_tv = holder.getItemView(R.id.score_tv);
        final LinearLayout score_layout = holder.getItemView(R.id.score_layout);
        final TextView no_score = holder.getItemView(R.id.no_score);
        try {
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.pentagram_red);
            int scroeHeight = bmp.getHeight();
            if(scroeHeight!=0){
                LinearLayout.LayoutParams llp = (LinearLayout.LayoutParams) rsv_rating.getLayoutParams();
                llp.width = -2;// 包裹内容
                llp.height = scroeHeight;
                rsv_rating.setLayoutParams(llp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

                rsv_rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        if (no_score.getVisibility() == View.VISIBLE){
                            no_score.setVisibility(View.GONE);
                            score_layout.setVisibility(View.VISIBLE);
                        }
                        float i =  rsv_rating.getRating();
                        score_tv.setText(i+"");
                    }
                });
        rsv_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_user_score;
    }


}
