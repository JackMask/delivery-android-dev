package com.guxingdongli.yizhangguan.model;

/**
 * @author 余先德
 * @data 2018/3/30
 */

public class ServiceAcceptanceInput {


    /**
     * id : 0
     * hospitalGuid : string
     * hospitalId : 0
     * processId : 0
     * businessNumber : string
     * engineerName : string
     * engineerId : 0
     * maintenanceType : 院内维修
     * serviceEvaluation : 0
     * evaluationComment : string
     * departmentId : 0
     * attachmentId : string
     * applyId : 0
     * acceptanceStatus : 未验收
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
