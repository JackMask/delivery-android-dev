package com.guxingdongli.yizhangguan.view.home.home_hospital;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.util.AbToastUtil;
import com.ab.view.sliding.AbSlidingPlayView;
import com.alibaba.fastjson.JSON;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.MaintenanceRecordsAdapter;
import com.guxingdongli.yizhangguan.controller.adapter.MyRepairOrderDetailsContentOneAdapter;
import com.guxingdongli.yizhangguan.controller.adapter.MyRepairOrderDetailsContentOtherAdapter;
import com.guxingdongli.yizhangguan.controller.adapter.RepairOrderDetailsTabAdapter;
import com.guxingdongli.yizhangguan.model.MaintenanceDataBase;
import com.guxingdongli.yizhangguan.model.MyDeviceDetailsBase;
import com.guxingdongli.yizhangguan.model.MyRepairOrderDetailsBase;
import com.guxingdongli.yizhangguan.model.MyRepairOrderDetailsContentBasicInfoBase;
import com.guxingdongli.yizhangguan.model.MyRepairOrderDetailsContentOtherBase;
import com.guxingdongli.yizhangguan.model.MyRepairOrderDetailsTabBase;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.guxingdongli.yizhangguan.view.login.LoginActivity;
import com.squareup.picasso.Picasso;
import com.yuxiaolong.yuxiandelibrary.NoSlideListView;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;
import com.yuxiaolong.yuxiandelibrary.YuXianDeUtile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 维修单详情/设备详情
 * Created by jackmask on 2018/3/4.
 */

