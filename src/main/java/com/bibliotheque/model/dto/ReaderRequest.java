package com.bibliotheque.model.dto;

import com.bibliotheque.model.statuses.ReaderStatus;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ReaderRequest {
    private String firstName;
    private String lastName;
    private String city;
    private ReaderStatus status;
    private CredentialsRequest credentials;
}
