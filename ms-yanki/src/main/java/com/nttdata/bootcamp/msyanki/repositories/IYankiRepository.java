package com.nttdata.bootcamp.msyanki.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.bootcamp.msyanki.domain.Yanki;

import reactor.core.publisher.Mono;

@Repository
public interface IYankiRepository extends ReactiveMongoRepository<Yanki, String> {
    public Mono<Yanki> findByNroDoc(String nroDoc);

    public Mono<Yanki> findByNroAccount(String nroAccount);

    public Mono<Yanki> findByNroPhone(String nroPhone);
}
