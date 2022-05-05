package org.line.core.req;


import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.line.core.domain.IdCardMsgDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 1: 将远程api请求报文中的 messageId  timestamp 设置到req中
 */


public class RemoteApiParamSetFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(RemoteApiParamSetFilter.class);

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String messageId = null;
        Long timestamp = null;
        //组装鉴权信息对象
        if (HttpMethod.GET.toString().equals(request.getMethod())) {
            messageId = request.getParameter("messageId");
            timestamp = Long.parseLong(StringUtils.isEmpty(request.getParameter("timestamp")) ? "0" : request.getParameter("timestamp"));
        } else if (HttpMethod.POST.toString().equals(request.getMethod())) {
            String paramsString = new String(StreamUtils.copyToByteArray(request.getInputStream()));
            IdCardMsgDto requestDto = JSON.parseObject(paramsString, IdCardMsgDto.class);
        } else {
            logger.error("httpMethod=[{}],requestUri=[{}],只支持get.post请求方式", request.getMethod(), request.getRequestURI());
            return;
        }
        request.setAttribute(RemoteReqConstants.MESSAGE_ID, messageId);
        request.setAttribute(RemoteReqConstants.TIMESTAMP, timestamp);
        filterChain.doFilter(request, response);
    }


}
