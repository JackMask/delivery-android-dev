package com.yum.two_yum.controller;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.text.TextUtils;

import com.yum.two_yum.R;
import com.yum.two_yum.controller.db.AddressDB;
import com.yum.two_yum.controller.db.PBankAccount;
import com.yum.two_yum.controller.db.PMyInformation;
import com.yum.two_yum.controller.db.POperationsSettings;
import com.yum.two_yum.controller.db.PYourId;
import com.yum.two_yum.utile.AppHttpUtile;
import com.yum.two_yum.view.client.ClientActivity;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

/**
 * @author 余先德
 * @data 2018/4/8
 */

public class YumApplication  extends Application {
    private int notLogInType = -1;
    private boolean conType = false;
    private boolean noLocation = false;
    private static YumApplication instance;
    private boolean inputType = true;
    private int view106dp = 0;
    private int view127dp = 0;
    private int viewh106dp = 0;
    private int dishesTitle = 0;
    private int dishesItem = 0;
    private boolean loginNowType = false;
    private String Lat,Long;
    private PMyInformation myInformation;
    private PYourId pYourId;
    private PBankAccount pBankAccount;
    private POperationsSettings pOperationsSettings;
    private List<Activity> oList;//用于存放所有启动的Activity的集合
    private List<Activity> guideActivitys;
    private boolean releaseType = false;
    private boolean clientOk = false;

    private boolean loginType = false;
    private boolean backToHome = false;
    private Activity ordersDetailsActivity;
    private boolean registered;
    private AddressDB addressDB;


