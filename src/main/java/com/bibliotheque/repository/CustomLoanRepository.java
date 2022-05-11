package com.bibliotheque.repository;

import com.bibliotheque.model.dao.Loan;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CustomLoanRepository extends ReactiveCrudRepository<Loan, Long> {

    @Query("""
            Select * from loan
            where status = ONGOING;
            """)
    Flux<Loan> findAllOnGoing();

    @Query("""
            Select * from loan
            where status = LATE;
            """)
    Flux<Loan> findAllLate();

    @Query("""
            Select * from loan
            where status = TODAY;
            """)
    Flux<Loan> findAllTODAY();

    @Query("""
            Select * from loan
            where reader_id = :readerId;
            """)
   Flux<Loan> findByReaderId(long readerId);
}
