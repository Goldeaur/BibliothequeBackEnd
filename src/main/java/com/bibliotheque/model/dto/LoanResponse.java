package com.bibliotheque.model.dto;

import com.bibliotheque.model.statuses.LoanStatus;

import java.time.LocalDateTime;

public record LoanResponse(
        long id,
        LocalDateTime creationDate,
        LocalDateTime endDate,
        LocalDateTime returnDate,
        LoanStatus status,
        BookResponse bookResponse,
        ReaderResponse readerResponse
) {
}
