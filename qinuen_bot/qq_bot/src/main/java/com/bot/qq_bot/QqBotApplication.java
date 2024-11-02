package com.bot.qq_bot;

import lombok.extern.slf4j.Slf4j;
import love.forte.simboot.spring.autoconfigure.EnableSimbot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableSimbot// 开启simbot
@EnableScheduling//开启定时任务
@EnableDiscoveryClient//开启服务治理
@EnableFeignClients
@Slf4j
@SpringBootApplication
public class QqBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(QqBotApplication.class, args);
        log.info("机器人启动成功！");
    }

}