public class MyRepairOrderDetailsActivity extends YiZhangGuanActivity {

    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.mAbSlidingPlayView)
    AbSlidingPlayView mAbSlidingPlayView;
    @Bind(R.id.tab_list)
    ListView tabList;
    @Bind(R.id.content_list)
    ListView contentList;
    @Bind(R.id.qr_code)
    LinearLayout qr_code;
    @Bind(R.id.qrcode_iv)
    ImageView qrcodeIv;

    private List<String> dataList = new ArrayList<>(); //图片轮播
    private List<MyRepairOrderDetailsTabBase> tabDataList = new ArrayList<>();//左边tab
    private List<MyRepairOrderDetailsContentBasicInfoBase> basicInfoDataList = new ArrayList<>();//左边基本信息
    private List<MyRepairOrderDetailsContentOtherBase> maintenanceWithin = new ArrayList<>(),
            maintenanceOuter = new ArrayList<>(),
            maintenanceCost = new ArrayList<>(),
            maintenanceAccessories = new ArrayList<>(),
            maintenanceAcceptance = new ArrayList<>();

    private List<MaintenanceDataBase> maintenanceDataList = new ArrayList<>();//左边基本信息

    private RepairOrderDetailsTabAdapter tabAdapter;//左边tab的adapter
    private MyRepairOrderDetailsContentOneAdapter basicInformationAdapter;//右边的基本信息
    private MyRepairOrderDetailsContentOtherAdapter otherAdapter;//右边别的选项卡的内容
    private MaintenanceRecordsAdapter maintenanceRecordsAdapter;//右边维修记录

    private boolean con = false;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) //如果消息是刚才发送的标识
            {
                String returnStr = (String)msg.obj;
                System.out.println("returnStr = " + returnStr);
                MyRepairOrderDetailsBase data = JSON.parseObject(returnStr,MyRepairOrderDetailsBase.class);
                if (MyRepairOrderDetailsModel.getBasicInformation(data.getData())!=null) {
                    for (MyRepairOrderDetailsContentBasicInfoBase item : MyRepairOrderDetailsModel.getBasicInformation(data.getData())) {
                        basicInfoDataList.add(item);
                    }
                    for (MyRepairOrderDetailsContentOtherBase itme :  MyRepairOrderDetailsModel.gethospitalProcessList(data.getData().getHospitalProcessList())){
                        maintenanceWithin.add(itme);
                    }
                    for (MyRepairOrderDetailsContentOtherBase itme : MyRepairOrderDetailsModel.getOutHospitalProcessList(data.getData().getOutHospitalProcessList())){
                        maintenanceOuter.add(itme);
                    }
                    for (MyRepairOrderDetailsContentOtherBase itme : MyRepairOrderDetailsModel.getRepairFeeList(data.getData().getRepairFeeList())){
                        maintenanceCost.add(itme);
                    }
                    for (MyRepairOrderDetailsContentOtherBase itme : MyRepairOrderDetailsModel.getRreplacingFittingList(data.getData().getReplacingFittingList())){
                        maintenanceAccessories.add(itme);
                    }
                    for (MyRepairOrderDetailsContentOtherBase itme : MyRepairOrderDetailsModel.getEvaluationList(data.getData().getEvaluationList())){
                        maintenanceAcceptance.add(itme);
                    }
                }
                if (data.getData().getAttachmentUrl()==null||data.getData().getAttachmentUrl().size()==0){
                    List<String> attachmentUrl = new ArrayList<>();
                    attachmentUrl.add("");
                    slidingData(attachmentUrl);
                }else {
                    slidingData(data.getData().getAttachmentUrl());
                }
                if (basicInformationAdapter!=null)
                basicInformationAdapter.notifyDataSetChanged();
            }else  if (msg.what == 1){
                String returnStr = (String)msg.obj;
                MyDeviceDetailsBase data = JSON.parseObject(returnStr,MyDeviceDetailsBase.class);
                if (MyRepairOrderDetailsModel.getDeviceInformation(data.getData())!=null){
                    for (MyRepairOrderDetailsContentBasicInfoBase item : MyRepairOrderDetailsModel.getDeviceInformation(data.getData())) {
                        basicInfoDataList.add(item);
                    }
                    qrcodeIv.setImageBitmap(YuXianDeUtile.generateBitmap(data.getData().getHospitalGuid()+"|"+data.getData().getMaterialNumber(), YuXianDeUtile.dip2px(MyRepairOrderDetailsActivity.this, 260), YuXianDeUtile.dip2px(MyRepairOrderDetailsActivity.this, 260)));
                }
                if (data.getData().getImgList()!=null&&data.getData().getImgList().size()>0){
                    slidingData(data.getData().getImgList());
                }else{
                    List<String> attachmentUrl = new ArrayList<>();
                    attachmentUrl.add("");
                    slidingData(attachmentUrl);
                }
                if (basicInformationAdapter!=null)
                basicInformationAdapter.notifyDataSetChanged();
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_repair_order_detilas);
        ButterKnife.bind(this);
        setView();
    }

    private void setView(){
        String type  = getIntent().getStringExtra("type");
        if (!TextUtils.isEmpty(type)) {
            con = true;
        }
        setData();

        if (!TextUtils.isEmpty(getIntent().getStringExtra("type"))&&getIntent().getStringExtra("type").equals("equipment")){
            titleText.setText("设备详情");
            slidingData(dataList);
            con = true;
        }else {
            titleText.setText("维修单详情");
        }
        tabAdapter = new RepairOrderDetailsTabAdapter(tabDataList);
        basicInformationAdapter = new MyRepairOrderDetailsContentOneAdapter(basicInfoDataList);
        tabList.setAdapter(tabAdapter);
        contentList.setAdapter(basicInformationAdapter);
        if (con){
            maintenanceRecordsAdapter = new MaintenanceRecordsAdapter(maintenanceDataList);
            contentList.setAdapter(basicInformationAdapter);
            tabList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    for (int con = 0; con < tabDataList.size(); con++) {
                        tabDataList.get(con).setSelectTab(false);
                    }
                    tabDataList.get(i).setSelectTab(true);
                    tabAdapter.notifyDataSetChanged();
                    if (i == 0) {
                        contentList.setVisibility(View.VISIBLE);
                        qr_code.setVisibility(View.GONE);
                        contentList.setAdapter(basicInformationAdapter);
                        basicInformationAdapter.notifyDataSetChanged();
                    } else if (i==1){
                        contentList.setVisibility(View.VISIBLE);
                        qr_code.setVisibility(View.GONE);
                        contentList.setAdapter(maintenanceRecordsAdapter);
                        maintenanceRecordsAdapter.notifyDataSetChanged();
                    }else {
                        contentList.setVisibility(View.GONE);
                        qr_code.setVisibility(View.VISIBLE);


                    }
                }
            });
            if (!TextUtils.isEmpty(getIntent().getStringExtra("guid"))) {
                RequestBody formBody = new FormBody.Builder()
                        .add("guid", getIntent().getStringExtra("guid"))
                        .build();
                HttpUtile httpUtile = new HttpUtile(this, Constant.DOMAIN_NAME + Constant.GETMATERIAL, formBody, new HttpUtileCallBack() {
                    @Override
                    public void getReturnStr(String returnStr) {

                        Message message = new Message();
                        message.what = 1;
                        message.obj = returnStr;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void getReturnStrFailure(String returnStr) {

                    }

                    @Override
                    public void getErrorStr(String errorStr) {

                    }
                },false);
            }
        }else {
            //维修单详情
            tabList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    for (int con = 0; con < tabDataList.size(); con++) {
                        tabDataList.get(con).setSelectTab(false);
                    }
                    tabDataList.get(i).setSelectTab(true);
                    tabAdapter.notifyDataSetChanged();
                    if (i == 0) {
                        contentList.setAdapter(basicInformationAdapter);
                        basicInformationAdapter.notifyDataSetChanged();
                    }else if (i == 1){
                        otherAdapter = new MyRepairOrderDetailsContentOtherAdapter(maintenanceWithin);
                        contentList.setAdapter(otherAdapter);
                        otherAdapter.notifyDataSetChanged();
                    }else if (i == 2){
                        otherAdapter = new MyRepairOrderDetailsContentOtherAdapter(maintenanceOuter);
                        contentList.setAdapter(otherAdapter);
                        otherAdapter.notifyDataSetChanged();
                    }else if (i == 3){
                        otherAdapter = new MyRepairOrderDetailsContentOtherAdapter(maintenanceCost);
                        contentList.setAdapter(otherAdapter);
                        otherAdapter.notifyDataSetChanged();
                    }else if (i == 4){
                        otherAdapter = new MyRepairOrderDetailsContentOtherAdapter(maintenanceAccessories);
                        contentList.setAdapter(otherAdapter);
                        otherAdapter.notifyDataSetChanged();
                    }else if (i == 5){
                        otherAdapter = new MyRepairOrderDetailsContentOtherAdapter(maintenanceAcceptance);
                        contentList.setAdapter(otherAdapter);
                        otherAdapter.notifyDataSetChanged();
                    }else {
                        contentList.setAdapter(otherAdapter);
                        if (otherAdapter!=null)
                        otherAdapter.notifyDataSetChanged();
                    }
                }
            });
            tabAdapter.notifyDataSetChanged();

            RequestBody  formBody = new  FormBody.Builder()
                    .add("guid", getIntent().getStringExtra("guid"))
                    .build();
            HttpUtile httpUtile = new HttpUtile(this, Constant.DOMAIN_NAME + Constant.GETREPAIRALLDETAILS, formBody, new HttpUtileCallBack() {
                @Override
                public void getReturnStr(String returnStr) {

                    Message message = new Message();
                    message.what = 0 ;
                    message.obj = returnStr;
                    handler.sendMessage(message);
                }

                @Override
                public void getReturnStrFailure(String returnStr) {
                    Looper.prepare();
                    Toast.makeText(MyRepairOrderDetailsActivity.this,returnStr,Toast.LENGTH_LONG).show();
                    Looper.loop();
                }

                @Override
                public void getErrorStr(String errorStr) {
                    System.out.println("errorStr = "+errorStr);
                }
            },false);
        }
    }


    /**
     * 轮播图的view
     * @param dataList
     */
    private void slidingData(List<String> dataList){

        //ImageView mPlayImage = (ImageView) mPlayView.findViewById(R.id.mPlayImage);
        for (int i =0 ; i < dataList.size();i++){
            View mPlayView = mInflater.inflate(R.layout.play_view_item, null);
            ImageView imageView = (ImageView) mPlayView.findViewById(R.id.mPlayImage);
            if (!TextUtils.isEmpty(dataList.get(i)))
            Picasso.get().load(dataList.get(i)).placeholder(R.mipmap.default_image).into(imageView);
            mAbSlidingPlayView.addView(mPlayView);
        }
        mAbSlidingPlayView.setNavHorizontalGravity(Gravity.RIGHT);
        Resources r = getResources();
        Bitmap mBitmapOn=BitmapFactory.decodeResource(r, R.mipmap.btn_radio_on);
        Bitmap mBitmapOff=BitmapFactory.decodeResource(r, R.mipmap.btn_radio_off);

        mAbSlidingPlayView.setPageLineImage(mBitmapOn,mBitmapOff);
        mAbSlidingPlayView.setOnItemClickListener(new AbSlidingPlayView.AbOnItemClickListener() {

            @Override
            public void onClick(int position) {
                //AbToastUtil.showToast(MyRepairOrderDetailsActivity.this,"点击"+position);
            }
        });


    }

    @OnClick(R.id.return_btn)
    public void onViewClicked() {
        finish();
    }

    /**
     * 测试数据
     */
    private void setData(){
        if (con) {
            for (int i = 0; i < 2; i++) {
                dataList.add("");
            }
            MyRepairOrderDetailsTabBase a ;
            a = new MyRepairOrderDetailsTabBase("基本信息",true);
            tabDataList.add(a);
            a = new MyRepairOrderDetailsTabBase("维修记录",false);
            tabDataList.add(a);
            a = new MyRepairOrderDetailsTabBase("二维码",false);
            tabDataList.add(a);



            /*List<MyRepairOrderDetailsContentBasicInfoBase> dataList = new ArrayList<>();
            MyRepairOrderDetailsContentBasicInfoBase c;
            MaintenanceDataBase d = new MaintenanceDataBase();
            for (int i = 0 ; i < 4 ; i++) {
                dataList = new ArrayList<>();
                c = new MyRepairOrderDetailsContentBasicInfoBase("报修人员:", "张三");
                dataList.add(c);
                c = new MyRepairOrderDetailsContentBasicInfoBase("报修单号:", "WX20180101151009");
                dataList.add(c);
                c = new MyRepairOrderDetailsContentBasicInfoBase("处理阶段:", "通知院外维修中");
                dataList.add(c);
                c = new MyRepairOrderDetailsContentBasicInfoBase("维修方式:", "院外维修");
                dataList.add(c);
                c = new MyRepairOrderDetailsContentBasicInfoBase("报修科室:", "门诊科");
                dataList.add(c);
                c = new MyRepairOrderDetailsContentBasicInfoBase("维修时间:", "2018-01-01 15:01:01");
                dataList.add(c);
                d.setDataList(dataList);
                maintenanceDataList.add(d);
            }*/

        }else {
            for (int i = 0; i < 5; i++) {
                dataList.add("");
            }
            MyRepairOrderDetailsTabBase a ;
            a = new MyRepairOrderDetailsTabBase("基本信息",true);
            tabDataList.add(a);
            a = new MyRepairOrderDetailsTabBase("院内维修",false);
            tabDataList.add(a);
            a = new MyRepairOrderDetailsTabBase("院外维修",false);
            tabDataList.add(a);
            a = new MyRepairOrderDetailsTabBase("维修费用",false);
            tabDataList.add(a);
            a = new MyRepairOrderDetailsTabBase("配件信息",false);
            tabDataList.add(a);
            a = new MyRepairOrderDetailsTabBase("验收信息",false);
            tabDataList.add(a);
            a = new MyRepairOrderDetailsTabBase("语音报修",false);
            tabDataList.add(a);


        }



    }
}
