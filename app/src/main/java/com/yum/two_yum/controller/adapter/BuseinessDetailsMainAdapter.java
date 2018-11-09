package com.yum.two_yum.controller.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.jude.rollviewpager.hintview.IconHintView;
import com.squareup.picasso.Picasso;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BuseinessDetailsBase;
import com.yum.two_yum.base.BusinessDetailsBase;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.controller.adapter.callback.BusinessDetailsCallBack;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.CircleImageView;
import com.yum.two_yum.utile.imgborwser.helper.UTPreImageViewHelper;
import com.yum.two_yum.view.ReportActivity;
import com.yum.two_yum.view.client.business.BusinessDetailsActivity;
import com.yum.two_yum.view.client.business.DishesDetailsActivity;
import com.yum.two_yum.view.dialog.PromptDialog;
import com.yum.two_yum.view.login.LoginActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author 余先德
 * @data 2018/4/10
 */

public class BuseinessDetailsMainAdapter extends BaseAdapter {

    private int state;
    private boolean menuType = false;
    private int allNum = 0;
    private PopupWindow popupWindow;
    private boolean collectionType = false;

    public interface ItemClic{
        void itemLessClike(BusinessDetailsBase.DataBean.MerchantMenuListRespResultBean.MerchantMenuRespResultsBean data,
                           ImageView lessBtn,int nowNum,int allNum,TextView numTv,ImageView plusBtn);
        void itemPlusClike(BusinessDetailsBase.DataBean.MerchantMenuListRespResultBean.MerchantMenuRespResultsBean data,
                           ImageView plusBtn,int nowNum,int allNum,TextView numTv,ImageView lessBtn);
        void itemLikeClick(boolean isLike,ImageView imageView,String uid,BusinessDetailsBase.DataBean titleData);

        void itemDishesClick(int position);
    }


    private static final int TITLE = 0;
    private static final int LIST = 1;
    private List<BusinessDetailsBase.DataBean.MerchantMenuListRespResultBean.MerchantMenuRespResultsBean> dataList = new ArrayList<>();

    private LayoutInflater inflater;
    private FragmentActivity context;
    private ItemClic callBack;
    private BusinessDetailsBase.DataBean titleData;
    private int like = -1;

    public boolean isCollectionType() {
        return collectionType;
    }

