package com.guxingdongli.yizhangguan.model;

/**
 * Created by jackmask on 2018/3/5.
 */

public class MyRepairOrderDetailsContentBasicInfoBase {
    private String name;
    private String content;

    public MyRepairOrderDetailsContentBasicInfoBase(String name,String content){
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
