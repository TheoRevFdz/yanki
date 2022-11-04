package com.nttdata.bootcamp.msyanki.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.bootcamp.msyanki.domain.Transaction;

@Repository
public interface ITransactionRepository extends ReactiveMongoRepository<Transaction,String>{
    
}
