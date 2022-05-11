package com.bibliotheque.repository;

import com.bibliotheque.model.dao.Reader;
import com.bibliotheque.model.statuses.ReaderStatus;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface CustomReaderRepository extends ReactiveCrudRepository<Reader, Long> {
    @Modifying
    @Query("""
            UPDATE reader SET
            first_name = :firstName,
            last_name = :lastName,
            city = :city,
            status = :status,
            last_modification_date = :now
            where id = :id
            """)
    Mono<Reader> updateReader(
            Long id,
            String firstName,
            String lastName,
            String city,
            ReaderStatus status,
            LocalDateTime now
    );
}
