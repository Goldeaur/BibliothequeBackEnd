package com.bibliotheque.service;

import com.bibliotheque.model.dao.Book;
import com.bibliotheque.model.dto.BookRequest;
import com.bibliotheque.model.dto.BookResponse;
import com.bibliotheque.repository.CustomBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;


@Service
public class BookService {
    @Autowired
    private CustomBookRepository bookRepository;

    public Mono<Book> saveBook (BookRequest bookRequest) {
        Book book = convertIntoDao(bookRequest);
        return bookRepository.save(book);
    }

    public Mono<Book> updateBook (long id, BookRequest bookRequest) {




        return bookRepository.updateBook(id,
                bookRequest.isbn(),
                bookRequest.title(),
                bookRequest.author(),
                bookRequest.epoch(),
                bookRequest.nationality(),
                bookRequest.type(),
                bookRequest.subType(),
                bookRequest.readerCategory(),
                bookRequest.comment(),
                bookRequest.refBibli(),
                LocalDateTime.now(),
                bookRequest.status()
                );
    }

    public Mono<Book> findById(long id){
        return bookRepository.findById(id);
    }

    public Flux<BookResponse> findAll(){
        return bookRepository.findAll().map(
                this::convertIntoResponse
        );
    }

    private Book convertIntoDao(BookRequest bookRequest){
        return Book.builder()
                .isbn(bookRequest.isbn())
                .title(bookRequest.title())
                .author(bookRequest.author())
                .epoch(bookRequest.epoch())
                .nationality(bookRequest.nationality())
                .type(bookRequest.type())
                .subType(bookRequest.subType())
                .readerCategory(bookRequest.readerCategory())
                .comment(bookRequest.comment())
                .refBibli(bookRequest.refBibli())
                .creationDate(LocalDateTime.now())
                .lastModificationDate(LocalDateTime.now())
                .status(bookRequest.status())
                .build();
    }

    private BookResponse convertIntoResponse(Book book){
        return BookResponse.builder()
                .id(book.getId())
                .isbn(book.getIsbn())
                .author(book.getAuthor())
                .epoch(book.getEpoch())
                .nationality(book.getNationality())
                .type(book.getType())
                .subType(book.getSubType())
                .readerCategory(book.getReaderCategory())
                .comment(book.getComment())
                .refBibli(book.getRefBibli())
                .creationDate(book.getCreationDate())
                .lastModificationDate(book.getLastModificationDate())
                .status(book.getStatus())
                .build();
    }
}
