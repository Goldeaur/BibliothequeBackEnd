package com.bibliotheque.repository;

import com.bibliotheque.model.dao.Credentials;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomCredentialsRepository extends ReactiveCrudRepository<Credentials, Long> {


}
