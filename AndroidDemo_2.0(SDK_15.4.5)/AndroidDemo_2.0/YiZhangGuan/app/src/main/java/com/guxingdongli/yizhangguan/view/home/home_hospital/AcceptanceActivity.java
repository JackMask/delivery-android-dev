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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.util.AbDialogUtil;
import com.alibaba.fastjson.JSON;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.YiZhangGuanApplication;
import com.guxingdongli.yizhangguan.controller.adapter.AddRepairAdapter;
import com.guxingdongli.yizhangguan.controller.adapter.UserScoreAdapter;
import com.guxingdongli.yizhangguan.controller.adapter.callback.UserScoreCallBack;
import com.guxingdongli.yizhangguan.model.AcceptanceBase;
import com.guxingdongli.yizhangguan.model.ServiceAcceptanceInput;
import com.guxingdongli.yizhangguan.model.UploadFileBase;
import com.guxingdongli.yizhangguan.model.passvalue.AcceptanceValue;
import com.guxingdongli.yizhangguan.util.AppUtile;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.util.PicassoImageLoader;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.guxingdongli.yizhangguan.view.login.LoginActivity;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;
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

public class AcceptanceActivity extends YiZhangGuanActivity  {

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
    @Bind(R.id.evaluation_group)
    RadioGroup evaluation_group;
    @Bind(R.id.opinion_et)
    EditText opinion_et;

    private int evaluationCon = 0;
    ArrayList<String> select = new ArrayList<>();//已经选择的图片数组
    private final int CAMERA_STORAGES = 2;
    @Bind(R.id.repair_order_tv)
    TextView repairOrderTv;
    private List<String> picStrs;//图片数据

    private AddRepairAdapter adapter;
    private UserScoreAdapter userScoreAdapter;
    private String hospitalGUID;
    private int hospitalId;
    private AcceptanceValue inputData = new AcceptanceValue();
    private String applyId;
    private List<AcceptanceBase.DataBean.MaintenanceEngineerBean> userData = new ArrayList<>();
    private List<Integer> userIds = new ArrayList<>();
    private List<Integer> processIDs = new ArrayList<>();
    private List<Integer> serviceEvaluations = new ArrayList<>();

    private String users;
    private String processID;
    private int departmentID;
    private String userId;

