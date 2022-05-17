- 默认轮询

# 负载种类
> - 随机
> - 轮训
> - 重试
> - 权重
> - 响应时间越短 权重越大, 被选中的概率越大 (nacos 自定义的)
> - 并发请求最小的实例
> - 区域就近-轮行
> - 过滤故障,并发最小

负载自定义方式1 :

@RibbonClients(value = {@RibbonClient(name = "core-io",configuration = RuleSelectConfiguration.class)})
    @Bean
    public IRule iRule() {
        return new RandomRule();
    }
    
负载自定义方式2 :
core-iocoi:
  ribbon:
    #nacos 实现的权重
    NFLoadBalancerRuleClassName: com.alibaba.cloud.nacos.ribbon.NacosRule
    
    

IRule: 负载策略顶级接口    
loadbalancer : 帮助选择的


### nacos权限控制
nacos application.properties 中 设置 nacos.core.auth.enable = true