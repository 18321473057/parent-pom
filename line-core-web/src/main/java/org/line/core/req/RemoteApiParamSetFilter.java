package org.line.core.req;


import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.line.core.domain.IdCardMsgDto;
import org.line.core.domain.RemoteReqDto;
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
        //包装request，以便可以多次读取其中内容
        HttpServletRequest requestToUse = request;
        if (!(request instanceof ContentReReadableHttpRequestWrapper)) {
            requestToUse = new ContentReReadableHttpRequestWrapper(request);
        }
        String messageId = null;
        Long timestamp = null;
        //组装鉴权信息对象
        if (HttpMethod.GET.toString().equals(requestToUse.getMethod())) {
            messageId = requestToUse.getParameter("messageId");
            timestamp = Long.parseLong(StringUtils.isEmpty(requestToUse.getParameter("timestamp")) ? "0" : requestToUse.getParameter("timestamp"));
        } else if (HttpMethod.POST.toString().equals(requestToUse.getMethod())) {
            byte[] content = StreamUtils.copyToByteArray(requestToUse.getInputStream());
            //远程请求携带身份信息
            RemoteReqDto requestDto = JSON.parseObject(new String(content), RemoteReqDto.class);
            IdCardMsgDto idCard = requestDto.getIdCard();
            if (null != idCard) {
                messageId = idCard.getMessageId();
                timestamp = idCard.getTimestamp();
            }

            ((ContentReReadableHttpRequestWrapper) requestToUse).reWriteContentIntoInputStream(content);
        } else {
            logger.error("httpMethod=[{}],requestUri=[{}],只支持get.post请求方式", requestToUse.getMethod(), requestToUse.getRequestURI());
        }
        requestToUse.setAttribute(RemoteReqConstants.MESSAGE_ID, messageId);
        requestToUse.setAttribute(RemoteReqConstants.TIMESTAMP, timestamp);
        filterChain.doFilter(requestToUse, response);
    }


}
