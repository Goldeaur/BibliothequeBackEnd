package com.bibliotheque.repository;

import com.bibliotheque.model.dao.Reader;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CustomReaderRepository extends ReactiveCrudRepository<Reader, Long> {
    @Query("""
            SELECT * from bibliotheque.reader
            where last_name = :name;
            """)
    Flux<Reader> findByName (String name);

    @Query("""
            SELECT * from bibliotheque.reader
            where credentials_id = :credentialId;
            """)
    Mono<Reader> findByCredentials(Long credentialId);

    @Query("""
            SELECT * from bibliotheque.reader
            """)
    Flux<Reader> findAll();
}
