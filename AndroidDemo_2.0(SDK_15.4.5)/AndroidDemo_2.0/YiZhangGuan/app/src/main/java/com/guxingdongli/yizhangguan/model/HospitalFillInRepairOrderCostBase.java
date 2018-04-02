package com.guxingdongli.yizhangguan.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/20
 */

public class HospitalFillInRepairOrderCostBase implements Serializable {


    /**
     * id : 0
     * gid : string
     * applyID : 0
     * applyGUID : string
     * maintenanceLevel : 0
     * faultAnalysisId : 0
     * testUser : string
     * processTime : 2018-03-20T02:31:30.186Z
     * maintenanceStartDate : 2018-03-20T02:31:30.186Z
     * maintenanceEndDate : 2018-03-20T02:31:30.186Z
     * manHour : 0
     * longShutdown : 0
     * noticStatus : 1
     * attachmentID : string
     * maintenanceSituation : string
     * maintenancePhone : string
     * maintenanceContacts : string
     * repairFeeList : [{"id":0,"feeType":"院内维修费用","fundSource":"string","feeName":"string","quotePrice":0,"actualPrice":0,"attachmentId":"string","attachmentUrl":["string"],"remark":"string"}]
     * replacingFittingList : [{"id":0,"name":"string","quantity":0,"prict":0,"price":0,"unitId":0,"unitName":"string","specification":"string","model":"string","placeOrigin":0,"placeOriginName":"string","brand":"string","manufacturerId":0,"manufacturer":"string","supplierId":0,"supplierName":"string","serialNumber":"string","factoryTime":"2018-03-20T02:31:30.186Z","factoryCode":"string"}]
     */



    private int id;
    private String gid;
    private int applyID;

    private String applyGUID;
    private int maintenanceLevel;
    private int faultAnalysisId;
    private String testUser;
    private String processTime;
    private String maintenanceStartDate;
    private String maintenanceEndDate;
    private String manHour;
    private String longShutdown;
    private int noticStatus;
    private String attachmentID;
    private String maintenanceSituation;
    private String maintenancePhone;
    private String maintenanceContacts;
    private List<RepairFeeListBean> repairFeeList;
    private List<ReplacingFittingListBean> replacingFittingList;

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"gid\":\"" + gid + "\"" +
                ", \"applyID\":" + applyID +
                ", \"applyGUID\":\"" + applyGUID + "\"" +
                ", \"maintenanceLevel\":"+ maintenanceLevel +
                ", \"faultAnalysisId\":"+ faultAnalysisId +
                ", \"testUser\":\"" + testUser + "\"" +
                ", \"processTime\":\"" + processTime + "\"" +
                ", \"maintenanceStartDate\":\"" + maintenanceStartDate + "\"" +
                ", \"maintenanceEndDate\":\"" + maintenanceEndDate + "\"" +
                ", \"manHour\":\"" + manHour + "\"" +
                ", \"longShutdown\":\"" + longShutdown + "\"" +
                ", \"noticStatus\":" + noticStatus +
                ", \"attachmentID\":\"" + attachmentID + "\"" +
                ", \"maintenanceSituation\":\"" + maintenanceSituation + "\"" +
                ", \"maintenancePhone\":\"" + maintenancePhone + "\"" +
                ", \"maintenanceContacts\":\"" + maintenanceContacts + "\"" +
                ", \"repairFeeList\":" + repairFeeList +
                ", \"replacingFittingList\":" + replacingFittingList +
                '}';
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

    public int getApplyID() {
        return applyID;
    }

    public void setApplyID(int applyID) {
        this.applyID = applyID;
    }

    public String getApplyGUID() {
        return applyGUID;
    }

    public void setApplyGUID(String applyGUID) {
        this.applyGUID = applyGUID;
    }

    public int getMaintenanceLevel() {
        return maintenanceLevel;
    }

    public void setMaintenanceLevel(int maintenanceLevel) {
        this.maintenanceLevel = maintenanceLevel;
    }

    public int getFaultAnalysisId() {
        return faultAnalysisId;
    }

    public void setFaultAnalysisId(int faultAnalysisId) {
        this.faultAnalysisId = faultAnalysisId;
    }

