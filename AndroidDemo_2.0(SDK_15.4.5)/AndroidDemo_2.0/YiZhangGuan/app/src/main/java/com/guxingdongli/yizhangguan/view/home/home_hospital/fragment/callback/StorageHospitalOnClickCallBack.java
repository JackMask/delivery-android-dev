package com.guxingdongli.yizhangguan.view.home.home_hospital.fragment.callback;

/**
 * 验收入库点击回调
 * Created by jackmask on 2018/3/2.
 */

public interface StorageHospitalOnClickCallBack {
    /**
     * 点击回调
     * @param position 点击的item
     * @param type 是否显示
     */
    public abstract void clickNo(int position,boolean type);
    /**
     * 点击回调
     * @param position 点击的item
     * @param type 是否显示
     */
    public abstract void clickTime(String hospitalGuid,String number);

}
