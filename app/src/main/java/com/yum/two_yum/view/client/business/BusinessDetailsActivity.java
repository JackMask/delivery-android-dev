package com.yum.two_yum.view.client.business;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.BaseReturn;
import com.yum.two_yum.base.BuseinessDetailsBase;
import com.yum.two_yum.base.BusinessDetailsBase;
import com.yum.two_yum.base.SearchNearbyDataBase;
import com.yum.two_yum.base.input.CreateOrdersInput;
import com.yum.two_yum.base.input.DishInput;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.controller.adapter.BuseinessDetailsMainAdapter;
import com.yum.two_yum.controller.adapter.CartAdapter;
import com.yum.two_yum.controller.adapter.callback.BusinessDetailsCallBack;
import com.yum.two_yum.utile.AnimationUtil;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.FakeAddImageView;
import com.yum.two_yum.utile.PointFTypeEvaluator;
import com.yum.two_yum.utile.Utils;
import com.yum.two_yum.utile.pulltorefresh.PullToRefreshBase;
import com.yum.two_yum.utile.pulltorefresh.PullToRefreshListView;
import com.yum.two_yum.view.ReportActivity;
import com.yum.two_yum.view.client.ClientActivity;
import com.yum.two_yum.view.dialog.PromptDialog;
import com.yum.two_yum.view.login.LoginActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @author 余先德
 * @data 2018/4/10
 */

public class BusinessDetailsActivity extends BaseActivity {


    @Bind(R.id.listview)
    PullToRefreshListView listview;
    @Bind(R.id.delivery_one_tv)
    TextView deliveryOneTv;
    @Bind(R.id.min_one_tv)
    TextView minOneTv;
    @Bind(R.id.all_pice)
    TextView allPice;
    @Bind(R.id.delivery_two_tv)
    TextView deliveryTwoTv;
    @Bind(R.id.min_two_tv)
    TextView minTwoTv;
    @Bind(R.id.continue_btn)
    TextView continueBtn;
    @Bind(R.id.shopping_cart_btn)
    ImageView shoppingCartBtn;
    @Bind(R.id.number_dian)
    TextView numberDian;
    @Bind(R.id.main_layout)
    RelativeLayout mainLayout;
    @Bind(R.id.select_dishes_layout)
    LinearLayout selectDishesLayout;
    @Bind(R.id.shopping_cart_layout)
    RelativeLayout shoppingCartLayout;
    @Bind(R.id.cart_bg)
    View cartBg;
    @Bind(R.id.cart_lv)
    ListView cartLv;
    @Bind(R.id.cart_layout)
    LinearLayout cartLayout;

    private BuseinessDetailsMainAdapter adapter;
    private List<BusinessDetailsBase.DataBean.MerchantMenuListRespResultBean.MerchantMenuRespResultsBean> strings = new ArrayList<>();
    private BusinessDetailsBase.DataBean titleData = new BusinessDetailsBase.DataBean();
    private int dishesNum = 0;
    private double price = 0;
    private float priceMin = 0;
    private int width = 0;
    private CartAdapter cartAdapter;
    private List<DishInput> menuData = new ArrayList<>();
    private int page = 1;
    private int size = 20;
    private int allpage = 1;
    private List<TextView> numViews = new ArrayList<>();
    private List<ImageView> lessViews = new ArrayList<>();
    private CreateOrdersInput createOrdersInput;
    private boolean submit = false;
    private int state = 1;
    private int con = -1;
    private String id;
    private String time;
    private ImageView collentImage;
    private boolean menuType = false;
    private double lat,lng;
    private int likeType = -1;

    public int getCon() {
        return con;
    }

