package com.bibliotheque.controller;

import com.bibliotheque.model.dto.LoanRequest;
import com.bibliotheque.model.dto.LoanResponse;
import com.bibliotheque.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/loan")
public class LoanController {

    private final LoanService lonService;

    public LoanController(LoanService lonService) {
        this.lonService = lonService;
    }

    @GetMapping
    public Flux<LoanResponse> getLoans(){
        return this.lonService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<LoanResponse> getLoan(@PathVariable long id) {
        return this.lonService.findById(id);
    }

    @GetMapping("/today")
    public Flux<LoanResponse> getToday(){
        return this.lonService.findAllToday();
    }

    @GetMapping("/late")
    public Flux<LoanResponse> getLate(){
        return this.lonService.findAllLate();
    }

    @GetMapping("/ongoing")
    public Flux<LoanResponse> getOnGoing(){
        return this.lonService.findAllOnGoing();
    }

    @PostMapping
    public Mono<ResponseEntity<LoanResponse>> createLoan(@RequestBody LoanRequest loan){
        return this.lonService.saveLoan(loan).map(loanResponse -> new ResponseEntity<>(loanResponse, HttpStatus.CREATED));
    }

    @PostMapping("/renew/{id}")
    public Mono<ResponseEntity<LoanResponse>> renewLoan(@PathVariable long id){
        return this.lonService.renewLoan(id).map(loanResponse -> new ResponseEntity<>(loanResponse, HttpStatus.OK));
    }

    @PostMapping("/end/{id}")
    public Mono<ResponseEntity<LoanResponse>> endLoan(@PathVariable long id){
        return this.lonService.endLoan(id).map(loanResponse -> new ResponseEntity<>(loanResponse, HttpStatus.OK));
    }

    @GetMapping("/reader/{id}")
    public Flux<LoanResponse> findByReader(@PathVariable long readerId){
        return this.lonService.findByReader(readerId);


    }


}
