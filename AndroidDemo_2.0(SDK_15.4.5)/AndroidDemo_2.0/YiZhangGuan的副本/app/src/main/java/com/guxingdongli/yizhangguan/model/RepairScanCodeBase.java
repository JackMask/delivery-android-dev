package com.guxingdongli.yizhangguan.model;

/**
 * @author 余先德
 * @data 2018/3/27
 */

public class RepairScanCodeBase {

    /**
     * success : true
     * tipCode : 0
     * msg : 操作成功！
     * other :
     * data : {"equipId":11629,"equipGuid":"912ce0e1-a419-4816-a80b-d7b385c35b2e","sbNumber":"BH20171000111629","businessNumber":"WX2018030318","name":"新奥洁牌JHJ/CYJ-B60型医用消毒机","hospitalGUID":"a170a487-2439-4657-b107-9ae203851eb1","hospitalName":"重庆市第十三人民医院","departmentID":2,"departmentName":"门诊科","repairTime":"2018-03-27 13:12:12"}
     */

    private boolean success;
    private String tipCode;
    private String msg;
    private String other;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * equipId : 11629
         * equipGuid : 912ce0e1-a419-4816-a80b-d7b385c35b2e
         * sbNumber : BH20171000111629
         * businessNumber : WX2018030318
         * name : 新奥洁牌JHJ/CYJ-B60型医用消毒机
         * hospitalGUID : a170a487-2439-4657-b107-9ae203851eb1
         * hospitalName : 重庆市第十三人民医院
         * departmentID : 2
         * departmentName : 门诊科
         * repairTime : 2018-03-27 13:12:12
         */



        private int equipId;
        private String equipGuid;
        private String sbNumber;
        private String businessNumber;
        private String name;
        private String hospitalGUID;
        private String hospitalName;
        private int departmentID;
        private String departmentName;
        private String repairTime;

        public int getEquipId() {
            return equipId;
        }

        public void setEquipId(int equipId) {
            this.equipId = equipId;
        }

        public String getEquipGuid() {
            return equipGuid;
        }

        public void setEquipGuid(String equipGuid) {
            this.equipGuid = equipGuid;
        }

        public String getSbNumber() {
            return sbNumber;
        }

        public void setSbNumber(String sbNumber) {
            this.sbNumber = sbNumber;
        }

        public String getBusinessNumber() {
            return businessNumber;
        }

        public void setBusinessNumber(String businessNumber) {
            this.businessNumber = businessNumber;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHospitalGUID() {
            return hospitalGUID;
        }

        public void setHospitalGUID(String hospitalGUID) {
            this.hospitalGUID = hospitalGUID;
        }

        public String getHospitalName() {
            return hospitalName;
        }

        public void setHospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
        }

        public int getDepartmentID() {
            return departmentID;
        }

        public void setDepartmentID(int departmentID) {
            this.departmentID = departmentID;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public String getRepairTime() {
            return repairTime;
        }

        public void setRepairTime(String repairTime) {
            this.repairTime = repairTime;
        }
    }
}