    public void setCollectionType(boolean collectionType,int like) {
        this.collectionType = collectionType;
        this.like = like;
    }

    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }

    public void setCallBack(ItemClic callBack) {
        this.callBack = callBack;
    }

    public void setMenuType(boolean menuType) {
        this.menuType = menuType;
    }

    public BuseinessDetailsMainAdapter(FragmentActivity context, List<BusinessDetailsBase.DataBean.MerchantMenuListRespResultBean.MerchantMenuRespResultsBean> dataList, BusinessDetailsBase.DataBean titleData) {
        this.context = context;
        this.dataList = dataList;
        this.titleData = titleData;
        inflater = LayoutInflater.from(context);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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
            final ImageView iv = titleViewHolder.backBtn;
            titleViewHolder.backBtn.setOnClickListener(new TitleClick(titleViewHolder, titleData.getUserId()));
            titleViewHolder.collectionBtn.setOnClickListener(new TitleClick(titleViewHolder,titleData.getUserId()));
            titleViewHolder.hospitalHeadImg.setOnClickListener(new TitleClick(titleViewHolder,titleData.getUserId()));
            //titleViewHolder.moreBtn.setOnClickListener(new TitleClick(titleViewHolder.moreBtn,titleData.getUserId()));
            titleViewHolder.moreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    View storageHospitalPopup = View.inflate(context, R.layout.popup_more, null);
                    LinearLayout share_btn = (LinearLayout) storageHospitalPopup.findViewById(R.id.share_btn);
                    LinearLayout report_btn = (LinearLayout) storageHospitalPopup.findViewById(R.id.report_btn);

                    PopupWindow popupWindow = new PopupWindow(storageHospitalPopup, YumApplication.getInstance().getView127dp()
                            , YumApplication.getInstance().getViewh106dp());
                    share_btn.setOnClickListener(new popupwindowView(titleData.getUserId(),popupWindow));
                    report_btn.setOnClickListener(new popupwindowView(titleData.getUserId(),popupWindow));
                    popupWindow.setFocusable(true);//popupwindow设置焦点
                    popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//设置背景
                    popupWindow.setOutsideTouchable(true);//点击外面窗口消失
                    int[] location = new int[2];
                    v.getLocationOnScreen(location);
                    popupWindow.showAsDropDown(v);
//                    popupWindow.showAsDropDown(v,0,0);//在v的下面
//                    if (popupWindow.isShowing()){
//                        System.out.println("1212121212121212121212");
//                    }
                }
            });

            titleViewHolder.mAbSlidingPlayView.setHintView(new IconHintView(context, R.mipmap.carousel_one, R.mipmap.carousel_two, 100));
            titleViewHolder.mAbSlidingPlayView.setAdapter(new MyPagerAdapter(titleData.getMerchantMenuCoversRespResults()));
            final ImageView collectionIv = titleViewHolder.collectionBtn;
            titleViewHolder.mAbSlidingPlayView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //Toast.makeText(context,""+position,Toast.LENGTH_SHORT).show();
                if (dataList!=null) {
                   a: for (int con = 0 ; con <dataList.size();con++){
                        if (!TextUtils.isEmpty(dataList.get(con).getId())&&dataList.get(con).getCover().equals(titleData.getMerchantMenuCoversRespResults().get(position).getCover())){
//

                            Intent intent = new Intent(context, DishesDetailsActivity.class);
                            intent.putExtra("data",(Serializable)dataList);
                            intent.putExtra("id",titleData.getMerchantMenuCoversRespResults().get(position).getId());
                            context.startActivity(intent);
                            context.overridePendingTransition(R.anim.activity_open,R.anim.in);
                            break a;
                        }
                    }

                }

            }
        });
            titleViewHolder.mAbSlidingPlayView.isPlaying();
            if (titleData!=null) {
                collectionIv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (callBack!=null){
                                callBack.itemLikeClick(titleData.isLike(),collectionIv,titleData.getUserId(),titleData);
                            }
                        }
                    });
                    if (titleData.isLike()){
                        collectionIv.setImageResource(R.mipmap.heart_red_small);
                    }else{
                        collectionIv.setImageResource(R.mipmap.heart_white_small);
                    }
                titleViewHolder.titleTv.setText(titleData.getName()+(!TextUtils.isEmpty(titleData.getSeries())?","+titleData.getSeries():""));
                //titleViewHolder.seriesTv.setText(titleData.getSeries());
                titleViewHolder.keywordTv.setText(titleData.getKeyword().replaceAll(","," • "));
                Picasso.get().load(titleData.getAvatar()).placeholder(R.mipmap.head_img_being).into(titleViewHolder.hospitalHeadImg);
                //titleViewHolder.keywordTv.setText(titleData.getKeyword());
            }
           // Picasso.get().load(titleData.getCurrencyId())
            if (menuType){
                collectionIv.setVisibility(View.GONE);
                titleViewHolder.moreBtn.setVisibility(View.GONE);
            }else{
                collectionIv.setVisibility(View.VISIBLE);
                titleViewHolder.moreBtn.setVisibility(View.VISIBLE);
            }

        } else if (type == LIST) {
             ListViewHolder listViewHolder = null ;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_business_details, null);
                listViewHolder = new ListViewHolder(convertView);
                convertView.setTag(listViewHolder);
            }else{
                listViewHolder =  (ListViewHolder)convertView.getTag();
            }
            final BusinessDetailsBase.DataBean.MerchantMenuListRespResultBean.MerchantMenuRespResultsBean data = dataList.get(position);
            final TextView numTv = listViewHolder.numTv;
            final ImageView lessBtn = listViewHolder.lessBtn;
            listViewHolder.nameTv.setText(data.getName());
            listViewHolder.contentTv.setText(data.getDescribe());
            Picasso.get().load(data.getCover()).placeholder(R.mipmap.timg).into(listViewHolder.dishesImg);
            listViewHolder.piceTv.setText("$ "+AppUtile.getPrice(data.getPrice()+""));
            numTv.setText(data.getDishNum()+"");
            if (position == dataList.size()-1){
                listViewHolder.viewNull.setVisibility(View.VISIBLE);
            }else{
                listViewHolder.viewNull.setVisibility(View.GONE);
            }
            numTv.setTag(data.getId());
            if (data.getDishNum()==0) {
                numTv.setVisibility(View.GONE);
                lessBtn.setVisibility(View.GONE);
            }else{
                numTv.setVisibility(View.VISIBLE);
                lessBtn.setVisibility(View.VISIBLE);
            }
            final ImageView plusView = listViewHolder.plusBtn;
            final ImageView lessView = listViewHolder.lessBtn;
            if (state==2||state==0){
                listViewHolder. btnsLayout.setVisibility(View.GONE);
            }else{
                listViewHolder. btnsLayout.setVisibility(View.VISIBLE);
                if (data.getNumber() == 0){
                    listViewHolder. btnsLayout.setVisibility(View.GONE);
                    listViewHolder.soldOutTv.setVisibility(View.VISIBLE);
                }else{
                    listViewHolder. btnsLayout.setVisibility(View.VISIBLE);

                    listViewHolder.soldOutTv.setVisibility(View.GONE);
                }

            }
            lessView.setTag(data.getId());
            plusView.setTag(data.getId());
            listViewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (callBack!=null){
//                        callBack.itemDishesClick(position);
//                    }
                    Intent intent = new Intent(context, DishesDetailsActivity.class);
                    intent.putExtra("data",(Serializable)dataList);
                    intent.putExtra("id",data.getId());
                    context.startActivity(intent);
                    context.overridePendingTransition(R.anim.activity_open,R.anim.in);

                }
            });
            lessView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int num = data.getDishNum();
                    num--;
                    allNum --;
                    numTv.setText(num+"");
                    data.setDishNum(num);
                    if (num ==0){
                        numTv.setVisibility(View.GONE);
                        lessBtn.setVisibility(View.GONE);
                    }
                    if (callBack!=null){
                        // callBack.itemLessClike(Integer.valueOf(data.getPrice()),v,data.getNumber(),plusView);
                        callBack.itemLessClike(data,lessView,num,data.getNumber(),numTv,plusView);
                    }
                }
            });

            plusView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int num = data.getDishNum();

                        allNum++;
                        num++;
                        numTv.setText(num + "");
                        data.setDishNum(num);
                        numTv.setVisibility(View.VISIBLE);
                        lessBtn.setVisibility(View.VISIBLE);
                        if (callBack != null) {
                            //     callBack.itemPlusClike(Integer.valueOf(data.getPrice()), v, data.getNumber(),lessView);
                            callBack.itemPlusClike(data,plusView,num,data.getNumber(),numTv,lessView);
                        }
                }
            });

        }
        return convertView;
    }

    private class TitleClick implements View.OnClickListener{
        private  TitleViewHolder titleViewHolder;
        private ImageView moreBtn;
        private String id;
        public TitleClick(TitleViewHolder titleViewHolder,String id){
            this.titleViewHolder = titleViewHolder;
            this.id = id;
        }
        public TitleClick(ImageView titleViewHolder,String id){
            this.moreBtn = titleViewHolder;
            this.id = id;
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.back_btn:
                    if (allNum>0) {
                        Intent intent = new Intent(context, PromptDialog.class);
                        context.startActivityForResult(intent, 0);
                    }else{
                        if (collectionType){
                            Intent intent = new Intent();
                            intent.putExtra("like",like);
                            context.setResult(1003,intent);
                        }
                        context.finish();
                    }
                    //context.finish();
                    break;
                case R.id.more_btn:

                    ((BusinessDetailsActivity)context).showPopupwindow(context,moreBtn,id);
                    break;
                case R.id.hospital_head_img:
                    AppUtile.headClick(context,titleViewHolder.hospitalHeadImg,titleData.getAvatar());
                    /*ImageView mCommentPic;
                    UTPreImageViewHelper helper1 = new UTPreImageViewHelper((Activity) context);
                    helper1.setIndicatorStyle(2);
                    helper1.setSaveTextMargin(0, 0, 0, 5000);
                    mCommentPic = titleViewHolder.hospitalHeadImg;
                    helper1.addImageView(mCommentPic, "1");
                    helper1.startPreActivity(0);*/
                    break;


            }

        }
    }

    private class popupwindowView implements View.OnClickListener{
        String id;
        PopupWindow popupWindow;
        public  popupwindowView(String id,PopupWindow popupWindow){
            this.id = id;
            this.popupWindow = popupWindow;
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.share_btn://分享
                    Intent share_intent = new Intent();

                    share_intent.setAction(Intent.ACTION_SEND);

                    share_intent.setType("text/plain");

                    share_intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.SHARENEW));

                    share_intent.putExtra(Intent.EXTRA_TEXT, "2Yum Food Delivery \n http://www.2yum.app/yum/share");

                    share_intent = Intent.createChooser(share_intent, context.getString(R.string.SHARENEW));

                    context.startActivity(share_intent);
                    if (popupWindow!=null){
                        popupWindow.dismiss();
                    }

                    break;
                case R.id.report_btn://举报
                    if (!TextUtils.isEmpty(YumApplication.getInstance().getMyInformation().getToken())) {
                        Intent intent = new Intent(context, ReportActivity.class);
                        intent.putExtra("userid", id);
                        context.startActivity(intent);
                        context.overridePendingTransition(R.anim.activity_open, R.anim.in);
                    }else{
                        ((BusinessDetailsActivity)context).setCon(0);
                        ((BusinessDetailsActivity)context).setId(id);
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.putExtra("test",true);
                        intent.putExtra("userid", id);
                        intent.putExtra("TAG",ReportActivity.TAG);
                        intent.putExtra("type",true);
                        context.startActivityForResult(intent,0);
                        context.overridePendingTransition(R.anim.activity_open,R.anim.in);
                    }
                    if (popupWindow!=null){
                        popupWindow.dismiss();
                    }
                    break;
            }
        }
    }


    /**
     * 轮播图的view
     */
    class MyPagerAdapter extends StaticPagerAdapter {

        private List<BusinessDetailsBase.DataBean.merchantMenuCoversRespResultsBean> urlList;

        public MyPagerAdapter(List<BusinessDetailsBase.DataBean.merchantMenuCoversRespResultsBean> dataList){
            this.urlList = dataList;
        }

        // SetScaleType(ImageView.ScaleType.CENTER_CROP);
        // 按比例扩大图片的size居中显示，使得图片长(宽)等于或大于View的长(宽)

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView imageView = new ImageView(container.getContext());
            Picasso.get().load(urlList.get(position).getCover()).placeholder(R.mipmap.timg).into(imageView);
           // imageView.setImageResource(image[position]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return imageView;
        }

        @Override
        public int getCount() {
            return urlList.size();
        }
    }

    static class TitleViewHolder {
        @Bind(R.id.mAbSlidingPlayView)
        RollPagerView mAbSlidingPlayView;
        @Bind(R.id.back_btn)
        ImageView backBtn;
        @Bind(R.id.title_tv)
        TextView titleTv;
        @Bind(R.id.keyword_tv)
        TextView keywordTv;
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
        @Bind(R.id.name_tv)
        TextView nameTv;
        @Bind(R.id.less_btn)
        ImageView lessBtn;
        @Bind(R.id.content_tv)
        TextView contentTv;
        @Bind(R.id.num_tv)
        TextView numTv;
        @Bind(R.id.plus_btn)
        ImageView plusBtn;
        @Bind(R.id.pice_tv)
        TextView piceTv;
        @Bind(R.id.item_layout)
        LinearLayout itemLayout;
        @Bind(R.id.btns_layout)
        RelativeLayout btnsLayout;
        @Bind(R.id.view_null)
                View viewNull;
        @Bind(R.id.sold_out_tv)
                TextView soldOutTv;

        ListViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
