package com.guxingdongli.yizhangguan.controller.adapter.callback;

import com.guxingdongli.yizhangguan.model.PushSelectHospitalBase;

/**
 * @author 余先德
 * @data 2018/3/24
 */

public interface PushSelectHospitalCallBack {
    public abstract void getData(PushSelectHospitalBase.DataBean item, boolean type);
}