    public void delOrdersDetailsActivity() {
        if (ordersDetailsActivity!=null) {
            if (isExistMainActivity(ordersDetailsActivity.getClass())) {
                ordersDetailsActivity.finish();
            }
        }
    }
    //判断某一个类是否存在任务栈里面
    private boolean isExistMainActivity(Class activity){
        Intent intent = new Intent(this, activity);
        ComponentName cmpName = intent.resolveActivity(getPackageManager());
        boolean flag = false;
        if (cmpName != null) { // 说明系统中存在这个activity
            ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfoList = am.getRunningTasks(10);  //获取从栈顶开始往下查找的10个activity
            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                if (taskInfo.baseActivity.equals(cmpName)) { // 说明它已经启动了
                    flag = true;
                    break;  //跳出循环，优化效率
                }
            }
        }
        return flag;
    }

    public boolean isConType() {
        return conType;
    }

    public void setConType(boolean conType) {
        this.conType = conType;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public void setOrdersDetailsActivity(Activity ordersDetailsActivity) {
        this.ordersDetailsActivity = ordersDetailsActivity;
    }

    public boolean isLoginNowType() {
        return loginNowType;
    }

    public void setLoginNowType(boolean loginNowType) {
        this.loginNowType = loginNowType;
    }

    public boolean isClientOk() {
        return clientOk;
    }

    public void setClientOk(boolean clientOk) {
        this.clientOk = clientOk;
    }

    public boolean isNoLocation() {
        return noLocation;
    }

    public AddressDB getAddressDB() {
        return addressDB;
    }

    public void setAddressDB(AddressDB addressDB) {
        this.addressDB = addressDB;
    }

    public boolean isLoginType() {
        return loginType;
    }

    public boolean isBackToHome() {
        return backToHome;
    }

    public void setBackToHome(boolean backToHome) {
        this.backToHome = backToHome;
    }

    public void setLoginType(boolean loginType) {
        this.loginType = loginType;
    }

    public void setNoLocation(boolean noLocation) {
        this.noLocation = noLocation;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(this);
        builder.statusBarDrawable = R.mipmap.ic_launcher; //通知图标
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
                | Notification.FLAG_SHOW_LIGHTS; //设置为自动消失和呼吸灯闪烁
        builder.notificationDefaults = Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE
                | Notification.DEFAULT_LIGHTS; // 设置为铃声、震动、呼吸灯闪烁都要
        // 指定定制的 Notification Layout
        //builder.statusBarDrawable = R.drawable.your_notification_icon;
        // 指定最顶层状态栏小图标
        //builder.layoutIconDrawable = R.drawable.your_2_notification_icon;
        // 指定下拉状态栏时显示的通知图标
        JPushInterface.setPushNotificationBuilder(1, builder);
        instance = this;
        oList = new ArrayList<Activity>();
        guideActivitys = new ArrayList<>();
        myInformation = new PMyInformation(this);
        pYourId = new PYourId(this);
        addressDB = new AddressDB(this);
        pBankAccount = new PBankAccount(this);
        pOperationsSettings = new POperationsSettings(this);
    }

    public boolean isReleaseType() {
        return releaseType;
    }

    public void setReleaseType(boolean releaseType) {
        this.releaseType = releaseType;
    }

    /**
     * 添加Activity
     */
    public void addGuideActivity_(Activity activity) {
// 判断当前集合中不存在该Activity
        if (!guideActivitys.contains(activity)) {
            guideActivitys.add(activity);//把当前Activity添加到集合中
        }
    }
    /**
     * 销毁所有的Activity
     */
    public void removeGuideALLActivity_() {
        //通过循环，把集合中的所有Activity销毁
        for (Activity activity : guideActivitys) {
            activity.finish();
        }
    }
    /**
     * 添加Activity
     */
    public void addActivity_(Activity activity) {
// 判断当前集合中不存在该Activity
        if (!oList.contains(activity)) {
            oList.add(activity);//把当前Activity添加到集合中
        }
    }
    /**
     * 销毁所有的Activity
     */
    public void removeALLActivity_() {
        //通过循环，把集合中的所有Activity销毁
        for (Activity activity : oList) {
            activity.finish();
        }
    }

    public PYourId getpYourId() {
        return pYourId;
    }

    public void setpYourId(PYourId pYourId) {
        this.pYourId = pYourId;
    }

    public PBankAccount getpBankAccount() {
        return pBankAccount;
    }

    public void setpBankAccount(PBankAccount pBankAccount) {
        this.pBankAccount = pBankAccount;
    }

    public POperationsSettings getpOperationsSettings() {
        return pOperationsSettings;
    }

    public void setpOperationsSettings(POperationsSettings pOperationsSettings) {
        this.pOperationsSettings = pOperationsSettings;
    }

    public PMyInformation getMyInformation() {
        return myInformation;
    }

    public void setMyInformation(PMyInformation myInformation) {
        this.myInformation = myInformation;
    }

    public boolean isInputType() {
        return inputType;
    }


    public String getLat() {
        if (!TextUtils.isEmpty(Lat)){
            return Lat;
        }else{
            return "0";
        }

    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLong() {
        if (!TextUtils.isEmpty(Long)){
            return Long;
        }else{
            return "0";
        }

    }

    public void setLong(String aLong) {
        Long = aLong;
    }

    public void setInputType(boolean inputType) {
        this.inputType = inputType;
    }

    public int getView127dp() {
        return view127dp;
    }

    public void setView127dp(int view127dp) {
        this.view127dp = view127dp;
    }

    public int getDishesTitle() {
        return dishesTitle;
    }

    public void setDishesTitle(int dishesTitle) {
        this.dishesTitle = dishesTitle;
    }

    public int getDishesItem() {
        return dishesItem;
    }

    public void setDishesItem(int dishesItem) {
        this.dishesItem = dishesItem;
    }

    public int getViewh106dp() {
        return viewh106dp;
    }

    public void setViewh106dp(int viewh106dp) {
        this.viewh106dp = viewh106dp;
    }

    public int getView106dp() {
        return view106dp;
    }

    public void setView106dp(int view106dp) {
        this.view106dp = view106dp;
    }

    public static YumApplication getInstance() {
        return instance;
    }

    public int getNotLogInType() {
        return notLogInType;
    }

    public void setNotLogInType(int notLogInType) {
        this.notLogInType = notLogInType;
    }
}
