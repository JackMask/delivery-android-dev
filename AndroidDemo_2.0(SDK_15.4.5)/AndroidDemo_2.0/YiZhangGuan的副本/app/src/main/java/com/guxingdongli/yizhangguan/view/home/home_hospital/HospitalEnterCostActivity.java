package com.guxingdongli.yizhangguan.view.home.home_hospital;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.util.AbToastUtil;
import com.alibaba.fastjson.JSON;
import com.dmcbig.mediapicker.PickerActivity;
import com.dmcbig.mediapicker.PickerConfig;
import com.dmcbig.mediapicker.entity.Media;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.YiZhangGuanApplication;
import com.guxingdongli.yizhangguan.controller.adapter.PicSelectAdapter;
import com.guxingdongli.yizhangguan.model.FeeNameBase;
import com.guxingdongli.yizhangguan.model.HospitalFillInRepairOrderCostBase;
import com.guxingdongli.yizhangguan.model.ProvinceBean;
import com.guxingdongli.yizhangguan.model.ReasonAnalysisBase;
import com.guxingdongli.yizhangguan.model.ServiceLevelBase;
import com.guxingdongli.yizhangguan.model.SourcesOfFundsBase;
import com.guxingdongli.yizhangguan.model.TestMaintainBase;
import com.guxingdongli.yizhangguan.util.AppUtile;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.GetPickerStrCallBack;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.guxingdongli.yizhangguan.view.home.dialog.InputDialog;
import com.guxingdongli.yizhangguan.view.home.home_merchants.EnterCostActivity;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TImage;
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

