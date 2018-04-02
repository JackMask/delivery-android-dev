package com.guxingdongli.yizhangguan.controller.adapter.callback;

import com.guxingdongli.yizhangguan.model.PushSelectHospitalBase;

/**
 * @author 余先德
 * @data 2018/3/25
 */

public interface MyOrderNewAdapterCallBack {
    public abstract void adapterCallBack(String guid,int position, boolean type);
}
