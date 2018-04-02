package com.guxingdongli.yizhangguan.view.home.home_merchants;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;
import com.yuxiaolong.yuxiandelibrary.YuXianDeUtile;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 查看二维码
 * Created by jackmask on 2018/3/8.
 */

public class QRExamineActivity extends YiZhangGuanActivity {

    @Bind(R.id.qrcode_iv)
    ImageView qrcodeIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_qrexamine);
        ButterKnife.bind(this);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("content"))){
            qrcodeIv.setImageBitmap(YuXianDeUtile.generateBitmap(getIntent().getStringExtra("content"), YuXianDeUtile.dip2px(this, 260), YuXianDeUtile.dip2px(this, 260)));
        }

    }

    @OnClick(R.id.return_btn)
    public void onViewClicked() {
        finish();
    }
}
