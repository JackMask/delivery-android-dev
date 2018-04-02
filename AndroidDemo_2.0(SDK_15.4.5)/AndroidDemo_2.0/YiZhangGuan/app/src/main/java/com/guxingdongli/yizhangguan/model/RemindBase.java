package com.guxingdongli.yizhangguan.model;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/15
 */

public class RemindBase extends ReturnBase {

    private List<DataBase> data;

    public List<DataBase> getData() {
        return data;
    }

    public void setData(List<DataBase> data) {
        this.data = data;
    }

    public class DataBase{
        private String name;
        private int quantity;

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


    }
}
