package com.yuxiaolong.yuxiandelibrary;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jackmask on 2018/2/27.
 */

public class YuXianDeUtile {
    /**
     * 获取权限5.0以上用
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
     * 网络图片转换成数组并压缩，适用于微信分享
     * @param url 图片地址
     * @return
     */
    public static byte[] getBitmap(String url) {
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            int length = http.getContentLength();

            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (bm!=null){
            return Bitmap3Bytes(bm);
        }else{
            return null;
        }
    }
    public static byte[] Bitmap3Bytes(Bitmap bmp) {
        int i;
        int j;
        if (bmp.getHeight() > bmp.getWidth()) {
            i = bmp.getWidth();
            j = bmp.getWidth();
        } else {
            i = bmp.getHeight();
            j = bmp.getHeight();
        }

        Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.RGB_565);
        Canvas localCanvas = new Canvas(localBitmap);

        while (true) {
            localCanvas.drawBitmap(bmp, new Rect(0, 0, i, j), new Rect(0, 0,i, j), null);
            bmp.recycle();
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 30,
                    localByteArrayOutputStream);
            localBitmap.recycle();
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            try {
                localByteArrayOutputStream.close();
                return arrayOfByte;
            } catch (Exception e) {
                //F.out(e);
            }
            i = bmp.getHeight();
            j = bmp.getHeight();
        }
              /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 30, baos);
                return baos.toByteArray();*/
    }
    /**
     * 生成一个不带logo的二维码
     * @param content
     * @param width
     * @param height
     * @return
     */
    public static Bitmap generateBitmap(String content,int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成一个带logo的二维码
     * @param logoBitmap
     * @return
     */
    public static Bitmap generateBitmapLogo(String content,int width, int height, Bitmap logoBitmap) {
        if (content!=null&&width>0&&height>0) {
            Bitmap qrBitmap = generateBitmap(content,width,height);
            int qrBitmapWidth = qrBitmap.getWidth();
            int qrBitmapHeight = qrBitmap.getHeight();
            int logoBitmapWidth = logoBitmap.getWidth();
            int logoBitmapHeight = logoBitmap.getHeight();
            Bitmap blankBitmap = Bitmap.createBitmap(qrBitmapWidth, qrBitmapHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(blankBitmap);
            canvas.drawBitmap(qrBitmap, 0, 0, null);
            canvas.save(Canvas.ALL_SAVE_FLAG);
            float scaleSize = 1.0f;
            while ((logoBitmapWidth / scaleSize) > (qrBitmapWidth / 5) || (logoBitmapHeight / scaleSize) > (qrBitmapHeight / 5)) {
                scaleSize *= 2;
            }
            float sx = 1.0f / scaleSize;
            canvas.scale(sx, sx, qrBitmapWidth / 2, qrBitmapHeight / 2);
            canvas.drawBitmap(logoBitmap, (qrBitmapWidth - logoBitmapWidth) / 2, (qrBitmapHeight - logoBitmapHeight) / 2, null);
            canvas.restore();
            return blankBitmap;
        }else{
            return null;
        }


    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 时间转字符串
     * @param date 时间
     * @param timeFormat 时间格式
     * @return
     */
    public static String getTime(Date date,String timeFormat) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat(timeFormat);
        return format.format(date);
    }
}
