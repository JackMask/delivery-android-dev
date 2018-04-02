package com.guxingdongli.yizhangguan.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/24
 */

public class PushSelectHospitalBase implements Serializable{


    /**
     * success : true
     * tipCode : 0
     * msg : 操作成功！
     * other :
     * data : [{"id":1,"gid":"a170a487-2439-4657-b107-9ae203851eb1","name":"重庆市第十三人民医院","hospitalAddress":""},{"id":4,"gid":"a170a487-2439-4657-b106-9ae203851eb1","name":"金堂县第一人民医院","hospitalAddress":""}]
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

    public static class DataBean implements Serializable {
        /**
         * id : 1
         * gid : a170a487-2439-4657-b107-9ae203851eb1
         * name : 重庆市第十三人民医院
         * hospitalAddress :
         */

        private int id;
        private String gid;
        private String name;
        private String hospitalAddress;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHospitalAddress() {
            return hospitalAddress;
        }

        public void setHospitalAddress(String hospitalAddress) {
            this.hospitalAddress = hospitalAddress;
        }
    }
}
