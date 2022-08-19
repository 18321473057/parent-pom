### skywalking

国产  吴晟 分布式 应用程序性能监视工具  微服务，容器架构设计 APM(application Performance management)    

分布式追踪 性能指标根系  服务依赖分析



服务： 可以理解为对微服务的一种抽象

服务实例： 微服务的一个实例

端点： 请求API的路径，端点类型有两种：Http的Url路径，gREP的类名+方法签名

追踪： 一次请求完整的经过，可以微服务内的请求，也可以是夸服务的请求。

 



https://skywalking.apache.org/

https://skywalking.apache.org/zh/

https://skyapm.github.io/document-cn-translation-of-skywalking/





layer 层的概念(操作系统， k8s,服务)，层的数据有共同性

general Service  - language Agent installed    语言探针

service mesh    --                服务管理

FAAS- openFunction  --    函数式程序

kubernetes   -   云原生

linux - infra

Browser APP         node.js 

Virtual Database  虚拟数据库   不是实体数据库，数据库是快是慢

So1y - self Observability     自我监控

So11y - satellite   长链接流量型的负载




![img](https://s0.lgstatic.com/i/image3/M01/6E/3E/Cgq2xl5fMbiAd3tdAAa1Trt-kIg886.png)

​    

![img](https://upload-images.jianshu.io/upload_images/23610677-0704687b809f2013.png)



11800 收集数据   
12800  前端请求接口  



-javaagent:D:\apache-skywalking-java-agent-8.8.0\skywalking-agent\skywalking-agent.jar
-Dskywalking.agent.service_name=infomation-agent
-Dskywalking.collector.backend_service=192.168.52.14:11800

存储方式修改为mysql 后 会出现没有驱动的问题
Caused by: java.sql.SQLException: No suitable driver 
哔了狗！！！ 性能剖析菜单不现实数据
1： 先新建task list
2:  端点时间不能小于指定的监控间隔,执行次数不小于最大采样数
3： V8.8以后端点命名有改动 示例：“GET:/test/rest/get”  不再是“/test/rest/get” 





- ### 告警

>告警配置在 ~/apache-skywalking-apm-bin/config/alarm-settings.yml 
>
>webhooks 两个实体类 已经拷贝出来了



