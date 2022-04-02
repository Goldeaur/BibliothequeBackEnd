package com.bibliotheque.repository;

import com.bibliotheque.model.dao.Credentials;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomCredentialsRepository extends ReactiveCrudRepository<Credentials, Long> {

    //Mono<Credentials> findByLogin(String login);

    //Mono<Credentials> saveCredentials (UUID id, String login, String password, String phone, String email, Date now, Role role);


}
