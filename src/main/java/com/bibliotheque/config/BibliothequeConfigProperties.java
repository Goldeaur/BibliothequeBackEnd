package com.bibliotheque.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.security.user")
@Getter
@Setter
public class BibliothequeConfigProperties {
    private String user;
    private String password;

}
