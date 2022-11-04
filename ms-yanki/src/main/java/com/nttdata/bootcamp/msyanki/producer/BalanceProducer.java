package com.nttdata.bootcamp.msyanki.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.nttdata.bootcamp.msyanki.web.model.BalanceModel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class BalanceProducer {
	
	private final KafkaTemplate<String, BalanceModel> kafkaTemplate;

	@Value(value = "${kafka.topic.balance.name}")
	private String topic;

	public void sendMessage(BalanceModel balanceModel) {

		ListenableFuture<SendResult<String, BalanceModel>> future = kafkaTemplate.send(this.topic, balanceModel);

		future.addCallback(new ListenableFutureCallback<SendResult<String, BalanceModel>>() {
			@Override
			public void onSuccess(SendResult<String, BalanceModel> result) {
				log.info("Message {} has been sent ", balanceModel);
			}

			@Override
			public void onFailure(Throwable ex) {
				log.error("Something went wrong with the balanceModel {} ", balanceModel);
			}
		});
	}
}
