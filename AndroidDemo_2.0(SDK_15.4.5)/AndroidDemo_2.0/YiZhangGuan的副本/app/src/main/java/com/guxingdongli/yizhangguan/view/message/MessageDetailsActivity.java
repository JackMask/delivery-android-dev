package com.guxingdongli.yizhangguan.view.message;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.YiZhangGuanApplication;
import com.guxingdongli.yizhangguan.model.LoginBase;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.guxingdongli.yizhangguan.view.home.HomeActivity;
import com.guxingdongli.yizhangguan.view.login.LoginActivity;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 消息详情
 * Created by jackmask on 2018/3/3.
 */

public class MessageDetailsActivity extends YiZhangGuanActivity {

    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.tv_conent)
    WebView tvConent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
        ButterKnife.bind(this);
        setView();
        setAnimCon(true);
    }

    private void setView(){


        String jumpType = getIntent().getStringExtra("jumpType");
        String titleStr = getIntent().getStringExtra("titleStr");
        String content = getIntent().getStringExtra("content");
        String msgUrl = getIntent().getStringExtra("msgUrl");
        //titleText.setText(titleStr);
        titleText.setText("消息详情");
        if (jumpType.equals("1")){
            tvConent.loadData(content, "text/html; charset=UTF-8", null);
        }else{
            //加上下面这段代码可以使网页中的链接不以浏览器的方式打开
            tvConent.setWebViewClient(new WebViewClient());
            tvConent.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);//滚动条风格，为0指滚动条不占用空间，直接覆盖在网页上
            //得到webview设置
            WebSettings webSettings = tvConent.getSettings();
            //允许使用javascript
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDefaultTextEncodingName("GBK");//设置字符编码
            tvConent.getSettings().setJavaScriptEnabled(true);
            tvConent.getSettings().setSupportZoom(true);//支持缩放
            tvConent.getSettings().setBuiltInZoomControls(true);
            tvConent.getSettings().setUseWideViewPort(true);
            tvConent.getSettings().setLoadWithOverviewMode(true);
            //将WebAppInterface与javascript绑定
            //tvConent.addJavascriptInterface(new PaymentJavaScriptInterface(), "Android");
            tvConent.loadUrl(msgUrl);//android assets目录下html文件路径url为 file:///android_asset/profile.html
        }

        /*tvConent.getSettings().setDefaultTextEncodingName("UTF-8");
        tvConent.loadData(data.getData().getContent(), "text/html; charset=UTF-8", null*/
    }
    // 设置回退
    // 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK) && tvConent.canGoBack()) {
            tvConent.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @OnClick(R.id.return_btn)
    public void onViewClicked() {
        finish();
    }
}
