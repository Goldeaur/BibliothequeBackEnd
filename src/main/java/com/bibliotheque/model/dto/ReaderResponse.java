package com.bibliotheque.model.dto;
import com.bibliotheque.model.statuses.ReaderStatus;
import lombok.*;

import java.time.LocalDateTime;

@Builder
public record ReaderResponse (
     Long id,
     String firstname,
     String lastname,
     String city,
     LocalDateTime creationDate,
     LocalDateTime lastModificationDate,
     CredentialsResponse credentials,
     ReaderStatus status
){}
