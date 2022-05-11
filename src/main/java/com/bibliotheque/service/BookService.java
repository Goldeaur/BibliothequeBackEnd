package com.bibliotheque.service;

import com.bibliotheque.exception.ResourceNotFoundException;
import com.bibliotheque.model.dao.Book;
import com.bibliotheque.model.dto.BookRequest;
import com.bibliotheque.model.dto.BookResponse;
import com.bibliotheque.model.statuses.BookStatus;
import com.bibliotheque.repository.CustomBookRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;


@Service
public class BookService {

    private final CustomBookRepository bookRepository;

    public BookService(CustomBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Mono<BookResponse> saveBook(BookRequest bookRequest) {
        Book book = convertIntoDao(bookRequest);
        return bookRepository.save(book).map(this::convertIntoResponse);
    }
    
    public Mono<BookResponse> updateBook(long id, BookRequest bookRequest) {
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Book with id : " + id + " does not exist")))
                .flatMap(bookToUpdate -> {
                    Book bookToSave = new Book(
                            id,
                            bookRequest.isbn() != null ? bookRequest.isbn() : bookToUpdate.getIsbn(),
                            bookRequest.title() != null ? bookRequest.title() : bookToUpdate.getTitle(),
                            bookRequest.author() != null ? bookRequest.author() : bookToUpdate.getTitle(),
                            bookRequest.epoch() != null ? bookRequest.epoch() : bookToUpdate.getEpoch(),
                            bookRequest.nationality() != null ? bookRequest.nationality() : bookToUpdate.getNationality(),
                            bookRequest.type() != null ? bookRequest.type() : bookToUpdate.getType(),
                            bookRequest.subType() != null ? bookRequest.subType() : bookToUpdate.getSubType(),
                            bookRequest.readerCategory() != null ? bookRequest.readerCategory() : bookToUpdate.getReaderCategory(),
                            bookRequest.comment() != null ? bookRequest.comment() : bookToUpdate.getComment(),
                            bookRequest.refBibli() != null ? bookRequest.refBibli() : bookToUpdate.getRefBibli(),
                            bookToUpdate.getCreationDate(),
                            LocalDateTime.now(),
                            bookRequest.status() != null ? bookRequest.status() : bookToUpdate.getStatus()
                    );
                    return bookRepository.save(bookToSave).map(this::convertIntoResponse);
                }
        );
    }

    public Mono<BookResponse> updateBookStatus (long id, BookStatus status){
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Book with id : " + id + " does not exist")))
                .flatMap(bookToUpdate -> {
                    Book bookToSave = new Book(
                            id,
                            bookToUpdate.getIsbn(),
                            bookToUpdate.getTitle(),
                            bookToUpdate.getTitle(),
                            bookToUpdate.getEpoch(),
                            bookToUpdate.getNationality(),
                            bookToUpdate.getType(),
                            bookToUpdate.getSubType(),
                            bookToUpdate.getReaderCategory(),
                            bookToUpdate.getComment(),
                            bookToUpdate.getRefBibli(),
                            bookToUpdate.getCreationDate(),
                            LocalDateTime.now(),
                            status != null ? status : bookToUpdate.getStatus()
                    );
                    return bookRepository.save(bookToSave).map(this::convertIntoResponse);
                });
    }


    public Mono<BookResponse> findById(Long id) {
        return bookRepository.findById(id).map(this::convertIntoResponse);
    }

    public Flux<BookResponse> findAll() {
        return bookRepository.findAll().map(
                this::convertIntoResponse
        );
    }

    private Book convertIntoDao(BookRequest bookRequest) {
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

    private BookResponse convertIntoResponse(Book book) {
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
