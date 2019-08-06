### 远程连接redis服务器
- 先启动redis服务器，确认redis服务器的IP地址、端口及登录密码；
- 使用xshell工具登录虚拟机（测试用的Ubuntu 18.04 LTS系统），使用如下命令即可远程连接redis；
```
redis-cli -h 192.168.0.14 -p 6379 -a "123456"
```
> 使用redis-cli命令的前提是需要先安装有 [redis-tools] 工具；
> Ubuntu的安装命令：
```
apt install redis-tools
```
---