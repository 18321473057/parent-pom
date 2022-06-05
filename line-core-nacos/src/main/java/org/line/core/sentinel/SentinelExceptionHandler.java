package org.line.core.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.fastjson.JSONObject;
import org.line.core.domain.IdCardMsgDto;
import org.line.core.req.RemoteReqConstants;
import org.line.core.sentinel.domain.SentinelBlockDto;
import org.line.core.sentinel.domain.SentinelBlockEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: yangcs
 * @Date: 2020/3/28 15:29
 * @Description: Sentinel 流控 等异常处理
 * 注意 :测试发现   /test/sentinel4  这种资源名才可以触发, 自定义的资源名不可以
 * 注意 :测试发现   /test/sentinel4  这种资源名才可以触发, 自定义的资源名不可以
 * 注意 :测试发现   /test/sentinel4  这种资源名才可以触发, 自定义的资源名不可以
 */


public class SentinelExceptionHandler implements BlockExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(SentinelExceptionHandler.class);

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        logger.info(">>>>>>>> Sentinel 触发流控,url =[{}],rule=[{}] ", httpServletRequest.getRequestURL().toString(), e.getRule());
        Object messageId = httpServletRequest.getAttribute(RemoteReqConstants.MESSAGE_ID);
        Object timestamp = httpServletRequest.getAttribute(RemoteReqConstants.TIMESTAMP);
        IdCardMsgDto idCardMsgDto = new IdCardMsgDto((String) (messageId == null ? "" : messageId), (long) (timestamp == null ? 0 : timestamp));

        SentinelBlockEnum sentinelBlockEnum = null;
        if (e instanceof FlowException) {
            //限流
            sentinelBlockEnum = SentinelBlockEnum.FLOW;
        } else if (e instanceof DegradeException) {
            //降级
            sentinelBlockEnum = SentinelBlockEnum.DEGRADE;
        } else if (e instanceof ParamFlowException) {
            //热点参数限流
            sentinelBlockEnum = SentinelBlockEnum.PARAM_FLOW;
        } else if (e instanceof SystemBlockException) {
            //触发系统保护规则
            sentinelBlockEnum = SentinelBlockEnum.SYSTEM_BLOCK;
        } else if (e instanceof AuthorityException) {
            //授权规则不通过
            sentinelBlockEnum = SentinelBlockEnum.AUTHORITY;
        }
        SentinelBlockDto sentinelBlockDto = new SentinelBlockDto(idCardMsgDto, sentinelBlockEnum);
        httpServletResponse.setStatus(500);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.getOutputStream().write(JSONObject.toJSON(sentinelBlockDto).toString().getBytes());
    }
}
