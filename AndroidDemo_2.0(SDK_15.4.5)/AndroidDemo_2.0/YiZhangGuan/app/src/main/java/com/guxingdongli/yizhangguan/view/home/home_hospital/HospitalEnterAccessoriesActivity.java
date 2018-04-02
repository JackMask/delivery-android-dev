package com.guxingdongli.yizhangguan.view.home.home_hospital;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ab.util.AbToastUtil;
import com.alibaba.fastjson.JSON;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.YiZhangGuanApplication;
import com.guxingdongli.yizhangguan.model.FeeNameBase;
import com.guxingdongli.yizhangguan.model.HospitalFillInRepairOrderCostBase;
import com.guxingdongli.yizhangguan.model.OriginBase;
import com.guxingdongli.yizhangguan.model.ProvinceBean;
import com.guxingdongli.yizhangguan.model.ReasonAnalysisBase;
import com.guxingdongli.yizhangguan.model.ServiceLevelBase;
import com.guxingdongli.yizhangguan.model.SourcesOfFundsBase;
import com.guxingdongli.yizhangguan.model.TestFittingBase;
import com.guxingdongli.yizhangguan.model.UnitBase;
import com.guxingdongli.yizhangguan.util.AppUtile;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.GetPickerStrCallBack;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;
import com.yuxiaolong.yuxiandelibrary.pickerview.OptionsPickerView;
import com.yuxiaolong.yuxiandelibrary.view.ProhibitSlideGridView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * @author 余先德
 * @data 2018/3/20
 */

