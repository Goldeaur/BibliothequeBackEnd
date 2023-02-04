package com.bibliotheque.controller;

import com.bibliotheque.model.dto.BookRequest;
import com.bibliotheque.model.dto.BookResponse;
import com.bibliotheque.service.GoogleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/google")
public class GoogleController {

    private final GoogleService googleService;

    public GoogleController(GoogleService googleService) {
        this.googleService = googleService;
    }

    @PostMapping()
    public Flux<BookResponse> askGoogleBooks(@RequestBody BookRequest googleRequest) {
        return this.googleService.askGoogle(googleRequest);
    }

}