    public String getTestUser() {
        return testUser;
    }

    public void setTestUser(String testUser) {
        this.testUser = testUser;
    }

    public String getProcessTime() {
        return processTime;
    }

    public void setProcessTime(String processTime) {
        this.processTime = processTime;
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

    public String getManHour() {
        return manHour;
    }

    public void setManHour(String manHour) {
        this.manHour = manHour;
    }

    public String getLongShutdown() {
        return longShutdown;
    }

    public void setLongShutdown(String longShutdown) {
        this.longShutdown = longShutdown;
    }

    public int getNoticStatus() {
        return noticStatus;
    }

    public void setNoticStatus(int noticStatus) {
        this.noticStatus = noticStatus;
    }

    public String getAttachmentID() {
        return attachmentID;
    }

    public void setAttachmentID(String attachmentID) {
        this.attachmentID = attachmentID;
    }

    public String getMaintenanceSituation() {
        return maintenanceSituation;
    }

    public void setMaintenanceSituation(String maintenanceSituation) {
        this.maintenanceSituation = maintenanceSituation;
    }

    public String getMaintenancePhone() {
        return maintenancePhone;
    }

    public void setMaintenancePhone(String maintenancePhone) {
        this.maintenancePhone = maintenancePhone;
    }

    public String getMaintenanceContacts() {
        return maintenanceContacts;
    }

    public void setMaintenanceContacts(String maintenanceContacts) {
        this.maintenanceContacts = maintenanceContacts;
    }

    public List<RepairFeeListBean> getRepairFeeList() {

        return repairFeeList;
    }

    public void setRepairFeeList(List<RepairFeeListBean> repairFeeList) {
        this.repairFeeList = repairFeeList;
    }

    public List<ReplacingFittingListBean> getReplacingFittingList() {

        return replacingFittingList;
    }

    public void setReplacingFittingList(List<ReplacingFittingListBean> replacingFittingList) {
        this.replacingFittingList = replacingFittingList;
    }

    public static class RepairFeeListBean implements Serializable {
        /**
         * id : 0
         * feeType : 院内维修费用
         * fundSource : string
         * feeName : string
         * quotePrice : 0
         * actualPrice : 0
         * attachmentId : string
         * attachmentUrl : ["string"]
         * remark : string
         */

        private int id;
        private String feeType;
        private String fundSource;
        private String fundSourceId;
        private String feeName;
        private String feeNameId;
        private int quotePrice;
        private int actualPrice;
        private String attachmentId;
        private String remark;
        private List<String> attachmentUrl;
        private List<String> picUrl;
        private List<String> selectPath;

        @Override
        public String toString() {
            return "{" +
                    "\"id\":" + id +
                    ", \"feeType\":\"" + feeType + "\"" +
                    ", \"fundSource\":\"" + fundSource + "\"" +
                    ", \"fundSourceId\":\"" + fundSourceId + "\"" +
                    ", \"feeName\":\"" + feeName + "\"" +
                    ", \"feeNameId\":\"" + feeNameId + "\"" +
                    ", \"quotePrice\":" + quotePrice +
                    ", \"actualPrice\":" + actualPrice +
                    ", \"attachmentId\":\"" + attachmentId + "\"" +
                    ", \"remark\":\"" + remark + "\"" +
                    ", \"attachmentUrl\":" + attachmentUrl +
                    '}';
        }

        public List<String> getSelectPath() {
            return selectPath;
        }

        public void setSelectPath(List<String> selectPath) {
            this.selectPath = selectPath;
        }

        public List<String> getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(List<String> picUrl) {
            this.picUrl = picUrl;
        }

        public String getFundSourceId() {
            return fundSourceId;
        }

        public void setFundSourceId(String fundSourceId) {
            this.fundSourceId = fundSourceId;
        }

        public String getFeeNameId() {
            return feeNameId;
        }

        public void setFeeNameId(String feeNameId) {
            this.feeNameId = feeNameId;
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFeeType() {
            return feeType;
        }

        public void setFeeType(String feeType) {
            this.feeType = feeType;
        }

        public String getFundSource() {
            return fundSource;
        }

        public void setFundSource(String fundSource) {
            this.fundSource = fundSource;
        }

        public String getFeeName() {
            return feeName;
        }

        public void setFeeName(String feeName) {
            this.feeName = feeName;
        }

        public int getQuotePrice() {
            return quotePrice;
        }

        public void setQuotePrice(int quotePrice) {
            this.quotePrice = quotePrice;
        }

        public int getActualPrice() {
            return actualPrice;
        }

        public void setActualPrice(int actualPrice) {
            this.actualPrice = actualPrice;
        }

        public String getAttachmentId() {
            return attachmentId;
        }

        public void setAttachmentId(String attachmentId) {
            this.attachmentId = attachmentId;
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

    public static class ReplacingFittingListBean implements Serializable {
        /**
         * id : 0
         * name : string
         * quantity : 0
         * prict : 0
         * price : 0
         * unitId : 0
         * unitName : string
         * specification : string
         * model : string
         * placeOrigin : 0
         * placeOriginName : string
         * brand : string
         * manufacturerId : 0
         * manufacturer : string
         * supplierId : 0
         * supplierName : string
         * serialNumber : string
         * factoryTime : 2018-03-20T02:31:30.186Z
         * factoryCode : string
         */

        private int id;
        private String name;
        private int quantity;
        private int prict;
        private int price;
        private String unitId;
        private String unitName;
        private String specification;
        private String model;
        private String placeOrigin;
        private String placeOriginName;
        private String brand;
        private String manufacturerId;
        private String manufacturer;
        private String supplierId;
        private String supplierName;
        private String serialNumber;
        private String factoryTime;
        private String factoryCode;

        @Override
        public String toString() {
            return "{" +
                    "\"id\":" + id +
                    ", \"name\":\"" + name + "\"" +
                    ", \"quantity\":" + quantity +
                    ", \"prict\":" + prict +
                    ", \"price\":" + price +
                    ", \"unitId\":\"" + unitId + "\"" +
                    ", \"unitName\":\"" + unitName + "\"" +
                    ", \"specification\":\"" + specification + "\"" +
                    ", \"model\":\"" + model + "\"" +
                    ", \"placeOrigin\":\"" + placeOrigin + "\"" +
                    ", \"placeOriginName\":\"" + placeOriginName + "\"" +
                    ", \"brand\":\"" + brand + "\"" +
                    ", \"manufacturerId\":\"" + manufacturerId + "\"" +
                    ", \"manufacturer\":\"" + manufacturer + "\"" +
                    ", \"supplierId\":\"" + supplierId + "\"" +
                    ", \"supplierName\":\"" + supplierName + "\"" +
                    ", \"serialNumber\":\"" + serialNumber + "\"" +
                    ", \"factoryTime\":\"" + factoryTime + "\"" +
                    ", \"factoryCode\":\"" + factoryCode + "\"" +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getPrict() {
            return prict;
        }

        public void setPrict(int prict) {
            this.prict = prict;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getUnitId() {
            return unitId;
        }

        public void setUnitId(String unitId) {
            this.unitId = unitId;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
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

        public String getPlaceOrigin() {
            return placeOrigin;
        }

        public void setPlaceOrigin(String placeOrigin) {
            this.placeOrigin = placeOrigin;
        }

        public String getPlaceOriginName() {
            return placeOriginName;
        }

        public void setPlaceOriginName(String placeOriginName) {
            this.placeOriginName = placeOriginName;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getManufacturerId() {
            return manufacturerId;
        }

        public void setManufacturerId(String manufacturerId) {
            this.manufacturerId = manufacturerId;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(String supplierId) {
            this.supplierId = supplierId;
        }

        public String getSupplierName() {
            return supplierName;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        public String getSerialNumber() {
            return serialNumber;
        }

        public void setSerialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
        }

        public String getFactoryTime() {
            return factoryTime;
        }

        public void setFactoryTime(String factoryTime) {
            this.factoryTime = factoryTime;
        }

        public String getFactoryCode() {
            return factoryCode;
        }

        public void setFactoryCode(String factoryCode) {
            this.factoryCode = factoryCode;
        }
    }
}
