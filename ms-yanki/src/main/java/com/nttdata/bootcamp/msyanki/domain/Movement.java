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
@Document("movements")
public class Movement {
    @Id
    private String id;
    @NotNull
    private String transactionId;
    @NotNull
    private String senderAccount;
    @NotNull
    private String reciverAccount;
    @NotNull
    private String mainAccount;
    @NotNull
    private Long amount;
}
