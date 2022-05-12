package com.bibliotheque.controller;


import com.bibliotheque.model.dto.BookRequest;
import com.bibliotheque.model.dto.BookResponse;
import com.bibliotheque.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public Flux<BookResponse> getBooks() {
        return this.bookService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<BookResponse> getBook(@PathVariable Long id) {
        return this.bookService.findById(id);
    }

    //TODO
    //getBooksByAuthor
    //getBooksByTitleLikes


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
