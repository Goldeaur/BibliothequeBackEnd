package com.bibliotheque.service;

import com.bibliotheque.model.dao.Book;
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

    public Flux<Book> findAll(){
        return bookRepository.findAll();
    }
}
