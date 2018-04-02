package com.yuxiaolong.yuxiandelibrary;

import android.text.TextUtils;

import com.ab.http.AbStringHttpResponseListener;

/**
 * Created by jackmask on 2018/2/15.
 */

public class YXDResponseLiistener extends AbStringHttpResponseListener {
    @Override
    public void onSuccess(int i, String s) {
        if (!TextUtils.isEmpty(s)){

        }
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onFailure(int i, String s, Throwable throwable) {

    }
}
