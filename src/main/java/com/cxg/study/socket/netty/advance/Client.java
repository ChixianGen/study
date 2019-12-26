package com.cxg.study.socket.netty.advance;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.ReferenceCountUtil;

import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        new Client().clientStart(9000);
    }

    private void clientStart(int port) {
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
                        pipeline.addLast(new ClientHandler());
                    }
                });
        try {
            ChannelFuture f = b.connect("192.168.0.14", port).sync();
            Scanner sc = new Scanner(System.in);
            while (sc.hasNextLine()) {
                f.channel().writeAndFlush(sc.nextLine() + "$");
            }
            // 尾部添加自定义分隔符；
//            f.channel().writeAndFlush("你好$");
//            f.channel().writeAndFlush("今天是星期三$");
//            f.channel().writeAndFlush("晚上去健身房锻炼$");

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workers.shutdownGracefully();
        }

    }

}

class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            String string = (String) msg;
//            Person person = new Gson().fromJson(string, Person.class);
//            System.out.printf("服务端响应数据：【%s】\n", person);
            System.out.printf("服务端响应数据：【%s】\n", string);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
