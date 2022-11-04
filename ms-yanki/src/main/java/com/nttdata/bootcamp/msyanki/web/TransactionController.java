package com.nttdata.bootcamp.msyanki.web;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bootcamp.msyanki.producer.BalanceProducer;
import com.nttdata.bootcamp.msyanki.services.MovementService;
import com.nttdata.bootcamp.msyanki.services.TransactionService;
import com.nttdata.bootcamp.msyanki.web.mapper.IMovementMapper;
import com.nttdata.bootcamp.msyanki.web.mapper.ITransactionMapper;
import com.nttdata.bootcamp.msyanki.web.model.BalanceModel;
import com.nttdata.bootcamp.msyanki.web.model.MovementModel;
import com.nttdata.bootcamp.msyanki.web.model.TransactionModel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/transaction")
public class TransactionController {

	@Value("${spring.application.name}")
	String name;

	@Value("${server.port}")
	String port;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private MovementService movementService;

	@Autowired
	private BalanceProducer balanceProducer;

	@Autowired
	private ITransactionMapper transactionMapper;

	@Autowired
	private IMovementMapper movementMapper;

	@GetMapping
	public Mono<ResponseEntity<Flux<TransactionModel>>> getAll() {
		log.info("getAll executed");
		return Mono.just(ResponseEntity.ok()
				.body(transactionService.findAll().map(transaction -> transactionMapper.entityToModel(transaction))));
	}

	@GetMapping("/{id}")
	public Mono<ResponseEntity<TransactionModel>> getById(@PathVariable String id) {
		log.info("getById executed {}", id);
		return transactionService.findById(id).map(transaction -> transactionMapper.entityToModel(transaction))
				.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PostMapping
	public Mono<ResponseEntity<TransactionModel>> create(@Valid @RequestBody TransactionModel request) {
		log.info("create executed {}", request);
		return transactionService.create(transactionMapper.modelToEntity(request))
				.map(transaction -> transactionMapper.entityToModel(transaction))
				.flatMap(transactionModel -> movementService
						.create(movementMapper.modelToEntity(initializeSender(transactionModel)))
						.map(movement -> movementMapper.entityToModel(movement))
						.map(this::sendBalance)
						.map(movement -> transactionModel))
				.flatMap(transactionModel -> movementService
						.create(movementMapper.modelToEntity(initializeReceiver(transactionModel)))
						.map(movement -> movementMapper.entityToModel(movement))
						.map(this::sendBalance)
						.map(movement -> transactionModel))
				.flatMap(c -> Mono.just(ResponseEntity
						.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "transaction", c.getId())))
						.body(c)))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public Mono<ResponseEntity<TransactionModel>> updateById(@PathVariable String id,
			@Valid @RequestBody TransactionModel request) {
		log.info("updateById executed {}:{}", id, request);
		return transactionService.update(id, transactionMapper.modelToEntity(request))
				.map(transaction -> transactionMapper.entityToModel(transaction))
				.flatMap(c -> Mono.just(ResponseEntity
						.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "transaction", c.getId())))
						.body(c)))
				.defaultIfEmpty(ResponseEntity.badRequest().build());
	}

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id) {
		log.info("deleteById executed {}", id);
		return transactionService.delete(id)
				.doOnSuccess(transaction -> movementService.findByTransactionId(id)
						.map(movement -> movementService.delete(movement.getId())))
				.map(r -> ResponseEntity.ok().<Void>build()).defaultIfEmpty(ResponseEntity.notFound().build());
	}

	private MovementModel initializeSender(TransactionModel transaction) {

		if (transaction.getTransactionType().equalsIgnoreCase("TRF")) {
			return MovementModel.builder().amount(transaction.getAmount() * -1)
					.receiverAccount(transaction.getReceiverAccount()).senderAccount(transaction.getSenderAccount())
					.mainAccount(transaction.getSenderAccount()).transactionId(transaction.getId()).build();
		} else {
			return MovementModel.builder().amount(transaction.getAmount() * 1)
					.receiverAccount(transaction.getReceiverAccount()).senderAccount(transaction.getSenderAccount())
					.mainAccount(transaction.getSenderAccount()).transactionId(transaction.getId()).build();
		}

	}

	private MovementModel initializeReceiver(TransactionModel transaction) {
		if (transaction.getTransactionType().equalsIgnoreCase("TRF")) {
			return MovementModel.builder().amount(transaction.getAmount() * 1)
					.receiverAccount(transaction.getReceiverAccount()).senderAccount(transaction.getSenderAccount())
					.mainAccount(transaction.getReceiverAccount()).transactionId(transaction.getId()).build();
		} else {
			return null;
		}
	}

	private MovementModel sendBalance(MovementModel movement) {
		log.debug("sendBalance executed {}", movement);
		if (movement != null) {
			balanceProducer.sendMessage(BalanceModel.builder().account(movement.getMainAccount())
					.amount(movement.getAmount()).movementId(movement.getId()).build());
		}
		return movement;
	}

}
