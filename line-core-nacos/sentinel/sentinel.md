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



<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
</dependency>

@Bean
public SentinelResourceAspect sentinelResourceAspect(){
    return new  SentinelResourceAspect();
}

FlowRule rule2 = new FlowRule();
//资源名称
rule2.setResource("sentinel1");
//qps方式
rule2.setGrade(RuleConstant.FLOW_GRADE_QPS);
//阈值
rule2.setCount(1);
list.add(rule2);
//加载
FlowRuleManager.loadRules(list);

无需 @SentinelResource,当接口有人访问的时候 就会记录在sentinel中
### 限流模式
#### 直接  对资源名知己限流
#### 关联  "关联资源"访问量大, 则关闭"资源名"的流量
#### 链路  不仅对接口, 对业务方法也可以 需要加 @SentinelResource;
controller  test1, test2 都调用 service dotest 方法;  dotest 加 @SentinelResource("doTest")
对"资源名"doTest 点击流控按钮; "资源名"默认就是doTest,"入口资源"配置test1;test1的流量就被限制了;test2没事

### 流控效果
#### 快速失败;直接抛出异常
#### warm up 预热; 预热时长配置10s ,在10s内慢慢将流量提高到将阈值; 就是在10s内消费阈值
#### 排队等到; 超过阈值的流量,将在配置的超时时间内处理; 过期再说


### 熔断-降级模式
#### 慢调用比例; 1:超过时长则为慢调用, 2:阈值百分比, 3:在多少次请求内, 4:熔断时长
#### 异常比例;  1:阈值百分比, 2:在多少次请求内, 3:熔断时长
#### 异常数;    1:异常数量, 2:在多少次请求内, 3:熔断时长 






### 安装
- 下载sentinel  
- java -jar -Dserver.port=8858 -Dsentinel.dashboard.auth.username=line -Dsentinel.dashboard.auth.password=ycs19930606  sentinel-dashboard-1.8.0.jar 

###  
 