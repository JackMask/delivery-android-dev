package com.guxingdongli.yizhangguan.model;

/**
 * @author 余先德
 * @data 2018/3/27
 */

public class RepairManagementScanCodeBase {


    /**
     * success : true
     * tipCode : 0
     * msg : 扫码报修
     * other : 扫码报修
     * data : null
     */

    private boolean success;
    private String tipCode;
    private String msg;
    private String other;
    private Object data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getTipCode() {
        return tipCode;
    }

    public void setTipCode(String tipCode) {
        this.tipCode = tipCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
