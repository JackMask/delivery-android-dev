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
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.util.AbDialogUtil;
import com.ab.util.AbToastUtil;
import com.alibaba.fastjson.JSON;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.YiZhangGuanApplication;
import com.guxingdongli.yizhangguan.controller.adapter.PicSelectAdapter;
import com.guxingdongli.yizhangguan.model.FeeNameBase;
import com.guxingdongli.yizhangguan.model.HospitalFillInRepairOrderCostBase;
import com.guxingdongli.yizhangguan.model.HospitalFillInRepairOrderNewBase;
import com.guxingdongli.yizhangguan.model.OriginBase;
import com.guxingdongli.yizhangguan.model.ProvinceBean;
import com.guxingdongli.yizhangguan.model.ReasonAnalysisBase;
import com.guxingdongli.yizhangguan.model.ServiceLevelBase;
import com.guxingdongli.yizhangguan.model.SourcesOfFundsBase;
import com.guxingdongli.yizhangguan.model.UnitBase;
import com.guxingdongli.yizhangguan.model.UploadFileBase;
import com.guxingdongli.yizhangguan.util.AppUtile;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.GetPickerStrCallBack;
import com.guxingdongli.yizhangguan.util.GetTimeStrCallBack;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.util.PicassoImageLoader;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.guxingdongli.yizhangguan.view.home.dialog.InputDialog;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;
import com.yuxiaolong.yuxiandelibrary.ActionSheetDialog;
import com.yuxiaolong.yuxiandelibrary.pickerview.OptionsPickerView;
import com.yuxiaolong.yuxiandelibrary.pickerview.TimePickerView;
import com.yuxiaolong.yuxiandelibrary.view.ProhibitSlideGridView;

import java.io.File;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 医院工程师填写维修单
 * Created by jackmask on 2018/3/9.
 */

public class HospitalFillInRepairOrderActivity extends YiZhangGuanActivity  {

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
    @Bind(R.id.unit_name)
    TextView unitName;
    @Bind(R.id.orders_name)
    TextView ordersName;
    @Bind(R.id.repair_time_name)
    TextView repairTimeName;
    @Bind(R.id.device_name)
    TextView deviceName;
    @Bind(R.id.repair_department_name)
    TextView repairDepartmentName;
    @Bind(R.id.user_name_et)
    EditText userNameEt;
    @Bind(R.id.user_phone_et)
    EditText userPhoneEt;
    @Bind(R.id.outside_congress_layout)
    LinearLayout outsideCongressLayout;
    @Bind(R.id.radio_button_layout)
    RadioGroup radioButtonLayout;
    @Bind(R.id.start_time_tv)
    TextView startTimeTv;


    @Bind(R.id.time_cost_et)
    TextView time_cost_et;
    @Bind(R.id.downtime_et)
    TextView downtimeEt;
    @Bind(R.id.happening_et)
    EditText happeningEt;
    @Bind(R.id.notice_btn)
    RadioButton noticeBtn;
    @Bind(R.id.transfer_btn)
    RadioButton transferBtn;

