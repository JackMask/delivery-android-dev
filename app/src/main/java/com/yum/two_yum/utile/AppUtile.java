package com.yum.two_yum.utile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.BaseReturn;
import com.yum.two_yum.base.ProvinceBean;
import com.yum.two_yum.utile.imgborwser.helper.UTPreImageViewHelper;
import com.yum.two_yum.utile.pickerview.OptionsPickerView;
import com.yum.two_yum.utile.pickerview.TimePickerView;
import com.yum.two_yum.view.client.business.SubmitOrdersActivity;
import com.yum.two_yum.view.dialog.Prompt2Dialog;
import com.yum.two_yum.view.login.LoginActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * @author 余先德
 * @data 2018/4/7
 */

public class AppUtile {

    //private static double EARTH_RADIUS = 6378.137;
    private static double EARTH_RADIUS = 6371.393;
    private static double rad(double d)
    {
        return d * Math.PI / 180.0;
    }

    public static boolean getNetWork(String content){
        if (!TextUtils.isEmpty(content)) {
            if (content.indexOf("Failed to connect to")!=-1){
                //存在包含
                return true;
            }
        }
        return false;
    }

    /**	 * 判断服务是否开启
     *  *
     *  * @return
     *  */
    public static boolean isServiceRunning(Context context) {

        ActivityManager myManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                .getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString()
                    .equals("com.yum.two_yum.controller.TimerService")) {

                return true;
            }
        }
        return false;
    }


    /**	 * 检查网络是否可用
     *  * 	 * @param context
     *  * @return
     *  */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }
        return true;
    }


    /**
     * 字符串转时间戳
     * @param data 日期字符串
     * @param type 日期格式
     * @return
     */
    public static long testString2Date(String data,String type) {
        SimpleDateFormat format = new SimpleDateFormat(type);

        try {
            Date dateTime = format.parse(data);

            return dateTime.getTime() ;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }
     /**

           * 判断一个Activity是否正在运行

           * @param pkg

           * @param cls

           * @param context

           * @return

           */

             public static boolean isClsRunning( String cls, Context context) {

                ActivityManager am =(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);

                List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);

            ActivityManager.RunningTaskInfo task = tasks.get(0);

            if (task != null) {

            return TextUtils.equals(task.topActivity.getPackageName(), "com.yum.two_yum") && TextUtils.equals(task.topActivity.getClassName(), cls);

            }

 return false;

}


    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }
    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return  手机IMEI
     */
    @SuppressLint("MissingPermission")
    public static String getIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        if (tm != null) {
            return tm.getDeviceId();
        }
        return null;
    }


    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /*
    * 将时间戳转换为时间
    */
    public static String stampToDate(Long timestamp,String type){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(type);
        //long lt = new Long(s);
        Date date = new Date(timestamp);
        res = simpleDateFormat.format(date);
        return res;
    }
    /**
     * 计算两个经纬度之间的距离
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public static double GetDistance(double lat1, double lng1, double lat2, double lng2)
    {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
                Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 1000);
        return s;
    }

    public static boolean codeJudgment(BaseActivity activity, String returnStr){
        boolean conType  = false;
        if (!TextUtils.isEmpty(returnStr)) {
            BaseReturn data = JSON.parseObject(returnStr, BaseReturn.class);
            int codeStr = Integer.valueOf(data.getCode());
            if (codeStr == 200){
                conType = true;
            }else if (codeStr == 700){
                activity.showMsg(data.getMsg());
            }else if (codeStr == 403){
                Intent intent = new Intent(activity, LoginActivity.class);
                activity.startActivityForResult(intent,10000);
            }else{
                activity.showMsg(activity.getString(R.string.NETERROR));
            }
        }
        return conType;
    }

    /**
     * 手机是否开启位置服务，如果没有开启那么所有app将不能使用定位功能
     */
    public static boolean isLocServiceEnable(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }
    /**
     * 压缩图片
     * @param filePaht 本地图片地址
     * @return
     */
    public static Bitmap    getSmallBitmap(String filePaht){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePaht,options);
        options.inSampleSize = calculateInSampleSize(options,480,800);
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePaht, options);
    }
    /**
     * 获取压缩比例
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight){
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight||width>reqWidth) {
            final int heightRatio = Math.round((float) height/ (float) reqHeight);
            final int widthRatio = Math.round((float) width/ (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
    /**
     * 将数据保留一位小数
     */
    public static double getOneDecimal(double num) {
        DecimalFormat dFormat=new DecimalFormat("#.0");

        String yearString=dFormat.format(num);
        Double temp= Double.valueOf(yearString);
        return temp;
    }
    /**
    /**
     * 将数据保留两位小数
     */
    public static double getTwoDecimal(double num) {
        DecimalFormat dFormat=new DecimalFormat("#.00");

        String yearString=dFormat.format(num);
        Double temp= Double.valueOf(yearString);
        return temp;
    }
    /**
     * double 相加
     * @param d1
     * @param d2
     * @return
     */
    public static double sum(double d1,double d2){
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.add(bd2).doubleValue();
    }
    /**
     * 提供精确的减法运算
     * @param v1
     *            被減数
     * @param v2
     *            減数
     * @return两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }
    /**
     * double 乘法
     * @param d1
     * @param d2
     * @return
     */
    public static double mul(double d1,double d2){
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.multiply(bd2).doubleValue();
    }

    public static boolean setCode(String code,BaseActivity baseActivity){
        if (!TextUtils.isEmpty(code)) {
            try {
                BaseReturn data = JSON.parseObject(code, BaseReturn.class);
                if (data.getCode().equals("200")) {
                    return true;
                } else if (data.getCode().equals("700")) {
                    baseActivity.showMsg(data.getMsg());
                    return false;
                }else if (data.getCode().equals("403")){

                    return false;
                }else if (data.getCode().equals("701")) {
                    //登录
                    Intent intent1 = new Intent(baseActivity,Prompt2Dialog.class);
                    intent1.putExtra("title",true);
                    intent1.putExtra("content",data.getMsg());
                    baseActivity.startActivity(intent1);
                    return false;
                } else {
                    // baseActivity.showMsg(baseActivity.getString(R.string.NETERROR));
                    return false;
                }
            }catch (JSONException e){
                baseActivity.showMsg(baseActivity.getString(R.string.NETERROR));
                return false;
            }

        }
        return false;
    }
    public static boolean setCodeOrder(String code,BaseActivity baseActivity){
        if (!TextUtils.isEmpty(code)) {
            try {
                BaseReturn data = JSON.parseObject(code, BaseReturn.class);
                if (data.getCode().equals("200")) {
                    return true;
                } else if (data.getCode().equals("700")) {
                    baseActivity.showMsg(data.getMsg());
                    return false;
                }else if (data.getCode().equals("403")||data.getCode().equals("701")){
                    Intent intent1 = new Intent(baseActivity,Prompt2Dialog.class);
                    intent1.putExtra("title",true);
                    intent1.putExtra("content",data.getMsg());
                    baseActivity.startActivity(intent1);
                    return false;
                } else {
                     baseActivity.showMsg(baseActivity.getString(R.string.NETERROR));
                    return false;
                }
            }catch (JSONException e){
                baseActivity.showMsg(baseActivity.getString(R.string.NETERROR));
                return false;
            }

        }
        return false;
    }
    public static String getPrice(String price){
        try {
            double priceDouble =getTwoDecimal(Double.valueOf(price));
            String priceStr = priceDouble+"";
            String[] prices = priceStr.split("\\.");
            if (prices.length==2){
                if (prices[1].length()<2){
                    return priceStr+"0";
                }else{
                    return priceStr;
                }
            }else{
                return priceStr+".00";
            }
        }catch (Exception e){
            return "0.00";
        }

    }

    /**
     *
     * @param price
     * @return
     */
    public static String getDistance(String price){
        String[] prices = price.split("\\.");
        if (prices.length==2){
            return price;
        }else{
            return price+".0";
        }
    }
    /**
     * 一级连动
     * 条件选择器初始化
     */
    @SuppressWarnings("unchecked")
    public static OptionsPickerView initOptionsPicker(Context context, OptionsPickerView pvOptions, final ArrayList<ProvinceBean> options1Items, String title, final GetPickerStrCallBack callBack) {
        pvOptions = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText();
                if (callBack!=null){
                    callBack.getStr(tx,options1Items.get(options1).getPickerViewId());
                }
            }
        })

                .setSubmitText("确认")
                .setCancelText("取消")
                .setTitleText(!TextUtils.isEmpty(title)?title:"")
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
        pvOptions.setPicker(options1Items);//一级选择器*/
        //pvOptions.setPicker(options1Items, options2Items);//二级选择器
        //pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器*/
        return pvOptions;
    }
    /**
     * 时间选择器
     * @param pvTime
     * @param title
     * @return
     */
    public static TimePickerView initBirthdayPicker(Calendar date,Context context, TimePickerView pvTime, final String title, final GetTimeStrCallBack callBack) {

        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        int year = selectedDate.get(Calendar.YEAR);
        int month = selectedDate.get(Calendar.MONTH);
        int day = selectedDate.get(Calendar.DAY_OF_MONTH);
        boolean con = false;
        if ( AppUtile.getLanguage().equals("zh-HK")||AppUtile.getLanguage().equals("zh-CN")){

            con = false;
        }else{
            con = true;
        }

        selectedDate.set(year-100,month,day,0,0,0);
        Calendar endDate = Calendar.getInstance();
        endDate.set(year,month,day,23,59,59);
        Calendar now ;
        if (date==null){
            now = Calendar.getInstance();
        }else{
            now = date;
        }
        //时间选择器
        pvTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                if (callBack!=null){
                    callBack.getStr(date,v);
                }
                /*btn_Time.setText(getTime(date));*/
                // Button btn = (Button) v;
                // btn.setText(getTime(date));



            }
        },null)
                .setSubmitText(context.getString(R.string.DONE))
                // .setCancelText(context.getString(R.string.CANCEL))
                .setTitleBgColor(Color.WHITE)
                .setTitleColor(Color.parseColor("#2177d5"))
                //.setTitleText(title)
                .setSubmitColor(Color.parseColor("#999999"))
                .setCancelColor(Color.parseColor("#999999"))
                .setType(TimePickerView.Type.YEAR_MONTH_DAY)
                .setOutSideCancelable(true)//点击外部dismiss default true
                .setLabel(context.getResources().getString(R.string.YEAR), context.getResources().getString(R.string.MONTH), context.getResources().getString(R.string.DAY), "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.parseColor("#e7e7e7"))
                .setContentSize(18)
                .setDate(now)
                .setMonthDayYear(con)
                .setRangDate(selectedDate,endDate)
                .build();
        return pvTime;
    }
    /**
     * 时间选择器
     * @param pvTime
     * @param title
     * @return
     */
    public static TimePickerView initTimePicker(Context context, TimePickerView pvTime, final String title, final GetTimeStrCallBack callBack) {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        int year = selectedDate.get(Calendar.YEAR);
        int month = selectedDate.get(Calendar.MONTH);
        int day = selectedDate.get(Calendar.DAY_OF_MONTH);
        boolean con = false;
        if ( AppUtile.getLanguage().equals("zh-HK")||AppUtile.getLanguage().equals("zh-CN")){

            con = false;
        }else{
            con = true;
        }

        selectedDate.set(year-100,month,day,0,0,0);
        Calendar endDate = Calendar.getInstance();
        endDate.set(year,month,day,23,59,59);

        //时间选择器
        pvTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                if (callBack!=null){
                    callBack.getStr(date,v);
                }
                /*btn_Time.setText(getTime(date));*/
                // Button btn = (Button) v;
                // btn.setText(getTime(date));



            }
        },null)
                .setSubmitText(context.getString(R.string.DONE))
               // .setCancelText(context.getString(R.string.CANCEL))
                .setTitleBgColor(Color.WHITE)
                .setTitleColor(Color.parseColor("#2177d5"))
                //.setTitleText(title)
                .setSubmitColor(Color.parseColor("#999999"))
                .setCancelColor(Color.parseColor("#999999"))
                .setType(TimePickerView.Type.YEAR_MONTH_DAY)
                .setOutSideCancelable(true)//点击外部dismiss default true
                .setLabel(context.getResources().getString(R.string.YEAR), context.getResources().getString(R.string.MONTH), context.getResources().getString(R.string.DAY), "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.parseColor("#e7e7e7"))
                .setContentSize(18)
                .setDate(Calendar.getInstance())
                .setMonthDayYear(con)
                .setRangDate(selectedDate,endDate)
                .build();
        return pvTime;
    }

    /**
     * 两个Double数相除，并保留scale位小数
     * @param v1
     * @param v2
     * @param scale
     * @return Double
     */
    public static Double div(Double v1,Double v2,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 时间格式化
     * @param date
     * @return
     */
    public static String getTime(Date date,String str) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat(str);
        return format.format(date);
    }
    /** 获取当前手机的系统语言
     * @return
     */
    public static String getLanguage(){
        String language = Locale.getDefault().getLanguage();
        String country = Locale.getDefault().getCountry();
        if (language.equals("zh")||language.equals("zh-cn")){
            if (country.equals("TW")||country.equals("HK")){
                return "zh-HK";
            }else {
                return "zh-CN";
            }
        }else if (language.equals("es")){
            return "es-ES";
        }else{
            return "en-US";
        }

    }

    /**
     * 四舍五入保留2位小数
     * @param d
     * @return
     */
    public static double formatDouble1(double d) {
        return (double)Math.round(d*100)/100;
    }
    /** 获取当前手机的系统语言
     * @return
     */
    public static int getLanguageInt(){
        String language = Locale.getDefault().getLanguage();
        String country = Locale.getDefault().getCountry();
        if (language.equals("zh")||language.equals("zh-cn")){
            if (country.equals("TW")||country.equals("HK")){
                return 2;
            }else {
                return 1;
            }
        }else if (language.equals("es")){
            return 4;
        }else{
            return 3;
        }

    }
    /** 获取当前手机的系统语言 用于获取地址
     * @return
     */
    public static String getLanguageIntSetAddress(){
        String language = Locale.getDefault().getLanguage();
        String country = Locale.getDefault().getCountry();
        if (language.equals("zh")||language.equals("zh-cn")){
            if (country.equals("TW")||country.equals("HK")){
                return "zh-tw";
            }else {
                return "zh-cn";
            }
        }else if (language.equals("es")){
            return "es-es";
        }else{
            return "en-us";
        }

    }
    /**
     * 返回系统时间的时间戳
     * @return
     */
    public static String getTime(){

        long time=System.currentTimeMillis();//获取系统时间的10位的时间戳

        String  str=String.valueOf(time);

        return str;

    }

    public static void headClick(Context context,ImageView imageView,String url){
        ImageView mCommentPic = imageView;
        UTPreImageViewHelper helper1 = new UTPreImageViewHelper((Activity) context);
        helper1.setIndicatorStyle(2);
        helper1.setSaveTextMargin(0, 0, 0, 5000);
        helper1.addImageView(mCommentPic, url);
        helper1.startPreActivity(0);
    }

    /**
     * 读取文件
     * @param inputStream
     * @return
     */
    public static String getString(InputStream inputStream) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "Unicode");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
