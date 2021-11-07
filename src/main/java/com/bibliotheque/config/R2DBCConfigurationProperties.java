package com.bibliotheque.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "spring.r2dbc")
@Validated
@Getter
@Setter
public class R2DBCConfigurationProperties {

    private String url;

    private String username;

    private String password;

}
