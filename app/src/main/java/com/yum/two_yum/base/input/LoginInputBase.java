package com.yum.two_yum.base.input;

/**
 * @author 余先德
 * @data 2018/4/17
 */

public class LoginInputBase {

    /**
     * email : 2357281@qq.com
     * password : 123456
     * typeCode : en
     */

    private String email;
    private String password;
    private String typeCode;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
}
