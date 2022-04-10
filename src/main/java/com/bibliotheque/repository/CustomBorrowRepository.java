package com.bibliotheque.repository;

import com.bibliotheque.model.dao.Borrow;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CustomBorrowRepository extends ReactiveCrudRepository<Borrow, Long> {
/*    @Query("INSERT INTO borrow" +
            " (borrow_date, return_date, reader_id, book_id)" +
            " VALUES " +
            " (:now, :returnDate, :city, :now, :now, :status, :credentialsId)")
    Mono<Borrow> createBorrow(LocalDateTime now, LocalDateTime returnDate, Long readerId, Long bookId, String status);*/
}
