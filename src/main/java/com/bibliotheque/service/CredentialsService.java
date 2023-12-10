package com.bibliotheque.service;

import com.bibliotheque.converter.Converters;
import com.bibliotheque.exception.ResourceNotFoundException;
import com.bibliotheque.model.dao.Credentials;
import com.bibliotheque.model.dto.CredentialsRequest;
import com.bibliotheque.model.dto.CredentialsResponse;
import com.bibliotheque.repository.CustomCredentialsRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class CredentialsService {

    private final CustomCredentialsRepository credentialsRepo;

    private final Converters converter;

    public CredentialsService(CustomCredentialsRepository credentialsRepo, Converters converter){
        this.credentialsRepo = credentialsRepo;
        this.converter = converter;
    }



    public Mono<CredentialsResponse> saveCredentials(Credentials credentials) {
        return credentialsRepo.save(credentials).map(converter::convertIntoResponse);
    }

    public Mono<CredentialsResponse> findById(Long id) {
        return credentialsRepo.findById(id).map(converter::convertIntoResponse);
    }

    public Mono<CredentialsResponse> findByEmail(String email){
        return credentialsRepo.findByEmail(email)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("incorrect login and/or password")))
                .map(converter::convertIntoResponse);
    }

    public Mono<String> findPasswordByEmail (String email){
        return credentialsRepo.findPasswordByEmail(email)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Incorrect login")));
    }

    public Mono<CredentialsResponse> updateCredentials(Long id, CredentialsRequest credentialsRequest) {
        return credentialsRepo.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Credentials with id : " + id + " does not exist")))
                .flatMap(previousCredentials -> {
                    LocalDateTime now = LocalDateTime.now();
                    Credentials credentialToSave = new Credentials(
                            id,
                            credentialsRequest.password() != null ? credentialsRequest.password() : previousCredentials.getPassword(),
                            credentialsRequest.phone() != null ? credentialsRequest.phone() : previousCredentials.getPhone(),
                            credentialsRequest.email() != null ? credentialsRequest.email() : previousCredentials.getEmail(),
                            previousCredentials.getCreationDate(),
                            now,
                            previousCredentials.getRole()
                    );
                    return credentialsRepo.save(credentialToSave).map(converter::convertIntoResponse);
                });
    }



}
