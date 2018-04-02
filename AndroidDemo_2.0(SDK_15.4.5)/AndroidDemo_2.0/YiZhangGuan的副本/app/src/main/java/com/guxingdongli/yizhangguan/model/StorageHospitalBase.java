package com.guxingdongli.yizhangguan.model;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/16
 */

public class StorageHospitalBase {

    /**
     * success : true
     * tipCode : 0
     * msg : 操作成功！
     * other :
     * data : {"data":[{"id":183,"gid":"103b9641-bdd9-45e6-955f-30 c8364b5dc5","businessNumber ":"TZ20180316008 ","hospitalGuid ":"a170a487 - 2439 - 4657 - b107 - 9 ae203851eb1 ","hospitalName ":"重庆市第十三人民医院 ","departmentName ":"设备物资科 ","supplierName ":"河南省百合医疗器械销售有限公司","orderTime ":"2018 - 03 - 16 00: 00: 00 ","totalAmount ":5401.95,"orderStage ":"待接单 ","orderStageColor ":"red ","acceptanceResult ":"未验收 ","isShow ":false,"detailsList":[{"orderId":183,"orderGuid":"103b9641-bdd9-45e6-955f-30c8364b5dc5","internalDetailId":6019,"internalConsumablesId":39841,"name":"医用棉签","specificationId":600,"specification":"10公分*50支","modelId":16441,"model":"10公分*50支","packingId":16442,"batchNumber":"","valid":"1900-01-01 00:00:00","packing":"1600包/件","unit":"包","price":0.65,"internalManufacturerID":124,"manufacturerId":424,"manufacturer":"河南飘安集团有限公司","internalSupplierID":123,"supplierId":29,"supplierGUID":"6b49f65b-386e-45b4-a74e-4ac52f32cb96","supplierName":"河南省百合医疗器械销售有限公司","quantity":3,"acceptanceResult":"未验收","acceptanceQuantity":0,"provinceId":4,"cityId":86,"countyId":0,"id":2577,"gid":"3b186177-6a7c-42e5-9fd4-a22721353135","createTime":"2018-03-16 13:42:43","status":1,"remark":""},{"orderId":183,"orderGuid":"103b9641-bdd9-45e6-955f-30c8364b5dc5","internalDetailId":6020,"internalConsumablesId":33966,"name":"一次性使用腔内切割吻合器及组件","specificationId":16509,"specification":"GCJQW-45L","modelId":16508,"model":"GCJQW-45L","packingId":688,"batchNumber":"","valid":"1900-01-01 00:00:00","packing":"1把/盒","unit":"把","price":2700,"internalManufacturerID":150,"manufacturerId":425,"manufacturer":"江苏冠创医疗科技有限公司","internalSupplierID":123,"supplierId":29,"supplierGUID":"6b49f65b-386e-45b4-a74e-4ac52f32cb96","supplierName":"河南省百合医疗器械销售有限公司","quantity":2,"acceptanceResult":"未验收","acceptanceQuantity":0,"provinceId":4,"cityId":86,"countyId":0,"id":2578,"gid":"a632964c-87d6-4a7d-8937-a17c6db90ba6","createTime":"2018-03-16 13:42:43","status":1,"remark":""}]}],"total ":1}
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
         * data : [{"id":183,"gid":"103b9641-bdd9-45e6-955f-30 c8364b5dc5","businessNumber ":"TZ20180316008 ","hospitalGuid ":"a170a487 - 2439 - 4657 - b107 - 9 ae203851eb1 ","hospitalName ":"重庆市第十三人民医院 ","departmentName ":"设备物资科 ","supplierName ":"河南省百合医疗器械销售有限公司","orderTime ":"2018 - 03 - 16 00: 00: 00 ","totalAmount ":5401.95,"orderStage ":"待接单 ","orderStageColor ":"red ","acceptanceResult ":"未验收 ","isShow ":false,"detailsList":[{"orderId":183,"orderGuid":"103b9641-bdd9-45e6-955f-30c8364b5dc5","internalDetailId":6019,"internalConsumablesId":39841,"name":"医用棉签","specificationId":600,"specification":"10公分*50支","modelId":16441,"model":"10公分*50支","packingId":16442,"batchNumber":"","valid":"1900-01-01 00:00:00","packing":"1600包/件","unit":"包","price":0.65,"internalManufacturerID":124,"manufacturerId":424,"manufacturer":"河南飘安集团有限公司","internalSupplierID":123,"supplierId":29,"supplierGUID":"6b49f65b-386e-45b4-a74e-4ac52f32cb96","supplierName":"河南省百合医疗器械销售有限公司","quantity":3,"acceptanceResult":"未验收","acceptanceQuantity":0,"provinceId":4,"cityId":86,"countyId":0,"id":2577,"gid":"3b186177-6a7c-42e5-9fd4-a22721353135","createTime":"2018-03-16 13:42:43","status":1,"remark":""},{"orderId":183,"orderGuid":"103b9641-bdd9-45e6-955f-30c8364b5dc5","internalDetailId":6020,"internalConsumablesId":33966,"name":"一次性使用腔内切割吻合器及组件","specificationId":16509,"specification":"GCJQW-45L","modelId":16508,"model":"GCJQW-45L","packingId":688,"batchNumber":"","valid":"1900-01-01 00:00:00","packing":"1把/盒","unit":"把","price":2700,"internalManufacturerID":150,"manufacturerId":425,"manufacturer":"江苏冠创医疗科技有限公司","internalSupplierID":123,"supplierId":29,"supplierGUID":"6b49f65b-386e-45b4-a74e-4ac52f32cb96","supplierName":"河南省百合医疗器械销售有限公司","quantity":2,"acceptanceResult":"未验收","acceptanceQuantity":0,"provinceId":4,"cityId":86,"countyId":0,"id":2578,"gid":"a632964c-87d6-4a7d-8937-a17c6db90ba6","createTime":"2018-03-16 13:42:43","status":1,"remark":""}]}]
         * total  : 1
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
             * id : 183
             * gid : 103b9641-bdd9-45e6-955f-30 c8364b5dc5
             * businessNumber  : TZ20180316008
             * hospitalGuid  : a170a487 - 2439 - 4657 - b107 - 9 ae203851eb1
             * hospitalName  : 重庆市第十三人民医院
             * departmentName  : 设备物资科
             * supplierName  : 河南省百合医疗器械销售有限公司
             * orderTime  : 2018 - 03 - 16 00: 00: 00
             * totalAmount  : 5401.95
             * orderStage  : 待接单
             * orderStageColor  : red
             * acceptanceResult  : 未验收
             * isShow  : false
             * detailsList : [{"orderId":183,"orderGuid":"103b9641-bdd9-45e6-955f-30c8364b5dc5","internalDetailId":6019,"internalConsumablesId":39841,"name":"医用棉签","specificationId":600,"specification":"10公分*50支","modelId":16441,"model":"10公分*50支","packingId":16442,"batchNumber":"","valid":"1900-01-01 00:00:00","packing":"1600包/件","unit":"包","price":0.65,"internalManufacturerID":124,"manufacturerId":424,"manufacturer":"河南飘安集团有限公司","internalSupplierID":123,"supplierId":29,"supplierGUID":"6b49f65b-386e-45b4-a74e-4ac52f32cb96","supplierName":"河南省百合医疗器械销售有限公司","quantity":3,"acceptanceResult":"未验收","acceptanceQuantity":0,"provinceId":4,"cityId":86,"countyId":0,"id":2577,"gid":"3b186177-6a7c-42e5-9fd4-a22721353135","createTime":"2018-03-16 13:42:43","status":1,"remark":""},{"orderId":183,"orderGuid":"103b9641-bdd9-45e6-955f-30c8364b5dc5","internalDetailId":6020,"internalConsumablesId":33966,"name":"一次性使用腔内切割吻合器及组件","specificationId":16509,"specification":"GCJQW-45L","modelId":16508,"model":"GCJQW-45L","packingId":688,"batchNumber":"","valid":"1900-01-01 00:00:00","packing":"1把/盒","unit":"把","price":2700,"internalManufacturerID":150,"manufacturerId":425,"manufacturer":"江苏冠创医疗科技有限公司","internalSupplierID":123,"supplierId":29,"supplierGUID":"6b49f65b-386e-45b4-a74e-4ac52f32cb96","supplierName":"河南省百合医疗器械销售有限公司","quantity":2,"acceptanceResult":"未验收","acceptanceQuantity":0,"provinceId":4,"cityId":86,"countyId":0,"id":2578,"gid":"a632964c-87d6-4a7d-8937-a17c6db90ba6","createTime":"2018-03-16 13:42:43","status":1,"remark":""}]
             */

