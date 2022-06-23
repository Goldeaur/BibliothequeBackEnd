package com.bibliotheque.service;

import com.bibliotheque.exception.ResourceNotFoundException;
import com.bibliotheque.model.dao.Loan;
import com.bibliotheque.model.dto.BookResponse;
import com.bibliotheque.model.dto.LoanRequest;
import com.bibliotheque.model.dto.LoanResponse;
import com.bibliotheque.model.dto.ReaderResponse;
import com.bibliotheque.model.statuses.BookStatus;
import com.bibliotheque.model.statuses.LoanStatus;
import com.bibliotheque.repository.CustomLoanRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class LoanService {

    private final CustomLoanRepository loanRepo;

    private final BookService bookService;

    private final ReaderService readerService;

    public LoanService(CustomLoanRepository loanRepo, BookService bookService, ReaderService readerService) {
        this.loanRepo = loanRepo;
        this.bookService = bookService;
        this.readerService = readerService;
    }

    public Mono<LoanResponse> saveLoan(LoanRequest request) {
        Loan loanToSave = convertIntoDao(request);
        loanToSave.setStatus(LoanStatus.ONGOING);
        return bookService.updateBookStatus(request.bookId(), BookStatus.BORROWED)
                .flatMap(bookResponse -> loanRepo.save(loanToSave).flatMap(this::convertIntoResponse));

    }

    public Flux<LoanResponse> findAll() {
        return loanRepo.findAll().flatMap(this::convertIntoResponse);
    }

    public Mono<LoanResponse> findById(long loanId) {
        return loanRepo.findById(loanId).flatMap(this::convertIntoResponse);
    }

    public Mono<LoanResponse> findByBookId(long bookId) {
        return loanRepo.findByBookId(bookId).flatMap(this::convertIntoResponse);
    }

    public Flux<LoanResponse> findAllOnGoing() {
        return loanRepo.findAllOnGoing().flatMap(this::convertIntoResponse);
    }

    public Flux<LoanResponse> findAllLate() {
        return loanRepo.findAllLate().flatMap(this::convertIntoResponse);
    }

    public Flux<LoanResponse> findAllToday() {
        return loanRepo.findAllTODAY().flatMap(this::convertIntoResponse);
    }

    public Flux<LoanResponse> findByReader(long readerId) {
        return loanRepo.findByReaderId(readerId).flatMap(this::convertIntoResponse);
    }

    public Mono<LoanResponse> endLoan(long id) {
        return loanRepo.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("loan with id : " + id + " does not exist")))
                .flatMap(loanToSave ->
                        bookService.updateBookStatus(loanToSave.getBookId(), BookStatus.AVAILABLE)
                                .flatMap(bookResponse -> {
                                    loanToSave.setStatus(LoanStatus.RETURNED);
                                    loanToSave.setReturnDate(LocalDateTime.now());
                                    return loanRepo.save(loanToSave)
                                            .flatMap(this::convertIntoResponse);
                                }));
    }

    public Mono<LoanResponse> renewLoan(long id) {
        return loanRepo.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("loan with id : " + id + " does not exist")))
                .flatMap(loanToSave -> {
                    loanToSave.setStatus(LoanStatus.RENEWED);
                    loanToSave.setEndDate(LocalDateTime.now().plusDays(14));
                    return loanRepo.save(loanToSave)
                            .flatMap(this::convertIntoResponse);
                });
    }

    private Loan convertIntoDao(LoanRequest request) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime backDate = now.plusDays(14);
        return Loan.builder()
                .status(LoanStatus.ONGOING)
                .bookId(request.bookId())
                .readerId(request.readerId())
                .returnDate(backDate)
                .loanDate(now)
                .build();
    }

    private Mono<LoanResponse> convertIntoResponse(Loan loan) {
        var bookId = loan.getBookId();
        var readerId = loan.getReaderId();
        Mono<BookResponse> bookResponseMono = bookService.findById(bookId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("book with id : " + bookId + " does not exist")));
        Mono<ReaderResponse> readerResponseMono = readerService.findById(readerId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("reader with id : " + readerId + " does not exist")));
        return bookResponseMono
                .zipWith(readerResponseMono)
                .map(zip -> {
                    var bookResponse = zip.getT1();
                    var readerResponse = zip.getT2();
                    return new LoanResponse(
                            loan.getId(),
                            loan.getLoanDate(),
                            loan.getEndDate(),
                            loan.getReturnDate(),
                            loan.getStatus(),
                            bookResponse,
                            readerResponse
                    );
                });
    }


}
