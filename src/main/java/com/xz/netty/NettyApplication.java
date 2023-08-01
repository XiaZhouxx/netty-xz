package com.xz.netty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Netty 主入口
 * @author xz
 * @date 2023/7/25 21:45
 */
@SpringBootApplication
public class NettyApplication {
    public static void main(String[] args) {
        new SpringApplication(NettyApplication.class).run(args);
    }
}
