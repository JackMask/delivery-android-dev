package com.guxingdongli.yizhangguan.model;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/19
 */

public class HospitalFillInRepairOrderBase {

    /**
     * success : true
     * tipCode : 0
     * msg : 操作成功！
     * other :
     * data : {"applyId":172,"applyGuid":"559e53b9-560e-47a2-8030-442fa323bbab","businessNumber":"WX20180129144155","name":"办公地点","hospitalId":1,"hospitalGUID":"a170a487-2439-4657-b107-9ae203851eb1","hospitalName":"重庆市第十三人民医院","departmentID":2,"departmentName":"门诊科","repairTime":"2018-01-29 14:42:10","engineerName":"qw,qw","maintenanceEngineer":[{"processID":102,"maintenanceEngineerId":3,"maintenanceEngineer":"qw","maintenanceType":"院内维修","maintenance":"院内工程师"},{"processID":103,"maintenanceEngineerId":3,"maintenanceEngineer":"qw","maintenanceType":"院内维修","maintenance":"院内工程师"}],"maintenanceStartDate":"2018-01-29 14:49:57","maintenanceEndDate":"2018-01-29 14:54:00","totalRepairPrice":52,"faultAnalysis":null,"longShutdown":1,"manHour":1}
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
         * applyId : 172
         * applyGuid : 559e53b9-560e-47a2-8030-442fa323bbab
         * businessNumber : WX20180129144155
         * name : 办公地点
         * hospitalId : 1
         * hospitalGUID : a170a487-2439-4657-b107-9ae203851eb1
         * hospitalName : 重庆市第十三人民医院
         * departmentID : 2
         * departmentName : 门诊科
         * repairTime : 2018-01-29 14:42:10
         * engineerName : qw,qw
         * maintenanceEngineer : [{"processID":102,"maintenanceEngineerId":3,"maintenanceEngineer":"qw","maintenanceType":"院内维修","maintenance":"院内工程师"},{"processID":103,"maintenanceEngineerId":3,"maintenanceEngineer":"qw","maintenanceType":"院内维修","maintenance":"院内工程师"}]
         * maintenanceStartDate : 2018-01-29 14:49:57
         * maintenanceEndDate : 2018-01-29 14:54:00
         * totalRepairPrice : 52.0
         * faultAnalysis : null
         * longShutdown : 1
         * manHour : 1
         */

        private int applyId;
        private String applyGuid;
        private String businessNumber;
        private String name;
        private int hospitalId;
        private String hospitalGUID;
        private String hospitalName;
        private int departmentID;
        private String departmentName;
        private String repairTime;
        private String engineerName;
        private String maintenanceStartDate;
        private String maintenanceEndDate;
        private double totalRepairPrice;
        private Object faultAnalysis;
        private int longShutdown;
        private int manHour;
        private List<MaintenanceEngineerBean> maintenanceEngineer;

        public int getApplyId() {
            return applyId;
        }

        public void setApplyId(int applyId) {
            this.applyId = applyId;
        }

        public String getApplyGuid() {
            return applyGuid;
        }

        public void setApplyGuid(String applyGuid) {
            this.applyGuid = applyGuid;
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

        public int getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(int hospitalId) {
            this.hospitalId = hospitalId;
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

        public String getEngineerName() {
            return engineerName;
        }

        public void setEngineerName(String engineerName) {
            this.engineerName = engineerName;
        }

        public String getMaintenanceStartDate() {
            return maintenanceStartDate;
        }

        public void setMaintenanceStartDate(String maintenanceStartDate) {
            this.maintenanceStartDate = maintenanceStartDate;
        }

        public String getMaintenanceEndDate() {
            return maintenanceEndDate;
        }

        public void setMaintenanceEndDate(String maintenanceEndDate) {
            this.maintenanceEndDate = maintenanceEndDate;
        }

        public double getTotalRepairPrice() {
            return totalRepairPrice;
        }

        public void setTotalRepairPrice(double totalRepairPrice) {
            this.totalRepairPrice = totalRepairPrice;
        }

        public Object getFaultAnalysis() {
            return faultAnalysis;
        }

        public void setFaultAnalysis(Object faultAnalysis) {
            this.faultAnalysis = faultAnalysis;
        }

        public int getLongShutdown() {
            return longShutdown;
        }

        public void setLongShutdown(int longShutdown) {
            this.longShutdown = longShutdown;
        }

        public int getManHour() {
            return manHour;
        }

        public void setManHour(int manHour) {
            this.manHour = manHour;
        }

        public List<MaintenanceEngineerBean> getMaintenanceEngineer() {
            return maintenanceEngineer;
        }

        public void setMaintenanceEngineer(List<MaintenanceEngineerBean> maintenanceEngineer) {
            this.maintenanceEngineer = maintenanceEngineer;
        }

        public static class MaintenanceEngineerBean {
            /**
             * processID : 102
             * maintenanceEngineerId : 3
             * maintenanceEngineer : qw
             * maintenanceType : 院内维修
             * maintenance : 院内工程师
             */

            private int processID;
            private int maintenanceEngineerId;
            private String maintenanceEngineer;
            private String maintenanceType;
            private String maintenance;

            public int getProcessID() {
                return processID;
            }

            public void setProcessID(int processID) {
                this.processID = processID;
            }

            public int getMaintenanceEngineerId() {
                return maintenanceEngineerId;
            }

            public void setMaintenanceEngineerId(int maintenanceEngineerId) {
                this.maintenanceEngineerId = maintenanceEngineerId;
            }

            public String getMaintenanceEngineer() {
                return maintenanceEngineer;
            }

            public void setMaintenanceEngineer(String maintenanceEngineer) {
                this.maintenanceEngineer = maintenanceEngineer;
            }

            public String getMaintenanceType() {
                return maintenanceType;
            }

            public void setMaintenanceType(String maintenanceType) {
                this.maintenanceType = maintenanceType;
            }

            public String getMaintenance() {
                return maintenance;
            }

            public void setMaintenance(String maintenance) {
                this.maintenance = maintenance;
            }
        }
    }
}
