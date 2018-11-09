package com.yum.two_yum.utile;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.ab.util.AbDialogUtil;
import com.alibaba.fastjson.JSON;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.LoginBase;
import com.yum.two_yum.base.UploadFlieBase;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.view.login.LoginActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author 余先德
 * @data 2018/4/19
 */

public class AppHttpUtile {
    private final String TAG = "AppHttpUtile";
    private  AppHttpUtileCallBack callBack;
    private  Context context;
    private String languageStr;

    public AppHttpUtileCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(AppHttpUtileCallBack callBack) {
        this.callBack = callBack;
    }

    public AppHttpUtile(Context context,String languageStr){
       this.context = context;
       this.languageStr = languageStr;
   }

    public  void UploadPersonal(String filePath,String typeCode){
        OkHttpClient client=new OkHttpClient();
        MediaType type=MediaType.parse("application/octet-stream");
        File file=new File(filePath);
        RequestBody fileBody=RequestBody.create(type,file);
        RequestBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("typeCode",languageStr)
                .addFormDataPart("picType",typeCode)
                .addFormDataPart("uploadFile",AppUtile.getTime()+".png",fileBody)
                .build();
        Request request=new Request.Builder().url(Constant.FILEINFO_UPLOADPERSONAL)
                .addHeader("User-Agent","android")
                .header("Content-Type","text/html; charset=utf-8;")
                .post(multipartBody)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack != null) {
                    callBack.onFailure(e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String string = response.body().string();

                    if (!TextUtils.isEmpty(string)) {
                        UploadFlieBase uploadFlieBase = JSON.parseObject(string,UploadFlieBase.class);
                        if (callBack != null) {
                            callBack.onSuccess(string,"0");
                        }else{
                            if (callBack != null) {
                                callBack.onError(response.code(),uploadFlieBase.getMessage());
                            }
                        }
                    }else{
                        if (callBack != null) {
                            callBack.onError(response.code(),"1");
                        }
                    }
                }else{
                    if (callBack != null) {
                        callBack.onError(response.code(),"1");
                    }
                }
            }
        });
    }
    public void postJson(String url,String jsonStr){
        OkHttpUtils
                .postString()
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .url(url)
                .content(jsonStr)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        if (callBack != null) {
                            callBack.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(String string, int i) {
                        if (!TextUtils.isEmpty(string)){
                            UploadFlieBase uploadFlieBase = JSON.parseObject(string,UploadFlieBase.class);
                            if (callBack != null) {
                                callBack.onSuccess(string,"0");
                            }else{
                                if (callBack != null) {
                                    callBack.onError(i,uploadFlieBase.getMessage());
                                }
                            }
                        }else{
                            if (callBack != null) {
                                callBack.onError(i,"1");
                            }
                        }
                    }
                });
    }
//    public void postGe(String url,String jsonStr){
//        OkHttpUtils
//                .postString()
//                .mediaType(MediaType.parse("application/json; charset=utf-8"))
//                .url(url)
//                .content(jsonStr)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int i) {
//                        if (callBack != null) {
//                            callBack.onFailure(e.getMessage());
//                        }
//                    }
//
//                    @Override
//                    public void onResponse(String string, int i) {
//                        if (!TextUtils.isEmpty(string)){
//                            UploadFlieBase uploadFlieBase = JSON.parseObject(string,UploadFlieBase.class);
//                            if (callBack != null) {
//                                callBack.onSuccess(string,"0");
//                            }else{
//                                if (callBack != null) {
//                                    callBack.onError(i,uploadFlieBase.getMessage());
//                                }
//                            }
//                        }else{
//                            if (callBack != null) {
//                                callBack.onError(i,"1");
//                            }
//                        }
//                    }
//                });
//    }
}
