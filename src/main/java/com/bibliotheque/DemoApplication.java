package com.bibliotheque;

import com.bibliotheque.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan("com.bibliotheque")
public class DemoApplication {

    @Autowired
    SecurityConfig securityConfig;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
