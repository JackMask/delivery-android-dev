package com.guxingdongli.yizhangguan.model.passvalue;

/**
 * @author 余先德
 * @data 2018/3/25
 */

public class StoraeHopitalDetailsValue {
    private String orderGuid;
    private String guid;
    private int acceptanceQuantity;

    public String getOrderGuid() {
        return orderGuid;
    }

    public void setOrderGuid(String orderGuid) {
        this.orderGuid = orderGuid;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public int getAcceptanceQuantity() {
        return acceptanceQuantity;
    }

    public void setAcceptanceQuantity(int acceptanceQuantity) {
        this.acceptanceQuantity = acceptanceQuantity;
    }
}
