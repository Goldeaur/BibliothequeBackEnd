package com.bibliotheque.model.dto;

import lombok.Builder;

@Builder
public record LoanRequest(
        Long bookId,
        Long readerId
) {
}
