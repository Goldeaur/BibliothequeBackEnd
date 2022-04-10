package com.bibliotheque.repository;

import com.bibliotheque.model.dao.Reader;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomReaderRepository extends ReactiveCrudRepository<Reader, Long> {


}
