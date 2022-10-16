package com.bibliotheque.repository;

import com.bibliotheque.model.dao.Credentials;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomCredentialsRepository extends ReactiveCrudRepository<Credentials, Long> {

    @Query("""
            select * from Bibliotheque.credentials
            where email = :email;
            """)
        Mono<Credentials> findByEmail(String email);
    @Query("""
            select password from Bibliotheque.credentials
            where email = :email;
            """)
        Mono<String> findPasswordByEmail(String email);

}