package com.guxingdongli.yizhangguan.model;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/21
 */

public class UnitBase {

    /**
     * success : true
     * tipCode : 0
     * msg : 操作成功！
     * other :
     * data : [{"id":27,"gid":"ab5b986c-9937-46e0-80e6-4e48a5c4f655","dicName":"单位","dicValue":"个"},{"id":29,"gid":"b20871c1-2f38-407a-bddb-f7043aa0060f","dicName":"单位","dicValue":"88"},{"id":437,"gid":"666815b3-a941-4128-b8a6-7bf51be8e49b","dicName":"单位","dicValue":"把"},{"id":392,"gid":"a5fa29c6-8e13-4c35-ac7f-b0c89ea2f061","dicName":"单位","dicValue":"盒"},{"id":393,"gid":"e4f06847-8a70-4397-8a6c-b7eaf3d1f726","dicName":"单位","dicValue":"瓶"},{"id":394,"gid":"e4972434-325e-4391-b186-89f8b3d17d14","dicName":"单位","dicValue":"套"},{"id":395,"gid":"2c8fcda3-49bc-491f-8116-c7e5b6f25a3e","dicName":"单位","dicValue":"张"},{"id":396,"gid":"3f3be382-0afc-472d-81f7-6d01f5e2b0fc","dicName":"单位","dicValue":"桶"},{"id":397,"gid":"c6e028b2-548d-4f69-8f45-71b66d68b0eb","dicName":"单位","dicValue":"筒"},{"id":398,"gid":"3c10cb58-caee-4b9e-b6c2-bc5666f573be","dicName":"单位","dicValue":"箱"},{"id":399,"gid":"0d9af54f-64f9-4acc-b499-dbf18c034f8e","dicName":"单位","dicValue":"支"},{"id":416,"gid":"d339af9d-acc6-4e71-bc31-dc635188c441","dicName":"单位","dicValue":"才晓得"},{"id":419,"gid":"83e2a654-066d-402b-b546-27450cfd46f8","dicName":"单位","dicValue":"刚刚"},{"id":422,"gid":"827d8fe4-8e8d-4636-b934-df4b47c0a9ab","dicName":"单位","dicValue":"没计划"},{"id":428,"gid":"53402ab3-71c4-4a9a-b209-cd9f9a623200","dicName":"单位","dicValue":"错错错"},{"id":431,"gid":"469976a7-fa5e-49fc-b18e-89e24d4faa5c","dicName":"单位","dicValue":"台"},{"id":432,"gid":"031e2d29-8ed3-461e-a090-2c1b1dbd5378","dicName":"单位","dicValue":"包"},{"id":434,"gid":"a83478c0-2229-4687-940d-e5f14a80df66","dicName":"单位","dicValue":"只"}]
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
         * id : 27
         * gid : ab5b986c-9937-46e0-80e6-4e48a5c4f655
         * dicName : 单位
         * dicValue : 个
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
