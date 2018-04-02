package com.guxingdongli.yizhangguan.view.home.home_merchants;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.dmcbig.mediapicker.PickerActivity;
import com.dmcbig.mediapicker.PickerConfig;
import com.dmcbig.mediapicker.entity.Media;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.PicSelectAdapter;
import com.guxingdongli.yizhangguan.model.DasisDataBase;
import com.guxingdongli.yizhangguan.util.AppUtile;
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
import com.yuxiaolong.yuxiandelibrary.view.ProhibitSlideGridView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 基础数据详情
 * Created by jackmask on 2018/3/11.
 */

public class DaisDataDetailsActivity extends YiZhangGuanActivity implements TakePhoto.TakeResultListener, InvokeListener {

    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.pic_grid)
    ProhibitSlideGridView picLaout;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.specification)
    TextView specification;
    @Bind(R.id.model)
    TextView model;
    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.manufacturer_name)
    TextView manufacturerName;
    @Bind(R.id.registration_number)
    TextView registrationNumber;
    @Bind(R.id.common_name)
    TextView commonName;
    @Bind(R.id.code_tv)
    TextView codeTv;
    @Bind(R.id.bar_code)
    TextView barCode;
    @Bind(R.id.numbering)
    TextView numbering;
    @Bind(R.id.packing_specifications)
    TextView packingSpecifications;
    @Bind(R.id.attributes)
    TextView attributes;
    @Bind(R.id.attributes_high)
    TextView attributesHigh;
    @Bind(R.id.unit)
    TextView unit;

    private TakePhoto takePhoto;
    private static final int CAMERA_STORAGES = 2;
    private InvokeParam invokeParam;
    private PicSelectAdapter adapter;
    ArrayList<Media> select = new ArrayList<>();
    private List<String> picStrs = new ArrayList<>();
    private List<String> addPic  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dais_data_details);
        ButterKnife.bind(this);
        setView();
    }

    private void setView() {
        titleText.setText("详请");
        DasisDataBase.DataBeanX.DataBean data = (DasisDataBase.DataBeanX.DataBean) getIntent().getSerializableExtra("data");
        adapter = new PicSelectAdapter(picStrs);
        picLaout.setNumColumns(4);
        picLaout.setAdapter(adapter);
        setData(data);
    }

    private void setData(DasisDataBase.DataBeanX.DataBean data){
        if (data!=null){
            name.setText(data.getName());
            specification.setText(data.getSpecification());
            model.setText(data.getModel());
            price.setText(data.getPrice()+"");
            manufacturerName.setText(data.getManufacturerName());
            registrationNumber.setText(data.getRegistrationNumber());
            commonName.setText(data.getVerbalName());
            codeTv.setText(data.getCode());
            barCode.setText("");//条码
            numbering.setText(data.getModel());
            packingSpecifications.setText(data.getPacking());
            attributes.setText(data.getProperty());
            attributesHigh.setText(data.getHightProperty());
            unit.setText(data.getUnit());
            if (data.getAttachmentUrl()!=null&&data.getAttachmentUrl().size()>0) {
                for (String item : data.getAttachmentUrl()){
                    picStrs.add(item);
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 321) {
            if (data != null) {
                select = data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT);
                picStrs.clear();
                for (int i = 0; i < select.size(); i++) {
                    picStrs.add(select.get(i).path);
                    addPic.add(select.get(i).path);
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void uploadPic(String picAdd){

    }

    @OnClick({R.id.return_btn, R.id.cancel_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_btn:
                if (addPic.size()>0){

                }
                finish();
                break;
            case R.id.cancel_btn:
                new ActionSheetDialog(this)
                        .builder()
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem("相机", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        if (AppUtile.dynamicAuthority(DaisDataDetailsActivity.this, Manifest.permission.CAMERA, CAMERA_STORAGES)) {
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
                                        Intent intent = new Intent(DaisDataDetailsActivity.this, PickerActivity.class);
                                        intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_IMAGE);//default image and video (Optional)
                                        long maxSize = 188743680L;//long long long
                                        intent.putExtra(PickerConfig.MAX_SELECT_SIZE, maxSize); //default 180MB (Optional)
                                        intent.putExtra(PickerConfig.MAX_SELECT_COUNT, 9-picStrs.size()-1);  //default 40 (Optional)
                                        intent.putExtra(PickerConfig.DEFAULT_SELECTED_LIST, select); // (Optional)
                                        startActivityForResult(intent, 321);
                                    }
                                })
                        .show();
                break;
        }
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

        if (picStrs.size() < 9) {
            Media a = new Media(pathUrl, "", 0, 0, 0, 0, "");
            select.add(a);
            addPic.add(pathUrl);
            picStrs.add(pathUrl);
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
