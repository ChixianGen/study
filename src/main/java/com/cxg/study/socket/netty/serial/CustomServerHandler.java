package com.cxg.study.socket.netty.serial;   // Administrator 于 2019/8/7 创建;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 自定义服务端处理器；处理具体数据
 */
public class CustomServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SerialObj data = (SerialObj) msg;

        data.setName("【" + data.getName() + "】");
        data.setSalary(data.getSalary() + 1000);
        ctx.writeAndFlush(data).addListener(ChannelFutureListener.CLOSE);
    }
}
