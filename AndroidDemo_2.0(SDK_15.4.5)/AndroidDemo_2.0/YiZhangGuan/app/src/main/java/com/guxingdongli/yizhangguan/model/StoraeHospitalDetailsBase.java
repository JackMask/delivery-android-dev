package com.guxingdongli.yizhangguan.model;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/16
 */

public class StoraeHospitalDetailsBase {

    /**
     * success : true
     * tipCode : 0
     * msg : 操作成功！
     * other :
     * data : {"id":183,"gid":"103b9641-bdd9-45e6-955f-30c8364b5dc5","businessNumber":"TZ20180316008","internalOrderId":1549,"hospitalGUID":"a170a487-2439-4657-b107-9ae203851eb1","hospitalName":"重庆市第十三人民医院","departmentName":"设备物资科","supplierGUID":"6b49f65b-386e-45b4-a74e-4ac52f32cb96","supplierName":"河南省百合医疗器械销售有限公司","orderTime":"2018-03-16 00:00:00","totalAmount":5401.95,"orderStage":"待接单","detailsList":[{"id":2577,"gid":"3b186177-6a7c-42e5-9fd4-a22721353135","name":"医用棉签","specification":"10公分*50支","model":"10公分*50支","packing":"1600包/件","unit":"包","price":"0.65","quantity":3,"batchNumber":"","valid":"","acceptanceQuantity":0,"noAcceptanceQuantity":3,"acceptanceResult":"未验收","status":"正常"},{"id":2578,"gid":"a632964c-87d6-4a7d-8937-a17c6db90ba6","name":"一次性使用腔内切割吻合器及组件","specification":"GCJQW-45L","model":"GCJQW-45L","packing":"1把/盒","unit":"把","price":"2700","quantity":2,"batchNumber":"","valid":"","acceptanceQuantity":0,"noAcceptanceQuantity":2,"acceptanceResult":"未验收","status":"正常"}]}
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
         * id : 183
         * gid : 103b9641-bdd9-45e6-955f-30c8364b5dc5
         * businessNumber : TZ20180316008
         * internalOrderId : 1549
         * hospitalGUID : a170a487-2439-4657-b107-9ae203851eb1
         * hospitalName : 重庆市第十三人民医院
         * departmentName : 设备物资科
         * supplierGUID : 6b49f65b-386e-45b4-a74e-4ac52f32cb96
         * supplierName : 河南省百合医疗器械销售有限公司
         * orderTime : 2018-03-16 00:00:00
         * totalAmount : 5401.95
         * orderStage : 待接单
         * detailsList : [{"id":2577,"gid":"3b186177-6a7c-42e5-9fd4-a22721353135","name":"医用棉签","specification":"10公分*50支","model":"10公分*50支","packing":"1600包/件","unit":"包","price":"0.65","quantity":3,"batchNumber":"","valid":"","acceptanceQuantity":0,"noAcceptanceQuantity":3,"acceptanceResult":"未验收","status":"正常"},{"id":2578,"gid":"a632964c-87d6-4a7d-8937-a17c6db90ba6","name":"一次性使用腔内切割吻合器及组件","specification":"GCJQW-45L","model":"GCJQW-45L","packing":"1把/盒","unit":"把","price":"2700","quantity":2,"batchNumber":"","valid":"","acceptanceQuantity":0,"noAcceptanceQuantity":2,"acceptanceResult":"未验收","status":"正常"}]
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
             * id : 2577
             * gid : 3b186177-6a7c-42e5-9fd4-a22721353135
             * name : 医用棉签
             * specification : 10公分*50支
             * model : 10公分*50支
             * packing : 1600包/件
             * unit : 包
             * price : 0.65
             * quantity : 3.0
             * batchNumber :
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
            private String SerialNum;
            private int quantity;
            private String batchNumber;
            private String valid;
            private int acceptanceQuantity;
            private int noAcceptanceQuantity;
            private String acceptanceResult;
            private String ProductNum;
            private String status;

            public String getProductNum() {
                return ProductNum;
            }

            public void setProductNum(String productNum) {
                ProductNum = productNum;
            }

            public String getSerialNum() {
                return SerialNum;
            }

            public void setSerialNum(String serialNum) {
                SerialNum = serialNum;
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

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public String getBatchNumber() {
                return batchNumber;
            }

            public void setBatchNumber(String batchNumber) {
                this.batchNumber = batchNumber;
            }

            public String getValid() {
                return valid;
            }

            public void setValid(String valid) {
                this.valid = valid;
            }

            public int getAcceptanceQuantity() {
                return acceptanceQuantity;
            }

            public void setAcceptanceQuantity(int acceptanceQuantity) {
                this.acceptanceQuantity = acceptanceQuantity;
            }

            public int getNoAcceptanceQuantity() {
                return noAcceptanceQuantity;
            }

            public void setNoAcceptanceQuantity(int noAcceptanceQuantity) {
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
