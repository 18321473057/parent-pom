package org.line.core.sentinel.domain;

import org.apache.commons.lang3.StringUtils;
import org.line.core.domain.BasicResponse;
import org.line.core.domain.IdCardMsgDto;
import org.line.core.exception.RemoteBusinessException;
import org.springframework.util.CollectionUtils;

/**
 * @Author: yangcs
 * @Date: 2020/3/27 14:04
 * @Description: sentinel 流控异常信息
 */
public class SentinelBlockDto extends BasicResponse {
    //请求相应识别信息
    private IdCardMsgDto  idCard;
    //抛出的异常
    private String exceptionClass;

    public SentinelBlockDto(IdCardMsgDto idCard, SentinelBlockEnum blockEnum) {
        this.idCard = idCard;
        success=false;
        if(blockEnum != null){
            this.exceptionClass = blockEnum.getExceptionClass();
            code = blockEnum.getCode();
            msg = blockEnum.getName();
        }
    }


    public SentinelBlockDto(IdCardMsgDto idCard) {
        this.idCard = idCard;
    }

    public String getExceptionClass() {
        return exceptionClass;
    }

    public void setExceptionClass(String exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    public IdCardMsgDto getIdCard() {
        return idCard;
    }

    public void setIdCard(IdCardMsgDto idCard) {
        this.idCard = idCard;
    }
}
