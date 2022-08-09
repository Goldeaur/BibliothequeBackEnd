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
        return credentialsRepo.save(credentials).map(this::convertIntoResponse);
    }

    public Mono<CredentialsResponse> findById(Long id) {
        return credentialsRepo.findById(id).map(this::convertIntoResponse);
    }

    public Mono<CredentialsResponse> findByEmail(String email){
        return credentialsRepo.findByEmail(email)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("incorrect login and/or password")))
                .map(this::convertIntoResponse);
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
                    return credentialsRepo.save(credentialToSave).map(this::convertIntoResponse);
                });
    }

    private CredentialsResponse convertIntoResponse(Credentials credentials) {
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
