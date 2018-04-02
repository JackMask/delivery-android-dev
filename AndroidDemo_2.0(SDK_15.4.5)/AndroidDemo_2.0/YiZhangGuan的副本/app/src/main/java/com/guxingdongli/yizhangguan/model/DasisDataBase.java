package com.guxingdongli.yizhangguan.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/23
 */

public class DasisDataBase implements Serializable {


    /**
     * success : true
     * tipCode : 0
     * msg : 操作成功！
     * other :
     * data : {"data":[{"attachmentUrl":["http://www.yizhangguan.cn/RepairImg/b80d345f-2ef4-49b6-a576-ce12226a6582.png","http://www.yizhangguan.cn/RepairImg/aaefd0f2-9bc1-4fd6-9ffa-c25b92d9b50e.png","http://www.yizhangguan.cn/RepairImg/0121a8db-6198-4b51-bb74-b5684d1c5afc.png"],"serialNumber":"28594","materialTypeID":0,"materialType":"","categoryID":1,"code":"682121-26-25-6","name":"全自动血液分析仪","namePinYin":"","namePY":"","verbalName":"血细胞分析用稀释液","verbalNamePinYin":"","verbalNamePY":"","manufacturerGUID":"00000000-0000-0000-0000-000000000000","manufacturerID":0,"manufacturerName":"希森美康","supplierId":0,"supplierGUID":"00000000-0000-0000-0000-000000000000","supplierName":"","price":321,"property":"试剂","hightPropertyID":0,"hightProperty":"其它","specificationID":0,"specification":"20L/桶","modelID":0,"model":"XS-800，XT,XE通用","packingID":0,"packing":"20L/桶","unitID":0,"unit":"桶","registrationNumber":"国械注进20162404065","useDepartmennt":"全院","barcode":"","attachmentID":"645,646,647","auditStatus":1,"operatorIP":"","operatorID":1,"operatingTime":"2018-03-19 17:33:34","detailsList":null,"retailPrice":0,"id":38,"gid":"05bddad3-5097-420f-b9a2-f04cb74d2fb1","createTime":"2018-03-19 17:33:34","status":1,"remark":""}],"total":27}
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

    public static class DataBeanX implements Serializable {
        /**
         * data : [{"attachmentUrl":["http://www.yizhangguan.cn/RepairImg/b80d345f-2ef4-49b6-a576-ce12226a6582.png","http://www.yizhangguan.cn/RepairImg/aaefd0f2-9bc1-4fd6-9ffa-c25b92d9b50e.png","http://www.yizhangguan.cn/RepairImg/0121a8db-6198-4b51-bb74-b5684d1c5afc.png"],"serialNumber":"28594","materialTypeID":0,"materialType":"","categoryID":1,"code":"682121-26-25-6","name":"全自动血液分析仪","namePinYin":"","namePY":"","verbalName":"血细胞分析用稀释液","verbalNamePinYin":"","verbalNamePY":"","manufacturerGUID":"00000000-0000-0000-0000-000000000000","manufacturerID":0,"manufacturerName":"希森美康","supplierId":0,"supplierGUID":"00000000-0000-0000-0000-000000000000","supplierName":"","price":321,"property":"试剂","hightPropertyID":0,"hightProperty":"其它","specificationID":0,"specification":"20L/桶","modelID":0,"model":"XS-800，XT,XE通用","packingID":0,"packing":"20L/桶","unitID":0,"unit":"桶","registrationNumber":"国械注进20162404065","useDepartmennt":"全院","barcode":"","attachmentID":"645,646,647","auditStatus":1,"operatorIP":"","operatorID":1,"operatingTime":"2018-03-19 17:33:34","detailsList":null,"retailPrice":0,"id":38,"gid":"05bddad3-5097-420f-b9a2-f04cb74d2fb1","createTime":"2018-03-19 17:33:34","status":1,"remark":""}]
         * total : 27
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
             * attachmentUrl : ["http://www.yizhangguan.cn/RepairImg/b80d345f-2ef4-49b6-a576-ce12226a6582.png","http://www.yizhangguan.cn/RepairImg/aaefd0f2-9bc1-4fd6-9ffa-c25b92d9b50e.png","http://www.yizhangguan.cn/RepairImg/0121a8db-6198-4b51-bb74-b5684d1c5afc.png"]
             * serialNumber : 28594
             * materialTypeID : 0
             * materialType :
             * categoryID : 1
             * code : 682121-26-25-6
             * name : 全自动血液分析仪
             * namePinYin :
             * namePY :
             * verbalName : 血细胞分析用稀释液
             * verbalNamePinYin :
             * verbalNamePY :
             * manufacturerGUID : 00000000-0000-0000-0000-000000000000
             * manufacturerID : 0
             * manufacturerName : 希森美康
             * supplierId : 0
             * supplierGUID : 00000000-0000-0000-0000-000000000000
             * supplierName :
             * price : 321
             * property : 试剂
             * hightPropertyID : 0
             * hightProperty : 其它
             * specificationID : 0
             * specification : 20L/桶
             * modelID : 0
             * model : XS-800，XT,XE通用
             * packingID : 0
             * packing : 20L/桶
             * unitID : 0
             * unit : 桶
             * registrationNumber : 国械注进20162404065
             * useDepartmennt : 全院
             * barcode :
             * attachmentID : 645,646,647
             * auditStatus : 1
             * operatorIP :
             * operatorID : 1
             * operatingTime : 2018-03-19 17:33:34
             * detailsList : null
             * retailPrice : 0
             * id : 38
             * gid : 05bddad3-5097-420f-b9a2-f04cb74d2fb1
             * createTime : 2018-03-19 17:33:34
             * status : 1
             * remark :
             */

