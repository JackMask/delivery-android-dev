package com.yum.two_yum.view.client.business;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.squareup.picasso.Picasso;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.BaseReturn;
import com.yum.two_yum.base.CreditCardBase;
import com.yum.two_yum.base.GetDefaultReturn;
import com.yum.two_yum.base.ProvinceBean;
import com.yum.two_yum.base.SearchNearbyDataBase;
import com.yum.two_yum.base.SelelctTimeBase;
import com.yum.two_yum.base.SubmitOrdersBase;
import com.yum.two_yum.base.input.CreateOrdersInput;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.controller.adapter.SelectTimeAdapter;
import com.yum.two_yum.controller.adapter.SubmitOrdersAdapter;
import com.yum.two_yum.utile.AnimationUtil;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.CircleImageView;
import com.yum.two_yum.utile.GetPickerStrCallBack;
import com.yum.two_yum.utile.pickerview.OptionsPickerView;
import com.yum.two_yum.utile.view.NoSlideListView;
import com.yum.two_yum.view.client.ClientActivity;
import com.yum.two_yum.view.client.orders.OrdersDetailsActivity;
import com.yum.two_yum.view.dialog.Prompt2Dialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * @author 余先德
 * @data 2018/4/10
 */

public class SubmitOrdersActivity extends BaseActivity {

    public final static String TAG = "SubmitOrdersActivity";

