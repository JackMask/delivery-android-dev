package com.guxingdongli.yizhangguan.model;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/25
 */

public class PreparationOrderDetailsBase {

    /**
     * success : true
     * tipCode : 0
     * msg : 操作成功！
     * other :
     * data : {"id":22,"gid":"3b46d5f3-54c7-4e44-9765-be2ed38f320f","businessNumber":"SQ20180314003","internalOrderId":147,"hospitalGUID":"a170a487-2439-4657-b107-9ae203851eb1","hospitalName":"重庆市第十三人民医院","departmentName":"肿瘤科含胸外科","supplierGUID":"6b49f65b-386e-45b4-a74e-4ac52f32cb96","supplierName":"河南省百合医疗器械销售有限公司","orderTime":"2018-03-14 00:00:00","totalAmount":21300,"orderStage":"处理中","detailsList":[{"id":215,"gid":"259b060e-e627-431a-8e2d-257c234c8b9f","name":"一次性使用腔内切割吻合器及组件","specification":"GCJQW-45L","model":"GCJQW-45L","packing":"1把/盒","unit":"437","price":"2700","quantity":3,"batchNumber":"","serialNum":"","productNum":"","valid":"","acceptanceQuantity":0,"noAcceptanceQuantity":3,"acceptanceResult":"未验收","status":"正常"},{"id":216,"gid":"367deb85-f022-4fc9-ba12-32ed4f6c4793","name":"一次性使用管型消化道吻合器","specification":"AYC-24","model":"AYC-24","packing":"1把/盒","unit":"437","price":"2200","quantity":6,"batchNumber":"","serialNum":"","productNum":"","valid":"","acceptanceQuantity":0,"noAcceptanceQuantity":6,"acceptanceResult":"未验收","status":"正常"}]}
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
         * id : 22
         * gid : 3b46d5f3-54c7-4e44-9765-be2ed38f320f
         * businessNumber : SQ20180314003
         * internalOrderId : 147
         * hospitalGUID : a170a487-2439-4657-b107-9ae203851eb1
         * hospitalName : 重庆市第十三人民医院
         * departmentName : 肿瘤科含胸外科
         * supplierGUID : 6b49f65b-386e-45b4-a74e-4ac52f32cb96
         * supplierName : 河南省百合医疗器械销售有限公司
         * orderTime : 2018-03-14 00:00:00
         * totalAmount : 21300.0
         * orderStage : 处理中
         * detailsList : [{"id":215,"gid":"259b060e-e627-431a-8e2d-257c234c8b9f","name":"一次性使用腔内切割吻合器及组件","specification":"GCJQW-45L","model":"GCJQW-45L","packing":"1把/盒","unit":"437","price":"2700","quantity":3,"batchNumber":"","serialNum":"","productNum":"","valid":"","acceptanceQuantity":0,"noAcceptanceQuantity":3,"acceptanceResult":"未验收","status":"正常"},{"id":216,"gid":"367deb85-f022-4fc9-ba12-32ed4f6c4793","name":"一次性使用管型消化道吻合器","specification":"AYC-24","model":"AYC-24","packing":"1把/盒","unit":"437","price":"2200","quantity":6,"batchNumber":"","serialNum":"","productNum":"","valid":"","acceptanceQuantity":0,"noAcceptanceQuantity":6,"acceptanceResult":"未验收","status":"正常"}]
         */

        private int id;
        private String gid;
        private String businessNumber;
        private int internalOrderId;
        private String hospitalGUID;
        private String hospitalName;
        private String departmentName;
        private String supplierGUID;
        private String supplierName;
        private String orderTime;
        private double totalAmount;
        private String orderStage;
        private List<DetailsListBean> detailsList;

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

        public String getBusinessNumber() {
            return businessNumber;
        }

        public void setBusinessNumber(String businessNumber) {
            this.businessNumber = businessNumber;
        }

        public int getInternalOrderId() {
            return internalOrderId;
        }

        public void setInternalOrderId(int internalOrderId) {
            this.internalOrderId = internalOrderId;
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

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public String getSupplierGUID() {
            return supplierGUID;
        }

        public void setSupplierGUID(String supplierGUID) {
            this.supplierGUID = supplierGUID;
        }

        public String getSupplierName() {
            return supplierName;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getOrderStage() {
            return orderStage;
        }

        public void setOrderStage(String orderStage) {
            this.orderStage = orderStage;
        }

        public List<DetailsListBean> getDetailsList() {
            return detailsList;
        }

        public void setDetailsList(List<DetailsListBean> detailsList) {
            this.detailsList = detailsList;
        }

        public static class DetailsListBean {
            /**
             * id : 215
             * gid : 259b060e-e627-431a-8e2d-257c234c8b9f
             * name : 一次性使用腔内切割吻合器及组件
             * specification : GCJQW-45L
             * model : GCJQW-45L
             * packing : 1把/盒
             * unit : 437
             * price : 2700
             * quantity : 3.0
             * batchNumber :
             * serialNum :
             * productNum :
             * valid :
             * acceptanceQuantity : 0.0
             * noAcceptanceQuantity : 3.0
             * acceptanceResult : 未验收
             * status : 正常
             */

            private int id;
            private String gid;
            private String name;
            private String specification;
            private String model;
            private String packing;
            private String unit;
            private String price;
            private double quantity;
            private String batchNumber;
            private String serialNum;
            private String productNum;
            private String valid;
            private double acceptanceQuantity;
            private double noAcceptanceQuantity;
            private String acceptanceResult;
            private String status;

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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSpecification() {
                return specification;
            }

            public void setSpecification(String specification) {
                this.specification = specification;
            }

            public String getModel() {
                return model;
            }

            public void setModel(String model) {
                this.model = model;
            }

            public String getPacking() {
                return packing;
            }

            public void setPacking(String packing) {
                this.packing = packing;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public double getQuantity() {
                return quantity;
            }

            public void setQuantity(double quantity) {
                this.quantity = quantity;
            }

            public String getBatchNumber() {
                return batchNumber;
            }

            public void setBatchNumber(String batchNumber) {
                this.batchNumber = batchNumber;
            }

            public String getSerialNum() {
                return serialNum;
            }

            public void setSerialNum(String serialNum) {
                this.serialNum = serialNum;
            }

            public String getProductNum() {
                return productNum;
            }

            public void setProductNum(String productNum) {
                this.productNum = productNum;
            }

            public String getValid() {
                return valid;
            }

            public void setValid(String valid) {
                this.valid = valid;
            }

            public double getAcceptanceQuantity() {
                return acceptanceQuantity;
            }

            public void setAcceptanceQuantity(double acceptanceQuantity) {
                this.acceptanceQuantity = acceptanceQuantity;
            }

            public double getNoAcceptanceQuantity() {
                return noAcceptanceQuantity;
            }

            public void setNoAcceptanceQuantity(double noAcceptanceQuantity) {
                this.noAcceptanceQuantity = noAcceptanceQuantity;
            }

            public String getAcceptanceResult() {
                return acceptanceResult;
            }

            public void setAcceptanceResult(String acceptanceResult) {
                this.acceptanceResult = acceptanceResult;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
