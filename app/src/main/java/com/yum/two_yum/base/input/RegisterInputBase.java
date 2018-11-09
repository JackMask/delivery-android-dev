package com.yum.two_yum.base.input;

import com.yum.two_yum.base.BaseReturn;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/4/16
 */

public class RegisterInputBase extends BaseReturn {

    /**
     * data : {"fileRespResults":[{"url":"/resources/201805/29/eb1c4fdbce2241828bdf92f743cb7bb5.png"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<FileRespResultsBean> fileRespResults;

        public List<FileRespResultsBean> getFileRespResults() {
            return fileRespResults;
        }

        public void setFileRespResults(List<FileRespResultsBean> fileRespResults) {
            this.fileRespResults = fileRespResults;
        }

        public static class FileRespResultsBean {
            /**
             * url : /resources/201805/29/eb1c4fdbce2241828bdf92f743cb7bb5.png
             */

            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
