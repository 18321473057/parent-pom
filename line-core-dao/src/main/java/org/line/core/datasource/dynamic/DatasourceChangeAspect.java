package org.line.core.datasource.dynamic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.line.core.datasource.dynamic.config.DatasourceChangeConfiguration;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;

/**
 * @Description: 数据源切换切面
 */
@Aspect
@Order(Integer.MIN_VALUE)
public class DatasourceChangeAspect {
    Log logger = LogFactory.getLog(DatasourceChangeConfiguration.class);

    /**
     * 根据注解上指定的数据源类型切换数据源
     */
    @Around("@annotation(AnotherDatasource)")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            //获取目标类
            Class<?> targetClazz = joinPoint.getTarget().getClass();
            //获取执行的方法名
            String methodName = joinPoint.getSignature().getName();
            //获取此方法的参数列表
            Class<?>[] par = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
            //获取到实际执行的方法
            Method objMethod = targetClazz.getMethod(methodName, par);
            //从执行的方法上获取注解
            AnotherDatasource annotation = objMethod.getAnnotation(AnotherDatasource.class);
            if (annotation != null) {
                //将注解上指定的数据源类型读取到
                String datasourceType = annotation.value();
                //设置当前处理的数据源类型为注解上指定的
                ContextDatasourceTypeHolder.setDatasourceType(datasourceType);
                logger.info("------当前数据源名称-------"+datasourceType);
            }
            Object result = joinPoint.proceed();
            return result;
        } finally {
            ContextDatasourceTypeHolder.clearDatasourceType();
        }
    }

}
