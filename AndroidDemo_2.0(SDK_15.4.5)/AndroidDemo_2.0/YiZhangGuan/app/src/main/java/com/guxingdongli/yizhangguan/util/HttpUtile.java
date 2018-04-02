package com.guxingdongli.yizhangguan.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;

import com.ab.util.AbDialogUtil;
import com.ab.util.AbToastUtil;
import com.alibaba.fastjson.JSON;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.guxingdongli.yizhangguan.controller.YiZhangGuanApplication;
import com.guxingdongli.yizhangguan.model.ReturnBase;
import com.guxingdongli.yizhangguan.view.login.LoginActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by jackmask on 2018/3/12.
 */

public class HttpUtile {

    private String url;
    private String json;
    private String getStr;
    private RequestBody body;
    private Activity context;
    private boolean type = true;

    /**
     * 带josn数据的post请求
     * @param context
     * @param url
     * @param json
     * @param callBack
     */
    public HttpUtile(Activity context,String url,String json,HttpUtileCallBack callBack,boolean showCon){
        this.url = url;
        this.json = json;
        this.context = context;
        this.callBack = callBack;
        if (showCon)
            AbDialogUtil.showProgressDialog(context,0,"正在加载");
        new Thread(networkJSONTask).start();

    }
    /**
     * 不带数据的post请求
     * @param context
     * @param url
     * @param callBack
     */
    public HttpUtile(Activity context,String url,HttpUtileCallBack callBack,boolean showCon){
        this.url = url;
        this.context = context;
        this.callBack = callBack;
        if (showCon)
            AbDialogUtil.showProgressDialog(context,0,"正在加载");
        new Thread(networkTask).start();

    }
    /**
     * 带键值对数据的post请求
     * @param context
     * @param url
     * @param callBack
     */
    public HttpUtile(Activity context,String url, RequestBody body, HttpUtileCallBack callBack,boolean showCon){
        this.url = url;
        this.context = context;
        this.body = body;
        this.callBack = callBack;
        if (showCon)
        AbDialogUtil.showProgressDialog(context,0,"正在加载");
        new Thread(networkKeyValueTask).start();

    }

    /**
     * 带数据的get请求
     * @param context
     * @param url
     */
    public HttpUtile(Activity context,String url){
        this.url = url;
        this.context = context;

    }
    public void setGetData(String getStr,boolean con,HttpUtileCallBack callBack){
        this.getStr = getStr;
        this.callBack = callBack;
        if (con){
            this.url+=getStr;
        }
        new Thread(networkKeyValueTaskGet).start();
    }
    public void setGetAddressData(String getStr,boolean con,HttpUtileCallBack callBack){
        this.getStr = getStr;
        this.callBack = callBack;
        if (con){
            this.url+=getStr;
        }
        new Thread(networkKeyValueTaskGetSelectAddress).start();
    }

    private HttpUtileCallBack callBack;

    public HttpUtileCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(HttpUtileCallBack callBack) {
        this.callBack = callBack;
    }

    /**
     * 以键值对提交
     * 网络操作相关的子线程
     */
    Runnable networkKeyValueTaskGetSelectAddress = new Runnable() {

        @Override
        public void run() {
            // TODO
            // 在这里进行 http request.网络请求相关操作
            try {
                // OkhttpManager.getInstance().setTrustrCertificates(getAssets().open("yizhangguan.cer"));//放到Assets下或者只要能访问的地方
                OkHttpClient mOkhttpClient= OkhttpManager.getInstance().build();
                Request request;
                if (!TextUtils.isEmpty(YiZhangGuanApplication.getInstance().getMyInfo().getTokenData())) {
                    request = new Request.Builder().addHeader("Authorization", YiZhangGuanApplication.getInstance().getMyInfo().getToken_type()+" "+YiZhangGuanApplication.getInstance().getMyInfo().getTokenData()).url(url).get().build();
                }else{
                    request = new Request.Builder().url(url).get().build();
                }
                Call call = mOkhttpClient.newCall(request);
                Response response =call.execute();
                if (response.isSuccessful()) {
                    String str =response.body().string();
                    if (!TextUtils.isEmpty(str)) {
                        try {
                            ReturnBase msg = JSON.parseObject(str,ReturnBase.class);
                            if (msg.isSuccess()) {
                                if (callBack != null) {
                                    callBack.getReturnStr(str);
                                }
                            }else{
                                if (msg.getMsg().indexOf("会话过期")!=-1){
                                    if (YiZhangGuanApplication.getInstance().isLoginType()){
                                        YiZhangGuanApplication.getInstance().setLoginType(false);
                                        YiZhangGuanApplication.getInstance().getMyInfo().clear();
                                        ActivityCollector.finishAll();
                                        Intent intent = new Intent(context, LoginActivity.class);
                                        context.startActivity(intent);
                                        context.finish();
                                    }
                                }
                                if (callBack != null) {
                                    callBack.getReturnStrFailure(msg.getMsg());
                                }
                            }
                        }catch (Exception e){
                            if (callBack != null) {
                                callBack.getReturnStr(str);
                            }
                        }

                    }else{
                        AbToastUtil.showToast(context,"出错了");
                    }
                } else {

                    System.out.println("Unexpected code " + response);
                    throw new IOException("Unexpected code " + response);
                }

                /*System.out.println("str = " + str);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("value", str);
                msg.setData(data);
                handler.sendMessage(msg);*/
            }catch (IOException e){
                if (callBack!=null){
                    callBack.getErrorStr(e.getMessage());
                }
                System.out.println("eLocalized = " + e.getLocalizedMessage());
                System.out.println("e = " + e.getMessage());
            }

        }
    };

