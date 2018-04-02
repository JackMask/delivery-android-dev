package com.guxingdongli.yizhangguan.model;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/24
 */

public class StatementManagementBase {


    /**
     * success : true
     * tipCode : 0
     * msg : 操作成功！
     * other :
     * data : {"data":[{"id":3,"gid":"f04f90fb-be56-4607-9e4c-9c12ca794afa","requestNumber":"ZQ20180208003","hospitalGuid":"a170a487-2439-4657-b107-9ae203851eb1","hospitalId":1,"hospitalName":"重庆市第十三人民医院","supplierId":29,"supplierGuid":"6b49f65b-386e-45b4-a74e-4ac52f32cb96","supplierName":"河南省百合医疗器械销售有限公司","handleStatus":"未处理","reconciliationDate1":"2018-01-01 00:00:00","reconciliationDate2":"2018-01-31 23:59:59","createTime":"2018-02-08 15:22:23"}],"total":1}
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
         * data : [{"id":3,"gid":"f04f90fb-be56-4607-9e4c-9c12ca794afa","requestNumber":"ZQ20180208003","hospitalGuid":"a170a487-2439-4657-b107-9ae203851eb1","hospitalId":1,"hospitalName":"重庆市第十三人民医院","supplierId":29,"supplierGuid":"6b49f65b-386e-45b4-a74e-4ac52f32cb96","supplierName":"河南省百合医疗器械销售有限公司","handleStatus":"未处理","reconciliationDate1":"2018-01-01 00:00:00","reconciliationDate2":"2018-01-31 23:59:59","createTime":"2018-02-08 15:22:23"}]
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

        public static class DataBean {
            /**
             * id : 3
             * gid : f04f90fb-be56-4607-9e4c-9c12ca794afa
             * requestNumber : ZQ20180208003
             * hospitalGuid : a170a487-2439-4657-b107-9ae203851eb1
             * hospitalId : 1
             * hospitalName : 重庆市第十三人民医院
             * supplierId : 29
             * supplierGuid : 6b49f65b-386e-45b4-a74e-4ac52f32cb96
             * supplierName : 河南省百合医疗器械销售有限公司
             * handleStatus : 未处理
             * reconciliationDate1 : 2018-01-01 00:00:00
             * reconciliationDate2 : 2018-01-31 23:59:59
             * createTime : 2018-02-08 15:22:23
             */

            private int id;
            private String gid;
            private String requestNumber;
            private String hospitalGuid;
            private int hospitalId;
            private String hospitalName;
            private int supplierId;
            private String supplierGuid;
            private String supplierName;
            private String handleStatus;
            private String reconciliationDate1;
            private String reconciliationDate2;
            private String createTime;

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

            public String getRequestNumber() {
                return requestNumber;
            }

            public void setRequestNumber(String requestNumber) {
                this.requestNumber = requestNumber;
            }

            public String getHospitalGuid() {
                return hospitalGuid;
            }

            public void setHospitalGuid(String hospitalGuid) {
                this.hospitalGuid = hospitalGuid;
            }

            public int getHospitalId() {
                return hospitalId;
            }

            public void setHospitalId(int hospitalId) {
                this.hospitalId = hospitalId;
            }

            public String getHospitalName() {
                return hospitalName;
            }

            public void setHospitalName(String hospitalName) {
                this.hospitalName = hospitalName;
            }

            public int getSupplierId() {
                return supplierId;
            }

            public void setSupplierId(int supplierId) {
                this.supplierId = supplierId;
            }

            public String getSupplierGuid() {
                return supplierGuid;
            }

            public void setSupplierGuid(String supplierGuid) {
                this.supplierGuid = supplierGuid;
            }

            public String getSupplierName() {
                return supplierName;
            }

            public void setSupplierName(String supplierName) {
                this.supplierName = supplierName;
            }

            public String getHandleStatus() {
                return handleStatus;
            }

            public void setHandleStatus(String handleStatus) {
                this.handleStatus = handleStatus;
            }

            public String getReconciliationDate1() {
                return reconciliationDate1;
            }

            public void setReconciliationDate1(String reconciliationDate1) {
                this.reconciliationDate1 = reconciliationDate1;
            }

            public String getReconciliationDate2() {
                return reconciliationDate2;
            }

            public void setReconciliationDate2(String reconciliationDate2) {
                this.reconciliationDate2 = reconciliationDate2;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }
    }
}
