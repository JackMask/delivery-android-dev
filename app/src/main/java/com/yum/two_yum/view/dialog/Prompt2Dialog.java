package com.yum.two_yum.view.dialog;

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

public class Prompt2Dialog extends BaseActivity {


    @Bind(R.id.content_tv)
    TextView contentTv;
    @Bind(R.id.title_tv)
    TextView titleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prompt2_dialog);
        ButterKnife.bind(this);
        String content = getIntent().getStringExtra("content");
        boolean titleShowCon = getIntent().getBooleanExtra("title", false);
        if (!TextUtils.isEmpty(content)) {
            contentTv.setText(content);
        }
        if (titleShowCon) {
            titleTv.setVisibility(View.GONE);
        }


    }


    @OnClick({R.id.ok_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ok_btn:
                setResult(1001);
                finish();
                break;
        }
    }
}
