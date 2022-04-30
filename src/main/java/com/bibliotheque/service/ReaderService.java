package com.bibliotheque.service;

import com.bibliotheque.exception.ResourceNotFoundException;
import com.bibliotheque.model.dao.Credentials;
import com.bibliotheque.model.dao.Reader;
import com.bibliotheque.model.dto.CredentialsRequest;
import com.bibliotheque.model.dto.CredentialsResponse;
import com.bibliotheque.model.dto.ReaderRequest;
import com.bibliotheque.model.dto.ReaderResponse;
import com.bibliotheque.repository.CustomReaderRepository;
import com.bibliotheque.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class ReaderService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomReaderRepository readerRepo;


    @Autowired
    private CredentialsService credentialsService;


    public Flux<ReaderResponse> findAll() {
        return this.readerRepo.findAll().flatMap(this::convertIntoResponse);
    }

    public Mono<ReaderResponse> findById(Long id) {
        return this.readerRepo.findById(id).flatMap(this::convertIntoResponse);
    }

    public Mono<ReaderResponse> createReader(ReaderRequest readerRequest) {
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        CredentialsRequest credentialsRequest = readerRequest.getCredentials();
        Credentials encodedCredentials = Credentials.builder()
                .email(credentialsRequest.getEmail())
                .phone(credentialsRequest.getPhone())
                .password(passwordEncoder.encode(credentialsRequest.getPassword()))
                .role(credentialsRequest.getRole())
                .creationDate(zdt)
                .lastModificationDate(zdt)
                .build();
        Mono<CredentialsResponse> credentialsMono = this.credentialsService.saveCredentials(encodedCredentials);
        return credentialsMono.flatMap(credentialsResponse -> {
            Reader readerToSave = Reader.builder()
                    .city(readerRequest.getCity())
                    .creationDate(zdt)
                    .lastModificationDate(zdt)
                    .firstName(readerRequest.getFirstName())
                    .lastName(readerRequest.getLastName())
                    .credentialsId(credentialsResponse.getId())
                    .status(readerRequest.getStatus())
                    .build();

            return this.readerRepo.save(readerToSave).flatMap(this::convertIntoResponse);
        });
    }

    private Mono<ReaderResponse> convertIntoResponse(Reader reader) {
        return credentialsService.findById(reader.getCredentialsId()).switchIfEmpty(
                Mono.error(new ResourceNotFoundException("This credentials does not exist yet"))
        ).flatMap(
                credentialsResponse ->
                        Mono.just(
                                ReaderResponse.builder()
                                        .id(reader.getId())
                                        .firstName(reader.getFirstName())
                                        .lastName(reader.getLastName())
                                        .city(reader.getCity())
                                        .creationDate(reader.getCreationDate())
                                        .lastModificationDate(reader.getLastModificationDate())
                                        .credentials(credentialsResponse)
                                        .status(reader.getStatus())
                                        .build())
        );
    }
}
