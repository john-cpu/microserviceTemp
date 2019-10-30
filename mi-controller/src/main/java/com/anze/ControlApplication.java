package com.anze;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhanghonglin
 * @version 2.0
 * @Date ,
 * @since 2.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.base.client")
@ComponentScan(basePackages = "com")
public class ControlApplication {
    public static void main(String[] args) {
        SpringApplication.run(ControlApplication.class,args);
    }
/*    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }//2019-9-18*/
}
