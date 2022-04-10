package com.bibliotheque.service;

import com.bibliotheque.model.dao.Credentials;
import com.bibliotheque.model.dto.CredentialsResponse;
import com.bibliotheque.repository.CustomCredentialsRepository;
import com.bibliotheque.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
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

    private Mono<CredentialsResponse> convertIntoResponse(Credentials credentials) {
        return Mono.just(CredentialsResponse.builder()
                .creationDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(credentials.getCreationDate()), Utils.getZoneId()))
                .email(credentials.getEmail())
                .phone(credentials.getPhone())
                .id(credentials.getId())
                .role(credentials.getRole())
                .lastModificationDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(credentials.getLastModificationDate()), Utils.getZoneId()))
                .build());
    }

}
