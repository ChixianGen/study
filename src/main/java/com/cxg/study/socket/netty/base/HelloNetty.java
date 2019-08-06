package com.cxg.study.socket.netty.base;

import com.cxg.study.socket.base.udp.Person;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

public class HelloNetty {
    public static void main(String[] args) {
        new NettyServer(8888).serverStart();
    }
}

class NettyServer {
    private int port;

    public NettyServer(int port) {
        this.port = port;
    }

    public void serverStart() {
        // 负责连接；
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        // 负责连接后的IO处理；
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();

        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new Handler());
                    }
                });
        try {
            ChannelFuture f = b.bind(port).sync();

            f.channel().closeFuture().sync();
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
        //super.channelRead(ctx, msg);
        System.out.println("server: channel read");
        ByteBuf buf = (ByteBuf) msg;
        String string = buf.toString(CharsetUtil.UTF_8);
        System.out.printf("客户端请求数据：【%s】\n", string);

        Person person = new Person(88, string, 88888);
        ChannelFuture channelFuture = ctx.writeAndFlush(string);
        channelFuture.addListener(future -> {
           assert channelFuture == future;
           ctx.close();
        });
        System.out.println("服务端响应完成。。。。。。。。。。。。。");
//        ctx.close();
        //buf.release();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
