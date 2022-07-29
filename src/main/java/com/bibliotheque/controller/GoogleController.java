package com.bibliotheque.controller;

import com.bibliotheque.model.dto.googleBook.GoogleBooksResponse;
import com.bibliotheque.model.dto.googleBook.GoogleRequest;
import com.bibliotheque.service.GoogleService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/google")
public class GoogleController {

    private final GoogleService googleService;

    public GoogleController(GoogleService googleService) {
        this.googleService = googleService;
    }

    @PostMapping()
    public Mono<GoogleBooksResponse> askGoogleBooks(@RequestBody GoogleRequest googleRequest) {
        return this.googleService.askGoogle(googleRequest);
    }

}
