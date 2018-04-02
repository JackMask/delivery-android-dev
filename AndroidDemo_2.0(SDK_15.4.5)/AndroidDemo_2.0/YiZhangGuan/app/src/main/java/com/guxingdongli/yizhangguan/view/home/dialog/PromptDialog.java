package com.guxingdongli.yizhangguan.view.home.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 提示，dialog 样式的activity
 * Created by jackmask on 2017/11/29.
 */

public class PromptDialog extends YiZhangGuanActivity {

    @Bind(R.id.edit_view)
    TextView editView;

    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_prompt);
        ButterKnife.bind(this);
        content = getIntent().getStringExtra("content");
        if (!TextUtils.isEmpty(content)){
            if (content.equals("MakeUpInfo")){
                editView.setText("录入的信息不会被保存，是否返回？");
            }
        }
        //editView.setText(getIntent().getStringExtra("content"));
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
