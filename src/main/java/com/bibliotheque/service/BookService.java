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
import java.util.Arrays;
import java.util.List;


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

    public Flux<String> findAuthors() {
        return bookRepository.findAuthors();
    }

    public Flux<String> findTitles() {
        return bookRepository.findtitles();
    }

    public Flux<String> findEpochs() {
        return bookRepository.findEpochs();
    }

    public Flux<String> findTypes() {
        return bookRepository.findTypes();
    }

    public Flux<String> findSubTypes() {
        return bookRepository.findSubTypes();
    }

    public Flux<String> findReaderCategories() {
        return bookRepository.findReaderCategories();
    }

    public Flux<String> findNationalities() {
        return bookRepository.findNationalities();
    }

    public Mono<List<String>> findStatuses() {
        return Mono.just(Arrays.stream(BookStatus.values()).map(BookStatus::name).toList());
    }

    public Flux<BookResponse> findBooks(BookStatus bookStatus) {
        return bookRepository
                .findByStatus(bookStatus.name())
                .map(this::convertIntoResponse);
    }

    public Flux<BookResponse> findBooksToComplete() {
        return bookRepository
                .findBooksToComplete()
                .map(this::convertIntoResponse);
    }

    public Flux<BookResponse> findBooksByFilters(BookRequest bookRequest) {
        return bookRepository
                .findFiltered(
                        bookRequest.author(),
                        bookRequest.type(),
                        bookRequest.subType(),
                        bookRequest.readerCategory(),
                        bookRequest.nationality(),
                        bookRequest.status() != null ? bookRequest.status().name() : null)
                .map(this::convertIntoResponse);
    }

    public Mono<BookResponse> updateBook(long id, BookRequest bookRequest) {
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Book with id : " + id + " does not exist")))
                .flatMap(bookToUpdate -> {
                            Book bookToSave = Book.builder()
                                    .id(id)
                                    .isbn10(bookRequest.isbn10() != null ? bookRequest.isbn10() : bookToUpdate.getIsbn10())
                                    .isbn13(bookRequest.isbn13() != null ? bookRequest.isbn13() : bookToUpdate.getIsbn13())
                                    .imageLink(bookRequest.imageLink() != null ? bookRequest.imageLink() : bookToUpdate.getImageLink())
                                    .title(bookRequest.title() != null ? bookRequest.title() : bookToUpdate.getTitle())
                                    .author(bookRequest.author() != null ? bookRequest.author() : bookToUpdate.getTitle())
                                    .epoch(bookRequest.epoch() != null ? bookRequest.epoch() : bookToUpdate.getEpoch())
                                    .nationality(bookRequest.nationality() != null ? bookRequest.nationality() : bookToUpdate.getNationality())
                                    .type(bookRequest.type() != null ? bookRequest.type() : bookToUpdate.getType())
                                    .subType(bookRequest.subType() != null ? bookRequest.subType() : bookToUpdate.getSubType())
                                    .readerCategory(bookRequest.readerCategory() != null ? bookRequest.readerCategory() : bookToUpdate.getReaderCategory())
                                    .comment(bookRequest.comment() != null ? bookRequest.comment() : bookToUpdate.getComment())
                                    .description(bookRequest.description() != null ? bookRequest.description() : bookToUpdate.getDescription())
                                    .refBibli(bookRequest.refBibli() != null ? bookRequest.refBibli() : bookToUpdate.getRefBibli())
                                    .creationDate(bookToUpdate.getCreationDate())
                                    .lastModificationDate(LocalDateTime.now())
                                    .status(bookRequest.status() != null ? bookRequest.status() : bookToUpdate.getStatus())
                                    .build();
                            return bookRepository.save(bookToSave).map(this::convertIntoResponse);
                        }
                );
    }

    public Mono<BookResponse> updateBookStatus(long id, BookStatus status) {
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Book with id : " + id + " does not exist")))
                .flatMap(bookToUpdate -> {
                    Book bookToSave = new Book(
                            id,
                            bookToUpdate.getIsbn10(),
                            bookToUpdate.getIsbn13(),
                            bookToUpdate.getImageLink(),
                            bookToUpdate.getTitle(),
                            bookToUpdate.getAuthor(),
                            bookToUpdate.getEpoch(),
                            bookToUpdate.getNationality(),
                            bookToUpdate.getType(),
                            bookToUpdate.getSubType(),
                            bookToUpdate.getReaderCategory(),
                            bookToUpdate.getComment(),
                            bookToUpdate.getDescription(),
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
                .isbn10(bookRequest.isbn10())
                .isbn13(bookRequest.isbn13())
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
                .isbn10(book.getIsbn10())
                .isbn13(book.getIsbn13())
                .imageLink(book.getImageLink())
                .title(book.getTitle())
                .author(book.getAuthor())
                .epoch(book.getEpoch())
                .nationality(book.getNationality())
                .type(book.getType())
                .subType(book.getSubType())
                .readerCategory(book.getReaderCategory())
                .comment(book.getComment())
                .description(book.getDescription())
                .refBibli(book.getRefBibli())
                .creationDate(book.getCreationDate())
                .lastModificationDate(book.getLastModificationDate())
                .status(book.getStatus())
                .build();
    }
}
