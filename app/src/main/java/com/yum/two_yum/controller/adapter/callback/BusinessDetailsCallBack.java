package com.yum.two_yum.controller.adapter.callback;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author 余先德
 * @data 2018/4/9
 */

public interface BusinessDetailsCallBack {
    public abstract void itemPlusClike(int position, View view, int num, ImageView lessView);
    public abstract void itemLessClike(int position,View view,int num, ImageView plusView);
}
