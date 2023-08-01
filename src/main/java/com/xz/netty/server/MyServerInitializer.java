package com.xz.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.StandardCharsets;

/**
 * 客户端初始化器
 * @author xz
 * @date 2023/7/27 10:44
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 错误的编排处理的执行顺序可能导致无法接收消息
        /**
         *             请求进入的顺序, 从上自下
         */
        socketChannel.pipeline()
                .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4))
                .addLast(new LengthFieldPrepender(4))
                .addLast(new StringDecoder(StandardCharsets.UTF_8))
                .addLast(new StringEncoder(StandardCharsets.UTF_8))
                .addLast(new MyServerHandler());
        /**
         *             写出的顺序, 从下自上, 所以需要编排注意pipeline中 in/outBound处理的的顺序
         */
        /**
         *
         * 客户端发送消息到服务端, 服务端对应的是ChannelInboundHandler, 读入
         *
         * request read -> LengthFieldBasedFrameDecoder(二进制固定长度解码) -> StringDecoder(解码为字符串) ->  MyServerHandler(具体的处理)
         *
         * 客户端对应的则是ChannelOutBoundHandler, 写出
         *
         * MyClientHandler -> StringEncoder -> LengthFieldPrepender ->  write
         *
         * 一个端的写入对应另一个端的写入, w/r, 且需要注意 客户端和服务端的编解码格式需要一致
         */
    }
}
