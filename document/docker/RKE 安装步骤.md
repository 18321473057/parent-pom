# RKE 安装步骤
## 一、准备 RKE 安装包
1. 下载 RKE 安装包（v1.3.15）
2. 上传 RKE 安装包
3. mv rke_linux-amd64 rke
4. chmod +x rke
## 二、准备集群节点 OS 环境
> https://www.suse.com/suse-rancher/support-matrix/all-supported-versions/rancher-v2-6-8/ RKE Docker 版本对照表
1. 安装 EPEL 企业软件包源：yum -y install epel-release
2. 安装工具：yum -y install lrzsz vim gcc glibc openssl openssl-devel net-tools wget curl
3. 更新软件包版本和内核版本：yum -y update
4. 内核参数调优：
- 安装模块：modprobe br_netfilter
- cat >> /etc/sysctl.conf << eof
vm.swappiness=0
net.ipv4.ip_forward=1
net.bridge.bridge-nf-call-iptables=1
net.bridge.bridge-nf-call-ip6tables=1
net.ipv4.neigh.default.gc_thresh1=4096
net.ipv4.neigh.default.gc_thresh2=6144
net.ipv4.neigh.default.gc_thresh3=8192
eof
- 启用内核配置：sysctl -p
5. 修改主机名，不同节点分别执行：hostnamectl set-hostname rancher-01,hostnamectl set-hostname rancher-02，hostnamectl set-hostname rancher-03
6. 安装 IPVS 模块，配合 Kube-proxy 使用：
- cat > /etc/sysconfig/modules/ipvs.modules <<EOF
#!/bin/bash
modprobe -- ip_vs
modprobe -- ip_vs_rr
modprobe -- ip_vs_wrr
modprobe -- ip_vs_sh
modprobe -- nf_conntrack_ipv4
EOF
- chmod 755 /etc/sysconfig/modules/ipvs.modules 
- bash /etc/sysconfig/modules/ipvs.modules
- lsmod | grep -e ip_vs -e nf_conntrack_ipv4
- yum install -y ipvsadm
- yum install -y ipset
7. 安装 Docker：
- curl https://releases.rancher.com/install-docker/20.10.sh | sh
- mkdir -p /etc/docker;tee /etc/docker/daemon.json <<-'EOF'
{
"registry-mirrors": ["https://registry.cn-hangzhou.aliyuncs.com","https://mirror.ccs.tencentyun.com","https://registry.docker-cn.com","http://hub-mirror.c.163.com"],
"exec-opts": ["native.cgroupdriver=systemd"],
"log-driver": "json-file",
"log-opts": {
"max-size": "100m"
},
"storage-driver": "overlay2",
"graph": "/data/docker/lib",
"insecure-registries":["192.168.33.129"]
}
EOF
- systemctl daemon-reload
- systemctl enable docker
- systemctl enable containerd.service
- systemctl start docker

8. 启用 SSH 配置 /etc/ssh/sshd_config，启用 AllowTcpForwarding：
- vim  /etc/ssh/sshd_config
- systemctl restart sshd
9. 打开 TCP/6443 端口：iptables -A INPUT -p tcp --dport 6443 -j ACCEPT
10. 创建 SSH 用户，该 SSH 用户必须在 Docker 用户组：
- useradd rke
- usermod -aG docker rke
11. 配置 RKE 用户节点间免密登陆，每个节点都执行：
- ssh-keygen -t rsa
- ssh-copy-id -i rke@rancher-01
- ssh-copy-id -i rke@rancher-02
- ssh-copy-id -i rke@rancher-03
## 三、创建 RKE 集群配置文件
1. 配置 RKE 集群：cd /data/rke & rke config --name cluster.yml
2. 修改目录权限：chown -R rke:rke rke
3. 启动 RKE 集群：./rke up
## 四、通过 Helm 安装 Rancher 集群
1. 安装 kubectl 工具
- mkdir -p /data/kubectl
- cd /data/kubectl
- curl -LO "https://dl.k8s.io/release/1.25.2/bin/linux/amd64/kubectl"
- curl -LO "https://dl.k8s.io/1.25.2/bin/linux/amd64/kubectl.sha256"
- echo "$(cat kubectl.sha256)  kubectl" | sha256sum --check
- sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
- 确认安装的 kubectl 版本：kubectl version --client
2. 安装 Helm 工具（Helm v3.2.x or higher is required to install or upgrade Rancher v2.5）
- 下载最新的 Helm 发行包：mkdir -p /data/helm & cd /data/helm （helm-v3.10.0-linux-amd64.tar.gz）
- 解压：tar -zxvf helm-v3.10.0-linux-amd64.tar.gz
- CLI 加入环境变量：mv linux-amd64/helm /usr/local/bin/helm
- 添加仓库：helm repo add bitnami https://charts.bitnami.com/bitnami
- 添加 Rancher 稳定版仓库：helm repo add rancher-stable https://releases.rancher.com/server-charts/stable
3. K8S 创建 Rancher 使用的命名空间：kubectl --kubeconfig /data/rke/kube_config_cluster.yml create namespace cattle-system
4. 选择 SSL 配置（https://docs.ranchermanager.rancher.io/pages-for-subheaders/install-upgrade-on-a-kubernetes-cluster#cli-tools）：如果选择 Rancher 自有 CA，那么 Helm chart 的 配置需设置为ingress.tls.source=rancher
5. 由于选择 Rancher 自有 CA，需安装 cert-manager：
- kubectl --kubeconfig /data/rke/kube_config_cluster.yml apply -f https://github.com/cert-manager/cert-manager/releases/download/v1.9.1/cert-manager.crds.yaml
- 更新 helm 仓库：helm repo add jetstack https://charts.jetstack.io
- 更新 helm 仓库本地缓存：helm repo update
- 安装 helm chart of cert-manager：helm --kubeconfig /data/rke/kube_config_cluster.yml install cert-manager jetstack/cert-manager --namespace cert-manager --create-namespace --version v1.9.1 --debug
- 验证 POD 运行状态：kubectl --kubeconfig /data/rke/kube_config_cluster.yml get pods --namespace cert-manager
6. 安装 Rancher 
- helm --kubeconfig /data/rke/kube_config_cluster.yml install rancher rancher-stable/rancher --namespace cattle-system --set hostname=rancher.tianneng.tech --set bootstrapPassword=RancherPwd22 --version 2.6.8
- 查看 Rancher 安装状态：kubectl --kubeconfig /data/rke/kube_config_cluster.yml -n cattle-system rollout status deploy/rancher