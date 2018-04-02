package com.guxingdongli.yizhangguan.model;

import java.util.List;

/**
 * 故障等级
 * @author 余先德
 * @data 2018/3/19
 */

public class ServiceLevelBase {

    /**
     * success : true
     * tipCode : 0
     * msg : 操作成功！
     * other :
     * data : [{"id":9,"gid":"b0cffd68-6c43-4075-9c0c-728b0d0e13a4","dicName":"维修级别","dicValue":"未设置"},{"id":10,"gid":"8555208f-9317-4f38-80f6-023e4695e893","dicName":"维修级别","dicValue":"大修"},{"id":11,"gid":"adf2d957-4d89-4338-b827-4bc358eba955","dicName":"维修级别","dicValue":"中修"},{"id":12,"gid":"12740a26-f6d1-4b53-b981-58b8731f4b10","dicName":"维修级别","dicValue":"部件检修"},{"id":13,"gid":"b695599e-b405-4c36-8271-7e9eea707fce","dicName":"维修级别","dicValue":"更换部件"},{"id":14,"gid":"c8eed58e-837c-4449-8fb4-0879aaf3e5fd","dicName":"维修级别","dicValue":"例行检修"}]
     */

    private boolean success;
    private String tipCode;
    private String msg;
    private String other;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 9
         * gid : b0cffd68-6c43-4075-9c0c-728b0d0e13a4
         * dicName : 维修级别
         * dicValue : 未设置
         */

        private int id;
        private String gid;
        private String dicName;
        private String dicValue;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getDicName() {
            return dicName;
        }

        public void setDicName(String dicName) {
            this.dicName = dicName;
        }

        public String getDicValue() {
            return dicValue;
        }

        public void setDicValue(String dicValue) {
            this.dicValue = dicValue;
        }
    }
}
