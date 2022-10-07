### skywalking

国产  吴晟 分布式 应用程序性能监视工具  微服务，容器架构设计 APM(application Performance management)    

分布式追踪 性能指标根系  服务依赖分析

11800 收集数据   
12800  前端请求接口  



> 服务： 可以理解为对微服务的一种抽象
>
> 服务实例： 微服务的一个实例
>
> 端点： 请求API的路径，端点类型有两种：Http的Url路径，gREP的类名+方法签名
>
> 追踪： 一次请求完整的经过，可以微服务内的请求，也可以是夸服务的请求

 



>layer 层的概念(操作系统， k8s,服务)，层的数据有共同性
>
>general Service  - language Agent installed    语言探针
>
>service mesh    --                服务管理
>
>FAAS- openFunction  --    函数式程序
>
>kubernetes   -   云原生
>
>linux - infra
>
>Browser APP         node.js 
>
>Virtual Database  虚拟数据库   不是实体数据库，数据库是快是慢
>
>So1y - self Observability     自我监控
>
>So11y - satellite   长链接流量型的负载
>
>

- 性能剖析菜单不现实数据

>
>1： 先新建task list
>2:  端点时间不能小于指定的监控间隔,执行次数不小于最大采样数
>3： V8.8以后端点命名有改动 示例：“GET:/test/rest/get”  不再是“/test/rest/get” 
>
>



 

- ### skywalking  日志设置追踪ID


```xml
 <!--  
1： 先排除springboot自带的logback, 注意继承父项目，引用spring-boot-starter时 需要再次排除logback
2： 选择使用log4j2
3: skywalking 整合包
 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
        <exclusions> <!-- 去掉默认日志配置logback -->
            <exclusion>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-logging</artifactId>
            </exclusion>
        </exclusions>
    </dependency>

      <!-- 日志  -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-log4j2</artifactId>
    </dependency>

    <!--skywalking 整合log4j   -->
    <dependency>
        <groupId>org.apache.skywalking</groupId>
        <artifactId>apm-toolkit-log4j-2.x</artifactId>
        <version>8.8.0</version>
    </dependency>
```


      不需要MDC方式设置一个uuid为traceId, %X{traceId}获取的id也不对；
      这里需要整合skywalking，才能在skywalking中根据追踪ID查询
      1：log4j2-spring.xml 配置 <configuration status="WARN" monitorInterval="30" packages="org.apache.skywalking.apm.toolkit.log.log4j.v2.x">
      2：log4j2-spring.xml 配置 <PatternLayout pattern="[%d{HH:mm:ss:SSS}] [%p] -traceId=[%traceId]- %l - %m%n"/> 注意：是%traceId 没有X{}


​     

#### 题外话： 建议在resources放置的文件名为log4j2-spring.xml ，而不是 log4j.properties。约定大于配置，也省一个配置 

​      

- ### skywalking  收集日志

​      https://github.com/apache/skywalking-java/blob/v8.8.0/docs/en/setup/service-agent/java-agent/Application-toolkit-log4j-2.x.md

>```xml
><!-- log4j2-spring.xml 添加--> 
><GRPCLogClientAppender name="grpc-log">
>   <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>   </GRPCLogClientAppender>
><loggers>
><root level="INFO">
>    <appender-ref ref="grpc-log"/>      
></root>
></loggers>
>```
>
>```properties
># ~\apache-skywalking-java-agent-8.8.0\skywalking-agent\config\agent.config 中添加
># 注意：v8.8 版本 agent 与skywalking 已经分离， 需要重新下载
>log.max_message_size=${SW_GRPC_LOG_MAX_MESSAGE_SIZE:10485760}
>```

