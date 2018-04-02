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
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbStringHttpResponseListener;
import com.alibaba.fastjson.JSON;
import com.dmcbig.mediapicker.PickerActivity;
import com.dmcbig.mediapicker.PickerConfig;
import com.dmcbig.mediapicker.entity.Media;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.YiZhangGuanApplication;
import com.guxingdongli.yizhangguan.controller.adapter.PicSelectAdapter;
import com.guxingdongli.yizhangguan.model.ProvinceBean;
import com.guxingdongli.yizhangguan.util.AppUtile;
import com.guxingdongli.yizhangguan.util.GetPickerStrCallBack;
import com.guxingdongli.yizhangguan.util.GetTimeStrCallBack;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
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

public class FillInRepairOrderActivity extends YiZhangGuanActivity implements TakePhoto.TakeResultListener,InvokeListener {

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
    ArrayList<Media> select = new ArrayList<>();
    private List<String> picStrs = new ArrayList<>();
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
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
                                Intent intent =new Intent(FillInRepairOrderActivity.this, PickerActivity.class);
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
        getPic(result.getImages());
    }

    private void getPic(ArrayList<TImage> images) {
        setBitmap(images.get(0).getCompressPath());
    }
    private void setBitmap(String pathUrl) {
        Bitmap bitmap = AppUtile.getSmallBitmap(pathUrl);
        AppUtile.compressBmpToFile(bitmap, new File(pathUrl));
        for (int i = 0 ; i < picStrs.size();i++){
            if (picStrs.get(i).equals("btn")){
                picStrs.remove(i);
                break;
            }
        }

        if (picStrs.size() < 9) {
            Media a = new Media(pathUrl, "", 0, 0, 0, 0, "");
            select.add(a);
            picStrs.add(pathUrl);
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
        PermissionManager.TPermissionType type=PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
        PermissionManager.handlePermissionsResult(this,type,invokeParam,this);
    }
}
