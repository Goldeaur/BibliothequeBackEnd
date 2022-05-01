package com.bibliotheque.model.dao;

import com.bibliotheque.model.Role;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;


@Table(value = "credentials")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Credentials {
    @Id
    @Column(value="id")
    private Long id;

    @Column(value = "login")
    private String login;

    @Column(value = "password")
    private String password;

    @Column(value = "phone")
    private String phone;

    @Column(value = "email")
    private String email;

    @Column(value = "creation_date")
    @CreatedDate
    private LocalDateTime creationDate;

    @Column(value = "last_modification_date")
    @LastModifiedDate
    private LocalDateTime lastModificationDate;

    @Column(value="role")
    private Role role;

}
