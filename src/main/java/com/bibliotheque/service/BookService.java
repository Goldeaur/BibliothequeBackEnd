package com.bibliotheque.service;

import com.bibliotheque.model.dao.Book;
import com.bibliotheque.model.dto.BookRequest;
import com.bibliotheque.model.dto.BookResponse;
import com.bibliotheque.model.statuses.BookStatus;
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

    public Mono<Book> findById(Long id){
        return bookRepository.findById(id);
    }

    public Flux<BookResponse> findAll(){
        return bookRepository.findAll().map(
                this::convertIntoResponse
        );
    }

    private Book convertIntoDao(BookRequest bookRequest){
        return Book.builder()
                .isbn(bookRequest.getIsbn())
                .title(bookRequest.getTitle())
                .author(bookRequest.getAuthor())
                .epoch(bookRequest.getEpoch())
                .nationality(bookRequest.getNationality())
                .type(bookRequest.getType())
                .subType(bookRequest.getSubType())
                .readerCategory(bookRequest.getReaderCategory())
                .comment(bookRequest.getComment())
                .refBibli(bookRequest.getRefBibli())
                .creationDate(LocalDateTime.now())
                .lastModificationDate(LocalDateTime.now())
                .status(BookStatus.AVAILABLE)
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
