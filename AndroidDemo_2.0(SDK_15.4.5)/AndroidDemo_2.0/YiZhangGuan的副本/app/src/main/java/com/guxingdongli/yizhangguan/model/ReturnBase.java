package com.guxingdongli.yizhangguan.model;

/**
 * Created by jackmask on 2018/3/13.
 */

public class ReturnBase {

    /**
     * success : false
     * tipCode : 1
     * msg : 用户名未取得授权，不能登录
     * other : 未通过参数验证，当前错误消息内容是：Name:用户名未取得授权，不能登录
     * data : null
     */

    private boolean success;
    private String tipCode;
    private String msg;
    private String other;

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


}
