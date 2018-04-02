package com.guxingdongli.yizhangguan.model;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/22
 */

public class EquipmentDynamicBase {

    /**
     * success : true
     * tipCode : 0
     * msg : 操作成功！
     * other :
     * data : {"data":[{"id":46605,"gid":"0d741b9c-4cd3-449f-b7e4-2e1afac43101","productStandard":"YZB/鄂0465-2010","specification":"ZQ-1206","materialNumber":"BH2017090386","hospitalGuid":"a170a487-2439-4657-b107-9ae203851eb1","name":"生物疫苗冷藏箱","enableTime":"1900-01-01 00:00:00","useDepartment":"急诊科","useStatus":"在用设备"}],"total":11652}
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
         * data : [{"id":46605,"gid":"0d741b9c-4cd3-449f-b7e4-2e1afac43101","productStandard":"YZB/鄂0465-2010","specification":"ZQ-1206","materialNumber":"BH2017090386","hospitalGuid":"a170a487-2439-4657-b107-9ae203851eb1","name":"生物疫苗冷藏箱","enableTime":"1900-01-01 00:00:00","useDepartment":"急诊科","useStatus":"在用设备"}]
         * total : 11652
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
             * id : 46605
             * gid : 0d741b9c-4cd3-449f-b7e4-2e1afac43101
             * productStandard : YZB/鄂0465-2010
             * specification : ZQ-1206
             * materialNumber : BH2017090386
             * hospitalGuid : a170a487-2439-4657-b107-9ae203851eb1
             * name : 生物疫苗冷藏箱
             * enableTime : 1900-01-01 00:00:00
             * useDepartment : 急诊科
             * useStatus : 在用设备
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
            private boolean type;

            @Override
            public String toString() {
                return "DataBean{" +
                        "id=" + id +
                        ", gid='" + gid + '\'' +
                        ", productStandard='" + productStandard + '\'' +
                        ", specification='" + specification + '\'' +
                        ", materialNumber='" + materialNumber + '\'' +
                        ", hospitalGuid='" + hospitalGuid + '\'' +
                        ", name='" + name + '\'' +
                        ", enableTime='" + enableTime + '\'' +
                        ", useDepartment='" + useDepartment + '\'' +
                        ", useStatus='" + useStatus + '\'' +
                        ", type=" + type +
                        '}';
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
        }
    }
}
