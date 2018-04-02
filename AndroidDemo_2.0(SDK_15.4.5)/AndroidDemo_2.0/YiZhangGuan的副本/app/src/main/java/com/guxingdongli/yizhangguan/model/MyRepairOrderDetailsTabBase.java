package com.guxingdongli.yizhangguan.model;

/**
 * Created by jackmask on 2018/3/5.
 */

public class MyRepairOrderDetailsTabBase {

    private String name;
    private boolean selectTab;

    public MyRepairOrderDetailsTabBase(String name,boolean selectTab){
        this.name = name;
        this.selectTab = selectTab;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelectTab() {
        return selectTab;
    }

    public void setSelectTab(boolean selectTab) {
        this.selectTab = selectTab;
    }
}
