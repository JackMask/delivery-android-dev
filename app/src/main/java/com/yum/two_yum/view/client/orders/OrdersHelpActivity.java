package com.yum.two_yum.view.client.orders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.utile.AnimationUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 余先德
 * @data 2018/4/11
 */

public class OrdersHelpActivity extends BaseActivity {

    @Bind(R.id.select_layout)
    LinearLayout selectLayout;
    @Bind(R.id.no_data_layout)
    View noDataLayout;
    @Bind(R.id.phone_tv)
    TextView phoneTv;
    @Bind(R.id.email_btn)
    TextView emailBtn;
    @Bind(R.id.email_cancel_btn)
    TextView emailCancelBtn;
    @Bind(R.id.select_email_layout)
    LinearLayout selectEmailLayout;
    private String phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_help);
        ButterKnife.bind(this);
        phone = getIntent().getStringExtra("phone");
        phoneTv.setText(phone + "");
        setView();
    }

    @Override
    protected void setView() {
        super.setView();
    }

    @OnClick({R.id.del_btn, R.id.mail_btn, R.id.phone_btn, R.id.call_btn, R.id.message_btn, R.id.cancel_btn, R.id.no_data_layout
    ,R.id.email_btn,R.id.email_cancel_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.no_data_layout:
                noDataLayout.setVisibility(View.GONE);
                selectLayout.setVisibility(View.GONE);
                selectEmailLayout.setVisibility(View.GONE);
                selectEmailLayout.setAnimation(AnimationUtil.fromNowToBottom());
                selectLayout.setAnimation(AnimationUtil.fromNowToBottom());
                break;
            case R.id.call_btn:
                noDataLayout.setVisibility(View.GONE);
                selectLayout.setVisibility(View.GONE);
                selectEmailLayout.setVisibility(View.GONE);
                selectEmailLayout.setAnimation(AnimationUtil.fromNowToBottom());
                selectLayout.setAnimation(AnimationUtil.fromNowToBottom());
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
                break;
            case R.id.message_btn:
                noDataLayout.setVisibility(View.GONE);
                selectLayout.setVisibility(View.GONE);
                selectEmailLayout.setVisibility(View.GONE);
                selectEmailLayout.setAnimation(AnimationUtil.fromNowToBottom());
                selectLayout.setAnimation(AnimationUtil.fromNowToBottom());
                Uri uri = Uri.parse("smsto:" + phone);
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                startActivity(it);
                break;
            case R.id.email_btn:
                noDataLayout.setVisibility(View.GONE);
                selectLayout.setVisibility(View.GONE);
                selectEmailLayout.setVisibility(View.GONE);
                selectEmailLayout.setAnimation(AnimationUtil.fromNowToBottom());
                selectLayout.setAnimation(AnimationUtil.fromNowToBottom());
                Intent i = new Intent(Intent.ACTION_SEND);
                // i.setType("text/plain"); //模拟器请使用这行
                i.setType("message/rfc822"); // 真机上使用这行
                i.putExtra(Intent.EXTRA_EMAIL,
                        new String[]{Constant.EMAIl});
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.GIVEUSFEEDBACK));
                i.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(i,
                        "Select email application."));
                break;
            case R.id.email_cancel_btn:
            case R.id.cancel_btn:
                noDataLayout.setVisibility(View.GONE);
                selectLayout.setVisibility(View.GONE);
                selectEmailLayout.setVisibility(View.GONE);
                selectEmailLayout.setAnimation(AnimationUtil.fromNowToBottom());
                selectLayout.setAnimation(AnimationUtil.fromNowToBottom());
                break;
            case R.id.del_btn:
                finish();
                break;
            case R.id.mail_btn:
                noDataLayout.setVisibility(View.VISIBLE);
                selectEmailLayout.setVisibility(View.VISIBLE);
                selectEmailLayout.setAnimation(AnimationUtil.fromBottomToNow());
                break;
            case R.id.phone_btn:
                noDataLayout.setVisibility(View.VISIBLE);
                selectLayout.setVisibility(View.VISIBLE);
                selectLayout.setAnimation(AnimationUtil.fromBottomToNow());

                break;
        }
    }
}
