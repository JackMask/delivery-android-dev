package com.yum.two_yum.base;

// FIXME generate failure  field _$Data233
/**
 * @author 余先德
 * @data 2018/4/16
 */

public class LoginBase extends BaseReturn {


    /**
     * data : {"avatar":"string","bornTime":"2018-05-29T01:21:44.491Z","currentType":0,"email":"string","gender":0,"id":0,"token":"string","type":0,"username":"string"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * avatar : string
         * bornTime : 2018-05-29T01:21:44.491Z
         * currentType : 0
         * email : string
         * gender : 0
         * id : 0
         * token : string
         * type : 0
         * username : string
         */

        private String avatar;
        private String bornTime;
        private String currentType;
        private String email;
        private String gender;
        private String id;
        private String token;
        private String type;
        private String username;
        private int merchantType;

        public int getMerchantType() {
            return merchantType;
        }

        public void setMerchantType(int merchantType) {
            this.merchantType = merchantType;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getBornTime() {
            return bornTime;
        }

        public void setBornTime(String bornTime) {
            this.bornTime = bornTime;
        }

        public String getCurrentType() {
            return currentType;
        }

        public void setCurrentType(String currentType) {
            this.currentType = currentType;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
