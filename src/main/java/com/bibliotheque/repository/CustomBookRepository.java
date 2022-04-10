package com.bibliotheque.repository;

import com.bibliotheque.model.dao.Book;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomBookRepository extends ReactiveCrudRepository<Book, Long> {
}
