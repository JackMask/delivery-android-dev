package com.guxingdongli.yizhangguan.view.home.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ab.util.AbToastUtil;
import com.alibaba.fastjson.JSON;
import com.google.zxing.integration.android.IntentIntegrator;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.YiZhangGuanApplication;
import com.guxingdongli.yizhangguan.model.HomeBase;
import com.guxingdongli.yizhangguan.util.AppUtile;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.guxingdongli.yizhangguan.view.CustomScanActivity;
import com.guxingdongli.yizhangguan.view.home.HomeActivity;
import com.guxingdongli.yizhangguan.view.home.home_hospital.EquipmentDynamicAcivity;
import com.guxingdongli.yizhangguan.view.home.home_hospital.MyRepairOrderActivity;
import com.guxingdongli.yizhangguan.view.home.home_hospital.ServiceAcceptanceActivity;
import com.guxingdongli.yizhangguan.view.home.home_hospital.StorageHospitalActivity;
import com.guxingdongli.yizhangguan.view.message.MessageDetailsActivity;
import com.squareup.picasso.Picasso;
import com.yuxiaolong.yuxiandelibrary.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jackmask on 2018/3/1.
 */

public class HospitalFragment extends Fragment {

    @Bind(R.id.hospital_head_img)
    CircleImageView hospitalHeadImg;
    @Bind(R.id.hospital_name)
    TextView hospitalName;
    @Bind(R.id.doctors_name)
    TextView doctorsName;
    @Bind(R.id.medical_tv)
    TextView medicalTv;
    @Bind(R.id.doctors_id)
    TextView doctorsId;
    @Bind(R.id.order_num)
    TextView orderNum;
    @Bind(R.id.repair_statistics_num)
    TextView repairStatisticsNum;
    @Bind(R.id.repair_statistics_btn)
    LinearLayout repairStatisticsBtn;
    @Bind(R.id.approved_num)
    TextView approvedNum;
    @Bind(R.id.hospital_layout)
    LinearLayout hospitalLayout;
    @Bind(R.id.message_tv)
    TextView messageTv;

    @Bind(R.id.scanning_num)
    TextView scanningNum;

