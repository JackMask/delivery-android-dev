package com.yum.two_yum.utile;

import android.view.View;

import java.util.Date;

/**
 * @author 余先德
 * @data 2018/4/19
 */

public interface AppHttpUtileCallBack {
    public abstract void onFailure(String message);
    public abstract void onSuccess(String date, String message);
    public abstract void onError(int code, String message);
}
