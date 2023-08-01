package com.xz.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author xz
 * @date 2023/7/26 10:13
 */
public class MyClient1 {
    public static void main(String[] args) {
        EventLoopGroup clientGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(clientGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new MyClientInitializer());

            ChannelFuture sync = bootstrap.connect("localhost", 8080).sync();
            Channel channel = sync.channel();
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                channel.writeAndFlush(input.readLine());
            }

        } catch (Exception e) {
            e.printStackTrace();
            clientGroup.shutdownGracefully();
        }
    }
}
