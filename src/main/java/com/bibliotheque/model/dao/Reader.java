package com.bibliotheque.model.dao;

import com.bibliotheque.model.statuses.ReaderStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.ZonedDateTime;


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

    @Column(value = "city")
    private String city;

    @Column(value = "status")
    private ReaderStatus status;

    @Column(value = "creation_date")
    @CreatedDate
    private ZonedDateTime creationDate;

    @Column(value = "last_modification_date")
    @LastModifiedDate
    private ZonedDateTime lastModificationDate;

    @Column(value = "credentials_id")
    private Long credentialsId;

}
