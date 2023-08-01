package com.xz.netty.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author xz
 * @date 2023/7/31 9:08
 */
@Component
@Slf4j
public class ChatServerStarter implements ApplicationRunner {
    @Resource
    ChatServer chatServer;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Thread thread = new Thread(chatServer);
        thread.setName("netty-websocket");
        thread.start();
    }
}
