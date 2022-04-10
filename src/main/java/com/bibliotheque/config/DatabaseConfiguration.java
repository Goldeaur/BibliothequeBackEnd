package com.bibliotheque.config;


import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
@EnableR2dbcRepositories
public class DatabaseConfiguration extends AbstractR2dbcConfiguration {

    @Autowired
    private R2DBCConfigurationProperties r2DBCConfigurationProperties;

    @Autowired
    private Environment env;

/*    public DatabaseConfiguration(R2DBCConfigurationProperties r2DBCConfigurationProperties, Environment env) {
        this.r2DBCConfigurationProperties = r2DBCConfigurationProperties;
        this.env = env;
    }*/

    @Bean
    @Override
    public ConnectionFactory connectionFactory() {
        ConnectionFactoryOptions baseOptions = ConnectionFactoryOptions.parse(r2DBCConfigurationProperties.getUrl());
        ConnectionFactoryOptions.Builder ob = ConnectionFactoryOptions.builder().from(baseOptions)
                .option(ConnectionFactoryOptions.USER, r2DBCConfigurationProperties.getUsername())
                .option(ConnectionFactoryOptions.PASSWORD, r2DBCConfigurationProperties.getPassword())
                .option(ConnectionFactoryOptions.SSL, false);
        ConnectionFactory connectionFactory =
                ConnectionFactories.get(ob.build());
        ConnectionPoolConfiguration configuration = ConnectionPoolConfiguration.builder(connectionFactory)
                .maxIdleTime(Duration.ofMinutes(Integer.valueOf(env.getProperty("spring.r2dbc.pool.maxIdleTime"))))
                .initialSize(Integer.valueOf(env.getProperty("spring.r2dbc.pool.initialSize")))
                .maxSize(Integer.valueOf(env.getProperty("spring.r2dbc.pool.maxSize")))
               .maxCreateConnectionTime(Duration.ofSeconds(Integer.valueOf(env.getProperty("spring.r2dbc.pool.maxCreateConnectionTime"))))
                .build();
        return new ConnectionPool(configuration);
    }

    @Override
    protected List<Object> getCustomConverters() {
        List<Object> converterList = new ArrayList<>();
        //TODO
        // create converters and add to List

        return converterList;
    }


}


