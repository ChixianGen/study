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
- a
### TCP
> tcp是一个基于"流"的协议，数据之间没有明确的界限；
    可能会一个数据包被拆分成几个小包发送，也可能会几个小数据包合并成一个整包发送；
- tcp的沾包和拆包问题，常见的几种解决方案：
    - 消息定长，不足补空格；
    - 包尾添加特殊符号进行分隔，例如回车等；
    - 将消息分为消息头和消息体，在消息头中添加消息的总长度，然后再进行逻辑处理；（自定义协议）
---
### Netty
> - 底层是基于 NIO(NON-BLOCKING) 模型；
> - Linux系统的AIO（异步IO）模型底层仍然是基于NIO；
- NioEventLoopGroup：线程组默认大小为cpu内核数 * 2；
- FastThreadLocalThread extends Thread：Netty底层使用的线程；
- ThreadPerTaskExecutor implements Executor：保存线程的执行器；