public class HospitalEnterAccessoriesActivity extends YiZhangGuanActivity {

    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.right_tv_btn)
    TextView rightTvBtn;
    @Bind(R.id.pic_grid)
    ProhibitSlideGridView picGrid;
    @Bind(R.id.maintain_layout)
    LinearLayout maintainLayout;
    @Bind(R.id.fitting_name)
    EditText fittingName;
    @Bind(R.id.specification_tv)
    EditText specificationTv;
    @Bind(R.id.fitting_layout)
    LinearLayout fittingLayout;
    @Bind(R.id.model_tv)
    EditText modelTv;
    @Bind(R.id.brand_tv)
    EditText brandTv;
    @Bind(R.id.serial_number_tv)
    EditText serialNumberTv;

    @Bind(R.id.factory_tv)
    EditText factoryTv;
    @Bind(R.id.supplier_tv)
    EditText supplierTv;
    @Bind(R.id.quantity_tv)
    EditText quantityTv;
    @Bind(R.id.unit_price_tv)
    EditText unitPriceTv;

    @Bind(R.id.one_btn)
    RelativeLayout one_btn;
    @Bind(R.id.two_btn)
    RelativeLayout two_btn;
    @Bind(R.id.three_btn)
    RelativeLayout three_btn;
    @Bind(R.id.four_btn)
    RelativeLayout four_btn;
    @Bind(R.id.five_btn)
    RelativeLayout five_btn;
    @Bind(R.id.unit_tv)
    TextView unitTv;
    @Bind(R.id.origin_tv)
    TextView originTv;
    @Bind(R.id.scroll_layout)
    ScrollView scrollView;



    @Bind(R.id.add_btn)
    LinearLayout add_btn;



    private OptionsPickerView optionsPickerView;
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private List<View> btnList = new ArrayList<>();
    private List<View> maintainTextViewList;
    private List<HospitalFillInRepairOrderCostBase.ReplacingFittingListBean> pageData = new ArrayList<>(5);
    private int index = 0 , clickIndex = 0,showAllIndex = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_cost);
        ButterKnife.bind(this);
        setView();
    }

    private void setView(){
        rightTvBtn.setVisibility(View.VISIBLE);
        rightTvBtn.setText("录入");
        rightTvBtn.setTextColor(Color.parseColor("#2ea1fb"));
        fittingLayout.setVisibility(View.VISIBLE);
        maintainLayout.setVisibility(View.GONE);
        titleText.setText("录入配件信息");
        btnList.add(one_btn);
        btnList.add(two_btn);
        btnList.add(three_btn);
        btnList.add(four_btn);
        btnList.add(five_btn);

        bindMonitor();
        getDictionaryList("产地");
        getDictionaryList("单位");
        setRestore();
    }
    //数据回填
    @SuppressWarnings("unchecked")
    private void setRestore(){
        List<HospitalFillInRepairOrderCostBase.ReplacingFittingListBean> oldData = (List<HospitalFillInRepairOrderCostBase.ReplacingFittingListBean>)getIntent().getSerializableExtra("data");
        if (oldData!=null&&oldData.size()>0){
            index = oldData.size()-1;
            clickIndex = oldData.size()-1;
            for (int i = 0 ; i < oldData.size();i++){
                btnList.get(i).setVisibility(View.VISIBLE);
                pageData.add(oldData.get(i));
            }
            for (int i =0 ; i < btnList.size();i++){
                btnList.get(i).setBackgroundResource(R.drawable.button_gray3_white_stroke_2dp);
            }
            btnList.get(index).setBackgroundColor(Color.WHITE);
            getAddPageData(index);
        }
    }

    private void bindMonitor(){
        for (int i = 0 ; i < btnList.size();i++){
            btnList.get(i).setOnClickListener(new tabClick(i));
        }
    }

    private class tabClick implements View.OnClickListener{
        int con;
        public tabClick(int con){
            this.con = con;
        }

        @Override
        public void onClick(View view) {
            for (int i =0 ; i < btnList.size();i++){
                btnList.get(i).setBackgroundResource(R.drawable.button_gray3_white_stroke_2dp);
            }
            btnList.get(con).setBackgroundColor(Color.WHITE);
            getSavePageData(index);
            index = con;
            getAddPageData(index);
        }
    }

    @OnClick({R.id.return_btn, R.id.right_tv_btn,R.id.cost_name_btn,
            R.id.cost_source_btn, R.id.origin_btn, R.id.unit_btn,
            R.id.add_btn,R.id.one_del_btn,R.id.two_del_btn,R.id.three_del_btn,R.id.four_del_btn
            ,R.id.five_del_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.one_del_btn:

                delData(0);

                break;
            case R.id.two_del_btn:

                delData(1);

                break;
            case R.id.three_del_btn:

                delData(2);

                break;
            case R.id.four_del_btn:

                delData(3);

                break;
            case R.id.five_del_btn:

                delData(4);

                break;
            case R.id.return_btn:
                finish();
                break;
            case R.id.right_tv_btn://确定
                int showBtn = 0;
                boolean noData = true;
                for (View btnView:btnList){
                    if (btnView.getVisibility() == View.VISIBLE){
                        showBtn++;
                    }
                }

                getSavePageData(index);
                if (showBtn == pageData.size()){
                    for (HospitalFillInRepairOrderCostBase.ReplacingFittingListBean item:pageData){
                        if (!tesingData(item)){
                            noData = false;
                            break;
                        }
                    }
                }else{
                    if (!tesingData()){
                        noData = false;
                    }
                }
                for (int i = 0 ; i < pageData.size();i++){
                    pageData.get(i).setSupplierId("0");
                    pageData.get(i).setManufacturerId("0");
                    pageData.get(i).setFactoryTime("1990-01-01 00:00:00");
                }

                if (noData){
                    Intent intent = new Intent();
                    intent.putExtra("data", (Serializable) pageData);
                    setResult(1002, intent);
                    finish();
                }
                break;
            case R.id.add_btn://添加
                if (getData()) {
                    clickIndex++;
                    scrollView.fullScroll(ScrollView.FOCUS_UP);
                    if (clickIndex < 5) {
                        btnList.get(clickIndex).setVisibility(View.VISIBLE);
                        for (int i =0 ; i < btnList.size();i++){
                            btnList.get(i).setBackgroundResource(R.drawable.button_gray3_white_stroke_2dp);
                        }
                        btnList.get(clickIndex).setBackgroundColor(Color.WHITE);
                        index = clickIndex;
                        clearMaintain();
                    }
                    showAllIndex ++;
                }
                break;


            case R.id.origin_btn://产地
                if (setTestUserData()) {
                    optionsPickerView = AppUtile.initOptionsPicker(this, optionsPickerView,
                            options1Items, "请选择产地", new GetPickerStrCallBack() {
                                @Override
                                public void getStr(String returnStr, long id) {
                                    originTv.setText(returnStr);
                                    String idStr = String.valueOf(id);
                                    originTv.setTag(idStr);
                                }
                            });
                    optionsPickerView.show();
                }
                break;
            case R.id.unit_btn://单位
                if(setTestUnitData()) {
                    optionsPickerView = AppUtile.initOptionsPicker(this, optionsPickerView,
                            options1Items, "请选择单位", new GetPickerStrCallBack() {
                                @Override
                                public void getStr(String returnStr, long id) {
                                    unitTv.setText(returnStr);
                                    String idStr = String.valueOf(id);
                                    unitTv.setTag(idStr);
                                }
                            });
                    optionsPickerView.show();
                }
                break;
        }
    }

    private void delData(int index){
        try {
            pageData.remove(index);
        }catch (Exception e){

        }

        for (int i = btnList.size()-1;i>0;i--){
            if (btnList.get(i).getVisibility()==View.VISIBLE){
                btnList.get(i).setVisibility(View.GONE);
                break;
            }
        }
        this.index -=1;
    }

    /**
     * 缓存当前页面的数据
     * @return
     */
    private void getSavePageData(int index){

        HospitalFillInRepairOrderCostBase.ReplacingFittingListBean nowPage = new HospitalFillInRepairOrderCostBase.ReplacingFittingListBean();
        nowPage.setName(fittingName.getText().toString());
        nowPage.setSpecification(specificationTv.getText().toString());
        nowPage.setModel(modelTv.getText().toString());
        nowPage.setBrand(brandTv.getText().toString());
        nowPage.setPlaceOriginName(originTv.getText().toString());
        nowPage.setPlaceOrigin((String)originTv.getTag());
        nowPage.setManufacturer(factoryTv.getText().toString());
        nowPage.setManufacturerId(factoryTv.getText().toString());
        nowPage.setManufacturerId("0");
        nowPage.setSupplierId("0");
        nowPage.setSupplierName(supplierTv.getText().toString());
        nowPage.setQuantity(Integer.valueOf(!TextUtils.isEmpty(quantityTv.getText().toString())?quantityTv.getText().toString():"0"));
        nowPage.setPrice(Integer.valueOf(!TextUtils.isEmpty(unitPriceTv.getText().toString())?unitPriceTv.getText().toString():"0"));
        nowPage.setUnitName(unitTv.getText().toString());
        nowPage.setUnitId((String)unitTv.getTag());
        nowPage.setSerialNumber(serialNumberTv.getText().toString());
        delExcessData(index);
        pageData.add(index, nowPage);
    }

    /**
     * 维修费用页面数据清空
     */
    private void clearMaintain(){
        fittingName.setText("");
        specificationTv.setText("");
        modelTv.setText("");
        brandTv.setText("");
        originTv.setText("");
        factoryTv.setText("");
        supplierTv.setText("");
        quantityTv.setText("");
        unitPriceTv.setText("");
        unitTv.setText("");
        serialNumberTv.setText("");
    }

    private void delExcessData(int index){
        try{
            pageData.remove(index);
        }catch (Exception e){

        }
    }

    /**
     * 数据还原
     *
     */
    private void getAddPageData(int index){
        fittingName.setText(pageData.get(index).getName());
        specificationTv.setText(pageData.get(index).getSpecification());
        modelTv.setText(pageData.get(index).getModel());
        brandTv.setText(pageData.get(index).getBrand());
        originTv.setText(pageData.get(index).getPlaceOriginName());
        originTv.setTag(pageData.get(index).getPlaceOrigin());
        factoryTv.setText(pageData.get(index).getUnitName());
        supplierTv.setText(pageData.get(index).getSupplierName());
        quantityTv.setText(pageData.get(index).getQuantity()==0?"":pageData.get(index).getQuantity()+"");
        unitPriceTv.setText(pageData.get(index).getPrice() == 0?"":pageData.get(index).getPrice()+"");
        unitTv.setText(pageData.get(index).getUnitName()+"");
        unitTv.setTag(pageData.get(index).getUnitId()+"");
        serialNumberTv.setText(pageData.get(index).getSerialNumber()+"");


    }
    private boolean tesingData(HospitalFillInRepairOrderCostBase.ReplacingFittingListBean nowPage){
        if (!AppUtile.checkNull(this,nowPage.getName(),"",false)){
            return false;
        }
        if (!AppUtile.checkNull(this,nowPage.getSpecification(),"",false)){
            return false;
        }
        if (!AppUtile.checkNull(this, nowPage.getModel(),"",false)){
            return false;
        }
        if (!AppUtile.checkNull(this, nowPage.getBrand(),"",false)){
            return false;
        }
        if (!AppUtile.checkNull(this,nowPage.getPlaceOrigin(),"",false)){
            return false;
        }
        if (!AppUtile.checkNull(this,nowPage.getManufacturer(),"",false)){
            return false;
        }
        if (!AppUtile.checkNull(this,nowPage.getSupplierName(),"",false)){
            return false;
        }
        if (!AppUtile.checkNull(this,nowPage.getQuantity()==0?"":nowPage.getQuantity()+"","",false)){
            return false;
        }
        if (!AppUtile.checkNull(this,nowPage.getPrice() == 0?"":nowPage.getPrice()+"","",false)){
            return false;
        }
        if (!AppUtile.checkNull(this,nowPage.getUnitName()+"","",false)){
            return false;
        }
        if (!AppUtile.checkNull(this,nowPage.getSerialNumber()+"","",false)){
            return false;
        }
        return true;
    }
    private boolean tesingData(){
        HospitalFillInRepairOrderCostBase.ReplacingFittingListBean nowPage = new HospitalFillInRepairOrderCostBase.ReplacingFittingListBean();
        if (AppUtile.checkNull(this,fittingName.getText().toString(),"",false)){
            nowPage.setName(fittingName.getText().toString());
        }else{
            return false;
        }
        if (AppUtile.checkNull(this,specificationTv.getText().toString(),"",false)){
            nowPage.setSpecification(specificationTv.getText().toString());
        }else{
            return false;
        }
        if (AppUtile.checkNull(this,modelTv.getText().toString(),"",false)){
            nowPage.setModel(modelTv.getText().toString());
        }else{
            return false;
        }
        if (AppUtile.checkNull(this,brandTv.getText().toString(),"",false)){
            nowPage.setBrand(brandTv.getText().toString());
        }else{
            return false;
        }
        if (AppUtile.checkNull(this,originTv.getText().toString(),"",false)){
            nowPage.setPlaceOrigin((String)originTv.getTag());
            nowPage.setPlaceOriginName(originTv.getText().toString());
        }else{
            return false;
        }
        if (AppUtile.checkNull(this,factoryTv.getText().toString(),"",false)){
            nowPage.setManufacturer(factoryTv.getText().toString());
        }else{
            return false;
        }
        if (AppUtile.checkNull(this,supplierTv.getText().toString(),"",false)){
            nowPage.setSupplierName(supplierTv.getText().toString());
        }else{
            return false;
        }
        if (AppUtile.checkNull(this,quantityTv.getText().toString(),"",false)){
            nowPage.setQuantity(Integer.valueOf(quantityTv.getText().toString()));
        }else{
            return false;
        }
        if (AppUtile.checkNull(this,unitPriceTv.getText().toString(),"",false)){
            nowPage.setPrice(Integer.valueOf(unitPriceTv.getText().toString()));
        }else{
            return false;
        }
        if (AppUtile.checkNull(this,unitTv.getText().toString(),"",false)){
            nowPage.setUnitName(unitTv.getText().toString());
            nowPage.setUnitId((String)unitTv.getTag());
        }else{
            return false;
        }
        if (AppUtile.checkNull(this,serialNumberTv.getText().toString(),"",false)){
            nowPage.setSerialNumber(serialNumberTv.getText().toString());
        }else{
            return false;
        }


        pageData.add(index, nowPage);
        return true;
    }

    private boolean testingNowData(){
        int showNum = 0 ;
        for (View btn : btnList){
            if (btn.getVisibility() == View.VISIBLE) {
                showNum++;
            }
        }
        if (pageData.get(showNum-2)!=null){
            if (!AppUtile.checkNull(this,pageData.get(showNum-2).getName(),"",false)){
                return false;
            }
            if (!AppUtile.checkNull(this,pageData.get(showNum-2).getSpecification(),"",false)){
                return false;
            }
            if (!AppUtile.checkNull(this,pageData.get(showNum-2).getModel()+"","",false)){
                return false;
            }
            if (!AppUtile.checkNull(this,pageData.get(showNum-2).getBrand()+"","",false)){
                return false;
            }
            if (!AppUtile.checkNull(this,pageData.get(showNum-2).getPlaceOrigin()+"","",false)){
                return false;
            }
            if (!AppUtile.checkNull(this,pageData.get(showNum-2).getManufacturer()+"","",false)){
                return false;
            }
            if (!AppUtile.checkNull(this,pageData.get(showNum-2).getSupplierName()+"","",false)){
                return false;
            }
            if (!AppUtile.checkNull(this,pageData.get(showNum-2).getQuantity()+"","",false)){
                return false;
            }
            if (!AppUtile.checkNull(this,pageData.get(showNum-2).getPrice()+"","",false)){
                return false;
            }
            if (!AppUtile.checkNull(this,pageData.get(showNum-2).getUnitName()+"","",false)){
                return false;
            }
            if (!AppUtile.checkNull(this,pageData.get(showNum-2).getSerialNumber()+"","",false)){
                return false;
            }
        }
        return true;

    }

    /**
     * 空值判断
     * @return
     */
    private boolean getData(){

        HospitalFillInRepairOrderCostBase.ReplacingFittingListBean nowFittingData = new HospitalFillInRepairOrderCostBase.ReplacingFittingListBean();
        if (!TextUtils.isEmpty(fittingName.getText().toString()))
        if (AppUtile.checkNull(this,fittingName.getText().toString(),"",false)){
            nowFittingData.setName(fittingName.getText().toString());
        }else{
            return false;
        }
        if (AppUtile.checkNull(this,specificationTv.getText().toString(),"",false)){
            nowFittingData.setSpecification(specificationTv.getText().toString());
        }else{
            return false;
        }
        if (AppUtile.checkNull(this,modelTv.getText().toString(),"",false)){
            nowFittingData.setModel(modelTv.getText().toString());
        }else{
            return false;
        }
        if (AppUtile.checkNull(this,brandTv.getText().toString(),"",false)){
            nowFittingData.setBrand(brandTv.getText().toString());
        }else{
            return false;
        }
        if (AppUtile.checkNull(this,originTv.getText().toString(),"",false)){
            nowFittingData.setPlaceOrigin((String)originTv.getTag());
            nowFittingData.setPlaceOriginName(originTv.getText().toString());
        }else{
            return false;
        }
        if (AppUtile.checkNull(this,factoryTv.getText().toString(),"",false)){
            nowFittingData.setManufacturer(factoryTv.getText().toString());
        }else{
            return false;
        }
        if (AppUtile.checkNull(this,supplierTv.getText().toString(),"",false)){
            nowFittingData.setSupplierName(supplierTv.getText().toString());
        }else{
            return false;
        }
        if (AppUtile.checkNull(this,quantityTv.getText().toString(),"",false)){
            nowFittingData.setQuantity(Integer.valueOf(quantityTv.getText().toString()));
        }else{
            return false;
        }
        if (AppUtile.checkNull(this,unitPriceTv.getText().toString(),"",false)){
            nowFittingData.setPrice(Integer.valueOf(unitPriceTv.getText().toString()));
        }else{
            return false;
        }
        if (AppUtile.checkNull(this,unitTv.getText().toString(),"",false)){
            nowFittingData.setUnitName(unitTv.getText().toString());
            nowFittingData.setUnitId((String)unitTv.getTag());
        }else{
            return false;
        }
        if (AppUtile.checkNull(this,serialNumberTv.getText().toString(),"",false)){
            nowFittingData.setSerialNumber(serialNumberTv.getText().toString());
        }else{
            return false;
        }
        if (pageData.size()>1)
            if (!testingNowData()){
                return false;
            }
        delExcessData(index);
        pageData.add(index, nowFittingData);
        return true;
    }
    private void getDictionaryList(final String dicType){
        RequestBody formBody = new  FormBody.Builder()
                .add("dicType", dicType)
                .build();
        HttpUtile httpUtile = new HttpUtile(this, Constant.DOMAIN_NAME + Constant.GETDICTIONARYLIST, formBody, new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {
                if (dicType.equals("产地")){
                    originBase = JSON.parseObject(returnStr,OriginBase.class);
                    //YiZhangGuanApplication.getInstance().setOrigingList(data.getData());
                }else if (dicType.equals("单位")){
                    unitBase = JSON.parseObject(returnStr,UnitBase.class);
                    //YiZhangGuanApplication.getInstance().setUnitList(data.getData());
                }
            }


            @Override
            public void getReturnStrFailure(String returnStr) {


            }

            @Override
            public void getErrorStr(String errorStr) {

            }
        },false);

    }
    private OriginBase originBase;
    private UnitBase unitBase;
    /**
     * 获取产地数据
     */
    private boolean setTestUserData() {

        options1Items.clear();
        if (originBase != null) {
            if (originBase.getData() != null && originBase.getData().size() > 0) {
                for (int i = 0; i < originBase.getData().size(); i++)
                    options1Items.add(new ProvinceBean(originBase.getData().get(i).getId(), originBase.getData().get(i).getDicValue(), "", ""));
                return true;
            }
        }
                AbToastUtil.showToast(this, "暂无数据");
                return false;
        }

    /**
     * 获取单位数据
     */
    private boolean setTestUnitData() {
        options1Items.clear();
        if (unitBase!=null) {
            if (unitBase.getData() != null && unitBase.getData().size() > 0) {
                for (int i = 0; i < unitBase.getData().size(); i++)
                    options1Items.add(new ProvinceBean(unitBase.getData().get(i).getId(), unitBase.getData().get(i).getDicValue(), "", ""));
                return true;
            }
        }
            AbToastUtil.showToast(this,"暂无数据");
            return false;
    }

}
