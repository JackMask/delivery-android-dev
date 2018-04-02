package com.guxingdongli.yizhangguan.model;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/24
 */

    public class PushBase {


    /**
     * success : true
     * tipCode : 0
     * msg : 操作成功！
     * other :
     * data : [{"hospitalGuid":[],"name":"全自动尿有形成分分析仪","manufacturerName":"日本ARKRAY","registrationNumber":"国食药监械【2003】333号","price":0,"listMany":[{"id":27,"model":"10PA","specification":"10PA","price":280},{"id":28,"model":"5*1L/箱","specification":"5*1L/箱","price":3000}]}]
     */

    private boolean success;
    private String tipCode;
    private String msg;
    private String other;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * hospitalGuid : []
         * name : 全自动尿有形成分分析仪
         * manufacturerName : 日本ARKRAY
         * registrationNumber : 国食药监械【2003】333号
         * price : 0.0
         * listMany : [{"id":27,"model":"10PA","specification":"10PA","price":280},{"id":28,"model":"5*1L/箱","specification":"5*1L/箱","price":3000}]
         */

        private String name;
        private String manufacturerName;
        private String registrationNumber;
        private double price;
        private boolean type;
        private List<?> hospitalGuid;
        private List<ListManyBean> listMany;

        public boolean isType() {
            return type;
        }

        public void setType(boolean type) {
            this.type = type;
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

        public List<?> getHospitalGuid() {
            return hospitalGuid;
        }

        public void setHospitalGuid(List<?> hospitalGuid) {
            this.hospitalGuid = hospitalGuid;
        }

        public List<ListManyBean> getListMany() {
            return listMany;
        }

        public void setListMany(List<ListManyBean> listMany) {
            this.listMany = listMany;
        }

        public static class ListManyBean {
            /**
             * id : 27
             * model : 10PA
             * specification : 10PA
             * price : 280.0
             */

            private int id;
            private String model;
            private String specification;
            private String price;

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

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }
}
