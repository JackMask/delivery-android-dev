package com.guxingdongli.yizhangguan.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/23
 */

public class DasisDataHospitalBase implements Serializable{


    /**
     * success : true
     * tipCode : 0
     * msg : 操作成功！
     * other :
     * data : {"data":[{"id":20,"gid":"fb66f29f-1c1b-4830-8d12-1460b73dae31","name":"凝聚胺介质试剂","hisCode":"","price":281.4,"hospitalName":"金堂县第一人民医院","code":"684010-7-7-3"}],"total":20}
     */

    private boolean success;
    private String tipCode;
    private String msg;
    private String other;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX implements Serializable{
        /**
         * data : [{"id":20,"gid":"fb66f29f-1c1b-4830-8d12-1460b73dae31","name":"凝聚胺介质试剂","hisCode":"","price":281.4,"hospitalName":"金堂县第一人民医院","code":"684010-7-7-3"}]
         * total : 20
         */

        private int total;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean implements Serializable {
            /**
             * id : 20
             * gid : fb66f29f-1c1b-4830-8d12-1460b73dae31
             * name : 凝聚胺介质试剂
             * hisCode :
             * price : 281.4
             * hospitalName : 金堂县第一人民医院
             * code : 684010-7-7-3
             */

            private int id;
            private String gid;
            private String name;
            private String hisCode;
            private double price;
            private String hospitalName;
            private String code;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getGid() {
                return gid;
            }

            public void setGid(String gid) {
                this.gid = gid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getHisCode() {
                return hisCode;
            }

            public void setHisCode(String hisCode) {
                this.hisCode = hisCode;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getHospitalName() {
                return hospitalName;
            }

            public void setHospitalName(String hospitalName) {
                this.hospitalName = hospitalName;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }
        }
    }
}
