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
> <!-- log4j2-spring.xml 添加--> 
><GRPCLogClientAppender name="grpc-log">
>        <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>   </GRPCLogClientAppender>
><loggers>
>    <root level="INFO">
>         <appender-ref ref="grpc-log"/>      
>    </root>
></loggers>
>```
>
>```properties
># ~\apache-skywalking-java-agent-8.8.0\skywalking-agent\config\agent.config 中添加
># 注意：v8.8 版本 agent 与skywalking 已经分离， 需要重新下载
>log.max_message_size=${SW_GRPC_LOG_MAX_MESSAGE_SIZE:10485760}
>```

