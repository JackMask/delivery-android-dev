package com.guxingdongli.yizhangguan.util;

import android.os.Bundle;
import android.view.MotionEvent;
import com.bugtags.library.Bugtags;

import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;

/**
 * @author 余先德
 * @data 2018/3/24
 */

public class YiZhangGuanActivity extends YuXianDeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }
    public void setNum(int position,String num){

    }
    @Override
    protected void onResume() {
        super.onResume();
        //注：回调 1
        Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //注：回调 2
        Bugtags.onPause(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //注：回调 3
        Bugtags.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
