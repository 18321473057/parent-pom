package org.line.core.resp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.line.core.domain.*;
import org.line.core.exception.BusinessException;
import org.line.core.req.RemoteReqConstants;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ycs
 * @Description 方法上必须包含@ResponseBody 注解才会走这个类
 * 将 AjaxResponse.class; PageResponse.class; RemoteResponse.class; 标注的方法 返回数据包装
 */
@RestControllerAdvice
public class ResponseResultBodyAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 处理注解
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        //已经被包装,或者直接返回包装类型的 就不在处理了防止重复包裹的问题出现
//        if (body instanceof BasicResponse || body instanceof AjaxResponseDto || body instanceof PageResponseDto || body instanceof RemoteResponseDto) {
//            return body;
//        }

        //已经被包装,或者直接返回包装类型的 就不在处理了防止重复包裹的问题出现
        if (body instanceof BasicResponse) {
            return body;
        }

        //处理AjaxResponse 注解
        if (returnType.hasMethodAnnotation(AJAX_ANNOTATION_TYPE)) {
            return this.disposeAjaxAnnotation(body, returnType);
        }
        //处理 PageResponse 注解
        if (returnType.hasMethodAnnotation(PAGE_ANNOTATION_TYPE)) {
            return this.disposePageAnnotation(body);
        }
        //处理 RemoteResponse 注解
        if (returnType.hasMethodAnnotation(REMOTE_ANNOTATION_TYPE)) {
            return this.disposeRemoteAnnotation(body, returnType, request);
        }
        return body;
    }

    private Object disposeRemoteAnnotation(Object body, MethodParameter returnType, ServerHttpRequest request) {
        HttpServletRequest hRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Object msgId = hRequest.getAttribute(RemoteReqConstants.MESSAGE_ID);
        Object timstamp = hRequest.getAttribute(RemoteReqConstants.TIMESTAMP);
        //从request 中取出存放的消息id和时间戳
        return RemoteResponseDto.success(new IdCardMsgDto((String) (msgId == null ? "" : msgId),  (long)(timstamp == null ? 0L : timstamp)), body);
    }

    private Object disposePageAnnotation(Object body) {
        //PageResponse 不会返回String , 不需要单独处理;
        if (body instanceof Page) {
            return PageResponseDto.success((Page) body);
        }
        if (body instanceof PageInfo) {
            return PageResponseDto.success((PageInfo) body);
        }
        throw new BusinessException("使用注解PageResponse,返回的对象必须属于PageHelper中[Page,PageInfo]的一员");
    }

    private Object disposeAjaxAnnotation(Object body, MethodParameter returnType) {
        /*
         * ajaxAnnotation注解标注的方法可能会返回 String;返回的body是String的话会先执行SpringMVC的StringHttpMessageConverter,而不是我们自定义的beforeBodyWrite;
         * 这里需要单独处理,将ajaxAnnotation转成String;
         * */
        if (body instanceof String) {
            ObjectMapper om = new ObjectMapper();
            try {
                return om.writeValueAsString(AjaxResponseDto.success(body));
            } catch (JsonProcessingException e) {
                throw new BusinessException("ObjectMapper.writeValueAsString();统一处理返回对象中,自定义转换失败!e={}", e.getMessage());
            }
        }
        return AjaxResponseDto.success(body);
    }


    //ajax返回信息包装注解
    private static final Class<? extends Annotation> AJAX_ANNOTATION_TYPE = AjaxResponse.class;
    //分页返回信息包装注解
    private static final Class<? extends Annotation> PAGE_ANNOTATION_TYPE = PageResponse.class;
    //远程请求返回信息包装注解
    private static final Class<? extends Annotation> REMOTE_ANNOTATION_TYPE = RemoteResponse.class;

    //包装注解集合
    private static final List<Class<? extends Annotation>> ANNOTATION_TYPE_COLLCTION = new ArrayList<Class<? extends Annotation>>() {{
        add(AJAX_ANNOTATION_TYPE);
        add(PAGE_ANNOTATION_TYPE);
        add(REMOTE_ANNOTATION_TYPE);
    }};

    /**
     * 判断类或者方法是否使用了 @需要处理的注解
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Annotation[] annotations = returnType.getMethod().getAnnotations();
        for (Class<? extends Annotation> annotationType : ANNOTATION_TYPE_COLLCTION) {
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(annotationType)) {
                    return true;
                }
            }
        }
        return false;
    }


}