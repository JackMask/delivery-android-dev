package com.guxingdongli.yizhangguan.model;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/30
 */

public class MyStockOrderListBase {

    /**
     * success : true
     * tipCode : 0
     * msg : 操作成功！
     * other :
     * data : {"data":[{"id":2,"gid":"42b4deb5-bb2d-47d4-8108-af297683ce5e","internalOrderId":0,"businessNumber":"SQ20180329001","orderStaff":null,"orderTime":"2018-03-29 00:00:00","hospitalGuid":"d117b678-b229-4d32-a780-6e002299faea","hospitalId":0,"hospitalName":"金堂县中医医院","departmentId":0,"departmentName":"肿瘤肾内科","patientNo":null,"zyNumber":null,"patientName":null,"age":0,"gender":0,"supplierGUID":"00000000-0000-0000-0000-000000000000","supplierId":0,"internalSupplierID":0,"supplierName":"新华手术器械有限公司","supplierContactName":null,"supplierMobilePhone":null,"orderStage":"已处理","acceptanceResult":0,"auditStatus":0,"operatorId":0,"operatorIp":null,"orderStageColor":"d9d9d9","totalAmount":36,"detailsList":[{"spareOrderId":2,"spareOrderGuid":"42b4deb5-bb2d-47d4-8108-af297683ce5e","internalDetailId":359,"internalConsumablesId":2847,"name":"超声高频外科11111111111111111111111","specificationId":1,"specification":"HAR36","modelId":2,"model":"HAR36","packingId":1,"packing":"塑封","unit":"把","price":12,"batchNumber":"滚滚滚","valid":"2018-03-30 00:00:00","serialNum":"刚刚","productNum":"哥哥哥哥","manufacturerId":27,"manufacturer":"","supplierId":392,"supplierName":"新华手术器械有限公司","quantity":3,"acceptanceResult":"未验收","acceptanceQuantity":0,"provinceId":23,"cityId":374,"countyId":2442,"id":2}]},{"id":1,"gid":"9cb4ad3d-d27e-4eed-9933-116ede82c39b","internalOrderId":0,"businessNumber":"SQ20180328006","orderStaff":null,"orderTime":"2018-03-28 00:00:00","hospitalGuid":"d117b678-b229-4d32-a780-6e002299faea","hospitalId":0,"hospitalName":"金堂县中医医院","departmentId":0,"departmentName":"肿瘤肾内科","patientNo":null,"zyNumber":null,"patientName":null,"age":0,"gender":0,"supplierGUID":"00000000-0000-0000-0000-000000000000","supplierId":0,"internalSupplierID":0,"supplierName":"新华手术器械有限公司","supplierContactName":null,"supplierMobilePhone":null,"orderStage":"处理中","acceptanceResult":0,"auditStatus":0,"operatorId":0,"operatorIp":null,"orderStageColor":"00ff2b","totalAmount":24,"detailsList":[{"spareOrderId":1,"spareOrderGuid":"9cb4ad3d-d27e-4eed-9933-116ede82c39b","internalDetailId":358,"internalConsumablesId":2847,"name":"超声高频外科11111111111111111111111","specificationId":1,"specification":"HAR36","modelId":2,"model":"HAR36","packingId":1,"packing":"塑封","unit":"把","price":12,"batchNumber":"v刚刚","valid":"2018-03-29 15:54:06","serialNum":"4334","productNum":"岗位","manufacturerId":27,"manufacturer":"","supplierId":392,"supplierName":"新华手术器械有限公司","quantity":2,"acceptanceResult":"未验收","acceptanceQuantity":0,"provinceId":23,"cityId":374,"countyId":2442,"id":1}]}],"total":2}
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
         * data : [{"id":2,"gid":"42b4deb5-bb2d-47d4-8108-af297683ce5e","internalOrderId":0,"businessNumber":"SQ20180329001","orderStaff":null,"orderTime":"2018-03-29 00:00:00","hospitalGuid":"d117b678-b229-4d32-a780-6e002299faea","hospitalId":0,"hospitalName":"金堂县中医医院","departmentId":0,"departmentName":"肿瘤肾内科","patientNo":null,"zyNumber":null,"patientName":null,"age":0,"gender":0,"supplierGUID":"00000000-0000-0000-0000-000000000000","supplierId":0,"internalSupplierID":0,"supplierName":"新华手术器械有限公司","supplierContactName":null,"supplierMobilePhone":null,"orderStage":"已处理","acceptanceResult":0,"auditStatus":0,"operatorId":0,"operatorIp":null,"orderStageColor":"d9d9d9","totalAmount":36,"detailsList":[{"spareOrderId":2,"spareOrderGuid":"42b4deb5-bb2d-47d4-8108-af297683ce5e","internalDetailId":359,"internalConsumablesId":2847,"name":"超声高频外科11111111111111111111111","specificationId":1,"specification":"HAR36","modelId":2,"model":"HAR36","packingId":1,"packing":"塑封","unit":"把","price":12,"batchNumber":"滚滚滚","valid":"2018-03-30 00:00:00","serialNum":"刚刚","productNum":"哥哥哥哥","manufacturerId":27,"manufacturer":"","supplierId":392,"supplierName":"新华手术器械有限公司","quantity":3,"acceptanceResult":"未验收","acceptanceQuantity":0,"provinceId":23,"cityId":374,"countyId":2442,"id":2}]},{"id":1,"gid":"9cb4ad3d-d27e-4eed-9933-116ede82c39b","internalOrderId":0,"businessNumber":"SQ20180328006","orderStaff":null,"orderTime":"2018-03-28 00:00:00","hospitalGuid":"d117b678-b229-4d32-a780-6e002299faea","hospitalId":0,"hospitalName":"金堂县中医医院","departmentId":0,"departmentName":"肿瘤肾内科","patientNo":null,"zyNumber":null,"patientName":null,"age":0,"gender":0,"supplierGUID":"00000000-0000-0000-0000-000000000000","supplierId":0,"internalSupplierID":0,"supplierName":"新华手术器械有限公司","supplierContactName":null,"supplierMobilePhone":null,"orderStage":"处理中","acceptanceResult":0,"auditStatus":0,"operatorId":0,"operatorIp":null,"orderStageColor":"00ff2b","totalAmount":24,"detailsList":[{"spareOrderId":1,"spareOrderGuid":"9cb4ad3d-d27e-4eed-9933-116ede82c39b","internalDetailId":358,"internalConsumablesId":2847,"name":"超声高频外科11111111111111111111111","specificationId":1,"specification":"HAR36","modelId":2,"model":"HAR36","packingId":1,"packing":"塑封","unit":"把","price":12,"batchNumber":"v刚刚","valid":"2018-03-29 15:54:06","serialNum":"4334","productNum":"岗位","manufacturerId":27,"manufacturer":"","supplierId":392,"supplierName":"新华手术器械有限公司","quantity":2,"acceptanceResult":"未验收","acceptanceQuantity":0,"provinceId":23,"cityId":374,"countyId":2442,"id":1}]}]
         * total : 2
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
             * id : 2
             * gid : 42b4deb5-bb2d-47d4-8108-af297683ce5e
             * internalOrderId : 0
             * businessNumber : SQ20180329001
             * orderStaff : null
             * orderTime : 2018-03-29 00:00:00
             * hospitalGuid : d117b678-b229-4d32-a780-6e002299faea
             * hospitalId : 0
             * hospitalName : 金堂县中医医院
             * departmentId : 0
             * departmentName : 肿瘤肾内科
             * patientNo : null
             * zyNumber : null
             * patientName : null
             * age : 0
             * gender : 0
             * supplierGUID : 00000000-0000-0000-0000-000000000000
             * supplierId : 0
             * internalSupplierID : 0
             * supplierName : 新华手术器械有限公司
             * supplierContactName : null
             * supplierMobilePhone : null
             * orderStage : 已处理
             * acceptanceResult : 0
             * auditStatus : 0
             * operatorId : 0
             * operatorIp : null
             * orderStageColor : d9d9d9
             * totalAmount : 36.0
             * detailsList : [{"spareOrderId":2,"spareOrderGuid":"42b4deb5-bb2d-47d4-8108-af297683ce5e","internalDetailId":359,"internalConsumablesId":2847,"name":"超声高频外科11111111111111111111111","specificationId":1,"specification":"HAR36","modelId":2,"model":"HAR36","packingId":1,"packing":"塑封","unit":"把","price":12,"batchNumber":"滚滚滚","valid":"2018-03-30 00:00:00","serialNum":"刚刚","productNum":"哥哥哥哥","manufacturerId":27,"manufacturer":"","supplierId":392,"supplierName":"新华手术器械有限公司","quantity":3,"acceptanceResult":"未验收","acceptanceQuantity":0,"provinceId":23,"cityId":374,"countyId":2442,"id":2}]
             */

