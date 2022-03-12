package com.bibliotheque.model.dao;

import com.bibliotheque.model.Role;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;

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
    private long Id;

    @Column(value = "login")
    private String login;

    @Column(value = "password")
    private String password;

    @Column(value = "phone")
    private String phone;

    @Column(value = "email")
    private String email;

    @Column(value = "creation_date")
    private Date creationDate;

    @Column(value = "last_modification_date")
    private Date lastModificationDate;

    @Column(value="role")
    private Role role;

}
