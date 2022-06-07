##### sentinel-dashboard 自身修改开始

###  sentinel 不能讲数据持久化到nacos, 只能在nacos 修改;; 这里只做了,流控规则菜单栏的修改

1:引入 nacos , 原来就有 只不过写得是test阶段 
        <dependency>
            <groupId>com.alibaba.csp</groupId>
            <artifactId>sentinel-datasource-nacos</artifactId>
<!--            ycs改造标记-->
<!--            <scope>test</scope>-->
        </dependency>
        
2: sentinel-dashboard resources/application.properties 中添加

# ycs改造标记
# nacos config server
sentinel.nacos.serverAddr=192.168.52.17:8847
sentinel.nacos.namespace=yom-test
#sentinel.nacos.group-id=SENTINEL-GROUP
#sentinel.nacos.group-id=DEFAULT_GROUP
sentinel.nacos.user-name=line
sentinel.nacos.pass-word=ycs19930606...



3: 将 本文件同级的nacos 文件夹
拷贝到
sentinel-dashboard项目
com.alibaba.csp.sentinel.dashboard.rule  目录下

4: 修改 com.alibaba.csp.sentinel.dashboard.controller.v2.FlowControllerV2
   //    ycs改造标记
    @Autowired
//    @Qualifier("flowRuleDefaultProvider")
    @Qualifier("flowRuleNacosProvider")
    private DynamicRuleProvider<List<FlowRuleEntity>> ruleProvider;

    //    ycs改造标记
    @Autowired
//    @Qualifier("flowRuleDefaultPublisher")
    @Qualifier("flowRuleNacosPublisher")
    private DynamicRulePublisher<List<FlowRuleEntity>> rulePublisher;

#### 做这些 其实就是 配置nacos的服务连接
#### 接下来修改后台管理页面请求的地址
5: 修改 sentinel-dashboard\src\main\webapp\resources\app\scripts\directives\sidebar\sidebar.html

后台管理页面中请求的地址 修改为v2 连接

<!--          <li ui-sref-active="active" ng-if="!entry.isGateway">-->
<!--            <a ui-sref="dashboard.flowV1({app: entry.app})">-->
<!--              <i class="glyphicon glyphicon-filter"></i>&nbsp;&nbsp;流控规则</a>-->
<!--          </li>-->

<!--          ycs改造标记-->
          <li ui-sref-active="active" ng-if="!entry.isGateway">
            <a ui-sref="dashboard.flow({app: entry.app})">
              <i class="glyphicon glyphicon-filter"></i>&nbsp;&nbsp;123流控规则</a>
          </li>
##### sentinel-dashboard 自身修改结束


##### 需要sentinel管理的项目的配置开始

# sentinel 的配置文件 在nacos 中; nacos中的配置文件的具体信息
spring:
  cloud:
    sentinel:
      datasource:
        flow-rule:
          nacos:
            server-addr: 192.168.52.17:8847
            username: line
            password: ycs19930606...
            namespace: yom-test
            dataId: ${spring.application.name}-flow-rules
            group-id: SENTINEL_GROUP
            rule-type: flow
            data-type: json
            
            
###  先install  sentinel-parent