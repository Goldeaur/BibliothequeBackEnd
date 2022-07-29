package com.bibliotheque.model.dto;

import com.bibliotheque.model.statuses.ReaderStatus;
import lombok.Builder;

@Builder
public record CreateReaderRequest(
     String firstname,
     String lastname,
     String city,
     ReaderStatus status,
     CredentialsRequest credentials
){}
