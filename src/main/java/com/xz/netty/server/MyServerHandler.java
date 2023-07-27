package com.xz.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

/**
 * @author xz
 * @date 2023/7/27 10:46
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {
    ChannelGroup group = null;
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(channelHandlerContext.channel().remoteAddress());
        System.out.println(s);

        channelHandlerContext.channel().writeAndFlush("server write");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        System.out.println("error");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        group = new DefaultChannelGroup(ctx.executor());
        super.channelReadComplete(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
    }
}
