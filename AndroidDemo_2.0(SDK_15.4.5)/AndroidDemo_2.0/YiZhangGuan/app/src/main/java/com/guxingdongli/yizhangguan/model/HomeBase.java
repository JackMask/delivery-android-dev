package com.guxingdongli.yizhangguan.model;

import java.util.List;

/**
 * Created by jackmask on 2018/3/14.
 */

public class HomeBase extends ReturnBase {

    private dataModel data;

    public dataModel getData() {
        return data;
    }

    public void setData(dataModel data) {
        this.data = data;
    }

    public class dataModel {

        private int unAcceptOrder;
        private int myRepair;
        private String pushMessageGuid;
        private String  pushMessageTitle;
        private String pushMessageType;
        private String pushMessageUrl;
        private int myAudit;
        private int unHandleOrder;
        private int unHandleRepair;
        private int messageCount;
        private List<dataBase> appModules;

        public String getPushMessageGuid() {
            return pushMessageGuid;
        }

        public void setPushMessageGuid(String pushMessageGuid) {
            this.pushMessageGuid = pushMessageGuid;
        }

        public String getPushMessageTitle() {
            return pushMessageTitle;
        }

        public void setPushMessageTitle(String pushMessageTitle) {
            this.pushMessageTitle = pushMessageTitle;
        }

        public String getPushMessageType() {
            return pushMessageType;
        }

        public void setPushMessageType(String pushMessageType) {
            this.pushMessageType = pushMessageType;
        }

        public String getPushMessageUrl() {
            return pushMessageUrl;
        }

        public void setPushMessageUrl(String pushMessageUrl) {
            this.pushMessageUrl = pushMessageUrl;
        }

        public int getUnAcceptOrder() {
            return unAcceptOrder;
        }

        public void setUnAcceptOrder(int unAcceptOrder) {
            this.unAcceptOrder = unAcceptOrder;
        }

        public int getMyRepair() {
            return myRepair;
        }

        public void setMyRepair(int myRepair) {
            this.myRepair = myRepair;
        }

        public int getMyAudit() {
            return myAudit;
        }

        public void setMyAudit(int myAudit) {
            this.myAudit = myAudit;
        }

        public int getUnHandleOrder() {
            return unHandleOrder;
        }

        public void setUnHandleOrder(int unHandleOrder) {
            this.unHandleOrder = unHandleOrder;
        }

        public int getUnHandleRepair() {
            return unHandleRepair;
        }

        public void setUnHandleRepair(int unHandleRepair) {
            this.unHandleRepair = unHandleRepair;
        }

        public int getMessageCount() {
            return messageCount;
        }

        public void setMessageCount(int messageCount) {
            this.messageCount = messageCount;
        }

        public List<dataBase> getAppModules() {
            return appModules;
        }

        public void setAppModules(List<dataBase> appModules) {
            this.appModules = appModules;
        }

        public class dataBase {
            private String iconUrl;
            private String name;
            private int quantity;
            private String status;

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
