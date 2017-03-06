package com.jorge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients //It is necessary to use Feign, an easy way to call RESTful services
public class EurekaDiscoveryClientSentenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaDiscoveryClientSentenceApplication.class, args);
    }
}
