package com.guxingdongli.yizhangguan.model;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/22
 */

public class MyDeviceDetailsBase {


    /**
     * success : true
     * tipCode : 0
     * msg : 操作成功！
     * other :
     * data : {"id":347,"hospitalGuid":"a170a487-2439-4657-b107-9ae203851eb1","hospitalId":1,"hospitalName":"重庆市第十三人民医院","internalAccountId":0,"accountType":1,"materialNumber":"BH201710001347","contractNumber":"","code":"6840","productStandard":"YZB/鲁 0278-2012","name":"自动血液细菌培养仪","namePinYin":"ZIDONGXUEYEXIJUNPEIYANGYI","namePy":"ZDXYXJPYY","specification":"规格347","model":"LABSTAR EX","placeOrigin":"475","brand":"鑫科","worth":399000,"sbpower":"","unit":"单位","registrationNumber":"鲁械注准20142400087","manufacturer":"山东鑫科生物科技股份有限公司","factoryCode":"F160324001","factoryTime":"2016-03-24 00:00:00","serialNumber":"","attachmentId":"","measureId":-1,"specialTypeId":-1,"testCycleId":-1,"isAssets":2,"property":1,"useAgeLimit":0,"warrantyPeriod":0,"warrantyDate1":"1900-01-01 00:00:00","warrantyDate2":"1900-01-01 00:00:00","useDepartmentId":2,"useDepartment":"门诊科","responsible":"喻茂文","responsiblePhone":"18113154306","responsibleEngineer":"qwce","responsibleEngineerPhone":"18382046629","enableTime":"1900-01-01 00:00:00","useStatus":"维修设备","maintenanceCompany":"","warrantyStatus":1,"maintenanceContacts":"1212","maintenancePhone":"18382046629","maintenanceYears":0,"maintenanceDate1":"1900-01-01 00:00:00","maintenanceDate2":"1900-01-01 00:00:00","fundSource":"230","purchaseDate":"1900-01-01 00:00:00","supplierId":29,"supplierName":"河南省百合医疗器械销售有限公司","quantity":1,"userDefinedCode":"","storedLocation":"","imgList":[]}
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
         * id : 347
         * hospitalGuid : a170a487-2439-4657-b107-9ae203851eb1
         * hospitalId : 1
         * hospitalName : 重庆市第十三人民医院
         * internalAccountId : 0
         * accountType : 1
         * materialNumber : BH201710001347
         * contractNumber :
         * code : 6840
         * productStandard : YZB/鲁 0278-2012
         * name : 自动血液细菌培养仪
         * namePinYin : ZIDONGXUEYEXIJUNPEIYANGYI
         * namePy : ZDXYXJPYY
         * specification : 规格347
         * model : LABSTAR EX
         * placeOrigin : 475
         * brand : 鑫科
         * worth : 399000.0
         * sbpower :
         * unit : 单位
         * registrationNumber : 鲁械注准20142400087
         * manufacturer : 山东鑫科生物科技股份有限公司
         * factoryCode : F160324001
         * factoryTime : 2016-03-24 00:00:00
         * serialNumber :
         * attachmentId :
         * measureId : -1
         * specialTypeId : -1
         * testCycleId : -1
         * isAssets : 2
         * property : 1
         * useAgeLimit : 0
         * warrantyPeriod : 0
         * warrantyDate1 : 1900-01-01 00:00:00
         * warrantyDate2 : 1900-01-01 00:00:00
         * useDepartmentId : 2
         * useDepartment : 门诊科
         * responsible : 喻茂文
         * responsiblePhone : 18113154306
         * responsibleEngineer : qwce
         * responsibleEngineerPhone : 18382046629
         * enableTime : 1900-01-01 00:00:00
         * useStatus : 维修设备
         * maintenanceCompany :
         * warrantyStatus : 1
         * maintenanceContacts : 1212
         * maintenancePhone : 18382046629
         * maintenanceYears : 0
         * maintenanceDate1 : 1900-01-01 00:00:00
         * maintenanceDate2 : 1900-01-01 00:00:00
         * fundSource : 230
         * purchaseDate : 1900-01-01 00:00:00
         * supplierId : 29
         * supplierName : 河南省百合医疗器械销售有限公司
         * quantity : 1
         * userDefinedCode :
         * storedLocation :
         * imgList : []
         */

