package com.nttdata.bootcamp.msyanki.web.model;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @JsonIgnore
    private String id;
    @NotBlank
    private String senderPhone;
    @NotBlank
    private String receiverPhone;
    @NotBlank
    private String transactionType;
    private Long amount;
}