    @Bind(R.id.del_btn)
    ImageView delBtn;
    @Bind(R.id.content_lv)
    NoSlideListView contentLv;
    @Bind(R.id.time_tv)
    TextView timeTv;
    @Bind(R.id.distribution_tv)
    TextView distributionTv;
    @Bind(R.id.all_pice)
    TextView allPiceView;
    @Bind(R.id.create_account_btn)
    TextView createAccountBtn;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.coordinate_img)
    ImageView coordinateImg;
    @Bind(R.id.address_tv)
    TextView addressTv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.phone_tv)
    TextView phoneTv;
    @Bind(R.id.right_img)
    ImageView rightImg;
    @Bind(R.id.select_address_btn)
    RelativeLayout selectAddressBtn;
    @Bind(R.id.time_img)
    ImageView timeImg;
    @Bind(R.id.right_img1)
    ImageView rightImg1;
    @Bind(R.id.select_time_btn)
    RelativeLayout selectTimeBtn;
    @Bind(R.id.credit_btn)
    LinearLayout creditBtn;
    @Bind(R.id.order_btn)
    TextView orderBtn;
    @Bind(R.id.credit_tv)
    TextView creditTv;
    @Bind(R.id.credit_prompt_tv)
    TextView creditPromptTv;
    @Bind(R.id.tax_tv)
    TextView taxTv;
    @Bind(R.id.head_img)
    CircleImageView headImg;
    @Bind(R.id.restaurant_name_tv)
    TextView restaurant_name_tv;
    @Bind(R.id.no_data_layout)
    View noDataLayout;
    @Bind(R.id.select_layout)
    LinearLayout selectLayout;
    @Bind(R.id.data_list)
    ListView timeList;

    private SubmitOrdersAdapter adapter;
    private String addressId;
    private SelectTimeAdapter selectTimeAdapter;
    private List<CreateOrdersInput.MenuReqListBean> dataList = new ArrayList<>();
    private ArrayList<SelelctTimeBase> options1Items = new ArrayList<>();//条件选择器的数据
    private CreateOrdersInput createOrdersInput;
    private double allPice = 0;
    private String[] times;
    private boolean click = false;
    private SearchNearbyDataBase data;
    private Card cards;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_orders);
        ButterKnife.bind(this);

        data = (SearchNearbyDataBase)getIntent().getSerializableExtra("addressData");
        createOrdersInput = (CreateOrdersInput) getIntent().getSerializableExtra("data");

        setView();

    }

    private boolean examinationData(){
        String creditId = createOrdersInput.getUserCreditCardId();
        String addressId = createOrdersInput.getUserAddressId();
        String deliveryTime = createOrdersInput.getDeliveryTime();
        if (!TextUtils.isEmpty(creditId)){
            if (!TextUtils.isEmpty(addressId)){
                if (!TextUtils.isEmpty(deliveryTime)){
                    click = true;
                }else{
                    click = false;
                }
            }else{
                click = false;
            }
        }
        else{
            click = false;
        }
        return click;
    }
    @Override
    protected void setView() {
        super.setView();
        timeTv.setText("(" + getResources().getString(R.string.REQUIREDS) + ")");

        for (CreateOrdersInput.MenuReqListBean item : createOrdersInput.getMenuReqList()) {
            dataList.add(item);
            allPice = AppUtile.sum(AppUtile.mul(item.getPrice(),item.getNumber()),allPice);
        }
        distributionTv.setText("$" + AppUtile.getPrice(createOrdersInput.getDeliveryMoney()));
        allPice = AppUtile.sum(Integer.valueOf(createOrdersInput.getDeliveryMoney()),allPice);
        double tax= AppUtile.div(!TextUtils.isEmpty(createOrdersInput.getTaxRate())?Double.valueOf(createOrdersInput.getTaxRate()):0.0,100.0,5);
        double all = AppUtile.mul(allPice,tax);
        double taxDouble1 = AppUtile.formatDouble1(all);
        //double a =  AppUtile.mul(allPice,!TextUtils.isEmpty(createOrdersInput.getTaxRate())?Double.valueOf(createOrdersInput.getTaxRate()):0.0  );
        //double taxDouble = AppUtile.formatDouble2(AppUtile.mul(allPice,AppUtile.div(!TextUtils.isEmpty(createOrdersInput.getTaxRate())?Double.valueOf(createOrdersInput.getTaxRate()):0.0,100.0,9)));
        //double taxDouble = AppUtile.formatDouble2(AppUtile.getTwoDecimal((allPice+(!TextUtils.isEmpty(createOrdersInput.getDeliveryMoney())?Float.valueOf(createOrdersInput.getDeliveryMoney()):0.0))*(!TextUtils.isEmpty(createOrdersInput.getTaxRate())?Float.valueOf(createOrdersInput.getTaxRate()):0.0  )    ));
        taxTv.setText("$"+AppUtile.getPrice(taxDouble1 +""));
        //createOrdersInput.setTaxRate((allPice*Float.valueOf(createOrdersInput.getTaxRate()))+"");
        createOrdersInput.setTotalMoney(AppUtile.getTwoDecimal(allPice+(allPice*Float.valueOf(createOrdersInput.getTaxRate())))+"");
        createOrdersInput.setPayMoney(AppUtile.getTwoDecimal(allPice+(allPice*Float.valueOf(createOrdersInput.getTaxRate())))+"");
        allPiceView.setText("$" +AppUtile.getPrice(AppUtile.sum(taxDouble1,allPice)+""));
        //allPiceView.setText("$" + AppUtile.getPrice(AppUtile.getTwoDecimal(allPice+((allPice+(!TextUtils.isEmpty(createOrdersInput.getDeliveryMoney())?Float.valueOf(createOrdersInput.getDeliveryMoney()):0.0))*(!TextUtils.isEmpty(createOrdersInput.getTaxRate())?Float.valueOf(createOrdersInput.getTaxRate()):0.0  )      )+(!TextUtils.isEmpty(createOrdersInput.getDeliveryMoney())?Float.valueOf(createOrdersInput.getDeliveryMoney()):0.0))+""));
        restaurant_name_tv.setText(createOrdersInput.getName());
        Picasso.get().load(Constant.SHOW_IMG+createOrdersInput.getHeadUrl()).placeholder(R.mipmap.head_img_being).into(headImg);
        initOptionData(getIntent().getStringExtra("time"));
        adapter = new SubmitOrdersAdapter(dataList);
        contentLv.setAdapter(adapter);
        selectTimeAdapter = new SelectTimeAdapter(options1Items);
        selectTimeAdapter.setItemClick(new SelectTimeAdapter.itemClick() {
            @Override
            public void clickItem(String time) {
                for (int i = 0 ; i < options1Items.size();i++){
                    options1Items.get(i).setConType(false);
                    if (options1Items.get(i).getTitle().equals(time)){
                        options1Items.get(i).setConType(true);
                    }
                }
                timeTv.setText("(" + time + ")");
                createOrdersInput.setDeliveryTime(time.replace("~","-"));
                examinationData();
                selectTimeAdapter.notifyDataSetChanged();
                noDataLayout.setVisibility(View.GONE);
                selectLayout.setVisibility(View.GONE);
                selectLayout.setAnimation(AnimationUtil.fromNowToBottom());
            }
        });
        timeList.setAdapter(selectTimeAdapter);
        if (!TextUtils.isEmpty(YumApplication.getInstance().getAddressDB().getId())){
            if ((Double.valueOf(createOrdersInput.getRange())*1600)>= AppUtile.GetDistance(Double.valueOf(createOrdersInput.getLat()),Double.valueOf(createOrdersInput.getLng())
                    ,Double.valueOf(YumApplication.getInstance().getAddressDB().getLat()),Double.valueOf(YumApplication.getInstance().getAddressDB().getLng()))){
                addressTv.setText(YumApplication.getInstance().getAddressDB().getAddress().replace("\n",", "));
                addressTv.setTextColor(Color.parseColor("#484848"));
                nameTv.setText(YumApplication.getInstance().getAddressDB().getName());
                phoneTv.setText(YumApplication.getInstance().getAddressDB().getPhone());
                createOrdersInput.setUserAddressId(YumApplication.getInstance().getAddressDB().getId());
                createOrdersInput.setNote(YumApplication.getInstance().getAddressDB().getNote());
                addressId = YumApplication.getInstance().getAddressDB().getId();
                examinationData();
            }else{
                data = null;
            }
        }
//        if (data!=null){
//            if ((Double.valueOf(createOrdersInput.getRange())*1600)>= AppUtile.GetDistance(Double.valueOf(createOrdersInput.getLat()),Double.valueOf(createOrdersInput.getLng())
//                    ,Double.valueOf(data.getLat()),Double.valueOf(data.getLng()))){
//                addressTv.setText(data.getAddress().replace("\n",", "));
//                addressTv.setTextColor(Color.parseColor("#484848"));
//                nameTv.setText(data.getName());
//                phoneTv.setText(data.getPhone());
//                createOrdersInput.setUserAddressId(data.getId());
//                createOrdersInput.setNote(data.getNote());
//                addressId = data.getId();
//                examinationData();
//            }else{
//                data = null;
//            }
//
//        }
        getCreditCard();
    }

    @OnClick({R.id.del_btn, R.id.order_btn,R.id.restaurant_name_tv, R.id.create_account_btn, R.id.select_address_btn, R.id.select_time_btn, R.id.credit_btn,R.id.no_data_layout,R.id.head_img})
    public void onViewClicked(View view) {
        final Intent intent;
        switch (view.getId()) {
            case R.id.restaurant_name_tv:
            case R.id.head_img:
                intent = new Intent(this, BusinessDetailsActivity.class);
                intent.putExtra("id", createOrdersInput.getSalerUserId());
                intent.putExtra("type", createOrdersInput.getState());
                intent.putExtra("like", createOrdersInput.isLike());
                startActivityForResult(intent, 1);
                break;
            case R.id.del_btn:
                finish();
                break;
            case R.id.order_btn:
                if (TextUtils.isEmpty(createOrdersInput.getUserAddressId())){
                    showMsg(getString(R.string.SDELIVERYADDRESS));
                    break;
                }
                if (TextUtils.isEmpty(createOrdersInput.getDeliveryTime())){
                    showMsg(getString(R.string.DELIVERYTIME));
                    break;
                }
                if (card==null){
                    showMsg(getString(R.string.PAYMENTMETHOD));
                    break;
                }
                showLoading();
                if (cards!=null){
                    Stripe stripe = new Stripe(getApplicationContext(), Constant.APPKEY);
                    stripe.createToken(
                            cards,
                            new TokenCallback() {
                                public void onSuccess(Token token) {
                                    // Send token to your server
                                    createOrdersInput.setStripeToken(token.getId());
                                    String content = JSON.toJSONString(createOrdersInput);
                                    System.out.println("YumApplication.getInstance().getMyInformation().getToken() = " + YumApplication.getInstance().getMyInformation().getToken());
                                    OkHttpUtils.postString()
                                            .url(Constant.CREATE_ORDER)
                                            .mediaType(MediaType.parse("application/json"))
                                            .addHeader("Accept-Language", AppUtile.getLanguage())
                                            .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                                            .content(content)
                                            .build()
                                            .execute(new StringCallback() {
                                                @Override
                                                public void onError(Call call, Exception e, int i) {
                                                    if (AppUtile.setCodeOrder(e.getMessage(),SubmitOrdersActivity.this)) {
                                                        dismissLoading();
                                                    }
                                                }

                                                @Override
                                                public void onResponse(String s, int i) {
                                                    dismissLoading();
                                                    if (!TextUtils.isEmpty(s)){
                                                        if (AppUtile.setCodeOrder(s,SubmitOrdersActivity.this)){
                                                            SubmitOrdersBase data = JSON.parseObject(s,SubmitOrdersBase.class);
                                                            Intent intent = new Intent(SubmitOrdersActivity.this, OrdersDetailsActivity.class);
                                                            intent.putExtra("id", data.getData().getOrderId()+"");
                                                            startActivity(intent);

                                                            Intent intent1 = new Intent();
                                                            intent1.putExtra("id",data.getData().getOrderId()+"");
                                                            setResult(RESULT_OK,intent1);
                                                            finish();
                                                        }
                                                    }
                                                }
                                            });
                                }
                                public void onError(Exception error) {
                                    // Show localized error message
                                    dismissLoading();
                                    Intent intent1 = new Intent(SubmitOrdersActivity.this,Prompt2Dialog.class);
                                    intent1.putExtra("title",true);
                                    intent1.putExtra("content",getString(R.string.CREDITDEBITCARDERROR));
                                    startActivity(intent1);
                                   // showMsg(error.getLocalizedMessage());

                                }
                            }
                    );
                }else{
                    showMsg(getString(R.string.PAYMENTMETHOD));
                }

                break;
            case R.id.create_account_btn:
                break;
            case R.id.select_address_btn://地址
                intent = new Intent(this, ChooseDeliveryAddressActivity.class);
                intent.putExtra("lat",createOrdersInput.getLat());
                intent.putExtra("lng",createOrdersInput.getLng());
                intent.putExtra("id",addressId);
                intent.putExtra("distance",createOrdersInput.getRange());
                startActivityForResult(intent, 0);
                break;
            case R.id.no_data_layout:
                noDataLayout.setVisibility(View.GONE);
                selectLayout.setVisibility(View.GONE);
                selectLayout.setAnimation(AnimationUtil.fromNowToBottom());
                break;
            case R.id.select_time_btn:
//
                if (noDataLayout.getVisibility() ==View.GONE&&selectLayout.getVisibility() == View.GONE) {
                    noDataLayout.setVisibility(View.VISIBLE);
                    selectLayout.setVisibility(View.VISIBLE);
                    selectLayout.setAnimation(AnimationUtil.fromBottomToNow());
                }else{
                    noDataLayout.setVisibility(View.GONE);
                    selectLayout.setVisibility(View.GONE);
                    selectLayout.setAnimation(AnimationUtil.fromNowToBottom());
                }
                //pvOptions.show();
                break;
            case R.id.credit_btn://银行卡
                if (card!=null){
                    OkHttpUtils
                            .post()
                            .url(Constant.CREDIT_CARD_DELETE)
                            .addHeader("Accept-Language", AppUtile.getLanguage())
                            .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                            .addParams("userId", YumApplication.getInstance().getMyInformation().getUid())
                            .addParams("creditCardId", card.getId())
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int i) {

                                }

                                @Override
                                public void onResponse(String s, int i) {
                                    if (!TextUtils.isEmpty(s)) {
                                        if (AppUtile.setCode(s,SubmitOrdersActivity.this)) {
                                            card = null;
                                            cards = null;
                                            creditTv.setText(getString(R.string.CREDITDEBITCARDS));
                                            creditPromptTv.setText(getString(R.string.PROVIDE));
                                            createOrdersInput.setUserCreditCardId("");
                                        }
                                    }
                                }
                            });

                }else {
                    intent = new Intent(this, ProvideCreditActivity.class);
                    //intent.putExtra("data",card);
                    startActivityForResult(intent, 1);
                    overridePendingTransition(R.anim.activity_open, R.anim.in);
                }
                break;
        }
    }
    Handler mHandler1 = new Handler();
    Runnable r = new Runnable() {

        @Override
        public void run() {
            //do something
            //每隔1s循环执行run方法
            ClientActivity.instance.submitOk();
            finish();
            BusinessDetailsActivity.instance.finish();
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                if (data != null) {
                    addressTv.setText(data.getStringExtra("address").replace("\n",", "));
                    addressTv.setTextColor(Color.parseColor("#484848"));
                    nameTv.setText(data.getStringExtra("name"));
                    phoneTv.setText(data.getStringExtra("phone"));
                    createOrdersInput.setUserAddressId(data.getStringExtra("id"));
                    createOrdersInput.setNote(data.getStringExtra("note"));
                    addressId = data.getStringExtra("id");
                    examinationData();
                }
            } else if (requestCode == 1) {
                if (data!=null){
                    card = (CreditCardBase.DataBean.UserCreditCardRespResultsBean)data.getSerializableExtra("data");
                    String cardNoStr = card.getCardNo();
                    if (cardNoStr.length()<4){
                        showMsg(getString(R.string.CREDITDEBITCARDERROR));
                        return;
                    }else{
                        creditTv.setText("***"+cardNoStr.substring(card.getCardNo().length()-4,card.getCardNo().length()));
                    }
                    int year = 0,month = 0;
                    if (!TextUtils.isEmpty(card.getExpiringDate())){
                        String[] date = card.getExpiringDate().split("/");
                        month = Integer.valueOf(date[0]);
                        if (date.length>1){
                            year = Integer.valueOf("20"+date[1]);
                        }
                    }
                    cards = new Card(card.getCardNo(), month, year, card.getCvvCode());
                    cards.setName(card.getName());
                    creditPromptTv.setText(getString(R.string.REMOVE));
                    createOrdersInput.setUserCreditCardId(card.getId());
                    examinationData();
                }
            }
        }else if (resultCode == 2){
            if (requestCode == 0) {
                addressTv.setText(getString(R.string.CHOOSEDELIVERYADDRESS));
                addressTv.setTextColor(Color.parseColor("#FF007AFF"));
                nameTv.setText("");
                phoneTv.setText("");
                createOrdersInput.setUserAddressId("");
                addressId = "";
            }
        }
    }
    private CreditCardBase.DataBean.UserCreditCardRespResultsBean card ;
    private void getCreditCard() {
//                OkHttpUtils
//                .get()
//                .url(Constant.USER_ADDRESS_DEFAULT)
//                .addHeader("Accept-Language", AppUtile.getLanguage())
//                .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int i) {
//                        if (AppUtile.getNetWork(e.getMessage())){
//                            showMsg(getString(R.string.NETERROR));
//                        }
//                    }
//
//                    @Override
//                    public void onResponse(String s, int i) {
//                        if (!TextUtils.isEmpty(s)) {
//                            GetDefaultReturn data = JSON.parseObject(s, GetDefaultReturn.class);
//                            if (AppUtile.setCode(s,SubmitOrdersActivity.this)) {
//                                if (data.getData()!=null&&!TextUtils.isEmpty(data.getData().getId())){
//
//                                    addressTv.setText(data.getData().getAddress().replace("\n",""));
//                                    addressTv.setTextColor(Color.parseColor("#484848"));
//                                    nameTv.setText(data.getData().getName());
//                                    phoneTv.setText(data.getData().getPhone());
//                                    createOrdersInput.setUserAddressId(data.getData().getId()+"");
//                                    createOrdersInput.setNote(data.getData().getNote());
//                                    addressId = data.getData().getId()+"";
//                                    examinationData();
//                                }
//                            }
//                        }
//                    }
//                });
        OkHttpUtils
                .get()
                .url(Constant.CREDIT_CARD_GET)
                .addHeader("Accept-Language", AppUtile.getLanguage())
                .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                .addParams("userId", YumApplication.getInstance().getMyInformation().getUid())
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
                        if (!TextUtils.isEmpty(s)) {

                            if (AppUtile.setCode(s,SubmitOrdersActivity.this)) {
                                CreditCardBase data = JSON.parseObject(s, CreditCardBase.class);
                                if (data.getData().getUserCreditCardRespResults() != null && data.getData().getUserCreditCardRespResults().size() > 0) {
                                    card = data.getData().getUserCreditCardRespResults().get(0);
                                    int year = 0,month = 0;
                                    if (!TextUtils.isEmpty(card.getExpiringDate())){
                                        String[] date = card.getExpiringDate().split("/");
                                        month = Integer.valueOf(date[0]);
                                        if (date.length>1){
                                            year = Integer.valueOf("20"+date[1]);
                                        }
                                    }
                                    cards = new Card(card.getCardNo(), month, year, card.getCvvCode());
                                    cards.setName(card.getName());
                                    String cardNoStr = card.getCardNo();
                                    if (cardNoStr.length()<4){
                                        showMsg(getString(R.string.CREDITDEBITCARDERROR));
                                        return;
                                    }else{
                                        creditTv.setText("***"+cardNoStr.substring(card.getCardNo().length()-4,card.getCardNo().length()));
                                    }


                                    creditPromptTv.setText(getString(R.string.REMOVE));
                                    createOrdersInput.setUserCreditCardId(data.getData().getUserCreditCardRespResults().get(0).getId());
                                }
                            }
                        }
                    }
                });
    }

    /**
     * 获取测试数据
     */
    private void initOptionData(String time) {
        if (!TextUtils.isEmpty(time)) {
            options1Items.clear();
            String[] sourceStrArray = time.split(",");
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
            String nowData = df.format(new Date());
            //System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
            //times = getResources().getStringArray(R.array.string_array_delivery_time);
            for (int i = 0; i < sourceStrArray.length; i++) {
                String[] timeOneStr = sourceStrArray[i].split("-");
                if (timeOneStr.length==2){
                    int hhStart = Integer.valueOf((timeOneStr[0].split(":"))[0]);
                    int mmStart = Integer.valueOf((timeOneStr[0].split(":"))[1]);

                    int hhEnd = Integer.valueOf((timeOneStr[1].split(":"))[0]);
                    int mmEnd = Integer.valueOf((timeOneStr[1].split(":"))[1]);

                    int hhNow = Integer.valueOf((nowData.split(":"))[0]);
                    int mmNow = Integer.valueOf((nowData.split(":"))[1]);

                    int startTime = (hhStart*60)+mmStart;
                    int endTime = (hhEnd*60)+mmEnd;
                    int nowTime = (hhNow*60)+mmNow;
                    if (startTime>nowTime){
                        SelelctTimeBase data = new SelelctTimeBase();
                        data.setTitle(sourceStrArray[i].replace("-","~"));
                        data.setConType(false);
                        data.setClickType(true);
                        options1Items.add(data);
                    }else{
                        SelelctTimeBase data = new SelelctTimeBase();
                        data.setTitle(sourceStrArray[i].replace("-","~"));
                        data.setConType(false);
                        data.setClickType(false);
                        options1Items.add(data);
                    }
                }
            }
        }
    }

}
