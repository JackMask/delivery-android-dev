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
import android.widget.ImageView;
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
import com.guxingdongli.yizhangguan.view.CustomScanActivity;
import com.guxingdongli.yizhangguan.view.home.HomeActivity;
import com.guxingdongli.yizhangguan.view.home.home_hospital.AddRepairActivity;
import com.guxingdongli.yizhangguan.view.home.home_hospital.MyRepairOrderActivity;
import com.guxingdongli.yizhangguan.view.home.home_merchants.DasisDataActivity;
import com.guxingdongli.yizhangguan.view.home.home_merchants.MyOrderActivity;
import com.guxingdongli.yizhangguan.view.home.home_merchants.PreparationOrderActivity;
import com.guxingdongli.yizhangguan.view.home.home_merchants.StatementManagementActivity;
import com.squareup.picasso.Picasso;
import com.yuxiaolong.yuxiandelibrary.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jackmask on 2018/3/1.
 */

public class MerchantsFragment extends Fragment {

    @Bind(R.id.merchants_head_img)
    CircleImageView merchantsHeadImg;

    @Bind(R.id.fixed_num)
    TextView fixedNum;
    @Bind(R.id.merchants_name)
    TextView merchantsName;
    @Bind(R.id.layout_fixed_num)
    RelativeLayout layoutFixedNum;
    @Bind(R.id.merchants_company)
    TextView merchantsCompany;
    @Bind(R.id.scanning_num)
    TextView scanningNum;
    @Bind(R.id.layout_scanning_num)
    RelativeLayout layoutScanningNum;
    @Bind(R.id.manifest_num)
    TextView manifestNum;
    @Bind(R.id.layout_manifest_num)
    RelativeLayout layoutManifestNum;
    @Bind(R.id.statement_num)
    TextView statementNum;
    @Bind(R.id.layout_statement_num)
    RelativeLayout layoutStatementNum;
    @Bind(R.id.repair_certificate_num)
    TextView repairCertificateNum;
    @Bind(R.id.layout_certificate_num)
    RelativeLayout layoutCertificateNum;
    @Bind(R.id.permit_num)
    TextView permitNum;
    @Bind(R.id.layout_permit_num)
    RelativeLayout layoutPermitNum;
    @Bind(R.id.order_num_m)
    TextView order_num_m;
    @Bind(R.id.warranty_num)
    TextView warranty_num;


    private HomeActivity abActivity;
    private View view;
    @Bind(R.id.qr_btn)
    ImageView qr_btn;

    Handler handler = new Handler()
    {
        public void handleMessage(android.os.Message msg) {
            if(msg.what==0) //如果消息是刚才发送的标识
            {
                for (int i = 0 ; i < ((HomeBase)msg.obj).getData().getAppModules().size() ;i ++){
                    if (((HomeBase)msg.obj).getData().getAppModules().get(i).getName().equals("证照管理")){
                        if (((HomeBase)msg.obj).getData().getAppModules().get(i).getQuantity()>0){
                            permitNum.setText(((HomeBase)msg.obj).getData().getAppModules().get(i).getQuantity()+"");
                            layoutPermitNum.setVisibility(View.VISIBLE);
                        }
                    }else if (((HomeBase)msg.obj).getData().getAppModules().get(i).getName().equals("维修单")){
                        if (((HomeBase)msg.obj).getData().getAppModules().get(i).getQuantity()>0){
                            repairCertificateNum.setText(((HomeBase)msg.obj).getData().getAppModules().get(i).getQuantity()+"");
                            layoutCertificateNum.setVisibility(View.VISIBLE);
                        }
                    }else if(((HomeBase)msg.obj).getData().getAppModules().get(i).getName().equals("对账单管理")){
                        if (((HomeBase)msg.obj).getData().getAppModules().get(i).getQuantity()>0){
                            statementNum.setText(((HomeBase)msg.obj).getData().getAppModules().get(i).getQuantity()+"");
                            layoutStatementNum.setVisibility(View.VISIBLE);
                        }
                    }else if (((HomeBase)msg.obj).getData().getAppModules().get(i).getName().equals("备货单")){
                        if (((HomeBase)msg.obj).getData().getAppModules().get(i).getQuantity()>0){
                            manifestNum.setText(((HomeBase)msg.obj).getData().getAppModules().get(i).getQuantity()+"");
                            layoutManifestNum.setVisibility(View.VISIBLE);
                        }
                    }else if (((HomeBase)msg.obj).getData().getAppModules().get(i).getName().equals("基础数据")){
                        if (((HomeBase)msg.obj).getData().getAppModules().get(i).getQuantity()>0) {
                            scanningNum.setText(((HomeBase)msg.obj).getData().getAppModules().get(i).getQuantity() + "");
                            layoutScanningNum.setVisibility(View.VISIBLE);
                        }

                    }else if (((HomeBase)msg.obj).getData().getAppModules().get(i).getName().equals("订单管理")){
                        if (((HomeBase)msg.obj).getData().getAppModules().get(i).getQuantity()>0) {
                            fixedNum.setText(((HomeBase)msg.obj).getData().getAppModules().get(i).getQuantity() + "");

                            layoutFixedNum.setVisibility(View.VISIBLE);
                        }
                    }

                }
                order_num_m.setText(((HomeBase)msg.obj).getData().getUnHandleOrder() + "");
                warranty_num.setText(((HomeBase)msg.obj).getData().getUnHandleRepair()+"");
            }
        };
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        abActivity = (HomeActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_merchants, container, false);
        ButterKnife.bind(this, view);
        getData();
        return view;
    }

