package com.guxingdongli.yizhangguan.model;

import java.util.List;

/**
 * Created by jackmask on 2018/3/5.
 */

public class MyRepairOrderDetailsContentOtherBase {
    private String title;
    private List<DataBean> data;


    public MyRepairOrderDetailsContentOtherBase(String title, List<DataBean> data){
        this.title = title;
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String title;
        private String content;

        public DataBean(String title,String content){
            this.title = title;
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
