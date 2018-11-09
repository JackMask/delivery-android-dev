package com.yum.two_yum.controller.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.yum.two_yum.R;
import com.yum.two_yum.base.NearbyBase;
import com.yum.two_yum.controller.adapter.callback.ClientNearbyCallBack;
import com.yum.two_yum.controller.adapter.universalAdapter.BaseCommAdapter;
import com.yum.two_yum.controller.adapter.universalAdapter.ViewHolder;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/4/7
 */

public class CollectionAdapter extends BaseCommAdapter<NearbyBase.DataBean.HomeMerchantRespResultListBean> {

    private ClientNearbyCallBack clientNearbyCallBack;

    public void setClientNearbyCallBack(ClientNearbyCallBack clientNearbyCallBack) {
        this.clientNearbyCallBack = clientNearbyCallBack;
    }

    public CollectionAdapter(List<NearbyBase.DataBean.HomeMerchantRespResultListBean> datas) {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, final int position, final Context context) {
        View title_view = holder.getItemView(R.id.title_view);
        LinearLayout item_layout = holder.getItemView(R.id.content_view);

            title_view.setVisibility(View.GONE);
            item_layout.setVisibility(View.VISIBLE);
            holder.getItemView(R.id.content_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clientNearbyCallBack!=null){
                        clientNearbyCallBack.itemClike(position,"");
                    }
                }
            });
            ((RollPagerView)holder.getItemView(R.id.mAbSlidingPlayView)).setHintView(new ColorPointHintView(context, Color.YELLOW, Color.WHITE));
            ((RollPagerView)holder.getItemView(R.id.mAbSlidingPlayView)).setAdapter(new MyPagerAdapter());
            ((RollPagerView)holder.getItemView(R.id.mAbSlidingPlayView)).setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(context,""+position,Toast.LENGTH_SHORT).show();
                }
            });
            ((RollPagerView)holder.getItemView(R.id.mAbSlidingPlayView)).pause();

    }
    /**
     * 轮播图的view
     */
    class MyPagerAdapter extends StaticPagerAdapter {

        private int[] image = {R.mipmap.one, R.mipmap.one, R.mipmap.one, R.mipmap.one};


        // SetScaleType(ImageView.ScaleType.CENTER_CROP);
        // 按比例扩大图片的size居中显示，使得图片长(宽)等于或大于View的长(宽)

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView imageView = new ImageView(container.getContext());
            imageView.setImageResource(image[position]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return imageView;
        }

        @Override
        public int getCount() {
            return image.length;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_client_nearby;
    }
}
