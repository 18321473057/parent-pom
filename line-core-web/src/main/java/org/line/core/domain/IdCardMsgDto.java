package org.line.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.CollectionUtils;

/**
 * @Author: yangcs
 * @Date: 2020/3/27 14:04
 * @Description: 请求/返回 身份识别类
 */
@Data
@AllArgsConstructor
public class IdCardMsgDto {
    //请求消息ID
    private String messageId;
    //时间戳
    private long timestamp;
}
