package com.yum.two_yum.view.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;


import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jackmask on 2017/11/29.
 */

public class PromptDialog extends BaseActivity {

    @Bind(R.id.prompt_tv)
    TextView promptTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prompt_dialog);
        ButterKnife.bind(this);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("content")))
         promptTv.setText(getIntent().getStringExtra("content"));
    }



    @OnClick({R.id.cancel_btn, R.id.ok_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel_btn:
                finish();
                break;
            case R.id.ok_btn:
                setResult(1001);
                finish();
                break;
        }
    }
}
