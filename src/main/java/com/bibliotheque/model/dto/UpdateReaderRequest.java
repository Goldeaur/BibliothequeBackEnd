package com.bibliotheque.model.dto;

import com.bibliotheque.model.statuses.ReaderStatus;
import lombok.Builder;

@Builder
public record UpdateReaderRequest(
        String firstname,
        String lastname,
        String city,
        ReaderStatus status) {
}
