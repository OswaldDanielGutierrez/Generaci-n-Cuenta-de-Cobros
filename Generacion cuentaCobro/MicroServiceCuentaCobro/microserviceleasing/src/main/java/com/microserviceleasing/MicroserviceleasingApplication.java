package com.microserviceleasing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MicroserviceleasingApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceleasingApplication.class, args);
    }

}
