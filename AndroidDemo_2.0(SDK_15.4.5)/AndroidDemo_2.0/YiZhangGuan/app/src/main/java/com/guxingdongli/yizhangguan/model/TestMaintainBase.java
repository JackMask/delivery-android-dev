package com.guxingdongli.yizhangguan.model;

/**
 * 设备维修测试数据
 * Created by jackmask on 2018/3/10.
 */

public class TestMaintainBase {
    private String cost_name;
    private String cost_source;
    private String maintain_quotation;
    private String actual_quotation;

    public String getCost_name() {
        return cost_name;
    }

    public void setCost_name(String cost_name) {
        this.cost_name = cost_name;
    }

    public String getCost_source() {
        return cost_source;
    }

    public void setCost_source(String cost_source) {
        this.cost_source = cost_source;
    }

    public String getMaintain_quotation() {
        return maintain_quotation;
    }

    public void setMaintain_quotation(String maintain_quotation) {
        this.maintain_quotation = maintain_quotation;
    }

    public String getActual_quotation() {
        return actual_quotation;
    }

    public void setActual_quotation(String actual_quotation) {
        this.actual_quotation = actual_quotation;
    }
}
