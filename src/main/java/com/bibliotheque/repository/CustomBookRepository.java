package com.bibliotheque.repository;

import com.bibliotheque.model.dao.Book;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CustomBookRepository extends ReactiveCrudRepository<Book, Long> {


    @Query("""           
            Select * from Bibliotheque.book
            where description is null;
            """)
    Flux<Book> findBooksToComplete();

    @Query("""           
            Select * from Bibliotheque.book
            where title = :title;
            """)
    Flux<Book> findByTitle (String title);

    @Query("""           
            Select * from Bibliotheque.book
            where author = :author;
            """)
    Flux<Book> findByAuthor (String author);

    @Query("""           
            Select * from Bibliotheque.book
            where type = :type;
            """)
    Flux<Book> findByType (String type);
}
