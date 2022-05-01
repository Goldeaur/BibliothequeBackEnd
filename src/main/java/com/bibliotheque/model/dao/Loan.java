package com.bibliotheque.model.dao;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(value = "borrow")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Loan {
    @Id
    @Column(value="id")
    private Long Id;

    @Column(value = "isbn")
    private Long isbn;

    @Column(value = "borrow_date")
    @CreatedDate
    private LocalDateTime borrowDate;

    @Column(value = "return_date")
    private LocalDateTime returnDate;

    private Book book;

    private Reader reader;
}
