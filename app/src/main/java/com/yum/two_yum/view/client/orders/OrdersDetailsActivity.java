package com.yum.two_yum.view.client.orders;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.squareup.picasso.Picasso;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.OrderLogBase;
import com.yum.two_yum.base.OrdersDetailsBase;
import com.yum.two_yum.base.input.CancelOrderBase;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.controller.adapter.NumberDianAdapter;
import com.yum.two_yum.controller.adapter.OrdersDetailsAdapter;
import com.yum.two_yum.utile.AnimationUtil;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.CircleImageView;
import com.yum.two_yum.utile.view.NoSlideListView;
import com.yum.two_yum.view.dialog.PromptDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * @author 余先德
 * @data 2018/4/11
 */

public class OrdersDetailsActivity extends BaseActivity {

    @Bind(R.id.status_title_tv)
    TextView statusTitleTv;
    @Bind(R.id.cancel_btn)
    TextView cancelBtn;
    @Bind(R.id.processing_iv)
    ImageView processingIv;
    @Bind(R.id.in_delivery_iv)
    ImageView inDeliveryIv;
    @Bind(R.id.paid_iv)
    ImageView paidIv;
    @Bind(R.id.cancelled_iv)
    ImageView cancelledIv;
    @Bind(R.id.status_prompt_tv)
    TextView statusPromptTv;
    @Bind(R.id.content_lv)
    NoSlideListView contentLv;
    @Bind(R.id.icon_file)
    ImageView iconFile;
    @Bind(R.id.order_num_tv)
    TextView orderNumTv;
    @Bind(R.id.time_tv)
    TextView timeTv;
    @Bind(R.id.send_time_tv)
    TextView sendTimeTv;
    @Bind(R.id.card_num_tv)
    TextView cardNumTv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.phone_tv)
    TextView phoneTv;
    @Bind(R.id.address_tv)
    TextView addressTv;
    @Bind(R.id.ps_tv)
    TextView psTv;
    @Bind(R.id.cart_bg)
    View cartBg;
    @Bind(R.id.number_dian)
    ListView numberDian;
    @Bind(R.id.delivery_money_tv)
    TextView delivery_money_tv;
    @Bind(R.id.tax_rate_tv)
    TextView tax_rate_tv;
    @Bind(R.id.total_tv)
    TextView total_tv;
    @Bind(R.id.restaurant_name_tv)
    TextView restaurant_name_tv;
    @Bind(R.id.head_img)
    CircleImageView headImg;

    @Bind(R.id.cart_layout)
    LinearLayout cartLayout;

    @Bind(R.id.type_time_1)
    TextView typeTime1;

    @Bind(R.id.type_time_2)
    TextView typeTime2;

    @Bind(R.id.type_time_3)
    TextView typeTime3;

    @Bind(R.id.type_time_4)
    TextView typeTime4;

    @Bind(R.id.type_time_5)
    TextView typeTime5;
    @Bind(R.id.type_tv_1)
    TextView typeTv1;
    @Bind(R.id.type_tv_2)
    TextView typeTv2;
    @Bind(R.id.type_tv_3)
    TextView typeTv3;
    @Bind(R.id.type_tv_4)
    TextView typeTv4;
    @Bind(R.id.type_tv_5)
    TextView typeTv5;
    @Bind(R.id.type_layout_1)
    LinearLayout typeLayout1;
    @Bind(R.id.type_layout_2)
    LinearLayout typeLayout2;
    @Bind(R.id.type_layout_3)
    LinearLayout typeLayout3;
    @Bind(R.id.type_layout_4)
    LinearLayout typeLayout4;
    @Bind(R.id.type_layout_5)
    LinearLayout typeLayout5;

    private int styleCon = 0;// 0 支付成功 1 进行中 2 配送中 3 已交付 4 已取消
    private NumberDianAdapter dianAdapter;
    private List<Integer> numberList = new ArrayList<>();
    private OrdersDetailsAdapter adapter;
    private List<OrdersDetailsBase.DataBean.OrderDetailRespResultsBean> orderDataList = new ArrayList<>();
    private String OrderId;
    private String merchantPhone;

    private List<TextView> typeTexts = new ArrayList<>();
    private List<TextView> typeTimes = new ArrayList<>();
    private List<LinearLayout> typeLayout = new ArrayList<>();
    private List<View> types = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_details);
        YumApplication.getInstance().setOrdersDetailsActivity(this);
        ButterKnife.bind(this);
        OrderId = getIntent().getStringExtra("id");
        setView();
    }

    @Override
    protected void setView() {
        super.setView();
        typeTexts.add(typeTv1);
        typeTexts.add(typeTv2);
        typeTexts.add(typeTv3);
        typeTexts.add(typeTv4);
        typeTexts.add(typeTv5);
        typeTimes.add(typeTime1);
        typeTimes.add(typeTime2);
        typeTimes.add(typeTime3);
        typeTimes.add(typeTime4);
        typeTimes.add(typeTime5);
        typeLayout.add(typeLayout1);
        typeLayout.add(typeLayout2);
        typeLayout.add(typeLayout3);
        typeLayout.add(typeLayout4);
        typeLayout.add(typeLayout5);
        types.add(cancelBtn);
        types.add(processingIv);
        types.add(inDeliveryIv);
        types.add(paidIv);
        types.add(cancelledIv);


        dianAdapter = new NumberDianAdapter(numberList);
        numberDian.setAdapter(dianAdapter);

        adapter = new OrdersDetailsAdapter(orderDataList);
        contentLv.setAdapter(adapter);
        getData();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1001) {
            CancelOrderBase inputData = new CancelOrderBase();
            inputData.setCancelNote("客户取消");
            inputData.setOrderId(OrderId);
            inputData.setOrderStatus("CANCEL");
            String content = JSON.toJSONString(inputData);
            OkHttpUtils
                    .postString()
                    .url(Constant.MERCHANT_ORDER_SETUP)
                    .addHeader("Accept-Language", AppUtile.getLanguage())
                    .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                    .mediaType(MediaType.parse("application/json"))
                    .content(content)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int i) {
                            if (AppUtile.getNetWork(e.getMessage())) {
                                showMsg(getString(R.string.NETERROR));
                            }
                        }

                        @Override
                        public void onResponse(String s, int i) {
                            if (!TextUtils.isEmpty(s)) {
                                if (AppUtile.setCode(s, OrdersDetailsActivity.this)) {
                                    getData();
                                }
                            }

                        }
                    });
        }
    }

    private void getData() {
        OkHttpUtils
                .get()
                .url(Constant.ORDER_BUYER_INFO)
                .addHeader("Accept-Language", AppUtile.getLanguage())
                .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                .addParams("userId", YumApplication.getInstance().getMyInformation().getUid())
                .addParams("orderId", OrderId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        if (AppUtile.getNetWork(e.getMessage())) {
                            showMsg(getString(R.string.NETERROR));
                        }
                    }

                    @Override
                    public void onResponse(String s, int m) {
                        if (!TextUtils.isEmpty(s)) {

                            if (AppUtile.setCode(s, OrdersDetailsActivity.this)) {
                                OrdersDetailsBase data = JSON.parseObject(s, OrdersDetailsBase.class);
                                for (View item : types) {
                                    item.setVisibility(View.GONE);
                                }
                                switch (data.getData().getStatus()) {
                                    case 0://取消
                                        statusTitleTv.setText(getString(R.string.THENCANCELED));
                                        cancelledIv.setVisibility(View.VISIBLE);
                                        statusPromptTv.setText(data.getData().getCancelNote());
                                        break;
                                    case 1://支付成功
                                        statusTitleTv.setText(getString(R.string.PAYMENTSUCCESSFUL));
                                        cancelBtn.setVisibility(View.VISIBLE);
                                        statusPromptTv.setText(getString(R.string.MINSUNANSWEREDAUTOMATICALLYCANCELPLEASEREORDER));
                                        break;
                                    case 2://进行中
                                        statusTitleTv.setText(getString(R.string.PREPARING));
                                        processingIv.setVisibility(View.VISIBLE);
                                        statusPromptTv.setText(getString(R.string.ORDERSINPRODUCTIONCANNOTBECANCELED));
                                        break;
                                    case 3://配送中
                                        statusTitleTv.setText(getString(R.string.DELIVERYNOW));
                                        inDeliveryIv.setVisibility(View.VISIBLE);
                                        statusPromptTv.setText(getString(R.string.ONMYWAY));
                                        break;
                                    case 4://交付
                                        statusTitleTv.setText(getString(R.string.THENDELIVERED));
                                        paidIv.setVisibility(View.VISIBLE);
                                        statusPromptTv.setText(getString(R.string.WELCOMEBACK));
                                        break;
                                }
                                orderDataList.clear();
                                if (data.getData().getOrderDetailRespResults() != null && data.getData().getOrderDetailRespResults().size() > 0) {
                                    for (OrdersDetailsBase.DataBean.OrderDetailRespResultsBean item : data.getData().getOrderDetailRespResults()) {
                                        orderDataList.add(item);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                                merchantPhone = data.getData().getMerchantPhone();

                                delivery_money_tv.setText("$" + AppUtile.getPrice(data.getData().getDeliveryMoney() + ""));
                                double allPrice = 0;
                                if (data.getData().getOrderDetailRespResults() != null && data.getData().getOrderDetailRespResults().size() > 0) {
                                    for (int i = 0; i < data.getData().getOrderDetailRespResults().size(); i++) {
                                        allPrice = AppUtile.sum(AppUtile.mul(data.getData().getOrderDetailRespResults().get(i).getPrice(), data.getData().getOrderDetailRespResults().get(i).getNumber()), allPrice);
                                    }
                                }
                                allPrice = AppUtile.sum(allPrice, Double.valueOf(data.getData().getDeliveryMoney()));
                                double tax = AppUtile.div(!TextUtils.isEmpty(data.getData().getTaxRate()) ? Double.valueOf(data.getData().getTaxRate()) : 0.0, 100.0, 5);
                                double all = AppUtile.mul(allPrice, tax);
                                double taxDouble = AppUtile.formatDouble1(all);
                                tax_rate_tv.setText("$" + taxDouble + "");
                                //tax_rate_tv.setText("$" + AppUtile.getPrice(AppUtile.formatDouble2(AppUtile.mul(AppUtile.div(Double.valueOf(data.getData().getTaxRate()),100.0,2),allPrice))+""));
                                total_tv.setText("$" + AppUtile.getPrice(AppUtile.sum(taxDouble, allPrice) + ""));
                                sendTimeTv.setText(getString(R.string.TODAY1) + " " + data.getData().getDeliveryTime());
                                restaurant_name_tv.setText(data.getData().getMerchantName());
                                Picasso.get().load(data.getData().getMerchantAvatar()).placeholder(R.mipmap.head_img_being).into(headImg);
                                orderNumTv.setText(data.getData().getOrderNumber());
                                timeTv.setText(data.getData().getDoneTime());
                                cardNumTv.setText(data.getData().getCardNo());
                                nameTv.setText(data.getData().getName());
                                phoneTv.setText(data.getData().getBuyerPhone());
                                addressTv.setText(data.getData().getAddress().replace("\n", ", "));
                                psTv.setText(data.getData().getNote());

                            }
                        }
                    }
                });
    }

    @OnClick({R.id.del_btn, R.id.help_btn, R.id.status_btn, R.id.coyp_btn, R.id.cart_bg, R.id.cancel_btn})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.cancel_btn:
                Intent intent1 = new Intent(OrdersDetailsActivity.this, PromptDialog.class);
                intent1.putExtra("content", getResources().getString(R.string.CANCELORDER) + "?");
                startActivityForResult(intent1, 0);
                break;
            case R.id.cart_bg:
                cartBg.setVisibility(View.GONE);
                cartLayout.setVisibility(View.GONE);
                cartLayout.setAnimation(AnimationUtil.moveToViewLocation1());
                break;
            case R.id.del_btn:
                finish();
                break;
            case R.id.help_btn://帮助
                intent = new Intent(this, OrdersHelpActivity.class);
                intent.putExtra("phone", merchantPhone);
                startActivity(intent);
                break;
            case R.id.status_btn://订单
                OkHttpUtils
                        .get()
                        .url(Constant.ORDER_BUYER_LOG)
                        .addHeader("Accept-Language", AppUtile.getLanguage())
                        .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                        .addParams("userId", YumApplication.getInstance().getMyInformation().getUid())
                        .addParams("orderId", OrderId)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int i) {
                                if (AppUtile.getNetWork(e.getMessage())) {
                                    showMsg(getString(R.string.NETERROR));
                                }
                            }

                            @Override
                            public void onResponse(String s, int cone) {
                                if (!TextUtils.isEmpty(s)) {

                                    if (AppUtile.setCode(s, OrdersDetailsActivity.this)) {
                                        OrderLogBase data = JSON.parseObject(s, OrderLogBase.class);
                                        numberList.clear();
                                        if (data.getData().getOrderLogRespResults() != null && data.getData().getOrderLogRespResults().size() > 0) {
                                            int num = data.getData().getOrderLogRespResults().size();
                                            for (int con = 0; con < num; con++) {
                                                numberList.add(con);
                                            }
                                            dianAdapter.notifyDataSetChanged();
                                            for (int con = 0; con < 5; con++) {
                                                typeTexts.get(con).setVisibility(View.GONE);
                                                typeTimes.get(con).setVisibility(View.GONE);
                                                typeLayout.get(con).setVisibility(View.GONE);
                                            }
                                            for (int i = 0; i < num; i++) {
                                                if (data.getData().getOrderLogRespResults().get(i).getOperationTypeId() == 6 || data.getData().getOrderLogRespResults().get(i).getOperationTypeId() == 5) {
                                                    typeTexts.get(typeTexts.size() - 1).setVisibility(View.VISIBLE);
                                                    typeTimes.get(typeTimes.size() - 1).setVisibility(View.VISIBLE);
                                                    typeLayout.get(typeLayout.size() - 1).setVisibility(View.VISIBLE);
                                                    if (data.getData().getOrderLogRespResults().get(i).getOperationTypeId() == 6) {
                                                        typeTexts.get(typeTexts.size() - 1).setText(getString(R.string.THENCANCELED));
                                                    } else {
                                                        typeTexts.get(typeTexts.size() - 1).setText(getString(R.string.THENDELIVERED));
                                                    }
                                                    typeTimes.get(typeTimes.size() - 1).setText(data.getData().getOrderLogRespResults().get(i).getOperationTime());
                                                } else {
                                                    typeLayout.get(data.getData().getOrderLogRespResults().get(i).getOperationTypeId() - 1).setVisibility(View.VISIBLE);
                                                    typeTexts.get(data.getData().getOrderLogRespResults().get(i).getOperationTypeId() - 1).setVisibility(View.VISIBLE);
                                                    typeTimes.get(data.getData().getOrderLogRespResults().get(i).getOperationTypeId() - 1).setVisibility(View.VISIBLE);
                                                    typeTimes.get(data.getData().getOrderLogRespResults().get(i).getOperationTypeId() - 1).setText(data.getData().getOrderLogRespResults().get(i).getOperationTime());
                                                }

                                            }
//                                            switch (num) {
//                                                case 1:
//                                                    for (int con = 0; con < 5; con++) {
//                                                        typeTexts.get(con).setVisibility(View.GONE);
//                                                        typeTimes.get(con).setVisibility(View.GONE);
//                                                    }
//                                                    typeTv1.setVisibility(View.VISIBLE);
//                                                    typeTime1.setVisibility(View.VISIBLE);
//                                                    typeTime1.setText(data.getData().getOrderLogRespResults().get(0).getOperationTime());
//                                                    break;
//                                                case 2:
//                                                    for (int con = 0; con < 5; con++) {
//                                                        typeTexts.get(con).setVisibility(View.GONE);
//                                                        typeTimes.get(con).setVisibility(View.GONE);
//                                                    }
//                                                    typeTv1.setVisibility(View.VISIBLE);
//                                                    typeTv2.setVisibility(View.VISIBLE);
//                                                    typeTime1.setVisibility(View.VISIBLE);
//                                                    typeTime2.setVisibility(View.VISIBLE);
//                                                    typeTime1.setText(data.getData().getOrderLogRespResults().get(0).getOperationTime());
//                                                    typeTime2.setText(data.getData().getOrderLogRespResults().get(1).getOperationTime());
//                                                    break;
//                                                case 3:
//                                                    for (int con = 0; con < 5; con++) {
//                                                        typeTexts.get(con).setVisibility(View.GONE);
//                                                        typeTimes.get(con).setVisibility(View.GONE);
//                                                    }
//                                                    typeTv1.setVisibility(View.VISIBLE);
//                                                    typeTv2.setVisibility(View.VISIBLE);
//                                                    typeTv3.setVisibility(View.VISIBLE);
//                                                    typeTime1.setVisibility(View.VISIBLE);
//                                                    typeTime2.setVisibility(View.VISIBLE);
//                                                    typeTime3.setVisibility(View.VISIBLE);
//                                                    typeTime1.setText(data.getData().getOrderLogRespResults().get(0).getOperationTime());
//                                                    typeTime2.setText(data.getData().getOrderLogRespResults().get(1).getOperationTime());
//                                                    typeTime3.setText(data.getData().getOrderLogRespResults().get(2).getOperationTime());
//                                                    break;
//                                                case 4:
//                                                    for (int con = 0; con < 5; con++) {
//                                                        typeTexts.get(con).setVisibility(View.GONE);
//                                                        typeTimes.get(con).setVisibility(View.GONE);
//                                                    }
//                                                    typeTv1.setVisibility(View.VISIBLE);
//                                                    typeTv2.setVisibility(View.VISIBLE);
//                                                    typeTv3.setVisibility(View.VISIBLE);
//                                                    typeTv4.setVisibility(View.VISIBLE);
//                                                    typeTime1.setVisibility(View.VISIBLE);
//                                                    typeTime2.setVisibility(View.VISIBLE);
//                                                    typeTime3.setVisibility(View.VISIBLE);
//                                                    typeTime4.setVisibility(View.VISIBLE);
//                                                    typeTime1.setText(data.getData().getOrderLogRespResults().get(0).getOperationTime());
//                                                    typeTime2.setText(data.getData().getOrderLogRespResults().get(1).getOperationTime());
//                                                    typeTime3.setText(data.getData().getOrderLogRespResults().get(2).getOperationTime());
//                                                    typeTime4.setText(data.getData().getOrderLogRespResults().get(3).getOperationTime());
//                                                    break;
//                                                case 5:
//                                                    typeTv1.setVisibility(View.VISIBLE);
//                                                    typeTv2.setVisibility(View.VISIBLE);
//                                                    typeTv3.setVisibility(View.VISIBLE);
//                                                    typeTv4.setVisibility(View.VISIBLE);
//                                                    typeTv5.setVisibility(View.VISIBLE);
//                                                    typeTime1.setVisibility(View.VISIBLE);
//                                                    typeTime2.setVisibility(View.VISIBLE);
//                                                    typeTime3.setVisibility(View.VISIBLE);
//                                                    typeTime4.setVisibility(View.VISIBLE);
//                                                    typeTime5.setVisibility(View.VISIBLE);
//                                                    typeTime1.setText(data.getData().getOrderLogRespResults().get(0).getOperationTime());
//                                                    typeTime2.setText(data.getData().getOrderLogRespResults().get(1).getOperationTime());
//                                                    typeTime3.setText(data.getData().getOrderLogRespResults().get(2).getOperationTime());
//                                                    typeTime4.setText(data.getData().getOrderLogRespResults().get(3).getOperationTime());
//                                                    typeTime5.setText(data.getData().getOrderLogRespResults().get(4).getOperationTime());
//                                                    break;
//
//                                            }
                                        } else {
                                            for (int con = 0; con < 5; con++) {
                                                typeTexts.get(con).setVisibility(View.GONE);
                                                typeTimes.get(con).setVisibility(View.GONE);
                                            }
                                        }
                                        cartBg.setVisibility(View.VISIBLE);
                                        cartLayout.setVisibility(View.VISIBLE);
                                        cartLayout.setAnimation(AnimationUtil.moveToViewBottom1());
                                    }
                                }
                            }
                        });

                break;
            case R.id.coyp_btn://复制
                // 从API11开始android推荐使用android.content.ClipboardManager
                // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(orderNumTv.getText());
                //showMsg(getString(R.string.ORDERNUMBERCOPY));
                break;
        }
    }
}
