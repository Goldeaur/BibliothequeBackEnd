package com.bibliotheque.model.dto;

import com.bibliotheque.model.statuses.BookStatus;

public record BookRequest(
        long isbn,
        String title,
        String author,
        String epoch,
        String nationality,
        String type,
        String subType,
        String readerCategory,
        String comment,
        String refBibli,
        BookStatus status
) {

}