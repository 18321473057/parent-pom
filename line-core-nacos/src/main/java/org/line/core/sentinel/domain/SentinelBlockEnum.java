package org.line.core.sentinel.domain;

import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;

/**
 * @Author: yangcs
 * @Date: 2020/3/27 14:04
 * @Description: sentinel 流控异常信息枚举
 */
public enum  SentinelBlockEnum {
    FLOW("88581","限流异常","com.alibaba.csp.sentinel.slots.block.flow.FlowException"),
    DEGRADE("88582","降级异常","com.alibaba.csp.sentinel.slots.block.degrade.DegradeException"),
    PARAM_FLOW("88583","热点参数限流异常","com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException"),
    SYSTEM_BLOCK("88584","系统保护异常","com.alibaba.csp.sentinel.slots.system.SystemBlockException"),
    AUTHORITY("88585","授权异常","com.alibaba.csp.sentinel.slots.block.authority.AuthorityException");

    //自定义异常code
    private String code;
    //异常名称
    private String name;
    //抛出的异常
    private String exceptionClass;


    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getExceptionClass() {
        return exceptionClass;
    }

    SentinelBlockEnum(String code, String name, String exceptionClass) {
        this.code = code;
        this.name = name;
        this.exceptionClass = exceptionClass;
    }

}
