package com.bibliotheque.converter;

import com.bibliotheque.model.dao.Book;
import com.bibliotheque.model.dao.Credentials;
import com.bibliotheque.model.dto.BookRequest;
import com.bibliotheque.model.dto.BookResponse;
import com.bibliotheque.model.dto.CredentialsResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class Converters {
    public Book convertIntoDao(BookRequest bookRequest) {
        return Book.builder()
                .isbn10(bookRequest.isbn10())
                .isbn13(bookRequest.isbn13())
                .title(bookRequest.title())
                .author(bookRequest.author())
                .epoch(bookRequest.epoch())
                .nationality(bookRequest.nationality())
                .type(bookRequest.type())
                .subType(bookRequest.subType())
                .readerCategory(bookRequest.readerCategory())
                .comment(bookRequest.comment())
                .refBibli(bookRequest.refBibli())
                .creationDate(LocalDateTime.now())
                .lastModificationDate(LocalDateTime.now())
                .status(bookRequest.status())
                .build();
    }

    public BookResponse convertIntoResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .isbn10(book.getIsbn10())
                .isbn13(book.getIsbn13())
                .imageLink(book.getImageLink())
                .title(book.getTitle())
                .author(book.getAuthor())
                .epoch(book.getEpoch())
                .nationality(book.getNationality())
                .type(book.getType())
                .subType(book.getSubType())
                .readerCategory(book.getReaderCategory())
                .comment(book.getComment())
                .description(book.getDescription())
                .refBibli(book.getRefBibli())
                .creationDate(book.getCreationDate())
                .lastModificationDate(book.getLastModificationDate())
                .status(book.getStatus())
                .build();
    }

    public CredentialsResponse convertIntoResponse(Credentials credentials) {
        return CredentialsResponse.builder()
                .creationDate(credentials.getCreationDate())
                .email(credentials.getEmail())
                .phone(credentials.getPhone())
                .id(credentials.getId())
                .role(credentials.getRole())
                .lastModificationDate(credentials.getLastModificationDate())
                .build();
    }
}
