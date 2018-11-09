package com.yum.two_yum.view.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 余先德
 * @data 2018/4/12
 */

public class TermsServiceActivity extends BaseActivity {

    @Bind(R.id.back_btn)
    ImageView backBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_service);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.back_btn)
    public void onViewClicked() {
        finish();
    }
}
