package com.guxingdongli.yizhangguan.view.message;

import android.os.Bundle;
import android.view.View;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jackmask on 2018/3/3.
 */

public class DelDialog extends YiZhangGuanActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_del);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ok_btn,R.id.dialog_layout})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.ok_btn:
                finish();
                break;
            case R.id.dialog_layout:
                finish();
                break;
        }

    }
}
