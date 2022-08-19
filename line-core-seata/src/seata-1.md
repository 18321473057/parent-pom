
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
 4： 修改registry.conf;  
 5： 修改配置  src/script/config-center/config.txt 修改
 service.vgroupMapping.shanghai=default
 store.mode=DB
 6: 将配置提交到nacos； 
  config.txt 修改好后， 将script文件夹整体放到linux 上   
  执行 sh nacos-config.sh -h 192.168.52.17 -p 8847   -u line -w ycs19930606...
  注意： 用户是否有权限

 
 
 
 
 最大的坑
 >2022-07-07 16:47:15.658  INFO --- [           main] com.alibaba.druid.pool.DruidDataSource   : {dataSource-1} inited
  Exception in thread "main" io.seata.common.loader.EnhancedServiceNotFoundException: java.lang.IllegalStateException: Extension instance(definition: io.seata.common.loader.ExtensionDefinition@c8d06535, class: class io.seata.server.coordinator.AbstractCore)  could not be instantiated: null
  Caused by: java.lang.IllegalStateException: Extension instance(definition: io.seata.common.loader.ExtensionDefinition@c8d06535, class: class io.seata.server.coordinator.AbstractCore)  could not be instantiated: null
  	at io.seata.common.loader.EnhancedServiceLoader$InnerEnhancedServiceLoader.createNewExtension(EnhancedServiceLoader.java:406)
  	at io.seata.common.loader.EnhancedServiceLoader$InnerEnhancedServiceLoader.getExtensionInstance(EnhancedServiceLoader.java:388)
  	at io.seata.common.loader.EnhancedServiceLoader$InnerEnhancedServiceLoader.loadAll(EnhancedServiceLoader.java:314)
  	at io.seata.common.loader.EnhancedServiceLoader$InnerEnhancedServiceLoader.access$600(EnhancedServiceLoader.java:190)
  	at io.seata.common.loader.EnhancedServiceLoader.loadAll(EnhancedServiceLoader.java:152)
  	at io.seata.server.coordinator.DefaultCore.<init>(DefaultCore.java:61)
  	at io.seata.server.coordinator.DefaultCoordinator.<init>(DefaultCoordinator.java:150)
  	at io.seata.server.Server.main(Server.java:86)
  Caused by: java.lang.reflect.InvocationTargetException
  	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
  	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
  	at java.base/jdk.internal.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
  	at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:490)
  	at io.seata.common.loader.EnhancedServiceLoader$InnerEnhancedServiceLoader.initInstance(EnhancedServiceLoader.java:570)
  	at io.seata.common.loader.EnhancedServiceLoader$InnerEnhancedServiceLoader.createNewExtension(EnhancedServiceLoader.java:402)
  	... 7 more
  Caused by: java.lang.ExceptionInInitializerError
  	at io.seata.server.coordinator.AbstractCore.<init>(AbstractCore.java:58)
  	at io.seata.server.transaction.at.ATCore.<init>(ATCore.java:36)
  	... 13 more
  Caused by: io.seata.common.loader.EnhancedServiceNotFoundException: not found service provider for : io.seata.server.lock.LockManager

 