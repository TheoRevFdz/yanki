package com.nttdata.bootcamp.msyanki.domain;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("yanki_transactions")
public class YankiTransaction {
    @Id
    private String id;
    @NotNull
    private String senderPhone;
    @NotNull
    private String reciverPhone;
    @NotNull
    private String transactionType;
    @NotNull
    private Long amount;
}
