package com.nttdata.bootcamp.msyanki.web.model;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class YankiTransactionModel {
    private String id;
    @NotBlank
    private String senderPhone;
    @NotBlank
    private String reciverPhone;
    @NotBlank
    private String transactionType;
    private Long amount;
}
