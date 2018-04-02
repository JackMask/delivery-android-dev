package com.guxingdongli.yizhangguan.model;

/**
 * @author 余先德
 * @data 2018/3/27
 */

public class AddStatementDataBase {

    /**
     * success : true
     * tipCode : 0
     * msg : 操作成功！
     * other :
     * data : 2018-03-25 00:00:00
     */

    private boolean success;
    private String tipCode;
    private String msg;
    private String other;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
