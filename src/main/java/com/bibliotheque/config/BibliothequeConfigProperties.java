package com.bibliotheque.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@PropertySource("classpath:application.properties")
@ConfigurationProperties("bibliotheque")
@Validated
@Getter
@Setter
public class BibliothequeConfigProperties {
    private String userActuator;
    private String passwordActuator;
}
