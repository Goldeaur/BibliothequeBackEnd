package com.bibliotheque.repository;

import com.bibliotheque.model.dao.Credentials;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CredentialsRepository extends ReactiveCrudRepository<Credentials, Long> {

    public Mono<Credentials> findByLogin (String login);


}
