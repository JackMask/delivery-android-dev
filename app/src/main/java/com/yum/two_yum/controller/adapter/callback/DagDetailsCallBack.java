package com.yum.two_yum.controller.adapter.callback;

import android.widget.TextView;

/**
 * @author 余先德
 * @data 2018/4/12
 */

public interface DagDetailsCallBack {
    public abstract void clickCall(int position, String num);
    public abstract void clickCancel(int position, String id);
    public abstract void clickOk(int position, String id, TextView okBtn,String type);
    public abstract void clickaddres(int position,String latitude,String longitude,String addressStr);
}
