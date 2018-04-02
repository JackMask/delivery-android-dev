package com.guxingdongli.yizhangguan.view.home.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.model.AcceptanceScanCodeBase;
import com.guxingdongli.yizhangguan.model.AssetsRepairBase;
import com.guxingdongli.yizhangguan.model.RegistrationScanCodeBase;
import com.guxingdongli.yizhangguan.model.RepairManagementScanCodeBase;
import com.guxingdongli.yizhangguan.model.RepairScanCodeBase;
import com.guxingdongli.yizhangguan.model.ReturnBase;
import com.guxingdongli.yizhangguan.model.StoraeHospitalDetailsBase;
import com.guxingdongli.yizhangguan.model.passvalue.ScanCodeInput;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.guxingdongli.yizhangguan.view.home.home_hospital.AcceptanceActivity;
import com.guxingdongli.yizhangguan.view.home.home_hospital.AddRepairActivity;
import com.guxingdongli.yizhangguan.view.home.home_hospital.AssetsRepairActivity;
import com.guxingdongli.yizhangguan.view.home.home_hospital.HospitalFillInRepairOrderActivity;
import com.guxingdongli.yizhangguan.view.home.home_hospital.StoraeHospitalDetailsActivity;
import com.guxingdongli.yizhangguan.view.login.LoginActivity;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * @author 余先德
 * @data 2018/3/26
 */

public class ScanCodeDialog extends YiZhangGuanActivity {


    public final static int STORAGE = 1; //验收入库
    public final static int REPAIR = 2; //报修
    public final static int TIMING = 3; //计时
    public final static int REGISTRATION = 4; //登记
    public final static int ACCEPTANCE = 5; //报修验收


