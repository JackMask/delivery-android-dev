package com.yuxiaolong.yuxiande;

import android.app.DownloadManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbStringHttpResponseListener;
import com.yuxiaolong.yuxiandelibrary.OkhttpManager;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by jackmask on 2018/3/11.
 */

public class OkhttpActivity extends YuXianDeActivity {

    @Bind(R.id.okhttpclick)
    Button okhttpclick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ok_http_layout);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.okhttpclick)
    public void onViewClicked() {

        new Thread(networkJSONTask).start();



        /*AbHttpUtil abHttpUtil =  AbHttpUtil.getInstance(this);
        abHttpUtil.setTimeout(10000);
        abHttpUtil.post("https://www.yizhangguan.cn/services/v1/GetBusinessNumber", new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                System.out.println("s = " + s);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {

            }
        });*/

    }
    /**
     * 网络操作相关的子线程
     */
    Runnable networkJSONTask = new Runnable() {

        @Override
        public void run() {
            // TODO
            // 在这里进行 http request.网络请求相关操作
            try {
               // OkhttpManager.getInstance().setTrustrCertificates(getAssets().open("yizhangguan.cer"));//放到Assets下或者只要能访问的地方
                OkHttpClient mOkhttpClient= OkhttpManager.getInstance().build();
                MediaType JSON = MediaType.parse("application/text; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, "参数");
                Request request = new Request.Builder().url("http://www.yizhangguan.cn/services/v1/GetBusinessNumber").post(body).build();
                Call call = mOkhttpClient.newCall(request);
                Response response =call.execute();
                String str =response.body().string();
                System.out.println("str = " + str);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("value", str);
                msg.setData(data);
                handler.sendMessage(msg);
            }catch (IOException e){
                System.out.println("eLocalized = " + e.getLocalizedMessage());
                System.out.println("e = " + e.getMessage());
            }

        }
    };
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.i("mylog", "请求结果为-->" + val);
            // TODO
            // UI界面的更新等相关操作
        }
    };
}
