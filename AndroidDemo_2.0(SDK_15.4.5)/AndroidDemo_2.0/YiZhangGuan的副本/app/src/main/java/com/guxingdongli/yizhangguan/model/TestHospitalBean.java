package com.guxingdongli.yizhangguan.model;

import java.util.List;

/**
 * Created by jackmask on 2018/3/2.
 */

public class TestHospitalBean {
    private String con;
    private boolean type;
    private List<String> dataList;

    public List<String> getDataList() {
        return dataList;
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }
}
