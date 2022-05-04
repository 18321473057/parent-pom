package org.line.core.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.line.core.domain.IdCardMsgDto;
import org.line.core.domain.RemoteResponseDto;

import java.util.List;

/**
 * @Author: yangcs
 * @Date: 2020/4/14 15:43
 * @Description: 自定义远程请求异常;
 */
@Data
@AllArgsConstructor
public class RemoteBusinessException extends RuntimeException {
    //请求相应识别信息
    private IdCardMsgDto idCardMsgDto;
    private String code;
    private List<RemoteResponseDto.RemoteErrorItemDto> errorList;
}

