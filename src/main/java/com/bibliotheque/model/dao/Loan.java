package com.bibliotheque.model.dao;


import com.bibliotheque.model.statuses.LoanStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(value = "loan")
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

    @Column(value = "creation_date")
    @CreatedDate
    private LocalDateTime loanDate;

    @Column(value = "end_date")
    @CreatedDate
    private LocalDateTime endDate;

    @Column(value = "return_date")
    private LocalDateTime returnDate;

    @Column(value = "book_id")
    private Long bookId;

    @Column(value = "reader_id")
    private Long readerId;

    @Column(value="status")
    private LoanStatus status;
}
