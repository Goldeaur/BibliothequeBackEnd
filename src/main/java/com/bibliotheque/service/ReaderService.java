package com.bibliotheque.service;

import com.bibliotheque.exception.ResourceNotFoundException;
import com.bibliotheque.model.dao.Credentials;
import com.bibliotheque.model.dao.Reader;
import com.bibliotheque.model.dto.CredentialsRequest;
import com.bibliotheque.model.dto.CredentialsResponse;
import com.bibliotheque.model.dto.ReaderRequest;
import com.bibliotheque.model.dto.ReaderResponse;
import com.bibliotheque.repository.CustomReaderRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ReaderService {

    private final PasswordEncoder passwordEncoder;

    private final CustomReaderRepository readerRepo;

    private final CredentialsService credentialsService;

    public ReaderService(PasswordEncoder passwordEncoder, CustomReaderRepository readerRepo, CredentialsService credentialsService) {
        this.passwordEncoder = passwordEncoder;
        this.readerRepo = readerRepo;
        this.credentialsService = credentialsService;
    }

    public Flux<ReaderResponse> findAll() {
        return this.readerRepo.findAll().flatMap(this::convertIntoResponse);
    }

    public Mono<ReaderResponse> findById(Long id) {
        return this.readerRepo.findById(id).flatMap(this::convertIntoResponse);
    }

    public Mono<ReaderResponse> createReader(ReaderRequest readerRequest) {
        LocalDateTime now = LocalDateTime.now();
        CredentialsRequest credentialsRequest = readerRequest.credentials();
        Credentials encodedCredentials = Credentials.builder()
                .email(credentialsRequest.email())
                .phone(credentialsRequest.phone())
                .password(passwordEncoder.encode(credentialsRequest.password()))
                .role(credentialsRequest.role())
                .creationDate(now)
                .lastModificationDate(now)
                .build();
        Mono<CredentialsResponse> credentialsMono = this.credentialsService.saveCredentials(encodedCredentials);
        return credentialsMono.flatMap(credentialsResponse -> {
            Reader readerToSave = Reader.builder()
                    .city(readerRequest.city())
                    .creationDate(now)
                    .lastModificationDate(now)
                    .firstName(readerRequest.firstName())
                    .lastName(readerRequest.lastName())
                    .credentialsId(credentialsResponse.id())
                    .status(readerRequest.status())
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
                                        .build()));
    }
}
