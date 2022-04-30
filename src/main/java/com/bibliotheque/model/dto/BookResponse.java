package com.bibliotheque.model.dto;

import com.bibliotheque.model.statuses.BookStatus;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {

    private long id;
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
    private LocalDateTime creationDate;
    private LocalDateTime lastModificationDate;
    private BookStatus status;
}
