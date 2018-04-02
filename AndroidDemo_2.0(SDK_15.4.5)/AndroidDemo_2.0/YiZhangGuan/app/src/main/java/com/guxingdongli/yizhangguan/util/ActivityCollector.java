package com.guxingdongli.yizhangguan.util;

import com.yuxiaolong.yuxiandelibrary.YuXianDeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/24
 */

public class ActivityCollector {


    public static List<YuXianDeActivity> activities = new ArrayList<YuXianDeActivity>();

    public static void addActivity(YuXianDeActivity activity) {
        activities.add(activity);
    }

    public static void removeActivity(YuXianDeActivity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (YuXianDeActivity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
