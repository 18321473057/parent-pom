>sw主要是对oapServer做高可用 



![img](https://img2020.cnblogs.com/blog/158914/202012/158914-20201213222046351-484524677.png)





- 配置 ~/apache-skywalking-apm-bin/config/application.yml 

```yml
cluster:
  selector: ${SW_CLUSTER:nacos}
  nacos:
    serviceName: ${SW_SERVICE_NAME:"SkyWalking_OAP_Cluster"}
    hostPort: ${SW_CLUSTER_NACOS_HOST_PORT:192.168.52.14:8847}
    # Nacos Configuration namespace
    namespace: ${SW_CLUSTER_NACOS_NAMESPACE:"yom-test"}
    # Nacos auth username
    username: ${SW_CLUSTER_NACOS_USERNAME:"line"}
    password: ${SW_CLUSTER_NACOS_PASSWORD:"ycs19930606..."}
core:
  default:
  #修改web请求和oap请求端口
    restPort: ${SW_CORE_REST_PORT:12802}
    gRPCPort: ${SW_CORE_GRPC_PORT:11802}
```

- 配置 ~/apache-skywalking-apm-bin/webapp/webapp.yml

```yml
server:
  port: 8899

spring:
  cloud:
    discovery:
      client:
        simple:
          instances:
            oap-service:
              - uri: http://127.0.0.1:12800
              - uri: http://127.0.0.1:12801
              - uri: http://127.0.0.1:12802

```

- 配置  启动项目参数  VM options

>-javaagent:D:\apache-skywalking-java-agent-8.8.0\skywalking-agent\skywalking-agent.jar
>-Dskywalking.agent.service_name=core-agent
>-Dskywalking.collector.backend_service=192.168.52.14:11800,192.168.52.14:11801,192.168.52.14:11802
>
>