    @Bind(R.id.storage_num)
    TextView storageNum;
    @Bind(R.id.layout_storage_num)
    RelativeLayout layoutStorageNum;
    @Bind(R.id.repair_num)
    TextView repairNum;
    @Bind(R.id.layout_repair_num)
    RelativeLayout layoutRepairNum;
    @Bind(R.id.acceptance_num)
    TextView acceptanceNum;
    @Bind(R.id.layout_acceptance_num)
    RelativeLayout layoutAcceptanceNum;
    @Bind(R.id.statistics_num)
    TextView statisticsNum;
    @Bind(R.id.layout_statistics_num)
    RelativeLayout layoutStatisticsNum;
    @Bind(R.id.equipment_num)
    TextView equipmentNum;
    @Bind(R.id.layout_equipment_num)
    RelativeLayout layoutEquipmentNum;
    private HomeActivity abActivity;
    private View view;
    private  HttpUtile httpUtile;
    private String pushMessageGuid;
    private String pushMessageTitle;
    private String pushMessageType;
    private String pushMessageUrl;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) //如果消息是刚才发送的标识
            {
                for (int i = 0; i < ((HomeBase) msg.obj).getData().getAppModules().size(); i++) {
                    if (((HomeBase) msg.obj).getData().getAppModules().get(i).getName().equals("验收入库")) {
                        if (((HomeBase) msg.obj).getData().getAppModules().get(i).getQuantity() > 0) {
                            storageNum.setText(((HomeBase) msg.obj).getData().getAppModules().get(i).getQuantity() + "");
                            layoutStorageNum.setVisibility(View.VISIBLE);
                        }
                    } else if (((HomeBase) msg.obj).getData().getAppModules().get(i).getName().equals("维修管理")) {
                        if (((HomeBase) msg.obj).getData().getAppModules().get(i).getQuantity() > 0) {
                            repairNum.setText(((HomeBase) msg.obj).getData().getAppModules().get(i).getQuantity() + "");
                            layoutRepairNum.setVisibility(View.VISIBLE);
                        }
                    } else if (((HomeBase) msg.obj).getData().getAppModules().get(i).getName().equals("报修验收")) {
                        if (((HomeBase) msg.obj).getData().getAppModules().get(i).getQuantity() > 0) {
                            acceptanceNum.setText(((HomeBase) msg.obj).getData().getAppModules().get(i).getQuantity() + "");
                            layoutAcceptanceNum.setVisibility(View.VISIBLE);
                        }
                    } else if (((HomeBase) msg.obj).getData().getAppModules().get(i).getName().equals("维修统计")) {
                        if (((HomeBase) msg.obj).getData().getAppModules().get(i).getQuantity() > 0) {
                            statisticsNum.setText(((HomeBase) msg.obj).getData().getAppModules().get(i).getQuantity() + "");
                            layoutStatisticsNum.setVisibility(View.VISIBLE);
                        }
                    } else if (((HomeBase) msg.obj).getData().getAppModules().get(i).getName().equals("设备动态")) {
                        if (((HomeBase) msg.obj).getData().getAppModules().get(i).getQuantity() > 0) {
                            equipmentNum.setText(((HomeBase) msg.obj).getData().getAppModules().get(i).getQuantity() + "");
                            layoutEquipmentNum.setVisibility(View.VISIBLE);
                        }

                    }

                }
                pushMessageGuid = ((HomeBase) msg.obj).getData().getPushMessageGuid();
                pushMessageTitle = ((HomeBase) msg.obj).getData().getPushMessageTitle();
                pushMessageType = ((HomeBase) msg.obj).getData().getPushMessageType();
                pushMessageUrl = ((HomeBase) msg.obj).getData().getPushMessageUrl();
                messageTv.setText(pushMessageTitle);
                orderNum.setText(((HomeBase) msg.obj).getData().getUnAcceptOrder()+"");
                repairStatisticsNum.setText(((HomeBase) msg.obj).getData().getMyRepair()+"");
                approvedNum.setText(((HomeBase) msg.obj).getData().getMyAudit()+"");
            }
        }

        ;
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        abActivity = (HomeActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_merchants_gys, container, false);
        ButterKnife.bind(this, view);
        getData();
        return view;
    }
    public  void refreshUserInfo(){
        hospitalName.setText(YiZhangGuanApplication.getInstance().getMyInfo().getName());
        doctorsName.setText(YiZhangGuanApplication.getInstance().getMyInfo().getRealName());
        medicalTv.setText("   "+YiZhangGuanApplication.getInstance().getMyInfo().getDepartmentName());
        doctorsId.setText(YiZhangGuanApplication.getInstance().getMyInfo().getLoginName());
        if (!TextUtils.isEmpty(YiZhangGuanApplication.getInstance().getMyInfo().getHeadImg()))
            Picasso.get().load(YiZhangGuanApplication.getInstance().getMyInfo().getHeadImg()).placeholder(R.mipmap.uimg).into(hospitalHeadImg);

    }
    private void getData() {
        refreshUserInfo();
        httpUtile= new HttpUtile(getActivity(), Constant.DOMAIN_NAME + Constant.HOME);
        getWebData();
    }

    public void getWebData(){
        httpUtile.setGetData("", false, new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {
                HomeBase data = JSON.parseObject(returnStr, HomeBase.class);
                Message msg = Message.obtain();
                msg.obj = data;
                msg.what = 0;
                handler.sendMessage(msg);
            }
            @Override
            public void getReturnStrFailure(String returnStr) {
            }
            @Override
            public void getErrorStr(String errorStr) {

            }
        });
    }
    public void setHeadImg(String path){
        Bitmap bitmap = AppUtile.getSmallBitmap(path);
        hospitalHeadImg.setImageBitmap(bitmap);
       // Picasso.get().load(path).into(hospitalHeadImg);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    @OnClick({R.id.order_btn, R.id.approved_btn, R.id.storage_btn, R.id.scanning_btn, R.id.repair_btn,
            R.id.acceptance_btn, R.id.statistics_btn, R.id.equipment_btn, R.id.qr_btn, R.id.repair_statistics_btn,R.id.message_btn})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.message_btn:
                intent = new Intent(getActivity(), MessageDetailsActivity.class);
               intent.putExtra("titleStr",pushMessageTitle);
               intent.putExtra("jumpType",pushMessageType);
                intent.putExtra("content",pushMessageTitle);
               intent.putExtra("msgUrl",pushMessageUrl);
               getActivity().startActivity(intent);
                break;
            case R.id.repair_statistics_btn://我的报修
                intent = new Intent(abActivity, MyRepairOrderActivity.class);
                abActivity.startActivity(intent);
                break;
            case R.id.qr_btn://扫码报修
                AbToastUtil.showToast(getContext(),"程序猿哥哥正在开发中~");
                /*new IntentIntegrator(abActivity)
                        .setOrientationLocked(false)
                        .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                        .initiateScan(); // 初始化扫描*/
                break;
            case R.id.order_btn:
                intent = new Intent(abActivity, StorageHospitalActivity.class);
                abActivity.startActivity(intent);
                break;
            case R.id.approved_btn:

                break;
            case R.id.storage_btn://验收入库
                intent = new Intent(abActivity, StorageHospitalActivity.class);
                abActivity.startActivityForResult(intent,0);
                break;
            case R.id.scanning_btn://扫码报修
                new IntentIntegrator(abActivity)
                        .setOrientationLocked(false)
                        .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                        .initiateScan(); // 初始化扫描
                break;
            case R.id.repair_btn://维修管理
                intent = new Intent(abActivity, MyRepairOrderActivity.class);
                abActivity.startActivity(intent);
                break;
            case R.id.acceptance_btn://报修验收
                intent = new Intent(abActivity, ServiceAcceptanceActivity.class);
                abActivity.startActivity(intent);
                break;
            case R.id.statistics_btn:
                AbToastUtil.showToast(getContext(),"程序猿哥哥正在开发中~");
                break;
            case R.id.equipment_btn://设备动态
                intent = new Intent(abActivity, EquipmentDynamicAcivity.class);
                abActivity.startActivity(intent);

                break;
        }
    }
}

