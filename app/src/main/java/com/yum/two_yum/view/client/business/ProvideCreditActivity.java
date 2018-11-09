package com.yum.two_yum.view.client.business;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.CreditCardBase;
import com.yum.two_yum.base.CreditCardSaveBase;
import com.yum.two_yum.base.input.ProvideCreditInput;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.ContainsEmojiEditText;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * @author 余先德
 * @data 2018/4/11
 */

public class ProvideCreditActivity extends BaseActivity {

    @Bind(R.id.save_btn)
    TextView saveBtn;
    @Bind(R.id.card_name)
    ContainsEmojiEditText cardName;
    @Bind(R.id.card_num)
    ContainsEmojiEditText cardNum;
    @Bind(R.id.mm_et)
    ContainsEmojiEditText mmEt;
    @Bind(R.id.yy_et)
    ContainsEmojiEditText yyEt;
    @Bind(R.id.card_cvv)
    ContainsEmojiEditText cardCvv;

    private boolean clickCon = false;
    private CreditCardBase.DataBean.UserCreditCardRespResultsBean card ;
    private ProvideCreditInput provideCreditInput =new ProvideCreditInput();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provide_credit);
        ButterKnife.bind(this);
        card = (CreditCardBase.DataBean.UserCreditCardRespResultsBean)getIntent().getSerializableExtra("data");
        setView();
    }



    @Override
    protected void setView() {
        super.setView();
        provideCreditInput.setUserId(YumApplication.getInstance().getMyInformation().getUid());
        if (card!=null){
            cardName.setText(card.getName());
            cardNum.setText(card.getCardNo());
            mmEt.setText(card.getExpiringDate().substring(0,2));
            yyEt.setText(card.getExpiringDate().substring(card.getExpiringDate().length()-2,card.getExpiringDate().length()));
            cardCvv.setText(card.getCvvCode());
        }
        cardName.addTextChangedListener(new TextWatcherMonitor());
        cardNum.addTextChangedListener(new TextWatcherMonitor());
        mmEt.addTextChangedListener(new TextWatcherMonitor());
        yyEt.addTextChangedListener(new TextWatcherMonitor());
        cardCvv.addTextChangedListener(new TextWatcherMonitor());
    }

    private class TextWatcherMonitor implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            checkingData();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private void checkingData(){
        String name = cardName.getText().toString();
        String num = cardNum.getText().toString();
        String day = mmEt.getText().toString()+"/"+yyEt.getText().toString();
        String cvv = cardCvv.getText().toString();
        if (!TextUtils.isEmpty(name)){
            if (!TextUtils.isEmpty(num)){
                if (!TextUtils.isEmpty(day)){
                    if (!TextUtils.isEmpty(cvv)){
                        saveBtn.setTextColor(Color.parseColor("#484848"));
                        clickCon = true;
                    }else{
                        saveBtn.setTextColor(Color.parseColor("#DBDBDB"));
                        clickCon = false;
                    }
                }else{
                    saveBtn.setTextColor(Color.parseColor("#DBDBDB"));
                    clickCon = false;
                }
            }else{
                saveBtn.setTextColor(Color.parseColor("#DBDBDB"));
                clickCon = false;
            }
        }else{
            saveBtn.setTextColor(Color.parseColor("#DBDBDB"));
            clickCon = false;
        }

    }


    @OnClick({R.id.del_btn, R.id.save_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.del_btn:
                finish();
                overridePendingTransition(R.anim.activity_open, R.anim.in);
                break;
            case R.id.save_btn:
                if (clickCon){
                    showLoading();
                    provideCreditInput.setName(cardName.getText().toString());
                    provideCreditInput.setCardNo(cardNum.getText().toString());
                    provideCreditInput.setCvvCode(cardCvv.getText().toString());
                    provideCreditInput.setExpiringDate(mmEt.getText().toString()+"/"+yyEt.getText().toString());
                    OkHttpUtils.postString()
                            .mediaType(MediaType.parse("application/json"))
                            .url(Constant.CREDIT_CARD_SAVE)
                            .addHeader("Accept-Language", AppUtile.getLanguage())
                            .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                            .content(JSON.toJSONString(provideCreditInput))
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int i) {
                                    dismissLoading();
                                    if (AppUtile.getNetWork(e.getMessage())){
                                        showMsg(getString(R.string.NETERROR));
                                    }
                                }

                                @Override
                                public void onResponse(String s, int i) {
                                    dismissLoading();
                                    if (!TextUtils.isEmpty(s)){

                                        if (AppUtile.setCode(s,ProvideCreditActivity.this)){
                                            CreditCardSaveBase data = JSON.parseObject(s,CreditCardSaveBase.class);
                                            card = new CreditCardBase.DataBean.UserCreditCardRespResultsBean();
                                            card.setCardNo(data.getData().getCardNo());
                                            card.setCvvCode(data.getData().getCvvCode());
                                            card.setExpiringDate(data.getData().getExpiringDate());
                                            card.setName(data.getData().getName());
                                            card.setId(data.getData().getId()+"");
                                            card.setUserId(YumApplication.getInstance().getMyInformation().getUid());
                                            Intent intent = new Intent();
                                            intent.putExtra("data",card);
                                            setResult(RESULT_OK,intent);
                                            finish();
                                            overridePendingTransition(R.anim.activity_open, R.anim.in);
                                        }
                                    }
                                }
                            });

                    //finish();
                }
                break;
        }
    }
}
