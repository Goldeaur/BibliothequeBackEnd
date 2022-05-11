package com.bibliotheque.repository;

import com.bibliotheque.model.dao.Book;
import com.bibliotheque.model.statuses.BookStatus;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface CustomBookRepository extends ReactiveCrudRepository<Book, Long> {
    @Modifying
    @Query("""           
            UPDATE book SET
            isbn = :isbn,
            title = :title,
            author= :author,
            epoch = :epoch,
            nationality = :nationality,
            type = :type,
            sub_type = :subType,
            reader_category = :readerCategory,
            comment = :comment,
            ref_bibli = :refBibli,
            last_modification_date = :now,
            status = :status
            where id = :id;
            """)
    Mono<Object> updateBook(
            Long id,
            Long isbn,
            String title,
            String author,
            String epoch,
            String nationality,
            String type,
            String subType,
            String readerCategory,
            String comment,
            String refBibli,
            LocalDateTime now,
            BookStatus status
    );
}
