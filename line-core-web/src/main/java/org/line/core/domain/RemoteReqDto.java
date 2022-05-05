package org.line.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: yangcs
 * @Date: 2020/3/27 14:04
 * @Description: 远程请求封装对象
 */
@AllArgsConstructor
@Data
public class RemoteReqDto<T> {
    //请求相应识别信息
    private IdCardMsgDto idCard;
    private T data;
}
