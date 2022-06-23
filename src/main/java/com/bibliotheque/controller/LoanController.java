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

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public Flux<LoanResponse> getLoans(){
        return this.loanService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<LoanResponse> getLoan(@PathVariable long id) {
        return this.loanService.findById(id);
    }

    @GetMapping("/book/{id}")
    public Mono<LoanResponse> getLoanByBookId(@PathVariable long id) {
        return this.loanService.findByBookId(id);
    }

    @GetMapping("/today")
    public Flux<LoanResponse> getToday(){
        return this.loanService.findAllToday();
    }

    @GetMapping("/late")
    public Flux<LoanResponse> getLate(){
        return this.loanService.findAllLate();
    }

    @GetMapping("/ongoing")
    public Flux<LoanResponse> getOnGoing(){
        return this.loanService.findAllOnGoing();
    }

    @PostMapping
    public Mono<ResponseEntity<LoanResponse>> createLoan(@RequestBody LoanRequest loan){
        return this.loanService.saveLoan(loan).map(loanResponse -> new ResponseEntity<>(loanResponse, HttpStatus.CREATED));
    }

    @PostMapping("/renew/{id}")
    public Mono<ResponseEntity<LoanResponse>> renewLoan(@PathVariable long id){
        return this.loanService.renewLoan(id).map(loanResponse -> new ResponseEntity<>(loanResponse, HttpStatus.OK));
    }

    @PostMapping("/end/{id}")
    public Mono<ResponseEntity<LoanResponse>> endLoan(@PathVariable long id){
        return this.loanService.endLoan(id).map(loanResponse -> new ResponseEntity<>(loanResponse, HttpStatus.OK));
    }

    @GetMapping("/reader/{id}")
    public Flux<LoanResponse> findByReader(@PathVariable long readerId){
        return this.loanService.findByReader(readerId);


    }


}
