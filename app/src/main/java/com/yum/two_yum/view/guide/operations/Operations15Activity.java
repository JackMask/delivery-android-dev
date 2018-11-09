package com.yum.two_yum.view.guide.operations;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.ProvinceBean;
import com.yum.two_yum.base.input.MerchantInput;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.utile.pickerview.OptionsPickerView;
import com.yum.two_yum.view.guide.GuideBaseActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * @author 余先德
 * @data 2018/4/14
 */

public class Operations15Activity extends GuideBaseActivity {

    @Bind(R.id.map_tv)
    TextView mapTv;
    @Bind(R.id.save_btn)
    TextView saveBtn;

    private MerchantInput data;
    private boolean click = false;
    private boolean clickType = false;

    private OptionsPickerView optionsPickerView;
    private String mapName = "Google地图";
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations15);
        ButterKnife.bind(this);
        data = (MerchantInput) getIntent().getSerializableExtra("data");
        setViewData();
    }

    private void setViewData() {
        if (data != null) {
            mapTv.setText(data.getMap());
            if (!TextUtils.isEmpty(data.getMap())) {
                mapName = data.getMap();
                click = true;
                saveBtn.setBackgroundResource(R.drawable.button_red_bright);
            }
        }
        getTimeItems();
        optionsPickerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText();
                mapTv.setText(tx);
                click = true;
                mapName = tx;
                saveBtn.setBackgroundResource(R.drawable.button_red_bright);


            }
        })

                .setSubmitText("确认")
                .setCancelText("取消")
                .setTitleText("")
                .setTitleBgColor(Color.WHITE)
                .setTitleColor(Color.parseColor("#2177d5"))
                .setSubmitColor(Color.parseColor("#999999"))
                .setCancelColor(Color.parseColor("#999999"))

                .setContentTextSize(18)//设置滚轮文字大小
                .setDividerColor(Color.parseColor("#e7e7e7"))
                .setSelectOptions(0, 0)//默认选中项
                .setOutSideCancelable(true)//点击外部dismiss default true
                .isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                //.setBackgroundId(0x66000000) //设置外部遮罩颜色
                .build();

        //pvOptions.setSelectOptions(1,1);
        optionsPickerView.setPicker(options1Items);//一级选择器*/
    }
    private void getTimeItems() {
        options1Items.add(new ProvinceBean(0, "Google地图", "", ""));
    }

    @OnClick({R.id.del_btn, R.id.save_out_btn, R.id.map_btn, R.id.save_btn})
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
            case R.id.map_btn:
                optionsPickerView.show();
                break;
            case R.id.save_btn:
                if (click) {
                    clickType = true;
                    showLoading();
                    sevaData();

                    //data.setMap(mapName);

                }
                break;
        }
    }
    private void sevaData() {
        // data.setStartTime(openEd.getText().toString());
        //  data.setEndTime(closeEd.getText().toString());
        data.setMap(mapName);
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
                            Intent intent = new Intent(Operations15Activity.this, Operations16Activity.class);
                            intent.putExtra("data", data);
                            startActivityForResult(intent,0);
                        }

                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {

            setResult(RESULT_OK);
            finish();
        }

    }
}