            private int id;
            private String gid;
            private int internalOrderId;
            private String businessNumber;
            private Object orderStaff;
            private String orderTime;
            private String hospitalGuid;
            private int hospitalId;
            private String hospitalName;
            private int departmentId;
            private String departmentName;
            private Object patientNo;
            private Object zyNumber;
            private Object patientName;
            private int age;
            private int gender;
            private String supplierGUID;
            private int supplierId;
            private int internalSupplierID;
            private String supplierName;
            private Object supplierContactName;
            private Object supplierMobilePhone;
            private String orderStage;
            private int acceptanceResult;
            private int auditStatus;
            private int operatorId;
            private Object operatorIp;
            private String orderStageColor;
            private double totalAmount;
            private boolean type;
            private List<DetailsListBean> detailsList;

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

            public int getInternalOrderId() {
                return internalOrderId;
            }

            public void setInternalOrderId(int internalOrderId) {
                this.internalOrderId = internalOrderId;
            }

            public String getBusinessNumber() {
                return businessNumber;
            }

            public void setBusinessNumber(String businessNumber) {
                this.businessNumber = businessNumber;
            }

            public Object getOrderStaff() {
                return orderStaff;
            }

            public void setOrderStaff(Object orderStaff) {
                this.orderStaff = orderStaff;
            }