    public  void refreshUserInfo(){
        merchantsName.setText(YiZhangGuanApplication.getInstance().getMyInfo().getRealName());
        merchantsCompany.setText(YiZhangGuanApplication.getInstance().getMyInfo().getName());
        if (!TextUtils.isEmpty(YiZhangGuanApplication.getInstance().getMyInfo().getHeadImg()))
            Picasso.get().load(YiZhangGuanApplication.getInstance().getMyInfo().getHeadImg()).placeholder(R.mipmap.uimg).into(merchantsHeadImg);

    }

    public void getData() {
        refreshUserInfo();
        //Picasso.get().load(BaseHttp.SERVER_ADDRESS + data.getData().getFace()).transform(new RoundTransform(20)).placeholder(R.drawable.ease_default_avatar).into(head);


        HttpUtile httpUtile = new HttpUtile(getActivity(), Constant.DOMAIN_NAME + Constant.HOME);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void setHeadImg(String path){
        Bitmap bitmap = AppUtile.getSmallBitmap(path);
        merchantsHeadImg.setImageBitmap(bitmap);
        //Picasso.get().load(path).into(merchantsHeadImg);

    }
    @OnClick(R.id.qr_btn)
    public void onViewClicked() {
        new IntentIntegrator(abActivity)
                .setOrientationLocked(false)
                .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                .initiateScan(); // 初始化扫描
    }

    @OnClick({R.id.order_btn_m, R.id.warranty_btn, R.id.fixed_btn, R.id.scanning_btn, R.id.manifest_btn, R.id.statement_btn, R.id.repair_certificate_btn, R.id.permit_btn})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.order_btn_m://待验收订单
                intent = new Intent(getActivity(), MyOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.warranty_btn:
                intent = new Intent(getActivity(), MyRepairOrderActivity.class);
                intent.putExtra("type",true);
                startActivity(intent);
                break;
            case R.id.fixed_btn://订单管理
                intent = new Intent(getActivity(), MyOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.scanning_btn://基础数据
                intent = new Intent(getActivity(), DasisDataActivity.class);
                startActivity(intent);
                break;
            case R.id.manifest_btn://我的备货单
                intent = new Intent(getActivity(), PreparationOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.statement_btn://对账单管理
                intent = new Intent(getActivity(), StatementManagementActivity.class);
                startActivity(intent);
                break;
            case R.id.repair_certificate_btn://维修单
                intent = new Intent(getActivity(), MyRepairOrderActivity.class);
                intent.putExtra("type",true);
                startActivity(intent);
                break;
            case R.id.permit_btn:
                AbToastUtil.showToast(getContext(),"程序猿哥哥正在开发中~");
                break;
        }
    }
}
