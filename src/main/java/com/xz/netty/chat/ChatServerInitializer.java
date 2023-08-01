package com.xz.netty.chat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author xz
 * @date 2023/7/31 17:41
 */
@Component
public class ChatServerInitializer extends ChannelInitializer<SocketChannel> {
    @Value("${xz.netty.protocol.websocket.path:/websocket}")
    String websocketPath;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
//        pipeline.addLast(new WebSocketIndexPageHandler(websocketPath));
        pipeline.addLast(new WebSocketServerCompressionHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler(websocketPath, null, true));
        pipeline.addLast(new WebSocketChatServerHandler());
    }
}
