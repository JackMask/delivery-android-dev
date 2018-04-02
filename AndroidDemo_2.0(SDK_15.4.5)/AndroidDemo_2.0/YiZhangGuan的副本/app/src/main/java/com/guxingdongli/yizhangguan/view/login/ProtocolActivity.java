package com.guxingdongli.yizhangguan.view.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.MessageAdapter;
import com.guxingdongli.yizhangguan.model.ProtocolBase;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;

/**
 * @author 余先德
 * @data 2018/3/28
 */

public class ProtocolActivity extends YiZhangGuanActivity {

    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.web_view)
    WebView webView;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) //如果消息是刚才发送的标识
            {
                String formBody = (String) msg.obj;
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl(formBody);
            }
        }

        ;
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.protocol_layout);
        ButterKnife.bind(this);
        titleText.setText("用户协议");
        getUrl();

    }

    private void getUrl(){
        HttpUtile httpUtile = new HttpUtile(this, Constant.DOMAIN_NAME + Constant.GETREGISTAGREEURL, new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {
                ProtocolBase data = JSON.parseObject(returnStr,ProtocolBase.class);
                Message message = new Message();
                message.obj = data.getData();
                message.what = 0;
                handler.sendMessage(message);

            }

            @Override
            public void getReturnStrFailure(String returnStr) {
                Looper.prepare();
                Toast.makeText(ProtocolActivity.this,returnStr,Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void getErrorStr(String errorStr) {
                System.out.println("errorStr = "+errorStr);
            }
        },false);
    }

    @OnClick(R.id.return_btn)
    public void onViewClicked() {
        finish();
    }
}
