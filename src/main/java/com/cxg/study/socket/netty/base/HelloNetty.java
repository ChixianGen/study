package com.cxg.study.socket.netty.base;

import com.cxg.study.socket.base.udp.Person;
import com.google.common.base.Strings;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

public class HelloNetty {
    public static void main(String[] args) {
        new NettyServer().serverStart();
    }
}

class NettyServer {
    public void serverStart() {
        // 负责连接；
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        // 负责连接后的IO处理；
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();

        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024) // 设置 tcp 缓冲区；
                .option(ChannelOption.SO_SNDBUF, 1024 * 10) // 设置发送缓冲大小
                .option(ChannelOption.SO_RCVBUF, 1024 * 10) // 设置接收缓冲大小
                .option(ChannelOption.SO_KEEPALIVE, true) // 保持连接
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new Handler());
                    }
                });
        try {
            // 可绑定多个端口；
            ChannelFuture channelFuture1 = b.bind(8888).sync();
            ChannelFuture channelFuture2 = b.bind(9999).sync();

            channelFuture1.channel().closeFuture().sync(); // 阻塞状态，服务器一直存活，等待关闭；
            channelFuture2.channel().closeFuture().sync(); // 阻塞状态，服务器一直存活，等待关闭；
        } catch (InterruptedException e) {
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}

class Handler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("server: channel read");
        ByteBuf buf = (ByteBuf) msg;
        String string = buf.toString(CharsetUtil.UTF_8);
        System.out.printf("客户端请求数据：【%s】\n", string);

        // 模拟响应；
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello client".getBytes()))
                // 服务端成功响应后自动断开连接；
                .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