            private String serialNumber;
            private int materialTypeID;
            private String materialType;
            private int categoryID;
            private String code;
            private String name;
            private String namePinYin;
            private String namePY;
            private String verbalName;
            private String verbalNamePinYin;
            private String verbalNamePY;
            private String manufacturerGUID;
            private int manufacturerID;
            private String manufacturerName;
            private int supplierId;
            private String supplierGUID;
            private String supplierName;
            private int price;
            private String property;
            private int hightPropertyID;
            private String hightProperty;
            private int specificationID;
            private String specification;
            private int modelID;
            private String model;
            private int packingID;
            private String packing;
            private int unitID;
            private String unit;
            private String registrationNumber;
            private String useDepartmennt;
            private String barcode;
            private String attachmentID;
            private int auditStatus;
            private String operatorIP;
            private int operatorID;
            private String operatingTime;
            private Object detailsList;
            private int retailPrice;
            private int id;
            private String gid;
            private String createTime;
            private int status;
            private String remark;
            private List<String> attachmentUrl;

            public String getSerialNumber() {
                return serialNumber;
            }

            public void setSerialNumber(String serialNumber) {
                this.serialNumber = serialNumber;
            }

            public int getMaterialTypeID() {
                return materialTypeID;
            }

            public void setMaterialTypeID(int materialTypeID) {
                this.materialTypeID = materialTypeID;
            }

            public String getMaterialType() {
                return materialType;
            }

            public void setMaterialType(String materialType) {
                this.materialType = materialType;
            }

            public int getCategoryID() {
                return categoryID;
            }

