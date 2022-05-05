package org.line.core.domain;

/**
 * @Author: yangcs
 * @Date: 2020/3/27 14:04
 * @Description: 请求/返回 身份识别类
 */
public class IdCardMsgDto {
    //请求消息ID
    private String messageId;
    //时间戳
    private long timestamp;


    public IdCardMsgDto() {
    }

    public IdCardMsgDto(String messageId, long timestamp) {
        this.messageId = messageId;
        this.timestamp = timestamp;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "IdCardMsgDto{" +
                "messageId='" + messageId + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
