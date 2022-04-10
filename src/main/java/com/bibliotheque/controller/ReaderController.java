package com.bibliotheque.controller;

import com.bibliotheque.model.dao.Reader;
import com.bibliotheque.model.dto.ReaderRequest;
import com.bibliotheque.model.dto.ReaderResponse;
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
        return readerService.findById(id).map(acc -> new ResponseEntity<>(acc, HttpStatus.OK)).switchIfEmpty(Mono.just(new ResponseEntity<>(null, HttpStatus.NOT_FOUND)));
    }

    @GetMapping
    public Flux<ReaderResponse> getReaders() {
        return this.readerService.findAll();
    }

    @PostMapping()
    public Mono<ResponseEntity<ReaderResponse>> createReader(@RequestBody ReaderRequest reader) {
        return readerService.createReader(reader).map(acc -> new ResponseEntity<>(acc, HttpStatus.CREATED));
    }



}
