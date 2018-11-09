package com.yum.two_yum.base;

/**
 * @author 余先德
 * @data 2018/4/19
 */

public class UploadFlieBase {

    /**
     * status : 1
     * message :
     * data : http://personal-upload.s3.amazonaws.com/2018-04-19/zh_cn2018011908560222.png?AWSAccessKeyId=AKIAISJERYZGVWD66R4A&Expires=1609372800&Signature=aEMLXBYM%2Fyj19dhBqb0JEpgFQKk%3D
     * error : null
     */

    private String status;
    private String message;
    private String data;
    private String error;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
