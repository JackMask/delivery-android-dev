package com.guxingdongli.yizhangguan.model;

import java.util.List;

/**
 * Created by jackmask on 2018/3/15.
 */

public class CityBase {
    private String name;
    private List<cityBase> city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<cityBase> getCity() {
        return city;
    }

    public void setCity(List<cityBase> city) {
        this.city = city;
    }

    public static class cityBase{
        private String name;
        private List<String> area;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getArea() {
            return area;
        }

        public void setArea(List<String> area) {
            this.area = area;
        }
    }
}
