package com.guxingdongli.yizhangguan.model;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/22
 */

public class AcceptanceListHandleBase {


    /**
     * success : true
     * tipCode : 0
     * msg : 操作成功！
     * other :
     * data : [{"id":105,"gid":"77608009-9524-478b-944a-80bec128e6ab","maintenanceEngineer":"qw","maintenanceStartDate":"2018-01-29 15:17:08","maintenanceEndDate":"2018-01-29 15:42:00","manHour":1,"longShutdown":1,"maintenanceSituation":"vv都不敢"},{"id":107,"gid":"dcbe0d07-fe26-4829-8f5d-88211989bc35","maintenanceEngineer":"1212","maintenanceStartDate":"2018-01-29 15:51:23","maintenanceEndDate":"2018-01-29 16:49:00","manHour":1,"longShutdown":2,"maintenanceSituation":"测定\n"}]
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
         * id : 105
         * gid : 77608009-9524-478b-944a-80bec128e6ab
         * maintenanceEngineer : qw
         * maintenanceStartDate : 2018-01-29 15:17:08
         * maintenanceEndDate : 2018-01-29 15:42:00
         * manHour : 1
         * longShutdown : 1
         * maintenanceSituation : vv都不敢
         */

        private int id;
        private String gid;
        private String maintenanceEngineer;
        private String maintenanceStartDate;
        private String maintenanceEndDate;
        private int manHour;
        private int longShutdown;
        private String maintenanceSituation;

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

        public String getMaintenanceEngineer() {
            return maintenanceEngineer;
        }

        public void setMaintenanceEngineer(String maintenanceEngineer) {
            this.maintenanceEngineer = maintenanceEngineer;
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

        public int getManHour() {
            return manHour;
        }

        public void setManHour(int manHour) {
            this.manHour = manHour;
        }

        public int getLongShutdown() {
            return longShutdown;
        }

        public void setLongShutdown(int longShutdown) {
            this.longShutdown = longShutdown;
        }

        public String getMaintenanceSituation() {
            return maintenanceSituation;
        }

        public void setMaintenanceSituation(String maintenanceSituation) {
            this.maintenanceSituation = maintenanceSituation;
        }
    }
}
