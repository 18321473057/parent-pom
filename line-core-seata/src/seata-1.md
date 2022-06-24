
### AC 模式
- 无侵入,逻辑由数据库sql完成, (备份原sql情况)
- 有行级锁

### TCC 模式
- 侵入性大, 需要自己 try(先调用)  confirm(后确认)  cancel(失败取消操作)
- 无锁, 性能更强


### 可靠消息最终一致性方案
- 预备消息
- 更新数据



TC（service端，需要单独部署）  TM  RM  由业务系统集成

spring.cloud.alibaba.dependencies.version 2.2.5.RELEASE  对应      seata-server-1.3.0.tar.gz 


存储模式 
 1：file  默认单机模式，全局事务会话信息早内存读写； 持久化到root.data 性能高
 2：DB   高可用模式，全局事务会话通过DB共享， 性能稍差
 3：redis 性能较高，存在事务丢失风险
 
 
 安装：  DB+nacos 方式部署集群模式
 1： 下载 seata-server-1.3.0.tar.gz ； 解压
 2： 修改file.conf ； 配置model DB 和数据量连接信息
 3： 下载 seata-1.3.0(这里面有配置文件)， 执行 \parent-pom\line-core-seata\src\script\server\db\mysql.sql
 4： 修改registry.conf;   原来的文件在   \parent-pom\line-core-seata\src\script\config-center\config.txt
  现在我们需要
 
 
 
 