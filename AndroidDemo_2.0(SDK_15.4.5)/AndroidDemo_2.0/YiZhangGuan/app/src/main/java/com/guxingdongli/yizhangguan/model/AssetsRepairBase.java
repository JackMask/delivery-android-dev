package com.guxingdongli.yizhangguan.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/19
 */

public class AssetsRepairBase implements Serializable{


    /**
     * success : true
     * tipCode : 0
     * msg : 操作成功！
     * other :
     * data : {"data":[{"id":1260,"gid":"f819a4f0-2b9e-41ab-811c-e32b87f0e3c3","productStandard":"","specification":"规格1260","materialNumber":"BH2017100011260","hospitalGuid":"a170a487-2439-4657-b107-9ae203851eb1","name":"三人沙发","enableTime":"2017-02-10 00:00:00","useDepartment":"门诊科","useStatus":"在用设备","isShow":false,"detaList":[]}],"total":1}
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

    public static class DataBeanX implements Serializable{
        /**
         * data : [{"id":1260,"gid":"f819a4f0-2b9e-41ab-811c-e32b87f0e3c3","productStandard":"","specification":"规格1260","materialNumber":"BH2017100011260","hospitalGuid":"a170a487-2439-4657-b107-9ae203851eb1","name":"三人沙发","enableTime":"2017-02-10 00:00:00","useDepartment":"门诊科","useStatus":"在用设备","isShow":false,"detaList":[]}]
         * total : 1
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

        public static class DataBean implements Serializable {
            /**
             * id : 1260
             * gid : f819a4f0-2b9e-41ab-811c-e32b87f0e3c3
             * productStandard :
             * specification : 规格1260
             * materialNumber : BH2017100011260
             * hospitalGuid : a170a487-2439-4657-b107-9ae203851eb1
             * name : 三人沙发
             * enableTime : 2017-02-10 00:00:00
             * useDepartment : 门诊科
             * useStatus : 在用设备
             * isShow : false
             * detaList : []
             */

            private int id;
            private String gid;
            private String productStandard;
            private String specification;
            private String materialNumber;
            private String hospitalGuid;
            private String name;
            private String enableTime;
            private String useDepartment;
            private String useStatus;
            private boolean isShow;
            private boolean selectTab;
            private List<?> detaList;

            public boolean isSelectTab() {
                return selectTab;
            }

            public void setSelectTab(boolean selectTab) {
                this.selectTab = selectTab;
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

            public String getProductStandard() {
                return productStandard;
            }

            public void setProductStandard(String productStandard) {
                this.productStandard = productStandard;
            }

            public String getSpecification() {
                return specification;
            }

            public void setSpecification(String specification) {
                this.specification = specification;
            }

            public String getMaterialNumber() {
                return materialNumber;
            }

            public void setMaterialNumber(String materialNumber) {
                this.materialNumber = materialNumber;
            }

            public String getHospitalGuid() {
                return hospitalGuid;
            }

            public void setHospitalGuid(String hospitalGuid) {
                this.hospitalGuid = hospitalGuid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getEnableTime() {
                return enableTime;
            }

            public void setEnableTime(String enableTime) {
                this.enableTime = enableTime;
            }

            public String getUseDepartment() {
                return useDepartment;
            }

            public void setUseDepartment(String useDepartment) {
                this.useDepartment = useDepartment;
            }

            public String getUseStatus() {
                return useStatus;
            }

            public void setUseStatus(String useStatus) {
                this.useStatus = useStatus;
            }

            public boolean isIsShow() {
                return isShow;
            }

            public void setIsShow(boolean isShow) {
                this.isShow = isShow;
            }

            public List<?> getDetaList() {
                return detaList;
            }

            public void setDetaList(List<?> detaList) {
                this.detaList = detaList;
            }
        }
    }
}
