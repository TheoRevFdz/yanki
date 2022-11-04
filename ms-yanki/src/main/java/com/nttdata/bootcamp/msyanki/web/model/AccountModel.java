package com.nttdata.bootcamp.msyanki.web.model;

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
public class AccountModel {
    private String id;
    private String nroAccount;
    private String typeAccount;
    private Double amount;
    private String typeDoc;
    private String nroDoc;
    private String nroCard;
    private String level;
}