            public String getOrderTime() {
                return orderTime;
            }

            public void setOrderTime(String orderTime) {
                this.orderTime = orderTime;
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

            public int getDepartmentId() {
                return departmentId;
            }

            public void setDepartmentId(int departmentId) {
                this.departmentId = departmentId;
            }

            public String getDepartmentName() {
                return departmentName;
            }

            public void setDepartmentName(String departmentName) {
                this.departmentName = departmentName;
            }

            public Object getPatientNo() {
                return patientNo;
            }

            public void setPatientNo(Object patientNo) {
                this.patientNo = patientNo;
            }

            public Object getZyNumber() {
                return zyNumber;
            }

            public void setZyNumber(Object zyNumber) {
                this.zyNumber = zyNumber;
            }

            public Object getPatientName() {
                return patientName;
            }

            public void setPatientName(Object patientName) {
                this.patientName = patientName;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public String getSupplierGUID() {
                return supplierGUID;
            }

            public void setSupplierGUID(String supplierGUID) {
                this.supplierGUID = supplierGUID;
            }

            public int getSupplierId() {
                return supplierId;
            }

            public void setSupplierId(int supplierId) {
                this.supplierId = supplierId;
            }

            public int getInternalSupplierID() {
                return internalSupplierID;
            }

            public void setInternalSupplierID(int internalSupplierID) {
                this.internalSupplierID = internalSupplierID;
            }

            public String getSupplierName() {
                return supplierName;
            }

            public void setSupplierName(String supplierName) {
                this.supplierName = supplierName;
            }

            public Object getSupplierContactName() {
                return supplierContactName;
            }

            public void setSupplierContactName(Object supplierContactName) {
                this.supplierContactName = supplierContactName;
            }

            public Object getSupplierMobilePhone() {
                return supplierMobilePhone;
            }

            public void setSupplierMobilePhone(Object supplierMobilePhone) {
                this.supplierMobilePhone = supplierMobilePhone;
            }

            public String getOrderStage() {
                return orderStage;
            }

            public void setOrderStage(String orderStage) {
                this.orderStage = orderStage;
            }

            public int getAcceptanceResult() {
                return acceptanceResult;
            }

            public void setAcceptanceResult(int acceptanceResult) {
                this.acceptanceResult = acceptanceResult;
            }

            public int getAuditStatus() {
                return auditStatus;
            }

            public void setAuditStatus(int auditStatus) {
                this.auditStatus = auditStatus;
            }

            public int getOperatorId() {
                return operatorId;
            }

            public void setOperatorId(int operatorId) {
                this.operatorId = operatorId;
            }

            public Object getOperatorIp() {
                return operatorIp;
            }

            public void setOperatorIp(Object operatorIp) {
                this.operatorIp = operatorIp;
            }

            public String getOrderStageColor() {
                return orderStageColor;
            }

            public void setOrderStageColor(String orderStageColor) {
                this.orderStageColor = orderStageColor;
            }

            public double getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(double totalAmount) {
                this.totalAmount = totalAmount;
            }

            public List<DetailsListBean> getDetailsList() {
                return detailsList;
            }

            public void setDetailsList(List<DetailsListBean> detailsList) {
                this.detailsList = detailsList;
            }

            public static class DetailsListBean {
                /**
                 * spareOrderId : 2
                 * spareOrderGuid : 42b4deb5-bb2d-47d4-8108-af297683ce5e
                 * internalDetailId : 359
                 * internalConsumablesId : 2847
                 * name : 超声高频外科11111111111111111111111
                 * specificationId : 1
                 * specification : HAR36
                 * modelId : 2
                 * model : HAR36
                 * packingId : 1
                 * packing : 塑封
                 * unit : 把
                 * price : 12.0
                 * batchNumber : 滚滚滚
                 * valid : 2018-03-30 00:00:00
                 * serialNum : 刚刚
                 * productNum : 哥哥哥哥
                 * manufacturerId : 27
                 * manufacturer :
                 * supplierId : 392
                 * supplierName : 新华手术器械有限公司
                 * quantity : 3.0
                 * acceptanceResult : 未验收
                 * acceptanceQuantity : 0.0
                 * provinceId : 23
                 * cityId : 374
                 * countyId : 2442
                 * id : 2
                 */

                private int spareOrderId;
                private String spareOrderGuid;
                private int internalDetailId;
                private int internalConsumablesId;
                private String name;
                private int specificationId;
                private String specification;
                private int modelId;
                private String model;
                private int packingId;
                private String packing;
                private String unit;
                private double price;
                private String batchNumber;
                private String valid;
                private String serialNum;
                private String productNum;
                private int manufacturerId;
                private String manufacturer;
                private int supplierId;
                private String supplierName;
                private double quantity;
                private String acceptanceResult;
                private double acceptanceQuantity;
                private int provinceId;
                private int cityId;
                private int countyId;
                private int id;
                private boolean type;

                public boolean isType() {
                    return type;
                }

                public void setType(boolean type) {
                    this.type = type;
                }

                public int getSpareOrderId() {
                    return spareOrderId;
                }

                public void setSpareOrderId(int spareOrderId) {
                    this.spareOrderId = spareOrderId;
                }

                public String getSpareOrderGuid() {
                    return spareOrderGuid;
                }

                public void setSpareOrderGuid(String spareOrderGuid) {
                    this.spareOrderGuid = spareOrderGuid;
                }

                public int getInternalDetailId() {
                    return internalDetailId;
                }

                public void setInternalDetailId(int internalDetailId) {
                    this.internalDetailId = internalDetailId;
                }

                public int getInternalConsumablesId() {
                    return internalConsumablesId;
                }

                public void setInternalConsumablesId(int internalConsumablesId) {
                    this.internalConsumablesId = internalConsumablesId;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getSpecificationId() {
                    return specificationId;
                }

                public void setSpecificationId(int specificationId) {
                    this.specificationId = specificationId;
                }

                public String getSpecification() {
                    return specification;
                }

                public void setSpecification(String specification) {
                    this.specification = specification;
                }

                public int getModelId() {
                    return modelId;
                }

                public void setModelId(int modelId) {
                    this.modelId = modelId;
                }

                public String getModel() {
                    return model;
                }

                public void setModel(String model) {
                    this.model = model;
                }

                public int getPackingId() {
                    return packingId;
                }

                public void setPackingId(int packingId) {
                    this.packingId = packingId;
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

                public double getPrice() {
                    return price;
                }

                public void setPrice(double price) {
                    this.price = price;
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

                public int getManufacturerId() {
                    return manufacturerId;
                }

                public void setManufacturerId(int manufacturerId) {
                    this.manufacturerId = manufacturerId;
                }

                public String getManufacturer() {
                    return manufacturer;
                }

                public void setManufacturer(String manufacturer) {
                    this.manufacturer = manufacturer;
                }

                public int getSupplierId() {
                    return supplierId;
                }

                public void setSupplierId(int supplierId) {
                    this.supplierId = supplierId;
                }

                public String getSupplierName() {
                    return supplierName;
                }

                public void setSupplierName(String supplierName) {
                    this.supplierName = supplierName;
                }

                public double getQuantity() {
                    return quantity;
                }

                public void setQuantity(double quantity) {
                    this.quantity = quantity;
                }

                public String getAcceptanceResult() {
                    return acceptanceResult;
                }

                public void setAcceptanceResult(String acceptanceResult) {
                    this.acceptanceResult = acceptanceResult;
                }

                public double getAcceptanceQuantity() {
                    return acceptanceQuantity;
                }

                public void setAcceptanceQuantity(double acceptanceQuantity) {
                    this.acceptanceQuantity = acceptanceQuantity;
                }

                public int getProvinceId() {
                    return provinceId;
                }

                public void setProvinceId(int provinceId) {
                    this.provinceId = provinceId;
                }

                public int getCityId() {
                    return cityId;
                }

                public void setCityId(int cityId) {
                    this.cityId = cityId;
                }

                public int getCountyId() {
                    return countyId;
                }

                public void setCountyId(int countyId) {
                    this.countyId = countyId;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }
            }
        }
    }
}
