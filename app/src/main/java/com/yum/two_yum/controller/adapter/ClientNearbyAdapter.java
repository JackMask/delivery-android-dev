package com.yum.two_yum.controller.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.view.sliding.AbSlidingPlayView;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.jude.rollviewpager.hintview.IconHintView;
import com.squareup.picasso.Picasso;
import com.yum.two_yum.R;
import com.yum.two_yum.base.NearbyBase;
import com.yum.two_yum.controller.adapter.callback.ClientNearbyCallBack;
import com.yum.two_yum.controller.adapter.universalAdapter.BaseCommAdapter;
import com.yum.two_yum.controller.adapter.universalAdapter.ViewHolder;
import com.yum.two_yum.utile.AppUtile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 余先德
 * @data 2018/4/7
 */

public class ClientNearbyAdapter extends BaseCommAdapter<NearbyBase.DataBean.HomeMerchantRespResultListBean> {

    private NearbyCallBack clientNearbyCallBack;
    public interface NearbyCallBack{
        void itemClick(int position,String id,boolean isLike,int state,ImageView likeView);
        void itemCollectionClick(ImageView imageView,NearbyBase.DataBean.HomeMerchantRespResultListBean data);
    }
    public void setClientNearbyCallBack(NearbyCallBack clientNearbyCallBack) {
        this.clientNearbyCallBack = clientNearbyCallBack;
    }

    public ClientNearbyAdapter(List<NearbyBase.DataBean.HomeMerchantRespResultListBean> datas) {
        super(datas);
    }

    @Override
    protected void setUI(final ViewHolder holder, final int position, final Context context) {
       final NearbyBase.DataBean.HomeMerchantRespResultListBean data = mDatas.get(position);
        LinearLayout title_view = holder.getItemView(R.id.title_view);
        LinearLayout item_layout = holder.getItemView(R.id.content_view);
        TextView name_tv = holder.getItemView(R.id.name_tv);
        View reserved_v = holder.getItemView(R.id.reserved_v);
        TextView like_num_tv = holder.getItemView(R.id.like_num_tv);
        TextView distance_tv = holder.getItemView(R.id.distance_tv);
        TextView series_tv = holder.getItemView(R.id.series_tv);
        TextView close_tv = holder.getItemView(R.id.close_tv);
        final ImageView collection_btn = holder.getItemView(R.id.collection_btn);
        final ImageView headImg = holder.getItemView(R.id.head_img);
        name_tv.setText(!TextUtils.isEmpty(data.getKeyword())?data.getKeyword().replaceAll(","," • "):"");
        //distance_tv.setText(data.getDistance());
        series_tv.setText(data.getName()+(!TextUtils.isEmpty(data.getSeries())?","+data.getSeries():""));
        Double distanceEnd = 0.0;
        if (!TextUtils.isEmpty(data.getDistance())){
            Double distance = Double.valueOf(data.getDistance());
            distanceEnd = AppUtile.getOneDecimal(distance/1609.344);
        }

        distance_tv.setText(AppUtile.getDistance(distanceEnd+"")+" "+context.getResources().getString(R.string.MILE));
        if (data.isLike()){
            collection_btn.setImageResource(R.mipmap.heart_red_big);
        }else{
            collection_btn.setImageResource(R.mipmap.heart_white_big);
        }
        collection_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clientNearbyCallBack!=null){
                    clientNearbyCallBack.itemCollectionClick(collection_btn,data);
                }
            }
        });
        headImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // AppUtile.headClick(context,headImg,data.getAvatar());
                if (clientNearbyCallBack!=null){
                    clientNearbyCallBack.itemClick(position,data.getUserId()+"",data.isLike(),data.getState(),collection_btn);
                }
            }
        });
        like_num_tv.setText(data.getLikeNum()+"");
        Picasso.get().load(data.getAvatar()).placeholder(R.mipmap.head_img_being).into(headImg);
        if (data.getState() == 2){
            close_tv.setVisibility(View.VISIBLE);
            close_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clientNearbyCallBack!=null){
                        clientNearbyCallBack.itemClick(position,data.getUserId()+"",data.isLike(),data.getState(),collection_btn);
                    }
                }
            });
        }else {
            close_tv.setVisibility(View.GONE);
        }
        holder.getItemView(R.id.content_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clientNearbyCallBack!=null){
                    clientNearbyCallBack.itemClick(position,data.getUserId()+"",data.isLike(),data.getState(),collection_btn);
                }
            }
        });
        item_layout.setVisibility(View.VISIBLE);

        ((RollPagerView) holder.getItemView(R.id.mAbSlidingPlayView)).setHintView(new IconHintView(context, R.mipmap.carousel_one, R.mipmap.carousel_two));
        if (data.getMerchantMenuCoversRespResults()!=null&&data.getMerchantMenuCoversRespResults().size()>0) {
            // ((RollPagerView)holder.getItemView(R.id.mAbSlidingPlayView)).setHintView(new ColorPointHintView(context, Color.YELLOW, Color.WHITE));
            ((RollPagerView) holder.getItemView(R.id.mAbSlidingPlayView)).setAdapter(new MyPagerAdapter(data.getMerchantMenuCoversRespResults()));
            ((RollPagerView) holder.getItemView(R.id.mAbSlidingPlayView)).setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    if (clientNearbyCallBack != null&&data.getState() != 2) {
                        clientNearbyCallBack.itemClick(position, data.getUserId()+"",data.isLike(),data.getState(),collection_btn);
                    }
                }
            });
            ((RollPagerView) holder.getItemView(R.id.mAbSlidingPlayView)).pause();
        }
        if (position == mDatas.size()-1){
            reserved_v.setVisibility(View.VISIBLE);
        }else{
            reserved_v.setVisibility(View.GONE);
        }

    }
    /**
     * 轮播图的view
     */
    class MyPagerAdapter extends StaticPagerAdapter {



       // private int[] image = {R.mipmap.one, R.mipmap.one, R.mipmap.one, R.mipmap.one};
        private List<NearbyBase.DataBean.HomeMerchantRespResultListBean.MerchantMenuCoversRespResultsBean> urls;

        public MyPagerAdapter(List<NearbyBase.DataBean.HomeMerchantRespResultListBean.MerchantMenuCoversRespResultsBean> urls){
            this.urls = urls;
        }
        // SetScaleType(ImageView.ScaleType.CENTER_CROP);
        // 按比例扩大图片的size居中显示，使得图片长(宽)等于或大于View的长(宽)

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView imageView = new ImageView(container.getContext());
            Picasso.get().load(urls.get(position).getCover()).placeholder(R.mipmap.timg).into(imageView);
            //imageView.setImageResource(image[position]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return imageView;
        }

        @Override
        public int getCount() {
            return urls.size();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_client_nearby;
    }
}
