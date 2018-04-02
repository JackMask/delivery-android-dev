package com.guxingdongli.yizhangguan.model;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/27
 */

public class AcceptanceScanCodeBase {

    /**
     * success : true
     * tipCode : 0
     * msg : 操作成功！
     * other :
     * data : {"applyId":167,"applyGuid":"da2361f2-3f63-49e8-b8ba-06d7739bc0c9","businessNumber":"WX20180126175308","name":"输液泵","hospitalId":1,"hospitalGUID":"a170a487-2439-4657-b107-9ae203851eb1","hospitalName":"重庆市第十三人民医院","departmentID":2,"departmentName":"门诊科","repairTime":"2018-01-26 17:53:13","engineerName":"qw","maintenanceEngineer":[{"processID":99,"maintenanceEngineerId":3,"maintenanceEngineer":"qw","maintenanceType":"院内维修","maintenance":"院内工程师"}],"maintenanceStartDate":"2018-01-29 13:13:48","maintenanceEndDate":"2018-01-29 15:46:00","totalRepairPrice":0,"faultAnalysis":"设备操作不当","longShutdown":70,"manHour":3}
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
         * applyId : 167
         * applyGuid : da2361f2-3f63-49e8-b8ba-06d7739bc0c9
         * businessNumber : WX20180126175308
         * name : 输液泵
         * hospitalId : 1
         * hospitalGUID : a170a487-2439-4657-b107-9ae203851eb1
         * hospitalName : 重庆市第十三人民医院
         * departmentID : 2
         * departmentName : 门诊科
         * repairTime : 2018-01-26 17:53:13
         * engineerName : qw
         * maintenanceEngineer : [{"processID":99,"maintenanceEngineerId":3,"maintenanceEngineer":"qw","maintenanceType":"院内维修","maintenance":"院内工程师"}]
         * maintenanceStartDate : 2018-01-29 13:13:48
         * maintenanceEndDate : 2018-01-29 15:46:00
         * totalRepairPrice : 0.0
         * faultAnalysis : 设备操作不当
         * longShutdown : 70
         * manHour : 3
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
        private String faultAnalysis;
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

        public String getFaultAnalysis() {
            return faultAnalysis;
        }

        public void setFaultAnalysis(String faultAnalysis) {
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
             * processID : 99
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
