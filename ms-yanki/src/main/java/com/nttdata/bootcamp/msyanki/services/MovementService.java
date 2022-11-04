package com.nttdata.bootcamp.msyanki.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nttdata.bootcamp.msyanki.domain.Movement;
import com.nttdata.bootcamp.msyanki.repositories.IMovementRepository;
import com.nttdata.bootcamp.msyanki.web.mapper.IMovementMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MovementService {

    @Autowired
    private IMovementRepository movementRepository;
    
	@Autowired
    private IMovementMapper movementMapper;
	
	public Flux<Movement> findAll(){
    	log.debug("findAll executed");
        return movementRepository.findAll();
    }
    
	public Mono<Movement> findById(String movementId){
    	log.debug("findById executed {}", movementId);
        return movementRepository.findById(movementId);
    }

	public Mono<Movement> findByTransactionId(String transactionId){
    	log.debug("findByTransactionId executed {}", transactionId);
        return movementRepository.findByTransactionId(transactionId).last();
    }

	
	public Mono<Movement> create(Movement movement){
    	log.debug("create executed {}", movement);    	
    	return movementRepository.save(movement);  
    }
    
	public Mono<Movement> update(String movementId,  Movement movement){
    	log.debug("update executed {}:{}", movementId, movement);
        return movementRepository.findById(movementId)
                .flatMap(dbMovement -> {
                	movementMapper.update(dbMovement, movement);
                    return movementRepository.save(dbMovement);
                });
    }
    
	public Mono<Movement> delete(String movementId){
    	log.debug("delete executed {}", movementId);
        return movementRepository.findById(movementId)
                .flatMap(existingMovement -> movementRepository.delete(existingMovement)
                .then(Mono.just(existingMovement)));
    }
}
