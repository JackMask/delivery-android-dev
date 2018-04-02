package com.yuxiaolong.yuxiande;

import java.util.List;

/**
 * Created by jackmask on 2017/9/19.
 */

public class AboutUsVo {

    /**
     * msg : 关于我们
     * result : [{"content":"<p>你好，我是地铁维保系统<\/p>","id":"1","title":"关于我们"},{"content":"13555555555","id":"2","title":"联系我们"},{"content":"2","id":"3","title":"迟到时间"}]
     * state : success
     * status : 1
     */

    private String msg;
    private String state;
    private int status;
    private List<ResultBean> result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * content : <p>你好，我是地铁维保系统</p>
         * id : 1
         * title : 关于我们
         */

        private String content;
        private String id;
        private String title;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
