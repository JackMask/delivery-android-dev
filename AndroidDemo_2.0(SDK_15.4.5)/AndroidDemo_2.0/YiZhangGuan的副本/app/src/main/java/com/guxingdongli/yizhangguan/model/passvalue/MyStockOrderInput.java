package com.guxingdongli.yizhangguan.model.passvalue;

/**
 * @author 余先德
 * @data 2018/3/25
 */

public class MyStockOrderInput {
    /**
     * orderGuid : string
     * guid : string
     * batchNumber : string
     * valid : string
     * serialNum : string
     * productNum : string
     */

    private String orderGuid;
    private String guid;
    private String batchNumber;
    private String valid;
    private String serialNum;
    private String productNum;

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

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }
}
