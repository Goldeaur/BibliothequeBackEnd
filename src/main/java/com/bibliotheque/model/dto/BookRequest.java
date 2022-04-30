package com.bibliotheque.model.dto;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {

    private long  isbn;
    private String title;
    private String author;
    private String epoch;
    private String nationality;
    private String type;
    private String subType;
    private String readerCategory;
    private String comment;
    private String refBibli;
}
