package com.bibliotheque.model.dto;

import com.bibliotheque.model.Role;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CredentialsRequest {
    private String email;
    private String password;
    private String phone;
    private Role role;
}


