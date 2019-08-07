package com.cxg.study.socket.netty.serial;   // Administrator 于 2019/8/7 创建;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.AllArgsConstructor;
import lombok.Data;

public class SerialServerDemo {
    public static void main(String[] args) {
        new NettyServer(8888).startServer();
    }
}

@Data
@AllArgsConstructor
class NettyServer {
    private int port;
    public void startServer() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(5);
        EventLoopGroup workerGroup = new NioEventLoopGroup(10);
        ServerBootstrap bootstrap = new ServerBootstrap();
        try{
            bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .handler(new LoggingHandler(LogLevel.INFO)) // 添加日志处理器
                .childHandler(new ServerChannelInitializer())
                .bind(port).sync() // 绑定端口，等待客户端的连接
                .addListener(future -> {
                    System.out.println("=-=-=-=-=-=服务端端口绑定成功，等待连接=-=-=-=-=-=");
                })
                .channel().closeFuture().sync()
                .addListener(future -> {
                    System.out.println("=-=-=-=-=-=服务端停止服务=-=-=-=-=-=");
                });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline()
//                    .addLast(new StringDecoder())
//                    .addLast(new StringEncoder())
                    .addLast(MarshallingCodeFactory.buildMarshallingDecode())
                    .addLast(MarshallingCodeFactory.buildMarshallingEncode())
                    .addLast(new CustomServerHandler());
        }
    }
}


