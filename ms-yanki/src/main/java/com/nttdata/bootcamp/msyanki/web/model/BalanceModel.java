package com.nttdata.bootcamp.msyanki.web.model;


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
public class BalanceModel {
	
	@JsonIgnore
    private String id;
		
	private String account;
	
	private String movementId;
	
	private Long amount;
}
