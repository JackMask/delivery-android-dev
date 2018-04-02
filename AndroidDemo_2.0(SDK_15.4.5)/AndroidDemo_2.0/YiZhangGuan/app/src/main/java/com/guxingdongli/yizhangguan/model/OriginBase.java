package com.guxingdongli.yizhangguan.model;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/21
 */

public class OriginBase {

    /**
     * success : true
     * tipCode : 0
     * msg : 操作成功！
     * other :
     * data : [{"id":21,"gid":"5180b94b-5a50-464d-99f8-6daf554a6874","dicName":"产地","dicValue":"四川绵阳"},{"id":22,"gid":"423f9cb4-d1a6-49f0-bc5d-d160768c2166","dicName":"产地","dicValue":"成都"},{"id":23,"gid":"ec472511-a42f-48dd-a29d-0123efdf2b33","dicName":"产地","dicValue":"国产"},{"id":24,"gid":"37f12de1-66c4-477b-bef0-7590b4bb30ca","dicName":"产地","dicValue":"合肥"},{"id":25,"gid":"dc4d05df-9bbd-4e0c-a28e-21e55ed61d10","dicName":"产地","dicValue":"德国"},{"id":26,"gid":"966475d4-8ebd-491c-87ef-ff1a17b712bf","dicName":"产地","dicValue":"自定义"},{"id":414,"gid":"c59b09c8-a6b8-4571-b92f-b209ef69c431","dicName":"产地","dicValue":"淡淡的"},{"id":415,"gid":"13e9a627-542e-4203-8169-974488181850","dicName":"产地","dicValue":"很过分"},{"id":421,"gid":"425d17b1-f09f-4544-b9cf-f9a68738fd40","dicName":"产地","dicValue":"刚发的"},{"id":429,"gid":"d7dd064c-cc8d-4e69-ad68-9dc9089fb28c","dicName":"产地","dicValue":"美国"},{"id":433,"gid":"53d315cb-a833-4d79-98f7-c83870812f29","dicName":"产地","dicValue":"上海"}]
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
         * id : 21
         * gid : 5180b94b-5a50-464d-99f8-6daf554a6874
         * dicName : 产地
         * dicValue : 四川绵阳
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