            public void setCategoryID(int categoryID) {
                this.categoryID = categoryID;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNamePinYin() {
                return namePinYin;
            }

            public void setNamePinYin(String namePinYin) {
                this.namePinYin = namePinYin;
            }

            public String getNamePY() {
                return namePY;
            }

            public void setNamePY(String namePY) {
                this.namePY = namePY;
            }

            public String getVerbalName() {
                return verbalName;
            }

            public void setVerbalName(String verbalName) {
                this.verbalName = verbalName;
            }

            public String getVerbalNamePinYin() {
                return verbalNamePinYin;
            }

            public void setVerbalNamePinYin(String verbalNamePinYin) {
                this.verbalNamePinYin = verbalNamePinYin;
            }

            public String getVerbalNamePY() {
                return verbalNamePY;
            }

            public void setVerbalNamePY(String verbalNamePY) {
                this.verbalNamePY = verbalNamePY;
            }

            public String getManufacturerGUID() {
                return manufacturerGUID;
            }

            public void setManufacturerGUID(String manufacturerGUID) {
                this.manufacturerGUID = manufacturerGUID;
            }

            public int getManufacturerID() {
                return manufacturerID;
            }

            public void setManufacturerID(int manufacturerID) {
                this.manufacturerID = manufacturerID;
            }

            public String getManufacturerName() {
                return manufacturerName;
            }

            public void setManufacturerName(String manufacturerName) {
                this.manufacturerName = manufacturerName;
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

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public String getProperty() {
                return property;
            }

            public void setProperty(String property) {
                this.property = property;
            }

            public int getHightPropertyID() {
                return hightPropertyID;
            }

            public void setHightPropertyID(int hightPropertyID) {
                this.hightPropertyID = hightPropertyID;
            }

            public String getHightProperty() {
                return hightProperty;
            }

            public void setHightProperty(String hightProperty) {
                this.hightProperty = hightProperty;
            }

            public int getSpecificationID() {
                return specificationID;
            }

            public void setSpecificationID(int specificationID) {
                this.specificationID = specificationID;
            }

            public String getSpecification() {
                return specification;
            }

            public void setSpecification(String specification) {
                this.specification = specification;
            }

            public int getModelID() {
                return modelID;
            }

            public void setModelID(int modelID) {
                this.modelID = modelID;
            }

            public String getModel() {
                return model;
            }

            public void setModel(String model) {
                this.model = model;
            }

            public int getPackingID() {
                return packingID;
            }

            public void setPackingID(int packingID) {
                this.packingID = packingID;
            }

            public String getPacking() {
                return packing;
            }

            public void setPacking(String packing) {
                this.packing = packing;
            }

            public int getUnitID() {
                return unitID;
            }

            public void setUnitID(int unitID) {
                this.unitID = unitID;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getRegistrationNumber() {
                return registrationNumber;
            }

            public void setRegistrationNumber(String registrationNumber) {
                this.registrationNumber = registrationNumber;
            }

            public String getUseDepartmennt() {
                return useDepartmennt;
            }

            public void setUseDepartmennt(String useDepartmennt) {
                this.useDepartmennt = useDepartmennt;
            }

            public String getBarcode() {
                return barcode;
            }

            public void setBarcode(String barcode) {
                this.barcode = barcode;
            }

            public String getAttachmentID() {
                return attachmentID;
            }

            public void setAttachmentID(String attachmentID) {
                this.attachmentID = attachmentID;
            }

            public int getAuditStatus() {
                return auditStatus;
            }

            public void setAuditStatus(int auditStatus) {
                this.auditStatus = auditStatus;
            }

            public String getOperatorIP() {
                return operatorIP;
            }

            public void setOperatorIP(String operatorIP) {
                this.operatorIP = operatorIP;
            }

            public int getOperatorID() {
                return operatorID;
            }

            public void setOperatorID(int operatorID) {
                this.operatorID = operatorID;
            }

            public String getOperatingTime() {
                return operatingTime;
            }

            public void setOperatingTime(String operatingTime) {
                this.operatingTime = operatingTime;
            }

            public Object getDetailsList() {
                return detailsList;
            }

            public void setDetailsList(Object detailsList) {
                this.detailsList = detailsList;
            }

            public int getRetailPrice() {
                return retailPrice;
            }

            public void setRetailPrice(int retailPrice) {
                this.retailPrice = retailPrice;
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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public List<String> getAttachmentUrl() {
                return attachmentUrl;
            }

            public void setAttachmentUrl(List<String> attachmentUrl) {
                this.attachmentUrl = attachmentUrl;
            }
        }
    }
}
