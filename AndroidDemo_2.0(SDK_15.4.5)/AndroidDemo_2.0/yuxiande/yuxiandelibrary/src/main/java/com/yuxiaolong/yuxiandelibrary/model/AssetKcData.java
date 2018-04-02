package com.yuxiaolong.yuxiandelibrary.model;

/**
 * Created by jackmask on 2018/3/1.
 */

public class AssetKcData {
    private String desc;
    private int color;
    private int num;
    private float sNum;

    public AssetKcData(String desc,int color,int num,float sNum){

    }

    public float getSNum() {
        return sNum;
    }

    public void setSNum(float sNum) {
        this.sNum = sNum;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
