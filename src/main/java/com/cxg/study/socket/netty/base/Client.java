package com.cxg.study.socket.netty.base;

import com.cxg.study.socket.base.udp.Person;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class Client {
    public static void main(String[] args) {
        new Client().clientStart();
    }

    private void clientStart() {
        EventLoopGroup workers = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(workers)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        System.out.println("channel initialized!");
                        ch.pipeline().addLast(new ClientHandler());
                    }
                });
        try {
            System.out.println("start to connect...");
            ChannelFuture channelFuture1 = b.connect("127.0.0.1", 8888).sync();
            ChannelFuture channelFuture2 = b.connect("127.0.0.1", 9999).sync();

            System.out.println("阻塞地监听关闭事件");
            channelFuture1.channel().closeFuture().sync(); // 可理解为阻塞监听连接状态事件；
            channelFuture2.channel().closeFuture().sync(); // 可理解为阻塞监听连接状态事件；
            System.out.println("监听到关闭事件，断开连接，释放资源");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workers.shutdownGracefully();
        }
    }
}

class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel is activated.");
        final ChannelFuture f = ctx.writeAndFlush(Unpooled.copiedBuffer("Hello Netty Server".getBytes()));
        f.addListener(future -> {
            assert f == future;
            System.out.println("msg 发送成功!");
        });
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ByteBuf buf = (ByteBuf) msg;
            String string = buf.toString(CharsetUtil.UTF_8);
            System.out.printf("服务端响应数据：【%s】\n", string);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
