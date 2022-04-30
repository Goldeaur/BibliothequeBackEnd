package com.bibliotheque.service;

import com.bibliotheque.model.dao.Book;
import com.bibliotheque.model.dto.BookResponse;
import com.bibliotheque.repository.CustomBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class BookService {
    @Autowired
    private CustomBookRepository bookRepository;

    public Mono<Book> saveBook (Book book) {
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
