package com.bibliotheque.repository;

import com.bibliotheque.model.dao.Reader;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@EnableWebFlux
@EnableR2dbcRepositories
public class ReaderRepository {

    @Autowired
    private ConnectionFactory connectionFactory;

    public Flux<Reader> findAll() {
        return Mono.from(this.connectionFactory.create())
                .flatMap((c) -> Mono.from(c.createStatement("select id,firstname,lastname from reader")
                                .execute())
                        .doFinally((st) -> c.close())).flatMapMany(result -> Flux.from(result.map((row, meta) -> {
                    Reader acc = new Reader();
                    acc.setId(row.get("id", Long.class));
                    acc.setFirstName(row.get("firstname", String.class));
                    acc.setLastName(row.get("lastname", String.class));
                    return acc;
                })));
    }
}