    /**
     * 以键值对提交
     * 网络操作相关的子线程
     */
    Runnable networkKeyValueTaskGet = new Runnable() {

        @Override
        public void run() {
            // TODO
            // 在这里进行 http request.网络请求相关操作
            try {
                // OkhttpManager.getInstance().setTrustrCertificates(getAssets().open("yizhangguan.cer"));//放到Assets下或者只要能访问的地方
                OkHttpClient mOkhttpClient= OkhttpManager.getInstance().build();
                Request request;
                if (!TextUtils.isEmpty(YiZhangGuanApplication.getInstance().getMyInfo().getTokenData())) {
                    request = new Request.Builder().addHeader("Authorization", YiZhangGuanApplication.getInstance().getMyInfo().getToken_type()+" "+YiZhangGuanApplication.getInstance().getMyInfo().getTokenData()).url(url).get().build();
                }else{
                    request = new Request.Builder().url(url).get().build();
                }
                Call call = mOkhttpClient.newCall(request);
                Response response =call.execute();
                if (response.isSuccessful()) {
                    String str =response.body().string();
                    if (!TextUtils.isEmpty(str)) {
                        ReturnBase msg = JSON.parseObject(str,ReturnBase.class);
                        if (msg.isSuccess()){
                            if (callBack != null) {
                                callBack.getReturnStr(str);
                            }
                        }else{
                            if (msg.getMsg().indexOf("会话过期")!=-1){
                                if (YiZhangGuanApplication.getInstance().isLoginType()){
                                    YiZhangGuanApplication.getInstance().setLoginType(false);
                                    YiZhangGuanApplication.getInstance().getMyInfo().clear();
                                    ActivityCollector.finishAll();
                                    Intent intent = new Intent(context, LoginActivity.class);
                                    context.startActivity(intent);
                                    context.finish();
                                }
                            }
                            if (callBack != null) {
                                callBack.getReturnStrFailure(str);
                            }
                        }
                    }else{
                      //  AbToastUtil.showToast(context,"出错了");
                    }
                } else {
                    System.out.println("Unexpected code " + response);
                    throw new IOException("Unexpected code " + response);
                }

                /*System.out.println("str = " + str);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("value", str);
                msg.setData(data);
                handler.sendMessage(msg);*/
            }catch (IOException e){
                if (callBack!=null){
                    callBack.getErrorStr(e.getMessage());
                }
                System.out.println("eLocalized = " + e.getLocalizedMessage());
                System.out.println("e = " + e.getMessage());
            }

        }
    };

    /**
     * 以键值对提交
     * 网络操作相关的子线程
     */
    Runnable networkKeyValueTask = new Runnable() {

        @Override
        public void run() {
            // TODO
            // 在这里进行 http request.网络请求相关操作
            try {
                // OkhttpManager.getInstance().setTrustrCertificates(getAssets().open("yizhangguan.cer"));//放到Assets下或者只要能访问的地方
                OkHttpClient mOkhttpClient= OkhttpManager.getInstance().build();
                Request request;
                if (!TextUtils.isEmpty(YiZhangGuanApplication.getInstance().getMyInfo().getTokenData())) {
                    request = new Request.Builder().addHeader("Authorization", YiZhangGuanApplication.getInstance().getMyInfo().getToken_type()+" "+YiZhangGuanApplication.getInstance().getMyInfo().getTokenData()).url(url).post(body).build();
                }else{
                    request = new Request.Builder().url(url).post(body).build();
                }
                Call call = mOkhttpClient.newCall(request);
                Response response =call.execute();
                if (response.isSuccessful()) {
                    String str =response.body().string();
                    if (!TextUtils.isEmpty(str)) {
                        ReturnBase msg = JSON.parseObject(str,ReturnBase.class);
                        if (msg.isSuccess()){
                        if (callBack != null) {
                            callBack.getReturnStr(str);
                        }
                        }else{
                                if (msg.getMsg().indexOf("会话过期")!=-1){
                                    if (YiZhangGuanApplication.getInstance().isLoginType()){
                                        YiZhangGuanApplication.getInstance().setLoginType(false);
                                        YiZhangGuanApplication.getInstance().getMyInfo().clear();
                                        ActivityCollector.finishAll();
                                        Intent intent = new Intent(context, LoginActivity.class);
                                        context.startActivity(intent);
                                        context.finish();
                                    }
                                }
                                if (callBack != null) {
                                    callBack.getReturnStrFailure(msg.getMsg());
                                }
                        }
                    }else{
                       // AbToastUtil.showToast(context,"出错了");
                    }
                } else {
                    System.out.println("Unexpected code " + response);
                    throw new IOException("Unexpected code " + response);
                }

                /*System.out.println("str = " + str);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("value", str);
                msg.setData(data);
                handler.sendMessage(msg);*/
            }catch (IOException e){
                if (callBack!=null){
                    callBack.getErrorStr(e.getMessage());
                }
                System.out.println("eLocalized = " + e.getLocalizedMessage());
                System.out.println("e = " + e.getMessage());
            }

        }
    };

