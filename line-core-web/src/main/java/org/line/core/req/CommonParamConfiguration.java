package org.line.core.req;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.Arrays;

/**
 * @Description 通用参数过滤器 配置器
 */
@Configuration
public class CommonParamConfiguration {

    /**
     * 注入 RemoteApiParamSetFilter 过滤器 , 拦截所有请求
     */
    @Bean
    public FilterRegistrationBean aramsVerifyFilterRegistration() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new RemoteApiParamSetFilter());
        registrationBean.setUrlPatterns(Arrays.asList("/*"));
        registrationBean.setOrder(Ordered.LOWEST_PRECEDENCE - 10000);
        return registrationBean;
    }
}
