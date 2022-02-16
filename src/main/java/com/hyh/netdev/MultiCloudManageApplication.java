package com.hyh.netdev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MultiCloudManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(MultiCloudManageApplication.class,args);
    }
}