public class HospitalEnterCostActivity extends YiZhangGuanActivity implements TakePhoto.TakeResultListener,InvokeListener {


    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.pic_grid)
    ProhibitSlideGridView picGrid;
    @Bind(R.id.fitting_layout)
    LinearLayout fittingLayout;
    @Bind(R.id.maintain_layout)
    LinearLayout maintainLayout;
    @Bind(R.id.cost_name)
    TextView costName;
    @Bind(R.id.cost_source)
    TextView costSource;
    @Bind(R.id.maintain_quotation)
    EditText maintainQuotation;
    @Bind(R.id.actual_quotation)
    EditText actualQuotation;
    @Bind(R.id.right_tv_btn)
    TextView rightTvBtn;
    @Bind(R.id.remark_tv)
    TextView remarkTv;




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


    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private PicSelectAdapter adapter;
    ArrayList<Media> select = new ArrayList<>();
    private List<String> picStrs = new ArrayList<>();
    private static final int CAMERA_STORAGES = 2;
    private OptionsPickerView optionsPickerView;
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private List<View> btnList = new ArrayList<>();
    private List<View> maintainTextViewList;
    private SourcesOfFundsBase  sourcesOfFundsBase;
    private FeeNameBase  feeNameBase;
    private List<HospitalFillInRepairOrderCostBase.RepairFeeListBean> pageData = new ArrayList<>(5);
    private int index = 0 , clickIndex = 0,showAllIndex = 1;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) //如果消息是刚才发送的标识
            {
                sourcesOfFundsBase = (SourcesOfFundsBase) msg.obj;
            }else  if (msg.what == 1){
                feeNameBase = (FeeNameBase) msg.obj;
            }
        }

        ;
    };
    private void getDictionaryList(final String dicType){
        RequestBody formBody = new  FormBody.Builder()
                .add("dicType", dicType)
                .build();
        HttpUtile httpUtile = new HttpUtile(this, Constant.DOMAIN_NAME + Constant.GETDICTIONARYLIST, formBody, new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {
                if (dicType.equals("费用名称")){
                    FeeNameBase data = JSON.parseObject(returnStr,FeeNameBase.class);
                    Message message = new Message();
                    message.what = 1;
                    message.obj = data;
                    handler.sendMessage(message);
                }else if (dicType.equals("资金来源")){
                    SourcesOfFundsBase data = JSON.parseObject(returnStr,SourcesOfFundsBase.class);
                    Message message = new Message();
                    message.what = 0;
                    message.obj = data;
                    handler.sendMessage(message);
                }
            }


            @Override
            public void getReturnStrFailure(String returnStr) {
                Looper.prepare();
                Toast.makeText(HospitalEnterCostActivity.this, returnStr, Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void getErrorStr(String errorStr) {

            }
        },false);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_cost);
        ButterKnife.bind(this);
        setView();
    }


    private void setView() {
        rightTvBtn.setVisibility(View.VISIBLE);
        rightTvBtn.setText("录入");
        rightTvBtn.setTextColor(Color.parseColor("#2ea1fb"));
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

        btnList.add(one_btn);
        btnList.add(two_btn);
        btnList.add(three_btn);
        btnList.add(four_btn);
        btnList.add(five_btn);
        getDictionaryList("费用名称");
        getDictionaryList("资金来源");
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
                if (showBtn == pageData.size()){
                    for (HospitalFillInRepairOrderCostBase.RepairFeeListBean item:pageData){
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
                for(int i = 0 ; i < pageData.size();i++){
                    pageData.get(i).setFeeType("院内维修费用");
                }
                if (noData) {
                    Intent intent = new Intent();
                    intent.putExtra("data", (Serializable) pageData);
                    setResult(1001, intent);
                    finish();
                }
                break;
            case R.id.add_btn://添加
                if (getData()) {
                    clickIndex++;
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

            case R.id.cost_name_btn://费用名称
                if (setTestLevelData()) {
                    optionsPickerView = AppUtile.initOptionsPicker(HospitalEnterCostActivity.this, optionsPickerView,
                            options1Items, "请选择费用名称", new GetPickerStrCallBack() {
                                @Override
                                public void getStr(String returnStr, long id) {
                                    costName.setText(returnStr);
                                    String iDstr = String.valueOf(id);
                                    costName.setTag(iDstr);

                                }
                            });
                    optionsPickerView.show();
                }
                break;
            case R.id.cost_source_btn://资金来源
                if (setTestReasonData()) {
                    optionsPickerView = AppUtile.initOptionsPicker(HospitalEnterCostActivity.this, optionsPickerView,
                            options1Items, "请选择资金来源", new GetPickerStrCallBack() {
                                @Override
                                public void getStr(String returnStr, long id) {
                                    costSource.setText(returnStr);
                                    String iDstr = String.valueOf(id);
                                    costSource.setTag(iDstr);
                                }
                            });
                    optionsPickerView.show();
                }
                break;

        }
    }






    private void delData(int index){
        pageData.remove(index);
        for (int i = btnList.size()-1;i>0;i--){
        if (btnList.get(i).getVisibility()==View.VISIBLE){
            btnList.get(i).setVisibility(View.GONE);
            break;
            }
        }
        this.index -=1;
    }

    /**
     * 空值判断/添加
     * @return
     */
    private boolean getData(){
        HospitalFillInRepairOrderCostBase.RepairFeeListBean nowPage = new HospitalFillInRepairOrderCostBase.RepairFeeListBean();
        if (AppUtile.checkNull(this,costName.getText().toString(),"",false)){
            nowPage.setFeeNameId(costName.getText().toString());
            nowPage.setFeeName((String)costName.getTag());
        }else{
            return false;
        }
        if (AppUtile.checkNull(this,costSource.getText().toString(),"",false)){
            nowPage.setFundSourceId(costSource.getText().toString());
            nowPage.setFeeName((String)costSource.getTag());
        }else{
            return false;
        }
        if (AppUtile.checkNull(this,maintainQuotation.getText().toString(),"",false)){
            nowPage.setQuotePrice(Integer.valueOf(maintainQuotation.getText().toString()));
        }else{
            return false;
        }
        if (AppUtile.checkNull(this,actualQuotation.getText().toString(),"",false)){
            nowPage.setActualPrice(Integer.valueOf(actualQuotation.getText().toString()));
        }else{
            return false;
        }
        nowPage.setRemark(remarkTv.getText().toString());
        List<String> fileUrls = new ArrayList<>();
        for (String str : picStrs)
            fileUrls.add(str);
        List<String> selectPic = new ArrayList<>();
        for (Media pic : select){
            selectPic.add(pic.path);
        }
        nowPage.setSelectPath(selectPic);
       /* ArrayList<Media> selecPic = new ArrayList<>();
        for (Media pic : select)
            selecPic.add(pic);
        nowPage.setSelectPic(selecPic);*/
        nowPage.setPicUrl(fileUrls);

        if (pageData.size()>1)
        if (!testingNowData()){
            return false;
        }
        delExcessData(index);
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
            if (!AppUtile.checkNull(this,pageData.get(showNum-2).getFeeNameId(),"",false)){
                return false;
            }
            if (!AppUtile.checkNull(this,pageData.get(showNum-2).getFundSourceId(),"",false)){
                return false;
            }
            if (!AppUtile.checkNull(this,pageData.get(showNum-2).getQuotePrice()+"","",false)){
                return false;
            }
            if (!AppUtile.checkNull(this,pageData.get(showNum-2).getActualPrice()+"","",false)){
                return false;
            }
        }
            return true;

    }

        private void delExcessData(int index){
            try{
                pageData.remove(index);
            }catch (Exception e){

            }
        }
    private boolean tesingData(HospitalFillInRepairOrderCostBase.RepairFeeListBean nowPage){
        if (AppUtile.checkNull(this,nowPage.getFeeNameId(),"",false)){
            return false;
        }
        if (AppUtile.checkNull(this,nowPage.getFundSourceId(),"",false)){
            return false;
        }
        if (AppUtile.checkNull(this,nowPage.getQuotePrice()==0?"":nowPage.getQuotePrice()+"","",false)){
            return false;
        }
        if (AppUtile.checkNull(this,nowPage.getActualPrice()==0?"":nowPage.getActualPrice()+"","",false)){
            return false;
        }
        return true;
    }
        private boolean tesingData(){
            HospitalFillInRepairOrderCostBase.RepairFeeListBean nowPage = new HospitalFillInRepairOrderCostBase.RepairFeeListBean();
            if (AppUtile.checkNull(this,costName.getText().toString(),"",false)){
                nowPage.setFeeNameId(costName.getText().toString());
                nowPage.setFeeName((String)costName.getTag());
            }else{
                return false;
            }
            if (AppUtile.checkNull(this,costSource.getText().toString(),"",false)){
                nowPage.setFundSourceId(costSource.getText().toString());//不用改
                nowPage.setFundSource((String)costSource.getTag());
            }else{
                return false;
            }
            if (AppUtile.checkNull(this,maintainQuotation.getText().toString(),"",false)){
                nowPage.setQuotePrice(Integer.valueOf(maintainQuotation.getText().toString()));
            }else{
                return false;
            }
            if (AppUtile.checkNull(this,actualQuotation.getText().toString(),"",false)){
                nowPage.setActualPrice(Integer.valueOf(actualQuotation.getText().toString()));
            }else{
                return false;
            }
            nowPage.setRemark(remarkTv.getText().toString());
            List<String> fileUrls = new ArrayList<>();
            for (String str : picStrs){
                if (!str.equals("btn"))
                fileUrls.add(str);
            }
            List<String> selectPic = new ArrayList<>();
            for (Media pic : select){
                selectPic.add(pic.path);
            }
            nowPage.setSelectPath(selectPic);
            /*ArrayList<Media> selecPic = new ArrayList<>();
            for (Media pic : select)
                selecPic.add(pic);
            nowPage.setSelectPic(selecPic);*/
            nowPage.setPicUrl(fileUrls);
            pageData.add(index, nowPage);
            return true;
        }

        private void addMedia(){

        }

    /**
     * 缓存当前页面的数据
     * @return
     */
    private void getSavePageData(int index){

        HospitalFillInRepairOrderCostBase.RepairFeeListBean nowPage = new HospitalFillInRepairOrderCostBase.RepairFeeListBean();
        nowPage.setFeeName((String)costName.getTag());
        nowPage.setFeeNameId(costName.getText().toString());
        nowPage.setFundSource((String)costSource.getTag());
        nowPage.setFundSourceId(costSource.getText().toString());
        nowPage.setQuotePrice(!TextUtils.isEmpty(maintainQuotation.getText().toString())?Integer.valueOf(maintainQuotation.getText().toString()):0);
        nowPage.setActualPrice(!TextUtils.isEmpty(actualQuotation.getText().toString())?Integer.valueOf(actualQuotation.getText().toString()):0);
        nowPage.setRemark(remarkTv.getText().toString());
        List<String> fileUrls = new ArrayList<>();
        for (String str : picStrs){
                fileUrls.add(str);
        }
        List<String> selectPic = new ArrayList<>();
        for (Media pic : select){
            selectPic.add(pic.path);
        }
        nowPage.setSelectPath(selectPic);
        /*ArrayList<Media> selecPic = new ArrayList<>();
        for (Media pic : select)
            selecPic.add(pic);
        nowPage.setSelectPic(selecPic);*/
        nowPage.setPicUrl(fileUrls);
        delExcessData(index);
        pageData.add(index, nowPage);
    }

    /**
     * 数据还原
     *
     */
    private void getAddPageData(int index){
        select.clear();
            costName.setText(pageData.get(index).getFeeNameId());
            costSource.setText(pageData.get(index).getFundSourceId());
            maintainQuotation.setText(pageData.get(index).getQuotePrice() == 0 ? "" : pageData.get(index).getQuotePrice()+"");
            actualQuotation.setText(pageData.get(index).getActualPrice()==0?"":pageData.get(index).getActualPrice()+"");
            remarkTv.setText(pageData.get(index).getRemark()+"");
            picStrs.clear();
            if (pageData.get(index).getPicUrl()!=null){
                for (String str :pageData.get(index).getPicUrl() )
                    picStrs.add(str);
            }
            if (pageData.get(index).getSelectPath()!=null){
                for (String str :pageData.get(index).getSelectPath() ){
                    Media a = new Media(str, "", 0, 0, 0, 0, "");
                    select.add(a);
                }
            }
            adapter.notifyDataSetChanged();
    }

    /**
     * 维修费用页面数据清空
     */
    private void clearMaintain(){
        costName.setText("");
        costSource.setText("");
        maintainQuotation.setText("");
        actualQuotation.setText("");
        remarkTv.setText("");
        picStrs.clear();
        picStrs.add("btn");
        select.clear();
        adapter.notifyDataSetChanged();
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
                                if (AppUtile.dynamicAuthority(HospitalEnterCostActivity.this, Manifest.permission.CAMERA, CAMERA_STORAGES)) {
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
                                Intent intent =new Intent(HospitalEnterCostActivity.this, PickerActivity.class);
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
                ArrayList<Media> selectList = data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT);
                if (select==null){
                    select = new ArrayList<>();
                }else{
                    select.clear();
                }
                for (int i = 0 ; i < selectList.size();i++){
                    select.add(selectList.get(i));
                }
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
     * 获取费用名称测试数据
     */
    private boolean setTestLevelData(){

        options1Items.clear();
        if (feeNameBase.getData()!=null&&feeNameBase.getData().size()>0) {
            for (int i = 0; i < feeNameBase.getData().size(); i++)
                options1Items.add(new ProvinceBean(feeNameBase.getData().get(i).getId(), feeNameBase.getData().get(i).getDicValue(), "", ""));
            return true;
        }else{
            AbToastUtil.showToast(HospitalEnterCostActivity.this,"暂无数据");
            return false;
        }
    }
    /**
     * 获取资金来源测试数据
     */
    private boolean setTestReasonData(){
        options1Items.clear();
        options1Items.clear();
        if (sourcesOfFundsBase.getData()!=null&&sourcesOfFundsBase.getData().size()>0) {
            for (int i = 0; i < sourcesOfFundsBase.getData().size(); i++)
                options1Items.add(new ProvinceBean(sourcesOfFundsBase.getData().get(i).getId(), sourcesOfFundsBase.getData().get(i).getDicValue(), "", ""));
            return true;
        }else{

            AbToastUtil.showToast(this,"暂无数据");
            return false;
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
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    public void takeSuccess(TResult result) {
        showImg(result.getImages());
    }
    private void showImg(ArrayList<TImage> images) {
        setBitmap(images.get(0).getCompressPath());
    }
    private void setBitmap(String pathUrl) {
        Bitmap bitmap = AppUtile.getSmallBitmap(pathUrl);
        AppUtile.compressBmpToFile(bitmap, new File(pathUrl));
        picStrs.clear();
        Media a = new Media(pathUrl, "", 0, 0, 0, 0, "");
        if (select==null){
            select = new ArrayList<>();
        }
        select.add(a);
        for (int i = 0; i < select.size(); i++) {
            picStrs.add(select.get(i).path);
        }
        if (picStrs.size() < 9) {
            picStrs.add("btn");
        }
        adapter.notifyDataSetChanged();
        /*imageView14.setImageBitmap(bitmap);
        path = new File(pathUrl);*/
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
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }
}
