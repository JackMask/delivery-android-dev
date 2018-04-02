package com.guxingdongli.yizhangguan.model;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/17
 */

public class MyRepairOrderBase {


    /**
     * success : true
     * tipCode : 0
     * msg : 操作成功！
     * other :
     * data : {"data":[{"id":234,"gid":"7ce94b52-443f-4026-a964-8ebf84bffa77","hospitalGuid":"a170a487-2439-4657-b107-9ae203851eb1","hospitalName":"重庆市第十三人民医院","businessNumber":"WX20180228212047","repairTime":"2018-02-28 21:21:08","name":"血压计","departmentName":"门诊科","repairUser":"赵菊","maintenanceStage":"院内维修中","maintenanceType":"院内维修","faultDescription":"\u201cvccvv\u201d","isHandle":false,"isShow":false,"attachmentId":"637,638","scanTiming":false,"checkIn":false,"imgList":["http://www.yizhangguan.cn/RepairImg/a1249297-3cdb-46e0-a9ac-5e652153371d.png","http://www.yizhangguan.cn/RepairImg/edd05fc5-8937-4a8c-b10b-19bb817c0f0d.png"]}],"total ":135}
     */

    private boolean success;
    private String tipCode;
    private String msg;
    private String other;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * data : [{"id":234,"gid":"7ce94b52-443f-4026-a964-8ebf84bffa77","hospitalGuid":"a170a487-2439-4657-b107-9ae203851eb1","hospitalName":"重庆市第十三人民医院","businessNumber":"WX20180228212047","repairTime":"2018-02-28 21:21:08","name":"血压计","departmentName":"门诊科","repairUser":"赵菊","maintenanceStage":"院内维修中","maintenanceType":"院内维修","faultDescription":"\u201cvccvv\u201d","isHandle":false,"isShow":false,"attachmentId":"637,638","scanTiming":false,"checkIn":false,"imgList":["http://www.yizhangguan.cn/RepairImg/a1249297-3cdb-46e0-a9ac-5e652153371d.png","http://www.yizhangguan.cn/RepairImg/edd05fc5-8937-4a8c-b10b-19bb817c0f0d.png"]}]
         * total  : 135
         */

        private int total;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 234
             * gid : 7ce94b52-443f-4026-a964-8ebf84bffa77
             * hospitalGuid : a170a487-2439-4657-b107-9ae203851eb1
             * hospitalName : 重庆市第十三人民医院
             * businessNumber : WX20180228212047
             * repairTime : 2018-02-28 21:21:08
             * name : 血压计
             * departmentName : 门诊科
             * repairUser : 赵菊
             * maintenanceStage : 院内维修中
             * maintenanceType : 院内维修
             * faultDescription : “vccvv”
             * isHandle : false
             * isShow : false
             * attachmentId : 637,638
             * scanTiming : false
             * checkIn : false
             * imgList : ["http://www.yizhangguan.cn/RepairImg/a1249297-3cdb-46e0-a9ac-5e652153371d.png","http://www.yizhangguan.cn/RepairImg/edd05fc5-8937-4a8c-b10b-19bb817c0f0d.png"]
             */

            private int id;
            private String gid;
            private String hospitalGuid;
            private String hospitalName;
            private String businessNumber;
            private String repairTime;
            private String name;
            private String departmentName;
            private String repairUser;
            private String maintenanceStage;
            private String maintenanceType;
            private String faultDescription;
            private boolean isHandle;
            private boolean isShow;
            private boolean type;
            private boolean isTime;

            private String attachmentId;
            private boolean scanTiming;
            private boolean checkIn;
            private List<String> imgList;

            @Override
            public String toString() {
                return "DataBean{" +
                        "id=" + id +
                        ", gid='" + gid + '\'' +
                        ", hospitalGuid='" + hospitalGuid + '\'' +
                        ", hospitalName='" + hospitalName + '\'' +
                        ", businessNumber='" + businessNumber + '\'' +
                        ", repairTime='" + repairTime + '\'' +
                        ", name='" + name + '\'' +
                        ", departmentName='" + departmentName + '\'' +
                        ", repairUser='" + repairUser + '\'' +
                        ", maintenanceStage='" + maintenanceStage + '\'' +
                        ", maintenanceType='" + maintenanceType + '\'' +
                        ", faultDescription='" + faultDescription + '\'' +
                        ", isHandle=" + isHandle +
                        ", isShow=" + isShow +
                        ", type=" + type +
                        ", isTime=" + isTime +
                        ", attachmentId='" + attachmentId + '\'' +
                        ", scanTiming=" + scanTiming +
                        ", checkIn=" + checkIn +
                        ", imgList=" + imgList +
                        '}';
            }

            public boolean isTime() {
                return isTime;
            }

            public void setTime(boolean time) {
                isTime = time;
            }

            public boolean isType() {
                return type;
            }

            public void setType(boolean type) {
                this.type = type;
            }

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

            public String getHospitalGuid() {
                return hospitalGuid;
            }

            public void setHospitalGuid(String hospitalGuid) {
                this.hospitalGuid = hospitalGuid;
            }

            public String getHospitalName() {
                return hospitalName;
            }

            public void setHospitalName(String hospitalName) {
                this.hospitalName = hospitalName;
            }

            public String getBusinessNumber() {
                return businessNumber;
            }

            public void setBusinessNumber(String businessNumber) {
                this.businessNumber = businessNumber;
            }

            public String getRepairTime() {
                return repairTime;
            }

            public void setRepairTime(String repairTime) {
                this.repairTime = repairTime;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDepartmentName() {
                return departmentName;
            }

            public void setDepartmentName(String departmentName) {
                this.departmentName = departmentName;
            }

            public String getRepairUser() {
                return repairUser;
            }

            public void setRepairUser(String repairUser) {
                this.repairUser = repairUser;
            }

            public String getMaintenanceStage() {
                return maintenanceStage;
            }

            public void setMaintenanceStage(String maintenanceStage) {
                this.maintenanceStage = maintenanceStage;
            }

            public String getMaintenanceType() {
                return maintenanceType;
            }

            public void setMaintenanceType(String maintenanceType) {
                this.maintenanceType = maintenanceType;
            }

            public String getFaultDescription() {
                return faultDescription;
            }

            public void setFaultDescription(String faultDescription) {
                this.faultDescription = faultDescription;
            }

            public boolean isIsHandle() {
                return isHandle;
            }

            public void setIsHandle(boolean isHandle) {
                this.isHandle = isHandle;
            }

            public boolean isIsShow() {
                return isShow;
            }

            public void setIsShow(boolean isShow) {
                this.isShow = isShow;
            }

            public String getAttachmentId() {
                return attachmentId;
            }

            public void setAttachmentId(String attachmentId) {
                this.attachmentId = attachmentId;
            }

            public boolean isScanTiming() {
                return scanTiming;
            }

            public void setScanTiming(boolean scanTiming) {
                this.scanTiming = scanTiming;
            }

            public boolean isCheckIn() {
                return checkIn;
            }

            public void setCheckIn(boolean checkIn) {
                this.checkIn = checkIn;
            }

            public List<String> getImgList() {
                return imgList;
            }

            public void setImgList(List<String> imgList) {
                this.imgList = imgList;
            }
        }
    }
}
