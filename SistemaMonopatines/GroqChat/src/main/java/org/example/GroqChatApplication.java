package org.example;

import org.example.config.FeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(defaultConfiguration = FeignConfig.class)
public class GroqChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(GroqChatApplication.class, args);
    }

}