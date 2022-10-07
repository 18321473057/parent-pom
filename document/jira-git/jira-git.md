



- gitlab地址                      https://git.etianneng.cn
- jira地址                          https://jira.etianneng.cn/



## gitlab 创建令牌

创建一个测试项目  以 tn-test-group/project-test 为例

进入要配置的项目

![image-20220905152256313](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220905152256313.png)





![image-20220905152522580](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220905152522580.png)

点击创建令牌后 ，会展示令牌，请保存好， 离开这个页面后就再也查询不到了; 令牌在jira 中会使用到。

![image-20220905152649918](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220905152649918.png)









### jira 系统配置

##### 安装git插件   

![image-20220905155031671](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220905155031671.png)

![image-20220905155135579](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220905155135579.png)



##### 配置GIt

![image-20220905155232565](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220905155232565.png)



![image-20220905155253355](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220905155253355.png)



![image-20220905155328091](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220905155328091.png)



![image-20220905155504436](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220905155504436.png)

![image-20220905155820397](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220905155820397.png)



第一次使用Git库信息需要配置令牌 

![image-20220906173355862](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220906173355862.png)
![image-20220906173501764](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220906173501764.png)





## 功能点

- ### 比较代码

![image-20220906172501927](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220906172501927.png)







![image-20220906172642260](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220906172642260.png)

- ### 相关分支

建议点击后面的分享按钮可以查看到Git中查看最新更新

![image-20220906172852664](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220906172852664.png)

注意： 这里的删除会删除Git 远程分支， 慎用！

![image-20220906172934727](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220906172934727.png)

- 合并

![image-20220906173108676](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220906173108676.png)

![image-20220906173809806](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220906173809806.png)

点击超链 直接前往Git合并页面

![image-20220906174149702](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220906174149702.png)

![image-20220906174229275](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220906174229275.png)



查看提交记录



![image-20220907082224961](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220907082224961.png)





------

# Git源代码不现实或显示不全问题配置

这一部分的显示是根据git 配置而来的， 当配置了”项目级别“git时， 只拥有比较代码功能

create branch和create merge request 需要配置**GitLab Server (CE/EE) (APIv4)** 账户

![image-20220920143359503](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220920143359503.png)

#### **GitLab Server (CE/EE) (APIv4)** 账户 配置如下

![image-20220920143706089](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220920143706089.png)

![image-20220920143743483](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220920143743483.png)



![image-20220920143755300](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220920143755300.png)

![image-20220920143808937](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220920143808937.png)

### git personal Access Token配置



![image-20220920144234994](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220920144234994.png)

![image-20220920144245382](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220920144245382.png)

![image-20220920144257357](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220920144257357.png)