package com.guxingdongli.yizhangguan.view.home.home_merchants;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dmcbig.mediapicker.PickerActivity;
import com.dmcbig.mediapicker.PickerConfig;
import com.dmcbig.mediapicker.entity.Media;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.PicSelectAdapter;
import com.guxingdongli.yizhangguan.model.ProvinceBean;
import com.guxingdongli.yizhangguan.model.TestFittingBase;
import com.guxingdongli.yizhangguan.model.TestMaintainBase;
import com.guxingdongli.yizhangguan.util.AppUtile;
import com.guxingdongli.yizhangguan.util.GetPickerStrCallBack;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.yuxiaolong.yuxiandelibrary.ActionSheetDialog;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;
import com.yuxiaolong.yuxiandelibrary.pickerview.OptionsPickerView;
import com.yuxiaolong.yuxiandelibrary.view.ProhibitSlideGridView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 维修费用&配件费用
 * Created by jackmask on 2018/3/9.
 */

public class EnterCostActivity extends YiZhangGuanActivity implements TakePhoto.TakeResultListener,InvokeListener {


    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.right_tv_btn)
    TextView rightTvBtn;
    @Bind(R.id.cost_name)
    TextView costName;
    @Bind(R.id.cost_source)
    TextView costSource;
    @Bind(R.id.maintain_quotation)
    EditText maintainQuotation;
    @Bind(R.id.actual_quotation)
    EditText actualQuotation;
    @Bind(R.id.pic_grid)
    ProhibitSlideGridView picGrid;
    @Bind(R.id.maintain_layout)
    LinearLayout maintainLayout;
    @Bind(R.id.fitting_name)
    EditText fittingName;
    @Bind(R.id.specification_tv)
    EditText specificationTv;
    @Bind(R.id.model_tv)
    EditText modelTv;
    @Bind(R.id.brand_tv)
    EditText brandTv;
    @Bind(R.id.origin_tv)
    TextView originTv;
    @Bind(R.id.factory_tv)
    EditText factoryTv;
    @Bind(R.id.supplier_tv)
    EditText supplierTv;
    @Bind(R.id.quantity_tv)
    EditText quantityTv;
    @Bind(R.id.unit_price_tv)
    EditText unitPriceTv;
    @Bind(R.id.unit_tv)
    TextView unitTv;
    @Bind(R.id.serial_number_tv)
    EditText serialNumberTv;
    @Bind(R.id.fitting_layout)
    LinearLayout fittingLayout;

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



    @Bind(R.id.add_btn)
    LinearLayout add_btn;


    boolean type = true; // true维修费用,false配件费用
    private PicSelectAdapter adapter;
    ArrayList<Media> select = new ArrayList<>();
    private List<String> picStrs = new ArrayList<>();
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private static final int CAMERA_STORAGES = 2;
    private OptionsPickerView optionsPickerView;
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private List<View> btnList = new ArrayList<>();
    private List<View> maintainTextViewList;
    private int tapCon = 0;

    private List<TestMaintainBase> maintainBases = new ArrayList<>();
    private TestMaintainBase nowMaintainData;
    private TestMaintainBase pageMaintainData;
    private List<TestFittingBase> fittingBases = new ArrayList<>();
    private TestFittingBase nowFittingData;
    private TestFittingBase pageFittingData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_cost);
        ButterKnife.bind(this);
        setView();
    }

    private void setView() {
        type = getIntent().getBooleanExtra("type", false);
        rightTvBtn.setVisibility(View.VISIBLE);
        rightTvBtn.setText("录入");
        rightTvBtn.setTextColor(Color.parseColor("#2ea1fb"));
        if (type) {
            fittingLayout.setVisibility(View.GONE);
            maintainLayout.setVisibility(View.VISIBLE);
            maintainTextViewList = new ArrayList<>();
            maintainTextViewList.add(costName);
            maintainTextViewList.add(costSource);
            maintainTextViewList.add(maintainQuotation);
            maintainTextViewList.add(actualQuotation);
            titleText.setText("录入设备维修费");
            picStrs.add("btn");
            adapter = new PicSelectAdapter(picStrs);
            picGrid.setNumColumns(4);
            picGrid.setAdapter(adapter);
        } else {
            fittingLayout.setVisibility(View.VISIBLE);
            maintainLayout.setVisibility(View.GONE);
            titleText.setText("录入配件信息");
        }
        btnList.add(one_btn);
        btnList.add(two_btn);
        btnList.add(three_btn);
        btnList.add(four_btn);
        btnList.add(five_btn);

        bindMonitor();
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
            storagePageData();
            if (type){
                try {
                    setStoragePageMaintainData(maintainBases.get(con));
                }catch (Exception e){
                    setStoragePageMaintainData(pageMaintainData);
                }

            }else{
                try {
                    setStoragePageFittingData(fittingBases.get(con));
                }catch (Exception e){
                    setStoragePageFittingData(pageFittingData);
                }
            }
        }
    }

    /**
     * 设置当前设备维修费数据
     * @param data
     */
    private void setStoragePageMaintainData(TestMaintainBase data){
        costName.setText(data.getCost_name());
        costSource.setText(data.getCost_source());
        maintainQuotation.setText(data.getMaintain_quotation());
        actualQuotation.setText(data.getActual_quotation());

    }
    /**
     * 设置当前配件数据
     * @param data
     */
    private void setStoragePageFittingData(TestFittingBase data){
        fittingName.setText(data.getFitting_name());
        specificationTv.setText(data.getSpecification_tv());
        modelTv.setText(data.getModel_tv());
        brandTv.setText(data.getBrand_tv());
        originTv.setText(data.getOrigin_tv());
        factoryTv.setText(data.getFactory_tv());
        supplierTv.setText(data.getSupplier_tv());
        quantityTv.setText(data.getQuantity_tv());
        unitPriceTv.setText(data.getUnit_price_tv());
        unitTv.setText(data.getUnit_tv());
        serialNumberTv.setText(data.getSerial_number_tv());

    }

    private void storagePageData(){
        if (type){
            pageMaintainData = new TestMaintainBase();
            pageMaintainData.setCost_name(costName.getText().toString());
            pageMaintainData.setCost_source(costSource.getText().toString());
            pageMaintainData.setMaintain_quotation(maintainQuotation.getText().toString());
            pageMaintainData.setActual_quotation(actualQuotation.getText().toString());
        }else{
            pageFittingData = new TestFittingBase();
            pageFittingData.setFitting_name(fittingName.getText().toString());
            pageFittingData.setSpecification_tv(specificationTv.getText().toString());
            pageFittingData.setModel_tv(modelTv.getText().toString());
            pageFittingData.setBrand_tv(brandTv.getText().toString());
            pageFittingData.setOrigin_tv(originTv.getText().toString());
            pageFittingData.setFactory_tv(factoryTv.getText().toString());
            pageFittingData.setSupplier_tv(supplierTv.getText().toString());
            pageFittingData.setQuantity_tv(quantityTv.getText().toString());
            pageFittingData.setUnit_price_tv(unitPriceTv.getText().toString());
            pageFittingData.setUnit_tv(unitTv.getText().toString());
            pageFittingData.setSerial_number_tv(serialNumberTv.getText().toString());
        }
    }

    /**
     * 空值判断
     * @return
     */
    private boolean getData(){
        if (type){
            nowMaintainData = new TestMaintainBase();
            if (AppUtile.checkNull(this,costName.getText().toString(),"",false)){
                nowMaintainData.setCost_name(costName.getText().toString());
            }else{
                return false;
            }
            if (AppUtile.checkNull(this,costSource.getText().toString(),"",false)){
                nowMaintainData.setCost_source(costSource.getText().toString());
            }else{
                return false;
            }
            if (AppUtile.checkNull(this,maintainQuotation.getText().toString(),"",false)){
                nowMaintainData.setMaintain_quotation(maintainQuotation.getText().toString());
            }else{
                return false;
            }
            if (AppUtile.checkNull(this,actualQuotation.getText().toString(),"",false)){
                nowMaintainData.setActual_quotation(actualQuotation.getText().toString());
            }else{
                return false;
            }
            maintainBases.add(nowMaintainData);
            return true;
        }else{
            nowFittingData = new TestFittingBase();
            if (AppUtile.checkNull(this,fittingName.getText().toString(),"",false)){
                nowFittingData.setFitting_name(fittingName.getText().toString());
            }else{
                return false;
            }
            if (AppUtile.checkNull(this,specificationTv.getText().toString(),"",false)){
                nowFittingData.setSpecification_tv(specificationTv.getText().toString());
            }else{
                return false;
            }
            if (AppUtile.checkNull(this,modelTv.getText().toString(),"",false)){
                nowFittingData.setModel_tv(modelTv.getText().toString());
            }else{
                return false;
            }
            if (AppUtile.checkNull(this,brandTv.getText().toString(),"",false)){
                nowFittingData.setBrand_tv(brandTv.getText().toString());
            }else{
                return false;
            }
            if (AppUtile.checkNull(this,originTv.getText().toString(),"",false)){
                nowFittingData.setOrigin_tv(originTv.getText().toString());
            }else{
                return false;
            }
            if (AppUtile.checkNull(this,factoryTv.getText().toString(),"",false)){
                nowFittingData.setFactory_tv(factoryTv.getText().toString());
            }else{
                return false;
            }
            if (AppUtile.checkNull(this,supplierTv.getText().toString(),"",false)){
                nowFittingData.setSupplier_tv(supplierTv.getText().toString());
            }else{
                return false;
            }
            if (AppUtile.checkNull(this,quantityTv.getText().toString(),"",false)){
                nowFittingData.setQuantity_tv(quantityTv.getText().toString());
            }else{
                return false;
            }
            if (AppUtile.checkNull(this,unitPriceTv.getText().toString(),"",false)){
                nowFittingData.setUnit_price_tv(unitPriceTv.getText().toString());
            }else{
                return false;
            }
            if (AppUtile.checkNull(this,unitTv.getText().toString(),"",false)){
                nowFittingData.setUnit_tv(unitTv.getText().toString());
            }else{
                return false;
            }
            if (AppUtile.checkNull(this,serialNumberTv.getText().toString(),"",false)){
                nowFittingData.setSerial_number_tv(serialNumberTv.getText().toString());
            }else{
                return false;
            }
            fittingBases.add(nowFittingData);
            return true;
        }
    }

    /**
     * 维修费用页面数据清空
     */
    private void clearMaintain(){
        costName.setText("");
        costSource.setText("");
        maintainQuotation.setText("");
        actualQuotation.setText("");
    }
    /**
     * 配件页面数据清空
     */
    private void clearFitting(){
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

    @OnClick({R.id.return_btn, R.id.right_tv_btn,R.id.cost_name_btn,
            R.id.cost_source_btn, R.id.origin_btn, R.id.unit_btn,
            R.id.add_btn,R.id.one_del_btn,R.id.two_del_btn,R.id.three_del_btn,R.id.four_del_btn
            ,R.id.five_del_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.one_del_btn:

                break;
            case R.id.two_del_btn:

                break;
            case R.id.three_del_btn:

                break;
            case R.id.four_del_btn:

                break;
            case R.id.five_del_btn:

                break;
            case R.id.return_btn:
                finish();
                break;
            case R.id.right_tv_btn://确定

                break;
            case R.id.add_btn://添加
                if (getData()) {
                    tapCon++;
                    if (tapCon < 5) {
                        btnList.get(tapCon).setVisibility(View.VISIBLE);
                        for (int i =0 ; i < btnList.size();i++){
                            btnList.get(i).setBackgroundResource(R.drawable.button_gray3_white_stroke_2dp);
                        }
                        btnList.get(tapCon).setBackgroundColor(Color.WHITE);
                        if (type){
                            clearMaintain();
                        }else{
                            clearFitting();
                        }
                    }
                }
                break;

            case R.id.cost_name_btn://费用名称
                setTestLevelData();
                optionsPickerView = AppUtile.initOptionsPicker(this, optionsPickerView,
                        options1Items, "请选择费用名称", new GetPickerStrCallBack() {
                            @Override
                            public void getStr(String returnStr,long id) {
                                costName.setText(returnStr);
                            }
                        });
                optionsPickerView.show();
                break;
            case R.id.cost_source_btn://资金来源
                setTestReasonData();
                optionsPickerView = AppUtile.initOptionsPicker(this, optionsPickerView,
                        options1Items, "请选择资金来源", new GetPickerStrCallBack() {
                            @Override
                            public void getStr(String returnStr,long id) {
                                costSource.setText(returnStr);
                            }
                        });
                optionsPickerView.show();
                break;
            case R.id.origin_btn://产地
                setTestUserData();
                optionsPickerView = AppUtile.initOptionsPicker(this, optionsPickerView,
                        options1Items, "请选择产地", new GetPickerStrCallBack() {
                            @Override
                            public void getStr(String returnStr,long id) {
                                originTv.setText(returnStr);
                            }
                        });
                optionsPickerView.show();
                break;
            case R.id.unit_btn://单位
                setTestUnitData();
                optionsPickerView = AppUtile.initOptionsPicker(this, optionsPickerView,
                        options1Items, "请选择单位", new GetPickerStrCallBack() {
                            @Override
                            public void getStr(String returnStr,long id) {
                                unitTv.setText(returnStr);
                            }
                        });
                optionsPickerView.show();
                break;
        }
    }

    /**
     * 获取费用名称测试数据
     */
    private void setTestLevelData(){
        options1Items.clear();
        for (int i = 0  ; i < 5;i++)
            options1Items.add(new ProvinceBean(0,"费用名称数据"+i,"",""));
    }
    /**
     * 获取资金来源测试数据
     */
    private void setTestReasonData(){
        options1Items.clear();
        for (int i = 0  ; i < 5;i++)
            options1Items.add(new ProvinceBean(0,"资金来源数据"+i,"",""));
    }
    /**
     * 获取产地测试数据
     */
    private void setTestUserData(){
        options1Items.clear();
        for (int i = 0  ; i < 5;i++)
            options1Items.add(new ProvinceBean(0,"产地数据"+i,"",""));
    }
    /**
     * 获取产地测试数据
     */
    private void setTestUnitData(){
        options1Items.clear();
        for (int i = 0  ; i < 5;i++)
            options1Items.add(new ProvinceBean(0,"单位数据"+i,"",""));
    }


    public void selePic(){
        new ActionSheetDialog(this)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("相机", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                if (AppUtile.dynamicAuthority(EnterCostActivity.this, Manifest.permission.CAMERA, CAMERA_STORAGES)) {
                                    File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
                                    if (!file.getParentFile().exists())
                                        file.getParentFile().mkdirs();
                                    Uri imageUri = Uri.fromFile(file);
                                    getTakePhoto().onEnableCompress(null, false);
                                    TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
                                    builder.setWithOwnGallery(true);
                                    builder.setCorrectImage(true);
                                    getTakePhoto().setTakePhotoOptions(builder.create());
                                    CompressConfig config = new CompressConfig.Builder()
                                            .setMaxSize(102400)
                                            .setMaxPixel(800)
                                            .enableReserveRaw(false)
                                            .create();
                                    getTakePhoto().onEnableCompress(config, false);
                                    getTakePhoto().onPickFromCapture(imageUri);
                                }
                            }
                        })
                .addSheetItem("相册", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                Intent intent =new Intent(EnterCostActivity.this, PickerActivity.class);
                                intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_IMAGE);//default image and video (Optional)
                                long maxSize=188743680L;//long long long
                                intent.putExtra(PickerConfig.MAX_SELECT_SIZE,maxSize); //default 180MB (Optional)
                                intent.putExtra(PickerConfig.MAX_SELECT_COUNT,9);  //default 40 (Optional)
                                intent.putExtra(PickerConfig.DEFAULT_SELECTED_LIST,select); // (Optional)
                                startActivityForResult(intent,321);
                            }
                        })
                .show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 321 ) {
            if (data!=null) {
                select = data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT);
                picStrs.clear();
                for (int i = 0; i < select.size(); i++) {
                    picStrs.add(select.get(i).path);
                }
                if (picStrs.size() < 9) {
                    picStrs.add("btn");
                }
                adapter.notifyDataSetChanged();

            }
        }
    }

    /**
     *  获取TakePhoto实例
     * @return
     */
    public TakePhoto getTakePhoto(){
        if (takePhoto==null){
            takePhoto= (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this,this));
        }
        return takePhoto;
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type=PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
        if(PermissionManager.TPermissionType.WAIT.equals(type)){
            this.invokeParam=invokeParam;
        }
        return type;
    }

    @Override
    public void takeSuccess(TResult result) {

    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type=PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
        PermissionManager.handlePermissionsResult(this,type,invokeParam,this);
    }
}
