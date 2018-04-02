package com.guxingdongli.yizhangguan.view.home.home_merchants;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbStringHttpResponseListener;
import com.alibaba.fastjson.JSON;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.YiZhangGuanApplication;
import com.guxingdongli.yizhangguan.controller.adapter.PicSelectAdapter;
import com.guxingdongli.yizhangguan.model.ProvinceBean;
import com.guxingdongli.yizhangguan.util.AppUtile;
import com.guxingdongli.yizhangguan.util.GetPickerStrCallBack;
import com.guxingdongli.yizhangguan.util.GetTimeStrCallBack;
import com.guxingdongli.yizhangguan.util.PicassoImageLoader;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;
import com.yuxiaolong.yuxiandelibrary.ActionSheetDialog;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;
import com.yuxiaolong.yuxiandelibrary.pickerview.OptionsPickerView;
import com.yuxiaolong.yuxiandelibrary.pickerview.TimePickerView;
import com.yuxiaolong.yuxiandelibrary.view.ProhibitSlideGridView;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 填写维修单
 * Created by jackmask on 2018/3/9.
 */

public class FillInRepairOrderActivity extends YiZhangGuanActivity {
    public final static String TAG = "FillInRepairOrderActivity1";
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.right_tv_btn)
    TextView rightTvBtn;
    @Bind(R.id.maintain_price)
    TextView maintainPrice;
    @Bind(R.id.maintain_price_num)
    TextView maintainPriceNum;
    @Bind(R.id.btn_maintain)
    TextView btnMaintain;
    @Bind(R.id.accessories_price)
    TextView accessoriesPrice;
    @Bind(R.id.accessories_price_num)
    TextView accessoriesPriceNum;
    @Bind(R.id.btn_accessories)
    TextView btnAccessories;
    @Bind(R.id.pic_laout)
    ProhibitSlideGridView picLaout;
    @Bind(R.id.level_tv)
    TextView level_tv;
    @Bind(R.id.maintain_time_tv)
    TextView maintain_time_tv;
    @Bind(R.id.end_time_tv)
    TextView end_time_tv;
    @Bind(R.id.reason_tv)
    TextView reason_tv;
    @Bind(R.id.user_tv)
    TextView user_tv;
    @Bind(R.id.time_cost_et)
    TextView time_cost_et;

    private int  maxPicNum = 0;
    private PicSelectAdapter adapter;
    ArrayList<String> select = new ArrayList<>();
    private List<String> picStrs = new ArrayList<>();
    private static final int CAMERA_STORAGES = 2;
    private OptionsPickerView optionsPickerView;
    private TimePickerView timePickerView;
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_in_repair_order);
        ButterKnife.bind(this);
        setView();
    }

    private void setView(){
        titleText.setText("填写维修单");
        rightTvBtn.setVisibility(View.VISIBLE);
        rightTvBtn.setText("确定");
        rightTvBtn.setTextColor(Color.parseColor("#2ea1fb"));
        picStrs.add("btn");
        adapter = new PicSelectAdapter(picStrs);
        picLaout.setNumColumns(4);
        picLaout.setAdapter(adapter);
    }


    IHandlerCallBack iHandlerCallBack = new IHandlerCallBack() {
        @Override
        public void onStart() {
        }

        @Override
        public void onSuccess(List<String> photoList) {
          //onSuccess: 返回数据
            if (photoList.size()>1) {
                for (String s : photoList) {
                    //path.add(s);
                    select.add(s);
                }
                picStrs.clear();
                for (int i = 0; i < select.size(); i++) {
                    picStrs.add(select.get(i));
                }
                if (picStrs.size() < 9) {
                    picStrs.add("btn");
                }
            }else if (photoList.size()>0){
                for (int i = 0 ; i < picStrs.size();i++){
                    if (picStrs.get(i).equals("btn")){
                        picStrs.remove(i);
                        break;
                    }
                }
                if (picStrs.size() < 9) {
                    select.add(photoList.get(0));
                    picStrs.add(photoList.get(0));
                    picStrs.add("btn");
                }

            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancel() {
            //onCancel: 取消
        }

        @Override
        public void onFinish() {
            //onFinish: 结束
        }

        @Override
        public void onError() {
            //onError: 出错
        }
    };
    public void selePic(){
        new ActionSheetDialog(this)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("相机", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                if (AppUtile.dynamicAuthority(FillInRepairOrderActivity.this, Manifest.permission.CAMERA, CAMERA_STORAGES)) {
                                    GalleryConfig galleryConfig = new GalleryConfig.Builder()
                                            .imageLoader(new PicassoImageLoader())    // ImageLoader 加载框架（必填）
                                            .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                                            .provider("com.guxingdongli.yizhangguan.fileprovider")   // provider (必填)
                                            .filePath("/Gallery/Pictures")          // 图片存放路径   （选填）
                                            .isOpenCamera(true)                  // 直接开启相机的标识位
                                            .build();

                                    GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(FillInRepairOrderActivity.this);
                                }
                            }
                        })
                .addSheetItem("相册", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                GalleryConfig galleryConfig = new GalleryConfig.Builder()
                                        .imageLoader(new PicassoImageLoader())    // ImageLoader 加载框架（必填）
                                        .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                                        .provider("com.guxingdongli.yizhangguan.fileprovider")   // provider (必填)
                                        .pathList(select)                         // 记录已选的图片
                                        .multiSelect(true)                      // 是否多选   默认：false
                                        .multiSelect(true, 9-picStrs.size())                   // 配置是否多选的同时 配置多选数量   默认：false ， 9
                                        .maxSize(9-picStrs.size())                             // 配置多选时 的多选数量。    默认：9
                                        .isShowCamera(false)                     // 是否现实相机按钮  默认：false
                                        .filePath("/Gallery/Pictures")          // 图片存放路径
                                        .build();
                                GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(FillInRepairOrderActivity.this);
                                /*Intent intent =new Intent(FillInRepairOrderActivity.this, PickerActivity.class);
                                intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_IMAGE);//default image and video (Optional)
                                long maxSize=188743680L;//long long long
                                intent.putExtra(PickerConfig.MAX_SELECT_SIZE,maxSize); //default 180MB (Optional)
                                intent.putExtra(PickerConfig.MAX_SELECT_COUNT,9);  //default 40 (Optional)
                                intent.putExtra(PickerConfig.DEFAULT_SELECTED_LIST,select); // (Optional)
                                startActivityForResult(intent,321);*/
                            }
                        })
                .show();
    }

    private String serviceHour(String startTime,String endTime){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long m = sdf.parse(endTime).getTime() - sdf.parse(startTime).getTime();
            System.out.println(String.valueOf(Math.ceil(m/(1000*60*60))));
            return String.valueOf(Math.ceil(m/(1000*60*60)));
        }catch (ParseException e){
            System.out.println("0");
            return "0";
        }

    }



    @OnClick({R.id.return_btn, R.id.right_tv_btn, R.id.btn_maintain, R.id.btn_accessories,
    R.id.level_btn,R.id.end_time_btn,R.id.maintain_time_btn,R.id.reason_btn,R.id.user_btn})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.return_btn:
                finish();
                break;
            case R.id.right_tv_btn:
                //确定按钮

                break;
            case R.id.btn_maintain:
                //维修费用
                intent = new Intent(this,EnterCostActivity.class);
                intent.putExtra("type",true);
                startActivityForResult(intent,0);
                break;
            case R.id.btn_accessories:
                //配件费用
                intent = new Intent(this,EnterCostActivity.class);
                intent.putExtra("type",false);
                startActivityForResult(intent,0);
                break;
            case R.id.level_btn://维修等级
                setTestLevelData();
                optionsPickerView = AppUtile.initOptionsPicker(this, optionsPickerView,
                        options1Items, "请选择维修等级", new GetPickerStrCallBack() {
                            @Override
                            public void getStr(String returnStr,long id) {
                                level_tv.setText(returnStr);
                            }
                        });
                optionsPickerView.show();
                break;
            case R.id.maintain_time_btn://开始时间
                timePickerView = AppUtile.initTimePicker(this,timePickerView,"请选择维修时间",new GetTimeStrCallBack(){
                    @Override
                    public void getStr(Date date, View v) {
                        maintain_time_tv.setText(AppUtile.getTime(date));
                        System.out.println("22222222222222222");
                        time_cost_et.setText(serviceHour(maintain_time_tv.getText().toString(),end_time_tv.getText().toString()));
                    }
                });
                timePickerView.show();
                break;
            case R.id.end_time_btn://结束时间
                timePickerView = AppUtile.initTimePicker(this,timePickerView,"请选择结束时间",new GetTimeStrCallBack(){
                    @Override
                    public void getStr(Date date, View v) {
                        end_time_tv.setText(AppUtile.getTime(date));
                        time_cost_et.setText(serviceHour(maintain_time_tv.getText().toString(),end_time_tv.getText().toString()));
                    }
                });
                timePickerView.show();

                break;
            case R.id.reason_btn://原因分析
                setTestReasonData();
                optionsPickerView = AppUtile.initOptionsPicker(this, optionsPickerView,
                        options1Items, "请选择原因", new GetPickerStrCallBack() {
                            @Override
                            public void getStr(String returnStr,long id) {
                                reason_tv.setText(returnStr);
                            }
                        });
                optionsPickerView.show();
                break;
            case R.id.user_btn://检查人员
                setTestUserData();
                optionsPickerView = AppUtile.initOptionsPicker(this, optionsPickerView,
                        options1Items, "请选择检查人员", new GetPickerStrCallBack() {
                            @Override
                            public void getStr(String returnStr,long id) {
                                user_tv.setText(returnStr);
                            }
                        });
                optionsPickerView.show();

                break;
        }
    }

    /**
     * 获取等级数据
     */
    private void setTestLevelData(){
        options1Items.clear();
        if (YiZhangGuanApplication.getInstance().getSourcesOfFundsList()!=null)
        for (int i = 0  ; i < YiZhangGuanApplication.getInstance().getSourcesOfFundsList().size();i++)
        options1Items.add(new ProvinceBean(YiZhangGuanApplication.getInstance().getSourcesOfFundsList().get(i).getId(),YiZhangGuanApplication.getInstance().getSourcesOfFundsList().get(i).getDicValue(),"",""));
    }
    /**
     * 获取原因数据
     */
    private void setTestReasonData(){
        options1Items.clear();
        if (YiZhangGuanApplication.getInstance().getReasonAnalysisList()!=null)
        for (int i = 0  ; i < YiZhangGuanApplication.getInstance().getReasonAnalysisList().size();i++)
            options1Items.add(new ProvinceBean(YiZhangGuanApplication.getInstance().getReasonAnalysisList().get(i).getId(),YiZhangGuanApplication.getInstance().getReasonAnalysisList().get(i).getDicValue(),"",""));
    }
    /**
     * 获取人员测试数据
     */
    private void setTestUserData(){
        options1Items.clear();
        for (int i = 0  ; i < 5;i++)
            options1Items.add(new ProvinceBean(0,"检查人员数据"+i,"",""));
    }

}
