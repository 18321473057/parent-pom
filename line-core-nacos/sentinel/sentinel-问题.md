###  sentinel 只显示项目名,簇点链路和降级规则都是空的;
1:  配置客户端的地址 
spring:
     cloud:
       sentinel:
         transport:
           clientIp: 192.168.52.101
           
2: 虚拟机或者服务器 防火墙  是否关闭/可通过
ufw status
-- Status: inactive   关闭的


3: 虚拟机或者服务器 是否可以ping通项目所在的地址
ping  192.168.52.101 或 curl xxxxxxx


4: 无法创建规/创建了规则却没有规则显示
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.58</version>
        </dependency>

5: 有时候不知道怎么了, 链路和实时监控没了; 重启项目和sentinel就好了

      
6: 使用了 @SentinelResource  就不会走BlockExceptionHandler