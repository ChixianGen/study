package com.cxg.study.socket.netty.advance;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


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
                        ChannelPipeline pipeline = ch.pipeline();

                        // 自定义【消息后缀分隔符】，用于解决沾包拆包问题；
                        ByteBuf byteBuf = Unpooled.copiedBuffer("$".getBytes());
                        pipeline.addLast(new DelimiterBasedFrameDecoder(1024, byteBuf));

                        // 解析string类型的数据
                        pipeline.addLast(new StringDecoder());
                        // 可以直接写入string类型数据；
                        pipeline.addLast(new StringEncoder());

                        // 添加自己的消息处理器；
                        pipeline.addLast(new Handler());
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
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server: channel read");
        String string = (String) msg;
//        System.out.printf("客户端请求数据：【%s】\n", new Gson().fromJson(string, Person.class));
        System.out.printf("客户端请求数据：【%s】\n", string);

//        Person person = new Person(88, string, 88888);
        ChannelFuture channelFuture = ctx.writeAndFlush("我是服务端响应字符串$");
//        ChannelFuture channelFuture = ctx.writeAndFlush(new Gson().toJson(person));

        // 服务端响应完成，自动关闭连接；
        channelFuture.addListener(ChannelFutureListener.CLOSE);
        System.out.println("服务端响应客户端完成，关闭客户端连接");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
