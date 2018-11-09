package com.yum.two_yum.view.merchants.menu;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.BuseinessDetailsBase;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.controller.adapter.BuseinessDetailsMainAdapter;
import com.yum.two_yum.controller.adapter.CartAdapter;
import com.yum.two_yum.controller.adapter.PreviewMenuAdapter;
import com.yum.two_yum.controller.adapter.callback.BusinessDetailsCallBack;
import com.yum.two_yum.utile.AnimationUtil;
import com.yum.two_yum.utile.FakeAddImageView;
import com.yum.two_yum.utile.PointFTypeEvaluator;
import com.yum.two_yum.utile.pulltorefresh.PullToRefreshListView;
import com.yum.two_yum.view.client.business.SubmitOrdersActivity;
import com.yum.two_yum.view.dialog.PromptDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 余先德
 * @data 2018/4/14
 */

public class PreviewMenuActivity extends BaseActivity {

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

    private PreviewMenuAdapter adapter;
    private List<BuseinessDetailsBase> strings = new ArrayList<>();
    private int dishesNum = 0;
    private int price = 0;
    private int width = 0;
    private CartAdapter cartAdapter;
    private List<String> menuData = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_details);
        ButterKnife.bind(this);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        width = dm.widthPixels;
        setView();
    }

    @Override
    protected void setView() {
        super.setView();
        for (int i = 0; i < 5; i++) {
            BuseinessDetailsBase a = new BuseinessDetailsBase();
            if (i == 0) {
                a.setTypeCon(i);
            } else {
                a.setTypeCon(1);
            }
            a.setPrice("12");
            strings.add(a);
        }
       // cartAdapter = new CartAdapter(menuData);
        cartLv.setAdapter(cartAdapter);
        adapter = new PreviewMenuAdapter(this, strings);
        listview.setAdapter(adapter);

    }

    private void LessPrice(int position) {
        dishesNum--;
        price -= position;
        if (dishesNum == 0) {
            numberDian.setVisibility(View.GONE);
            shoppingCartBtn.setImageResource(R.mipmap.shopping_cart_round_gray);
            selectDishesLayout.setVisibility(View.GONE);
        }
        numberDian.setText(dishesNum + "");
        allPice.setText("$" + price);
    }

    private void showTotalPrice(int position) {
        dishesNum++;
        price += position;
        if (dishesNum == 1) {
            numberDian.setVisibility(View.VISIBLE);
            shoppingCartBtn.setImageResource(R.mipmap.shopping_cart_round_red);
            selectDishesLayout.setVisibility(View.VISIBLE);
        }
        numberDian.setText(dishesNum + "");
        allPice.setText("$" + price);
    }

    public void add(View view, int position) {
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
        animatorSet.setDuration(800);
        animatorSet.start();

        showTotalPrice(position);
    }

    private void changeMenuLayout() {
        int height = YumApplication.getInstance().getDishesItem() * (dishesNum > 5 ? 5 : dishesNum) + YumApplication.getInstance().getDishesTitle();
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) cartLayout.getLayoutParams();
        params.height = height;//设置当前控件布局的高度
        params.width = width;//设置当前控件布局的高度
        cartLayout.setLayoutParams(params);//将设置好的布局参数应用到控件中
        menuData.clear();
        for (int i = 0; i < dishesNum; i++) {
            menuData.add("");
        }
        cartAdapter.notifyDataSetChanged();

    }


}
