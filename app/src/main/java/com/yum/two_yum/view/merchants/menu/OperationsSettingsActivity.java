package com.yum.two_yum.view.merchants.menu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.squareup.picasso.Picasso;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.BaseReturn;
import com.yum.two_yum.base.FilterWebBase;
import com.yum.two_yum.base.ProvinceBean;
import com.yum.two_yum.base.SelectKewordBase;
import com.yum.two_yum.base.input.MerchantInput;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.ContainsEmojiEditText;
import com.yum.two_yum.utile.pickerview.OptionsPickerView;
import com.yum.two_yum.view.client.clientorder.SearchAddressActivity;
import com.yum.two_yum.view.dialog.PromptDialog;
import com.yum.two_yum.view.guide.SelectCountryActivity;
import com.yum.two_yum.view.guide.SelectTimeActivity;
import com.yum.two_yum.view.guide.SelelctKeywordActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * @author 余先德
 * @data 2018/4/13
 */

public class OperationsSettingsActivity extends BaseActivity {

    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.name_ed)
    ContainsEmojiEditText nameEd;
    @Bind(R.id.main_dish_ed)
    ContainsEmojiEditText mainDishEd;
    @Bind(R.id.county_ed)
    TextView countyEd;
    @Bind(R.id.street_ed)
    TextView streetEd;
    @Bind(R.id.apt_ed)
    ContainsEmojiEditText aptEd;
    @Bind(R.id.city_ed)
    TextView cityEd;
    @Bind(R.id.province_ed)
    TextView provinceEd;
    @Bind(R.id.zip_ed)
    TextView zipEd;
    @Bind(R.id.keyword_ed)
    TextView keywordEd;
    @Bind(R.id.open_ed)
    TextView openEd;
    @Bind(R.id.close_ed)
    TextView closeEd;
    @Bind(R.id.amount_ed)
    TextView amountEd;
    @Bind(R.id.delivery_ed)
    TextView deliveryEd;
    @Bind(R.id.distance_ed)
    TextView distanceEd;
    @Bind(R.id.time_tv)
    TextView timeTv;
    @Bind(R.id.map_tv)
    TextView mapTv;
    @Bind(R.id.currency_tv)
    TextView currencyTv;
    @Bind(R.id.taxrate_ed)
    TextView taxrateEd;
    @Bind(R.id.save_btn)
    TextView saveBtn;
    @Bind(R.id.amount_btn)
    LinearLayout amountBtn;
    @Bind(R.id.delivery_btn)
    LinearLayout deliveryBtn;
    @Bind(R.id.select_area_btn)
    LinearLayout selectAreaBtn;
    @Bind(R.id.street_btn)
    LinearLayout street_btn;
    @Bind(R.id.flag_iv)
    ImageView flagIv;

    private MerchantInput data,noPutData;
    private boolean click = false;
    private boolean changeType = false;
    private String countrySymbol;

    private OptionsPickerView optionsPickerView;
    private OptionsPickerView options2PickerView;
    private OptionsPickerView option3PickerView;
    private OptionsPickerView option4PickerView;
    private OptionsPickerView option5PickerView;
    private boolean selecType = true;
    private String[] times ;
    private String money = "6.99";
    private String countryIdStr = "";
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ProvinceBean> options2Items = new ArrayList<>();
    private ArrayList<ProvinceBean> options3Items = new ArrayList<>();
    private ArrayList<ProvinceBean> options4Items = new ArrayList<>();
    private ArrayList<ProvinceBean> options5Items = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations_settings);
        ButterKnife.bind(this);
        data = (MerchantInput)getIntent().getSerializableExtra("data");
        setNoPutData();
        setDateView();
        optionsPickerView = getSelector(optionsPickerView, getTimeItems(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String tx = options1Items.get(options1).getPickerViewText();
                if (selecType) {
                    openEd.setText(tx);
                    data.setStartTime(tx);
                    YumApplication.getInstance().getpOperationsSettings().setStartTime(tx);
                } else {
                    closeEd.setText(tx);
                    data.setEndTime(tx);
                    YumApplication.getInstance().getpOperationsSettings().setEndTime(tx);
                }
                examinationData();
            }
        });
        options2PickerView = getSelector(options2PickerView, getTime2Items(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String tx = options2Items.get(options1).getPickerViewText();
                amountEd.setText("$ "+tx);
                click = true;
                data.setMoney(tx);
                YumApplication.getInstance().getpOperationsSettings().setMoney(tx);
                saveBtn.setBackgroundResource(R.drawable.button_red_bright);
                data.setMoney(tx);
                examinationData();
            }
        });
        option3PickerView = getSelector(option3PickerView, getTime3Items(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String tx = options3Items.get(options1).getPickerViewText();
                deliveryEd.setText("$ "+tx);
                data.setDeliveryMoney(tx);
                click = true;
                YumApplication.getInstance().getpOperationsSettings().setDeliveryMoney(tx);
                saveBtn.setBackgroundResource(R.drawable.button_red_bright);
                data.setDeliveryMoney(tx);
                examinationData();
            }
        });

        option4PickerView = getSelector(option4PickerView, getTime4Items(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String tx = options4Items.get(options1).getPickerViewText();
                distanceEd.setText(tx);
                data.setRange(tx);
                YumApplication.getInstance().getpOperationsSettings().setRange(tx);
                click = true;
                saveBtn.setBackgroundResource(R.drawable.button_red_bright);
                examinationData();
            }
        });
        option5PickerView = getSelector(option5PickerView, getTime5Items(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String tx = options5Items.get(options1).getPickerViewText();
                mapTv.setText(tx);
                click = true;
                if (tx.equals(getString(R.string.GOOGLEMAPS))){
                    YumApplication.getInstance().getpOperationsSettings().setMap("Google地图");
                    data.setMap("Google地图");
                }else{
                    YumApplication.getInstance().getpOperationsSettings().setMap(tx);
                    data.setMap(tx);
                }
                saveBtn.setBackgroundResource(R.drawable.button_red_bright);
                examinationData();
            }
        });
        nameEd.addTextChangedListener(new TextChanged(0));
        mainDishEd.addTextChangedListener(new TextChanged(1));
       // streetEd.addTextChangedListener(new TextChanged(2));
        aptEd.addTextChangedListener(new TextChanged(3));
        cityEd.addTextChangedListener(new TextChanged(4));
        provinceEd.addTextChangedListener(new TextChanged(5));
        zipEd.addTextChangedListener(new TextChanged(6));
        taxrateEd.addTextChangedListener(new TextChanged(7));
        aptEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setChangeType();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void setNoPutData(){
        noPutData = new MerchantInput();
        noPutData.setRange(data.getRange());
        noPutData.setMoney(data.getMoney());
        noPutData.setDeliveryMoney(data.getDeliveryMoney());
        noPutData.setLng(data.getLng());
        noPutData.setLat(data.getLat());
        noPutData.setKeyword(data.getKeyword());
        noPutData.setTime(data.getTime());
        noPutData.setTaxRate(data.getTaxRate());
        noPutData.setZipCode(data.getZipCode());
        noPutData.setProvince(data.getProvince());
        noPutData.setCity(data.getCity());
        noPutData.setRoomNo(data.getRoomNo());
        noPutData.setStreet(data.getStreet());
        noPutData.setSeries(data.getSeries());
        noPutData.setName(data.getName());
        noPutData.setMap(data.getMap());
        noPutData.setCountry(data.getCountry());
        noPutData.setCountryId(data.getCountryId());
        noPutData.setEndTime(data.getEndTime());
        noPutData.setStartTime(data.getStartTime());
        noPutData.setDate(data.getDate());
        noPutData.setCurrencyId(data.getCurrencyId());
        noPutData.setUserId(data.getUserId());
    }
    private void setChangeType(){

        String name = !TextUtils.isEmpty(noPutData.getName())?noPutData.getName():"";
        String nameStr = !TextUtils.isEmpty(nameEd.getText().toString())?nameEd.getText().toString():"";

        String series = !TextUtils.isEmpty(noPutData.getSeries())?noPutData.getSeries():"";
        String seriesStr = !TextUtils.isEmpty(mainDishEd.getText().toString())?mainDishEd.getText().toString():"";

        String countryId = !TextUtils.isEmpty(noPutData.getCountryId())?noPutData.getCountryId():"";
        String countryIdStr = !TextUtils.isEmpty(data.getCountryId())?data.getCountryId():"";

        String street = !TextUtils.isEmpty(noPutData.getStreet())?noPutData.getStreet():"";
        String streetStr = !TextUtils.isEmpty(streetEd.getText().toString())?streetEd.getText().toString():"";

        String roomNo = !TextUtils.isEmpty(noPutData.getRoomNo())?noPutData.getRoomNo():"";
        String roomNoStr = !TextUtils.isEmpty(aptEd.getText().toString())?aptEd.getText().toString():"";

        String city = !TextUtils.isEmpty(noPutData.getCity())?noPutData.getCity():"";
        String cityStr = !TextUtils.isEmpty(cityEd.getText().toString())?cityEd.getText().toString():"";

        String keyword = !TextUtils.isEmpty(noPutData.getKeyword())?noPutData.getKeyword():"";
        String keywordStr = !TextUtils.isEmpty(keywordEd.getText().toString())?keywordEd.getText().toString():"";

        String province = !TextUtils.isEmpty(noPutData.getProvince())?noPutData.getProvince():"";
        String provinceStr = !TextUtils.isEmpty(provinceEd.getText().toString())?provinceEd.getText().toString():"";

        String zip = !TextUtils.isEmpty(noPutData.getZipCode())?noPutData.getZipCode():"";
        String zipStr = !TextUtils.isEmpty(zipEd.getText().toString())?zipEd.getText().toString():"";

        String money = !TextUtils.isEmpty(noPutData.getMoney())?"$ "+noPutData.getMoney():"$ 6.99";
        String moneyStr = !TextUtils.isEmpty(amountEd.getText().toString())?amountEd.getText().toString():"";

        String time = !TextUtils.isEmpty(noPutData.getTime())?noPutData.getTime():"";
        String timeStr = !TextUtils.isEmpty(timeTv.getText().toString())?timeTv.getText().toString():"";

        String open = !TextUtils.isEmpty(noPutData.getStartTime())?noPutData.getStartTime():"";
        String openStr = !TextUtils.isEmpty(openEd.getText().toString())?openEd.getText().toString():"";

        String deliveryMoney = !TextUtils.isEmpty(noPutData.getDeliveryMoney())?"$ "+noPutData.getDeliveryMoney():"$ 1.00";
        String deliveryMoneyStr = !TextUtils.isEmpty(deliveryEd.getText().toString())?deliveryEd.getText().toString():"";

        String range = !TextUtils.isEmpty(noPutData.getRange())?noPutData.getRange():"5.0";
        String rangeStr = !TextUtils.isEmpty(distanceEd.getText().toString())?distanceEd.getText().toString():"";

        String taxRate = !TextUtils.isEmpty(noPutData.getTaxRate())?noPutData.getTaxRate():"";
        String taxRateStr = !TextUtils.isEmpty(taxrateEd.getText().toString())?taxrateEd.getText().toString():"";

        String endTiem = !TextUtils.isEmpty(noPutData.getEndTime())?noPutData.getEndTime():"";
        String endTiemStr = !TextUtils.isEmpty(closeEd.getText().toString())?closeEd.getText().toString():"";

        String map = !TextUtils.isEmpty(noPutData.getMap())?noPutData.getMap():getString(R.string.GOOGLEMAPS);
        String mapStr = !TextUtils.isEmpty(mapTv.getText().toString())?mapTv.getText().toString():"";

        String currencyId = noPutData.getCurrencyId()!=-1?noPutData.getCurrencyId()+"":"";
        String currencyIdStr = data.getCurrencyId()!=-1?data.getCurrencyId()+"":"";

        if (!name.equals(nameStr)
                ||!series.equals(seriesStr)
                ||!countryId.equals(countryIdStr)
                ||!street.equals(streetStr)
                ||!roomNo.equals(roomNoStr)
                ||!city.equals(cityStr)
                ||!keyword.equals(keywordStr)
                ||!province.equals(provinceStr)
                ||!zip.equals(zipStr)
                ||!money.equals(moneyStr)
                ||!time.equals(timeStr)
                ||!open.equals(openStr)
                ||!deliveryMoney.equals(deliveryMoneyStr)
                ||!range.equals(rangeStr)
                ||!taxRate.equals(taxRateStr)
                ||!endTiem.equals(endTiemStr)
                ||!map.equals(mapStr)
                ||!currencyId.equals(currencyIdStr)){
            changeType = true;
        }

    }
    private void setDateView(){
        if (data!=null){
            nameEd.setText(data.getName());
            mainDishEd.setText(data.getSeries());
            countyEd.setText(data.getCountry());
            streetEd.setText(data.getStreet());
            aptEd.setText(data.getRoomNo());
            cityEd.setText(data.getCity());
            keywordEd.setText(data.getKeyword());
            if (!TextUtils.isEmpty(data.getKeyword())){
                String[] sourceStrArray = data.getKeyword().split(",");
                for (String itme:sourceStrArray){
                    SelectKewordBase.DataBean.MerchantKeywordRespResultsBean data = new SelectKewordBase.DataBean.MerchantKeywordRespResultsBean();
                    data.setKeyword(itme);
                    selectDataList.add(data);
                }
            }

            provinceEd.setText(data.getProvince());
            zipEd.setText(data.getZipCode());
            if (!TextUtils.isEmpty(data.getMoney())) {
                amountEd.setText("$ " + data.getMoney());
            }else{
                data.setMoney("6.99");
            }
            timeTv.setText(data.getTime());
            openEd.setText(data.getStartTime());
            if (!TextUtils.isEmpty(data.getDeliveryMoney())) {
                deliveryEd.setText("$ " + data.getDeliveryMoney());
            }else{
                data.setDeliveryMoney("1.00");
            }
            if(!TextUtils.isEmpty(data.getRange())){
                distanceEd.setText(data.getRange());
            }else{
                data.setRange("5.0");
            }
            taxrateEd.setText(data.getTaxRate());
            closeEd.setText(data.getEndTime());
            if (!TextUtils.isEmpty(data.getMap())) {
                mapTv.setText(data.getMap());
            }else{
                data.setMap(getString(R.string.GOOGLEMAPS));
            }
            if (!TextUtils.isEmpty(data.getCurrencyFlag()))
             Picasso.get().load(data.getCurrencyFlag()).placeholder(R.mipmap.timg).into(flagIv);
            currencyTv.setText(data.getCurrencyName());
            if (!TextUtils.isEmpty(data.getName())&&!TextUtils.isEmpty(data.getSeries())
                    &&!TextUtils.isEmpty(data.getTaxRate())
                    &&!TextUtils.isEmpty(data.getKeyword())
                    &&!TextUtils.isEmpty(data.getStreet())
                    &&!TextUtils.isEmpty(data.getCity())
                    &&!TextUtils.isEmpty(data.getProvince())
                    &&!TextUtils.isEmpty(data.getZipCode())
                    &&!TextUtils.isEmpty(data.getStartTime()) &&
                    !TextUtils.isEmpty(data.getEndTime())
                    &&!TextUtils.isEmpty(data.getTime())
                    &&data.getCurrencyId()!=-1
                    &&!TextUtils.isEmpty(data.getMap())){
                click = true;
                saveBtn.setBackgroundResource(R.drawable.button_red_bright);
            }
        }
        examinationData();
    }
    private class TextChanged implements TextWatcher {
        private int type;
        public TextChanged(int type){
            this.type = type;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

                switch (type) {
                    case 0:
                        YumApplication.getInstance().getpOperationsSettings().setName(s.toString());
                        data.setName(s.toString());
                        break;
                    case 1:
                        YumApplication.getInstance().getpOperationsSettings().setSeries(s.toString());
                        data.setSeries(s.toString());
                        break;
                    case 2:
                        YumApplication.getInstance().getpOperationsSettings().setStreet(s.toString());
                        data.setStreet(s.toString());
                        break;
                    case 3:
                        YumApplication.getInstance().getpOperationsSettings().setRoomNo(s.toString());
                        data.setRoomNo(s.toString());
                        break;
                    case 4:
                        YumApplication.getInstance().getpOperationsSettings().setCity(s.toString());
                        data.setCity(s.toString());
                        break;
                    case 5:
                        YumApplication.getInstance().getpOperationsSettings().setProvince(s.toString());
                        data.setProvince(s.toString());
                        break;
                    case 6:
                        YumApplication.getInstance().getpOperationsSettings().setZipCode(s.toString());
                        data.setZipCode(s.toString());
                        break;
                    case 7:
                        YumApplication.getInstance().getpOperationsSettings().setTaxRate(s.toString());
                        data.setTaxRate(s.toString());
                        break;
                }

            examinationData();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }


    private OptionsPickerView getSelector(OptionsPickerView optionsPickerView,ArrayList<ProvinceBean> optionsItems,OptionsPickerView.OnOptionsSelectListener onOptionsSelectListener){
        optionsPickerView = new OptionsPickerView.Builder(this, onOptionsSelectListener)

                .setSubmitText(getString(R.string.DONE))
                //.setCancelText("取消")
                .setTitleText("")
                .setTitleBgColor(Color.WHITE)
                .setTitleColor(Color.parseColor("#2177d5"))
                .setSubmitColor(Color.parseColor("#999999"))
                .setCancelColor(Color.parseColor("#999999"))

                .setContentTextSize(18)//设置滚轮文字大小
                .setDividerColor(Color.parseColor("#e7e7e7"))
                .setSelectOptions(0, 0)//默认选中项
                .setOutSideCancelable(true)//点击外部dismiss default true
                .isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                //.setBackgroundId(0x66000000) //设置外部遮罩颜色
                .build();

        //pvOptions.setSelectOptions(1,1);
        optionsPickerView.setPicker(optionsItems);//一级选择器*/
        return optionsPickerView;
    }

    private ArrayList<ProvinceBean> getTimeItems() {
        times = getResources().getStringArray(R.array.string_array_time);
        for (int i = 0; i < times.length; i++) {
            options1Items.add(new ProvinceBean(0, times[i], "", ""));
        }
        return options1Items;

    }
    private ArrayList<ProvinceBean> getTime2Items() {
        options2Items.add(new ProvinceBean(0, "4.99", "", ""));
        options2Items.add(new ProvinceBean(0, "5.99", "", ""));
        options2Items.add(new ProvinceBean(0, "6.99", "", ""));
        return options2Items;
    }
    private ArrayList<ProvinceBean> getTime3Items() {
        options3Items.add(new ProvinceBean(0, "1.00", "", ""));
        return options3Items;
    }
    private ArrayList<ProvinceBean> getTime4Items() {
        options4Items.add(new ProvinceBean(0, "1.0", "", ""));
        options4Items.add(new ProvinceBean(0, "2.0", "", ""));
        options4Items.add(new ProvinceBean(0, "3.0", "", ""));
        options4Items.add(new ProvinceBean(0, "4.0", "", ""));
        options4Items.add(new ProvinceBean(0, "5.0", "", ""));
        return options4Items;
    }
    private ArrayList<ProvinceBean> getTime5Items() {
        options5Items.add(new ProvinceBean(0, getString(R.string.GOOGLEMAPS), "", ""));
        return options5Items;
    }


    private boolean examinationData(){
        if (data!=null){
            if (!TextUtils.isEmpty(data.getDeliveryMoney())){
                if (!TextUtils.isEmpty(data.getMap())){
                    if (!TextUtils.isEmpty(data.getCity())){
                        if (!TextUtils.isEmpty(data.getCountryId())){//国家
                            if (data.getCurrencyId()!=-1){//货币
                               // if (!TextUtils.isEmpty(data.getDate())){//配送日期 ,
                                    if (!TextUtils.isEmpty(data.getEndTime())){//当天关店时间-比如:【20:35】开店 ,
//                                        if (!TextUtils.isEmpty(data.getKeyword())){//关键字
//                                            if (!TextUtils.isEmpty(data.getLat())){
//                                                if (!TextUtils.isEmpty(data.getLng())){
                                                    if (!TextUtils.isEmpty(data.getMoney())){//配送金额 ,
                                                        if (!TextUtils.isEmpty(data.getName())){// 商户名称
                                                            if (!TextUtils.isEmpty(data.getProvince())){//州/省
                                                                if (!TextUtils.isEmpty(data.getRange())){//配送范围
                                                                    if (!TextUtils.isEmpty(data.getSeries())){//菜系 ,
                                                                        if (!TextUtils.isEmpty(data.getStartTime())){//当天开店时间-比如:【09:30】开店 ,
                                                                           //if (!TextUtils.isEmpty(data.getStreet())){//街道地址
                                                                               if (!TextUtils.isEmpty(data.getTaxRate())){//税率 ,
                                                                                   if (!TextUtils.isEmpty(data.getTime())){//配送时间-配送格式：11:00~11:20,11:20~11:40 ,
                                                                                       if (!TextUtils.isEmpty(data.getZipCode())){//邮编
                                                                                           click = true;
                                                                                           saveBtn.setBackgroundResource(R.drawable.button_red_bright);
                                                                                       }else{
                                                                                           click = false;
                                                                                           saveBtn.setBackgroundResource(R.drawable.button_red);
                                                                                       }

                                                                                   }else{
                                                                                       click = false;
                                                                                       saveBtn.setBackgroundResource(R.drawable.button_red);
                                                                                   }

                                                                               }else{
                                                                                   click = false;
                                                                                   saveBtn.setBackgroundResource(R.drawable.button_red);
                                                                               }

//                                                                           }else{
//                                                                               click = false;
//                                                                               saveBtn.setBackgroundResource(R.drawable.button_red);
//                                                                           }
                                                                        }else{
                                                                            click = false;
                                                                            saveBtn.setBackgroundResource(R.drawable.button_red);
                                                                        }

                                                                    }else{
                                                                        click = false;
                                                                        saveBtn.setBackgroundResource(R.drawable.button_red);
                                                                    }

                                                                }else{
                                                                    click = false;
                                                                    saveBtn.setBackgroundResource(R.drawable.button_red);
                                                                }

                                                            }else{
                                                                click = false;
                                                                saveBtn.setBackgroundResource(R.drawable.button_red);
                                                            }

                                                        }else{
                                                            click = false;
                                                            saveBtn.setBackgroundResource(R.drawable.button_red);
                                                        }

                                                    }else{
                                                        click = false;
                                                        saveBtn.setBackgroundResource(R.drawable.button_red);
                                                    }
//                                                }else{
//                                                    click = false;
//                                                    saveBtn.setBackgroundResource(R.drawable.button_red);
//                                                }
//                                            }else{
//                                                click = false;
//                                                saveBtn.setBackgroundResource(R.drawable.button_red);
//                                            }

//                                        }else{
//                                            click = false;
//                                            saveBtn.setBackgroundResource(R.drawable.button_red);
//                                        }

                                    }else{
                                        click = false;
                                        saveBtn.setBackgroundResource(R.drawable.button_red);
                                    }

//                                }else{
//                                    click = false;
//                                    saveBtn.setBackgroundResource(R.drawable.button_red);
//                                }

                            }else{
                                click = false;
                                saveBtn.setBackgroundResource(R.drawable.button_red);
                            }
                        }else{
                            click = false;
                            saveBtn.setBackgroundResource(R.drawable.button_red);
                        }
                    }else{
                        click = false;
                        saveBtn.setBackgroundResource(R.drawable.button_red);
                    }
                }else{
                    click = false;
                    saveBtn.setBackgroundResource(R.drawable.button_red);
                }
            }else{
                click = false;
                saveBtn.setBackgroundResource(R.drawable.button_red);
            }
        }
        setChangeType();
        return false;
    }


    private List<SelectKewordBase.DataBean.MerchantKeywordRespResultsBean> selectDataList = new ArrayList<>();


    @OnClick({R.id.del_btn, R.id.county_btn, R.id.keyword_btn, R.id.open_btn, R.id.closed_btn
    ,R.id.map_btn,R.id.time_btn,R.id.save_btn,R.id.currency_btn,R.id.select_area_btn,R.id.delivery_btn
    ,R.id.amount_btn,R.id.street_btn})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.select_area_btn:
                option4PickerView.show();
                break;
            case R.id.delivery_btn:
                option3PickerView.show();
                break;
            case R.id.del_btn:
                if (changeType&&!getIntent().getBooleanExtra("type",false)){
                    intent = new Intent(this, PromptDialog.class);
                    intent.putExtra("content",getString(R.string.SAVEEDITRETURN));
                    startActivityForResult(intent,5);
                }else{
                    finish();
                }
                break;
            case R.id.county_btn:
                intent = new Intent(this, SelectCountryActivity.class);
                intent.putExtra("title","country");
                intent.putExtra("type",true);
                startActivityForResult(intent,1);

                break;
            case R.id.amount_btn:
                options2PickerView.show();
                break;
            case R.id.keyword_btn:

               intent = new Intent(this, SelelctKeywordActivity.class);
                if (selectDataList!=null&&selectDataList.size()>0) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list", (Serializable) selectDataList);//序列化,要注意转化(Serializable)
                    intent.putExtras(bundle);//发送数据
                }
                //intent.putStringArrayListExtra("keywords",selectDataList);
                startActivityForResult(intent,0);
                break;
            case R.id.open_btn:
                selecType = true;
                optionsPickerView.show();
                break;
            case R.id.closed_btn:
                selecType = false;
                optionsPickerView.show();
                break;
            case R.id.map_btn:
                option5PickerView.show();
                break;
            case R.id.time_btn:
                intent = new Intent(this, SelectTimeActivity.class);
                String timeStr = timeTv.getText().toString();
                if (!TextUtils.isEmpty(timeStr)){
                    String[] times = timeStr.split(",");
                    ArrayList<String> timeList = new ArrayList<>();
                    for (int i =0 ; i < times.length;i++){
                        timeList.add(times[i]);
                    }
                    intent.putStringArrayListExtra("infoList", timeList);
                }
                startActivityForResult(intent,3);
                break;
            case R.id.currency_btn:
                intent = new Intent(this,SelectCountryActivity.class);
                intent.putExtra("title","currency");
                intent.putExtra("type",true);
                startActivityForResult(intent,4);
                break;
            case R.id.save_btn:
                if (click){
                    showLoading();
                    sevaData(true);
                }

                break;
            case R.id.street_btn:
                intent = new Intent(this, SearchAddressActivity.class);
                intent.putExtra("type","operations");
                startActivityForResult(intent,2);
                overridePendingTransition(R.anim.activity_open,R.anim.in);
                break;

        }
    }
    private void sevaData(boolean con){
        data.setTime(data.getTime().replace("~","-"));
        if (con) {
            OkHttpUtils
                    .postString()
                    .url(Constant.GET_MERCHANT_SAVE)
                    .addHeader("Accept-Language", AppUtile.getLanguage())
                    .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                    .mediaType(MediaType.parse("application/json"))
                    .content(JSON.toJSONString(data))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int i) {
                            dismissLoading();
                            if (AppUtile.getNetWork(e.getMessage())){
                                showMsg(getString(R.string.NETERROR));
                            }
                        }

                        @Override
                        public void onResponse(String s, int i) {
                            dismissLoading();
                            if (!TextUtils.isEmpty(s)) {
                                BaseReturn data = JSON.parseObject(s, BaseReturn.class);
                                if (AppUtile.setCode(s,OperationsSettingsActivity.this)) {
                                    YumApplication.getInstance().getpOperationsSettings().setEndTime("");
                                    YumApplication.getInstance().getpOperationsSettings().setKeyword("");
                                    YumApplication.getInstance().getpOperationsSettings().setMap("");
                                    YumApplication.getInstance().getpOperationsSettings().setMoney("6.99");
                                    YumApplication.getInstance().getpOperationsSettings().setName("");
                                    YumApplication.getInstance().getpOperationsSettings().setRange("5.0");
                                    YumApplication.getInstance().getpOperationsSettings().setSeries("");
                                    YumApplication.getInstance().getpOperationsSettings().setStartTime("");
                                    YumApplication.getInstance().getpOperationsSettings().setTime("");
                                    YumApplication.getInstance().getpOperationsSettings().setUserId("");
                                    YumApplication.getInstance().getpOperationsSettings().setTaxRate("");
                                    YumApplication.getInstance().getpOperationsSettings().setLng("0.0");
                                    YumApplication.getInstance().getpOperationsSettings().setLat("0.0");
                                    YumApplication.getInstance().getpOperationsSettings().setCurrencyId(1);
                                    YumApplication.getInstance().getpOperationsSettings().setCountrySymbol("");
                                    YumApplication.getInstance().getpOperationsSettings().setCurrencyName("");
                                    YumApplication.getInstance().getpOperationsSettings().setCurrencyFlag("");
                                    YumApplication.getInstance().getpOperationsSettings().setCountry("");
                                    YumApplication.getInstance().getpOperationsSettings().setCountryId("1");
                                    YumApplication.getInstance().getpOperationsSettings().setProvince("");
                                    YumApplication.getInstance().getpOperationsSettings().setRoomNo("");
                                    YumApplication.getInstance().getpOperationsSettings().setZipCode("");
                                    YumApplication.getInstance().getpOperationsSettings().setCity("");
                                    YumApplication.getInstance().getpOperationsSettings().setStreet("");
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            }

                        }
                    });
        }else{
            OkHttpUtils
                    .postString()
                    .url(Constant.GET_MERCHANT_UPDATE)
                    .addHeader("Accept-Language", AppUtile.getLanguage())
                    .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                    .mediaType(MediaType.parse("application/json"))
                    .content(JSON.toJSONString(data))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int i) {
                            dismissLoading();
                            if (AppUtile.getNetWork(e.getMessage())){
                                showMsg(getString(R.string.NETERROR));
                            }
                        }

                        @Override
                        public void onResponse(String s, int i) {
                            dismissLoading();
                            if (!TextUtils.isEmpty(s)) {
                                if (AppUtile.setCode(s,OperationsSettingsActivity.this)) {
                                    YumApplication.getInstance().getpOperationsSettings().setEndTime("");
                                    YumApplication.getInstance().getpOperationsSettings().setKeyword("");
                                    YumApplication.getInstance().getpOperationsSettings().setMap("");
                                    YumApplication.getInstance().getpOperationsSettings().setMoney("6.99");
                                    YumApplication.getInstance().getpOperationsSettings().setName("");
                                    YumApplication.getInstance().getpOperationsSettings().setRange("5.0");
                                    YumApplication.getInstance().getpOperationsSettings().setSeries("");
                                    YumApplication.getInstance().getpOperationsSettings().setStartTime("");
                                    YumApplication.getInstance().getpOperationsSettings().setTime("");
                                    YumApplication.getInstance().getpOperationsSettings().setUserId("");
                                    YumApplication.getInstance().getpOperationsSettings().setTaxRate("");
                                    YumApplication.getInstance().getpOperationsSettings().setLng("0.0");
                                    YumApplication.getInstance().getpOperationsSettings().setLat("0.0");
                                    YumApplication.getInstance().getpOperationsSettings().setCurrencyId(1);
                                    YumApplication.getInstance().getpOperationsSettings().setCountrySymbol("");
                                    YumApplication.getInstance().getpOperationsSettings().setCurrencyName("");
                                    YumApplication.getInstance().getpOperationsSettings().setCurrencyFlag("");
                                    YumApplication.getInstance().getpOperationsSettings().setCountry("");
                                    YumApplication.getInstance().getpOperationsSettings().setCountryId("1");
                                    YumApplication.getInstance().getpOperationsSettings().setProvince("");
                                    YumApplication.getInstance().getpOperationsSettings().setRoomNo("");
                                    YumApplication.getInstance().getpOperationsSettings().setZipCode("");
                                    YumApplication.getInstance().getpOperationsSettings().setCity("");
                                    YumApplication.getInstance().getpOperationsSettings().setStreet("");
                                    setResult(RESULT_OK);
                                    finish();

                                }
                            }

                        }
                    });

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 5){
            if (resultCode == 1001){
                setResult(RESULT_OK);
                finish();
//                showLoading();
//                sevaData(!getIntent().getBooleanExtra("type",false));
            }
        }
        if (resultCode == RESULT_OK){
            if (requestCode == 0){
                selectDataList = (List<SelectKewordBase.DataBean.MerchantKeywordRespResultsBean>) intent.getSerializableExtra("list");//获取list方式
                if (selectDataList != null && selectDataList.size() > 0) {
                    String names = "" ;
                    for (SelectKewordBase.DataBean.MerchantKeywordRespResultsBean item : selectDataList) {
                        names = names+(item.getKeyword()+",");
                    }
                    if (names.length()>0) {
                        keywordEd.setText(names.substring(0, names.length() - 1));
                        data.setKeyword(names.substring(0, names.length() - 1));
                        YumApplication.getInstance().getpOperationsSettings().setKeyword(names.substring(0, names.length() - 1));
                    }else{
                        keywordEd.setText("");
                        data.setKeyword("");
                        YumApplication.getInstance().getpOperationsSettings().setKeyword("");
                    }

                }else{
                    keywordEd.setText("");
                    data.setKeyword("");
                    YumApplication.getInstance().getpOperationsSettings().setKeyword("");
                }
            }else if (requestCode == 1){
                if (intent!=null){
                    YumApplication.getInstance().getpOperationsSettings().setCountry(intent.getStringExtra("countryName")+"");
                    YumApplication.getInstance().getpOperationsSettings().setCountryId(intent.getStringExtra("countryId")+"");
                    data.setCountryId(intent.getStringExtra("countryId")+"");
                    countyEd.setText(intent.getStringExtra("countryName"));
                }
                examinationData();
            }else if (requestCode == 2){
                if (intent!=null){
                    String address = intent.getStringExtra("address2");
                    if (!TextUtils.isEmpty(address)){
                        String[] addressStrArray = address.split(",");
                        if (addressStrArray.length>2){
                            data.setStreet(addressStrArray[0]);
                            YumApplication.getInstance().getpOperationsSettings().setStreet(addressStrArray[0]);
                            streetEd.setText(addressStrArray[0]);
                            cityEd.setText(addressStrArray[1]);
                            if (!TextUtils.isEmpty(addressStrArray[2])){
                                String[] provinceStrArray = addressStrArray[2].trim().split(" ");
                                if (provinceStrArray.length>1){
                                    provinceEd.setText(provinceStrArray[0]);
                                    zipEd.setText(provinceStrArray[1]);
                                }
                            }

                        }
                    }
                    data.setLat(intent.getStringExtra("lat"));
                    data.setLng(intent.getStringExtra("lng"));
                    YumApplication.getInstance().getpOperationsSettings().setLat(intent.getStringExtra("lat"));
                    YumApplication.getInstance().getpOperationsSettings().setLng(intent.getStringExtra("lng"));
//                    data.setStreet(intent.getStringExtra("address"));
//                    cityEd.setText(intent.getStringExtra("city"));
//                    provinceEd.setText(intent.getStringExtra("province"));
//                    zipEd.setText(intent.getStringExtra("zip"));
//                    streetEd.setText(intent.getStringExtra("name"));
                }
                examinationData();
            }else if (requestCode ==3){
                if (intent!=null) {
                    YumApplication.getInstance().getpOperationsSettings().setTime(intent.getStringExtra("data"));
                    timeTv.setText(intent.getStringExtra("data"));
                    data.setTime(intent.getStringExtra("data"));
                }
            }else if (requestCode == 4){
                if (intent!=null){
                    countrySymbol = intent.getStringExtra("countrySymbol");
                    YumApplication.getInstance().getpOperationsSettings().setCurrencyId(Integer.valueOf(!TextUtils.isEmpty(intent.getStringExtra("countryId"))?intent.getStringExtra("countryId"):"-1"));
                    data.setCurrencyId(Integer.valueOf(!TextUtils.isEmpty(intent.getStringExtra("countryId"))?intent.getStringExtra("countryId"):"-1"));
                    YumApplication.getInstance().getpOperationsSettings().setCurrencyName(intent.getStringExtra("countryName"));
                    YumApplication.getInstance().getpOperationsSettings().setCurrencyFlag(intent.getStringExtra("countryFlag"));
                    YumApplication.getInstance().getpOperationsSettings().setCountrySymbol(countrySymbol);
                    currencyTv.setText(intent.getStringExtra("countryName")+"("+countrySymbol+")");
                    amountEd.setText(countrySymbol+" " +data.getMoney());
                    deliveryEd.setText(countrySymbol+" " +data.getDeliveryMoney());
                    Picasso.get().load(intent.getStringExtra("countryFlag")).placeholder(R.mipmap.timg).into(flagIv);
                    click = true;
                    saveBtn.setBackgroundResource(R.drawable.button_red_bright);
                }
            }else{
                setResult(RESULT_OK);
                finish();
            }
            examinationData();

        }
    }
}
