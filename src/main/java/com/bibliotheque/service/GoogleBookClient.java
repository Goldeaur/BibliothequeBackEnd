package com.bibliotheque.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;


public interface GoogleBookClient {

    /**
     * Request books from google
     */
    @GetMapping("https://www.googleapis.com/books/v1/volumes?q="+"{title}"+"inauthor:"+"{author}")
    Mono<String> askGoogle(@PathVariable String title, @PathVariable String author);


}
