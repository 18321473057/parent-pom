package org.line.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: yangcs
 * @Date: 2020/3/27 14:04
 * @Description: 远程请求封装对象
 */
public class RemoteReqDto<T> {
    //请求相应识别信息
    private IdCardMsgDto idCard;
    private T data;


    public IdCardMsgDto getIdCard() {
        return idCard;
    }

    public void setIdCard(IdCardMsgDto idCard) {
        this.idCard = idCard;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RemoteReqDto{" +
                "idCard=" + idCard +
                ", data=" + data +
                '}';
    }
}
