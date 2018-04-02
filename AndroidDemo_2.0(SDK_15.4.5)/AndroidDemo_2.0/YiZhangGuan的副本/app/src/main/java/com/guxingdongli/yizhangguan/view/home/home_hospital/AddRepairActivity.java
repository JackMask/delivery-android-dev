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
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.util.AbToastUtil;
import com.alibaba.fastjson.JSON;
import com.dmcbig.mediapicker.PickerActivity;
import com.dmcbig.mediapicker.PickerConfig;
import com.dmcbig.mediapicker.entity.Media;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.YiZhangGuanApplication;
import com.guxingdongli.yizhangguan.controller.adapter.AddRepairAdapter;
import com.guxingdongli.yizhangguan.model.AddrepairBase;
import com.guxingdongli.yizhangguan.model.AssetsRepairBase;
import com.guxingdongli.yizhangguan.model.ProvinceBean;
import com.guxingdongli.yizhangguan.model.UploadFileBase;
import com.guxingdongli.yizhangguan.util.AppUtile;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.GetPickerStrCallBack;
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
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;
import com.yuxiaolong.yuxiandelibrary.pickerview.OptionsPickerView;
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
 * 添加报修单
 * Created by jackmask on 2018/3/5.
 */

public class AddRepairActivity extends YiZhangGuanActivity implements TakePhoto.TakeResultListener,InvokeListener {

    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.search_et)
    EditText searchEt;
    @Bind(R.id.assets_name)
    EditText assetsName;
    @Bind(R.id.department_name)
    TextView departmentName;
    @Bind(R.id.time_tv)
    TextView timeTv;

    @Bind(R.id.malfunction_et)
    EditText malfunctionEt;
    @Bind(R.id.pic_grid)
    ProhibitSlideGridView picGrid;
    @Bind(R.id.repair_number)
    TextView repairNumber;

    private List<String> picStrs;//图片数据
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();//条件选择器的数据
    private OptionsPickerView pvOptions;//条件选择器
    private AddRepairAdapter adapter;
    ArrayList<Media> select;//已经选择的图片数组
    private final int CAMERA_STORAGES = 2;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private String departmentId = "";
    private String hospitalId = "";
    private AssetsRepairBase.DataBeanX.DataBean equipment;
    private boolean equipmentNoNull = false;

    private int allPic = 0;
    private int nowPic = 0;
    private int device_id = 0;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            HttpUtile httpUtile;
            switch (what) {
                case 0:
                   String  content = (String)msg.obj;
                    final AddrepairBase data = JSON.parseObject(content,AddrepairBase.class);
                    repairNumber.setText(data.getData().getBusinessNumber());
                    hospitalId = data.getData().getHospitalGUID();
                    departmentId = data.getData().getDepartmentID()+"";
                    timeTv.setText(data.getData().getRepairTime());
                    if (equipmentNoNull){
                        assetsName.setText(equipment.getName());
                        assetsName.setEnabled(false);
                        departmentName.setText(equipment.getUseDepartment());
                    }else{
                        departmentName.setText(data.getData().getDepartmentName());

                        initOptionData(data.getData().getDepartment());
                        pvOptions = AppUtile.initOptionsPicker(AddRepairActivity.this, pvOptions, options1Items,"选择部门", new GetPickerStrCallBack() {
                            @Override
                            public void getStr(String returnStr,long id) {
                                //选择部门
                                departmentName.setText(returnStr);
                                for (AddrepairBase.DataBean.DepartmentBean dataSelect : data.getData().getDepartment()){
                                    if (dataSelect.getDepartment().equals(returnStr)){
                                        departmentId = dataSelect.getDepartmentId()+"";
                                    }
                                }
                            }
                        });
                    }



                    break;
                case 1:
                    adapter.notifyDataSetChanged();
                    break;
            }

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_add_repair);
        ButterKnife.bind(this);

        setView();
    }

    private void setView(){
        equipment = (AssetsRepairBase.DataBeanX.DataBean)getIntent().getSerializableExtra("equipment");
        if (equipment!=null){
            equipmentNoNull = true;
            device_id = equipment.getId();
        }else{
            equipmentNoNull = false;
        }
        titleText.setText("添加报修单");
        picStrs = new ArrayList<>();
        picStrs.add("btn");
        adapter = new AddRepairAdapter( picStrs);
        picGrid.setNumColumns(4);
        picGrid.setAdapter(adapter);
        HttpUtile httpUtile = new HttpUtile(this, Constant.DOMAIN_NAME + Constant.GETREPAIRNUMBER, "", new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {

                Message message = new Message();
                message.what = 0;
                message.obj = returnStr;
                mHandler.sendMessage(message);

            }

            @Override
            public void getReturnStrFailure(String returnStr) {

            }

            @Override
            public void getErrorStr(String errorStr) {

            }
        },false);

    }




    /**
     * 选择图片
     */
    public void selePic(){
        new ActionSheetDialog(this)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("相机", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                //调取系统相机
                                if (AppUtile.dynamicAuthority(AddRepairActivity.this, Manifest.permission.CAMERA, CAMERA_STORAGES)) {
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
                                Intent intent = new Intent(AddRepairActivity.this, PickerActivity.class);
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

    @OnClick({R.id.return_btn, R.id.select_department, R.id.save_btn,R.id.input_btn,R.id.input_play})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_btn:
                finish();
                break;
            case R.id.input_btn:
                AbToastUtil.showToast(this,"程序猿哥哥正在开发中~");
                break;
            case R.id.input_play:
                AbToastUtil.showToast(this,"程序猿哥哥正在开发中~");
                break;
            case R.id.select_department:
                if (!equipmentNoNull)
                pvOptions.show();
                break;
            case R.id.save_btn:
                if (picStrs!=null&&picStrs.size()>1){
                    nowPic = 0;
                    allPic = picStrs.size();
                    UploadPic();
                }else{
                    repairApply();
                }
                break;
        }
    }
    private String picId = " ";
    private void UploadPic(){
        RequestBody formBody = new  FormBody.Builder()
                .add("base64String", AppUtile.GetImageStr(picStrs.get(nowPic)))
                .add("uploadType", "报修上传图片")
                .build();
        HttpUtile httpUtile = new HttpUtile(AddRepairActivity.this, Constant.DOMAIN_NAME + Constant.UPLOADFILE, formBody, new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {
                UploadFileBase data = JSON.parseObject(returnStr,UploadFileBase.class);

                if (nowPic == 0) {
                    picId.substring(0,picId.length()-1);
                    picId = data.getData().get(0).getId() + ",";
                }else{
                    picId+=data.getData().get(0).getId() + ",";
                }
                nowPic ++;
                if (nowPic<picStrs.size()-1&&!picStrs.get(nowPic).equals("btn")){
                    UploadPic();
                }else{
                    repairApply();
                }
            }

            @Override
            public void getReturnStrFailure(String returnStr) {
                nowPic = 0 ;
                picId = " ";
                Looper.prepare();
                Toast.makeText(AddRepairActivity.this,returnStr,Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void getErrorStr(String errorStr) {

            }
        },false);
    }

    private void repairApply(){

        RequestBody formBody = new  FormBody.Builder()
                .add("Name", assetsName.getText().toString())
                .add("HospitalGUID", hospitalId)
                .add("BusinessNumber", repairNumber.getText().toString())
                .add("DepartmentId", departmentId)
                .add("AccountingID", device_id+"")
                .add("AttachmentId", !TextUtils.isEmpty(picId.substring(0,picId.length()-1))?picId.substring(0,picId.length()-1):"0")
                .add("FaultDescription", malfunctionEt.getText().toString())
                .build();

        HttpUtile httpUtile = new HttpUtile(AddRepairActivity.this, Constant.DOMAIN_NAME + Constant.REPAIRAPPLY, formBody, new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {
                Looper.prepare();
                Toast.makeText(AddRepairActivity.this,"保存成功",Toast.LENGTH_LONG).show();
                setResult(1001);
                finish();
                Looper.loop();

            }

            @Override
            public void getReturnStrFailure(String returnStr) {

                Looper.prepare();
                Toast.makeText(AddRepairActivity.this,returnStr,Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void getErrorStr(String errorStr) {

            }
        },false);

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

    /**
     * 获取测试数据
     */
    private void initOptionData(List<AddrepairBase.DataBean.DepartmentBean> dataList) {

        /**
         * 注意：如果是添加JavaBean实体数据，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */

        for (AddrepairBase.DataBean.DepartmentBean data : dataList){
            options1Items.add(new ProvinceBean(data.getDepartmentId(), data.getDepartment(), "描述部分", "其他数据"));
        }

    }
    ////////////////////////////////////相机选择的方法群//////////////////////////////////////////////////////
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
        PermissionManager.TPermissionType type=PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
        PermissionManager.handlePermissionsResult(this,type,invokeParam,this);
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
    ////////////////////////////////////相机选择的方法群//////////////////////////////////////////////////////
}
