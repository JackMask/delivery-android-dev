package com.guxingdongli.yizhangguan.model;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/15
 */

public class MessageBase extends ReturnBase {

    private MessageDataBase data;

    public MessageDataBase getData() {
        return data;
    }

    public void setData(MessageDataBase data) {
        this.data = data;
    }

    public class MessageDataBase{
        private List<dataBase> data;

        public List<dataBase> getData() {
            return data;
        }

        public void setData(List<dataBase> data) {
            this.data = data;
        }
        public class dataBase{
            private int id;
            private String gid;
            private String messageContent;
            private String msgUrl;
            private String readState;
            private String messageType;
            private int jumpType;
            private String sendTime;
            private boolean isShow;

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

            public String getMessageContent() {
                return messageContent;
            }

            public void setMessageContent(String messageContent) {
                this.messageContent = messageContent;
            }

            public String getMsgUrl() {
                return msgUrl;
            }

            public void setMsgUrl(String msgUrl) {
                this.msgUrl = msgUrl;
            }

            public String getReadState() {
                return readState;
            }

            public void setReadState(String readState) {
                this.readState = readState;
            }

            public String getMessageType() {
                return messageType;
            }

            public void setMessageType(String messageType) {
                this.messageType = messageType;
            }

            public int getJumpType() {
                return jumpType;
            }

            public void setJumpType(int jumpType) {
                this.jumpType = jumpType;
            }

            public String getSendTime() {
                return sendTime;
            }

            public void setSendTime(String sendTime) {
                this.sendTime = sendTime;
            }

            public boolean isShow() {
                return isShow;
            }

            public void setShow(boolean show) {
                isShow = show;
            }
        }
    }

}