    /**
     * 以json提交
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
                MediaType Json = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(Json, json);
                Request request;
                if (!TextUtils.isEmpty(YiZhangGuanApplication.getInstance().getMyInfo().getTokenData())) {
                    request = new Request.Builder().addHeader("Authorization", YiZhangGuanApplication.getInstance().getMyInfo().getToken_type()+" "+YiZhangGuanApplication.getInstance().getMyInfo().getTokenData()).url(url).post(body).build();
                }else{
                    request = new Request.Builder().url(url).post(body).build();
                }

                Call call = mOkhttpClient.newCall(request);
                Response response =call.execute();
                if (response.isSuccessful()) {

                    String str = response.body().string();

                    if (!TextUtils.isEmpty(str)) {
                        System.out.println("str = " + str);
                        ReturnBase msg = JSON.parseObject(str,ReturnBase.class);
                        if (msg.isSuccess()) {
                            if (callBack != null) {
                                callBack.getReturnStr(str);
                            }
                        }else{
                            if (msg.getMsg().indexOf("会话过期")!=-1){
                                if (YiZhangGuanApplication.getInstance().isLoginType()){
                                    YiZhangGuanApplication.getInstance().setLoginType(false);
                                    YiZhangGuanApplication.getInstance().getMyInfo().clear();
                                    ActivityCollector.finishAll();
                                    Intent intent = new Intent(context, LoginActivity.class);
                                    context.startActivity(intent);
                                    context.finish();
                                }
                            }
                            if (callBack != null) {
                                callBack.getReturnStrFailure(msg.getMsg());
                            }
                        }
                    }
                }
                /*System.out.println("str = " + str);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("value", str);
                msg.setData(data);
                handler.sendMessage(msg);*/
            }catch (IOException e){
                if (callBack!=null){
                    callBack.getErrorStr(e.getMessage());
                }

            }

        }
    };

    /**
     * 以键值对提交
     * 网络操作相关的子线程
     */
    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            // TODO
            // 在这里进行 http request.网络请求相关操作
            try {
                // OkhttpManager.getInstance().setTrustrCertificates(getAssets().open("yizhangguan.cer"));//放到Assets下或者只要能访问的地方
                OkHttpClient mOkhttpClient= OkhttpManager.getInstance().build();
                Request request;
                RequestBody  formBody = new  FormBody.Builder().build();
                if (!TextUtils.isEmpty(YiZhangGuanApplication.getInstance().getMyInfo().getTokenData())) {
                    request = new Request.Builder().addHeader("Authorization", YiZhangGuanApplication.getInstance().getMyInfo().getToken_type()+" "+YiZhangGuanApplication.getInstance().getMyInfo().getTokenData()).url(url).post(formBody).build();
                }else{
                    request = new Request.Builder().url(url).post(formBody).build();
                }
                Call call = mOkhttpClient.newCall(request);
                Response response =call.execute();
                if (response.isSuccessful()) {
                    String str =response.body().string();
                    if (!TextUtils.isEmpty(str)) {
                        ReturnBase msg = JSON.parseObject(str,ReturnBase.class);
                        if (msg.isSuccess()){
                            if (callBack != null) {

                                callBack.getReturnStr(str);
                            }
                        }else{
                            if (msg.getMsg().indexOf("会话过期")!=-1){
                                if (YiZhangGuanApplication.getInstance().isLoginType()){
                                    YiZhangGuanApplication.getInstance().setLoginType(false);
                                    YiZhangGuanApplication.getInstance().getMyInfo().clear();
                                    ActivityCollector.finishAll();
                                    Intent intent = new Intent(context, LoginActivity.class);
                                    context.startActivity(intent);
                                    context.finish();
                                }
                            }
                            if (callBack != null) {
                                callBack.getReturnStrFailure(msg.getMsg());
                            }
                        }
                    }else{
                        // AbToastUtil.showToast(context,"出错了");
                    }
                } else {
                    System.out.println("Unexpected code " + response);
                    throw new IOException("Unexpected code " + response);
                }

                /*System.out.println("str = " + str);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("value", str);
                msg.setData(data);
                handler.sendMessage(msg);*/
            }catch (IOException e){
                if (callBack!=null){
                    callBack.getErrorStr(e.getMessage());
                }
                System.out.println("eLocalized = " + e.getLocalizedMessage());
                System.out.println("e = " + e.getMessage());
            }

        }
    };
}
