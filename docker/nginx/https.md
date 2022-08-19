--本地生成ssl --原文
openssl genrsa -des3 -out xxx.key 1024
openssl req -new -key xxx.key -out xxx.csr
cp xxx.key xxx.key.org
openssl rsa -in xxx.key.org -out xxx.key
openssl x509 -req -days 365 -in xxx.csr -signkey xxx.key -out xxx.crt
 
--密码要好好输入
openssl genrsa -des3 -out ggmt.key 1024
openssl req -new -key ggmt.key -out ggmt.csr
ggmt.key 复制一份 ggmt.key.org
openssl rsa -in ggmt.key.org -out ggmt.key
openssl x509 -req -days 365 -in ggmt.csr -signkey ggmt.key -out ggmt.crt


在当前文件夹下生成  ggmt.crt  ggmt.key;  

配置nginx 请看nignx_http.conf

------------------------------------------------------------------------------------------------
阿里云生成；
1： 买个域名 花钱
2： 设置解析  给域名配个本地地址， 可以访问到；
3：  申请免费ssl 证书 ； 
4： 下载nginx版本证书


