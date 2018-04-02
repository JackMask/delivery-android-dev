package com.guxingdongli.yizhangguan.model;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/21
 */

public class FeeNameBase {

    /**
     * success : true
     * tipCode : 0
     * msg : 操作成功！
     * other :
     * data : [{"id":19,"gid":"1f33338f-01b5-4bb6-8c79-29dc0b2c9301","dicName":"费用名称","dicValue":"费用名称"},{"id":28,"gid":"14b19e4a-0bac-433e-9314-9f8b2953a052","dicName":"费用名称","dicValue":"rrr"},{"id":40,"gid":"60a7b636-b850-495b-b9f2-f54756bf7257","dicName":"费用名称","dicValue":"testname"},{"id":401,"gid":"1b83da98-b3d6-4736-b1ad-9ed6b9046b71","dicName":"费用名称","dicValue":"配件费"},{"id":405,"gid":"b7e7da23-79a4-44ba-b393-de7c367be0d4","dicName":"费用名称","dicValue":"古古怪怪"},{"id":406,"gid":"0d33cd26-f039-4068-b680-4a5e0c2393a7","dicName":"费用名称","dicValue":"测试测试"},{"id":407,"gid":"6a9c856a-5641-4135-bc93-c51929650e46","dicName":"费用名称","dicValue":"cesff"},{"id":408,"gid":"d9af9d15-880d-4af6-87bc-8d1ab205edc0","dicName":"费用名称","dicValue":"121212"},{"id":409,"gid":"39dc2018-0bad-4b73-a267-245c81f40e3f","dicName":"费用名称","dicValue":"sdfsdf"},{"id":410,"gid":"070178c7-6453-42f7-a767-4351651808b8","dicName":"费用名称","dicValue":"test1"},{"id":412,"gid":"71e200d3-6333-480d-8e8d-dd4de21a5546","dicName":"费用名称","dicValue":"测试仪"},{"id":417,"gid":"de62a05c-9c36-4e81-a471-7667dd37b406","dicName":"费用名称","dicValue":"刚好"},{"id":424,"gid":"a9442de1-d93f-426d-b5c3-885ca6a96744","dicName":"费用名称","dicValue":"v吃蛋糕v"}]
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
         * id : 19
         * gid : 1f33338f-01b5-4bb6-8c79-29dc0b2c9301
         * dicName : 费用名称
         * dicValue : 费用名称
         */

        private int id;
        private String gid;
        private String dicName;
        private String dicValue;

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

        public String getDicName() {
            return dicName;
        }

        public void setDicName(String dicName) {
            this.dicName = dicName;
        }

        public String getDicValue() {
            return dicValue;
        }

        public void setDicValue(String dicValue) {
            this.dicValue = dicValue;
        }
    }
}
