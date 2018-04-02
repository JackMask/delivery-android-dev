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
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.guxingdongli.yizhangguan.view.home.dialog.InputDialog;
import com.guxingdongli.yizhangguan.view.login.LoginActivity;
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
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 医院工程师填写维修单
 * Created by jackmask on 2018/3/9.
 */

public class HospitalFillInRepairOrderActivity extends YiZhangGuanActivity implements TakePhoto.TakeResultListener, InvokeListener {

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
    ArrayList<Media> select = new ArrayList<>();
    private List<String> picStrs = new ArrayList<>();
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private static final int CAMERA_STORAGES = 2;
    private OptionsPickerView optionsPickerView;
    private TimePickerView timePickerView;
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private String gid;
    private String guid;
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
                guid = data.getData().getApplyGuid();
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
                                Intent intent = new Intent(HospitalFillInRepairOrderActivity.this, PickerActivity.class);
                                intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_IMAGE);//default image and video (Optional)
                                long maxSize = 188743680L;//long long long
                                intent.putExtra(PickerConfig.MAX_SELECT_SIZE, maxSize); //default 180MB (Optional)
                                intent.putExtra(PickerConfig.MAX_SELECT_COUNT, 9);  //default 40 (Optional)
                                intent.putExtra(PickerConfig.DEFAULT_SELECTED_LIST, select); // (Optional)
                                startActivityForResult(intent, 321);
                            }
                        })
                .show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 321) {
            if (data != null) {
                ArrayList<Media> selectList = data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT);
                if (select == null) {
                    select = new ArrayList<>();
                } else {
                    select.clear();
                }
                for (int i = 0; i < selectList.size(); i++) {
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
        } else if (requestCode == 0) {
            if (resultCode == 1001) {
                int allPrice = 0;
                feeListBeans.clear();
                jsonData.getRepairFeeList().clear();
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
                    jsonData.getRepairFeeList().add(item);
                }

                //jsonData.setRepairFeeList(feeListBeans);
                maintainPriceNum.setText("共" + feeListBeans.size() + "项");
                maintainPrice.setText(allPrice + "");
            } else if (resultCode == 1002) {
                int allPrice = 0;
                replacingFittingLists.clear();
                jsonData.getReplacingFittingList().clear();
                for (HospitalFillInRepairOrderCostBase.ReplacingFittingListBean item : (ArrayList<HospitalFillInRepairOrderCostBase.ReplacingFittingListBean>) data.getSerializableExtra("data")) {
                    replacingFittingLists.add(item);
                }
                for (HospitalFillInRepairOrderCostBase.ReplacingFittingListBean item : replacingFittingLists) {
                    allPrice = allPrice + (item.getQuantity() * item.getPrice());
                    jsonData.getReplacingFittingList().add(item);
                }
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
                startActivityForResult(intent, 0);
                break;
            case R.id.btn_accessories:
                //配件费用
                intent = new Intent(this, HospitalEnterAccessoriesActivity.class);
                intent.putExtra("type", false);
                startActivityForResult(intent, 0);
                break;
            case R.id.level_btn://维修等级
                if (setTestLevelData()){
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
                }

               // setTestLevelData();

                break;
            case R.id.maintain_time_btn://维修时间
                timePickerView = AppUtile.initTimePicker(this, timePickerView, "请选择维修时间", new GetTimeStrCallBack() {

                    @Override
                    public void getStr(Date date, View v) {
                        maintain_time_tv.setText(AppUtile.getTime(date));
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
                        System.out.println(AppUtile.getTime(date));
                        if (comparingTime(startTimeTv.getText().toString().substring(0, startTimeTv.getText().toString().length() - 3), end_time_tv.getText().toString())){
                            end_time_tv.setText(AppUtile.getTime(date));
                            time_cost_et.setText(serviceHour(startTimeTv.getText().toString().substring(0, startTimeTv.getText().toString().length() - 3), end_time_tv.getText().toString()));//维修工时
                            downtimeEt.setText(serviceHour(repairTimeName.getText().toString().substring(0, repairTimeName.getText().toString().length() - 3), end_time_tv.getText().toString()));//停机
                        }else{
                            AbToastUtil.showToast(HospitalFillInRepairOrderActivity.this,"结束时间不能小于开始时间");
                        }

                    }
                });
                timePickerView.show();

                break;
            case R.id.reason_btn://原因分析
                //setTestReasonData();
                //
                if (setTestReasonData()) {
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
                }
                break;
            case R.id.user_btn://检查人员

                break;
            case R.id.right_tv_btn:
                //确定按钮
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
                    Toast.makeText(HospitalFillInRepairOrderActivity.this, returnStr, Toast.LENGTH_LONG).show();
                    Looper.loop();
                }

                @Override
                public void getErrorStr(String errorStr) {

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
        jsonData.setApplyGUID(guid);
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
        HttpUtile httpUtile = new HttpUtile(HospitalFillInRepairOrderActivity.this, Constant.DOMAIN_NAME + Constant.SCANCODECHECKIN, jsonStr, new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {
                finish();
            }

            @Override
            public void getReturnStrFailure(String returnStr) {
                System.out.println("returnStr = " + returnStr);
                Looper.prepare();
                Toast.makeText(HospitalFillInRepairOrderActivity.this, returnStr, Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void getErrorStr(String errorStr) {

            }
        },false);
        getDictionaryList("维修级别");
        getDictionaryList("原因分析");
    }

    /**
     * 获取等级数据
     */
    private boolean setTestLevelData() {
        options1Items.clear();
        if (serviceLevelBase!=null&&serviceLevelBase.getData().size()>0) {
            for (ServiceLevelBase.DataBean itemData : serviceLevelBase.getData()) {
                options1Items.add(new ProvinceBean(0, itemData.getDicValue(), "", ""));
            }
            options1Items.add(new ProvinceBean(0, "添加", "", ""));
            return true;
        }
        options1Items.add(new ProvinceBean(0, "添加", "", ""));
        return false;


    }

    /**
     * 获取原因测试数据
     */
    private boolean setTestReasonData() {
        options1Items.clear();
        if (reasonAnalysisBase.getData()!=null&&reasonAnalysisBase.getData().size()>0) {
            for (ReasonAnalysisBase.DataBean itemData : reasonAnalysisBase.getData()) {
                options1Items.add(new ProvinceBean(0, itemData.getDicValue(), "", ""));
            }
            options1Items.add(new ProvinceBean(0, "添加", "", ""));
            return true;
        }
        options1Items.add(new ProvinceBean(0, "添加", "", ""));
        return false;

    }

    /**
     * 获取人员测试数据
     */
    private void setTestUserData() {
       /* options1Items.clear();

        for (HospitalFillInRepairOrderBase.DataBean.MaintenanceEngineerBean user : userList) {
            options1Items.add(new ProvinceBean(0, user.getMaintenanceEngineer(), "", ""));
        }*/
    }


    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
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
        getPic(result.getImages());
    }

    private void getPic(ArrayList<TImage> images) {
        setBitmap(images.get(0).getCompressPath());
    }

    private void setBitmap(String pathUrl) {
        Bitmap bitmap = AppUtile.getSmallBitmap(pathUrl);
        AppUtile.compressBmpToFile(bitmap, new File(pathUrl));
        for (int i = 0; i < picStrs.size(); i++) {
            if (picStrs.get(i).equals("btn")) {
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
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }
}
