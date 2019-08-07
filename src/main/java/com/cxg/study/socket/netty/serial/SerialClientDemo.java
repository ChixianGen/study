package com.cxg.study.socket.netty.serial;   // Administrator 于 2019/8/7 创建;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.AllArgsConstructor;
import lombok.Data;

public class SerialClientDemo {
    public static void main(String[] args) {
        new NettyClient("192.168.0.14", 8888).connect();
    }
}

@Data
@AllArgsConstructor
class NettyClient {
    private String hostname;
    private int port;

    public void connect() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO)) // 添加日志处理器
                    .handler(new ClientInitializer()) // 自定义管道初始化器
                    .connect(hostname, port).sync() // 连接服务端
                    .addListener(future -> {
                        System.out.println("=-=-=-=-=-=客户端连接成功=-=-=-=-=-=");
                    })
                    .channel().closeFuture().sync() // 监听管道关闭
                    .addListener(future -> {
                        System.out.println("=-=-=-=-=-=连接断开=-=-=-=-=-=");
                    });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    private class ClientInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline()
//                    .addLast(new StringDecoder())
//                    .addLast(new StringEncoder())
                    .addLast(MarshallingCodeFactory.buildMarshallingDecode())
                    .addLast(MarshallingCodeFactory.buildMarshallingEncode())
                    .addLast(new CustomClientHandle());
        }
    }
}
