package com.bibliotheque.model.dto;

import com.bibliotheque.model.Role;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CredentialsResponse {

    private Long id;

    private String phone;

    private String email;

    private LocalDateTime creationDate;

    private LocalDateTime lastModificationDate;

    private Role role;
}
