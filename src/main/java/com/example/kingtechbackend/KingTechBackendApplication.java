package com.example.kingtechbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class KingTechBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(KingTechBackendApplication.class, args);
    }

}
