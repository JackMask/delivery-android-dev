package com.guxingdongli.yizhangguan.model.passvalue;

import java.io.Serializable;

/**
 * @author 余先德
 * @data 2018/3/26
 */

public class ScanCodeInput implements Serializable{
    private int type;
    private String guid;
    private String single_or_numbering;

    @Override
    public String toString() {
        return "ScanCodeInput{" +
                "type=" + type +
                ", guid='" + guid + '\'' +
                ", single_or_numbering='" + single_or_numbering + '\'' +
                '}';
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getSingle_or_numbering() {
        return single_or_numbering;
    }

    public void setSingle_or_numbering(String single_or_numbering) {
        this.single_or_numbering = single_or_numbering;
    }
}
