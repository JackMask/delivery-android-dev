package com.yuxiaolong.yuxiandelibrary;

import android.content.Intent;
import android.os.Bundle;

import com.ab.activity.AbActivity;

/**
 * Created by jackmask on 2018/2/14.
 */

public class YuXianDeActivity extends AbActivity {
    /**
     * 简单的退出和跳转动画
     */
    private boolean animCon = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    protected void setAnimCon(boolean animCon) {
        this.animCon = animCon;
    }

    @Override
    public void finish() {
        if (animCon)
        overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
        super.finish();
    }

    @Override
    public void startActivity(Intent intent) {
        if (animCon)
        overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
        super.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (animCon)
        overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
        super.startActivityForResult(intent, requestCode);
    }
}
