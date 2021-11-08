package com.bibliotheque.config;


import io.r2dbc.pool.PoolingConnectionFactoryProvider;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Option;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
@EnableWebFlux
@ComponentScan("com.bibliotheque")
public class DatabaseConfiguration extends AbstractR2dbcConfiguration {

    private final R2DBCConfigurationProperties r2DBCConfigurationProperties;

    private final Environment env;

    public DatabaseConfiguration(R2DBCConfigurationProperties r2DBCConfigurationProperties, Environment env) {
        this.r2DBCConfigurationProperties = r2DBCConfigurationProperties;
        this.env = env;
    }


    @Bean
    @Override
    public ConnectionFactory connectionFactory() {
        ConnectionFactoryOptions baseOptions = ConnectionFactoryOptions.parse(r2DBCConfigurationProperties.getUrl());
        ConnectionFactoryOptions.Builder ob = ConnectionFactoryOptions.builder().from(baseOptions)
                .option(ConnectionFactoryOptions.USER, r2DBCConfigurationProperties.getUsername())
                .option(ConnectionFactoryOptions.PASSWORD, r2DBCConfigurationProperties.getPassword())
                .option(ConnectionFactoryOptions.SSL, false);

        return ConnectionFactories.get(ob.build());
    }

    @Override
    protected List<Object> getCustomConverters() {
        List<Object> converterList = new ArrayList<>();
        //TODO
        // create converters and add to List
        //converterList.add(new ReaderreadConverter());

        return converterList;
    }


}


