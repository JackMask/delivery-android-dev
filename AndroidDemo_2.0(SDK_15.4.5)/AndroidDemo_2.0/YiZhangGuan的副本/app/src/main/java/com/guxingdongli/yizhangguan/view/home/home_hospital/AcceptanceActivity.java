package com.guxingdongli.yizhangguan.view.home.home_hospital;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dmcbig.mediapicker.PickerActivity;
import com.dmcbig.mediapicker.PickerConfig;
import com.dmcbig.mediapicker.entity.Media;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.AddRepairAdapter;
import com.guxingdongli.yizhangguan.controller.adapter.UserScoreAdapter;
import com.guxingdongli.yizhangguan.model.AcceptanceBase;
import com.guxingdongli.yizhangguan.model.passvalue.AcceptanceValue;
import com.guxingdongli.yizhangguan.util.AppUtile;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
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
import com.yuxiaolong.yuxiandelibrary.NoSlideListView;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;
import com.yuxiaolong.yuxiandelibrary.view.ProhibitSlideGridView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 验收维修单
 * Created by jackmask on 2018/3/6.
 */

public class AcceptanceActivity extends YiZhangGuanActivity implements TakePhoto.TakeResultListener, InvokeListener {

    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.right_img_btn)
    ImageView rightImgBtn;
    @Bind(R.id.no_pass_button)
    RadioButton noPassButton;
    @Bind(R.id.pass_button)
    RadioButton passButton;
    @Bind(R.id.pic_grid)
    ProhibitSlideGridView picGrid;
    @Bind(R.id.evaluation_list)
    NoSlideListView evaluationList;
    @Bind(R.id.equipment_name)
    TextView equipmentName;
    @Bind(R.id.unit_name)
            TextView unitName;
    @Bind(R.id.department_name)
            TextView departmentName;
    @Bind(R.id.repair_name)
            TextView repairName;
    @Bind(R.id.engineer_name)
            TextView engineerName;
    @Bind(R.id.service_time)
            TextView serviceTime;
    @Bind(R.id.service_end_time)
            TextView serviceEndTime;
    @Bind(R.id.reason_tv)
            TextView reasonTv;
    @Bind(R.id.time_cost_tv)
            TextView timeCostTv;
    @Bind(R.id.downtime_tv)
            TextView downtimeTv;
    @Bind(R.id.all_cost_tv)
            TextView allCostTv;

    ArrayList<Media> select;//已经选择的图片数组
    private final int CAMERA_STORAGES = 2;
    @Bind(R.id.repair_order_tv)
    TextView repairOrderTv;
    private List<String> picStrs;//图片数据

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private AddRepairAdapter adapter;
    private UserScoreAdapter userScoreAdapter;
    private List<String> userDatas;
    private String hospitalGUID;
    private int hospitalId;
    private AcceptanceValue inputData = new AcceptanceValue();
    private String applyId;
    private String users;
    private String processID;
    private String userId;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) //如果消息是刚才发送的标识
            {
                AcceptanceBase formBody = (AcceptanceBase)msg.obj;
                repairOrderTv.setText(formBody.getData().getBusinessNumber());
                equipmentName.setText(formBody.getData().getName());
                unitName.setText(formBody.getData().getHospitalName());
                departmentName.setText(formBody.getData().getDepartmentName());
                repairName.setText(formBody.getData().getRepairTime());
                hospitalGUID = formBody.getData().getHospitalGUID();
                applyId = formBody.getData().getApplyId()+"";
                hospitalId = formBody.getData().getHospitalId();
                users = "";
                userId = "";
                processID = "";
                for (AcceptanceBase.DataBean.MaintenanceEngineerBean user : formBody.getData().getMaintenanceEngineer()){
                    users+=user.getMaintenanceEngineer()+",";
                    userId +=user.getMaintenanceEngineerId()+",";
                    processID += user.getProcessID()+",";
                }
                if (!TextUtils.isEmpty(userId)){
                    userId =userId.substring(0,userId.length()-1);
                }
                if (!TextUtils.isEmpty(processID)){
                    processID = processID.substring(0,processID.length()-1);
                }
                engineerName.setText(!TextUtils.isEmpty(users)?users.substring(0,users.length()-1):"");
                serviceTime.setText(formBody.getData().getMaintenanceStartDate());
                serviceEndTime.setText(formBody.getData().getMaintenanceEndDate());
                reasonTv.setText(formBody.getData().getFaultAnalysis());
                timeCostTv.setText(formBody.getData().getManHour()+"");
                downtimeTv.setText(formBody.getData().getLongShutdown()+"");
                allCostTv.setText("¥"+formBody.getData().getTotalRepairPrice());
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptance);
        ButterKnife.bind(this);
        setView();
    }

    private void setView() {
        titleText.setText("维修单");
        userDatas = new ArrayList<>();
        for (int i = 0 ; i < 2 ; i++){
            userDatas.add("");
        }
        repairOrderTv.setText(Html.fromHtml("<u>" + "WX1234567890" + "</u>"));
        picStrs = new ArrayList<>();
        picStrs.add("btn");
        adapter = new AddRepairAdapter(picStrs);
        userScoreAdapter = new UserScoreAdapter(userDatas);
        picGrid.setNumColumns(4);
        picGrid.setAdapter(adapter);
        evaluationList.setAdapter(userScoreAdapter);



        RequestBody formBody = new  FormBody.Builder()
                .add("guid", getIntent().getStringExtra("guid"))
                .build();
        HttpUtile httpUtile = new HttpUtile(this, Constant.DOMAIN_NAME + Constant.GETGUIDREAPIRACCEPTANCE, formBody, new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {
                AcceptanceBase data = JSON.parseObject(returnStr,AcceptanceBase.class);
                Message message = new Message();
                message.obj = data;
                message.what =0 ;
                handler.sendMessage(message);
            }

            @Override
            public void getReturnStrFailure(String returnStr) {
                Looper.prepare();
                Toast.makeText(AcceptanceActivity.this,returnStr,Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void getErrorStr(String errorStr) {

            }
        },false);


    }

    @OnClick({R.id.return_btn, R.id.examine_btn, R.id.details_btn, R.id.cost_btn, R.id.ok_btn})
    public void onViewClicked(View view) {
        Intent intent ;
        switch (view.getId()) {
            case R.id.return_btn:
                //返回
                finish();
                break;
            case R.id.examine_btn:
                //处理情况查看
                if (!TextUtils.isEmpty(hospitalGUID)) {
                    intent = new Intent(this, AcceptanceListActivity.class);
                    intent.putExtra("type", "handle");
                    intent.putExtra("hospitalGUID", hospitalGUID);
                    intent.putExtra("applyId", applyId);
                    startActivity(intent);
                }
                break;
            case R.id.details_btn:
                //零件明细
                if (!TextUtils.isEmpty(hospitalGUID)) {
                    intent = new Intent(this, AcceptanceListActivity.class);
                    intent.putExtra("hospitalGUID", hospitalGUID);
                    intent.putExtra("applyId", applyId);
                    intent.putExtra("type", "components");
                    startActivity(intent);
                }
                break;
            case R.id.cost_btn:
                //费用明细
                if (!TextUtils.isEmpty(hospitalGUID)) {
                    intent = new Intent(this, AcceptanceListActivity.class);
                    intent.putExtra("hospitalGUID", hospitalGUID);
                    intent.putExtra("applyId", applyId);
                    intent.putExtra("type", "cost");
                    startActivity(intent);
                }
                break;
            case R.id.ok_btn:
                //确认
                inputData.setId(0);
                inputData.setHospitalGuid(hospitalGUID);
                inputData.setHospitalId(hospitalId);
                //inputData.setProcessId(processID);
                inputData.setBusinessNumber(repairOrderTv.getText().toString());
                inputData.setEngineerName(engineerName.getText().toString());
                inputData.setEngineerId(userId);
                //inputData.setMaintenanceType();
                /*inputData.setHospitalGuid();
                SCANCODEREAPIRACCEPTANCE*/
                break;
        }
    }

    /**
     * 选择图片
     */
    public void selePic() {
        new ActionSheetDialog(this)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("相机", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                //调取系统相机
                                if (AppUtile.dynamicAuthority(AcceptanceActivity.this, Manifest.permission.CAMERA, CAMERA_STORAGES)) {
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
                                Intent intent = new Intent(AcceptanceActivity.this, PickerActivity.class);
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
                if (select==null){
                    select = new ArrayList<>();
                }else{
                    select.clear();
                }
                for (int i = 0 ; i < selectList.size();i++){
                    select.add(selectList.get(i));
                }
                // picStrs.remove(picStrs.size() - 1);
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
}
