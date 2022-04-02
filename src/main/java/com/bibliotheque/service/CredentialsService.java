package com.bibliotheque.service;

import com.bibliotheque.model.dao.Credentials;
import com.bibliotheque.repository.CustomCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CredentialsService {

    @Autowired
    private CustomCredentialsRepository credentialsRepo;


    public Mono<Credentials> saveCredentials (Credentials credentials){
        return credentialsRepo.save(credentials);
    }

}
