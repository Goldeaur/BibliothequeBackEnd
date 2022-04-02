package com.bibliotheque.repository;

import com.bibliotheque.model.dao.Reader;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface CustomReaderRepository extends ReactiveCrudRepository<Reader, Long> {

    @Query("INSERT INTO reader" +
            " (first_name, last_name, city, creation_date, last_modification_date, status, credentials_id)" +
            " VALUES " +
            " (:firstName, :lastName, :city, :now, :now, :status, :credentialsId)")
    Mono<Reader> createReader(String firstName, String lastName, String city, String status, LocalDateTime now, Long credentialsId);

}
