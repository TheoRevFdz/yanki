package com.nttdata.bootcamp.msyanki.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.nttdata.bootcamp.msyanki.domain.YankiTransaction;
import com.nttdata.bootcamp.msyanki.repositories.IYankiTransactionRepository;
import com.nttdata.bootcamp.msyanki.web.mapper.IYankiTransactionMapper;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class YankiTransactionService {
    @Autowired
    private IYankiTransactionRepository transactionRepository;
    
	@Autowired
    private IYankiTransactionMapper transactionMapper;
	
	public Flux<YankiTransaction> findAll(){
    	log.debug("findAll executed");
        return transactionRepository.findAll();
    }
    
	public Mono<YankiTransaction> findById(String transactionId){
    	log.debug("findById executed {}", transactionId);
        return transactionRepository.findById(transactionId);
    }

	public Mono<YankiTransaction> create(YankiTransaction transaction){
    	log.debug("create executed {}", transaction);
    	
    	return transactionRepository.save(transaction);  
    }
}
