package com.guxingdongli.yizhangguan.model.passvalue;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/24
 */

public class AddStatementValue {


    /**
     * hospitalId : [0]
     * beginTime : 2018-03-22T15:16:44.824Z
     * endTime : 2018-03-22T15:16:44.824Z
     */

    private String beginTime;
    private String endTime;
    private List<Integer> hospitalId;

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<Integer> getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(List<Integer> hospitalId) {
        this.hospitalId = hospitalId;
    }
}
