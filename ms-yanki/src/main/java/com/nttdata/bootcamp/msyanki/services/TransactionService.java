package com.nttdata.bootcamp.msyanki.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nttdata.bootcamp.msyanki.domain.Transaction;
import com.nttdata.bootcamp.msyanki.repositories.ITransactionRepository;
import com.nttdata.bootcamp.msyanki.web.mapper.ITransactionMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TransactionService {

	   
    @Autowired
    private ITransactionRepository transactionRepository;
    
	@Autowired
    private ITransactionMapper transactionMapper;
	
	public Flux<Transaction> findAll(){
    	log.debug("findAll executed");
        return transactionRepository.findAll();
    }
    
	public Mono<Transaction> findById(String transactionId){
    	log.debug("findById executed {}", transactionId);
        return transactionRepository.findById(transactionId);
    }

	public Mono<Transaction> create(Transaction transaction){
    	log.debug("create executed {}", transaction);
    	
    	return transactionRepository.save(transaction);  
    }
    
	public Mono<Transaction> update(String transactionId,  Transaction transaction){
    	log.debug("update executed {}:{}", transactionId, transaction);
        return transactionRepository.findById(transactionId)
                .flatMap(dbTransaction -> {
                	transactionMapper.update(dbTransaction, transaction);
                    return transactionRepository.save(dbTransaction);
                });
    }
    
	public Mono<Transaction> delete(String transactionId){
    	log.debug("delete executed {}", transactionId);
        return transactionRepository.findById(transactionId)
                .flatMap(existingTransaction -> transactionRepository.delete(existingTransaction)
                .then(Mono.just(existingTransaction)));
    }
}