        private int id;
        private String hospitalGuid;
        private int hospitalId;
        private String hospitalName;
        private int internalAccountId;
        private int accountType;
        private String materialNumber;
        private String contractNumber;
        private String code;
        private String productStandard;
        private String name;
        private String namePinYin;
        private String namePy;
        private String specification;
        private String model;
        private String placeOrigin;
        private String brand;
        private double worth;
        private String sbpower;
        private String unit;
        private String registrationNumber;
        private String manufacturer;
        private String factoryCode;
        private String factoryTime;
        private String serialNumber;
        private String attachmentId;
        private int measureId;
        private int specialTypeId;
        private int testCycleId;
        private int isAssets;
        private int property;
        private int useAgeLimit;
        private int warrantyPeriod;
        private String warrantyDate1;
        private String warrantyDate2;
        private int useDepartmentId;
        private String useDepartment;
        private String responsible;
        private String responsiblePhone;
        private String responsibleEngineer;
        private String responsibleEngineerPhone;
        private String enableTime;
        private String useStatus;
        private String maintenanceCompany;
        private int warrantyStatus;
        private String maintenanceContacts;
        private String maintenancePhone;
        private int maintenanceYears;
        private String maintenanceDate1;
        private String maintenanceDate2;
        private String fundSource;
        private String purchaseDate;
        private int supplierId;
        private String supplierName;
        private int quantity;
        private String userDefinedCode;
        private String storedLocation;
        private List<String> imgList;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getInternalAccountId() {
            return internalAccountId;
        }

        public void setInternalAccountId(int internalAccountId) {
            this.internalAccountId = internalAccountId;
        }

        public int getAccountType() {
            return accountType;
        }

        public void setAccountType(int accountType) {
            this.accountType = accountType;
        }

        public String getMaterialNumber() {
            return materialNumber;
        }

        public void setMaterialNumber(String materialNumber) {
            this.materialNumber = materialNumber;
        }

        public String getContractNumber() {
            return contractNumber;
        }

