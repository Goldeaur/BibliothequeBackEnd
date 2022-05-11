package com.bibliotheque.model.dto;

import com.bibliotheque.model.statuses.ReaderStatus;
import lombok.Builder;

@Builder
public record CreateReaderRequest(
     String firstName,
     String lastName,
     String city,
     ReaderStatus status,
     CredentialsRequest credentials
){}
