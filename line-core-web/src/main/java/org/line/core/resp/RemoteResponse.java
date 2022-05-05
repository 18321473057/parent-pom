package org.line.core.resp;


import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;

/**
 * @Author: yangcs
 * @Date: 2020/11/7 15:30
 * @description 封装 返回远程调用请求的注解;
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ResponseBody
public @interface RemoteResponse {
}