    private int maxPicNum = 0;
    private PicSelectAdapter adapter;
    ArrayList<String> select = new ArrayList<>();
    private List<String> picStrs = new ArrayList<>();
    private static final int CAMERA_STORAGES = 2;
    private OptionsPickerView optionsPickerView;
    private TimePickerView timePickerView;
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private String gid;
    private int applyId;
    private String applyGuid;
    private int noticStatus = 1;
    //private List<HospitalFillInRepairOrderBase.DataBean.MaintenanceEngineerBean> userList;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) //如果消息是刚才发送的标识
            {
                HospitalFillInRepairOrderNewBase data = (HospitalFillInRepairOrderNewBase) msg.obj;
                unitName.setText(data.getData().getHospitalName());
                ordersName.setText(data.getData().getBusinessNumber());
                repairTimeName.setText(data.getData().getRepairTime());
                deviceName.setText(data.getData().getName());
                applyId = data.getData().getApplyId();
                applyGuid = data.getData().getApplyGuid();
                repairDepartmentName.setText(data.getData().getDepartmentName());
                // userList = data.getData().getMaintenanceEngineer();
                startTimeTv.setText(data.getData().getMaintenanceStartDate());
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_in_repair_order);
        ButterKnife.bind(this);
        setView();
        getDictionaryList("维修级别");
        getDictionaryList("原因分析");
    }

    private void setView() {
        titleText.setText("填写维修单");
        rightTvBtn.setVisibility(View.VISIBLE);
        rightTvBtn.setText("确定");
        rightTvBtn.setTextColor(Color.parseColor("#2ea1fb"));
        picStrs.add("btn");
        gid = getIntent().getStringExtra("guid");
        adapter = new PicSelectAdapter(picStrs);
        picLaout.setNumColumns(4);
        picLaout.setAdapter(adapter);
        if (!YiZhangGuanApplication.getInstance().isAppType()){
            transferBtn.setVisibility(View.GONE);
        }
        RequestBody formBody = new FormBody.Builder()
                .add("guid", gid)
                .build();
        HttpUtile httpUtile = new HttpUtile(this, Constant.DOMAIN_NAME + Constant.GETGUIDCHECKIN, formBody, new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {
                HospitalFillInRepairOrderNewBase data = JSON.parseObject(returnStr, HospitalFillInRepairOrderNewBase.class);
                System.out.println("returnStr = " +returnStr);
                Message message = new Message();
                message.what = 0;
                message.obj = data;
                handler.sendMessage(message);
            }

            @Override
            public void getReturnStrFailure(String returnStr) {
                Looper.prepare();
                Toast.makeText(HospitalFillInRepairOrderActivity.this, returnStr, Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void getErrorStr(String errorStr) {
                System.out.println("errorStr = " + errorStr);
            }
        },false);
        radioButtonLayout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                String select = radioButton.getText().toString();
                if (select.equals("转院外")) {
                    noticStatus = 3;
                    outsideCongressLayout.setVisibility(View.VISIBLE);
                } else {
                    noticStatus = 1;
                    outsideCongressLayout.setVisibility(View.GONE);
                }
            }
        });
    }


    public void selePic() {
        new ActionSheetDialog(this)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("相机", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                if (AppUtile.dynamicAuthority(HospitalFillInRepairOrderActivity.this, Manifest.permission.CAMERA, CAMERA_STORAGES)) {
                                    GalleryConfig galleryConfig = new GalleryConfig.Builder()
                                            .imageLoader(new PicassoImageLoader())    // ImageLoader 加载框架（必填）
                                            .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                                            .provider("com.guxingdongli.yizhangguan.fileprovider")   // provider (必填)
                                            .filePath("/Gallery/Pictures")          // 图片存放路径   （选填）
                                            .isOpenCamera(true)                  // 直接开启相机的标识位
                                            .build();

                                    GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(HospitalFillInRepairOrderActivity.this);
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
                                        .multiSelect(true, 9)                   // 配置是否多选的同时 配置多选数量   默认：false ， 9
                                        .maxSize(9)                             // 配置多选时 的多选数量。    默认：9

                                        .isShowCamera(false)                     // 是否现实相机按钮  默认：false
                                        .filePath("/Gallery/Pictures")          // 图片存放路径
                                        .build();
                                GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(HospitalFillInRepairOrderActivity.this);
                            }
                        })
                .show();
    }

    IHandlerCallBack iHandlerCallBack = new IHandlerCallBack() {
        @Override
        public void onStart() {
            //onStart: 开启
        }

        @Override
        public void onSuccess(List<String> photoList) {
            //返回数据
            if (photoList!=null) {
                if (photoList.size() > 1) {
                    List<String> selectList = photoList;
                    if (select == null) {
                        select = new ArrayList<>();
                    } else {
                        select.clear();
                    }
                    for (int i = 0; i < selectList.size(); i++) {
                        Bitmap bitmap = AppUtile.getSmallBitmap(selectList.get(i));
                        AppUtile.compressBmpToFile(bitmap, new File(selectList.get(i)));
                        select.add(selectList.get(i));
                    }
                    picStrs.clear();
                    for (int i = 0; i < select.size(); i++) {
                        picStrs.add(select.get(i));
                    }
                    if (picStrs.size() < 9) {
                        picStrs.add("btn");
                    }

                }else if (photoList.size() > 0){
                    for (int i = 0; i < picStrs.size(); i++) {
                        if (picStrs.get(i).equals("btn")) {
                            picStrs.remove(i);
                            break;
                        }
                    }

                    if (picStrs.size() < 9) {
                        Bitmap bitmap = AppUtile.getSmallBitmap(photoList.get(0));
                        AppUtile.compressBmpToFile(bitmap, new File(photoList.get(0)));
                        select.add(photoList.get(0));
                        picStrs.add(photoList.get(0));
                        picStrs.add("btn");
                    }


                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancel() {
            //取消
        }

        @Override
        public void onFinish() {
            //结束
        }

        @Override
        public void onError() {
            //出错
        }
    };
    @Override
    @SuppressWarnings("unchecked")
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if (requestCode == 0) {
            if (resultCode == 1001) {
                int allPrice = 0;
                feeListBeans.clear();

                for (HospitalFillInRepairOrderCostBase.RepairFeeListBean item : (ArrayList<HospitalFillInRepairOrderCostBase.RepairFeeListBean>) data.getSerializableExtra("data")) {
                    feeListBeans.add(item);
                }
                for (HospitalFillInRepairOrderCostBase.RepairFeeListBean item : feeListBeans) {
                    for (int i = 0; i < item.getPicUrl().size(); i++) {
                        if (item.getPicUrl().get(i).equals("btn")) {
                            item.getPicUrl().remove(i);
                        }
                    }
                    allPrice = allPrice + item.getActualPrice();
                }
                jsonData.setRepairFeeList(feeListBeans);

                maintainPriceNum.setText("共" + feeListBeans.size() + "项");
                maintainPrice.setText(allPrice + "");
            } else if (resultCode == 1002) {
                int allPrice = 0;
                replacingFittingLists.clear();

                for (HospitalFillInRepairOrderCostBase.ReplacingFittingListBean item : (ArrayList<HospitalFillInRepairOrderCostBase.ReplacingFittingListBean>) data.getSerializableExtra("data")) {
                    replacingFittingLists.add(item);
                }
                for (HospitalFillInRepairOrderCostBase.ReplacingFittingListBean item : replacingFittingLists) {
                    allPrice = allPrice + (item.getQuantity() * item.getPrice());
                }
                jsonData.setReplacingFittingList(replacingFittingLists);
                String json = JSON.toJSONString(jsonData);
                accessoriesPriceNum.setText("共" + replacingFittingLists.size() + "项");
                accessoriesPrice.setText(allPrice + "");
            }
        }else if (requestCode == 1){
            if (data!=null) {
                RequestBody formBody = new FormBody.Builder()
                        .add("Id", "0")
                        .add("DicName", "维修级别")
                        .add("DicValue", data.getStringExtra("content"))
                        .build();
                HttpUtile httpUtile = new HttpUtile(HospitalFillInRepairOrderActivity.this, Constant.DOMAIN_NAME + Constant.ADDDICTIONARY, new HttpUtileCallBack() {
                    @Override
                    public void getReturnStr(String returnStr) {

                        getDictionaryList("维修级别");
                    }

                    @Override
                    public void getReturnStrFailure(String returnStr) {
                        Looper.prepare();
                        Toast.makeText(HospitalFillInRepairOrderActivity.this, returnStr, Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }

                    @Override
                    public void getErrorStr(String errorStr) {

                    }
                },false);
            }
        }else if (requestCode == 2){

            RequestBody formBody = new FormBody.Builder()
                    .add("Id", "0")
                    .add("DicName", "原因分析")
                    .add("DicValue", data.getStringExtra("content"))
                    .build();
            HttpUtile httpUtile = new HttpUtile(HospitalFillInRepairOrderActivity.this, Constant.DOMAIN_NAME + Constant.ADDDICTIONARY, new HttpUtileCallBack() {
                @Override
                public void getReturnStr(String returnStr) {
                    getDictionaryList("原因分析");
                }

                @Override
                public void getReturnStrFailure(String returnStr) {
                    Looper.prepare();
                    Toast.makeText(HospitalFillInRepairOrderActivity.this, returnStr, Toast.LENGTH_LONG).show();
                    Looper.loop();
                }

                @Override
                public void getErrorStr(String errorStr) {

                }
            },false);
        }
    }
    private ReasonAnalysisBase reasonAnalysisBase;
    private ServiceLevelBase serviceLevelBase;

    private void getDictionaryList(final String dicType){
        RequestBody formBody = new  FormBody.Builder()
                .add("dicType", dicType)
                .build();
        HttpUtile httpUtile = new HttpUtile(this, Constant.DOMAIN_NAME + Constant.GETDICTIONARYLIST, formBody, new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {
                if (dicType.equals("原因分析")){
                    reasonAnalysisBase = JSON.parseObject(returnStr,ReasonAnalysisBase.class);
                    options1Items.clear();
                   // List<ReasonAnalysisBase.DataBean> data = YiZhangGuanApplication.getInstance().getReasonAnalysisList();
                }else if (dicType.equals("维修级别")){
                    serviceLevelBase  =JSON.parseObject(returnStr,ServiceLevelBase.class);

                }
            }


            @Override
            public void getReturnStrFailure(String returnStr) {
                Looper.prepare();
                Toast.makeText(HospitalFillInRepairOrderActivity.this, returnStr, Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void getErrorStr(String errorStr) {

            }
        },false);

    }

    private ArrayList<HospitalFillInRepairOrderCostBase.ReplacingFittingListBean> replacingFittingLists = new ArrayList<>();
    private ArrayList<HospitalFillInRepairOrderCostBase.RepairFeeListBean> feeListBeans = new ArrayList<>();

    private String serviceHour(String startTime, String endTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            long m = sdf.parse(endTime).getTime() - sdf.parse(startTime).getTime();
            return String.valueOf(Math.ceil(m / (1000 * 60 * 60))>0?Math.ceil(m / (1000 * 60 * 60)):1);
        } catch (ParseException e) {
            return "1";
        }

    }

    HospitalFillInRepairOrderCostBase jsonData = new HospitalFillInRepairOrderCostBase();
    private int RepairFeeListCon = 0;
    private boolean comparingTime(String startTime, String endTime){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            long m = sdf.parse(endTime).getTime() - sdf.parse(startTime).getTime();
            if (m > 0) {
                return true;
            }else{
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
    }
    @OnClick({R.id.return_btn, R.id.right_tv_btn, R.id.btn_maintain, R.id.btn_accessories,
            R.id.level_btn, R.id.end_time_btn, R.id.maintain_time_btn, R.id.reason_btn, R.id.user_btn})
    public void onViewClicked(View view) {
        final Intent intent;
        switch (view.getId()) {
            case R.id.return_btn:
                finish();
                break;

            case R.id.btn_maintain:
                //维修费用
                intent = new Intent(this, HospitalEnterCostActivity.class);
                if (feeListBeans!=null&&feeListBeans.size()>0){
                    intent.putExtra("data",(Serializable)feeListBeans);
                }
                startActivityForResult(intent, 0);
                break;
            case R.id.btn_accessories:
                //配件费用
                intent = new Intent(this, HospitalEnterAccessoriesActivity.class);
                intent.putExtra("type", false);
                if (replacingFittingLists!=null&&replacingFittingLists.size()>0){
                    intent.putExtra("data",(Serializable)replacingFittingLists);
                }
                startActivityForResult(intent, 0);
                break;
            case R.id.level_btn://维修等级
                setTestLevelData();
                    optionsPickerView = AppUtile.initOptionsPicker(HospitalFillInRepairOrderActivity.this, optionsPickerView,
                            options1Items, "请选择维修等级", new GetPickerStrCallBack() {
                                @Override
                                public void getStr(String returnStr, long id) {
                                    if (returnStr.equals("添加")) {
                                        Intent intent1 = new Intent(HospitalFillInRepairOrderActivity.this, InputDialog.class);
                                        intent1.putExtra("title","请输入维修等级");
                                        startActivityForResult(intent1,1);
                                    } else {
                                        level_tv.setText(returnStr);
                                    }
                                }
                            });
                    optionsPickerView.show();

               // setTestLevelData();

                break;
            case R.id.maintain_time_btn://维修时间
                timePickerView = AppUtile.initTimePicker(this, timePickerView, "请选择始修时间", new GetTimeStrCallBack() {

                    @Override
                    public void getStr(Date date, View v) {
                        maintain_time_tv.setText(AppUtile.getTime(date));
                        System.out.println(startTimeTv.getText().toString().substring(0, startTimeTv.getText().toString().length() - 3));
                        time_cost_et.setText(serviceHour(startTimeTv.getText().toString().substring(0, startTimeTv.getText().toString().length() - 3), end_time_tv.getText().toString()));//维修工时
                        //time_cost_et.setText(serviceHour(startTimeTv.getText().toString(),end_time_tv.getText().toString()));//维修工时
                    }
                });
                timePickerView.show();
                break;
            case R.id.end_time_btn://结束时间
                timePickerView = AppUtile.initTimePicker(this, timePickerView, "请选择结束时间", new GetTimeStrCallBack() {
                    @Override
                    public void getStr(Date date, View v) {
                        if (comparingTime(startTimeTv.getText().toString().substring(0, startTimeTv.getText().toString().length() - 3), AppUtile.getTime(date))){
                            end_time_tv.setText(AppUtile.getTime(date));
                            time_cost_et.setText(serviceHour(startTimeTv.getText().toString().substring(0, startTimeTv.getText().toString().length() - 3), end_time_tv.getText().toString()));//维修工时
                            downtimeEt.setText(serviceHour(repairTimeName.getText().toString().substring(0, repairTimeName.getText().toString().length() - 3), end_time_tv.getText().toString()));//停机
                        }else{
                            AbToastUtil.showToast(HospitalFillInRepairOrderActivity.this,"结束时间不能小于计时时间");
                        }

                    }
                });
                timePickerView.show();

                break;
            case R.id.reason_btn://原因分析
                setTestReasonData();
                    optionsPickerView = AppUtile.initOptionsPicker(HospitalFillInRepairOrderActivity.this, optionsPickerView,
                            options1Items, "请选择原因", new GetPickerStrCallBack() {
                                @Override
                                public void getStr(String returnStr, long id) {
                                    if (returnStr.equals("添加")) {
                                        Intent intent1 = new Intent(HospitalFillInRepairOrderActivity.this, InputDialog.class);
                                        intent1.putExtra("title", "请输入原因");
                                        startActivityForResult(intent1, 2);
                                    } else {
                                        reason_tv.setText(returnStr);
                                    }
                                }
                            });
                    optionsPickerView.show();
                break;
            case R.id.user_btn://检查人员

                break;
            case R.id.right_tv_btn:
                //确定按钮
                AbDialogUtil.showProgressDialog(HospitalFillInRepairOrderActivity.this,0,"正在提交");
                if (jsonData.getRepairFeeList() != null)
                    for (int i = 0; i < jsonData.getRepairFeeList().size(); i++) {
                        HospitalFillInRepairOrderCostBase.RepairFeeListBean item = jsonData.getRepairFeeList().get(i);
                        if (item.getPicUrl() != null && item.getPicUrl().size() > 0) {
                            countUpload++;
                            num a = new num();
                            a.outerCon = i;
                            a.AllNum = item.getPicUrl().size();
                            uploadFileUrl.add(a);
                        }
                    }
                if (countUpload > 0) {
                    //如果维修费用里有图片
                    webPic(uploadFileUrl.get(0).outerCon, uploadFileUrl.get(0).AllNum, jsonData.getRepairFeeList().get(uploadFileUrl.get(0).outerCon).getPicUrl().get(0), true);
                } else if (picStrs.size() > 1) {
                    //如果维修费用里没有图片
                    picStrs.remove(picStrs.get(picStrs.size() - 1));
                    webServicePic(picStrs.get(0));
                } else {
                    //如果都没有图片
                    getDataJson();
                }
                break;
        }
    }

    private class num {
        public int outerCon;
        public int AllNum;
    }

    private List<num> uploadFileUrl = new ArrayList<>();
    private String attachmentId = "";
    private int countUpload = 0;
    private int nowNum = 0;//num计数
    private int nowNumPage = 0;//lsitnum的计数

    /**
     * 维修处理上传图片
     */
    private void webPic(final int outerCon, final int AllNum, String fileStr, boolean attachmentIdCon) {
        if (attachmentIdCon) {
            attachmentId = "";
        }

        if (uploadFileUrl != null) {
            RequestBody formBody = new FormBody.Builder()
                    .add("base64String", AppUtile.GetImageStr(fileStr))
                    .add("uploadType", "维修费用上传图片")
                    .build();
            HttpUtile httpUtile = new HttpUtile(HospitalFillInRepairOrderActivity.this, Constant.DOMAIN_NAME + Constant.UPLOADFILE, formBody, new HttpUtileCallBack() {
                @Override
                public void getReturnStr(String returnStr) {
                    UploadFileBase data = JSON.parseObject(returnStr, UploadFileBase.class);

                    if (nowNum < AllNum) {
                        if (nowNum == 0) {
                            attachmentId = data.getData().get(0).getId() + ",";
                        } else {
                            attachmentId += data.getData().get(0).getId() + ",";
                        }
                        webPic(outerCon, AllNum, jsonData.getRepairFeeList().get(outerCon).getPicUrl().get(nowNum), false);
                        nowNum++;
                    } else {
                        jsonData.getRepairFeeList().get(outerCon).setAttachmentId(attachmentId.substring(0, attachmentId.length() - 1));
                        jsonData.getRepairFeeList().get(outerCon).getPicUrl().clear();
                        nowNum = 0;
                        attachmentId = "";
                        nowNumPage++;
                        if (nowNumPage < uploadFileUrl.size()) {
                            webPic(uploadFileUrl.get(nowNumPage).outerCon, uploadFileUrl.get(nowNumPage).AllNum, jsonData.getRepairFeeList().get(uploadFileUrl.get(nowNumPage).outerCon).getPicUrl().get(0), true);
                        } else {
                            //所有维修处理上传图片循环传完
                            if (picStrs.size() > 1) {
                                //如果维修处理里有图片
                                webServicePic(picStrs.get(0));
                            } else {
                                //如果都有图片
                                getDataJson();
                            }
                        }

                    }

                }

                @Override
                public void getReturnStrFailure(String returnStr) {
                    Looper.prepare();
                    AbDialogUtil.removeDialog(HospitalFillInRepairOrderActivity.this);
                    Toast.makeText(HospitalFillInRepairOrderActivity.this, returnStr, Toast.LENGTH_LONG).show();
                    Looper.loop();
                }

                @Override
                public void getErrorStr(String errorStr) {
                    AbDialogUtil.removeDialog(HospitalFillInRepairOrderActivity.this);

                }
            },false);
        }
    }

    private int serviceCon = 0;
    private String serviceId = "";

    private void webServicePic(String picAdd) {

        System.out.println("picStrs.size() = " + picStrs.size());
        RequestBody formBody = new FormBody.Builder()
                .add("base64String", AppUtile.GetImageStr(picAdd))
                .add("uploadType", "维修处理上传图片")
                .build();
        HttpUtile httpUtile = new HttpUtile(HospitalFillInRepairOrderActivity.this, Constant.DOMAIN_NAME + Constant.UPLOADFILE, formBody, new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {
                UploadFileBase data = JSON.parseObject(returnStr, UploadFileBase.class);

                if (serviceCon < picStrs.size() - 2) {
                    if (serviceCon == 0) {
                        serviceId = data.getData().get(0).getId() + ",";
                    } else {
                        serviceId += data.getData().get(0).getId() + ",";
                    }
                    webServicePic(picStrs.get(serviceCon));
                    serviceCon++;
                } else {
                    //维修处理图片上传完
                    if (serviceCon == 0) {
                        serviceId = data.getData().get(0).getId() + ",";
                    } else {
                        serviceId += data.getData().get(0).getId() + ",";
                    }
                    jsonData.setAttachmentID(serviceId.substring(0, serviceId.length() - 1));
                    getDataJson();
                }
            }

            @Override
            public void getReturnStrFailure(String returnStr) {
                Looper.prepare();
                AbDialogUtil.removeDialog(HospitalFillInRepairOrderActivity.this);
                Toast.makeText(HospitalFillInRepairOrderActivity.this, returnStr, Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void getErrorStr(String errorStr) {

            }
        },false);
    }

    private void getDataJson() {
        jsonData.setGid(gid);
        jsonData.setApplyID(applyId);
        jsonData.setApplyGUID(applyGuid);
        for (ServiceLevelBase.DataBean serviceLevelData : YiZhangGuanApplication.getInstance().getServiceLeveList()) {
            if (serviceLevelData.getDicValue().equals(level_tv.getText().toString())) {
                jsonData.setMaintenanceLevel(serviceLevelData.getId());
            }
        }
        jsonData.setNoticStatus(noticStatus);
        jsonData.setProcessTime(startTimeTv.getText().toString());
        jsonData.setMaintenanceStartDate(maintain_time_tv.getText().toString() + ":00");
        jsonData.setMaintenanceEndDate(end_time_tv.getText().toString() + ":00");
        for (ReasonAnalysisBase.DataBean reasonAnalysis : YiZhangGuanApplication.getInstance().getReasonAnalysisList()) {
            if (reasonAnalysis.getDicValue().equals(reason_tv.getText().toString())) {
                jsonData.setFaultAnalysisId(reasonAnalysis.getId());
            }
        }
        if (noticStatus == 3) {
            jsonData.setMaintenanceContacts(userNameEt.getText().toString());
            jsonData.setMaintenancePhone(userPhoneEt.getText().toString());
        }
        jsonData.setTestUser(user_tv.getText().toString());
        jsonData.setManHour(time_cost_et.getText().toString());
        jsonData.setLongShutdown(downtimeEt.getText().toString());
        jsonData.setMaintenanceSituation(happeningEt.getText().toString());

        String jsonStr = jsonData.toString();
        System.out.println("jsonStr = " + jsonStr);
        HttpUtile httpUtile = new HttpUtile(HospitalFillInRepairOrderActivity.this, Constant.DOMAIN_NAME + Constant.SCANCODECHECKIN, jsonStr, new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {
                Looper.prepare();

                AbDialogUtil.removeDialog(HospitalFillInRepairOrderActivity.this);
                Toast.makeText(HospitalFillInRepairOrderActivity.this, "成功", Toast.LENGTH_LONG).show();
                finish();
                Looper.loop();

            }

            @Override
            public void getReturnStrFailure(String returnStr) {
                Looper.prepare();
                AbDialogUtil.removeDialog(HospitalFillInRepairOrderActivity.this);
                Toast.makeText(HospitalFillInRepairOrderActivity.this, returnStr, Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void getErrorStr(String errorStr) {

            }
        },false);

    }

    /**
     * 获取等级数据
     */
    private void setTestLevelData() {
        options1Items.clear();
        if (serviceLevelBase!=null) {
            if (serviceLevelBase != null && serviceLevelBase.getData().size() > 0) {
                for (ServiceLevelBase.DataBean itemData : serviceLevelBase.getData()) {
                    options1Items.add(new ProvinceBean(0, itemData.getDicValue(), "", ""));
                }
                options1Items.add(new ProvinceBean(0, "添加", "", ""));
            }
        }
        options1Items.add(new ProvinceBean(0, "添加", "", ""));


    }

    /**
     * 获取原因测试数据
     */
    private void setTestReasonData() {
        options1Items.clear();
        if (reasonAnalysisBase!=null){
        if (reasonAnalysisBase.getData()!=null&&reasonAnalysisBase.getData().size()>0) {
            for (ReasonAnalysisBase.DataBean itemData : reasonAnalysisBase.getData()) {
                options1Items.add(new ProvinceBean(0, itemData.getDicValue(), "", ""));
            }
            options1Items.add(new ProvinceBean(0, "添加", "", ""));
        }
        }
        options1Items.add(new ProvinceBean(0, "添加", "", ""));

    }




}
