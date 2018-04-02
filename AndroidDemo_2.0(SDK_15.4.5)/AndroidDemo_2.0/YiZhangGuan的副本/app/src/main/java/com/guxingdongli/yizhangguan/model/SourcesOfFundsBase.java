package com.guxingdongli.yizhangguan.model;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/21
 */

public class SourcesOfFundsBase {

    /**
     * success : true
     * tipCode : 0
     * msg : 操作成功！
     * other :
     * data : [{"id":20,"gid":"8b84eb9c-f944-422b-bb18-05cfbbbe8c66","dicName":"资金来源","dicValue":"资金来源"},{"id":36,"gid":"47ec886c-156f-41d5-a023-f8ba304b7aa9","dicName":"资金来源","dicValue":"rrqwe"},{"id":37,"gid":"e58721a8-fdca-47cb-8976-005af1a316cc","dicName":"资金来源","dicValue":"ccc"},{"id":402,"gid":"988eee34-fc8c-4552-a881-8d62930b209d","dicName":"资金来源","dicValue":"设备费"},{"id":404,"gid":"85ae69e8-21ec-42af-8d9d-41e6c2405a76","dicName":"资金来源","dicValue":"rwdf"},{"id":411,"gid":"e108b599-0490-40d2-9e95-beaea2c63755","dicName":"资金来源","dicValue":"test2"},{"id":413,"gid":"249bff94-ce4d-4ca0-b177-a6da0daa4f10","dicName":"资金来源","dicValue":"抓紧了"},{"id":418,"gid":"c9c3846a-54d1-436e-b245-5f0f0e9b636d","dicName":"资金来源","dicValue":"哦哦"},{"id":420,"gid":"62821e5b-bc52-4737-9d3c-1d4de45efac8","dicName":"资金来源","dicValue":"测定"},{"id":423,"gid":"97b31375-777f-4b37-b1ed-ff474e32abfd","dicName":"资金来源","dicValue":"家具城"},{"id":425,"gid":"358ddffe-6f54-4a50-bd0d-40a756e7b7d4","dicName":"资金来源","dicValue":"反反复复"},{"id":430,"gid":"abf98b54-fbeb-4c97-8cfc-dceb1b7f680e","dicName":"资金来源","dicValue":"221"}]
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
         * id : 20
         * gid : 8b84eb9c-f944-422b-bb18-05cfbbbe8c66
         * dicName : 资金来源
         * dicValue : 资金来源
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
