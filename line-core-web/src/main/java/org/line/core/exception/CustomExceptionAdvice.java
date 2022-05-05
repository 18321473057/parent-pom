package org.line.core.exception;

import org.apache.commons.lang3.StringUtils;
import org.line.core.req.RemoteReqConstants;
import org.line.core.domain.AjaxResponseDto;
import org.line.core.domain.IdCardMsgDto;
import org.line.core.domain.RemoteResponseDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: yangcs
 * @Date: 2020/3/28 15:29
 * @Description: 自定义的异常处理controller  不是全局处理,只有继承的类抛出自定义的异常 才会处理
 */
@RestControllerAdvice
public class CustomExceptionAdvice {

    /**
     * 异常处理逻辑
     */
    @ExceptionHandler(Exception.class)
    public Object handlerException(Exception exception) throws Exception {
        if (exception instanceof BusinessException) {
            //本地业务异常,返回状态500,以及异常信息;
            BusinessException bException = (BusinessException) exception;
            return StringUtils.isEmpty(bException.getCode()) ? AjaxResponseDto.error(bException.getMessage()) : AjaxResponseDto.error(bException.getCode(), bException.getMessage());
        } else if (exception instanceof RemoteBusinessException) {
            //这里处理远程调用异常
            RemoteBusinessException rException = (RemoteBusinessException) exception;
            HttpServletRequest hRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            /*
             * 优先使用抛出的RemoteBusinessException 中的messageId和timestamp;
             * RemoteBusinessExceptionmessageId和timestamp不存在,再使用request中的;
             * 都没有则不设置messageId和timestamp;
             * */
            IdCardMsgDto idCardMsgDto = rException.getIdCardMsgDto();
            if (idCardMsgDto != null && !StringUtils.isEmpty(idCardMsgDto.getMessageId()) && idCardMsgDto.getTimestamp() != 0) {
                return RemoteResponseDto.error(rException);
            } else if (hRequest.getAttribute(RemoteReqConstants.MESSAGE_ID) != null && hRequest.getAttribute(RemoteReqConstants.TIMESTAMP) != null) {
                return RemoteResponseDto.error(new IdCardMsgDto((String) hRequest.getAttribute(RemoteReqConstants.MESSAGE_ID), (long) hRequest.getAttribute(RemoteReqConstants.TIMESTAMP)), rException);
            }
            return RemoteResponseDto.error(rException);
        } else {
            throw exception;
        }
    }
}
