package com.bibliotheque.controller;


import com.bibliotheque.model.dao.Book;
import com.bibliotheque.model.dto.BookResponse;
import com.bibliotheque.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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


}
