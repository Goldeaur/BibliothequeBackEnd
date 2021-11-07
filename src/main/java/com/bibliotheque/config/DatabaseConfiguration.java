package com.bibliotheque.config;


import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.pool.PoolingConnectionFactoryProvider;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Option;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
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
public class DatabaseConfiguration extends AbstractR2dbcConfiguration {

    private final R2DBCConfigurationProperties r2DBCConfigurationProperties;
    private final Environment env;

    public DatabaseConfiguration(R2DBCConfigurationProperties r2DBCConfigurationProperties, Environment env) {
        this.r2DBCConfigurationProperties = r2DBCConfigurationProperties;
        this.env = env;
    }

    @Override
    @Bean
        public ConnectionFactory connectionFactory() {



/*        ConnectionFactoryOptions baseOptions = ConnectionFactoryOptions.parse(
                //r2DBCConfigurationProperties.getUrl()
                String.valueOf(env.getProperty("spring.spring.r2dbc.url"))
        );
        ConnectionFactory connectionFactory =
                MySqlConnectioFactory.from(MySqlConnectionConfiguration)


                buildConnectionFactory(baseOptions);
        ConnectionPoolConfiguration configuration = ConnectionPoolConfiguration.builder(connectionFactory)
                .maxIdleTime(Duration.ofMinutes(Integer.valueOf(env.getProperty("spring.r2dbc.pool.maxIdleTime"))))
                .initialSize(Integer.valueOf(env.getProperty("spring.r2dbc.pool.initialSize")))
                .maxSize(Integer.valueOf(env.getProperty("spring.r2dbc.pool.maxSize")))
                .maxCreateConnectionTime(Duration.ofSeconds(Integer.valueOf(env.getProperty("spring.r2dbc.pool.maxCreateConnectionTime"))))
                .build();
        return new ConnectionPool(configuration);*/
        return null;
    }

//    @NotNull
//    private ConnectionFactory buildConnectionFactory(ConnectionFactoryOptions baseOptions) {
//        return ConnectionFactories.get(ConnectionFactoryOptions.builder().from(baseOptions)
//                .option(ConnectionFactoryOptions.USER, env.getProperty("spring.r2dbc.username"))
//                .option(ConnectionFactoryOptions.PASSWORD, env.getProperty("spring.r2dbc.password"))
//                .option(ConnectionFactoryOptions.DATABASE, env.getProperty("cloud.aws.rds.instances[0].databaseName"))
//                .option(ConnectionFactoryOptions.DRIVER, "mysql")
//                .option(PoolingConnectionFactoryProvider.MAX_SIZE, Integer.valueOf(env.getProperty("spring.r2dbc.pool.maxSize")))
//                .build());
//    }

    @Override
    protected List<Object> getCustomConverters() {
        List<Object> converterList = new ArrayList<Object>();
        //TODO
        // create converters and add to List

        return converterList;
    }


}


