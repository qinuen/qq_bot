package com.record.record_history;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.record.record_history.dao")
@SpringBootApplication
public class RecordHistoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecordHistoryApplication.class, args);
    }

}
