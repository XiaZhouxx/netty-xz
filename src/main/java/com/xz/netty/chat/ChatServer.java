package com.xz.netty.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xz
 * @date 2023/7/31 14:15
 */
@Component
@Slf4j
public class ChatServer implements Runnable {
    @Value("${xz.netty.protocol.websocket.port:8080}")
    int port;
    @Resource
    ChatServerInitializer serverInitializer;

    @Override
    public void run() {
        log.info("netty server starting....");
        boolean SSL = false;
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(serverInitializer);

            Channel ch = b.bind(port).sync().channel();

            log.info("Open your web browser and navigate to " +
                    (SSL? "https" : "http") + "://127.0.0.1:" + port + '/');

            ch.closeFuture().sync();
        } catch (Exception e) {
          log.error(e.getMessage());
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
