package com.bibliotheque.controller;

import com.bibliotheque.model.dto.CreateReaderRequest;
import com.bibliotheque.model.dto.CredentialsRequest;
import com.bibliotheque.model.dto.ReaderResponse;
import com.bibliotheque.model.dto.UpdateReaderRequest;
import com.bibliotheque.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/reader")
public class ReaderController {

    @Autowired
    private ReaderService readerService;

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ReaderResponse>> getReaderById(@PathVariable Long id) {
        return readerService.findById(id)
                .map(readerResponse -> new ResponseEntity<>(readerResponse, HttpStatus.OK))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(null, HttpStatus.NOT_FOUND)));
    }

    @GetMapping("/name/{name}")
    public Flux<ReaderResponse> getReaderByName(@PathVariable String name) {
        return readerService.findByName(name);
    }

    @GetMapping
    public Flux<ReaderResponse> getReaders() {
        return this.readerService.findAll();
    }

    @PostMapping()
    public Mono<ResponseEntity<ReaderResponse>> createReader(@RequestBody CreateReaderRequest reader) {
        return readerService.createReader(reader)
                .map(readerResponse -> new ResponseEntity<>(readerResponse, HttpStatus.CREATED));
    }

    @PostMapping("/authenticate")
    public Mono<ReaderResponse> authenticate(@RequestBody CredentialsRequest credentialsRequest) {
        return readerService.authenticate(credentialsRequest);
    }

    @PutMapping("/{id}")
    public Mono<ReaderResponse> updateReader(@PathVariable Long id, @RequestBody UpdateReaderRequest readerRequest) {
        return readerService.updateReader(id, readerRequest);
    }

}
