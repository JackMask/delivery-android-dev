package com.guxingdongli.yizhangguan.model;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/19
 */

public class MyRepairOrderDetailsBase {

    /**
     * success : true
     * tipCode : 0
     * msg : 操作成功！
     * other :
     * data : {"id":240,"gid":"64f60553-ab43-485b-9629-43b7d8d30d15","businessNumber":"WX201803007","name":"压力表","specification":"ZQ-1206","model":"/","worth":0,"responsible":"刘义胜","manufacturer":"武汉市东湖开发区东信路SBI创业街10栋A座16楼","departmentName":"急诊科","repairTime":"2018-03-01 17:02:10","repairUser":"陈玲","attachmentId":"","attachmentUrl":["http://www.yizhangguan.cn/RepairImg/a1249297-3cdb-46e0-a9ac-5e652153371d.png","http://www.yizhangguan.cn/RepairImg/edd05fc5-8937-4a8c-b10b-19bb817c0f0d.png"],"faultDescription":"vvvvvvvv","acceptanceUser":"陈玲","completionDate":"2018-03-01 17:07:59","maintenanceStage":"已验收","maintenanceType":"院内维修","acceptanceStatus":"验收通过","acceptanceComments":"nhh","hospitalProcessList":[{"id":143,"gid":"e5f95d31-831a-4359-8cfd-28d64ae44ce8","maintenanceLevel":10,"maintenanceLevelName":"大修","faultAnalysisId":1,"faultAnalysisName":"设备操作不当","testUser":"vgg","processTime":"2018-02-28 17:50:00","maintenanceStartDate":"2018-02-28 17:49:35","maintenanceEndDate":"2018-02-28 17:50:00","manHour":1,"longShutdown":1,"attachmentId":"","attachmentUrl":null,"maintenanceSituation":"cgjhjjjh"}],"outHospitalProcessList":[{"id":144,"gid":"2a412704-4372-4517-b1c7-fb20411026cf","maintenanceLevel":10,"maintenanceLevelName":"大修","faultAnalysisId":2,"faultAnalysisName":"电源故障","testUser":"vbjj","processTime":"2018-02-28 17:53:00","maintenanceStartDate":"2018-02-28 17:52:58","maintenanceEndDate":"2018-02-28 17:53:00","manHour":1,"longShutdown":1,"attachmentId":"","attachmentUrl":null,"maintenanceSituation":"chjjjjj"}],"repairFeeList":[{"id":72,"feeType":"院外维修费用","fundSource":"资金来源","feeName":"费用名称","quotePrice":52,"actualPrice":69,"attachmentId":"","attachmentUrl":null,"remark":"vgjj"}],"replacingFittingList":[{"id":63,"name":"fhjj","quantity":1,"prict":2,"price":2,"unitId":392,"unitName":"盒","specification":"hhj","model":"ggj","placeOrigin":22,"placeOriginName":"成都","brand":"fhj","manufacturerId":0,"manufacturer":"fhj","supplierId":0,"supplierName":"ghj","serialNumber":"bhjkmn","factoryTime":"1900-01-01 00:00:00","factoryCode":""}],"evaluationList":[{"id":48,"hospitalGuid":"a170a487-2439-4657-b107-9ae203851eb1","hospitalId":1,"processId":143,"businessNumber":"WX201802030","engineerName":"曾荣","engineerId":3,"maintenanceType":"院内维修","serviceEvaluation":5,"evaluationComment":"不过v不不不","departmentId":5,"attachmentId":"","applyId":233,"acceptanceStatus":"验收通过"},{"id":49,"hospitalGuid":"a170a487-2439-4657-b107-9ae203851eb1","hospitalId":1,"processId":144,"businessNumber":"WX201802030","engineerName":"1212","engineerId":1,"maintenanceType":"院外维修","serviceEvaluation":5,"evaluationComment":"不过v不不不","departmentId":5,"attachmentId":"","applyId":233,"acceptanceStatus":"验收通过"}]}
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
         * id : 240
         * gid : 64f60553-ab43-485b-9629-43b7d8d30d15
         * businessNumber : WX201803007
         * name : 压力表
         * specification : ZQ-1206
         * model : /
         * worth : 0
         * responsible : 刘义胜
         * manufacturer : 武汉市东湖开发区东信路SBI创业街10栋A座16楼
         * departmentName : 急诊科
         * repairTime : 2018-03-01 17:02:10
         * repairUser : 陈玲
         * attachmentId :
         * attachmentUrl : ["http://www.yizhangguan.cn/RepairImg/a1249297-3cdb-46e0-a9ac-5e652153371d.png","http://www.yizhangguan.cn/RepairImg/edd05fc5-8937-4a8c-b10b-19bb817c0f0d.png"]
         * faultDescription : vvvvvvvv
         * acceptanceUser : 陈玲
         * completionDate : 2018-03-01 17:07:59
         * maintenanceStage : 已验收
         * maintenanceType : 院内维修
         * acceptanceStatus : 验收通过
         * acceptanceComments : nhh
         * hospitalProcessList : [{"id":143,"gid":"e5f95d31-831a-4359-8cfd-28d64ae44ce8","maintenanceLevel":10,"maintenanceLevelName":"大修","faultAnalysisId":1,"faultAnalysisName":"设备操作不当","testUser":"vgg","processTime":"2018-02-28 17:50:00","maintenanceStartDate":"2018-02-28 17:49:35","maintenanceEndDate":"2018-02-28 17:50:00","manHour":1,"longShutdown":1,"attachmentId":"","attachmentUrl":null,"maintenanceSituation":"cgjhjjjh"}]
         * outHospitalProcessList : [{"id":144,"gid":"2a412704-4372-4517-b1c7-fb20411026cf","maintenanceLevel":10,"maintenanceLevelName":"大修","faultAnalysisId":2,"faultAnalysisName":"电源故障","testUser":"vbjj","processTime":"2018-02-28 17:53:00","maintenanceStartDate":"2018-02-28 17:52:58","maintenanceEndDate":"2018-02-28 17:53:00","manHour":1,"longShutdown":1,"attachmentId":"","attachmentUrl":null,"maintenanceSituation":"chjjjjj"}]
         * repairFeeList : [{"id":72,"feeType":"院外维修费用","fundSource":"资金来源","feeName":"费用名称","quotePrice":52,"actualPrice":69,"attachmentId":"","attachmentUrl":null,"remark":"vgjj"}]
         * replacingFittingList : [{"id":63,"name":"fhjj","quantity":1,"prict":2,"price":2,"unitId":392,"unitName":"盒","specification":"hhj","model":"ggj","placeOrigin":22,"placeOriginName":"成都","brand":"fhj","manufacturerId":0,"manufacturer":"fhj","supplierId":0,"supplierName":"ghj","serialNumber":"bhjkmn","factoryTime":"1900-01-01 00:00:00","factoryCode":""}]
         * evaluationList : [{"id":48,"hospitalGuid":"a170a487-2439-4657-b107-9ae203851eb1","hospitalId":1,"processId":143,"businessNumber":"WX201802030","engineerName":"曾荣","engineerId":3,"maintenanceType":"院内维修","serviceEvaluation":5,"evaluationComment":"不过v不不不","departmentId":5,"attachmentId":"","applyId":233,"acceptanceStatus":"验收通过"},{"id":49,"hospitalGuid":"a170a487-2439-4657-b107-9ae203851eb1","hospitalId":1,"processId":144,"businessNumber":"WX201802030","engineerName":"1212","engineerId":1,"maintenanceType":"院外维修","serviceEvaluation":5,"evaluationComment":"不过v不不不","departmentId":5,"attachmentId":"","applyId":233,"acceptanceStatus":"验收通过"}]
         */

