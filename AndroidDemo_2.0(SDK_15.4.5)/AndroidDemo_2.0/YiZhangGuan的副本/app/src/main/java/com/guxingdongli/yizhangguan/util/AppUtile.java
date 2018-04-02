package com.guxingdongli.yizhangguan.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.util.AbToastUtil;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.model.ProvinceBean;
import com.yuxiaolong.yuxiandelibrary.pickerview.OptionsPickerView;
import com.yuxiaolong.yuxiandelibrary.pickerview.TimePickerView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import Decoder.BASE64Encoder;

/**
 * Created by jackmask on 2018/3/1.
 */

public class AppUtile {

    //图片转化成base64字符串
    public static String GetImageStr(String path)
    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String imgFile = path;//待处理的图片
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try
        {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }


    /**
     * 读取assets下的txt文件，返回utf-8 String
     * @param context
     * @return
     */
    public static String readAssetsCityTxt(Context context){
        try {
            //Return an AssetManager instance for your application's package
            InputStream is = context.getAssets().open("City");
            int size = is.available();
            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert the buffer into a string.
            String text = new String(buffer, "utf-8");
            // Finally stick the string into the text view.
            return text;
        } catch (IOException e) {
            // Should never happen!
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
        return "读取错误，请检查文件名";
    }

    /**
     * 控制标题头箭头
     * @param con  true 点击左边 false 点击右边
     * @param left 左边的 view
     */
    public static boolean setTitleFilter(boolean con, ImageView left){
        boolean type = false;
        if (con){
            left.setImageResource(R.mipmap.arrow_up);
            type = true;
        }else{
            left.setImageResource(R.mipmap.arrow_down);
            type = false;
        }

        return type;
    }

    /**
     * 空值校验
     * @param context
     * @param checkStr 校验的字符串
     * @param promptStr 提示
     * @param con 是否提示
     * @return
     */
    public static boolean checkNull(Context context, String checkStr,String promptStr,boolean con){
        if (!TextUtils.isEmpty(checkStr)){
            return true;
        }else{
            if (con)
            AbToastUtil.showToast(context,promptStr+"不能为空");
        }
        return false;
    }
    /**
     * 密码校验
     * @param context
     * @param password 密码
     * @return
     */
    public static boolean checkPassword(Context context, String password){
        if (checkNull(context,password,"密码",true))
            if (password.length()>5){
                return true;
            }else{
                AbToastUtil.showToast(context,"密码长度不符合规范，密码长度最少6位");
            }

        return false;
    }

    /**
     * 再次输入密码校验
     * @param context
     * @param password 密码
     * @param againPassword 再次密码
     * @return
     */
    public static boolean checkAgainPassword(Context context,String againPassword, String password){
        if (checkNull(context,password,"密码",true))
            if (checkNull(context,againPassword,"再次输入的密码",true))
                if (againPassword.length()>5) {
                    if (password.length() > 5) {
                        if (password.equals(againPassword)){
                            return true;
                        }else{
                            AbToastUtil.showToast(context, "两次输入的密码不相同");
                        }
                    } else {
                        AbToastUtil.showToast(context, "密码长度不符合规范，密码长度最少6位");
                    }
                }else{
                    AbToastUtil.showToast(context, "再次输入的密码长度不符合规范，密码长度最少6位");
                }
        return false;
    }

    /**
     * 手机号校验
     * @param context
     * @param phone 手机号
     * @return
     */
    public static boolean checkPhone(Context context, String phone){
        if (checkNull(context,phone,"手机号",true))
            if (phone.length()==11){
                return true;
            }else{
                AbToastUtil.showToast(context,"手机号长度不符合规范");
            }
        return false;
    }
    /**
     * 定时器类
     */
    public static class TimeCount extends CountDownTimer {

        TextView textBtn;

        public TimeCount(long millisInFuture, long countDownInterval,TextView textBtn) {
            super(millisInFuture, countDownInterval);
            this.textBtn = textBtn;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            textBtn.setClickable(false);
            textBtn.setText(millisUntilFinished / 1000 + "秒后重发");
            //verificationBtn.setTextColor(Color.parseColor("#999999"));
            //verificationBtn.setBackgroundResource(R.drawable.area_stroke_white_gred_btn);
        }


        @Override
        public void onFinish() {
            textBtn.setClickable(true);
            textBtn.setText("重新获取");
            // verificationBtn.setClickable(true);
            // verificationBtn.setTextColor(Color.parseColor("#2177d5"));
            // verificationBtn.setBackgroundResource(R.drawable.area_stroke_white_btn);

        }
    }
    /**
     * 获取十六进制的颜色代码.例如  "#6E36B4" , For HTML ,
     * @return String
     */
    public static String getRandColorCode(){
        String r,g,b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();

        r = r.length()==1 ? "0" + r : r ;
        g = g.length()==1 ? "0" + g : g ;
        b = b.length()==1 ? "0" + b : b ;

        return "#"+r+g+b;
    }

    /**
     * 一级连动
     * 条件选择器初始化
     */
    public static OptionsPickerView initOptionsPicker(Context context,OptionsPickerView pvOptions, final ArrayList<ProvinceBean> options1Items,String title, final GetPickerStrCallBack callBack) {
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
     * 三级连动
     * 条件选择器初始化
     */
    public static OptionsPickerView initOptionsPicker(Context context,OptionsPickerView pvOptions, final ArrayList<ProvinceBean> options1Items,
                                                      final ArrayList<ArrayList<String>> options2Items,final ArrayList<ArrayList<ArrayList<String>>> options3Items ,
                                                      String title, final GetPickerStrCallBack callBack) {
        pvOptions = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String province = options1Items.get(options1).getPickerViewText();
                String city = options2Items.get(options1).get(options2);
                String area = options3Items.get(options1).get(options2).get(options3);
                String tx;
                if (province.equals(city)){
                    tx = province+" "+area;
                }else{
                    tx = province+" "+ city+" "+ area;
                }

                if (callBack!=null){
                    callBack.getStr(tx,0);
                }
            }
        })
                .setTitleText(!TextUtils.isEmpty(title)?title:"")
                .setSubmitText("确认")
                .setCancelText("取消")
                .setTitleBgColor(Color.WHITE)
                .setTitleColor(Color.parseColor("#2177d5"))
                .setTitleText("")
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
        //pvOptions.setPicker(options1Items);//一级选择器*/
        //pvOptions.setPicker(options1Items, options2Items);//二级选择器
        pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器*/
        return pvOptions;
    }

    /**
     * 时间选择器
     * @param pvTime
     * @param title
     * @return
     */
    public static TimePickerView initTimePicker(Context context, TimePickerView pvTime,final String title,final GetTimeStrCallBack callBack) {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        int year = selectedDate.get(Calendar.YEAR);
        int month = selectedDate.get(Calendar.MONTH);
        int day = selectedDate.get(Calendar.DAY_OF_MONTH);
        selectedDate.set(year,month-1,day,selectedDate.get(Calendar.HOUR),selectedDate.get(Calendar.MINUTE));
        Calendar endDate = Calendar.getInstance();
        endDate.set(year+100,month+1,day,0,0);
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
                .setSubmitText("确认")
                .setCancelText("取消")
                .setTitleBgColor(Color.WHITE)
                .setTitleColor(Color.parseColor("#2177d5"))
                .setTitleText(title)
                .setSubmitColor(Color.parseColor("#999999"))
                .setCancelColor(Color.parseColor("#999999"))
                .setType(TimePickerView.Type.YEAR_MONTH_DAY_HOUR_MIN)
                .setOutSideCancelable(true)//点击外部dismiss default true
                .setLabel("年", "月", "日", "时", "分", "") //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.parseColor("#e7e7e7"))
                .setContentSize(18)
                .setDate(Calendar.getInstance())

                .setRangDate(selectedDate,endDate)
                .build();
        return pvTime;
    }

    /**
     * 时间格式化
      * @param date
     * @return
     */
    public static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
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

    /**
     * 获取权限
     * @param abActivity
     * @param permission
     * @param returnValue
     * @return
     */
    public static boolean dynamicAuthority(Activity abActivity, String permission, int returnValue){
        if (ContextCompat.checkSelfPermission(abActivity, permission)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(abActivity,
                    new String[]{permission}, returnValue);
            return false;
        }else{
            return true;
        }
    }

    /**
     * 压缩图片
     * @param filePaht 本地图片地址
     * @return
     */
    public static Bitmap getSmallBitmap(String filePaht){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePaht,options);
        options.inSampleSize = calculateInSampleSize(options,480,800);
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePaht, options);
    }

    /**
     * 把压缩的图片存到本地
     * @param bmp
     * @param file
     */
    public static void compressBmpToFile(Bitmap bmp,File file){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 80;//个人喜欢从80开始,
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        while (baos.toByteArray().length / 1024 > 100) {
            baos.reset();
            options -= 10;
            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
     * convert px to its equivalent dp
     *
     * 将px转换为与之相等的dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale =  context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * convert dp to its equivalent px
     *
     * 将dp转换为与之相等的px
     */
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    /**
     * convert px to its equivalent sp
     *
     * 将px转换为sp
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    /**
     * convert sp to its equivalent px
     *
     * 将sp转换为px
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
