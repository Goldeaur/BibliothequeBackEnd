package com.bibliotheque.model.dto;

import com.bibliotheque.model.statuses.ReaderStatus;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReaderResponse {

    private long id;
    private String firstName;
    private String lastName;
    private String city;
    private LocalDateTime creationDate;
    private LocalDateTime lastModificationDate;
    private CredentialsResponse credentials;
    private ReaderStatus status;
}
