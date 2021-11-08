package com.bibliotheque.controller;

import com.bibliotheque.model.dao.Reader;
import com.bibliotheque.repository.ReaderRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/reader")
public class ReaderController {

    private final ReaderRepository repo;

    public ReaderController(ReaderRepository repo) {
        this.repo = repo;
    }



/*    @GetMapping("/reader/{id}")
    public Mono<ResponseEntity<Reader>> getReaders(@PathVariable "id" Long id) {
        return repo.findById(id).map(acc -> new ResponseEntity<>(acc, HttpStatus.OK)).switchIfEmpty(Mono.just(new ResponseEntity<>(null, HttpStatus.NOT_FOUND)));
    }*/

    @GetMapping
    public Flux<Reader> getAllAccounts() {
        return repo.findAll();
    }

/*    @PostMapping("/createReader")
    public Mono<ResponseEntity<Reader>> createUser(@RequestBody Reader user) {
        return repo.createAccount(user).map(acc -> new ResponseEntity<>(acc, HttpStatus.CREATED)).log();
    }*/


}
