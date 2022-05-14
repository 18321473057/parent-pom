- gitHub上下载nacos
- 解压到本地,修改startup.cmd 中
> set MODE="cluster"  ->  set MODE="standalone"
- 双击启动,  访问首页 http://192.168.180.1:8848/nacos/index.html
- 默认账号密码 nacos/nacos

- RestTemplate 注入时 加上@LoadBalanced  //默认轮训


###  集群部署
```
上传nacos linux版本;解压

建一个nacos数据库  执行nacos-mysql.sql
修改 application.properties 
修改 startup.sh中的启动内存
要注意mysql是否启动和内存不足的问题
 
  

访问http://192.168.52.17:8851/nacos/#/login

出现问题排查
  application.properties 中 nacos.inetutils.ip-address=192.168.52.17  (这个是nginx 地址) 
  看看集群列表  conf/cluster.conf  是否有问题
  删除 nasoc 下data文件夹
  

```