    public void setCon(int con) {
        this.con = con;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static BusinessDetailsActivity instance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_business_details);
        ButterKnife.bind(this);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        width = dm.widthPixels;
        state = getIntent().getIntExtra("type",1);
        menuType = getIntent().getBooleanExtra("menuType",false);
        createOrdersInput = new CreateOrdersInput();
        setView();
        Utils.setStatusTextColor(false,this);
    }

    @Override
    protected void setView() {
        super.setView();

        cartAdapter = new CartAdapter(menuData);
        cartLv.setAdapter(cartAdapter);
        adapter = new BuseinessDetailsMainAdapter(this, strings,titleData);
       // adapter.setState(state);
        adapter.setMenuType(menuType);
        listview.setAdapter(adapter);
        listview.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page=1;
                getData(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    if (allpage>page){
                        page++;
                        getData(false);
                    }else{
                        new Handler().postDelayed(new Runnable(){
                            public void run() {
                                listview.onRefreshComplete();
                            }
                        }, 200);

                    }
            }
        });
        listview.setGoneHead();
        listview.setGoneAll();
        //////////////////////////////////mainadapter/////////////////////////////////////////////////////////
        adapter.setCallBack(new BuseinessDetailsMainAdapter.ItemClic() {
            @Override
            public void itemLessClike(BusinessDetailsBase.DataBean.MerchantMenuListRespResultBean.MerchantMenuRespResultsBean data, ImageView lessBtn, int nowNum, int allNum, TextView numTv, ImageView plusBtn) {
               // add(view, position,num);

                LessPrice(data,nowNum);
            }

            @Override
            public void itemPlusClike(BusinessDetailsBase.DataBean.MerchantMenuListRespResultBean.MerchantMenuRespResultsBean data, ImageView plusBtn, int nowNum, int allNum, TextView numTv, ImageView lessBtn) {
               boolean con = true;
                boolean conLess = true;
                for (ImageView itme:lessViews){
                    if (((String)itme.getTag()).equals(data.getId())){
                        conLess = false;
                        break;
                    }
                }
                for (TextView itme:numViews){
                   if (((String)itme.getTag()).equals(data.getId())){
                       con = false;
                       break;
                   }
               }
                if (conLess){
                    lessViews.add(lessBtn);
                }
               if (con){
                   numViews.add(numTv);
               }
                submit = true;
                add(plusBtn, data.getPrice(),nowNum,data);
            }

            @Override
            public void itemLikeClick(boolean isLike, final ImageView imageView,String userId,final BusinessDetailsBase.DataBean titleData) {
                if (!TextUtils.isEmpty(YumApplication.getInstance().getMyInformation().getToken())) {
                    Map<String, String> params = new HashMap<>();
                    params.put("likeType", isLike ? "CANCEL" : "LIKE");
                    params.put("userId", YumApplication.getInstance().getMyInformation().getUid());
                    params.put("salerUserId", userId);
                    OkHttpUtils
                            .post()
                            .url(Constant.ADD_LIKE)
                            .addHeader("Accept-Language", AppUtile.getLanguage())
                            .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                            .params(params)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int i) {
                                    if (AppUtile.getNetWork(e.getMessage())){
                                        showMsg(getString(R.string.NETERROR));
                                    }
                                }

                                @Override
                                public void onResponse(String s, int i) {
                                    if (AppUtile.setCode(s,BusinessDetailsActivity.this)) {

                                        if (titleData.isLike()) {
                                            titleData.setLike(false);
                                            imageView.setImageResource(R.mipmap.heart_white_big);
                                            likeType = 1;
                                            adapter.setCollectionType(true,1);
                                        } else {
                                            titleData.setLike(true);
                                            imageView.setImageResource(R.mipmap.heart_red_big);
                                            likeType = 0;
                                            adapter.setCollectionType(true,0);
                                        }
                                    }
                                }
                            });
                }else{
                    collentImage = imageView;
                    con = 2;
                    Intent intent = new Intent(BusinessDetailsActivity.this, LoginActivity.class);
                    intent.putExtra("type",true);
                    startActivityForResult(intent,1001);
                    overridePendingTransition(R.anim.activity_open,R.anim.in);
                }

            }

            @Override
            public void itemDishesClick(int position) {

            }
        });
        //////////////////////////////////mainadapter/////////////////////////////////////////////////////////



        //////////////////////////////////dalogadapter/////////////////////////////////////////////////////////

        cartAdapter.setCallBack(new BuseinessDetailsMainAdapter.ItemClic() {
            @Override
            public void itemLessClike(BusinessDetailsBase.DataBean.MerchantMenuListRespResultBean.MerchantMenuRespResultsBean data, ImageView lessBtn, int nowNum, int allNum, TextView numTv, ImageView plusBtn) {
                dishesNum--;
                adapter.setAllNum(adapter.getAllNum()-1);
                price = AppUtile.sub(price,data.getPrice());
                //price -= data.getPrice();


                    for (int i = 0; i < menuData.size(); i++) {
                        if (data.getId().equals(menuData.get(i).getData().getId())) {
                            if (nowNum == 0){
                                for (int j = 0 ; j < strings.size();j++){
                                    if (!TextUtils.isEmpty(strings.get(j).getId())&&strings.get(j).getId().equals(menuData.get(i).getData().getId())) {
                                        strings.get(j).setDishNum(0);
                                    }
                                }
                                menuData.remove(i);
                            }else{
                                for (int j = 0 ; j < strings.size();j++){
                                    if (!TextUtils.isEmpty(strings.get(j).getId())&&strings.get(j).getId().equals(menuData.get(i).getData().getId())){
                                        strings.get(j).setDishNum(nowNum);
                                        menuData.get(i).setDishNum(nowNum);
                                    }
                                }
                            }

                        }
                    }
                adapter.notifyDataSetChanged();
                cartAdapter.notifyDataSetChanged();
                if (menuData.size()==0){
                        cartBg.setVisibility(View.GONE);
                        cartLayout.setVisibility(View.GONE);
                        cartLayout.setAnimation(AnimationUtil.moveToViewLocation1());
                    }
                if (priceMin>price){

                    double s = AppUtile.sub(priceMin,price);
                    minOneTv.setText("+$"+AppUtile.getPrice(s+"")+getString(R.string.MIN));
                    minTwoTv.setText("+$"+AppUtile.getPrice(s+"")+getString(R.string.MIN));
                    continueBtn.setVisibility(View.INVISIBLE);
                }else{
                    continueBtn.setVisibility(View.VISIBLE);

                }
                if (dishesNum == 0) {
                    selectDishesLayout.setVisibility(View.GONE);
                    numberDian.setVisibility(View.GONE);
                    shoppingCartBtn.setImageResource(R.mipmap.shopping_cart_round_gray);
                }
                numberDian.setText(dishesNum + "");
                allPice.setText("$" + AppUtile.getPrice(price+""));
            }

            @Override
            public void itemPlusClike(BusinessDetailsBase.DataBean.MerchantMenuListRespResultBean.MerchantMenuRespResultsBean data, ImageView plusBtn, int nowNum, int allNum, TextView numTv, ImageView lessBtn) {
                for (TextView item:numViews){
                    if (((String)item.getTag()).equals(data.getId())){
                        dishesNum++;
                        price = AppUtile.sum(price,data.getPrice());
                        //price += data.getPrice();

                        for (int i = 0; i < menuData.size(); i++) {
                            if (data.getId().equals(menuData.get(i).getData().getId())) {
                                    for (int j = 0 ; j < strings.size();j++){
                                        if (!TextUtils.isEmpty(strings.get(j).getId())&&strings.get(j).getId().equals(menuData.get(i).getData().getId())){
                                            strings.get(j).setDishNum(nowNum);
                                            menuData.get(i).setDishNum(nowNum);
                                        }
                                    }
                            }
                        }
                        adapter.notifyDataSetChanged();


                        if (priceMin>price){
                            double s = AppUtile.sub(priceMin,price);
                            minOneTv.setText("+$"+AppUtile.getPrice(s+"")+getString(R.string.MIN));
                            minTwoTv.setText("+$"+AppUtile.getPrice(s+"")+getString(R.string.MIN));
                            continueBtn.setVisibility(View.INVISIBLE);
                        }else{
                            continueBtn.setVisibility(View.VISIBLE);
                            //numberDian.setVisibility(View.VISIBLE);

                        }

                        if (dishesNum == 1) {
                            selectDishesLayout.setVisibility(View.VISIBLE);
                            numberDian.setVisibility(View.VISIBLE);
                            shoppingCartBtn.setImageResource(R.mipmap.shopping_cart_round_red);
                            // selectDishesLayout.setVisibility(View.VISIBLE);
                        }
                        numberDian.setText(dishesNum + "");
                        allPice.setText("$" + AppUtile.getPrice(price+""));
                        item.setText(nowNum+"");
                    }
                }
            }

            @Override
            public void itemLikeClick(boolean isLike, final ImageView imageView, String uid, final BusinessDetailsBase.DataBean titleData) {
                if (!TextUtils.isEmpty(YumApplication.getInstance().getMyInformation().getToken())) {
                    Map<String, String> params = new HashMap<>();
                    params.put("likeType", isLike ? "CANCEL" : "LIKE");
                    params.put("userId", YumApplication.getInstance().getMyInformation().getUid());
                    params.put("salerUserId", uid + "");
                    OkHttpUtils
                            .post()
                            .url(Constant.ADD_LIKE)
                            .addHeader("Accept-Language", AppUtile.getLanguage())
                            .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                            .params(params)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int i) {
                                    if (AppUtile.getNetWork(e.getMessage())){
                                        showMsg(getString(R.string.NETERROR));
                                    }
                                }

                                @Override
                                public void onResponse(String s, int i) {
                                    if (AppUtile.setCode(s,BusinessDetailsActivity.this)) {
                                        if (titleData.isLike()) {
                                            imageView.setImageResource(R.mipmap.heart_white_big);
                                            titleData.setLike(false);
                                            likeType = 1;
                                            adapter.setCollectionType(true,1);
                                        } else {
                                            imageView.setImageResource(R.mipmap.heart_red_big);
                                            titleData.setLike(true);
                                            likeType = 0;
                                            adapter.setCollectionType(true,0);
                                            BaseReturn data = JSON.parseObject(s, BaseReturn.class);
                                            showMsg(data.getMsg());
                                        }
                                    }
                                }
                            });
                }else{
                    con = 2;
                    Intent intent = new Intent(BusinessDetailsActivity.this, LoginActivity.class);
                    intent.putExtra("type",true);
                    startActivityForResult(intent,0);
                    overridePendingTransition(R.anim.activity_open,R.anim.in);
                }
            }

            @Override
            public void itemDishesClick(int position) {

            }


        });
        //////////////////////////////////dalogadapter/////////////////////////////////////////////////////////
        getData(false);
    }
    /**
     * 显示popupwind
     */
    public void showPopupwindow(Context context, View v, String id){
        View storageHospitalPopup = View.inflate(context, R.layout.popup_more, null);
        LinearLayout share_btn = (LinearLayout) storageHospitalPopup.findViewById(R.id.report_btn);
        LinearLayout report_btn = (LinearLayout) storageHospitalPopup.findViewById(R.id.report_btn);
        share_btn.setOnClickListener(new popupwindowView(id));
        report_btn.setOnClickListener(new popupwindowView(id));

        PopupWindow popupWindow = new PopupWindow(storageHospitalPopup, YumApplication.getInstance().getView127dp()
                , YumApplication.getInstance().getViewh106dp());
        popupWindow.setFocusable(true);//popupwindow设置焦点
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//设置背景
        popupWindow.setOutsideTouchable(true);//点击外面窗口消失
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        popupWindow.showAsDropDown(v,0,0);//在v的下面
        if (popupWindow.isShowing()){
            System.out.println("1212121212121212121212");
        }
    }
    private class popupwindowView implements View.OnClickListener{
        String id;
        public  popupwindowView(String id){
            this.id = id;
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.share_btn://分享
                    Intent share_intent = new Intent();

                    share_intent.setAction(Intent.ACTION_SEND);

                    share_intent.setType("text/plain");

                    share_intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.SHARENEW));

                    share_intent.putExtra(Intent.EXTRA_TEXT, "2Yum Food Delivery \n http://www.2yum.app/yum/share");

                    share_intent = Intent.createChooser(share_intent, getString(R.string.SHARENEW));

                    startActivity(share_intent);

                    break;
                case R.id.report_btn://举报
                    if (!TextUtils.isEmpty(YumApplication.getInstance().getMyInformation().getToken())) {
                        Intent intent = new Intent(BusinessDetailsActivity.this, ReportActivity.class);
                        intent.putExtra("userid", id);
                        startActivity(intent);
                    }else{
                        con = 0;
                        setCon(0);
                        setId(id);
                        Intent intent = new Intent(BusinessDetailsActivity.this, LoginActivity.class);
                        intent.putExtra("type",true);
                        startActivityForResult(intent,1001);
                        overridePendingTransition(R.anim.activity_open,R.anim.in);
                    }
                    break;
            }
        }
    }
    private void LessPrice(BusinessDetailsBase.DataBean.MerchantMenuListRespResultBean.MerchantMenuRespResultsBean data,int num) {

        dishesNum--;
        price = AppUtile.sub(price,data.getPrice());
        for (int i = 0 ; i < menuData.size();i++){
            if (menuData.get(i).getData().getId().equals(data.getId())){
                if (num == 0){
                    menuData.remove(i);
                }else {
                    menuData.get(i).setDishNum(num);
                }
            }
        }
        for (DishInput item : menuData){
            if (item.getData().getId().equals(data.getId())){

                item.setDishNum(num);
            }
        }
        if (priceMin>price){
           // selectDishesLayout.setVisibility(View.GONE);
            double s = AppUtile.sub(priceMin,price);
            minOneTv.setText("+$"+AppUtile.getPrice(s+"")+getString(R.string.MIN));
            minTwoTv.setText("+$"+AppUtile.getPrice(s+"")+getString(R.string.MIN));
            continueBtn.setVisibility(View.INVISIBLE);
        }else{
            continueBtn.setVisibility(View.VISIBLE);

        }
        if (dishesNum == 0) {
            for (int i = 0 ; i < menuData.size();i++){
                if (data.getId().equals(menuData.get(i).getData().getId())){
                    menuData.remove(i);
                    break;
                }
            }
            selectDishesLayout.setVisibility(View.GONE);
            numberDian.setVisibility(View.GONE);
            shoppingCartBtn.setImageResource(R.mipmap.shopping_cart_round_gray);

        }
        numberDian.setText(dishesNum + "");
        allPice.setText("$" + AppUtile.getPrice(price+""));
        //changeMenuLayout();
    }

    private void showTotalPrice(final BusinessDetailsBase.DataBean.MerchantMenuListRespResultBean.MerchantMenuRespResultsBean data, final int num) {
        new Handler().postDelayed(new Runnable(){
            public void run() {
                boolean con = true;
                for (DishInput item :menuData){
                    if (data.getId().equals(item.getData().getId())){
                        con =false;
                        break;
                    }
                }
                if (con) {
                    DishInput item = new DishInput();
                    item.setData(data);
                    item.setDishNum(num);
                    menuData.add(item);
                }else{
                    for (DishInput item :menuData){
                        if (data.getId().equals(item.getData().getId())){
                            item.setDishNum(num);
                            break;
                        }
                    }
                }
                cartAdapter.notifyDataSetChanged();
                dishesNum++;
                price = AppUtile.sum(price,data.getPrice());
                if (priceMin>price){
                    double s = AppUtile.sub(priceMin,price);
                    minOneTv.setText("+$"+AppUtile.getPrice(s+"")+getString(R.string.MIN));
                    minTwoTv.setText("+$"+AppUtile.getPrice(s+"")+getString(R.string.MIN));
                    continueBtn.setVisibility(View.INVISIBLE);
                }else{
                    continueBtn.setVisibility(View.VISIBLE);
                    //numberDian.setVisibility(View.VISIBLE);

                }

                if (dishesNum == 1) {
                    selectDishesLayout.setVisibility(View.VISIBLE);
                    numberDian.setVisibility(View.VISIBLE);
                    shoppingCartBtn.setImageResource(R.mipmap.shopping_cart_round_red);
                   // selectDishesLayout.setVisibility(View.VISIBLE);
                }
                numberDian.setText(dishesNum + "");
                allPice.setText("$" + AppUtile.getPrice(price+""));
            }
        }, 400);

    }

    public void add(View view, double position,int num,BusinessDetailsBase.DataBean.MerchantMenuListRespResultBean.MerchantMenuRespResultsBean data) {
        int[] addLocation = new int[2];
        int[] cartLocation = new int[2];
        int[] recycleLocation = new int[2];
        view.getLocationInWindow(addLocation);
        shoppingCartBtn.getLocationInWindow(cartLocation);
        listview.getLocationInWindow(recycleLocation);

        PointF startP = new PointF();
        PointF endP = new PointF();
        PointF controlP = new PointF();

        startP.x = addLocation[0];
        startP.y = addLocation[1] - recycleLocation[1];
        endP.x = cartLocation[0];
        endP.y = cartLocation[1] - recycleLocation[1];
        controlP.x = endP.x;
        controlP.y = startP.y;

        final FakeAddImageView fakeAddImageView = new FakeAddImageView(this);
        mainLayout.addView(fakeAddImageView);
        fakeAddImageView.setImageResource(R.mipmap.ring_plus);
        fakeAddImageView.getLayoutParams().width = getResources().getDimensionPixelSize(R.dimen.item_dish_circle_size);
        fakeAddImageView.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.item_dish_circle_size);
        fakeAddImageView.setVisibility(View.VISIBLE);
        ObjectAnimator addAnimator = ObjectAnimator.ofObject(fakeAddImageView, "mPointF",
                new PointFTypeEvaluator(controlP), startP, endP);
        addAnimator.setInterpolator(new AccelerateInterpolator());
        addAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                fakeAddImageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                fakeAddImageView.setVisibility(View.GONE);
                mainLayout.removeView(fakeAddImageView);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        ObjectAnimator scaleAnimatorX = new ObjectAnimator().ofFloat(shoppingCartBtn, "scaleX", 0.6f, 1.0f);
        ObjectAnimator scaleAnimatorY = new ObjectAnimator().ofFloat(shoppingCartBtn, "scaleY", 0.6f, 1.0f);
        scaleAnimatorX.setInterpolator(new AccelerateInterpolator());
        scaleAnimatorY.setInterpolator(new AccelerateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleAnimatorX).with(scaleAnimatorY).after(addAnimator);
        animatorSet.setDuration(400);
        animatorSet.start();

        showTotalPrice(data,num);
    }


    @OnClick({R.id.continue_btn, R.id.shopping_cart_layout,R.id.clear_cart,R.id.cart_bg})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.continue_btn:
                if (!TextUtils.isEmpty(YumApplication.getInstance().getMyInformation().getToken())) {
                    if (menuData.size() > 0) {
                        List<CreateOrdersInput.MenuReqListBean> dishList = new ArrayList<>();
                        for (DishInput item : menuData) {
                            CreateOrdersInput.MenuReqListBean data = new CreateOrdersInput.MenuReqListBean();
                            data.setMerchantMenuId(item.getData().getId());
                            data.setMerchantMenuName(item.getData().getName());
                            data.setNumber(item.getDishNum());
                            data.setPrice(item.getData().getPrice());
                            dishList.add(data);
                        }
                        createOrdersInput.setMenuReqList(dishList);
                        createOrdersInput.setState(state);
                        createOrdersInput.setLike(menuType);
                        intent = new Intent(this, SubmitOrdersActivity.class);
                        SearchNearbyDataBase data = (SearchNearbyDataBase)getIntent().getSerializableExtra("data");
                        if (data!=null&&AppUtile.GetDistance(Double.valueOf(data.getLat()),Double.valueOf(data.getLng()),lat,lng)/1609.344<=5) {
                            intent.putExtra("addressData", getIntent().getSerializableExtra("data"));
                        }

                        intent.putExtra("time",time);
                        intent.putExtra("data", createOrdersInput);
                        startActivityForResult(intent, 0);
                    }
                }else{
                    con = 1;
                     intent = new Intent(this, LoginActivity.class);
                    if (menuData.size() > 0) {
                        List<CreateOrdersInput.MenuReqListBean> dishList = new ArrayList<>();
                        for (DishInput item : menuData) {
                            CreateOrdersInput.MenuReqListBean data = new CreateOrdersInput.MenuReqListBean();
                            data.setMerchantMenuId(item.getData().getId());
                            data.setMerchantMenuName(item.getData().getName());
                            data.setNumber(item.getDishNum());
                            data.setPrice(item.getData().getPrice());
                            dishList.add(data);
                        }
                        createOrdersInput.setMenuReqList(dishList);
                        createOrdersInput.setState(state);
                        createOrdersInput.setLike(menuType);
                    }
                    SearchNearbyDataBase data = (SearchNearbyDataBase)getIntent().getSerializableExtra("data");
                    if (data!=null&&AppUtile.GetDistance(Double.valueOf(data.getLat()),Double.valueOf(data.getLng()),lat,lng)/1609.344<=5) {
                        intent.putExtra("addressData", getIntent().getSerializableExtra("data"));
                    }
                    intent.putExtra("lat",lat);
                    intent.putExtra("lng",lng);
                    intent.putExtra("time",time);
                    intent.putExtra("data", createOrdersInput);
                    intent.putExtra("test",true);
                    intent.putExtra("TAG",SubmitOrdersActivity.TAG);
                    intent.putExtra("type",true);
                    startActivityForResult(intent,1001);
                    overridePendingTransition(R.anim.activity_open,R.anim.in);
                }

                break;
            case R.id.shopping_cart_layout:
                if (menuData.size()>0) {
                    //changeMenuLayout();
                    if (cartBg.getVisibility() == View.VISIBLE) {
                        cartBg.setVisibility(View.GONE);
                        cartLayout.setVisibility(View.GONE);
                        cartLayout.setAnimation(AnimationUtil.moveToViewLocation1());
                    } else {
                        cartBg.setVisibility(View.VISIBLE);
                        cartLayout.setVisibility(View.VISIBLE);
                        cartLayout.setAnimation(AnimationUtil.moveToViewBottom1());
                    }
                }
                break;
            case R.id.clear_cart://清空购物车
