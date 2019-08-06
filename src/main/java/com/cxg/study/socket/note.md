### windows系统查看端口
- 查看所有端口：`netstat  -ano`
- 查看指定端口：`netstat -ano|findstr "8080"   -- 指定端口`
- 查看指定进程：`tasklist|findstr "15208"  -- 指定进程ID`
---
> - (IP)定位到目标主机；
> - (PORT)端口定位到目标主机上的具体服务（软件进程）；
> - (URL)定位到具体的资源；
---
### UDP
### TCP
---
### Netty
> - 底层是基于 NIO(NON-BLOCKING) 模型；
> - Linux系统的AIO（异步IO）模型底层仍然是基于NIO；
