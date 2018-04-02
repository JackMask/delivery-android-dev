package com.guxingdongli.yizhangguan.model;

import java.util.List;

/**
 * Created by jackmask on 2018/3/14.
 */

public class SelectAddressModel extends ReturnBase {

   private List<SelectAddressBase> data;

    public List<SelectAddressBase> getData() {
        return data;
    }

    public void setData(List<SelectAddressBase> data) {
        this.data = data;
    }
}
