package com.bibliotheque.model.dto;

import com.bibliotheque.model.statuses.BookStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BookResponse(
        Long id,
        String isbn10,
        String isbn13,
        String imageLink,
        String title,
        String author,
        String epoch,
        String nationality,
        String type,
        String subType,
        String readerCategory,
        String comment,
        String description,
        String refBibli,
        LocalDateTime creationDate,
        LocalDateTime lastModificationDate,
        BookStatus status
) {
}

