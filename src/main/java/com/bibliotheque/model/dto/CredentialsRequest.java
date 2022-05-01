package com.bibliotheque.model.dto;

import com.bibliotheque.model.Role;
import lombok.Builder;

@Builder
public record CredentialsRequest (
    String email,
    String password,
    String phone,
    Role role
){}


