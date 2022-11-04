package com.nttdata.bootcamp.msyanki.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.bootcamp.msyanki.domain.YankiTransaction;

@Repository
public interface IYankiTransactionRepository extends ReactiveMongoRepository<YankiTransaction,String>{
    
}
