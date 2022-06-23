package com.bibliotheque.service;

import com.bibliotheque.exception.ResourceNotFoundException;
import com.bibliotheque.model.dao.Credentials;
import com.bibliotheque.model.dao.Reader;
import com.bibliotheque.model.dto.*;
import com.bibliotheque.model.statuses.ReaderStatus;
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

    public Mono<ReaderResponse> authenticate(CredentialsRequest request){
        String login = request.email();
        String password = passwordEncoder.encode(request.password());
        return this.credentialsService.findByCredentials(login, password)
                .flatMap(credentialsResponse -> findByCredentials(credentialsResponse.id()));

    }

    public Flux<ReaderResponse> findAll() {
        return this.readerRepo.findAll().flatMap(this::convertIntoResponse);
    }

    public Mono<ReaderResponse> findByCredentials (Long credentialId){
        return readerRepo.findByCredentials(credentialId).flatMap(this::convertIntoResponse);
    }

    public Mono<ReaderResponse> findById(Long id) {
        return this.readerRepo.findById(id)
                .flatMap(this::convertIntoResponse);
    }

    public Flux<ReaderResponse> findByName(String name) {
        return this.readerRepo.findByName(name)
                .flatMap(this::convertIntoResponse);
    }

    public Mono<ReaderResponse> createReader(CreateReaderRequest readerRequest) {
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
                    .lastName(readerRequest.lastName().toUpperCase())
                    .credentialsId(credentialsResponse.id())
                    .status(ReaderStatus.NEW)
                    .build();
            return this.readerRepo.save(readerToSave).flatMap(this::convertIntoResponse);
        });
    }

    public Mono<ReaderResponse> updateReader(Long idRequested, UpdateReaderRequest readerRequest) {
        return readerRepo.findById(idRequested)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Reader with id : " + idRequested + " does not exist")))
                .flatMap(previousReaderData -> {
                    LocalDateTime now = LocalDateTime.now();
                    Reader readerToSave = new Reader(
                            idRequested,
                            readerRequest.firstName() != null ? readerRequest.firstName() : previousReaderData.getFirstName(),
                            readerRequest.lastName() != null ? readerRequest.lastName() : previousReaderData.getLastName(),
                            readerRequest.city() != null ? readerRequest.city() : previousReaderData.getCity(),
                            readerRequest.status() != null ? readerRequest.status() : previousReaderData.getStatus(),
                            previousReaderData.getCreationDate(),
                            now,
                           previousReaderData.getCredentialsId()
                    );
                           return readerRepo.save(readerToSave).flatMap(this::convertIntoResponse);
                });
    }

    private Mono<ReaderResponse> convertIntoResponse(Reader reader) {
        return credentialsService.findById(reader.getCredentialsId())
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("This credentials does not exist")))
                .map(
                        credentialsResponse ->
                                ReaderResponse.builder()
                                        .id(reader.getId())
                                        .firstName(reader.getFirstName())
                                        .lastName(reader.getLastName())
                                        .city(reader.getCity())
                                        .creationDate(reader.getCreationDate())
                                        .lastModificationDate(reader.getLastModificationDate())
                                        .credentials(credentialsResponse)
                                        .status(reader.getStatus())
                                        .build());
    }

}
