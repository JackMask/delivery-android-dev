package com.guxingdongli.yizhangguan.model;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/19
 */

public class AddrepairBase {

    /**
     * success : true
     * tipCode : 0
     * msg : 操作成功！
     * other :
     * data : {"businessNumber":"WX20180317141317","hospitalGUID":"a170a487-2439-4657-b107-9ae203851eb1","hospitalName":"重庆市第十三人民医院","departmentID":2,"departmentName":"门诊科","department":[{"department":"门诊科","departmentId":2,"isSelect":false}],"repairTime":"2018-03-17 14:13:17"}
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
         * businessNumber : WX20180317141317
         * hospitalGUID : a170a487-2439-4657-b107-9ae203851eb1
         * hospitalName : 重庆市第十三人民医院
         * departmentID : 2
         * departmentName : 门诊科
         * department : [{"department":"门诊科","departmentId":2,"isSelect":false}]
         * repairTime : 2018-03-17 14:13:17
         */

        private String businessNumber;
        private String hospitalGUID;
        private String hospitalName;
        private int departmentID;
        private String departmentName;
        private String repairTime;
        private List<DepartmentBean> department;

        public String getBusinessNumber() {
            return businessNumber;
        }

        public void setBusinessNumber(String businessNumber) {
            this.businessNumber = businessNumber;
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

        public List<DepartmentBean> getDepartment() {
            return department;
        }

        public void setDepartment(List<DepartmentBean> department) {
            this.department = department;
        }

        public static class DepartmentBean {
            /**
             * department : 门诊科
             * departmentId : 2
             * isSelect : false
             */

            private String department;
            private int departmentId;
            private boolean isSelect;

            public String getDepartment() {
                return department;
            }

            public void setDepartment(String department) {
                this.department = department;
            }

            public int getDepartmentId() {
                return departmentId;
            }

            public void setDepartmentId(int departmentId) {
                this.departmentId = departmentId;
            }

            public boolean isIsSelect() {
                return isSelect;
            }

            public void setIsSelect(boolean isSelect) {
                this.isSelect = isSelect;
            }
        }
    }
}
