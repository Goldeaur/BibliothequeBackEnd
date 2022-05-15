package com.bibliotheque.service;

import com.bibliotheque.exception.ResourceNotFoundException;
import com.bibliotheque.model.dao.Credentials;
import com.bibliotheque.model.dto.CredentialsRequest;
import com.bibliotheque.model.dto.CredentialsResponse;
import com.bibliotheque.repository.CustomCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class CredentialsService {

    @Autowired
    private CustomCredentialsRepository credentialsRepo;


    public Mono<CredentialsResponse> saveCredentials(Credentials credentials) {
        return credentialsRepo.save(credentials).flatMap(this::convertIntoResponse);
    }

    public Mono<CredentialsResponse> findById(Long id) {
        return credentialsRepo.findById(id).flatMap(this::convertIntoResponse);
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
                    return credentialsRepo.save(credentialToSave).flatMap(this::convertIntoResponse);
                });
    }

    private Mono<CredentialsResponse> convertIntoResponse(Credentials credentials) {
        return Mono.just(
                CredentialsResponse.builder()
                        .creationDate(credentials.getCreationDate())
                        .email(credentials.getEmail())
                        .phone(credentials.getPhone())
                        .id(credentials.getId())
                        .role(credentials.getRole())
                        .lastModificationDate(credentials.getLastModificationDate())
                        .build()
        );
    }

}
