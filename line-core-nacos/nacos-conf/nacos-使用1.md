- gitHub上下载nacos
- 解压到本地,修改startup.cmd 中
> set MODE="cluster"  ->  set MODE="standalone"
- 双击启动,  访问首页 http://192.168.180.1:8848/nacos/index.html
- 默认账号密码 nacos/nacos

- RestTemplate 注入时 加上@LoadBalanced  //默认轮训


# nacos = 注册中心 + 配置中心 + 服务管理平台

- 服务发现
- 服务监控
- 动态配置
- 服务元数据配置

Nacos      CP/AP    雪崩保护
Eureka     AP
Consul     CP
Zookeeper  CP

一致性协议, 健康检查, 负载策略, 雪崩保护

CAP即：
Consistency（一致性）
Availability（可用性）
Partition tolerance（分区容忍性）


###  集群部署
```
修改上传nacos linux版本;解压

建一个nacos数据库  执行nacos-mysql.sql
修改 application.properties 
修改 startup.sh中的启动内存
要注意mysql是否启动和内存不足的问题
 
 
访问http://192.168.52.17:8851/nacos/#/login

出现问题排查
  application.properties 中 nacos.inetutils.ip-address=192.168.52.17  (这个是nginx 地址) 
  看看集群列表  conf/cluster.conf  是否有问题
  删除 nacos 下data文件夹
  
```
