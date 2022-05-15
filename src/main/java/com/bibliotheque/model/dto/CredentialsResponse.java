package com.bibliotheque.model.dto;
import com.bibliotheque.model.Role;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CredentialsResponse(
        Long id,
        String phone,
        String email,
        LocalDateTime creationDate,
        LocalDateTime lastModificationDate,
        Role role
) {
}