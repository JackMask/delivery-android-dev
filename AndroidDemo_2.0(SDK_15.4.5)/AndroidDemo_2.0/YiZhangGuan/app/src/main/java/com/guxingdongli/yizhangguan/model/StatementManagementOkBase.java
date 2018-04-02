package com.guxingdongli.yizhangguan.model;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/24
 */

public class StatementManagementOkBase {

    /**
     * success : true
     * tipCode : 0
     * msg : 操作成功！
     * other :
     * data : {"data":[{"id":4,"gid":"a3399c90-2465-4202-b314-c6772db18d41","reconciliationRequestId":1,"reconciliationRequestGuid":"A9399C90-2465-4202-B314-C6776DB18A41","requestNumber":" ","recordNumber":" ","hospitalGuid":"A170A487-2439-4657-B107-9AE203851EB1","hospitalId":1,"hospitalName":"重庆市第十三人民医院","supplierId":29,"supplierGuid":"6b49f65b-386e-45b4-a74e-4ac52f32cb96","supplierName":"河南省百合医疗器械销售有限公司","inBusinessNumber":"111111","returnBusinessNumber":"111","actualAmount":21,"pretendAmount":1112,"returnAmount":122,"reconciliationAmount":223,"confirmStatus":"已确认","createTime":"2017-10-25 13:21:16"}],"total":1}
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
         * data : [{"id":4,"gid":"a3399c90-2465-4202-b314-c6772db18d41","reconciliationRequestId":1,"reconciliationRequestGuid":"A9399C90-2465-4202-B314-C6776DB18A41","requestNumber":" ","recordNumber":" ","hospitalGuid":"A170A487-2439-4657-B107-9AE203851EB1","hospitalId":1,"hospitalName":"重庆市第十三人民医院","supplierId":29,"supplierGuid":"6b49f65b-386e-45b4-a74e-4ac52f32cb96","supplierName":"河南省百合医疗器械销售有限公司","inBusinessNumber":"111111","returnBusinessNumber":"111","actualAmount":21,"pretendAmount":1112,"returnAmount":122,"reconciliationAmount":223,"confirmStatus":"已确认","createTime":"2017-10-25 13:21:16"}]
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
             * id : 4
             * gid : a3399c90-2465-4202-b314-c6772db18d41
             * reconciliationRequestId : 1
             * reconciliationRequestGuid : A9399C90-2465-4202-B314-C6776DB18A41
             * requestNumber :
             * recordNumber :
             * hospitalGuid : A170A487-2439-4657-B107-9AE203851EB1
             * hospitalId : 1
             * hospitalName : 重庆市第十三人民医院
             * supplierId : 29
             * supplierGuid : 6b49f65b-386e-45b4-a74e-4ac52f32cb96
             * supplierName : 河南省百合医疗器械销售有限公司
             * inBusinessNumber : 111111
             * returnBusinessNumber : 111
             * actualAmount : 21.0
             * pretendAmount : 1112.0
             * returnAmount : 122.0
             * reconciliationAmount : 223.0
             * confirmStatus : 已确认
             * createTime : 2017-10-25 13:21:16
             */

            private int id;
            private String gid;
            private int reconciliationRequestId;
            private String reconciliationRequestGuid;
            private String requestNumber;
            private String recordNumber;
            private String hospitalGuid;
            private int hospitalId;
            private String hospitalName;
            private int supplierId;
            private String supplierGuid;
            private String supplierName;
            private String inBusinessNumber;
            private String returnBusinessNumber;
            private double actualAmount;
            private double pretendAmount;
            private double returnAmount;
            private double reconciliationAmount;
            private String confirmStatus;
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

            public int getReconciliationRequestId() {
                return reconciliationRequestId;
            }

            public void setReconciliationRequestId(int reconciliationRequestId) {
                this.reconciliationRequestId = reconciliationRequestId;
            }

            public String getReconciliationRequestGuid() {
                return reconciliationRequestGuid;
            }

            public void setReconciliationRequestGuid(String reconciliationRequestGuid) {
                this.reconciliationRequestGuid = reconciliationRequestGuid;
            }

            public String getRequestNumber() {
                return requestNumber;
            }

            public void setRequestNumber(String requestNumber) {
                this.requestNumber = requestNumber;
            }

            public String getRecordNumber() {
                return recordNumber;
            }

            public void setRecordNumber(String recordNumber) {
                this.recordNumber = recordNumber;
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

            public String getInBusinessNumber() {
                return inBusinessNumber;
            }

            public void setInBusinessNumber(String inBusinessNumber) {
                this.inBusinessNumber = inBusinessNumber;
            }

            public String getReturnBusinessNumber() {
                return returnBusinessNumber;
            }

            public void setReturnBusinessNumber(String returnBusinessNumber) {
                this.returnBusinessNumber = returnBusinessNumber;
            }

            public double getActualAmount() {
                return actualAmount;
            }

            public void setActualAmount(double actualAmount) {
                this.actualAmount = actualAmount;
            }

            public double getPretendAmount() {
                return pretendAmount;
            }

            public void setPretendAmount(double pretendAmount) {
                this.pretendAmount = pretendAmount;
            }

            public double getReturnAmount() {
                return returnAmount;
            }

            public void setReturnAmount(double returnAmount) {
                this.returnAmount = returnAmount;
            }

            public double getReconciliationAmount() {
                return reconciliationAmount;
            }

            public void setReconciliationAmount(double reconciliationAmount) {
                this.reconciliationAmount = reconciliationAmount;
            }

            public String getConfirmStatus() {
                return confirmStatus;
            }

            public void setConfirmStatus(String confirmStatus) {
                this.confirmStatus = confirmStatus;
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
