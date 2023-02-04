package com.bibliotheque.controller;


import com.bibliotheque.model.dto.BookRequest;
import com.bibliotheque.model.dto.BookResponse;
import com.bibliotheque.model.statuses.BookStatus;
import com.bibliotheque.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(value="/book", produces="application/json")
@CrossOrigin(origins = {"*"}, maxAge = 3600)
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @CrossOrigin(origins = {"*"})
    public Flux<BookResponse> getBooks() {
        return this.bookService.findAll();
    }

    @GetMapping("/author")
    public Mono<List<String>> getAuthors() {
        return this.bookService.findAuthors().collectList();
    }

    @GetMapping("/title")
    public Mono<List<String>> getTitles() {
        return this.bookService.findTitles().collectList();
    }

    @GetMapping("/epoch")
    public Mono<List<String>> getEpochs() {
        return this.bookService.findEpochs().collectList();
    }

    @GetMapping("/type")
    public Mono<List<String>> getTypes() {
        return this.bookService.findTypes().collectList();
    }

    @GetMapping("/subtype")
    public Mono<List<String>> getSubTypes() {
        return this.bookService.findSubTypes().collectList();
    }

    @GetMapping("/category")
    public Mono<List<String>> getReaderCategories() {
        return this.bookService.findReaderCategories().collectList();
    }

    @GetMapping(value="/nationality", produces = "application/json")
    public Mono<List<String>> getNationalities() {
        return this.bookService.findNationalities().collectList();
    }

    @GetMapping("/status")
    public Mono<List<String>> getStatuses() {
        return this.bookService.findStatuses();
    }

    @GetMapping("/status/{status}")
    public Flux<BookResponse> getBooksByStatus(@PathVariable String status) {
        return this.bookService.findBooks(BookStatus.valueOf(status));
    }

    @GetMapping("/{id}")
    public Mono<BookResponse> getBook(@PathVariable Long id) {
        return this.bookService.findById(id);
    }

    @GetMapping("/request")
    public Flux<BookResponse> getBookByRequest(@RequestBody BookRequest bookRequest) {
        return this.bookService.findBooks(bookRequest);
    }

    @GetMapping("/incomplete")
    public Flux<BookResponse> getBooksToComplete() {
        return this.bookService.findBooksToComplete();
    }


    @PostMapping
    public Mono<ResponseEntity<BookResponse>> createBook(@RequestBody BookRequest book) {
        return this.bookService.saveBook(book)
                .map(bookResponse -> new ResponseEntity<>(bookResponse, HttpStatus.CREATED));
    }

    @PutMapping("/{id}")
    public Mono<BookResponse> updateBook(@PathVariable Long id, @RequestBody BookRequest book) {
        return this.bookService.updateBook(id, book);
    }


}
