package com.xz.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;

/**a
 * @author xz
 * @date 2023/7/31 17:44
 */
@Slf4j
public class WebSocketChatServerHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    private static ChannelGroup chatGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        if (frame instanceof TextWebSocketFrame) {
            // Send the uppercase string back.
            String request = ctx.channel().remoteAddress() + ": " + ((TextWebSocketFrame) frame).text();
            chatGroup.writeAndFlush(new TextWebSocketFrame(request.toUpperCase(Locale.US)));
        } else {
            log.error("unsupported frame type: " + frame.getClass().getName());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        String request = ctx.channel().remoteAddress() + ": 已离开群聊";
        chatGroup.writeAndFlush(new TextWebSocketFrame(request.toUpperCase(Locale.US)));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("client added :" + channel.remoteAddress());

        String request = ctx.channel().remoteAddress() + ": 已加入聊天";
        chatGroup.writeAndFlush(new TextWebSocketFrame(request.toUpperCase(Locale.US)));

        chatGroup.add(ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 上下线对客户端状态作标识.
        log.info(ctx.channel().remoteAddress() + " 已上线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info(ctx.channel().remoteAddress() + " 已下线");
    }
}
