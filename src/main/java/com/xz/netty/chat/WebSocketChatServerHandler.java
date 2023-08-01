package com.xz.netty.chat;

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

/**
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
            String request = ((TextWebSocketFrame) frame).text();
            ctx.channel().writeAndFlush(new TextWebSocketFrame(request.toUpperCase(Locale.US)));
        } else {
            log.error("unsupported frame type: " + frame.getClass().getName());
        }
    }

//    @Override
//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
//            //Channel upgrade to websocket, remove WebSocketIndexPageHandler.
//            ctx.pipeline().remove(WebSocketIndexPageHandler.class);
//        } else {
//            super.userEventTriggered(ctx, evt);
//        }
//    }

}
