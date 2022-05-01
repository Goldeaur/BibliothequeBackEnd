package com.bibliotheque.controller;


import com.bibliotheque.model.dao.Book;
import com.bibliotheque.model.dto.BookRequest;
import com.bibliotheque.model.dto.BookResponse;
import com.bibliotheque.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public Flux<BookResponse> getBooks() {
        return this.bookService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Book> getBook(@PathVariable Long id) {
        return this.bookService.findById(id);
    }

    @PostMapping
    public Mono<Book> createBook(@RequestBody BookRequest book) {
        return this.bookService.saveBook(book);
    }

    @PutMapping("/{id}")
    public Mono<Book> updateBook(@PathVariable long id, @RequestBody BookRequest book) {
        return this.bookService.updateBook(id, book);
    }


}
