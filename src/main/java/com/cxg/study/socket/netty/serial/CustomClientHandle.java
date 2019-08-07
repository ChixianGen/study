package com.cxg.study.socket.netty.serial;   // Administrator 于 2019/8/7 创建;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * 自定义客户端处理器
 */
public class CustomClientHandle extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("=-=-=-=-=-=【channelRegistered】=-=-=-=-=-=");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("=-=-=-=-=-=【channelUnregistered】=-=-=-=-=-=");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("=-=-=-=-=-=【channelActive】=-=-=-=-=-=");
        SerialObj serialObj = new SerialObj("池贤根", 10000);
        ctx.writeAndFlush(serialObj);
        System.out.printf("=-=-=-=-=-=【发送数据成功】：【%s】=-=-=-=-=-=", serialObj);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("=-=-=-=-=-=【channelInactive】=-=-=-=-=-=");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("=-=-=-=-=-=【channelRead】=-=-=-=-=-=");
        try {
            SerialObj data = (SerialObj) msg;
            System.out.printf("服务端响应数据：【%s】\n", data);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("=-=-=-=-=-=【channelReadComplete】=-=-=-=-=-=");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
