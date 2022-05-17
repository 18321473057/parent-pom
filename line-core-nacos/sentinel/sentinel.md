###    sentinel  分布式架构 高可用防护组件


### 容错机制 
- 超时机制
- 限流  qps: 每秒访问量  每秒500 过多的直接拒绝  -- 服务端
- 隔离  限制线程数量
- 熔断  相应时间/失败比例 设置一个阈值, 固定时间后 重新使用这个服务
- 降级  弱依赖   --消费端
- 流量整形  慢启动 云速器模式慢慢启动
- 持久化  保存原有的设置参数  -- 在nacos上持久化




###  sentinel核心库  / dashboard (控制台)  

### 降级注解
#### @SentinelResource(value="uri"  blockHandler="流控降级方法" fallback="" 
exceptionToIgnore={}
)
- blockHandlerClass=""
- blockHandler: 处理降级方法;入参一样, 最后可以添加一个BlockException 
- fallbackClass = ""
- fallback :  当接口出现异常处理的方法;入参一样, 最后可以添加一个BlockException