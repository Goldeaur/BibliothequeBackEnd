package com.bibliotheque.model.dao;

import com.bibliotheque.model.ReaderStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Table(value = "reader")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Reader {

    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "first_name")
    private String firstName;

    @Column(value = "last_name")
    private String lastName;

    @Column(value = "phone")
    private String phone;

    @Column(value = "email")
    private String email;

    @Column(value = "city")
    private String city;

    @Column(value = "status")
    private ReaderStatus status;

    @Column(value = "creation_date")
    private Long creationDate;

    @Column(value = "last_modification_date")
    private Long lastModificationDate;


}
