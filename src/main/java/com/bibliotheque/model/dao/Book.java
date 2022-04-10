package com.bibliotheque.model.dao;

import com.bibliotheque.model.Role;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(value = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Book {
    @Id
    @Column(value="id")
    private Long Id;

    @Column(value = "isbn")
    private Long isbn;

    @Column(value = "title")
    private String title;

    @Column(value = "author")
    private String author;

    @Column(value = "epoch")
    private String epoch;

    @Column(value = "nationality")
    private String nationality;

    @Column(value = "type")
    private String type;

    @Column(value = "sub_type")
    private String subType;

    @Column(value = "reader_category")
    private String readerCategory;

    @Column(value = "comment")
    private String comment;

    @Column(value = "ref_bibli")
    private String refBibli;

    @Column(value = "creation_date")
    private Long creationDate;

    @Column(value = "last_modification_date")
    private Long lastModificationDate;

    @Column(value="role")
    private Role role;



}