        private int id;
        private String gid;
        private String businessNumber;
        private String name;
        private String specification;
        private String model;
        private int worth;
        private String responsible;
        private String manufacturer;
        private String departmentName;
        private String repairTime;
        private String repairUser;
        private String attachmentId;
        private String faultDescription;
        private String acceptanceUser;
        private String completionDate;
        private String maintenanceStage;
        private String maintenanceType;
        private String acceptanceStatus;
        private String acceptanceComments;
        private List<String> attachmentUrl;
        private List<HospitalProcessListBean> hospitalProcessList;
        private List<OutHospitalProcessListBean> outHospitalProcessList;
        private List<RepairFeeListBean> repairFeeList;
        private List<ReplacingFittingListBean> replacingFittingList;
        private List<EvaluationListBean> evaluationList;

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

        public int getWorth() {
            return worth;
        }

        public void setWorth(int worth) {
            this.worth = worth;
        }

        public String getResponsible() {
            return responsible;
        }

        public void setResponsible(String responsible) {
            this.responsible = responsible;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public String getRepairTime() {
            return repairTime;
        }

        public void setRepairTime(String repairTime) {
            this.repairTime = repairTime;
        }

        public String getRepairUser() {
            return repairUser;
        }

        public void setRepairUser(String repairUser) {
            this.repairUser = repairUser;
        }

        public String getAttachmentId() {
            return attachmentId;
        }

        public void setAttachmentId(String attachmentId) {
            this.attachmentId = attachmentId;
        }

        public String getFaultDescription() {
            return faultDescription;
        }

        public void setFaultDescription(String faultDescription) {
            this.faultDescription = faultDescription;
        }

        public String getAcceptanceUser() {
            return acceptanceUser;
        }

        public void setAcceptanceUser(String acceptanceUser) {
            this.acceptanceUser = acceptanceUser;
        }

        public String getCompletionDate() {
            return completionDate;
        }

        public void setCompletionDate(String completionDate) {
            this.completionDate = completionDate;
        }

        public String getMaintenanceStage() {
            return maintenanceStage;
        }

        public void setMaintenanceStage(String maintenanceStage) {
            this.maintenanceStage = maintenanceStage;
        }

        public String getMaintenanceType() {
            return maintenanceType;
        }

        public void setMaintenanceType(String maintenanceType) {
            this.maintenanceType = maintenanceType;
        }

        public String getAcceptanceStatus() {
            return acceptanceStatus;
        }

        public void setAcceptanceStatus(String acceptanceStatus) {
            this.acceptanceStatus = acceptanceStatus;
        }

        public String getAcceptanceComments() {
            return acceptanceComments;
        }

        public void setAcceptanceComments(String acceptanceComments) {
            this.acceptanceComments = acceptanceComments;
        }

        public List<String> getAttachmentUrl() {
            return attachmentUrl;
        }

        public void setAttachmentUrl(List<String> attachmentUrl) {
            this.attachmentUrl = attachmentUrl;
        }

        public List<HospitalProcessListBean> getHospitalProcessList() {
            return hospitalProcessList;
        }

        public void setHospitalProcessList(List<HospitalProcessListBean> hospitalProcessList) {
            this.hospitalProcessList = hospitalProcessList;
        }

        public List<OutHospitalProcessListBean> getOutHospitalProcessList() {
            return outHospitalProcessList;
        }

        public void setOutHospitalProcessList(List<OutHospitalProcessListBean> outHospitalProcessList) {
            this.outHospitalProcessList = outHospitalProcessList;
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

        public List<EvaluationListBean> getEvaluationList() {
            return evaluationList;
        }

        public void setEvaluationList(List<EvaluationListBean> evaluationList) {
            this.evaluationList = evaluationList;
        }

        public static class HospitalProcessListBean {
            /**
             * id : 143
             * gid : e5f95d31-831a-4359-8cfd-28d64ae44ce8
             * maintenanceLevel : 10
             * maintenanceLevelName : 大修
             * faultAnalysisId : 1
             * faultAnalysisName : 设备操作不当
             * testUser : vgg
             * processTime : 2018-02-28 17:50:00
             * maintenanceStartDate : 2018-02-28 17:49:35
             * maintenanceEndDate : 2018-02-28 17:50:00
             * manHour : 1.0
             * longShutdown : 1.0
             * attachmentId :
             * attachmentUrl : null
             * maintenanceSituation : cgjhjjjh
             */

            private int id;
            private String gid;
            private int maintenanceLevel;
            private String maintenanceLevelName;
            private int faultAnalysisId;
            private String faultAnalysisName;
            private String testUser;
            private String processTime;
            private String maintenanceStartDate;
            private String maintenanceEndDate;
            private double manHour;
            private double longShutdown;
            private String attachmentId;
            private Object attachmentUrl;
            private String maintenanceSituation;

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

            public int getMaintenanceLevel() {
                return maintenanceLevel;
            }

            public void setMaintenanceLevel(int maintenanceLevel) {
                this.maintenanceLevel = maintenanceLevel;
            }

            public String getMaintenanceLevelName() {
                return maintenanceLevelName;
            }

            public void setMaintenanceLevelName(String maintenanceLevelName) {
                this.maintenanceLevelName = maintenanceLevelName;
            }

            public int getFaultAnalysisId() {
                return faultAnalysisId;
            }

            public void setFaultAnalysisId(int faultAnalysisId) {
                this.faultAnalysisId = faultAnalysisId;
            }

            public String getFaultAnalysisName() {
                return faultAnalysisName;
            }

            public void setFaultAnalysisName(String faultAnalysisName) {
                this.faultAnalysisName = faultAnalysisName;
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

            public double getManHour() {
                return manHour;
            }

            public void setManHour(double manHour) {
                this.manHour = manHour;
            }

            public double getLongShutdown() {
                return longShutdown;
            }

            public void setLongShutdown(double longShutdown) {
                this.longShutdown = longShutdown;
            }

            public String getAttachmentId() {
                return attachmentId;
            }

            public void setAttachmentId(String attachmentId) {
                this.attachmentId = attachmentId;
            }

            public Object getAttachmentUrl() {
                return attachmentUrl;
            }

            public void setAttachmentUrl(Object attachmentUrl) {
                this.attachmentUrl = attachmentUrl;
            }

            public String getMaintenanceSituation() {
                return maintenanceSituation;
            }

            public void setMaintenanceSituation(String maintenanceSituation) {
                this.maintenanceSituation = maintenanceSituation;
            }
        }

        public static class OutHospitalProcessListBean {
            /**
             * id : 144
             * gid : 2a412704-4372-4517-b1c7-fb20411026cf
             * maintenanceLevel : 10
             * maintenanceLevelName : 大修
             * faultAnalysisId : 2
             * faultAnalysisName : 电源故障
             * testUser : vbjj
             * processTime : 2018-02-28 17:53:00
             * maintenanceStartDate : 2018-02-28 17:52:58
             * maintenanceEndDate : 2018-02-28 17:53:00
             * manHour : 1.0
             * longShutdown : 1.0
             * attachmentId :
             * attachmentUrl : null
             * maintenanceSituation : chjjjjj
             */

            private int id;
            private String gid;
            private int maintenanceLevel;
            private String maintenanceLevelName;
            private int faultAnalysisId;
            private String faultAnalysisName;
            private String testUser;
            private String processTime;
            private String maintenanceStartDate;
            private String maintenanceEndDate;
            private double manHour;
            private double longShutdown;
            private String attachmentId;
            private Object attachmentUrl;
            private String maintenanceSituation;

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

            public int getMaintenanceLevel() {
                return maintenanceLevel;
            }

            public void setMaintenanceLevel(int maintenanceLevel) {
                this.maintenanceLevel = maintenanceLevel;
            }

            public String getMaintenanceLevelName() {
                return maintenanceLevelName;
            }

            public void setMaintenanceLevelName(String maintenanceLevelName) {
                this.maintenanceLevelName = maintenanceLevelName;
            }

            public int getFaultAnalysisId() {
                return faultAnalysisId;
            }

            public void setFaultAnalysisId(int faultAnalysisId) {
                this.faultAnalysisId = faultAnalysisId;
            }

            public String getFaultAnalysisName() {
                return faultAnalysisName;
            }

            public void setFaultAnalysisName(String faultAnalysisName) {
                this.faultAnalysisName = faultAnalysisName;
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

            public double getManHour() {
                return manHour;
            }

            public void setManHour(double manHour) {
                this.manHour = manHour;
            }

            public double getLongShutdown() {
                return longShutdown;
            }

            public void setLongShutdown(double longShutdown) {
                this.longShutdown = longShutdown;
            }

            public String getAttachmentId() {
                return attachmentId;
            }

            public void setAttachmentId(String attachmentId) {
                this.attachmentId = attachmentId;
            }

            public Object getAttachmentUrl() {
                return attachmentUrl;
            }

            public void setAttachmentUrl(Object attachmentUrl) {
                this.attachmentUrl = attachmentUrl;
            }

            public String getMaintenanceSituation() {
                return maintenanceSituation;
            }

            public void setMaintenanceSituation(String maintenanceSituation) {
                this.maintenanceSituation = maintenanceSituation;
            }
        }

        public static class RepairFeeListBean {
            /**
             * id : 72
             * feeType : 院外维修费用
             * fundSource : 资金来源
             * feeName : 费用名称
             * quotePrice : 52.0
             * actualPrice : 69.0
             * attachmentId :
             * attachmentUrl : null
             * remark : vgjj
             */

            private int id;
            private String feeType;
            private String fundSource;
            private String feeName;
            private double quotePrice;
            private double actualPrice;
            private String attachmentId;
            private Object attachmentUrl;
            private String remark;

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

            public double getQuotePrice() {
                return quotePrice;
            }

            public void setQuotePrice(double quotePrice) {
                this.quotePrice = quotePrice;
            }

            public double getActualPrice() {
                return actualPrice;
            }

            public void setActualPrice(double actualPrice) {
                this.actualPrice = actualPrice;
            }

            public String getAttachmentId() {
                return attachmentId;
            }

            public void setAttachmentId(String attachmentId) {
                this.attachmentId = attachmentId;
            }

            public Object getAttachmentUrl() {
                return attachmentUrl;
            }

            public void setAttachmentUrl(Object attachmentUrl) {
                this.attachmentUrl = attachmentUrl;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }

        public static class ReplacingFittingListBean {
            /**
             * id : 63
             * name : fhjj
             * quantity : 1.0
             * prict : 2.0
             * price : 2.0
             * unitId : 392
             * unitName : 盒
             * specification : hhj
             * model : ggj
             * placeOrigin : 22
             * placeOriginName : 成都
             * brand : fhj
             * manufacturerId : 0
             * manufacturer : fhj
             * supplierId : 0
             * supplierName : ghj
             * serialNumber : bhjkmn
             * factoryTime : 1900-01-01 00:00:00
             * factoryCode :
             */

            private int id;
            private String name;
            private double quantity;
            private double prict;
            private double price;
            private int unitId;
            private String unitName;
            private String specification;
            private String model;
            private int placeOrigin;
            private String placeOriginName;
            private String brand;
            private int manufacturerId;
            private String manufacturer;
            private int supplierId;
            private String supplierName;
            private String serialNumber;
            private String factoryTime;
            private String factoryCode;

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

            public double getQuantity() {
                return quantity;
            }

            public void setQuantity(double quantity) {
                this.quantity = quantity;
            }

            public double getPrict() {
                return prict;
            }

            public void setPrict(double prict) {
                this.prict = prict;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public int getUnitId() {
                return unitId;
            }

            public void setUnitId(int unitId) {
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

            public int getPlaceOrigin() {
                return placeOrigin;
            }

            public void setPlaceOrigin(int placeOrigin) {
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

        public static class EvaluationListBean {
            /**
             * id : 48
             * hospitalGuid : a170a487-2439-4657-b107-9ae203851eb1
             * hospitalId : 1
             * processId : 143
             * businessNumber : WX201802030
             * engineerName : 曾荣
             * engineerId : 3
             * maintenanceType : 院内维修
             * serviceEvaluation : 5
             * evaluationComment : 不过v不不不
             * departmentId : 5
             * attachmentId :
             * applyId : 233
             * acceptanceStatus : 验收通过
             */

            private int id;
            private String hospitalGuid;
            private int hospitalId;
            private int processId;
            private String businessNumber;
            private String engineerName;
            private int engineerId;
            private String maintenanceType;
            private int serviceEvaluation;
            private String evaluationComment;
            private int departmentId;
            private String attachmentId;
            private int applyId;
            private String acceptanceStatus;

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

            public int getProcessId() {
                return processId;
            }

            public void setProcessId(int processId) {
                this.processId = processId;
            }

            public String getBusinessNumber() {
                return businessNumber;
            }

            public void setBusinessNumber(String businessNumber) {
                this.businessNumber = businessNumber;
            }

            public String getEngineerName() {
                return engineerName;
            }

            public void setEngineerName(String engineerName) {
                this.engineerName = engineerName;
            }

            public int getEngineerId() {
                return engineerId;
            }

            public void setEngineerId(int engineerId) {
                this.engineerId = engineerId;
            }

            public String getMaintenanceType() {
                return maintenanceType;
            }

            public void setMaintenanceType(String maintenanceType) {
                this.maintenanceType = maintenanceType;
            }

            public int getServiceEvaluation() {
                return serviceEvaluation;
            }

            public void setServiceEvaluation(int serviceEvaluation) {
                this.serviceEvaluation = serviceEvaluation;
            }

            public String getEvaluationComment() {
                return evaluationComment;
            }

            public void setEvaluationComment(String evaluationComment) {
                this.evaluationComment = evaluationComment;
            }

            public int getDepartmentId() {
                return departmentId;
            }

            public void setDepartmentId(int departmentId) {
                this.departmentId = departmentId;
            }

            public String getAttachmentId() {
                return attachmentId;
            }

            public void setAttachmentId(String attachmentId) {
                this.attachmentId = attachmentId;
            }

            public int getApplyId() {
                return applyId;
            }

            public void setApplyId(int applyId) {
                this.applyId = applyId;
            }

            public String getAcceptanceStatus() {
                return acceptanceStatus;
            }

            public void setAcceptanceStatus(String acceptanceStatus) {
                this.acceptanceStatus = acceptanceStatus;
            }
        }
    }
}
