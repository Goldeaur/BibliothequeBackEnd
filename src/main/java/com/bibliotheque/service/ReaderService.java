package com.bibliotheque.service;

import com.bibliotheque.model.dao.Reader;
import com.bibliotheque.repository.CustomReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Calendar;

@Service
public class ReaderService {

    @Autowired
    private CustomReaderRepository readerRepo;


    @Autowired
    private CredentialsService credentialsService;


    public Flux<Reader> findAll() {
        return this.readerRepo.findAll();
    }

    public Mono<Reader> findById(Long id) {
        return this.readerRepo.findById(id);
    }

    public Mono<Reader> createReader(Reader reader) {
        LocalDateTime now = LocalDateTime.now();
        return this.credentialsService.saveCredentials(reader.getCredentials())
                .flatMap(credentials -> this.readerRepo.createReader(
                        reader.getFirstName(),
                        reader.getLastName(),
                        reader.getCity(),
                        reader.getStatus().name(),
                        now,
                        credentials.getId())
                );
    }
}
