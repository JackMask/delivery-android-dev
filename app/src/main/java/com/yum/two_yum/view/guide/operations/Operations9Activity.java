package com.yum.two_yum.view.guide.operations;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.SelectKewordBase;
import com.yum.two_yum.base.input.MerchantInput;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.view.guide.GuideBaseActivity;
import com.yum.two_yum.view.guide.SelelctKeywordActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * @author 余先德
 * @data 2018/4/14
 */

public class Operations9Activity extends GuideBaseActivity {

    @Bind(R.id.keyword_ed)
    TextView keywordEd;
    @Bind(R.id.keyword_btn)
    LinearLayout keywordBtn;
    @Bind(R.id.save_btn)
    TextView saveBtn;

    private MerchantInput data;
    private boolean click = false;
    private boolean clickType = false;
    private ArrayList<String> keywords = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations9);
        ButterKnife.bind(this);
        data = (MerchantInput)getIntent().getSerializableExtra("data");
        setViewData();
    }
    private void setViewData(){
        if (data!=null){
            keywordEd.setText(data.getKeyword());
            if (!TextUtils.isEmpty(data.getKeyword())){
                click = true;
                saveBtn.setBackgroundResource(R.drawable.button_red_bright);
            }
        }
        keywordEd.addTextChangedListener(new TextChanged());
    }
    private class TextChanged implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            examinationData();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private boolean examinationData(){
        String keyword = keywordEd.getText().toString();

        if (!TextUtils.isEmpty(keyword)){
            click = true;
            saveBtn.setBackgroundResource(R.drawable.button_red_bright);
            return true;
        }else{
            click = false;
            saveBtn.setBackgroundResource(R.drawable.button_red);
            return false;
        }

    }
    @OnClick({R.id.del_btn, R.id.save_out_btn, R.id.save_btn,R.id.keyword_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.del_btn:
                finish();
                break;
            case R.id.save_out_btn:
                clickType = true;
                showLoading();
                sevaData();
                break;
            case R.id.save_btn:
                if (click) {
                    clickType = false;
                    showLoading();
                    sevaData();

                    data.setKeyword(keywordEd.getText().toString());

                }
                break;
            case R.id.keyword_btn:
                Intent intent = new Intent(this, SelelctKeywordActivity.class);
                if (selectDataList!=null&&selectDataList.size()>0) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list", (Serializable) selectDataList);//序列化,要注意转化(Serializable)
                    intent.putExtras(bundle);//发送数据
                }
                //intent.putStringArrayListExtra("keywords",selectDataList);
                startActivityForResult(intent,0);
                break;
        }
    }
    private void sevaData(){
        data.setKeyword(keywordEd.getText().toString());
        OkHttpUtils
                .postString()
                .url(Constant.GET_MERCHANT_SAVE)
                .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                .mediaType(MediaType.parse("application/json"))
                .content(JSON.toJSONString(data))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        dismissLoading();
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        dismissLoading();
                        if (clickType) {
                            setResult(RESULT_OK);
                            YumApplication.getInstance().removeGuideALLActivity_();
                        }else{
                            Intent intent = new Intent(Operations9Activity.this, Operations10Activity.class);
                            intent.putExtra("data",data);
                            startActivityForResult(intent,1);
                        }
                    }
                });
    }
    private List<SelectKewordBase.DataBean.MerchantKeywordRespResultsBean> selectDataList;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK){
                if (requestCode == 0){
                    if (intent!=null) {
                        selectDataList = (List<SelectKewordBase.DataBean.MerchantKeywordRespResultsBean>) intent.getSerializableExtra("list");//获取list方式
                        if (selectDataList != null && selectDataList.size() > 0) {
                            String names = "" ;
                            for (SelectKewordBase.DataBean.MerchantKeywordRespResultsBean item : selectDataList) {
                                names += item.getKeyword();
                            }
                            keywordEd.setText(names);
                        }
                    }
                }else {
                    setResult(RESULT_OK);
                    finish();
                }

        }
    }
}
