package com.nttdata.bootcamp.msyanki.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.bootcamp.msyanki.domain.Movement;

import reactor.core.publisher.Flux;

@Repository
public interface IMovementRepository extends ReactiveMongoRepository<Movement, String> {
    Flux<Movement> findByTransactionId(String transactionId);
}
