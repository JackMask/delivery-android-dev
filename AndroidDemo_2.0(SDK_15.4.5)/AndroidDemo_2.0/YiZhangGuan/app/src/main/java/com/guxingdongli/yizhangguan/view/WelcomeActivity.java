package com.guxingdongli.yizhangguan.view;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ab.http.AbHttpUtil;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.MyService;
import com.guxingdongli.yizhangguan.controller.YiZhangGuanApplication;
import com.guxingdongli.yizhangguan.util.YiZhangGuanActivity;
import com.guxingdongli.yizhangguan.view.home.HomeActivity;
import com.guxingdongli.yizhangguan.view.login.LoginActivity;
import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jackmask on 2018/2/27.
 */

public class WelcomeActivity extends YiZhangGuanActivity {


    @Bind(R.id.storage_popup)
     View storagePopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        AbHttpUtil a = AbHttpUtil.getInstance(this);

        setAnimCon(true);
        Intent startIntent = new Intent(this, MyService.class);
        startService(startIntent);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                YiZhangGuanApplication.getInstance().setStorageHospitalPopupW(storagePopup.getWidth());
                YiZhangGuanApplication.getInstance().setStorageHospitalPopupH(storagePopup.getHeight());
                Intent i;
                if (!TextUtils.isEmpty(YiZhangGuanApplication.getInstance().getMyInfo().getUid())) {
                    //已经登陆，不是第一次启动
                    i = new Intent(WelcomeActivity.this,
                            HomeActivity.class);
                    YiZhangGuanApplication.getInstance().setEngineer(YiZhangGuanApplication.getInstance().getMyInfo().isEngineer());
                    YiZhangGuanApplication.getInstance().setAppType((YiZhangGuanApplication.getInstance().getMyInfo().getUserType().equals("1"))? true:false);
                } else {
                    //未登陆，不是第一次启动
                    i = new Intent(WelcomeActivity.this,
                            LoginActivity.class);
                }

                startActivity(i);
                finish();

            }
        }, 3000);
        getAppInfo();

    }
    @Override
    protected void onResume() {
        super.onResume();

    }
    /**
     * 权限的结果回调函数
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            Intent startIntent = new Intent(this, MyService.class);
            startService(startIntent);
        }
    }
    private void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //检查权限
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //请求权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }else{
                Intent startIntent = new Intent(this, MyService.class);
                startService(startIntent);
            }
        }
    }







    ////////////////////////////////////自动更新///////////////////////////////////////////////////////////////////
    private void getAppInfo() {
        try {
            pkName = this.getPackageName();
            versionName = this.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;//版本号

        } catch (Exception e) {
        }
    }
    /**
     * 显示软件下载对话框
     */
    private void showDownloadDialog(final boolean con)
    {
        // 构造软件下载对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);
        builder.setTitle("更新中。。。");
        // 给下载对话框增加进度条
        final LayoutInflater inflater = LayoutInflater.from(WelcomeActivity.this);
        View v = inflater.inflate(R.layout.update_ing, null);
        mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
        builder.setView(v);
        // 取消更新
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                // 设置取消状态
                cancelUpdate = true;
                if (!con){
                    finish();
                }

            }

        });
        mDownloadDialog = builder.create();
        mDownloadDialog.show();
        // 现在文件
        downloadApk();
    }
    /**
     * 下载apk文件
     */
    private void downloadApk()
    {
        // 启动新线程下载软件
        new downloadApkThread().start();
    }
    /**
     * 安装APK文件
     */
    private void installApk()
    {
        File apkfile = new File(mSavePath, versionName);
        if (!apkfile.exists())
        {
            return;
        }
        // 通过Intent安装APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        startActivity(i);
    }
    /* 下载中 */
    private static final int DOWNLOAD = 1;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 2;
    private ProgressBar mProgress;//进度条
    /* 是否取消更新 */
    private boolean cancelUpdate = false;
    /* 记录进度条数量 */
    private int progress;
    private String pkName,versionName;
    Method overrideAnimation = null;
    private boolean delayMark = false;
    private Dialog mDownloadDialog;
    private boolean isFirstStart;
    private String mSavePath;
    private String strPth;
    private Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                // 正在下载
                case DOWNLOAD:
                    // 设置进度条位置
                    mProgress.setProgress(progress);
                    break;
                case DOWNLOAD_FINISH:
                    // 安装文件
                    installApk();
                    break;
                default:
                    break;
            }
        };
    };
    /**
     * 下载文件线程
     *
     * @author coolszy
     *@date 2012-4-26
     *@blog http://blog.92coding.com
     */
    private class downloadApkThread extends Thread
    {
        @Override
        public void run()
        {
            try
            {
                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                {
                    // 获得存储卡的路径
                    String sdpath = Environment.getExternalStorageDirectory() + "/";
                    mSavePath = sdpath + "download";
                    URL url = new URL(strPth);
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    // 获取文件大小
                    int length = conn.getContentLength();
                    // 创建输入流
                    InputStream is = conn.getInputStream();

                    File file = new File(mSavePath);
                    // 判断文件目录是否存在
                    if (!file.exists())
                    {
                        file.mkdir();
                    }
                    File apkFile = new File(mSavePath, versionName);
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // 缓存
                    byte buf[] = new byte[1024];
                    // 写入到文件中
                    do
                    {
                        int numread = is.read(buf);
                        count += numread;
                        // 计算进度条位置
                        progress = (int) (((float) count / length) * 100);
                        // 更新进度
                        mHandler.sendEmptyMessage(DOWNLOAD);
                        if (numread <= 0)
                        {
                            // 下载完成
                            mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                            break;
                        }
                        // 写入文件
                        fos.write(buf, 0, numread);
                    } while (!cancelUpdate);// 点击取消就停止下载.
                    fos.close();
                    is.close();
                }
            } catch (MalformedURLException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            // 取消下载对话框显示
            mDownloadDialog.dismiss();
        }
    };

}
