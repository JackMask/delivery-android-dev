package com.guxingdongli.yizhangguan.view.home.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ab.util.AbToastUtil;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 带输入框的dialog样式的activity
 * Created by jackmask on 2018/3/8.
 */

public class InputDialog extends YiZhangGuanActivity {

    @Bind(R.id.edit_view)
    EditText editView;
    @Bind(R.id.dialog_layout)
    LinearLayout dialogLayout;
    @Bind(R.id.title_tv)
    TextView titleTv;

    private String name = "群聊01";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFinishOnTouchOutside(true);
        setContentView(R.layout.dialog_input_layout);
        ButterKnife.bind(this);
        setView();
    }

    private void setView(){
        titleTv.setText(getIntent().getStringExtra("title"));
    }


    @OnClick({R.id.cancel_btn, R.id.ok_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel_btn:
                finish();
                break;
            case R.id.ok_btn:
                if (!TextUtils.isEmpty(editView.getText().toString())){
                    Intent intent = new Intent();
                    intent.putExtra("content",editView.getText().toString());
                    setResult(1001);
                    finish();
                }

                break;
        }
    }
}
