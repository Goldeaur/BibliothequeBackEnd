package com.bibliotheque.repository;

import com.bibliotheque.model.dao.Loan;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomLoanRepository extends ReactiveCrudRepository<Loan, Long> {

    @Query("""
            Select * from from bibliotheque.loan
            where status = ONGOING;
            """)
    Flux<Loan> findAllOnGoing();

    @Query("""
            Select * from bibliotheque.loan
            where status = LATE;
            """)
    Flux<Loan> findAllLate();

    @Query("""
            Select * from bibliotheque.loan
            where status = TODAY;
            """)
    Flux<Loan> findAllTODAY();

    @Query("""
            Select * from bibliotheque.loan
            where reader_id = :readerId;
            """)
   Flux<Loan> findByReaderId(long readerId);

    @Query("""
            Select * from bibliotheque.loan
            where book_id = : bookId;
            """)
    Mono<Loan> findByBookId(long bookId);
}
