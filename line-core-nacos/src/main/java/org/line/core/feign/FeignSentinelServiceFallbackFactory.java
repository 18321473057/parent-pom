package org.line.core.feign;

import feign.hystrix.FallbackFactory;
import org.line.core.domain.AjaxResponseDto;
import org.line.core.domain.BasicResponse;

/**
 * @Author: yangcs
 * @Date: 2022/5/25 18:19
 * @Description:   FeignSentinel 整合降级服务
 */
public class FeignSentinelServiceFallbackFactory implements FallbackFactory {

    @Override
    public Object create(Throwable throwable) {
//        BasicResponse response = new BasicResponse();
//        response.setCode("500");
//        response.setMsg("FallbackFactory:: -- 发生异常  "+throwable.getMessage());
//        return  response;
        return "FallbackFactory:: -- 发生异常  "+throwable.getMessage();
    }
}