    private ScanCodeInput data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_scan_code_layout);
        data = (ScanCodeInput)getIntent().getSerializableExtra("data");
        RequestBody formBody;
        switch (data.getType()){
            case STORAGE://验收入库
                formBody = new  FormBody.Builder()
                        .add("businessNumber", data.getSingle_or_numbering())
                        .add("hospitalGuid", data.getGuid())
                        .build();
            System.out.println(data.toString());
                accessWeb(Constant.DOMAIN_NAME+Constant.GETQRPURCHASEORDER,formBody,true);
                break;
            case REPAIR://报修
                formBody = new  FormBody.Builder()
                        .add("number", data.getSingle_or_numbering())
                        .add("hospitalGuid", data.getGuid())
                        .build();
                accessWeb(Constant.DOMAIN_NAME+Constant.SCANCODEREPAIR,formBody,true);

                break;
            case TIMING://计时
                formBody = new  FormBody.Builder()
                        .add("number", data.getSingle_or_numbering())
                        .add("hospitalGuid", data.getGuid())
                        .build();
                accessWeb(Constant.DOMAIN_NAME+Constant.SCANCODEACTION,formBody,true);
                break;
            case REGISTRATION://登记
                formBody = new  FormBody.Builder()
                        .add("number", data.getSingle_or_numbering())
                        .add("hospitalGuid", data.getGuid())
                        .build();
                accessWeb(Constant.DOMAIN_NAME+Constant.GETNUMBERCHECKIN,formBody,true);
                break;
            case ACCEPTANCE://报修验收
                formBody = new  FormBody.Builder()
                        .add("number", data.getSingle_or_numbering())
                        .add("hospitalGuid", data.getGuid())
                        .build();
                accessWeb(Constant.DOMAIN_NAME+Constant.GETNUMBERREAPIRACCEPTANCE,formBody,true);

                break;
        }
    }




    private void accessWeb(String url,RequestBody formBody,final boolean type){
        HttpUtile httpUtile =new HttpUtile(this, url, formBody, new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {
                if (type){
                Intent intent;
                switch (data.getType()) {
                    case STORAGE://验收入库
                        StoraeHospitalDetailsBase dataBase = JSON.parseObject(returnStr, StoraeHospitalDetailsBase.class);
                        intent = new Intent(ScanCodeDialog.this, StoraeHospitalDetailsActivity.class);
                        intent.putExtra("guid", dataBase.getData().getGid());
                        startActivity(intent);
                        finish();
                        break;
                    case REPAIR://报修
                        RepairScanCodeBase base = JSON.parseObject(returnStr, RepairScanCodeBase.class);
                        AssetsRepairBase.DataBeanX.DataBean equipment = new AssetsRepairBase.DataBeanX.DataBean();
                        equipment.setName(base.getData().getName());
                        equipment.setId(base.getData().getEquipId());
                        equipment.setUseDepartment(base.getData().getDepartmentName());
                        intent = new Intent(ScanCodeDialog.this, AddRepairActivity.class);
                        intent.putExtra("equipment", equipment);
                        startActivityForResult(intent, 1);
                        finish();
                        break;
                    case TIMING://计时

                        RepairManagementScanCodeBase returnData = JSON.parseObject(returnStr, RepairManagementScanCodeBase.class);
                        if (returnData.getMsg().equals("扫码报修")) {
                            data.setType(REPAIR);
                            RequestBody requestBody = new FormBody.Builder()
                                    .add("number", data.getSingle_or_numbering())
                                    .add("hospitalGuid", data.getGuid())
                                    .build();
                            accessWeb(Constant.DOMAIN_NAME + Constant.SCANCODEREPAIR, requestBody,true);
                        } else if (returnData.getMsg().equals("扫码计时")) {
                            RequestBody formBody = new FormBody.Builder()
                                    .add("number", data.getSingle_or_numbering())
                                    .add("hospitalGuid", data.getGuid())
                                    .build();
                            accessWeb(Constant.DOMAIN_NAME + Constant.SCANCODETIMING, formBody,false);

                        } else if (returnData.getMsg().equals("维修登记")) {
                            data.setType(REGISTRATION);
                            RequestBody formBody = new  FormBody.Builder()
                                    .add("number", data.getSingle_or_numbering())
                                    .add("hospitalGuid", data.getGuid())
                                    .build();
                            System.out.println(data.getSingle_or_numbering()+":"+data.getGuid());
                            accessWeb(Constant.DOMAIN_NAME+Constant.GETNUMBERCHECKIN,formBody,true);
                        } else if (returnData.getMsg().equals("查看报修单")) {

                        } else if (returnData.getMsg().equals("报修验收")) {
                            data.setType(ACCEPTANCE);
                            RequestBody formBody = new  FormBody.Builder()
                                    .add("number", data.getSingle_or_numbering())
                                    .add("hospitalGuid", data.getGuid())
                                    .build();
                            accessWeb(Constant.DOMAIN_NAME+Constant.GETNUMBERREAPIRACCEPTANCE,formBody,true);
                        }


                        break;
                    case REGISTRATION://登记
                            RegistrationScanCodeBase registrationScanCodeBase = JSON.parseObject(returnStr,RegistrationScanCodeBase.class);
                            intent = new Intent(ScanCodeDialog.this, HospitalFillInRepairOrderActivity.class);
                            intent.putExtra("guid", registrationScanCodeBase.getData().getApplyGuid());
                            startActivity(intent);
                            finish();
                        break;
                    case ACCEPTANCE://报修验收
                        AcceptanceScanCodeBase data = JSON.parseObject(returnStr,AcceptanceScanCodeBase.class);
                        intent = new Intent(ScanCodeDialog.this, AcceptanceActivity.class);
                        intent.putExtra("guid",data.getData().getApplyGuid());
                        startActivity(intent);
                        finish();
                        break;
                    }
                }else{
                    Looper.prepare();
                    ReturnBase msg = JSON.parseObject(returnStr,ReturnBase.class);
                    Toast.makeText(ScanCodeDialog.this,msg.getMsg(),Toast.LENGTH_LONG).show();
                    finish();
                    Looper.loop();

                }

            }

            @Override
            public void getReturnStrFailure(String returnStr) {
                Looper.prepare();
                Toast.makeText(ScanCodeDialog.this,returnStr,Toast.LENGTH_LONG).show();
                finish();
                Looper.loop();
            }

            @Override
            public void getErrorStr(String errorStr) {

            }
        },false);

    }
}
