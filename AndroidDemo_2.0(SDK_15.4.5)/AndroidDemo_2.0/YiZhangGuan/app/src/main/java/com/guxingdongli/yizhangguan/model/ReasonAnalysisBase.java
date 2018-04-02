package com.guxingdongli.yizhangguan.model;

import java.util.List;

/**
 * 原因分析
 * @author 余先德
 * @data 2018/3/19
 */

public class ReasonAnalysisBase {

    /**
     * success : true
     * tipCode : 0
     * msg : 操作成功！
     * other :
     * data : [{"id":1,"gid":"5b1b8a05-202d-46aa-ae03-5638d4e14969","dicName":"原因分析","dicValue":"设备操作不当"},{"id":2,"gid":"a1c95a27-f7e2-411b-b150-179df2e36a9a","dicName":"原因分析","dicValue":"电源故障"},{"id":3,"gid":"dae44156-8554-4b5a-a4ac-43c9a7d6b329","dicName":"原因分析","dicValue":"板件故障"},{"id":4,"gid":"4772984a-c02e-47fe-a01c-4702279ef13f","dicName":"原因分析","dicValue":"设备软件故障"},{"id":5,"gid":"5e550067-b51b-4314-9b64-9588b435e816","dicName":"原因分析","dicValue":"机械故障"},{"id":6,"gid":"a85a8887-e63e-4cac-91f1-554026166833","dicName":"原因分析","dicValue":"试剂问题"},{"id":7,"gid":"f31f22e5-b7d0-47f6-89e5-4441315d95a2","dicName":"原因分析","dicValue":"其他"},{"id":8,"gid":"8779467d-3fa2-4932-8128-c4604622c468","dicName":"原因分析","dicValue":"未分类"}]
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
         * id : 1
         * gid : 5b1b8a05-202d-46aa-ae03-5638d4e14969
         * dicName : 原因分析
         * dicValue : 设备操作不当
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
