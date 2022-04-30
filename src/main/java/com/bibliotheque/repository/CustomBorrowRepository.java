package com.bibliotheque.repository;

import com.bibliotheque.model.dao.Loan;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CustomBorrowRepository extends ReactiveCrudRepository<Loan, Long> {
/*    @Query("INSERT INTO borrow" +
            " (borrow_date, return_date, reader_id, book_id)" +
            " VALUES " +
            " (:now, :returnDate, :city, :now, :now, :status, :credentialsId)")
    Mono<Loan> createBorrow(LocalDateTime now, LocalDateTime returnDate, Long readerId, Long bookId, String status);*/
}
