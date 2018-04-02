package com.yuxiaolong.yuxiandelibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Administrator on 2016/7/3 0003.
 */

public class ProhibitSlideGridView extends GridView {
    public ProhibitSlideGridView(Context context) {
        super(context);
    }

    public ProhibitSlideGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProhibitSlideGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
