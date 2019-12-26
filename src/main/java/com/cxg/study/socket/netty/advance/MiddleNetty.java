package com.cxg.study.socket.netty.advance;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class MiddleNetty {
    public static void main(String[] args) {
//        Map<String, ChannelFuture> futureMap = new ConcurrentHashMap<>(2);
//        MiddleClient middleClient = new MiddleClient(futureMap);
//        middleClient.clientStart(9999);

        MiddleServerHandler serverHandler = new MiddleServerHandler();
//        serverHandler.setClientFuture(middleClient.getFuture());

        MiddleNettyServer middleNettyServer = new MiddleNettyServer(9000);
        middleNettyServer.serverStart(serverHandler);
    }
}

@Data
class MiddleNettyServer {
    private int port;
    private ChannelFuture serverFuture;

    public MiddleNettyServer(int port) {
        this.port = port;
    }

    public void serverStart(MiddleServerHandler serverHandler) {
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
                        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                        // 可以直接写入string类型数据；
                        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));

                        // 添加自己的消息处理器；
                        pipeline.addLast(serverHandler);
                    }
                });
        try {
            serverFuture = b.bind("192.168.0.14", port).sync();

            serverFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}

@Setter
class MiddleServerHandler extends ChannelInboundHandlerAdapter {

    private ChannelFuture clientFuture;
//
//    public MiddleServerHandler(ChannelFuture clientFuture) {
//        this.clientFuture = clientFuture;
//    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println("server: channel read");
        String string = (String) msg;

//        client.forwark("MiddleClient - 转发" + msg + "$");
        new Thread(() -> {
            MiddleClient middleClient = new MiddleClient(ctx, string);
            middleClient.clientStart(9999);
        }).start();
//        System.out.printf("客户端请求数据：【%s】\n", new Gson().fromJson(string, Person.class));
        System.out.printf("客户端请求数据：【%s】\n", string);

//        Person person = new Person(88, string, 88888);
        ChannelFuture channelFuture = ctx.writeAndFlush("MiddleServer - " + string + "$");
//        ChannelFuture channelFuture = ctx.writeAndFlush(new Gson().toJson(person));

        // 服务端响应完成，自动关闭连接；
//        channelFuture.addListener(ChannelFutureListener.CLOSE);
//        System.out.println("服务端响应客户端完成，关闭客户端连接");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

@Getter
class MiddleClient {

    private String msg;
    private ChannelHandlerContext ctx;
//    private Map<String, ChannelFuture> futureMap;

    public MiddleClient(ChannelHandlerContext ctx, String msg) {
        this.msg = msg;
        this.ctx = ctx;
    }

    private ChannelFuture future;

//    public void forwark(String msg) {
//        future.channel().writeAndFlush(msg);
//    }

    public void clientStart(int port) {
        EventLoopGroup workers = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(workers)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();

                        // 自定义【消息后缀分隔符】，用于解决沾包拆包问题；
                        ByteBuf byteBuf = Unpooled.copiedBuffer("$".getBytes());
                        pipeline.addLast(new DelimiterBasedFrameDecoder(1024, byteBuf));

                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new MiddleClientHandler(msg, ctx));
                    }
                });
        try {
            future = b.connect("192.168.0.14", port).sync();
//            futureMap.put("client", future);
//            Scanner sc = new Scanner(System.in);
//            while (sc.hasNextLine()) {
//                f.channel().writeAndFlush(sc.nextLine() + "$");
//            }
            // 尾部添加自定义分隔符；
            future.channel().writeAndFlush("MiddleClient转发 - " + msg + "$");
//            f.channel().writeAndFlush("今天是星期三$");
//            f.channel().writeAndFlush("晚上去健身房锻炼$");

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workers.shutdownGracefully();
        }

    }
}

@AllArgsConstructor
class MiddleClientHandler extends ChannelInboundHandlerAdapter {

    private String msg;
    private ChannelHandlerContext context;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            String string = (String) msg;
//            Person person = new Gson().fromJson(string, Person.class);
//            System.out.printf("服务端响应数据：【%s】\n", person);
            System.out.printf("MiddleClientHandler 接收 FinalHandler 响应数据：【%s】\n", string);
            context.channel().writeAndFlush(string + "$");
        } finally {
//            ReferenceCountUtil.release(msg);
        }
    }
}
