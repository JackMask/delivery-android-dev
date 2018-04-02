package com.guxingdongli.yizhangguan.model.passvalue;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/24
 */

public class PushValue {
    private List<String> hospitalGuid;
    private String name;
    private String manufacturerName;
    private String registrationNumber;
    private double price;
    private List<listManyBase> listMany;

    public List<String> getHospitalGuid() {
        return hospitalGuid;
    }

    public void setHospitalGuid(List<String> hospitalGuid) {
        this.hospitalGuid = hospitalGuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<listManyBase> getListMany() {
        return listMany;
    }

    public void setListMany(List<listManyBase> listMany) {
        this.listMany = listMany;
    }

    public static class listManyBase{
        private int id;
        private String model;
        private String specification;
        private double price;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getSpecification() {
            return specification;
        }

        public void setSpecification(String specification) {
            this.specification = specification;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }

}