    private List<ServiceAcceptanceInput> inputs = new ArrayList<>();

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) //如果消息是刚才发送的标识
            {
                AcceptanceBase formBody = (AcceptanceBase)msg.obj;
                departmentID = formBody.getData().getDepartmentID();
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
                    userData.add(user);
                    userIds.add(user.getMaintenanceEngineerId());
                    processIDs.add(user.getProcessID());
                    serviceEvaluations.add(0);
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
                userScoreAdapter.notifyDataSetChanged();
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

        repairOrderTv.setText(AppUtile.getHtmlFrom("<u>" + "WX1234567890" + "</u>"));
        picStrs = new ArrayList<>();
        picStrs.add("btn");
        adapter = new AddRepairAdapter(picStrs);
        userScoreAdapter = new UserScoreAdapter(userData);
        userScoreAdapter.setCallBack(new UserScoreCallBack() {
            @Override
            public void getNum(int position, int num) {
                serviceEvaluations.remove(position);
                serviceEvaluations.add(position,num);
                for (int i = 0 ; i <serviceEvaluations.size();i++){
                    System.out.println(serviceEvaluations.get(i));
                }
            }
        });
        picGrid.setNumColumns(4);
        picGrid.setAdapter(adapter);
        evaluationList.setAdapter(userScoreAdapter);
        evaluation_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton)findViewById(group.getCheckedRadioButtonId());
                if (rb.getText().toString().equals("未通过")){
                    evaluationCon = 1;
                }else{
                    evaluationCon = 2;
                }

            }
        });


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
                AbDialogUtil.showProgressDialog(this,0,"正在提交");
                if (picStrs.size()>1){
                    inputImg(picStrs.get(0));
                }else {
                    List<AcceptanceValue> inputs = new ArrayList<>();
                    int num = userData.size();
                    for (int i = 0; i < num; i++) {
                        AcceptanceValue inputData = new AcceptanceValue();
                        inputData.setId(0);
                        inputData.setApplyId(Integer.valueOf(!TextUtils.isEmpty(applyId) ? applyId : "0"));
                        inputData.setDepartmentId(departmentID);
                        inputData.setHospitalGuid(hospitalGUID);
                        inputData.setHospitalId(hospitalId);
                        inputData.setProcessId(processIDs.get(i));
                        inputData.setBusinessNumber(repairOrderTv.getText().toString());
                        inputData.setEngineerName(userData.get(i).getMaintenanceEngineer());
                        inputData.setEngineerId("" + userIds.get(i));
                        inputData.setAttachmentId(attachmentId);
                        inputData.setMaintenanceType(userData.get(i).getMaintenanceType());
                        if (evaluationCon == 1) {
                            inputData.setAcceptanceStatus("验收未通过");
                        } else {
                            inputData.setAcceptanceStatus("验收通过");
                        }
                        inputData.setEvaluationComment(opinion_et.getText().toString());
                        inputData.setServiceEvaluation(serviceEvaluations.get(i));
                        inputs.add(inputData);
                    }
                    String inputStr = JSON.toJSONString(inputs);
                    inputDataWeb(inputStr);
                }
                //inputData.setMaintenanceType();
                /*inputData.setHospitalGuid();
                SCANCODEREAPIRACCEPTANCE*/
                break;
        }
    }

    private int imgNum = 0;
    private String attachmentId = " ";

    private void inputImg(String imgStr){

        RequestBody formBody = new FormBody.Builder()
                .add("base64String", AppUtile.GetImageStr(imgStr))
                .add("uploadType", "维修验收上传图片")
                .build();
        HttpUtile httpUtile = new HttpUtile(this, Constant.DOMAIN_NAME + Constant.UPLOADFILE, formBody, new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {
               if (picStrs.size()-2>imgNum){
                   UploadFileBase data = JSON.parseObject(returnStr,UploadFileBase.class);
                   if (imgNum == 0) {
                       attachmentId.substring(0,attachmentId.length()-1);
                       attachmentId = data.getData().get(0).getId() + ",";
                   }else{
                       attachmentId+=data.getData().get(0).getId() + ",";
                   }
                   imgNum++;
               }else{
                   List<AcceptanceValue> inputs = new ArrayList<>();
                   int num = userData.size();
                   for (int i = 0; i < num; i++) {
                       AcceptanceValue inputData = new AcceptanceValue();
                       inputData.setId(0);
                       inputData.setApplyId(Integer.valueOf(!TextUtils.isEmpty(applyId) ? applyId : "0"));
                       inputData.setDepartmentId(departmentID);
                       inputData.setHospitalGuid(hospitalGUID);
                       inputData.setHospitalId(hospitalId);
                       inputData.setProcessId(processIDs.get(i));
                       inputData.setBusinessNumber(repairOrderTv.getText().toString());
                       inputData.setEngineerName(userData.get(i).getMaintenanceEngineer());
                       inputData.setEngineerId("" + userIds.get(i));
                       inputData.setMaintenanceType(userData.get(i).getMaintenanceType());
                       if (evaluationCon == 1) {
                           inputData.setAcceptanceStatus("验收未通过");
                       } else {
                           inputData.setAcceptanceStatus("验收通过");
                       }
                       inputData.setAttachmentId(attachmentId.substring(0,attachmentId.length()-1));
                       inputData.setEvaluationComment(opinion_et.getText().toString());
                       inputData.setServiceEvaluation(serviceEvaluations.get(i));
                       inputs.add(inputData);
                   }
                   String inputStr = JSON.toJSONString(inputs);
                   System.out.println("inputStr = "+inputStr);
                   inputDataWeb(inputStr);
               }
            }

            @Override
            public void getReturnStrFailure(String returnStr) {
                Looper.prepare();
                AbDialogUtil.removeDialog(AcceptanceActivity.this);
                Toast.makeText(AcceptanceActivity.this, returnStr, Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void getErrorStr(String errorStr) {
                Looper.prepare();
                AbDialogUtil.removeDialog(AcceptanceActivity.this);
                Toast.makeText(AcceptanceActivity.this, "网络错误", Toast.LENGTH_LONG).show();
                Looper.loop();

            }
        }, false);

    }

    private void inputDataWeb(String inputStr){
        HttpUtile httpUtile = new HttpUtile(this, Constant.DOMAIN_NAME + Constant.SCANCODEREAPIRACCEPTANCE, inputStr, new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {
                Looper.prepare();
                AbDialogUtil.removeDialog(AcceptanceActivity.this);
                Toast.makeText(AcceptanceActivity.this, "成功", Toast.LENGTH_LONG).show();
                setResult(10001);
                finish();
                Looper.loop();
            }

            @Override
            public void getReturnStrFailure(String returnStr) {
                Looper.prepare();
                AbDialogUtil.removeDialog(AcceptanceActivity.this);
                Toast.makeText(AcceptanceActivity.this, returnStr, Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void getErrorStr(String errorStr) {

            }
        }, true);

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
                        select.add(selectList.get(i));
                    }
                    // picStrs.remove(picStrs.size() - 1);
                    picStrs.clear();
                    for (int i = 0; i < select.size(); i++) {
                        picStrs.add(select.get(i));
                    }
                    if (picStrs.size() < 9) {
                        picStrs.add("btn");
                    }

                }else if (photoList.size() > 0){
                    picStrs.clear();

                    if (select==null){
                        select = new ArrayList<>();
                    }
                    select.add(photoList.get(0));
                    for (int i = 0; i < select.size(); i++) {
                        picStrs.add(select.get(i));
                    }
                    if (picStrs.size() < 9) {
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
                                    GalleryConfig galleryConfig = new GalleryConfig.Builder()
                                            .imageLoader(new PicassoImageLoader())    // ImageLoader 加载框架（必填）
                                            .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                                            .provider("com.guxingdongli.yizhangguan.fileprovider")   // provider (必填)
                                            .filePath("/Gallery/Pictures")          // 图片存放路径   （选填）
                                            .isOpenCamera(true)                  // 直接开启相机的标识位
                                            .build();

                                    GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(AcceptanceActivity.this);
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
                                GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(AcceptanceActivity.this);

                            }
                        })
                .show();
    }


}