        public void setContractNumber(String contractNumber) {
            this.contractNumber = contractNumber;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getProductStandard() {
            return productStandard;
        }

        public void setProductStandard(String productStandard) {
            this.productStandard = productStandard;
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

        public String getNamePy() {
            return namePy;
        }

        public void setNamePy(String namePy) {
            this.namePy = namePy;
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

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public double getWorth() {
            return worth;
        }

        public void setWorth(double worth) {
            this.worth = worth;
        }

        public String getSbpower() {
            return sbpower;
        }

        public void setSbpower(String sbpower) {
            this.sbpower = sbpower;
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

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getFactoryCode() {
            return factoryCode;
        }

        public void setFactoryCode(String factoryCode) {
            this.factoryCode = factoryCode;
        }

        public String getFactoryTime() {
            return factoryTime;
        }

        public void setFactoryTime(String factoryTime) {
            this.factoryTime = factoryTime;
        }

        public String getSerialNumber() {
            return serialNumber;
        }

        public void setSerialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
        }

        public String getAttachmentId() {
            return attachmentId;
        }

        public void setAttachmentId(String attachmentId) {
            this.attachmentId = attachmentId;
        }

        public int getMeasureId() {
            return measureId;
        }

        public void setMeasureId(int measureId) {
            this.measureId = measureId;
        }

        public int getSpecialTypeId() {
            return specialTypeId;
        }

        public void setSpecialTypeId(int specialTypeId) {
            this.specialTypeId = specialTypeId;
        }

        public int getTestCycleId() {
            return testCycleId;
        }

        public void setTestCycleId(int testCycleId) {
            this.testCycleId = testCycleId;
        }

        public int getIsAssets() {
            return isAssets;
        }

        public void setIsAssets(int isAssets) {
            this.isAssets = isAssets;
        }

        public int getProperty() {
            return property;
        }

        public void setProperty(int property) {
            this.property = property;
        }

        public int getUseAgeLimit() {
            return useAgeLimit;
        }

        public void setUseAgeLimit(int useAgeLimit) {
            this.useAgeLimit = useAgeLimit;
        }

        public int getWarrantyPeriod() {
            return warrantyPeriod;
        }

        public void setWarrantyPeriod(int warrantyPeriod) {
            this.warrantyPeriod = warrantyPeriod;
        }

        public String getWarrantyDate1() {
            return warrantyDate1;
        }

        public void setWarrantyDate1(String warrantyDate1) {
            this.warrantyDate1 = warrantyDate1;
        }

        public String getWarrantyDate2() {
            return warrantyDate2;
        }

        public void setWarrantyDate2(String warrantyDate2) {
            this.warrantyDate2 = warrantyDate2;
        }

        public int getUseDepartmentId() {
            return useDepartmentId;
        }

        public void setUseDepartmentId(int useDepartmentId) {
            this.useDepartmentId = useDepartmentId;
        }

        public String getUseDepartment() {
            return useDepartment;
        }

        public void setUseDepartment(String useDepartment) {
            this.useDepartment = useDepartment;
        }

        public String getResponsible() {
            return responsible;
        }

        public void setResponsible(String responsible) {
            this.responsible = responsible;
        }

        public String getResponsiblePhone() {
            return responsiblePhone;
        }

        public void setResponsiblePhone(String responsiblePhone) {
            this.responsiblePhone = responsiblePhone;
        }

        public String getResponsibleEngineer() {
            return responsibleEngineer;
        }

        public void setResponsibleEngineer(String responsibleEngineer) {
            this.responsibleEngineer = responsibleEngineer;
        }

        public String getResponsibleEngineerPhone() {
            return responsibleEngineerPhone;
        }

        public void setResponsibleEngineerPhone(String responsibleEngineerPhone) {
            this.responsibleEngineerPhone = responsibleEngineerPhone;
        }

        public String getEnableTime() {
            return enableTime;
        }

        public void setEnableTime(String enableTime) {
            this.enableTime = enableTime;
        }

        public String getUseStatus() {
            return useStatus;
        }

        public void setUseStatus(String useStatus) {
            this.useStatus = useStatus;
        }

        public String getMaintenanceCompany() {
            return maintenanceCompany;
        }

        public void setMaintenanceCompany(String maintenanceCompany) {
            this.maintenanceCompany = maintenanceCompany;
        }

        public int getWarrantyStatus() {
            return warrantyStatus;
        }

        public void setWarrantyStatus(int warrantyStatus) {
            this.warrantyStatus = warrantyStatus;
        }

        public String getMaintenanceContacts() {
            return maintenanceContacts;
        }

        public void setMaintenanceContacts(String maintenanceContacts) {
            this.maintenanceContacts = maintenanceContacts;
        }

        public String getMaintenancePhone() {
            return maintenancePhone;
        }

        public void setMaintenancePhone(String maintenancePhone) {
            this.maintenancePhone = maintenancePhone;
        }

        public int getMaintenanceYears() {
            return maintenanceYears;
        }

        public void setMaintenanceYears(int maintenanceYears) {
            this.maintenanceYears = maintenanceYears;
        }

        public String getMaintenanceDate1() {
            return maintenanceDate1;
        }

        public void setMaintenanceDate1(String maintenanceDate1) {
            this.maintenanceDate1 = maintenanceDate1;
        }

        public String getMaintenanceDate2() {
            return maintenanceDate2;
        }

        public void setMaintenanceDate2(String maintenanceDate2) {
            this.maintenanceDate2 = maintenanceDate2;
        }

        public String getFundSource() {
            return fundSource;
        }

        public void setFundSource(String fundSource) {
            this.fundSource = fundSource;
        }

        public String getPurchaseDate() {
            return purchaseDate;
        }

        public void setPurchaseDate(String purchaseDate) {
            this.purchaseDate = purchaseDate;
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

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getUserDefinedCode() {
            return userDefinedCode;
        }

        public void setUserDefinedCode(String userDefinedCode) {
            this.userDefinedCode = userDefinedCode;
        }

        public String getStoredLocation() {
            return storedLocation;
        }

        public void setStoredLocation(String storedLocation) {
            this.storedLocation = storedLocation;
        }

        public List<String> getImgList() {
            return imgList;
        }

        public void setImgList(List<String> imgList) {
            this.imgList = imgList;
        }
    }
}
