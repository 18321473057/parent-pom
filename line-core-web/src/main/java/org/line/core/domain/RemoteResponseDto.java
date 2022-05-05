package org.line.core.domain;

import org.apache.commons.lang3.StringUtils;
import org.line.core.exception.RemoteBusinessException;
import org.springframework.util.CollectionUtils;

/**
 * @Author: yangcs
 * @Date: 2020/3/27 14:04
 * @Description: 远程请求返回对象
 */
public class RemoteResponseDto<T> extends BasicResponse {
    //请求相应识别信息
    private IdCardMsgDto  idCard;
    //返回对象
    private T result;


    public static <T> RemoteResponseDto<T> success(IdCardMsgDto idCardMsgDto, T result) {
        RemoteResponseDto response = getScuuess(RemoteResponseDto.class);
        response.setIdCard(idCardMsgDto);
        response.result = result;
        return response;
    }

    public static <T> RemoteResponseDto<T> error(RemoteBusinessException rException) {
        //code和msg覆盖; 没有就默认 500 /系统异常
        RemoteResponseDto response = getError(RemoteResponseDto.class);
        if(null == rException.getIdCardMsgDto()){
            response.setIdCard(rException.getIdCardMsgDto());
        }
        if (StringUtils.isNotEmpty(rException.getCode())) {
            response.setCode(rException.getCode());
        }
        if (StringUtils.isNotEmpty(rException.getMessage())) {
            response.setMsg(rException.getMessage());
        }
        if (!CollectionUtils.isEmpty(rException.getErrorList())) {
            response.result = rException.getErrorList();
        }
        return response;
    }


    public static <T> RemoteResponseDto<T> error(IdCardMsgDto idCardMsgDto, RemoteBusinessException rException) {
        //code和msg覆盖; 没有就默认 500 /系统异常
        RemoteResponseDto response = getError(RemoteResponseDto.class);
        if (StringUtils.isNotEmpty(rException.getCode())) {
            response.setCode(rException.getCode());
        }
        if (StringUtils.isNotEmpty(rException.getMessage())) {
            response.setMsg(rException.getMessage());
        }
        if (!CollectionUtils.isEmpty(rException.getErrorList())) {
            response.result = rException.getErrorList();
        }
        response.setIdCard(idCardMsgDto);
        return response;
    }


    public IdCardMsgDto getIdCard() {
        return idCard;
    }

    public void setIdCard(IdCardMsgDto idCard) {
        this.idCard = idCard;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public class RemoteErrorItemDto {

        private String code;
        private String msg;


        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

}
