package com.yum.two_yum.controller.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BuseinessDetailsBase;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.controller.adapter.callback.BusinessDetailsCallBack;
import com.yum.two_yum.utile.CircleImageView;
import com.yum.two_yum.utile.imgborwser.helper.UTPreImageViewHelper;
import com.yum.two_yum.view.ReportActivity;
import com.yum.two_yum.view.client.business.DishesDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author 余先德
 * @data 2018/4/14
 */

public class PreviewMenuAdapter extends BaseAdapter {
    private static final int TITLE = 0;
    private static final int LIST = 1;
    private List<BuseinessDetailsBase> dataList = new ArrayList<>();

    private LayoutInflater inflater;
    private FragmentActivity context;
    private BusinessDetailsCallBack callBack;

    public void setCallBack(BusinessDetailsCallBack callBack) {
        this.callBack = callBack;
    }

    public PreviewMenuAdapter(FragmentActivity context, List<BuseinessDetailsBase> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    //判断itemView类型
    @Override
    public int getItemViewType(int position) {
        int type = TITLE;
        switch (dataList.get(position).getTypeCon()) {
            case TITLE:
                type = TITLE;

                break;
            case LIST:
                type = LIST;
                break;
        }
        return type;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        if (type == TITLE) {
            TitleViewHolder titleViewHolder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_business_details_title, null);
                titleViewHolder = new TitleViewHolder(convertView);
                convertView.setTag(titleViewHolder);
            } else {
                titleViewHolder = (TitleViewHolder) convertView.getTag();
            }
            titleViewHolder.backBtn.setOnClickListener(new TitleClick(titleViewHolder));

            titleViewHolder.mAbSlidingPlayView.setHintView(new ColorPointHintView(context, Color.YELLOW, Color.WHITE));
            titleViewHolder.mAbSlidingPlayView.setAdapter(new MyPagerAdapter());

            titleViewHolder.mAbSlidingPlayView.isPlaying();

        } else if (type == LIST) {
            ListViewHolder listViewHolder = null ;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_business_details, null);
                listViewHolder = new ListViewHolder(convertView);
                convertView.setTag(listViewHolder);
            }else{
                listViewHolder =  (ListViewHolder)convertView.getTag();
            }
            final BuseinessDetailsBase data = dataList.get(position);
            final TextView numTv = listViewHolder.numTv;
            final ImageView lessBtn = listViewHolder.lessBtn;
            listViewHolder.piceTv.setText("$ "+data.getPrice());
            numTv.setVisibility(View.GONE);
            lessBtn.setVisibility(View.GONE);


        }
        return convertView;
    }

    private class TitleClick implements View.OnClickListener{
        private TitleViewHolder titleViewHolder;
        public TitleClick(TitleViewHolder titleViewHolder){
            this.titleViewHolder = titleViewHolder;
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.back_btn:
                    context.finish();
                    break;

            }

        }
    }

    private class popupwindowView implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.share_btn://分享

                    break;
                case R.id.report_btn://举报
                    Intent intent = new Intent(context, ReportActivity.class);
                    context.startActivity(intent);
                    break;
            }
        }
    }
    /**
     * 显示popupwind
     */
    private void showPopupwindow(Context context, View v){
        View storageHospitalPopup = View.inflate(context, R.layout.popup_more, null);
        LinearLayout share_btn = (LinearLayout) storageHospitalPopup.findViewById(R.id.report_btn);
        LinearLayout report_btn = (LinearLayout) storageHospitalPopup.findViewById(R.id.report_btn);
        share_btn.setOnClickListener(new popupwindowView());
        report_btn.setOnClickListener(new popupwindowView());

        PopupWindow popupWindow = new PopupWindow(storageHospitalPopup, YumApplication.getInstance().getView127dp()
                , YumApplication.getInstance().getViewh106dp());
        popupWindow.setFocusable(true);//popupwindow设置焦点
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//设置背景
        popupWindow.setOutsideTouchable(true);//点击外面窗口消失
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        popupWindow.showAsDropDown(v);//在v的下面


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

    static class TitleViewHolder {
        @Bind(R.id.mAbSlidingPlayView)
        RollPagerView mAbSlidingPlayView;
        @Bind(R.id.back_btn)
        ImageView backBtn;
        @Bind(R.id.collection_btn)
        ImageView collectionBtn;
        @Bind(R.id.more_btn)
        ImageView moreBtn;
        @Bind(R.id.hospital_head_img)
        CircleImageView hospitalHeadImg;

        TitleViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ListViewHolder {
        @Bind(R.id.dishes_img)
        ImageView dishesImg;
        @Bind(R.id.less_btn)
        ImageView lessBtn;
        @Bind(R.id.num_tv)
        TextView numTv;
        @Bind(R.id.plus_btn)
        ImageView plusBtn;
        @Bind(R.id.pice_tv)
        TextView piceTv;
        @Bind(R.id.item_layout)
        LinearLayout itemLayout;

        ListViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
