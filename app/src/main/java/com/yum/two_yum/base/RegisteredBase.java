package com.yum.two_yum.base;

/**
 * @author 余先德
 * @data 2018/4/17
 */

public class RegisteredBase {

    /**
     * message : 邮箱格式错误
     * data : null
     * status : 0
     * error : null
     */

    private String message;
    private String data;
    private int status;
    private String error;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
