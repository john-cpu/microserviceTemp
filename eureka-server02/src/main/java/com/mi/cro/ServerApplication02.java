package com.mi.cro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author zhanghonglin
 * @version 2.0
 * @Date ,
 * @since 2.0
 */
@SpringBootApplication
@EnableEurekaServer
public class ServerApplication02 {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication02.class,args);
    }
}
