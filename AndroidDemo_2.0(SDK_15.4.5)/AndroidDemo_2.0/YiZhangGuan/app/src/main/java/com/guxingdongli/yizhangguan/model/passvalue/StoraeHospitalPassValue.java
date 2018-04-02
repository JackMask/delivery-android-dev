package com.guxingdongli.yizhangguan.model.passvalue;

/**
 * 订单验收
 * @author 余先德
 * @data 2018/3/17
 */

public class StoraeHospitalPassValue {
    private String orderGuid;
    private String guid;
    private int acceptanceQuantity;

    @Override
    public String toString() {
        return "{" +
                "\"orderGuid\":\"" + orderGuid + '\"' +
                ", \"guid\":\"" + guid + '\"' +
                ", \"acceptanceQuantity\":" + acceptanceQuantity +
                '}';
    }

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
