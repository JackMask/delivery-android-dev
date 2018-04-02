package com.guxingdongli.yizhangguan.model;

/**
 * Created by jackmask on 2018/3/13.
 */

public class LoginBase extends ReturnBase {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    /**
     * user : {"uid":"ae094bb8-d813-4d77-8ac5-a9e1732ddd14","headImg":"http://www.yizhangguan.cn/HeadImg/a1e4bd0b-ec58-46f0-9a3e-c29ff36eda07.png","name":"河南省百合医疗器械销售有限公司","realName":"1**2","loginName":"9001","email":"","mobilePhone":"151*******87","userType":2,"isAdmin":false,"isEngineer":true,"departmentName":null}
     * tokenData : {"access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiJhZTA5NGJiOC1kODEzLTRkNzctOGFjNS1hOWUxNzMyZGRkMTQiLCJuYmYiOjE1MjA5NDgwMDgsImV4cCI6MTUyMTU1MjgwOCwiaXNzIjoiY3JlYWN0aW9uIiwiYXVkIjoiYXBwIn0.gEloXLYjYvInq-Gt6LNl0ztmtjS0KlXkmtLrFAOiIfU","expires_in":6.048E8,"expires":"2018-03-20 13:33:28","token_type":"Bearer"}
     */
    public static class DataBean {
        private UserBean user;
        private TokenDataBean tokenData;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public TokenDataBean getTokenData() {
            return tokenData;
        }

        public void setTokenData(TokenDataBean tokenData) {
            this.tokenData = tokenData;
        }

        public static class UserBean {
            /**
             * uid : ae094bb8-d813-4d77-8ac5-a9e1732ddd14
             * headImg : http://www.yizhangguan.cn/HeadImg/a1e4bd0b-ec58-46f0-9a3e-c29ff36eda07.png
             * name : 河南省百合医疗器械销售有限公司
             * realName : 1**2
             * loginName : 9001
             * email :
             * mobilePhone : 151*******87
             * userType : 2
             * isAdmin : false
             * isEngineer : true
             * departmentName : null
             */

            private String uid;
            private String headImg;
            private String name;
            private String realName;
            private String loginName;
            private String email;
            private String mobilePhone;
            private int userType;
            private boolean isAdmin;
            private boolean isEngineer;
            private String departmentName;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getHeadImg() {
                return headImg;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getLoginName() {
                return loginName;
            }

            public void setLoginName(String loginName) {
                this.loginName = loginName;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getMobilePhone() {
                return mobilePhone;
            }

            public void setMobilePhone(String mobilePhone) {
                this.mobilePhone = mobilePhone;
            }

            public int getUserType() {
                return userType;
            }

            public void setUserType(int userType) {
                this.userType = userType;
            }

            public boolean isIsAdmin() {
                return isAdmin;
            }

            public void setIsAdmin(boolean isAdmin) {
                this.isAdmin = isAdmin;
            }

            public boolean isIsEngineer() {
                return isEngineer;
            }

            public void setIsEngineer(boolean isEngineer) {
                this.isEngineer = isEngineer;
            }

            public String getDepartmentName() {
                return departmentName;
            }

            public void setDepartmentName(String departmentName) {
                this.departmentName = departmentName;
            }
        }

        public static class TokenDataBean {
            /**
             * access_token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiJhZTA5NGJiOC1kODEzLTRkNzctOGFjNS1hOWUxNzMyZGRkMTQiLCJuYmYiOjE1MjA5NDgwMDgsImV4cCI6MTUyMTU1MjgwOCwiaXNzIjoiY3JlYWN0aW9uIiwiYXVkIjoiYXBwIn0.gEloXLYjYvInq-Gt6LNl0ztmtjS0KlXkmtLrFAOiIfU
             * expires_in : 6.048E8
             * expires : 2018-03-20 13:33:28
             * token_type : Bearer
             */

            private String access_token;
            private double expires_in;
            private String expires;
            private String token_type;

            public String getAccess_token() {
                return access_token;
            }

            public void setAccess_token(String access_token) {
                this.access_token = access_token;
            }

            public double getExpires_in() {
                return expires_in;
            }

            public void setExpires_in(double expires_in) {
                this.expires_in = expires_in;
            }

            public String getExpires() {
                return expires;
            }

            public void setExpires(String expires) {
                this.expires = expires;
            }

            public String getToken_type() {
                return token_type;
            }

            public void setToken_type(String token_type) {
                this.token_type = token_type;
            }
        }
    }
}
