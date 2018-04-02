package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.YiZhangGuanApplication;
import com.guxingdongli.yizhangguan.model.StorageHospitalBase;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackmask on 2018/3/2.
 */

public class StorageHospitalChildAdapter extends BaseCommAdapter<StorageHospitalBase.DataBeanX.DataBean.DetailsListBean> {
    private boolean clickType = true;
    public StorageHospitalChildAdapter(List<StorageHospitalBase.DataBeanX.DataBean.DetailsListBean> datas) {
        super(datas);
    }

    public void setClick(boolean clickType){
        this.clickType = clickType;
    }

    @Override
    protected void setUI(ViewHolder holder, int position, final Context context) {
        final StorageHospitalBase.DataBeanX.DataBean.DetailsListBean data = getItem(position);
        TextView name_tv = holder.getItemView(R.id.name_tv);
        TextView model_tv = holder.getItemView(R.id.model_tv);
        TextView num_tv = holder.getItemView(R.id.num_tv);
        TextView price_tv = holder.getItemView(R.id.price_tv);
        final LinearLayout item_layout = holder.getItemView(R.id.item_layout);
        name_tv.setText(data.getName());
        model_tv.setText(data.getSpecification());
        num_tv.setText(data.getQuantity()+data.getUnit());
        price_tv.setText(data.getPrice()+"");
        item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickType) {
                    showPopupwindow(context, item_layout,data);
                }
            }
        });

    }

    /**
     * 显示popupwind
     */
    private void showPopupwindow(Context context,View v,StorageHospitalBase.DataBeanX.DataBean.DetailsListBean data){
        View storageHospitalPopup = View.inflate(context, R.layout.popup_storage_hospital_child, null);
        TextView name_tv = (TextView) storageHospitalPopup.findViewById(R.id.name_tv);
        TextView specification_tv = (TextView) storageHospitalPopup.findViewById(R.id.specification_tv);
        TextView model_tv = (TextView) storageHospitalPopup.findViewById(R.id.model_tv);
        TextView package_tv = (TextView) storageHospitalPopup.findViewById(R.id.package_tv);
        TextView price_tv = (TextView) storageHospitalPopup.findViewById(R.id.price_tv);
        TextView batch_number_tv = (TextView) storageHospitalPopup.findViewById(R.id.batch_number_tv);
        TextView validity_period_tv = (TextView) storageHospitalPopup.findViewById(R.id.validity_period_tv);
        TextView quantity_tv = (TextView) storageHospitalPopup.findViewById(R.id.quantity_tv);
        TextView factory_tv = (TextView) storageHospitalPopup.findViewById(R.id.factory_tv);

        PopupWindow popupWindow = new PopupWindow(storageHospitalPopup, YiZhangGuanApplication.getInstance().getStorageHospitalPopupW()
                , YiZhangGuanApplication.getInstance().getStorageHospitalPopupH());
        popupWindow.setFocusable(true);//popupwindow设置焦点
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//设置背景
        popupWindow.setOutsideTouchable(true);//点击外面窗口消失
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        popupWindow.showAsDropDown(v);//在v的下面
        name_tv.setText(data.getName());
        specification_tv.setText(data.getSpecification());
        model_tv.setText(data.getModel());
        package_tv.setText(data.getPacking());
        price_tv.setText(data.getPrice()+"");
        factory_tv.setText(data.getManufacturer());
        batch_number_tv.setText(data.getBatchNumber());
        validity_period_tv.setText(data.getValid());
        quantity_tv.setText(data.getQuantity()+"");

    }
    @Override
    protected int getLayoutId() {
        return R.layout.item_storage_hospital_child;
    }
}