//                intent = new Intent(BusinessDetailsActivity.this, PromptDialog.class);
//                startActivityForResult(intent, 1);

                menuData.clear();
                cartAdapter.notifyDataSetChanged();
                for (BusinessDetailsBase.DataBean.MerchantMenuListRespResultBean.MerchantMenuRespResultsBean item : strings) {
                    item.setDishNum(0);
                }

                adapter.setAllNum(0);
                continueBtn.setVisibility(View.INVISIBLE);
                selectDishesLayout.setVisibility(View.GONE);
                //changeMenuLayout();
                for (TextView textView : numViews) {
                    textView.setText("");
                    textView.setVisibility(View.GONE);
                }
                for (ImageView imageView : lessViews) {
                    imageView.setVisibility(View.GONE);
                }
                price = 0;
                dishesNum = 0;
                minOneTv.setText("$" + priceMin + getString(R.string.MIN));
                minTwoTv.setText("$" + priceMin + getString(R.string.MIN));
                numberDian.setVisibility(View.GONE);
                shoppingCartBtn.setImageResource(R.mipmap.shopping_cart_round_gray);
                cartBg.setVisibility(View.GONE);
                cartLayout.setVisibility(View.GONE);
                cartLayout.setAnimation(AnimationUtil.moveToViewLocation1());
               break;
            case R.id.cart_bg:
                cartBg.setVisibility(View.GONE);
                cartLayout.setVisibility(View.GONE);
                cartLayout.setAnimation(AnimationUtil.moveToViewLocation1());
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == 1001) {
                menuData.clear();
                cartAdapter.notifyDataSetChanged();
                for (BusinessDetailsBase.DataBean.MerchantMenuListRespResultBean.MerchantMenuRespResultsBean item : strings) {
                    item.setDishNum(0);
                }

                adapter.setAllNum(0);
                continueBtn.setVisibility(View.INVISIBLE);
                selectDishesLayout.setVisibility(View.GONE);
                //changeMenuLayout();
                for (TextView textView : numViews) {
                    textView.setText("");
                    textView.setVisibility(View.GONE);
                }
                for (ImageView imageView : lessViews) {
                    imageView.setVisibility(View.GONE);
                }
                price = 0;
                dishesNum = 0;
                minOneTv.setText("$" + priceMin + getString(R.string.MIN));
                minTwoTv.setText("$" + priceMin + getString(R.string.MIN));
                numberDian.setVisibility(View.GONE);
                shoppingCartBtn.setImageResource(R.mipmap.shopping_cart_round_gray);
                cartBg.setVisibility(View.GONE);
                cartLayout.setVisibility(View.GONE);
                cartLayout.setAnimation(AnimationUtil.moveToViewLocation1());
            }
        }else  if (requestCode == 0) {
            if (resultCode == 1001){
                if (likeType !=-1){
                    Intent intent = new Intent();
                    intent.putExtra("like",likeType);
                    setResult(1003,intent);
                }
                finish();
            }else if (resultCode == RESULT_OK) {
                if (data!=null){
                    Intent intent1 = new Intent();
                    intent1.putExtra("id",data.getStringExtra("id"));
                    setResult(1001,intent1);
                    finish();
                }

            }else if (resultCode == 3){
                Intent intent = new Intent(this, ReportActivity.class);
                intent.putExtra("userid", id);
                startActivity(intent);
            }
        }else if (resultCode == 3){
            if (con == 0) {
                Intent intent = new Intent(this, ReportActivity.class);
                intent.putExtra("userid", id);
                startActivity(intent);
            }else if (con ==1){

                if (menuData.size() > 0) {
                    List<CreateOrdersInput.MenuReqListBean> dishList = new ArrayList<>();
                    for (DishInput item : menuData) {
                        CreateOrdersInput.MenuReqListBean dataMenu = new CreateOrdersInput.MenuReqListBean();
                        dataMenu.setMerchantMenuId(item.getData().getId());
                        dataMenu.setMerchantMenuName(item.getData().getName());
                        dataMenu.setNumber(item.getDishNum());
                        dataMenu.setPrice(item.getData().getPrice() * item.getDishNum());
                        dishList.add(dataMenu);
                    }
                    createOrdersInput.setMenuReqList(dishList);
                    Intent intent = new Intent(this, SubmitOrdersActivity.class);
                    intent.putExtra("time",time);
                    intent.putExtra("data", createOrdersInput);
                    startActivityForResult(intent, 0);
                }
            }else if (con == 2){

                Map<String, String> params = new HashMap<>();
                params.put("likeType", titleData.isLike() ? "CANCEL" : "LIKE");
                params.put("userId", YumApplication.getInstance().getMyInformation().getUid());
                params.put("salerUserId", titleData.getUserId() + "");
                OkHttpUtils
                        .post()
                        .url(Constant.ADD_LIKE)
                        .addHeader("Accept-Language", AppUtile.getLanguage())
                        .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                        .params(params)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int i) {
                                if (AppUtile.getNetWork(e.getMessage())){
                                    showMsg(getString(R.string.NETERROR));
                                }
                            }

                            @Override
                            public void onResponse(String s, int i) {
                                if (AppUtile.setCode(s,BusinessDetailsActivity.this)) {
                                    if (titleData.isLike()) {
                                        likeType = 1;
                                        collentImage.setImageResource(R.mipmap.heart_white_big);
                                        titleData.setLike(false);
                                        adapter.setCollectionType(true,1);
                                    } else {
                                        likeType = 0;
                                        collentImage.setImageResource(R.mipmap.heart_red_big);
                                        titleData.setLike(true);
                                        adapter.setCollectionType(true,0);
                                    }
                                }
                            }
                        });
            }
        }else if (requestCode != 1001){
            setResult(1001,data);
            finish();
        }
    }

    private void getData(boolean jumpType){
        Map<String,String> map = new HashMap<>();
        map.put("userId",getIntent().getStringExtra("id"));
        map.put("pageIndex",page+"");
        map.put("pageSize",size+"");

        OkHttpUtils
                .get()
                .url(Constant.MERCHANT_HOME)
                .addHeader("Accept-Language", AppUtile.getLanguage())
                .addHeader("YUM-AUTH-TOKEN",YumApplication.getInstance().getMyInformation().getToken())
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        listview.onRefreshComplete();
                        if (AppUtile.getNetWork(e.getMessage())){
                            showMsg(getString(R.string.NETERROR));
                        }
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        listview.onRefreshComplete();
                        if (!TextUtils.isEmpty(s)) {

                            if (AppUtile.setCode(s,BusinessDetailsActivity.this)){
                                BusinessDetailsBase data = JSON.parseObject(s, BusinessDetailsBase.class);
                                time = data.getData().getTime();
                                if (page == 1) {
                                    allpage = data.getData().getMerchantMenuListRespResult().getPageCount();
                                    strings.clear();
                                    titleData.setName(data.getData().getName());
                                    titleData.setUserId(data.getData().getUserId());
                                    titleData.setSeries(data.getData().getSeries());
                                    titleData.setKeyword(data.getData().getKeyword());
                                    titleData.setAvatar(Constant.SHOW_IMG+data.getData().getAvatar());
                                    titleData.setLike(getIntent().getBooleanExtra("like",false));
                                    minOneTv.setText("$"+data.getData().getMoney()+getString(R.string.MIN));
                                    minTwoTv.setText("$"+data.getData().getMoney()+getString(R.string.MIN));
                                    priceMin = data.getData().getMoney();
                                    if (!TextUtils.isEmpty(data.getData().getLat())&&!TextUtils.isEmpty(data.getData().getLng())) {
                                        lat = Double.valueOf(data.getData().getLat());
                                        lng = Double.valueOf(data.getData().getLng());
                                    }
                                    deliveryOneTv.setText(getString(R.string.DELIVERYCOST)+"$"+ AppUtile.getPrice(data.getData().getDeliveryMoney()+""));
                                    deliveryTwoTv.setText(getString(R.string.DELIVERYCOST)+"$"+AppUtile.getPrice(data.getData().getDeliveryMoney()+""));
                                    titleData.setMerchantMenuCoversRespResults(data.getData().getMerchantMenuCoversRespResults());
                                    //titleData = data.getData();
                                    BusinessDetailsBase.DataBean.MerchantMenuListRespResultBean.MerchantMenuRespResultsBean titleData = new BusinessDetailsBase.DataBean.MerchantMenuListRespResultBean.MerchantMenuRespResultsBean();
                                    createOrdersInput.setBuyerUserId(YumApplication.getInstance().getMyInformation().getUid());
                                    createOrdersInput.setSalerUserId(data.getData().getUserId());
                                    createOrdersInput.setDeliveryMoney(data.getData().getDeliveryMoney()+"");
                                    createOrdersInput.setTaxRate(data.getData().getTaxRate());
                                    createOrdersInput.setRange(data.getData().getRange()+"");
                                    createOrdersInput.setLat(data.getData().getLat());
                                    createOrdersInput.setTaxRate(data.getData().getTaxRate());
                                    createOrdersInput.setLng(data.getData().getLng());
                                    createOrdersInput.setName(data.getData().getName());
                                    createOrdersInput.setHeadUrl(data.getData().getAvatar());
                                    adapter.setState(data.getData().getState());
                                    //createOrdersInput.set();
                                    titleData.setTypeCon(0);
                                    List<String> url = new ArrayList<>();
                                    String coverStr  = "";
                                    if (data.getData().getMerchantMenuListRespResult() != null && data.getData().getMerchantMenuListRespResult().getMerchantMenuRespResults() != null) {
                                        for (int c =0 ; c < data.getData().getMerchantMenuListRespResult().getMerchantMenuRespResults().size();c++){
                                            coverStr+=data.getData().getMerchantMenuListRespResult().getMerchantMenuRespResults().get(c).getCover()+",";
                                            url.add(c,data.getData().getMerchantMenuListRespResult().getMerchantMenuRespResults().get(c).getCover());
                                        }
//                                        for (BusinessDetailsBase.DataBean.MerchantMenuListRespResultBean.MerchantMenuRespResultsBean item : data.getData().getMerchantMenuListRespResult().getMerchantMenuRespResults()) {
//                                            url.add(item.getCover());
//                                        }
                                    }
                                    if (!TextUtils.isEmpty(coverStr)&&coverStr.length()>0)
                                        titleData.setCover(coverStr.substring(0, coverStr.length() - 1));
                                    //titleData.setCover(url.toString().substring(1, url.toString().length() - 1).replace(" ", ""));
                                    strings.add(titleData);
                                }
                                if (data.getData().getMerchantMenuListRespResult() != null && data.getData().getMerchantMenuListRespResult().getMerchantMenuRespResults() != null) {
                                    for (BusinessDetailsBase.DataBean.MerchantMenuListRespResultBean.MerchantMenuRespResultsBean item : data.getData().getMerchantMenuListRespResult().getMerchantMenuRespResults()) {
                                       item.setTypeCon(1);
                                        strings.add(item);
                                    }
                                }
                                if (data.getData().getAuditState() !=2 ){
                                    adapter.setState(2);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

    }

}
