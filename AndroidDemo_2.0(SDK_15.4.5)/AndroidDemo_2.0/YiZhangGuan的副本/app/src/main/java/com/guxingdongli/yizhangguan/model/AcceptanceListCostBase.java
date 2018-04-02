package com.guxingdongli.yizhangguan.model;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/22
 */

public class AcceptanceListCostBase {


    /**
     * success : true
     * tipCode : 0
     * msg : 操作成功！
     * other :
     * data : [{"id":30,"gid":"7caab676-281c-4a1a-b29b-d6c1c7ce76ab","feeType":"院内维修费用","fundSource":"资金来源","feeName":"费用名称","quotePrice":88,"actualPrice":99},{"id":31,"gid":"c82172b6-9b22-438f-99fe-d747231a26bf","feeType":"院外维修费用","fundSource":"资金来源","feeName":"费用名称","quotePrice":85,"actualPrice":25}]
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
         * id : 30
         * gid : 7caab676-281c-4a1a-b29b-d6c1c7ce76ab
         * feeType : 院内维修费用
         * fundSource : 资金来源
         * feeName : 费用名称
         * quotePrice : 88.0
         * actualPrice : 99.0
         */

        private int id;
        private String gid;
        private String feeType;
        private String fundSource;
        private String feeName;
        private double quotePrice;
        private double actualPrice;

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

        public String getFeeType() {
            return feeType;
        }

        public void setFeeType(String feeType) {
            this.feeType = feeType;
        }

        public String getFundSource() {
            return fundSource;
        }

        public void setFundSource(String fundSource) {
            this.fundSource = fundSource;
        }

        public String getFeeName() {
            return feeName;
        }

        public void setFeeName(String feeName) {
            this.feeName = feeName;
        }

        public double getQuotePrice() {
            return quotePrice;
        }

        public void setQuotePrice(double quotePrice) {
            this.quotePrice = quotePrice;
        }

        public double getActualPrice() {
            return actualPrice;
        }

        public void setActualPrice(double actualPrice) {
            this.actualPrice = actualPrice;
        }
    }
}
