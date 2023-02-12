package com.bibliotheque.repository;

import com.bibliotheque.model.dao.Book;
import com.bibliotheque.model.dto.BookResponse;
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
            where title like '%:title%' order by title;
            """)
    Flux<Book> findByTitle(String title);

    @Query("""           
            Select * from Bibliotheque.book
            where author like '%:author%' order by author;
            """)
    Flux<Book> findByAuthor(String author);

    @Query("""           
            Select * from Bibliotheque.book
            where nationality = :nationality order by title;
            """)
    Flux<Book> findByNationality(String nationality);

    @Query("""           
            Select * from Bibliotheque.book
            where reader_category = :readerCategory order by title;
            """)
    Flux<Book> findByReaderCategory(String readerCategory);

    @Query("""
            Select * from Select * from Bibliotheque.book
            where status = :status;
            """)
    Flux<Book> findByStatus(String status);

    @Query("""           
            Select * from Bibliotheque.book
            where type = :type;
            """)
    Flux<Book> findByType(String type);

    @Query("""           
            Select * from Bibliotheque.book
            where sub_type = :subType;
            """)
    Flux<Book> findBySubType(String subType);

    @Query("""           
            Select * from Bibliotheque.book
            where epoch = :epoch;
            """)
    Flux<Book> findBySEpoch(String epoch);


    @Query("""
            select DISTINCT author from Bibliotheque.book;
            """)
    Flux<String> findAuthors();

    @Query("""
            select DISTINCT title from Bibliotheque.book;
            """)
    Flux<String> findtitles();

    @Query("""
            select DISTINCT epoch from Bibliotheque.book;
            """)
    Flux<String> findEpochs();

    @Query("""
            select DISTINCT type from Bibliotheque.book;
            """)
    Flux<String> findTypes();

    @Query("""
            select DISTINCT sub_type from Bibliotheque.book;
            """)
    Flux<String> findSubTypes();

    @Query("""
            select DISTINCT reader_category from Bibliotheque.book;
            """)
    Flux<String> findReaderCategories();

    @Query("""
            select DISTINCT nationality from Bibliotheque.book;
            """)
    Flux<String> findNationalities();

    @Query("""
            select * from Bibliotheque.book where
            author = coalesce(:author, author) AND
            type = coalesce(:type, type) AND
            nationality = coalesce(:nationality, nationality) AND
            sub_type = coalesce(:subtype, sub_type) AND
            reader_category = coalesce(:readerCategory, reader_category) AND
            status = coalesce(:status, status)
            ;
            """)
    Flux<Book> findFiltered(String author, String type, String subtype, String readerCategory, String nationality, String status);
}