            private int id;
            private String gid;
            private String businessNumber;
            private String hospitalGuid;
            private String hospitalName;
            private String departmentName;
            private String supplierName;
            private String orderTime;
            private double totalAmount;
            private String orderStage;
            private String orderStageColor;
            private String acceptanceResult;
            private boolean isShow;
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

            public String getBusinessNumber() {
                return businessNumber;
            }

            public void setBusinessNumber(String businessNumber) {
                this.businessNumber = businessNumber;
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

            public String getDepartmentName() {
                return departmentName;
            }

            public void setDepartmentName(String departmentName) {
                this.departmentName = departmentName;
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

            public String getOrderStageColor() {
                return orderStageColor;
            }

            public void setOrderStageColor(String orderStageColor) {
                this.orderStageColor = orderStageColor;
            }

            public String getAcceptanceResult() {
                return acceptanceResult;
            }

            public void setAcceptanceResult(String acceptanceResult) {
                this.acceptanceResult = acceptanceResult;
            }

            public boolean isIsShow() {
                return isShow;
            }

            public void setIsShow(boolean isShow) {
                this.isShow = isShow;
            }

            public List<DetailsListBean> getDetailsList() {
                return detailsList;
            }

            public void setDetailsList(List<DetailsListBean> detailsList) {
                this.detailsList = detailsList;
            }

            public static class DetailsListBean {
                /**
                 * orderId : 183
                 * orderGuid : 103b9641-bdd9-45e6-955f-30c8364b5dc5
                 * internalDetailId : 6019
                 * internalConsumablesId : 39841
                 * name : 医用棉签
                 * specificationId : 600
                 * specification : 10公分*50支
                 * modelId : 16441
                 * model : 10公分*50支
                 * packingId : 16442
                 * batchNumber :
                 * valid : 1900-01-01 00:00:00
                 * packing : 1600包/件
                 * unit : 包
                 * price : 0.65
                 * internalManufacturerID : 124
                 * manufacturerId : 424
                 * manufacturer : 河南飘安集团有限公司
                 * internalSupplierID : 123
                 * supplierId : 29
                 * supplierGUID : 6b49f65b-386e-45b4-a74e-4ac52f32cb96
                 * supplierName : 河南省百合医疗器械销售有限公司
                 * quantity : 3
                 * acceptanceResult : 未验收
                 * acceptanceQuantity : 0
                 * provinceId : 4
                 * cityId : 86
                 * countyId : 0
                 * id : 2577
                 * gid : 3b186177-6a7c-42e5-9fd4-a22721353135
                 * createTime : 2018-03-16 13:42:43
                 * status : 1
                 * remark :
                 */

                private int orderId;
                private String orderGuid;
                private int internalDetailId;
                private int internalConsumablesId;
                private String name;
                private int specificationId;
                private String specification;
                private int modelId;
                private String model;
                private int packingId;
                private String batchNumber;
                private String valid;
                private String packing;
                private String unit;
                private double price;
                private int internalManufacturerID;
                private int manufacturerId;
                private String manufacturer;
                private int internalSupplierID;
                private int supplierId;
                private String supplierGUID;
                private String supplierName;
                private int quantity;
                private String acceptanceResult;
                private int acceptanceQuantity;
                private int provinceId;
                private int cityId;
                private int countyId;
                private int id;
                private String gid;
                private String createTime;
                private String status;
                private String remark;

                public int getOrderId() {
                    return orderId;
                }

                public void setOrderId(int orderId) {
                    this.orderId = orderId;
                }

                public String getOrderGuid() {
                    return orderGuid;
                }

                public void setOrderGuid(String orderGuid) {
                    this.orderGuid = orderGuid;
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

                public int getInternalManufacturerID() {
                    return internalManufacturerID;
                }

                public void setInternalManufacturerID(int internalManufacturerID) {
                    this.internalManufacturerID = internalManufacturerID;
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

                public int getInternalSupplierID() {
                    return internalSupplierID;
                }

                public void setInternalSupplierID(int internalSupplierID) {
                    this.internalSupplierID = internalSupplierID;
                }

                public int getSupplierId() {
                    return supplierId;
                }

                public void setSupplierId(int supplierId) {
                    this.supplierId = supplierId;
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

                public int getQuantity() {
                    return quantity;
                }

                public void setQuantity(int quantity) {
                    this.quantity = quantity;
                }

                public String getAcceptanceResult() {
                    return acceptanceResult;
                }

                public void setAcceptanceResult(String acceptanceResult) {
                    this.acceptanceResult = acceptanceResult;
                }

                public int getAcceptanceQuantity() {
                    return acceptanceQuantity;
                }

                public void setAcceptanceQuantity(int acceptanceQuantity) {
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

                public String getGid() {
                    return gid;
                }

                public void setGid(String gid) {
                    this.gid = gid;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getRemark() {
                    return remark;
                }

                public void setRemark(String remark) {
                    this.remark = remark;
                }
            }
        }
    }
}
