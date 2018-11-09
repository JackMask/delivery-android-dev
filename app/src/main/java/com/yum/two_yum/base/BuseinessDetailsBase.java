package com.yum.two_yum.base;

/**
 * @author 余先德
 * @data 2018/4/10
 */

public class BuseinessDetailsBase {
    private boolean type;
    private String price;
    private int typeCon;

    public int getTypeCon() {
        return typeCon;
    }

    public void setTypeCon(int typeCon) {
        this.typeCon = typeCon;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
